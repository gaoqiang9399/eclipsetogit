<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%
	String allianceId = (String)request.getParameter("allianceId");
%>
<script type="text/javascript">
    var allianceId = '<%=allianceId%>';
    var allianceFormId = '${param.formId}';
    $(function() {
        allianceDetailInfo.init();
    });
</script>
<script type="application/javascript" src="${webPath}/component/alliance/js/MfBusAlliance_info_body.js"></script>

<!--主信息-->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>联保体详情</span>
			</div>
			<div class="content collapse in" id="pactDetailInfo" name="pactDetailInfo">
				<form method="post" theme="simple" id="pactDetailForm" name="operform" action="${webPath}/mfBusAlliance/updateAjaxByOne">
					<dhcc:propertySeeTag property="formallianceDetail" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>
