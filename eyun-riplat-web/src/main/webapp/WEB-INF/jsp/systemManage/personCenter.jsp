<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglib.jsp"%>
<!DOCTYPE HTML>

<html>
<head>
<title>易韵联盟</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/styles/ad/material.css"></link>
<script type="text/javascript" src="${ctx}/common/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/uploadPreview.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/mater_add.js"></script>
<style type="text/css">
#materialShowDiv {
	position: absolute;
	right: 1px;
	top: 80px;
}

.inpt-file {
	left: 612px;
}

.input-hidde {
	border: 0px solid #ccc;
	padding-left: 0px;
}

.input {
	margin: 0;
}

.clear {
	display: none;
	position: absolute;
}

.input::-ms-clear {
	display: none;
}

.input:valid+.clear {
	display: inline;
}
</style>
</head>
<body>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">信息修改</a></li>
			<li><a href="#tabs-2">密码修改</a></li>
			<li><a href="#tabs-3">设置别名</a></li>
		</ul>
		<div id="tabs-1">
			<div class="">
				<form action="${ctx}/systemSetup/member!updateMemberInfo.do" id="placeForm" method="post">
					<input type="hidden" id="cmaterialType" value="4" />
					<table height="430" cellpadding="0" cellspacing="0" class="size-12 clr-bd-grey">
						<tr>
							<td colspan="2" class="ht-35 size-14 clr-blue clr-bg-blue clr-bd-b-grey" style="width:615px;">
								<h3>
									<label id="materialTitle" class="mg-l-20 fw">个人信息</label>
								</h3>
							</td>
							<td class="ht-35 size-14 clr-blue clr-bg-blue clr-bd-b-grey" style="text-align: right;"><label id="materialTitle" class="mg-l-20 fw"><a class="opt-lnk" style="color:#09F; text-decoration:none;" href="#" onclick="updateData();">编辑&nbsp;&nbsp;&nbsp;&nbsp;</a></label></td>
						</tr>
						<tr>
							<td class="txt-right clr-bd-r-grey clr-bd-b-grey" style="width: 130px;">真实姓名：</td>
							<td class="clr-bd-b-grey" style="height:52px; width:485px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="input_middle input-hidde input" type="text" name="usrmemberbase.memberrealname" id="memberRealName" value="${usrmemberbase.memberrealname}" readonly="readonly" /></td>
							<td class=" clr-bd-l-grey" style="text-align: center;">
								<div>会员头像</div>
							</td>

						</tr>
						<tr>
							<td class="txt-right clr-bd-r-grey clr-bd-b-grey" style="width: 130px;">联系电话(固话)：</td>
							<td class="clr-bd-b-grey" style="height:52px; width:485px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="input_middle input-hidde input" type="text" name="usrmemberbase.tel " id="tel" value="${usrmemberbase.tel}" maxlength="20" readonly="readonly" /><span style="color:#FF0000 "></span></td>
							<td class=" clr-bd-l-grey" style="text-align: right;" rowspan="3">
								<div style="width: 150px; height: 150px; text-align: center;">
									<div id="imgdiv" style="text-align: center;">
										<input id="cmaterialUrl" type="hidden" name="usrmemberbase.headimgurl" value="${usrmemberbase.headimgurl}"> <img id="imgView" class="img-show" src="${usrmemberbase.headimgurl}" />
									</div>
									<span class="uploadMess"></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="txt-right clr-bd-r-grey clr-bd-b-grey" style="width: 130px;">手机：</td>
							<td class="clr-bd-b-grey" style="height:52px; width:485px;">&nbsp;&nbsp;&nbsp;&nbsp;<input class="input_middle input-hidde input" type="text" name="usrmemberbase.mobile" id="mobile" value="${usrmemberbase.mobile}" maxlength="11" readonly="readonly" /><span style="color:#FF0000 "></span></td>
						</tr>
						<tr>
							<td class="txt-right clr-bd-r-grey clr-bd-b-grey" style="width: 130px;">Email：</td>
							<td class="clr-bd-b-grey" style="height:52px; width:485px;">&nbsp;&nbsp;&nbsp;&nbsp;<input class="input_middle input-hidde input" type="text" name="textfield6" id="email" value="${usrmemberbase.email}" style="width:200px;" readonly="readonly" /><span style="color:#FF0000 "></span></td>
						</tr>
						<tr>
							<td class="txt-right clr-bd-r-grey clr-bd-b-grey" style="width: 130px; height:55px;">所在地区：</td>
							<td class="clr-bd-b-grey" colspan="" style="height:52px; width:485px;">&nbsp;&nbsp;&nbsp; <select name="" onchange="loadCity(this.value);" id="province" style="width:80px" class="tag-hide" disabled="disabled">
									<%-- <s:iterator value="areaList" items="${areaList}" var="ps">
					<option value="${ps.pId}">${ps.areaName}</option>
				</s:iterator> --%>
							</select> <label class="tag-hide">省&nbsp;&nbsp;&nbsp;</label><input type="hidden" id="areaID" class="tag-hide" value="${usrmemberbase.areaid}" readonly="readonly"> <select name="select" class="tag-hide" onchange="loadTown(this.value);" id="city" style="width:100px" value="" disabled="disabled">
									<!-- <option value="0">----</option> -->
							</select class="tag-hide"><label class="tag-hide">地级市&nbsp;&nbsp;&nbsp;</label> <select name="" id="county" style="width:80px" class="tag-hide" onchange="area();" disabled="disabled">
									<!-- 	<option value="0">----</option> -->
							</select> <label class="tag-hide">县级市<span id="area" style="color:#FF0000 "></span></label> <input class="input-hidde" id="areaName" readonly="readonly" style="width:320px" name="usrmemberbase.areaName" value="${usrmemberbase.areaName}">
							</td>
							<td class="txt-center clr-bd-r-grey clr-bd-b-grey clr-bd-l-grey">
								<div>
									<input class="btn_style02" value="浏 览" type="button" style="display:none;" /> <input type="file" name="imgfile" id="fileField" class="inpt-file" size="14" style="display:none;" /> <input class="btn_style02 mg-l-20" type="button" id="file_upload" value="上传" style="display:none;" />
								</div>
						</tr>
						<tr>
							<td class="txt-right clr-bd-r-grey clr-bd-b-grey" style="width: 130px;">详细地址：</td>
							<td class="clr-bd-b-grey" colspan="2" height="80"><textarea rows="3" cols="58" class="input-hidde" id="addr" readonly="readonly" name="usrmemberbase.addr">${usrmemberbase.addr}</textarea></td>
						</tr>
						<tr>
							<td colspan="3"><span style="margin-left:15%;color:#FF0000 ;" id="errorTip">&nbsp;</span></td>
						</tr>
						<tr>
							<td height="30" colspan="2" align="center" class="clr-bd-b-grey"><input type="button" class="btn_style01" value="保存" onclick="updateMemberInfo();" style="display:none;" /> <input id="btnCancel" name="btnCancel" type="button" class="btn_style01" value="取消" onclick="goback()"
								style="display:none;" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="tabs-2">
			<!--memberInfo  -->
			<%-- <jsp:include page="/WEB-INF/jsp/systemManage/updatePwd.jsp"></jsp:include> --%>
			<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce
				sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
		</div>
		<div id="tabs-3">
			<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce
				sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
			<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus
				dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus
				hendrerit hendrerit.</p>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$( "#tabs" ).tabs();
			//控制手机号码,以前用户注册没有手机号码
	/* 	var mobile=$("#mobile").val();
		if($.trim(mobile)!=null&&""!=mobile){
			$("#mobile").attr("readonly",true);
			$("#mobile").siblings("span").html("");
		}else{
			$("#mobile").attr("readonly",true);
		} */
		var headImgUrl = $("#cmaterialUrl").val();//头像初始化，空则设置为默认头像
		var smallImg = headImgUrl.replace('/User/','/User/small/');
		if(headImgUrl!=null&""!=headImgUrl){
        	$("#imgView").attr("src", smallImg).css("display","inline-block");
		}else{
        	var defaultSrc="/eyunion/common/images/user.png";
            $("#imgView").attr("src", defaultSrc).css("display","inline-block");
		}
        $(":text").blur(function() {
			if ($(this).val() != null && $(this).val() != "") {
				$(this).siblings("span").html("");
			}
		});
		var areaID = $("#areaID").val();

		/****加载省份信息******/
		$.ajax({
			type:"post",
			url:"${ctx}/pubArea/toLoadProvince.do",
			dataType:"json",
			success:function(data){
				var jsonObj = eval('('+data.data+')');
				var str ='';
				$("#province").append('<option value="-1">请选择</option>')
				$.each(jsonObj, function (i, item) {
					$("#province").append("<option value="+ item.id+">"+ item.areaName+"</option>");
				});
// 				$("#province").find("option[value='" + 420000 + "']").prop("selected", true);
				//$("#province option[value='" + 420000 + "']").prop("selected", true);//默认加载选项
			},
			error:function(){
				showMsg("error!");
			}
		});
	});
	
	/****加载市信息******/
	function loadCity(parentCode) {
		$.ajax({
			type:"post",
			url:"${ctx}/pubArea/toLoadCity.do",
			dataType:"json",
			data : {"parentCode": parentCode},
			success:function(data){
				var jsonObj = eval('('+data.data+')');
				var str ='';
				$("#city").empty("");
				$("#city").append('<option value="-1">请选择</option>')
				$.each(jsonObj, function (i, item) {
					$("#city").append("<option value="+ item.id+">"+ item.areaName+"</option>");
				});
			},
			error:function(){
				showMsg("error!");
			}
		});
	}
	
	/****加载区镇信息******/
	function loadTown(parentCode) {
		$.ajax({
			type:"post",
			url:"${ctx}/pubArea/toLoadTown.do",
			dataType:"json",
			data : {"parentCode" : parentCode},
			success:function(data){
				var jsonObj = eval('('+data.data+')');
				var str ='';
				$("#county").empty("");
				$("#county").append('<option value="-1">请选择</option>')
				$.each(jsonObj, function (i, item) { 
					$("#county").append("<option value="+ item.id+">"+ item.areaName+"</option>");
				});
			},
			error:function(){
				showMsg("error!");
			}
		});
	}
	

	function loadArea(obj) {
		var fontObj = $(obj);
		var id = fontObj.attr("id");
		var parentId = fontObj.val();
		//var level = 1;
		if (id == "province") {
			//level = 2;
			$("#county").html("<option value='0'>----</option>");
		} /* else if (id == "city") {
			level = 3;
		} */
		$.ajax({
			type : "GET",
			url : "${ctx}/pubArea/toLoadProvince.do",
			data :"",/*  {
				"level" : level,
				"parentId" : parentId
			}, */
			dataType : 'json',
			success : function(data) {
				// 				showMsg(data.msg);
				if (id == "province") {
					$("#city").empty("");
					$("#city").append(data.msg);
				} else if (id == "city") {
					$("#county").empty("");
					$("#county").append(data.msg);
				}
			}
		});
	}
	//修改个人信息
	function updateMemberInfo() {
		var headImgUrl = $("#cmaterialUrl").val();
		var memberRealName = $("#memberRealName").val();
		var tel = $("#tel").val();
		var mobile = $("#mobile").val();
		var email = $("#email").val();
		var areaId = $("#county").val();
		var addr = $("#addr").val();
		//电话号码与手机号码同时验证
		var telReg = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;  
		//var mobileReg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
		var mobileReg = /^1[3,4,5,6,7,8,9][0-9]{9}$/;
		var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var emailReg2 = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
		if (tel != null && "" != tel && !telReg.test(tel)) {
			$("#tel").siblings("span").html("电话号码不正确");
			return false;
		}
		if (mobile != null && "" != mobile && !mobileReg.test(mobile)) {
			$("#mobile").siblings("span").html("手机号码不正确");
			return false;
		}else if($.trim(mobile)==null||$.trim(mobile)==""){
			$("#mobile").siblings("span").html("请输入手机号码");
			return false;
		}
		if ((email != null && "" != email) && !(emailReg.test(email)|| emailReg2.test(email))) {
			$("#email").siblings("span").html("Email地址不正确");
			return false;
		}
		if(areaId == null || "" == areaId || "0" == areaId){
			$("#area").html("县级市必填");
			return false;
		}
		var url = "${ctx}/systemSetup/member!updateMemberInfo.do";
		$.ajax({
			type : 'post',
			url : url,
			data : {
				"userMemberEntity.headImgUrl" : headImgUrl,
				"userMemberEntity.memberRealName" : memberRealName,
				"userMemberEntity.tel" : tel,
				"userMemberEntity.mobile" : mobile,
				"userMemberEntity.email" : email,
				"userMemberEntity.areaId" : areaId,
				"userMemberEntity.addr" : addr
			},
			dataType : 'json',
			success : function(data) {
				if (data) {
					var msg = data.msg;
					if (msg == -1) {
						$("#errorTip").html("修改失败");
						return false;
					} else if (msg == 0) {
						layer.msg("修改成功", 2, 9,function(){
							location.reload();
						});
						return false;
					} else if (msg == -3) {
						$("#errorTip").html("email重复");
						return false;
					} else if (msg == -4) {
						$("#errorTip").html("手机号码重复");
						return false;
					}
				} else {
					$("#errorTip").html("修改失败");
					return false;
				}
			}
		});
	}
    function area(){
    	$("#area").html("");
    } 
	/* function resizeParentSize(){
		var height =  500+100;
		window.parent.resizeIframe(null,height);
	} */
	
	function goback(){
		window.location.href="${ctx}/systemSetup/member!memberInfo.do";
	}
	
	function updateData(){
		var mobile = $("#mobile").val();
		$(".input-hidde").removeAttr("readonly");
		$(".tag-hide").removeAttr("disabled");
		/* if(mobile = null || mobile == ""){
			$("#mobile").attr("readonly",false);
			$("#mobile").siblings("span").html("保存后不能修改");
		}else{
			$("#mobile").attr("disabled",true);
		} */
		$(".input-hidde").removeClass("input-hidde");
		$(".tag-hide").removeClass("tag-hide");
		$("#areaName").css("display","none");
		$(".btn_style01").css("display","inline-block");
		$(".btn_style02").css("display","inline-block");
		$(".inpt-file").css("display","inline-block");
		
	}
	
	/* for file upload */
	function uploadFile(){
	  new uploadPreview({ UpBtn: "fileField", DivShow: "imgdiv", ImgShow: "imgView" }); 
	}
	window.onload = function () {
		uploadFile();
	}
</script>

</html>
