<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String fincMainId = (String)request.getParameter("fincMainId");
	String formId = (String)request.getParameter("formId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/pubview/js/pub_rece_finc_main_detail_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    pub_rece_finc_main_detail_info.fincMainId = '<%=fincMainId%>';
    pub_rece_finc_main_detail_info.formId = '<%=formId%>';
    $(function(){
        pub_rece_finc_main_detail_info.init();
    });

</script>
<!--详情表单信息-->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincappMain">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" style="margin-top: 15px;" id="fincappMain" name="fincappMain">
				<form id="fincAppMainDetail" method="post" theme="simple" name="operform">
				</form>
			</div>
		</div>
	</div>
</div>
	
