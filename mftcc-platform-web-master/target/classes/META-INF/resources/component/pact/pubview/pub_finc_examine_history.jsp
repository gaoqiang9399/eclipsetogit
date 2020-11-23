<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	var examTableId = '${param.formId}';
	var fincId = '${param.fincId}';
	var examEntrance = 'finc';
	$(function() {
		pubMfExamineHisList.init();
	});
</script>
<!--检查历史信息 -->
<div class="row clearfix" id="mfExamineHis-div">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 检查历史
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#examineHis">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="examineHis">
				<dhcc:tableTag paginate="mfExamineHisList" property="tableexamhis0001" head="true" />
			</div>
		</div>
	</div>
</div>


