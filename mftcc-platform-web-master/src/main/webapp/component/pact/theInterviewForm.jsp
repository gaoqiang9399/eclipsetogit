<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String wkfAppId = (String)request.getParameter("wkfAppId");
	String taskId = (String)request.getParameter("taskId");
	String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var wkfAppId='<%=wkfAppId%>';
			var taskId='<%=taskId%>';
			var appId='<%=appId%>';
			var cusNo='${cusNo}';
			var scNo ='${scNo}';//客户要件场景
			var docParm = "cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询文档信息的url的参数
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
			});
			window.ajaxUpdate1 = function(obj,callback){
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
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
								window.top.alert(data.msg,3,function(){
									 top.flag=true;
									 top.pactUpdateFlag=true;//表示是否是合同签约节点
									 top.pactDetailInfo = data.pactDetailInfo;
									 var url = webPath+'/mfBusPact/getById?pactId='+data.pactId;
									 $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
									 myclose_click();
								});									
							}
						},error:function(data){
							 LoadingAnimate.stop();
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
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
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
					$("input[name=endDate]").val(str);
				}
			}
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
			};
			
			//shb 根据银行卡号带出开户行和开户行编号等信息
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
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="pactInsertForm" theme="simple" name="operform"  action="${webPath}/mfLoanApply/updatePactInfoAjax">
						<dhcc:bootstarpTag property="formpact0008" mode="query"/>
					</form>	
					</div>
				</div>	
				<div class="row clearfix">
					<div class="col-md-8 col-md-offset-2 column margin_top_20" >
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdate1('#pactInsertForm',myclose)"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
