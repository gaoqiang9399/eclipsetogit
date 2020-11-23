<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var cusNo = '${cusNo}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusPact/findByPageAjaxForTrh?cusNo=" + cusNo,//列表数据查询的url
			    	tableId:"tablepact0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 
			/**跳转至还款页面**/
			function toRepayment(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			  	location.href = url;
			}
			
			function getDetailPage(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.LoadingAnimate.start();		
				window.location.href=url;			
			}
				 
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
                  "optName": "合同金额",
                  "parm": [],
                  "optCode":"pactAmt",
                  "dicType":"num"
              }, {
                  "optName": "签订日期",
                  "parm": [],
                  "optCode":"signDate",
                  "dicType":"date"
              }, {
                  "optName": "合同期限",
                  "parm": [],
                  "optCode":"term",
                  "dicType":"num"
              },{
             	  "optCode" : "pactSts",
				  "optName" : "业务阶段",
				  "parm" : [ {
					 "optName" : "未完善",
					 "optCode" : "0"
				   }, {
					 "optName" : "未提交",
					 "optCode" : "1"
				   }, {
					 "optName" : "流程中",
					 "optCode" : "2"
				  }, {
					 "optName" : "退回",
					 "optCode" : "3"
				  }, {
					 "optName" : "审批通过",
					 "optCode" : "4"
				  }, {
					 "optName" : "已否决",
					 "optCode" : "5"
				  }, {
					 "optName" : "已完结",
					 "optCode" : "6"
				  } ],
				  "dicType" : "y_n"
              },{
                  "optName": "开始日期",
                  "parm": [],
                  "optCode":"beginDate",
                  "dicType":"date"
              },
          ];
// 		  addDefFliter("0","合同状态","pactSts","PACT_STS","0,1,2,3,4,5,6");
          
	</script>
</html>