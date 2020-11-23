<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	$(function() {
		jQuery.ajax({
			url:webPath+"/mfPolicyInfo/getShowDetailAjax",
			data : {
				appId : appId
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#creditContentdiv").html(data.formHtml);
					$("#creditContent-div").show();
				}
				if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	});
</script>
<!-- 保单表单信息 -->
<div class="row clearfix" id="creditContent-div" style="display:none">
	<div class="col-xs-12 column">
		<div class="form-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>保单信息</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#creditContentdiv">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content collapse in" style="margin-top: 15px;" id="creditContentdiv" name="creditContentdiv">
				<form id="ExtenAppDetail" method="post" theme="simple" name="operform" action="${webPath}/mfBusExtensionApply/updateAjaxByOne">
					<dhcc:propertySeeTag property="formextensionapply0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>


