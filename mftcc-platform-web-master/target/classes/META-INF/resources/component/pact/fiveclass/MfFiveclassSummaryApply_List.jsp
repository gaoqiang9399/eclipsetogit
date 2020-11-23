<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var path = "${webPath}";
			var fincId = "${fincId}";
			var fiveclassId = "${fiveclassId}";
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfFiveclassSummaryApply/findByPageAjax",//列表数据查询的url
			    	tableId:"tablequerymfiveclasssummaryapply",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
			//查看详情
			function getById(obj,url){
				top.openBigForm(webPath+url, "批量五级分类详情",function(){});
			};
			//返回
			function getBack(){
				window.location.href=webPath+"/mfFiveclassSummaryApply/getListPage"; 
			};
			function apply(){
				window.location.href=webPath+"/mfPactFiveclass/getFiveClassAndPactListPageForBatch"; 
			}
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
			<div class="btn-div">
						<dhcc:pmsTag pmsId="query-batch-five-class">
							<button type="button" class="btn btn-primary" onclick="apply();">人工认定</button>
						</dhcc:pmsTag>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=月份/申请人"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<!--页面显示区域-->
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
		filter_dic = [
		    {
                  "optName": "分类时间",
                  "parm": [],
                  "optCode":"appDate",
                  "dicType":"val"
              } ,
              {
                  "optName": "五级分类笔数",
                  "parm": [],
                  "optCode":"sumCount",
                  "dicType":"num"
              }];
	</script>
</html>
