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
			var pledgeWarning = "${pledgeWarning}";
			var outFlag = '<%=outFlag%>';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pledgeBaseInfo/getPledgeToDateAjax?pledgeWarning="+pledgeWarning+"&warningType="+outFlag,//列表数据查询的url
			    	tableId:"tablepledgetodatewarning0001",//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
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
			 function getById(obj,url){
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
				<div class="btn-div top-title">押品到期预警</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=押品名称/客户名称"/>
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
		filter_dic = [
			  {
                  "optName": "筛选条件",
                  "parm": ${scopeJsonArray},
                  "optCode":"scope",
                  "dicType":"y_n"
              }];
            $(document).ready(function(){
            	var changeFlag = false;
	            var timer = null;
            	if("0"== outFlag){
					timer = window.setTimeout(function selectedFilter(changeFlag){
		          		if(changeFlag){
							window.clearTimeout(timer);//清除时间间隔的设置
						}
						if($("#my_filter_1")){
						   $('#my_filter_3_a').trigger("click");
						    changeFlag = true ;
						}
					},1000);
            	}

			})
	</script>
</html>
