<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	<style type="text/css">
		.editbox{
			width: 100%;
			height: 45px;
		}
		.operating{
			width: 28px
		}
	</style>
</head>
<body class="overFlowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
<!-- 						<div class="form-title">预算申请</div> -->
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="OaBudgetMstInsert" theme="simple" name="operform" action="${webPath}/mfOaBudgetMst/insertAjax">
						<dhcc:bootstarpTag property="formbudget0002" mode="query" />
						<!-- 预算列表 -->
						<div class="feeInfo showOrHide">
							<div class="list-table margin_0">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								预算列表</span>
								<!-- <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#busfee-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button> -->
							</div>
							<%-- <span>费用标准</span> --%>
							<div class="content_table collapse in" id="budgetamt-div">
								<dhcc:tableTag property="tablebudget0002" paginate="mfOaBudgetDetailList" head="true"></dhcc:tableTag>
							</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<c:if test='${query eq ""}'>
				<dhcc:thirdButton value="保存" action="保存" onclick="doSubmit('#OaBudgetMstInsert');"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="关闭" action="关闭" onclick="backList();" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/budget/js/MfOaBudgetMst_Insert.js"></script>
<script type="text/javascript">
<%-- 	OaConsInsert.path = "${webPath}"; --%>
	$(document.body).height($(window).height());
	var query = '${query}';
	var budgetId = '${budgetId}';
	$(function() {
		MfOaBudgetInsert.init();
		
	});
	
	//审批提交
	function doSubmit(obj){
		MfOaBudgetInsert.doSubmit(obj);
		
	}
	
	function backList(){
		MfOaBudgetInsert.backList();
	}
	
	function sumBudgetAmt(){
		MfOaBudgetInsert.sumBudgetAmt();
	}
	
	//选择科目
	function selectComItem(obj){
		openComItemDialog('0', function(data){
				$(obj).val(data.showName);
		});
	}
	//选择操作员
	function selectUser(obj){
		selectUserDialog(function(data){
			$(obj).val(data.opNo + '/' + data.opName);
		});
	}
	
</script>
</html>
