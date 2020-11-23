<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	$(function() {
		$.ajax({
			url:webPath+"/mfTuningReport/getByIdDetailAjax?reportId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){
			    if (data.flag == "success"){
                    var html = data.reportDetailInfo;
                    $("#appreportDetailForm").empty().html(html);
                }else{
					$("#InterviewInformationDiv").hide();
				}
			}
		});
	});
</script>
<!-- 面访信息-->
<div class="row clearfix" id = "InterviewInformationDiv">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>面访信息</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#appreportDetailInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="appreportDetailInfo" name="appreportDetailInfo">
				<form method="post" theme="simple" id="appreportDetailForm" name="operform" action="${webPath}/mfTuningReport/updateAjaxByOne">
					<dhcc:propertySeeTag property="formappReportBase_detail" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div> 


