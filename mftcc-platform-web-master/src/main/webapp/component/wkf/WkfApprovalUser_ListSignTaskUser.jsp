<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title><%=request.getAttribute("title") %></title>
	</head>
	<script type="text/javascript">
			function commit() {
				var elements=document.getElementsByName("nextUser");
				var selectedValue = "";
				var userId="";
				var userName="";
				var selectedUserIdValue="";
				var selectedUserNameValue="";
				for(var i=0; i<elements.length; i++) {
					if(elements[i].checked){
						var elementValue=elements[i].value;
						var elementlabel=elements[i].text;
						selectedUserIdValue += elementValue + ",";
					}
				}
				if( selectedUserIdValue == "" ) {
					alert("请选择用户！",4);
					return;
				}
				selectedUserIdValue=selectedUserIdValue.substring(0,selectedUserIdValue.length-1);
				var nextUser = new Object();
				nextUser.selectedValue = selectedUserIdValue;
				parent.dialog.get('nextUserDialog').close(nextUser).remove();
			}
			$(function(){
				var $parentObj = $("input[type=checkbox][name=nextUser]").parent();
				var checkboxHtml="";
				var userName = $parentObj.text().split("\n");
				$("input[type=checkbox][name=nextUser]").each(function(index){
					checkboxHtml+=$(this).prop("outerHTML")+userName[index]+'('+$(this).val()+')';
				});
				$parentObj.html(checkboxHtml);
			});
		</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="signTaskUserForm" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formwkf0041" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="确认" action="确认" onclick="commit();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>