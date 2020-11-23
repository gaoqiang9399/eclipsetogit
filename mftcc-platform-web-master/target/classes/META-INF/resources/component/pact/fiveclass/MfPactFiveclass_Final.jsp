<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=${cssJsVersion}" />
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'></script>
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_Detail.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
			var allowManualFlag = "${mfPactFiveclass.allowManualFlag}";
			var fiveclass = "${mfPactFiveclass.fiveclass}";
		</script>
	</head>
	<style type="text/css">
		.business-span{
			font-size: 18px;
	    	color: #32b5cb;
	    	margin-right:5px;
		}
		.advice{
			position: absolute;
		    top: 0px;
		    margin-left: 200px;
		}
		.adviceinfo{
			color: #f34d00;
			text-decoration: underline;
			font-size: 18px;
			cursor: pointer;
			margin-left:5px;
			position: absolute;
		    top: 0px;
		    margin-left: 300px;
		}
		.body_bg{
			line-height: 3.125em
		}
		.col-md-3{
			width:50%
		}
	</style>
	<body class="body_bg overFlowHidden">
		<div class="mf_content">
			<div class="row clearfix" style="padding:10px 100px;">
				<div class="col-md-12 column">
					<div class="block-title"><span>基本信息</span></div>
					<div class="row clearfix padding_top_15 padding_left_40">
						<div class="col-md-12 column">
							<div class="col-md-3 column">
								<span>客户名称：</span>
								<span class="margin_top_15"><span class="business-span">${mfCusCustomer.cusName}</span> </span>
							</div>
							<div class="col-md-3 column">
								<span>合同编号：</span>
								<span class="margin_top_15"><span class="business-span">${mfBusPact.pactId}</span> </span>
							</div>
							
					   </div>	
					   <div class="col-md-12 column">
					   		<div class="col-md-3 column">
								<span>项目名称：</span>
								<span class="margin_top_15"><span class="business-span">${mfBusPact.appName}</span> </span>
							</div>
					   		<div class="col-md-3 column">
								<span>合同金额：</span>
								<span class="margin_top_15"><span class="business-span">${mfBusPact.pactAmt}</span>元</span>
							</div>
					   </div>	
					   <div class="col-md-12 column">
					   		<div class="col-md-3 column">
								<span>当前五级分类结果：</span>
								<span class="margin_top_15">
									<span class="business-span">
										<c:if test="${mfPactFiveclass.fiveclass == 1}">正常</c:if>
										<c:if test="${mfPactFiveclass.fiveclass == 2}">关注</c:if>
										<c:if test="${mfPactFiveclass.fiveclass == 3}">次级</c:if>
										<c:if test="${mfPactFiveclass.fiveclass == 4}">可疑</c:if>
										<c:if test="${mfPactFiveclass.fiveclass == 5}">损失</c:if>
									</span> 
								</span>
							</div>
					   </div>
					</div>
				</div>
			</div>
			<div class="content-box" style="width: 83%;">
				<p class="tip"><span>说明：</span>带*号的为必填项信息，请填写完整。</p>
				<div class="tab-content">
					<form method="post" id="operform"   name="operform" action="${webPath}/mfPactFiveclass/confirmAjax">
						<dhcc:bootstarpTag property="formfiveclass0004" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdate('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</body>
</html>
