<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>企业基本信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script>
	  	var cusNo = "${cusNo}";
	  	$(function(){
	  		validatIs();
	  	});

	  	function validatIs(){
  			var $legalIdNum = $("input[name='legalIdNum']")
  			$legalIdNum.change(function(){
	  			var idType = $("select[name='legalIdType']").val();
	  			if(idType=='0'){
				   var msg = CheckIdValue(this);
				   if(msg!=""){
				 	 alert(msg); 
				   } 
				}
			});
			
  		}
	  	function saveFormAjax(obj){
	  		//添加验证
	  		var saveFlag =MfAppBusApply.saveValidat();
	  		if(!saveFlag){
	  			return false;
	  		}
	  		//校验法人身份证号码
	  		var unVal = $("input[name=legalIdNum]").val();
			var column = $("input[name=legalIdNum]").attr("title");
			var legalIdNumType = $("select[name=legalIdType]").val();
			var relationId = $("input[name=cusNo]").val();
			var result = DingUtil.checkUniqueVal(unVal, column, relationId, "MfCusCorpBaseInfo", "01", "insert");
			var checkFlag = result.split("&")[0];
			result = result.split("&")[1];
			if (checkFlag == "1") {
				$.confirm(result,"确定继续吗？", function() {
				  //点击确认后的回调函数
				  ajaxCusFormInsert(obj);
				  }, function() {
				  //点击取消后的回调函数
				  });
			}else{
		  		ajaxCusFormInsert(obj);
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
				async:false,
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
	  	
	  	</script>
	</head>
	<body>
		<form method="post" action="${webPath}/dingCusPersBaseInfo/updateCusCorpAjax" class="weui_cells weui_cells_form " id="insertForm">
			<input class="weui_input" type="hidden" placeholder="请输入"
						name="cusNo" value="${mfCusCorpBaseInfo.cusNo}">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">企业名称</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="cusName" value="${mfCusCorpBaseInfo.cusName}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">法人名称</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="legalRepresName" value="${mfCusCorpBaseInfo.legalRepresName}">
				</div>
			</div>
			<div class="weui_cell weui_cell_select">
				<div class="weui_cell_hd">
					<label for="" class="weui_label">法人证件类型</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<select class="weui_select" name="legalIdType">
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
					<label class="weui_label">法人证件号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入" title="法人代表证件号码"
						name="legalIdNum" value="${mfCusCorpBaseInfo.legalIdNum}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">注册资本</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="number" placeholder="请输入"
						name="registeredCapital" value="${registeredCapitalFormatStr}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">资产总额</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="number" placeholder="请输入"
						name="assetSum" value="${assetSumFormatStr}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">营业收入</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="number" placeholder="请输入"
						name="bussIncome" value="${bussIncomeFormatStr}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">员工人数</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="number" placeholder="请输入"
						name="empCnt" value="${mfCusCorpBaseInfo.empCnt}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">注册地址</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="address" value="${mfCusCorpBaseInfo.address}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">联系人</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="text" placeholder="请输入"
						name="contactsName" value="${mfCusCorpBaseInfo.contactsName}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">联系人电话</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input mustinput='1' class="weui_input" type="number" placeholder="请输入"
						name="contactsTel" value="${mfCusCorpBaseInfo.contactsTel}">
				</div>
			</div>
			<div class="ding_btn_div">
				<a href="javascript:;" class="weui_btn weui_btn_primary mf_btn " onclick="saveFormAjax('#insertForm');">保存</a>
			</div>
		</form>
	</body>
</html>
