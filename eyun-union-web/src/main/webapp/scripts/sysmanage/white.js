/** =======================================================================
 *  白名单公共的脚本方法
 * */
var whiteComm = {
    /**
     * 获取选择框里面现象的字符串内容
     * @param dataList
     * @returns {string}
     */
    getSelOpts : function(dataList){
        var place = null;
        var optStr = '';
        if(dataList){
            for(var idx in dataList){
                place = dataList[idx];
                if(place){
                    optStr += '<option value="'+ place.placeCode +'" data-placeid="'+ place.id +'" >'+ place.placeName +'</option>';
                }
            }
        }
        return optStr;
    },
    loadPlaceData : function(callback){
        $.ajax({
            type : 'post',
            url : ctx+'/systemManage/place/getPlacesOpt.do',
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                var placeList = data.data;
                callback(placeList);
            }
        });
    }
};
/** =======================================================================
 *  白名单新增页面的脚本方法
 * */
var addPage = function(){
    var addEvent = function(){
            //场所选择添加事件
            $("#addForm select[name='placeName']").unbind("change").change(function(){
                clearPlaceTip();
            });
            $("#addForm input[name='mobile']").focus(function(){
                clearMobileTip();
            });
            /*$("#addForm input[name='eyunId']").focus(function(){
                clearEyunIdTip();
            });*/
            $("#addForm input[name='memberName']").focus(function(){
                clearMemberNameTip();
            });
            $("#addForm input[name='idCard']").focus(function(){
                clearIdCardTip();
            });
            $("#addForm input[name='devMac']").focus(function(){
                clearDevMacTip();
            });
        },
        /*clearEyunIdTip = function(){
            $('#eyunIdTip').html('');
        },*/
        clearPlaceTip = function(){//清空场所提示
            $("#placeTip").html("");
        },
        clearMobileTip = function(){//清空手机提示
            $("#mobileTip").html("");
        },
        clearMemberNameTip = function(){
            $('#memberNameTip').html('');
        },
        clearIdCardTip = function(){
            $('#idCardTip').html('');
        },
        clearDevMacTip = function(){
            $('#devMacTip').html('');
        },
        loadPlace = function(){
            whiteComm.loadPlaceData(fillPlace);
        },
        fillPlace = function(placeList){
            var $obj = $('#addForm select[name="placeName"]');
            var optStr = whiteComm.getSelOpts(placeList);
            $obj.html("");
            if(optStr){
                $obj.append(optStr);
            }
        },
        init = function(){
            loadPlace();
            addEvent();
        };
    init();
};

/** =======================================================================
 *  白名单列表页面的脚本方法
 * */
var whiteFunc = function(){
    var _addEvent = function(){
            $("#tabs").tabs();
            $("#functionDiv").append(addHtm);
            //给查找按钮添加事件
            $("#btnSearch").unbind("click").click(function(){
                _reloadTableData();
            });
            //给新增按钮添加事件
            $("#addFunc").unbind("click").click(function(){
                showAddBox();
            });
            //给文本框添加事件,手机号码不能以0开头，而且手机号码中不可以有非数字
            var numRule = /^[1-9]\d*/;
            $(".txt-num").keyup(function(e){
                var val = this.value;
                this.value = val.replace(/^0+/g,'');//不可以从0开始
            }).change(function(e){
                var val = this.value;
                this.value = val.replace(/\D+/g,'');
            });
            //给场所下拉框添加修改事件
            $("#placeOptId").change(function(e){
                _reloadTableData();
            });
        },
        showAddBox = function () {
            $('#addDialog').dialog({
                bgiframe: true,
                minHeight : 50,
                title : '新增白名单',
                width: 480,
                height: 380,
                dataheight: 'auto',
                scroll : false,
                drag: true,
                resize: false,
                zIndex : 9999,
                modal : true,
                closeOnEscape : true,
                buttons: {
                    "确定": function() {
                        saveWhiteList();
                    },
                    "取消": function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        },
        saveWhiteList = function(){
            var whiteListId = $("#addForm input[name='whiteListId']").val();
            var placeCode = '';//Object
            var selObj = $("#addForm").find("select[name='placeName']");
            if(selObj && selObj.size()>0){
                var optObj = selObj.find("option:selected");
                if(optObj){
                    placeCode =optObj.val();
                }else{
                    placeCode = '';
                }
            }
            //var eyunId =$("#addForm input[name='eyunId']").val().trim();
            var memberName=$("#addForm input[name='memberName']").val().trim();
            var idCard=$("#addForm input[name='idCard']").val().trim();
            var mobile=$("#addForm input[name='mobile']").val().trim();
            var devMac=$("#addForm input[name='devMac']").val().trim();
            if(!placeCode){//场所编号不存在，或者是测试场所，无编号
                $("#placeTip").html("场所编号不合法！");
                return false;
            }
            if(!memberName){//姓名没有输入
                $("#memberNameTip").html("姓名不可以为空！");
                return false;
            }
            if(idCard){//身份证号没有输入
                if(!custValid.isIDCard(idCard)){
                    $("#idCardTip").html("身份证号不合法！");
                    return false;
                }
            }else{
                $("#idCardTip").html("身份证号不可以为空！");
                return false;
            }
            if(mobile){//手机号没有输入
                if(!custValid.isPhoneNo(mobile)){
                    $("#mobileTip").html("手机号不合法！");
                    return false;
                }
            }else{
                $("#mobileTip").html("手机号不可以为空！");
                return false;
            }
            //保存白名单列表
            $.ajax({
                type : 'post',
                url : ctx+'/account/white/saveWhite.do',
                data : {'id':whiteListId,'placeCode':placeCode,'memberName':memberName,'idCard':idCard,'mobile':mobile ,'devMac':devMac},
                dataType : 'json',
                success : function(data){
                    if(data){
                        var status = data.status;
                        var msg = data.msg;
                        if('SUCC'==status){
                            alert('操作成功');
                            $('#addDialog').dialog( "close" );
                            _reloadTableData();
                        }else{
                            alert(msg);
                        }
                    }
                }
            });
        },
        _delWhiteList = function(id,devMac , userName , idCard ,mobile , placeCode){
            if(id && idCard  && placeCode){
                if(confirm("确定要删除该账号："+mobile)==true){
                    doDel(id,devMac , userName , idCard , mobile, placeCode);
                }
            }
        },
        /**
         * 更新白名单信息
         * @private
         */
        _uptWhiteList = function(id,devMac , userName , idCard ,mobile , placeCode){
            //初始化值
            $('#addForm input[name="whiteListId"]').val(id);
            $('#addForm select[name="placeName"] option[value="'+ placeCode +'"]').prop({"selected":true,"disabled":true});
            $('#addForm input[name="memberName"]').val(userName);
            $('#addForm input[name="idCard"]').val(idCard);
            $('#addForm input[name="mobile"]').val(mobile);
            $('#addForm input[name="devMac"]').val(devMac);

            $('#addDialog').dialog({
                bgiframe: true,
                minHeight : 50,
                title : '修改白名单',
                width: 480,
                height: 380,
                dataheight: 'auto',
                scroll : false,
                drag: true,
                resize: false,
                zIndex : 9999,
                modal : true,
                closeOnEscape : true,
                buttons: {
                    "确定": function() {
                        saveWhiteList();
                    },
                    "取消": function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        },
        doDel = function(id , devMac ,userName , idCard,mobile, placeCode){
            $.ajax({
                type : 'post',
                url : ctx+'/account/white/delWhite.do',
                data : {'entityId':id , 'devMac':devMac ,'memberName':userName,'idCard': idCard, 'mobile':mobile , 'placeCode':placeCode},
                dataType : 'json',
                success : function(data){
                    if(data){
                        var status = data.status;
                        var msg = data.msg;
                        if('SUCC'==status){
                            alert('删除成功');
                            _reloadTableData();
                        }else{
                            alert(msg);
                        }
                    }
                }
            });
        },
        loadPlace = function(){
            whiteComm.loadPlaceData(fillPlace);
        },
        fillPlace = function(placeList){
            if(placeList){
                var $obj = $('#placeOptId');
                var optStr = whiteComm.getSelOpts(placeList);
                $obj.find("option[value!='-1']").remove();
                if(optStr){
                    $obj.append(optStr);
                    //选中第一个场所
                    $obj.find("option[value='-1']").next().prop("selected",true);
                }

                _reloadTableData();//重新加载表格数据
            }
        },
        _getGridParam = function(){
            /**
             * jqgrid 似乎会保留每个参数的值，所以如果不希望过滤，最好置空
             * @type {{creator}}
             */
            var data = {"creator" : whiteConstants.creator};
            var placeCode =  $('#placeOptId option:selected').val();
            data['placeCode'] =placeCode=='-1'? '': placeCode;
            data['placeId'] =  $('#placeOptId option:selected').data('placeid');
            data['mobile'] = $('#mobile').val().trim();
            return data;
        },
        _loadTableData = function(){
            var data = _getGridParam();

            $("#gridTable").jqGrid({
                url : ctx+'/account/white/whiteList.do',
                mtype : "post",
                datatype : "json",
                postData : data,
                height : "auto",
                forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
                altRows : true,
                colNames : [ "手机号","姓名", "身份证号", "场所名称","设备Mac","placeCode", "创建时间", "操作" ],/*"易韵账号",*/
                colModel : [ {name : 'mobile',index : 'mobile',	sorttype : "string",align : 'center',	width : '200'}
                    /*,{name : 'surfUserId',index : 'surfUserId',align : 'center',sorttype : "string",width : '150'}*/
                    ,{name : 'userName',index : 'userName', align : 'center',	width : '280'}
                    ,{name : 'idCard',index : 'idCard',sorttype : "string",	align : 'center',	width : '300'	}
                    ,{name : 'placeName',index : 'placeName',	sorttype : "string",align : 'center',	width : '300'}
                    ,{name : 'devMac',index : 'devMac',	sorttype : "string",align : 'center',	width : '300'}
                    ,{name : 'placeCode',index : 'placeCode',sortable : false,hidden:true,	width : '0'}
                    ,{name : 'createTimeStr',index : 'create_time',	sorttype : "string",align : 'center',	width : '200'}
                    ,{ name : 'id' , index : 'id' ,sortable: false , align: 'center' , width: '150',formatter:CellFormatters.optFormatter } ],
                rowList : whiteConstants.rowList,
                jsonReader : {
                    rowNum : "rowNum",
                    root : "dataRows", // 数据行（默认为：rows）
                    page : "curPage", // 当前页
                    total : "totalPages", // 总页数
                    records : "totalRecords", // 总记录数
                    repeatitems : false// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
                },
                pager:"#gridPager"
            }).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false});
        },
        CellFormatters = {
            optFormatter : function(cellvalue, options, rowObject){
                var valStr = '';
                if(cellvalue){
                    valStr = preDelHtm + 	' onclick="javascript:whiteFunc.delWhiteList('+cellvalue+',\''+ rowObject['devMac'] +'\',\''+ rowObject['userName'] +'\',\''+ rowObject['idCard'] + '\',\''+ rowObject['mobile'] +'\',\''+ rowObject['placeCode'] +'\');" '+sufDelHtm
                    + preUpdHtm +  ' onclick="javascript:whiteFunc.uptWhiteList('+cellvalue+',\''+ rowObject['devMac'] +'\',\''+ rowObject['userName'] +'\',\''+ rowObject['idCard'] + '\',\''+ rowObject['mobile'] +'\',\''+ rowObject['placeCode'] +'\');" '+sufUpdHtm;
                }else{
                    valStr = '';
                }
                return valStr;

            }
        },

        _reloadTableData = function(){
            $("#gridTable").jqGrid('setGridParam', {
                page : 1,
                postData:_getGridParam()
            }).trigger('reloadGrid');
        },
        _init = function(){
            _addEvent(); //添加事件
            _loadTableData();//加载表格
            loadPlace(); //加载场所,选中第一个元素，然后重新加载表格
        };
    return {
        init :function(){
            _init();
        },
        delWhiteList : function(id,devMac , userName , idCard ,mobile , placeCode){
            _delWhiteList(id,devMac , userName , idCard ,mobile , placeCode);
        },
        uptWhiteList : function(id,devMac , userName , idCard ,mobile , placeCode){
            _uptWhiteList(id,devMac , userName , idCard ,mobile , placeCode);
        }
    }
}();