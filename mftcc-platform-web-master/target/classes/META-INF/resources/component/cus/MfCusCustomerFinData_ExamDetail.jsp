<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="dhcc" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<c:if test="${cusBaseType == 1}">
	<div name="MfCusFinMainAction" title="财务报表"
		class="dynamic-block cus-fin">
		<div class="list-table">
			<div class="title">
				<span class="formName"> <i class="i i-xing blockDian"></i>财务报表
				</span>
				<button title="新增" id="pfsAdd" onclick="getPfsDialog();return false;"
					class="btn btn-link formAdd-btn">
					<i class="i i-jia3"></i>
				</button>
				<%--<dhcc:pmsTag pmsId="cus-multi-period-comparison">--%>
					<div style="margin-top: -28px;margin-left: 145px;" >
						<a href="javascript:void(0);" onclick="multiPeriodComparisonView();">多期对比</a>
					</div>
				<%--</dhcc:pmsTag>--%>
				<button data-target="#CusFinMainAction" data-toggle="collapse"
					class=" btn btn-link pull-right formAdd-btn">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div id="CusFinMainAction" class="content collapse in">
				<table cellspacing="1" border="0" align="center" width="100%"
					class="ls_list" id="tablist">
					<colgroup style="width: 10%"></colgroup>
					<colgroup style="width: 10%"></colgroup>
					<colgroup style="width: 10%"></colgroup>
					<colgroup style="width: 10%"></colgroup>
					<colgroup style="width: 10%"></colgroup>
					<tbody id="tab">
					<div class="table_content" style="padding: 0px">
						<table class="ls_list" id="tablist1" width="100%" cellspacing="1" border="0" align="center">
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<thead>
							<tr>
								<th scope="col" name="shareholderName" sorttype="0" width="10%" align="center">报表日期</th>
								<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">资产负债表</th>
								<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">利润分配表</th>
								<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">现金流量表</th>
								<dhcc:pmsTag pmsId="cus-edit-SubjectData">
									<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">科目余额表</th>
								</dhcc:pmsTag>
								<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">类型</th>
								<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">操作</th>
							</tr>
							</thead>
							<tbody id="tab1">
							<c:choose>
								<c:when test="${(cusFinMainList)!= null && fn:length(cusFinMainList) > 0}">
									<c:forEach items="${cusFinMainList}"  var="cusFinMain">
										<c:if test="${cusFinMain.caliber == 1 }">


											<tr>
												<td width="10%" align="center">
													<a class="abatch" onclick="cusFinMainList.reportView('${webPath}/cusFinMain/inputReportView?finRptType=${cusFinMain.reportType}&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}&accRule=1');" href="javascript:void(0);">
															${cusFinMain.weeks }
													</a>
												</td>
												<td width="15%" align="center">
													<c:if test="${cusFinMain.assetsDataId != null }">
														<c:if test="${cusFinMain.reportSts == 2 }">
															<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
														</c:if>
														<c:if test="${cusFinMain.reportSts != 2 }">
															<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
														</c:if>
													</c:if>
													<c:if test="${cusFinMain.assetsDataId == null }">
														<c:if test="${cusFinMain.reportSts==2 }">
															上传
														</c:if>
														<c:if test="${cusFinMain.reportSts!=2 }">
															<a class="abatch" href="javascript:void(0);" onclick="cusFinMainList.uploadFinReport();">上传</a>
														</c:if>
													</c:if>
												</td>
												<td width="15%" align="center">
													<c:if test="${cusFinMain.incomeDataId!=null }">
														<c:if test="${cusFinMain.reportSts == 2 }">
															<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
														</c:if>
														<c:if test="${cusFinMain.reportSts != 2 }">
															<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
														</c:if>
													</c:if>
													<c:if test="${cusFinMain.incomeDataId==null }">
														<c:if test="${cusFinMain.reportSts==2 }">
															上传
														</c:if>
														<c:if test="${cusFinMain.reportSts!=2 }">
															<a class="abatch" href="javascript:void(0);" onclick="cusFinMainList.uploadFinReport();">上传</a>
														</c:if>
													</c:if>
												</td>
												<td width="15%" align="center">
													<c:if test="${cusFinMain.cashDataId!=null }">
														<c:if test="${cusFinMain.reportSts == 2 }">
															<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
														</c:if>
														<c:if test="${cusFinMain.reportSts != 2 }">
															<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
														</c:if>
													</c:if>
													<c:if test="${cusFinMain.cashDataId==null }">
														<c:if test="${cusFinMain.reportSts==2 }">
															上传
														</c:if>
														<c:if test="${cusFinMain.reportSts!=2 }">
															<a class="abatch" href="javascript:void(0);" onclick="cusFinMainList.uploadFinReport();">上传</a>
														</c:if>
													</c:if>
												</td>
												<dhcc:pmsTag pmsId="cus-edit-SubjectData">
													<td width="15%" align="center">
														<c:if test="${cusFinMain.balanceDataId!=null }">
															<c:if test="${cusFinMain.reportSts == 2 }">
																<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
															</c:if>
															<c:if test="${cusFinMain.reportSts != 2 }">
																<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
															</c:if>
														</c:if>
														<c:if test="${cusFinMain.balanceDataId==null }">
															<c:if test="${cusFinMain.reportSts==2 }">
																上传
															</c:if>
															<c:if test="${cusFinMain.reportSts!=2 }">
																<a class="abatch" href="javascript:void(0);" onclick="cusFinMainList.uploadFinReport();">上传</a>
															</c:if>
														</c:if>
													</td>
												</dhcc:pmsTag>
												<td width="10%" align="center">
													<c:if test="${cusFinMain.ifMonmer == 1 }">
														<span class="listOpStyle">合并</span>
													</c:if>
													<c:if test="${cusFinMain.ifMonmer == 2 }">
														<span class="listOpStyle">单体</span>
													</c:if>
												</td>

												<td width="20%" align="center">
													<c:if test="${reportConfirmFlag!=2}">
														<c:if test="${cusFinMain.isBase == 1 }">
															<span class="listOpStyle">默认</span>
														</c:if>
														<c:if test="${cusFinMain.reportSts != 2 }">
															<a id="finDataFirm" class="abatch" onclick="cusFinMainList.confirmFinMain(this,'${webPath}/cusFinMain/updateReportConfirmAjax?finRptType=${cusFinMain.reportType}&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}&accountId=${cusFinMain.accountId}');" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
														</c:if>
														<c:if test="${cusFinMain.reportSts == 2 }">
															<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;
														</c:if>
													</c:if>
													<c:if test="${cusFinMain.isUsed == 1 }">
														<span class="listOpStyle">删除</span>
													</c:if>
													<c:if test="${cusFinMain.isUsed != 1 }">
														<a id="finDataDel" class="abatch_del" onclick="ajaxTrDelete(this,'/cusFinMain/deleteAjax?finRptType=${cusFinMain.reportType}&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}&accountId=${cusFinMain.accountId}');" href="javascript:void(0);">删除</a>
													</c:if>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td style="text-align: center;" colspan="6">暂无数据</td></tr>
								</c:otherwise>
							</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cusBaseType == 2}">
	<c:if test="${cusFinMainList.size() != 0}">
		<div name="MfCusFinMainAction" title="财务报表"
			class="dynamic-block cus-fin">
			<div class="list-table">
				<div class="title">
					<span class="formName"> <i class="i i-xing blockDian"></i>名下企业财务报表
					</span>
					<button title="新增" onclick="getPersonPfsDialog();return false;"
						class="btn btn-link formAdd-btn">
						<i class="i i-jia3"></i>
					</button>
					<button data-target="#CusFinMainAction" data-toggle="collapse"
						class=" btn btn-link pull-right formAdd-btn">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div id="CusFinMainAction" class="content collapse in">
					<table cellspacing="1" border="0" align="center" width="100%"
						class="ls_list" id="tablist">
						<colgroup style="width: 10%"></colgroup>
						<colgroup style="width: 10%"></colgroup>
						<colgroup style="width: 10%"></colgroup>
						<colgroup style="width: 10%"></colgroup>
						<colgroup style="width: 10%"></colgroup>
						<thead>
							<tr>
								<th align="center" width="10%" scope="col"
									name="shareholderName" sorttype="0">报表日期</th>
								<th align="center" width="10%" scope="col"
									name="shareholderName" sorttype="0">企业名称</th>
								<th align="center" width="10%" scope="col"
									name="pushCapitalScale" sorttype="0">资产负债表</th>
								<th align="center" width="10%" scope="col"
									name="pushCapitalScale" sorttype="0">利润分配表</th>
								<th align="center" width="10%" scope="col"
									name="pushCapitalScale" sorttype="0">现金流量表</th>
								<th align="center" width="10%" scope="col"
									name="pushCapitalScale" sorttype="0">操作</th>
							</tr>
						</thead>
						<tbody id="tab">
							<c:forEach items="${ cusFinMainList}" var="cusFinMain"
								varStatus="status">
								<tr>
									<td align="center" width="10%"><a class="abatch"
										onclick="reportView(this,'${webPath}/cusFinMain/inputReportView?finRptType=${cusFinMain.finRptType}&finRptDate=${cusFinMain.finRptDate}&cusNo=${cusFinMain.cusNo}&accRule=1&relationCorpNo=${cusFinMain.relationCorpNo}&relationCorpName=${cusFinMain.relationCorpName}');return false;"
										href="javascript:void(0);">${cusFinMain.finRptDate}</a></td>
									<td align="center" width="25%">
										${cusFinMain.relationCorpName}</td>
									<td align="center" width="15%"><c:if
											test="${cusFinMain.finCapFlag == true}">
											<c:if test="${cusFinMain.finRptSts == 1}">
												<i style="line-height: 2.5; color: gray;"
													class="i i-gouxuan color_theme"></i>
											</c:if>
											<c:if test="${cusFinMain.finRptSts != 1}">
												<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
											</c:if>
										</c:if> <c:if test="${cusFinMain.finCapFlag != true}">
											<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if>
											<c:if test="${cusFinMain.finRptSts != 1}">
												<a class="abatch" href="javascript:void(0);"
													onclick="getPersonPfsDialog();return false;">上传</a>
											</c:if>
										</c:if></td>
									<td align="center" width="15%"><c:if
											test="${cusFinMain.finProFlag == true}">
											<c:if test="${cusFinMain.finRptSts == 1}">
												<i style="line-height: 2.5; color: gray;"
													class="i i-gouxuan color_theme"></i>
											</c:if>
											<c:if test="${cusFinMain.finRptSts != 1}">
												<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
											</c:if>
										</c:if> <c:if test="${cusFinMain.finProFlag != true}">
											<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if>
											<c:if test="${cusFinMain.finRptSts != 1}">
												<a class="abatch" href="javascript:void(0);"
													onclick="getPersonPfsDialog();return false;">上传</a>
											</c:if>
										</c:if></td>
									<td align="center" width="15%"><c:if
											test="${cusFinMain.finCashFlag == true}">
											<c:if test="${cusFinMain.finRptSts == 1}">
												<i style="line-height: 2.5; color: gray;"
													class="i i-gouxuan color_theme"></i>
											</c:if>
											<c:if test="${cusFinMain.finRptSts != 1}">
												<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
											</c:if>
										</c:if> <c:if test="${cusFinMain.finCashFlag != true}">
											<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if>
											<c:if test="${cusFinMain.finRptSts != 1}">
												<a class="abatch" href="javascript:void(0);"
													onclick="getPersonPfsDialog();return false;">上传</a>
											</c:if>
										</c:if></td>
									<td align="center" width="20%"><c:if
											test="${cusFinMain.finRptSts == 1}">
											<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if> <c:if test="${cusFinMain.finRptSts != 1}">
											<a id="finDataFirm" class="abatch"
												onclick="confirmFinMain(this,'${webPath}/cusFinMain/updateReportConfirmAjax?finRptType=${cusFinMain.finRptType}&finRptDate=${cusFinMain.finRptDate}&cusNo=${cusFinMain.cusNo}&relationCorpName=${cusFinMain.relationCorpName}&relationCorpNo=${cusFinMain.relationCorpNo}');return false;"
												href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
                                        <c:if test="${cusFinMain.ifShowDel == '0' }">
                                            <span class="listOpStyle">删除</span>
                                        </c:if>
                                        <c:if test="${cusFinMain.ifShowDel == '1' }">
                                            <a id="finDataDel" class="abatch_del" onclick="ajaxTrDelete(this,'/cusFinMain/deleteAjax?finRptType=${cusFinMain.finRptType}&finRptDate=${cusFinMain.finRptDate}&cusNo=${cusFinMain.cusNo}');" href="javascript:void(0);">删除</a>
                                        </c:if>
                                    </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</c:if>
</c:if>