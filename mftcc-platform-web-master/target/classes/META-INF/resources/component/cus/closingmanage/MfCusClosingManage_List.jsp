<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusClosingManage.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url:webPath+"/mfCusClosingManage/findByPageAjax", //列表数据查询的url
				tableId : "tablemfCusClosingManage", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });


            function getDetailPage(obj,url){
                if(url.substr(0,1)=="/"){
                    url =webPath + url;
                }else{
                    url =webPath + "/" + url;
                }
                top.LoadingAnimate.start();
                window.location.href=url;
                event.stopPropagation();
            }
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="MfCusClosingManage.insertClosing();">新增</button>
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
