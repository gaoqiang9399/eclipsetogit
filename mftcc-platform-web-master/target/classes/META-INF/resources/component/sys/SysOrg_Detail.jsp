<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
		var pId = ${pId};
		var opNoType = ${opNoType};
		var ajaxData ="";
		</script>
		<script src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"
	type="text/javascript"></script>
		<script type="text/javascript" src='${webPath}/component/sys/js/sysOrg.js'> </script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
            <c:choose>
				<c:when test="${ !empty ifcompany }">
                <div class="col-md-10 col-md-offset-1 colum margin_top_20">
				</c:when>
				<c:otherwise >
                 <div class="col-md-8 col-md-offset-2 column margin_top_20">
				</c:otherwise>
			</c:choose>
                     <div class="bootstarpTag">
                        <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                        <form method="post" id="updateSysOrgForm" theme="simple" name="operform" action="${webPath}/sysOrg/updateAjax">
                            <dhcc:bootstarpTag property="formsys6002" mode="query"/>
                        </form>
                     </div>
                 </div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="saveSysOrg('#updateSysOrgForm');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
	   	</div>
	</body>
</html>