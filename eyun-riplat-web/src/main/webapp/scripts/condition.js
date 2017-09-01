//搜索
function loadPostData(){
	var params = {
		searchValue:[]
	};
	$("[search-type='search']").each(function(index, value) {
		var $this = $(value);
		var thisValue;
		if(typeof($this.attr('search-valAttrName'))!="undefined") {
			thisValue = $.trim($this.attr($this.attr('search-valAttrName')));
		}else{
			thisValue = $.trim($this.val());
		}
		if(thisValue != ''){
			params.searchValue.push({property:$this.attr('search-field'),value:thisValue,operation:$this.attr('search-operation'),fieldType:$this.attr('search-fieldType')});
		}
	});
	console.log(JSON.stringify(params.searchValue));
	$("#gridTable").jqGrid('setGridParam', {
        page : 1,
        postData:{"searchValue":JSON.stringify(params.searchValue)}
    }).trigger('reloadGrid');
}

//搜索
function loadPostDataWithSelector(inputselector,gridSelector){
	var params = {
		searchValue:[]
	};
	$("[search-type=" + inputselector + "]").each(function(index, value) {
		var $this = $(value);
		var thisValue;
		if(typeof($this.attr('search-valAttrName'))!="undefined") {
			thisValue = $.trim($this.attr($this.attr('search-valAttrName')));
		}else{
			thisValue = $.trim($this.val());
		}
		if(thisValue != ''){
			params.searchValue.push({property:$this.attr('search-field'),value:thisValue,operation:$this.attr('search-operation'),fieldType:$this.attr('search-fieldType')});
		}
	});
	console.log(JSON.stringify(params.searchValue));
	$("#" + gridSelector).jqGrid('setGridParam', {
        page : 1,
        postData:{"searchValue":JSON.stringify(params.searchValue)}
    }).trigger('reloadGrid');
}