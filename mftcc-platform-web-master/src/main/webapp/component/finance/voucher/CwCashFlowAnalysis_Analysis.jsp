<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
		<title>凭证详情</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/voucher.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/icon.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/iconColor.css" />
		<style type="text/css">
			* {
				-webkit-box-sizing: content-box;
				-moz-box-sizing: content-box;
				box-sizing: content-box;
			}
		</style>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/voucher/js/voucher.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
	</head>
	<body class="page-voucher">
		<div id="pzEdit"></div>
</body>
<script type="text/javascript">
var voucherNo = '${dataMap.voucherNo}';
$(function(){
	//自定义滚动条
	$(".page-voucher").mCustomScrollbar({
		advanced:{
			theme:"minimal-dark",
			updateOnContentResize:true
		}
	});
	$('#pzEdit').voucher({
		id:'vchEdit',
		pzno:voucherNo,
		for_which: 'view',
		cb:function(){
			$('#toolBottom .fr').append('<a class="ui-btn ui-btn-sp" onclick="analyseThis()">现金流量</a><a class="ui-btn m0" id="vchAudit" onclick="closeDiolog()">取消</a>');
		}
	});
})
function analyseThis(){
	createShowDialog("${webPath}/cwVoucherMst/toCashFlowAnalysisSave?voucherNo="+voucherNo,"现金流量分析",'70','55', closeDiolog);
	$(".fa").hide();
	$(".close").hide();
}
//关闭弹出层
function closeDiolog(){
	myclose_click();
}
</script>
</html>