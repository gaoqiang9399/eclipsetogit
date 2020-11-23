<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/wkf_bus_base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>尽职调查</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/app/js/riskInvestigate.js"></script>
	
	<script type="text/javascript">	
		var cusNo='${mfBusApply.cusNo}';
		var wkfAppId = '${mfBusApply.wkfAppId}';
		var appId = '${appId}';
		var kindNo = '${mfBusApply.kindNo}';
		var vouType=JSON.parse('${ajaxData}');
		var jsonStr = $.parseJSON('${jsonStr}');
		var cmpdRateType = '${cmpdRateType}';
		var processId = '${mfBusApply.applyProcessId}';
		var docOffline = "1";
		$(function() {
			// 是否隐藏 复利利率上浮字段
			if(cmpdRateType =="0"){//隐藏						
				$('input[name=cmpdFloat]').parent('.input-group').hide();
				$('input[name=cmpdFloat]').parents('.tdvalue').prev('td').find('label').hide();
			}
			riskInvestigate.appId = "${appId}"; 
			riskInvestigate.init();	
		});	
		
		//产品种类缺少验证期限
		function checkTerm(obj){
			
		}
		function checkByKindInfo(obj){
		}
		function getFincUse(obj){
			$("input[name=fincUse]").val(obj.fincUse);
			$("input[name=fincUseName]").val(obj.fincUseShow);
		}
		//保存方法
		function insertRiskInvestigateAjax(obj){
			riskInvestigate.insertRiskInvestigate(obj);
		}
	</script>

<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="riskInvestigate" theme="simple" name="operform" action="${webPath}/mfLoanApply/updateTuningReportAjax">
							<dhcc:bootstarpTag property="formapply0001" mode="query" />
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" onclick="insertRiskInvestigateAjax('#riskInvestigate');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
