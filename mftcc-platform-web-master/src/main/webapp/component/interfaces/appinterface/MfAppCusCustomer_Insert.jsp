<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>客户登记</title>
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
	  	$(function(){
	  	  MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function(){
					//安卓对返回按钮的处理
		  		document.addEventListener('backbutton', function(e) {
	              // 在这里处理你的业务逻辑
				  document.location.href=webPath+"/mfAppBusApply/getListPage";
	              e.preventDefault(); //backbutton事件的默认行为是回退历史记录，如果你想阻止默认的回退行为，那么可以通过preventDefault()实现
	
	  			 });
				//ios处理
				dd.biz.navigation.setLeft({
				    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
				    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
				    showIcon: false,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
				    text: '',//控制显示文本，空字符串表示显示默认文本
				    onSuccess : function(result) {
				    	 document.location.href=webPath+"/mfAppBusApply/getListPage";
				    },
				    onFail : function(err) {}
				});
			});
	  		var formcus00002Json = ${formcus00002Json};//解析的登记客户表单数据的json
// 	  		var formcus00002Json = $.parseJSON(formcus00002Str); 
	  		if(formcus00002Json){
	  			var formItemList = formcus00002Json.formActiveArray;
	  			var cusTypeHtml = "";//客户类型选择域框放到最上层
	  			var formItemListHmtl = "";//所有表单元素
	  			for(var i=0;i<formItemList.length;i++){
	  				var formItem = formItemList[i];
	  				var itemHtml = "";
	  				if(formItem.mustInput == "1"){//必填项才展示
		  				if(formItem.fieldType == "2"){//选择域样式
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
								if(optionItem.optCode == formItem.initValue){//默认选中
									optionHtml = "<option selected=\"selected\" value=\""+optionItem.optCode +"\">"+optionItem.optName+"</option>"
								}else{
									optionHtml = "<option value=\""+optionItem.optCode +"\">"+optionItem.optName+"</option>"
								}
								itemHtml+=optionHtml;
							}
							//拼接最后闭合标签
							itemHtml +=
"										</select>"+
"									</div>"+
"								</div>";
							if(formItem.fieldName && formItem.fieldName == "cusType"){//如果是客户类型，替换清空
								cusTypeHtml = itemHtml;
								itemHtml = "";
							}
		  				}else{//文本样式
			  				itemHtml =
	"   		            <div class=\"weui_cell\">"+
	"							<div class=\"weui_cell_hd\">"+
	"								<label class=\"weui_label\">"+formItem.labelName+"</label>"+
	"							</div>"+
	"							<div class=\"weui_cell_bd weui_cell_primary\">"+
	"								<input class=\"weui_input\" type=\"text\"  placeholder=\"请输入\" mustinput='1' name=\""+formItem.fieldName+"\" value=\""+formItem.initValue+"\">"+
	"							</div>"+
	"						</div>";
		  				}
	  				}else{
	  					itemHtml ="               <div class=\"weui_cell\" style=\"display:none;\">"+
	"					<div class=\"weui_cell_hd\">"+
	"						<label class=\"weui_label\">"+formItem.labelName+"</label>"+
	"					</div>"+
	"					<div class=\"weui_cell_bd weui_cell_primary\">"+
	"						<input class=\"weui_input\" type=\"text\"  placeholder=\"请输入\" name=\""+formItem.fieldName+"\" value=\""+formItem.initValue+"\">"+
	"					</div>"+
	"				</div>";
	  				}
	  				formItemListHmtl += itemHtml;
	  			}
	  			formItemListHmtl = cusTypeHtml + formItemListHmtl;//客户类型放前面
				$("#insertForm").append(formItemListHmtl);
	  			//最后添加保存按钮
// 	  			$("#insertForm").append("<div class=\"ding_btn_div\"><a href=\"javascript:;\" class=\"weui_btn weui_btn_primary mf_btn \" onclick=\"beforeSaveAjax('#insertForm');\">保存</a></div>");
	  		}
	  		//根据客户类型动态切换表单
	  		$("select[name='cusType']").change(function(){
	  			var cusTypeVal = $("select[name='cusType']").val();
	  			var tmpUrl = webPath+"/mfAppCusCustomer/input?cusType="+cusTypeVal;
	  			document.location.href=tmpUrl;
	  		});
	  		//手机号码 联系人 联系号码展示 通讯地址
	  		$("input[name='cusTel']").parents(".weui_cell").show();
	  		$("input[name='cusTel']").attr("maxlength","11");
	  		$("input[name='cusTel']").attr("type","tel");
	  		$("input[name='contactsName']").parents(".weui_cell").show();
	  		$("input[name='contactsTel']").parents(".weui_cell").show();
	  		$("input[name='contactsTel']").attr("maxlength","11");
	  		$("input[name='contactsTel']").attr("type","tel");
	  		$("input[name='commAddress']").parents(".weui_cell").show();
	  		//营业开始日期固定死格式
	  		$("input[name='beginDate']").attr("type","date");
	  		//注册资本只能是数字
	  		$("input[name='registeredCapital']").attr("type","tel").attr("maxlength","14");
	  		$("input[name='registeredCapital']").attr("onkeyup","DingUtil.inputOnlyNumber(this)");
	  	});
	  	/**
	  		表单id
	  	*/
	  	function validateMustInput(obj){
	  		var flag = true;
	  		$(obj+" input").each(function(){
	  			var $this = $(this);
	  			var mustValue = $this.attr("mustinput");
	  			var thisVal = $this.val();
	  			if(mustValue == "1" && (!thisVal ||thisVal == "")){//必填为空时提示
	  				var labelVal = $this.parents(".weui_cell").find("label").text();
	  				$.alert(labelVal + "不能为空！");
	  				flag =  false;
	  				return false;
	  			}
	  		});
	  		return flag;
	  	}
	  	function beforeSaveAjax(obj){
	  		//校验必填项
	  		if(validateMustInput(obj)){
	  			//校验身份证合法性
	  			if(validatIs()){
	  			//校验证件
			  		var relationId = "";
			  		var checkFlag = "",columnTitle = "";
					var unValCheckResult = null;
					var cusType = $("[name=cusType]").val();
	  				//手机号码唯一性验证
					var $tel =  $("input[name=cusTel]")
					if(cusType.indexOf("2") != 0){  //企业客户
						$tel = $("input[name=contactsTel]");
					}
					unVal = $tel.val();
					if(unVal && unVal !=null && unVal!=""){
						columnTitle= "手机号码";
						unValCheckResult = DingUtil.checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","20","insert","");
						checkFlag = unValCheckResult.split("&")[0];//result.split("&")[0];
						if(checkFlag == "1"){
							/*window.top.alert(telResultMsg+" 是否继续保存?",2,function(){
								saveForBus(obj,saveType);
							});*/
							$.alert(unValCheckResult.split("&")[1]);
							return false;
						}
					}
			  		
					//社会信用代码 证件号码唯一性验证
					var unVal = $("[name=idNum]").val();
					if(cusType.indexOf("2") != 0){  //企业客户
						columnTitle = "社会信用代码";
					} else {
						columnTitle = $("select[name=idType]").find("option:selected").text();
					}
					unValCheckResult = DingUtil.checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","01","insert","");
					checkFlag = unValCheckResult.split("&")[0];//idNumResult.split("&")[0];
					if(checkFlag == "1"){
						$.alert(unValCheckResult.split("&")[1]);
						return false;
					}
					
					saveFormAjax(obj);
	  			}
	  		}
	  	}
	  	function saveFormAjax(obj){
// 	  		var saveFlag=MfAppBusApply.saveValidat();
// 	  		if(!saveFlag){
// 	  			return false;
// 	  		}
	  		var url = $(obj).attr("action");
	  		var dataParam = JSON.stringify($(obj).serializeArray()); 
	  		$.showLoading("正在保存...");
	  		$.ajax({
				url:url,
				data:{ajaxData:dataParam, cusType : $("select[name=cusType]").find("option:selected").val()},
				type:'post',
				dataType:'json',
				success:function(data){
					$.hideLoading();
					if(data.flag == "success"){
						$.toast("保存成功");
						//进入下个页面
						document.location.href=webPath+'/mfAppCusCustomer/getById?cusNo='+data.cusNo;
					}else{
						$.toast(data.msg,"cancel");
					}
				},error:function(){
					$.hideLoading();
					$.toast("找不到服务器", "cancel");
				}
			}); 
	  	}
	  	/**
	  		校验身份证合法性
	  	*/
  		function validatIs(){
  			var $idNum = $("input[name='idNum']");
  			var idType = $("select[name='idType']").val();
  			if(idType=='0'){
			   var msg = CheckIdValue($idNum[0]);
			   if(msg!=""){
			   		$.alert(msg);
			   /* 	dd.device.notification.alert({
		    	    message: msg,
		    	    buttonName: "确定",
		    	    onSuccess : function() {
		    	        //onSuccess将在点击button之后回调
		    	    },
		    	    onFail : function(err) {}
		    	}); */
		    	    return false;
			   }
			}
			return true;
  		}
	  	</script>
	</head>
	<body>
		<form method="post" action="${webPath}/mfAppCusCustomer/insertForBusAjax" class="weui_cells weui_cells_form" id="insertForm">
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
