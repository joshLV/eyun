<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta content="email=no" name="format-detection" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <title>PC预览模版</title>
  <style>
    /*公用样式*/
    *{margin:0;padding:0}
    body{font:12px/1.5 微软雅黑,Arial,Helvetica,sans-serif;color:#606060;min-width:1000px; background:url(${ctx}/images/portal/body_bg.jpg) top center no-repeat;}
    ol,ul{list-style:none}
    img,input,select,textarea{vertical-align:middle;outline:0;font-family:微软雅黑;font-size:12px}
    a{text-decoration:none;color:#606060}
    a:hover{text-decoration:none}
    img,input{border:0}
    i,em{font-style:normal}
    .fl{float:left}
    .fr{float:right}
    .blank{clear:both;font-size:0;height:0;line-height:0;overflow:hidden}
    .hack:after{clear:both;height:0;overflow:hidden;display:block;visibility:hidden;content:"."}
    .hack{*zoom:1}
    .main{ width:1000px; margin:0 auto;}

    .top{ overflow:hidden;}
    .top h1{ float:left; background:url(${ctx}/images/portal/yy.png) no-repeat; width:155px; height:83px; text-indent:-999px; margin:12px 0 0 24px;}
    .top_c{ float:left; font-size:22px; font-weight:bold; text-align:center; letter-spacing:2px; margin:33px 0 0 10px;}
    .top_c span{ font-size:12px; letter-spacing:normal; margin-top:-5px; display:inline-block;}
    .top_r{ font-size:16px; margin-top:42px;}
    .top_r a{ margin:0 10px; padding:0 5px; float:left; color:#758893;}
    .top_r a.on{ color:#0d72cc; border-bottom:3px solid #0d72cc}

    .Layout{ margin:20px 0 33px;}
    .Layout_l{ background:url(${ctx}/images/portal/Layout_l.png) no-repeat; width:274px; height:385px; padding:15px 24px 19px 25px; position:relative}
    .Layout_l_t{ margin-bottom:10px;}
    .Layout_l_t h2{ float:left; color:#167bc8; font-size:18px; font-weight:normal}
    .Layout_l_t label{ float:right; font-size:12px; color:#167bc8; margin-top:4px}
    .Layout_l_t label span{ vertical-align:middle; margin-left:5px;}
    .Layout_l_list{ margin-bottom:20px;}
    .Layout_l_list input{ background:0; background:url(${ctx}/images/portal/Layout_l_list_input.png) no-repeat; width:263px; height:16px; line-height:16px; padding:12px 5px;}
    .Layout_l_list input.submit{ background:url(${ctx}/images/portal/submit2.png) no-repeat; width:274px; height:40px; padding:0; line-height:normal}
    .Layout_l_publicity img{ width:274px; height:168px;}
    .Layout_l_prompt{ text-align:center; position:absolute; top:213px; left:25px; width:274px; color:red; display:none}
    .Layout_c{ background:url(${ctx}/images/portal/Layout_c.png) no-repeat; width:274px; height:385px; padding:15px 24px 19px 25px; margin-left:15px; position:relative;}
    .Layout_c h2{ font-size:18px; font-weight:normal; text-align:center; color:#ff8a00;}
    .Layout_c p{ line-height:40px; color:#393939; font-size:14px;}
    .Layout_c_arrow{ position:absolute; top:86px; left:-27px;}
    .Layout_c_arrow2{ position:absolute; top:135px; left:-27px;}
    .Layout_c_arrow3{ position:absolute; top:200px; right:-60px;}
    .Layout_r{ background:url(${ctx}/images/portal/Layout_r.png) no-repeat; width:274px; height:385px; padding:15px 24px 19px 25px; text-align:center;}
    .Layout_r h2{ font-size:18px; font-weight:normal; text-align:center; color:#40bb03;}
    .Layout_r p{ line-height:26px; font-size:14px; text-align:left}

    input::-webkit-input-placeholder{ color:#fff}
    input::-moz-placeholder{ color:#fff;}
    input:-ms-input-placeholder { color:#fff;}

    .Advertising{ overflow:hidden; width:1000px; padding-bottom:20px;}
    .Advertising a{ float:left; margin-right:13px; width:240px;}
    .Advertising a img{ width:240px; height:140px;}
  </style>
</head>
<body>
<div class="main">
    <div class="top">
        <h1>上海地铁</h1>
        <p class="top_c">${portal.title}<br /><%--<span>Shanghai Metro</span>--%></p>
        <div class="top_r fr">
            <a href="" class="on">中文版</a><a href="">English</a>
        </div>
    </div>
    <div class="Layout hack">
        <div class="Layout_l fl">
            <div class="Layout_l_t hack">
                <h2>易韵上网服务中心</h2>
                <label><input type="checkbox"><span>加入会员</span></label>
            </div>
            <div class="Layout_l_list">
                <input type="text" class="Layout_l_input" placeholder="手机号 / 易韵账号 / 分享账号" />
            </div>
            <div class="Layout_l_list">
                <input type="password" class="Layout_l_input" placeholder="验证码 / 易韵密码 / 分享密码" />
            </div>
            <div class="Layout_l_list">
                <input type="submit" value="" class="submit" />
            </div>
            <div class="Layout_l_prompt">您输入的帐号或密码错误！</div>
            <a  title="" class="Layout_l_publicity"><img src="${ctx}/images/portal/default_ad.jpg" /></a>
        </div>
        <div class="Layout_c fl">
            <h2>友情提示</h2>
            <div style="padding-left:45px">
                <p style="line-height:26px">如您未安装“易韵”，请先用您的手机认证上网</p>
                <p>填入您通过认证的手机号。</p>
                <p>填入您收到的验证码</p>
            </div>
            <p>请扫描右边的二维码，下载并注册"易韵"。打开易韵，点击首页，会看到“易韵账号、易韵密码”，“易韵分享号、易韵分享密码”，填入左边登录框中，即可上网。</p>
            <img src="${ctx}/images/portal/arrow_l.png" class="Layout_c_arrow" />
            <img src="${ctx}/images/portal/arrow_l.png" class="Layout_c_arrow2" />
            <img src="${ctx}/images/portal/arrow_r.png" class="Layout_c_arrow3" />
        </div>
        <div class="Layout_r fr">
            <h2>易韵-您的私人秘书</h2>
            <p>易韵注册完后，同一账号支持4台设备同时上网。</p>
            <img src="${ctx}/images/portal/ewm.png"  style=" margin:15px 0;">
            <a><img src="${ctx}/images/portal/iphone.png" style=" margin-bottom:15px;" /></a>
            <a><img src="${ctx}/images/portal/android.png" /></a>
        </div>
    </div>
    <div class="Advertising">
        <a><img src="${portal.position4Url}"/></a>
        <a><img src="${portal.position5Url}"/></a>
        <a><img src="${portal.position6Url}"/></a>
        <a style="margin:0"><img src="${portal.position7Url}"/></a>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>
<script>
    $(function () {
        var logoUrl = '${portal.logoUrl}';
        if (logoUrl != null && logoUrl != '') {
            $(".top h1").css({"background": "url(${portal.logoUrl})", "background-repeat": "no-repeat"});
        }

        if (!placeholderSupport()) {   // 判断浏览器是否支持 placeholder
            $(".Layout_l_input").focus(function () {
                var input = $(this);
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                    input.removeClass('placeholder');
                }
            }).blur(function () {
                var input = $(this);
                if (input.val() == '' || input.val() == input.attr('placeholder')) {
                    input.addClass('placeholder');
                    input.val(input.attr('placeholder'));
                }
            }).blur();
        }

        $("img").each(function() {
           if($(this).attr("src") == "") {
               $(this).attr("src", "${ctx}/images/portal/default_ad.jpg");
           }
        });

        /*$("img").error(function() {
            $(this).attr("src", "images/portal/3333.jpg");
        });*/
    });
    function placeholderSupport() {
        return 'placeholder' in document.createElement('input');
    }
</script>
</body>
</html>
