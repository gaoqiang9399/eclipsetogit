<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
	var agenciesUid = "${agenciesUid}";
	$(function(){
		 myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/mfBusPact/findAgenciesCusByPageAjax",//列表数据查询的url
		    	tableId:"tablepact0001",//列表数据查询的table编号
		    	tableType:"tableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	data:{agenciesUid:agenciesUid}//指定参数 (过滤掉已经封挡的数据)
		    });
	});
	/**跳转至还款页面**/
	function toRepayment(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		location.href = url;
	  	event.stopPropagation();
	}
	
	function getDetailPage(obj,url){		
		top.LoadingAnimate.start();	
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		window.location.href=url;	
		event.stopPropagation();
	}

</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
