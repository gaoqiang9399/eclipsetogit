<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfSysKind/findByPageAjax",//列表数据查询的url
			    	tableId:"tablesyskind0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    
			    });
			 });
		</script>
	</head>
	<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="newKind();">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
							<li class="active">产品设置 </li>
						</ol>
					</div>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=产品名称/产品编号"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript">
	function newKind(){
		top.createShowDialog('<%=request.getContextPath() %>/mfSysKind/getListPageBusModel','选择业务模式','50','50',closeCallBack);
	};
	function closeCallBack(){
		
	};
	
	$(function() {
    	 $("tr").bind("dblclick", function(event){
    	 	var url = $("tr").find('td').eq(0).find("a").attr("href");
    	 	window.location.href = url;
    	 })
	});	

</script>
</html>
