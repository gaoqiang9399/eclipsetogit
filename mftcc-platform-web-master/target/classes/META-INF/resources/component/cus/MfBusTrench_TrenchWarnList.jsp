<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript">
			$(function(){
				myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/mfBusTrench/findTrenchWarnByPageAjax",//列表数据查询的url
				    	tableId:"tabletrenchWarnList",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	pageSize:30//加载默认行数(不填为系统默认行数)
				 });
			});
			//调视角详情页面
			function getViewCommon(url){
				window.location.href=webPath+url;
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
				<div  class="btn-div">
					<!-- <div class="col-md-2">
						<button type="button" class="btn btn-primary pull-left" onclick="finForm_input('MfBusTrenchAction_input.action');">新增</button>
					</div> -->
					<div class="col-md-8 text-center">
						<span class="top-title">渠道额度预警</span>
					</div>
				</div>
					<!-- 我的筛选选中后的显示块 -->
					<div class="search-div" id="search-div">
							<!-- begin -->
								<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=渠道名称/联系人"/>
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
           		"optName" : "授信总额",
				"parm" : [],
				"optCode" : "creditAmt",
				"dicType" : "num"
              },{
             		"optName" : "授信余额",
  				"parm" : [],
  				"optCode" : "creditBal",
  				"dicType" : "num"
                },{
             		"optName" : "预警比例",
      				"parm" : [],
      				"optCode" : "warningRate",
      				"dicType" : "num"
                }];
	</script>	
</html>
