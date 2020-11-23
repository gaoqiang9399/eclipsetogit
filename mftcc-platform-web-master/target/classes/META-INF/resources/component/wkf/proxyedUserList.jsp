<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.WFProxy" %>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@page import="app.util.DateUtil"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>转授权/代理用户设置</title>
		<script type="text/javascript">
		function addProxyUser() {
			window.location.href = "${webPath}/wkf/System/addUserProxy";
		}
	</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/system/listProxy" name="cms_form" action="<%=webPath %>/wkf/System/listProxy" method="post">
						<!-- search start -->
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
										<tr>
											<td align="right" class="tdlable" >用户编号&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="用户编号" name="UserId" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
											<td align="right" class="tdlable" >用户名称&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="用户名称" name="UserName" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-- search end -->
						<!--tools -->
						<div class="tools_372">
							<input type="submit" value="查询" onclick="return submitJsMethod(this.form, 'firstEadisPageFlag()')" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
							<input type="button" value="新增" onclick="addProxyUser()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
						</div>
						<!--tools end-->
						<!--table -->
						<div class="table_w">
							<!--list -->
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="转授权/代理用户列表">
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
										<th scope="col"  align="center">序号</th>
										<th scope="col"  align="center">被代理人</th>
										<th scope="col"  align="center">被代理人名称</th>
										<th scope="col"  align="center">代理人</th>
										<th scope="col"  align="center">代理人名称</th>
										<th scope="col"  align="center">开始时间</th>
										<th scope="col"  align="center">结束时间</th>
										<th scope="col"  align="center">状态</th>
										<th scope="col" colspan="2" align="center" > <font class="button_color">操作</font></th>
									</tr>
								</thead>
							 <tbody id="tab"> 
							<%
								Object obj =  request.getAttribute(WF.PARAM_LIST_NAME);
								int total = 0;
								List list = null;
								if(obj != null){
									list = (List)obj;
									total = list.size();
								}
								for(int i = 0; i < total ;i++) {
									WFProxy proxy = (WFProxy)list.get(i);
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=proxy.getUser() %></td>
									<td align="center"><%=proxy.getUserName() %></td>
									<td align="center"><%=proxy.getProxyUser()%></td>
									<td align="center"><%=proxy.getProxyUserName()%></td>
									<td align="center"><%=DateUtil.dateToStr(proxy.getStartTime()) %></td>
									<td align="center"><%=DateUtil.dateToStr(proxy.getEndTime()) %></td>
									<td align="center"><%=Format.getProxyState(proxy.getState()) %></td>
									<td width="5%" align="center">
										<button  onclick="javascript:buttonforward('${webPath}/wkf/System/findProxy?proxyId=<%=proxy.getId() %>')" class="ico_button4All" title="修改">修改</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/System/deleteProxy?proxyId=<%=proxy.getId() %>')" class="ico_button4All" title="删除">删除</button>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
 			<pageC:page urlPath="${webPath}/system/listProxy" scope="request" page="ipage" />
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
