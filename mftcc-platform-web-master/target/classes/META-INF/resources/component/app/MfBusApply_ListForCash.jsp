<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="<%=webPath %>/component/app/js/MfBusApply_ListForCash.js"></script>
<script type="text/javascript">
	$(function() {
		var dataType = '${dataType}';
		mfBusApplyList.init(dataType);
	});
	
	function getDetailPage(obj,url){		
		mfBusApplyList.getDetailPage(obj,url);
	}

	function ajaxInprocess(obj, ajaxUrl) {
		mfBusApplyList.ajaxInprocess(obj,ajaxUrl);
	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=客户名称/项目名称"/>
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
<script type="text/javascript">
	
</script>
</html>
