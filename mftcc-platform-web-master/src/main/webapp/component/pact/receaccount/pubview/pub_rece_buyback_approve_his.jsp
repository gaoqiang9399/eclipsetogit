<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String receId = (String)request.getParameter("receId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/pubview/js/pub_rece_buyback_approve_his.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    pub_rece_buyback_approve_his.receId = '<%=receId%>';
    $(function(){
        pub_rece_buyback_approve_his.init();
    });

</script>
<!--  账款反转让审批历史 -->
<div id="receBuyback-spInfo-block" class="approval-hist">
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>账款反转让审批历史</span>
			<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
		</div>
		<div class="content margin_left_15 collapse in " id="receBuyBackSpInfo-div">
			<div class="approval-process">
				<div id="modeler1" class="modeler">
					<ul id="wj-modeler-buyback" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>