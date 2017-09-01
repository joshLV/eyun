package com.rainsoft.riplat.web.mgrparam.controller;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.mgrparam.model.Platform;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;
import com.rainsoft.riplat.web.notice.service.INoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/mgrparam/platform")
public class PlatformController extends SpringBaseController<Platform, String> {

    @Resource
    private IPlatformService platformServiceImpl;
    
    @Resource
    private INoticeService noticeServiceImpl;
    
    @Override
    protected IMybatisBasePersitenceService<Platform, String> getBaseService() {
        return platformServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/mgrparam/";
    }
    
    /**
     * 跳转到平台信息列表 页面
     */
    @RequestMapping("/toPlatformList")
    public String toList(Model model) {
        List<Platform> List = platformServiceImpl.getPlatformTypeList();
        String platFormId = platformServiceImpl.getStrRandom(8);
        model.addAttribute("List", List);//添加分类信息
        model.addAttribute("rowList", rowList);//添加分页信息
        model.addAttribute("platFormId",platFormId);
        return getPrefix()+"/platformList";
    }
    
    /**
     * 保存
     */
    @RequestMapping("/save")
    public void save(Platform item) {
//    	Result result = new Result();
        Platform platform = new Platform();
        
        /** del by huxiaolong 2016/05/11 begin */
        /** 新增主键ID不需要session中获取
        	String id = SpringMvcUtil.getParameter("dialogId");
        del by huxiaolong 2016/05/11 end */
        /**根據ID判斷新增還是為修改 begin**/
        String id = SpringMvcUtil.getParameter("dialogId");
        if(StringUtil.isNotEmpty(id)){
            platform.setId(Integer.valueOf(id));
        }
        /**根據ID判斷新增還是為修改 end**/
        String platformName = SpringMvcUtil.getParameter("addPlatformName");
        String platformIP = SpringMvcUtil.getParameter("addPlatformIp");
        String platformType = SpringMvcUtil.getParameter("addGroupType");
        /**** 添加新的字段 begin by sh_j_xuchunchun 2016/5/23***/
        String platformUserName=SpringMvcUtil.getParameter("addPlatformUserName");
        String platformUserPhone=SpringMvcUtil.getParameter("addPlatformUserPhone");
        String platformUserAddress=SpringMvcUtil.getParameter("addPlatformUserAddress");
        platform.setPlatformUserName(platformUserName);
        platform.setPlatformUserPhone(platformUserPhone);
        platform.setPlatformUserAddress(platformUserAddress);
        /**** 添加新的字段 end by sh_j_xuchunchun 2016/5/23***/
        platform.setPlatformIP(platformIP.trim());
        platform.setPlatformName(platformName.trim());
        platform.setPlatformType(platformType);
        
        /** del by huxiaolong 2016/05/11 begin */
        /** 新增主键ID不需要特殊操作
        if(StringUtil.isEmpty(id)){//修改组信息不允许更改平台信息类型
            platform.setId(Integer.valueOf(id));
        }
        /** del by huxiaolong 2016/05/11 end */
        super.save(platform);
    }
  
    
    
    /**
	 * 
	 */
	@RequestMapping(value = "/savePlatform", method = RequestMethod.POST)
	public void savePlatform(Platform item){
			Platform platform = new Platform();
			List<Platform> list =platformServiceImpl.getPlatformIdList();
	        String platformId = SpringMvcUtil.getParameter("addPlatformId");
	        String platformName = SpringMvcUtil.getParameter("addPlatformName");
	        String platformIP = SpringMvcUtil.getParameter("addPlatformIp");
	        String platformType = SpringMvcUtil.getParameter("addGroupType");
	        /**** 添加新的字段 begin by sh_j_xuchunchun 2016/5/23***/
	        String platformUserName=SpringMvcUtil.getParameter("addPlatformUserName");
	        String platformUserPhone=SpringMvcUtil.getParameter("addPlatformUserPhone");
	        String platformUserAddress=SpringMvcUtil.getParameter("addPlatformUserAddress");
	        platform.setPlatformUserName(platformUserName);
	        platform.setPlatformUserPhone(platformUserPhone);
	        platform.setPlatformUserAddress(platformUserAddress);
	        /**** 添加新的字段 end by sh_j_xuchunchun 2016/5/23***/
	        platform.setPlatformId(platformId.trim());
	        platform.setPlatformIP(platformIP.trim());
	        platform.setPlatformName(platformName.trim());
	        platform.setPlatformType(platformType);
	        RequestContext requestContext = new RequestContext(getRequest());
	        Result result = null;
			try {
				result = platformServiceImpl.savePlatform(platform, requestContext);
			} catch (Exception e) {
				result.setMessage("保存失败");
				result.setStatus(Constants.RETURN_ERROR);
				e.printStackTrace();
			}
	        this.responseAjaxData(result);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/updPlatform", method = RequestMethod.POST)
	public void updPlatform(Platform item)  {
		Platform platform = new Platform();
		String id = SpringMvcUtil.getParameter("dialogId");
        if(StringUtil.isNotEmpty(id)){
            platform.setId(Integer.valueOf(id));
        }
        /**根據ID判斷新增還是為修改 end**/
        String platformId = SpringMvcUtil.getParameter("addPlatformId");
        String platformName = SpringMvcUtil.getParameter("addPlatformName");
        String platformIP = SpringMvcUtil.getParameter("addPlatformIp");
        String platformType = SpringMvcUtil.getParameter("addGroupType");
        /**** 添加新的字段 begin by sh_j_xuchunchun 2016/5/23***/
        String platformUserName=SpringMvcUtil.getParameter("addPlatformUserName");
        String platformUserPhone=SpringMvcUtil.getParameter("addPlatformUserPhone");
        String platformUserAddress=SpringMvcUtil.getParameter("addPlatformUserAddress");
        platform.setPlatformUserName(platformUserName);
        platform.setPlatformUserPhone(platformUserPhone);
        platform.setPlatformUserAddress(platformUserAddress);
        /**** 添加新的字段 end by sh_j_xuchunchun 2016/5/23***/
        platform.setPlatformIP(platformIP.trim());
        platform.setPlatformName(platformName.trim());
        platform.setPlatformId(platformId.trim());
        platform.setPlatformType(platformType);
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = null;
		try {
			result = platformServiceImpl.updatePlatform(platform, requestContext);
		} catch (Exception e) {
			result.setMessage("更新失败");
			result.setStatus(Constants.RETURN_ERROR);
			e.printStackTrace();
		}
		this.responseAjaxData(result);
	}

    /**
     * 删除平台信息
     */
    @RequestMapping("/delete")
    public void delete(Platform item) {
    	platformServiceImpl.deletePlatform(item);
        super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "删除成功", null));

    }
    /**
     * 修改页面加载信息
     * @param platform
     */
    @ResponseBody
    @RequestMapping("getPlatformById")
    public void getPlatformById(Platform platform) {
        super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "成功", platformServiceImpl.findById(platform.getId().toString())));
    }

    /**
     * 生成其他平台导入的加密文件
     * 
     * @param model
     * @return
     */
    @RequestMapping("/generateLicense.do")
    public void generateKeyFile(Model model) {
        String id = SpringMvcUtil.getParameter("genPlatId");// 获取从页面传入的平台ID
        String license = null;// 平台Key内容
        if (StringUtil.isNotEmpty(id)) {
            Platform platform = platformServiceImpl.findById(id);
            if (platform != null && StringUtil.isNotEmpty(platform.getPlatformType())) {
                String uuid = UUID.randomUUID().toString();
                license = platform.getPlatformType()+uuid.substring(uuid.indexOf("-"), uuid.lastIndexOf("-"));
                logger.info("license: "+license);
                //insert license
                
                //insert license into DB
                platform.setPlatformId(license);
                platform.setActiveable("0");
                platformServiceImpl.update(platform);
                /**
            	 * add 加入新的Platform到Map中，
            	 * bigin 20160510 by huxiaolong
            	 */
                Map<String, String> platMap = new HashMap<String, String>();
                platMap.put("activeableName", platform.getActiveableName());
                platMap.put("platformTypeName", platform.getPlatformTypeName());
                platMap.put("activeable", platform.getActiveableName());
                platMap.put("platformType", platform.getActiveableName());
                platMap.put("platformId", platform.getActiveableName());
                platMap.put("platformName", platform.getActiveableName());
                platMap.put("platformIP", platform.getPlatformIP());
                Constants.PLATFORM_DETAIL_MAP.put(license,platMap);
                /**
                 * end 20160510 by huxiaolong
                 */
                Constants.PLATFORM_MAP.put(license, "0");//加入新的License到Map中
                //下载license文件
                download(license, SystemConfigUtil.getValue("PLATFORMID_DOWNLOADFILENAME"));
                
                return;
            }
        } else {
            super.responseAjaxData(new Result(Constants.RETURN_ERROR, "生成文件失败，平台ID不能为空", null));
        }
    }
    
    /**
     * 下载文件
     * @param content
     * @param fileName
     */
	public void download(String content, String fileName) {
		getResponse().reset(); // 非常重要
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("multipart/form-data");
		String finalfileName = null;
		try {
			finalfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		getResponse().setHeader("Content-Disposition",
				"attachment;fileName= " + finalfileName);
		InputStream in = null;
		OutputStream os = null;
		BufferedWriter out = null;
		File tempFile = null;
		try {
			// 创建临时文件
			tempFile = File.createTempFile("riplat_temp", ".txt");
			// 向临时文件中写入内容
			out = new BufferedWriter(new FileWriter(tempFile));

			out.write(content);

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			in = new FileInputStream(tempFile);
			os = getResponse().getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = in.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			SpringMvcUtil.getRealPath();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {// 关闭流
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 方法结束时删除临时文件
			tempFile.delete();
		}
	}
}
