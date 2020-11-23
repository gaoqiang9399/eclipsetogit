<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript">
			var id = '${id}';
			var webPath = '${webPath}';
			
		</script>
		<!--引入这个js一定要在该jsp的js之后，因为这个js需要使用这里定义的js变量  -->
		<script type="text/javascript" src="${webPath}/component/collateral/js/MfCollateralFormConfig_List.js"></script>
		
	</head>
	<body style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div color_theme">
						<%-- <ol class="breadcrumb pull-left padding_left_0">
							<li><a href="${webPath}/mfCollateralClass/getMfCollateralConfig">表单设置</a></li>
							<li class="active">押品动态表单</li>
						</ol> --%>
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
	</body>	
</html>
