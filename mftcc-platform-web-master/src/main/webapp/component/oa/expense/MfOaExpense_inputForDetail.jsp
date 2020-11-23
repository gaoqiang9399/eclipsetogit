<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript">
		var expenseId = '${expenseId}';
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					updateOnContentResize : true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			$("#tablist").find("tr").find("td:eq(4)").hide();
			$("#tablist").find("tr").find("th:eq(4)").hide();
		});															
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">						
						<form method="post" theme="simple" id="OaExpenseInsert" name="operform" action="${webPath}/mfOaExpense/insertForApplyAjax">
							<dhcc:bootstarpTag property="formexpense0007" mode="query" />
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
					<%-- <dhcc:thirdButton value="保存" action="保存" typeclass="insert" ></dhcc:thirdButton> --%>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
				</div>
		</div>
	</body>
</html>