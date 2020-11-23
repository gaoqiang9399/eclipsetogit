<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfOaFileCountersign/findByPageAjax",//列表数据查询的url
			    	tableId:"tablefilesign0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			    
			    $('#fileInsert').on('click', function(){
			    	top.openBigForm(webPath+'/mfOaFileCountersign/input',"单独盖章申请", updateTableData);
			    });
			 });
			
			function openGetByIdForm(obj, url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,"详情", updateTableData);
			}
			
			function showAppHis(obj, url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,"审批历史", updateTableData);
			}
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<%--<dhcc:pmsTag pmsId="oa-file-sign-apply">--%>

				<%--</dhcc:pmsTag>--%>
					<button type="button" class="btn btn-primary pull-left" id="fileInsert">单独盖章申请</button>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=主题" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;"></div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>
