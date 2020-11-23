<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
	</head>
	<script type="text/javascript">
	var appId = '${appId}';
	var fincId = '${fincId}';
	var authCycle = '${authCycle}';
	var processId = '${processId}';
		$(function() {
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			var groupNameLabel = $("input[name=groupName]").parents(".rows")
					.find(".form-label");
			var groupNameLabelText = $(groupNameLabel).text();
			$(groupNameLabel).empty().append(
					"<span class='required'>*</span>" + groupNameLabelText);
			$("input[name=groupName]").attr("mustinput", "1");
		});
		
		function getLegalIdType() {
			var legalIdType = $("select[name =legalIdType]").val();
			if (legalIdType == "0") {
				$("input[name=legalIdNum]").attr("alt", "idnum");
			} else {
				$("input[name=legalIdNum]").attr("alt", "tmp");
			}
			$("input[name=legalIdNum]").val("");
		};

		function insertChkInfo(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
			    if(authCycle == "1"){
				    alert(top.getMessage("CONFIRM_OPERATION","出库并完结合同"),2,function(){
						isAuthCycle(obj);
					});
			    }else{
			    	isAuthCycle(obj);
			    }
				
			}
		}
		function isAuthCycle(obj){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url : url,
				data : {ajaxData : dataParam,appId:appId,fincId:fincId},
				success : function(data) {
					if (data.flag == "success") {
						top.outStockBatch=true;
						top.isExamineInOutStock=data.keepInfo.isExamineInOutStock;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else if (data.flag == "error") {
						window.top.alert(data.msg, 0);
					}
				},error : function(data) {
					alert("操作失败！", 0);
				}
			});
		}
	</script>
	<body class=" overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="outStockInsert" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/outstockBatchAjax">
						<dhcc:bootstarpTag property="formbatchkeepout0008" mode="query"/>
					</form>		
				</div>
			</div>	
		</div>
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="insertChkInfo('#outStockInsert');"></dhcc:thirdButton>
	   		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>
   		</div>
	</body>
	
<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
</html>
