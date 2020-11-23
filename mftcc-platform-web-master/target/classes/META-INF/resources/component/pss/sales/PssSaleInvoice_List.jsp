<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/pss/stock/css/PssStock_common.css">
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="pss-saleinvoice-init">
						<button type="button" class="btn btn-primary" onclick="PssSaleInvoice_List.saleInvoiceInsert();">新增</button>
					</dhcc:pmsTag>

					<dhcc:pmsTag pmsId="pss-saleinvoice-batchdelete">
						<button type="button" class="btn btn-default" onclick="PssSaleInvoice_List.batchDeleteInvoiceInfo();">删除</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=单据编号/客户名称/发票号/发票抬头" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/pss/sales/js/PssSaleInvoice_List.js"></script>
	<script type="text/javascript">
	$(function(){
		PssSaleInvoice_List.init();
	});
	/*我的筛选加载的json*/
	filter_dic = [{
			"optCode" : "invoiceDate",
			"optName" : "开票日期",
			"dicType" : "date"
		}, {
			"optCode" : "billDate",
			"optName" : "单据日期",
			"dicType" : "date"
		} 
	];
	</script>
</html>