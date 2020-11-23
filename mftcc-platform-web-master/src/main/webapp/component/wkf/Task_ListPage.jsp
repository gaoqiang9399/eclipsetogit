<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script language="javascript">
	//var isGoBack = true;
</script>
<script type="text/javascript"
	src="${webPath}/component/include/tablelistshowdiv.js"></script>
</head>
<script type="text/javascript">
	function reAssign(parm) {
		var taskId=parm.split("?")[1].split("&")[0].split("=")[1];
		var appId=parm.split("?")[1].split("&")[1].split("=")[1];
		var title = "选人";
		var url = webPath+'/WkfApprovalUser/getListPage?wkfBrNo=100000&wkfRoleNo=LSP026';
		url = encodeURI(url);//
		window.top.openBigForm(webPath+"/task/toChangeUser?appId="+appId+"&taskId="+taskId,"重新分派","${webPath}/task/getListPage","50%","50%");
		<%-- window.top.dhccModalDialog.dialog(null,null,url,title,function(result){
			if( result!="" && result != null && result != "undefined" ) {
				if(confirm("确定改派给[" + result.split(":")[0] + "]?")){
					window.location.href="${webPath}/task/reAssign?taskId=" + taskId + "&UserId=" + result.split(":")[0] + "&appId=" + appId;
				}
			}
		}); --%>
	}
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/task/findByPageAjax",//列表数据查询的url
			tableId : "tablewkf0023",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true//是否有我的筛选
		});
	});
	function refuseTask(obj,actionUrl){
		window.top.alert("确认否决该任务？",2,function(){
			$.ajax({
			type:"GET",
			cache:false,
			url:actionUrl,
			dataType:"json",
			success:function(jsonData){
				window.top.alert("否决成功",1,function(){
					window.location.reload();
				});
			},
			error:function(){
				window.top.alert("否决失败",0);
			}
		});
		});
	}
</script>
	<body style="overflow-y: hidden;">
		<input type="hidden" name="wkfUserName">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-md-9 column  color_theme">
							<ol class="breadcrumb pull-left padding_left_0">
								<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
								<li class="active">流程任务列表 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>