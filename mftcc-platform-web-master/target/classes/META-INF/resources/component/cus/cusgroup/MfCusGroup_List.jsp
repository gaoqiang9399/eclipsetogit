<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<%--<style type="text/css">
 .table-float-head {
     display: block;
     position: absolute;
     background-color: #ffffff;
     border-top: 1px solid #e9ebf2;
     border-bottom: 0px solid #e9ebf2;
     border-right: 1px solid #e9ebf2;
   }
 /*
   .top-title {
    color: rgb(50, 181, 203);
    font-size: 18px;
    text-align: center;
    line-height: 45px; 
  }*/

</style>--%>
<title>列表</title>
<script type="text/javascript" src="${webPath}/component/cus/cusgroup/js/MfCusGroup_List.js"></script>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath + "/mfCusGroup/findByPageAjax",//列表数据查询的url
			tableId : "tablecusgroup0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	});
	function choseGroup(parm) {
		parm = parm.split("?")[1];
		var parmArray = parm.split("&");
		var groupInfo = new Object();
		groupInfo.groupNo = parmArray[0].split("=")[1];
		groupInfo.groupName = parmArray[1].split("=")[1];
		parent.dialog.get('groupDialog').close(groupInfo);
	};
	function getDetailPage(obj, url) {
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		MfCusGroup_List.getDetailPage(obj, url);
		event.stopPropagation();
	};
</script>
</head>

<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div class="btn-div">
					 <div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="MfCusGroup_List.checkCusType();">新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">集团管理</span>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=集团名称/联系人" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12">
				<div id="content" class="table_content" style="height: auto;"></div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	filter_dic = [ {
		"optCode" : "groupName",
		"parm": [],
		"optName" : "集团名称",
		"dicType" : "val"
	}, {
		"optCode" : "idNum",
		"parm": [],
		"optName" : "营业执照号/组织机构代码/社会信用代码",
		"dicType" : "val"
	}, {
		"optCode" : "contactsTel",
		"parm": [],
		"optName" : "联系电话",
		"dicType" : "val"
	}, {
		"optCode" : "contactsName",
		"parm": [],
		"optName" : "联系人",
		"dicType" : "val"
	} ];
</script>
</html>
