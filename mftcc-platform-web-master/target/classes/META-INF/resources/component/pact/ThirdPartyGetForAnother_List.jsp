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
					obj : "#content", //页面内容绑定的id
					url:webPath+"/thirdPartyGetForAnother/findByPageAjax", //列表数据查询的url
					tableId : "tablethirdPartyGet0001", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:30, //加载默认行数(不填为系统默认行数)
					callback : function () {//模糊查询回调
						$("th[class=table-float-th]").eq(0).html('<a href="javascript:void(0);" onclick="checkAllPayDetail()">全选</a>');
					}
				});
			    $("th[class=table-float-th]").eq(0).html('<a href="javascript:void(0);" onclick="checkAllPayDetail()">全选</a>');			    
			 });
			
			function checkAllPayDetail(){
		   		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
					if($(this).prop("checked")){
						$(this).prop("checked",false);
					}else{
						$(this).prop("checked",true);
					}
		    	 });
		   	}
		   	
		   	function getDetailPage(obj,url){		
				top.openBigForm(url,"客户详情", function(){
			 		getListInfo();
			 	});			
			};
			
			function getAppDetailPage(obj,url){	
				url = url.split("?")[1].split("&");	
			    var appId = url[1].split("=")[1];
		    	var fincId = url[0].split("=")[1];
		    	var busEntrance = "3";
		    	url = webPath+"/mfBusPact/getPactFincById?fincId="+ fincId +"&appId="+ appId +"&busEntrance="+ busEntrance +"&subStringNub=";
				top.openBigForm(url,"项目详情", function(){
			 		getListInfo();
			 	});				
			};
			
			function exportExcel(){
				var exportFincIdStr ="";
				$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
					val = this.value.split('=') [1] ;
		    	    exportFincIdStr+=val+",";
		    	});
		    	if(exportFincIdStr.length==0){
		    		alert(top.getMessage("FIRST_SELECT_FIELD","导出的数据"), 0);
		    		return;
		    	}else{
		    		exportFincIdStr = exportFincIdStr.substring(0,exportFincIdStr.length-1);
		    	}
				LoadingAnimate.start();
				$.ajax({
					url:webPath+"/thirdPartyGetForAnother/exportListAjax",
					data : {exportFincId:exportFincIdStr},
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							window.top.location.href = encodeURI("DocUploadAction_fileDownload_fuyiou.action?path="+data.filePath+"&fuyiouType=2");
						}else{
							alert(data.msg,0);
						}
					},error:function(){
						LoadingAnimate.stop();
						alert(data.msg,0);
					}
				});
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="exportExcel();">导出</button>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
	</body>
</html>
