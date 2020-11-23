<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title><%=request.getAttribute("title") %></title>
	</head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
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
				window.top.dhccModalDialog.returnVal(window,selectedValue,false);
				//window.returnValue = selectedValue;
				//window.close();
				return;
			}
			function enterClickForMulti() 
			{
				var elements=document.getElementsByName("wkfUserName");
				var selectedValue = "";
				var userId="";
				var userName="";
				var selectedUserIdValue="";
				var selectedUserNameValue="";
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
				window.top.dhccModalDialog.returnVal(window,selectedValue,false);
				//window.returnValue = selectedValue;
				//window.close();
				return;
			}
		</script>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/wkfApprovalUser/findByPageForWkfAjax",//列表数据查询的url
			tableId : "tablewkf0014",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true,
			data:{"wkfRoleNo":"<%=request.getAttribute("wkfRoleNo")%>","wkfBrNo":"<%=request.getAttribute("wkfBrNo")%>","title":"<%=request.getAttribute("title")%>"},
			    	callback:function(options){
			    		
			    	}
		});
	});
</script>
	<body class="body_bg">
		<input name="wkfRoleNo"  id="wkfRoleNo" type="hidden" value=${wkfRoleNo} />
		<input name="wkfBrNo"  id="wkfBrNo" type="hidden" value=${wkfBrNo} />
		<input name="wkfUserName"  id="wkfUserName" type="hidden" value=${wkfUserName} />
		<div>
			<div style="vertical-align: bottom; display: block;" class="tabCont">
				<strong>审批用户选择</strong>
				
				<div class="search-group">
					<!--我的筛选选中后的显示块-->
					<div class="search-lable" id="pills">
					<%-- <dhcc:thirdButton value="新增" action="新增"
						onclick="${webPath}/acGlParm/input"></dhcc:thirdButton> --%>
						<!--我的筛选按钮-->
						<div class="filter-btn-group">
							<!--自定义查询输入框-->
							<input id="filter_in_input" placeholder="智能搜索" class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search" class="filter_btn_search" type="button">
									<i class="i i-fangdajing"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div id="content" class="table_content" style="height: auto;">
				<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
			</div>
		</div>
	</body>
</html>