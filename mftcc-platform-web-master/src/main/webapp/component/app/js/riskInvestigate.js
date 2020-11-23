;
var riskInvestigate = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		bindVouTypeByKindNo($("input[name=vouType]"), kindNo);
		
		if($("input[name=coborrNum]").is(':visible')){
			 $("input[name=coborrNum]").popupSelection({
					searchOn:true,//启用搜索
					inline:false,//下拉模式
					items:jsonStr,//请求数据URL
					multiple:true,//多选
					title:"共同借款人",
					handle:false
			});
		}
		$(".pops-value").unbind("click");
		$(".pops-close").remove();
		//渠道来源选择组件				    
	    if($('input[name=channelSource]').length > 0){
			$('input[name=channelSource]').popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl:webPath+"/mfBusTrench/getChannelAjax",//请求数据URL
				valueElem:"input[name=channelSourceNo]",//真实值选择器
				title: "选择渠道来源",//标题
				labelShow:false,
				tablehead:{//列表显示列配置
					"trenchUid":"渠道编号",
					"trenchName":"渠道名称"
				},
				returnData:{//返回值配置
					disName:"trenchName",//显示值
					value:"trenchUid"//真实值
				}
			});
	    }
		_bindClose();
	};

	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	};
	



	var _insertRiskInvestigate = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			
			var datas = [];
			$("#doc_offline-list").find("tbody tr").each(function(index){
				var entity = {};
				$thisTr = $(this);
				if($thisTr.find("input[type=checkbox]").is(':checked')) {
					entity.docSplitNo= $thisTr.find("input[name=docSplitNo]").val();
					entity.docSplitName = $thisTr.find("input[name=docSplitName]").val();
					entity.docType = $thisTr.find("input[name=docType]").val();
					entity.docTypeName = $thisTr.find("input[name=docTypeName]").val();
					entity.scNo = $thisTr.find("input[name=scNo]").val().replace(/,/g, "");
					datas.push(entity);
				}
			});
			var ajaxDataList = "";
			if(datas.length>0){
				ajaxDataList=JSON.stringify(datas);
			}
			$.ajax({
				url : url,
				data : {ajaxData:dataParam,appId:appId,cusNo:cusNo,ajaxDataList:ajaxDataList},
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						top.flag = true;
						myclose_click();
					}else{
						window.top.alert(data.msg, 0);
					}
				},error : function() {
					alert(top.getMessage("ERROR_SERVER"),0);
				}
			});
				
		}
	};

	/** 客户经理放大镜回调 */
	var _getCusMngNameDialog = function(userInfo) {
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		insertRiskInvestigate:_insertRiskInvestigate,
		getCusMngNameDialog : _getCusMngNameDialog
	};
}(window, jQuery);