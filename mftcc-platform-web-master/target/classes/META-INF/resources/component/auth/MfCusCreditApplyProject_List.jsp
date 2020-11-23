<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<script src="${webPath}/component/auth/js/MfCusCreditApplyProject.js"></script>
		<script type="text/javascript">
			$(function(){
				MfCusCreditApplyProject.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
				<div  class="btn-div">
					<div class="col-md-8 text-center">
						<span class="top-title">立项查询</span>
					</div>
				</div>
					<!-- 我的筛选选中后的显示块 -->
					<div class="search-div" id="search-div">
							<!-- begin -->
								<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=立项查询"/>
							<!-- end -->
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
				<%@ include file="/component/include/PmsUserFilter.jsp"%>
				<script type="text/javascript">
		
			filter_dic = [ {
				"optCode" : "projectNo",
				"optName" : "项目编号",
				"parm" : [],
				"dicType" : "val"
			},{
				"optCode" : "projectName",
				"optName" : "项目名称",
				"parm" : [],
				"dicType" : "val"
			} ,{
				"optCode" : "cusName",
				"optName" : "客户名称",
				"parm" : [],
				"dicType" : "val"
			},{
				"optCode" : "beginDate",
				"optName" : "授信开始日期",
				"parm" : [],
				"dicType" : "date"
			},{
				"optCode" : "endDate",
				"optName" : "授信结束日期",
				"parm" : [],
				"dicType" : "date"
			},{
				"optCode" : "creditSts",
				"optName" : "授信状态",
					"parm" : [ {
						"optName" : "已授信",
						"optCode" : "1"
					}, {
						"optName" : "未授信",
						"optCode" : "0"
					} ],
				"dicType" : "y_n"
			}   ];
		</script>
	</body>	
</html>
