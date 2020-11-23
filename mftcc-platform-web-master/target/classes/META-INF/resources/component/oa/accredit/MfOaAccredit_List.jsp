<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		 <link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class=" col-md-3 column" >
						<dhcc:pmsTag pmsId="oa-work-accredit-btn">
							<button type="button" class="btn btn-primary"id="accreditInsert">工作托管</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="oa-accredit-history-btn">
							<button type="button" class="btn btn-primary"id="accreditHis">托管历史</button>
						</dhcc:pmsTag>
					</div>
					<div class="col-md-7 column">
						<div class="breadcrumb pull-left leave-evaluation">
							<span class="active li">您曾托管 <span class="leave-sum">${map.total}</span> 次,最长托管 <span class="leave-sum">${map.maxSum}</span> 天,最值得信赖的同事 <span class="leave-sum">${map.maxAgentName}</span></span>
						</div> 
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
	</div>
</body>
	<script type="text/javascript" src="${webPath}/component/oa/accredit/js/MfOaAccreditList.js"></script>
	<script type="text/javascript" >
		var authorizerNo="${authorizerNo}";
		var map = "${map}";
		var url="${webPath}/sysUser/getByIdAjax?opNo="+authorizerNo+"&query=query";
		$(function(){
			mfOaAccreditList.init();
		});	
	</script>
</html>