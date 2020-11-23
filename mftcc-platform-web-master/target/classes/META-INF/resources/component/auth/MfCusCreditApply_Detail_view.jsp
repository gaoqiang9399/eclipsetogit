<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	var applyFormId = '${param.formId}';
	$(function() {
		$.ajax({
			url:webPath+"/mfCusCreditApply/getCusCreditApplyDetailAjax?creditAppId="+creditAppId+"&formId="+applyFormId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.cusCreditApplyDetail;
				$("#cusCreditApplyDetailForm").empty().html(html);
			}
		});
	});
</script>
<!-- 面访信息-->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>申请信息</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#cusCreditApplyDetailInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="cusCreditApplyDetailInfo" name="cusCreditApplyDetailInfo"  >
				<form method="post" theme="simple" id="cusCreditApplyDetailForm" name="operform" action="${webPath}/mfCusCreditApply/updateAjaxByOne">
					<dhcc:propertySeeTag property="formcusCreditApplyDetail" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div> 


