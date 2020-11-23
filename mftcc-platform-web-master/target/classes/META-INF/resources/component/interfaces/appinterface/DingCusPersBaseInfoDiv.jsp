<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>个人基本信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 	  	<script type="text/javascript" src="wechat/libs/jQueryWeUI/lib/jquery-2.1.4.js"></script> -->
<!-- 	  	<script type="text/javascript" src="wechat/libs/jQueryWeUI/js/jquery-weui.js"></script> -->
	  	<script type="text/javascript" src="/factor/wechat/libs/jQueryWeUI/js/city-picker.js"></script>
	  	<script>
	   	$(function(){
	   	var careaCode ='${mfCusPersBaseInfo.careaProvice}';
	   	if(careaCode!=""){
   			var provinceCode=careaCode.substring(0,2)+"0000";
		   	var cityCode;
		   	if(provinceCode=="110000"){
		   		cityCode="110000"
		   	}else if (provinceCode=="120000"){
		   		cityCode="120000"
		   	}else if (provinceCode=="310000"){
		   		cityCode="310000"
		   	}else if (provinceCode=="500000"){
		   		cityCode="500000"
		   	}else{
		   		cityCode=careaCode.substring(0,4)+"00";
		   	}
		  	var cityName = "";
		   	var provinceObj = $.rawCitiesData;
		   	   var cityObj;
		   	var careaObj;
		   	for(var i=0;i<provinceObj.length;i++){
		   		if(provinceObj[i].code==provinceCode){
		   			careaName =provinceObj[i].name+" ";
		   			cityObj = provinceObj[i].sub;
		   			for(var j=0;j<cityObj.length;j++){
		   				if(cityObj[j].code==cityCode){
		   					careaName =careaName+cityObj[j].name+" ";
		   					careaObj = cityObj[j].sub;
		   					for(var k=0;k<careaObj.length;k++){
		   						if(careaObj[k].code==careaCode){
		   							careaName=careaName+careaObj[k].name;
		   							break;
		   						}
		   					}
		   				}
		   			}
		   		}
		   	}
		   	$("#insertForm input[name='careaProviceDesc']").val(careaName);
	   	}
	
	   	//通过code获取地域名称，并且赋值地域信息
	   		cityPicker();
		   	$("#insertForm input[name!='careaProviceDesc']").blur(function(){
			   	var input =  $(this);
			   	var inputVal =  $(this).val();
		   		if(!inputVal || inputVal == ""){
			   		return false;
			   	}
			   	if(input.attr("readonly")){
					return;
				}
			   	var nameAttr = input.attr("name");
	 		   	var msg = input.parent().siblings().children('label').text();
			    if(saveFormAjax("#insertForm")){
			    	cusCompleteFlag = "1";//已完善信息标识
				    if(nameAttr == 'cusName'){//联系人名
				    	$("#contactsNameDetailTitle").text(inputVal);
				    }else if(nameAttr == 'cusTel'){//手机号
				    	$("#contactsTelDetailTitle").text(inputVal);
				    }
			    }
			});
			$("#insertForm select[name='idType']").change(function(){
				 saveFormAjax("#insertForm");
			});
			$("input[readonly='readonly']").each(function(){
				var $this = $(this);
				$this.css("color","graytext");
			});
	   	})
	  	function saveFormAjax(obj){
			var careaCode = $("#insertForm input[name='careaProviceDesc']").data('code');
			if(careaCode){
				$("#insertForm input[name='careaProvice']").val(careaCode.toString());
			}
	  		var flag = false;
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
// 					cityPicker();
				},error:function(){
					$.hideLoading();
					$.toast("保存失败", "cancel");
				}
			});
			return flag;
	  	}
	  	function cityPicker(){ 
	  	//首先这是有默认值的
	  		$careaProviceDesc=$("#insertForm input[name='careaProviceDesc']");
	  		$careaProviceDesc.cityPicker({
	  			  onClose: function(p, v, d) {
	  			  	 saveFormAjax("#insertForm");
			       }
	  		});
	  	}
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
		<form method="post" action="${webPath}/dingCusPersBaseInfo/updateAjax" class="weui_cells weui_cells_form" style="margin-bottom:0px;margin-top: 0px;" id="insertForm">
			<input class="weui_input" type="hidden" placeholder="请输入"
						name="cusNo" value="${mfCusPersBaseInfo.cusNo}">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">客户名称</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input  id='name' class="weui_input" type="text" placeholder="请输入"
					readonly="readonly"	name="cusName" value="${mfCusPersBaseInfo.cusName}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">手机号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="tel" maxlength="11" placeholder="请输入"
						name="cusTel" value="${mfCusPersBaseInfo.cusTel}">
				</div>
			</div>
			<div class="weui_cell weui_cell_select">
				<div class="weui_cell_hd">
					<label for="" class="weui_label">证件类型</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<select  class="weui_select" name="idType">
						<c:if test="${mfCusPersBaseInfo.idType == '0'}">	<option value="0" selected="">身份证</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '9'}"><option value="9">警官证</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '1'}"><option value="1">户口簿</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '2'}"><option value="2">护照</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '3'}"><option value="3">军官证</option></c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '4'}"><option value="4">士兵证</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '5'}"><option value="5">港澳居民来往内地通行证</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '6'}"><option value="6">台湾同胞来往内地通行证</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '7'}"><option value="7">临时身份证</option> </c:if>
						<c:if test="${mfCusPersBaseInfo.idType == '8'}"><option value="8">外国人居留证</option> </c:if>
					</select>
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">证件号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input  class="weui_input" type="text" placeholder="请输入" readonly="readonly"
						name="idNum" value="${mfCusPersBaseInfo.idNum}">
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">客户所属地区</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="text"  placeholder="请输入"
						name="careaProviceDesc" value="" readonly="readonly" >
					<input class="weui_input" type="hidden"  placeholder="请输入"
						name="careaProvice" value="${mfCusPersBaseInfo.careaProvice}" readonly="" >
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">户籍地址</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input class="weui_input" type="text" placeholder="请输入"
						name="regHomeAddre" value="${mfCusPersBaseInfo.regHomeAddre}">
				</div>
			</div>
		</form>
	</body>
</html>
