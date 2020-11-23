function vouTypeChange(){
		var str = '';
		var length = $("input[name='vouType']:checked").length;
		$("input[name='vouType']:checked").each(function(i){
			if(i < length -1){
			  str = str + $(this).val() + ',';
			}else{
				str = str + $(this).val();
			}
		});
		makeOptionsJQ(option1, str);
	};
	function rateTypeChange(){
		var str = '';
		var length = $("input[name='rateType']:checked").length;
		$("input[name='rateType']:checked").each(function(i){
			if(i < length -1){
			  str = str + $(this).val() + ',';
			}else{
				str = str + $(this).val();
			}
		});
		
		makeOptionsJQ(option2, str);
	}; 
	function icTypeChange(){
		var str = '';
		var length = $("input[name='icType']:checked").length;
		$("input[name='icType']:checked").each(function(i){
			if(i < length -1){
			  str = str + $(this).val() + ',';
			}else{
				str = str + $(this).val();
			}
		});
		
		makeOptionsJQ(option3, str);
	};
	function repayTypeChange(){
		var str = '';
		var length = $("input[name='repayType']:checked").length;
		$("input[name='repayType']:checked").each(function(i){
			if(i < length -1){
			  str = str + $(this).val() + ',';
			}else{
				str = str + $(this).val();
			}
		});
		
		makeOptionsJQ(option4, str);
	};
	
	function feeStdChange(){
		$("input[name='feeStdName']").val($("select[name=feeStdNo]").find("option:selected").text());
	};
	/*function pactModelChange(){
		$("input[name='pactModelName']").val($("select[name=pactModelNo]").find("option:selected").text());

	};*/
	function pactModelChange(){
		$("input[name='pleFormName']").val($("select[name=pleFormNo]").find("option:selected").text());

	};
	function isFincChange(){
		if($("select[name='isFinc']").val() == 0){
			$("input[name=rateType]").attr('disabled',true);
			$("input[name=icType]").attr('disabled',true);
			$("input[name=repayType]").attr('disabled',true);
			
			//$("select[name=vouTypeDef]").find("option");
			$("select[name=rateTypeDef]").attr('disabled',true);
			$("select[name=icTypeDef]").attr('disabled',true);
			$("select[name=repayTypeDef]").attr('disabled',true);
			$("input[name='maxPrepayRate']").val('0');
			$("input[name='minPrepayRate']").val('0');
		}else{
			
			$("input[name=rateType]").attr('disabled',false);
			$("input[name=icType]").attr('disabled',false);
			$("input[name=repayType]").attr('disabled',false);
			
			//$("select[name=vouTypeDef]").find("option");
			$("select[name=rateTypeDef]").attr('disabled',false);
			$("select[name=icTypeDef]").attr('disabled',false);
			$("select[name=repayTypeDef]").attr('disabled',false);
		}
	};
	function back(){
		window.location.href = path+"/MfSysKindAction_mfKindConfig.action";
	};
	function close(){
		window.location.href = path+"/MfSysKindAction_mfKindConfig.action";
	};
	function openFormDesign(obj){
		var url = path+'/tech/dragDesginer/openForm.action?formId='+obj.value;
		window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
	};
	function setProcessNo(key,fieldName){
		$("input[name='"+fieldName+"']").val(key);
	};
	function glNameCallback(data){
		$("input[name=glNo]").val(data.glNo);
	};
	function getProcessNo(fieldName){
		return $("input[name='"+fieldName+"']").val();
	};
	/*function setProcessNo(key,fieldName){
		$("input[name='"+fieldName+"']").val(key);
	};*/
	function pleFormChange(){
		$("input[name='pleFormName']").val($("select[name=pleFormNo]").find("option:selected").text());

	};
	function getFincUseInfo(obj){
		$("input[name=fincUse]").val(obj.fincUse);
		$("input[name=fincUseName]").val(obj.fincUseShow);
	};
	//保存产品信息
	function saveKindInfo(obj){
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
						window.top.alert(data.msg, 1);
//						var url="MfSysKindAction_mfKindConfig.action";
//						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						top.addFlag=true;
						top.mfSysKind = data.mfSysKind;
						myclose_click();
					}else if(data.flag == "error"){
						alert(top.getMessage("FAILED_SAVE_CONTENT", {"content" : "","reason" : "<br>"+data.msg}), 0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_UPDATE"),0);
				}
			});
		}
	}
	//检查选择的费用标准是否配置费用项
	function checkFeeBeforeSub(obj){
		var feeStdNo = $("select[name=feeStdNo]").val();
		var feeStdName = $("select[name=feeStdNo]").find("option:selected").text();
		if(feeStdNo!=""){
			$.ajax({
				url:webPath+"/MfSysFeeStd/checkFeeStdIsEmpty?feeStdNo="+feeStdNo,
				dataType:"json",
				data:"POST",
				success:function(data){
					if(data.flag == "success"){
						if(data.checkFlag == "success"){
							saveKindInfo(obj);
						}else{
							alert("费用标准"+feeStdName+"下没有费用项,确认保存?",2,function(){
								saveKindInfo(obj);
							});
						}
					}else{
						alert(top.getMessage("SUCCEED_SAVE"),0);
					}
				},error:function(){
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
		}
	};
	//选择审批流程
	function selectWkf(parm){
		switch(parm){
		case 1:
			window.top.showDialog('wkf_showView.action?fieldName=applyProcessName','选择流程编号',80,90);
			break;
		case 2:
			window.top.showDialog('wkf_showView.action?fieldName=pactProcessName','选择流程编号',80,90);
			break;
		case 3:
			window.top.showDialog('wkf_showView.action?fieldName=fincProcessName','选择流程编号',80,90);
			break;
		}
	};
	//删除产品
	function deleteKind(){
		var kindNo = $("input[name=kindNo]").val();
		//先判断该产品是否发生过业务，如果发生过不允许删除、如果没有发生过，可以删除
		$.ajax({
			url:"MfBusApplyActionAjax_getMfBusApplyByKindNoAjax.action?kindNo="+kindNo,
			dataType:"json",
			data:"POST",
			success:function(data){
				if(data.flag == "success"){
					if(data.hasBiz=="1"){
						alert(top.getMessage("EXIST_PRDCT_USING"),0);
					}else{
						alert(top.getMessage("CONFIRM_DELETE"),2,function(){
							$.ajax({
								url:"MfSysKindActionAjax_deleteAjax.action?kindNo="+kindNo,
								dataType:"json",
								data:"POST",
								success:function(data){
									if(data.flag == "success"){
										var url="MfSysKindAction_mfKindConfig.action";
										$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
										myclose_click();
									}else{
										alert(data.msg,0);
									}
								},error:function(){
									alert(top.getMessage("FAILED_DELETE"),0);
								}
							});
						});
					}
				}else{
					alert(top.getMessage("SUCCEED_DELETE"),0);
				}
			},error:function(){
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		});
	
	}
	
	//默认担保方式选中值
	var checkedVouTypeDefValue = "";
	function clickVouTypeDef(value){
		checkedVouTypeDefValue = value;
	}
	
	//默认同性质选中值
	var checkedPactPropertyDefValue = "";
	function clickPactPropertyDef(value){
		checkedPactPropertyDefValue = value;
	}
	
	/**
	 * 
	 * @param ajaxData
	 * @param initType 初始化类型 add-新增页面 update-编辑页面
	 * @param brNoName
	 * @param brNo 
	 * @param checkedVouTypeDef 选中的默认担保方式
	 * @param checkedPactPropertyDef 选中的默认合同性质
	 */
	function initKindConfig(ajaxData,initType,brNoName,brNo,checkedVouTypeDef,checkedPactPropertyDef){
		checkedVouTypeDefValue = checkedVouTypeDef;
		checkedPactPropertyDefValue = checkedPactPropertyDef;
		//客户新组件
		$("[name=cusType]").popupSelection({
			searchOn:false,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			title:"客户类型",
			labelShow: false,
			items:ajaxData.cusType,
			changeCallback : function (obj, elem) {
			},
		});
		//客户新组件
		$("[name=subKindNo]").popupSelection({
			searchOn:false,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			title:"产品子类",
			labelShow: false,
			items:ajaxData.subKind,
			changeCallback : function (obj, elem) {
			},
		});
		//产品模板组件
        $("[name=srcKindNo]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//多选
            changeCallback : chooseKindTemplate
        });
		//ztree 选择设置
		var ztreeSetting = {
				check: {  
	                enable: true,  
	                chkStyle: "checkbox",  
	                chkboxType: { "Y": "s", "N": "" }  
	            },
	            data:    {
	                simpleData:{
	                    enable:true
	                }
	            },
		}

		//角色新组件
		$("input[name=roleNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			items:ajaxData.role,
			title:"开办角色",
			labelShow: false,
            selectAllItems:true,
			changeCallback : function (obj, elem) {
			},
		});
		//贷款投向
		$("input[name=fincUse]").popupSelection({
			searchOn : true,//启用搜索
			inline : false,//下拉模式
			multiple : true,//多选
			items:ajaxData.trade,
			title:"贷款投向",
			labelShow: false,
            selectAllItems:true,
			changeCallback : function (obj, elem) {
			},
		});
		//贷款投向
		$("input[name=busBreed]").popupSelection({
			searchOn : true,//启用搜索
			inline : false,//下拉模式
			multiple : true,//多选
			items:ajaxData.busBreed,
			title:"业务品种",
			labelShow: false,
            selectAllItems:true,
			changeCallback : function (obj, elem) {
			},
		});

        //ztree 选择设置
        var classZtreeSetting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "s", "N": "" }
            },
            data:    {
                simpleData:{
                    enable:true
                }
            },callback: {
                beforeCheck: zTreeBeforeCheck
            }
        };
        function zTreeBeforeCheck(treeId, treeNode) {
            return !treeNode.isParent;//当是父节点 返回false 不让选取
        }
        //部门新组件
        $("input[name=brNo]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            ztree:true,
            parentSelect : true,//选择父节点
            ztreeSetting : ztreeSetting,
            title:"开办部门",
            items:ajaxData.org,
            changeCallback : function (obj, elem) {
            },
        });
        //资产类别组件
        $("input[name=assetClass]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            ztree:true,
            parentSelect : true,//选择父节点
            ztreeSetting : ztreeSetting,
            title:"资产类别",
            items:ajaxData.assetClass,
            changeCallback : function (elem) {
                var node = elem.data("treeNode");
                var assetClassName="";
			    for(var i=0;i<node.length;i++){
					assetClassName = assetClassName+node[i].name+"|";
				}
				$("input[name=assetClassName]").val(assetClassName);
            },
        });
		//展业区域
		if(!$("input[name='exhibitionArea']").is(":hidden")){
			var areaSetting = {
					check: {
						enable: true,
						chkStyle: "checkbox",
						chkboxType: { "Y": "ps", "N": "ps" }
					}
			};
			$("input[name=exhibitionArea]").popupSelection({
				//ajaxUrl : "NmdAreaActionAjax_getAllCityAjax.action",
                ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
				searchOn : true,//启用搜索
				inline : false,//下拉模式
				multiple : true,//多选
				ztree : true,
				ztreeSetting : areaSetting,
				title:"展业区域",
				changeCallback : function (elem) {

				},
			});
		}
		var firstVou = "<span id='vouTypeDefHtml'></span>";
		var firstPactPropertyDef = "<span id='pactPropertyDefHtml'></span>";
		$("input[name=vouTypeDef]").parent().html(firstVou);
		$("input[name=pactPropertyDef]").parent().html(firstPactPropertyDef);
		if(initType == 'add'){//新增
			//业务模式
			$("input[name=busModel]").popupSelection({
				searchOn : true,//启用搜索
				inline : false,//下拉模式
				multiple : false,//单选
				items:ajaxData.busModel,
				valueClass:"show-text",
				title:"业务模式",
				labelShow: false,
				changeCallback : function () {
				},
			});
			initKindDef(firstVou,firstPactPropertyDef);
		}else{
            //业务模式
            $("input[name=busModel]").popupSelection({
                searchOn : true,//启用搜索
                inline : false,//下拉模式
                multiple : false,//单选
                items:ajaxData.busModel,
                valueClass:"show-text",
                title:"业务模式",
                labelShow: false,
                changeCallback : function () {
                },
            });
			initKindDef(firstVou,firstPactPropertyDef);
			hideKindDef();
		}
	}


	
	//默认担保方式
	var zhiyaVou = "<input type='radio' name='vouTypeDef' title='默认担保方式'  onclick='clickVouTypeDef(4)' value='4'> 质押担保";
    var diyaVou = "<input type='radio' name='vouTypeDef' title='默认担保方式'   onclick='clickVouTypeDef(3)' value='3' > 抵押担保";
    var assureVou = "<input type='radio' name='vouTypeDef' title='默认担保方式' onclick='clickVouTypeDef(2)' value='2' > 保证担保";
    var creditVou = "<input type='radio' name='vouTypeDef' title='默认担保方式' onclick='clickVouTypeDef(1)' value='1' > 信用担保";
    var attornVou = "<input type='radio' name='vouTypeDef' title='默认担保方式' onclick='clickVouTypeDef(5)' value='5'  > 转让";
    
    //默认合同性质
    var commonPactDef = "<input type='radio' name='pactPropertyDef' title='默认合同性质' value='1' onclick='clickPactPropertyDef(1)'> 普通";
    var highPropertyDef = "<input type='radio' name='pactPropertyDef' title='默认合同性质' value='2'  onclick='clickPactPropertyDef(2)'> 最高额担保合同";
	//初始化担保方式，合同性质
	function initKindDef(firstVou,firstPactPropertyDef){
	    $("input[name=vouType]").click(function(){
	   	    var defVal ="";
	   	    $("input[name=vouTypeDef]").parent().html(firstVou);
	   	    $("#vouTypeDefHtml").html('');
	    	$("input[name=vouType]:checked").each(function(i,item){
			   if($(item).val()=='4'){
			   		defVal+= zhiyaVou;
			   }else if($(item).val()=='3'){
			    	defVal+= diyaVou;
			   }else if($(item).val()=='2'){
			    	defVal+= assureVou;
			   }else if($(item).val()=='1'){
			    	defVal+= creditVou;
			   }else if($(item).val()=='5'){
			    	defVal+= attornVou;
			   }
			});
			$("#vouTypeDefHtml").append(defVal);
			$("input[type=radio][name='vouTypeDef'][value="+checkedVouTypeDefValue+"]").attr("checked",true);
	    });
	    
	    $("input[name=pactProperty]").click(function(){
	    	var defVal ="";
	    	$("input[name=pactPropertyDef]").parent().html(firstPactPropertyDef);
	   	    $("#pactPropertyDefHtml").html('');
	    	$("input[name=pactProperty]:checked").each(function(i,item){
			   if($(item).val()=='1'){
			   		defVal+= commonPactDef;
			   }else if($(item).val()=='2'){
			    	defVal+= highPropertyDef;
			   }
			}); 
			$("#pactPropertyDefHtml").append(defVal);
			$("input[type=radio][name='pactPropertyDef'][value="+checkedPactPropertyDefValue+"]").attr("checked",true);
	    });
	}
	
	
	//编辑页面，隐藏多余的默认合同性质，默认担保方式
	function hideKindDef(){
		var vouTypeDefHtml ="";
		$("input[name=vouType]:checked").each(function(i,item){
		   var value = $(item).val();
		   if(value=='4'){
			   vouTypeDefHtml+= zhiyaVou;
		   }else if(value=='3'){
			   vouTypeDefHtml+= diyaVou;
		   }else if(value=='2'){
			   vouTypeDefHtml+= assureVou;
		   }else if(value=='1'){
			   vouTypeDefHtml+= creditVou;
		   }else if(value=='5'){
			   vouTypeDefHtml+= attornVou;
		   }
		});
		$("#vouTypeDefHtml").append(vouTypeDefHtml);
		$("input[type=radio][name='vouTypeDef'][value="+checkedVouTypeDefValue+"]").attr("checked",true);
		
		var pactPropertyDefHtml="";
		$("input[name=pactProperty]:checked").each(function(i,item){
		   var value = $(item).val();
		   if(value=='1'){
			   pactPropertyDefHtml+= commonPactDef;
		   }else if($(item).val()=='2'){
			   pactPropertyDefHtml+= highPropertyDef;
		   }
		}); 
		$("#pactPropertyDefHtml").append(pactPropertyDefHtml);
		//处理默认合同性质性质为空时js出错
		if(checkedPactPropertyDefValue!=""){
			$("input[type=radio][name='pactPropertyDef'][value="+checkedPactPropertyDefValue+"]").attr("checked",true);
		}
	}
	//选择产品模板
	function chooseKindTemplate(obj, elem){
		var addOrCopy = $("[name=addOrCopy]").val();
		var srckindNo = $(obj).val();
        $.ajax({
            url:webPath+"/mfSysKind/getByIdAjax?kindNo="+srckindNo+"&addOrCopy="+addOrCopy,
            success:function(data){
                if(data.flag == "success"){
					$("#kindFormAdd").html(data.htmlStr);
					var mfSysKindTmp = data.mfSysKind;
                    initKindConfig(ajaxData,'update','',mfSysKindTmp.brNo,mfSysKindTmp.vouTypeDef,mfSysKindTmp.pactPropertyDef);
                    $("[name=kindName]").val("");
                    $("[name=remark]").val("");
                }else{
                    alert(data.msg,0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","选择产品模板"),0);
            }
        });

	};