<%@page import="com.core.domain.screen.FormData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<% 
	List<Map<String,String>> list = (ArrayList)request.getAttribute("mapList");
%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript"
	src='${webPath}/component/app/js/inputBusCommonForm.js'></script>
<script type="text/javascript">
			var kindNo = ${kindNo};
			var cusType = ${cusType};
			var cusSubType = ${dataMap.cusSubType};
			var cusNo = null;
			var mobilemsg = '';
			var maxAmt = null;
			var minAmt = null;
			var minTerm = null;
			var maxTerm = null;
			var creditAmt = null;
			var kindCreditAmt = null;	
			var postalCodemsg = '';
			var jsonStr = $.parseJSON('${jsonStr}');
			$(function(){
				inputBusCommonForm.init();
			});
			
			function getIdType(){
			}
			
			function controlMax(){
				inputBusCommonForm.controlMax();
			}
			function showcheckinfo(){
				inputBusCommonForm.showcheckinfo();
			}
			function postalCodecheck(obj){
				inputBusCommonForm.postalCodecheck(obj);
			}
			//产品种类缺少验证期限
			function checkTerm(obj){
				inputBusCommonForm.checkTerm(obj);
			}
			function getCusMngNameDialog(userInfo){
				$("input[name=cusMngName]").val(userInfo.opName);
				$("input[name=cusMngNo]").val(userInfo.opNo);
			};
			
			function getFincUse(obj){
				$("input[name=fincUse]").val(obj.fincUse);
				$("input[name=fincUseName]").val(obj.fincUseShow);
			}
			
			function checkByKindInfo(obj){
				inputBusCommonForm.checkByKindInfo(obj);
			}
			
			selectNextUser=function(){
				selectUserDialog(selectUserDialogCallBack);
			}
			
			function selectUserDialogCallBack(userInfo){
				$("input[name=nextOpName]").val(userInfo.opName);
				$("input[name=nextOpNo]").val(userInfo.opNo);
			}
			
			function checkRate(obj){
				inputBusCommonForm.checkRate(obj);
			}
			
			function mobilecheck(obj){	
				inputBusCommonForm.mobilecheck(obj);
			}
			
			function chooseCusType(){
				var val = $("input[name=cusType]").val();
				var tmpUrl="/mfLoanApplyAction/inputBusCommonForm?cusType="+val;
				document.location.href=tmpUrl;
			}
			function changeKindNo(){
				kindNo = $("input[name=kindNo]").val();
				var formId = document.getElementsByName('kindNo')[0].form.id;
				inputBusCommonForm.getForm(kindNo,formId);
			}
		</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-title">进件登记</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<%for(int i=0;i<list.size();i++){%>
							<form method="post" id="commonBusAdd" formType='<%=list.get(i).get("formType") %>' theme="simple" name="operform">
								<dhcc:bootstarpTag property='<%=list.get(i).get("property")%>' mode="query" />
							</form>
						<% } %>
				</div>
			</div>
		</div>
		<input type="hidden" id="type" value="1"></input>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存"
				onclick="inputBusCommonForm.insertCommonAjax()"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
				onclick="inputBusCommonForm.cancleBack();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
