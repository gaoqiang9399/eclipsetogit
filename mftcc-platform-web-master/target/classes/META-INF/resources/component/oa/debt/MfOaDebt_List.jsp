<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript">
 $(function(){
    myCustomScrollbar({
    	obj:"#content",//页面内容绑定的id
    	url:webPath+"/mfOaDebt/findByPageAjax",//列表数据查询的url
    	tableId:"tabledebt00001",//列表数据查询的table编号
    	tableType:"thirdTableTag",//table所需解析标签的种类
    	pageSize:30,//加载默认行数(不填为系统默认行数)
    	ownHeight : true,
	    callback:function(){	    
	    		top.LoadingAnimate.stop();
		}//方法执行完回调函数（取完数据做处理的时候）
    });
 }); 
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary" id="debtInsert">申请借款</button>
<!-- //					<button type="button" class="btn btn-info" id="returnInsert">还款</button>
 -->				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
		<div style="height: 20px;"></div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript"
	src="${webPath}/component/oa/debt/js/MfOaDebtList.js"></script>
<script type="text/javascript">
OaDebtList.path = "${webPath}";
	$(function() {
		OaDebtList.init();
	});
	/*我的筛选加载的json*/
	filter_dic = [ {
		"optName" : "申请时间",
		"parm" : [],
		"optCode" : "applyTime",
		"dicType" : "num"
	}, {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "applyAmt",
		"dicType" : "num"
	}, {
		"optName" : "销账金额",
		"parm" : [],
		"optCode" : "returnAmt",
		"dicType" : "num"
	}, {
		"optName" : "欠款金额",
		"parm" : [],
		"optCode" : "debtAmt",
		"dicType" : "num"
	}, {
		"optName" : "申请状态",
		"optCode" : "debtSts",
		"parm" : [ {
			"optName" : "登记",
			"optCode" : "1"
		}, {
			"optName" : "审批中",
			"optCode" : "2"
		}, {
			"optName" : "审批复核",
			"optCode" : "3"
		}, {
			"optName" : "申请拒绝",
			"optCode" : "4"
		}, {
			"optName" : "已借出",
			"optCode" : "5"
		}, {
			"optName" : "还款复核中",
			"optCode" : "6"
		}, {
			"optName" : "已完结",
			"optCode" : "7"
		} ],
		"dicType" : "y_n"
	}, {
		"optCode" : "applyName",
		"optName" : "申请名称",
		"parm" : [],
		"dicType" : "val"
	}, ];
</script>
</html>


