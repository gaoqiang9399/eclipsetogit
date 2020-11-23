;
var MfCusCreditConfig_Insert = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
        //部门新组件
        $("input[name=startupBrNo]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            ztree:true,
            parentSelect : true,//选择父节点
            ztreeSetting : ztreeSetting,
            title:"开办部门",
            items:ajaxData.org,
            changeCallback : function (obj, elem) {
            },
        });
        //角色新组件
        $("input[name=startupRoleNo]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            items:ajaxData.role,
            title:"开办角色",
            labelShow: false,
            changeCallback : function (obj, elem) {
            },
        });
        //产品新组件
        $("input[name=adaptationKindNo]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            items:ajaxData.sysKind,
            title:"适配产品",
            labelShow: false,
            changeCallback : function (obj, elem) {
            },
        });
        //客户新组件
        $("[name=startupCusType]").popupSelection({
            searchOn:false,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            title:"客户类型",
            labelShow: false,
            items:ajaxData.cusType,
            changeCallback : function (obj, elem) {
            },
        });
        // 初始化授信流程
        $("input[name=templateCredit]") .popupSelection({
            searchOn : true,// 启用搜索
            inline : true,// 下拉模式
            multiple : false,// 多选
            items : ajaxData.templateCredit,
            changeCallback : function( obj, elem) {
                var templateCredit = $(obj).val();
                $("[name=templateCreditName]").val($("[name=templateCredit]").prev().text());
            }
        });
        // 初始化适配授信流程
        $("input[name=adaptationCreditId]") .popupSelection({
            searchOn : true,// 启用搜索
            inline : false,// 下拉模式
            multiple : true,// 多选
            items : ajaxData.apaCreditKind,
            changeCallback : function( obj, elem) {
                var templateCredit = $(obj).val();
                $("[name=adaptationCreditIdName]").val($("[name=adaptationCreditId]").prev().text());
            }
        });
        //业务模式 20200225
        $("input[name=busModel]").popupSelection({
            searchOn : true,//启用搜索
            inline : false,//下拉模式
            multiple : false,//单选
            items:ajaxData.busModel,
            valueClass:"show-text",
            title:"业务模式",
            labelShow: false,
            changeCallback : function () {
            },
        });
	};
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
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
                        top.creditConfigAddFlag = true;
                        top.creditId = data.mfCusCreditConfig.creditId;
                        top.creditModel = data.mfCusCreditConfig.creditModel;
                        top.creditName = data.mfCusCreditConfig.creditName;
                        top.remark = data.mfCusCreditConfig.remark;
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	var _getCreditConfigList = function(){
		var addOrCopy = $("[name='addOrCopy']").val();
		if(addOrCopy != null && addOrCopy == "2"){
            var creditModel = $("[name='creditModel']").val();
            $.ajax({
                url : webPath + "/mfCusCreditConfig/getCreditConfigList",
                data : {
                    creditModel:creditModel
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        $("[name=popstemplateCredit]").popupSelection("updateItems",data.templateCredit);
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                    alert("获取授信流程配置列表失败", 0);
                }
            });
        }

	};

    //ztree 选择设置
    var ztreeSetting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "" }
        },
        data:    {
            simpleData:{
                enable:true
            }
        },
    }
	return {
		init : _init,
		ajaxSave:_ajaxSave,
        getCreditConfigList:_getCreditConfigList
	};
}(window, jQuery);
