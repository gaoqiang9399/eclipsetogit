function showcheckinfo(obj){
		var cusName = $(obj).val();
		if(cusName!=""){
			$(obj).parent().find(".block-view").removeAttr("disabled"); 
		}else{
			$(obj).parent().find(".block-view").attr("disabled","disabled"); 
		}
		
	};
var IdentityCheck=function(window,$){
	var _init=function(){
		
	};
	//担保人页面初始化
	var _assureInit=function(){
		var assureType=$("[name=assureType]").val();
		if(assureType=="2"){//自然人
			$("span[id=identityCheck]").show();
		}else if(assureType=="1"){//法人
			$("span[id=identityCheck]").hide();
		}
	};
	//客户身份核查
	var _cusIdentityCheck=function(){
		var cusName=$("input[name=cusName]").val();
		var idNum=$("input[name=idNum]").val();
		_doIdentityCheck(cusName,idNum);
	};
	//客户社会关系身份核查
	var _cusFamilyIdentityCheck=function(){
		var cusName=$("input[name=relName]").val();
		var idNum=$("input[name=idNum]").val();
		_doIdentityCheck(cusName,idNum);
	};
	
	//担保人身份核查
	var _assureIdentityCheck=function(){
		var cusName=$("input[name=assureName]").val();
		var idNum=$("input[name=idNum]").val();
		_doIdentityCheck(cusName,idNum);
	};
	//身份核查
	var _doIdentityCheck=function(cusName,idNum){
		var idType=$("[name=idType]").val();
		if(idType!="0"&&idType!="7"){
			alert(top.getMessage("FIRST_SELECT_FIELD","身份证或临时身份证证件类型"), 0);
			return;
		}
		if(cusName==""){
			alert(top.getMessage("FIRST_OPERATION_ADD","核查名称"), 0);
			return;
		}
		if(idNum==""){
			alert(top.getMessage("FIRST_OPERATION_ADD","核查身份证号码"), 0);
			return;
		}
		
		alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content":"请核实查询的身份证号不是恐怖分子、在逃人员等不法分子身份证号","operation":"查询"}), 2, function() {
			$.ajax({
				url : webPath+'/mfIdentityCheckRecordInfo/doIdentityCheckAjax',
				type : "POST",
				dataType : "json",
				data:{cusName:cusName,idNum:idNum},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == 'success') {
						var spanStr='<span class="input-group-addon"><span class="btn block-view" disabled="disabled">'+data.checkResultMsg+'</span></span>';
						$("input[name=idNum]").parent().find(".input-group-addon").remove();
						$("input[name=idNum]").parent().append(spanStr);
					} else if(data.flag == 'error'){
						alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		});
	};
	return{
		doIdentityCheck:_doIdentityCheck,
		cusIdentityCheck:_cusIdentityCheck,
		assureIdentityCheck:_assureIdentityCheck,
		cusFamilyIdentityCheck:_cusFamilyIdentityCheck,
		assureInit:_assureInit
	};
}(window,jQuery);