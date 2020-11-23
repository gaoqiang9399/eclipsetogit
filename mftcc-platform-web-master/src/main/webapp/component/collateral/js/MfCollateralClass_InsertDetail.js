$(function(){	
	//展示客户模块
	$.each(collateralTableList,function(i,collateralTable){
		//shouType:1是form2是table
		setBlock1(collateralTable.showType,collateralTable.tableDes,collateralTable.action,collateralTable.htmlStr,collateralTable.dataFullFlag,collateralTable.isMust,collateralTable.tableName,i%4+1,collateralTable.delFlag);
		dblclickflag();
	});
	var cusAdd = '<div class="rotate-div"><div class="rotate-obj rotate-borderColor2" id="cus-add"><div class="rotate-des rotate-color2"><div class="rotate-formName pull-left"><i class="i i-jia1"></i></div></div></div></div>';
	$("#rotate-body").append(cusAdd);
	$("#cus-add").click(function(){updateCollateralFormStas();});
	LoadingAnimate.stop();
	$("#pledgeDetailBillExport").remove();
	//暂时去掉
	//$("#PledgeBaseInfoBillAction").prev().find(".formAdd-btn:first").after("<a id='pledgeDetailBillExport' href='javascript:exportPledgeBill();'>导出</a>");
	$("#test").bind("click",function(){
		exportPledgeBill();
	});
});

function exportPledgeBill(){
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
/**
 * 删除押品清单
 * @param obj
 * @param url
 */
function deletePledgeBaseInfoBill(obj , url){
	var pledgeBillNo = url.split("?")[1].split("&")[0].split("=")[1];
	var pledgeNo = url.split("?")[1].split("&")[1].split("=")[1];
	var tableId = $(obj).parent().parent().parent().parent().attr("title");
	tableId = tableId.substring(5,tableId.length);
	$.ajax({
		url:webPath+"/pledgeBaseInfoBill/deleteAjax",
		data:{pledgeBillNo:pledgeBillNo,pledgeNo:pledgeNo,tableId:tableId},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				if(data.dataFullFlag == "1"){
					if(data.htmlStrFlag == "1"){
						var divTable = $(obj).parent().parent().parent().parent().parent();
						$(obj).parent().parent().parent().parent().remove();
						$(divTable).html(data.htmlStr);
					}
				}else if(data.dataFullFlag == "0"){
					$("div[name='PledgeBaseInfoBillAction']").remove();
					$("#rotate-body").empty();
					$.each(data.mfCollateralTableList,function(i,collateralTable){
						setBlock(collateralTable.showType,collateralTable.tableDes,collateralTable.action,collateralTable.htmlStr,collateralTable.dataFullFlag,collateralTable.isMust,collateralTable.tableName,i%4+1,collateralTable.delFlag);
					});
					var cusAdd = '<div class="rotate-div"><div class="rotate-obj rotate-borderColor2" id="cus-add"><div class="rotate-des rotate-color2"><div class="rotate-formName pull-left"><i class="i i-jia1"></i></div></div></div></div>';
					$("#rotate-body").append(cusAdd);
					$("#cus-add").click(function(){updateCollateralFormStas();});
				}
			}else{
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
			top.LoadingAnimate.stop();
		},error:function(data){
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	
}

function setBlock1(showType,title,name,htmlStr,dataFullFlag,isMust,tableName,color,delFlag){
	//把PledgeBaseInfoAction改为pledgeBaseInfo
    if(tableName == "mf_car_check_form"){
    	showType = "2";
    }
	var _name = name;
	if(name!=null&&name!=""){
		_name = "/"+name.substring(0,1).toLowerCase()+name.substring(1);
		_name = _name.replace("Action","");
		_name = webPath+_name;
	}
	var rotateColor="rotate-color";	
	var rotateBorderColor="rotate-borderColor";
	var rotateTubiaoBac="rotate-tubiaoBac";
	if(color){
		rotateColor="rotate-color"+color;
		rotateBorderColor="rotate-borderColor"+color;
		rotateTubiaoBac="rotate-tubiaoBac"+color;
	}
	var iconClass = "info-box-icon i ";
	var backColor= "";
	if(color==1){
		iconClass =iconClass + "i-dengji";
		backColor = "#81B960";
	}else if(color==2){
		iconClass =iconClass + "i-jibenxinxi"
		backColor = "#FCB865";
	}else if(color==3){
		iconClass =iconClass + "i-caiji"
		backColor = "#5FC8DB";
	}else if(color==4){
		iconClass =iconClass + "i-qiye"
		backColor = "#8EAFE4";
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
		delBtnHtml1 = "<button class='rotate-del i i-lajitong' title='删除' onclick='deleteRotate(this);'></button>";
	}
	if(dataFullFlag == "0"){
		if(delFlag == "0"){
			htmlStr = "";
			htmlStr = "<div class='rotate-div' name='"+name+"' data-showtype='"+showType+"' data-name='"+name+"' data-title='"+title+"' data-tablename='"+tableName+"' data-ismust='"+isMust+"'>"+
			"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtml1+
			"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+";'><li class='iconStyle "
			+ iconClass
			+ "'></li></div><div style='margin-left:20px' class='rotate-formName pull-left'>登记"+title+"</div></div>" +
			"<div class='rotate-opre'>" +
			"<button class='rotate-add i i-jia2' onclick='addCollateralFormByRotate(this);'></button>"+delBtnHtml1+
			"</div>"+
			"</div>"+
			"</div>";
			$("#rotate-body").append(htmlStr);
			htmlStr = "";
		}
		return false;
	}
	if(showType == "2"){
		if(tableName != "mf_car_check_form"){
            addBtnHtml = "<button class='btn btn-link formAdd-btn' onclick='addCollateralFormInfo(this,\""+showType+"\");' title='新增'><i class='i i-jia3'></i></button>";
        }
        //addBtnHtml = "";
    }
	var htmlStrThis = "";
	if(showType == "1"){
		var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>"
		htmlStrThis = htmlStrThis + "<div class='dynamic-block' title='"+title+"' name='"+name+"'>"
		+ "<div class='form-table'>"
		+ "<div class='title' ><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"/*+isMustHtml*/+addBtnHtml+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"'>"
		+"<form action='"+_name+"/updateAjaxByOne' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
		+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-add").before(htmlStrThis);
		$(".block-add").before(clearDiv);
	}else if(showType == "2"){
		if(name =="allApp"){
			htmlStrThis = htmlStrThis + "<div class='dynamic-block'>"
			+ "<div class='list-table'>"
			+ "<div class='title'><span class='formName'><i class='i i-ding blockDian'></i>"+title+"</span></div>"
			+ "<div class='content' name='"+name+"'>"+htmlStr+"</div>"
			+ "</div>"
			+ "</div>";
		}else{
			var collapseButtonHtml2="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
			htmlStrThis = htmlStrThis + "<div class='dynamic-block' title='"+title+"' name='"+name+"'>"
			+ "<div class='list-table'>"
			+ "<div class='title' ><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"/*+isMustHtml*/+addBtnHtml+collapseButtonHtml2+"</div>"
			+ "<div class='content collapse in' id='"+name+"' >"+htmlStr+"</div>"
			+ "</div>"
			+ "</div>";
		}
		$(".block-add").before(htmlStrThis);
		$(".block-add").before(clearDiv);
	}
};
function setBlock(showType,title,name,htmlStr,dataFullFlag,isMust,tableName,color,delFlag){
	
	var rotateColor="rotate-color";	
	var rotateBorderColor="rotate-borderColor";
	var rotateTubiaoBac="rotate-tubiaoBac";
	if(color){
		rotateColor="rotate-color"+color;
		rotateBorderColor="rotate-borderColor"+color;
		rotateTubiaoBac="rotate-tubiaoBac"+color;
	}
	var iconClass = "info-box-icon i ";
	var backColor= "";
	if(color==1){
		iconClass =iconClass + "i-dengji";
		backColor = "#81B960";
	}else if(color==2){
		iconClass =iconClass + "i-jibenxinxi";
		backColor = "#FCB865";
	}else if(color==3){
		iconClass =iconClass + "i-caiji";
		backColor = "#5FC8DB";
	}else if(color==4){
		iconClass =iconClass + "i-qiye";
		backColor = "#8EAFE4";
	}
	var delBtnHtml1 = "";
	var isMustHtml1 = "";
	if(isMust == "1"){
		isMustHtml = "<span class='formMust-span'>*</span>";
		isMustHtml1 = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
	}else{
		delBtnHtml1 = "<button class='rotate-del i i-lajitong' title='删除' onclick='deleteRotate(this);'></button>";
	}
	if(dataFullFlag == "0"){
		if(delFlag == "0"){
			htmlStr = "";
			htmlStr = "<div class='rotate-div' name='"+name+"' data-showtype='"+showType+"' data-name='"+name+"' data-title='"+title+"' data-tablename='"+tableName+"' data-ismust='"+isMust+"'>"+
			"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtml1+
			"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+";'><li class='iconStyle "
			+ iconClass
			+ "'></li></div><div style='margin-left:20px' class='rotate-formName pull-left'>登记"+title+"</div></div>" +
			"<div class='rotate-opre'>" +
			"<button class='rotate-add i i-jia2' onclick='addCollateralFormByRotate(this);'></button>"+delBtnHtml1+
			"</div>"+
			"</div>"+
			"</div>";
			$("#rotate-body").append(htmlStr);
			htmlStr = "";
		}
		return false;
	}
};
function addCollateralFormByRotate(obj){
	var $rotateDiv = $(obj).parents(".rotate-div");
	var title =$rotateDiv.data("title");
	var action = $rotateDiv.data("name");
	top.action = action;
    var callBack = action;
	//处理action为controller;
	action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
	action =action.replace("Action","");
	top.title = title;
	top.isMust = $rotateDiv.data("ismust");
	top.showType = $rotateDiv.data("showtype");
	var inputUrl =action + "/input?collateralNo="+collateralNo+"&classId="+classId;
	top.addFlag = false;
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.openBigForm(inputUrl,title,function(){
        addCollateralFormInfoCall(callBack);
        refreshHeadInfo(collateralNo);
	});
	//top.createShowDialog(inputUrl,title,addCollateralFormInfoCall);
};
function addCollateralFormInfo(obj,showType){//当客户信息是列表，继续增加一条记录时
	var $dynamicBlock = $(obj).parents(".dynamic-block");
	var title = $dynamicBlock.attr("title");
	var action = $dynamicBlock.attr("name");
	var callBack = action;
	//处理action为controller;
	action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
	action =action.replace("Action","");
	var inputUrl =action + "/input?collateralNo="+collateralNo+"&classId="+classId;
	top.action = action;
	top.showType = showType;
	top.title = title;
	top.addFlag = false;
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.getTableUrl = action + "_getListPage?collateralNo="+collateralNo;
	top.openBigForm(inputUrl,title,function(){
		addCollateralFormInfoCall(callBack);
		if(callBack=="EvalInfo"){
            refreshHeadInfo(collateralNo);
        }
	});
};
function addCollateralFormInfoCall(callBack){
	if(top.addFlag){
		$("#rotate-body").find(".rotate-div[name="+callBack+"]").remove();
		if($(".dynamic-block[name="+callBack+"]").length){
			var $dynamicBlock = $(".dynamic-block[name="+callBack+"]");
			$dynamicBlock.find(".formDel-btn").remove();
			$dynamicBlock.show();
			if(top.htmlStrFlag){
				if(top.showType == "1"){
					$dynamicBlock.find(".content form").empty();
					$dynamicBlock.find(".content form").html(top.htmlString);
					$dynamicBlock.find(".formAdd-btn").remove();
				}else{
					$dynamicBlock.find(".content").empty();
					$dynamicBlock.find(".content").html(top.htmlString);
				}
			}
		}else{
			if(top.htmlStrFlag){
				setBlock1(top.showType,top.title,top.action,top.htmlString,"1",top.isMust,null,null,null);	
				if("PledgeBaseInfoBillAction" == top.action){
                    //暂时去掉
					//$("#PledgeBaseInfoBillAction").prev().find(".formAdd-btn:first").after("<a id='pledgeDetailBillExport' href='javascript:exportPledgeBill();'>导出</a>");
				}
				dblclickflag();	
			}
		}
        var action1 = top.action;
        action1 = action1.split("/");
        action1 = action1[action1.length - 1];
        var a = action1.substring(0,1);
        a = a.toUpperCase();
        action1 = a + action1.substring(1,action1.length);
        //新增权证信息更新押品信息
        if(action1=="CertiInfoAction"){
            jQuery.ajax({
                url: webPath + "/mfBusCollateralRel/getBaseCollateralHtmlAjax",
                data: {collateralId: collateralNo},
                success: function (data) {
                    if (data.flag = "success") {
                        if (data.vouType != "2") {//抵质押信息
                            $("#PledgeBaseInfoAction").html(data.htmlStr);
                        }
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }
            });
        }

		
	}
};
function deleteRotate(obj){
	var $rotateDiv = $(obj).parents(".rotate-div");
    alert("确定要删除？",2,function () {
        $.ajax({
            url: webPath + "/mfCollateralTable/updateDelFlagAjax?collateralNo=" + collateralNo + "&tableName=" + $rotateDiv.data("tablename") + "&delFlag=1",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    $rotateDiv.remove();
                } else {
                    //alert(top.getMessage("FAILED_DELETE"),0);
                }
            }, error: function () {
                //alert(top.getMessage("FAILED_DELETE"),0);
            }
        });
    });
	
};
		
//获取业务信息
function getBusInfo(appId){
	LoadingAnimate.start();
	window.location.href=webPath+"/mfBusApply/getSummary?appId="+appId;
};
//获取押品信息
function getPleInfo(){
	LoadingAnimate.start();
	window.location.href=webPath+"/mfBusPledge/getPledgeById?cusNo="+cusNo+"&appId="+appId;
};
//获取合同信息
function getPactInfo(){
	LoadingAnimate.start();
	window.location.href=webPath+"/mfBusPact/getById?appId="+appId;
};
function getBusDetail(obj,urlThis){
	var parm=urlThis.split("?")[1];
	var parmArray=parm.split("&");
	var appIdThis = parmArray[0].split("=")[1];
	var appStsThis = parmArray[1].split("=")[1];
	if(appStsThis == "4"){//表示申请审批通过
		window.location.href=webPath+"/mfBusPact/getById?appId="+appIdThis;
	}else{
		window.location.href=webPath+"/mfBusApply/getSummary?appId="+appIdThis;
	}
};
function addBlockInfo(){
	//如果客户基础信息不存在，则先录入基本信息
	/*if(cusBaseFlag == '0'){
		addBaseInfo();
		return false;
	}*/
	$.ajax({
		url:webPath+"/mfCollateralTable/checkCusInfoIsFull?cusNo="+cusNo,
		type:"post",
		dataType:"json",
		success:function(data){
			
			if(data.fullFlag == '1'){//全部都填写了
				//alert("客户资料已经全部完善",0);
			}else if(data.fullFlag == '0'){
				top.addFlag = false;
				top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
				top.htmlString = null;
				top.action = null;
				top.title = null;
				top.name = null;
				top.cusNo = cusNo;
				top.showType = null;
				//top.baseInfo="0";标识 该表单信息是否是客户的基本信息
				top.openBigForm(webPath+'/mfCollateralTable/getListPage?cusNo='+cusNo+'&dataFullFlag=0','选择表单',addCollateralFormInfoCall);
			}
		},error:function(){
			
		}
	});
	
};
function updateCollateralFormStas(){
	top.updateFlag = false;
	top.addFlag = false;
	top.openBigForm(webPath+'/mfCollateralTable/getPageUpdateStas?collateralNo='+collateralNo,'完善资料',function(){
		addCollateralFormInfoCall();
		if(top.updateFlag){
			$.ajax({
				url:webPath+"/mfCollateralTable/getListAjax?collateralNo="+collateralNo+"&delFlag=0&dataFullFlag=0",
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						$("#rotate-body").empty();
						$.each(data.collateralTableList,function(i,collateralTable){
							setBlock1(collateralTable.showType,collateralTable.tableDes,collateralTable.action,collateralTable.htmlStr,collateralTable.dataFullFlag,collateralTable.isMust,collateralTable.tableName,i%4+1,"0");
						});
						var cusAdd = '<div class="rotate-div"><div class="rotate-obj rotate-borderColor2" id="cus-add"><div class="rotate-des rotate-color2"><div class="rotate-formName pull-left"><i class="i i-jia1"></i></div></div></div></div>';
						$("#rotate-body").append(cusAdd);
						$("#cus-add").click(function(){updateCollateralFormStas();});
					}
				},error:function(){
					
				}
			});
		}
	});
};
function getCollateralInfoById(obj,getUrl){//根据列表超链接获得信息详情，支持编辑
	var $dynamicBlock = $(obj).parents(".dynamic-block");
	var title = $dynamicBlock.attr("title");
	var action = $dynamicBlock.attr("name");
	//处理action为controller;
	action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
	action =action.replace("Action","");
	top.action = action;
	top.showType = "2";
	top.addFlag = false;
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.getTableUrl = action + "_getListPage?collateralNo="+collateralNo;
	top.openBigForm(getUrl,title,addCollateralFormInfoCall);
};
			
//删除文件
function delFile(){
	var srcPath = "/tmp/";
	$.ajax({
		url:webPath+"/uploadFile/delFile",
		data:{srcPath:srcPath},
		type:'post',
		dataType:'json',
		success:function(data){
			
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

