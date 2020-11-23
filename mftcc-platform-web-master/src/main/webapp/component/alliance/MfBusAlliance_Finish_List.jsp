<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var allianceId = '${allianceId}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusAlliance/getFinishedListByPageAjax",//列表数据查询的url
			    	tableId:"tableassureAllianceDetail",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{allianceId:allianceId},//指定参数
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });

            function getDetailPage(obj, url) {
                top.openBigForm(url, "详情", function () {
                });
            };
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=项目名称"/>
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
		filter_dic = [
		      {
                  "optName": "担保余额",
                  "parm": [],
                  "optCode":"assureBalance",
                  "dicType":"num"
              }

          ];

	</script>
</html>
