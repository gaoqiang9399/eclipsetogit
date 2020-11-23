<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/creditBusinessRelation.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_InputQuery.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_applyInput.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/include/showBusinessCount.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/nmd/js/parLoanuse.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApplyInput.js?v=${cssJsVersion}"></script>
</head>
<style type="text/css">
.mCSB_container {
	min-height: 100%;
}
.text-primary input {
  color: #428bca !important;
  font-weight: bold;
  -webkit-animation:shine 1.5s linear 0.5s infinite; animation:shine 1.5s linear 0.5s infinite;
}
.text-danger input {
  color: #a94442 !important;
  font-weight: bold;
  -webkit-animation:shine 1.5s linear 0.5s infinite; animation:shine 1.5s linear 0.5s infinite;
}

@-webkit-keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
@keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
</style>
<script type="text/javascript">
	var cusNo = '${cusNo}';
	var appId = '${appId}';//传递参数是为了在新增业务页面取消时，返回到原来的页面
	var liftType = '${liftType}';
	var liftId = '${liftId}';
	var liftKindNo = '${liftKindNo}';
	var liftCusNo = '${liftCusNo}';
	var busModel = '';
	var fromPage = '${dataMap.fromPage}';
    var firstKindNo = '';
    var maxAmt = null;
	var minAmt = null;
	var minTerm = null;
	var maxTerm = null;
	var minFincRate = null;
	var maxFincRate = null;
	var creditAmt = null;
	var creditSum = null;
	var kindCreditAmt = null;
	var oldKindNo = firstKindNo;
	var processId = null;
	var ajaxData = ${ajaxData};
    var rechargeAmtBal =  '${rechargeAmtBal}';
    var rechargeAmtBalShow =  '${rechargeAmtBalShow}';
    var withdrawAmtBal =  '${withdrawAmtBal}';
    var withdrawAmtBalShow =  '${withdrawAmtBalShow}';
	//ajaxData = JSON.parse(ajaxData);
	var rateTypeMap = ajaxData.rateType;
	var applyEnt="apply";//业务申请发起入口。cus客户详情发起 apply进件列表发起
	var updateType = '${dataMap.updateType}';
	var projectName = '${projectName}';
	var formId = '${dataMap.formId}';//表单
    var sign = "apply";//申请
    var aList = new Array();
    var breedInitFlag=0;
    var agenicesInitFlag=0;
	$(function() {
		//$("select[name=kindNo]").html("");
        //MfBusApply_InputQuery.jsp.init();
        MfBusApply_applyInput.init();
	});
    //选择借据
    function selectBusFincApp(){
        //var cusNo = $("input[name=cusNo]").val();
        selectFincDialog(_selectFincBack1,cusNo,"选择借据","finc_sts5");
    };
    //选择借据回调
    var _selectFincBack1=function(finc){
        cusNo=finc.cusNo;
        $("input[name=fincId]").val(finc.fincId);
        //借新还旧关联借据隐藏域赋值
        $("input[name=fincIdOld]").val(finc.fincId);
        $("input[name=fincShowId]").val(finc.fincShowId);
        //借据金额
        $("input[name=putoutAmt1]").val(finc.putoutAmt1);
        //开始日期
        $("input[name=intstBeginDate1]").val(finc.intstBeginDate);
        //到期日期
        $("input[name=intstEndDate1]").val(finc.intstEndDateShow);
        //利率 前端转换为两位有效小数
        //利率整数的时候保留两位小数，是小数的时候直接显示
        var lilv=finc.fincRate.toString();
        //可以保留两位小数
        //var value=Math.round(parseFloat(lilv)*100)/100;
        var xsd=lilv.toString().split(".");
        if(xsd.length==1){
            lilv=lilv.toString()+".00";
        }
        if(xsd.length>1){
            if(xsd[1].length<2){
                lilv=lilv.toString()+"0";
            }
        }
        $("input[name=fincRateOld]").val(lilv);
        $("input[name=fincRateOld]").next().html(finc.fincRateUnit);
        //担保方式
        $("[name=vouTypeOld]").val(finc.vouType);
        $("[name=vouTypeOld]").attr("disabled","true");
        //将借据余额自动引入到新业务申请中，作为新业务的申请金额
        $("input[name=appAmt]").val(finc.loanBal1);
        //借新还旧 贷款项目默认为 周转
        var fincUseDes="周转";
        $("input[name=fincUseDes]").val(fincUseDes);
    };
</script>
<body class="overflowHidden bg-white">
	<div class="container form-container" id="normal">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" id="applyBaseDive">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="insertForApplyForm" method="post" theme="simple" name="operform" action="${webPath}/mfBusApply/insertForApplyAjax_query">
						<dhcc:bootstarpTag property="formapply0007_query" mode="query" />
					</form>
				</div>
				<div class="repayplanInfo showOrHide hidden">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>还款计划</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfBusApply_applyInput.insertTr()" title="新增"><i class="i i-jia3"></i></button>
							<button class="btn btn-link pull-right formAdd-btn"
									data-toggle="collapse" data-target="#repayplan-list">
								<i class='i i-close-up'></i> <i class='i i-open-down'></i>
							</button>
						</div>
						<div class="content_table collapse in" id="repayplan-list"></div>
					</div>
				</div>
				<div class="busfeeInfo showOrHide hidden">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>费用标准</span>
							<button class="btn btn-link pull-right formAdd-btn"
								data-toggle="collapse" data-target="#busfee-list">
								<i class='i i-close-up'></i> <i class='i i-open-down'></i>
							</button>
						</div>
						<div class="content_table collapse in" id="busfee-list"></div>
					</div>
				</div>

				<div id="coborrNumName" class="row clearfix" style="display: none">
					<%@ include file="/component/app/MfBusCoborrList.jsp"%>
				</div>
			</div>

			<div class="col-md-10 col-md-offset-1 column margin_top_20" style="display: none" id="quotaCalcDiv">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="quotaCalc" theme="simple" name="operform" action="${webPath}/mfCusCreditApply/calcQuotaAjax">
						<dhcc:bootstarpTag property="formQuotaCalc" mode="query"/>
					</form>
				</div>
			</div>

		</div>

		<div class="formRowCenter" id="applySaveBth">
			<dhcc:thirdButton value="提交" action="提交" onclick="insertForApply('#insertForApplyForm','1');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfBusApply_applyInput.cancelApply();"></dhcc:thirdButton>
		</div>
		<div class="formRowCenter" id="temporaryStorage" style="display: none;">
			<dhcc:thirdButton value="暂存" action="暂存" onclick="insertForApply('#insertForApplyForm','0');"></dhcc:thirdButton>
			<dhcc:thirdButton value="提交" action="提交" onclick="insertForApply('#insertForApplyForm','1');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfBusApply_applyInput.cancelApply();"></dhcc:thirdButton>
		</div>
		<div class="formRowCenter" style="display: none" id="saveBtnCalc">
			<dhcc:thirdButton value="测算" action="测算" typeclass="insertAjax" onclick="calcQuotaAjax('#quotaCalc');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="changeFormDisplay();"></dhcc:thirdButton>
		</div>
		<div style="display: none;" id="fincUse-div"></div>
	</div>
</body>
</html>