<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
        <script type="text/javascript" src='${webPath}/component/auth/js/MfCreditQueryApp_List.js'></script>
        <script type="text/javascript" >
            var cusNo = "${cusNo}";
			$(function(){
                MfCreditQueryApp_List.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
                    <c:if test="${count == 0}">
                        <div class="btn-div">
                            <button type="button" class="btn btn-primary" onclick="MfCreditQueryApp_List.addCreditQueryApp();">新增申请</button>
                        </div>
                    </c:if>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
