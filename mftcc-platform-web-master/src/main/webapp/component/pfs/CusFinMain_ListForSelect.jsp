<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<style type="text/css">
			.message{
				text-align:center;
			}
			.bigform_content{
				padding-top: 0px;
			}
		</style>
	</head>
	<body class="body_bg" >
	<div class="bigform_content">
		<c:if test="${fn:length(cusFinMainList) ==0}">
			<div  class="message" >暂无财务报表信息</div>
		</c:if>
		<c:if test="${fn:length(cusFinMainList) !=0}">
			<div class="content_table">
				<dhcc:tableTag paginate="cusFinMainList" property="tablepfs0001" head="true" />
			</div>
		</c:if>
    </div>
	</body>	
	<script type="text/javascript" >
		function enterClick(lk){
			var parm=lk.split("?")[1];
			var parmArray=parm.split("&");
			var finMain = new Object();
			finMain.rptType = parmArray[0].split("=")[1];
			finMain.rptDate = parmArray[1].split("=")[1];
			finMain.accountId = parmArray[2].split("=")[1];
			var artdialog = parent.dialog.get("selectFinMainDialog");
			artdialog.close(finMain);
		};
        $(".bigform_content").mCustomScrollbar({
            advanced:{
                updateOnBrowserResize:true
            },
            autoHideScrollbar: true
        });
	</script>
</html>
