<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String appId = (String)request.getParameter("appId");
	String cusNo = (String)request.getParameter("cusNo");
	String tableId = (String)request.getParameter("formId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/pubview/js/pub_rece_account_list.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    pub_rece_account_list.appId = '<%=appId%>';
    pub_rece_account_list.cusNo = '<%=cusNo%>';
    pub_rece_account_list.tableId = '<%=tableId%>';
    pub_rece_account_list.query = '${query}';
    $(function(){
        pub_rece_account_list.init();
    });

</script>
<!--  应收账款列表信息 -->
<div class="row clearfix" id="rece_account_list" style="display:none;">
	<div class="dynamic-block" title="应收账款" name="rece_account_list">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>应收账款</span>
				<c:if test="${query!='query'}">
					<button class="btn btn-link formAdd-btn" onclick="pub_rece_account_list.insertReceBaseInfo();" title="新增"><i class="i i-jia3"></i></button>
				</c:if>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="receAccountList" name="receAccountList"></div>
		</div>
	</div>
</div>

