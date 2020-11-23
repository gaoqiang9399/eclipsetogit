<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
	</head>
	<script type="text/javascript">
		var cusNo = '${cusNo}';
		var vouType = '${vouType}';
		var classModel = '${classModel}';
		var ajaxData = ${ajaxData};
		var caseId = '${caseId}';
		var ifPrivateAssets = '${ifPrivateAssets}';
	    // ajaxData =JSON.parse(ajaxData);
        var classFirstNo="";
	    var  isQuote ="0";
		$(function() {
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced:{
					updateOnContentResize:true
				}
			});
			
		//客户新组件 20181109注释
		/*$("input[name=cusName]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
		});*/
		//押品展示号
		$.ajax({
			url:webPath+"/pledgeBaseInfo/getPledgeShowNoAjax",
			data:{cusNo:cusNo},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				$("input[name=pledgeShowNo]").val(data.pledgeShowNo);
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
		//担保方式
		$("select[name=pledgeMethod]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			changeCallback : function (obj, elem) {
				CollateralCommon.refreshFormByVouType("","",obj.val(),"");
			}
		});
		//押品新组件
		$("input[name=classId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.collClass,
			changeCallback : function (obj, elem) {
				$("input[name=classSecondName]").val(obj.data("text"));

				CollateralCommon.freshPledgeBaseForm("","","");
			}
		});
		$('select[name=classModel]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false //单选
		});
		//给主折扣率和次折扣率添加格式化保留两位小数的操作
 		$("input[name='extNum03']").bind("blur",function(){
		  formatToTwo(this);
		});
 		$("input[name='extNum04']").bind("blur",function(){
		  formatToTwo(this);
		});
	});
	
	function formatToTwo(obj){
		var numberValue = parseFloat($(obj).val());
		$(obj).val(numberValue.toFixed(2));
	}
	
	function freshPledgeBaseInfo(){
		var classId = $("select[name=classId]").val();
		var dataParam = JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
		$.ajax({
			url:webPath+"/pledgeBaseInfo/freshPledgeFormAjax",
			data:{classId:classId,ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				$("#pledgeBaseInfoInsert").empty().html(data.formHtml);
				//var option = $("select[name=classNo]").find("option");
				//makeOptionsJQ(option, data.classNo, data.pledgeMethod);
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
		
	function insertPledgeBaseInfo(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {ajaxData : dataParam,isQuote:isQuote,caseId:caseId,ifPrivateAssets:ifPrivateAssets},
				type : "POST",
				dataType : "json",
				beforeSend : function() {},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						var pledgeNo=data.pledgeNo;
						parent.dialog.get('addAssetDialog').close(data.pledge).remove();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
				
		
	function cancelInsert(){
		window.location.href=webPath+"/mfBusCollateral/getListPage";
	};
					//表单信息提示
	function func_uior_addTips(obj,msg){
		var $this =$(obj);
		var val = $this.val();
		if ($this.hasClass("Required")) {
			$this.removeClass("Required");
		}
		if($this.parent().find(".Required-font").length>0){
			$this.parent().find(".Required-font").remove();
		}
		//if(val==null||val==""||typeof(val)=="undefined"){
			//var $label = $('<label class="Required-font"><i class="i i-jingbao"></i>'+msg+'</label>');
			var $label = $('<div class="error required">'+msg+'</div>');
			$label.appendTo($this.parent());
			$this.addClass("Required");
		//}
		$this.one("focus.addTips", function(){
			$label.remove();
		});
	};
	function validateDupExtOstr04(){
				$.ajax({
					url:webPath+"/pledgeBaseInfo/validateDupExtOstr04Ajax",
					data : {
						ajaxData : 	$("input[name=extOstr04]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=extOstr04]"),"发票号码已存在");
							$("input[name=extOstr04]").val("");
						}
					},
					error : function(data) {
						$("input[name=extOstr04]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};
	
	function validateDupExtOstr07(){
				$.ajax({
					url:webPath+"/pledgeBaseInfo/validateDupExtOstr07Ajax",
					data : {
						ajaxData : 	$("input[name=extOstr07]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=extOstr07]"),"订单号码已存在");
							$("input[name=extOstr07]").val("");
						}
					},
					error : function(data) {
						$("input[name=extOstr07]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};
		
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
		           		<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/pledgeBaseInfo/insertAjax">
							<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						</form>	
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertPledgeBaseInfo('#pledgeBaseInfoInsert');"></dhcc:thirdButton>
	   			<%-- <dhcc:thirdButton value="关闭" action="关闭" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton> --%>
	   		</div>
   		</div>
	</body>
</html>
