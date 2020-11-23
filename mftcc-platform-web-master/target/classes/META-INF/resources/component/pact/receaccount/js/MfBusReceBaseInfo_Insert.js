;
var MfBusReceBaseInfo_Insert = function(window, $) {
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
        //账款类别
        if (!$("input[name='classId']").is(":hidden")){
            $("input[name=classId]").popupSelection({
                searchOn:false,//启用搜索
                inline:true,//下拉模式
                multiple:false,//多选选
                items:ajaxData.collClassArray,
                changeCallback : function (obj, elem) {
                }
            });
        }
        //应收账款商务合同选择组件初始化
        $("[name=recePactId]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//多选选
            labelShow: false,
            items:ajaxData.recePactArray,
            addBtn:{
                "title":"新增商务合同",
                "fun":function(hiddenInput, elem){
                    top.addBusContractFlag=false;
                    top.window.openBigForm(webPath+"/mfReceBusinessContractInfo/input?cusNo="+cusNo, '商务合同', function(){
                        if(top.addBusContractFlag){
                            getReceBusContractInfo(top.recePactId,function(data){
                                var mfReceBusinessContractInfo = data.mfReceBusinessContractInfo;
                                $("input[name=recePactNo]").val(mfReceBusinessContractInfo.recePactNo);
                                $("input[name=receCounterparty]").val(mfReceBusinessContractInfo.receCounterparty);//合同/发票号
                                $("input[name=termShow]").val(data.termShow);
                                $("input[name=term]").val(mfReceBusinessContractInfo.term);
                                $("input[name=termType]").val(mfReceBusinessContractInfo.termType);
                                $("input[name=recePactBeginDate]").val(mfReceBusinessContractInfo.recePactBeginDate);//合同/日
                                $("input[name=recePactEndDate]").val(mfReceBusinessContractInfo.recePactEndDate);//合同到期日
                                // $("input[name=receEndDate]").val(mfReceBusinessContractInfo.recePactEndDate);//应收账款到期日（默认等于合同到期日）


                                $("input[name=recePactId]").parent(".input-group").find(".pops-value").text(mfReceBusinessContractInfo.recePactNo);
                                $("input[name=recePactId]").val(mfReceBusinessContractInfo.recePactId);
                                $("input[name=popsrecePactId]").popupSelection("addItem",{"id":mfReceBusinessContractInfo.recePactId,"name":mfReceBusinessContractInfo.recePactNo});
                                if($("input[name=recePactId]").parent(".input-group").find('.error.required').length>0){
                                    $("input[name=recePactId]").parent(".input-group").find('.error.required').remove();
                                }
                            });
                        }
                    });
                }
            },
            changeCallback : function (obj, elem) {
                var recePactId = $(obj).val();
                getReceBusContractInfo(recePactId,function(data){
                    var mfReceBusinessContractInfo = data.mfReceBusinessContractInfo;
                    $("input[name=recePactId]").val(mfReceBusinessContractInfo.recePactId);
                    $("input[name=recePactNo]").val(mfReceBusinessContractInfo.recePactNo);//合同号
                    $("input[name=receCounterparty]").val(mfReceBusinessContractInfo.receCounterparty);//交易方
                    $("input[name=termShow]").val(data.termShow);
                    $("input[name=term]").val(mfReceBusinessContractInfo.term);
                    $("input[name=termType]").val(mfReceBusinessContractInfo.termType);
                    $("input[name=recePactBeginDate]").val(mfReceBusinessContractInfo.recePactBeginDate);//合同/日
                    $("input[name=recePactEndDate]").val(mfReceBusinessContractInfo.recePactEndDate);//合同到期日
                    // $("input[name=receEndDate]").val(mfReceBusinessContractInfo.recePactEndDate);//应收账款到期日（默认等于合同到期日）

                });
            }
        });
    };

    //获取应收账款商务合同信息
    var getReceBusContractInfo = function(recePactId,callBack){
        $.ajax({
            url : webPath+"/mfReceBusinessContractInfo/getReceBusContractInfoAjax",
            data:{recePactId:recePactId},
            success:function(data){
                if(data.flag=="success"){
                    if(callBack!==undefined && typeof(callBack) == "function") {
                        callBack(data);
                    }
                }
            }
        });
    };

    //账款登记保存方法
    var _ajaxInsert = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam,appId:appId},
                success:function(data){
                    if(data.flag == "success"){
                        top.addReceInfoFlag=true;
                        top.receId=data.receId;
                        myclose_click();
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        }
    };

    //校验金额大小（转让账款金额<=应收账款金额<=票面金额）
    var  _checkAmt = function(obj){
        //票面金额
        var receInvoiceAmt = $("input[name=receInvoiceAmt]").val().replace(/,/g,"");
        if(receInvoiceAmt==""){
            receInvoiceAmt="0.00";
        }
        //应收账款金额（应收账款金额<=票面金额）
        var receAmt = $("input[name=receAmt]").val().replace(/,/g,"");
        if(receAmt==""){
            receAmt="0.00";
        }
        if(parseFloat(receInvoiceAmt)<parseFloat(receAmt)){
            alert(top.getMessage("NOT_FORM_TIME",{"timeOne":$("input[name=receAmt]").attr("title"),"timeTwo":$("input[name=receInvoiceAmt]").attr("title")}),0);
            $("input[name=receAmt]").val($("input[name=receInvoiceAmt]").val());
        }

        //转让金额（转让金额<=应收账款金额）
        var receTransAmt=$("input[name=receTransAmt]").val().replace(/,/g,"");
        if(receTransAmt==""){
            receTransAmt="0.00";
        }
        if(parseFloat(receAmt)<parseFloat(receTransAmt)){
            alert(top.getMessage("NOT_FORM_TIME",{"timeOne":$("input[name=receTransAmt]").attr("title"),"timeTwo":$("input[name=receAmt]").attr("title")}),0);
            $("input[name=receTransAmt]").val($("input[name=receAmt]").val());
        }

    };

    //计算账款到期日
    var _calcReceEndDate =function(){
        var beginDate =  $("input[name=receBeginDate]").val();
        //开始日期选择后，默认带出签约日期
        var term = $("input[name=term]").val();
        var termType = $("[name=termType]").val();
        $.ajax({
            url:webPath+"/mfReceBusinessContractInfo/getRecePactEndDateAjax",
            data:{"recePactBeginDate":beginDate,"term":term,"termType":termType},
            success:function(data){
                if(data.flag == "success"){
                    var receEndDate=data.recePactEndDate;
                    $("input[name=receEndDate]").val(receEndDate);
                    //选择合同开始日后，清除合同结束日中的不能为空提示
                    $("input[name=receEndDate]").parent().find(".error.required").remove();
                }else{
                    window.top.alert(data.msg,0);
                }
            },error:function(){
                window.top.alert(top.getMessage("FAILED_OPERATION",""),0);
            }
        });
    };

    return {
        init : _init,
        ajaxInsert:_ajaxInsert,
        checkAmt : _checkAmt,
        calcReceEndDate: _calcReceEndDate,
    };
}(window, jQuery);