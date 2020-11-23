<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var expenseId = '${expenseId}';
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false //单选
				});				
			});
			 
			 //审批提交
			function doSubmit(obj){
 				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
//				var opinionType = $("select[name=opinionType]").val();
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfOaExpense/commitProcessAjax?expenseId="+ expenseId+"&opinionType="+opinionType, obj,'sp');
				}
			}
		</script>
</head>
 
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">						
						<form method="post" theme="simple" id="OaExpenseAppro" name="operform" action="${webPath}/mfOaExpense/insertForApplyAjax">
							<dhcc:bootstarpTag property="formexpense0006" mode="query" />
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
					<dhcc:thirdButton value="保存" action="保存" typeclass="insert" onclick="doSubmit('#OaExpenseAppro');"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
				</div>
		</div>
	</body>
</html>