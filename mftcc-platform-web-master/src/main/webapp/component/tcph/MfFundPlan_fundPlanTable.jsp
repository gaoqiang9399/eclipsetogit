<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<link rel="stylesheet" href="${webPath}/component/tcph/css/MfFundPlan_fundPlanTable.css" />
<script type="text/javascript" src="${webPath}/component/tcph/js/MfFundPlan_fundPlanTable.js"></script>
<script type="text/javascript">
	$(function(){
		MfFundPlan_fundPlanTable.init();
	});
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="col-md-2" >
						<button type="button" class="btn btn-primary" onclick="MfFundPlan_fundPlanTable.returnPrePage();">返回</button>
						<c:if test="${mfFundPlanTableMap.rowCount > 0}">
							<button type="button" class="btn btn-primary" onclick="MfFundPlan_fundPlanTable.exportExcel();">导出Excel</button>
						</c:if>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">资金计划表</span>
					</div>
					<div class="scroll-content">
						<div class="col-md-8 col-md-offset-2 column margin_top_20">
							<div class="bootstarpTag" align="right" style="margin-right: 30px;">
								<label class="control-label "><font color="#FF0000">*</font>开始日期</label>
								<input type="text" class="" readonly="readonly" name="beginDate" onclick = "fPopUpCalendarDlg()" value="${beginDate}" placeholder=""/>
								<label class="control-label "><font color="#FF0000">*</font>结束日期</label>
								<input type="text" class="" readonly="readonly" name="endDate" onclick = "fPopUpCalendarDlg()" value="${endDate}" placeholder=""/>
								<input type="button" style="margin-left: 20px;" class="btn btn-primary" onclick="MfFundPlan_fundPlanTable.search();" value="搜索"/>
							</div>
						</div>	
					</div>
				</div>
			</div>
		</div>
		<div id = "tableDiv" style = "overflow-x: auto; overflow-y: auto; height: 590px; width:1323px;margin-bottom: 30px;">
		<c:choose>
		<c:when test="${mfFundPlanTableMap.rowCount > 0}">
		<table cellpadding="0" cellspacing="0" border="1" style = "margin: 20px;padding-bottom: 10px;">
			<c:set var="a" value="0"></c:set>
			<c:set var="b" value="0"></c:set>
			<c:set var="c" value="1"></c:set>
			<c:set var="d" value="0"></c:set>
			<c:forEach begin = "0" end="${mfFundPlanTableMap.rowCount - 1}" var = "i">
			<tr>
				<c:forEach begin = "0" end="${mfFundPlanTableMap.columnCount - 1}" var = "j">
					<c:choose>
						<c:when test="${j == 0}">
							<c:if test = "${b == 0}">
								<td rowspan="${mfFundPlanTableMap.list[a].count}" class = "firstTd">预计${mapType[mfFundPlanTableMap.list[a].key]}表</td>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${j==1}">
									<c:choose>
										<c:when test="${b==0}">
											<!--每个类型的单位/日期列-->
											<td class ="second">单位/日期</td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${mfFundPlanTableMap.list[a].count -1 == b}">
													<!--合计行-->
													<td class ="second">&nbsp;</td>
												</c:when>
												<c:otherwise>
													<c:if test = "${d == 0}">
														<td class ="second" rowspan="${mfFundPlanTableMap.list[a].value[c].count}">${mapUnit[mfFundPlanTableMap.list[a].value[c].key]}</td>
													</c:if>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${b==0}">
										<!-- 日期行 -->
											<c:if test = "${j % 2 == 1}">
												<td class = "date" colspan="2">${mfFundPlanTableMap.list[a].value[0].date[((j-1)/2) - 1]}</td>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${mfFundPlanTableMap.list[a].count -1 == b}">
												<!-- 合计行 -->
													<c:choose>
														<c:when test="${j % 2 == 0}">
														<!-- 合计行 -->
															<td class= "project sum">${mfFundPlanTableMap.list[a].value[0].sum[j-2]}</td>
														</c:when>
														<c:otherwise>
															<td class= "amt sum">${mfFundPlanTableMap.list[a].value[0].sum[j-2]}</td>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
														<c:choose>
															<c:when test="${mfFundPlanTableMap.list[a].value[c].count -1 == d}">
															<!-- 合计行 -->
																<c:choose>
																	<c:when test="${j % 2 == 0}">
																	<!-- 合计行 -->
																		<td class= "project sum">${mfFundPlanTableMap.list[a].value[c].value[d].sum[j-2]}</td>
																	</c:when>
																	<c:otherwise>
																		<td class= "amt sum">${mfFundPlanTableMap.list[a].value[c].value[d].sum[j-2]}</td>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${j % 2 == 0}">
																		<td class= "project">${mfFundPlanTableMap.list[a].value[c].value[d].sum[j-2]}</td>
																	</c:when>
																	<c:otherwise>
																		<td class= "amt">${mfFundPlanTableMap.list[a].value[c].value[d].sum[j-2]}</td>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
			<c:if test="${b != 0 and b != mfFundPlanTableMap.list[a].count - 1}">
				<c:choose>
					<c:when test="${mfFundPlanTableMap.list[a].value[c].count - 1 == d}">
						<c:set var="c" value="${c+1}"></c:set>
						<c:set var="d" value="0"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="d" value="${d+1}"></c:set>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:choose>
				<c:when test="${mfFundPlanTableMap.list[a].count - 1 == b}">
					<c:set var="a" value="${a+1}"></c:set>
					<c:set var="b" value="0"></c:set>
					<c:set var="c" value="1"></c:set>
					<c:set var="d" value="0"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="b" value="${b+1}"></c:set>
				</c:otherwise>
			</c:choose>
			</c:forEach>
		</table>
		</c:when>
		<c:otherwise>
			<div style="text-align: center;">暂无数据</div>
		</c:otherwise>
		</c:choose>
		</div>
	</div>
</body>
</html>
