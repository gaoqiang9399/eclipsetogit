<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.ProcessDefinition"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@page import="com.dhcc.workflow.api.Deployment"%>
<%@page import="app.creditapp.wkf.AppConstant"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>流程定义管理</title>
		
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/processDefinition/findByPage" name="cms_form" action="<%=webPath %>/wkf/ProcessDefinition/findByPage" method="post">
						<!-- search start -->
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
										<tr>
											<td align="right" class="tdlable" >流程标识&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="流程标识" name="<%=AppConstant.PARAM_PROCESSDEF_KEY %>" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
											<td align="right" class="tdlable" >流程名称&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="流程名称" name="<%=AppConstant.PARAM_PROCESSDEF_NAME %>" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
											<td align="right" class="tdlable" >流程描述&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="流程描述" name="processDefDesc" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
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
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="流程定义列表">
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
										<th scope="col"  align="center">流程标识</th>
										<th scope="col"  align="center">流程名称</th>
										<th scope="col"  align="center">流程描述</th>
										<th scope="col"  align="center">流程版本</th>
										<th scope="col"  align="center">创建时间</th>
										<th scope="col"  align="center">过期时间</th>
										<th scope="col"  align="center">创建者</th>
										<th scope="col"  align="center">状态</th>
										<th scope="col" colspan="2" align="center" > <font class="button_color">操作</font></th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%
								Object obj =  request.getAttribute(WF.PARAM_LIST_NAME);
								
								int total = 0;
								List list = null;
								if( obj != null ) {
									list = (List) obj;
									total = list.size();
								}
								for(int i = 0; i < total ;i++) {
									ProcessDefinition pd = (ProcessDefinition)list.get(i);
							%>
								<tr >
									<td align="center"><%=pd.getKey() %></td>
									<td align="center"><%=pd.getName() %></td>
									<td align="center"><%=Format.formatString(pd.getDescription()) %></td>
									<td align="center"><%=pd.getVersion() %></td>
									<td align="center"><%=Format.formatDate(Format.parseDate(pd.getCreate())) %></td>
									<td align="center"><%=Format.formatDate(Format.parseDate(pd.getExpire()))%></td>
									<td align="center"><%=Format.formatString(pd.getAuthor()) %></td>
									<td align="center"><%=Format.formatDefState(pd.getState()) %></td>
									<td width="5%" align="center">
									<% if(!Deployment.STATE_ACTIVE.equals(pd.getState())) { %>
										<button  onclick="javascript:buttonforward('/wkf/ProcessDefinition/<%=WF.PARAM_PROCESSDEF_ACTIVATE %>?processDeploymentId=<%=pd.getDeploymentId() %>')" class="ico_button4All" title="启用">启用</button>
									<%} else { %>
										<button  onclick="javascript:buttonforward('/wkf/ProcessDefinition/<%=WF.PARAM_PROCESSDEF_SUSPENED%>?processDeploymentId=<%=pd.getDeploymentId() %>')" class="ico_button4All" title="暂停">暂停</button>
									<%} %>
									<button  onclick="javascript:window.open('/workflow/WFDLDesigner.jsp?command=DesignProcess&objectId=<%=pd.getId() %>')" class="ico_button4All" title="设计">设计</button>
									<%if(Deployment.STATE_DISABLED.equals(pd.getState())) {%>
										<button  onclick="javascript:lkconfirm('wkf/ProcessDefinition/delete?processDeploymentId=<%=pd.getDeploymentId() %>')" class="ico_button4All" title="删除">删除</button>
									<%} %>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
 			<pageC:page urlPath="${webPath}/processDefinition/findByPage" scope="request" page="ipage" />
							<!--list end-->
						</div>
						<!--table end -->
					</form>
				</div>
				<!--con end-->
			</div>
 
		</div>
	</body>
</html>
