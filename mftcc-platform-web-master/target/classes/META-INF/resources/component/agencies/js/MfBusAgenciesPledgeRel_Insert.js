;
var MfBusAgenciesPledgeRel_Insert = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        _agenciesInitList();
        _pledgeInitList();
        _assureInitList();
	};
	
	var _submitForm = function(obj) {
		var dataParam = JSON.stringify($(obj).serializeArray());
        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var collateralId = $("input[name=collateralId]").val();
            if(collateralId==null||collateralId==''){
                alert("押品ID不能为空", 0);
                return;
            }
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam ,appId:appId},
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        top.flag = true;
                        myclose_click();
                    }else if (data.flag == "finish") {
                        top.flag = true;
                        myclose_click();
                    } else {
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
            });
        }
	};
	




    //保证人初始化
    var _assureInitList = function(){
        $("input[name=assureName]").popupList({
            searchOn : false, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgenciesPledgeRel/getPledgeListAjax?creditAppId="+appId+"&vouType=2",// 请求数据URL
            valueElem : "input[name=collateralId]",//真实值选择器
            title : "选择保证人",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=assureName]").val(sltVal.assureName);
                $("input[name=idNum]").val(sltVal.idNum);
                $("input[name=collateralId]").val(sltVal.id);
                $("input[name=collateralName]").val(sltVal.assureName);
            },
            tablehead : {//列表显示列配置
                "assureName" : "保证人名称",
                "idNum" : "证件号码"
            },
            returnData : {//返回值配置
                disName : "assureName",//显示值
                value : "id"//真实值
            }
        });
    };

    var _vouTypeChange = function(){
        $("input[name=pledgeName]").val("");
        $("input[name=pledgeShowNo]").val("");
        $("input[name=collateralId]").val("");
        $("input[name=classSecondName]").val("");
        $("input[name=collateralName]").val("");
        $("input[name=pledgeName]").parent().find(".pops-value").remove();
        _pledgeInitList();
    };

    //押品初始化
    var _pledgeInitList = function(){
        var vouType =  $("select[name=vouType]").val();
        $("input[name=pledgeName]").popupList({
            searchOn : false, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgenciesPledgeRel/getPledgeListAjax?creditAppId="+appId+"&vouType="+vouType,// 请求数据URL
            valueElem : "input[name=collateralId]",//真实值选择器
            title : "选择押品",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=pledgeName]").val(sltVal.pledgeName);
                $("input[name=pledgeShowNo]").val(sltVal.pledgeShowNo);
                $("input[name=collateralId]").val(sltVal.pledgeNo);
                $("input[name=classSecondName]").val(sltVal.classSecondName);
                $("input[name=collateralName]").val(sltVal.pledgeName);
            },
            tablehead : {//列表显示列配置
                "pledgeName" : "押品名称",
                "classSecondName" : "押品类别",
                "pledgeShowNo" : "押品编号"
            },
            returnData : {//返回值配置
                disName : "pledgeName",//显示值
                value : "pledgeNo"//真实值
            }
        });
    };
    //合作银行初始化
    var _agenciesInitList = function(){
        $("input[name=agenciesName]").popupList({
            searchOn : false, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusAgenciesPledgeRel/getAgenciesListAjax?creditAppId="+appId,// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=agenciesName]").val(sltVal.agenciesName);
                $("input[name=agenciesId]").val(sltVal.agenciesId);
                $("input[name=address]").val(sltVal.address);
                $("input[name=agenciesPhone]").val(sltVal.agenciesPhone);
                $("input[name=bankName]").val(sltVal.bankName);
                $("input[name=areaName]").val(sltVal.areaName);
            },
            tablehead : {//列表显示列配置
                "agenciesName" : "合作银行",
                "creditAmt" : "授信额度",
                "address" : "地址",
                "agenciesPhone" : "联系人电话"
            },
            returnData : {//返回值配置
                disName : "agenciesName",//显示值
                value : "agenciesId"//真实值
            }
        });
    };

	return {
		init : _init,
		submitForm : _submitForm,
        agenciesInitList:_agenciesInitList,
        vouTypeChange:_vouTypeChange
	};
}(window, jQuery);
