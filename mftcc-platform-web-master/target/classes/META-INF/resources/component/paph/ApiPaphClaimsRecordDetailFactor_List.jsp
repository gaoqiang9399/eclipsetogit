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
				obj : "#content", //页面内容绑定的id
				url:webPath+"/apiPaphClaimsRecordDetailFactor/findByPageAjax", //列表数据查询的url
				tableId : "tablepaphclaimsdetail", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 ,//加载默认行数(不填为系统默认行数)
				topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
			    myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			 });
			function getDetailPage(obj,url){		
				top.LoadingAnimate.start();		
				window.location.href=url;			
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
				<div  class="btn-div">
					<div class="col-md-8 text-center">
						<span class="top-title">理赔列表</span>
					</div>
				</div>
					<!-- 我的筛选选中后的显示块 -->
					<div class="search-div" id="search-div">
							<!-- begin -->
								<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=理赔客户名称/借据号"/>
							<!-- end -->
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
		</div>
				<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		filter_dic = [{
           		"optName" : "处理状态",
           		"parm" : [{
					"optName" : "未处理",
					"optCode" : "0"
				}, {
					"optName" : "已处理",
					"optCode" : "1"
				}],
				"dicType" : "y_n",
				"optCode" : "handleSts"
              }];
	</script>	
</html>
