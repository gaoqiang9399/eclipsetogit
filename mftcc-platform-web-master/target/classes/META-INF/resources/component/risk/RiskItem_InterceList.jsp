<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=方案名称/方案描述"/>
				</div>
			</div>
		</div>
		<div class="row clearfix margin_bottom_60">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
					<dhcc:tableTag paginate="riskItemList" property="tableinterceptlistfornode" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="riskItemInterceptList.selectConfirm();"></dhcc:thirdButton>
		</div>
	</div>
</body>	
<script type="text/javascript" src="${webPath}/component/risk/js/riskItemInterceptList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function() {
		riskItemInterceptList.init();
	});
</script>

</html>
