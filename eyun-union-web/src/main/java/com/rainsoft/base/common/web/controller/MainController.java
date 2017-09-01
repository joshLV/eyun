package com.rainsoft.base.common.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.*;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseUser;
import com.rainsoft.base.web.system.service.IBaseCompanyService;
import com.rainsoft.base.web.system.service.IBaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Random;


@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Resource
	private IBaseUserService baseUserService;
	@Resource
	private IBaseCompanyService baseCompanyService;

	/**
	 *
	 * 方法功能说明：访问项目、页面初期跳转
	 *
	 * @author sh_j_baoxu
	 * @throws ServletException
	 * @throws IOException
	 * @data 2014年12月25日 上午10:54:54
	 */
	@RequestMapping("/login")
	public String Main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "forward:/tologin.do";
	}

	/**
	 *
	 * 方法功能说明：登录成功后跳转
	 *
	 * @author sh_j_baoxu
	 * @data 2015年12月15日 下午9:58:33
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/main/union")
	public String toUnion(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		List<BaseResource> resourceList = null;
		Object obj = SpringMvcUtil.getSessionAttribute(request, "allResource");
		if(obj instanceof List) {
			resourceList = (List<BaseResource>) obj;
		}
		String allResourceList = JSONArray.toJSONString(resourceList);
		model.addAttribute("allResourceList", allResourceList);
		List<BaseCompany> placeList = baseCompanyService.selectList("getPlaceList", SpringMvcUtil.getUserId());
		model.addAttribute("placeList", placeList);
		return "main";
	}
	/**
	 *
	 * 方法功能说明：未登录跳转
	 *
	 * @author sh_j_baoxu
	 * @data 2015年12月15日 下午9:58:57
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/tologin")
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		try {
			//每次都会生成新的密钥
			KeyPair kp = RSAUtil.generateKeyPair();
			RSAPublicKey rsap = (RSAPublicKey) kp.getPublic();
			String module = rsap.getModulus().toString(16);
			String empoent = rsap.getPublicExponent().toString(16);
			// 私钥存入session
			HttpSession hs = request.getSession();
			hs.setAttribute("privKey", kp.getPrivate());
			request.setAttribute("m", module);
			request.setAttribute("e", empoent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}

	@RequestMapping("/logout")
	public String loginout() {
		HttpSession session = SpringMvcUtil.getRequest().getSession();
		session.invalidate();
		return "forward:/tologin.do";
	}

	/**
	 * 功能说明：系统登录
	 *
	 * @author admin
	 * @return
	 * @created 2014年7月7日 上午9:12:15
	 * @updated
	 * @return
	 */
	@RequestMapping("/dologin")
	public void login(HttpServletRequest request, HttpServletResponse response,
					  @RequestParam("loginName") String loginName, @RequestParam("password") String password, @RequestParam(value = "rememberPwd", required = false) String rememberPwd) throws UnsupportedEncodingException {
		Result result = new Result();
		String vCode = request.getParameter("vCode");
		if (StringUtil.isNotEmpty(vCode)) {
			if (!vCode.toUpperCase().equals(request.getSession().getAttribute("rand"))) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage("验证码不正确，请重新输入");
				responseAjaxData(response, result);
				return;
			}
		}
		BaseUser user = new BaseUser();

		//是对前段的encodeURIComponent 进行的反解密操作，主要针对中文的情况
		try {
			HttpSession hs = request.getSession();
			RSAPrivateKey privKey = (RSAPrivateKey) hs.getAttribute("privKey");
			// 解密
			String pwd = password;
			byte[] en_result = RSAUtil.hexStringToBytes(pwd);
			byte[] de_result = RSAUtil.decrypt(privKey, en_result);

			StringBuffer sb = new StringBuffer();
			sb.append(new String(de_result));
			pwd = sb.reverse().toString();
			user.setPassword(pwd);
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_OTHER);
			result.setMessage("私钥过期了");
			responseAjaxData(response, result);
			return;
		}
		user.setLoginName(loginName);
		user.setLoginWay(1); // 最后登录方式, 1：web网页; 2：wap网页; 3：手机客户端；4：未知
		result = baseUserService.login(user, request);
		if(Constants.RETURN_SUCCESS.equals(result.getStatus())) {
			if (StringUtil.isNotEmpty(rememberPwd) && "yes".equals(rememberPwd)) {
				// 保存cookie一个月
				Cookie cookie = new Cookie("userId", SpringMvcUtil.getUserId().toString());
				cookie.setMaxAge(30 * 24 * 60 * 60);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		responseAjaxData(response, result);
	}

	//设置字母的大小,大小
	private Font mFont = new Font("Times New Roman", Font.PLAIN, 17);
	/**
	 * 生成验证码
	 * @param request request
	 * @param response response
	 * @throws Exception
	 */
	@RequestMapping("/loginVCode")
	private void v_code(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		//表明生成的响应是图片
		response.setContentType("image/jpeg");
		int width=103, height=38;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200,250));
		g.fillRect(1, 1, width-1, height-1);
		g.setColor(new Color(102,102,102));
		g.drawRect(0, 0, width-1, height-1);
		g.setFont(mFont);
		g.setColor(getRandColor(160,200));
		//画随机线
		for (int i=0;i<155;i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x,y,x + xl,y + yl);
		}
		//从另一方向画随机线
		for (int i = 0;i < 70;i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x,y,x - xl,y - yl);
		}

		//生成随机数,并将随机数字转换为字母
		String sRand="";
		for (int i=0;i<4;i++) {
			int itmp = random.nextInt(26) + 65;
			char ctmp = (char)itmp;
			sRand += String.valueOf(ctmp);
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			g.drawString(String.valueOf(ctmp),15*i+24,24);
		}
		HttpSession session = request.getSession(true);
		// 验证码存入session
		session.setAttribute("rand",sRand);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	/**
	 * 随机获取颜色
	 * @param fc 参数
	 * @param bc 参数
	 * @return  Color对象
	 */
	Color getRandColor(int fc,int bc)
	{
		Random random = new Random();
		if(fc>255) fc=255;
		if(bc>255) bc=255;
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}

	/**
	 *
	 * 方法功能说明：Ajax返回处理
	 *
	 * @author sh_j_baoxu
	 * @data 2015年11月25日 上午12:13:52
	 * @param result
	 */
	private void responseAjaxData(HttpServletResponse response, Result result) {
		// 定义返回的数据类型：json
		JSONObject jsonObj = new JSONObject();

		String data = "";
		if (Util.isNotNull(result.getData())) {
			data = result.getData().toString();
		}
		jsonObj.put("status", result.getStatus());
		jsonObj.put("msg", result.getMessage());
		jsonObj.put("data", data);

		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		try {

			response.getWriter().print(jsonObj);
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("responseAjaxData出错",e);
		}
	}
}
