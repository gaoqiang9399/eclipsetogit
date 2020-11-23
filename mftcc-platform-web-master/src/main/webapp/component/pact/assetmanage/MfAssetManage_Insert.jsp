<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/pact/assetmanage/js/MfAssetManage_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div id="infoDiv" style="display:none;height:100%;">
				<%--<iframe src="${webPath}/mfBusApply/getSummary?appId=${mfBusApply.appId}&busEntrance=apply_approve&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>--%>
			</div>
			<div id="submitDiv" class="scroll-content" style="display:none;">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusWhitenameForm" theme="simple" name="operform" action="${webPath}/mfAssetManage/insertApplyAjax?flag=0">
							<dhcc:bootstarpTag property="formassetmanagebase" mode="query"/>
						</form>
					</div>
				</div>
				<!-- 借据列表  -->
				<div class="col-md-10 col-md-offset-1 margin_top_0" id="test">
					<dhcc:pmsTag pmsId="mf-history-service">
						<jsp:include page="/component/pact/assetmanage/MfBusFinAppList.jsp"/>				
					</dhcc:pmsTag>
				</div>

				<!-- 文件上传  -->
				<div class="col-md-10 col-md-offset-1 margin_top_0">
					<div id="doc_div"></div>
					<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
				</div>
	   		</div>
			<div id="submitBtn" class="formRowCenter" style="display:none;">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfAssetManage_Insert.ajaxSave('#MfCusWhitenameForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfAssetManage_Insert.myclose();"></dhcc:thirdButton>
	   		</div>
			<div id="showBtn" class="formRowCenter" style="display:none;">
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" >
var no=$("input[name=cusNo]").val();
var cusNo = '0000';
var isCusDoc='cusDoc'; 
var relNo=$("input[name=assetId]").val();
var scNo = 'asset_insert';//客户要件场景
var docParm = "cusNo="+cusNo+"&relNo="+relNo+"&scNo="+scNo;//查询文档信息的url的参数  
var applyStatus='6';
var applyFlag='${mfAssetManage.applyFlag}';
var isf='${isFlag}';
$("input[name=cusName]").click(function(){
    if(applyFlag=='0'){
        var url = "/component/pact/assetmanage/OverdueCus_getList.jsp";
        openCreatShowDialog(url,"选择客户",70,70,function(data){
            if(data){
                $("input[name=cusName]").val(data.cusName);
                $("input[name=loanNum]").val(data.loanNum);
                $("input[name=loanAmt]").val(data.loanAmt);
                $("input[name=loanBalance]").val(data.loanBalance);
                $("input[name=overduePrincipal]").val(data.overduePrincipal);
                $("input[name=overdueInterest]").val(data.overdueInterest);
                $("input[name=penaltyAmount]").val(data.penaltyAmount);
                $("input[name=proposer]").val(data.proposer);
                $("input[name=regDate]").val(data.regDate);
                $("input[name=cusNo]").val(data.cusNo);

                $.post('/mfAssetManage/getMfBusFinAppList',{cusNo:data.cusNo},function(r){
                    $('#mfBusFinAppList').html(r.htmlStr)
                },'json');
            }
        })
	}else{
        var url = "/mfAssetManage/openJsp";
        openCreatShowDialog(url+"?applyFlag="+applyFlag,"选择客户",70,70,function(data){
            if(data){
                $("input[name=cusName]").val(data.cusName);
                $("input[name=loanNum]").val(data.loanNum);
                $("input[name=loanAmt]").val(data.loanAmt);
                $("input[name=loanBalance]").val(data.loanBalance);
                $("input[name=overduePrincipal]").val(data.overduePrincipal);
                $("input[name=overdueInterest]").val(data.overdueInterest);
                $("input[name=penaltyAmount]").val(data.penaltyAmount);
                /*$("input[name=proposer]").val(data.proposer);
                $("input[name=regDate]").val(data.regDate);*/
                $("input[name=cusNo]").val(data.cusNo);

                $("input[name=lendingBank]").val(data.lendingBank);
                $("input[name=pactBeginDate]").val(data.pactBeginDate);
                $("input[name=compensatoryDate]").val(data.compensatoryDate);
                $("input[name=compensatoryAmount]").val(data.compensatoryAmount);
                $("input[name=caseCourt]").val(data.caseCourt);
                $("input[name=originalOperationStatus]").val(data.originalOperationStatus);
                $("textarea[name=remark]").val(data.remark);
                $("textarea[name=customerStatus]").val(data.customerStatus);
                $("textarea[name=overdueLitigationReasons]").val(data.overdueLitigationReasons);
                $("textarea[name=guarantorCondition]").val(data.guarantorCondition);
                $("textarea[name=litigationPriority]").val(data.litigationPriority);
                $("textarea[name=originalOperationStatus]").val(data.originalOperationStatus);

                $.post('/mfAssetManage/getMfBusFinAppList',{cusNo:data.cusNo},function(r){
                    $('#mfBusFinAppList').html(r.htmlStr)
                },'json');
            }
        })
	}
});
function getDetailPage(obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
    $('#infoDiv').empty();
    $('#infoDiv').append("<iframe src="+url+"marginheight='0' marginwidth='0' frameborder='0' scrolling='auto' width='100%' height='100%' id='iframepage' name='iframepage'></iframe>");
    $("#infoDiv").css("display","block");
    $("#submitBtn").css("display","none");
    $("#submitDiv").css("display","none");
    $("#showBtn").css("display","block");
};
//返回详情页面
function approvalBack(){
    $("#infoDiv").css("display","none");
    $("#showBtn").css("display","none");
    $("#submitDiv").css("display","block");
    $("#submitBtn").css("display","block");
}
$(function() {
	MfAssetManage_Insert.init();
    $("#submitBtn").css("display","block");
    $("#submitDiv").css("display","block");
});
</script>

