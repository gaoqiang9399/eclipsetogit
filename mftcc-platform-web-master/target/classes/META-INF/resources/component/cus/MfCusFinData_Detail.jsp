<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div name="MfCusFinMainAction" title="财务报表"  class="dynamic-block cus-fin">
	<div class="list-table">
		<div class="title">
			<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>
			<button title="新增" onclick="getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>
			<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">
				<i class="i i-close-up"></i><i class="i i-open-down"></i>
			</button>
		</div>
		<div id="CusFinMainAction" class="content collapse in">
			<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<thead>
					<tr>
						<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>
					</tr>
				</thead>
				<tbody id="tab">
					<c:forEach items="${cusFinMainList}" var="cusFinMain" varStatus="status">
						<tr>
							<td align="center" width="10%">
								<a class="abatch" onclick="reportView(this,'${webPath}/cusFinMain/inputReportView.action?finRptType=${cusFinMain.finRptType}&finRptDate=${cusFinMain.finRptDate}&cusNo=${cusFinMain.cusNo}&accRule=1');return false;" href="javascript:void(0);">${cusFinMain.finRptDate}</a>
							</td>
							<td align="center" width="15%">
								<c:if test="${cusFinMain.finCapFlag == true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
									</c:if>
								</c:if> 
								<c:if test="${cusFinMain.finCapFlag != true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>
									</c:if>
								</c:if>
							</td>
							<td align="center" width="15%">
								<c:if test="${cusFinMain.finProFlag == true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
									</c:if>
								</c:if> 
								<c:if test="${cusFinMain.finProFlag != true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if >
									<c:if test="${cusFinMain.finRptSts != 1}">
										<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>
									</c:if>
								</c:if>
							</td>
							<td align="center" width="15%">
								<c:if test="${cusFinMain.finCashFlag == true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
									</c:if>
								</c:if> 
								<c:if test="${cusFinMain.finCashFlag != true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>
									</c:if>
								</c:if>
							</td>
							<td align="center" width="15%">
								<c:if test="${cusFinMain.finSubjectFlag == true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>
									</c:if>
								</c:if>
								<c:if test="${cusFinMain.finSubjectFlag != true}">
									<c:if test="${cusFinMain.finRptSts == 1}">
										上传
									</c:if>
									<c:if test="${cusFinMain.finRptSts != 1}">
										<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>
									</c:if>
								</c:if>
							</td>
							<td align="center" width="20%">
								<c:if test="${cusFinMain.finRptSts == 1}">
									<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
								<c:if test="${cusFinMain.finRptSts != 1}">
									<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,'${webPath}/cusFinMain/updateReportConfirmAjax?finRptType=${cusFinMain.finRptType}&finRptDate=${cusFinMain.finRptDate}&cusNo=${cusFinMain.cusNo}');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
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