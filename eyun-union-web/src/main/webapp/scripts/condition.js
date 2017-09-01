//搜索
	function loadPostData(){
		 var params = {
				searchValue:[]
			};		
		$("input[search-type='search']").each(function (){
			var $this = $(this);
			var thisValue;
			if(typeof($this.attr('search-valAttrName'))!="undefined") {
				thisValue = $.trim($this.attr($this.attr('search-valAttrName')));
			}else{
				thisValue = $.trim($this.val());
			}
		if(thisValue != ''){
			params.searchValue.push({property:$this.attr('search-field'),value:thisValue,operation:$this.attr('search-operation'),fieldType:$this.attr('search-fieldType')});
		}
		console.log(JSON.stringify(params.searchValue));//调试代码，发布删除
		});
		$("#gridTable").jqGrid('setGridParam', {
	        page : 1,
	        postData:{"searchValue":JSON.stringify(params.searchValue)}
	        }).trigger('reloadGrid');
	}