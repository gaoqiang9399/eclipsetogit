<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="${webPath}/component/hzey/js/MfBusIcloudManage.js"></script>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			   getList();
			 });
			 function getList(){
			 	myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfBusIcloudManage/findByPageAjax", //列表数据查询的url
				tableId : "tablehzey0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				,topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
			 }
			 filter_dic = [
			  {
                  "optName": "筛选条件",
                  "parm": ${jsonArray},
                  "optCode":"status",
                  "dicType":"y_n"
              }];
			 function toAddIcloud() {
				 top.window.openBigForm(webPath+"/mfBusIcloudManage/input",null,getList);
			}
			function toUpdate(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				MfbusIcloudManage.toUpdatePage(obj,url);
			}
			function unBindAjax(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				MfbusIcloudManage.unBindAjax(obj,url);
			}
			function updateCifInfoAjax(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				MfbusIcloudManage.updateCifInfoAjax(obj,url);
			}
			function getCifInfo(date){
				var cusNo = date.cusNo;
				var cusName = date.cusName;
				var url = $("#ajaxurl").val();
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				url =url+"&cusNo="+cusNo+"&cusName="+cusName;
				$.ajax({
			        type: "get",
			        url: url,
			        success: function(data) {
			        	window.location.href=webPath+"/mfBusIcloudManage/getListPage";
			        },
			        error: function() {
			            alert(top.getMessage("FAILED_DELETE"),0);
			        }
			    });
			}
			
		</script>
	</head>
	<input id="ajaxurl" type="hidden" value="" />
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="toAddIcloud();">新增</button>
	  				</div>
	  				<div class="col-md-8 text-center">
	  					<span class="top-title">icloud管理</span>
	  				</div>
	  				<div class="col-md-2">
	  				</div>
				</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=icloud账号/绑定客户姓名"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
</html>
