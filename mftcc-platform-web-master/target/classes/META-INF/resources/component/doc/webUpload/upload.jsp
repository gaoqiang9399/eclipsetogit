<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath %>">
<title></title>
<script type="text/javascript" src="${webPath}component/doc/webUpload/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="${webPath}component/doc/webUpload/webuploader/webuploader.js"></script>
<link rel="stylesheet" href="${webPath}component/doc/webUpload/webuploader/webuploader.css" />
<script type="text/javascript" src="${webPath}UIplug/zTree/jquery.ztree.all.js"></script>
<link rel="stylesheet" href="${webPath}UIplug/zTree/metroStyle/metroStyle.css" />
<%-- <link rel="stylesheet"
	href="${webPath}UIplug/Font-Awesome/css/font-awesome.css" /> --%>
	<%--bootstrap框架--%>
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<%--滚动条js 和鼠标滚动事件js--%>
<link rel="stylesheet" href="${webPath}UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
	<!-- 弹层关闭的方法 -->
<script type="text/javascript" src='${webPath}/component/include/closePopUpBox.js'> </script>
<script type="text/javascript" src="${webPath}UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${webPath}UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${webPath}component/doc/webUpload/js/upload.js"></script>
<link rel="stylesheet" href="${webPath}component/doc/webUpload/css/upload.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}UIplug/ViewImg/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}UIplug/ViewImg/viewer.css" type="text/css"/>
<link id="BS-factor" rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor${skinSuffix}.css?v=${cssJsVersion}"/>
<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
<link id="common" rel="stylesheet" href="${webPath}/themes/factor/css/common${skinSuffix}.css?v=${cssJsVersion}" />
<script type="text/javascript">
			var zTreeNodes = eval('('+'<%=request.getAttribute("zTreeNodes")%>' + ')');
			query = "<%=request.getAttribute("query")%>";
			var appId = "<%=request.getAttribute("appId")%>";
			var wkfAppId = "<%=request.getAttribute("wkfAppId")%>";
			var taskId = "<%=request.getAttribute("taskId")%>";
			var scNo = "<%=request.getAttribute("scNo")%>";
	$(function() {
// 		$("body").height($(window).height());
// 		$("body").mCustomScrollbar({
// 			theme:"minimal-dark"
// 		});
	if(zTreeNodes.length == 0){
		$("#te").css('display','');
	}
	});
	
	
	function toNextPoint(){
		if(!valiDocIsUp(appId)){
				return false;
		} 
		jQuery.ajax({
			url:webPath+'/docManage/toNextPointAjax',
			data:{wkfAppId:wkfAppId,taskId:taskId,appId:appId},
			type:"post",
			dataType:"json",
			success:function(data){
			top.investigation=true;
				if(data.flag == "success"){
					 top.flag=true;
					 myclose_click();
				}
			}
		});
	
	}
	//验证文档是否上传
	function valiDocIsUp(relNo){
		var flag = true;
		$.ajax({
			type: "post",
			dataType: 'json',
			url:webPath+"/docBizManage/valiWkfDocIsUp",
			data:{relNo:relNo,scNo:scNo},
			async: false,
			success: function(data) {
				if(!data.flag){
					window.top.alert(data.msg,0);
				}
				flag = data.flag;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
		return flag;
	}
</script>
</head>

<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div id="te" style="display:none;text-align:center;">未匹配到文档模型</div>
		  	<div class="clear"></div>
			<div class="upload_body">
				<ul class="ztree" id="uploadTree"></ul>
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="toNextPoint();"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>
	<script type="text/javascript" src="${webPath}UIplug/notie/notie.js"></script>

</body>

</html>