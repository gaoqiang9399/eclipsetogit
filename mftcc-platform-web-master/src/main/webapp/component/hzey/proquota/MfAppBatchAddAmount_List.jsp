<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
					obj : "#content", //页面内容绑定的id
					url : webPath+"/mfBusPact/findPactBatchByPageAjax", //列表数据查询的url
					tableId : "tableaddamtpact0001", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:30, //加载默认行数(不填为系统默认行数)
					topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
					//如果是所有客户，则关闭超链接
					callback:function(){
						//每次加载后执行，如果a标签的内容未所有客户，则取消超链接
						$("body").find("a").each(function(e,obj){
							var cusName = $(obj).text();
							if(cusName=="所有客户"){
								$(obj).removeAttr('href');
								$(obj).removeAttr('onclick');
								$(obj).css({
									'color':'#575757',
									'cursor':'default',
									'text-decoration':'none'						
								});
							}
						});
					}
			    });
				//去除表头 点击事件 换成 全选事件
				var isCheckAll = false;
				function addCheckAllEvent() {
				 	$(".table-float-head").delegate("th:first-child","click",function(){
						if (isCheckAll) {
							$(".review_list").find(':checkbox').each(function() {
								this.checked = false;
							});
							isCheckAll = false;
						} else {
							$(".review_list").find(':checkbox').each(function() {
								this.checked = true;
							});
							isCheckAll = true;
						}
					});
				} 
			
			/*我的筛选加载的json*/
			filter_dic = [
		      	  {
	                  "optName": "合同金额",
	                  "parm": [],
	                  "optCode":"pactAmt",
	                  "dicType":"num"
	              },{
	                  "optName": "放款次数",
	                  "parm": [],
	                  "optCode":"putoutCount",
	                  "dicType":"num"
	              },{
	             	  "optCode" : "overdueSts",
					  "optName" : "是否逾期",
					  "parm" : ${overdueStsJsonArray},
					  "dicType" : "y_n"
	              }
	          ];
	        addCheckAllEvent();
			    
		});
		 
		 function getCusDetailPage(obj,url){
			if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
			top.openBigForm(url,"客户信息",function(){});
		 }
		 function getBusDetailPage(obj,url){
			if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
			top.openBigForm(url,"客户业务信息",function(){});
		 }
		  function getAmtDetailPage(obj,url){
			if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
			top.openBigForm(url,"合同提额历史记录",function(){});
		 }
		</script>
	</head>
	<body class="overflowHidden">
		
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" id="batchRe_btn" onclick="mfAppBatchAddAmount.toBatchAddAmt();">批量提额</button>
					</div>
					<div class="search-title hidden"><span value=""></span></div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content review_list"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/hzey/proquota/js/mfAppBatchAddAmount.js"></script>
	</body>
</html>
