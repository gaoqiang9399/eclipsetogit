<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String recType = (String)request.getParameter("recType").substring(1, 2);
	String noteCondition = (String)request.getParameter("recType").substring(0, 1);
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
		<script type="text/javascript" src="${webPath}/component/rec/js/recallConfig.js" ></script>	
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
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
			
			var recType = '<%=recType%>';
			if(recType =='1'){
				$($("input[name=recName]").parents("tr").get(0)).hide();
			}else if(recType == '2'){
				$($("input[name=roleName]").parents("tr").get(0)).hide();
			}else{
				$($("input[name=recName]").parents("tr").get(0)).hide();
				$($("input[name=roleName]").parents("tr").get(0)).hide();
			}
			
			var noteCondition = '<%=noteCondition%>';
			if(noteCondition == "1"){
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).show();
				$($("input[name=returnAfterDays]").parents("tr").get(0)).hide();				
				$("input[name=returnAfterDays]").val("");
				//$($("input[name=returnAfterInteval]").parents("tr").get(0)).hide();
				//$("input[name=returnAfterInteval]").val("");
				$($("input[name=noteDay]").parents("tr").get(0)).hide();
				$("input[name=noteDay]").val("");
				
			}else if(noteCondition == "2"){
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).hide();
				$("input[name=returnBeforeDays]").val("");	
				$($("input[name=returnAfterDays]").parents("tr").get(0)).show();				
				//$($("input[name=returnAfterInteval]").parents("tr").get(0)).show();
				$($("input[name=noteDay]").parents("tr").get(0)).hide();
				$("input[name=noteDay]").val("");
			}else if(noteCondition == "3"){
				$($("input[name=returnBeforeDays]").parents("tr").get(0)).hide();
				$("input[name=returnBeforeDays]").val("");	
				$($("input[name=returnAfterDays]").parents("tr").get(0)).hide();				
				$("input[name=returnAfterDays]").val("");
				//$($("input[name=returnAfterInteval]").parents("tr").get(0)).hide();
				//$("input[name=returnAfterInteval]").val("");
				$($("input[name=noteDay]").parents("tr").get(0)).show();
			}
		});

		function insertRecallConfigLetter(obj) {
			
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
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
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
							//					  alert("操作成功！",1);
							/**top.addFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
							}**/
							top.addFlag = true;
						  	top.recItem = data.recItem;
							myclose_click();
							//window.close();
							//myclose_showDialogClick();
							if (callback && typeof (callback) == "function") {
								callback.call(this, data);
							}
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert("操作失败！", 0);
					}
				});
			}
		}
		
		function selectUserDialog1() {
			selectUserForRecDialog(getSysUserDialog,$("input[name=recNo]").val());
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
		
		function urgeInfoModelCallBack(data){
			$("textarea[name=recallDesc]").val(data);
		}
		
		function addUrgeInfoModel1(){
			//top.window.openBigForm(webPath+'/mfUrgeInfoModel/input','催收信息模版配置',function(){},90,60);
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
		
		function ajaxDeleteThis(){
			var id = $("input[name=letterNo]").val();	
			var url =webPath+"/recallConfigLetter/deleteAjax?letterNo="+id;
			alert(top.getMessage("CONFIRM_DELETE"),2,function(){
				$.ajax({
					url:url,
					data:{},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
							top.deleteFlag=true;
							myclose_click();
						}else{
							window.top.alert(top.getMessage("ERROR_INSERT"),1);
						}
					},error:function(){
						alert(top.getMessage("ERROR_INSERT"),0);
					}
				}); 
			});
			
		};
		
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="recallConfigLetterInsert" theme="simple" name="operform" action="${webPath}/recallConfigLetter/updateAjax">
							<dhcc:bootstarpTag property="formdlrecallconfigletter0003" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="修改" action="修改" onclick="insertRecallConfigLetter('#recallConfigLetterInsert');"></dhcc:thirdButton>
	   			<!--<dhcc:thirdButton value="删除" action="删除" onclick="ajaxDeleteThis('#recallConfigLetterInsert');"></dhcc:thirdButton>-->
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
