<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=${cssJsVersion}" />
	</head>
	<body class="bg-white">
		<div class="container">
			<div class="row clearfix" >
				<div class="col-md-8 col-md-offset-2 column ">
					<div class="row clearfix" >
						<div class="col-md-12 column">
							<div class="block-title"><span>基本信息</span></div>
							<div class="row clearfix padding_top_15 padding_left_40">
								<div class="col-md-4 column">
									<p>产品名称：${mfBusAppKind.kindName}</p>
									<p>业务类型：${mfBusAppKind.busModel}</p>
									<p>担保方式：${mfBusAppKind.vouTypeDef}</p>
								</div>
								<div class="col-md-8 column">
									<div class="row clearfix">
										<div class="col-md-4 column">
											<div class="block-sm-div backgroundcolor1">
												<p>${mfBusAppKind.minAmtStr}-${mfBusAppKind.maxAmtStr}万</p>
												<p>融资范围（万元）</p>
											</div>
										</div>
										<div class="col-md-4 column">
											<div class="block-sm-div backgroundcolor2">
												<p>${mfBusAppKind.minTerm}-${mfBusAppKind.maxTerm}</p>
												<p>期限范围（${mfBusAppKind.termType}）</p>
											</div>
										</div>
										<div class="col-md-4 column">
											<div class="block-sm-div backgroundcolor3">
												<p>${mfBusAppKind.fincRate}</p>
												<p>默认利率（%）</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
						<div class="block-title"><span>申请条件</span></div>
							<div class="padding_top_15 padding_left_40">
								<p>1、企业成立时间满3年；</p>
								<p>2、近半年开票额：150万左右；</p>
								<p>3、开票(增值税发票)，两年的年报表，最近一个月的月报表，近6个月的发票情况；</p>
								<p>4、申请人近三个月的个人贷款不能逾期，企业负债率不能超过60~70%</p>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div class="block-title"><span>产品说明</span></div>
							<div class="padding_top_15 padding_left_40">
								<p>${mfBusAppKind.remark}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>