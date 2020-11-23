<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.no-data{ 
margin:0 auto; 
width:400px; 
height:100px; 
text-align: center;
line-height: 100px;
font-size: 15px;
} 
</style>

<title>列表</title>
<script type="text/javascript"
	src="${webPath}/component/analysistable/js/MfAnalysisTable_AnalysisTable.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function() {
		MfAnalysisTable_AnalysisTable.init();
	});
</script>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<c:forEach items="${listDataMap}" var="map">
				<div class="row clearfix" style="padding:0px 20px 20px 20px;">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>${map.tableShowName }</span>
							<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#${map.className}"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						</div>
						<div id="${map.className}" class="content collapse in">
							<div id="assetProtect-form" class="row clearfix">
								<div class="col-md-10 col-md-offset-1 margin_top_20">
									<div class="bootstarpTag">
										<div class="form-tips">说明：差异字段显示为红色字体。</div>
										<form method="post" id="${map.className}Form" theme="simple" name="operform">
											<table class="table table-bordered" title="${map.tableName}">
												<tbody>
												<tr>
													<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label "></label></td>
													<td class="tdvalue  right" colspan="2" rowspan="1" style="border-right:1px solid #DDDDDD" >
														<div class="input-group" >
															<input title=""
																   name=""
																   class="form-control"
																   readonly="readonly"
																   type="text"
																   value="旧业务"

															>
														</div>
													</td>

													<td class="tdvalue  right" colspan="2" rowspan="1">
														<div class="input-group" style="width: 350px">
															<input title=""
																   name=""
																   class="form-control"
																   readonly="readonly"
																   type="text"
																   value="新业务"
															>
														</div>
													</td>
												</tr>
												 <c:forEach items="${map.list}" var="mfAnalysisTable">
													<tr>
														<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">${mfAnalysisTable.fieldName }</label></td>
														<td class="tdvalue  right" colspan="2" rowspan="1" style="border-right:1px solid #DDDDDD" >
															<div class="input-group" >
															<input title="${mfAnalysisTable.fieldName}"
															name="${mfAnalysisTable.field}"
															class="form-control"
															readonly="readonly"
															type="text"
															value="${mfAnalysisTable.firstVal}"
															<c:if test="${mfAnalysisTable.ifEqual == '0' }"> style="color:red;"</c:if>
															>
															<span class="input-group-addon">${mfAnalysisTable.unit}</span>
															</div>
														</td>

														<td class="tdvalue  right" colspan="2" rowspan="1">
															<div class="input-group" style="width: 350px">
															<input title="${mfAnalysisTable.fieldName}" 
															name="${mfAnalysisTable.field}" 
															class="form-control"  
															readonly="readonly"  
															type="text" 
															value="${mfAnalysisTable.secondVal}"
															<c:if test="${mfAnalysisTable.ifEqual == '0' }"> style="color:red;"</c:if>
															>
															<span class="input-group-addon">${mfAnalysisTable.unit}</span>
															</div>
														</td>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				</c:forEach>
				<c:if test="${listDataMap == null or listDataMap.size() == 0 }">
					<div class="no-data">
						暂无数据
					</div>
				</c:if>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>
