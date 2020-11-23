<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo = '${mfBusPact.cusNo}';
			var busModel='${mfBusPact.busModel}';
			var appId='${appId}';
			var pactId='${pactId}';
			var wkfAppId='${wkfAppId}';
			var taskId='${taskId}';
			var coborrNum = $.parseJSON('${coborrNum}');//共同借款人
			var ajaxData = '${ajaxData}';
    		ajaxData = JSON.parse(ajaxData);
			var relNo = '${mfBusPact.pactId}';// 要件业务编号
			var cmpdRateType='${mfBusPact.cmpFltRateShow}'; 
			var processId = '${mfBusPact.pactProcessId}';
			var calcIntstFlag='${calcIntstFlag}';//1-算头不算尾 2-首尾都计算
			var pactEndDateShowFlag='${pactEndDateShowFlag}';//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天
			var querySaveFlag = '${querySaveFlag}';
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="pactInsertForm" theme="simple" name="operform"  action="${webPath}/mfBusPact/updateAjaxForManage">
							<dhcc:bootstarpTag property="formpact0008" mode="query"/>
						</form>	
						<c:if test="${mfBusFollowPactList != null}">
							<div class="list-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>从合同文档</span>
									<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#followPact">
										<i class="i i-close-up"></i>
										<i class="i i-open-down"></i>
									</button>
								</div>
								<div id="followPact" class="content collapse in" aria-expanded="true">
									<dhcc:tableTag paginate="mfBusFollowPactList" property="tablemfBusFollowPactNo" head="true" />
								</div>
							</div>
						</c:if>
						<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
					</div>
				</div>
			</div>
			<div class="formRowCenter" style="z-index: 1000">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusPactInsertForManage.ajaxUpdate('#pactInsertForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactInsertForManage.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
	<script type="text/javascript">
		$(function() {
			if(busModel=='26'){
				$("#pactInsertForm").find("[name=payMethod]").val('2');//中茂要求默认受托支付
			}
			MfBusPactInsertForManage.init();
		});
	</script>
</html>
