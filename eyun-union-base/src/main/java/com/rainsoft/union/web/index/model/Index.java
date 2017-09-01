package com.rainsoft.union.web.index.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 功能说明：用户实体
 * 
 * @author admin
 * @created 2014年6月16日 上午6:01:02
 * @updated
 */
public class Index extends PersistenceCommon {
	private Integer articleID; // 新闻ID
	private String title; // 标题
	private String validDate; // 生效时间
//	private String createTime; // 创建时间
	private String content; // 文章内容
	private Integer userId;//当前用户id
	
	private String wwbBalance; // 旺旺币余额
	private String smsBalance; // 短信余额
	private String memberHP;//会员头像
	private String createDate;//创建时间
	private String articleType;//新闻类型
	
	public String getWwbBalance() {
		return wwbBalance;
	}
	public void setWwbBalance(String wwbBalance) {
		this.wwbBalance = wwbBalance;
	}
	public String getSmsBalance() {
		return smsBalance;
	}
	public void setSmsBalance(String smsBalance) {
		this.smsBalance = smsBalance;
	}
	public String getMemberHP() {
		return memberHP;
	}
	public void setMemberHP(String memberHP) {
		this.memberHP = memberHP;
	}

	public Integer getArticleID() {
		return articleID;
	}

	public void setArticleID(Integer articleID) {
		this.articleID = articleID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

//	public void setCreateTime(String createTime) {
//		// 时间处理，显示年月日
//		if (createTime != null && createTime.length() > 10) {
//			this.createTime = createTime.substring(0, 10);
//		} else {
//			this.createTime = createTime;
//		}
//	}

	
	public String getContent() {
		return content;
	}

	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	};
}