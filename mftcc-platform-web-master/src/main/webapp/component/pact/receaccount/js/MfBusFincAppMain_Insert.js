;
var MfBusFincAppMain_Insert = function(window, $) {
    var _init = function () {
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });

        MfBusBankAccCom.bankAccInit();

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

        //账款列表融资到期日绑定onClick事件
        $("#receList tbody tr").on('click', 'input[name=intstEndDate]', function(){
            var trObj = $(this).parents('tr');
            var receEndDate = trObj.find(".receEndDate").text()
            fPopUpCalendarDlg({isclear : false,min:today,max: receEndDate});
        });

        $("#receList tbody tr").on('keyup', 'input[name=fincAmt]', function(){
            var fincAmt=$(this).val();
            var reg=/^(0\.?\d{0,2}|[1-9]\d*\.?\d{0,2})$/;
            if(!reg.test(fincAmt)) {
                $(this).val("");
            }
        });
        //账款列表绑定onblur事件，用来校验大小
        $("#receList tbody tr").on('blur', 'input[name=fincAmt]', function(){
            var trObj = $(this).parents('tr');
            var fincAmt = $(this).val();
            var usableFincAmt = trObj.find("input[name=usableFincAmt]").val().replace(/,/g, "");
            if(parseFloat(usableFincAmt)<parseFloat(fincAmt)){
                $(this).val("");
                window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"账款申请金额" , "timeTwo": "账款可融资金额"}), 3);
            }else{
                var fincAmtSum = 0.00;
                $("#receList tbody tr").each(function(){
                    var fincAmtObj = $(this).find("input[name=fincAmt]");
                    fincAmtSum = fincAmtSum*1 + fincAmtObj.val()*1;
                });
                usableFincAmt = $("form input[name=usableFincAmt]").val().replace(/,/g, "")
                if(parseFloat(usableFincAmt)<parseFloat(fincAmtSum)){
                    window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"融资申请金额" , "timeTwo": "可融资金额"}), 3);
                    $(this).val("");
                    return false;
                }
                // $("input[name=putoutAmt]").val(fincAmtSum);
            }
        });


    };
    //放款申请保存方法
    var _ajaxInsert = function(obj){
        if (parseFloat($("[name=putoutAmt]").val()) <= 0) {
            alert("申请金额必须大于0！", 0);
            return;
        }

        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            //规则验证
            var ruleCusNo = $("input[name=cusNo]").val();
            var rulePutoutAmt = $("input[name=putoutAmt]").val().replace(/,/g, "");
            var parmData = {'nodeNo':'putout_apply', 'relNo': ruleCusNo, 'cusNo': ruleCusNo,'putoutAmt':rulePutoutAmt};
            $.ajax({
                url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
                data : {ajaxData : JSON.stringify(parmData)},
                success : function(data) {
                    if (data.exsitRefused == true) {// 存在业务拒绝
                             top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+ ruleCusNo, '风险拦截信息', function() {
                        });
                    } else if (data.exsitFX == true) {//存在风险项
                        alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {"content" : data.fxlist,"operation" : "放款申请"}), 2, function() {
                            saveReceFincAppInfo(obj);
                        });
                    } else {
                        alert(top.getMessage("CONFIRM_OPERATION","融资申请提交"),2,function(){
                            saveReceFincAppInfo(obj);
                        });
                    }
                },error : function() {
                }
            });
        }
    };

    var saveReceFincAppInfo = function(obj){
        var datas = [];
        var receFincAmtSum = 0.00;
        $("#receList tbody tr").each(function(){
            $thisTr = $(this);
            var fincAmt = $thisTr.find("input[name=fincAmt]").val();
            if(fincAmt!="" &&　parseFloat(fincAmt)>0) {
                receFincAmtSum = receFincAmtSum*1 +fincAmt*1;
            }
            var entity = {};
            var $checkbox = $thisTr.find("input[type=checkbox]");
            if($checkbox.is(':checked')){
                if(fincAmt!="" &&　parseFloat(fincAmt)>0) {
                    entity.transId = $thisTr.find("input[name=transId]").val();
                    entity.putoutAmt = fincAmt;
                    entity.intstEndDate = $thisTr.find("input[name=intstEndDate]").val().replace(/-/g, "");
                    datas.push(entity);
                }
            }
        });

        //校验账款融资总额与手动填写的申请总额是否一直
        var putoutAmt =$("#receFincAppInsertForm input[name=putoutAmt]").val().replace(/,/g, "");
        if(parseFloat(receFincAmtSum) !=parseFloat(putoutAmt)){
            window.top.alert(top.getMessage("NOT_MONEY_SAME",{"money1":"账款融资总额" , "money2": "表单申请金额"}), 3);
            return false;
        }
        var url = $(obj).attr("action");
        var dataParam = JSON.stringify($(obj).serializeArray());
        jQuery.ajax({
            url:url,
            data:{ajaxData:dataParam,receFincList:JSON.stringify(datas),pactId:pactId,confirmId:confirmId},
            success:function(data){
                if(data.flag == "success"){
                    window.top.alert(data.msg, 3);
                    var fincMainId = data.fincMainId;
                    window.location.href = webPath+"/mfBusFincAppMain/getSummary?pactId="+pactId+"&fincMainId="+fincMainId+"&busEntrance=rece_finc";

                }else{
                    window.top.alert(data.msg, 0);
                }
            },error:function(data){
                alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    };

    //处理融资申请金额（按照账款到期日的升序顺序，根据账款的可融资金额瓜分融资申请金额）
    var _dealPutoutAmt = function(obj){
        var putoutAmt =$(obj).val().replace(/,/g, "");
        var usableFincAmt =$("#receFincAppInsertForm input[name=usableFincAmt]").val().replace(/,/g, "");
        $("#receList tbody tr input[type=checkbox]").prop("checked",false);
        $("#receList tbody tr input[name=fincAmt]").val("0.00");
        if(parseFloat(usableFincAmt)<parseFloat(putoutAmt)){
            $(obj).val("0.00");
            window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"融资申请金额" , "timeTwo": "可融资金额"}), 3);
        }else{
            var fincAmtSum = putoutAmt;
            $("#receList tbody tr").each(function(){
               if(parseFloat(fincAmtSum)>0){
                   var $thisTr = $(this);
                   var receUsablefincAmt = $thisTr.find("input[name=usableFincAmt]").val().replace(/,/g, "");
                   if(parseFloat(fincAmtSum)>=parseFloat(receUsablefincAmt)){//如果总的申请金额没有被分配完，该账款的融资金额就是其可融资金额
                       $thisTr.find("input[name=fincAmt]").val(receUsablefincAmt);
                       fincAmtSum =parseFloat(fincAmtSum)-parseFloat(receUsablefincAmt);
                       fincAmtSum = fincAmtSum.toFixed(2);
                   }else{//如果剩余的申请金额不够分配了，直接将剩余的赋值给该账款
                       $thisTr.find("input[name=fincAmt]").val(fincAmtSum);
                       fincAmtSum=0.00;
                   }
                   $thisTr.find("input[type=checkbox]").prop("checked",true);
               }
            });
        }
    }

    //取消回退至列表页面
    var _cancelBack = function(){
        window.location.href = webPath+"/mfBusFincAppMain/getListPage?entranceNo=15";
    };

    return {
        init : _init,
        ajaxInsert:_ajaxInsert,
        dealPutoutAmt:_dealPutoutAmt,
        cancelBack:_cancelBack,
    };
}(window, jQuery);