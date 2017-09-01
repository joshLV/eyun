package com.rainsoft.riplat.web.notice.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.model.Place;

public interface IPlaceService extends IMybatisBasePersitenceService<Place, String> {
	
	
	/**
	 * 功能描述：根据place实体修改场所的信息
	 * @author sh_j_xuchunchun
	 * @param place 场所信息
	 * @return Result 返回处理的结果
	 */
	public Result updatePlace(Place place, HttpServletRequest request);

}
