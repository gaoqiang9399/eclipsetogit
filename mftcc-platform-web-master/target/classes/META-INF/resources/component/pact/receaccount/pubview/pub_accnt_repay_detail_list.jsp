<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String receId = (String)request.getParameter("receId");
	String tableId = (String)request.getParameter("formId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/pubview/js/pub_accnt_repay_detail_list.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    pub_accnt_repay_detail_list.receId = '<%=receId%>';
    pub_accnt_repay_detail_list.tableId = '<%=tableId%>';
    $(function(){
        pub_accnt_repay_detail_list.init();
    });

</script>
<!--  应收账款列表信息 -->
<div class="row clearfix" id="rece_account_list" style="display:none;">
	<div class="dynamic-block" title="还款/解付历史" name="rece_account_list">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>还款/解付历史</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="receAccountList" name="receAccountList"></div>
		</div>
	</div>
</div>

