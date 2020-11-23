<%@page import="app.component.common.BizPubParm"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
    function cusInfoByTrenchPage() {//搞不懂合作机构为啥找不到这个function，先暂时放着
        top.openBigForm('${webPath}/mfCusCustomer/getEntCusListPage?cusNo='+cusNo,'推荐客户列表',function() {});
    }
	$(function(){
        var baseType = '${param.baseType}';//客户类型
		//根据不同客户类型，决定使用哪些个按钮
		if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_QUDAO%>'){//渠道商
            dealButton("#A1,#D,#I1");
			$("#A3").hide();
            $("#A2").hide();
            $("#A1").hide();
            $("#I1").hide();
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_QUDAOB%>'){//个人渠道
			dealButton("#A1,#D,#I1");
			$("#A3").hide();
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_ZIJIN%>'){//资金机构
			dealButton("#A3,#A2,#D,#F2");
			$("#A1").hide();
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_HEXIAN%>'){//核心企业
			dealButton("#A2,#D,#I2");
			$("#A1").hide();
			$("#A3").hide();
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_PERSON%>' ||baseType=='<%=BizPubParm.CUS_BASE_TYPE_CORP%>'){//普通客户
			dealButton("#A2,#AA,#HAIMA,#E,#F,#G,#H,#I,#J,#K");
			$("#A3").hide();
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_DANBAO%>'){//担保公司
			dealButton("#A2,#D");
			$("#A1").hide();
			$("#A3").hide();
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_WAERHOUSE%>'){//仓储机构
            dealButton("#A2,#D");
            $("#A1").hide();
            $("#A3").hide();
            $("#C").hide();
        }else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_COOPAGENCY%>'){//通用合作机构
            dealButton("#A2,#D");;
            $("#A1").hide();
            $("#A3").hide();
            $("#C").hide();
		}
	});
	function dealButton(buttnStr){
		$(buttnStr).show();
	}
</script>
<!--信息登记操作入口 -->
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right">
			<dhcc:pmsTag pmsId="cus-add-creditAmt-btn">
				<button class="btn btn-opt cus-add" onclick="addTrenchCreditAmt();" type="button" id="A1">
					<i class="i i-bi"></i><span>追加额度</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-info-addBtn">
				<button class="btn btn-opt cus-add" onclick="updateCusFormStas();" type="button" id="A2">
					<i class="i i-bi"></i><span>完善资料</span>	
				</button>
			</dhcc:pmsTag>
            <dhcc:pmsTag pmsId="cus-electronic-esign-btn">
                <button class="btn btn-opt cus-add" onclick="cusElectronicEsignBtn();" type="button" id="cusElectronicEsignBtn">
                    <i class="i i-dengji"></i><span>委托付款协议电子签约</span>
                </button>
            </dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-info-confirm">
				<button class="btn btn-opt cus-add" onclick="cusInfoByTrenchPage();" type="button" id="A1">
					<i class="i i-bi"></i><span>客户认定</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-add-agenciesAmt-btn">
				<button class="btn btn-opt cus-add" onclick="addAgenciesCreditAmt();" type="button" id="A3">
					<i class="i i-bi"></i><span>追加额度</span>	
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-auth-btn">
				<button class="btn btn-opt" style="display:none;" onclick="MfCusCredit.getDealerAppAuth('1');" type="button" id="I">
					<i class="i i-credit"></i><span>发起授信</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-trench-btn">
				<button class="btn btn-opt" style="display:none;" onclick="MfCusCredit.getAppAuth('1');" type="button" id="I1">
					<i class="i i-credit"></i><span>发起授信</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-core-company-btn">
				<button class="btn btn-opt" style="display:none;" onclick="MfCusCredit.getAppAuth('1');" type="button" id="I2">
					<i class="i i-credit"></i><span>发起授信</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cw-report-btn">
				<button class="btn btn-opt" style="display:none;" onclick="getPfsDialog();" type="button" id="AA">
					<i class="i i-qian1" ></i><span>财务报表</span>	
				</button>
			</dhcc:pmsTag>
				<button class="btn btn-opt" style="display:none;" id="B">
					<i class="i i-qian1"></i><span>返费记录</span>
				</button>
				<button class="btn btn-opt" style="display:none;" id="C">
					<i class="i i-falv"></i><span>放款确认历史</span>
				</button>
			<dhcc:pmsTag pmsId="coop-fund-btn">
				<button class="btn btn-opt cus-add" onclick="updateCusFormStas();" type="button" name="C">
					<i class="i i-bi"></i><span>完善资料</span>
				</button>
				<button class="btn btn-opt" style="display:none;" name="C" onclick="MfCusCredit.getAppAuth('1');">
					<i class="i i-falv"></i><span>发起授信</span>
				</button>
				<button class="btn btn-opt" style="display:none;" name="C" onclick="MfCusCustomer_CooperativeView.inputOpenFundAccount();">
					<i class="i i-falv"></i><span>基金开户</span>
				</button>
				<button class="btn btn-opt" style="display:none;" name="C" onclick="MfCusCustomer_CooperativeView.inputFundPurchaseInfo();">
					<i class="i i-falv"></i><span>基金认购</span>
				</button>
			</dhcc:pmsTag>
				<button class="btn btn-opt" type="button" style="display:none;" id="D" onclick="cusRelation()">
					<i class="i i-guanXi"></i><span>关联关系</span>	
				</button>
				<button class="btn btn-opt" type="button" style="display:none;" id="F2" onclick="MfBusAgencies.agenciesConfig()">
					<i class="i i-guanXi"></i><span>资金机构配置</span>	
				</button>
				<button class="btn btn-opt" type="button" style="display:none;" id="F1" onclick="MfBusAgencies.depositBondApp()">
					<i class="i i-guanXi"></i><span>存出保证金</span>	
				</button>
			<div class="btn-group" style="display:none" id="HAIMA">
				<button type="button" class="btn btn-opt  dropdown-toggle"  data-toggle="dropdown">
					更多<span class="caret"></span>
				</button>
				<ul class="dropdown-menu btn-opt pull-right" role="menu">
					<li class="btn-opt" role="presentation" onclick="useFundApp();" style="display:none;" id="E">
						<button class="btn btn-opt more-btn-opt" type="button">
							<i class="i i-guanXi"></i><span>用款下拨</span>	
						</button>
					</li>
					<li class="btn-opt" role="presentation" style="display:none;" id="F" onclick="releaseCashDepositApp()">
						<button class="btn btn-opt more-btn-opt" type="button">
							<i class="i i-guanXi"></i><span>保证金释放</span>	
						</button>
					</li>
					<li class="btn-opt" role="presentation" style="display:none;" id="G" onclick="mfBusOpenAccountApp()">
						<button class="btn btn-opt more-btn-opt" type="button">
							<i class="i i-guanXi"></i><span>客户开户</span>	
						</button>
					</li>
					<li class="btn-opt" role="presentation" style="display:none;" id="H" onclick="mfBusCloseAccountApp()">
						<button class="btn btn-opt more-btn-opt" type="button">
							<i class="i i-guanXi"></i><span>客户销户</span>	
						</button>
					</li>
					<dhcc:pmsTag pmsId="cus-auth-recheck_btn">
						<li class="btn-opt" role="presentation" style="display:none;" id="J" onclick="dealerCreditReCheck()">
							<button class="btn btn-opt more-btn-opt" type="button">
								<i class="i i-guanXi"></i><span>授信复核</span>	
							</button>
						</li>
					</dhcc:pmsTag>
					<li class="btn-opt" role="presentation" style="display:none;" id="K" onclick="mfBusRedeemCertificateApp()">
						<button class="btn btn-opt more-btn-opt" type="button">
							<i class="i i-guanXi"></i><span>赎证申请</span>	
						</button>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>