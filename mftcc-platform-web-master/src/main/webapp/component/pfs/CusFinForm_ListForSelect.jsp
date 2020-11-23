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
			    	obj:"#content",//页面内容绑定的id
			    	url:"${webPath}/cusFinForm/findByPageAjax1",//列表数据查询的url
			    	tableId:"tablepfs2001select",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{},//指定参数
			    	callback:function(){
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			
			function enterClick(lk){
				//因为财务指标描述的格式为A=B+C，这样直接通过解析参数获取信息就会有问题
				//这里是获取到财务指标的编号，再通过查询获取所有信息
				var url = '${webPath}' + lk;
				$.ajax({
					url:url,
					type:"POST",
					dataType:"json",
					success:function(data){
						if(data.flag == "success"){
							parent.dialog.get("selectCusFinFormDialog").close(data.cusFinForm);
						}else{
							alert("获取财务指标信息出错",0);
						}
					},error:function(){
						alert("获取财务指标信息出错",0);
					}
				});
				//parent.dialog.get("selectCusFinFormDialog").close(cusFinForm);
			};
		</script>
	</head>
<body class="body_bg" style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-5 column">
					</div>
					<div class="col-xs-7 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
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
</body>	
</html>
