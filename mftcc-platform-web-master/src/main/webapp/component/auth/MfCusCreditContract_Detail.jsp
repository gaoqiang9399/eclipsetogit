<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信协议</title>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditContract_Detail.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_input.js'></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
		<script type='text/javascript' src='dwr/engine.js'></script>  
		<script type='text/javascript' src='dwr/util.js'></script>  
		<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<style type="text/css">
			.subBtn{
				margin-left:62px;
				margin-top: -31px;
				background-color:#fff
			}
		</style>
		<script type="text/javascript">
			var path = "${webPath}";
			var basePath = "${webPath}";
			var modelNo = "<%=(String)request.getAttribute("modelNo")%>";
			var cusNo = "${cusNo}";
			var creditAppId = "${creditAppId}";
			var wkfAppId = "${wkfAppId}";
			var pactId = "${pactId}";
			var nodeNo = "${nodeNo}";
			var cusType = "${cusType}";
			var creditType = "${creditType}";
			var processId = "${processId}";
            var busModel = "${busModel}";
			var relId=pactId;
			var appId=creditAppId;
			if(creditType == '2'){
				var creditAppIdPro = "${creditAppIdPro}";
				var adjAppIdPro = "${adjAppIdPro}";
				relId = creditAppIdPro;
				appId = creditAppIdPro;
			}
            var CREDIT_END_DATE_REDUCE ='${CREDIT_END_DATE_REDUCE}';// 授信结束日自动减一天
			var mfCusPorductCreditList = eval("(" + '${json}' + ")").mfCusPorductCreditList;
			var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
			var index = 0;  //动态增加产品计数使用
			var n = 0;//做删除标识
			//var m = 0;
			var tempType="PROTOCOL";//尽调报告
			$(function(){
				//校验客户是否开户
				var params = {
					"cusNo" : cusNo
				};
				mfCusCreditContractDetail.init();
				//加载尽调报告模板
				//MfCusCreditTemplate.template_init();
			});
			var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
	        PageMessageSend.onPageLoad(userId); 
			dwr.engine.setActiveReverseAjax(true);
		 	dwr.engine.setNotifyServerOnPageUnload(true);
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
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditContract/updateAjax">
							<dhcc:bootstarpTag property="formcreditpact0001" mode="query"/>
						</form>
						<c:if test="${busModel != '12'}">
							<div class="list-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>非系统生成相关合同</span>
									<button class="btn btn-link formAdd-btn" onclick="mfCusCreditContractDetail.addPactExtend();" title="新增"><i class="i i-jia3"></i></button>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExtendList">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div class="content collapse in" id="mfBusPactExtendList" name="mfBusPactExtendList">
									<dhcc:tableTag property="tablemfBusPactExtendList" paginate="mfBusPactExtendList" head="true"></dhcc:tableTag>
								</div>
							</div>
						    <div class="form-tips">说明：系统将主要合同已经生成，请点击下方系统生成合同名称，完成编辑后请保存。除系统生成合同以外，可以添加其他合同。</div>
                        </c:if>
                        <%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
	   				</div>
				</div>

			</div>

			<c:if test="${SAVE_ONLY_2 == '0'}">
				<div class="formRowCenter">
					<c:if test="${busModel != '12'}">
						<dhcc:thirdButton value="暂存" action="暂存" onclick="mfCusCreditContractDetail.updateForm('#operform', '0')"></dhcc:thirdButton>
					</c:if>
					<dhcc:thirdButton value="提交" action="提交" onclick="mfCusCreditContractDetail.ajaxUpdate('#operform', '0')"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditContractDetail.close();"></dhcc:thirdButton>
				</div>
			</c:if>
			<c:if test="${SAVE_ONLY_2 == '1'}">
				<div class="formRowCenter">
					<dhcc:thirdButton value="保存" action="保存" onclick="mfCusCreditContractDetail.ajaxUpdate('#operform', '1')"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditContractDetail.close();"></dhcc:thirdButton>
					<dhcc:thirdButton value="提交" action="提交" onclick="mfCusCreditContractDetail.ajaxUpdate('#operform', '0')"></dhcc:thirdButton>
				</div>
			</c:if>
		</div>
	</body>
</html>