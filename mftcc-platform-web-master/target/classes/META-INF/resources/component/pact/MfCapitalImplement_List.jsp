<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfCapitalImplementDetail_Insert.js"></script>
		<script type="text/javascript" >
			var queryType = "${queryType}";
			var url = webPath+"/mfBusFincApp/findCapitalImplementWarningByPageAjax";
			var tableId = "tablecapitalimplentmentwarning";
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:tableId,//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pactSts:"8"}//指定参数，过滤掉已经封挡的数据
			    });
			 });
			function exportExcel(){
				window.top.location.href = encodeURI(url +  "Excel?tableId=" + tableId);
			};


			

			
			
			var timeFunc=null;
			//监听ctrl键
			document.onkeydown=function(event){
				var e = event || window.event || arguments.callee.caller.arguments[0];
				//若点击了ctrl 键则 清除timeFunc
				if(e && e.ctrlKey){ 
					clearTimeout(timeFunc);
				}
			}; 

		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
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
        "optCode" : "warningSts",
        "optName" : "登记状态",
        "parm" : [ {
            "optName" : "未登记",
            "optCode" : "0"
        }, {
            "optName" : "已登记",
            "optCode" : "1"
        } ],
        "dicType" : "y_n"
    } ];
</script>
</html>
