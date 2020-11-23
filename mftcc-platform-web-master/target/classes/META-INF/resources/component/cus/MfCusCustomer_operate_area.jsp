<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

</script>
<c:if test='${operable != "query"}'>
	<!-- 1-公司客户 -->
	<c:if test="${mfCusCustomer.cusBaseType == '1'}">
		<!--信息登记操作入口 -->
		<div class="row clearfix btn-opt-group">
			<div class="col-xs-12 column">
				<div class="btn-group pull-right">
					<dhcc:pmsTag pmsId="cus-info-addBtn">
						<button class="btn btn-opt cus-add"
							onclick="MfCusDyForm.updateCusFormStas();" stype="display:none;"
							type="button">
							<i class="i i-bi"></i><span>完善资料</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-info-slip">
						<button id="slip" class="btn btn-opt cus-slip"
								onclick="MfCusDyForm.GetNotCusInfo();" stype="display:none;"
								type="button">
							<i class="i i-bi"></i><span>客户转单</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="apply-new-btn">
						<button class="btn btn-opt" onclick="applyInsert();">
							<i class="i i-jia2"></i><span>新增业务</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cw-report-btn">
						<button class="btn btn-opt" onclick="getPfsDialog();"
							type="button">
							<i class="i i-qian1"></i><span>财务报表</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-eval-btn">
						<button class="btn btn-opt" onclick="cusEval.getInitatEcalApp('${mfCusCustomer.cusNo}','4','1');"
							type="button">
							<i class="i i-eval1"></i><span>发起评级</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-evalman-btn">
						<button class="btn btn-opt" onclick="cusEval.getManEvalApp();"
							type="button">
							<i class="i i-eval1"></i><span>外部评级</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-credit-calc-btn">
						<button class="btn btn-opt" onclick="cusEval.getInitatEcalAppY(4);"
								type="button">
							<i class="i i-eval1"></i><span>授信测算</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-credit-intention">
						<button class="btn btn-opt" onclick="MfCusCredit.getIntentionAppAuth();"
								type="button">
							<i class="i i-eval1"></i><span>意向申请</span>
						</button>
					</dhcc:pmsTag>
					<%--<c:if test="${cusCreditAddPro =='0'}">
						<dhcc:pmsTag pmsId="cus-auth-btn">
							<button id="creditApply" class="btn btn-opt"
								onclick="MfCusCredit.getAppAuth('1');" type="button">
								<i class="i i-credit"></i><span>发起授信</span>
							</button>
						</dhcc:pmsTag>
					</c:if>--%>
					<%--<dhcc:pmsTag pmsId="cus-projectCredit-btn">
						<button id="projectApply" class="btn btn-opt"
							onclick="MfCusCredit.getAppAuth('2');" type="button">
							<i class="i i-credit"></i><span>发起立项</span>
						</button>
					</dhcc:pmsTag>--%>
					<div class="btn-group">
						<button type="button" class="btn btn-opt  dropdown-toggle"
							data-toggle="dropdown">
							更多<span class="caret"></span>
						</button>
						<ul class="dropdown-menu btn-opt pull-right" role="menu">
                            <dhcc:pmsTag pmsId="cus-report-auth-btn">
                                <button id="reportApply" class="btn btn-opt"
                                        onclick="reportApply();" type="button">
                                    <i class="i i-credit"></i><span>贷前报告</span>
                                </button>
                            </dhcc:pmsTag>
		                 <c:if test="${dataMap.moreFincCount !='0'}">
                            <dhcc:pmsTag pmsId="cus-do-after-check-btn">
                                <button class="btn btn-opt"
                                        onclick="BusExamine.loanAfterExamine();" type="button">
                                    <i class="i i-qian1"></i><span>贷后检查</span>
                                </button>
                            </dhcc:pmsTag>
						 </c:if>
                            <dhcc:pmsTag pmsId="cus-tracing-btn">
                                <button class="btn btn-opt" onclick="cusTrack('0');" type="button">
                                    <i class="i i-dianhua"></i><span>客户跟进</span>
                                </button>
                            </dhcc:pmsTag>
                           <%-- <dhcc:pmsTag pmsId="cus-transHis-btn">
                                <button class="btn btn-opt" onclick="MfCusDyForm.getCusTransHis();">
                                    <i class="i i-ren"></i><span>客户移交历史</span>
                                </button>
                            </dhcc:pmsTag>--%>
							<dhcc:pmsTag pmsId="cus-relations-btn">

								<li class="btn-opt" role="presentation"
									onclick="cusRelationIn();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-guanXi"></i><span>关联关系</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="cus-classify-btn">
								<li class="btn-opt" role="presentation" onclick="cusTag();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">客户分类</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="cus-openaccount-btn">
								<li class="btn-opt" role="presentation"
									onclick="mfBusOpenAccountApp();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">客户开户</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="cus-closeaccount-btn">
								<li class="btn-opt" role="presentation"
									onclick="mfBusCloseAccountApp();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">客户销户</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="cus-releasecashdeposit-btn">
								<li class="btn-opt" role="presentation"
									onclick="releaseCashDepositApp();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">保证金释放</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="bus-redeemcertificate-btn">
								<li class="btn-opt" role="presentation"
									onclick="redeemCertificateApp();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">赎证申请</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="cus-autoclassify-btn">
								<li class="btn-opt" role="presentation" onclick="cusAutoClassify();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">客户自动分类</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<!-- 客户共享 -->
							<dhcc:pmsTag pmsId="cus-customer-share">
								<li class="btn-opt" role="presentation" onclick="MfCusCustomerShare.openCusCustomerShare();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren2"></i><span id="cusCustomerShare">客户共享</span>
									</button>
								</li>
							</dhcc:pmsTag>
						</ul>
					</div>
					<dhcc:pmsTag pmsId="cus-expansion">
						<button class="btn btn-opt" onclick="expansion();" type="button">
							<i class="i i-qian1"></i><span id="expansion">一键收起</span>
						</button>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${mfCusCustomer.cusBaseType == '2'}">
		<!--信息登记操作入口 -->
		<div class="row clearfix btn-opt-group">
			<div class="col-xs-12 column">
				<div class="btn-group pull-right">
					<dhcc:pmsTag pmsId="cus-info-addBtn">
						<button class="btn btn-opt cus-add"
							onclick="MfCusDyForm.updateCusFormStas();" stype="display:none;"
							type="button">
							<i class="i i-bi"></i><span>完善资料</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-info-slip">
						<button id="slip" class="btn btn-opt cus-slip"
								onclick="MfCusDyForm.GetNotCusInfo();" stype="display:none;"
								type="button">
							<i class="i i-bi"></i><span>客户转单</span>
						</button>
					</dhcc:pmsTag>

					<dhcc:pmsTag pmsId="apply-new-btn">

						<button class="btn btn-opt" onclick="applyInsert();">
							<i class="i i-jia2"></i><span>新增业务</span>
						</button>
					</dhcc:pmsTag>
					<!-- 								<button class="btn btn-opt"  onclick="addService();"> -->
					<!-- 									<i class="i i-xinxihecha"></i><span>联网核查</span> -->
					<!-- 								</button> -->
					<dhcc:pmsTag pmsId="person-cw-report-btn">
						<button class="btn btn-opt" onclick="getPersonPfsDialog();"
							type="button">
							<i class="i i-qian1"></i><span>名下企业财务报表</span>
						</button>
					</dhcc:pmsTag>

					<dhcc:pmsTag pmsId="person-eval-btn">
						<button class="btn btn-opt" onclick="cusEval.getInitatEcalApp('${mfCusCustomer.cusNo}','4','1|2');"
								type="button">
							<i class="i i-eval1"></i><span>发起评级</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-credit-calc-btn">
						<button class="btn btn-opt" onclick="cusEval.getInitatEcalAppY(4);"
								type="button">
							<i class="i i-eval1"></i><span>授信测算</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="cus-credit-intention">
						<button class="btn btn-opt" onclick="MfCusCredit.getIntentionAppAuth();"
								type="button">
							<i class="i i-eval1"></i><span>意向申请</span>
						</button>
					</dhcc:pmsTag>

					<!-- <div class="btn-group">
										<button type="button" class="btn btn-opt hidden-lg dropdown-toggle"  data-toggle="dropdown">
											更多<span class="caret"></span>
										</button>
										<ul class="dropdown-menu btn-opt pull-right" role="menu">
											<li class="btn-opt hidden-lg" role="presentation" onclick="cusTag();">
												<button class="btn btn-opt more-btn-opt" onclick="cusTag();" type="button">
													<i class="i i-ren"></i><span id="cusTag">客户分类</span>
												</button>
											</li>
										</ul>
									</div> -->
					<div class="btn-group">
						<button type="button" class="btn btn-opt  dropdown-toggle"
							data-toggle="dropdown">
							更多<span class="caret"></span>
						</button>
						<ul class="dropdown-menu btn-opt pull-right" role="menu">
							<dhcc:pmsTag pmsId="cus-evalman-btn">
								<button class="btn btn-opt" onclick="cusEval.getManEvalApp();"
										type="button">
									<i class="i i-eval1"></i><span>外部评级</span>
								</button>
							</dhcc:pmsTag>
                            <dhcc:pmsTag pmsId="cus-report-auth-btn">
                                <button id="reportApply" class="btn btn-opt"
                                        onclick="reportApply();" type="button">
                                    <i class="i i-credit"></i><span>贷前报告</span>
                                </button>
                            </dhcc:pmsTag>
		                   <c:if test="${dataMap.moreFincCount !='0'}">
                            <dhcc:pmsTag pmsId="cus-do-after-check-btn">
                                <button class="btn btn-opt"
                                        onclick="BusExamine.loanAfterExamine();" type="button">
                                    <i class="i i-qian1"></i><span>贷后检查</span>
                                </button>
                            </dhcc:pmsTag>
						   </c:if>
                            <dhcc:pmsTag pmsId="cus-relations-btn">
                                <button class="btn btn-opt" onclick="cusRelationIn();"
                                        type="button">
                                    <i class="i i-guanXi"></i><span>关联关系</span>
                                </button>
                            </dhcc:pmsTag>
                            <dhcc:pmsTag pmsId="cus-tracing-btn">
                                <button class="btn btn-opt" type="button" onclick="cusTrack('0');">
                                    <i class="i i-dianhua"></i><span>客户跟进</span>
                                </button>
                            </dhcc:pmsTag>
                            <%--<dhcc:pmsTag pmsId="cus-transHis-btn">
                                <button class="btn btn-opt" onclick="MfCusDyForm.getCusTransHis();">
                                    <i class="i i-ren"></i><span>客户移交历史</span>
                                </button>
                            </dhcc:pmsTag>--%>
							<dhcc:pmsTag pmsId="cus-classify-btn">
								<li class="btn-opt" role="presentation" onclick="cusTag();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-ren"></i><span id="cusTag">客户分类</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="cus-channel-self-putout-set-btn">
								<li class="btn-opt" role="presentation"
									onclick="channelSelfPutoutSet();">
									<button class="btn btn-opt more-btn-opt" type="button">
										<i class="i i-chilun"></i><span id="cusTag">放款控制</span>
									</button>
								</li>
							</dhcc:pmsTag>
							<c:if test="${mfCusCustomer.cusType == '204'}">
								<dhcc:pmsTag pmsId="cus-family-assets-liabilities-btn">
									<li class="btn-opt" role="presentation"
										onclick="showFamilyTable();">
										<button class="btn btn-opt more-btn-opt" type="button">
											<i class="i i-excel"></i><span id="cusTag">家庭资产表</span>
										</button>
									</li>
								</dhcc:pmsTag>
							</c:if>
							<c:if test="${mfCusCustomer.cusType == '204'}">
								<dhcc:pmsTag pmsId="cus-family-profit-btn">
									<li class="btn-opt" role="presentation"
										onclick="showFamilyProfitTable();">
										<button class="btn btn-opt more-btn-opt" type="button">
											<i class="i i-excel"></i><span id="cusTag">家庭利润表</span>
										</button>
									</li>
								</dhcc:pmsTag>
							</c:if>
						</ul>
					</div>
					<dhcc:pmsTag pmsId="cus-expansion">
						<button class="btn btn-opt" onclick="expansion();" type="button">
							<i class="i i-qian1"></i><span id="expansion">一键收起</span>
						</button>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</c:if>
</c:if>