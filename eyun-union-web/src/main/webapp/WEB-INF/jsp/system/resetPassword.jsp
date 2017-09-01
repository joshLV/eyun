<!DOCTYPE>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>易盟重置密码页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/jsp/taglib.jsp" %>
    <%@ include file="/common/jsp/header.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/style.css"/>
    <style type="text/css">
        .pswd_state {
            margin: 0 5px;
            position: relative;
            zoom: 1;
            vertical-align: middle;
            display: inline-block;
            width: 124px;
            height: 10px;
            background-color: #ececec;
            border: 1px solid #8fd1ff;
        }
        .pswd_state{margin:0 5px;position:relative;zoom:1;vertical-align:middle;display:inline-block;*display:inline;*zoom:1;width:124px;height:10px;background-color:#ececec;border:1px solid #8fd1ff;}
        .pswd_state_def{border:1px solid #c7c7c7;}
        .pswd_state .level{position:absolute;top:1px;left:1px;width:40px;height:8px;display:inline-block;*display:inline;*zoom:1;overflow:hidden;}
        .pswd_state .level_1{background-color:#8fd1ff;}
        .pswd_state .level_2{background-color:#43b1fe;left:42px;}
        .pswd_state .level_3{background-color:#3399e0;left:83px;}
        .pswd_state .level_0{background-color:#c7c7c7;}
        .pswd_state .level_0_1{background-color:#a0a0a0;left:42px;}
        .pswd_state .level_0_2{background-color:#898989;left:83px;}
    </style>
</head>
<body class="bg">
<form id="setpwd" action="${ctx}/system/user/resetPassword_login.do">
    <input type="hidden" id="userName" value="${userName}">
    <input type="hidden" id="mobile" value="${mobile}">
    <input type="hidden" id="code" value="${code}">
    <div class="top">
        <div class="main hack top_c">
            <h1>易盟服务中心</h1>
            <a href="" title="易盟服务中心" class="logo fl"><p class="logo1"><span></span><i></i></p><p class="logo2"></p></a>
        </div>
    </div>
    <div class="RackPW">
        <div class="RackPW_main">
            <div class="RackPW_t"><span>重置密码</span><img src="${ctx}/images/zhmm7.png" /></div>
            <div class="RackPW_list">
                <img src="${ctx}/images/zhmm5.png" />
                <input id="pwd" type="password" name="pwd"  placeholder="新密码" /><i></i><u></u>
            </div>
            <%--<div class="RackPW_strength">密码强度：弱<p><span class="on"></span><span></span><span></span></p>强

            </div>--%>
            <div class="RackPW_strength">
                密码强度：弱<span class="pswd_state pswd_state_def">
							<span class="level level_0"></span><span class="level level_0_1"></span>
							<span class="level level_0_2"></span></span><span class="pswd_result">强</span>
            </div>
            <div class="RackPW_list">
                <img src="${ctx}/images/zhmm6.png" />
                <input id="re_pwd" name="re_pwd" type="password" placeholder="确认密码"  /><i></i><u></u>
            </div>
            <input type="button" class="RackPW_submit" value="确 认"  onclick="Setpwd.setpwd();"/>
        </div>
    </div>
    <div class="foot">上海云辰信息科技有限公司版权所有</div>
<%--<div class="box-retrieve-pwd">
    <div class="yanxian">
        <a>重置密码</a>
    </div>
        <table class="param-box">
            <tr>
                <td width="250" class="txt-right lbl">新密码：</td>
                <td width="300" colspan="2">
                    <input id="pwd" type="password" name="pwd" class="val wdth-normal">
                    <span class="msg"></span>
                </td>
            </tr>
            <tr>
                <td></td>
                <td width="300" colspan="2">
                    <div class="strength">
                        密码强度：弱<span class="pswd_state pswd_state_def">
							<span class="level level_0"></span><span class="level level_0_1"></span>
							<span class="level level_0_2"></span></span><span class="pswd_result">强</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td width="250" class="txt-right lbl">确认密码：</td>
                <td width="350" colspan="2">
                    <input id="re_pwd" name="re_pwd" type="password" class="val wdth-normal"/>
                    <span class="msg"></span>
                </td>
            </tr>
                <td></td>
                <td>
                    <input type="button" class="btnb" value="确认" onclick="Setpwd.setpwd();"/>
                </td>
            </tr>
        </table>
</div>--%>
</form>

</body>
<script type="text/javascript" src="${ctx}/scripts/system/find_pwd.js"></script>
<script type="text/javascript">pwdreset.init();</script>
<script type="text/javascript">var ctx = "${ctx}";</script>
</html>
