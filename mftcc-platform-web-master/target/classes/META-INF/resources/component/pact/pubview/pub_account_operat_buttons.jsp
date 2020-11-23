<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<!--信息登记操作入口 -->
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right" id="allButtonDiv">
			<dhcc:pmsTag pmsId="loan-opt-btn">
				<button class="btn btn-opt-dont" id="repay" onclick="MfBusReceBaseInfo_Detail.repayment();" type="button" disabled>
                    <i class="i i-huankuan"></i><span>反转让</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-opt-btn-buyer">
				<button class="btn btn-opt-dont" id="buyerRepay" onclick="MfBusReceBaseInfo_Detail.buyerRepayment();"
						type="button" disabled>
					<i class="i i-huankuan"></i><span>买方付款</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-opt-btn-tail">
				<button class="btn btn-opt-dont" id="tailPayment" onclick="MfBusReceBaseInfo_Detail.tailPayment();"
						type="button" disabled>
					<i class="i i-huankuan"></i><span>尾款解付</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pledge-buy-back-apply">
				<button id ="receBuyBack" class="btn btn-opt" onclick="MfBusReceBaseInfo_Detail.receBuyBack();" type="button">
					<i class="i i-fanzhuanrang"></i><span>账款反转让</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pledge-rece-repo-affirm">
				<button id ="receRepoAffirm" class="btn btn-opt" onclick="MfBusReceBaseInfo_Detail.receRepoAffirm();" type="button">
					<i class="i i-fanzhuanrang"></i><span>账款反转让确认</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-extension-btn">
				<button class="btn btn-opt" type="button"
						onclick="MfBusExtensionCommonForAccount.ExtensionApply();"
						id="extensionApplyButton">
					<i class="i i-bi1"></i><span>展期申请</span>
				</button>
			</dhcc:pmsTag>
		</div>
	</div>
</div>

