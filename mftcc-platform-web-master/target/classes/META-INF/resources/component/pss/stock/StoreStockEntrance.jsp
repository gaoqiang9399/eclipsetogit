<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>仓库</title>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
<script type="text/javascript">

 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/pssAlloTransBill/getListPage.action';
			break;
		case "2":
			window.location = webPath+'/pssCheckStockBill/getListPage';
			break;
		case "3":
			window.location = webPath+'/pssOtherStockInBill/getListPage';
			break;
		case "4":
			window.location = webPath+'/pssOtherStockOutBill/getListPage';
			//CwParmEntrance.showTips(obj);
			break;
		case "5":
			window.location = webPath+'/pssCostAdjustBill/getListPage';
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
		<div class="row info-content">
		<dhcc:pmsTag pmsId="pss-allo-trans">
			<div class="col-md-3 info-box-div" id="pssallotransbill">
				<div class="info-box" onclick="openReport(this, '1','调拨单');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">调拨单</span> 
					</div>
				</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="pss-check-stock">
			<div class="col-md-3 info-box-div" id="psscheckstockbill">
				<div class="info-box" onclick="openReport(this, '2','盘点');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">盘点</span> 
					</div>
				</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="pss-other-stock-in">
			<div class="col-md-3 info-box-div" id="pssotherstockinbill">
				<div class="info-box" onclick="openReport(this, '3','其他入库单');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">其他入库单</span> 
					</div>
				</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="pss-other-stock-out">
			<div class="col-md-3 info-box-div" id="pssotherstockoutbill">
				<div class="info-box" onclick="openReport(this, '4','其他出库');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">其他出库单</span> 
					</div>
				</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="pss-cost-adjust">
			<div class="col-md-3 info-box-div" id="psscostadjustbill">
				<div class="info-box" onclick="openReport(this, '5','成本调整');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">成本调整单</span> 
					</div>
				</div>
			</div>
		</dhcc:pmsTag>
		</div>
	</div>
</div>
</body>
</html>