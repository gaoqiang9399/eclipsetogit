<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwVchRuleMst/findByPageAjax",//列表数据查询的url
			    	tableId:"tablecwvchrulemst0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body>
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
				<button type="button" class="btn btn-primary pull-left" onclick="addcwPz()">新增</button>
					<ol class="breadcrumb pull-left">  
								<li><a href="${webPath}/cwParamset/cwParamEntrance">设置</a></li>
								<li class="active">业务凭证记账规则 </li>
							</ol>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4"/>
						<!-- end -->
				</div>
			</div>
		</div>
		
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!--页面显示区域-->
				 <div id="content" class="table_content"  style="height: auto;">
					</div> 
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		//top.openBigForm
		
		function addcwPz(){
			createShowDialog("${webPath}/cwVchRuleMst/goAddPage","新增业务凭证规则",'90','90',function(){
					 updateTableData();//重新加载列表数据
			});
		}
		//详情弹框，createShowDialog
		function thisGetById(url){
			createShowDialog(webPath+"/"+url,"业务凭证详情",'90','90',function(){
				updateTableData();//重新加载列表数据
			});
	};
	</script>
</html>
