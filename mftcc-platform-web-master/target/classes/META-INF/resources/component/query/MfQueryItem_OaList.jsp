<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link id="MfQueryEntrance" rel="stylesheet" href="${webPath}/component/query/css/MfQueryEntrance${skinSuffix}.css?v=${cssJsVersion}" />
		<style type="text/css">
		.info-box-div{
			 position:relative;
			 padding: 0px 30px;
			 display: inline-block;
		}
		.info-box-icon{
			width:48px;
		}
		</style>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div id="attentionDiv" class="row margin_top_20" style="padding:0px 30px;">
				<div class="info-title">已选</div>
			</div>
			<div id="unattentionDiv" class="row margin_top_20" style="padding:0px 30px;">
				<div class="info-title">未选</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="QueryItemOaList.selectConfirm();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/query/js/MfQueryItem_OaList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var mapObj = '${ajaxData}';
    mapObj = JSON.parse(mapObj);
    $(function(){
    	QueryItemOaList.init();
    });
   </script>
</html>
