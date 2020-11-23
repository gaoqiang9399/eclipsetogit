<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/wkf/js/wkfBusinessConfig.js" ></script>	
		<script type="text/javascript">
			$(function() {
				myCustomScrollbar({
					obj : "#content",//页面内容绑定的id
					url:webPath+"/wkfBusinessConfig/findByPageAjax",//列表数据查询的url
					tableId : "tablewkf0010",//列表数据查询的table编号
					tableType : "thirdTableTag",//table所需解析标签的种类
					myFilter : true,//是否有我的筛选
					data:{},//指定参数
			    	callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"sts"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
				});
			});
		</script>
		<style type="text/css">
		.table_content .ls_list tbody tr.selected{
			background-color:#e0e0e0;
			}
		</style>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="WkfBusinessConfig_ListPage"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="func_input('${webPath}/wkfBusinessConfig/inputAjax')">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
								<li class="active">工作流业务配置 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
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
</html>