<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/tablelistshowdiv.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wkfApprovalOpinion/findByPageForLoan">
		<input name="execution" type="hidden" value=${execution} />
		<input name="activityName" type="hidden" value=${activityName} />
		<!-- 
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div class="tools_372">
							<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<p class="p_blank">&nbsp;</p>
		 -->
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle"></div>
						</div>
						<dhcc:tableTag paginate="wkfApprovalOpinionList" property="tablewkf0036" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>