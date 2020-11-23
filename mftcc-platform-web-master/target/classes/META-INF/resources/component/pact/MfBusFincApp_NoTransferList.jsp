<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusFincApp/findNoTransferListByPageAjax",//列表数据查询的url
			    	tableId:"tablenotransferacclist",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 //客户名称/项目名称详情页面
		 	function getDetailPage(obj,url){		
				top.LoadingAnimate.start();	
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				window.location.href=url;			
			}
			
			//转账申请
			function transferAccApply(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.window.openBigForm(url,"转账申请",function(){
					updateTableData(true);
				});
			}
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
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
                  "optName": "借据金额",
                  "parm": [],
                  "optCode":"putoutAmt",
                  "dicType":"num"
              }, {
                  "optName": "借据期限",
                  "parm": [],
                  "optCode":"term",
                  "dicType":"num"
              },{
                  "optName": "借据到期日期",
                  "parm": [],
                  "optCode":"intstEndDate",
                  "dicType":"date"
              },{
              	 "optName" : "合同到期日期",
              	 "optCode" : "pactEndDate",
              	 "parm": [],
              	 "dicType":"date"
              },{
              	 "optCode" : "overdueSts",
				  "optName" : "逾期状态",
				  "parm" : [{
							"optName" : "未逾期",
							"optCode" : "0"
						}, {
							"optName" : "已逾期",
							"optCode" : "1"
						}],
				  "dicType" : "y_n"
              }
          ];
          
	</script>
</html>
