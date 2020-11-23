;
var CertiInfoInsert = function(window, $){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });

        //选择组件初始化
        $("[name=collateralId]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//多选选
            labelShow: false,
            items:pleItems,
            addBtn:{
                "title":"新增账款",
                "fun":function(hiddenInput, elem){
                    top.addCollateral=false;
                    top.window.openBigForm(webPath+"/mfBusCollateralRel/infoCollectInput?appId="+appId+"&cusNo="+cusNo+"&entrFlag=businessNoTask&infoType=account", '账款登记', function(){
                        if(top.addCollateral){
                            getPledgeBaseInfo(top.collateralId,function(data){
                                var pledgeBaseInfo = data.pledgeBaseInfo;
                                $("input[name=certiNo]").val(pledgeBaseInfo.pleInvoiceNum);//合同/发票号
                                $("input[name=certiAmount]").val(fmoney(pledgeBaseInfo.pleInvoiceAmount,2));//合同/票面金额
                                $("input[name=receAmt]").val(fmoney(pledgeBaseInfo.pleOriginalValue,2));//应收账款金额
                                $("input[name=receTransAmt]").val(fmoney(pledgeBaseInfo.pleValue,2));//转让金额
                                var pleValueDate = pledgeBaseInfo.pleValueDate;
                                pleValueDate = pleValueDate.substring(0, 4)+"-"+pleValueDate.substring(4,6)+"-"+pleValueDate.substring(6,8)
                                var pleExpiryDate = pledgeBaseInfo.pleExpiryDate;
                                var pleExpiryDateFinal = pleExpiryDate.substring(0, 4)+"-"+pleExpiryDate.substring(4,6)+"-"+pleExpiryDate.substring(6,8)
                                $("input[name=certiStartDate]").val(pleValueDate);//合同/发票日
                                $("input[name=certiDueDate]").val(pleExpiryDateFinal);//账款到期日


                                $("input[name=collateralId]").parent(".input-group").find(".pops-value").text(pledgeBaseInfo.pledgeName);
                                $("input[name=collateralId]").val(pledgeBaseInfo.pledgeNo);
                                $("input[name=popscollateralId]").popupSelection("addItem",{"id":data.pledgeNo,"name":pledgeBaseInfo.pledgeName});
                                if($("input[name=collateralId]").parent(".input-group").find('.error.required').length>0){
                                    $("input[name=collateralId]").parent(".input-group").find('.error.required').remove();
                                }

                            });
                        }
                    });
                }
            },
            changeCallback : function (obj, elem) {
                var collateralId = $(obj).val();
                getPledgeBaseInfo(collateralId,function(data){
                    var pledgeBaseInfo = data.pledgeBaseInfo;
                    $("input[name=certiNo]").val(pledgeBaseInfo.pleInvoiceNum);//合同/发票号
                    $("input[name=certiAmount]").val(fmoney(pledgeBaseInfo.pleInvoiceAmount,2));//合同/票面金额
                    $("input[name=receAmt]").val(fmoney(pledgeBaseInfo.pleOriginalValue,2));//应收账款金额
                    $("input[name=receTransAmt]").val(fmoney(pledgeBaseInfo.pleValue,2));//转让金额
                    if(pledgeBaseInfo.pleValueDate!="" && pledgeBaseInfo.pleValueDate!=null){
                        var  certiStartDate = pledgeBaseInfo.pleValueDate;
                        var  certiStartDateFinal = certiStartDate.substring(0, 4)+"-"+certiStartDate.substring(4,6)+"-"+certiStartDate.substring(6,8)
                        $("input[name=certiStartDate]").val(certiStartDateFinal);//合同/发票日
                    }
                    if(pledgeBaseInfo.pleValueDate!="" && pledgeBaseInfo.pleValueDate!=null){
                        var  certiDueDate = pledgeBaseInfo.pleExpiryDate;
                        var  certiDueDateFinal = certiDueDate.substring(0, 4)+"-"+certiDueDate.substring(4,6)+"-"+certiDueDate.substring(6,8)
                        $("input[name=certiDueDate]").val(certiDueDateFinal);//账款到期日
                    }
                });
            }
        });
    };

    var getPledgeBaseInfo = function(pledgeNo,callBack){
        //根据账款编号获取账款详细信息
        $.ajax({
            url : webPath+"/pledgeBaseInfo/getPledgeBaseInfoAjax",
            data:{pledgeNo:pledgeNo},
            success:function(data){
                if(data.flag=="success"){
                    if(callBack!==undefined && typeof(callBack) == "function") {
                        callBack(data);
                    }
                }
            }
        });
    };


    //担保登记
    var _insertCertiInfo = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url : url,
                data : {ajaxData : dataParam,appId:appId},
                success : function(data) {
                    if (data.flag == "success") {
                        top.addFlag=true;
                        top.msg=data.msg;
                        top.htmlStr = data.htmlStr;
                        myclose_click();
                    } else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },error : function(data) {
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    };

    //账款转让编辑
    var _updateCertiInfo = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url : url,
                data : {ajaxData : dataParam,appId:appId},
                success : function(data) {
                    if (data.flag == "success") {
                        top.addFlag=true;
                        top.msg=data.msg;
                        myclose_click();
                    } else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },error : function(data) {
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    };
    //表单信息提示
    var  func_uior_addTips = function(obj,msg){
        var $this =$(obj);
        var val = $this.val();
        if ($this.hasClass("Required")) {
            $this.removeClass("Required");
        }
        if($this.parent().find(".Required-font").length>0){
            $this.parent().find(".Required-font").remove();
        }
        var $label = $('<div class="error required">'+msg+'</div>');
        $label.appendTo($this.parent());
        $this.addClass("Required");
        //}
        $this.one("focus.addTips", function(){
            $label.remove();
        });
    };
    var  _validateDupCertiNo = function(){
        $.ajax({
            url:webPath+"/certiInfo/validateDupCertiNoAjax",
            data : {ajaxData : 	$("input[name=certiNo]").val()},
            success : function(data) {
                if(data.result == "0"){
                    func_uior_addTips($("input[name=certiNo]"),"权属证书编号已存在");
                    $("input[name=certiNo]").val("");
                }
            },error : function(data) {
                $("input[name=certiNo]").val("");
                alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };

    return {
        init:_init,
        insertCertiInfo:_insertCertiInfo,
        updateCertiInfo:_updateCertiInfo,
        validateDupCertiNo:_validateDupCertiNo,
    };
}(window, jQuery);