<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
		<title>列表</title>
		<script type="text/javascript" >
		var fincId='${fincId}';
		var appId='${appId}';
		var cusNo='${cusNo}';
		var pactId='${pactId}';
		var channalSource='${channelSource}';
		var channalSourceNo='${channelSourceNo}';
			$(function(){
			$("#channalSource").html(channalSource);
			$("#channalSourceNo").html(channalSourceNo);
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusFeePlan/findReturnFeeByPageAjax",//列表数据查询的url
			    	tableId:"tabletablereturnfeeplan001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{fincId:fincId,appId:appId,cusNo:cusNo,pactId:pactId}//指定参数 (过滤掉已经封挡的数据)
			    });
			 });
			 
			/**跳转至还款页面**/
			function toRepayment(obj,url){
			  	location.href = url;
			}
			/**返费按钮**/
			function returnFee(url){		
				$.ajax({
					url:url,
					dataType:'json',
					type:'POST',
					success:function(data){
						if(data.flag=="success"){
							window.top.alert(data.msg, 1);
							updateTableData();//重新加载列表数据
						}else{
							window.top.alert(data.msg, 0);
						}
					},error:function(){
						window.top.alert("访问"+url+"失败！");
					}
				})
			}
				 
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class=" col-md-3 column" >
					</div>
					<div class="col-md-7 column">
						<div class="breadcrumb pull-left leave-evaluation">
							<span class="active li">渠道商：<span class="leave-sum" id="channalSource"></span>&nbsp;&nbsp;渠道商编号：<span class="leave-sum" id="channalSourceNo"></span></span>
						</div>	
					</div>				
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
</body>
</html>
