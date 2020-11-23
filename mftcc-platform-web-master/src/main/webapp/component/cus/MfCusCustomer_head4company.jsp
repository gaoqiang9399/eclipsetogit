<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	
</script>
<!--头部主要信息 -->

<div class="row clearfix head-info">
	<!--头像 -->

	<div class="col-xs-3 column text-center head-img">
		<div class="btn btn-link">
			<img id="headImgShow" name="headImgShow" class="img-circle"
				onclick="uploadHeadImg();" />
			<dhcc:pmsTag pmsId="cus-header-change">
				<a class="btn btn-link head-word" onclick="uploadHeadImg();">更换头像</a>
			</dhcc:pmsTag>
		</div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="clearfix">
			<div class="multi-bus pull-right">
				客户共有<span class="moreCnt_apply">申请中业务<a
					class="moreCnt more-apply-count pointer"
					onclick="getMultiBusList('apply');">${dataMap.moreApplyCount}</a>
					笔,
				</span> <span class="moreCnt_pact">在履行合同<a
					class="moreCnt more-pact-count pointer"
					onclick="getMultiBusList('pact');">${dataMap.morePactCount}</a> 笔
				</span> <span class="moreCnt_finc">, 在履行借据<a
					class="moreCnt more-finc-count pointer"
					onclick="getMultiBusList('finc');">${dataMap.moreFincCount}</a> 笔
				</span> <span class="moreCnt_assure">, 为他人担保<a
					class="moreCnt more-assure-count pointer"
					onclick="getMultiBusList('assure');">${dataMap.moreAssureCount}</a>
					笔
				</span>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-xs-10 column">
				<div class="margin_bottom_5">
					<button class="btn btn-link cus head-title"
						onclick="updateCustomerInfo();">${mfCusCustomer.cusName}
					</button>
				</div>
				<!--信息查看入口 -->
				<div class="margin_bottom_10">
					<!-- 客户分类 -->
					<dhcc:pmsTag pmsId="cus-classify-custype">
						<button class="btn btn-view cus-tag" type="button"
							onclick="cusTagHis();">
							<i class="i i-ren2"></i><span id="cusNameRate-span"></span>
						</button>
					</dhcc:pmsTag>
					<%-- <c:if test="${mfCusCustomer.cusBaseType == '1'}">
					<!-- 裁决文书 -->
					<dhcc:pmsTag pmsId="cus-Judgment-document">
						<a href="http://wenshu.court.gov.cn/" target="_blank">
							<button class="btn btn-view btn-dodgerblue" type="button">
								<i class="i i-ren2"></i><span id="cusNameRate-span">裁判文书</span>
							</button>
						</a>
					</dhcc:pmsTag>
					<!-- 失信人 -->
					<dhcc:pmsTag pmsId="cus-dishonest-people">
						<a href="http://shixin.court.gov.cn/" target="_blank">
							<button class="btn btn-view btn-dodgerblue type="button">
								<i class="i i-ren2"></i><span id="cusNameRate-span">失信人</span>
							</button>
						</a>
					</dhcc:pmsTag>
					<!-- 全国被执行人 -->
					<dhcc:pmsTag pmsId="cus-if-the-person" >
						<a href="http://zhixing.court.gov.cn/search/" target="_blank">
							<button class="btn btn-view btn-dodgerblue" type="button">
								<i class="i i-ren2"></i><span id="cusNameRate-span">全国被执行人</span>
							</button>
						</a>
					</dhcc:pmsTag>
					</c:if> --%>
					<!-- 查看资料完整度 -->
					<dhcc:pmsTag pmsId="cus-integrity-datalevel">
						<button class="btn btn-view cus-integrity" type="button"
							onclick="getInfList();">
							<i class="i i-xing2"></i><span id="integrity-span">完整度0%</span>
						</button>
					</dhcc:pmsTag>
					<!-- 查看关联关系 -->
					<dhcc:pmsTag pmsId="cus-relations-ship">
						<button class="btn btn-relation btn-view" type="button"
							onclick="cusRelation();">
							<i class="i i-guanXi"></i><span>关联关系</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-eval-rating">
						<%-- <c:if test='${mfCusCustomer.cusLevelName!=null}'>
												<c:if test='${mfCusCustomer.cusLevelName.contains("A")}' > 
												<button  class="btn btn-forestgreen btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</c:if>
												<c:if test='${mfCusCustomer.cusLevelName.contains("B")}' > 
												<button  class="btn cus-middle btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</c:if>
												<c:if test='${mfCusCustomer.cusLevelName.contains("C")}' > 
												<button  class="btn btn-danger btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</c:if>
											</c:if>
											<c:if test='${mfCusCustomer.cusLevelName==null}'>
												<button class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
													<i class="i i-eval1"></i>未评估
												</button>
											</c:if> --%>

						<button id="cusEvalRating-button"
							class="btn btn-lightgray btn-view " type="button"
							onclick="cusEval.getEvalDetailResult('0');">
							<i class="i i-eval1"></i><span id="cusEvalRating-span">未评估</span>
						</button>
					</dhcc:pmsTag>

					<dhcc:pmsTag pmsId="cus-auth-credit">
						<button id="cusCredit-button" class="btn btn-lightgray btn-view"
							title="授信总额" type="button" onclick="">
							<i class="i i-credit"></i><span class="creditBus">未授信</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-projectCredit-btn">
						<button id="projectCredit-button"
							class="btn btn-lightgray btn-view" title="立项授信额度" type="button"
							onclick="">
							<i class="i i-credit"></i><span class="projectCredit-span">未立项</span>
						</button>
					</dhcc:pmsTag>
					<!-- 贷后检查 -->
					<dhcc:pmsTag pmsId="cus-get-after-check">
						<button id="examineResult" class="btn btn-lightgray btn-view"
							type="button" onclick="BusExamine.examineDetailResult();">
							<i class="i i-fangdajing"></i><span>保后检查</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-risk-level">
						<c:if test="${dataMap.riskLevel > -1}">
							<button class="btn risklevel${dataMap.riskLevel} btn-view"
								type="button" onclick="cusRisk();">
								<i class="i i-risk"></i><span>${dataMap.riskName}</span>
							</button>
						</c:if>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-approve-report">
						<button class="btn btn-view btn-forestgreen" type="button"
							onclick="openCustomerCerReport();">
							<i class="i i-ren2"></i><span>认证报告</span>
						</button>
					</dhcc:pmsTag>
					<!-- 征信查询-->
					<dhcc:pmsTag pmsId="cus_credit_query">
						<button id="creditQuery" class="btn btn-lightgray btn-view"
							type="button"
							onclick="MfCreditQueryRecordInfo.openCreditQuery();">
							<i class="i i-ren2"></i><span>征信查询</span>
						</button>
					</dhcc:pmsTag>
					<!-- 法执情况-->
					<dhcc:pmsTag pmsId="cus-law-enforcement">
						<button id="lawEnforcementQuery"
							class="btn btn-lightgray btn-view" type="button"
							onclick="MfThirdMftccHighcourt.openLawEorcement();">
							<i class="i i-ren2"></i><span>法执情况</span>
						</button>
					</dhcc:pmsTag>
					<!-- 账户状态 -->
					<dhcc:pmsTag pmsId="cus-account-status">
						<button id="cusAccountStatus-button"
							class="btn btn-lightgray btn-view" type="button" onclick="">
							<i class="i i-ren2"></i><span id="cusAccountStatus-span">未开户</span>
						</button>
					</dhcc:pmsTag>

				</div>
				<div>
					<p>
						<span><i class="i i-ren1 "></i> <span id="contactsName">
								<c:if
									test="${mfCusCustomer.contactsName!=null&&mfCusCustomer.contactsName!=''}">
														${mfCusCustomer.contactsName}
													</c:if> <c:if
									test="${mfCusCustomer.contactsName==null||mfCusCustomer.contactsName==''}">
									<span class="unregistered">未登记</span>
								</c:if>
						</span> </span> <span class="vertical-line"></span> <span><i
							class="i i-dianhua "></i> <span id="contactsTel"> <c:if
									test="${mfCusCustomer.contactsTel!=null&&mfCusCustomer.contactsTel!=''}">
													${mfCusCustomer.contactsTel}
												</c:if> <c:if
									test="${mfCusCustomer.contactsTel==null||mfCusCustomer.contactsTel==''}">
									<span class="unregistered">未登记</span>
								</c:if></span></span> <span class="vertical-line"></span> <span><i
							class="i i-idcard2 "></i><span id="idNum"><c:if
									test="${mfCusCustomer.idNum!=null&&mfCusCustomer.idNum!=''}">
													${mfCusCustomer.idNum}
												</c:if> <c:if
									test="${mfCusCustomer.idNum==null||mfCusCustomer.idNum==''}">
									<span class="unregistered">未登记</span>
								</c:if></span></span>
					</p>
				</div>
			</div>
			<div class="col-xs-2 column">
				<div class="i i-warehouse cus-type-font">
					<div class="type-name-div">${cusTypeName}</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div>
				<p>
					<i class="i i-location "></i> <span id="commAddress"> <c:if
							test="${mfCusCustomer.commAddress!=null&&mfCusCustomer.commAddress!=''}">
													${mfCusCustomer.commAddress}
												</c:if> <c:if
							test="${mfCusCustomer.commAddress==null||mfCusCustomer.commAddress==''}">
							<span class="unregistered">未登记</span>
						</c:if>
					</span> <span class="vertical-line"></span> <i class="i i-youjian1 "></i>
					<span id="postalCode"> <c:if
							test="${!empty mfCusCustomer.postalCode}">
													${mfCusCustomer.postalCode}
												</c:if> <c:if test="${empty mfCusCustomer.postalCode}">
							<span class="unregistered">未登记</span>
						</c:if>
					</span>
				</p>
			</div>
		</div>
	</div>
</div>