<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta content="email=no" name="format-detection" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <title>手机预览模版</title>
    <style>
        /*公用样式*/
        *{ margin:0; padding:0;}
        body{ font-size:16px; line-height:1.5; color:#000; background:#1f67d6}
        ol,ul{ list-style:none;}
        select,input,img,textarea{ vertical-align:middle; outline:none;}
        a{ text-decoration:none; color:#000;}
        a:hover{ text-decoration:none;}
        fieldset,img,input{ border:0;}
        .fl{ float:left;}
        .fr{ float:right;}
        .blank{ clear: both; font-size: 0; height: 0; line-height: 0; overflow: hidden;}
        .hack{ *zoom:1;}
        .hack:after{ clear:both; height:0; overflow:hidden; display:block; visibility:hidden; content:".";}

        .title{ text-align:center; position:relative; font-size:1.5rem; padding:1rem 0; background:#fff}
        .logo{ position:absolute; left:1.5rem; top:1.1rem; background:url(${ctx}/images/portal/yy.png) no-repeat; background-size:2rem 2.2rem; width:2rem; height:2.2rem;}
        .main{ padding-bottom:1rem;}
        .login{ margin-top:3rem}
        .Edition{ text-align:center; padding:1.5rem 2rem 0}
        .Edition_c{ width:45%;}
        .Explain{ font-size:1rem; color:#fff; line-height:1.875rem; font-weight:bold; padding:1rem 2rem 0}
        .Explain h2{ font-size:1rem; background:url(${ctx}/images/portal/Explain3.png) left center / 1.875rem auto no-repeat; padding-left:2.1875rem;}
        .advertising{ position:absolute; bottom:0; left:0;}

        .edition{ font-size:0.6rem; /*position:absolute;*/ right:0.5rem; top:1.6rem; border:0;}

        @media screen and (min-width:320px) and (max-width:360px){
            html{ font-size:14px}
        }
        @media screen and (min-width:361px) and (max-width:414px){
            html{ font-size:16px}
        }
        @media screen and (min-width:415px){
            html{ font-size:28px}
        }
    </style>
</head>
<body>
<div class="top">
    <h1 class="title">${portal.title}<span class="logo"></span><%--<select name="" class="edition"><option>中文</option><option>English</option></select>--%></h1>
</div>
<div class="main">
    <a ><img src="${portal.mobileMaterial1Url}" width="100%"></a>
    <div class="Edition hack">
        <div class="Edition_c fl">
            <a ><img src="${ctx}/images/portal/yyxz.png" width="100%" /></a>
        </div>
        <div class="Edition_c fr">
            <a ><img src="${ctx}/images/portal/dcrz.png" width="100%" /></a>
        </div>
    </div>
    <div class="Explain">
        <p>若已安装易韵APP，请手工打开，即可上网。易韵下载使用的是本场所的WIFI流量</p>
    </div>
</div>
<a  class="advertising"><img src="${ctx}/images/portal/wangwangba.jpg" width="100%"></a>
</body>
<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
    $(function () {
        var logoUrl = '${portal.logoUrl}';
        if (logoUrl != null && logoUrl != '') {
            $(".logo").css({"background": "url(${portal.logoUrl})", "background-repeat": "no-repeat","background-size":"2rem 2.2rem","width":"2rem","height":"2.2rem"});
        }

        $("img").each(function () {
            if ($(this).attr("src") == "") {
                $(this).attr("src", "${ctx}/images/portal/default_ad.jpg");
            }
        });
    });
</script>
</html>
