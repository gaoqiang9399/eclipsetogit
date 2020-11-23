<%@page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.api.task.Task"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.TaskImpl"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.IForm"%>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<html>
	<head>
		<title>流程任务管理</title>
		<script type="text/javascript">
		function selectUsers(taskId) {
			var result = window.showModalDialog(webPath+"/wkf/WkfSystem/selectUser?brNo=all",'选择执行人','dialogWidth:500px;dialogHeight:400px;status:no;');
			if( result != null && result != "undefined" ) {
				if(confirm("确定改派给[" + result + "]?")){
					result = result.substring(0,result.indexOf(":"));
					window.location.href=webPath+"/wkf/Task/reAssign?taskId=" + taskId + "&UserId=" + result;
				}
			}
		}
	</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/task/findByPage" name="cms_form" action="${webPath}/wkf/task/findByPage" method="post">
						<!-- search start -->
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td>
									<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
										<tr>
											<td align="right" class="tdlable" >任务名称&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="流程名称" name="taskName" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
											<td align="right" class="tdlable" >执行人&nbsp</td>
											<td align="left" class="tdvalue" ><input type="text" title="流程标识" name="assignee" dataType="0" mustinput="0" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></td>
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
							<table id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="流程任务列表">
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
										<th scope="col"  align="center">序号</th>
										<th scope="col"  align="center">任务实例编号</th>
										<th scope="col"  align="center">任务名称</th>
										<th scope="col"  align="center">任务描述</th>
										<th scope="col"  align="center">业务编号</th>
										<th scope="col"  align="center">业务数据</th>
										<th scope="col"  align="center">任务执行人</th>
										<th scope="col"  align="center">创建时间</th>
										<th scope="col"  align="center">截止时间</th>
										<th scope="col"  align="center">业务表单</th>
										<th scope="col" colspan="5" align="center" > <font class="button_color">操作</font></th>
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
									Task task = (Task)list.get(i);
							%>
								<tr >
									<td align="center"><%=i+1 %></td>
									<td align="center"><%=task.getId() %></td>
									<td align="center"><%=task.getName()%></td>
									<td align="center"><%=Format.formatString(task.getDescription()) %></td>
									<td align="center"><%=Format.formatString(task.getAppId()) %></td>
									<td align="left"><script language="javascript">document.write(formatAppValue('<%=Format.formatString(task.getAppValue()) %>'));</script></td>
									<td align="center"><%=Format.formatString(task.getAssignee()) %></td>
									<td align="center"><%=Format.formatDate(task.getCreateTime()) %></td>
									<td align="center"><%=Format.formatDate(task.getDuedate()) %></td>
									<td align="center"><script language="javascript">document.write(formatTaskForm("${webPath}", "<%=task.getId()%>", "<%=Format.formatString(task.getForms()) %>"));</script></td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/Task/suspendTask?taskId=<%=task.getId() %>')" class="ico_button4All" title="暂停">暂停</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/Task/resumeTask?taskId=<%=task.getId() %>')" class="ico_button4All" title="恢复">恢复</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:selectUsers('<%=task.getId() %>')" class="ico_button4All" title="改派">改派</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:lkconfirm('${webPath}/wkf/Task/refuseTask?taskId=<%=task.getId() %>')" class="ico_button4All" title="否决">否决</button>
									</td>
									<td width="5%" align="center">
										<button  onclick="javascript:buttonforward('${webPath}/wkf/svg/ProcessToSVG/viewProcessIns?processInstanceId=<%=task.getExecutionId() %>')" class="ico_button4All" title="查看流程">查看流程</button>
									</td>
								</tr>
								<%} %>
							 </tbody> 
			</table>
 			<pageC:page urlPath="${webPath}/task/findByPage" scope="request" page="ipage" />
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
