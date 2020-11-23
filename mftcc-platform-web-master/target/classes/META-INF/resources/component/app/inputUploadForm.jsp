<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript"
	src='${webPath}/component/app/js/inputUploadForm.js'></script>
	<script type="text/javascript">	
		var cusNo='${cusNo}';
		var scNo ='${scNo}';//客户要件场景
		var appId = '${appId}';
		var kindNo = '${kindNo}';
		var kindName = '${dataMap.kindName}';
		var nodeName = '${dataMap.nodeName}';
		var fincUse = $.parseJSON('${jsonStr}');
		var maxAmt = '${dataMap.maxAmt}';
		var minAmt = '${dataMap.minAmt}';
		var minTerm = '${dataMap.minTerm}';
		var maxTerm = '${dataMap.maxTerm}';
		var termType = '${dataMap.termType}';
		var creditAmt = null;
		var kindCreditAmt = null;	
		var creditflag = '${creditflag}';
		var kindCreditflag = '${kindCreditflag}';
		if(creditflag=="success"){
			creditAmt = '${creditAmt}';
			if(kindCreditflag=="success"){
				kindCreditAmt = '${kindCreditAmt}';
			}
		}
		
		$(function(){
			inputUploadForm.init();
		});
		function uploadcommit(obj, temporaryStorage){
			inputUploadForm.douploadcommit(obj, temporaryStorage);
		}
		function getCusMngNameDialog(userInfo){
			$("input[name=cusMngName]").val(userInfo.opName);
			$("input[name=cusMngNo]").val(userInfo.opNo);
		};
		function checkByKindInfo(obj){
			inputUploadForm.checkByKindInfo(obj);
		}
		function checkTerm(obj){
			inputUploadForm.checkTerm(obj);
		}
		
		//格式化金额
		function fmoney(s, n) { 
			n = n > 0 && n <= 20 ? n : 2; 
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
			var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
			t = ""; 
			for (i = 0; i < l.length; i++) { 
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
			} 
			return t.split("").reverse().join("") + "." + r; 
		};
	</script>

<body class="overflowHidden bg-white">
	<div class="container form-container" id="normal">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="updateForApplyForm" method="post" theme="simple" name="operform" action="${webPath}/mfLoanApply/updateApplyInfo">
					    <dhcc:bootstarpTag property="formapply0001" mode="query" />
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>

		<c:if test="${SAVE_ONLY_3 == '0'}">
			<div class="formRowCenter">
				<dhcc:thirdButton value="提交" action="提交" onclick="uploadcommit('#updateForApplyForm', '0');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</c:if>
		<c:if test="${SAVE_ONLY_3 == '1'}">
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="uploadcommit('#updateForApplyForm', '1')"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
				<dhcc:thirdButton value="提交" action="提交" onclick="uploadcommit('#updateForApplyForm', '0');"></dhcc:thirdButton>
			</div>
		</c:if>
<!-- 		<div style="display: none;" id="fincUse-div"></div> -->
	</div>
</body>
</html>