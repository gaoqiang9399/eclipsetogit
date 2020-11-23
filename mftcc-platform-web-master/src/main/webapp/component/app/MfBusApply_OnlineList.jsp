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
			url : webPath+"/mfBusApply/findOnlineByPageAjax",//列表数据查询的url
			tableId : "tablecusandapply0002",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	});
	
	function assignBill(obj,url){
   		top.window.openBigForm(url,'分单页面',closeCallBack);
	}

	function applyInsert() {
		top.window.openBigForm(webPath+'/mfCusCustomer/input','客户登记',myclose);
	};

	
	function getDetailPage(obj,url){		
		top.LoadingAnimate.start();		
		window.location.href=url;			
	}

	function ajaxInprocess(obj, ajaxUrl) {
		var contentForm = $(obj).parents(".content_table");
		var tableId = contentForm.find(".ls_list").attr("title");
		jQuery.ajax({
			url:ajaxUrl,
			data:{tableId : tableId,appId : appId},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},success : function(data) {
				if (data.flag == "success") {
					$.myAlert.Alert(top.getMessage("SUCCEED_INSERT_PROCESS"));
					if (data.tableData != undefined && data.tableData != null) {
						var tableHtml = $(data.tableData).find("tbody").html();
						contentForm.find(".ls_list tbody").html(tableHtml);
					}
				} else if (data.flag == "error") {
					if (alertFlag) {
						window.parent.window.$.myAlert.Alert(data.msg);
					} else {
						$.myAlert.Alert(data.msg);
					}
				}
			},error : function(data) {
				if (alertFlag) {
					window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				} else {
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		});
	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary" onclick="applyInsert();">进件</button>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称,项目名称"/>
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
		"dicType" : "num"
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
		"optCode" : "appSts",
		"optName" : "办理阶段",
		"parm" : ${appStsJsonArray},
		"dicType" : "y_n"
	}, /**{
					"optCode" : "vouType",
					"optName" : "担保方式",
					"parm" : [ {
						"optName" : "信用担保",
						"optCode" : "1"
					}, {
						"optName" : "保证担保",
						"optCode" : "2"
					}, {
						"optName" : "抵押担保",
						"optCode" : "3"
					}, {
						"optName" : "质押担保",
						"optCode" : "4"
					} ],
					"dicType" : "y_n"
				} */
		{
		"optName" : "客户类型",
		"parm" : ${cusTypeJsonArray},
		"optCode" : "cusType",
		"dicType" : "y_n"
	},/* {
		"optName" : "联系人",
		"parm" : [],
		"optCode" : "contactsName",
		"dicType" : "val"
	},{
		"optName" : "联系电话",
		"parm" : [],
		"optCode" : "contactsTel",
		"dicType" : "val"
	},{
		"optName" : "项目名称",
		"parm" : [],
		"optCode" : "appName",
		"dicType" : "val"
	}, */{
		"optName" : "产品种类",
		"parm" : [],
		"optCode" : "kindName",
		"dicType" : "val"
	},];
	//           addDefFliter("0","担保方式","vouType","VOU_TYPE","1,2,3");
	// 		  addDefFliter("0","申请状态","appSts","APP_STS","0,1,2,3,4,5");
</script>
</html>
