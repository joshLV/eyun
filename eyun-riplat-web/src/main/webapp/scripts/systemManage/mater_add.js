/**保存素材**/
function save_click(ctx) {
    var materialName = $("#cmaterialName").val();
    if (!$.trim(materialName)) {
        showDialogErrMsg("cmaterialName", "请输入素材名称","dialog-form");
        //$(".material_error").html("请输入素材名称");
        return;
    }
    hideErrMsg("cmaterialName");
    //素材类型
    var type = $("#cmaterialType").val();
    if (!type) {
        //$(".uploadMess").addClass("clr-error").html("请选择素材类型！");
        showDialogErrMsg("cmaterialType", "请选择素材类型","dialog-form");
        return;
    }
    hideErrMsg("cmaterialType");
    //图片上传
    var fileField = $("#fileField").val();
    /**如果文件存在才上传**/
    if(fileField) {
        /**开始上传图片**/
        $.ajaxFileUpload({
            url: ctx + '/upload/uploadPro.do?type=' + type,
            secureuri: false,
            fileElementId: 'fileField',
            dataType: 'json',
            error: function (data, status)//服务器响应失败处理函数
            {
                console.log("服务器响应失败了" + status);
                $(".uploadMess").addClass("clr-error").html("上传失败！");
                uploadFile();
            },
            success: function (data) {
                if (data.result == "0") {
                    console.log(data.url);
                    $("#cmaterialUrl").val(data.url);
                    $(".uploadMess").removeClass("clr-error").html(data.msg);
                    saveOrUpdMaterial(ctx);
                } else {
                    $("#cmaterialUrl").val("");
                    $(".uploadMess").addClass("clr-error").html(data.msg);

                }
                uploadFile();
            }
        });
    }else{
        saveOrUpdMaterial(ctx);
    }
}
/**编辑或者保存素材**/
function saveOrUpdMaterial(ctx) {
    var materialId = $("#materialId").val();
    var materialUrl = $("#cmaterialUrl").val();
    /**判断是否有上传图片的url**/
    if(materialUrl == null || materialUrl == ""){
        //$(".uploadMess").addClass("clr-error").html("请选择上传的素材");
        showDialogErrMsg("", "请选择上传的素材","dialog-form");
        return;
    }
    hideErrMsg("cmaterialUrl");
    var keyId = 'save';
    if ($.trim(materialId)) {
        keyId = 'update';
    }
    $.ajax({
        async: false,
        cache: true,
        type: "POST",
        url: ctx + '/systemManage/material/saveOrUpdMaterial.do?keyId=' + keyId,
        data: $('#signupForm').serialize(),
        dataType: 'json',
        success: function (data) {
            var result = data.message;
            if (result >= 0) {
                $(".uploadMess").html("保存成功");
                refresh();
                var hehe = setTimeout(function () {
                    $("#dialog-form").dialog('close');
                }, 1000);
            } else if (result == -1) {
                $(".uploadMess").html("保存失败");
            } else if (result == -2) {
                $(".uploadMess").html("保存失败,素材名称重复");
            }
        }
    });
}

//模板类型事件
function material_change() {
    var type = $("#cmaterialType").val();
    $(".uploadMess").html("");
    if (type == '1') {
        $(".plcsize").html("1920*900");
    } else if (type == '2') {
        $(".plcsize").html("92*75");
    } else if (type == '3') {
        $(".plcsize").html("628*407");
    } else if (type == '4') {
        $(".plcsize").html("230*130");
    } else if (type == '5') {
        $(".plcsize").html("640*200");
    } else {
        $(".plcsize").html("");
    }
}

/**
 * 初始化预览插件
 */
function uploadFile() {
    new uploadPreview({UpBtn: "fileField", DivShow: "imgdiv", ImgShow: "imgView"});
}

/**清除dialog内容**/
function clearDiv() {
    $("#clearData").click();
    $(".uploadMess").html("");
    $("#imgView").attr('src', "");
    $(".plcsize").html("");
    $("#cmaterialUrl").val('');
    $("#material_error").html('');
    $("#materialId").val('');
}

function updateMaterialStatus(ctx) {
    var id= $("#materialId").val();
    var keyId = 'updateStatus';
    $.ajax({
        //async: false,
        cache: true,
        type: "POST",
        url: ctx + '/systemManage/material/saveOrUpdMaterial.do?keyId=' + keyId,
        data:{id:id,status:'待审核'},
        dataType: 'json',
        success: function (data) {
            //alert(data.message)
            refresh();
        }
        });

}
//jqgrid刷新
function refresh() {
    jQuery("#gridTable").trigger("reloadGrid");
}