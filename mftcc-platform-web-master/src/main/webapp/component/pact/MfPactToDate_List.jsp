<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<% 
String outFlag = request.getParameter("outFlag");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var pactWarning = "${pactWarning}";
			var repayWarning = "${repayWarning}";
			var outFlag = '<%=outFlag%>';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusFincApp/getPactToDateAjax?pactWarning="+pactWarning+"&repayWarning="+repayWarning+"&warningType="+outFlag,//列表数据查询的url
			    	tableId:"tablequerypacttodate",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
			 function getDetailRepayPlan(obj,url){
				 if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
			 	window.location.href=url;
			 }
			 function getDetailCus(obj,url){
				 if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
			 	window.location.href=url;
			 }
			
			 //filterOnClick(null,"my_filter",nodeObj);
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div top-title">框架合同到期</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=项目名称/客户名称"/>
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
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		filter_dic = [{
                  "optName": "合同到期日期",
 				  "optCode":"pactEndDate",
                  "dicType":"date"
              },{
                  "optName": "还款到期日期",
                  "optCode":"planEndDate",
                  "dicType":"date"
              }];
            $(document).ready(function(){
            	var changeFlag = false;
	            var timer = null;
            	if("0"== outFlag){//还款逾期预警
					timer = window.setTimeout(function selectedFilter(changeFlag){
		          		if(changeFlag){
							window.clearTimeout(timer);//清除时间间隔的设置
						}
						if($("#my_filter_3")){
						   //$('#my_filter_3_a').trigger("click");
						   $("#my_filter_1").removeClass("mySelectedNode");//去除全部
						   $("#my_filter_3").addClass("mySelectedNode");
						    changeFlag = true ;
						}
					},200);
            	}else if("1"== outFlag){//贷后逾期预警
            		timer = window.setTimeout(function selectedFilter(changeFlag){
		          		if(changeFlag){
							window.clearTimeout(timer);//清除时间间隔的设置
						}
						if($("#my_filter_2")){
						   //$('#my_filter_2_a').trigger("click");
						   $("#my_filter_1").removeClass("mySelectedNode");//去除全部
						   $("#my_filter_2").addClass("mySelectedNode");
						    changeFlag = true ;
						}
					},200);
            	}
            	

			})
	</script>
</html>
