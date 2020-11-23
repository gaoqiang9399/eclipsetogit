<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" >
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/archiveInfoVoucherReturn/findByPageAjax",//列表数据查询的url
			tableId : "tablearchivevoucherlist",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	});

	//新增界面
	function input(){
		top.openBigForm(webPath+"/archiveInfoVoucherReturn/input", "新增", function () {
			window.location.reload();
		});
	}

	function voucherReturn(obj,lk){
		top.openBigForm(webPath+lk, "补充资料", function(){
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
						<button type="button" class="btn btn-primary" onclick="input();">新增</button>
	  				</div>
	  				<div class="col-md-8 text-center">
	  					<span class="top-title">凭证退还申请</span>
	  				</div>
	  				<div class="col-md-2">
	  				</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/合同编号"/>
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
			"optCode" : "returnDate",
			"optName" : "退还日期",
			"dicType" : "date"
		},{
			"optCode" : "returnState",
			"optName" : "状态",
			"parm" : ${stateJsonArray},
			"dicType" : "y_n"
		}];
	</script>
</html>