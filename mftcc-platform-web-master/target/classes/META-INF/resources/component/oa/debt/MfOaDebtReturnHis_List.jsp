<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfOaDebtReturnHis/findByPageAjax",//列表数据查询的url
			    	tableId:"tablereturndebt0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight : true,
				    callback:function(){
			    		// 加载数据为空。
						if($('#content tbody tr').length == 0){
						var emptyHtml = '<tr ><td style="text-align: center;" colspan="7">暂无数据</td></tr>';
							$('#content tbody').append(emptyHtml);
						}
					}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >借款归还历史表</strong>
				<!--我的筛选按钮-->
				<div class="filter-btn-group">
					<!--自定义查询输入框-->
					<input placeholder="智能搜索"  class="filter_in_input" type="text" />
					<div class="filter-sub-group">
						<button  class="filter_btn_search" type="button" ><i class="fa fa-search"></i></button>
						<button id="fiter_ctrl_btn" class="filter_btn_myFilter" type="button"  ><i class="fa fa-caret-down"></i></button>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
	</body>	
</html>
