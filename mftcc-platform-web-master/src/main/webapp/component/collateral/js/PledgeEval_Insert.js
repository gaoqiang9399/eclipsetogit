;
var PledgeEval_Insert = function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		
		//客户新组件
		$("input[name=cusNo]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				var cusNo = obj.val();
				//$("input[name=cusNo]").val(cusNo);
				$("input[name=cusName]").val(obj.data("text"));
				$("input[name=certificateName]").val(obj.data("text"));
				$.ajax({
					url:webPath+"/pledgeBaseInfo/getPledgeShowNoAjax",
					data:{cusNo:cusNo},
					type:"POST",
					dataType:"json",
					beforeSend:function(){},
					success:function(data){
						$("input[name=pledgeShowNo]").val(data.pledgeShowNo);
					},
					error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		});
		//担保方式
		$("select[name=pledgeMethod]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			changeCallback : function (obj, elem) {
				CollateralCommon.refreshFormByVouType(busModel,appId,obj.val(),entrFlag);
			}
		});
		if(vouType=="2"){
			//var option = $("select[name=classNo]").find("option");
			//makeOptionsJQ(option, data.classNo, data.pledgeMethod);
			$("#pledgeBaseInfoInsert").attr("action",webPath+"/mfAssureInfo/insertAjax");
			var assureType = $("select[name=assureType]").val();
            var options;
			if(assureType=="1"){
				options = $("select[name=assureCusType]").find("option");
				makeOptionsJQ(options,corpCusTypeStr);
				$("select[name=assureCusType] option:first").prop("selected", 'selected');
				
				options = $("select[name=idType]").find("option");
				makeOptionsJQ(options,corpIdTypeStr);
				$("select[name=idType] option:first").prop("selected", 'selected'); 
			}else{
				options = $("select[name=assureCusType]").find("option");
				makeOptionsJQ(options,perCusTypeStr);
				$("select[name=assureCusType] option:first").prop("selected", 'selected'); 
				
				options = $("select[name=idType]").find("option");
				makeOptionsJQ(options,perIdTypeStr);
				$("select[name=idType] option:first").prop("selected", 'selected'); 
			}
			
			//客户所属区域选择组件
			$("input[name=careaProvice]").popupSelection({
					ajaxUrl : webPath+"/nmdArea/getAreaListAllAjax",
					searchOn : true,//启用搜索
					multiple : false,//单选
					valueClass : "show-text",//自定义显示值样式
					ztree : true,
					ztreeSetting : setting,
					title : "客户所属地区",
					handle : BASE.getIconInTd($("input[name=careaProvice]")),
					changeCallback : function (elem) {
					var areaNo=elem.data("values").val();
						var node = elem.data("treeNode");
						var parNode =  node.getParentNode();
						var address=node.name;
						while(parNode) {
							address=parNode.name+address;
							parNode=parNode.getParentNode();
						}
						BASE.removePlaceholder($("input[name=regHomeAddre]"));
						$("input[name=regHomeAddre]").val(address);
						$("input[name=careaCity]").val(address);
						var $careaProviceObj = $("input[name=careaProvice]").parent(".input-group").find(".pops-label-alt");
						$careaProviceObj.removeClass("pops-label-alt");
						$careaProviceObj.html(address);
				}
			});
			
			
		}
		if(vouType!="2"){
			$("#pledgeBaseInfoInsert").attr("action",webPath+"/mfBusCollateralRel/insertPledgeEvalAjax");
			//押品新组件
			$("input[name=classId]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//多选选
				items:ajaxData.collClass,
				changeCallback : function (obj, elem) {
					$("input[name=classSecondName]").val(obj.data("text"));
					CollateralCommon.freshPledgeBaseForm(busModel,appId,entrFlag);
				}
			});
			$('select[name=classModel]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false //单选
			});
			
			//人民币状态
			$('select[name=extDic02]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false //单选
			});
			//支付方式
			$('select[name=extDic03]').popupSelection({
				searchOn: true, //启用搜索
				inline: true, //下拉模式
				multiple: false //单选
			});
			//添加押品放大镜
			$("input[name=pledgeName]").after('<span class="input-group-addon">'+
			'<i class="i i-fangdajing pointer" onclick="CollateralInsert.selectCollateralData(CollateralInsert.setCollateralData);"></i></span>');
			$("input[name=cusNo]").attr("readOnly","readOnly").removeAttr("onclick");
			if(busModel!='26'){//中茂押品所有权人允许修改
				$("input[name=certificateName]").attr("readOnly","readOnly").removeAttr("onclick");
			}
				$("input[name=popscusNo]").remove();
			}
		//支持跳过时，跳过按钮显示
		if(supportSkipFlag=="1"){
			$(".skipButton").show();
		}else{
			$(".skipButton").hide();
		}
		
		//切换保证方式时,清空保证人名称和证件号码
		$('select[name=assureType]').change(function(){
			$('input[name=assureName]').parent().find(".pops-value").remove();
			$('input[name=assureName]').val('');
			$('input[name=idNum]').val('');
			$('input[name=assureName]').show();
			if(changeCusRelFlag == '1'){//切换关系标志
				$('input[name=relation]').val('');
				$('input[name=relation]').parent().find(".pops-value").html("");
				var assureTypeVal = $('select[name=assureType]').val();
				changeRelation(assureTypeVal);
			}
		});
		
		//关系下拉框
		$("select[name=relation]").popupSelection({
			searchOn : false,//启用搜索
			inline : true,//下拉模式
			multiple : false,//多选
			items:ajaxData.cusRelComType,
			title:"关系",
			labelShow: false,
			changeCallback : function (obj, elem) {
			},
		});
		$("input[name='pleExpiryDate']").val(busEndDate);
		$("input[name='pleExpiryDate']").attr("onclick","fPopUpCalendarDlg({min:'" + busEndDate + "'})");

		$("select[name=assureCusType]").bind("change", function() {// 保证人类型change事件
			var cusTypeSet = CollateralCommon.getCusTypeSet($(this).val());// 根据客户类别查询业务身份(parm_dic.key_name = 'CUS_TYPE_SET')

			if (cusTypeSet == '8') {// 担保公司必须是全额担保
				$("input[name='assureAmt']").val(appAmt);
				$("input[name='assureAmt']").attr("readonly", true);
			} else {
				$("input[name='assureAmt']").val("");
				$("input[name='assureAmt']").attr("readonly", false);
			}
		});
	};
	
	//切换关系下拉框内容
	var changeRelation = function(assureTypeVal){
		if(assureTypeVal=='1'){//企业
			$("select[name=popsrelation]").popupSelection("updateItems",ajaxData.cusRelComType);
		}else{//个人
			$("select[name=popsrelation]").popupSelection("updateItems",ajaxData.cusRelPersType);
		}
	};
	
	var _insertCollateralBase=function(obj,skipFlag){
		//跳过担保登记，直接提交不验证
		if(skipFlag=="1"){
			if(vouType!="1"){
				_insertBase(obj,skipFlag);
			}else{
				_submitBussProcessAjax();
			}
		}else{
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				_insertBase(obj,skipFlag);
			}
		}
	};
	var _insertBase=function(obj,skipFlag){
		if(entrFlag == "business"){
			var assureNo = $("input[name=assureNo]").val();
			if(assureNo == ''){
				assureNo = "newAssure";
			}
			var parmData = {'nodeNo':nodeNo, 'assureNo': assureNo, 'appId': appId,'relNo':appId};
			$.ajax({
				url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
				type : "post",
				data : {ajaxData: JSON.stringify(parmData)}, 
				dataType : "json",
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				},
				success : function(data) {
					if (data.exsitRefused == true) {// 存在业务拒绝
						top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息',function(){});
					} else {
						var url = $(obj).attr("action");
						var dataParam = JSON.stringify($(obj).serializeArray());
						LoadingAnimate.start();
						jQuery.ajax({
							url : url,
							data : {ajaxData : dataParam,appId:appId,entrFlag:entrFlag,isQuote:isQuote,skipFlag:skipFlag,extensionApplyId:extensionApplyId},
							type : "POST",
							dataType : "json",
							beforeSend : function() {},
							success : function(data) {
								LoadingAnimate.stop();
								if (data.flag == "success") {
									if(top.collaFlag!=undefined){
										top.collaFlag=true;
									}
									if(top.addCollateral!=undefined){
										top.addCollateral=true;
										top.collateralId =data.pledgeNo;
										top.collateralName =data.pledgeName;
										top.classId=data.classId;
										top.vouType=data.pledgeMethod;
									}
									if(top.addCreditCollaFlag!=undefined){
										top.addCreditCollaFlag=true;
										top.creditAppId=appId;
									}
									if(top.extenFlag!=undefined){
										top.skipFlag=skipFlag;
										top.extenFlag=true;
										top.addCollateral=true;
										top.pledgeId=data.pledgeNo;
									}
									alert(data.msg, 1);
									myclose_click();
								} else if (data.flag == "error") {
									alert(data.msg, 0);
								}
							},
							error : function(data) {
								LoadingAnimate.stop();
								alert(top.getMessage("FAILED_OPERATION"," "), 0);
							}
						});
					}
				}
			});
		}else{
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {ajaxData : dataParam,appId:appId,entrFlag:entrFlag,isQuote:isQuote,skipFlag:skipFlag,extensionApplyId:extensionApplyId},
				type : "POST",
				dataType : "json",
				beforeSend : function() {},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(top.collaFlag!=undefined){
							top.collaFlag=true;
						}
						if(top.addCollateral!=undefined){
							top.addCollateral=true;
							top.collateralId =data.pledgeNo;
							top.collateralName =data.pledgeName;
							top.classId=data.classId;
							top.vouType=data.pledgeMethod;
						}
						if(top.addCreditCollaFlag!=undefined){
							top.addCreditCollaFlag=true;
							top.creditAppId=appId;
						}
						if(top.extenFlag!=undefined){
							top.skipFlag=skipFlag;
							top.extenFlag=true;
							top.addCollateral=true;
							top.pledgeId=data.pledgeNo;
						}
						alert(data.msg, 1);
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	var _replaceCollateral=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					collateralId_old:collateralId_old,
					appId:appId,
					entrFlag:entrFlag,
					isQuote:isQuote
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						if(top.repCollateral!=undefined){
							top.repCollateral=true;
							top.collateralId =data.pledgeNo;
							top.collateralName =data.pledgeName;
							top.classId =data.classId;
						}
						alert(top.getMessage("SUCCEED_REPLACE"), 1);
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	//选择押品类型
	var _selectCollateralTypeData=function(_setCollateralType){
		var vouType = $("input[name=vouType]").val();
		if(vouType==undefined){//如果取不到，就查询全部押品类别
			vouType = "";
		}
		if(vouType=="5"){
			vouType ="4";
		}
		selectCollateralTypeDataDialog(_setCollateralType, vouType);
	};
	//选择押品类型回调设置表单中押品类型相关字段
	var _setCollateralType=function(data){
		if(entrance=="credit"){//如果是授信登记押品，担保方式隐藏
			$("input[name=pledgeMethod]").val(data.vouType);
		}
		$("input[name=classId]").val(data.classId);
		$("input[name=collateralTypeName]").val(data.className);
	};
	//选择客户的押品
	var _selectCollateralData=function(_setCollateralData){
		var pledgeMethod = $("input[name=pledgeMethod]").val();
		selectCollateralDataDialog(_setCollateralData,cusNo,appId,pledgeMethod);
	};
	//选择客户押品回调设置押品相关字段。
	var _setCollateralData=function(data){
		var pledgeNo = data.pledgeNo;
		jQuery.ajax({
			url :webPath+"/mfBusCollateralRel/getAddPledgeBaseHtmlAjax",
			data : {pledgeNo:pledgeNo},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#pledgeBaseInfoInsert").find("table").remove();
					$("#pledgeBaseInfoInsert").find(".hidden-content").remove();
					$("#pledgeBaseInfoInsert").html(data.htmlStr);
					isQuote="1";
					$("input[name=classId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//下拉模式
						multiple:false,//多选选
						items:data.collClass,
					});
					$("input[name=classId]").parents("td").find(".pops-select").remove();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//信用担保方式情况下，跳过担保登记业务节点直接进行下一步
	var _submitBussProcessAjax = function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralRel/submitBussProcessAjax",
			data : {appId:appId,extensionApplyId:extensionApplyId},
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					if(top.collaFlag!=undefined){
						top.collaFlag=true;
						top.vouType=vouType;
					}
					if(top.extenFlag!=undefined){
						top.skipFlag=1;
						top.extenFlag=true;
						top.addCollateral=true;
					}
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete : function(){
				LoadingAnimate.stop();
			}
		});
	};
	
	return {
		init:_init,
		insertCollateralBase:_insertCollateralBase,
		replaceCollateral:_replaceCollateral,
		selectCollateralTypeData:_selectCollateralTypeData,
		setCollateralType:_setCollateralType,
		selectCollateralData:_selectCollateralData,
		setCollateralData:_setCollateralData,
	};
}(window, jQuery);