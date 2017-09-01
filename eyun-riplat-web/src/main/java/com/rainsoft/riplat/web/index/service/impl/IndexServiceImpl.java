package com.rainsoft.riplat.web.index.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.index.dao.IIndexDao;
import com.rainsoft.riplat.web.index.model.Index;
import com.rainsoft.riplat.web.index.service.IIndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("indexService")
public class IndexServiceImpl extends MybatisBasePersitenceServiceImpl<Index, String> implements IIndexService {

	@Resource
	private IIndexDao indexDao;

	@Override
	protected IMybatisPersitenceDao<Index, String> getBaseDao() {

		return indexDao;

	}
	
	/**
	 * 方法功能说明：查询新闻、公告列表
	 * @author sh_j_wangwen
	 * @data 2015年12月09号
	 * @param articleType 新闻类型
	 * @return
	 */
	@Override
	public Result findNewsBy(Map<String,Object> map){
		Result result = new Result();
//		List<Index> newsList = indexDao.selectList("findNewsBy", map);
//		if(newsList==null || newsList.size()<=0){
//			result.setMessage("查无数据！");
//			result.setStatus(Constants.RETURN_ERROR);
//		}else{
//			// 定义字符拼接变量 htmlStr
//			StringBuilder htmlStr = new StringBuilder();
//			htmlStr.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
//			// 循环拼接html代码 http://localhost:8080/eyun-union
//			for (Index obj : newsList) {
//				htmlStr.append("<tr><td><a href='");
//				htmlStr.append(map.get("contextPath")+"/index/index/findById.do?articleID=");
//				htmlStr.append(obj.getArticleID());
//				htmlStr.append("' target='_blank'>");
//				htmlStr.append(obj.getTitle());
//				htmlStr.append("</a></td><td>");
//				htmlStr.append(obj.getCreateDate().toString());
//				htmlStr.append("</td></tr>");
//			}
//			htmlStr.append("</table>");
//			JSONObject obj = new JSONObject();
//			obj.put("htmlStr", htmlStr);
//			result.setData(obj);
			result.setMessage("查询成功！");
		result.setStatus(Constants.RETURN_SUCCESS);
//		}
		return result;
	}
	
	/**
	 * 方法功能说明：根据id查询新闻、公告明细
	 * @author sh_j_wangwen
	 * @data 2015年12月09号
	 * @param articleID 新闻ID
	 * @return
	 */
	@Override
	public Index findById(String articleID){
		Index news = indexDao.findById(articleID);
		if(news!=null && news.getContent() != null && !"".equals(news)){
			// 处理新闻明细空格、换行
			String content = news.getContent();
			content = content.replace("&amp;nbsp;", "&nbsp;");
			content = content.replace("&lt;", "<");
			content = content.replace("&gt;", ">");
			news.setContent(content);
		}
		return news;
	}
	
	/**
	 * 方法功能说明：根据id查询头像、旺旺币余额、短信余额
	 * @author sh_j_wangwen
	 * @data 2015年12月11号
	 * @param userId 当前用户id
	 * @return
	 */
	@Override
	public Result findAccountInfo(Map<String,Object> map){
		Result result = new Result();
		Index userInfo = indexDao.findBy("findAccountInfo", map.get("userId"));
		if(userInfo != null && !"".equals(userInfo)){
				if(userInfo.getWwbBalance()==null || "".equals(userInfo.getWwbBalance())){
					userInfo.setWwbBalance("0.0");
				}
				if(userInfo.getNoteBalance()==null || "".equals(userInfo.getNoteBalance())){
					userInfo.setNoteBalance("0");
				}
				if(userInfo.getMemberHP()==null || "".equals(userInfo.getMemberHP())){
					userInfo.setMemberHP(map.get("contextPath")+"/images/Koala.jpg");
				}
				result.setData(JSONObject.toJSONString(userInfo));
				result.setMessage("查询成功！");
			result.setStatus(Constants.RETURN_SUCCESS);
		}else{
//			userInfoMap.put("HEADIMGURL", getApplication().getContextPath()+ "/common/images/user.png");
			userInfo.setWwbBalance("0.0");
			userInfo.setNoteBalance("0");
			userInfo.setMemberHP(map.get("contextPath")+"/images/Koala.jpg");
			result.setMessage("查询失败！");
			result.setStatus(Constants.RETURN_ERROR);
			result.setData(JSONObject.toJSONString(userInfo));
		}
		return result;
	}
}