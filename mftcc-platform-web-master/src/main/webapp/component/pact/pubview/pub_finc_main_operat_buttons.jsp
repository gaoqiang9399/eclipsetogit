<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<!--信息登记操作入口 -->
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right" id="allButtonDiv"
			>
			<dhcc:pmsTag pmsId="loan-opt-btn">
				<button class="btn btn-opt-dont" id="repay" onclick="repayment();" type="button" disabled>
					<c:if test="${busModel!='13'}">
						<i class="i i-huankuan"></i><span>正常还款</span>
					</c:if>
					<c:if test="${busModel=='13'}">
						<i class="i i-huankuan"></i><span>反转让</span>
					</c:if>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-opt-btn-buyer">
				<button class="btn btn-opt-dont" id="buyerRepay" onclick="buyerRepayment();"
						type="button" disabled>
					<i class="i i-huankuan"></i><span>买方付款</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-opt-btn-tail">
				<button class="btn btn-opt-dont" id="tailPayment" onclick="tailPayment();"
						type="button" disabled>
					<i class="i i-huankuan"></i><span>尾款解付</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-file-filing-btn">
				<button class="btn btn-opt" id="fileArchive"
					onclick="fileArchive();" type="button">
					<i class="i i-guidang"></i><span>文件归档</span>
				</button>
			</dhcc:pmsTag>


			<dhcc:pmsTag pmsId="loan-lawsuit-btn">
				<button class="btn btn-opt" onclick="lawsuit();" type="button"
					id="lawsuitAdd">
					<i class="i i-falv"></i><span>法律诉讼</span>
				</button>
			</dhcc:pmsTag>
		</div>
	</div>
</div>

