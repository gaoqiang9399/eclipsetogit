;
var MfTrenchCreditAmtModifyHis_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	//根据保证金和保证比例计算授信额度
	var _setCreditAmt=function(){
		var addAssureAmt=$("input[name=addAssureAmt]").val();
		var assureRate=$("input[name=assureRate]").val();
		if(addAssureAmt==""||assureRate==""){
			$("input[name=addCreditAmt]").val("");
			return;
		}
		addAssureAmt=Number(addAssureAmt.replace(/,/g,""));
		assureRate=Number(assureRate.replace(/,/g,""));
		
		var assureAmtTmp=addAssureAmt+$("input[name=assureAmt]").val()*1;
		if(assureAmtTmp<=0){
			$("input[name=addCreditAmt]").val("");
			$("input[name=addAssureAmt]").val("");
			alert("保证金减少的金额不能大于当前渠道保证金",0);
			return;
		}
		addAssureAmt=(addAssureAmt*assureRate)/100;
		if(addAssureAmt>0){
			$("input[name=addCreditAmt]").val(addAssureAmt);
			$("input[name=addCreditAmt]").attr("id","addCreditAmt");
			toMoney(document.getElementById("addCreditAmt"));
			$("#addCreditAmt").focus();
			$("#addCreditAmt").blur();
		}else if(addAssureAmt<0){
			addAssureAmt=addAssureAmt+$("input[name=creditBal]").val()*1;
			if(addAssureAmt<0){
				$("input[name=addCreditAmt]").val("");
				alert("授信余额不能为负数",0);
			}else if(addAssureAmt>=0){
				$("input[name=addCreditAmt]").val(addAssureAmt);
				$("input[name=addCreditAmt]").attr("id","addCreditAmt");
				toMoney(document.getElementById("addCreditAmt"));
				$("#addCreditAmt").focus();
				$("#addCreditAmt").blur();
			}
		}
	}
	
	var _checkCreditBal=function(){
		
	};
	var _insertAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				success:function(data){
					if(data.flag == "success"){
						  top.addAmtFlag = true;
						  myclose_click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				}
			});
		}
	};
	return{
		init:_init,
		insertAjax:_insertAjax,
		setCreditAmt:_setCreditAmt
	}
}(window,jQuery)