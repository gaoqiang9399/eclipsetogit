<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-info pull-left"id="consAppInsert">新增申领</button>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3"/>
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
<script type="text/javascript" src="${webPath}/component/oa/consumable/js/MfOaConsAppList.js"></script>
<script type="text/javascript">
OaConsAppList.path = "${webPath}";
		$(function() {
			OaConsAppList.init(); 
		});	
		
		/*我的筛选加载的json*/
		filter_dic = [
		      {
                  "optName": "资产名称",
                  "parm": [],
                  "optCode":"consName",
                  "dicType":"val"
              }, {
                  "optName": "资产类别",
                  "parm": [],
                  "optCode":"className",
                  "dicType":"val"
              }, {
                  "optName": "单价",
                  "parm": [],
                  "optCode":"price",
                  "dicType":"num"
              },{
                  "optName": "当前库存",
                  "parm": [],
                  "optCode":"storeNum",
                  "dicType":"num"
              } ,{
				"optCode" : "appType",
				"optName" : "申领类型",
				"parm" : [ {
					"optName" : "领用",
					"optCode" : "1"
				}, {
					"optName" : "借用",
					"optCode" : "2"
				} ],
				"dicType" : "y_n"
			},
          ];
     </script>
</html>
