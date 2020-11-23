<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/risk/audit/js/MfBusRiskAudit_accurateList.js"></script>
	<script type="text/javascript" >
        var busModel = '${busModel}';
        $(function(){
            MfBusRiskAudit_accurateList.initForNotice();
        });
	</script>
	<style type="text/css">
		.change-td-color-0{
			color: #ff0000 !important;
		}
		.ui-dialog-title{
			font-size: medium;/*修改指派弹框的标题字体样式*/
		}
	</style>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="MfBusRiskAudit_accurateList.auditTaskBatch();">批量通知</button>
						<button type="button" class="btn btn-primary" onclick="MfBusRiskAudit_accurateList.exportExcel();">导出</button>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">风控稽核列表</span>
				</div>
			</div>


			<div class="search-div" id="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">

</script>
</html>