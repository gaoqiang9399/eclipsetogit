<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/MfCreditTemplateList.js"></script>
		<script type="text/javascript" >
            var query = "${query}";//该值标志位是否可以上传
			var creditAppId = "${creditAppId}";
			var cusNo = "${cusNo}";
			var templateType = "${templateType}";
			var saveBtn = "${saveBtn}";
			var tableId = "${tableId}";
			$(function(){
                MfCreditTemplateList.init();
			 });
		</script>
	</head>
	<body>
		<div class="container">
            <div class="row clearfix bg-white tabCont">
                <c:if test="${!empty saveBtn}">
                    <div  class="btn-div">
                        <div class="col-md-2">
                        </div>
                        <div class="col-md-8 text-center">
                            <span class="top-title">空白模板打印</span>
                        </div>
                    </div>
				</c:if>
				<div class="col-md-12 column">
					<div class="search-div">
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
