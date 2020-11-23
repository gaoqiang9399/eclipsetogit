<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>账簿查询</title>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
<script type="text/javascript">

 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/pssReceiptBill/getListPage';
			break;
		case "2":
			window.location = webPath+'/pssPaymentBill/getListPage';
			break;
		case "3":
			window.location = webPath+'/pssCancelVerificationBill/getListPage';
			break;
		case "4":
			window.location = webPath+'/pssOtherRecBill/getListPage';
			break;
		case "5":
			window.location = webPath+'/pssOtherPayBill/getListPage';
			break;
		case "6":
			window.location = webPath+'/pssBuySaleExpBill/getListPage';
			break;
		}
	}
</script>
<body>
<div class="container">
	<div class="row info-block bg-white">
		<div class="row info-content">
			<dhcc:pmsTag pmsId="pss-receipt">
				<div class="col-md-3 info-box-div" id="pssReceiptBill">
					<div class="info-box" onclick="openReport(this, '1','收款单');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">收款单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-payment">
				<div class="col-md-3 info-box-div" id="pssPaymentBill">
					<div class="info-box" onclick="openReport(this, '2','付款单');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">付款单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-cancel-verification">
				<div class="col-md-3 info-box-div" id="pssCancelVerificationBill">
					<div class="info-box" onclick="openReport(this, '3','核销单');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">核销单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-other-rec">
				<div class="col-md-3 info-box-div" id="pssOtherRecBill">
					<div class="info-box" onclick="openReport(this, '4','其他收入单');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">其他收入单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-other-pay">
				<div class="col-md-3 info-box-div" id="pssOtherPayBill">
					<div class="info-box" onclick="openReport(this, '5','其他支出单');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">其他支出单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pss-buy-sale-exp">
				<div class="col-md-3 info-box-div" id="pssBuySaleExpBill">
					<div class="info-box" onclick="openReport(this, '6','采购销售费用清单');">
						<i class="info-box-icon i i-selbuss"></i>
						<div class="info-box-content">
							<span class="info-box-text">采购销售费用清单</span> 
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
		</div>
	</div>
</div>
</body>
</html>