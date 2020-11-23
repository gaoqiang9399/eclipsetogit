<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden multi-select-list">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary" onclick="MfCocoborr_CusList.inputCus();">新增</button>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="确认" action="确认" typeclass ="insertAjax" onclick="MfCocoborr_CusList.selectCusInfo();"></dhcc:thirdButton>
		<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfCocoborr_CusList.closeDialog();"></dhcc:thirdButton>
	</div>
</body>
	<script type="text/javascript" src="${webPath}/component/app/js/MfCocoborr_CusList.js?v=${cssJsVersion}"></script>
<script type="text/javascript" >
	var cusNo = '${cusNo}';
    var coborrNo = '${coborrNo}';
	$(function(){
        MfCocoborr_CusList.init();
	});
</script>
</html>
