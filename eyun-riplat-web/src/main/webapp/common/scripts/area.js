/**
 * 系统区域js
 * 用法：在页面需要加载系统区域的地方调用方法initArea即可
 * @author baox
 * @date 2016-01-22 15:01:25
 */

//获取主机地址之后的目录，如： eyun-union/system/areaList.jsp
var pathName=window.document.location.pathname;
//获取带"/"的项目名，如：/eyun-union
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

/**
 * 初始化区域
 * @param areaArray 区域初始化数组，其中key分别为：appendSelectors，selectors，styles，propertys;
 * @param appendSelectors 需追加区域选择器ID（多个或一个）多个以“,”分割，如："selProvinceDiv,selCityDiv,selCountyDiv";
 * @param selectors 区域下拉框选择器ID（必须多个），以“,”分割，如："selProvince, selCity, selCounty"
 * @param styles 区域下拉框选择器式样，可为空，多个以“,”分割。如："width:80px,width:80px,width:80px"
 * @param propertys 区域下拉框选择器属性，可为空，多个以“,”分割。如：",,name=areaId"
 * @param defaultSelects 选中区域ID,可传多个，也可传某一级ID，如："100000,101000,101010"
 * 调用示例： areaArray["appendSelectors"] = "areaTd";
           areaArray["selectors"] = "loadProvince,loadCity,loadCounty";
           areaArray["styles"] = "width:80px,width:80px,width:80px";
           areaArray["propertys"] = ",,name=areaId";
           areaArray["defaultSelects"] = "100000,101000,101010";
           initArea(areaArray);
 * @author baox
 * @date 2016-01-22 15:01:25
 */
function initArea(areaArray) {
	var appendFlg = false;
	var appendSelectors = areaArray["appendSelectors"];
	var selectors = ["", "", ""];
	var styles = ["", "", ""];
	var propertys = ["", "", ""];
	var defaultSelects = "";
	
	var labelVal = "";
	var onchangeHtm = "";
	var html = "";
	try {
		for (var key in areaArray) {
			switch (key) {
			case "appendSelectors" : 
				if(areaArray[key].indexOf(",") != -1) {
					appendFlg = true;
					appendSelectors = appendSelectors.split(",");
					break;
				}
			case "selectors" :
				selectors = areaArray[key].split(",");
				break;
			case "styles" :
				styles = areaArray[key].split(",");
				break;
			case "propertys" :
				propertys = areaArray[key].split(",");
				break;
			case "defaultSelects" :
				defaultSelects = areaArray[key].split(",");
				break;
			}
		}
		
		
		$.each(selectors, function(i, n){
			//下拉框存在则跳过
			if ($("#" + n).hasClass('exist')) {return;}
			if (i == 0) {labelVal = "省";onchangeHtm = "onchange=\"loadCityList('" + selectors[i + 1] + "', this.value);\"";}
			else if (i == 1) {labelVal = "市";onchangeHtm = "onchange=\"loadCountyList('" + selectors[i + 1] + "', this.value);\"";}
			else {labelVal = "区（县）";onchangeHtm = "";}
			if (appendFlg) {
				$("#" + appendSelectors[i]).append("<select id='" + n + "' class=\"exist\" style='" + styles[i] + "'" + propertys[i] + " " + onchangeHtm + ">" + 
					"<option value='-1'>请选择</option></select><label>" + labelVal + "</label>");
				return;
			} else {
				html = "<select id='" + n + "' class=\"exist\" style='" + styles[i] + "'" + propertys[i] + " " + onchangeHtm + ">" +
					"<option value='-1'>请选择</option></select><label>" + labelVal + "</label>";
			}
			$("#" + appendSelectors).append(html);
		});
		//加载省列表
		loadProvinceList(selectors[0]);
		//若有默认区域怎将传入的area标号排序从小到大
		if(defaultSelects != "") {
			for(var i = 1; i < defaultSelects.length; i++) {
				var tem = "";
				if(isNOtEmpty(defaultSelects[i])) {
					if(isNOtEmpty(defaultSelects[0])) {
						if(Number(defaultSelects[0]) < Number(defaultSelects[i])) {
							tem = defaultSelects[0];
							defaultSelects[0] = defaultSelects[i];
							defaultSelects[i] = tem;
						}
					} else {
						tem = defaultSelects[0];
						defaultSelects[0] = defaultSelects[i];
						defaultSelects[i] = tem;
					}
				}else {
					continue;
				}
			}

			//给三级省市县下拉框设置默认值
			var areaCode = defaultSelects[0];
			if(isNOtEmpty(areaCode)) {
				if(areaCode.substring(2,4) == "00") {
					$("#" + selectors[0] + " option[value='" + areaCode + "']").prop('selected', true);
					loadCityList("loadCity", areaCode.substring(0,2) + "00");
				} else if(areaCode.substring(4,6) == "00") {
					loadCityList("loadCity", areaCode.substring(0,2) + "0000");
					$("#" + selectors[0] + " option[value='" + areaCode.substring(0,2) + "0000']").prop('selected', true);
					$("#" + selectors[1] + " option[value='" + areaCode + "']").prop('selected', true);
					loadCountyList("loadCounty", areaCode.substring(0,4) + "00")
				} else {
					loadCityList("loadCity", areaCode.substring(0,2) + "0000");
					loadCountyList("loadCounty", areaCode.substring(0,4) + "00")
					$("#" + selectors[0] + " option[value='" + areaCode.substring(0,2) + "0000']").prop('selected', true);
					$("#" + selectors[1] + " option[value='" + areaCode.substring(0,4) + "00']").prop('selected', true);
					$("#" + selectors[2] + " option[value='" + areaCode + "']").prop('selected', true);
				}
			}
		}
	} catch (err) {
		showMsg("初始化区域失败，请核对参数值是否正确！" + err);
	}
}

/**
 * 加载省下拉列表
 * @author baox
 * @date 2016-01-22 15:01:25
 */
function loadProvinceList(selector) {
	$.ajax({
        url : projectName + "/pubArea/toLoadProvince.do",  
        type : 'POST', 
        dataType : 'json',
        async : false,
        success : function(data) {  
            if (data && data.status == "SUCC") {
            	var areaHtm = "<option value='-1'>请选择</option>";
            	if (data.data && data.data != "") {
                    $.each($.parseJSON(data.data), function(i, n) {
                    	areaHtm += "<option value='" + n.id + "'>" + n.name + "</option>";
                    });
            	}
            	$("#" + selector).html(areaHtm);
            } else {
            	showMsg("加载省失败！");
            }
        },
	});  
};

/**
 * 加载地级市
 * @author baox
 * @date 2016-01-22 15:01:25
 */
function loadCityList(selector, province){
	if(province == null || province == ""){
		return false;
	}
	if(province == -1){
		$('#loadCity').html("<option value='-1'>请选择</option>");
		$('#loadCounty').html("<option value='-1'>请选择</option>");
		return false;
	}
	$.ajax({  
        url : projectName + "/pubArea/toLoadCity.do",  
        type : 'POST', 
        dataType : 'json',
        async : false,
        data : {"parentCode" : province}, 
        success : function(data) {  
            if (data.status == "SUCC") {
            	$('#loadCounty').html("<option value='-1'>请选择</option>");
            	var areaHtm = "<option value='-1'>请选择</option>";
                if (data.data && data.data != "") {
                    $.each($.parseJSON(data.data), function(i, n) {
                        areaHtm += "<option value='" + n.id + "'>" + n.name + "</option>";
                    });
                }
            	$("#" + selector).html(areaHtm);
            } else {  
            	showMsg("加载市失败！");
            }  
        },  
	}); 
}
/**
 * 加载县级市
 * @author baox
 * @date 2016-01-22 15:01:25
 */
function loadCountyList(selector, city){
	if(city == null || city == ""){
		return false;
	}
	if(city == -1){
		$('#loadCounty').html("<option value='-1'>请选择</option>");
		return false;
	}
	$.ajax({  
        url : projectName + "/pubArea/toLoadTown.do",  
        type : 'POST', 
        dataType : 'json',
        async : false,
        data : {"parentCode" : city}, 
        success : function(data) {  
            if (data.status == "SUCC") {
//            	$('#loadCounty').html("<option value='-1'>请选择</option>");
            	var areaHtm = "<option value='-1'>请选择</option>";
                if (data.data && data.data != "") {
                    $.each($.parseJSON(data.data), function(i, n) {
                        areaHtm += "<option value='" + n.id + "'>" + n.name + "</option>";
                    });
                }
            	$("#" + selector).html(areaHtm);
            } else {  
            	showMsg("加载区失败！");
            }  
        },  
    }); 
}

function isNOtEmpty(str) {
	if(str != null && str != "" && str != undefined) {
		return true;
	} else {
		return false;
	}
}