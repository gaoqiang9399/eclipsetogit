<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
        <link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
        <script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
        <link id="MfSysKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfSysKindConfig${skinSuffix}.css?v=${cssJsVersion}">
        <script type="text/javascript" src="${webPath}\component\cus\js\editTemplatePageUpdate.js?v=${cssJsVersion}"></script>
    </head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
                    <!-- 配置信息 -->
                    <div class="row clearfix config-div margin_top_5">
                        <div class="nav-content-div"  style="width:730px;margin:0px auto;position: relative;">
                        </div>
                    </div>
                </div>
                <div class="formRowCenter">
                    <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var mfKindNodeTemplates = "";
        var cusType = '${cusType}';
        var kindNo = 'cus_template';
        var cusTypeName = '${cusTypeName}';
        $(function () {
            editTemplatePageUpdate.init();
        });
    </script>
</html>