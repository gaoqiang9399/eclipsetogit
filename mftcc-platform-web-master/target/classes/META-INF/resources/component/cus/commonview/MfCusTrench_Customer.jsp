<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String trenchId = (String)request.getParameter("trenchId");
%>
<script type="application/javascript" src="${webPath}/component/cus/commonview/js/MfCusTrench_Customer.js"></script>
<script type="text/javascript">
    var trenchId = '<%=trenchId%>';

    $(function() {
        MfCusTrench_Customer.init();
    });
</script>

<div class="row clearfix" id="putout-his-block">
	<div class="dynamic-block" title="信用村成员" name="putoutHisDiv">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>信用村成员</span>

				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#putoutHis">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="putoutHis" name="putoutHis"></div>
		</div>
	</div>
</div>
