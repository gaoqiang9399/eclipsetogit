<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>

		<script type="text/javascript" >
			/* function getPleInfo(){
				alert(appId);
				window.location.href="MfBusPledgeAction_getPledgeById.action?pleId="+appId;
			} */
		
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusPledge/findByPageAjax",//列表数据查询的url
			    	tableId:"tablequerypledge0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 
			 //异步删除
			 function ajaxDelete(obj,url){
			 	alert("是否确认删除!",2,function(){
									 		$.ajax({
											url:url,
											type:'post',
											dataType:'json',
											success:function(data){
												if(data.flag=="success"){
													window.top.alert(data.msg, 1);
													window.location.href=webPath+"/mfBusPledge/getQueryListPage";
												}if(data.flat=="error"){
													window.top.alert(data.msg, 1);
												}
											},
											error:function(data){
												window.top.alert(data.msg, 1);
											}
										});
									 },
									 function(){
									 }
				);
			 	
			 }
			 
			 function getDetailPledge(obj,url){
			 	top.openBigForm(url+"&flag=show","押品详情", function(){});
			 }
			 
			 function getDetailCustomer(obj,url){
			 	window.location.href=url.split("&")[0];
			 }
			 
		</script>
	</head>
	<body>
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="<%=webPath %>/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/押品名称"/>
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
                  "optName": "押品名称",
                  "parm": [],
                  "optCode":"pledgeName",
                  "dicType":"val"
              }, {
                  "optName": "类别名称",
                  "parm": [],
                  "optCode":"className",
                  "dicType":"val"
              }, {
                  "optName": "押品所有人",
                  "parm": [],
                  "optCode":"cusName",
                  "dicType":"val"
              }, {
				"optCode" : "pledgeSts",
				"optName" : "押品状态",
				"parm" : ${pleStsJsonArray},
				"dicType" : "y_n"
			},{
				"optCode" : "pledgeMethod",
				"optName" : "反担保类型",
				"parm" : ${vouTypeJsonArray},
				"dicType" : "y_n"
			}, {
            		"optName" : "反担保金额",
					"parm" : [],
					"optCode" : "guaAmt",
					"dicType" : "num"
              },{
            		"optName" : "入库日期",
					"parm" : [],
					"optCode" : "importDate",
					"dicType" : "date"
              }];
	</script>
</html>
