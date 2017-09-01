package com.rainsoft.riplat.web.notice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.riplat.web.notice.model.Group;

public interface IGroupService extends IMybatisBasePersitenceService<Group, String> {

	List<BaseArea> getBaseAreaByUserId(String userId, String platformId);

	/**
	 * 功能说明：根据group保存编辑组的信息
	 * 
	 * @param group
	 *            组实体对象
	 * @return Integer 影响行数
	 */
	void saveOrUpdate(Group group);

	Group loadGroupData(Group group);

	Integer deleteByIdWithMember(String id);

	// Integer updateGroup(Group group);

	/**
	 * 功能说明：根据id删除组的信息
	 * 
	 * @param id
	 *            组id
	 * @return Integer 影响行数 
	 */
	Integer deleteById(String id);

	/**
	 * 功能说明：根据id获取组的信息
	 * 
	 * @param id
	 *            组id
	 * @return Group组实体对象
	 */
	Group getGroupById(String id);

	/**
	 * @Description: 修改组信息
	 * @param group 组信息实体
	 * @return Result 返回的结果
	 * @author xcc
	 */
	public Result updateGroup(Group group, HttpServletRequest request);

	/**
	 * @Description: 添加组信息
	 * @param group 组信息实体
	 * @return Result  返回的结果
	 * @author xcc
	 */
	public Result addGroup(Group group, HttpServletRequest request);

}