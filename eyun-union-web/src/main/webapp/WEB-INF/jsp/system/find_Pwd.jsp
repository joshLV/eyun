<!DOCTYPE>
<%@ page language="java" pageEncoding="utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>易盟找回密码页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/jsp/taglib.jsp" %>
    <%@ include file="/common/jsp/header.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/style.css"/>
</head>
<body>
<form id="get_password" action="${ctx}/system/user/resetPage_login.do" method="post">
    <div class="top">
        <div class="main hack top_c">
            <h1>易盟服务中心</h1>
            <a href="" title="易盟服务中心" class="logo fl"><p class="logo1"><span></span><i></i></p>

                <p class="logo2"></p></a>
        </div>
    </div>
    <div class="RackPW">
        <div class="RackPW_main">
            <div class="RackPW_t"><span>找回密码</span><img src="${ctx}/images/zhmm.png"/></div>
            <div class="RackPW_list">
                <img src="${ctx}/images/zhmm2.png"/><input id="mobile" type="text" name="mobile" maxlength="11"
                                                           placeholder="手机号"/><i></i><u></u>
            </div>
            <div class="RackPW_list">
                <img src="${ctx}/images/zhmm3.png"/><input id="userName" name="userName" type="text"
                                                           placeholder="易盟帐号"/><i></i><u></u>
            </div>
            <div class="RackPW_list">
                <img src="${ctx}/images/zhmm4.png"/>
                <input id="code" name="code" type="text" placeholder="验证号" style="width:150px"/>
                <u></u>
                <input type="button" class="RackPW_button" id="sendCode" onclick="mb_pswd.getcode();" value="获取验证码"/>
            </div>
            <input type="button" class="RackPW_submit" value="确 认" onclick="mb_pswd.find_pwd();"/>
        </div>
    </div>
    <div class="foot">上海云辰信息科技有限公司版权所有</div>
    <%--<div class="box-retrieve-pwd">
        <div class="yanxian">
            <a>找回密码</a>
        </div>
            <table class="param-box">
                <tr>
                    <td width="250" class="txt-right lbl">手机号：</td>
                    <td width="300" colspan="2">
                        <input id="mobile" type="text" name="mobile" class="val wdth-normal" maxlength="11">
                        <span class="msg"></span>
                    </td>

                </tr>
                <tr>
                    <td width="250" class="txt-right lbl">易盟账号：</td>
                    <td width="350" colspan="2">
                        <input id="userName" name="userName" type="text" class="val wdth-normal"/>
                    </td>
                    <td with="200">
                        <span id="telTip2" class="clr-error"></span>
                    </td>
                </tr>
                <tr>
                    <td class="txt-right lbl">验证码：</td>
                    <td colspan="2">
                        <input id="code" name="code" type="text" class="val" maxlength="6"/>
                    <span class="msg">
                        <a href="#" onclick="mb_pswd.getcode();">免费获取短信</a>
                    </span>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" class="btnb" value="确认" onclick="mb_pswd.find_pwd();"/>
                    </td>
                </tr>
            </table>
    </div>--%>
</form>

</body>
<script type="text/javascript" src="${ctx}/scripts/system/find_pwd.js"></script>
<script type="text/javascript">mb_pswd.init();</script>
<script type="text/javascript">var ctx = "${ctx}";</script>
</html>
