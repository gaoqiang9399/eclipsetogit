<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String taskNo = (String)request.getParameter("taskNo");
	String taskNoStr = (String)request.getParameter("taskNoStr");
	if(taskNoStr == null || "".equals(taskNoStr)){
		taskNoStr = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	var ajaxData = '${ajaxData}';
	ajaxData = JSON.parse(ajaxData);
	
	$(function(){
		_bindClose();
		
		$("input[name=mgrNo]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//多选选
				items:ajaxData.sysUser,
				changeCallback : function (obj, elem) {		
					$("input[name=mgrName]").val(obj.data("text"));
				}
			});
	});
	var _bindClose = function () {//绑定关闭事件
		$(".cancel").bind("click", function(event){
			$(top.window.document).find("#showDialog").remove();
		});
	};
</script>
<title>列表</title>
<style>
.table_content .ls_list tr.selected{
		background-color:#F7F7F7;
}
</style>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
				 	<div class="form-tips">说明：请选择客户经理。</div>
						<form method="post" theme="simple" name="operform" id="operform" action="">
							<dhcc:bootstarpTag property="formrec0007" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="确定" action="确定" onclick="showMgrtDialog()"></dhcc:thirdButton>
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
<script type="text/javascript" src="${webPath}/component/rec/js/recallBase_send.js"></script>
<script type="text/javascript">	
	function showMgrtDialog(){
		var str1 = '<%=taskNo%>';
		var str2 = '<%=taskNoStr%>';
		SelectedUser(str1,str2);
	}
</script>
</html>
