<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
	<script type="text/javascript" 	src="${webPath}/component/app/makepolicymeeting/js/MfMakingMeetingSummary_Detail.js"></script>
	<script type="text/javascript">
        $(function(){
            MfMakingMeetingSummary_Detail.init();
        });
	</script>
</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="arch_sort" style="border: 0px;">
						<div class="dynamic-block" title="决策会会议纪要详情" name="MfMakingMeetingSummaryAction" data-sort="14" data-tablename="mf_oa_dimission">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>决策会会议纪要详情</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfMakingMeetingSummaryAction">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div disable="true" class="content collapse in" id="MfMakingMeetingSummaryAction" style="margin-top: 10px;">
									<form method="post" id="MfMakingMeetingSummaryForm" theme="simple" name="operform" action="${webPath}/mfMakingMeetingSummary/updateAjax">
										<dhcc:propertySeeTag property="formMakingMeetingSummaryDetail" mode="query"/>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="dynamic-block" title="决策会会议纪要客户列表" name="MfMakingMeetingSummaryListAction" data-sort="14" data-tablename="mf_oa_dimission">
						<div class="list-table">
							<div class="title">
								<span class="formName"><i class="i i-xing blockDian"></i>决策会会议纪要客户列表</span>
								<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfMakingMeetingSummaryListAction">
									<i class="i i-close-up"></i><i class="i i-open-down"></i>
								</button>
							</div>
							<div disable="true" class="content collapse in" id="MfMakingMeetingSummaryListAction" style="margin-top: 10px;">
								<div class="table_content">
									${tableHtml}
								</div>
							</div>
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>