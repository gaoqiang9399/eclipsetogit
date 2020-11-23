<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_ExamineStateList.js"></script>
		<script type="text/javascript" >
			var fincIdStr="";
			var assignType="";
			$(function(){
				MfBusFincApp_ExamineStateList.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="MfBusFincApp_ExamineStateList.assignExamineTaskBatch();">指派</button>
					</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
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
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic =[{ "optCode" : "putoutAmt",
			 			"optName" : "借据金额",
			 			"dicType" : "num" 
					 },{ "optCode" : "endDate",
			 			"optName" : "上次检查时间",
			 			"dicType" : "date" 
					 },{ "optCode" : "nextExamineEndDate",
			 			"optName" : "下次检查截止时间",
			 			"dicType" : "date" 
					 },{ "optCode" : "fincExamineSts",
			 			"optName" : "检查状态",
			 			"parm" : ${fincExamineStsJsonArray},
			 			"dicType" : "y_n" 
					 },{ "optCode" : "riskLevel",
			 			"optName" : "检查结果",
			 			"parm" : ${riskLevelJsonArray},
			 			"dicType" : "y_n" 
					 }];
		
	</script>
</html>
