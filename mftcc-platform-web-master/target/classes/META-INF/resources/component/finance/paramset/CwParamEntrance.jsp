<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
	<link rel="stylesheet" href="${webPath}/component/finance/paramset/css/CwParamEntrance.css" />
	<style type="text/css">
		.re_dialog .ui_icon {
		    vertical-align: top;
		}
		.re_dialog .ui_icon img {
		    width: 48px;
		    height: 48px;
		}
		.re_dialog .ui_icon_bg {
		    margin: 20px 15px 20px 15px;
		}
		.re_dialog .ui_content {
		    display: inline-block;
		    zoom: 1;
		    text-align: left;
		    font-size: 14px;
		    line-height: 1.8;
		}
		.re_dialog .re-initialize h4 {
		    color: red;
		    margin-bottom: 10px;
		    font-size: 16px;
		    font-weight: 700;
		}
		.re_dialog .re-initialize ul li {
		    list-style: square;
		}
		.re_dialog .re-initialize ul {
		    padding-left: 20px;
		    margin-bottom: 10px;
		}
		
		.re_dialog .re-initialize .check-confirm {
	 	    visibility: hidden; 
		    color: red;
		    font-size: 12px;
		}
		
		.re_dialog .ui_buttons {
		    padding: 4px 8px;
		    text-align: right;
		    white-space: nowrap;
		}
	
		.sysInitForm{
			padding: 15px 0;
		}
		.sysInitForm .form_item{
			text-align: center;
			margin-top: 15px;
		}
		.sysInitForm input{
			width: 200px;
			display: inline-block;
		}
		.sysInitForm select{
			width: 200px;
			display: inline-block;
		}
	</style>
</head>
<body >
<div class="container">
		<dhcc:pmsTag pmsId="cw-set-comitem">
			<div class="btn btn-app" id="kemu">
				<div><i class="i i-rss"></i></div>
				<div><i class="i i-guidang"></i></div>
				<div>科目管理</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-pz-word">
			<div class="btn btn-app" id="proofWord">
				<div><i class="i i-bi1"></i></div>
				<div>凭证字设置</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-assist">
			<div class="btn btn-app" id="fuzhuhesuan">
				<div><i class="i i-menu"></i></div>
				<div>辅助核算</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-init-bal">
			<div class="btn btn-app" id="cwInitBal">
				<div><i class="i i-c-refund"></i></div>
				<div>财务初始余额</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-system-param">
			<div class="btn btn-app" id="sysParam">
				<div><i class="i i-chilun"></i></div>
				<div>系统参数</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-re-init">
			<div class="btn btn-app" id="reInit">
				<div><i class="i i-xuanzhuan2"></i></div>
				<div>重新初始化</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-voucher-post">
			<div class="btn btn-app" id="gangwei">
				<div><i class="i i-ren3"></i></div>
				<div>凭证岗位设置</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-voucher-modle">
			<div class="btn btn-app" id="voucherPlate">
				<div><i class="i i-wenjian3"></i></div>
				<div>凭证模版</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-td-plage">
			<div class="btn btn-app" id="voucherDozen">
				<div><i class="i i-wenjian2"></i></div>
				<div>套打模版</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-bo-jz-rule">
			<div class="btn btn-app" id="cwVchRule">
				<div><i class="i i-filesign"></i></div>
				<div>业务记账规则</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-set-assist-class">
			<div class="btn btn-app" id="cwAssetsClass">
				<div><i class="i i-plejiancha"></i></div>
				<div>资产类别</div>
			</div>
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="cw-zt-manager">
			<div class="btn btn-app" id="ztBookManager">
				<div><i class="i i-fangkuai2"></i></div>
				<div>帐套管理</div>
			</div>
		</dhcc:pmsTag>
		<%-- <dhcc:pmsTag pmsId="cw-person-manager"> --%>
			<div class="btn btn-app hidden" id="personManage">
				<div><i class="i i-bumen"></i></div>
				<div>人员管理</div>
			</div>
	<%-- 	</dhcc:pmsTag> --%>
			<div class="btn btn-app " id="cwJTManage">
				<div><i class="i i-budgetmanage"></i></div>
				<div>计提管理</div>
			</div>
			<div class="btn btn-app hidden" id="monPriceTax">
				<div><i class="i i-yuebao"></i></div>
				<div>按月计税管理</div>
			</div>
			<div class="btn btn-app" id="vchOutSoftVer">
				<div><i class="i i-chuku1"></i></div>
				<div>凭证导出设置</div>
			</div>
			<dhcc:pmsTag pmsId="cw_cus_bank_acc_manage">
				<div class="btn btn-app" id="bankAccManage">
					<div><i class="i i-chuku1"></i></div>
					<div>银行账户管理</div>
				</div>
			</dhcc:pmsTag>
<!-- 		<div class="btn btn-app" id="sysInit"> -->
<!-- 			<img alt="借款申请" src="images/OA_7.png" /> -->
<!-- 			<div>财务初始化</div> -->
<!-- 		</div> -->
</div>
</body>
<script type="text/javascript" src="${webPath}/component/finance/paramset/js/CwParamEntrance.js"></script>
<script type="text/javascript">
	// 接收传参等
	CwParmEntrance.path = "${webPath}";
	$(function() {
		CwParmEntrance.init();
		CwParmEntrance.cwAloneUse();
		CwParmEntrance.ztBooksShow();
		CwParmEntrance.monPriceTaxShow();
	});
	var repeat = 0;
	//初始化财务数据
	function cwSystemInit(){
		var dataParam = JSON.stringify($('.sysInitForm').serializeArray()); 
		if(repeat > 0){
			//alert("正在初始化，请勿重复操作!",1);
			alert(top.getMessage("WAIT_OPERATION", "正在初始化"))
			return false;
		}else{
			window.top.LoadingAnimate.start();
			repeat++;
			jQuery.ajax({
				url:webPath+'/cwInitSystem/cwSystemInitAjax',
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						 alert(top.getMessage("SUCCEED_OPERATION", ""),1);
						 location.reload();
					}else if(data.flag == "error"){
						alert(data.msg,0);
						repeat--;
					}
					window.top.LoadingAnimate.stop();
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
					window.top.LoadingAnimate.stop();
					repeat--;
				}
			});
		}
	}
	
	//财务系统重新初始化操作，清理财务数据
	var reinitDialog;
	function reInitSystem(){
		if($('#understand:checked').is(":checked")){
// 			reinitDialog.close();
			if(repeat > 0){
				//alert("正在进行重新初始化，请勿重复操作!",1);
				alert(top.getMessage("WAIT_OPERATION", "正在初始化"))
				return false;
			}else{
				window.top.LoadingAnimate.start();
				repeat++;
				jQuery.ajax({
					url:webPath+"/cwInitSystem/reInitSystemAjax",
					data:{ajaxData:''},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							alert(top.getMessage("SUCCEED_OPERATION", ""),1);
							window.parent.location.reload();
						}else if(data.flag=="error"){
							alert(data.msg,0);
						}
						repeat--;
						window.top.LoadingAnimate.stop();
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
						repeat--;
						window.top.LoadingAnimate.stop();
					}
				});
			}
		}else{
			$('.check-confirm').css('visibility', 'visible');
		}
	}
	
	function closeCallBack() {
		myclose();
	};
</script>
</html>