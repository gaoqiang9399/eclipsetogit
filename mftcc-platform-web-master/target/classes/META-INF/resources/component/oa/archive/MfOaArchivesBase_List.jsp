<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<style type="text/css">
#content {
	    height: 89% !important;
}
</style>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="OaArchivesList.applyInsert(this);">新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">人事档案</span>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=员工姓名/证件号"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
		<div style="height: 20px;"></div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/oa/debt/js/MfOaDebtList.js"></script>
<script type="text/javascript" 	src="${webPath}/component/oa/archive/js/MfOaDrchivesBase_list.js"></script>
<script type="text/javascript">
OaDebtList.path = "${webPath}";
	$(function() {
		OaDebtList.init();
		OaArchivesList.init();
	});
</script>
</html>


