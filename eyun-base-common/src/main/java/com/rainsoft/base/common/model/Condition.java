package com.rainsoft.base.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Condition implements Serializable{	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6409839333665445616L;

	private String _dc;

    private Integer page = 1;

    // START INDEX
    private Integer start = 0;

    // PAGESIZE
    private Integer limit = 10;

    protected Map<String, Object> extras;

    private List<ConditionFilter> filters = new ArrayList<ConditionFilter>();

    private List<ConditionSort> sorts = new ArrayList<ConditionSort>();

    public Condition() {
        super();
    }

    public List<ConditionFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ConditionFilter> filters) {
        this.filters = filters;
    }

    public List<ConditionSort> getSorts() {
        return sorts;
    }

    public void setSorts(List<ConditionSort> sorts) {
        this.sorts = sorts;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
        this.start = (this.page - 1) * this.limit;
    }

    public String get_dc() {
        return _dc;
    }

    public void set_dc(String _dc) {
        this._dc = _dc;
    }

    public void addFilter(String property, Object value, Operation operation) {
        ConditionFilter filter = new ConditionFilter();
        filter.setProperty(property);
        filter.setValue(value);
        filter.setOperation(operation);
        this.addFilter(filter);
    }

    public void addFilter(String property, Object value) {
        this.addFilter(property, value, Operation.EQ);
    }
    
    public void addFilter(ConditionFilter filter){
    	this.filters.add(filter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getFilterValuefor(String property) {
        for (ConditionFilter filter : filters) {
            if (filter.getProperty().equals(property)) {
                return (T) filter.getValue();
            }
        }
        return null;
    }

    public void removeFilter(String... properties) {
        Iterator<ConditionFilter> iterator = filters.iterator();
        while (iterator.hasNext()) {
            ConditionFilter next = iterator.next();
            for (String property : properties) {
                if (next.getProperty().equalsIgnoreCase(property)) {
                    iterator.remove();
                }
            }
        }
    }

    public Map<String, Object> getExtras() {
        if (extras == null) {
            this.extras = new HashMap<String, Object>();
        }
        return this.extras;
    }

    public void doExtras(String... properties) {
        if (properties != null && properties.length != 0) {
            for (String property : properties) {
                if (!getExtras().containsKey(property)) {
                    Object value = this.getFilterValuefor(property);
                    if (null != value) {
                        getExtras().put(property, value);
                        removeFilter(property);
                    }
                }
            }
        }
    }

    public void addExtra(String property, Object value) {
        this.getExtras().put(property, value);
    }

    public void addSort(String property, String direction) {
        ConditionSort sort = new ConditionSort();
        sort.setProperty(property);
        sort.setDirection(direction);
        this.sorts.add(sort);
    }
    

    /**
     * 根据前台传的json转换成ConFilterList对象
     * @param json
     */
    @SuppressWarnings("unchecked")
	public void setFiltersByJson(String json){
		this.setFilters((List<ConditionFilter>) JSON.parseObject(json, ConditionFilter.class));
	}

}
