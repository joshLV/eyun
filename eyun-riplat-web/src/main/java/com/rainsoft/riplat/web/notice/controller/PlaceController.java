package com.rainsoft.riplat.web.notice.controller;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.model.Place;
import com.rainsoft.riplat.web.notice.service.IPlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/place")
public class PlaceController extends SpringBaseController<Place, String> {

    @Resource
    private IPlaceService placeServiceImpl;
    
    
    
    
    @Override
    protected IMybatisBasePersitenceService<Place, String> getBaseService() {
        return placeServiceImpl;
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
    
    
    @Override
    protected void loadGrid(Place item) {
        String groupId = SpringMvcUtil.getParameter("groupId");
        String userName = "";
        AuthenToken authenToken = SpringMvcUtil.getCurrentUser();
        if (null != authenToken) {
            userName = authenToken.getLoginName();
        }
        if(StringUtil.isNotEmpty(groupId)&&Integer.valueOf(groupId)<0){//当查询所有场所，所有用户时查询所有
            item.setGroupId(null);
            item.setIsSelected(null);
        }
        if(!CommonUtil.isEmpty(userName)){
            item.setUserName(userName);
        }
        super.loadGrid(item);
    }

    /**
	 * 方法说明：修改场所信息
	 * @param group 组实体
	 * @return void  
	 * @author sh_j_xuchunchun
	 */
	@Log(methodDesc = "修改组")
	@RequestMapping("/updatePlace")
	public void updatePlace(@ModelAttribute Place place) {
		Result result = null;
		if(place.getId() != null && place.getId() != 0) {
			result = placeServiceImpl.updatePlace(place, getRequest());
		} 
		this.responseAjaxData(result);
	}
}
