<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" >
	$(function(){
		myCustomScrollbar({
			obj : "#content",
			url : webPath+"/archiveInfoBorrow/findByPageAjax?optType=02", //列表数据查询的url
			tableId : "tablearchiveborrowout", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30, //加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
		});
	});

	//新增界面
	function applyInsert(){
		top.openBigForm(webPath+"/archiveInfoBorrow/inputOut", "新增", function () {
			window.location.reload();
		});
	}

	//详情
	function getOutDetail(obj,lk){
		top.openBigForm(webPath+lk, "借出详情", function () {
			window.location.reload();
		});
	}
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="applyInsert();">借出申请</button>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">借出申请</span>
				</div>
				<div class="col-md-2">
				</div>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content">
			</div>
		</div>
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
    filter_dic = [{
        "optCode" : "borrowingDate",
        "optName" : "借出日期",
        "dicType" : "date"
    },{
		"optCode" : "docType",
		"optName" : "类型",
		"parm" : ${docTypeJsonArray},
		"dicType" : "y_n"
	},{
        "optCode" : "status",
        "optName" : "申请状态",
        "parm" : ${cusTypeJsonArray},
        "dicType" : "y_n"
    }];
</script>
</html>