;
var MfBusBreed_Insert = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
        MfBusBreed_Insert.breedBankInit();

	};
	var _initDetail = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
        //MfBusBreed_Insert.breedBankInit();
        var breedAgenciesId = $("input[name='breedAgenciesId']").val();
        var id = $("input[name='id']").val();
        MfBusBreed_Insert.breedInit2(breedAgenciesId,id);
	};

	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
            var breedCreditAmt = $("input[name=breedCreditAmt]").val().replace(/,/g, "");
            var agenciesCreditAmt = $("input[name=agenciesCreditAmt]").val().replace(/,/g, "");
            if (CalcUtil.compare(breedCreditAmt, agenciesCreditAmt) ==1 ) {
                window.top.alert(top.getMessage("NOT_FORM_TIME", {
                    "timeOne" : "业务品种授信额度:",
                    "timeTwo" : "合作银行授信额度:"
                    + CalcUtil.formatMoney(breedCreditAmt, 2)
                }), 0);
            }else{
                if (CalcUtil.compare(breedCreditAmt, creditSum) ==1 ) {
                    window.top.alert(top.getMessage("NOT_FORM_TIME", {
                        "timeOne" : "业务品种授信额度:",
                        "timeTwo" : "授信总额:"
                        + CalcUtil.formatMoney(creditSum, 2)
                    }), 0);


                }else{
                    var url = $(obj).attr("action");
                    var dataParam = JSON.stringify($(obj).serializeArray());
                    $.ajax({
                        url : url,
                        data : {
                            ajaxData:dataParam
                        },
                        type : 'post',
                        dataType : 'json',
                        success : function(data) {
                            if (data.flag == "success") {
                                myclose_click();
                            } else {
                                alert(data.msg, 0);
                            }
                        },
                        error : function() {
                        }
                    });
                }
            }

		}
	};
    var _ajaxUpdate = function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var breedCreditAmt = $("input[name=breedCreditAmt]").val().replace(/,/g, "");
            var agenciesCreditAmt = $("input[name=agenciesCreditAmt]").val().replace(/,/g, "");
            if (CalcUtil.compare(breedCreditAmt, agenciesCreditAmt) ==1 ) {
                window.top.alert(top.getMessage("NOT_FORM_TIME", {
                    "timeOne" : "业务品种授信额度:",
                    "timeTwo" : "合作银行授信额度:"
                    + CalcUtil.formatMoney(breedCreditAmt, 2)
                }), 0);
            }else{
                if (CalcUtil.compare(breedCreditAmt, creditSum) ==1 ) {
                    window.top.alert(top.getMessage("NOT_FORM_TIME", {
                        "timeOne" : "业务品种授信额度:",
                        "timeTwo" : "授信总额:"
                        + CalcUtil.formatMoney(creditSum, 2)
                    }), 0);


                }else{
                    var url = $(obj).attr("action");
                    var dataParam = JSON.stringify($(obj).serializeArray());
                    $.ajax({
                        url : url,
                        data : {
                            ajaxData:dataParam
                        },
                        type : 'post',
                        dataType : 'json',
                        success : function(data) {
                            if (data.flag == "success") {
                                myclose_click();
                            } else {
                                alert(data.msg, 0);
                            }
                        },
                        error : function() {
                        }
                    });
                }
            }

        }
    };
    //业务品种界面合作银行初始化
    var _breedBankInit = function(){
        $.ajax({
            url:  webPath + "/mfCusCreditApply/breedBankInitForEdit",
            data:{creditAppId:creditAppId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("input[name='breedAgenciesId']").popupSelection({
                        searchOn: false,//启用搜索
                        inline: true,//下拉模式
                        multiple: false,//多选
                        items : data.items,
                        changeCallback: function (obj,elem) {
                            $("input[name='breedAgenciesName']").val(obj.data("text"));
                            var breedAgenciesId = $("input[name='breedAgenciesId']").val()
                            var  agenciesList = data.items;
                            for (var index in agenciesList){
                                if(breedAgenciesId==agenciesList[index].id){
                                    $("input[name='agenciesCreditAmt']").val(agenciesList[index].bankCreditAmt);
                                }
                            }
                            MfBusBreed_Insert.breedInit(breedAgenciesId);
                        }
                    })
                }
            }
        });
    }
    //新增业务品种初始化
    var _breedInit = function(breedAgenciesId){
        $.ajax({
            url:  webPath + "/mfCusCreditApply/breedInitForEdit",
            data:{kindNo:adaptationKindNo,creditAppId:creditAppId,agenciesId:breedAgenciesId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    if(breedInitFlag==0){
                        $("input[name='breedNo']").popupSelection({
                            searchOn: false,//启用搜索
                            inline: true,//下拉模式
                            multiple: true,//多选
                            items : data.items,
                            changeCallback: function (elem) {
                                var breedNos = $("input[name='breedNo']").val();
                                $.ajax({
                                    url: webPath + "/mfCusCreditApply/getNameByBreedNo",
                                    data:{breedNos:breedNos},
                                    type:'post',
                                    dataType:'json',
                                    success: function (data) {
                                        if (data.flag == "success") {
                                            $("input[name='breedName']").val(data.breedName)
                                        }
                                    }
                                });
                            }
                        })
                    }else{
                        $("input[name=popsbreedNo]").popupSelection("updateItems",data.items);
                    }

                }
            }
        });
    }
//新增业务品种初始化
    var _breedInit2 = function(breedAgenciesId,id){
        $.ajax({
            url:  webPath + "/mfCusCreditApply/breedInitForEdit",
            data:{kindNo:adaptationKindNo,creditAppId:creditAppId,agenciesId:breedAgenciesId,id:id},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    if(breedInitFlag==0){
                        $("input[name='breedNo']").popupSelection({
                            searchOn: false,//启用搜索
                            inline: true,//下拉模式
                            multiple: true,//多选
                            items : data.items,
                            changeCallback: function (elem) {
                                var breedNos = $("input[name='breedNo']").val();
                                $.ajax({
                                    url: webPath + "/mfCusCreditApply/getNameByBreedNo",
                                    data:{breedNos:breedNos},
                                    type:'post',
                                    dataType:'json',
                                    success: function (data) {
                                        if (data.flag == "success") {
                                            $("input[name='breedName']").val(data.breedName)
                                        }
                                    }
                                });
                            }
                        })
                    }else{
                        $("input[name=popsbreedNo]").popupSelection("updateItems",data.items);
                    }

                }
            }
        });
    }
	return {
		init : _init,
		ajaxSave:_ajaxSave,
        ajaxUpdate:_ajaxUpdate,
        breedBankInit:_breedBankInit,
        breedInit:_breedInit,
        initDetail:_initDetail,
        breedInit2:_breedInit2,
	};
}(window, jQuery);
