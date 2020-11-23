;
var mfRequestPayoutBillInsert=function(window,$){
	var _init=function(){
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        //押品新组件
        $("input[name=assetType]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,//下拉模式
            multiple: false,//多选选
            items: collClass,
            changeCallback: function (obj, elem) {
                $("input[name=assetType]").val(obj.data("text"));
                $("input[name=classId]").val($(obj).val());
            }
        });
        if(requestState == null||requestState==''){
            $("#fundsadd").bind("click", function(event){
                window.top.alert('请先保存基本信息！');
            });
        }else if(requestState=='0') {
            $("#fundsadd").bind("click", function(event){
                var url = webPath + "/mfRequestPayoutBill/inputDetail?requestId=" + requestId;
                top.fundsFlag = false;
                top.openBigForm(url,"请款条例明细", function(){
                    if(top.fundsFlag){
                        $("#requestPayoutFunds").html(top.detailTableHtml);
                        $("input[name=payoutTotalAmount]").val(top.payoutTotalAmount);
                    }
                },"80","80");
            });
        }else{
            $("#fundsadd").css({display:"none"});
        }

        $("input[name=assetType]").parents("td").find(".pops-value").html($("input[name=assetType]").val());

        //初始化关联请款单
        $("input[name=relationRequestId]") .popupSelection({
            searchOn : false,// 启用搜索
            inline : true,// 下拉模式
            multiple : false,// 单选
            items : relationRequestIdMap,
            changeCallback : function(obj, elem) {
            // 发送请求，更新表单信息
                LoadingAnimate.start();
                var relationRequestId = $(obj).val();
                jQuery.ajax({
                    url : webPath+"/mfRequestPayoutBill/getInfoById",
                    data : {
                        requestId : requestId, relationRequestId : relationRequestId,
                    },
                    type : "POST",
                    dataType : "json",
                    beforeSend : function() {
                    },
                    success : function(data) {
                        LoadingAnimate.stop();
                        if (data.flag == "success") {
                            var info = data.mfRequestPayoutBillInfo;
                            $("input[name=relationCusNo]").val(info.relationCusNo);
                            $("input[name=cusName]").val(info.cusName);
                            $("input[name=assetName]").val(info.assetName);
                            $("input[name=assetType]").val(info.assetType);
                            //设置div的值
                            $("input[name=assetType]").parents("td").find(".pops-value").html($("input[name=assetType]").val());
                            $("input[name=payoutTotalAmount]").val(info.payoutTotalAmount);

                            //解绑点击事件
                            $("input[name=cusName]")[0].onclick = function(){};
                            // $("input[name=assetType]")[0].onclick = function(){};
                            $("input[name=assetType]").parents("td").find(".pops-value").unbind();

                            $("input[name=assetName]").attr("readonly","readonly");

                        }else if (data.flag == "error") {
                        }
                    },
                    error : function(data) {
                        LoadingAnimate.stop();
                        alert(top.getMessage("FAILED_OPERATION"," "), 0);
                    }
                });
            }
        });

        /*//合同组件
        $("input[name=pactId]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//多选选
            items:ajaxOverduePactData.overduePact,
            changeCallback : function (obj, elem) {
                _appNameChange(obj);
            }
        });
        //客户新组件
        $("#pledgeBaseInfoInsert").find("input[name=cusNo]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//多选选
        });
        $("#pledgeBaseInfoInsert").find("input[name=cusNo]").parents(".input-group").find(".pops-select").remove();*/
	};
	//保存请款申请信息并提交审批
	var _saveRequestPayoutBillAjax=function(obj){
		var appFlag = submitJsMethod($(obj).get(0), '');
		if (appFlag) {
            var url = $(obj).attr("action");
            var appDataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
                    ajaxData : appDataParam,
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
                        window.top.alert(data.msg, 3);
                        myclose_click();
					}else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
    //保存基本信息
    var _saveRequestPayoutBaseAjax=function(obj){
        var appFlag = submitJsMethod($(obj).get(0), '');
        if (appFlag) {
            var url = webPath+"/mfRequestPayoutBill/saveBaseAjax";
            var appDataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxData : appDataParam,
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.flag=true;
                        window.top.alert(data.msg, 3);
                        $("#requestSaveBtn").css({display:"none"});
                        $("#requestSubmitBtn").css({display:"block"});
                        //唤醒点击事件
                        $("#fundsadd").unbind();        //先解绑点击事件
                        $("#fundsadd").bind("click", function(event){
                           var url = webPath + "/mfRequestPayoutBill/inputDetail?requestId=" + requestId;
                           top.fundsFlag = false;
                           top.openBigForm(url,"请款条例明细", function(){
                               if(top.fundsFlag){
                                   $("#requestPayoutFunds").html(top.detailTableHtml);
                                   $("input[name=payoutTotalAmount]").val(top.payoutTotalAmount);
                               }
                           },"80","80");
                        });
                        //myclose_click();
                    }else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    };
	//搜索客户
    var _selectCusDialog=function(){
        selectCusDialog(_selectCusBack,"","","1");
    };
    //选择客户回调
    var _selectCusBack=function(cus){
        cusNo=cus.cusNo;
        $("input[name=relationCusNo]").val(cusNo);
        $("input[name=cusName]").val(cus.cusName);
        // _selectRelationRequestId();
    };
    //初始化关联请款单编号
    var _selectRelationRequestId=function(){
        $.ajax({
            url : webPath + "/mfRequestPayoutBill/getRelationRequestIdList?relationCusNo="+ cusNo,
            data : {},
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    $("input[name=relationRequestId]") .popupSelection({
                        searchOn : false,// 启用搜索
                        inline : true,// 下拉模式
                        multiple : false,// 单选
                        items : data.relationRequestIdMap,
                        changeCallback : function( obj, elem) {

                        }
                    });
                }
            },
            error : function() {
            }
        });
    }
	return{
		init:_init,
        saveRequestPayoutBillAjax:_saveRequestPayoutBillAjax,
        saveRequestPayoutBaseAjax:_saveRequestPayoutBaseAjax,
        selectCusDialog:_selectCusDialog,
	};
}(window,jQuery);