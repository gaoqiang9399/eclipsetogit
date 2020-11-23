;
var mfBusApplyList = function(window, $) {
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : "/mfBusApply/findTrenchApplyByPageAjax",//列表数据查询的url
			tableId : "tabletrenchApplyList",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	};
	//进件列表业务申请入口
	var _applyInput=function(){
		window.location.href = "MfBusApplyAction_applyInput.action";
	}
	//进件列表业务申请复合表单入口
	var _applyInputBusCommon=function(){
		window.location.href = "MfLoanApplyAction_inputBusCommonForm.action";
	};

	// 渠道商做进件客户
	var _applyInputByTrench = function() {
		// 客户新增业务前置校验（是否开户）
		var params = {
			"cusNo" : channelSourceNo
		};
		window.location.href = "/applyTrench/inputQuery";
	};

	var _applyInsert = function(){
		jQuery.ajax({
			url:webPath+"MfApplyInsertConfigActionAjax_getReqUrlByUserAjax.action",
			success : function(data) {
				var kindNo="";
				if (data.flag == "success") {
					if(data.reqType==2){
						if(data.kindCnt == 1){
							window.location.href=data.reqUrl+"?kindNo="+data.kindNo; 
						}else{
							//客户经理新版选择组件
							$('input[name=kindName]').popupList({
								searchOn: true, //启用搜索
								multiple: false, //false单选，true多选，默认多选
								ajaxUrl:webPath+"MfSysKindActionAjax_findByPageForConfigAjax.action",//请求数据URL
								valueElem:"input[name=kindNo]",//真实值选择器
								title: "选择产品",//标题
								changeCallback:function(elem){//回调方法
									kindNo=elem.data("values").val();
									window.location.href=data.reqUrl+"?kindNo="+kindNo; 
								},
								tablehead:{//列表显示列配置
									"kindNo":"产品编号",
									"kindName":"产品名称"
								},
								returnData:{//返回值配置
									disName:"kindName",//显示值
									value:"kindNo"//真实值
								}
							});
							$("input[name=kindName]").parent().find(".pops-value").click();
						}
					}else{
						window.location.href=data.reqUrl; 
					}
				} else if (data.flag == "error") {
					
				}
			},error : function(data) {
				
			}
		}); 
	};
	
	var _applyInsert_zh = function (){
//		window.location.href="MfLoanApplyAction_inputBusCommonForm.action";  
	     //客户经理新版选择组件
	   	$('input[name=cusMngName]').popupList({
	   		searchOn: true, //启用搜索
	   		multiple: false, //false单选，true多选，默认多选
	   		ajaxUrl:webPath+"SysUserActionAjax_findByPageForSelectAjax.action",//请求数据URL
	   		valueElem:"input[name=cusMngNo]",//真实值选择器
	   		title: "选择业务员",//标题
	   		changeCallback:function(elem){//回调方法
	   			BASE.removePlaceholder($("input[name=cusMngName]"));
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
	};
	
	var _getDetailPage = function (obj,url){		
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	var _trClick = function (url){		
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	

	var _ajaxInprocess = function (obj, ajaxUrl) {
		var contentForm = $(obj).parents(".content_table");
		var tableId = contentForm.find(".ls_list").attr("title");
		jQuery.ajax({
			url:ajaxUrl,
			data:{tableId : tableId,appId : appId},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},success : function(data) {
				if (data.flag == "success") {
					$.myAlert.Alert(top.getMessage("SUCCEED_INSERT_PROCESS"));
					if (data.tableData != undefined && data.tableData != null) {
						var tableHtml = $(data.tableData).find("tbody").html();
						contentForm.find(".ls_list tbody").html(tableHtml);
					}
				} else if (data.flag == "error") {
					if (alertFlag) {
						window.parent.window.$.myAlert.Alert(data.msg);
					} else {
						$.myAlert.Alert(data.msg);
					}
				}
			},error : function(data) {
				if (alertFlag) {
					window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				} else {
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		});
	};
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		applyInsert:_applyInsert,
		applyInsert_zh :_applyInsert_zh,
		getDetailPage :_getDetailPage,
		trClick :_trClick,
		ajaxInprocess :_ajaxInprocess,
		applyInput:_applyInput,
		applyInputBusCommon:_applyInputBusCommon,
		applyInputByTrench : _applyInputByTrench
	};
}(window, jQuery);