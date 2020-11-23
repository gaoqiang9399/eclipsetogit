<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/factor/UIplug/iconmoon/style.css" >
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/common/jqueryWeUI-1.01/weui.css"/>
<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/css/approvalPag.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style type="text/css">
	
</style>
<title>还款详情</title>
<script type="text/javascript">
		 
			//跳回到期还款列表
			/*
			function toList(){
				window.location.href=webPath+"/dingPactApproval/getDingPactApproveList";
			};
			*/
		$(function(){
			var phoneNumber = "${mfCusCustomer.contactsTel}";
			MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function(){
				$("#phone").click(function(){
					dd.biz.telephone.showCallMenu({
					    phoneNumber: phoneNumber, // 期望拨打的电话号码
					    code: '+86', // 国家代号，中国是+86
					    showDingCall: false, // 是否显示钉钉电话
					    onSuccess : function() {},
					    onFail : function() {}
					});
				})
			})
		})	
</script>
</head>
<body>
	<div id="headDiv" class="weui_cells weui_cells_access marginTop0 baner-background">
		<div style="padding:20px;color:#ffffff">
			<table style="width:100%">
				<tr style="width:100%">
					<td colspan='2' style="width:50%;font-size:19px;"><span>${mfCusCustomer.contactsName}</span></td> <%-- ${mfCusCustomer.contactsName} --%> 
					<td rowspan='2' style="width:50%;text-align:right;"><span id='phone' class="ding_circle " style="padding:6px 10px;"><i class="i i-dianhua ding_circle_icon"></i></span></td>
				</tr>
				<tr><td><span>${mfCusCustomer.contactsTel}</span></td></tr>
				<tr><td colspan='4'><span>${mfCusCustomer.commAddress}</span></td></tr>
			</table>	
		</div>
	</div>
	<div class="weui_panel">
		<div class="weui_panel_bd" id="panel_db_1">
			<div class="second_part">
				<div class="weui_cells weui_cells_access"  id="weui-ca">
					
					<a class="weui_cell" href="javascript:;">		
						<div class="weui_cell_hd">
						</div>	
												
						<div class="weui_cell_bd weui_cell_primary">			
								<span class='info-box-icon i i-applyinfo' style="margin-right:10px;font-size:24px"></span><span class="">还款信息</span>		
						</div>			
					</a>						
				</div>
				<div class="mf_list_item">							
					<div class="xiangmuxinxi ding-bule" style="padding-left:18px;">								 
						<!-- <i class="info-box-icon i i-qiye "> --></i><span style="margin-right:5px;">金额总计</span><span style="font-size:20px;">${mfReceivableList[0].benQiYingShouZongJi}</span><span>元</span>						
					</div>						
				</div>						
				<div class="mf_list_item">							
					<div class="baozhengxinxi">								
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">应收本金(元)</span>									
								<p>${mfReceivableList[0].yingShouBenJin}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">应收利息(元)</span>									
								<p>${mfReceivableList[0].yingShouLiXi}</p>								
						</div>								
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">违约金(元)</span>									
								<p>${mfReceivableList[0].yingShouFaXi}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">应收费用(元)</span>									
							<p>${mfReceivableList[0].yingShouFeiYong}</p>								
						</div>								
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">上次结余(元)</span>									
							<p>${mfRepaymentBean.benCiJieYuFormat}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">到期日期</span>									
							<p>${dueDate}</p>								
						</div>							
						<div class="clearfloat"></div>							
					</div>						
				</div>					
			</div>
		</div>
	</div>
	
	
	<!-- <div style="height:75px;width:100%;"></div>
	<div class="ding_btn_div">
			<a href="javascript:;" class="weui_btn weui_btn_primary mf_btn " style='background-color:#38adff' onclick="doFormSubmit('#insertForm');">提交</a>
	</div> -->
</body>

</html>