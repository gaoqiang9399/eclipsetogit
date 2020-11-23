<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
		var cusType = '${cusType}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusCustomer/findByPageAjax",//列表数据查询的url
			    	tableId:"tablecus00001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{cusType:cusType},//指定参数
			    	callback:function(){
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			function choseCus(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var cusNo = parmArray[0].split("=")[1];
				$.ajax({
					url:webPath+"/mfCusCustomer/getCusByIdAjax?cusNo="+cusNo,
					dataType:"json",
					type:"POST",
					success:function(data){
						if(data.flag == "success"){
							parent.dialog.get('cusDialog').close(data.cusInfo);
						}else{
							alert("获取客户信息失败");
						}
					},error:function(){
						alert("获取客户信息出错");
					}
				});
			};
		</script>
	</head>
	<body class="bodybg-gray" style="background: white;">
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
			<div class="searchCont">
			<div class="search-input-dialog">
				<!--我的筛选按钮-->
				<div class="filter-btn-group">
					<!--自定义查询输入框-->
					<input placeholder="智能搜索" id="filter_in_input" class="filter_in_input" type="text" />
					<div class="filter-sub-group">
						<button id="filter_btn_search" class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
					</div>
				</div>
			</div>
			</div>
		</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		
		</div>
		
	</body>	

</html>
