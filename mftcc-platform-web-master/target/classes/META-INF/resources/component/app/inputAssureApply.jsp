<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>准入</title>
<script type="text/javascript" src='${webPath}/component/app/js/inputAssureApply.js'></script>
<script type="text/javascript">
			var maxAmt = null;
			var minAmt = null;
			var minTerm = null;
			var maxTerm = null;
			var cusNo = null;
			var jsonStr = '${jsonStr}';
			var jsonObj = JSON.parse(jsonStr);
			var dataMap = <%=request.getAttribute("dataMap")%>;
			var htmlStrCorp = dataMap.htmlStrCorp;
			var htmlStrper = dataMap.htmlStrper;
			var htmlZJ = dataMap.htmlZJ;
			var scNo = '0000000024';//客户要件场景
			var docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&scNo="+scNo;//查询文档信息的url的参数
			var appId = '${appId}';
			$(function(){
				
				assureCusInfo.init();
				//添加产品种类
				assureCusInfo.addProOption(jsonObj);
			});
			
			function getIdType(){
			}
			
// 			function chooseCusType(){
// 				busInfo.chooseCusType();
// 				busInfo.addProOption(jsonObj);
// 			}
			
// 			function getCusMngNameDialog(userInfo){
// 				$("input[name=cusMngName]").val(userInfo.opName);
// 				$("input[name=cusMngNo]").val(userInfo.opNo);
// 			};
			//新增保存
			function insertAppAndCus(obj){
				assureCusInfo.insertAppAndCus(obj);
			}
// 			function getFincUse(obj){
// 				$("input[name=fincUse]").val(obj.fincUse);
// 				$("input[name=fincUseName]").val(obj.fincUseShow);
// 			}
			//返回列表
			function cancle(){
				assureCusInfo.cancleBack();
			}
			
			function checkByKindInfo(obj){
// 				busInfo.checkByKindInfo(obj);
			}
			
// 			selectNextUser=function(){
// 				selectUserDialog(selectUserDialogCallBack);
// 			}
			
// 			function selectUserDialogCallBack(userInfo){
// 				$("input[name=nextOpName]").val(userInfo.opName);
// 				$("input[name=nextOpN0]").val(userInfo.opNo);
// 			}
			
// 			function checkRate(obj){
// 				busInfo.checkRate(obj);
// 			}
		</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-title">进件登记</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="assureCusInsertForm" theme="simple"
						name="operform" action="${webPath}/mfAssureApply/insertAssureApplyAjax">
						<dhcc:bootstarpTag property="formapplyzh00001" mode="query" />
					</form>
				</div>
				<script type="text/javascript">
					assureCusInfo.getCusNo();
				</script>
<!-- 				<div class="row clearfix"> -->
<!-- 					<div class="col-xs-12 column" > -->
<%-- 						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%> --%>
<!-- 					</div> -->
<!-- 				</div> -->
			</div>
		</div>
		<input type="hidden" id="type" value="1"></input>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存"
				onclick="insertAppAndCus('#assureCusInsertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
				onclick="cancle();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
