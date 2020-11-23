<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/mfCollateralClass_select.js"></script>
		<script type="text/javascript" >
		var cusType = '${cusType}';
		var appId = '${appId}';
		var vouType = '${vouType}';
		var cusNo = '${cusNo}';
		var entrFlag = '${entrFlag}';
		var busModel = '${busModel}';
			$(function(){
				mfCollateralClassSelect.init();
			 });
		</script>
	</head>
	<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div col-xs-7 pull-right">
					<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="客户名称/客户编号">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>	

</html>
