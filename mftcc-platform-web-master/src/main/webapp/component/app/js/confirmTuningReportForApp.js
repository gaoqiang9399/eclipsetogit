;
var confirmTuningReport = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		if($("input[name=vouType]").is(':visible')){
			 $("input[name=vouType]").popupSelection({
					searchOn:true,//启用搜索
					inline:false,//下拉模式
					multiple:true,//多选
					items:vouType,
					title:"担保方式",
					handle:false
			});
		}
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
		
		$('input[name=manageOpName2]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/sysUser/findSameDownBrAndRoleByPageAjax",//请求数据URL
			valueElem:"input[name=manageOpNo2]",//真实值选择器
			title: "选择办理人员",//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=manageOpName2]"));
			},
			tablehead:{//列表显示列配置
				"opName":"操作员编号",
				"opNo":"操作员名称"
			},
			returnData:{//返回值配置
				disName:"opName",//显示值
				value:"opNo"//真实值
			}
		});
		_bindClose();
	};

	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	};

	var _insertTuningReport = function(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
						var url = $(obj).attr("action");
						var dataParam = JSON.stringify($(obj).serializeArray());
						LoadingAnimate.start();
						$.ajax({
							url : url,
							data : {
								ajaxData : dataParam,
								appId : confirmTuningReport.appId,
								channelType : confirmTuningReport.channelType
							},
							type : 'post',
							dataType : 'json',
							async : false,
							success : function(data) {
								LoadingAnimate.stop();
								if (data.flag == "success") {
									window.top.alert(data.msg, 3);
									//尽调报告处    处理回调方法要的参数
									top.tuningReport = true;
									top.refsh = true;
									top.appSts = data.appSts;
									top.applyInfo = data.applyInfo;
									top.applyDetail = data.applyDetail;
									top.flag = true;
									myclose_click();
								} else {
									top.tuningReport = true;
									top.refsh = true;
									top.flag = true;
									top.appSts = data.appSts;
									top.applyInfo = data.applyInfo;
									top.applyDetail = data.applyDetail;
									window.top.alert(data.msg, 0);
									myclose_click();
								}
							},
							error : function() {
								LoadingAnimate.stop();
								alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
							}
						});
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
		insertTuningReport:_insertTuningReport,
		getCusMngNameDialog : _getCusMngNameDialog
	};
}(window, jQuery);