;
var ShowPledgeDetailInfo=function(window,$){
	//展示押品信息模块信息
	var _showPledgeInfo=function(pledgeNo,relId,classId){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getPledgeListHtmlAjax",
			data:{pledgeNo:pledgeNo,appId:relId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag="success"){
					$.each(data.collateralTableList,function(index,collateralTable){
						collateralTable.action = collateralTable.action.substring(0,1).toLowerCase()+collateralTable.action.substring(1);
						collateralTable.action =collateralTable.action.replace("Action","");
						if(collateralTable.dataFullFlag=="1"){
                            var htmlStr;
							if(collateralTable.showType=="1"){//表单
								htmlStr = '<div class="handle title"><span class="sub-title" id="'+pledgeNo+'">'+collateralTable.tableDes+'</span></div>';
								htmlStr = '<div class="cover infoTilte form-table" id="'+collateralTable.tableName+pledgeNo+'">'+htmlStr+
								'<form class="form-margin" action="' + webPath +'/'+collateralTable.action+'/updateAjaxByOne" id="'+collateralTable.action+'Ajax_updateByOne">'+collateralTable.htmlStr+'</form></div>';
							}else if(collateralTable.showType=="2"){//列表
                                htmlStr ="";
								if (collateralTable.action != "insInfo") {
                                     htmlStr = '<div class="handle title" data-showtype="2" data-name="'+collateralTable.action+'" data-title="'+collateralTable.tableDes+'" name="'+collateralTable.action+'"title="'+collateralTable.tableDes+'"><span class="sub-title"  id="'+pledgeNo+'">'+collateralTable.tableDes+'</span><button class="btn btn-link jia-color" onclick="ShowPledgeDetailInfo.addCollateralFormByRotate(this,\''+pledgeNo+'\',\''+classId+'\',2);" title="新增"><i class="i i-jia3"></i></button></div>';
                                }else {
                                    htmlStr = '<div class="handle title" data-showtype="2" data-name="'+collateralTable.action+'" data-title="'+collateralTable.tableDes+'" name="'+collateralTable.action+'"title="'+collateralTable.tableDes+'"><span class="sub-title"  id="'+pledgeNo+'">'+collateralTable.tableDes+'</span></div>';
                                }
								htmlStr = '<div class="cover infoTilte list-table" id="'+collateralTable.tableName+pledgeNo+'">'+htmlStr+'<div class="margin_left_20 collapse in" >'+collateralTable.htmlStr+'</div></div>';
							}
							$("#rotate-body"+pledgeNo).before(htmlStr);
							if(collateralTable.action == "PledgeBaseInfoBillAction"){
								$("#pledgeDetailBillExport"+pledgeNo).remove();
								$("#"+collateralTable.tableName+pledgeNo).find("button:first").after('<a id="pledgeDetailBillExport'+pledgeNo+'" onclick="ShowPledgeDetailInfo.exportPledgeBill(\''+pledgeNo+'\');" href="javascript:;">导出</a>');
							}
						}
					});
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//展示押品信息块
	var _setBlock=function(collateralTable,i,classId){
		 var color = i%4+1;
		 var collateralNo = collateralTable.collateralNo;
		 var showType = collateralTable.showType;
		 var title = collateralTable.tableDes;
		 var name = collateralTable.action;
		 var htmlStr = collateralTable.htmlStr;
		 var dataFullFlag = collateralTable.dataFullFlag;
		 var isMust = collateralTable.isMust;
		 var tableName = collateralTable.tableName;
		 var delFlag = collateralTable.delFlag;
		var rotateColor="rotate-color";	
		var rotateBorderColor="rotate-borderColor";
		var rotateTubiaoBac="rotate-tubiaoBac";
		if(color){
			rotateColor="rotate-color"+color;
			rotateBorderColor="rotate-borderColor"+color;
			rotateTubiaoBac="rotate-tubiaoBac"+color;
		}
			var clearDiv = '<div style="clear:both;"></div>';
			var addBtnHtml = "";
			var delBtnHtml1 = "";
			var isMustHtml = "";
			var isMustHtml1 = "";
			if(isMust == "1"){
				isMustHtml = "<span class='formMust-span'>*</span>";
				isMustHtml1 = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			}else{
				delBtnHtml1 = '<button class="rotate-del i i-lajitong color_theme" title="删除" onclick="ShowPledgeDetailInfo.deleteRotate(this,\''+collateralNo+'\');"></button>';
			}
			if(dataFullFlag == "0"){
				if(delFlag == "0"&&name!="MfAccntRepayDetailAction"){
					htmlStr = "";
					htmlStr = "<div class='rotate-div' name='"+name+"' data-showtype='"+showType+"' data-name='"+name+"' data-title='"+title+"' data-tablename='"+tableName+"' data-ismust='"+isMust+"'>"+
					"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtml1+
					"<div class='rotate-des "+rotateColor+"'><div class='rotate-tubiao pull-left "+rotateTubiaoBac+"'></div><div class='rotate-formName pull-left'>登记"+title+"</div></div>" +
					"<div class='rotate-opre'>" +
					'<button class="rotate-add i i-jia2 color_theme" onclick="ShowPledgeDetailInfo.addCollateralFormByRotate(this,\''+collateralNo+'\',\''+classId+'\',1);"></button>'+delBtnHtml1+
					"</div>"+
					"</div>"+
					"</div>";
					$("#rotate-body"+collateralNo).append(htmlStr);
					htmlStr = "";
				}
				return false;
			}
	};
	//添加信息模块
	var _cusAdd=function(collateralId){
		if(operable=="operable"){
			var cusAdd = '<div class="rotate-div" id="add'+collateralId+'"><div class="rotate-obj rotate-borderColor2" id="cus-add" onclick="ShowPledgeDetailInfo.updateCollateralFormStas(\''+collateralId+'\')">'+
			'<div class="rotate-des rotate-color2"><div class="rotate-formName pull-left"><i class="i i-jia1"></i></div></div></div></div>';
			$("#rotate-body"+collateralId).append(cusAdd);
		}
	};
	//新增押品其他模块信息
	var _addCollateralFormByRotate=function(obj,collateralNo,classId,type){
        var $rotateDiv;
		if(type=="1"){
			$rotateDiv = $(obj).parents(".rotate-div");
		}else{
			$rotateDiv = $(obj).parents(".handle");
		}
		var title =$rotateDiv.data("title");
		var action = $rotateDiv.data("name");
		//处理action为controller;
		action = action.substring(0,1).toLowerCase()+action.substring(1);
		action =action.replace("Action","");
		//处理新增押品检查后再次新增 action = "http://192.168.2.111:8080/的问题"
		if(action.indexOf("/") >=0)
		{
			var index = action.lastIndexOf("/");
			action = action.substring(index+1);
		}
		top.action = action;
		top.title = title;
		top.isMust = $rotateDiv.data("ismust");
		top.showType = $rotateDiv.data("showtype");
		var inputUrl = "";
		if("InsInfoAction" == action){
			inputUrl =webPath+"/"+ action + "/input?collateralNo="+collateralNo+"&classId="+classId + "&busEndDate=" +busEndDate;
		}else{
			inputUrl =webPath+"/"+ action + "/input?collateralNo="+collateralNo+"&classId="+classId;
		}
		top.flag="add";//编辑
		top.addFlag = false;
		top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
		top.htmlString = null;
		top.collateralNo=collateralNo;
		top.tableName=$rotateDiv.data("tablename");
		top.openBigForm(inputUrl,title,function(){
			_addCollateralFormInfoCall(collateralNo,classId);
		});
	};
	//回显信息块
	var _addCollateralFormInfoCall=function(collateralNo,classId){
		if(top.addFlag){
			if(top.htmlStrFlag){
                var htmlStr;
				if(top.flag=="edit"){//列表修改保存回显
					htmlStr = '<div class="handle" data-name="'+top.action+'" data-title="'+top.title+'"><span class="sub-title" id="'+top.collateralNo+'">'+top.title+'</span></div>';
					if($("#"+top.id).length > 0){
						htmlStr = htmlStr+'<div class="margin_left_20">'+top.htmlString+'</div>';
						$("#"+top.id).html(htmlStr);
					}
				}else if(top.flag=="add"){
					if(top.showType=="1"){//表单
						var action = top.action;
						action = action.split("/");
						action = action[action.length - 1];
						htmlStr = '<div class="handle title" data-name="'+top.action+'" data-title="'+top.title+'"><span class="sub-title" id="'+top.collateralNo+'">'+top.title+'</span></div>';
						htmlStr = '<div class="cover infoTilte form-table" id="'+top.tableName+top.collateralNo+'">'+htmlStr+
						'<form class="form-margin" action="'+webPath+'/'+top.action+'/updateAjaxByOne" id="'+action+'Ajax_updateByOne">'+top.htmlString+'</form></div>';
					}else if(top.showType=="2"){//列表
						if($(".handle[name='" + top.action + "']").length){
							var $dynamicBlock = $(".handle[name='" + top.action + "']");
							$dynamicBlock.find(".margin_left_20").empty();
						}
                        htmlStr = '<div class="handle title" data-name="'+top.action+'" data-title="'+top.title+'" name="'+top.action+'" data-showtype="2"><span class="sub-title" id="'+top.collateralNo+'">'+top.title+'</span>';
                        if("insInfo" != top.action){
                             htmlStr = htmlStr +'<button class="btn btn-link jia-color" onclick="ShowPledgeDetailInfo.addCollateralFormByRotate(this,\''+collateralNo+'\',\''+classId+'\',2);" title="新增"><i class="i i-jia3"></i></button>';
						}
                        htmlStr =  htmlStr+'</div>';
						htmlStr = '<div class="cover infoTilte list-table" id="'+top.tableName+top.collateralNo+'">'+htmlStr+'<div class="margin_left_20 collapse in">'+top.htmlString+'</div>'+'</div>';
					}
					if($("#"+top.tableName+top.collateralNo).length > 0){
						$("#"+top.tableName+top.collateralNo).find("div:last").html(top.htmlString);
					}else{
						$("#rotate-body"+top.collateralNo).before(htmlStr);
					}
					
					// if("PledgeBaseInfoBillAction" == top.action){
					// 	$("#pledgeDetailBillExport"+top.pledgeNo).remove();
					// 	$("#"+top.tableName+top.pledgeNo).find("button:first").after('<a id="pledgeDetailBillExport'+top.pledgeNo+'" onclick="ShowPledgeDetailInfo.exportPledgeBill(\''+top.pledgeNo+'\');" href="javascript:;">导出</a>');
					// }
				}
				var action1 = top.action;
				action1 = action1.split("/");
				action1 = action1[action1.length - 1];
				var a = action1.substring(0,1);
				a = a.toUpperCase();
				action1 = a + action1.substring(1,action1.length);
				$("#rotate-body"+top.collateralNo).find(".rotate-div[name='"+ action1 +"Action']").remove();
				//如果是新增押品信息,刷新担保总额和比例
				if(action1=="EvalInfo"){
					MfBusCollateralRelDetail.updateCollateral();
					MfBusCollateralRelDetail.updateBaseCollateralInfo(collateralNo);
				}

				//新增权证信息更新押品信息
                if(action1=="CertiInfo"){
                    MfBusCollateralRelDetail.updateBaseCollateralInfo(collateralNo);
                }
			}
			$.ajax({
				url:webPath+"/mfCollateralTable/getListAjax?collateralNo="+collateralNo+"&delFlag=0&dataFullFlag=0",
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						//信息块加完后，加号去掉
						if(data.collateralTableList.length==0){
							$("#add"+collateralNo).hide();
						}
					}
				},error:function(){
					
				}
			});
		}
	};
	//配置信息模块
	var _updateCollateralFormStas=function(collateralNo){
		top.updateFlag = false;
		top.htmlStrFlag = false;
		top.openBigForm(webPath+'/mfCollateralTable/getPageUpdateStas?collateralNo='+collateralNo,'完善资料',function(data){
			_addCollateralFormInfoCall(collateralNo);
			if(top.updateFlag){
				$.ajax({
					url:webPath+"/mfCollateralTable/getListAjax?collateralNo="+collateralNo+"&delFlag=0&dataFullFlag=0",
					type:"POST",
					dataType:"json",
					success:function(data){
						if(data.flag == "success"){
							$("#rotate-body"+collateralNo).empty();
							$.each(data.collateralTableList,function(i,collateralTable){
								_setBlock(collateralTable,i);
							});
							_cusAdd(collateralNo);
						}
					},error:function(){
						
					}
				});
			}
		});
	};
	//删除信息模块
	var _deleteRotate=function(obj,collateralNo){
		var $rotateDiv = $(obj).parents(".rotate-div");
		$.ajax({
			url:webPath+"/mfCollateralTable/updateDelFlagAjax?collateralNo="+collateralNo+"&tableName="+$rotateDiv.data("tablename")+"&delFlag=1",
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag =="success"){
					$rotateDiv.remove();
				}else{
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			},error:function(){
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		});
	};
	var _exportPledgeBill = function(pledgeNo){
		var poCntJson = {
			filePath : "",
			fileName :  "pledgeDetailList.docx",
			templateNo:"1033",
			appId:appId,
			pactId:pactId,
			cusNo:cusNo,
			pledgeNo:pledgeNo,
			saveBtn : "0",
			fileType : 0
		};
		mfPageOffice.openPageOffice(poCntJson);
		//var returnUrl = window.location.href;
		//poCntJson.returnUrl = returnUrl;
		
	}
	return{
		deleteRotate:_deleteRotate,
		updateCollateralFormStas:_updateCollateralFormStas,
		addCollateralFormByRotate:_addCollateralFormByRotate,
		showPledgeInfo:_showPledgeInfo,
		setBlock:_setBlock,
		cusAdd:_cusAdd,
		addCollateralFormInfoCall:_addCollateralFormInfoCall,
		exportPledgeBill:_exportPledgeBill
	};
}(window,jQuery);