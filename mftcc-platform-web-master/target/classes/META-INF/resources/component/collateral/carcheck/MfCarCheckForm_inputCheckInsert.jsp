<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<title>复验验车单</title>
	</head>
	<style type="text/css">
	.bootstarpTag .table-bordered > tbody > tr > td, .bootstarpTag .table-bordered > tbody > tr > th, .bootstarpTag .table-bordered > tfoot > tr > td, .bootstarpTag .table-bordered > tfoot > tr > th, .bootstarpTag .table-bordered > thead > tr > td, .bootstarpTag .table-bordered > thead > tr > th {
	    border-left: none;
	    border-right: none;
	}
	</style>
	<script type="text/javascript">
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="cusInsertForm" theme="simple" name="operform" action="http://192.168.2.180:8090/mfCusCustomer/insertForBusAjax">
							<table class="table table-bordered"  cellpadding="0" cellspacing="0" style="border-collapse:collapse">
								<tbody>
									<tr>
										<td class="tdlable right" width="30%" colspan="1" rowspan="1">
											<label class="control-label ">验车项目</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">车管验车</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">客户验车</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车型名称</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${mfCarCheckForm.modelName}</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${carCheckForm.modelName}</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">全车玻璃</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.glass == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.glass == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.glass == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.glass == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">行驶证</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.drivingCertificate == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.drivingCertificate == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.drivingCertificate == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.drivingCertificate == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">空调</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.airCon == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.airCon == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.airCon == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.airCon == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">音响</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.stereo == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.stereo == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.stereo == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.stereo == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">雨刷器</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.windscreenWiper == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.windscreenWiper == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.windscreenWiper == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.windscreenWiper == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">反光镜</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.reflector == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.reflector == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.reflector == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.reflector == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">随车文件</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carUseManual == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carUseManual == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carUseManual == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carUseManual == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">随车税标</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.taxLabel == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.taxLabel == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.taxLabel == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.taxLabel == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">点烟器</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.cigaretteLighter == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.cigaretteLighter == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.cigaretteLighter == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.cigaretteLighter == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">烟灰缸</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.ashtray == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.ashtray == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.ashtray == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.ashtray == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车轮</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.wheels == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.wheels == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.wheels == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.wheels == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车牌</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.licensePlate == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.licensePlate == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.licensePlate == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.licensePlate == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">内饰</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.interiorDeco == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.interiorDeco == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.interiorDeco == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.interiorDeco == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">座椅</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.seats == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.seats == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.seats == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.seats == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">室内灯</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.interiorLights == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.interiorLights == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.interiorLights == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.interiorLights == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车窗升降</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.windowLifts == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.windowLifts == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.windowLifts == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.windowLifts == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">拉手</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.handle == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.handle == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.handle == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.handle == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">故障警示牌</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.alertSign == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.alertSign == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.alertSign == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.alertSign == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">脚垫</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.footMat == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.footMat == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.footMat == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.footMat == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">安全带</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.safetyBelt == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.safetyBelt == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.safetyBelt == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.safetyBelt == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车大灯</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.headLights == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.headLights == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.headLights == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.headLights == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">转向灯</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.turnSignalLight == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.turnSignalLight == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.turnSignalLight == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.turnSignalLight == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">工具包</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.toolKit == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.toolKit == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.toolKit == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.toolKit == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">千斤顶</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.liftingJack == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.liftingJack == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.liftingJack == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.liftingJack == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">遮阳板</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.sunVisor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.sunVisor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.sunVisor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.sunVisor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车钥匙</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carKey == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carKey == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carKey == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carKey == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">刹车灯</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.brakeLight == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.brakeLight == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.brakeLight == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.brakeLight == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">倒车灯</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.reversingLamp == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.reversingLamp == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.reversingLamp == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.reversingLamp == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">备胎</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.spareTire == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.spareTire == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.spareTire == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.spareTire == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">灭火器</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.fireExtinguisher == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.fireExtinguisher == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.fireExtinguisher == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.fireExtinguisher == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车锁</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carLock == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carLock == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carLock == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carLock == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车内后视镜</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rearviewMirror == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rearviewMirror == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rearviewMirror == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rearviewMirror == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">手刹,脚刹</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.handFootBrake == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.handFootBrake == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.handFootBrake == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.handFootBrake == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">喇叭</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.horns == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.horns == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.horns == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.horns == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">仪表盘</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.dashboard == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.dashboard == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.dashboard == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.dashboard == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">封车标签</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.vehicleSeal == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.vehicleSeal == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.vehicleSeal == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.vehicleSeal == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">前保险杠</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.bumper == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.bumper == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.bumper == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.bumper == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">发动机舱盖</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.hood == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.hood == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.hood == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.hood == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">车顶</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.roof == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.roof == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.roof == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.roof == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">行李箱盖</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carPlate == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carPlate == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.carPlate == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.carPlate == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">后保险杠</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.backBumper == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.backBumper == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.backBumper == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.backBumper == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">左前门</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.lfDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.lfDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.lfDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.lfDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">左后门</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.lrDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.lrDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.lrDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.lrDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">右前门</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rfDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rfDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rfDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rfDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">右后门</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rrDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rrDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rrDoor == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rrDoor == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">左后翼子板</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.lrFender == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.lrFender == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.lrFender == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.lrFender == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">右前翼子板</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rfFender == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rfFender == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.rfFender == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.rfFender == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">天窗</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.sunroof == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.sunroof == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">
								<c:if test="${mfCarCheckForm.sunroof == '1'}">
								　　符合
								</c:if>
								<c:if test="${carCheckForm.sunroof == '2'}">
								　　不符合
								</c:if>
							</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">验车时间</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${mfCarCheckForm.departureTime}</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${carCheckForm.departureTime}</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">验车人</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${mfCarCheckForm.checkOpName}</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${carCheckForm.checkOpName}</label>
										</td>
									</tr>
									<tr>
										<td class="tdlable right" colspan="1" rowspan="1">
											<label class="control-label ">备注</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${mfCarCheckForm.remarks}</label>
										</td>
										<td class="right" width="30%"colspan="1" rowspan="1">
											<label class="control-label ">${carCheckForm.remarks}</label>
										</td>
									</tr>

								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>