<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详情</title>
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
		<script type="text/javascript">													
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">						
						<form method="post" theme="simple" id="OaExpenseInsert" name="operform" action="${webPath}/mfOaExpense/insertForApplyAjax">
							<dhcc:bootstarpTag property="formexpense0005" mode="query" />
							<div class="list-table margin_0">
								<div class="title">
									<span>
									<i class="i i-xing blockDian"></i>
									报销科目明细</span>
									</div>
									<div class="content_table " id="expenseList-div">
										<dhcc:tableTag property="tableexpenseList0001" paginate="mfOaExpenseListList" head="true"></dhcc:tableTag>
									</div>							
							</div>
						</form>
					</div>					
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass="insert" ></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/oa/expense/js/MfOaExpense_inputForApply.js"></script>
<script type="text/javascript">
	MfOaExpenseInputForApply.path ='${webPath}';
		$(function() {
			MfOaExpenseInputForApply.init();
		});	
</script>
</html>