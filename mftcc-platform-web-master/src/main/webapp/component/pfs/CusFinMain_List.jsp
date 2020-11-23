<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
		 <link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}"> 
		<script type="text/javascript" src='${webPath}/component/pfs/js/CusFinMain_List.js?v=${cssJsVersion}'></script>		
		<script type="text/javascript" >
			var isUpload = false;
			var cusNo = '${cusNo}';
			var webPath = "${webPath}";
			var sysProjectName = "${sysProjectName}";
			var uploader;
			$(function(){
				cusFinMainList.init();
                cusFinMainList.initupload();
				//点击上传
				$('#picker').on('click',function(){
					cusFinMainList.uploadFinReport();
				});
			});

            //打开财务报表多期对比页面 peng-财务改
            function multiPeriodComparisonView(){
                $.ajax({
                    type:"post",
                    url:webPath+"/cusFinMain/checkFinDataAjax?cusNo="+cusNo,
                    dataType:"json",
                    success:function(data){
                        if(data.flag=="success"){
                            LoadingAnimate.stop();
                            var url = webPath+"/cusFinMain/multiPeriodComparisonView?cusNo="+cusNo+"&accRule=1";
                            top.openBigForm(url,'财务报表多期对比',false);
                        }else if(data.flag=="error"){
                            LoadingAnimate.stop();
                            alert(top.getMessage("FIRST_FINC_VERIFY"),0);
                        }
                    },error:function(){
                        LoadingAnimate.stop();
                        window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
                    }
                });
            };

		</script>
	</head>
	<body class="bg-white " style="overflow: hidden;">
		<div class="container">
			<div class="row clearfix  margin_top_20">
				<div class="col-md-12 column">
					<ul class="tab-ul">
						<%--<li >--%>
							<%--<span>资产负债表模板下载</span> <i onclick="cusFinMainList.exportExcel('1');" class="i i-xiazai"></i>--%>
							<%--<i onclick="cusFinMainList.editRptOnline('001');" class="i i-bianji2"></i>--%>
						<%--</li>--%>
						<%--<li >--%>
							<%--<span>利润分配表模板下载</span> <i onclick="cusFinMainList.exportExcel('2');" class="i i-xiazai"></i>--%>
							<%--<i onclick="cusFinMainList.editRptOnline('003');" class="i i-bianji2"></i>--%>
						<%--</li>--%>
						<%--<li >--%>
							<%--<span>现金流量表模板下载</span> <i onclick="cusFinMainList.exportExcel('3');" class="i i-xiazai"></i>--%>
							<%--<i onclick="cusFinMainList.editRptOnline('002');" class="i i-bianji2"></i>--%>
						<%--</li>--%>
                       <%--<dhcc:pmsTag pmsId="cus-edit-SubjectData">--%>
						<%--<li >--%>
							<%--<span>余额科目表模板下载</span> <i onclick="cusFinMainList.exportExcel('subjectBal');" class="i i-xiazai"></i>--%>
							<%--<i onclick="cusFinMainList.editRptOnline('004');" class="i i-bianji2"></i>--%>
						<%--</li>--%>
					   <%--</dhcc:pmsTag>--%>
						<li >
							<span>全部模板下载</span> <i onclick="cusFinMainList.exportExcel('4');" class="i i-xiazai"></i>
							<%--<i onclick="cusFinMainList.editRptOnline('005');" class="i i-bianji2"></i>--%>
						</li>
					</ul>
				</div>
			</div>
			<div class="row clearfix margin_top_20">
				<div class="col-xs-6 col-xs-offset-3 column" id="newParm-div">
<%--					<div style="padding-bottom: 10px">--%>
<%--						<div id="ifMonmerRadio" style="float:left;width: 45%">--%>
<%--							　　<input type="radio" name ="ifMonmer"  value="1" checked>合并--%>
<%--							　　<input type="radio" name ="ifMonmer" value="2" >单体--%>
<%--						</div>--%>
<%--						<div id="caliberRadio">--%>
<%--							　　<input type="radio" name ="caliber"  value="1" checked>税务--%>
<%--							　　<input type="radio" name ="caliber" value="2" >管理--%>
<%--						</div>--%>
<%--					</div>--%>
					<div id="uploader" class="wu-example">
						<div id="thelist" class="cb-upload-div input-group input-group-lg" style="float: left;width: 82%">
							<input name="uploadFile" readonly="readonly" type="text" class="form-control">
<%--							<span id="picker" readonly="readonly" class="input-group-addon pointer">上传...</span>--%>
							<span readonly="readonly" class="input-group-addon pointer" onclick="cusFinMainList.visitPlatForm()">上传财务报表</span>
							<div class="hidden" id="picker-outer"></div>
						</div>
<%--						<div class="cb-upload-div input-group input-group-lg"  style="float: right;width: 10%;" onclick="cusFinMainList.visitPlatForm()">--%>
<%--							<span class="input-group-addon pointer" >众微平台</span>--%>
<%--						</div>--%>
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

			<div class="row clearfix margin_top_30">
				<div class="col-xs-8 col-xs-offset-2 column">
					注：对于同一期不同口径的财务报表，以最终 "数据确认" 为准！
				</div>
			</div>
			<div class="row clearfix margin_top_30">
				<div class="col-xs-8 col-xs-offset-2 column" id="finReport1">
					<div>
						税务口径
					</div>
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
												<c:if test="${cusFinMain.ifMonmer == '1' }">
													<span class="listOpStyle">合并</span>
												</c:if>
												<c:if test="${cusFinMain.ifMonmer == '2' }">
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


			<div class="row clearfix margin_top_20">
				<div class="col-xs-8 col-xs-offset-2 column" id="finReport" style="padding-bottom: 20px;">
					<div>
						管理口径
					</div>
					<div class="table_content" style="padding: 0px">
						<table class="ls_list" id="tablist" width="100%" cellspacing="1" border="0" align="center">
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
							<tbody id="tab">
							<c:choose>
								<c:when test="${(cusFinMainList)!= null && fn:length(cusFinMainList) > 0}">
									<c:forEach items="${cusFinMainList}"  var="cusFinMain">
										<c:if test="${cusFinMain.caliber == 2 }">

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
												<c:if test="${cusFinMain.ifMonmer == '1' }">
													<span>合并</span>
												</c:if>
												<c:if test="${cusFinMain.ifMonmer == '2' }">
													<span>单体</span>
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

			<div class="row clearfix margin_top_30">
				<div class="col-xs-8 col-xs-offset-2 column" id="postError">
					<div>
						报表自动化处理结果信息
					</div>
					<div class="table_content" style="padding: 0px">
						<table class="ls_list" id="tabPostError" width="100%" cellspacing="1" border="0" align="center">
							<colgroup style="width: 10%"></colgroup>
							<colgroup style="width: 30%"></colgroup>
							<colgroup style="width: 20%"></colgroup>
							<colgroup style="width: 30%"></colgroup>
							<colgroup style="width: 10%"></colgroup>
							<thead>
							<tr>
								<th scope="col" name="period" sorttype="0" width="10%" align="center">报表日期</th>
								<th scope="col" name="errorMsg" sorttype="0" width="30%" align="center">错误信息</th>
								<th scope="col" name="subjectName" sorttype="0" width="20%" align="center">错误科目</th>
								<th scope="col" name="fileName" sorttype="0" width="30%" align="center">文件</th>
								<th scope="col" name="pushCapitalScale" sorttype="0" width="10%" align="center">操作</th>
							</tr>
							</thead>
							<tbody id="tabPost">
							<c:choose>
								<c:when test="${(mfCusReportErrorInfoList)!= null && fn:length(mfCusReportErrorInfoList) > 0}">
									<c:forEach items="${mfCusReportErrorInfoList}"  var="reportError">
										<tr>
											<td width="10%" align="center">
												<i style="line-height: 2.5;color:gray;">${reportError.period}</i>
											</td>
											<td width="30%" align="center">
												<i style="line-height: 2.5;color:gray;">${reportError.errorMsg}</i>
											</td>
											<td width="20%" align="center">
												<i style="line-height: 2.5;color:gray;">${reportError.subjectName}</i>
											</td>
											<td width="30%" align="center">
												<i style="line-height: 2.5;color:gray;">${reportError.fileName}</i>
											</td>
											<td width="10%" align="center">
												<a id="oprate" class="abatch" onclick="cusFinMainList.downloadZipAjax(this,'${webPath}/cusFinMain/downloadZipAjax?period=${reportError.period}&cusNo=${reportError.cusNo}');" href="javascript:void(0);">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
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

		</div>
	</body>	
</html>
