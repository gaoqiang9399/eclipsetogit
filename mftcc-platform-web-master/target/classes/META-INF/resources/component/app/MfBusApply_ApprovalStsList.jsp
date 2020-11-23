<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfBusApply/findApprovalStsListByPageAjax",//列表数据查询的url
			tableId : "tableapprovalstsquerylist",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30//加载默认行数(不填为系统默认行数)
		});
	});
	function getDetailPage(obj,url){	
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div top-title">审批状态查询</div>
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
	filter_dic = [ {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "appAmt",
		"dicType" : "num"
	},{
		"optName" : "登记日期",
		"parm" : [],
		"optCode" : "appTime",
		"dicType" : "date"
	},{
		"optName" : "审批状态",
		"parm" : ${appStsJsonArray},
		"optCode" : "appSts",
		"dicType" : "y_n"
	}, {
		"optName" : "产品种类",
		"parm" : ${kindTypeJsonArray},
		"optCode" : "kindNo",
		"dicType" : "y_n"
	}];
</script>
</html>
