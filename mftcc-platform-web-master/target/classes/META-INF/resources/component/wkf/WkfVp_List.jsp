<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/wkf/js/wkfVpRightForm.js" ></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/wkfVp/findByPageAjax?wkfVpNo=${wkfVpNo}",//列表数据查询的url
			    	tableId:"tablewkf4001",//列表数据查询的table编号
			    	tableType:"thirdTableTag"//table所需解析标签的种类
			    });
			 });
		</script>
		<style type="text/css">
			.rightForm-table .formCol textarea[name='wkfVpMenuUrl'],
			.rightForm-table .formCol textarea[name='wkfVpRemark']{
				width: 440px;
				resize:none;
			}
		</style>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >${wkfVpName}-视角菜单配置</strong>
				<!--我的筛选按钮-->
				<div class="search-group">
					<div class="search-lable" id="pills">
					<dhcc:thirdButton value="新增" action="WkfVp001" onclick="func_input('${webPath}/wkfVp/inputAjax?wkfVpNo=${wkfVpNo}&wkfVpName=${wkfVpName}')"></dhcc:thirdButton>
						<!-- <div class="filter-btn-group">
							<input id="filter_in_input" placeholder="智能搜索" class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search" class="filter_btn_search" type="button">
									<i class="i i-fangdajing"></i>
								</button>
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
	</body>	
</html>
