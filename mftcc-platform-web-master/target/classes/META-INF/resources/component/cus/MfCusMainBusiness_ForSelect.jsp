<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusMainBusiness.js"></script>
		<script type="text/javascript">
            var cusNo = '${cusNo}';
            var mainType = '${mainType}';
			$(function() {
				$(".scroll-content").mCustomScrollbar({//滚动条的生成
					advanced: {
						theme: "minimal-dark",
						updateOnContentResize: true
					}
                });
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
	<c:if test="${mainType=='2'}">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">主要供应商信息及信用支持</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="cusMainBusinessInfoBase" theme="simple" name="operform" >
							<dhcc:bootstarpTag property="formdlcertiinfo0003" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfCusMainBusiness.insertInfo('#cusMainBusinessInfoBase')"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfCusMainBusiness.closeInfo();"></dhcc:thirdButton>
			</div>
		</div>
	</c:if>
		<%--下游客户--%>
      <c:if test="${mainType=='3'}">
		<div class="container form-container" id="infoCusEdit">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title" ></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="cusMainBusinessCusInfoBase" theme="simple" name="operform" >
							<dhcc:bootstarpTag property="formdlcertiinfo0003" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfCusMainBusiness.insertInfo('#cusMainBusinessCusInfoBase')"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	  </c:if>
	</body>
</html>
