<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/sys/css/sysUser.css" />
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/sysUser/findByPageForNoticeAjax",//列表数据查询的url
			    	tableId:"tablesys5005",//列表数据查询的table编号
			    	tableType:"thirdTableTag"//table所需解析标签的种类
			    });
			    
			 });
			function pushNoticeForOpNo(obj,url){
				window.top.createShowDialog(url,'系统通知新增','50','75',false);
			}
		</script>
	</head>
	<body class="bodybg-gray">
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >用户列表</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<%-- <dhcc:thirdButton value="推送系统通知" action="推送系统通知" onclick="window.top.createShowDialog('${webPath}/sysNotice/input?opNo=ALL&brNo=ALL&roleNo=ALL','系统通知新增','50','75',true)"></dhcc:thirdButton> --%>
					<input type="button" value="推送系统通知" onclick="window.top.createShowDialog('${webPath}/sysNotice/input?opNo=ALL&brNo=ALL&roleNo=ALL','系统通知新增','50','75',true)">
				<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input placeholder="智能搜索" id="filter_in_input"  class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search"  class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
							<button id="fiter_ctrl_btn"  class="filter_btn_myFilter" type="button"  ><i class="i i-jiantou7"></i></button>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript">
		addDefFliter("0","机构名称","brNo","ORG_NO");
		</script>
	</body>	
</html>
