<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo = '${mfBusPact.cusNo}';
			var appId='${appId}';
			var pactId='${pactId}';
			var wkfAppId='${wkfAppId}';
			var taskId='${taskId}';
			var coborrNum = $.parseJSON('${coborrNum}');//共同借款人
			var ajaxData = '${ajaxData}';
    		ajaxData = JSON.parse(ajaxData);
			var relNo = '${mfBusPact.pactId}';// 要件业务编号
			$(function(){
			MfBusBankAccCom.bankAccInit();
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				//只展示无需带产品
				//bindVouTypeByKindNo($("input[name=vouType]"), '${mfBusApply.kindNo}');
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				 $("input[name='accountNo']").on('keyup input',function(){
			           var  accountNo =$(this).val();
			           if(/\S{5}/.test(accountNo)){
			            	this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
			       	 	}
		        });
				 if($("input[name=coborrNum]").is(':visible')){
					 var $obj = $("input[name=coborrNum]");
					 $("input[name=coborrNum]").popupSelection({
							searchOn:true,//启用搜索
							inline:false,//下拉模式
							items:coborrNum,//请求数据URL
						    multiple:true,//多选
							title:"共同借款人",
							handle:false
					});
					 if(typeof($obj.attr('readonly'))!='undefined'){
					    	$obj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
					 }
				}
				
				 var $repayObj = $("input[name=repayType]");
				 if($repayObj.is(':visible')){
					$("input[name=repayType]").popupSelection({
						searchOn:false,//启用搜索
						inline:false,//下拉模式
						multiple:false,//多选选
						title:"还款方式",
						items:ajaxData.repayTypeMap,
						changeCallback : function (obj, elem) {
						}
					}); 
					if(typeof($repayObj.attr('readonly'))!='undefined'){
						$repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
					} 
				}
				
				var $vouObj = $("input[name=vouType]");
				if($vouObj.is(':visible')){
					$("input[name=vouType]").popupSelection({
						searchOn:false,//启用搜索
						inline:false,//下拉模式
						multiple:true,//多选选
						title:"担保方式",
						items:ajaxData.vouTypeMap,
						changeCallback : function (obj, elem) {
						}
					}); 
					if(typeof($vouObj.attr('readonly'))!='undefined'){
						$vouObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
					}
				}
			});
			window.ajaxUpdateForApp = function(obj,callback){
				var flag = submitJsMethod($(obj).get(0), '');
					if(flag){
						alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
							var url = $(obj).attr("action");
							var dataParam = JSON.stringify($(obj).serializeArray()); 
							LoadingAnimate.start();
							jQuery.ajax({
								url:url,
								data:{ajaxData:dataParam,wkfAppId:wkfAppId,taskId:taskId,appId:appId},
								type:"POST",
								dataType:"json",
								beforeSend:function(){  
								},success:function(data){
									LoadingAnimate.stop();
									if(data.flag == "success"){
										 top.flag=true;
										 top.pactUpdateFlag=true;//表示是否是合同签约节点
										 top.pactSts = data.pactSts;
										 top.pactDetailInfo = data.pactDetailInfo;
										 myclose_click();
									}
								},error:function(data){
									 LoadingAnimate.stop();
									 alert(top.getMessage("FAILED_OPERATION"," "),0);
								}
							});
						});
					}
			};
			function getAssName1Dialog(userInfo){
				$("input[name=assName1]").val(userInfo.opName);
				$("input[name=assNo1]").val(userInfo.opNo);
			};
			function updatePactEndDate(){
				var beginDate =  $("input[name=beginDate]").val();
				var termShow = $("input[name=termShow]").val();
				var term = $("input[name=term]").val();
				var termType = $("[name=termType]").val();
				var intTerm = parseInt(term);
				if(1==termType){ //融资期限类型为月 
					var d = new Date(beginDate);
					d.setMonth(d.getMonth()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
					$("input[name=endDate]").val(str);
				}else{ //融资期限类型为日 
					var d = new Date(beginDate);
				 	d.setDate(d.getDate()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
					$("input[name=endDate]").val(str);
				}
			}
			
			//签约时 如果有银行的选择
			function selectBankAcc(){
				var cusNo = $("input[name='cusNo']").val();
				selectBankAccDialog(getBankAccInfoArtDialog,cusNo);
			}
			function getBankAccInfoArtDialog(accountInfo){	
				var accountNo = accountInfo.accountNo;
				var space = " ";		
				var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
				$("input[name='incomAccount']").val(formatAccountNo);
				$("input[name='incomBank']").val(accountInfo.bank);
				$("input[name='incomName']").val(accountInfo.accountName);
				$("input[name='incomAccountName']").val(accountInfo.accountName);
				$("input[name=bankAccId]").val(accountInfo.id);//银行卡Id
			}
			function getBankByCardNumber(obj){
				var identifyNumber = obj.value;
				$.ajax({
					url:webPath+"/bankIdentify/getByIdAjax",
					data:{identifyNumber:identifyNumber},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
							$("input[name=incomBank]").val(data.bankName);
							$("input[name=bankNumbei]").val(data.bankId);
						}else{
							$("input[name=incomBank]").val("");
						}
					},error:function(){
						window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
				formatBankNo(obj);
			}
			//检查Icloud账号是否已经绑定完
			function getIcloudAccount(obj){
				var flag = false;
				$.ajax({
					url:webPath+"/bankIdentify/getByIdAjax",
					data:{},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data){
							flag = true;
						}else{
							flag = false;
						}
					},error:function(){
						flag = false;
					}
				});
				return flag;
			}
			
			function formatBankNo(obj){
				
				var  accountNo = $("input[name='incomAccount']").val();
				
				if(accountNo.length<=19){
					var space = " ";
					var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16,19);
					$("input[name='incomAccount']").val(formatAccountNo);
				}	
			} 
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="pactInsertForm1" theme="simple" name="operform"  action="${webPath}/mfBusPact/updateForAppAjax">
							<dhcc:bootstarpTag property="formpact0008" mode="query"/>
						</form>	
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateForApp('#pactInsertForm1',myclose)"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
</html>
