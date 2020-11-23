<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.identity.User" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
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
			function cancelClick() {
				window.close();
			}
			
			function enterClick() {
				var selectedValue = getSelectedValue("userIdCheckbox");
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
					<form id="${webPath}/system/findUsers" name="cms_form" action="${webPath}/wkf/System/findUsers" method="post">
						<!-- search start -->
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
										<tr>
											<td align="right" class="tdlable" >用户编号&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="用户编号" name="UserId" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
											<td align="right" class="tdlable" >用户名称&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="用户名" name="UserName" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-- search end -->
						<!--tools -->
						<div class="tools_372">
							<input type="submit" value="查询" onclick="return submitJsMethod(this.form, 'firstEadisPageFlag()')" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
						</div>
						<!--tools end-->
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="用户列表">
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<colgroup></colgroup>
								<thead> 
									<tr> 
										<th scope="col"  align="center">选择</th>
										<th scope="col"  align="center">用户编号</th>
										<th scope="col"  align="center">用户名称</th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%for(int i = 0; i < total ;i++) {
								User user = (User)list.get(i);
								%>
								<tr >
									<td align="center"><input name="userIdCheckbox" type="checkbox" value="<%=user.getId() %>" /></td>
									<td align="center"><%=user.getId() %></td>
									<td align="center"><%=user.getName()%></td>
								</tr>
								<%} %>
							 </tbody> 
						</table>
 
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
