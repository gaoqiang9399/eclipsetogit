<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="arch_sort" style="border: 0px;">
						<div class="dynamic-block" title="风险管理详情" name="MfRiskLevelManageAction" data-sort="14" data-tablename="mf_risk_level_manage">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>风险管理详情</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfRiskLevelAdjustAction">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div disable="true" class="content collapse in" id="MfRiskLevelAdjustAction" style="margin-top: 10px;">
									<form method="post" id="MfRiskLevelManageForm" theme="simple" name="operform" action="${webPath }/mfRiskLevelManage/updateAjax">
										<dhcc:propertySeeTag property="formrisklevelmanagedetail" mode="query" />
									</form>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${comeFrom == '1' and mfBusFincAppListSize > 0}">
						<div class="arch_sort" style="border: 0px;">
							<div class="dynamic-block" title="客户借据列表" name="MfCusFincList-div" data-sort="14" data-tablename="mf_bus_finc_app">
								<div class="list-table">
									<div class="title">
										<span class="formName"><i class="i i-xing blockDian"></i>客户借据列表</span>
										<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfCusFincList">
											<i class="i i-close-up"></i><i class="i i-open-down"></i>
										</button>
									</div>
									<div disable="true" class="content collapse in" id="MfCusFincList">
										<div id="content" class="table_content" style="height: auto;">
											${tableHtml }
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${dataMap.size == '1' }">
					<div class="arch_sort" style="border: 0px;">
						<div class="dynamic-block" title="风险处置历史列表" name="MfRiskLevelManageHis-div" data-sort="14" data-tablename="mf_risk_level_manage">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>风险处置历史列表</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfRiskLevelManageHisList">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div disable="true" class="content collapse in" id="MfRiskLevelManageHisList">
									<div id="manageContent" class="table_content" style="height: auto;">
										${dataMap.tableHtml }
									</div>
								</div>
							</div>
						</div>
					</div>
					</c:if>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	<script type="text/javascript" src="${webPath}/component/risk/risklevelmanage/js/MfRiskLevelManage_Detail.js"></script>
	<script type="text/javascript"
		src="${webPath}/component/risk/risklevelmanage/js/MfRiskLevelAdjust_Insert.js"></script>
	<script type="text/javascript">
			var riskId = "${riskId}";
			var webPath = "${webPath}";
			var comeFrom =  '${comeFrom}';
			var mfBusFincAppListSize =  '${mfBusFincAppListSize}';
			$(function(){
				MfRiskLevelManage_Detail.init();
			});	
	</script>
	</body>
</html>