<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<style>
.from_w{ width:80%; margin:0 auto; font-size:14px; line-heiht:18px;}
.from_w .bartitle{ text-align:left; border-bottom:1px solid #eee; font-size:16px;}
.from_w .tdvalue input{}
.from_w td{ padding:25px 0;}
.from_btn{text-align: center;}
.button3{background:#0090c4; color:#fff; border:none; margin:0 auto; width:120px; border-radius:3px; line-height:24px; font-size:14px;}
.button3:hover{background:#26b3e4;}
.checkboxShow{margin-left:50px;width: 100px;float: left;}
.tdlable{vertical-align: middle}
</style>
<!DOCTYPE html>
<html>
	<head>
		<title><%=request.getAttribute("title") %></title>
	</head>
	<script type="text/javascript">
			function cancelClick() {
				window.close();
			}
			
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
					alert("请选择用户！");
					return;
				}
				selectedUserIdValue=selectedUserIdValue.substring(0,selectedUserIdValue.length-1);
				//selectedUserNameValue=selectedUserNameValue.substring(0,selectedUserNameValue.length-1);
				//selectedValue=selectedUserIdValue+":"+selectedUserNameValue;
				window.returnValue = selectedUserIdValue;
				window.close();
				return;
			}
			$(function(){
				var $parentObj = $("input[type=checkbox][name=nextUser]").parent();
				var checkboxHtml="";
				$("input[type=checkbox][name=nextUser]").each(function(){
				checkboxHtml+='<div class="checkboxShow">'+$(this).prop("outerHTML")+$(this).val()+'</div>';
				});
				$parentObj.html(checkboxHtml);
			});
		</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wkfApprovalUser/findApprovalUserByPage" target="curWindow">
		<input name="wkfRoleNo"  id="wkfRoleNo" type="hidden" value=${wkfRoleNo} />
		<input name="wkfBrNo"  id="wkfBrNo" type="hidden" value=${wkfBrNo} />
		<input name="wkfUserName"  id="wkfUserName" type="hidden" value=${wkfUserName} />
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="cms_form"
					action="${webPath}/iqpProjectCommonality/MeetCommit">
					<dhcc:formTag property="formwkf0041" mode="query" />
					<div class="from_btn">
					<c:if test="query!='query'">
						 <dhcc:button typeclass="button3"  value="确认" action="SignTaskUser001"  onclick="commit()" ></dhcc:button>
					 </c:if>
					</div>
				</form>
				</div>
			</div>
			</div>
		</div>
	</form>
</body>
</html>