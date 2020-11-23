<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>尽职调查</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/app/js/confirmTuningReportForApp.js"></script>
	
	<script type="text/javascript">	
		var cusNo='${mfBusApply.cusNo}';
		var appId = '${appId}';
		var kindNo = '${mfBusApply.kindNo}';
		var vouType=JSON.parse('${ajaxData}');
		var jsonStr = $.parseJSON('${jsonStr}');
		var cmpdRateType = '${cmpdRateType}';
		var fincId = '${fincId}';// 借据号
		var channelType = '${channelType}';//渠道类型
		$(function() {
			// 是否隐藏 复利利率上浮字段
			if(cmpdRateType =="0"){//隐藏						
				$('input[name=cmpdFloat]').parent('.input-group').hide();
				$('input[name=cmpdFloat]').parents('.tdvalue').prev('td').find('label').hide();
			}
			confirmTuningReport.appId = "${appId}"; 
			confirmTuningReport.channelType = "${channelType}";
			confirmTuningReport.init();	
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
		
		function insertTuningReportAjax(obj){
			confirmTuningReport.insertTuningReport(obj);
		}
	</script>

<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form  method="post" id="confirmTuningReportForApp" theme="simple" name="operform" action="/mfLoanApply/updateTuningReportForAppAjax">
							<dhcc:bootstarpTag property="formcallService" mode="query" />
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="调用资方" action="调用资方" typeclass ="insertAjax" onclick="insertTuningReportAjax('#confirmTuningReportForApp');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
