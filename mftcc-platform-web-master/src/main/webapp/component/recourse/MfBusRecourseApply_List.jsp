<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src='${webPath}/component/recourse/js/MfBusRecourseConfirm_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/compensatory/js/MfBusCompensatory.js'></script>
		<script type="text/javascript" >
			var cuslendDays = "${cuslendDays}";
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusRecourseApply/findByPageAjax?cuslendDays="+cuslendDays,//列表数据查询的url
			    	tableId:"tablebusrecourseapply",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
               // MfBusRecourseConfirmDetail.getSts();
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
			<div class="col-md-12 column">
				<div class="btn-div top-title">追偿查看</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder= 客户名称"/>
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
</html>
