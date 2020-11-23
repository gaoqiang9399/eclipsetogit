<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
		
	</head>
	<script type="text/javascript">
	var cusNo = '${cusNo}';
	//var tradeList = eval("(" + '${json}' + ")").tradeList;
	$(function(){
		var optionVouType = $("select[name=vouType]").find("option");
		makeOptionsJQ(optionVouType, "5,4");
	});
	function getFincUse(obj){
		$("input[name=fincUse]").val(obj.fincUse);
		$("input[name=fincUseName]").val(obj.fincUseShow);
	};
	function cancelApply(){
		window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo;
	};
	function riskTest(obj){
		if(submitJsMethod(obj,'')){
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/mfBusApply/createRiskAjax",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
 					LoadingAnimate.stop();
					if(data.flag == "success"){
						if(data.passFlag == "pass"){
							alert("风险检测通过",0);
							/* if(submitJsMethod(obj,'')){
								$(obj).submit();
							} */
						}else{
							dialog({
								id:"infoDialog",
								backdropOpacity:0,
								content:$("#riskInfo-div")
							}).showModal();
						}
						
					}else if(data.flag == "error"){
						 alert("风险检测失败",0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}else{
			
		}
	};
	function showRiskInfo(){
		var appId= $("input[name=appId]").val();
		top.createShowDialog(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息');
	};
	function hideDialog(){
		 dialog.get('infoDialog').close();
	};
	function submitForm(){
		var obj = $("form[name=operform]");
		 dialog.get('infoDialog').close();
		 //if(submitJsMethod(obj,'')){
			 obj.submit();
		//}
		 
	};
	function getCusInfoArtDialog(cusInfo){
		$("input[name=cusNameTogetherBorrower]").val(cusInfo.cusName);
		$("input[name=cusNoTogetherBorrower]").val(cusInfo.cusNo);
	};
	
	function getCusMngNameDialog(userInfo){
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};
	
	function getOrgNameDialog(userInfo){
		$("input[name=orgName]").val(userInfo.opName);
		$("input[name=orgNo]").val(userInfo.opNo);
	};
	
	function getAssName1Dialog(userInfo){
		$("input[name=assName1]").val(userInfo.opName);
		$("input[name=assNo1]").val(userInfo.opNo);
	};
	
	function getAssName2Dialog(userInfo){
		$("input[name=assName2]").val(userInfo.opName);
		$("input[name=assNo2]").val(userInfo.opNo);
	};
	function insertForApply(obj){
			var url = $(obj).attr("action");
			var flag=submitJsMethod($(obj).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				LoadingAnimate.start();
				$.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag=="success"){
							window.location.href = webPath+'/mfBusApply/getSummary?appId='+data.appId;
						}else{
							alert(  top.getMessage("FAILED_SAVE"),0);
						}
					},error:function(){
						LoadingAnimate.stop();
						alert(  top.getMessage("FAILED_SAVE"),0);
					}
			});
			}else{
				alert(  top.getMessage("FAILED_SAVE"),0);
			} 
		};
		//验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
		function checkByKindInfo(obj){
			var name = $(obj).attr("name");
			var title = $(obj).attr("title").split("(")[0];
			//申请金额
			if(name=="appAmt"){
				var minAmt = "${mfSysKind.minAmt}";
				var maxAmt = "${mfSysKind.maxAmt}";
				maxAmt = new Number(maxAmt);
				minAmt = new Number(minAmt);
				var appAmt = $(obj).val();
				appAmt = appAmt.replace(/,/g, "");
				if(minAmt!=""&&maxAmt!=""){
					if(parseFloat(appAmt)<parseFloat(minAmt)||parseFloat(appAmt)>parseFloat(maxAmt)){
						$(obj).val("");
						alert(title+"必须在"+minAmt+"到"+maxAmt+"之间",0);
					}
				}
			}
			/*验证申请期限是否在产品设置的范围，需求不清楚，暂时保留，勿删！！！
			if(name=="term"){
				var minTerm = "${mfSysKind.minTerm}";
				var maxTerm = "${mfSysKind.maxTerm}";
				var termType = "${mfSysKind.termType}";
				var currTermType = $("select[name=y=termType]").val();
				var term = $(obj).val();
				if(minTerm!=""&&maxTerm!=""){
					if(parseFloat(term)<parseFloat(minTerm)||parseFloat(term)>parseFloat(maxTerm)){
						alert(title+"必须在${mfSysKind.minTerm}到${mfSysKind.maxTerm}之间",0);
					}
				}
			} */
		}
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">业务申请</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="insertForApplyForm" method="post" theme="simple" name="operform" action="${webPath}/mfBusApply/insertForApplyAjax">
						<dhcc:bootstarpTag property="formapply0007" mode="query"/>
					</form>
					</div>
				</div>
	   		</div>
	   		
			<div class="formRowCenter">
		    				<dhcc:thirdButton value="保存" action="保存" onclick="insertForApply('#insertForApplyForm');"></dhcc:thirdButton>
<%-- 		    				<dhcc:thirdButton value="风险检测" action="保存" onclick="riskTest(this.form);"></dhcc:thirdButton>
 --%>		    				
 							<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancelApply();"></dhcc:thirdButton>
		    </div>
			
		</div>	
				<div style="display: none;" id="fincUse-div"></div>
				
				<div style="display: none;padding-top: 20px;padding-left: 20px;padding-right: 20px;" id="riskInfo-div">
					<div>此申请存在风险，是否继续提交申请？<a href="javaScript:void(0);" onclick="showRiskInfo();">查看风险>></a></div>
					<div class="formRowCenter">
						<input type="button" style="height: 30px;line-height: 30px;width: 70px;" value="提交" onclick="submitForm();" >
						<input type="button" style="height: 30px;line-height: 30px;width: 70px;" value="取消" onclick="hideDialog();">
					</div>
				</div>
	</body>
</html>