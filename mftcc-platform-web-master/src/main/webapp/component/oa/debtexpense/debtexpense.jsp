<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
	<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript">
		$(function(){
			if(${sumReturnAmt<0}){
				$("#sumReturnAmt").css("color","red");
				$("#gaga").attr("hidden" ,"hidden");
				$("#title").attr("title","你欠公司"+${-sumReturnAmt}+"元");
			   // $(".li").html("借款总额：<span style='color:#32c5cb;'>"+${sumApplyAmt}+"</span>元,报销总额：<span style='color:#32c5cb;'>"+${sumExpenseAmt}+"</span>元");
			}else{
				$("#sumReturnAmt").css("color","#32b5cb");
				$("#title").attr("title","公司欠你"+${sumReturnAmt}+"元");
				//$(".li").html("公司欠你<span style='color:#32c5cb;'>"+${sumReturnAmt}+"</span>元");
			}
		 });
  
</script>	
<style type="text/css">
.block-view {
	text-align: left;
	border: 1px solid #32b5cb;
    border-radius: 0px;
    height: 39px;
    padding: 4px 12px;
    width: 86px;
    display: inline-block;
    color: white;
}
.head-info {
    background: rgb(248, 249, 252);
    padding: 0px 0px;
}
.block-view:hover {
    border: 1px solid #009db7;
    color: white;
}
.head-img img{
    width: 132px;
    height: 132px;
}
</style>

</head>

<body class="overflowHidden">
	<div class="container block-left">
		<div class="tabCont">
			<!--头部主要信息 -->
			<div class="row clearfix head-info" style="border-bottom: solid 1px #32b5cb;height: 135px;">
				<!--头像 -->
				<div class="col-xs-2 column text-center head-img">
						<img id="headImgShow" name="headImgShow" class="img-circle" class="img-circle" src="${webPath}/themes/factor/images/op_user.jpg"/>
				</div>
				<!--概要信息 -->
				<div class="col-xs-4 column head-content" style="margin-left: -2%;">
					<div class="margin_bottom_5" >
						<button  class="btn btn-link head-title"  id="user_name">
							
						</button>
					</div>
					<div style="margin-top: 20px;">
						<p>
							<span><i class="i i-ren1"></i><span id ="user_job"></span></span>
							<span class="vertical-line">|</span>
							<span><i class="i i-ren1"></i><span id ="user_brName"></span></span>
							<span class="vertical-line">|</span>
							<span><i class="i i-idcard2" ></i><span id="user_idNo"></span></span>
						</p>
						<p style="width: 110%;">
							<span><i class="i i-location"></i><span id = "contactsName">北京市东城区</span></span>
							<span class="vertical-line">|</span>
							<span><i class="i i-dianhua"></i><span id = "user_mobile"></span></span>
							<span class="vertical-line">|</span>
							<span><i class="i i-youjian1" ></i><span id="user_email"></span></span><!-- margin-top: 17%;margin-left: 30%; -->
						</p>
					</div>
				</div>
				<div id = "title" class="col-xs-2 column head-content" style="margin-top: 36px;margin-left: 9%;">
				<p>个人收支：</p><p id="sumReturnAmt" style="color:red;font-size: 2em;"><span id="gaga">+</span>${sumReturnAmt}</p>
				</div>
				<div class="col-xs-4 column head-content"style="padding-left: 15%;margin-left: -7%;">
							<dhcc:pmsTag pmsId="oa-debt-apply-btn">
								<div id="debtInsert" class="btn block-view" data-view="cuswarehouse" style="margin-top: 56px; padding-top: 9px;background-color: #32b5cb;" >
									<span>借款申请</span>
								</div>
							</dhcc:pmsTag>
							<dhcc:pmsTag pmsId="oa-baoxiao-apply-btn">
								<div id="expenseInsert" class="btn block-view" data-view="cuscore" style="margin-top: 56px;padding-top: 9px;background-color: #32b5cb;margin-left: 2%;" >
									</i><span>报销申请</span>
								</div>
							</dhcc:pmsTag>
				</div>
			</div>
			<div class="row clearfix bg-white ">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<ol class="breadcrumb pull-left">
								<li class="active li"><span>借款总额：<span style="color:#32c5cb;">${sumApplyAmt}</span>元，报销总额：<span style="color:#32c5cb;">${sumExpenseAmt}</span>元</li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=名称/登记时间"/>
					</div>
					
				</div>
			</div>
		</div>
			<!--隔离衔接线-->
			<!--客户其他信息-->
		<div class="row clearfix">
			<div class="col-xs-12 column" style= "margin-left:-1%;width:101.65%">
				<div class="block-add" >
				<!--页面显示区域-->
					<div class="container" id="mf_content">
						
						<div class="row clearfix">
							<div class="col-md-12 column">
								<div id="content" class="table_content"  style="height:auto;">
								</div>
							</div>
						</div>
					</div>
					<%@ include file="/component/include/PmsUserFilter.jsp"%>
				</div>
			</div>
		</div>	
	</div>
</body>
 <script type="text/javascript" src="${webPath}/component/oa/debtexpense/js/MfOaDebtexpenseList.js"></script>
<script>
var opNo="${opNo}";
var url=webPath+"/sysUser/getByIdAjax?opNo="+opNo+"&query=query";
$(function(){
	mfOaDebtExpenseList.init();
});			
</script>
</html>