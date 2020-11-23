<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<style type="text/css">
			.multi-select-list .footer_loader{
				display:none;
			}
		</style>
	</head>
<body  class="overflowHidden multi-select-list">
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

				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="MfKindNodeInterceptList.selectConfirm();"></dhcc:thirdButton>
		</div>
	</div>
</body>	
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindNodeInterceptList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var nodeNo='${nodeNo}';
	var kindNo='${kindNo}';
	$(function() {
		$("#content").height($("body").height()-110);
		MfKindNodeInterceptList.init();
	});
</script>

</html>
