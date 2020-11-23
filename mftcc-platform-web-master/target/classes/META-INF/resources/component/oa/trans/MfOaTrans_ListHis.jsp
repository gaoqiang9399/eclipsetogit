<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
				<%--<c:if test="${typeFlag =='1'}">--%>
					<div class="col-md-12 column">
						<div class="btn-div top-title">移交历史</div>
						<div class="search-div">
							<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/移交人/接收人"/>
						</div>
					</div>
				<%--</c:if>--%>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	<%-- <%@ include file="/component/include/PmsUserFilter.jsp"%> --%>
</body>
<script type="text/javascript" src="${webPath}/component/oa/trans/js/MfOaTransListHis.js?v=${cssJsVersion}"> </script>
<script type="text/javascript" >
var typeFlag = '${typeFlag}';
var cusNo = '${cusNo}';
$(function(){
	MfOaTransListHis.init();
 });			
</script>
</html>
