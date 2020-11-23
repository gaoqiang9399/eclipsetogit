<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfCollateralClass.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/collateral/js/collRcswitcher.js"></script>
<link rel="stylesheet" href="${webPath}/themes/factor/css/set.css" />
<%--字体图标--%>
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${webPath}/component/sys/css/B1.css" />
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css" />
<title>列表</title>
</head>
<style type="text/css">
.swraper{
	margin:-7.32px 0;
}
.vch-summary{
	width: 80px;
	display: inline;
}
.div1 {
	width: 80%;
	margin-left: 9px;
	/* 	margin: 10px; */
	/* position: relative;  */
	/* position:absolute; */
	left: 250px;
	/* border: 1px solid #ccc; */
	top: -49px;
	float: left;
	margin-bottom: 20px;
}
</style>
<body>
	<!-- 押品信息管理（金融质押品） -->
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<ol class="breadcrumb pull-left padding_left_0" id="pssConfig">
						<li class="active"><span name="title">设置</span></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="row clearfix margin_top_20">
			<div class="col-md-12 column">
				<div class="table_content">
					<%-- 	<s:iterator value="mfCollateralClassListA" status="st">
								<p class="p-content" id="collateralformName<s:property value="classId" />">
									<a href="javascript:void(0);" 
										onclick='MfCollateralClass.getById("<s:property value="classId" />","<s:property value="classSecondName" />")' 
										>
										<s:property value="classSecondName" />
									</a>
									<s:property value="vouType" />
									<s:property value="classModel" />
									<a class="config-font" href="javascript:void(0);" onclick='MfCollateralClass.editCollateralForm("<s:property value="classId" />","<s:property value="classSecondName" />")'>配置</a>
									<span class="config-font" name="mfcollateralclassname">
										<a href='MfCollateralClassActionAjax_updateUserFlagAjax.action?classId="<s:property value="classId" />"' id="aaa"><s:property value="useFlag" /></a>
									</span>
								</p>
						</s:iterator> --%>
						<div class="div1" id="collateralformName17061310475237624">
									本位币:
									<input type="text" class="form-control vch-summary" id="curType" value="RMB" readonly>
						</div>
						<div class="div1" id="collateralformName17061310475237624">
									数量小数位:
									<select class="form-control vch-summary" id="comType" name="comType" style="width='20%'">
										<option value="0">0</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="3">5</option>
										<option value="4">6</option>
									</select>
						</div>
<!-- 						<p class="p-content" id="collateralformName17061310475237624">
									<a href="javascript:void(0);">
										本位币
									</a>
									--
									<select class="form-control" id="comType" name="comType" style="width='20%'">
										<option value="">请选择</option>
										<option value="0">4-2-2（默认）</option>
										<option value="1">4-3-3</option>
										<option value="2">4-4-4</option>
										<option value="3">4-5-5</option>
									</select>
									--
									应收账款|动产质押
									<a class="config-font" href="javascript:void(0);" onclick="MfCollateralClass.editCollateralForm(&quot;17061310475237624&quot;,&quot;现金及其等价物&quot;)">配置</a>
									<span class="config-font" name="mfcollateralclassname"><span class="swraper lease" style="width: 50px; line-height: 12px;" input-name="useFlag" input-value="1" input-type="checkbox"><span class="stoggler on" style="font-size: 12px; transform: translateX(-6px);"><span class="slabel-on">启用</span><span class="sblob" style="width: 10px; height: 10px;"></span><span class="slabel-off">停用</span></span></span><input name="useFlag" type="checkbox" value="1" style="display: none;"></span>
						</p> -->
				</div>
			</div>
		</div>
	</div>
	<!-- 时间轴 -->
	<div class="work-zone-timeLine col-sm-2"
		style="position: fixed; margin-top: 3%; margin-right: 79px;">
		<div class="time_contents">
			<div class="time-line-bg">
				<div class="time-line-line"></div>
				<div class="time-line-body">
					<dl class="time-line-dl"></dl>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		//调用时间轴
		navLine.createNavLine();
		$("span[name='mfcollateralclassname']").collRcswitcher({
			name:"useFlag",onText:"启用",offText:"停用"});
	});
</script>
</html>
