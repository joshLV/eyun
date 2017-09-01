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
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.dao.IPlaceDao;
import com.rainsoft.riplat.web.notice.model.Place;
import com.rainsoft.riplat.web.notice.service.IPlaceService;

@Service
public class PlaceServiceImpl extends MybatisBasePersitenceServiceImpl<Place, String> implements IPlaceService {

	@Resource
	private IPlaceDao placeDaoImpl;

	@Override
	protected IMybatisPersitenceDao<Place, String> getBaseDao() {
		return placeDaoImpl;
	}

	@Override
	public List<Place> findListBy(Place place, String sortColumn, String des) {
		place.setUserId(SpringMvcUtil.getUserId().toString());
		return super.findListBy(place, sortColumn, des);
	}

	/**
	 * 功能描述：根据place实体修改场所的信息
	 * 
	 * @author sh_j_xuchunchun
	 * @param place
	 *            场所信息
	 * @return Result 返回处理的结果
	 */
	public Result updatePlace(Place place,HttpServletRequest request) {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		int count = placeDaoImpl.update(place);
		if (place.getId() != null && place.getId() != 0 && count > 0) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.updatePlace"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("erro.updatePlace"));
		}
		return result;
	}

}