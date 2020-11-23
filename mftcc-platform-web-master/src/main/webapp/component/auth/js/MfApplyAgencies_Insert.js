;
var MfApplyAgencies_Insert = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
        //_bankInit();
        _agenciesInitNew();
	};
//新增授信额度银行地区初始化
    var _agenciesInitNew = function(){
        var areaNo =  $("input[name=areaNo]").val();
        $("input[name=agenciesName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListAjax?kindNo="+adaptationKindNo+"&areaNo="+areaNo,// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName]").val(sltVal.agenciesName);
                $("input[name=agenciesId]").val(sltVal.agenciesId);
                $("input[name=address]").val(sltVal.address);
                $("input[name=agenciesPhone]").val(sltVal.agenciesButtPhone);
                $("input[name=bankCreditBal]").val(sltVal.creditBal);
                $("input[name=bankNo]").val(sltVal.bankNo);
                $("input[name=bankName]").val(sltVal.bankName);
                $("input[name=areaNo]").val(sltVal.areaNo);
                $("input[name=areaName]").val(sltVal.areaName);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "address" : "地址",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
    }
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
		    var agenciesId = $("input[name='agenciesId']").val();
		    var agenciesName = $("input[name='agenciesName']").val();
           top.applyAgenceisId = agenciesId;
           top.applyAgenceisName = agenciesName;
            myclose_click();
		}
	};
    //新增授信额度银行初始化
    var _bankInit = function(){
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
        if(bankInitFlag==0){
            $("input[name='bankNo']").popupSelection({
                ajaxUrl: webPath + "/mfCusCreditApply/bankInit?cusNo=" + cusNo+"&creditTemplateId=" + creditTemplateId,
                searchOn: false,//启用搜索
                inline: true,//下拉模式
                multiple: false,//单选
                changeCallback: function (obj,elem) {
                    $("input[name='bankName']").val(obj.data("text"));
                    $("input[name='areaNo']").val("");
                    $("input[name='areaNo']").parent().find(".pops-value").text("");
                    $("input[name='areaName']").val("");
                    $("input[name='agenciesId']").val("");
                    $("input[name='agenciesName']").val("");
                    _areaInit();
                }
            })
        }else{

        }

    }
    //新增授信额度银行地区初始化
    var _areaInit = function(){
        var bankNo =  $("input[name=bankNo]").val();
        if(bankNo==""){
            window.top.alert("请先选择银行!",0);
            return ;
        }
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
        if(areaInitFlag==0){
            $("input[name='areaNo']").popupSelection({
                ajaxUrl: webPath + "/mfCusCreditApply/areaInit?cusNo=" + cusNo+"&creditTemplateId=" + creditTemplateId+"&bankNo=" + bankNo,
                searchOn: false,//启用搜索
                inline: true,//下拉模式
                multiple: false,//单选
                changeCallback: function (obj,elem) {
                    $("input[name='areaName']").val(obj.data("text"));
                    $("input[name='agenciesId']").val("");
                    $("input[name='agenciesName']").val("");
                    $("input[name=address]").val("");
                    $("input[name=agenciesPhone]").val("");
                    $("input[name=agenciesName]").parent().find(".pops-value").remove();
                    _agenciesInit();
                }
            })
            areaInitFlag=1;
        }else{
            $.ajax({
                url: webPath + "/mfCusCreditApply/areaInit",
                data:{cusNo:cusNo,creditTemplateId:creditTemplateId,bankNo},
                type:'post',
                dataType:'json',
                success: function (data) {
                    if (data.flag == "success") {
                        $("input[name=popsareaNo]").popupSelection("updateItems",data.items);
                    }
                }
            });
        }
    }
    //新增授信额度银行地区初始化
    var _agenciesInit = function(){
        var areaNo =  $("input[name=areaNo]").val();
        var cusNo = $("input[name=cusNo]").val();
        var creditTemplateId = $("input[name=creditTemplateId]").val();
        $("input[name=agenciesName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgencies/getAgenciesListAjax?kindNo="+adaptationKindNo+"&areaNo="+areaNo,// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName]").val(sltVal.agenciesName);
                $("input[name=agenciesId]").val(sltVal.agenciesId);
                $("input[name=address]").val(sltVal.address);
                $("input[name=agenciesPhone]").val(sltVal.agenciesButtPhone);
                $("input[name=bankCreditBal]").val(sltVal.creditBal);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditBal" : "授信余额",
                "address" : "地址",
                "agenciesButtPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
    }
	return {
		init : _init,
		ajaxSave:_ajaxSave,
	};
}(window, jQuery);
