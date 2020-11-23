<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%-- <%@ taglib prefix="s" uri="/struts-tags"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>企业基本信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script>
	  
	  	function saveFormAjax(obj){
	  		var flag =false;
	  		//添加验证
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
						flag = true;
						$.toast("保存成功");
					}else{
						$.toast("保存失败", "cancel");
					}
				},error:function(){
					$.hideLoading();
					$.toast("保存失败", "cancel");
				}
			});
			return flag;
	  	}
	  	$(function(){
		  	$("#insertForm input").blur(function(){
		  		var input = $(this);
			   	var inputVal =  input.val();
//
			   	if(!inputVal || inputVal == ""){
			   		return false;
			   	}
		   		if(input.attr("readonly")){
					return;
				}
			   	var nameAttr = input.attr("name");
			   	var labeltext = input.parent().parent().children(".weui_label").text();
			    //
			    if(saveFormAjax("#insertForm")){
			    	cusCompleteFlag = "1";//已完善信息标识
			    	if(nameAttr == 'contactsName'){//联系人名
			    		$("#contactsNameDetailTitle").text(inputVal);
			   	 	}else if(nameAttr == 'contactsTel'){//手机号
			    		$("#contactsTelDetailTitle").text(inputVal);
			    	}
			    }
			});
			$("input[readonly='readonly']").each(function(){
				var $this = $(this);
				$this.css("color","graytext");
			});
			//法人身份证类型更改
			$("#insertForm select[name='legalIdType']").change(function(){
				saveFormAjax("#insertForm");
			});
	  	})
	  	</script>
	</head>
	<body>
		<a class="weui_cell" href="javascript:;">		
			<div class="weui_cell_hd">								
			</div>								
			<div class="weui_cell_bd weui_cell_primary">			
				<span class='info-box-icon i i-jibenxinxi icon-font'  style="background-color:#FF6263"></span><span class="">基本信息</span>		
			</div>			
		</a>
		<form method="post" action="${webPath}/dingCusPersBaseInfo/updateCusCorpAjax" class="weui_cells weui_cells_form " style="margin-bottom:0px;margin-top: 0px;" id="insertForm">
			<input class="weui_input" type="hidden" placeholder="请输入"
						name="commAddress" value="${mfCusCorpBaseInfo.commAddress}">
			<input class="weui_input" type="hidden" placeholder="请输入"
						name="cusNo" value="${mfCusCorpBaseInfo.cusNo}">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">企业名称</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input readonly="readonly" class="weui_input" type="text" placeholder="请输入"
						name="cusName" value="${mfCusCorpBaseInfo.cusName}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">法人名称</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="text" placeholder="请输入"
						name="legalRepresName" value="${mfCusCorpBaseInfo.legalRepresName}">
				</div>
			</div>
			<div class="weui_cell weui_cell_select">
				<div class="weui_cell_hd">
					<label for="" class="weui_label">法人证件类型</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<select  class="weui_select" name="legalIdType">
						<option value="0" <c:if test="${mfCusCorpBaseInfo.legalIdType == '0'}"> selected="selected" </c:if>>身份证</option>
						<option value="9" <c:if test="${mfCusCorpBaseInfo.legalIdType == '9'}"> selected="selected" </c:if>>警官证</option>
						<option value="1" <c:if test="${mfCusCorpBaseInfo.legalIdType == '1'}"> selected="selected" </c:if>>户口簿</option>
						<option value="2" <c:if test="${mfCusCorpBaseInfo.legalIdType == '2'}"> selected="selected" </c:if>>护照</option>
						<option value="3" <c:if test="${mfCusCorpBaseInfo.legalIdType == '3'}"> selected="selected" </c:if>>军官证</option>
						<option value="4" <c:if test="${mfCusCorpBaseInfo.legalIdType == '4'}"> selected="selected" </c:if>>士兵证</option>
						<option value="5" <c:if test="${mfCusCorpBaseInfo.legalIdType == '5'}"> selected="selected" </c:if>>港澳居民来往内地通行证</option>
						<option value="6" <c:if test="${mfCusCorpBaseInfo.legalIdType == '6'}"> selected="selected" </c:if>>台湾同胞来往内地通行证</option>
						<option value="7" <c:if test="${mfCusCorpBaseInfo.legalIdType == '7'}"> selected="selected" </c:if>>临时身份证</option>
						<option value="8" <c:if test="${mfCusCorpBaseInfo.legalIdType == '8'}"> selected="selected" </c:if>>外国人居留证</option>
					</select>
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">法人证件号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input  class="weui_input" type="text" placeholder="请输入"
						name="legalIdNum" value="${mfCusCorpBaseInfo.legalIdNum}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">注册资本</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" placeholder="请输入"
						name="registeredCapital" value="${registeredCapitalFormatStr}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">资产总额</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" placeholder="请输入"
						name="assetSum" value="${assetSumFormatStr}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">营业收入</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" placeholder="请输入"
						name="bussIncome" value="${bussIncomeFormatStr}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">员工人数</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" placeholder="请输入"
						name="empCnt" value="${mfCusCorpBaseInfo.empCnt}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">注册地址</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="text" placeholder="请输入"
						name="address" value="${mfCusCorpBaseInfo.address}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">联系人</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="text" placeholder="请输入"
						name="contactsName" value="${mfCusCorpBaseInfo.contactsName}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">联系人电话</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="tel" maxlength="20" placeholder="请输入"
						name="contactsTel" value="${mfCusCorpBaseInfo.contactsTel}">
				</div>
			</div>
		</form>
	</body>
</html>
