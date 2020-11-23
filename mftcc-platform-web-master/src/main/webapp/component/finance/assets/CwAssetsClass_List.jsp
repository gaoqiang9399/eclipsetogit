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
			    	url:webPath+"/cwAssetsClass/findByPageAjax",//列表数据查询的url
			    	tableId:"tableassetsclass0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head> 
	<body>
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary pull-left" onclick="finForm_input('');">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/cwAssets/getListPage">资产</a></li>
						<li class="active">资产类别</li>
					</ol>
				</div>
				<div class="search-title hidden"><span value=""></span></div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=类别"/>
						<!-- end -->
				</div>
			</div>
		</div>
	
		<!--页面显示区域-->
		<div id="content" class="table_content" style="height: auto;"></div>
	</body>	
	<script type="text/javascript">
	function finForm_input(url){//新增弹框
		top.openBigForm('${webPath}/cwAssetsClass/input', '新增', updateTableData);	
	}
	
	 function getByIdThis(obj, ajaxUrl){//详情弹框
		 top.openBigForm('${webPath}/'+ajaxUrl, '详情', updateTableData);	
	}
</script>
</html>
