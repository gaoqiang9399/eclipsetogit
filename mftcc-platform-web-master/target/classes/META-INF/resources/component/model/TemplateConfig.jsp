<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/model/js/useFlagRcswitcher.js"></script>

<link rel="stylesheet" href="${webPath}/themes/factor/css/filter.css?v=${cssJsVersion}" />
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/TemplateConfig.css?v=${cssJsVersion}">
<title>产品设置</title>
<script type="text/javascript">
	var basePath="${webPath}";
</script>
</head>
<body> 
	<div class="container">
		<!-- 新增模板 begin-->
		<div class="row clearfix tabCont">
			<div class="col-md-12 column">
				<div class="search-div kind_newline_div">
					<button type="button" class="btn btn-primary" onclick="TemplateConfig.addSysTemplate();">新增模板</button>
				</div>
			</div>
		</div>
		<!-- 新增模板 end-->
		<!-- 模板配置 begin -->
		<div class="row clearfix config-div" id="sysTemplateConfig-div">
			
		</div>
		<!-- 模板配置信息 end -->
	</div>
	<!-- 导航轴 -->
	<div class="row clearfix">
			<div class="work-zone-timeLine" style="position: fixed; margin-top: -78px; margin-right: 79px;">
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
	<!-- 导航轴 end -->
</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/TemplateConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function() {
		TemplateConfig.init();
	});
</script>
</html>
