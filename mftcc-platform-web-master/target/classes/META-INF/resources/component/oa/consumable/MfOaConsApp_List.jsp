<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
					<div class=" col-md-1 column">
						<dhcc:pmsTag pmsId="oa-consapp-add-btn">
							<button type="button" class="btn btn-primary pull-left"id="consAppInsert">新增申领</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=申领人/资产名称"/>
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
                  "optName": "操作人",
                  "parm": [],
                  "optCode":"opName",
                  "dicType":"val"
              }, {
				"optCode" : "operateType",
				"optName" : "操作类型",
				"parm" : ${consOperationTypeJsonArray},
				"dicType" : "y_n"
			}, {
                  "optName": "状态",
                  "optCode":"operateState",
                  "parm": ${consOperationStateJsonArray},
                  "dicType":"y_n"
              }, {
              		"optName" : "申领时间",
					"parm" : [],
					"optCode" : "createTime",
					"dicType" : "num"
              }
          ];
     </script>
</html>
