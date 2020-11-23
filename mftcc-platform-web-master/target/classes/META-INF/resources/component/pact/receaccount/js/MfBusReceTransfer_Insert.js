;
var MfBusReceTransfer_Insert = function(window, $){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });

        //选择组件初始化
        $("[name=receId]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//多选选
            labelShow: false,
            items:receItems,
            addBtn:{
                "title":"新增账款",
                "fun":function(hiddenInput, elem){
                    top.addReceInfoFlag=false;
                    top.window.openBigForm(webPath+"/mfBusReceBaseInfo/input?appId="+appId+"&cusNo="+cusNo, '账款登记', function(){
                        if(top.addReceInfoFlag){
                            getReceBaseInfo(top.receId,function(data){
                                var mfBusReceBaseInfo = data.mfBusReceBaseInfo;
                                $("input[name=receNo]").val(mfBusReceBaseInfo.receNo);//合同/发票号
                                $("input[name=receName]").val(mfBusReceBaseInfo.receName);//
                                $("input[name=receBuyer]").val(mfBusReceBaseInfo.receCounterparty);//

                                $("input[name=receInvoiceAmt]").val(fmoney(mfBusReceBaseInfo.receInvoiceAmt,2));//合同/票面金额
                                $("input[name=receAmt]").val(fmoney(mfBusReceBaseInfo.receAmt,2));//应收账款金额
                                $("input[name=receTransAmt]").val(fmoney(mfBusReceBaseInfo.receTransAmt,2));//转让金额
                                $("input[name=receBeginDate]").val(mfBusReceBaseInfo.receBeginDate);//发票日
                                $("input[name=receEndDate]").val(mfBusReceBaseInfo.receEndDate);//账款到期日
                                $("input[name=receTransDate]").val(mfBusReceBaseInfo.receTransDate);//账款转让日

                                // var receBeginDate = mfBusReceBaseInfo.receBeginDate;
                                // receBeginDate = receBeginDate.substring(0, 4)+"-"+receBeginDate.substring(4,6)+"-"+receBeginDate.substring(6,8)
                                // var receEndDate = mfBusReceBaseInfo.receEndDate;
                                // receEndDate = receEndDate.substring(0, 4)+"-"+receEndDate.substring(4,6)+"-"+receEndDate.substring(6,8)
                                // var receTransDate = mfBusReceBaseInfo.receTransDate;
                                // receTransDate = receTransDate.substring(0, 4)+"-"+receTransDate.substring(4,6)+"-"+receTransDate.substring(6,8)
                                // $("input[name=receBeginDate]").val(receBeginDate);//发票日
                                // $("input[name=receEndDate]").val(receEndDate);//账款到期日
                                // $("input[name=receTransDate]").val(receTransDate);//账款转让日


                                $("input[name=receId]").parent(".input-group").find(".pops-value").text(mfBusReceBaseInfo.receNo);
                                $("input[name=receId]").val(mfBusReceBaseInfo.receId);
                                $("input[name=popsreceId]").popupSelection("addItem",{"id":mfBusReceBaseInfo.receId,"name":mfBusReceBaseInfo.receNo});
                                if($("input[name=receId]").parent(".input-group").find('.error.required').length>0){
                                    $("input[name=receId]").parent(".input-group").find('.error.required').remove();
                                }

                            });
                        }
                    });
                }
            },
            changeCallback : function (obj, elem) {
                var receId = $(obj).val();
                getReceBaseInfo(receId,function(data){
                    var mfBusReceBaseInfo = data.mfBusReceBaseInfo;
                    $("input[name=receNo]").val(mfBusReceBaseInfo.receNo);//合同/发票号
                    $("input[name=receId]").val(mfBusReceBaseInfo.receId);
                    $("input[name=receName]").val(mfBusReceBaseInfo.receName);//
                    $("input[name=receBuyer]").val(mfBusReceBaseInfo.receCounterparty);//
                    $("input[name=receInvoiceAmt]").val(fmoney(mfBusReceBaseInfo.receInvoiceAmt,2));//合同/票面金额
                    $("input[name=receAmt]").val(fmoney(mfBusReceBaseInfo.receAmt,2));//应收账款金额
                    $("input[name=receTransAmt]").val(fmoney(mfBusReceBaseInfo.receTransAmt,2));//转让金额
                    $("input[name=receBeginDate]").val(mfBusReceBaseInfo.receBeginDate);//发票日
                    $("input[name=receEndDate]").val(mfBusReceBaseInfo.receEndDate);//账款到期日
                    $("input[name=receTransDate]").val(mfBusReceBaseInfo.receTransDate);//账款转让日
                    // if(mfBusReceBaseInfo.receBeginDate!="" && mfBusReceBaseInfo.receBeginDate!=null){
                    //     var  receBeginDate = mfBusReceBaseInfo.receBeginDate;
                    //     // receBeginDate = receBeginDate.substring(0, 4)+"-"+receBeginDate.substring(4,6)+"-"+receBeginDate.substring(6,8)
                    //     $("input[name=receBeginDate]").val(receBeginDate);//发票日
                    // }
                    // if(mfBusReceBaseInfo.receEndDate!="" && mfBusReceBaseInfo.receEndDate!=null){
                    //     var  receEndDate = mfBusReceBaseInfo.receEndDate;
                    //     // receEndDate = receEndDate.substring(0, 4)+"-"+receEndDate.substring(4,6)+"-"+receEndDate.substring(6,8)
                    //     $("input[name=receEndDate]").val(receEndDate);//账款到期日
                    // }
                });
            }
        });
    };

    var getReceBaseInfo = function(receId,callBack){
        //根据账款编号获取账款详细信息
        $.ajax({
            url : webPath+"/mfBusReceBaseInfo/getReceBaseInfoAjax",
            data:{receId:receId},
            success:function(data){
                if(data.flag=="success"){
                    if(callBack!==undefined && typeof(callBack) == "function") {
                        callBack(data);
                    }
                }
            }
        });
    };


    //账款转让
    var _insertAjax = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url : url,
                data : {ajaxData : dataParam,appId:appId,transMainId:transMainId},
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
    var _updateAjax = function(obj){
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
        insertAjax:_insertAjax,
        updateAjax:_updateAjax,
        validateDupCertiNo:_validateDupCertiNo,
    };
}(window, jQuery);