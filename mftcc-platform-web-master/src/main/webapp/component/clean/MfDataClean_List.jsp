<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<!-- 一键清理所有 -->
						<dhcc:pmsTag pmsId="clear-all-cus-bus-button">
							<button type="button" class="btn btn-primary pull-left" onclick="DataCleanList.cleanAllData();">一键清理所有</button>
						</dhcc:pmsTag>
						<ol class="breadcrumb pull-left"></ol>
						<button type="button" class="btn btn-primary pull-left" onclick="openBrInfo();">部门清理</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础配置</a></li>
							<li class="active">数据清理</li>
						</ol>
					</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
</body>
<script type="text/javascript" src="${webPath}/component/clean/js/MfDataClean_List.js"></script>
<script type="text/javascript">
	$(function() {
		DataCleanList.init();
	});
    // 打开部门清理页面
    function openBrInfo() {
        top.window.openBigForm(webPath + '/mfDataClean/brClearInfo', '部门清理', function () {
            DataCleanList.init();
        });
    }
</script>
</html>
