<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>

<title > Upload File </title >
<%@ include file="/common/jsp/header.jsp"%>
</head >

<body >
<div class="right">
	<div class="right_c hack">
    	<div class="Query">
    	<div class="Query_t">
					<h2>上传文件</h2>
		</div>
  <form action="${ctx}/notice/recieveFile.do" id="form1" method="post"  enctype="multipart/form-data">  
       <input id="file" type="file" name="file"/>
       <input type="submit" class="PublicButton" value="上传"/>  
  </form>
 </div>
 </div>
 </div> 
</body > 
<script type="text/javascript">
$(function() {
	console.log("init");
	var filepath = "${filepath}";
	var filename = '${filename}';
	if(filepath != null && filepath !='' && filename != null && filename !=''){
		showMsg('上传成功!');
		$(window.opener.document).find("input[name='attachment'").val(filename);
		$(window.opener.document).find("input[name='attachPath'").val(filepath);
		window.close();
	}
	
	$("#form1").submit(function() {
		if ($("#file").val() == "") {
			showMsg("请选择文件");
			return false;
		} else {
			return true;
		}
		
	});	
	
})
</script>
</html>


