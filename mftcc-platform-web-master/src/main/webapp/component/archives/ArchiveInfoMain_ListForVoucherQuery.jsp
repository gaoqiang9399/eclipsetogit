<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" >
	var appId = "${appId}";
	var docType = "${docType}";
	$(function(){
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/archiveInfoDetail/findVoucherQueryByPage", //列表数据查询的url
			tableId : "tablearchivevoucherquery", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		});
	});

	//客户视角
	function getDetailPage(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url;
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url, "客户信息", function () {
			window.location.reload();
		});
	}

	//合同视角
	function getDetailPact(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url;
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url, "合同信息", function () {
			window.location.reload();
		});
	}

	//查看归档资料
	function getDetailPageForQuery(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url;
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url, "归档资料信息", function () {
			window.location.reload();
		});
	}

	//借出记录
	function getBorrowVoucherDetail(obj,url){
		top.openBigForm(url, "借出记录", function () {
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
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">凭证查询</span>
				</div>
				<div class="col-md-2">
				</div>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
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
	filter_dic = [
		{
			"optName" : "资料类型",
			"parm" : ${docTypeJsonArray},
			"optCode" : "docType",
			"dicType" : "y_n"
		},{
			"optName" : "客户名称",
			"parm" : [],
			"optCode" : "cusName",
			"dicType" : "val"
		},{
			"optName" : "项目名称",
			"parm" : [],
			"optCode" : "appName",
			"dicType" : "val"
		}
	];
</script>
</html>
