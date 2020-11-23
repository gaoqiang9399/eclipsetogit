<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title><%=request.getAttribute("title") %></title>
		<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf.css"/>
	</head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<script type="text/javascript">
	var nextWkfRoleNo = '${nextWkfRoleNo}';
	$(function(){
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
		$("#tablist").show();
	});
		window.name = "curWindow";
		function cancelClick() {
			window.close();
		}
			
		function enterClick(lk){
			var parm=lk.split("?")[1];
			var parmArray=parm.split("&");
			var selectedValue = "";
			var userId=parmArray[0].split("=")[1];
			var userName=parmArray[1].split("=")[1];
			if( userId == "" ) {
				alert("请选择用户！");
				return;
			}
			selectedValue=userId+":"+userName;
			window.returnValue = selectedValue;
			var nextUser = new Object();
			nextUser.selectedValue = selectedValue;
			nextUser.displayName = userName;
			nextUser.wkfUserName = userId;
			parent.dialog.get('nextUserDialog').close(nextUser).remove();
		};
			
		function enterClickForMulti(){
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
	<body class="bg-white overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
					<div class="search-div col-xs-4 pull-right"">
						<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="操作员名称/操作员编号">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
						<div id="content" class="table_content"  style="height: auto;">
							<!-- ${tableHtml } -->
						<dhcc:tableTag paginate="wkfApprovalUserList" property="tablewkf0014" head="true"></dhcc:tableTag> 
						</div>
				<%-- 	<form method="post" theme="simple" name="cms_form" action="${webPath}/wkfApprovalUser/findApprovalUserByPage" target="curWindow">
						<input name="wkfRoleNo"  id="wkfRoleNo" type="hidden" value=${wkfRoleNo} />
						<input name="wkfBrNo"  id="wkfBrNo" type="hidden" value=${wkfBrNo} />
						<input name="wkfUserName"  id="wkfUserName" type="hidden" value=${wkfUserName} />
					</form> --%>
				</div>
			</div>
		</div>
	</body>
</html>