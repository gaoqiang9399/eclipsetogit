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
			    	url:webPath+"/mfBusPact/findBankRepayByPageAjax",//列表数据查询的url
			    	tableId:"tablepactBankRepayList",//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pactSts:"8"}//指定参数 (过滤掉已经封挡的数据)
			    });
			 });
			 
			/**跳转至还款页面**/
			/* function toRepayment(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				location.href = url;
			  	event.stopPropagation();
			} */
			
			function bankForReay(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				} 
				/* window.location.href=url;
				event.stopPropagation(); */
				top.window.openBigForm(url,"银行还款",function(){
					updateTableData(true);
				});
			}
			
			var timeFunc=null;
			//监听ctrl键
			document.onkeydown=function(event){
				var e = event || window.event || arguments.callee.caller.arguments[0];
				//若点击了ctrl 键则 清除timeFunc
				if(e && e.ctrlKey){ 
					clearTimeout(timeFunc);
				}
			}; 
			
			function trClick(url){		
				clearTimeout(timeFunc);
				timeFunc=setTimeout(function(){
					top.LoadingAnimate.start();	
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					window.location.href=url;
				}, 300);
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
		filter_dic = [
	       {
				"optName" : "产品种类",
				"parm" : ${kindTypeJsonArray},
				"optCode" : "kindNo",
				"dicType" : "y_n"
			},{
                  "optName": "合同金额",
                  "parm": [],
                  "optCode":"pactAmt",
                  "dicType":"num"
              },{
                  "optName": "放款金额",
                  "parm": [],
                  "optCode":"putoutAmt",
                  "dicType":"num"
              },{
                  "optName": "合同期限",
                  "parm": [],
                  "optCode":"term",
                  "dicType":"num"
              },{
             	  "optCode" : "busStage",
				  "optName" : "办理阶段",
				  "parm" : ${flowNodeJsonArray},
				  "dicType" : "y_n"
              },{
                  "optName": "合同开始日期",
                  "parm": [],
                  "optCode":"beginDate",
                  "dicType":"date"
              },{
                  "optName": "放款申请日期",
                  "parm": [],
                  "optCode":"currPutoutAppDate",
                  "dicType":"date"
              }
          ];
          
	</script>
</html>
