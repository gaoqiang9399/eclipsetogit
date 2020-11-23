<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/util.js'></script>
		<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js?v=${cssJsVersion}"></script>
		<title>授信申请表单详情</title>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<style type="text/css">
			[data-toggle="collapse"] .i-open-down {
			    display: none;
			}
			.collapsed[data-toggle="collapse"] .i-close-up {
			    display: none;
			}
			.collapsed[data-toggle="collapse"] .i-open-down {
			    display: block;
			}
		</style>
		<script type="text/javascript">
			var modelNo = "<%=(String)request.getAttribute("modelNo")%>";
			var path = "${webPath}";
			var basePath = "${webPath}";
			var appId = "${appId}";//授信申请编号。新增授信申请编号，调整授信编号
			var wkfAppId = "${wkfAppId}";
			var openType = "${openType}";
			var creditFlag = "${creditFlag}";
			var query = "${query}";     //该值标志位是否可以上传
			var mfCusPorductCreditList = eval("(" + '${json}' + ")").mfCusPorductCreditList;
			var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
			var index = 0;  //动态增加产品计数使用
			var cusNo='${cusNo}';
			var cusType='${cusType}';
			var baseType='${baseType}';
			var creditType='${creditType}';
			var processId='${processId}';
			var $form=$("#operform");
			var cusBaseType="1";
            var ifQuotaCalc = '${ifQuotaCalc}';
			//var aloneFlag = true;
			/* var dataDocParm={
					relNo:appId,
					docType:"creditDoc",
					docTypeName:"尽职报告要件资料",
					docSplitName:"尽职报告资料",
					query:query,
			}; */
			var relId=appId;
			var tempType="REPORT";//尽调报告
			var userId ="";
			var creditAppId = '${creditAppId}';
			var nodeNo = '${nodeNo}';
            var reportMatter = '${reportMatter}';
            var  projectShowFlag = '${projectShowFlag}';
			var title ;
			$(function(){
				mfCusCreditApplyDetail.init();
				//加载尽调尽调报告模板
				//MfCusCreditTemplate.template_init();
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
	        var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
	        //PageMessageSend.onPageLoad(userId); 
			//dwr.engine.setActiveReverseAjax(true);
		 	//dwr.engine.setNotifyServerOnPageUnload(true);
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container" id="creditInsert">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20" id="cusCreditApplyDiv">
					<div class="bootstarpTag">
						<c:if  test="openType == 0">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditApply/updateAjax">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
						</form>
						<c:if test="${projectShowFlag=='1'}">
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

					</div>
					<c:if test="${creditType=='2'}">
						<div class="bootstarpTag">
							<c:if  test="openType == 0">
								<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							</c:if>
							<form  method="post" theme="simple" name="supplementform" id="supplementform" action="${webPath}/mfCusCreditApply/updateAjax">
								<dhcc:bootstarpTag property="formsupplementOrder" mode="query"/>
							</form>
						</div>
					</c:if>
					<c:if test="${cusFinRptShowFlag=='1'}">
						<%@ include file="/component/cus/MfCusCustomerFinData_DetailForBus.jsp"%><!-- 客户财务报表 -->
					</c:if>
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
				<!-- 尽调模板模板 -->
				<%-- <div class="col-md-10 col-md-offset-1 column" >
					<div id="template_div">
						<div class="list-table margin_0">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>文档模板</span>
							</div>
						</div>
						<div id="bizConfigs" class="template-config item-div padding_left_15"></div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div> --%>
				</div>
				<div class="formRowCenter" id="saveBtn">
					<dhcc:thirdButton value="暂存" action="暂存" onclick="mfCusCreditApplyDetail.saveOnlyAjax('#operform');"></dhcc:thirdButton>
					<dhcc:thirdButton value="提交" action="提交" onclick="mfCusCreditApplyDetail.close('#operform');"></dhcc:thirdButton>
					 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
				</div>
			<div class="formRowCenter" style="display: none" id="saveBtnCalc">
				<dhcc:thirdButton value="测算" action="测算" typeclass="insertAjax" onclick="mfCusCreditApplyDetail.calcQuotaAjax('#quotaCalc');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="mfCusCreditApplyDetail.changeFormDisplay();"></dhcc:thirdButton>
			</div>
		</div>

		<%--新增页面--%>
		<div class="container form-container" style="display: none;" id="creditTuningInsert">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20"  >
					<div class="bootstarpTag">
						<form  method="post" id="creditTuningInsertForm" theme="simple" name="operform" action="${webPath}/mfCusCreditTuningDetail/insertAjax">
							<dhcc:bootstarpTag property="formcreditTuningBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="添加" action="保存" onclick="mfCusCreditApplyDetail.tuningSave('#creditTuningInsertForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditApplyDetail.tuningCancle();"></dhcc:thirdButton>
			</div>
		</div>

	</body>
</html>