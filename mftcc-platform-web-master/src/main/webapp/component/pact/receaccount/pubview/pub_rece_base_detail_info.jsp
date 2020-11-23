<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String receId = (String)request.getParameter("receId");
	String formId = (String)request.getParameter("formId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/pubview/js/pub_rece_base_detail_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    pub_rece_base_detail_info.receId = '<%=receId%>';
    pub_rece_base_detail_info.formId = '<%=formId%>';
    $(function(){
        pub_rece_base_detail_info.init();
    });

</script>
<!-- 申请表单信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="content" id="receDetailInfo">
				<form  method="post" theme="simple" id="receDetailForm" name="operform" action="${webPath}/mfBusReceBaseInfo/updateAjaxByOne">
					<dhcc:propertySeeTag property="formrecebaseinfo" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>
