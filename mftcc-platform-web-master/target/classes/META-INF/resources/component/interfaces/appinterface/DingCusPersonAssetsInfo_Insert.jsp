<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>资产信息</title>
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
	  	var formData = '${formcusjob00002Json}';
	  	var appId="${appId}";
	  	$(function(){
  			getForm(formData);
	  	});
	  	function getForm(data){
			if(data && data != ""){
				var formcus00002Str = data;
	  			var htmlStr = "";
				if(formcus00002Str){
					formcus00002Json = JSON.parse(formcus00002Str);
		  			var formItemList = formcus00002Json.formActiveArray;
		  			for(var i=0;i<formItemList.length;i++){
		  				var formItem = formItemList[i];
		  				var itemHtml = "";
		  				if(formItem.fieldType != "99" && formItem.fieldType != "90" ){//不是隐藏域才展示并且不是备用字段
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
			  					var ismustStr = "";
			  					if(formItem.mustInput == "1"){
			  						ismustStr = "mustinput='1'";
			  					}
				  				itemHtml +=
		"							<div class=\"weui_cell_hd\">"+
		"								<label class=\"weui_label\">"+formItem.labelName+unitStr+"</label>"+
		"							</div>"+
		"							<div class=\"weui_cell_bd weui_cell_primary\">"+
		"								<input class=\"weui_input\" type=\"text\" "+ismustStr+" placeholder=\""+placeholderStr+"\" name=\""+formItem.fieldName+"\" value=\""+formItem.initValue+"\">";
								//排量 市值 购买价值 按揭贷款余额 按揭贷款期限添加单位
								if(formItem.fieldName == "assetsArea"
									||formItem.fieldName == "assetsValue"
									||formItem.fieldName == "buyValue"
									||formItem.fieldName == "loanBalance"
									||formItem.fieldName == "loanPeriod"){
									itemHtml += "<span class=\"input-group-addon\" style=\"position: absolute;right: 15px;top: 16px;font-size: 13px;\">"+formItem.unit+"</span>";
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
		  			htmlStr += "<input class=\"weui_input\" type=\"hidden\" placeholder=\"请输入\" name=\"formId\" value=\""+formcus00002Json.formId+"\">";
		  			//最后添加保存按钮
// 		  			htmlStr += "<div class=\"ding_btn_div\"><a href=\"javascript:;\" class=\"weui_btn weui_btn_primary mf_btn \" onclick=\"beforeSaveAjax('#insertForm');\">保存</a></div>";
		  		}
		  		$("#insertForm").empty().html(htmlStr);
			}
			//添加表单限制
			$("input[name='cusName']").attr("readonly","readonly");
			$("input[name='buyDate']").attr("type","date");
			$("input[name='assetsArea']").attr("type","tel");
			$("input[name='assetsValue']").attr("type","tel");
			$("input[name='assetsValue']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
			$("input[name='buyValue']").attr("type","tel");
			$("input[name='buyValue']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
			$("input[name='loanBalance']").attr("type","tel");
			$("input[name='loanBalance']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
			$("input[name='loanPeriod']").attr("type","tel");
			$("input[name='loanPeriod']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
			//是否按揭
			$("select[name='isMortgage']").change(function(){
				changeMortgage(this);
			});
			//资产类型
			$("select[name='assetsType']").change(function(){
				changeAssets(this);
			});
			$("select[name='assetsType']").change();
	  	};
	  	function changeMortgage(obj){
			var isMortgage = $(obj).val();
			if("N"==isMortgage){//没有按揭
				$("input[name='loanBalance']").parents(".weui_cell").hide(); 
				$("input[name='loanPeriod']").parents(".weui_cell").hide();
				$("input[name='loanBalance']").val("");
				$("input[name='loanPeriod']").val("");
			}else{//有按揭
				$("input[name='loanBalance']").parents(".weui_cell").show(); 
				$("input[name='loanPeriod']").parents(".weui_cell").show();
			}
		};
		function changeAssets(obj){
			var assetsType = $(obj).val();	
			var html = "<font color='#FF0000'>*</font>";
			if("2"==assetsType){//资产类型为房屋
				$("input[name='assetsArea']").parents(".weui_cell").find("label").text("面积"); 
				$("input[name='assetsArea']").next(".input-group-addon").text("m²");		
				$("input[name='assetsNo']").parents(".weui_cell").find("label").text("房产证号");	
// 				$("input[name='assetsNo']").parents("tr").find("label").prepend(html);
				$("input[name='vehicleBrand']").attr("mustinput","");
				$("input[name='vehicleBrand']").parents(".weui_cell").hide();
				$("select[name='houseType']").parents(".weui_cell").show();
				$("select[name='houseType']").attr("mustinput","1");	
				//新增页面
				$("input[name='assetsArea']").val("");	
				$("input[name='assetsNo']").val("");
				
			}else{//资产类型为汽车
				$("input[name='assetsArea']").parents(".weui_cell").find("label").text("排量");
				$("input[name='assetsArea']").next(".input-group-addon").text("L");
				$("input[name='assetsNo']").parents(".weui_cell").find("label").text("车牌号");
// 				$("input[name='assetsNo']").parents("tr").find("label").prepend(html);
				$("select[name='houseType']").attr("mustinput","");
				$("select[name='houseType']").parents(".weui_cell").hide();
				$("select[name='houseType']").val("");
				$("input[name='vehicleBrand']").parents(".weui_cell").show();
				$("input[name='vehicleBrand']").attr("mustinput","1");
				$("input[name='assetsArea']").val("");	
				$("input[name='assetsNo']").val("");
			}
		};
		
		function beforeSaveAjax(obj){
			var saveFlag=MfAppBusApply.saveValidat();
	  		if(!saveFlag){
	  			return false;
	  		}
			var checkFlag = "";
			//证件号码唯一性验证
			var idNum = $("input[name='idNum']").val();
			var idNumTitle = $("input[name='idNum']").parents(".weui_cell").find(".weui_label").text();
			var idNumType = $("input[name='idType']").val();
			var relationId = $("input[name='cusNo']").val();
			var idNumResult = DingUtil.checkUniqueVal(idNum,idNumTitle,relationId,"MfCusPersonAssetsInfo","01","insert","");
			checkFlag = idNumResult.split("&")[0];
			idNumResult = idNumResult.split("&")[1];
			if(checkFlag == "1"){
				$.confirm(idNumResult+",继续保存？", function() {
			 		//点击确认后的回调函数
					saveFormAjax(obj);
			  		}, function() {
			  		//点击取消后的回调函数
			  	});
			}else{
				saveFormAjax(obj);
			}
		}
	  	function saveFormAjax(obj){
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
						location.href=webPath+"/mfAppCusCustomer/getById?cusNo="+cusNo+"&appId="+appId;
					}else{
						$.toast(data.msg, "cancel");
					}
				},error:function(){
					$.hideLoading();
					$.toast("保存失败", "cancel");
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
		<form method="post" action="${webPath}/dingCusPerson/insertAssetsAjax" class="weui_cells weui_cells_form" id="insertForm">
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
		<div class="ding_btn_div"><a href="javascript:;" class="weui_btn weui_btn_primary mf_btn " onclick="beforeSaveAjax('#insertForm');">保存</a></div>
	</body>
</html>
