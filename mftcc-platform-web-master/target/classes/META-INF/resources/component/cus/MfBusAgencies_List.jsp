<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<script src="${webPath}/component/cus/js/MfBusAgencies.js"></script>
		<script type="text/javascript">
			var tableId = "${tableId}";
			var itemName = "${itemName}";
			var showName = "${showName}";
			$(function(){
				MfBusAgencies.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
				<div  class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary pull-left" onclick="finForm_input('${webPath}/mfBusAgencies/input');">新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">${itemName}</span>
					</div>
				</div>
					<!-- 我的筛选选中后的显示块 -->
					<div class="search-div" id="search-div">
							<!-- begin -->
								<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=${itemName}"/>
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
		filter_dic = [
		      {
                  "optName": "${itemName}",
                  "parm": [],
                  "optCode":"agenciesName",
                  "dicType":"val"
              }];
	</script>	
</html>
