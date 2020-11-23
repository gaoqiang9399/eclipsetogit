<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<html>
	<head>
	</head>	
	<body class="overflowHidden bg-white">
	<div class="container form-container" id="normal">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="insertAccreditForm" method="post" theme="simple" name="operform" action="<%=webPath %>/mfOaAccredit/insertAjax">
					    <dhcc:bootstarpTag property="formaccredit0002" mode="query" />  
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfOaAccreditInsert.checkAccredit('#insertAccreditForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
		<div style="display: none;" id="fincUse-div"></div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/accredit/js/MfOaAccreditInsert.js"></script>
	<script type="text/javascript" >
		var funcsData='${dataMap.funcsData}';
		var nowTime = '${nowTime}';
		funcsData = eval('(' + funcsData + ')');
		$(function(){
			MfOaAccreditInsert.init();
		});	
	
		//判断当前时间是否大于等于选择时间
		function checkTime(dateStr){
			var curDate=nowTime.replace(" ","");
			var date=dateStr.replace("-","").replace("-","").replace(" ","").replace(":","").replace(":","");
			return Number(curDate)>=Number(date);
		}
	</script>
</html>