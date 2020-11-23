<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${webPath}/component/sys/css/B1.css" />
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css" />
<title>列表</title>
</head>
<style type="text/css">
/* hr {
	width: 70%;
	border-color: #DCDCDC;
	margin-left: 16px;
	margin-top: 0px
}
.time-line-border{
	left:71px;	
}
.breadcrumb>.active{
	font-size: 16px;
    font-weight: bold;
    color: #333;
}
.p-content{
	font-size:13px;
	margin-left:20px;
	letter-spacing:1px;
	color:#5d7388;
}
.p-weight{
	width: 70%;
}
 .p-content span{
	margin-left:20px
}
.p-span{
	margin-left: 0px;
}
.config-font{
	color:#1F4A9A;
}
.level-a{
	color: #CE8143; 
	font-size: 20px
}
a:focus, a:hover{
	color:#2a6496;
}
.btn:hover, .btn:focus, .btn.focus{
	text-decoration:none;
	color:#1F4A9A
} */
	.swraper{
		margin:-7.32px 0;
	}
	.btn-div{
		height:51px;
	}
</style>
<body class="">
	<c:if test='${entranceType eq "assets"}'>
		<!-- 押品信息管理（金融质押品） -->
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-9 column">
					<div class="btn-div">
						<ol class="breadcrumb pull-left padding_left_0" id="mfCollateralClassConfigA">
							<li class="active"><span name="title">金融质押品</span></li>
							<span class="btn btn-link config-font" id="classifyAdd"
								onclick="MfCollateralClass.addMfCollateralType('A','金融质押品')"><i
								class="i i-jia2"></i></span>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table" id="collateralFormContent_table_div_A">
							<c:forEach items="${mfCollateralClassListA}" varStatus="st" var = "mfCollateralClass">
									<p class="p-content" id="collateralformName${mfCollateralClass.classId}">
										<a href="javascript:void(0);"  onclick='MfCollateralClass.getById("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'>
											${mfCollateralClass.classSecondName}
										</a>
										--
										${mfCollateralClass.vouType}
										<%----
										${mfCollateralClass.classModel}--%>
										<a class="config-font" href="javascript:void(0);" onclick='MfCollateralClass.editCollateralForm("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName }")'>配置</a>
										<span class="config-font" name="mfcollateralclassname">
											<a href='${webPath}/mfCollateralClass/updateUserFlagAjax.action?classId=${mfCollateralClass.classId}' id="aaa">${mfCollateralClass.useFlag}</a>
										</span>
									</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 押品信息管理（商用房地产和居住用房地产） -->
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-9 column">
					<div class="btn-div">
						<ol class="breadcrumb pull-left padding_left_0" id="mfCollateralClassConfigB">
							<li class="active"><span name="title">商用房地产和居住用房地产</span></li>
							<span class="btn btn-link config-font" id="classifyAdd"
								onclick="MfCollateralClass.addMfCollateralType('B','商用房地产和居住用房地产');"><i
								class="i i-jia2"></i></span>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table" id="collateralFormContent_table_div_B">
							<c:forEach items="${mfCollateralClassListB}" varStatus="st" var = "mfCollateralClass">
									<p class="p-content" id="collateralformName${mfCollateralClass.classId}">
										<a href="javascript:void(0);"
											onclick='MfCollateralClass.getById("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'>
											${mfCollateralClass.classSecondName}
										</a>
										--
										${mfCollateralClass.vouType}
										<%----
										${mfCollateralClass.classModel}--%>
										<a href="javascript:void(0);"
											onclick='MfCollateralClass.editCollateralForm("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'
											>配置</a>
										<span class="config-font" name="mfcollateralclassname">
											<a class="config-font" href='${webPath}/mfCollateralClass/updateUserFlagAjax.action?classId=${mfCollateralClass.classId}' id="aaa">${mfCollateralClass.useFlag}</a>
										</span>
									</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 押品信息管理（应收账款） -->
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-9 column">
					<div class="btn-div">
						<ol class="breadcrumb pull-left padding_left_0" id="mfCollateralClassConfigC">
							<li class="active"><span name="title">应收账款</span></li>
							<span class="btn btn-link config-font" id="classifyAdd"
								onclick="MfCollateralClass.addMfCollateralType('C','应收账款');"><i
								class="i i-jia2"></i></span>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table" id="collateralFormContent_table_div_C">
							<c:forEach items="${mfCollateralClassListC}" varStatus="st" var = "mfCollateralClass">
									<p class="p-content" id="collateralformName${mfCollateralClass.classId}">
										<a href="javascript:void(0);"
											onclick='MfCollateralClass.getById("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'>
											${mfCollateralClass.classSecondName}
										</a>
										--
										${mfCollateralClass.vouType}
										<%----
										${mfCollateralClass.classModel}--%>
										<a class="config-font" href="javascript:void(0);"
											onclick='MfCollateralClass.editCollateralForm("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'
											>配置</a>
										<span class="config-font" name="mfcollateralclassname">
											<a href='${webPath}/mfCollateralClass/updateUserFlagAjax.action?classId=${mfCollateralClass.classId}' id="aaa">${mfCollateralClass.useFlag}</a>
										</span>
									</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 押品信息管理（其他押品） -->
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-9 column">
					<div class="btn-div">
						<ol class="breadcrumb pull-left padding_left_0" id="mfCollateralClassConfigD">
							<li class="active"><span name="title">其他押品</span></li>
							<span class="btn btn-link config-font" id="classifyAdd"
								onclick="MfCollateralClass.addMfCollateralType('D','其他押品');"><i
								class="i i-jia2"></i></span>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table" id="collateralFormContent_table_div_D">
							<c:forEach items="${mfCollateralClassListD}" varStatus="st" var = "mfCollateralClass">
									<p class="p-content" id="collateralformName${mfCollateralClass.classId}">
										<a href="javascript:void(0);"
											onclick='MfCollateralClass.getById("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'>
											${mfCollateralClass.classSecondName}
										</a>
										--
										${mfCollateralClass.vouType}
										<%----
										${mfCollateralClass.classModel}--%>
										<a class="config-font" href="javascript:void(0);"
											onclick='MfCollateralClass.editCollateralForm("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'
											>配置</a>
										<span class="config-font" name="mfcollateralclassname">
											<a href='${webPath}/mfCollateralClass/updateUserFlagAjax?classId=${mfCollateralClass.classId}' id="aaa">${mfCollateralClass.useFlag}</a>
										</span>
									</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test='${entranceType eq "account"}'>
		<!-- 应收账款 -->
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-9 column">
					<div class="btn-div">
						<ol class="breadcrumb pull-left padding_left_0" id="mfCollateralClassConfigE">
							<li class="active"><span name="title">应收账款</span></li>
							<span class="btn btn-link config-font" id="classifyAdd"
								  onclick="MfCollateralClass.addMfCollateralType('E','应收账款');"><i
									class="i i-jia2"></i></span>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table" id="collateralFormContent_table_div_E">
							<c:forEach items="${mfCollateralClassListE}" varStatus="st" var = "mfCollateralClass">
								<p class="p-content" id="collateralformName${mfCollateralClass.classId}">
									<a href="javascript:void(0);"
									   onclick='MfCollateralClass.getById("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'>
											${mfCollateralClass.classSecondName}
									</a>
									--
										${mfCollateralClass.vouType}
									<%----
										${mfCollateralClass.classModel}--%>
									<a class="config-font" href="javascript:void(0);"
									   onclick='MfCollateralClass.editCollateralForm("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'
									>配置</a>
									<span class="config-font" name="mfcollateralclassname">
											<a href='${webPath}/mfCollateralClass/updateUserFlagAjax.action?classId=${mfCollateralClass.classId}' id="aaa">${mfCollateralClass.useFlag}</a>
										</span>
								</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test='${entranceType eq "lease"}'>
		<!-- 租赁物 -->
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-9 column">
					<div class="btn-div">
						<ol class="breadcrumb pull-left padding_left_0" id="mfCollateralClassConfigF">
							<li class="active"><span name="title">租赁物</span></li>
							<span class="btn btn-link config-font" id="classifyAdd"
								  onclick="MfCollateralClass.addMfCollateralType('F','租赁物');"><i
									class="i i-jia2"></i></span>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table" id="collateralFormContent_table_div_F">
							<c:forEach items="${mfCollateralClassListF}" varStatus="st" var = "mfCollateralClass">
								<p class="p-content" id="collateralformName${mfCollateralClass.classId}">
									<a href="javascript:void(0);"
									   onclick='MfCollateralClass.getById("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'>
											${mfCollateralClass.classSecondName}
									</a>
									--
										${mfCollateralClass.vouType}
									<%----
										${mfCollateralClass.classModel}--%>
									<a class="config-font" href="javascript:void(0);"
									   onclick='MfCollateralClass.editCollateralForm("${mfCollateralClass.classId}","${mfCollateralClass.classSecondName}")'
									>配置</a>
									<span class="config-font" name="mfcollateralclassname">
											<a href='${webPath}/mfCollateralClass/updateUserFlagAjax.action?classId=${mfCollateralClass.classId}' id="aaa">${mfCollateralClass.useFlag}</a>
										</span>
								</p>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
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
