<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业务申请</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<style>
	  		.weui_cell_select{
	  			padding:0px 15px;
	  		}
	  		.weui_select{
	  			padding-left: 0px;
	  		}
	  		.mf_btn{
	  			width:90%;
	  			margin-top:20px;
	  		}
	  	</style>
	  	<script>
	  	var cusNo = '${cusNo}';
		var appId = '${appId}';//传递参数是为了在新增业务页面取消时，返回到原来的页面
		var fromPage = '${ajaxData}';
		var firstKindNo = '${kindNo}';
		var maxAmt = null;
		var minAmt = null;
		var minTerm = null;
		var maxTerm = null;
		var creditAmt = null;
		var kindCreditAmt = null;	
	  	$(function(){
  			getForm(firstKindNo);
	  	});
	  	function getForm(val){
		  	$.ajax({
				url:webPath+"/mfAppBusApply/chooseFormAjax?kindNo="+val+"&cusNo="+cusNo,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.flag=="success"){
						var formcus00002Str = data.formapply0007_queryJson;
			  			var htmlStr = "";
						if(formcus00002Str){
							formcus00002Json = JSON.parse(formcus00002Str);
				  			var formItemList = formcus00002Json.formActiveArray;
				  			for(var i=0;i<formItemList.length;i++){
				  				var formItem = formItemList[i];
				  				var itemHtml = "";
				  				if(formItem.mustInput == "1" && formItem.fieldType != "99" ){//必填项并且不是隐藏域才展示
					  				if(formItem.fieldType == "2" || formItem.fieldType == "81"){//2选择域样式 81自定义选择
					  					itemHtml = 
			"   							<div class=\"weui_cell weui_cell_select\">"+
			"									<div class=\"weui_cell_hd\">"+
			"										<label for=\"\" class=\"weui_label\">"+formItem.labelName+"</label>"+
			"									</div>"+
			"									<div class=\"weui_cell_bd weui_cell_primary\">"+
			"										<select class=\"weui_select\" name=\""+formItem.fieldName+"\">";
										//组装option数据
										for(var j=0;j<formItem.optionArray.length;j++){
											var optionItem = formItem.optionArray[j];
											var optionHtml = "";
											if(optionItem.optCode){//存的字典项
												if(optionItem.optCode == formItem.initValue){//默认选中
													optionHtml = "<option selected=\"selected\" value=\""+optionItem.optCode +"\">"+optionItem.optName+"</option>"
												}else{
													optionHtml = "<option value=\""+optionItem.optCode +"\">"+optionItem.optName+"</option>"
												}
											}else if(optionItem.optionValue){//存的optionArray
												if(optionItem.optionValue == formItem.initValue){//默认选中
													optionHtml = "<option selected=\"selected\" value=\""+optionItem.optionValue +"\">"+optionItem.optionlabel+"</option>"
												}else{
													optionHtml = "<option value=\""+optionItem.optionValue +"\">"+optionItem.optionlabel+"</option>"
												}
											}else if(optionItem.kindNo){//产品列表的选项
												if(optionItem.kindNo == formItem.initValue){
													optionHtml = "<option selected=\"selected\" value=\""+optionItem.kindNo +"\">"+optionItem.kindName+"</option>"
												}else{
													optionHtml = "<option value=\""+optionItem.kindNo +"\">"+optionItem.kindName+"</option>"
												
												}
											}else if(optionItem.id){//贷款投向jsonArray
												if(optionItem.id == formItem.initValue){
													optionHtml = "<option selected=\"selected\" value=\""+optionItem.id +"\">"+optionItem.name+"</option>"
												}else{
													optionHtml = "<option value=\""+optionItem.id +"\">"+optionItem.name+"</option>"
												
												}
											}
											itemHtml+=optionHtml;
										}
			// "							<option selected=\"\" value=\"0\">选择</option>"+
			// "							<option value=\"1\">微信号</option>"+
			// "							<option value=\"2\">QQ号</option>"+
			// "							<option value=\"3\">Email</option>"+
										//拼接最后闭合标签
										itemHtml +=
			"										</select>"+
			"									</div>"+
			"								</div>";
					  				
					  				}else{//文本样式
					  					if(formItem.fieldName == "cusMngName" || formItem.fieldName == "manageOpName1"){//隐藏客户经理和办理人员
					  					    itemHtml =  "<div class=\"weui_cell\" style=\"display:none;\">"
					  					}else{
							  				itemHtml ="<div class=\"weui_cell\">";
					  					}
					  					//提示信息
					  					var placeholderStr = "请输入";
					  					if(formItem.fieldName == "appAmt" || formItem.fieldName == "term"){//申请金额和申请期限有提示
					  						placeholderStr = formItem.alt;
					  					}
					  					//利率单位
					  					var unitStr = "";
					  					if("fincRate" == formItem.fieldName || formItem.fieldName == "overFloat" || formItem.fieldName == "cmpdFloat"){
					  						unitStr = "("+formItem.unit+")"
					  					}
						  				itemHtml +=
				"							<div class=\"weui_cell_hd\">"+
				"								<label class=\"weui_label\">"+formItem.labelName+unitStr+"</label>"+
				"							</div>"+
				"							<div class=\"weui_cell_bd weui_cell_primary\">"+
				"								<input class=\"weui_input\" type=\"text\" mustinput='1' placeholder=\""+placeholderStr+"\" name=\""+formItem.fieldName+"\" value=\""+formItem.initValue+"\">";
										//期限添加单位
										if(formItem.fieldName == "term"){
											itemHtml += "<span style=\"position: absolute;right: 15px;top: 16px;font-size: 13px;\">"+data.termUnit+"</span>";
										}
										itemHtml +=
				"							</div>"+
				"						</div>";
					  				}
				  				}else {
				  					if(formItem.fieldType != "90" ){//备用字段不展示
					  					itemHtml ="               <div class=\"weui_cell\" style=\"display:none;\">"+
				"					<div class=\"weui_cell_hd\">"+
				"						<label class=\"weui_label\">"+formItem.labelName+"</label>"+
				"					</div>"+
				"					<div class=\"weui_cell_bd weui_cell_primary\">"+
				"						<input class=\"weui_input\"  type=\"text\" placeholder=\"请输入\" name=\""+formItem.fieldName+"\" value=\""+formItem.initValue+"\">"+
				"					</div>"+
				"				</div>";
				  					}
				  				}
									htmlStr += itemHtml;
				  			}
				  			//添加formId
				  			htmlStr += "<input class=\"weui_input\" type=\"hidden\" placeholder=\"请输入\" name=\"formId\" value=\""+formcus00002Json.formId+"\">"
				  			//最后添加保存按钮
// 				  			htmlStr += "<div class=\"ding_btn_div\"><a href=\"javascript:;\" class=\"weui_btn weui_btn_primary mf_btn \" onclick=\"saveFormAjax('#insertForm');\">保存</a></div>";
				  		}
				  		$("#insertForm").empty().html(htmlStr);
				  			//根据产品类型动态切换表单
				  		$("select[name='kindNo']").change(function(){
				  			var kindNoVal = $("select[name='kindNo']").val();
				  			getForm(kindNoVal);
				  		});
				  		// 是否隐藏 复利利率上浮字段
						if(data.cmpdRateType=="0"){//隐藏						
							$("input[name='cmpdFloat']").parents('.weui_cell').hide();
						}
						validatIs(data);
						//控制表单数字格式
						$("input[name='term']").attr("type","tel");
						$("input[name='term']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
						$("input[name='fincRate']").attr("type","tel");
						$("input[name='fincRate']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
						$("input[name='appAmt']").attr("type","tel");
						$("input[name='appAmt']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
						$("input[name='overFloat']").parents('.weui_cell').hide();
						$("input[name='overFloat']").val("0.0");
						$("input[name='cmpdFloat']").attr("type","tel");
						$("input[name='cmpdFloat']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
						$("input[name='cmpdFloat']").val("0.00");
					}
				}
			});
	  	};
	  	function saveFormAjax(obj){
	  		var saveFlag=MfAppBusApply.saveValidat();
	  		if(!saveFlag){
	  			return false;
	  		}
	  		var minTerm = $("#minTerm").val();
 			var maxTerm = $("#maxTerm").val();
 			var minAmt = $("#minAmt").val();
 			var maxAmt = $("#maxAmt").val();
 			var term = $("input[name='term']").val();
 			var amt = $("input[name='appAmt']").val();
 			if(Number(term)<Number(minTerm)){
				dingAlert("申请期限小于最小期限");
				return false;
			}else if(Number(term)>Number(maxTerm)){
				dingAlert("申请期限大于最大期限");
				return false;
			}
			if(Number(amt)<Number(minAmt)){
				dingAlert(amt+"小于最小金额"+Number(minAmt)+"元");
				return false;
			}else if(Number(amt)>Number(maxAmt)){
				dingAlert(amt+"大于最大金额"+Number(maxAmt)+"元");
				return false;
			} 
	  		var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			//验证非空
			$.showLoading("保存中,请稍后...");
			$.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:'post',
				dataType:'json',
				success:function(data){
					$.hideLoading();
					if(data.flag=="success"){
						$.toast("保存成功");
						location.href=webPath+"/mfAppCusCustomer/getById?cusNo="+cusNo+"&appId="+data.appId;
					}else{
						$.toast("保存失败", "cancel");
					}
				},error:function(){
					$.hideLoading();
					$.toast("保存失败", "cancel");
				}
			});
	  	}
  		
  		function validatIs(data){
  			var $term = $("input[name='term']")
  			$term.change(function(){
				var minTerm =data.minTerm;
				var maxTerm =data.maxTerm;	
				$("#minTerm").val(minTerm);
				$("#maxTerm").val(maxTerm);
				if(Number(this.value)<Number(minTerm)){
					dingAlert("申请期限小于最小期限");
				}else if(Number(this.value)>Number(maxTerm)){
					dingAlert("申请期限大于最大期限");
				}
			});
			var $Amt = $("input[name='appAmt']")
  			$Amt.change(function(){
				var minAmt =data.minAmt;
				var maxAmt =data.maxAmt;
				$("#minAmt").val(minAmt);
				$("#maxAmt").val(maxAmt);
				if(Number(this.value)<Number(minAmt)){
					dingAlert(this.value+"小于最小金额"+Number(minAmt)+"元");
				}else if(Number(this.value)>Number(maxAmt)){
					dingAlert(this.value+"大于最大金额"+Number(maxAmt)+"元");
				}
			});
			
  		}
 		function dingAlert(msg){
 			dd.device.notification.alert({
		    	    message: msg,
		    	    buttonName: "确定",
		    	    onSuccess : function() {
		    	        //onSuccess将在点击button之后回调
		    	    },
		    	    onFail : function(err) {}
		    	});
 		}
	  	</script>
	</head>
	<body>
		<input type='hidden' id="minTerm"/>
		<input type='hidden' id="maxTerm"/>
		<input type='hidden' id="minAmt"/>
		<input type='hidden' id="maxAmt"/>
		<form method="post" action="MfAppBusApplyActionAjax_insertForApplyAjax_query.action" class="weui_cells weui_cells_form" id="insertForm">
			<!-- <div class="weui_cells_title">文本域</div>
			<div class="weui_cells weui_cells_form">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<textarea class="weui_textarea" placeholder="请输入评论" rows="3"></textarea>
						<div class="weui_textarea_counter">
							<span>0</span>/200
						</div>
					</div>
				</div>
			</div> -->
		</form>
		<div class="ding_btn_div"><a href="javascript:;" class="weui_btn weui_btn_primary mf_btn " onclick="saveFormAjax('#insertForm');">保存</a></div>
	</body>
</html>
