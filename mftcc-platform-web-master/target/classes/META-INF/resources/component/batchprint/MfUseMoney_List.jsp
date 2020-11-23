<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css"/>
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/batchprint/js/MfUseMoney.js"></script>
<script type="text/javascript">
	var webPath = '${webPath}';
	// 接收传参等
	$(function() {
		MfUseMoney.initTableList();
	});
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="MfUseMoney.refresh();">刷新</button>
						&nbsp;&nbsp;
						<button type="button" class="btn btn-default" onclick="MfUseMoney.batchPrint();">打印</button>
						</div>
					</div>
				</div>
		</div>	
		<div class="row clearfix">
			<div class="col-md-12">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
</body>
</html>
