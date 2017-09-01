package com.rainsoft.riplat.web.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.riplat.web.notice.dao.IGroupDao;
import com.rainsoft.riplat.web.notice.dao.IMemberGroupDao;
import com.rainsoft.riplat.web.notice.model.Group;
import com.rainsoft.riplat.web.notice.model.MemberGroup;
import com.rainsoft.riplat.web.notice.service.IGroupService;

@Service
public class GroupServiceImpl extends MybatisBasePersitenceServiceImpl<Group, String> implements IGroupService {

	@Resource
	private IGroupDao groupDaoImpl;

	@Resource
	private IMemberGroupDao memberGroupDaoImpl;

	@Override
	protected IMybatisPersitenceDao<Group, String> getBaseDao() {
		return groupDaoImpl;
	}

	@Override
	public List<Group> findListBy(Group group, String sortColumn, String des) {
		if (group != null) {
			int item = group.getItem() == null ? -1 : group.getItem();
			group.setPlatUserId(SpringMvcUtil.getUserId().toString());
			if (item == 1) {
				// 场所组
				group.setType(1);
			} else if (item == 0) {
				// 用户组
				group.setType(0);
			} else if (item == -1) {
				// 搜索全部
			}
		}
		return super.findListBy(group, sortColumn, des);
	}

	@Override
	public List<BaseArea> getBaseAreaByUserId(String userId, String platformId) {
		return groupDaoImpl.getBaseAreaByUserId(userId, platformId);
	}

	@Override
	public Integer deleteByIdWithMember(String id) {

		super.deleteById(id);// 删除Group表记录
		return memberGroupDaoImpl.deleteByGroupId(id);// 删除Group中成员的表记录

	};

	@Override
	public void saveOrUpdate(Group group) {
	    group.setPlatUserId(SpringMvcUtil.getUserId().toString());
		if (null != group.getId()) {// update
			super.update(group);
			memberGroupDaoImpl.deleteByGroupId(group.getId().toString());// 删除组与成员的关联关系
			// super.deleteById(group.getId().toString());// 删除组

		} else {// insert
			// 存放单个组的表信息
			super.save(group);
		}
		// 存放组与成员之间的关系表信息
		String[] idArr = StringUtil.isEmpty(group.getSelectedIds()) ? null : group.getSelectedIds().split(",");
		if (idArr != null && idArr.length > 0) {
			if (group.getId() == null) {
				group = groupDaoImpl.getGroupWithId(group);// 获取GroupID
			} else {
				group = groupDaoImpl.findById(group.getId().toString());
			}
			for (String id : idArr) {
				MemberGroup memberGroup = new MemberGroup();
				memberGroup.setGroupId(group.getId().toString());
				memberGroup.setMemberId(id);
				memberGroup.setMemberType(group.getType());
				memberGroupDaoImpl.save(memberGroup);
			}
		}
	}

	@Override
	public Group loadGroupData(Group group) {
		return groupDaoImpl.loadGroupData(group);
	}

	/*
	 * @Override public Integer updateGroup(Group group) {
	 * 
	 * return groupDaoImpl.update(group); }
	 */
	/**
	 * 根据id获取组的信息
	 */
	public Group getGroupById(String id) {

		return groupDaoImpl.findById(id);
	}

	/**
	 * 根据id删除组信息
	 */
	public Integer deleteById(String id) {

		return groupDaoImpl.deleteById(id);
	}

	/**
	 * @Description: 修改组信息
	 * @param group
	 *            组信息实体
	 * @return Result 返回的结果
	 * @param request请求作用域
	 * @author xcc
	 */
	public Result updateGroup(Group group, HttpServletRequest request) {
		RequestContext requestContext = new RequestContext(request);
		// 处理返回的结果
		Result result = new Result();
		// 执行修改
		int count = groupDaoImpl.update(group);
		// 根据count返回数据库影响的行数来确定是否修改成功
		if (group.getId() != null && group.getId() != 0 && count > 0) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("修改组信息成功！");
			result.setMessage(requestContext.getMessage("info.groupUpdate"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("修改组信息失败！");
			result.setMessage(requestContext.getMessage("error.groupUpdate"));
		}
		return result;
	}

	/**
	 * @Description: 添加组信息
	 * @param group
	 *            组信息实体
	 * @return Result 返回的结果
	 * @param request请求作用域
	 * @author xcc
	 */
	public Result addGroup(Group group, HttpServletRequest request) {
		RequestContext requestContext = new RequestContext(request);
		// 处理返回的结果
		Result result = new Result();
		// 执行保存
		int count = groupDaoImpl.save(group);
		// 根据count返回数据库影响的行数来确定是否新增成功
		if (group.getId() != null && count > 0) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("保存组信息成功！");
			result.setMessage(requestContext.getMessage("info.groupSave"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("保存组信息失败！");
			result.setMessage(requestContext.getMessage("error.groupSave"));
		}
		return result;
	}

}