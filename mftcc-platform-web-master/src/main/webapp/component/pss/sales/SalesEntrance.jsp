<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>销货入口主页面</title>
<%-- <link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css" /> --%>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
<!-- <script type="text/javascript" src="../paramset/js/CwParamEntrance.js"></script> -->
<script type="text/javascript">

 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/pssSaleOrder/getListPage';
			break;
		case "2":
			window.location = webPath+'/pssSaleBill/getListPage';
			break;
		case "3":
			window.location = webPath+'/pssSaleReturnBill/getListPage';
			break;
		case "4":
			window.location = webPath+'/pssSaleOriginalPic/getListPage';
			//CwParmEntrance.showTips(obj);
			break;
		case "5":
			window.location = webPath+'/pssSaleInvoice/getListPage';
			break;		
		}
	}
</script>
<body>
<div class="container">
	<div class="row info-block bg-white">
<!-- 		<div class="report-title">账簿查询</div> -->
		<div class="row info-content">
			<dhcc:pmsTag pmsId="pss-saleOrder-menu">
				<div class="col-md-3 info-box-div" id="xiaohuodingdan">
					<div class="info-box" onclick="openReport(this, '1','销货订单');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">销货订单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-saleBill-menu">
				<div class="col-md-3 info-box-div" id="xiaohuodan">
					<div class="info-box" onclick="openReport(this, '2','销货单');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">销货单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-saleReturnBill-menu">
				<div class="col-md-3 info-box-div" id="xiaohuotuihuodan">
					<div class="info-box" onclick="openReport(this, '3','销货退货单');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">销货退货单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- <div class="col-md-3 info-box-div" id="yuanshidanju">
				<div class="info-box" onclick="openReport(this, '4','原始单据');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">原始单据</span> 
					</div>
				</div>
			</div>-->
			<dhcc:pmsTag pmsId="pss-saleInvoice-menu">
				<div class="col-md-3 info-box-div" id="xiaoshoukaipiao">
					<div class="info-box" onclick="openReport(this, '5','销售开票');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">销售开票</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag> 
		</div>
	</div>
</div>
</body>
</html>