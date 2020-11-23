<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/sys/js/sysGlobalSearch.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" >
			var myCustomScrollbarOpt;
			$(function(){
			    myCustomScrollbarOpt = myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/sysGlobalSearch/findByPageAjax",//列表数据查询的url
			    	tableId:"tablesys9999",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{},//指定参数
			    	callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"sts"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
		</script>
		<style type="text/css">
			.formCol {
			    display: block;
			    margin-bottom: 2px;
			    margin-top: 1px;
			}
			.rightForm-table .formCol textarea {
				height: 25px;
				width: 90%;
				resize:none;
				padding:0 0 0 3px;
			}
			.rightForm-table .formCol input[type='text'][name='url']{
				width: 90%;
			}
		</style>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="SysGlobalSearch_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="func_input('${webPath}/sysGlobalSearch/inputAjax');">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
							<li class="active">全局搜索配置 </li>
						</ol>
					</div>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=配置编号/表名"/>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
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