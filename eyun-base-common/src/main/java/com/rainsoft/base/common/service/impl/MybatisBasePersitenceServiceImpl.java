package com.rainsoft.base.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;

public abstract class MybatisBasePersitenceServiceImpl<T extends IdEntity, ID extends Serializable> implements IMybatisBasePersitenceService<T, ID> {

	protected abstract IMybatisPersitenceDao<T, ID> getBaseDao();

	@Override
	public Integer save(T item) {

		return getBaseDao().save(item);

	}

	@Override
	public Integer update(T item) {

		return getBaseDao().update(item);

	}

	@Override
	public T findById(ID id) {

		return getBaseDao().findById(id);

	}

	@Override
	public Integer deleteById(ID id) {

		return getBaseDao().deleteById(id);

	}

	@Override
	public Integer deleteByIds(ID[] ids) {

		return getBaseDao().deleteByIds(ids);

	}

	@Override
	public Integer findCountBy(T item) {

		return getBaseDao().findCountBy(item);

	}

	@Override
	public List<T> findListBy(T item, String sortColumn, String des) {

		return getBaseDao().findListBy(item, sortColumn, des);

	}
	
	@Override
	public List<T> findListBy(String keyId,T item, String sortColumn, String des) {

		return getBaseDao().findListBy(keyId, item, sortColumn, des);

	}

	@Override
	public List<T> findListBy(T item) {

		return getBaseDao().findListBy(item);

	}

	@Override
	public List<T> findListByMap(Map<String, Object> map) {

		return getBaseDao().findListByMap(map);

	}

	@Override
	public List<T> findList() {

		return getBaseDao().findList();

	}

	@Override
	public Integer save(String keyId, Object parameter) {

		return getBaseDao().save(keyId, parameter);

	}

	@Override
	public Integer update(String keyId, Object parameter) {

		return getBaseDao().update(keyId, parameter);

	}

	@Override
	public T findBy(String keyId, Object parameter) {

		return getBaseDao().findBy(keyId, parameter);

	}

	@Override
	public int deleteBy(String keyId, Object parameter) {

		return getBaseDao().deleteBy(keyId, parameter);

	}

	@Override
	public List<T> selectList(String keyId, Object parameter) {

		return getBaseDao().selectList(keyId, parameter);

	}

	@Override
	public List<Map<String, Object>> selectListMap(String keyId, Object parameter) {

		return getBaseDao().selectListMap(keyId, parameter);

	}

}