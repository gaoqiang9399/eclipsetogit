<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>采购单据</title>
<%-- <link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css" /> --%>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
<!-- <script type="text/javascript" src="../paramset/js/CwParamEntrance.js"></script> -->
<script type="text/javascript">

 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/pssBuyOrder/getListPage';
			break;
		case "2":
			window.location = webPath+'/pssBuyBill/getListPage';
			break;
		case "3":
			window.location = webPath+'/pssBuyReturnBill/getListPage';
			break;
		case "4":
			window.location = webPath+'/pssSaleOrderDetail/getToBuyOrderListPage';
			//CwParmEntrance.showTips(obj);
			break;
		case "5":
			window.location = webPath+'/pssBuyOrderDetail/getSmartReplenishmentPage';
			break;
		case "6":
			window.location = webPath+'/capDayReport/getListPage';
			break;
		case "7":
			window.location = webPath+'/assistAccountDetail/getListPage';
			break;
		case "8":
			window.location = webPath+'/assistAccountDetail/getBlanceListPage';//获取辅助核算余额表数据
			break;
		}
	}
</script>
<body>
<div class="container">
	<div class="row info-block bg-white">
<!-- 		<div class="report-title">账簿查询</div> -->
		<div class="row info-content">
			<dhcc:pmsTag pmsId="pss-buyOrder-menu">
				<div class="col-md-3 info-box-div" id="gouhuodingdan">
					<div class="info-box" onclick="openReport(this, '1','购货订单');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">购货订单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-buyBill-menu">
				<div class="col-md-3 info-box-div" id="gouhuodan">
					<div class="info-box" onclick="openReport(this, '2','购货单');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">购货单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-buyReturnBill-menu">
				<div class="col-md-3 info-box-div" id="gouhuotuihuodan">
					<div class="info-box" onclick="openReport(this, '3','购货退货单');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">购货退货单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-saleToBuyOrder-menu">
				<div class="col-md-3 info-box-div" id="yixiaodinggou">
					<div class="info-box" onclick="openReport(this, '4','以销定购');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">以销定购</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-smartReplenishment-menu">
				<div class="col-md-3 info-box-div" id="zhinengbuhuo">
					<div class="info-box" onclick="openReport(this, '5','智能补货');;">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">智能补货</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
		</div>
	</div>
</div>
</body>
</html>