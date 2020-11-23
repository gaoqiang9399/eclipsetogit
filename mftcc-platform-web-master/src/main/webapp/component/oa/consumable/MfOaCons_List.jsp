<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
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
					<dhcc:pmsTag pmsId="oa-consapp-plusdata-btn">
						<button type="button" class="btn btn-primary pull-left"id="consInsert">入库</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=资产名称/资产类别"/>
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
     <script type="text/javascript" src="${webPath}/component/oa/consumable/js/MfOaConsList.js"></script>
<script type="text/javascript">
OaConsList.path = "${webPath}";
		$(function() {
			OaConsList.init(); 
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
				"parm" : ${consAppTypeJsonArray},
				"dicType" : "y_n"
			},
          ];
          </script>
</html>
