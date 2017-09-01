<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
<title>信息发布平台登录页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx }/css/style.css" />
</head>
<body>
	<div class="loginbg"></div>
	<div class="login">
		<div class="loginLogo">
			<p class="loginLogo1">
				<span></span><i></i>
			</p>
			<p class="loginLogo2"></p>
		</div>
		<div class="login_list">
			<div class="Tips">
				<i class="login_icon1"></i><input type="text" class="login_text Prompt_focus" id="loginName" /> <label class="Prompt login_Prompt">帐号</label>
			</div>
		</div>
		<div class="login_list">
			<div class="Tips">
				<i class="login_icon2"></i><input type="password" class="login_text Prompt_focus" id="password" /> <label class="Prompt login_Prompt">密码</label>
				<input type="hidden" id="platformId" value = "000000" />
			</div>
		</div>
		<%-- <div class="login_list hack">
			<div class="Tips fl">
				<input type="text" class="login_text2 Prompt_focus" /> <label class="Prompt login_Prompt2">验证码</label>
			</div>
			<span class="login_yzm fl" title="换一张"><img src="${ctx}/images/yzm.png" /></span> <a href="" title="忘记密码？" class="logon_wjmm fr">忘记密码？</a>
		</div> --%>
		<div class="Login_button" onclick="login();">登 录</div>
		<div class="login_Error">帐号或密码错误</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>

<script type="text/javascript">
window.history.forward(-1);
$(function(){$(".Tips").click(function(){$(this).find(".Prompt").hide().end().find(".Prompt_focus").focus().blur(function(){if($(this).val()==0){$(this).parent().find(".Prompt").show()}})});$(".Prompt_focus").keydown(function(){if($(this).val()==0){$(this).parent().find(".Prompt").hide()}})});var i=document.createElement("i"),frg=document.createDocumentFragment();i.className="start";function setI(a){a.style.cssText="width:"+(Math.random()*30+5)+"px;left:"+Math.random()*95+"%;top:"+Math.random()*10+"%;animation-delay:"+Math.random()*-10+"s;-webkit-animation-delay:"+Math.random()*-10+"s;";return a}frg.appendChild(setI(i));for(var j=0,ii=10;j<ii;j++){i=i.cloneNode(true);frg.appendChild(setI(i))}document.body.appendChild(frg);

$(function() {
	console.log($("#loginName").val());
	$("*").keydown(function (event){
        if (event.keyCode == 13) {
            login();
            return false;
        }
    });
});
    function login(){
        var memberName = $("#loginName").val();
        var memberPwd = $("#password").val();
        var platformId = $("#platformId").val();
        if(memberName=="" || memberName==null||$("#loginName").hasClass("clr-grey")){
        	$(".login_Error").text("请填写用户名").css("visibility","visible");
//             $(".login_Error").text("请填写用户名");
            return false;
        }else{
            $(".login_Error").text("");
        }
    
        if(memberPwd=="" || memberPwd==null){
            //$("#pwdTip").html("请填写密码");
             $(".login_Error").text("请填写密码").css("visibility","visible");
            return false;
        }else{
            $("#pwdTip").html("");
        }
//         var chkSts = $("#savePwd").prop("checked");
        //开始登陆
        $.ajax({
            type : "post",
            url : "${ctx}/dologin.do",
            type: "post",
            data : {"loginName":memberName,"password":memberPwd,"platformId":platformId},
            dataType : "json",
            success : function(data){
                if(data && data.status == "SUCC"){
                	window.location.href = "${ctx}/main/riplat.htm";
                } else {
                    $(".login_Error").html(data.msg).css("visibility","visible");
                }
            },
            error : function(){
            	
            }
        });
    }
</script>
</html>
