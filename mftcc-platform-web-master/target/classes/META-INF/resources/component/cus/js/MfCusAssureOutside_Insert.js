;
var MfCusAssureOutside_Insert = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
	};
	var _initAssureInfo = function(){
		$("input[name=assureName]").popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl : webPath+"/mfCusAssureOutside/getMfAssureListAjax?cusNo="+cusNo,// 请求数据URL
			valueElem:"input[name='assureNo']",//真实值选择器
			title: "选择人员",//标题
			changeCallback:function(elem){//回调方法
				var tmpData = elem.data("selectData");
				BASE.removePlaceholder($("input[name='assureName']"));
				$("input[name=assureNo]").val(tmpData.assureNo);
				$("input[name=idNum]").val(tmpData.idNum);
			},
			tablehead:{//列表显示列配置
				"assureName":"保证人名称",
				"idNum":"保证人证件号"
			},
			returnData:{//返回值配置
				disName:"assureName",//显示值
				value:"idNum"//真实值
			}
		});
		$('input[name=assureName]').next().click();
	};
	var _updateCallBack = function(){
		top.addFlag = true;
		myclose_click();
	};
	
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var idNum = $("input[name='idNum']").val();
			if(idNum == ''){
				alert(top.getMessage("FIRST_OPERATION","保证人信息的完善"),3);
			}else{
				ajaxInsertCusForm(obj);
			}
		}
	};
	
	return {
		init : _init,
		updateCallBack:_updateCallBack,
		ajaxSave:_ajaxSave,
		initAssureInfo:_initAssureInfo,
		
	};
}(window, jQuery);

