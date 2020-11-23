<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
		<link rel="stylesheet" href="${webPath}/component/cus/custracing/css/MfCusTrack_List.css?v=${cssJsVersion}" />
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-11 col-md-offset-1 margin_top_20 margin_left_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfbustourbase" theme="simple" name="operform" action="${webPath}/mfBusTour/insertAjax">
							<dhcc:bootstarpTag property="formmfbustour" mode="query"/>
						</form>
					</div>
				</div>
				<div class="row clearfix approval-hist" id="pactSpInfo-block">
						<div class="col-xs-12 column">
							<div class="list-table">
							   <div class="title">
								 <span><i class="i i-xing blockDian"></i>巡视历史记录</span>
								</button>
							   </div>
							  <c:if test="${mfBusTourList.size()==0}">
									<div class="message margin_top_10">暂无巡视记录</div>
								</c:if>
								<c:if test="${mfBusTourList.size()!=0}">
							   	 	<div class="row clearfix">
										<div class="col-md-12">
											<div id="content" class="table_content" style="height: auto;"></div>
										</div>
									</div>
								  </c:if>
							   </div> 
						</div>
			</div>
		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="MfBusTour_Insert.ajaxSave('#mfbustourbase')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
		</div>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" 	src="${webPath}/component/tour/js/MfBusTour_Insert.js"></script>
		<script type="text/javascript">
		var processId='${processId}';
		var fincChildId = '${mfBusTour.fincChildId}';
		 $(function(){
			 MfBusTour_Insert.init(fincChildId);
		});
</script>
	</body>
</html>
