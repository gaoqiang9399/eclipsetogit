<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/pact/assetmanage/js/MfLitigationExpenseApply_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusWhitenameForm" theme="simple" name="operform" action="${webPath}/mfLitigationExpenseApply/insertApplyAjax?flag=0">
							<dhcc:bootstarpTag property="formlitigationexpenseapplybase" mode="query"/>
						</form>
					</div>
				</div>

				<!-- 文件上传  -->
				<div class="col-md-10 col-md-offset-1 margin_top_0">
					<div id="doc_div"></div>
					<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfLitigationExpenseApply_Insert.ajaxSave('#MfCusWhitenameForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfLitigationExpenseApply_Insert.myclose();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
</html>
<script type="text/javascript" >
var no=$("input[name=cusNo]").val();
var cusNo = '0000';
var isCusDoc='cusDoc'; 
var relNo=$("input[name=litigationId]").val();
var scNo = 'litigation_expense_insert';//客户要件场景
var docParm = "cusNo="+cusNo+"&relNo="+relNo+"&scNo="+scNo;//查询文档信息的url的参数  
var applyStatus='6';
var isf='${isFlag}';

$("input[name=assetId]").click(function(){
	var url = "/mfAssetManage/openJsp?applyFlag="+"2";
	openCreatShowDialog(url,"选择客户",50,70,function(data){
		if(data){
		  $("input[name=cusName]").val(data.cusName);
		  $("input[name=cusNo]").val(data.cusNo);
		  $("input[name=litigationAmount]").val(data.overduePrincipal);
		  /*$("input[name=regDate]").val(data.regDate);
  	      $("input[name=applicant]").val(data.applicant);*/
  	      $("input[name=assetId]").val(data.assetId);
		}
	})
});
//金额格式化
function formatPrice(money) {
    var tpMoney = '0.00';
    if (money != null) {
        tpMoney = money;
    }
    tpMoney = new Number(tpMoney);
    if (isNaN(tpMoney)) {
        return '0.00';
    }
    tpMoney = tpMoney.toFixed(2) + '';
    var re = /^(-?\d+)(\d{3})(\.?\d*)/;
    while (re.test(tpMoney)) {
        tpMoney = tpMoney.replace(re, "$1,$2$3")
    }
    return tpMoney;
};
//实时计算费用总额
setInterval(function(){
	var filingFee=Number($("input[name=filingFee]").val().replace(/,/g,''));
	var saveCost=Number($("input[name=saveCost]").val().replace(/,/g,''));
	var securityPremium=Number($("input[name=securityPremium]").val().replace(/,/g,''));
	var appraisalCost=Number($("input[name=appraisalCost]").val().replace(/,/g,''));
	var announcementFee=Number($("input[name=announcementFee]").val().replace(/,/g,''));
	var executionFee=Number($("input[name=executionFee]").val().replace(/,/g,''));
	var postFee=Number($("input[name=postFee]").val().replace(/,/g,''));
	var barFee=Number($("input[name=barFee]").val().replace(/,/g,''));
	var otherCharges=Number($("input[name=otherCharges]").val().replace(/,/g,''));
	var amount=filingFee+saveCost+securityPremium+appraisalCost+announcementFee+executionFee+postFee+barFee+otherCharges
    amount=formatPrice(amount);
	$("input[name=totalExpenses]").val(amount);
	var monry=$("input[name=totalExpenses]").val();
	if(monry=='0.00'){
		var monry=$("input[name=totalExpenses]").val('');
	}
  },100);
$(function() {
	MfLitigationExpenseApply_Insert.init();
});
</script>

