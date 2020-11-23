;
var MfCollateralInsuranceClaims_Insert = function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});

        // 初始化保险编号
        $("input[name=policyNo]") .popupSelection({
            searchOn : false,// 启用搜索
            inline : true,// 下拉模式
            multiple : false,// 单选
            items : insInfoMap,
            changeCallback : function( obj, elem) {
                var policyNo = $(obj).val();
                //console(insInfoMap);
                $.ajax({
                    url : webPath + "/mfCollateralInsuranceClaims/getInsInfo",
                    data : { insId : policyNo },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        if (data.flag == "success") {
                            // 投保日期-保险到日期
                            $("input[name=policyStartEndDate]").val(data.insInfo.insStart+"-"+data.insInfo.insEnd);
                            // 保险金额
                            $("input[name=policyMoney]").val(data.insInfo.insAmount);
                        }
                    },
                    error : function() {
                        _func_insInfo_addTips(obj,data.msg);
                    }
                });
            }
        });
	};

    var _insertClaimsInfo=function(obj){
       var compensationMoney = $("input[name=compensationMoney]");
        var policyMoney = $("input[name=policyMoney]");
        if(compensationMoney.val()>policyMoney.val()){
            _func_insInfo_addTips("input[name=compensationMoney]","赔付金额不能大于保险金额！");
            return;
        }
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url : url,
                data : {ajaxData : dataParam},
                success : function(data) {
                    if (data.flag == "success") {
                        top.addFlag = true;
                        if (data.htmlStrFlag == "1") {
                            top.htmlStrFlag = true;
                            top.htmlString = data.htmlStr;
                            top.updateFlag = true;
                            top.tableName = "mf_collateral_insurance_claims";
                        }
                        myclose_click();
                    } else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    };

    //保单编号如果有错 信息提示
    var _func_insInfo_addTips=function(obj,msg){
        var $this =$(obj);
        if ($this.hasClass("Required")) {
            $this.removeClass("Required");
        }
        if($this.parent().find(".error.required").length>0){
            $this.parent().find(".error.required").remove();
        }

        var $label = $('<div class="error required">'+msg+'</div>');
        $label.appendTo($this.parent());
        $this.addClass("Required");
        $this.data("errorLabel", $label);
        //}
        $this.one("focus.addTips", function(){
            $label.remove();
        });
    }
	
	return {
		init:_init,
        insertClaimsInfo:_insertClaimsInfo,
        func_insInfo_addTips: _func_insInfo_addTips,
	};
}(window, jQuery);