<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%--<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		&lt;%&ndash; <link rel="stylesheet" href="${webPath}/tech/wkf/css/wjProcessDetail.css" /> &ndash;%&gt;
		&lt;%&ndash; <script type="text/javascript" src="${webPath}/tech/wkf/js/wjProcessDetail.js"></script> &ndash;%&gt;
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>--%>
		
		<!-- 同盾认证报告 -->
		<!-- <script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script> -->
		
<%--<script type="text/javascript">
	var cusNo='${dataMap.cusNo}';//客户号
	var query='${query}';
	var baseType='${dataMap.baseType}';
	var dataMap = '${dataMap}';
	$(function(){
		$("body").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			},
			callbacks: {
				//正在滚动的时候执行回调函数
				whileScrolling: function(){
					if ($(".changeval").length>0) {
						$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
					}
				}
			}
		});
	})
</script>
<script type="text/javascript" src="${webPath}/component/cus/commonview/js/MfCusCustomer_ComView.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js"></script>--%>
</head>
<%--<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<!--头部信息 -->
					<c:forEach items="${dataMap.headView}" var="item">
							<jsp:include page="${item.blockUrl }"   flush="true"></jsp:include>
					</c:forEach>

					<!--主信息-->
					<c:forEach items="${dataMap.bodyView}" var="item">
						<c:if test="${item.pmsBizNo=='' || item.pmsBizNo == null}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!='' && item.pmsBizNo != null}">
							<dhcc:pmsTag pmsId="${item.pmsBizNo}">
								<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
 							</dhcc:pmsTag>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<!--附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<c:forEach items="${dataMap.afftView}" var="item">
						<c:if test="${item.pmsBizNo==''|| item.pmsBizNo == null}">
							<jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
						</c:if>
						<c:if test="${item.pmsBizNo!=''&& item.pmsBizNo != null}">
							<dhcc:pmsTag pmsId="${item.pmsBizNo}">
								<jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
 							</dhcc:pmsTag>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>--%>
<script type="text/javascript" src="${webPath}/component/cus/cusgroup/js/MfCusGroup_Insert.js"></script>
<script type="text/javascript" >
    $(function(){
        MfCusGroup_Insert.init();
    });
</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<!--
        两列表单使用 col-md-8 col-md-offset-2
        四列表单使用 col-md-10 col-md-offset-1
         -->
		<div class="col-md-10 col-md-offset-1 margin_top_20">
			<div class="bootstarpTag">
				<!-- <div class="form-title">人力需求表</div> -->
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="MfCusGroupForm" theme="simple" name="operform" action="${webPath}/mfCusGroup/insertAjax">
					<dhcc:bootstarpTag property="formcusgroup0002" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="提交" action="提交" onclick="MfCusGroup_Insert.ajaxSave('#MfCusGroupForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfCusGroup_Insert.myclose();"></dhcc:thirdButton>
	</div>
</div>

</body>


</html>