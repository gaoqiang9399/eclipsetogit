<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
	</head>
<body class="overflowHidden bg-white">
		<div class="container form-container" style="height: 100%">
			<div class="scroll-content" style="height: 100%;">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag ">
						<form method="post" id="OaTransInsert" theme="simple" name="operform" action="${webPath}/mfOaTrans/insertAjax">
							<dhcc:bootstarpTag property="formoatrans0002" mode="query" />
						</form>
					</div>
					<div style="display: table" class="col-md-12 col-md-offset-0 margin_top_10" id="test">
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<div class="list-table-replan">
									<div class="content margin_left_15 collapse in" id="tableOaTransList">
										<dhcc:tableTag property="tablebrclear0001" paginate="tablebrclear0001" head="true"></dhcc:tableTag>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tabCont"></div>
					<div class="row clearfix" id="messageDiv"></div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="移交" action="移交" typeclass ="insertAjax" ></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel"></dhcc:thirdButton>
			</div>
		</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/trans/js/MfOaTransInsert.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var view ='${view}';
	var processId ='${processId}';
	$(function() {
    	OaTransInsert.init();
	});

</script>
</html>
