var MfCollateralClass=function(window, $){
	/****************************************押品配置--页面初始化 begin****************************************/
	//页面初始化函数
	var _init = function(){
		//金融质押品A
		var htmlStr = getcollateralFormConfigHtmlA();
		$("#collateralFormContent_table_div_A").append(htmlStr);		
	};
	
	//获取金融质押品Ahtml
	var getcollateralFormConfigHtmlA = function(){
		var htmlStr = "";
		$.each(mfCollateralClassListA, function(i, mfCollateralClass) {
			//押品预警html
			var mfMsgPledgeHtml = getMfMsgPledgeHtml(mmpMap[mfCollateralClass.classId],mfCollateralClass.classSecondName);
			htmlStr = htmlStr + '<p class="p-content" id="collateralformName'+mfCollateralClass.classId+'">'
				+'<a href="javascript:void(0);" onclick=\'MfCollateralClass.getById("'+mfCollateralClass.classId+'","'+mfCollateralClass.classSecondName+'")\'>'
				+mfCollateralClass.classSecondName
				+'</a>'
				+' -- '
				+mfCollateralClass.vouType
				+' -- '
				+mfCollateralClass.classModel
				+'<a class="config-font" href="javascript:void(0);" onclick=\'MfCollateralClass.editCollateralForm("'+mfCollateralClass.classId+'","'+mfCollateralClass.classSecondName+'")\'>'
				+'配置'
				+'</a>'
				+'<span class="config-font" name="mfcollateralclassname">'
				+'<a href="\webPath+/mfCollateralClass/updateUserFlagAjax.action?classId="mfCollateralClass.classId"\' id="aaa">'
				+mfCollateralClass.useFlag
				+'</a>'
				+'</span>'
				+'<div class="p-content">'
				+'<div>'
				+'<span>预警项设置</span>'
				+'<a href="javascript:void(0);" onclick=\'MfCollateralClass.addMfMsgPledge("'+mfCollateralClass.classId+'","'+mfCollateralClass.classSecondName+'")\' class="padding_left_15 pointer">'
				+'新增'
				+'</a>'
				+'</div>'
				+'<div>'
				+mfMsgPledgeHtml
				+'</div>'
				+'</div>'
				+'</p>';
				
		});
		return htmlStr;
	};
	
	var getMfMsgPledgeHtml = function(mfMsgPledgeList,classSecondName){
		var htmlStr = "";
		var checkHtml = "";
		if(mfMsgPledgeList == undefined || mfMsgPledgeList == null){
			return htmlStr;
		}
		$.each(mfMsgPledgeList,function(i,mfMsgPledge){
			if(mfMsgPledge.useFlag == 0){
				checkHtml = '<span class="checkbox-span"><i class="i i-gouxuan1"></i></span>';
			}else if(mfMsgPledge.useFlag == 1){
				checkHtml = '<span class="checkbox-span curChecked"><i class="i i-gouxuan1"></i></span>';
			}
			htmlStr = htmlStr + '<div>'
				+'<span class="item-checkbox">'
				+checkHtml
				+'<a href="javascript:void(0);" onclick=\'MfCollateralClass.getMfMsgPledge("'+mfMsgPledge.id+'","'+classSecondName+'")\';>'
				+mfMsgPledge.funName
				+'</a>'
				+'</span>'
				+'</div>';
		});
		return htmlStr;
	};
	
	//配置押品表单信息
	var _editCollateralForm = function(classId,classSecondName){
		var url = webPath+"/mfCollateralFormConfig/getAllList?id="+classId;
		top.updateFlag = false;
		top.mfCollateralClass = "";
		top.window.openBigForm(url,classSecondName,function(){
			
		});
		
	};
	//查看更多押品动态表单配置
	var _getCollateralFormByPage=function(){
		var url=webPath+"/mfCollateralClass/getCollateralTypeList";
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	};
	//新增押品类型
	var _addMfCollateralType=function(classFirstNo,classFirstName){
		var url = webPath+"/mfCollateralClass/collateralTypeInput?classFirstNo="+classFirstNo+"&classFirstName="+classFirstName;
		top.flag="";
		top.classSecondName="";
		top.classId="";
		window.parent.openBigForm(url,"押品类型配置",function(){
			if(top.flag!=""){
				var vouType = " -- " + top.vouType;
				var classModel = " -- " + top.classModel;
				var id = "collateralformName" + top.classId;
				var htmlStr = '<p class="p-content" id='+id+'>'+
				'<a href="javascript:void(0);" onclick="MfCollateralClass.getById(\''+top.classId+'\',\''+top.classSecondName+'\')">'+
				top.classSecondName+'</a>'+vouType+classModel+'<a class="config-font" href="javascript:void(0);" onclick="MfCollateralClass.editCollateralForm(\''+top.classId+'\',\''+top.classSecondName+'\')"> 配置</a>'+
				'<span class="config-font" name="mfcollateralclassname"><a href=webPath+"/mfCollateralClass/updateUserFlagAjax.action?classId='+top.classId+
				'" id="aaa">'+top.useFlag+'</a></span></p>';
				if(top.classFirstNo == "A"){
					$("#collateralFormContent_table_div_A").append(htmlStr);
				}else if(top.classFirstNo == "B"){
					$("#collateralFormContent_table_div_B").append(htmlStr);
				}else if(top.classFirstNo == "C"){
					$("#collateralFormContent_table_div_C").append(htmlStr);
				}else if(top.classFirstNo == "D"){
					$("#collateralFormContent_table_div_D").append(htmlStr);
				}else if(top.classFirstNo == "E"){
                    $("#collateralFormContent_table_div_E").append(htmlStr);
				}else if(top.classFirstNo == "F"){
                    $("#collateralFormContent_table_div_F").append(htmlStr);
                }
				$("span[name='mfcollateralclassname']").collRcswitcher({name:"useFlag",onText:"启用",offText:"停用"});
				
			}
		});
	};
	//查询押品类型配置（用于修改）
	var _getById = function(classId,classSecondName){
		var url = webPath+"/mfCollateralClass/getById?classId="+classId;
		top.window.openBigForm(url,classSecondName,function(){
			if(top.flag!=""){
				var vouType = " -- "+top.vouType;
				var classModel = " -- "+top.classModel;
				var id = "collateralformName"+top.classId;
				var htmlStr = '<p class="p-content" id='+id+'>'+
				'<a href="javascript:void(0);" onclick="MfCollateralClass.getById(\''+top.classId+'\',\''+top.classSecondName+'\')">'+
				top.classSecondName+'</a>'+vouType+classModel+'<a class="config-font" href="javascript:void(0);" onclick="MfCollateralClass.editCollateralForm(\''+top.classId+'\',\''+top.classSecondName+'\')"> 配置</a>'+
				'<span class="config-font" name="mfcollateralclassname"><a href=webPath+"/mfCollateralClass/updateUserFlagAjax.action?classId='+top.classId+
				'" id="aaa">'+top.useFlag+'</a></span></p>';
				id = "#"+id;
				$(id).replaceWith(htmlStr);
				$("span[name='mfcollateralclassname']").collRcswitcher({name:"useFlag",onText:"启用",offText:"停用"});
			}
		});
	};
	//添加押品预警配置信息
	var _addMfMsgPledge = function(classId,classSecondName){
		var url = webPath+"/mfMsgPledge/input?classId="+classId+"&classSecondName="+classSecondName;
		top.window.openBigForm(url,"押品预警配置",function(){
			
		});
	};
	//修改押品预警配置信息
	var _getMfMsgPledge = function(id,classSecondName){
		var url = webPath+"/mfMsgPledge/getById?id="+id+"&classSecondName="+classSecondName;
		top.window.openBigForm(url,"押品预警配置",function(){
			
		});
	};
	return{
		init:_init,
		editCollateralForm:_editCollateralForm,
		getCollateralFormByPage:_getCollateralFormByPage,
		addMfCollateralType:_addMfCollateralType,
		getById:_getById,
		addMfMsgPledge:_addMfMsgPledge,
		getMfMsgPledge:_getMfMsgPledge
	};
}(window, jQuery);