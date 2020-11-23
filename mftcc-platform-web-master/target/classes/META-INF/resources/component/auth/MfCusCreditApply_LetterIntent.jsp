<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
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
			var $form=$("#operform");
			var cusBaseType="1";
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
			$(function(){
				mfCusCreditApplyDetail.init();
				//加载尽调尽调报告模板
				//MfCusCreditTemplate.template_init();
			});
	        var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
	        //PageMessageSend.onPageLoad(userId); 
			//dwr.engine.setActiveReverseAjax(true);
		 	//dwr.engine.setNotifyServerOnPageUnload(true);
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<c:if  test="openType == 0">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditApply/updateAjax">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
						</form>
					</div>
					<div class="form-tips" style="color: red;">如果同时发起单笔贷款，请将“是否发起首笔委保业务”选“是”，确认无误后再提交。</div>
					<c:if test="${cusFinRptShowFlag=='1'}">
						<%@ include file="/component/cus/MfCusCustomerFinData_DetailForBus.jsp"%><!-- 客户财务报表 -->
					</c:if>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
				</div>
				<div class="formRowCenter">
					 <dhcc:thirdButton value="提交" action="提交" onclick="mfCusCreditApplyDetail.close('#operform');"></dhcc:thirdButton>
					 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
				</div>
		</div>
	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>  
		<script type='text/javascript' src='${webPath}/dwr/util.js'></script>  
		<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js?v=${cssJsVersion}"></script>
</html>