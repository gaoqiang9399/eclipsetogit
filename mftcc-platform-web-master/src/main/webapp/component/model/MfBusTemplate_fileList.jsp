<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><%=request.getAttribute("title")%>
	</title>
	<link rel="stylesheet" type="text/css"
		  href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
	<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/qrcode.min.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileList.js"></script>

<script type="text/javascript">
	var modelNo = '${modelNo}';
	var relNo = '${relNo}';
	var cusNo = '${cusNo}';
	var appId = '${appId}';
	var pactId = '${pactId}';
	var fincId = '${fincId}';
    var nodeNo = '${nodeNo}';//文件打印
	var pleId = '${pleId}';
	var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=' + fincId;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
	var generatePhase = '${generatePhase}';
	var templateBizConfigIds = '${templateBizConfigIds}';
    var querySaveFlag_pl = "0";
    var approvalNodeNo = "${approvalNodeNo}";
    var temBizNo =  appId ;
    var  bizConfigList="${bizConfigList}";
        var qrCodeShowFlag = '${qrCodeShowFlag}';
        var charResult='${charResult}';
        $(function () {
            MfBusTemplate_fileList.init();
            if (qrCodeShowFlag == "0") {
                $(".qrCode").remove();
            }
        });
</script>
</head>

<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont scroll-content" >
		<c:choose>
			<c:when test="${charResult=='0'}">
				<div class="text-center">暂时没有可以下载的文档</div>
			</c:when>
			<c:otherwise>
				<%@ include file="/component/model/templateIncludePage.jsp"%><!-- 功能挂载(要件、文档、费用...) -->

			</c:otherwise>
		</c:choose>


	</div>

</div>
<div id="qrcode"></div>
</body>


<%--<body class="bg-white"> 原有的展示方式
	<div class="list-table margin_0 ">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>文档模板</span>
			<div class="template-download-btn" onclick="MfBusTemplate_fileList.downloadTemplateZip('${appId}','${cusNo}','${templateBizConfigIds}');">打包下载</div>
		</div>


		<%@ include file="/component/model/templateIncludePage.jsp"%><!-- 功能挂载(要件、文档、费用...) -->


		<div style="position:fixed; bottom:25px; width:100%; text-align:center;">
			<span style="color:#FF0000">如果无法正常打开文档，请在系统右侧工具栏下载pageoffice控件并安装，重启浏览器后再次打开文档。</span>
		</div>
	</div>

	<div id="bizConfigs" class="template-config item-div padding_left_15">
		<div class="item-div">
			<c:forEach var="bizConfig" items="${bizConfigList}" varStatus="stat">
				<div id="${bizConfig.templateBizConfigId}" class="block-item">
					<c:set var="imgClass" value="item-word" />
					<c:if test="${bizConfig.templateSuffix == '2'}">
						<c:set var="imgClass" value="item-excel" />
					</c:if>
					<c:if test="${bizConfig.templateSuffix == '3'}">
						<c:set var="imgClass" value="item-pdf" />
					</c:if>

					<div class="item-title ${imgClass}" onclick="MfBusTemplate_fileList.printFile('${bizConfig.templateBizConfigId}');">
						<span title="${bizConfig.templateNameZh}">
						   	<c:if test="${fn:length(bizConfig.templateNameZh)>15 }">  
                         		${fn:substring(bizConfig.templateNameZh, 0, 15)}...  
                  			</c:if> 
                  			<c:if test="${fn:length(bizConfig.templateNameZh)<=15 }">  
                       			${bizConfig.templateNameZh}
                   			</c:if> 
						</span>
						<c:if test="${empty bizConfig.docFileName}">
							<div class="color_theme">
								<i class="i i-jia3"></i>新增
							</div>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>--%>
</html>