<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="${webPath}/wechat/libs/jQueryWeUI/js/city-picker.js"></script>
	
		<title>个人基本信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script>
	  	var cusNo = "${cusNo}";
	  		$(function(){
		  		validatIs();
		  		cityPicker();
		  	});
	  	function validatIs(){
  			var $idNum = $("input[name='idNum']")
  			$idNum.change(function(){
	  			var idType = $("select[name='idType']").val();
	  			if(idType=='0'){
				   var msg = CheckIdValue(this);
				   if(msg!=""){
				 	 alert(msg); 
				   }
				}
			});
			var $cusTel = $("input[name='cusTel']")
  			$cusTel.change(function(){
				   var msg = checkMobileOnly(this);
			});
  		}
	  	function saveFormAjax(obj){
// 	  		$.toast("保存成功");
// 	  		history.back(-1);
// 			return ;
	  		//添加验证
	  		var saveFlag =MfAppBusApply.saveValidat();
	  		if(!saveFlag){
	  			return false;
	  		}
	  		//验证手机号唯一
	  		//手机号码唯一性验证
			var $tel =  $("input[name=cusTel]")
			var relationId = $("input[name=cusNo]").val();
			var cusNo = $("input[name=cusNo]").val();
			var unVal = $tel.val();
			if(unVal  && unVal !=null && unVal!=""){
				var columnTitle= $tel.attr("title");
				var unValCheckResult = DingUtil.checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","20","insert",cusNo);
				var checkFlag = unValCheckResult.split("&")[0];//result.split("&")[0];
				var telResultMsg = unValCheckResult.split("&")[1];
				if(checkFlag == "1"){
					$.alert(telResultMsg);
					return false;
					/* $.confirm(telResultMsg,"确定继续吗？", function() {
					  //点击确认后的回调函数
					  	ajaxCusFormInsert(obj);
				   	}, function() {
					  //点击取消后的回调函数
					}); */
				}else{
					ajaxCusFormInsert(obj);
				}
			}
	  	};
	  	function ajaxCusFormInsert(obj){
	  		var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
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
						location.href="${webPath}/mfAppCusCustomer/getById?cusNo="+cusNo+"&appId=";
					}else{
						$.toast("保存失败", "cancel");
					}
				},error:function(){
					$.hideLoading();
					$.toast("保存失败", "cancel");
				}
			});
	  	}
	  	function cityPicker(){
	  		$careaProvice=$("input[name='careaProvice']");
	  		$careaProvice.cityPicker({
	  			  onClose: function(p, v, d) {
	  			   	var careaProvice =$("input[name='careaProvice']").val()
		  			$("input[name='regHomeAddre']").val(careaProvice);
			      }
	  		});
	  	}
	  	</script>
	</head>
	<body>
		<form method="post" action="${webPath}/dingCusPersBaseInfo/updateAjax" class="weui_cells weui_cells_form" id="insertForm">
			<input class="weui_input" type="hidden" placeholder="请输入"
						name="cusNo" value="${mfCusPersBaseInfo.cusNo}">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">客户名称</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="cusName" disabled value="${mfCusPersBaseInfo.cusName}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">手机号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入" title="手机号码"
						name="cusTel" value="${mfCusPersBaseInfo.cusTel}">
				</div>
			</div>
			<div class="weui_cell weui_cell_select">
				<div class="weui_cell_hd">
					<label for="" class="weui_label">证件类型</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<select class="weui_select" name="idType" disabled>
						<option value=""></option>
						<option value="0" selected="">身份证</option>
						<option value="9">警官证</option>
						<option value="1">户口簿</option>
						<option value="2">护照</option>
						<option value="3">军官证</option>
						<option value="4">士兵证</option>
						<option value="5">港澳居民来往内地通行证</option>
						<option value="6">台湾同胞来往内地通行证</option>
						<option value="7">临时身份证</option>
						<option value="8">外国人居留证</option>
					</select>
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">证件号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input disabled mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="idNum" value="${mfCusPersBaseInfo.idNum}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">客户所属地区</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="careaProvice" value="${mfCusPersBaseInfo.careaProvice}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">户籍地址</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="regHomeAddre" value="${mfCusPersBaseInfo.regHomeAddre}">
				</div>
			</div>
			<div class="ding_btn_div">
				<a href="javascript:;" class="weui_btn weui_btn_primary mf_btn " onclick="saveFormAjax('#insertForm');">保存</a>
			</div>	
		</form>
	</body>
</html>
