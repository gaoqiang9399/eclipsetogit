<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.identity.User" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@page import="app.creditapp.prd.entity.PrdBase" %>
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
		<title>选择代理业务</title>
		<script type="text/javascript">
			function cancelClick() {
				window.close();
			}
			
			function enterClick() {
				var selectedValue = getSelectedValue("workflowId");
				if( selectedValue == "" ) {
					alert("请选择代理业务！");
					return;
				}
				window.returnValue = selectedValue;
				window.close();
				return;
			}
			window.name = "buswindow";
		</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/wkfSystem/selectBusiness" name="cms_form" action="${webPath}/wkf/WkfSystem/selectBusiness" method="post"  target="buswindow">
						<!-- search start -->
						<!-- 
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<table width="80%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
										<tr>
											<td align="right" class="tdlable" >用户编号&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="用户编号" name="userId" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
											<td align="right" class="tdlable" >用户名称&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="用户名" name="userName" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
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
							<table id="tablist" width="80%" border="0"   align="center" cellspacing="1" class="ls_list" title="用户列表">
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
										<th scope="col"  align="center">产品名称</th>
										<th scope="col"  align="center">申请流程</th>
										<th scope="col"  align="center">放款流程</th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%for(int i = 0; i < total ;i++) {
								PrdBase prdBase = (PrdBase)list.get(i);
								String appWorkflowId = prdBase.getAppWorkflowId();
								String ddWorkflowId = prdBase.getDdWorkflowId();
							%>
								<tr >
									<td align="center"><%=prdBase.getPrdName() %></td>
									<td align="center">
									<%if(appWorkflowId != null && !"".equals(appWorkflowId) && !"null".equals(appWorkflowId)) {%>
										<input type="checkbox" name="workflowId" value="<%=appWorkflowId %>">
									<%} else { %>
										无
									<%} %>
									</td>
									<td align="center">
									<%if(ddWorkflowId != null && !"".equals(ddWorkflowId) && !"null".equals(ddWorkflowId)) {%>
										<input type="checkbox" name="workflowId" value="<%=ddWorkflowId %>">
									<%} else { %>
										无
									<%} %>
									</td>
								</tr>
								<%} %>
							 </tbody> 
						</table>
 <pageC:page urlPath="${webPath}/wkfSystem/selectBusiness" scope="request" page="ipage" />
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
