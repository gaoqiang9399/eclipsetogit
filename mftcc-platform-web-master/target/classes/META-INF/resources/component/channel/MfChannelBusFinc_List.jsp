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
			    	url:webPath+"/mfChannelBus/findChannelBusFincByPageAjax",//列表数据查询的url
			    	tableId:"tablechannelBusFinc0001",//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{fincSts:"5"}//指定参数 (过滤掉已经封挡的数据)
			    });
			 });
			 
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
			};
			
			function openUpload(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				} 
				/* window.location.href=url;
				event.stopPropagation(); */
				top.window.openBigForm(url,"上传银行还款",function(){
					updateTableData(true);
				});
			};
			
			var exportPlanList = function(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				var cusNo = url.split("?")[1].split("&")[1].split("=")[1];
				var downType = "5";
				LoadingAnimate.start();
				$.ajax({
					//url:webPath+"/mfRementpayPlan/exportExcelAjax",
					url:url,
					data:{
						//downType:downType,
						//pactId:pactId
					},
					dataType:"json",
					type:"POST",
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							if(data.exportFlag == "success"){
								window.top.location.href = encodeURI(webPath+"/docUpLoad/getFileDownload_new?path="+data.path+"&cusNo="+cusNo+"&downType="+downType);
							}else{
								alert(data.msg,0);
							}
						}else{
							alert(data.msg,0);
						}
					},error:function(){
						LoadingAnimate.stop();
						alert(data.msg,0);
					}
				});
			};
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/合同号码/项目名称"/>
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
	
</html>
