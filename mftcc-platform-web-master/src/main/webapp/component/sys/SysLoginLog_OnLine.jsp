<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<style type="text/css">
			body{
				overflow-x:auto;
			}
			.table_content .mCSB_scrollTools{
				position: fixed !important;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
				showTable();
			 });
			function viewLog(obj,url){
				window.top.createShowDialog(url,"系统通知详情","50","75");
			}
			function showTable(){
				jQuery.ajax({
					url:webPath+"/sysOrg/getBrNoAjax",
					type:"POST",
					dataType:"json",
					beforeSend:function(){ 
					},success:function(data){
						var res = data.data;
						console.log(res);
						var s = '';
						var brNo;
						$(res).each(function(index,item){
							s += ",'"+item.brNo+"'";
						});
						s = s.substring(1);
						if(s.length==0){
							brNo = "br_no in ('000000')";
						}else{
							brNo = "br_no in ("+s+")";
						}
						myCustomScrollbar({
					    	obj:"#content",//页面内容绑定的id
					    	url:webPath+"/sysLoginLog/findByPageOnLineAjax?brNo="+brNo,//列表数据查询的url
					    	tableId:"tablesys1001",//列表数据查询的table编号
					    	tableType:"thirdTableTag",//table所需解析标签的种类
					    	pageSize:30//加载默认行数(不填为系统默认行数)
					    });
					}
				});
			}
			function Topno(obj,url){
				jQuery.ajax({
					url:url,
					type:"POST",
					dataType:"json",
					beforeSend:function(){ 
					},success:function(data){
						var res = data.flag;
						if(res = "success"){
							$.myAlert.Alert("踢出系统成功");
							showTable();
						}else{
							$.myAlert.Alert("踢出系统失败");
						}
					}
				});
			}			
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >在线人数查询</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
				<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input placeholder="智能搜索" id="filter_in_input"  class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search"  class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
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
