<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="<%=webPath %>/component/app/js/MfBusApply_trhList.js"></script>
<script type="text/javascript">
	var cusNo = '${cusNo}';
	$(function() {
		mfBusApplyList.init();
	});
	
	function getDetailPage(obj,url){		
		mfBusApplyList.getDetailPage(obj,url);
	}

	function ajaxInprocess(obj, ajaxUrl) {
		mfBusApplyList.ajaxInprocess(obj,ajaxUrl);
	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
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
	filter_dic = [ {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "appAmt",
		"dicType" : "num"
	},{
		"optName" : "申请日期",
		"parm" : [],
		"optCode" : "appTime",
		"dicType" : "date"
	}, {
		"optName" : "申请利率",
		"parm" : [],
		"optCode" : "fincRate",
		"dicType" : "num"
	}, {
		"optName" : "申请期限",
		"parm" : [],
		"optCode" : "term",
		"dicType" : "num"
	}, {
		"optCode" : "busStage",
		"optName" : "办理阶段",
		"parm" : ${flowNodeJsonArray},
		"dicType" : "y_n"
	},{
		"optName" : "产品种类",
		"parm" : ${kindTypeJsonArray},
		"optCode" : "kindNo",
		"dicType" : "y_n"
	},
	];
</script>
</html>
