<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String allianceId = (String)request.getParameter("allianceId");
%>
<script type="application/javascript" src="${webPath}/component/alliance/js/MfBusAlliance_members.js"></script>
<script type="text/javascript">
    var allianceId = '<%=allianceId%>';
    var allianceCusFormId= '${param.formId}';
    $(function() {
        memberList.init();
    });
</script>

<div class="row clearfix" id="putout-his-block">
	<div class="dynamic-block" title="联保体成员" name="putoutHisDiv">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>联保体成员</span>
				<button class="btn btn-link formAdd-btn" onclick="memberList.addAllianceCustomer()" title="新增">
					<i class="i i-jia3"></i>
				</button>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#putoutHis">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
				<input type="hidden" name="memberHidden" onclick="bindDataSource('#memberHidden','2','memberHidden','选择客户',false,'2','','','','',true);">
			</div>
			<div class="content collapse in" id="putoutHis" name="putoutHis"></div>
		</div>
	</div>
</div>
