<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	var pactFormId = '${param.formId}';
	$(function() {
		$.ajax({
			url:webPath+"/mfCusCreditContract/getCusCreditContractDetailAjax?creditAppId="+creditAppId+"&formId="+pactFormId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.pactSign == '0'){
					$("#cusCreditContractDiv").remove();
				}else{
					var html = data.cusCreditContractDetail;
					$("#cusCreditContractDetailForm").empty().html(html);
				}
			}
		});
	});
</script>
<!-- 面访信息-->
<div class="row clearfix" id="cusCreditContractDiv">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>合同信息</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#cusCreditContractDetailInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="cusCreditContractDetailInfo" name="cusCreditContractDetailInfo">
				<form method="post" theme="simple" id="cusCreditContractDetailForm" name="operform">
					<dhcc:propertySeeTag property="formcusCreditContractDetail" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div> 


