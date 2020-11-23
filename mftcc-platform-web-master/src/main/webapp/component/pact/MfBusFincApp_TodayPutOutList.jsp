<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
$(function(){
	myCustomScrollbar({
		obj : "#content",//页面内容绑定的id
		url:webPath+"/mfBusFincApp/getTodayPutOutListAjax",//列表数据查询的url
		tableId : "tabletodayputoutmoney",//列表数据查询的table编号
		tableType : "thirdTableTag",//table所需解析标签的种类
		pageSize : 30,//加载默认行数(不填为系统默认行数)
		topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
	});
})
function getDetailPage(obj,url){		
	top.LoadingAnimate.start();	
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	window.location.href=url;
}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=客户名称/项目名称"/>
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
<script type="text/javascript">
	
</script>
</html>
