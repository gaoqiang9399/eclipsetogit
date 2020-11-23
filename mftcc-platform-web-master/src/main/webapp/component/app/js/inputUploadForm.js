var inputUploadForm = function(window, $) {
	
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		_initElement();
	};
	
	//初始化表单的元素
	var _initElement = function(){
		$("input[name=vouType]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,
			ajaxUrl:webPath+"/mfSysKind/getVouTypeSelectByNoAjax?kindNo="+kindNo,
			multiple : true//单选
		});
		//处理担保方式
//		bindVouTypeByKindNo($("#updateForApplyForm").find('select[name=vouType]'), kindNo);
		//贷款投向选择组件
	    $("select[name=fincUse]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,
			items:fincUse,
			multiple : false//单选
		});
	    //共同借款人
	    $('input[name=coborrName]').popupList({
			searchOn: true, //启用搜索
			multiple: true, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfCusCustomer/getCobBoowerAjax?cusNo="+cusNo,//请求数据URL
			valueElem:"input[name=coborrNum]",//真实值选择器
			title: "选择共同借款人",//标题
			labelShow:false,
			tablehead:{//列表显示列配置
				"cusName":"客户名称",
				"idNum":"证件号码"
			},
			returnData:{//返回值配置
				disName:"cusName",//显示值
				value:"idNum"//真实值
			}
		});
	 
	    //办理人员选择组件
		$('input[name=manageOpName1]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/sysUser/findByPageForSelectAjax",//请求数据URL
			valueElem:"input[name=manageOpNo1]",//真实值选择器
			title: "选择办理人员",//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=manageOpName1]"));
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
	
	var _douploadcommit = function(obj, temporaryStorage){
		var flag=submitJsMethod($(obj).get(0), '');
		if(flag){
			alert(top.getMessage("CONFIRM_OPERATION",nodeName),2,function(){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());

                LoadingAnimate.start();
				$.ajax({
					url:url,
					data:{ajaxData:dataParam, 'temporaryStorage':temporaryStorage},
					type:'post',
					dataType:'json',
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							window.location.href=webPath+"/mfBusApply/getSummary?appId="+data.appId;
							window.top.alert(data.msg,3);
							top.flag = true;
							top.appSts = data.appSts;
							myclose_click();
						}else{
							alert(data.msg,0);
						}
					},error:function(){
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_SAVE"),0);
					}
				});
			});
		}
	};
	
	//验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
	var _checkByKindInfo = function(obj){
		var name = $(obj).attr("name");
		var title = $(obj).attr("title").split("(")[0];
		//申请金额
		if(name=="appAmt"){
			if(maxAmt!=null && minAmt!=null && maxAmt!="" && minAmt!=""){
				maxAmtNum = new Number(maxAmt);
				minAmtNum = new Number(minAmt); 			
				var appAmt = $(obj).val();
				appAmt = appAmt.replace(/,/g, "");
				if(parseFloat(appAmt)<parseFloat(minAmtNum)||parseFloat(appAmt)>parseFloat(maxAmtNum)){//判断申请金额是否在产品设置范围内
					$(obj).val(null);
					alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE",{"info":"产品设置","field":title,"value1":fmoney(minAmtNum,2),"value2":fmoney(maxAmtNum,2)}),0);
				}else{
					if(creditAmt!=null&&creditAmt!=""){ //判断申请金额是否在授信余额内
						creditNum = new Number(creditAmt);
						if(parseFloat(appAmt)>parseFloat(creditNum)){
							$(obj).val(null);
							alert(top.getMessage("NOT_APPLY_VALUE_BIG",{"info":"该客户授信","field":title,"value":fmoney(creditNum,2)}),0);
						} else{
							if(kindCreditAmt!=null && kindCreditAmt!=""){ //判断申请金额是否在产品授信余额内
								kindCreditNum = new Number(kindCreditAmt);
								if(parseFloat(appAmt)>parseFloat(kindCreditNum)){
									$(obj).val(null);
									alert(top.getMessage("NOT_APPLY_VALUE_BIG",{"info":"该客户产品授信","field":title,"value":fmoney(kindCreditNum,2)}),0);
								};
							}; 
						};
					};
				};
			};			
		};
	};
	
	var _checkTerm = function(obj){
		var appTerm = $("input[name=term]").val();
		appTerm = appTerm.replace(/,/g, "");
		var title = $("input[name=term]").attr("title").split("(")[0];
		var appTermType = $("[name=termType]").val();
		appTermType = appTermType.replace(/,/g, "");
		var appMinTerm;
		var appMaxTerm;
		//申请期限
		if(minTerm!=null && maxTerm!=null && minTerm!="" && maxTerm!=""&&termType!=null&&termType!=""){				
			minTermNum = new Number(minTerm);
			maxTermNum = new Number(maxTerm);
			var unit = appTermType=="1"?"个月":"天";
			if(appTermType=="1"){//表单填写申请期限为月
				if(termType=="2"){//产品申请期限为日
					minTermNum = (minTerm/30).toFixed();
					maxTermNum = (maxTerm/30).toFixed();
				}
			}
			if(appTermType=="2"){//表单填写申请期限为日
				if(termType=="1"){//产品申请期限为月
					minTermNum = (minTerm*30).toFixed();
					maxTermNum = (maxTerm*30).toFixed();
				}
			}
			appMinTerm = minTermNum + unit;
			appMaxTerm = maxTermNum + unit;
			$("input[name=term]").attr("placeholder",appMinTerm+"-"+appMaxTerm);
			if(parseFloat(appTerm)<parseFloat(minTermNum)||parseFloat(appTerm)>parseFloat(maxTermNum)){
				$("input[name=term]").val("");
				alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE",{"info":"产品设置","field":title,"value1":appMinTerm,"value2":appMaxTerm}),0);
			}
		}
	};
	
	return {
		init :_init,
		douploadcommit : _douploadcommit,
		checkByKindInfo:_checkByKindInfo,
		checkTerm:_checkTerm,
	};
}(window, jQuery);
