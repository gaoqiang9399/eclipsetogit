<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<%-- <script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/pfs/js/cusFinForm.js"></script> --%>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cusFinForm/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepfs2001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{}//指定参数
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="finForm_input('/cusFinForm/input');">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/mfCusFormConfig/getMfCusConfig">客户设置</a></li>
								<li class="active">财务指标 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=公式名称/公式编号"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
					</div>
				</div>
			</div>
		</div>
	</body>	
	<script type="text/javascript">
	function finForm_input(url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.addFlag = false;
   		top.window.openBigForm(url,'指标配置',function(){
   			if(top.addFlag){
   				window.location.reload();
   			}
   		});
	};
	function form_getById(oj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.updateFlag = false;
   		top.window.openBigForm(url,'指标配置',function(){
   			if(top.updateFlag){
   				window.location.reload();
   			}
   		});
	};
	</script>
</html>
