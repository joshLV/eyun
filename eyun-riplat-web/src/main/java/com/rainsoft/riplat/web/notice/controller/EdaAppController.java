package com.rainsoft.riplat.web.notice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.service.IEdaAppService;

@Controller
@RequestMapping("/edaApp")
public class EdaAppController extends SpringBaseController<EdaAppMembers, String> {

    @Resource
    private IEdaAppService edaAppServiceImpl;
    
    @Override
    protected IMybatisBasePersitenceService<EdaAppMembers, String> getBaseService() {
        return edaAppServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/notice/";
    }
    
    @RequestMapping("/toList")
    public String toList(Model model) {
        String item = SpringMvcUtil.getParameter("item");
        model.addAttribute("item", item);
        return super.toList(model);
    }
    
        
    @Override
    protected void loadGrid(EdaAppMembers item) {
        String groupId = SpringMvcUtil.getParameter("groupId");
        if(StringUtil.isNotEmpty(groupId)&&Integer.valueOf(groupId)<0){
            item.setGroupId(null);
            item.setIsSelected(null);
        }
        super.loadGrid(item);
    }
}
