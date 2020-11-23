<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表表单</title>
<script type="text/javascript">
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwSysParam/findByPageAjax",//列表数据查询的url
			    	tableId:"tableparamset0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });	
   			
		</script>
</head>
<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<ol class="breadcrumb pull-left">
							<li><a
								href="${webPath}/cwParamset/cwParamEntrance">设置</a></li>
							<li class="active">系统参数</li>
						</ol>
					</div>
					<!-- <div class="col-xs-9 column"></div> -->
					<div class="col-xs-3 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i> <input type="text"
								class="form-control" id="filter_in_input" placeholder="智能搜索">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--页面显示区域-->
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content" style="height: auto;">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	
	function getByIdThis(url){//详情弹框
		top.detailFlag = false;
		top.createShowDialog(webPath+"/"+url,"参数化编辑",'600px','350px',function(){
			if(top.detailFlag){
	   			window.location.reload();//刷新页面
	   		}
		});
	}
	</script>
</html>
