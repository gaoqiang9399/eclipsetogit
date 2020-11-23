;
var checkOffs = function(window, $) {
	

	//核销申请
	var _addCheckOffApp = function(){
		top.window.openBigForm(webPath+'/mfBusCheckoffs/input','新增核销申请',function(){
			
		});
	};
	//核销申请数据来源
	var _checkOffDatasource=function(dataSorce){
		

		var columShow=null; 
		if("1"==dataSorce){
			columShow ={//列表显示列配置
					"cusName":"客户名称",
					"appName":"项目名称",
					"pactNo":"合同展示号",
					"loanBal":{"disName":"贷款余额(元)","align":"right"},
					"fiveClass":{"disName":"五级分类","align":"center"}
					
				};
			
		}
		if("2"==dataSorce){
			columShow ={//列表显示列配置
					"cusName":"客户名称",
					"appName":"项目名称",
					"pactNo":"合同展示号",
					"loanBal":{"disName":"贷款余额(元)","align":"right"},
					"overDays":{"disName":"逾期天数","align":"center"}
			};
			
			
		}
		//客户经理新版选择组件
		$('input[name=cusName]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfBusCheckoffs/findDataSourceByPageAjax",//请求数据URL
			handle:BASE.getIconInTd($("input[name=cusName]")),//其他触发控件
			valueElem:"input[name=cusMngNo]",//真实值选择器
			title: "选择核销数据",//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=cusName]"));
				var sltVal=elem.data("selectData");
				if(sltVal!=null){
				
//					2020000000537@测试唯一@CONT17072416270940340@MFT20170724000438@fincApp17072416292392455@3937@29.7@0@3966.7
					$("input[name='cusNo']").val(sltVal.cusNo);
					$("input[name='cusName']").val(sltVal.cusName);
					$("input[name='appId']").val(sltVal.appId);
					$("input[name='appName']").val(sltVal.appName);
					$("input[name='cusName']").parent().find("div").text(sltVal.cusName);
					$("input[name='popscusName']").val(sltVal.popscusName);
					$("input[name='pactId']").val(sltVal.pactId);
					$("input[name='pactNo']").val(sltVal.pactNo);
					$("input[name='fincId']").val(sltVal.fincId);
					$("input[name='fincShowId']").val(sltVal.fincShowId);
					$("input[name='checkoffPrcp']").val(sltVal.checkoffPrcp);
					$("input[name='checkoffIntst']").val(sltVal.checkoffIntst);
					$("input[name='checkoffFee']").val(sltVal.checkoffFee);
					$("input[name='checkoffFee']").val(sltVal.checkoffFee);
					$("input[name='checkoffSum']").val(sltVal.checkoffSum);
					$("input[name='checkoffNormalIntst']").val(sltVal.checkoffNormalIntst);
					$("input[name='checkoffOverIntst']").val(sltVal.checkoffOverIntst);
					$("input[name='checkoffDumpIntst']").val(sltVal.checkoffDumpIntst);
					$("input[name='loanBal']").val(sltVal.loanBal);
					tmpprcp=$('input[name=checkoffPrcp]').val();
					tmpInitst=$('input[name=checkoffIntst]').val();
					tmpFee=$('input[name=checkoffFee]').val();
					tmpSum=$('input[name=checkoffSum]').val();
					_setCheckOffTypeShow(sltVal.checkoffIntst,sltVal.checkoffPrcp);
						
					
				}
			},
			tablehead:columShow,
			returnData:{//返回值配置
				disName:"fincId",//显示值
				value:"fincId"//真实值
			}
		});
	}
	
	//设置核销类型展示
	var _setCheckOffTypeShow=function(intst,prcp){
        var checkfOffTypeDatasource;
		if(intst*1==0){
			//如果利息为零不能进行利息核销
			checkfOffTypeDatasource=[{"id":"1","name":"本金核销"},{"id":"3","name":"全部核销","selected":true}];
			$('select[name=popscheckoffType]').popupSelection("updateItems",checkfOffTypeDatasource);
		}else if(prcp*1==0){
			//如果本金为零不能进行本金核销
			checkfOffTypeDatasource=[{"id":"2","name":"利息核销"},{"id":"3","name":"全部核销","selected":true}];
			$('select[name=popscheckoffType]').popupSelection("updateItems",checkfOffTypeDatasource);
		}else{
			checkfOffTypeDatasource=[{"id":"1","name":"本金核销"},{"id":"2","name":"利息核销"},{"id":"3","name":"全部核销","selected":true}];
			$('select[name=popscheckoffType]').popupSelection("updateItems",checkfOffTypeDatasource);
		
		}
			
		
	};
	var _initFangdajingEvent=function(){
		$('input[name=popscusName]').click();
	}
	//核销新增申请保存
	var _addCheckOffSubmit = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_INSERT"), 1);
						var url=webPath+"/mfBusCheckoffs/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					}else if(data.flag == "error"){
						alert(top.getMessage("FAILED_SAVE_CONTENT", {
							"content" : "",
							"reason" : "<br>"+data.msg
						}), 0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			});
		}
		
	};
	//核销申请编辑保存
	var _updCheckOffSubmit = function(obj){
		
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = webPath+"/mfBusCheckoffs/updateAjax";
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						
						alert(top.getMessage("CONFIRM_COMMIT"), 2, function() {
							
							_checkOffApplySubmit(obj);
							
						});
						
//						window.top.alert(data.msg, 1);
//						var url=webPath+"/mfBusCheckoffs/getListPage";
//						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
//						myclose_click();
					}else if(data.flag == "error"){
						alert(top.getMessage("FAILED_SAVE_CONTENT", {
							"content" : "",
							"reason" : "<br>"+data.msg
						}), 0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			});
		}
		
	};
	//删除核销申请 CONFIRM_DELETE
	var _delCheckOff = function(widArgs){
		
		var wid=$("input[name='checkoffId']").val();	
		alert(top.getMessage("CONFIRM_COMMIT"), 2, function() {
			
			LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/mfBusCheckoffs/deleteAjax",
				data:{"checkoffId":wid},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_DELETE"), 1);
						var url=webPath+"/mfBusCheckoffs/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					
					}else if(data.flag == "error"){
						alert(top.getMessage("FAILED_DELETE"), 0);
						myclose_click();
					}
					
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			});
	
			
		});
			
		
		
	};
	//弹框展示客户信息
	var _popCusInfo=function(obj,url){
		var tmpStr=url.substring(7);//cusNo=2020000000122&appId=app17080718312135585
		var tmpAry=tmpStr.split("&");
		
		var cusno=tmpAry[0].split("=")[1];
		var appid=tmpAry[1].split("=")[1];
		top.window.openBigForm(webPath+'/mfCusCustomer/getById?cusNo='+cusno+'&appId='+appid+'&cusType=1&subStringNub=','客户信息',function(){
			
		});
		
	}
	//弹框展示项目信息
	var _popAppItemInfo=function(obj,url){
		var tmpStr=url.substring(7);//fincId=2020000000122&appId=app17080718312135585
		var tmpAry=tmpStr.split("&");
		
		var fincId=tmpAry[0].split("=")[1];
		var appid=tmpAry[1].split("=")[1];
		top.window.openBigForm(webPath+'/mfBusPact/getPactFincById?fincId='+fincId+'&appId='+appid+'&busEntrance=3&subStringNub=','项目信息',function(){
			
		});
		
	}
	
	//核销申请提交
	var _checkOffApplySubmit = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		
		if(flag){
			var wid=$("input[name='checkoffId']").val();
			LoadingAnimate.start();
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			jQuery.ajax({
				url:webPath+"/mfBusCheckoffs/checkOffAppSubmitAjax",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
                        window.top.alert(data.msg, 3, function () {
                            var url = webPath + "/mfBusCheckoffs/getListPage";
                            $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
                            myclose();
                        });
                    }else if(data.flag == "error"){
						alert(top.getMessage("FAILED_SAVE_CONTENT", {
							"content" : "",
							"reason" : "<br>"+data.msg
						}), 0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			});
		}
		
		
		
		
	};
	

	//核销申请详情查看
	var _checkOffAppDetail = function(obj,url){
		var wid=url.substring(18);
		top.window.openBigForm(webPath+'/mfBusCheckoffs/getById?checkoffId='+wid+'&operate=1','核销申请详情',function(){
			
		});
	};
	

	
	//核销审批提交
	var _checkOffArroveSave=function(obj){
		

		var opinionType = $("select[name=approveResult]").val();
		var checkoffId = $("input[name=checkoffId]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/mfBusCheckoffHis/submitUpdateAjax?checkoffId="+checkoffId+"&opinionType="+opinionType,obj,'checkOffSP');
		}
		
		
		
	};
	
	//核销操作执行
	var _exeCheckOff = function(wid){
		
//			var wid=url.substring(18);
		alert(top.getMessage("CONFIRM_OPERATION","核销"),2,function(){
			LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/mfBusCheckoffs/exeCheckOffAjax",
				data:{"checkoffId":wid},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						myclose();
						window.top.alert(top.getMessage("SUCCEED_OPERATION","核销"), 1);
						var url=webPath+"/mfBusCheckoffs/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
					
					}else if(data.flag == "error"){
						alert(top.getMessage("FAILED_SAVE_CONTENT", {
							"content" : "",
							"reason" : "<br>"+data.msg
						}), 0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			});
		})
			
	
		
		
	};
	//核销操作收回
	var _checkOffTakeback = function(wid){
		
//		var wid=url.substring(18);
		LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/mfBusCheckoffs/checkOffTakebackAjax",
			data:{"checkoffId":wid},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					myclose();
					window.top.alert(top.getMessage("SUCCEED_OPERATION","核销收回"), 1);
					var url=webPath+"/mfBusCheckoffs/getListPage";
					$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
					
				}else if(data.flag == "error"){
					alert(top.getMessage("FAILED_SAVE_CONTENT", {
						"content" : "",
						"reason" : "<br>"+data.msg
					}), 0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_UPDATE"),0);
			}
		});
		
		
		
	};
	
	var _checkOffTypeChg=function(curType){
		
		if(curType=="1"){
			//如果是本金核销  利息和费用设置为0 tmpInitst tmpFee
			$('input[name=checkoffPrcp]').val(tmpprcp);
			$('input[name=checkoffIntst]').val(0);
			$('input[name=checkoffFee]').val(0);
			$('input[name=checkoffSum]').val(tmpprcp);
			
			
			
		}
		if(curType=="2"){
			//如果是利息核销
			$('input[name=checkoffPrcp]').val(0);
			$('input[name=checkoffIntst]').val(tmpInitst);
			$('input[name=checkoffFee]').val(0);
			$('input[name=checkoffSum]').val(tmpInitst);
		}
		if(curType=="3"){
			//如果是本金核销
			$('input[name=checkoffPrcp]').val(tmpprcp);
			$('input[name=checkoffIntst]').val(tmpInitst);
			$('input[name=checkoffFee]').val(tmpFee);
			$('input[name=checkoffSum]').val(tmpSum);
		}
	}
	
	
	return {
	
		addCheckOffApp :_addCheckOffApp,
		addCheckOffSubmit :_addCheckOffSubmit,
		checkOffDatasource :_checkOffDatasource,
		initFangdajingEvent :_initFangdajingEvent,
		delCheckOff :_delCheckOff,
		checkOffAppDetail :_checkOffAppDetail,
		checkOffApplySubmit :_checkOffApplySubmit,
		checkOffArroveSave :_checkOffArroveSave,
		exeCheckOff :_exeCheckOff,
		checkOffTakeback :_checkOffTakeback,
		checkOffTypeChg :_checkOffTypeChg,
		popCusInfo :_popCusInfo,
		popAppItemInfo :_popAppItemInfo,
		updCheckOffSubmit :_updCheckOffSubmit
	
		
	};
}(window, jQuery);
