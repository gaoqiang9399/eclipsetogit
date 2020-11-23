<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.identity.User" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<%
    List list = (List) request.getAttribute(WF.PARAM_LIST_NAME);
    int total = 0;
	if( list != null ) {
		total = list.size();
	}
%>
<html>
	<head>
		<title>选择用户</title>
		<script type="text/javascript">
			window.name = "userwindow";
			function cancelClick() {
				window.close();
			}
			
			function enterClick() {
				var selectedValue = getSelectedValue("radio");
				if( selectedValue == "" ) {
					alert("请选择用户！");
					return;
				}
				
				window.returnValue = selectedValue;
				window.close();
				return;
			}
		</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/wkfSystem/findUsersInfo" name="cms_form" action="<%=webPath %>/wkf/WkfSystem/findUsersInfo" method="post" target="userwindow">
						<!-- search start -->
						<!-- 
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
										<tr>
											<td align="right" class="tdlable" width="10%">用户编号&nbsp</td>
											<td align="left" class="tdvalue" width="10%"><input type="text" title="用户编号" name="userId" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" size="10"></td>
											<td align="right" class="tdlable" width="10%">用户名称&nbsp</td>
											<td align="left" class="tdvalue" width="10%"><input type="text" title="用户名" name="userName" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" size="10"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						 -->
						<!-- search end -->
						<!--tools -->
						<!-- 
						<div class="tools_372">
							<input type="submit" value="查询" onclick="return submitJsMethod(this.form, 'firstEadisPageFlag()')" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
						</div>
						 -->
						<!--tools end-->
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="用户列表">
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<thead> 
									<tr> 
										<th scope="col"  align="center">选择</th>
										<th scope="col"  align="center">用户编号</th>
										<th scope="col"  align="center">真实姓名</th>
										<th scope="col"  align="center">所属角色</th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%for(int i = 0; i < total ;i++) {
								String str = (String)list.get(i);
								String[] user = str.split(",");
								if(user.length < 5){
									continue;
								}
								%>
								<tr >
									<td align="center"><input name="radio" type="radio" value="<%=user[0] %>:<%=user[1] %>" onclick="enterClick()"/>
									</td>
									<td align="center"><%=user[0] %></td>
									<td align="center"><%=user[1] %></td>
									<td align="center"><%=user[5] %></td>
								</tr>
								<%} %>
							 </tbody> 
						</table>
 <pageC:page urlPath="${webPath}/wkfSystem/findUsersInfo" scope="request" page="ipage" />
							<!--list end-->
						</div>
						<!--table end -->
						<div class="from_btn">
						  	<input type="button" value="确定" onclick="enterClick()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
							<input type="button" value="取消" onclick="cancelClick()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
					 	 </div>
					</form>
				</div>
				<!--con end-->
			</div>
 
		</div>
	</body>
</html>
