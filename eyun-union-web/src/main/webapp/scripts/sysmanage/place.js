/** =======================================================================
 *  场所列表页面的脚本方法
 * */
var placeFunc = function(){
    var _addEvent = function(){
            $('#btnSearch').click(function(){
                _reloadTableData();
            });
            $('#btnReset').click(function(){
                $('#searchForm input[type="text"]').val('');
                _reloadTableData();
            });
        },
        _getGridParam = function(){
            /**
             * jqgrid 似乎会保留每个参数的值，所以如果不希望过滤，最好置空
             * @type {{creator}}
             */
            var data = {};
            data['placeName'] = $('#placeName').val().trim();
            data['placeCode'] =  $('#placeCode').val().trim();
            data['serialNum'] = $('#serialNum').val().trim();
            data['memberName'] = $('#memberName').val().trim();
            return data;
        },
        _loadTableData = function(){
            var data = _getGridParam();

            $("#gridTable").jqGrid({
                url : ctx+'/place/placeList.do',
                mtype : "post",
                datatype : "json",
                postData : data,
                height : "auto",
                forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
                altRows : true,
                colNames : [ 'id','场所名称', '场所编号','设备序列号','易盟号' , '地址', '所在地区','付费状态','操作'],
                colModel : [
                    {name: 'id', index: 'id', hidden: true},
                    {name: 'placeName', index: 'placeName', align: 'center', width: '20'},
                    {name: 'placeCode', index: 'placeCode', align: 'center', width: '10'},
                    {name: 'serialNum', index: 'serialNum', align: 'center', width: '25'},
                    {name: 'memberName', index: 'memberName', align: 'center', width: '5'},
                    {name: 'addr', index: 'addr', align: 'center', width: '10'},
                    {name: 'areaName', index: 'areaName', align: 'center', width: '20'},
                    {name: 'smsFeeOwnerFlagStr', index: 'smsFeeOwnerFlag', sorttype: "string", align: 'center', width: '5'},
                    {name: 'placeDeviceId', index: 'placeDeviceId', sort: false, align: 'center', width: '10',formatter:CellFormatters.optFormatter },

                ],
                rowList : pageConstants.rowList,
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
                if(cellvalue){
                    return preUpdHtm + 	' onclick="javascript:placeFunc.udpPlace('+cellvalue+',\''+ rowObject['placeName'] +'\',\''+ rowObject['placeCode'] +'\',\''+ rowObject['serialNum'] + '\',\''+ rowObject['memberName'] +'\',\''+ rowObject['smsFeeOwnerFlag'] +'\');" '+sufUpdHtm;
                }else{
                    return '';
                }
            }
        },
        _udpPlace = function(placeDeviceId ,placeName , placeCode , serialNum ,memberName , smsFeeOwnerFlag){
            if(placeDeviceId && smsFeeOwnerFlag){
                $('#sh_placeName').text(placeName);
                $('#sh_placeCode').text(placeCode);
                $('#sh_serialNum').text(serialNum);
                $('#sh_memberName').text(memberName);
                var optObj = $('#editForm select[name="smsFeeOwnerFlag"] option[value="'+ smsFeeOwnerFlag +'"]');
                if(optObj){
                    $('#placeDeviceId').val(placeDeviceId);
                    optObj.prop("selected",true);
                }
                showEditDialog();
            }
        },
        showEditDialog = function(){
            $('#addDialog').dialog({
                bgiframe: true,
                minHeight : 50,
                title : '修改场所短信计费方式',
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
                        _doUpdate();
                    },
                    "取消": function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
        },
        _doUpdate = function(){
            var placeDeviceId = $('#placeDeviceId').val();
            var smsFeeOwnerFlag = $('#editForm select[name="smsFeeOwnerFlag"] option:selected').val();
            //保存白名单列表
            $.ajax({
                type : 'post',
                url : ctx+'/place/updateSmsRechargeFlag.do',
                data : {'placeDeviceId':placeDeviceId,'smsFeeOwnerFlag':smsFeeOwnerFlag},
                dataType : 'json',
                success : function(data){
                    if(data){
                        var status = data.status;
                        var msg = data.msg;
                        if('SUCC'==status){
                            alert('保存成功');
                            $('#addDialog').dialog( "close" );
                            _reloadTableData(); //重新加载数据
                        }else{
                            alert(msg);
                        }
                    }
                }
            });
        },
        _reloadTableData = function(){
            $("#gridTable").jqGrid('setGridParam', {
                page : 1,
                postData:_getGridParam()
            }).trigger('reloadGrid');
        },
        _init = function(){
            _addEvent(); //添加事件
            _loadTableData();
        };
    return {
        init :function(){
            _init();
        },
        udpPlace : function(placeDeviceId ,placeName , placeCode , serialNum ,memberName , smsFeeOwnerFlag){
            _udpPlace(placeDeviceId ,placeName , placeCode , serialNum ,memberName , smsFeeOwnerFlag);
        }
    }
}();