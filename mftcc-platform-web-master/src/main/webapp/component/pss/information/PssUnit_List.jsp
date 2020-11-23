<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/information/js/PssUnit_List.js"></script>
		<script type="text/javascript" >
			$(function() {
				PssUnit_List.init();
			});
		</script>
		<style type="text/css">
		.table_content{
			width:35% !important;
			padding:10 10 0 15 !important;
		}
		.pss_detail_list_sp{
			height:40% !important;
		}
		</style>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="btn-div column">
						<div class="show-btn" style="float:left">
							<dhcc:pmsTag pmsId="pss-unit-insert">
								<button type="button" class="btn btn-primary" onclick="PssUnit_List.addSingleUnit()">新增</button>
							</dhcc:pmsTag>
						</div>
					</div>
					<div class="pss-bigform-table">
						<div id="contentsin" class="table_content pss_detail_list_sp">
						</div>
					</div>
					<div class="btn-div column">
						<div class="show-btn" style="float:left">
							<dhcc:pmsTag pmsId="pss-unit-insert">
								<button type="button" class="btn btn-primary" onclick="PssUnit_List.addMutiUnit()">新增</button>
							</dhcc:pmsTag>
						</div>
					</div>
					<div class="pss-bigform-table">
						<div id="contentmuti" class="table_content pss_detail_list_sp">
						</div>
					</div>
				</div>
			</div>
   		</div>
	</body>
</html>