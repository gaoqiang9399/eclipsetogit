var MfCusPersonCorp=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		var  loanUseItems=null; 
		$.ajax({
				url : webPath+"/mfCusCorpBaseInfo/getLoanUseAjax",
				data : "",
				type : 'post',
				dataType : 'json',
				async:false,
				success : function(data) {
					loanUseItems=data.loanUse;
				},
				error : function() {
				}
			});
		$("select[name=wayClass]").popupSelection({
			searchOn : true,//启用搜索
			multiple : false,//单选
			valueClass : "show-text",//自定义显示值样式
			items:loanUseItems,
			ztree : true,
			ztreeSetting : setting,
			title : "行业分类",
			handle : BASE.getIconInTd($("input[name=wayClass]")),
			changeCallback : function (elem) {
			var areaNo=elem.data("values").val();
				var node = elem.data("treeNode");
				var parNode =  node.getParentNode();
			
				BASE.removePlaceholder($("input[name=wayClass]"));
			}
		});
		$("select[name=corpNature]").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : false//单选
		});
		$("select[name=registeredType]").popupSelection({
			searchOn : true,//启用搜索
			inline : true,//下拉模式
			multiple : false//单选
		});
		//企业名称新版选择组件
		$('input[name=corpNameHidden]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfCusPersonCorp/findCustomerByPageAjax",//请求数据URL
			handle:BASE.getIconInTd($("input[name=corpName]")),//其他触发控件
			valueElem:"input[name=corpCusNo]",//真实值选择器
			title: "选择企业客户",//标题
			changeCallback:function(elem){//回调方法
				$('input[name=corpName]').val($('input[name=corpNameHidden]').val());
				$('input[name=corpName]').parent().find(".error").remove();
				$('.hidden-content').find('div[class=pops-value]').hide();
				_getCusPersonCorpInfo(elem.data("values").val());
			},
			tablehead:{//列表显示列配置
				"cusNo":{"disName":"客户编号","align":"center"},
				"cusName":{"disName":"客户名称","align":"center"}
			},
			returnData:{//返回值配置
				disName:"cusName",//显示值
				value:"cusNo"//真实值
			}
		});
	};
	var  _getCusPersonCorpInfo = function(corpCusNo){
		$.ajax({
			url : webPath+"/mfCusPersonCorp/getCusPersonCorpInfoAjax?cusNo="+corpCusNo,
			type : 'post',
			dataType : 'json',
			async:false,
			success : function(data) {
				if(data.flag=="success"){
					var cusPersonCorpInfo=data.mfCusPersonCorp;
					$("input[name=idNum]").val(cusPersonCorpInfo.idNum);
					$("input[name=corpNature]").val(cusPersonCorpInfo.corpNature);
					var corpNatureName=$("input[name=corpNature]").parent().find("li[data-id='"+cusPersonCorpInfo.corpNature+"']").find('span').html()
					if(corpNatureName==undefined){
						$("input[name=corpNature]").parent().find(".pops-value").html("");
					}else{
						$("input[name=corpNature]").parent().find(".pops-value").html(corpNatureName);
					}
					$("input[name=registeredType]").val(cusPersonCorpInfo.registeredType);
					var registeredTypeName=$("input[name=registeredType]").parent().find("li[data-id='"+cusPersonCorpInfo.registeredType+"']").find('span').html()
					if(registeredTypeName==undefined){
						$("input[name=registeredType]").parent().find(".pops-value").html("");
					}else{
						$("input[name=registeredType]").parent().find(".pops-value").html(registeredTypeName);
					}
					$("input[name=wayClass]").val(cusPersonCorpInfo.wayClass);
					$("input[name=wayClass]").parent().find(".pops-label-alt").html(cusPersonCorpInfo.wayClassName);
					//行业分类
                    $("input[name=wayClassDes]").val(cusPersonCorpInfo.wayClassName);
					$("input[name=registeredCapital]").val(cusPersonCorpInfo.registeredCapital);
					$("input[name=address]").val(cusPersonCorpInfo.address);
					$("input[name=commAddress]").val(cusPersonCorpInfo.commAddress);
					$("textarea[name=wayArea]").val(cusPersonCorpInfo.wayArea);
					$("textarea[name=remark]").val(cusPersonCorpInfo.remark);
					$("input[name=beginDate]").val(cusPersonCorpInfo.beginDate);
					$("input[name=endDate]").val(cusPersonCorpInfo.endDate);
					
				}
			},
			error : function() {
			}
		});
	}
	var  _insertCusPersonCorpBase = function(obj) {
		var unVal = $("input[name=idNum]").val();
		var column = $("input[name=idNum]").attr("title");
		var relationId = $("input[name=cusNo]").val();
		var corpCusNo = $("input[name=corpCusNo]").val();
		var result = checkUniqueVal(unVal, column, relationId, "MfCusCustomer", "01", "insert",corpCusNo);
		var checkFlag = result.split("&")[0];
		result = result.split("&")[1];
		top.baseInfo = false;
		if (checkFlag == "1") {
			window.top.alert(result, 1);
		} else {
			ajaxInsertCusForm(obj);
		}

	}
    var  _insertCusPersonCorpBaseAndAdd = function(obj) {
        var unVal = $("input[name=idNum]").val();
        var column = $("input[name=idNum]").attr("title");
        var relationId = $("input[name=cusNo]").val();
        var corpCusNo = $("input[name=corpCusNo]").val();
        var result = checkUniqueVal(unVal, column, relationId, "MfCusCustomer", "01", "insert",corpCusNo);
        var checkFlag = result.split("&")[0];
        result = result.split("&")[1];
        top.baseInfo = false;
        if (checkFlag == "1") {
            window.top.alert(result, 1);
        } else {
            var cusNo = $("input[name='cusNo']").val();
            var inputUrl = webPath+"/mfCusPersonCorp/input?cusNo="+cusNo;
            ajaxInserAndAddCusForm(obj,inputUrl);
        }

    }
	return{
		init:_init,
		insertCusPersonCorpBase:_insertCusPersonCorpBase,
        insertCusPersonCorpBaseAndAdd:_insertCusPersonCorpBaseAndAdd,

	};
}(window, jQuery);
selectWayClassCallBack=function (waycls) {
	$("input[name=wayClassName]").val(waycls.disName);
	$("input[name=wayClassName]").parent().parent().find(".fieldShow").html(waycls.disName);
	$("input[name=wayClass]").val(waycls.disNo);
};
//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data){
	if(key=="careaCity"){
		data["careaProvice"] = $("input[name=careaProvice]").val();//存储最终的编号
	}else if(key=="wayClassName"){
		data["wayClass"] = $("input[name=wayClass]").val();
		data["wayMaxClass"] = $("input[name=wayMaxClass]").val();
	}
}

//行业分类选择后的回调处理
function nmdWaycCallBack(nmdWayInfo){
    //$("input[name=wayClass]").val(nmdWayInfo.wayName);
    $("input[name=wayClass]").val(nmdWayInfo.wayNo);
    $("input[name=wayClassDes]").val(nmdWayInfo.wayName);
};
