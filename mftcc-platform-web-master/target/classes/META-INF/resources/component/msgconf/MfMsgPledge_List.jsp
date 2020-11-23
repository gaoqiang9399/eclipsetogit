<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css"/>
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfMsgPledgeList.addMfMsgPledge();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">资产预警设置</span>
						</div>
					</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=预警方案名称"/>
					</div>
				</div>
		</div>	
		<div class="row clearfix">
			<div class="col-md-12">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/msgconf/js/MfMsgPledge_List.js"></script>
<script type="text/javascript">
	// 接收传参等
	$(function() {
		MfMsgPledgeList.init();
	});
	/*我的筛选加载的json*/
	/* filter_dic = [ {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "appAmt",
		"dicType" : "num"
	},{
		"optName" : "申请利率",
		"parm" : [],
		"optCode" : "rate",
		"dicType" : "num"
	}, {
		"optName" : "申请期限",
		"parm" : [],
		"optCode" : "term",
		"dicType" : "num"
	},{
		"optCode" : "cusType",
		"optName" : "客户类型",
		"parm" : ${cusTypeJsonArray},
		"dicType" : "y_n"
	}]; */
		
</script>
</html>