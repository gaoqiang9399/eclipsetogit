<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>风控核查</title>
</head>
<body class="overflowHidden  bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag" style="margin-left: 20px;margin-top: 10px;margin-bottom: 10px;">
				<div class="form-tips">说明：完善当前客户的法人、股东、高管的身份证号和手机号之后方可进行联网核查。</div>
			</div>
			<%--关系人列表--%>
			<div class="list-table" id="corpPartyListDiv">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>关系人</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#corpPartyList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="corpPartyList" name="corpPartyList">
					<dhcc:tableTag paginate="corpPartyList" property="tablecorpPartyList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<%--风控核查记录列表--%>
			<div class="list-table" id="riskVerificationListDiv">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>风控核查记录</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#riskVerificationList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="riskVerificationList" name="riskVerificationList">
					<dhcc:tableTag paginate="riskVerificationList" property="tableriskVerificationList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="formRowCenter">
	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCus_RiskManagement.js"></script>
<script type="text/javascript">
	cusNo = '${cusNo}';
    $(function() {
        MfCus_RiskManagement.init();
    });

</script>
</html>
