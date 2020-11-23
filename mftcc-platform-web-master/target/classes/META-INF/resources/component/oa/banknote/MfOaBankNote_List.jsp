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
			    	url:webPath+"/mfOaBankNote/findByPageAjax",//列表数据查询的url
			    	tableId:"tablebanknote0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="oa-banknote-apply-btn">
						<button type="button" class="btn btn-primary" id="billInsert">票据申请</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=出票行/账户名称"/>
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
<script type="text/javascript" src="${webPath}/component/oa/banknote/js/MfOaBankNote_list.js"></script>
<script type="text/javascript">
	// 接收传参等
	OaBankNoteList.path = "${webPath}";
	$(function() {
		OaBankNoteList.init();
	});
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "打款金额",
		"parm" : [],
		"optCode" : "txAmt",
		"dicType" : "num"
	},{
		"optName" : "打款金额",
		"parm" : [],
		"optCode" : "billSum",
		"dicType" : "num"
	},{
		"optName" : "最后修改日期",
		"parm" : [],
		"optCode" : "lstModTime",
		"dicType" : "date"
	},{
		"optName" : "打款利率",
		"parm" : [],
		"optCode" : "txRate",
		"dicType" : "num"
	},{
		"optCode" : "appSts",
		"optName" : "状态",
		"parm" : ${billPasJsonArray},
		"dicType" : "y_n"
	},{
		"optCode" : "billType",
		"optName" : "票据类型",
		"parm" : ${bankBillTypeJsonArray},
		"dicType" : "y_n"
	},];
</script>
</html>
