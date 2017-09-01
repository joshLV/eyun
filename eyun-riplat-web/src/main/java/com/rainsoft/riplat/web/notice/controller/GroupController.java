package com.rainsoft.riplat.web.notice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.model.Group;
import com.rainsoft.riplat.web.notice.service.IGroupService;

@Controller
@RequestMapping("/group")
public class GroupController extends SpringBaseController<Group, String> {

    @Resource
    private IGroupService groupServiceImpl;
    
    @Override
    protected IMybatisBasePersitenceService<Group, String> getBaseService() {
        return groupServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/group/";
        
        
        
    }
    
    @RequestMapping("/toList")
    public String toList(Model model) {
        String item = SpringMvcUtil.getParameter("item");
        model.addAttribute("item", item);
        return super.toList(model);
    }
    
    
    /**
     * 根据用户所管辖区域加载组
     * @param params
     * @author xcc
     */
    @RequestMapping(value = "/loadGroupData", method = RequestMethod.POST)
    public void loadGroupData(@ModelAttribute Group group) {
    	super.loadGrid(group, "loadGroupData");
    }
    

    @RequestMapping("/toGroupList")
    public String toGroupList(Model model) {
        String item = SpringMvcUtil.getParameter("item");
        model.addAttribute("item", item);
        model.addAttribute("rowList", rowList);
        return getPrefix()+"/groupList";
    }
    
    @RequestMapping(value="/saveOrUpdateGroup",method = RequestMethod.POST)
    public void saveOrUpdate(@ModelAttribute Group group) {
    	try {
			groupServiceImpl.saveOrUpdate(group);
			super.responseAjaxData(new Result(Constants.RETURN_SUCCESS,"保存成功", null));
		} catch (Exception e) {
			e.printStackTrace();
			super.responseAjaxData(new Result(Constants.RETURN_SUCCESS,"保存失败", null));
		}
    }
    
    @RequestMapping("/del")
    public void del(String id) {
        groupServiceImpl.deleteByIdWithMember(id);
        super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "删除成功", null));

    }
    
    /**
	 * @Description: 保存或修改组
	 * @param group 组实体
	 * @return void  
	 * @author xcc
	 */
	@Log(methodDesc = "新增组")
	@RequestMapping("/saveGroup")
	public void saveGroup(@ModelAttribute Group group) {
		Result result = null;
		if(group.getId() != null && group.getId() != 0) {
			result = groupServiceImpl.addGroup(group, getRequest());
		}
		this.responseAjaxData(result);
	}
	
	 /**
		 * @Description: 保存或修改组
		 * @param group 组实体
		 * @return void  
		 * @author xcc
		 */
		@Log(methodDesc = "修改组")
		@RequestMapping("/updateGroup")
		public void updateGroup(@ModelAttribute Group group) {
			Result result = null;
			if(group.getId() != null && group.getId() != 0) {
				result = groupServiceImpl.updateGroup(group, getRequest());
			} 
			this.responseAjaxData(result);
		}
    
    @RequestMapping("/getGroupById")
    public void getGroupById(String id){
    	 super.responseAjaxData(new Result(Constants.RETURN_SUCCESS, "成功", groupServiceImpl.getGroupById(id)));
    }
}
