<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="MfFundPlan_List.applyInsert(this);">新增</button>
						<dhcc:pmsTag pmsId="financial-fundplan-btn">
							<button type="button" class="btn btn-primary" onclick="MfFundPlan_List.createFundPlanTable(this);">资金计划表</button>
						</dhcc:pmsTag>
					</div>
						<div class="col-md-8 text-center">
							<span class="top-title">资金计划表</span>
						</div>
					<div class="col-md-2">
					</div>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=登记人/项目"/>
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
<script type="text/javascript" src="${webPath}/component/tcph/js/MfFundPlan_List.js"></script>
<script type="text/javascript">
	var regNo = '${regNo}';
	$(function() {
		MfFundPlan_List.init();
		filter_dic =[
				{"optCode":"date","optName":"日期","dicType":"date"},
				{"optCode":"fundPlanType","optName":"类型","parm":[{"optName":"回款","optCode":"1"},{"optName":"出款","optCode":"2"},{"optName":"还款","optCode":"3"}],"dicType":"y_n"},
				{"optCode":"fundPlanSts","optName":"状态","parm":[{"optName":"正常","optCode":"1"},{"optName":"坏账","optCode":"2"},{"optName":"未补息","optCode":"3"}],"dicType":"y_n"},
				{"optCode":"fundPlanAmt","optName":"金额","dicType":"num"},
				{"optCode":"unit","optName":"单位","parm":${jsonArrayByKeyName},"dicType":"y_n"}
			];
	});
</script>
</html>
