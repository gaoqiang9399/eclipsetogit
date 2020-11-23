;
var MfBusFincAppMain_InsertBatchForBiz = function(window, $) {
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

        //初始化出账明细列表复选框的选择事件
        _initFincAppListCheckBox();
    };

    //出账明细新增
    var _fincAppInput = function(){
        var usableFincAmt =  $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val().replace(/,/g, "");
        top.putoutAmt=usableFincAmt;
        top.fincAppFlag = false;
        top.htmlStr = $("#fincAppList").html();
        top.openBigForm(webPath+"/mfBusFincApp/inputForBatch?appId="+appId+"&pactId="+pactId+"&fincMainId="+fincMainId,'明细新增',function() {
            if(top.fincAppFlag){
                $("#fincAppList").html(top.htmlStr);
                usableFincAmt =top.usableFincAmt ;
                $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val(fmoney(usableFincAmt,2));
            }
        });
    }




    //放款申请保存方法
    var _ajaxInsert = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            //放款金额
            var putoutAmt = "0.00";
            $("#fincAppList tbody tr").each(function(){
                var $thisTr = $(this);
                var thisPutoutAmt = $thisTr.find("input[name=putoutAmt]").val().replace(/,/g, "");
                putoutAmt = parseFloat(putoutAmt)+parseFloat(thisPutoutAmt);
            });
            //规则验证
            var ruleCusNo = $("input[name=cusNo]").val();
            var rulePutoutAmt =putoutAmt;
            var parmData = {'nodeNo':nodeNo, 'relNo': ruleCusNo, 'cusNo': ruleCusNo,'putoutAmt':rulePutoutAmt};
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
                        alert(top.getMessage("CONFIRM_OPERATION","放款申请提交"),2,function(){
                            saveReceFincAppInfo(obj);
                        });
                    }
                },error : function() {
                }
            });
        }
    };

    var saveReceFincAppInfo = function(obj){
        var repayPlanMergeCnt=0;
        $("#fincAppList tbody tr").each(function(){
            var $thisTr = $(this);
            var $checkbox = $thisTr.find("input[type=checkbox]");
            if($checkbox.is(':checked')){
                repayPlanMergeCnt = parseInt(repayPlanMergeCnt) +1;
            }
        });
        if(parseInt(repayPlanMergeCnt)==1){
            DIALOG.error(top.getMessage("NOT_EQUAL_TIME",{"timeOne":"还款计划合并条数","value":"1条"}));
        }else{
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam,pactId:pactId,fincMainId:fincMainId},
                success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(data.msg, 3);
                        top.flag=true;
                        top.putoutSaveFlag=true;
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

    //编辑出账明细信息
    var _updateFincApp = function (obj,url) {
        var usableFincAmt =  $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val().replace(/,/g, "");
        top.usableFincAmt=usableFincAmt;
        top.fincAppFlag = false;
        top.htmlStr = $("#fincAppList").html();
        top.openBigForm(webPath+url,'明细修改',function() {
            if(top.fincAppFlag){
                $("#fincAppList").html(top.htmlStr);
                usableFincAmt =top.usableFincAmt ;
                $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val(fmoney(usableFincAmt,2));
                _initFincAppListCheckBox();
            }
        });
    };
    //删除出账明细信息
    var _deleteFincApp = function (obj,url) {
       DIALOG.confirm(top.getMessage("CONFIRM_DELETE"),function () {
           jQuery.ajax({
               url:url,
               data:{pactId:pactId},
               success:function(data){
                   if(data.flag == "success"){
                       $("#fincAppList").html(data.htmlStr);
                       var usableFincAmt =data.usableFincAmt ;
                       var planPutoutCnt = data.planPutoutCnt;
                       $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val(fmoney(usableFincAmt,2));
                       $("#batchFincAppInsertForm").find("input[name=planPutoutCnt]").val(planPutoutCnt);

                   }else{
                       window.top.alert(data.msg, 0);
                   }
               },error:function(data){
                   alert(top.getMessage("FAILED_OPERATION"," "),0);
               }
           });
       });
    };

    var _generateFincAppList = function(obj){
        //验证可支用金额和计划放款笔数
        var pactUsableFincAmt = $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val();
        if (parseFloat(pactUsableFincAmt) <= 0) {
            alert("可支用金额必须大于0！", 0);
            return;
        }
        var planPutoutCnt = $("#batchFincAppInsertForm").find("input[name=planPutoutCnt]").val();
        if(planPutoutCnt=="" || parseInt(planPutoutCnt)==0){
            alert("请输入计划放款笔数！", 0);
            return;
        }
        if (parseInt(planPutoutCnt) <= 0) {
            alert("可支用金额必须大于0！", 0);
            return;
        }
        DIALOG.confirm(top.getMessage("CONFIRM_OPERATION","出账明细生成"),function () {
            var dataParam = JSON.stringify($("#batchFincAppInsertForm").serializeArray());
            jQuery.ajax({
                url:webPath+"/mfBusFincAppMain/generateFincAppListAjax",
                data:{ajaxData:dataParam,pactId:pactId,fincMainId:fincMainId},
                success:function(data){
                    if(data.flag == "success"){
                        $("#fincAppList").html(data.htmlStr);
                        var usableFincAmt =data.usableFincAmt ;
                        $("#batchFincAppInsertForm").find("input[name=usableFincAmt]").val(fmoney(usableFincAmt,2));

                        $("#batchFincAppInsertForm").find("input[name=planPutoutCnt]").val(data.planPutoutCnt);// 回写'计划放款笔数', 避免页面填写不规范
                        _initFincAppListCheckBox();
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        });

    };

    //初始化出账明细列表复选框的选择事件
    var _initFincAppListCheckBox = function () {
        //初始化选中状态
        $("#fincAppList tbody tr input[type=checkbox]").each(function(){
            var trObj = $(this).parents("tr");
            var repayPlanMergeFlag =  trObj.find("input[name=repayPlanMergeFlag]").val();
            if(repayPlanMergeFlag=="1"){
                $(this).prop('checked',true);
            }
        });
        //绑定click事件
        $("#fincAppList tbody tr").on('click', 'input[type=checkbox]', function(){
           var  $this = $(this);
            var repayPlanMergeFlag="0";
            if($this.prop("checked")){//如果是选中
                repayPlanMergeFlag="1";
            }
            var thisFincId = $this.parents('tr').find("input[name=fincId]").val();
            jQuery.ajax({
                url:webPath+"/mfBusFincApp/updateRepayPlanMergeFlagAjax",
                data:{repayPlanMergeFlag:repayPlanMergeFlag,fincId:thisFincId,fincMainId:fincMainId},
                success:function(data){
                    if(data.flag == "success"){
                        $this.parents('tr').find("input[name=repayPlanMergeFlag]").val(repayPlanMergeFlag)
                    }else{
                        if($this.prop("checked")){
                            $this.prop("checked",false);
                        }else{
                            $this.prop("checked",true);
                        }
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });


        });
    };

    return {
        init : _init,
        ajaxInsert:_ajaxInsert,
        fincAppInput:_fincAppInput,
        updateFincApp:_updateFincApp,
        deleteFincApp:_deleteFincApp,
        generateFincAppList:_generateFincAppList,
        initFincAppListCheckBox:_initFincAppListCheckBox,
    };
}(window, jQuery);