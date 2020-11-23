;
var MfCusCooperativeAgency_Insert = function(window, $) {
	/** 初始化 */
	var _init = function() {
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		// 关闭按钮
		$(".cancel").bind("click", function(event) {
			$(top.window.document).find("#showDialog").remove();
		});
		
	};

	//新增
	var _ajaxSave = function(obj){
		var dataParam = JSON.stringify($(obj).serializeArray());	
			var url = $(obj).attr("action");
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){ 
					LoadingAnimate.start();
				},success:function(data){
					if(data.flag == "success"){
						top.addFlag = true;
						window.top.alert(data.msg, 1);
						$(top.window.document).find("#showDialog .close").click();// 点击弹出框的“X”，触发list页面的回调函数,刷新List页面
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				},complete:function(){
					LoadingAnimate.stop();
				}
			});
		
	};
    var _checkIdNumForCoopAgency = function () {
        var legalIdNum=$("[name=legalIdNum]").val();
        if(legalIdNum==""){
            return;
        }
        jQuery.ajax({
            url:webPath+'/mfCusCooperativeAgency/checkIdNumForCoopAgencyAjax',
            data:{legalIdNum:legalIdNum},
            dataType:'json',
            type:'POST',
            success:function(data){
                if(data.flag=="success"){
                    $("input[name=legalIdNum]").val("");
                    var coopAgency=data.mfCusCooperativeAgency;
                    var msg=coopAgency.opName+"登记的"+coopAgency.legalRepresName+"的证件号码"
                    alert(top.getMessage("CONFIRM_UPDATE_CUSTOMER",msg),2,function(){
                        $("input[name=legalIdNum]").val("");
                    });
                }else if(data.flag=="error"){
                    window.top.alert(data.msg,0);
                }
            }
        })
    }

    var _getCusInfoByName = function () {
    	var orgaName = $("input[name=orgaName]").val();
		if(null == orgaName || orgaName == ''){
            window.top.alert("受益人名称不能为空！",0);
            return;
		}
        jQuery.ajax({
            url:webPath+'/mfCusCooperativeAgency/getCusInfoByName',
            data:{orgaName:orgaName},
            dataType:'json',
            type:'POST',
            success:function(data){
                if(data.flag=="success"){
					console.log(data);
					if(null != data.data.socialCreditCode){
                        $("input[name=socialCreditCode]").val(data.data.socialCreditCode);
					}
                    if(null != data.data.corpNature){
                        $("select[name=corpNature]").val(data.data.corpNature);
                    }
                    if(null != data.data.legalRepresName){
                        $("input[name=legalRepresName]").val(data.data.legalRepresName);
                    }
                    if(null != data.data.setupDate){
                        $("input[name=setupDate]").val(data.data.setupDate);
                    }
                    if(null != data.data.careaCity){
                        $("input[name=careaCity]").val(data.data.careaCity);
                    }
                    if(null != data.data.empCnt){
                        $("input[name=empCnt]").val(data.data.empCnt);
                    }
                    if(null != data.data.scopeOfBusiness){
                        $("textarea[name=scopeOfBusiness]").val(data.data.scopeOfBusiness);
                    }
                }else if(data.flag=="error"){
                    window.top.alert(data.msg,0);
                }
            }
        })
    }

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave,
        checkIdNumForCoopAgency:_checkIdNumForCoopAgency,
        getCusInfoByName:_getCusInfoByName
	};
}(window, jQuery);
