;
var mfBusApplyList = function(window, $) {
	var url = webPath + "/mfBusApply/findByPageAjax";
	var tableId = "tablecusandapply0001";
	var _init = function(){
		if(typeof(queryType) !="undefined" && queryType == "busHandling"){//业务办理中数据(从渠道操作员进入系统)
			url = webPath + "/mfBusApply/findBusHandlingByPageAjax";
			tableId = "tablemfbusapplylist_trench";
		}
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : url,//列表数据查询的url
			tableId : tableId,//列表数据查询的table编号
			tableType : "tableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	};
	
	//进件列表业务申请入口
	var _applyInput=function(){
	  if(kindNo==null||kindNo==""){
			alert(top.getMessage("FIRST_CHECK_PRODUCT_CONFIG","操作员"),0);
			return ;
		}
		window.location.href = webPath+"/mfBusApply/applyInput?kindNo="+kindNo;
	}
	
	//进件列表业务新申请入口
	var _applyInitInput=function(){
	 window.location.href = webPath+"/mfBusApply/applyInitInput";
	}
	var _exportExcel = function(){
		window.top.location.href = encodeURI(url +  "Excel?tableId=" + tableId);
	};
	var _applyInsert = function(){
		jQuery.ajax({
			url:webPath+"/mfApplyInsertConfig/getReqUrlByUserAjax",
			success : function(data) {
				var kindNo="";
				if (data.flag == "success") {
					if(data.reqType==2){
						if(data.kindCnt == 1){
							window.location.href=webPath+data.reqUrl+"?kindNo="+data.kindNo + "&from=MfBusApply"; 
						}else{
							//客户经理新版选择组件
							$('input[name=kindName]').popupList({
								searchOn: true, //启用搜索
								multiple: false, //false单选，true多选，默认多选
								ajaxUrl:webPath+"/mfSysKind/findByPageForConfigAjax",//请求数据URL
								valueElem:"input[name=kindNo]",//真实值选择器
								title: "选择产品",//标题
								changeCallback:function(elem){//回调方法
									kindNo=elem.data("values").val();
									window.location.href=webPath+data.reqUrl+"?kindNo="+kindNo+ "&from=MfBusApply"; 
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
						window.location.href=webPath+data.reqUrl+ "?kindNo="+data.kindNo+ "&from=MfBusApply"; 
					}
				} else if (data.flag == "error") {
					
				}
			},error : function(data) {
				
			}
		}); 
	};
	
	var _applyInsert_zh = function (){
//		window.location.href=webPath+"/mfLoanApply/inputBusCommonForm";  
	     //客户经理新版选择组件
	   	$('input[name=cusMngName]').popupList({
	   		searchOn: true, //启用搜索
	   		multiple: false, //false单选，true多选，默认多选
	   		ajaxUrl:webPath+"/sysUser/findByPageForSelectAjax",//请求数据URL
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
		if(ajaxUrl.substr(0,1)=="/"){
			ajaxUrl =webPath + ajaxUrl; 
		}else{
			ajaxUrl =webPath + "/" + ajaxUrl;
		}
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
		applyInitInput:_applyInitInput,
		exportExcel:_exportExcel
	};
}(window, jQuery);