<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
	<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
	<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
	<script type="text/javascript" src='${webPath}/component/importexcel/js/MfCusImportExcel_List.js?v=${cssJsVersion}'></script>
	<script type="text/javascript" >
        var isUpload = false;
        var cusNo = '${cusNo}';
        var webPath = "${webPath}";
        $(function(){
            cusImportExcel.init();
        });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<dhcc:pmsTag pmsId="assets-assets-add-btn">
					</dhcc:pmsTag>
					<button type="button" class="btn btn-primary" onclick="cusImportExcel.downloadModel();">下载模板</button>
					<button type="button" class="btn btn-primary" onclick="cusImportExcel.uploadExcel();">导入</button>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">历史数据补录</span>
				</div>
			</div>
			<div class="search-div" id="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=导入内容"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12">
			<div id="content" class="table_content" style="height: auto;">
			</div>
		</div>
	</div>
	<div id="finData" class="data-list" style="display: none">
		<input type="hidden" name="finData">
		<input type="hidden" name="finRptType">
		<input type="hidden" name="finRptDate">
		<input type="hidden" name="cusName">
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic = [
        {
            "optName" : "导入状态",
            "parm" : ${hisStsArray},
            "optCode" : "hisSts",
            "dicType" : "y_n"
        },{
            "optName" : "解析总数量",
            "parm" : [],
            "optCode" : "sumCount",
            "dicType" : "num"
        },{
            "optName" : "校验成功数量",
            "parm" : [],
            "optCode" : "successCount",
            "dicType" : "num"
        },{
            "optName" : "校验错误数量",
            "parm" : [],
            "optCode" : "failCount",
            "dicType" : "num"
        }
    ];
</script>
</html>
