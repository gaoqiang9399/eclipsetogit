<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
		 <link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}"> 
		<script type="text/javascript" src='${webPath}/component/pfs/js/CusPersonFinMain_List.js?v=${cssJsVersion}'></script>		
		<script type="text/javascript" >
			var isUpload = false;
			var cusNo = '${cusNo}';
			var webPath = "${webPath}";
			$(function(){
				cusPersonFinMainList.init();
			});
		</script>
	</head>
	<body class="bg-white " style="overflow: hidden;">
		<div class="container">
			<div class="row clearfix  margin_top_20">
				<div class="col-md-12 column">
					<ul class="tab-ul">
						<li  onclick="cusPersonFinMainList.exportExcel('1');">
							<span>资产负债表模板下载</span> <i class="i i-xiazai"></i>
						</li>
						<li  onclick="cusPersonFinMainList.exportExcel('2');">
							<span>利润分配表模板下载</span> <i class="i i-xiazai"></i>
						</li>
						<li  onclick="cusPersonFinMainList.exportExcel('3');">
							<span>现金流量表模板下载</span> <i class="i i-xiazai"></i>
						</li>
						<dhcc:pmsTag pmsId="cus-edit-SubjectData">
							<li >
								<span>余额科目表模板下载</span> <i onclick="cusPersonFinMainList.exportExcel('subjectBal');" class="i i-xiazai"></i>
							</li>
						</dhcc:pmsTag>
						<li  onclick="cusPersonFinMainList.exportExcel('4');">
							<span>全部模板下载</span> <i class="i i-xiazai"></i>
						</li>
					</ul>
				</div>
			</div>
			<div class="row clearfix margin_top_80">
				<div class="col-xs-6 col-xs-offset-3 column" id="newParm-div">
					<div id="uploader" class="wu-example">
						<div id="thelist" class="cb-upload-div input-group input-group-lg">
							<input name="uploadFile" readonly="readonly" type="text" class="form-control">
							<span id="picker" readonly="readonly" class="input-group-addon pointer">上传...</span>
						</div>
					</div>
					<div style="color: red; margin-bottom: 50px;" id="showMsg"></div>
				</div>
				<div id="finData" class="data-list" style="display: none">
					<input type="hidden" name="finData">
					<input type="hidden" name="finRptType">
					<input type="hidden" name="finRptDate">
					<input type="hidden" name="cusName">
				</div>
			</div>
			<div class="row clearfix margin_top_60">
				<div class="col-xs-8 col-xs-offset-2 column" id="finReport">
					<div>已上传<span class="color_theme"> ${cusFinMainList.size()} </span>期财务报表,上传连续多期报表,让评级更准确,风控更有效！</div>
					<div class="table_content finData-div">
						<table class="ls_list" id="tablist" width="100%" cellspacing="1" border="0" align="center">
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<thead>
								<tr>
									<th scope="col" name="shareholderName" sorttype="0" width="10%" align="center">报表日期</th>
									<th scope="col" name="shareholderName" sorttype="0" width="10%" align="center">名下企业名称</th>
									<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">资产负债表</th>
									<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">利润分配表</th>
									<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">现金流量表</th>
									<dhcc:pmsTag pmsId="cus-edit-SubjectData">
										<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">科目余额表</th>
									</dhcc:pmsTag>
									<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">操作</th>
								</tr>
							</thead>
							<tbody id="tab">
								<c:if test="${fn:length(cusFinMainList)>0}">
									<c:forEach items="${cusFinMainList }" var="cusFinMain" >
										<tr>
											<td width="10%" align="center">
												<a class="abatch" onclick="cusPersonFinMainList.reportView('${webPath}/cusFinMain/inputReportView?finRptType=${cusFinMain.reportType }&finRptDate=${cusFinMain.weeks }&cusNo=${cusFinMain.cusNo}&relationCorpName=${cusFinMain.corpName}&relationCorpNo=${cusFinMain.corpNo}&accRule=1');" href="javascript:void(0);">
													${cusFinMain.weeks }
												</a>
											</td>
											<td width="15%" align="center">
												${cusFinMain.corpName}
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
														<a class="abatch" href="javascript:void(0);" onclick="cusPersonFinMainList.uploadFinReport();">上传</a>
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
														<a class="abatch" href="javascript:void(0);" onclick="cusPersonFinMainList.uploadFinReport();">上传</a>
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
														<a class="abatch" href="javascript:void(0);" onclick="cusPersonFinMainList.uploadFinReport();">上传</a>
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
															<a class="abatch" href="javascript:void(0);" onclick="cusPersonFinMainList.uploadFinReport();">上传</a>
														</c:if>
													</c:if>
												</td>
											</dhcc:pmsTag>
											<td width="20%" align="center">
												<c:if test="${reportConfirmFlag!=2}">
													<c:if test="${cusFinMain.reportSts != 2 }">
														<a id="finDataFirm" class="abatch" onclick="cusPersonFinMainList.confirmFinMain(this,'${webPath}/cusFinMain/updateReportConfirmAjax?finRptType=${cusFinMain.reportType}&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}&relationCorpNo=${cusFinMain.corpNo}');" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
													</c:if>
													<c:if test="${cusFinMain.reportSts == 2 }">
														<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;
													</c:if>
												</c:if>
												<c:if test="${cusFinMain.isUsed == 1 }">
													<span class="listOpStyle">删除</span>
												</c:if>
												<c:if test="${cusFinMain.isUsed != 1 }">
													<a id="finDataDel" class="abatch_del" onclick="ajaxTrDelete(this,'/cusFinMain/deleteAjax?finRptType=${cusFinMain.reportType}&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}&relationCorpNo=${cusFinMain.corpNo}');" href="javascript:void(0);">删除</a>
												</c:if>
											</td>

											<%--<td width="20%" align="center">--%>
												<%--<c:if test="${cusFinMain.finRptSts != 1 }">--%>
													<%--<a id="finDataFirm" class="abatch" onclick="cusPersonFinMainList.confirmFinMain(this,'${webPath}/cusFinMain/updateReportConfirmAjax?finRptType=${cusFinMain.reportType }&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}&relationCorpName=${cusFinMain.corpName}&relationCorpNo=${cusFinMain.corpNo}');" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;--%>
												<%--</c:if>--%>
												<%--<c:if test="${cusFinMain.finRptSts == 1 }">--%>
													<%--<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;--%>
												<%--</c:if>--%>
                                                <%--<c:if test="${cusFinMain.ifShowDel == '0' }">--%>
                                                    <%--<span class="listOpStyle">删除</span>--%>
                                                <%--</c:if>--%>
                                                <%--<c:if test="${cusFinMain.ifShowDel == '1' }">--%>
                                                    <%--<a id="finDataDel" class="abatch_del" onclick="ajaxTrDelete(this,'/cusFinMain/deleteAjax?finRptType=${cusFinMain.reportType}&finRptDate=${cusFinMain.weeks}&cusNo=${cusFinMain.cusNo}');" href="javascript:void(0);">删除</a>--%>
                                                <%--</c:if>--%>
											<%--</td>--%>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${fn:length(cusFinMainList)<=0}">
									<tr><td style="text-align: center;" colspan="6">暂无数据</td></tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
