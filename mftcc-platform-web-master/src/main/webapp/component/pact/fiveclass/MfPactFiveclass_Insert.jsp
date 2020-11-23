<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=${cssJsVersion}" />
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_Insert.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
			var pactId = "${pactId}";
			var fincId = "${fincId}";
			var fiveclass = "${mfPactFiveclass.fiveclass}";
		</script>
	</head>
	<style type="text/css">
	.business-span{
		font-size: 18px;
    	color: #32b5cb;
    	margin-right:5px;
	}
	.adviceinfo{
		color: #f34d00;
		font-size: 18px;
		margin-left:5px;
	}
	.body_bg{
		line-height: 3.125em
	}
	.col-md-3{
		width:50%
	}
	</style>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="operform"   name="operform" action="${webPath}/mfPactFiveclass/insertAjax?pactId=">
						<dhcc:bootstarpTag property="formfiveclass0002" mode="query" />
					</form>
				</div>
			</div>
			
			<div class="row clearfix" style="padding-left:10px;line-height: 3.125em;">
				<div class="col-md-10 col-md-offset-1 column ">
					<div class="row clearfix padding_top_15 padding_left_40">
						<div class="col-md-12 column">
							<div class="col-md-3 column">
								<span>项目名称：</span>
								<span class="margin_top_15">${mfBusPact.appName}</span>
							</div>
							<div class="col-md-3 column">
								<span>合同编号：</span>
								<span class="margin_top_15">${mfBusPact.pactNo}</span>
							</div>
					   </div>	
					   <div class="col-md-12 column">
					   		<div class="col-md-3 column">
								<span>客户名称：</span>
								<span class="margin_top_15">${mfCusCustomer.cusName}</span>
							</div>
					   		<div class="col-md-3 column">
								<span>合同金额(元)：</span>
								<span class="margin_top_15">${mfBusPact.fincAmt}</span>
							</div>
					   </div>
					   <div class="col-md-12 column">
					   		<div class="col-md-3 column">
								<span>产品名称：</span>
								<span class="margin_top_15">${mfBusPact.kindName}</span>
							</div>
					   		<div class="col-md-3 column">
								<span>逾期情况(天)：</span>
								<span class="margin_top_15">
									<c:if test="${empty mfPactFiveclass.overDate || mfPactFiveclass.overDate == 0}">未逾期</c:if>
									<c:if test="${!empty mfPactFiveclass.overDate && mfPactFiveclass.overDate != 0}">${mfPactFiveclass.overDate}</c:if>
								</span>
							</div>
					   </div>
					   <div class="col-md-12 column">
					   		<div class="col-md-3 column">
								<span>还款方式：</span>
								<span class="margin_top_15">
									${mfBusFincApp.repayType}
								</span>
							</div>
					   		<div class="col-md-3 column">
								<span>担保方式：</span>
								<span class="margin_top_15">
									${mfBusPact.vouType}
								</span>
							</div>
					   </div>
					   <div class="col-md-12 column">
					   		<div class="col-md-3 column">
								<span>当前五级分类：</span>
								<span class="margin_top_15">
									<span class="business-span">
										${mfPactFiveclass.fiveclassShow}
									</span> 
								</span>
							</div>
							<div class="col-md-3 column">
								<span>系统初分：</span>
								<span class="margin_top_15">
									<span class="business-span">
										${mfPactFiveclass.systemFiveclassShow}
									</span> 
								</span>
							</div>
					   </div>
					</div>
				</div>
			</div>
		</div>		
		<div class="formRowCenter">
			 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert('#operform')"></dhcc:thirdButton>
			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
