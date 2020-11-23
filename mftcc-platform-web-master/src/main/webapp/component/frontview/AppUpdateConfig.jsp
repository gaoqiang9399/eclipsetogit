<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet"
	href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css"
	type="text/css" />
<script type="text/javascript"
	src="${webPath}/component/include/myRcswitcher.js"></script>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/AppUpdateConfig.css" />
	<%--字体图标--%>
	<link rel="stylesheet" href="${webPath}/themes/factor/css/set.css?v=${cssJsVersion}" />
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
	<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
<script type="text/javascript" src="${webPath}/component/frontview/js/MfAppUpdateSetting.js"></script>
</head>
<body class="bg-white">
	<div class="container">
		<div class="row clearfix tabCont">
			<div class="col-md-12 column">
				<div class="btn-div" >
					<ol class="breadcrumb pull-left padding_left_0" id="frontViewManageA">
						<li class="active"><span name="title">C端App设置</span></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content appconfig">
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<!--1:banner设置  start-->
					<div id="banner">
						<div class="p-title margintop10 marginbottom10">
							<span class="content_title">banner设置</span>
							<a class="config-font" href="${webPath}/vwBannerManage/getListPage">
								 配置
							</a>
						</div>
					</div>
					<!--1:banner     end -->
					<!--2:基本模式 start-->
						<div id="repayModel">
						<div class="p-title margintop10 marginbottom10">
							<span class="content_title">信息采集项</span>
							<a class="config-font" href="${webPath}/mfAppCollectItem/getListPage">
								配置
							</a>
						</div>
						<div class="p-content">
						</div>
					</div>
					<!--2:基本模式 end-->
					<!--3:产品设置    start -->
					<div id="productList">
						<div class="p-title margintop10 marginbottom10">
							<span class="content_title">产品设置</span>
							<span class="btn btn-link config-font" id="cusTypeAdd" onclick="MfAppUpdateConfig.addKind('${webPath}/mfAppProdItem/getAppProdSelectListPage');"><i class="i i-jia2"></i></span>
						</div>
						<div class="p-content">
							<div class="item-div marginleft20 productItemList">
							</div>
						</div>
					</div>
					<!--3:产品设置  end-->
					<!--12:版本    start -->
					<div id="">
						<div class="p-title margintop10 marginbottom10">
							<span class="content_title">版本</span>
							<a class="config-font" href="${webPath}/mfAppVersion/getListPage" onclick="">查看历史版本</a>
						</div>
						<div class="p-content">
							<div class="item-div marginleft20 loginModel">
								<div class="item-content  margin_bottom_10">
									自定义版本号：<input type="text" name="appVersion" value="" id='appVersion'/>
								</div>
							</div>
						</div>
					</div>
					<!--12:版本  end-->
					<!--13:版本    start -->
					<div id="">
						<div class="p-title margintop10 marginbottom10"><span class="content_title">发布</span></div>
						<div class="p-content">
							<div class="formRowCenter">
					   			<input type="button" value="发布" onclick="MfAppUpdateConfig.publishVersion()">
					   		</div>
						</div>
					</div>
					<!--13:版本  end-->
				</div>
			</div>
		</div>
		<div class="row clearfix tabCont">
			<div class="col-md-12 column">
				<div class="btn-div" >
					<ol class="breadcrumb pull-left padding_left_0" id="appUpdateCongig">
						<li class="active"><span name="title">B端App设置</span></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					
				</div>
			</div>
		</div>
		<!-- 导航轴 -->
		<div class="row clearfix">
			<div class="work-zone-timeLine" style="position: fixed; margin-top: 3%; margin-right: 79px;">
				<div class="time_contents">
					<div class="time-line-bg">
						<div class="time-line-line"></div>
						<div class="time-line-body">
							<dl class="time-line-dl"></dl>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
		var path = '<%=request.getContextPath() %>';
		var basePath = '${webPath}';
		var ajaxData = '${ajaxData}';
		ajaxData = JSON.parse(ajaxData);
		var mfAppSetingList = ajaxData.mfAppSetingList;
		$(function(){
			MfAppUpdateConfig.init();
			//调用时间轴
			navLine.createNavLine();
		});
	</script>
</html>
