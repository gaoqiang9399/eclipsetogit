<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/component/thirdservice/tcph/css/serviceStyle.css" />
		<link rel="stylesheet" href="${webPath}/component/thirdservice/tcph/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/component/thirdservice/juxinli/css/potreport.css" />
		<style type="text/css">
			.selectClass{
				width: 300px;
			}
		</style>
	</head>
	<script type="text/javascript">
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content" style="height:calc(100% - 60px);">
				<div class="col-md-10 col-md-offset-1 column margin_top_20" id="inputContent">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="inputCommonForm" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formapply0001" mode="query" />
						</form>
					</div>
					<div id="result_cont" class="bootstarpTag  fourColumn">
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="查询" action="查询" typeclass ="insertAjax" onclick="MfBusApply_ThirdInput.queryThirdData('#inputCommonForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
</body>
 <script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_ThirdInput.js"></script>
 <script type="text/javascript">

 	var ajaxData = '${ajaxData}';
	$(function() {
		MfBusApply_ThirdInput.init();
	});
    //逾期欠款查询中 对日期时间的控制
    function getEndDate(){
        var date = $('input[name=beginTime]').val();
        return date;
    };
</script>
</html>
