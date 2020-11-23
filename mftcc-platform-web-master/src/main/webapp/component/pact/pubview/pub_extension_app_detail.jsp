<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincId = '${param.fincId}';
	var extenFormId = '${param.formId}';
	$(function() {
		//pubExtenAppDetail.init();
	});
</script>
<!-- 放款申请表单信息 -->
<div class="row clearfix" id="ExtenAppDetail-div" style="display:none">
	<div class="col-xs-12 column">
		<div class="form-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>展期详情</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#extensionApp">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content collapse in" style="margin-top: 15px;" id="extensionApp" name="extensionApp">
				<form id="ExtenAppDetail" method="post" theme="simple" name="operform" action="${webPath}/mfBusExtensionApply/updateAjaxByOne">
					<dhcc:propertySeeTag property="formextensionapply0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>


