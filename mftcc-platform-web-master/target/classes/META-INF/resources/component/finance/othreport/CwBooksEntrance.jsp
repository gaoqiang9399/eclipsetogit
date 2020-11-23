<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>账簿查询</title>
<%-- <link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css" /> --%>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
<!-- <script type="text/javascript" src="../paramset/js/CwParamEntrance.js"></script> -->
<script type="text/javascript">

 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/generalLedger/getListPage';
			break;
		case "2":
			window.location = webPath+'/detailAccount/getListPage';
			break;
		case "3":
			window.location = webPath+'/comBalance/getListPage';
			break;
		case "4":
			window.location = webPath+'/cwKemuHuiZong/getListPage';
			//CwParmEntrance.showTips(obj);
			break;
		case "5":
			window.location = webPath+'/capJournal/getListPage';
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
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '1','总账');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">总账</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '2','明细账');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">明细账</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '3','科目余额表');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">科目余额表</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '4','科目汇总表');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">科目汇总表</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '5','现金日记账表');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">现金日记账表</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '6','资金日报表');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">资金日报表</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '7','辅助核算明细账');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">辅助核算明细账</span> 
					</div>
				</div>
			</div>
			<div class="col-md-3 info-box-div" id="daikuanzonghe">
				<div class="info-box" onclick="openReport(this, '8','辅助核算余额表');;">
					<i class="info-box-icon i i-selbuss"></i>
					<div class="info-box-content">
						<span class="info-box-text">辅助核算余额表</span> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>