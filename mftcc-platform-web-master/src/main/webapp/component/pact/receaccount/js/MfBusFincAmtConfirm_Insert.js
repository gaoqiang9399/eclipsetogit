;
var MfBusFincAmtConfirm_Insert = function(window, $) {
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });


        //还款方式处理
        var $repayObj = $("input[name=repayType]");
        if($repayObj.is(':visible')){
            $("input[name=repayType]").popupSelection({
                searchOn:false,//启用搜索
                inline:false,//下拉模式
                multiple:false,//多选选
                title:"还款方式",
                valueClass:"show-text",
                items:ajaxData.repayTypeMap,
                changeCallback : function (obj, elem) {
                }
            });
            if(typeof($repayObj.attr('readonly'))!='undefined'){
                $repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
        }
    };
    //放款申请保存方法
    var _ajaxInsert = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url: url,
                data:{ajaxData:dataParam,pactId:pactId},
                success:function(data){
                    if(data.flag == "success"){
                        if(data.ifApproval=="1"){
                            alert(data.msg,2,function () {
                                _submitProcess(data.confirmId);
                            },function(){
                                window.location.href = webPath+"/mfBusFincAmtConfirm/getListPage";
                            });
                        }else{
                            DIALOG.msg(data.msg,function () {
                                window.location.href = webPath+"/mfBusFincAmtConfirm/getListPage";
                            })

                        }
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
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
                url: url,
                data:{ajaxData:dataParam,pactId:pactId},
                success:function(data){
                    if(data.flag == "success"){
                        if(data.ifApproval=="1"){
                            alert(data.msg,2,function () {
                                $.ajax({
                                    url : webPath+"/mfBusFincAmtConfirm/submitProcess",
                                    data:{pactId:pactId,confirmId:data.confirmId},
                                    success:function(data){
                                        if(data.flag=="success"){
                                            DIALOG.msg(data.msg,function () {
                                                top.confirmFlag=true;
                                                myclose_click();
                                            });
                                        }
                                    }
                                });
                            });
                        }else{
                            DIALOG.msg(data.msg,function () {
                               top.confirmFlag=true;
                               myclose_click();
                            });

                        }
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        }
    };

    var _submitProcess = function(confirmId){
        $.ajax({
            url : webPath+"/mfBusFincAmtConfirm/submitProcess",
            data:{pactId:pactId,confirmId:confirmId},
            success:function(data){
                if(data.flag=="success"){
                    DIALOG.msg(data.msg,function () {
                        window.location.href = webPath+"/mfBusFincAmtConfirm/getListPage";
                    });

                }
            }
        });
    };

    //取消回退至列表页面
    var _cancelBack = function(){
        window.location.href = webPath+"/mfBusFincAmtConfirm/getListPage";
    };
    return {
        init : _init,
        ajaxInsert:_ajaxInsert,
        updateAjax:_updateAjax,
        cancelBack:_cancelBack,
    };
}(window, jQuery);