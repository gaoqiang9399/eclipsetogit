<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String fincMainId = (String)request.getParameter("fincMainId");
%>
<script type="text/javascript">
	var fincMainId = '<%=fincMainId%>';
    $(function() {
		showWkfFlowVertical($("#wj-modeler-fincMain"), fincMainId, "","finc_approval");
    });
</script>
<!--  融资审批历史 -->
<div class="row clearfix approval-hist" id="fincMainSpInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincMainSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="fincMainSpInfo-div">
				<div class="approval-process">
					<div id="modeler1" class="modeler">
						<ul id="wj-modeler-fincMain" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

