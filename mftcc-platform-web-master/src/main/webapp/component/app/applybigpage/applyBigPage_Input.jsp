<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
</head>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/applyBigPage.js?v=${cssJsVersion}"></script>

<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/mf_bus_apply.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/mf_cus_pers_base_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/pledge_base_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/mf_cus_bank_acc_manage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/mf_cus_family_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/mf_cus_person_job.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_Insert.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src='${webPath}/component/collateral/js/shiLianAssess.js?v=${cssJsVersion}'></script>
<script type="text/javascript" src='${webPath}/component/cus/js/MfBusTrenchComm.js?v=${cssJsVersion}'></script>

<script type="text/javascript" src='${webPath}/component/app/applybigpage/js/fSelect.js?v=${cssJsVersion}'></script>
<link rel="stylesheet" type="text/css" href="${webPath}/component/app/applybigpage/js/fSelect.css?v=${cssJsVersion}" />

<script type="text/javascript">
	var ajaxData;
	var cusNo = '${cusNo}';
	var sysFlag = '${dataMap.sysFlag}';
	var areaData = null;// 行政区划数据
	$(function() {
		ajaxData = JSON.parse('${ajaxData}');

		$.ajax({
			url : "NmdAreaActionAjax_getAreaListAllAjax.action",
			type : "post",
			async : false,
			dataType : "json",
			success : function(data) {
				areaData = JSON.parse(data.items);
			}
		});

		mf_bus_apply.init();
		mf_cus_pers_base_info.init();
		pledge_base_info.init();
		mf_cus_bank_acc_manage.init();
		mf_cus_family_info.init();
		mf_cus_person_job.init();
		applyBigPage.init();
	});
</script>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<input id="cusNo" name="cusNo" type="hidden" value="${cusNo}" />

					<div id="forms_div">
						<c:forEach items="${configList}" var="configItem">
							<div class="dynamic-block" title="${configItem.funDescribe}" data-sort="1" data-tablename="${configItem.tableName}">
								<div class="list-table">
									<div class="title">
										<span class="formName"><i class="i i-xing blockDian"></i>${configItem.funDescribe}</span>
										<c:if test="${configItem.canMore == '1' || configItem.mustInput != '1'}">
											<button class="btn btn-link formAdd-btn" onclick="applyBigPage.addFormButOnclick(this, '${configItem.bpcId}', '${configItem.tableName}', '${configItem.canMore}', '${configItem.funDescribe}')" title="新增"><i class="i i-jia3"></i></button>
										</c:if>
										<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#${configItem.bpcId}"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
									</div>

									<div class="content collapse in" id="${configItem.bpcId}">
										<c:forEach items="${formListShow}" var="dataItem">
											<c:if test="${configItem.bpcId == dataItem.bpcId}">
												<div id="formDiv_${dataItem.bpcId}">
													<form id="form_${dataItem.tableName}" name="form_${dataItem.tableName}" method="post" theme="simple">
														<input id="bpcId" name="bpcId" type="hidden" value="${dataItem.bpcId}" />
														<input id="tableName" name="tableName" type="hidden" value="${dataItem.tableName}" />
														<input id="funDescribe" name="funDescribe" type="hidden" value="${dataItem.funDescribe}" />
														<input id="showSeq" name="showSeq" type="hidden" value="${dataItem.showSeq}" />
														<input id="saveSeq" name="saveSeq" type="hidden" value="${dataItem.saveSeq}" />
														<input id="canMore" name="canMore" type="hidden" value="${dataItem.canMore}" />
														<input id="formDataId" name="formDataId" type="hidden" value="${dataItem.formDataId}" />

														<div id="formDiv">
															<dhcc:bootstarpTag property="${dataItem.formDataId}" mode="query" />
															<c:if test="${configItem.canMore == '1' || configItem.mustInput != '1'}">
																<input id="delFormBut" name="delFormBut" type="button" value="删除" onclick="applyBigPage.delFormButOnclick(this, '${configItem.bpcId}', '${configItem.mustInput}', '${configItem.funDescribe}')" />
															</c:if>
														</div>
													</form>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>

					<div id="forms_div_base" style="display: none;">
						<c:forEach items="${formListBase}" var="item">
							<div id="formDiv_${item.value.bpcId}">
								<form id="form_${item.value.tableName}" name="form_${item.value.tableName}" method="post" theme="simple">
									<input id="bpcId" name="bpcId" type="hidden" value="${item.value.bpcId}" />
									<input id="tableName" name="tableName" type="hidden" value="${item.value.tableName}" />
									<input id="funDescribe" name="funDescribe" type="hidden" value="${item.value.funDescribe}" />
									<input id="showSeq" name="showSeq" type="hidden" value="${item.value.showSeq}" />
									<input id="saveSeq" name="saveSeq" type="hidden" value="${item.value.saveSeq}" />
									<input id="canMore" name="canMore" type="hidden" value="${item.value.canMore}" />
									<input id="formDataId" name="formDataId" type="hidden" value="" />
	
									<div id="formDiv">
										<dhcc:bootstarpTag property="${item.value.formDataId}" mode="query" />
										<input id="delFormBut" name="delFormBut" type="button" value="删除" onclick="applyBigPage.delFormButOnclick(this, '${configItem.bpcId}', '${configItem.mustInput}', '${configItem.funDescribe}')" />
									</div>
								</form>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="暂存" action="暂存" onclick="applyBigPage.insert(${cusNo})"></dhcc:thirdButton>
			<dhcc:thirdButton value="提交" action="提交" onclick="applyBigPage.submit(${cusNo})"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="applyBigPage.cancelApply(${cusNo})"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>