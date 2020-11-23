<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link href="${webPath}/component/rec/css/time/jquery-ui.min.css" rel="stylesheet" />
		<link href="${webPath}/component/rec/css/time/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
		<%-- <script type="text/javascript" src="${webPath}/component/rec/js/time/jquery-3.1.1.min.js"></script> --%>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
		<script type="text/javascript" src="${webPath}/component/rec/js/recallConfig.js" ></script>
		<script type="text/javascript" src="${webPath}/component/rec/js/time/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/rec/js/time/jquery-ui-timepicker-addon.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/rec/js/time/jquery-ui-timepicker-zh-CN.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.autocompleter.js"></script>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		
	</head>
	<script type="text/javascript">
	
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					theme : "minimal-dark",
// 					updateOnContentResize : true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			
		});

		function insertRecallConfig(obj) {
			var noteCondition = $("select[name=noteCondition]").val();
			if(noteCondition == "3"){
				var noteDay = $("input[name=noteDay]").val();
				if(isNaN(Number(noteDay))){
					alert("日期必须为1-31之间的整数", 0);
					return ;
				}else if(parseInt(noteDay)<1 || parseInt(noteDay)>31){
					alert("日期必须为1-31之间的整数", 0);
					return ;
				}
			}
			
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				//var url = $(obj).attr("action");
				var url = "";
				var recallWay = $("select[name=recallWay]").val();
				switch(recallWay){
					case '1':{
						$(obj).find("select[name=returnAfterInteval]").remove();
						url = webPath+"/recallConfigNote/insertAjax";
						break;
					}
					case '2':{
						$(obj).find("input[name=returnAfterInteval]").remove();
						url = webPath+"/recallConfigPhone/insertAjax";
						break;
					}
					case '3':{
						$(obj).find("input[name=returnAfterInteval]").remove();
						url = webPath+"/recallConfigLetter/insertAjax";
						break;
					}
					case '4':{
						$(obj).find("input[name=returnAfterInteval]").remove();
						url = webPath+"/recallConfigVisit/insertAjax";	
						break;
					}
					case '5':{
						$(obj).find("input[name=returnAfterInteval]").remove();
						url = webPath+"/recallConfigOut/insertAjax";
						break;
					}
					case '6':{
						$(obj).find("input[name=returnAfterInteval]").remove();
						url = webPath+"/recallConfigLaw/insertAjax";
						break;
					}
				}
				
				var dataParam = JSON.stringify($(obj).serializeArray());
				//var recKindNo1 = '${recKindNo}';
				//alert("recKindNo1:"+recKindNo1);
				
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg, 1);
							//var url=webPath+"/recallConfigNote/getListPage";
							//$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							top.addFlag = true;
						  	top.recItem = data.recItem;
							myclose_click();
							if (callback && typeof (callback) == "function") {
								callback.call(this, data);
							}
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
				
			}
		}
		
		function fPopUpCalendarDlgHMS() {
			$("input[name=sendTime]").timepicker({
				timeText : '时间',
				hourText : '小时',
				minuteText : '分钟',
				secondText : '秒',
				currentText : '现在',
				closeText : '完成',
				showSecond : true, //显示秒  
				timeFormat : 'HH:mm:ss' //格式化时间  
			});
			$("input[name=sendTime]").focus();
		}
		
		function urgeInfoModelCallBack(data){
			$("input[name=modelId]").val(data.urgeModelId);
			$("textarea[name=recallDesc]").val(data.modelContent);
		}
		
		function noteConditionOnChange(obj){
			var noteCondition = $(obj).val();
			if(noteCondition == "1"){
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).show();
				$("input[name=returnBeforeDays]").val("");
				$("input[name=returnBeforeDays]").attr("mustinput","1");	
				$($("input[name=returnAfterDays]").parents("tr").get(0)).hide();				
				$("input[name=returnAfterDays]").val("");
				$("input[name=returnAfterDays]").attr("mustinput","");
				/**
				$($("input[name=returnAfterInteval]").parents("tr").get(0)).hide();
				$("input[name=returnAfterInteval]").val("");
				$("input[name=returnAfterInteval]").attr("mustinput","");
				**/
				$($("input[name=noteDay]").parents("tr").get(0)).hide();
				$("input[name=noteDay]").val("");
				$("input[name=noteDay]").attr("mustinput","");
			}else if(noteCondition == "2"){
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).hide();
				$("input[name=returnBeforeDays]").val("");
				$("input[name=returnBeforeDays]").attr("mustinput","");	
				$($("input[name=returnAfterDays]").parents("tr").get(0)).show();				
				$("input[name=returnAfterDays]").val("");
				$("input[name=returnAfterDays]").attr("mustinput","1");
				/**
				$($("input[name=returnAfterInteval]").parents("tr").get(0)).show();
				$("input[name=returnAfterInteval]").val("");
				$("input[name=returnAfterInteval]").attr("mustinput","1");
				**/
				$($("input[name=noteDay]").parents("tr").get(0)).hide();
				$("input[name=noteDay]").val("");
				$("input[name=noteDay]").attr("mustinput","");
				
			}else if(noteCondition == "3"){				
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).hide();
				$("input[name=returnBeforeDays]").val("");
				$("input[name=returnBeforeDays]").attr("mustinput","");	
				$($("input[name=returnAfterDays]").parents("tr").get(0)).hide();				
				$("input[name=returnAfterDays]").val("");
				$("input[name=returnAfterDays]").attr("mustinput","");
				/**
				$($("input[name=returnAfterInteval]").parents("tr").get(0)).hide();
				$("input[name=returnAfterInteval]").val("");
				$("input[name=returnAfterInteval]").attr("mustinput","");
				**/
				$($("input[name=noteDay]").parents("tr").get(0)).show();
				$("input[name=noteDay]").val("");
				$("input[name=noteDay]").attr("mustinput","1");
				
			}else{	
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).show();
				$("input[name=returnBeforeDays]").val("");
				$("input[name=returnBeforeDays]").attr("mustinput","");	
				$($("input[name=returnAfterDays]").parents("tr").get(0)).show();				
				$("input[name=returnAfterDays]").val("");
				$("input[name=returnAfterDays]").attr("mustinput","");
				/**
				$($("input[name=returnAfterInteval]").parents("tr").get(0)).show();
				$("input[name=returnAfterInteval]").val("");
				$("input[name=returnAfterInteval]").attr("mustinput","");
				**/
				$($("input[name=noteDay]").parents("tr").get(0)).show();
				$("input[name=noteDay]").val("");
				$("input[name=noteDay]").attr("mustinput","");
			}
		}
		
		function urgeInfoModelCallBack(data){
			$("textarea[name=recallDesc]").val(data);
		}
		
		function addUrgeInfoModel1(){
			//top.window.openBigForm(webPath+''/mfUrgeInfoModel/input','催收信息模版配置',function(){},90,60);
			addUrgeInfoModel(urgeInfoModelCallBack);
		}
		
		function addUrgeInfoModel(callback){
			dialog({
				id:"MfUrgeInfoModelDialog",
				title:'催收信息模版配置',
				url: webPath+'/mfUrgeInfoModel/input',
				width:1200,
				height:300,
				backdropOpacity:0,
				onshow:function(){
					this.returnValue = null;
					},onclose:function(){
						if(this.returnValue){
							//返回对象的属性:opNo,opName;如果多个，使用@分隔
							if(typeof(callback)== "function"){
								callback(this.returnValue);
							}else{
							}
						}
					}
				}).showModal();
		}
		
		function selectUserDialog1() {
			//selectUserForRecDialog(getSysUserDialog,$("input[name=recNo]").val());
			selectUserForRecDialog(getSysUserDialog);
		};
		
		function getSysUserDialog(userInfo){
			$("input[name=recName]").val(userInfo.opName);
			$("input[name=recNo]").val(userInfo.opNo);
		};
	
		function selectRoleDialog1() {
			selectRoleDialog(getSysRoleDialog,$("input[name=roleNo]").val());
		};
		
		function getSysRoleDialog(sysRole){
				$("input[name=roleName]").val(sysRole.roleName);
				$("input[name=roleNo]").val(sysRole.roleNo);
		};
		
		function recallWayOnChange(obj){
			var recallWay=$(obj).val();
			if(recallWay != ''){
				switch(recallWay){
				case '1':{
					dealShortMessageOrLetterProperty('1');
					break;
				}
				case '2':{
					dealHideProperty();
					break;
				}
				case '3':{
					dealShortMessageOrLetterProperty('3');
					break;
				}
				case '4':{
					dealHideProperty();
					break;
				}
				case '5':{
					dealHideProperty();
					break;
				}
				case '6':{
					dealHideProperty();
					break;
				}
				}
			}else{
					$($("select[name=noteCondition]").parents("tr").get(0)).show();
					$("select[name=noteCondition]").val("");
					$("select[name=noteCondition]").attr("mustinput","");
					$($("input[name=returnBeforeDays]").parents("tr").get(0)).show();				
					$("input[name=returnBeforeDays]").val("");
					$("input[name=returnBeforeDays]").attr("mustinput","");
					$($("input[name=returnAfterDays]").parents("tr").get(0)).show();				
					$("input[name=returnAfterDays]").val("");
					$("input[name=returnAfterDays]").attr("mustinput","");
					/**
					$($("input[name=returnAfterInteval]").parents("tr").get(0)).show();
					$("input[name=returnAfterInteval]").val("");
					$("input[name=returnAfterInteval]").attr("mustinput","");
					**/
					$($("input[name=noteDay]").parents("tr").get(0)).show();
					$("input[name=noteDay]").val("");
					$("input[name=noteDay]").attr("mustinput","");
					$($("input[name=sendTime]").parents("tr").get(0)).show();
					$("input[name=sendTime]").val("");
					$("input[name=sendTime]").attr("mustinput","");
					
					$($("input[name=returnAfterStartDays]").parents("tr").get(0)).show();				
					$("input[name=returnAfterStartDays]").val("");
					$("input[name=returnAfterStartDays]").attr("mustinput","");
					$($("input[name=returnAfterEndDays]").parents("tr").get(0)).show();				
					$("input[name=returnAfterEndDays]").val("");
					$("input[name=returnAfterEndDays]").attr("mustinput","");
					$($("select[name=returnAfterInteval]").parents("tr").get(0)).show();
					$("select[name=returnAfterInteval]").val("");
					$("select[name=returnAfterInteval]").attr("mustinput","");
					$($("select[name=returnAfterIntevalUnit]").parents("tr").get(0)).show();
					$("select[name=returnAfterIntevalUnit]").val("");
					$("select[name=returnAfterIntevalUnit]").attr("mustinput","");
					$($("input[name=minRecallAmt]").parents("tr").get(0)).show();
					$("input[name=minRecallAmt]").val("");
					$("input[name=minRecallAmt]").attr("mustinput","");
					$($("select[name=recType]").parents("tr").get(0)).show();
					$("select[name=recType]").val("");
					$("select[name=recType]").attr("mustinput","");
					$($("input[name=roleName]").parents("tr").get(0)).show();
					$("input[name=roleName]").val("");
					$($("input[name=recName]").parents("tr").get(0)).show();
					$("input[name=recName]").val("");
					$("textarea[name=recallDesc]").val("");
			}
		}
		
		function dealShortMessageOrLetterProperty(recallWay){
			$($("select[name=noteCondition]").parents("tr").get(0)).show();
			$("select[name=noteCondition]").val("");
			$("select[name=noteCondition]").attr("mustinput","1");
			$($("input[name=returnBeforeDays]").parents("tr").get(0)).show();				
			$("input[name=returnBeforeDays]").val("");
			$("input[name=returnBeforeDays]").attr("mustinput","");
			$($("input[name=returnAfterDays]").parents("tr").get(0)).show();				
			$("input[name=returnAfterDays]").val("");
			$("input[name=returnAfterDays]").attr("mustinput","");
			/**
			$($("input[name=returnAfterInteval]").parents("tr").get(0)).show();
			$("input[name=returnAfterInteval]").val("");
			$("input[name=returnAfterInteval]").attr("mustinput","");
			**/
			$($("input[name=noteDay]").parents("tr").get(0)).show();
			$("input[name=noteDay]").val("");
			$("input[name=noteDay]").attr("mustinput","");
			$($("input[name=sendTime]").parents("tr").get(0)).show();
			$("input[name=sendTime]").val("");
			$("input[name=sendTime]").attr("mustinput","1");
			
			$($("input[name=returnAfterStartDays]").parents("tr").get(0)).hide();				
			$("input[name=returnAfterStartDays]").val("");
			$("input[name=returnAfterStartDays]").attr("mustinput","");
			$($("input[name=returnAfterEndDays]").parents("tr").get(0)).hide();				
			$("input[name=returnAfterEndDays]").val("");
			$("input[name=returnAfterEndDays]").attr("mustinput","");
			$($("select[name=returnAfterInteval]").parents("tr").get(0)).hide();
			$("select[name=returnAfterInteval]").val("");
			$("select[name=returnAfterInteval]").attr("mustinput","");
			$($("select[name=returnAfterIntevalUnit]").parents("tr").get(0)).hide();
			$("select[name=returnAfterIntevalUnit]").val("");
			$("select[name=returnAfterIntevalUnit]").attr("mustinput","");			
			if(recallWay == '1'){
				$($("input[name=minRecallAmt]").parents("tr").get(0)).hide();
				$("input[name=minRecallAmt]").val("");
				$("input[name=minRecallAmt]").attr("mustinput","");
				$($("select[name=recType]").parents("tr").get(0)).hide();
				$("select[name=recType]").val("");
				$("select[name=recType]").attr("mustinput","");
				$($("input[name=roleName]").parents("tr").get(0)).hide();
				$("input[name=roleName]").val("");
				$("input[name=roleName]").attr("mustinput","");
				$($("input[name=recName]").parents("tr").get(0)).hide();
				$("input[name=recName]").val("");
				$("input[name=recName]").attr("mustinput","");
			}else if(recallWay == '3'){
				$($("input[name=minRecallAmt]").parents("tr").get(0)).show();
				$("input[name=minRecallAmt]").val("");
				$("input[name=minRecallAmt]").attr("mustinput","1");
				$($("select[name=recType]").parents("tr").get(0)).show();
				$("select[name=recType]").val("");
				$("select[name=recType]").attr("mustinput","1");
				$($("input[name=roleName]").parents("tr").get(0)).show();
				$("input[name=roleName]").val("");
				$("input[name=roleName]").attr("mustinput","");
				$($("input[name=recName]").parents("tr").get(0)).show();
				$("input[name=recName]").val("");
				$("input[name=recName]").attr("mustinput","");
			}
			$("textarea[name=recallDesc]").val("");
		}
		
		function dealHideProperty(){			
			$($("select[name=noteCondition]").parents("tr").get(0)).hide();
			$("select[name=noteCondition]").val("");
			$("select[name=noteCondition]").attr("mustinput","");
			$($("input[name=returnBeforeDays]").parents("tr").get(0)).hide();				
			$("input[name=returnBeforeDays]").val("");
			$("input[name=returnBeforeDays]").attr("mustinput","");
			$($("input[name=returnAfterDays]").parents("tr").get(0)).hide();				
			$("input[name=returnAfterDays]").val("");
			$("input[name=returnAfterDays]").attr("mustinput","");
			/**
			$($("input[name=returnAfterInteval]").parents("tr").get(0)).hide();
			$("input[name=returnAfterInteval]").val("");
			$("input[name=returnAfterInteval]").attr("mustinput","");
			**/
			$($("input[name=noteDay]").parents("tr").get(0)).hide();
			$("input[name=noteDay]").val("");
			$("input[name=noteDay]").attr("mustinput","");
			$($("input[name=sendTime]").parents("tr").get(0)).hide();
			$("input[name=sendTime]").val("");
			$("input[name=sendTime]").attr("mustinput","");
			
			$($("input[name=returnAfterStartDays]").parents("tr").get(0)).show();				
			$("input[name=returnAfterStartDays]").val("");
			$("input[name=returnAfterStartDays]").attr("mustinput","1");
			$($("input[name=returnAfterEndDays]").parents("tr").get(0)).show();				
			$("input[name=returnAfterEndDays]").val("");
			$("input[name=returnAfterEndDays]").attr("mustinput","1");
			$($("select[name=returnAfterInteval]").parents("tr").get(0)).show();
			$("select[name=returnAfterInteval]").val("");
			$("select[name=returnAfterInteval]").attr("mustinput","1");
			$($("select[name=returnAfterIntevalUnit]").parents("tr").get(0)).show();
			$("select[name=returnAfterIntevalUnit]").val("");
			$("select[name=returnAfterIntevalUnit]").attr("mustinput","1");
			$($("input[name=minRecallAmt]").parents("tr").get(0)).show();
			$("input[name=minRecallAmt]").val("");
			$("input[name=minRecallAmt]").attr("mustinput","1");
			$($("select[name=recType]").parents("tr").get(0)).show();
			$("select[name=recType]").val("");
			$("select[name=recType]").attr("mustinput","1");
			$($("input[name=roleName]").parents("tr").get(0)).show();
			$("input[name=roleName]").val("");
			$($("input[name=recName]").parents("tr").get(0)).show();
			$("input[name=recName]").val("");
			$("textarea[name=recallDesc]").val("");
		}
		
		function selectUserForRecDialog(callback){
			dialog({
				id:"SysUserForRecDialog",
				title:'选择接收人',
				url: webPath+'/recallBase/getSysOrgListPage',
				width:700,
				height:500,
				backdropOpacity:0,
				onshow:function(){
					this.returnValue = null;
					},onclose:function(){
						if(this.returnValue){
							//返回对象的属性:opNo,opName;如果多个，使用@分隔
							if(typeof(callback)== "function"){
								callback(this.returnValue);
							}else{
							}
						}
					}
				}).showModal();
		}

	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="recallConfigInsert" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formdlrecallconfig0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertRecallConfig('#recallConfigInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>

