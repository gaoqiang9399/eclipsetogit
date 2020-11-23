;
var MfAssetProtectRecordInsert=function(window,$){
	var _init=function(){
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        _caseNameInit();
		//客户新组件
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
		});
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").parents(".input-group").find(".pops-select").remove();
	};
	//选择项目回调处理字段赋值等
	var _appNameChange=function(obj){
		var caseId=obj.val();
		$("input[name=caseId]").val(caseId);
		$.each(ajaxOverduePactData.overduePact,function(i,pactObj){
			if(caseId==pactObj.caseId){
				appId=pactObj.appId;
				cusNo=pactObj.cusNo;
				$("input[name=cusNo]").val(pactObj.cusNo);
				docParm ="cusNo="+pactObj.cusNo;
				$("input[name=cusName]").val(pactObj.cusName);
				$("input[name=pactNo]").val(pactObj.pactNo);
				$("input[name=appName]").val(pactObj.appName);
				_getBussPledgeData(appId);
			}
		})
	};
    //新增授信额度银行地区初始化
    var _caseNameInit = function(){
        $("input[name=caseName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfAssetProtectRecord/getLawsuitAjax",// 请求数据URL
            valueElem : "input[name=agenciesId]",//真实值选择器
            title : "选择合作银行",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=caseName]").val(sltVal.caseTitle);
                $("input[name=caseId]").val(sltVal.caseId);
                $("input[name=cusNo]").val(sltVal.cusNo);
                cusNo = sltVal.cusNo;
                $("input[name=cusName]").val(sltVal.cusName);
                $("input[name=pactId]").val(sltVal.pactId);
                $("input[name=pactNo]").val(sltVal.pactNo);
                $("input[name=appName]").val(sltVal.appName);
                docParm ="cusNo="+sltVal.cusNo;
                _getBussPledgeData(sltVal.appId);
            },
            tablehead : {//列表显示列配置
                "caseTitle" : "案件名称",
                "caseId" : "案件编号"
            },
            returnData : {//返回值配置
                disName : "caseTitle",//显示值
                value : "caseId"//真实值
            }
        });
    }
	//选择项目后，带出项目中押品
	var _getBussPledgeData=function(appId){
		jQuery.ajax({
			url :webPath+"/mfBusCollateralRel/getBussPledgeDataAjax",
			data : {appId:appId,pledgeNoStr:pledgeNoStr},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					var $assetNo=$("input[name=assetNo]").parent();
					var inputHtml='<input type="text" title="资产名称" name="assetNo" datatype="0" mustinput="0" class="form-control" maxlength="30" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">';
					$assetNo.html("");
					$assetNo.html(inputHtml);
					$("input[name=assetNo]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//下拉模式
						multiple:false,//多选选
						items:data.pledgeArray,
						addBtn:{//添加扩展按钮
							"title":"新增资产",
							"fun":function(hiddenInput,elem){
								//隐藏选择区域
								$(elem).popupSelection("hideSelect", elem);
								_addAsset();
							}
						},
						changeCallback : function (obj, elem) {
							var pledgeNo=obj.val();
							$.each(data.pledgeArray,function(i,pledge){
								if(pledge.pledgeNo==pledgeNo){
									$("input[name=assetName]").val(pledge.pledgeName);
									$("input[name=assetAmt]").val(pledge.pleValue);
									$("input[name=cretificateName]").val(pledge.certificateName);
									$("input[name=assetType]").val(pledge.classId);
									$("input[name=assetTypeName]").val(pledge.classSecondName);
									$("input[name=assetState]").val("1");//引用
								}
							});
						}
					});
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
	//新增
	var _addAsset=function(){
		dialog({
			id:"addAssetDialog",
			title:'新增资产',
			url: webPath+'/pledgeBaseInfo/inputInsertByCusNo?cusNo='+cusNo,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					var pledge=this.returnValue;
					$("input[name=assetNo]").parent().find(".pops-value").html(pledge.pledgeName);
					$("input[name=assetNo]").val(pledge.pledgeNo);
					$("input[name=assetName]").val(pledge.pledgeName);
					var assetAmt=pledge.pleValue;
					if(pledge.pleValue==null){
						assetAmt=0.00;
					}
					$("input[name=assetAmt]").val(assetAmt);
					$("input[name=cretificateName]").val(pledge.certificateName);
					$("input[name=assetType]").val(pledge.classId);
					$("input[name=assetTypeName]").val(pledge.classSecondName);
					$("input[name=assetState]").val("0");//新增
				}
			}
		}).showModal();
	};
	//选择抵债资产
	var _selectAssetProtectData=function(){
		selectBussCollateralDataDialog(_setAssetProtectData,appId,pledgeNoStr);
	};
	//选择客户押品回调设置押品相关字段。
	var _setAssetProtectData=function(data){
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
					//添加押品放大镜
					$("input[name=pledgeName]").after('<span id="selectpledge" class="input-group-addon">'+
					'<i class="i i-fangdajing pointer" onclick="MfAssetProtectRecordInsert.selectAssetProtectData();"></i></span>');
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
	//保存资产信息
    var _saveAssetProtectAjax=function(){
        var appFlag = submitJsMethod($("#MfAssetProtectRecordForm").get(0), '');
        if (appFlag) {
            var url = webPath+"/mfAssetProtectRecord/insertAssetProtectAjax";
            var appDataParam = JSON.stringify($("#MfAssetProtectRecordForm").serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxAppData : appDataParam,
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.flag=true;
                        //_submitAjax();
                        window.top.alert(data.msg,3);
                        myclose_click();
                        updateTableData();
                    }else if (data.flag == "error") {
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

    //保存资产信息
    var _updateAssetProtectAjax=function(){
        var appFlag = submitJsMethod($("#MfAssetProtectRecordForm").get(0), '');
        if (appFlag) {
            var url = webPath+"/mfAssetProtectRecord/chaFengAjax";
            var appDataParam = JSON.stringify($("#MfAssetProtectRecordForm").serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxAppData : appDataParam,
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.flag=true;
                        //_submitAjax();
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else if (data.flag == "error") {
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

    //保存抵债信息
    var _updateAssetDizhaiAjax=function(){
        var appFlag = submitJsMethod($("#MfAssetProtectRecordForm").get(0), '');
        if (appFlag) {
            var url = webPath+"/mfAssetProtectRecord/saveDizhaiAjax";
            var appDataParam = JSON.stringify($("#MfAssetProtectRecordForm").serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxAppData : appDataParam,
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        top.flag=true;
                        window.top.alert(data.msg,3);
                        myclose_click();
                    }else if (data.flag == "error") {
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
	//提交到审批
	var _submitAjax=function(){
		alert(top.getMessage("CONFIRM_OPERATION","资产保全审批提交"),2,function(){
			jQuery.ajax({
				url : webPath+"/mfAssetProtectRecord/submitAjax",
				data : {
					protectId : protectId,
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg,3);
						myclose_click();
					} else if (data.flag == "error") {
						window.top.alert(data.msg,0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		});
	};

	return{
		init:_init,
		saveAssetProtectAjax:_saveAssetProtectAjax,
		selectAssetProtectData:_selectAssetProtectData,
		submitAjax:_submitAjax,
        updateAssetProtectAjax:_updateAssetProtectAjax,
        updateAssetDizhaiAjax:_updateAssetDizhaiAjax
	};
}(window,jQuery);