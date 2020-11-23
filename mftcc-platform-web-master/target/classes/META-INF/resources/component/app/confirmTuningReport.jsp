<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/wkf_bus_base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>尽职调查</title>
	</head>
	<script>
        var projectName = '${projectName}';
        var busModel = '${busModel}';
	</script>
	<script type="text/javascript" src="${webPath}/component/app/js/confirmTuningReport.js"></script>
	<script type="text/javascript" src="${webPath}/component/nmd/js/parLoanuse.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_applyInput.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_InputQuery.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/app/js/pub_apply_view_closure.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript">	
		var cusNo='${mfBusApply.cusNo}';
		var wkfAppId = '${mfBusApply.wkfAppId}';
		var appId = '${mfBusApply.appId}';
		var kindNo = '${mfBusApply.kindNo}';
		var vouType=${ajaxData};
		var cmpdRateType = '${cmpdRateType}';
		var busModel = '${mfBusApply.busModel}';
		var processId = '${mfBusApply.applyProcessId}';
		var nodeNo = '${nodeNo}';
		var ifQuotaCalc = '${ifQuotaCalc}';
        var form = $("#confirmTuningReport").find("table").attr("title");
		var saveOnly4 = '${SAVE_ONLY_4}';
		var credtitFlag = '${credtitFlag}';
        var title ;
        if(form!=null && form!="undefined"){
            var forms = form.indexOf('m');
            var formId = form.substring(forms+1,form.length);
        }
        var sign = "confirm";//贷前
		$(function() {
			//是否已上收保证金校验
			var params = {
				"cusNo" : cusNo,
				"appId" : appId
			};

			// 是否隐藏 复利利率上浮字段
			if(cmpdRateType =="0"){//隐藏						
				$('input[name=cmpdFloat]').parent('.input-group').hide();
				$('input[name=cmpdFloat]').parents('.tdvalue').prev('td').find('label').hide();
			}
			confirmTuningReport.appId = "${appId}"; 
			confirmTuningReport.init();
			if(ifQuotaCalc == '1'){
			}else{
                $("input[name='quotaCalc']").parent().parent().parent().hide();
			}
            title = $(top.window.document).find("#myModalLabel").text();

            $("[name='isXiaoWeiFlag']").css("color","#13227a");
            $("[name='isXiaoWeiFlag']").prev().css("color","#13227a");
            $("[name='isXiaoWeiFlag']").parent().parent().prev().css("color","#13227a");

            $("[name='isJinYingFlag']").css("color","#13227a");
            $("[name='isJinYingFlag']").prev().css("color","#13227a");
            $("[name='isJinYingFlag']").parent().parent().prev().css("color","#13227a");

            $("[name='isBeiJingFlag']").css("color","#13227a");
            $("[name='isBeiJingFlag']").prev().css("color","#13227a");
            $("[name='isBeiJingFlag']").parent().parent().prev().css("color","#13227a");

            $("[name='isGaoKeJiFlag']").css("color","#13227a");
            $("[name='isGaoKeJiFlag']").prev().css("color","#13227a");
            $("[name='isGaoKeJiFlag']").parent().parent().prev().css("color","#13227a");

            $("[name='inventionPatent']").css("color","#13227a");
            $("[name='inventionPatent']").prev().css("color","#13227a");
            $("[name='inventionPatent']").parent().parent().prev().css("color","#13227a");
		});
		
		//产品种类缺少验证期限
		function checkTerm(obj){
			
		}
		function checkByKindInfo(obj){
		}
		function getFincUse(obj){
			$("input[name=fincUse]").val(obj.fincUse);
			$("input[name=fincUseName]").val(obj.fincUseShow);
		}
		
		function insertTuningReportAjax(obj, temporaryStorage){
			confirmTuningReport.insertTuningReport(obj, temporaryStorage);
		}
	</script>

	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" id="confirmTuningReportDiv">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="confirmTuningReport" theme="simple" name="operform" action="${webPath}/mfLoanApply/updateTuningReportAjax">
						<dhcc:bootstarpTag property="formapply0001" mode="query"/>
					</form>
				</div>
				<div id="coborrNumName" class="row clearfix" >
					<%@ include file="/component/app/MfBusCoborrList.jsp"%>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
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
		<div class="formRowCenter"  id="saveBtn1">
			<dhcc:thirdButton value="暂存" action="暂存" typeclass="insertAjax" onclick="insertTuningReportAjax('#confirmTuningReport', '1');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			<dhcc:thirdButton value="提交" action="提交" typeclass="insertAjax" onclick="insertTuningReportAjax('#confirmTuningReport', '0');"></dhcc:thirdButton>
		</div>
		<c:if test="${SAVE_ONLY_4 == '0'}">
			<div class="formRowCenter" id="saveBtn0">
				<dhcc:thirdButton value="提交" action="提交" typeclass="insertAjax" onclick="insertTuningReportAjax('#confirmTuningReport', '0');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"  onclick="myclose();"></dhcc:thirdButton>
			</div>
		</c:if>
		<c:if test="${SAVE_ONLY_4 == '1'}">
			<div class="formRowCenter"  id="saveBtn1">
				<dhcc:thirdButton value="暂存" action="暂存" typeclass="insertAjax" onclick="insertTuningReportAjax('#confirmTuningReport', '1');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
				<dhcc:thirdButton value="提交" action="提交" typeclass="insertAjax" onclick="insertTuningReportAjax('#confirmTuningReport', '0');"></dhcc:thirdButton>
			</div>
		</c:if>
		<div class="formRowCenter" style="display: none" id="saveBtnCalc">
			<dhcc:thirdButton value="测算" action="测算" typeclass="insertAjax" onclick="confirmTuningReport.calcQuotaAjax('#quotaCalc');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="confirmTuningReport.changeFormDisplay();"></dhcc:thirdButton>
		</div>
	</div>
	</body>
</html>
