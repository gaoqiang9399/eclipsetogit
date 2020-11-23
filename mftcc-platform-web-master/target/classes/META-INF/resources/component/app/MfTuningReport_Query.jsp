<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src='${webPath}/component/app/js/MfTuningReport_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/app/js/MfTuningReport_Query.js'></script>
		<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_applyInput.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_InputQuery.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript">
		var channelType;
		var appId = '${appId}';
		var ifWrite = '${ifWrite}';//是否可以编辑尽职调查报告
		var entranceType = '${entranceType}';//入口 1-授信/2-业务
		var cusNo = '${cusNo}';//入口 1-授信/2-业务
		var nodeNo = '${nodeNo}';//入口 1-授信/2-业务
		var kindNo = '${kindNo}';
        var busModel = '${busModel}';
		if(entranceType == '2'){
			channelType = '${channelType}';
		}
		$(function(){
			MfTuningReport_Detail.init();
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

			$("#uploadTree").hide();
		});


	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<c:if  test="${ifWrite == true}">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<c:if test="${entranceType=='1'}">
							<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditApply/updateAjax">
								<dhcc:bootstarpTag property="formappreport0002" mode="query"/>
							</form>
							<c:if test="${busModel=='12'}">
								<div class="list-table-replan" id="credittuning1" >
									<div class="title">
										<span>客户在同业授信情况：（可添加多条）</span>
										<button class="btn btn-link formAdd-btn"  onclick="mfCusCreditApplyDetail.creditTuningAdd('1');" title="新增"><i class="i i-jia3"></i></button>
									</div>
									<div class="content margin_left_15 collapse in" id="tuningList1">
										<dhcc:tableTag property="tablecredittuningList1" paginate="tablecredittuningList1" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table-replan" id="credittuning2" >
									<div class="title">
										<span>客户在同业对外担保情况：（可添加多条）</span>
										<button class="btn btn-link formAdd-btn"  onclick="mfCusCreditApplyDetail.creditTuningAdd('2');" title="新增"><i class="i i-jia3"></i></button>
									</div>
									<div class="content margin_left_15 collapse in" id="tuningList2">
										<dhcc:tableTag property="tablecredittuningList2" paginate="tablecredittuningList2" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table-replan" id="credittuning3" >
									<div class="title">
										<span>企业目前合同执行情况：</span>
										<button class="btn btn-link formAdd-btn"  onclick="mfCusCreditApplyDetail.creditTuningAdd('3');" title="新增"><i class="i i-jia3"></i></button>
									</div>
									<div class="content margin_left_15 collapse in" id="tuningList3">
										<dhcc:tableTag property="tablecredittuningList3" paginate="tablecredittuningList3" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table-replan" id="credittuning4" >
									<div class="title">
										<span>2018年末，上下游交易对手情况：</span>
										<button class="btn btn-link formAdd-btn"  onclick="mfCusCreditApplyDetail.creditTuningAdd('4');" title="新增"><i class="i i-jia3"></i></button>
									</div>
									<div class="content margin_left_15 collapse in" id="tuningList4">
										<dhcc:tableTag property="tablecredittuningList4" paginate="tablecredittuningList4" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table-replan" id="credittuning5" >
									<div class="title">
										<span>截至 ${weeks}（最近一期财务报表），上下游交易对手情况：</span>
										<button class="btn btn-link formAdd-btn"  onclick="mfCusCreditApplyDetail.creditTuningAdd('5');" title="新增"><i class="i i-jia3"></i></button>
									</div>
									<div class="content margin_left_15 collapse in" id="tuningList5">
										<dhcc:tableTag property="tablecredittuningList5" paginate="tablecredittuningList5" head="true"></dhcc:tableTag>
									</div>
								</div>
							</c:if>
						</c:if>
						<c:if test="${entranceType=='2'}">
							<form method="post"  theme="simple" name="operform" id="operform" action="${webPath}/mfLoanApply/updateTuningReportAjax">
								<dhcc:bootstarpTag property="formappreport0002" mode="query"/>
							</form>
						</c:if>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<c:if test="${entranceType=='2' && ifWrite == true}">
					<dhcc:thirdButton value="保存" action="保存" typeclass="insertAjax" onclick="MfTuningReport_Query.insertTuningReportAjax('#operform', '1');"></dhcc:thirdButton>
				</c:if>
				<c:if test="${entranceType=='1' && ifWrite == true}">
					<dhcc:thirdButton value="保存" action="保存" typeclass="insertAjax" onclick="MfTuningReport_Query.saveOnlyAjax('#operform');"></dhcc:thirdButton>
				</c:if>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
