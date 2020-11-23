<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusMainBusiness.js"></script>
		<script type="text/javascript">
            var cusNo = '${cusNo}';
            var cusName = '${cusName}';
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
		<div class="container form-container" id="pageJsp">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">法院信息 mf_cus_court_info</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusMainBusinessForm" theme="simple" name="operform" action="${webPath}/mfCusMainBusiness/insertAjax">
							<dhcc:bootstarpTag property="formmfcusMainBusinessBase" mode="query"/>
						</form>
					</div>

					<%--上游信息--%>
					<div class="col-xs-12 column">
						<div class="list-table-replan">
							<div class="title">
								<span>主要供应商信息及信用支持</span>
								<button class="btn btn-link formAdd-btn"  onclick="MfCusMainBusiness.infoAdd()" title="新增"><i class="i i-jia3"></i></button>
								<div style="display:inline-block">填写前五大供应商及最有代表性的供应商</div>
							</div>
							<div class="content margin_left_15 collapse in" id="info">
								<dhcc:tableTag property="tablemainBusinessInfoList" paginate="tablemainBusinessInfoList" head="true"></dhcc:tableTag>
							</div>
						</div>
					</div>
                    <%--下游客户--%>
					<div class="col-xs-12 column">
						<div class="list-table-replan">
							<div class="title">
								<span>主要下游客户信息及赊销政策</span>
								<button class="btn btn-link formAdd-btn"  onclick="MfCusMainBusiness.cusInfoAdd()" title="新增"><i class="i i-jia3"></i></button>
								<div style="display:inline-block">填写前五大客户及最有代表性的客户</div>
							</div>
							<div class="content margin_left_15 collapse in" id="cusInfo">
								<dhcc:tableTag property="tablemainBusinessCusInfoList" paginate="tablemainBusinessCusInfoList" head="true"></dhcc:tableTag>
							</div>
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusMainBusiness.ajaxSave('#MfCusMainBusinessForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfCusMainBusiness.closeInfo();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>
