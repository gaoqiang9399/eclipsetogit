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
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				jQuery.ajax({
					url : url,
					data : {ajaxData : dataParam,appId:appId,fincId:fincId},
					success : function(data) {
						if (data.flag == "success") {
							top.inStockBatch=true;
							top.isExamineInOutStock=data.keepInfo.isExamineInOutStock;
							window.top.alert(data.msg, 3);
							myclose_click();
						} else if (data.flag == "error") {
							window.top.alert(data.msg, 0);
						}
					},error : function(data) {
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		}
		function changeKeepType(){
			var keepType = $("input[name=keepType]").val();
			var obj = $("input[name=approvePartName]")
			if(keepType=="1"){//获取入库流程第一节点审批人
				WkfApprove.getNextUserByNodePage(obj,'1530951497100');
			}else{//获取出库流程第一节点审批人
				WkfApprove.getNextUserByNodePage(obj,'1530959424660');
			}
		}
	</script>
	<body class=" overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="instockInsert" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/instockBatchAjax">
						<dhcc:bootstarpTag property="formbatchkeepinfo0007" mode="query"/>
					</form>	
				</div>
			</div>	
		</div>
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="insertChkInfo('#instockInsert');"></dhcc:thirdButton>
   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>
   		</div>
	</body>
	<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
</html>
