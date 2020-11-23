<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.model.Transition"%>
<%@page import="com.dhcc.workflow.pvm.internal.model.TransitionImpl" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
<html>
	<head>
		<title>选择回退路径</title>
		<script type="text/javascript">
			function enterClick() {
			var selectedValue = getSelectedValue("transitionName");
			if( selectedValue == "" ) {
				alert("请选择流转路径！");
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
					<form id="" name="cms_form" action="" method="post">
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="转移路径列表">
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
										<th scope="col"  align="center">名称</th>
										<th scope="col"  align="center">描述</th>
										<th scope="col"  align="center">退回到</th>
									</tr>
								</thead>
							 <tbody id="tab"> 
								<%
								String data = request.getParameter("data");
								String[] transitions = data.split("@");
								String[] transition = null;
								for(int i=0; i<transitions.length; i++) {
									 transition = transitions[i].split(",");
								%>
								<tr >
									<td align="center"><input name="transitionName" type="radio" class="STYLE2" value="<%=transition[0] %>" /></td>
									<td align="center"><%=transition[0] %></td>
									<td align="center"><%=transition[1] %></td>
									<td align="center"><%=transition[2] %></td>
								</tr>
								<%} %>
							 </tbody> 
						</table>
 
							<!--list end-->
						</div>
						<!--table end -->
						<div class="from_btn">
						  	<input type="button" value="确定" onclick="enterClick()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
							<input type="button" value="取消" onclick="javascript:window.close();" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
					 	 </div>
					</form>
				</div>
				<!--con end-->
			</div>
 
		</div>
	</body>
</html>
