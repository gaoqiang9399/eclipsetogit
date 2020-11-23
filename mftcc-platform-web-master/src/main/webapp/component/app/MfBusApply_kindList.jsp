<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title><%=request.getAttribute("title") %></title>
	</head>
<%-- 	<script type="text/javascript" src="<%=webPath %>/creditapp/js/WF.js"></script> --%>
	<script type="text/javascript">
	window.name = "curWindow";
			function cancelClick() {
				window.close();
			}
			
			function enterClick(lk) 
			{
				var parm=lk.split("?")[1];
				var parmArray=parm.split("&");
				var selectedValue = "";
				var userId=parmArray[0].split("=")[1];
				var userName=parmArray[0].split("=")[1];
				if( userId == "" ) {
					alert("请选择用户！");
					return;
				}
				selectedValue=userId+":"+userName;
				window.returnValue = selectedValue;
				window.close();
				return;
			}
			function enterClickForMulti() 
			{
				var elements=document.getElementsByName("wkfUserName");
				var selectedValue = ""
				var userId="";
				var userName="";
				var selectedUserIdValue=""
				var selectedUserNameValue=""
				for(var i=0; i<elements.length; i++) 
				{
					if(elements[i].checked)
					{
						var elementValue=elements[i].value;
						userId=elementValue.split("&")[0];
						userId=userId.split("=")[1];
						selectedUserIdValue=selectedUserIdValue+userId+",";
						userName=elementValue.split("&")[1];
						userName=userName.split("=")[1];
						selectedUserNameValue=selectedUserNameValue+userName+",";
					}
				}
				if( selectedUserIdValue == "" ) {
					alert("请选择用户！");
					return;
				}
				selectedUserIdValue=selectedUserIdValue.substring(0,selectedUserIdValue.length-1);
				selectedUserNameValue=selectedUserNameValue.substring(0,selectedUserNameValue.length-1);
				selectedValue=selectedUserIdValue+":"+selectedUserNameValue;
				window.returnValue = selectedValue;
				window.close();
				return;
			}
		</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="/MfBusApplyAction_getKindList.action" target="curWindow">
		<input name="kindNo"  id="kindNo" type="hidden" value=${kindNo} />
		<input name="kindName"  id="kindName" type="hidden" value=${kindName} />
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle"></div>
						</div>
						<dhcc:tableTag paginate="mfSysKindList" property="tablekind0001" head="true" />
						<!--
						多选
						<dhcc:tableTag paginate="wkfApprovalUserMapList" property="tablewkf0008" head="true" />  
						-->
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>