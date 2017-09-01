<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
<title>易盟登录页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx }/css/style.css" />
<link href="${ctx}/images/eyun_title.ico" rel="shortcut icon" />

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
                <i class="login_icon1"></i><input type="text" class="login_text Prompt_focus" id="loginName" placeholder="账号"/> <%--<label
                    class="Prompt login_Prompt">帐号</label>--%>
			</div>
		</div>
		<div class="login_list">
			<div class="Tips">
                <i class="login_icon2"></i><input type="password" class="login_text Prompt_focus" id="password" placeholder="密码"/> <%--<label
                    class="Prompt login_Prompt">密码</label>--%>
			</div>
		</div>
		<div class="login_list hack">
			<div class="Tips fl">
				<input type="text" class="login_text2 Prompt_focus" id="v_code" maxlength="4"/> <label class="Prompt login_Prompt2">验证码</label>
			</div>
			<span class="login_yzm fl" title="换一张"><img id="vCode" title="点击更换" onclick="changeVCode();" src="${ctx}/loginVCode.do"/></span>
            <%--找回密码--%>
            <a href="${ctx}/system/user/find_pwd_login.do" title="忘记密码？" target="_blank" class="logon_wjmm fr">忘记密码？</a>
            <%--找回密码--%>
		</div>
		<div class="Login_button" onclick="login();">登 录</div>
		<div class="login_Error">帐号或密码错误</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/jQuery.md5.js"></script>
<%--rsa 所需要的js以下--%>
<script type="text/javascript" src="${ctx}/common/scripts/rsa/Barrett.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/rsa/BigInt.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/rsa/RSA.js"></script>

<%--rsa 所需要的js 以上--%>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<script type="text/javascript">
window.history.forward(-1);
$(function(){$(".Tips").click(function(){$(this).find(".Prompt").hide().end().find(".Prompt_focus").focus().blur(function(){if($(this).val()==0){$(this).parent().find(".Prompt").show()}})});$(".Prompt_focus").keydown(function(){if($(this).val()==0){$(this).parent().find(".Prompt").hide()}})});var i=document.createElement("i"),frg=document.createDocumentFragment();i.className="start";function setI(a){a.style.cssText="width:"+(Math.random()*30+5)+"px;left:"+Math.random()*95+"%;top:"+Math.random()*10+"%;animation-delay:"+Math.random()*-10+"s;-webkit-animation-delay:"+Math.random()*-10+"s;";return a}frg.appendChild(setI(i));for(var j=0,ii=10;j<ii;j++){i=i.cloneNode(true);frg.appendChild(setI(i))}document.body.appendChild(frg);

$(function() {
	$("*").keydown(function (event){
        if (event.keyCode == 13) {
            login();
            return false;
        }
    });
});
    function login(){
        var memberName = $("#loginName").val();//用户名
        var memberPwd = $("#password").val();//密码

        if(memberName=="" || memberName==null||$("#loginName").hasClass("clr-grey")){
            $(".login_Error").text("请填写用户名").css("visibility","visible");
            return false;
        }else{
            $(".login_Error").text("");
        }
        if(memberPwd=="" || memberPwd==null){
            $(".login_Error").text("请填写密码").css("visibility","visible");
            //$(".login_Error").html("请填写密码");
            return false;
        }else{
            $(".login_Error").html("");
        }
        var v_code = $("#v_code").val();
        if($.trim(v_code)==""||$.trim(v_code)==null) {
            $(".login_Error").text("请输入验证码").css("visibility","visible");
            return false;
        }
        // 密码生成md5
        var pwd = $.md5(memberPwd);
        // 对md5用rsa加密
        setMaxDigits(130);
        var key = new RSAKeyPair("${e}", "", "${m}");
        var result = encryptedString(key, pwd);
        var checkVCode = true;
        //开始登陆
        $.ajax({
            type : "post",
            url : "${ctx}/dologin.do",
            data: {
                "loginName": memberName,
                "password": result,
                "vCode":v_code
            },
            async :false,
            dataType : "json",
            success : function(data){
                if(data && data.status == "SUCC"){
                    window.location.href = "${ctx}/main/union.htm";
                } else if (data.status == 'OTHER') {
                    location.reload();//如果解密失败 刷新页面
                } else {
                    checkVCode = false;
                    $(".login_Error").text(data.msg).css("visibility","visible");
                }
            },
            error : function(){
            	
            }
        });
        if(!checkVCode) {
            changeVCode();
        }
    }

    // 验证码
    function changeVCode() {
        $("#vCode").attr('src', '${ctx}/loginVCode.do?t=' + Math.random());
    }
</script>
</html>
