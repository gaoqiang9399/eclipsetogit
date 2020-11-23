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
			    	url:webPath+"/mfBusPact/findReturnFeeListAjax",//列表数据查询的url
			    	tableId:"tablepactfincreturnfeeL001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pactSts:"8"}//指定参数，过滤掉已经封挡的数据
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
			function finForm_input(url){//
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.createShowDialog(url,"返费计划",'90','90',function(){
				});
			}	 
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 colum">
				<div class="btn-div top-title">返费查询</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名/项目名/渠道商"/>
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
             	  "optCode" : "fincSts",
				  "optName" : "业务阶段",
				  "parm" : ${fincStsJsonArray},
				  "dicType" : "y_n"
              },{
                  "optName": "开始日期",
                  "parm": [],
                  "optCode":"beginDate",
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
              },{
                  "optName": "渠道商名称",
                  "parm": [],
                  "optCode":"channelSource",
                  "dicType":"val"
              },{
                  "optName": "渠道商编号",
                  "parm": [],
                  "optCode":"channelSourceNo",
                  "dicType":"val"
              },
          ];
// 		  addDefFliter("0","合同状态","pactSts","PACT_STS","0,1,2,3,4,5,6");
          
	</script>
</html>