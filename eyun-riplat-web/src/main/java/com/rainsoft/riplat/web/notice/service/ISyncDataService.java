package com.rainsoft.riplat.web.notice.service;

import java.util.List;

import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.model.AppuserServiceCode;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.model.PlatformUser;


public interface ISyncDataService {
    
	/**
	 * 从易盟同步场所和易达号绑定关系
	 * @param jsonArr
	 * @return
	 */
	Result syncAppuserServiceCode(List<AppuserServiceCode> jsonArr);

	/**
	 * 从Isec同步易达账号
	 * @param edaObj
	 * @return
	 */
	Result syncEdaAcccount(EdaAppMembers edaObj);

	Result syncPlatformUser(PlatformUser platformUser) throws Exception;
}