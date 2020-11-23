$(function(){
	top.infIntegrity = null;
	$("body").mCustomScrollbar({
		advanced:{
			updateOnContentResize:true
		},
		callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
			whileScrolling: function(){
				if ($(".changeval").length>0) {
					$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
				}
			}
		}
	});
	showApproveHis();
	if(keepStatus!='0' && keepStatus!='1' && keepStatus!='2' && keepStatus!='3' && keepStatus!='4'){
		$(".cus-tag").addClass("btn-dodgerblue");
	}
	
	if (keepStatus == '0' || keepStatus == '1') {
		/*红色
		$(".class-type").addClass("i-blacklist");
		$(".classtype-name").addClass("i-blacklist");*/
		$(".class-type").addClass("i-good");
		$(".classtype-name").addClass("i-good");

		$("#instockBtnAgain").attr("disabled", true).attr("class","btn btn-opt set-disabled");

		$("#outstockBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#handleBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#checkBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
	} else if(keepStatus == '2' || keepStatus == '3' || keepStatus == '4'){
		/*默认黑色*/
		$(".class-type").addClass("i-good");
		$(".classtype-name").addClass("i-good");
		$("#outstockBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#handleBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#checkBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
	} else if (keepStatus == '5' || keepStatus == '6' || keepStatus == '8' || keepStatus == '9') {
		$(".class-type").addClass("i-good");
		$(".classtype-name").addClass("i-good");
		$("#instockBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
	} else if(keepStatus == '7' || keepStatus == '10' || keepStatus == '11'){
		/*灰色
		$(".class-type").addClass("i-color");
		$(".classtype-name").addClass("i-color");*/
		$(".class-type").addClass("i-blacklist");
		$(".classtype-name").addClass("i-blacklist");
		$("#instockBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#instockBtnAgain").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#outstockBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#handleBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
		$("#checkBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
	}
	//合同不完结，控制出库操作不可用。为空，说明未关联业务
	if(busSts!="7"&&busSts!="8"){
		$("#outstockBtn").attr("disabled", true).attr("class","btn btn-opt set-disabled");
	}
});

function refreshHeadInfo(collateralNo){
	jQuery.ajax({
		url:webPath+"/pledgeBaseInfo/refreshPageHeadAjax",
		data:{collateralNo:collateralNo},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			$("#envalueAmt").html(data.displayColRate);
			$("#receiptsAmount").html(data.displayColValue);
			$("#keepStatusName").html(data.keepStatusName);
			/* if(top.deteleFlag){
			} */
			top.tableData =data.tableData;
			closeCallBack1();
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}

//押品详情/编辑页面,此方法暂不要
function ajaxGetById(obj,getUrl){//根据列表超链接获得信息详情，支持编辑
	var $obj = $(obj);
	/*var title = $obj.parents(".block-info").find(".title").find("span").text();*/ 
	var title = $obj.parents(".clearfix").find(".title").find("span").text(); //block-info类已经不用 用clearfix 代替
	/*top.obj = $obj.parents(".block-info").find(".list-add");*/
	top.obj = $obj.parents(".clearfix").find(".list-add");//block-info类已经不用 用clearfix 代替
	top.flag = false;
	top.replaceFlag= false;
	top.getTableUrl = webPath+"/mfBusPledge/getListPage?cusNo="+cusNo+"&appId="+appId;
	top.openBigForm(getUrl,title,closeCallBack1);
};
//清单押品详情/编辑页面
function ajaxGetDetailById(obj,getUrl){//根据列表超链接获得信息详情，支持编辑
	top.tableId = $("#pleDetail-div").find("table").attr("title");
	top.flag = false;
	top.replaceFlag= false;
	top.editDetailFlag = false;
	top.remove = false;
	top.openBigForm(getUrl,'修改信息',refreshPleInfo);
};
function closeCallBack1(){
	if(top.flag){
        var tableHtml;
		if(top.editDetailFlag){
			tableHtml = $(top.tableData).find("tbody").html();
			$("#pleDetail-div").find(".ls_list tbody").html(tableHtml);
		}else if(top.remove){
			tableHtml = $(top.tableData).find("tbody").html();
			$("#pleDetail-div").find(".ls_list tbody").html(tableHtml);
			
			tableHtml = $(top.tableData1).find("tbody").html();
			$("#pleRemove-div").find(".ls_list tbody").html(tableHtml);
		}
		//adjustheight();
	}
};

//更换押品信息的弹出层
function replaceInput(obj,getUrl){//根据列表超链接获得信息详情，支持编辑
	var $obj = $(obj);
	var title ="押品替换";
	/*top.obj = $obj.parents(".block-info").find(".list-table");*/
	top.obj = $obj.parents(".clearfix").find(".list-table");////block-info类已经不用 用clearfix 代替
	top.addFlag = false;
	top.getTableUrl = webPath+"/mfBusPledgeDetail/getListPage?pledgeNo="+pledgeNo;
	top.openBigForm(getUrl,title,closeCallBack1);
};

//多业务大于3条时，弹层列表页面
function getMultiBusList(){
	top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo,"多笔业务",function(){});
}

//押品解押弹出层
function removeInput(obj,inputUrl){
	var title = "押品解押";
	top.tableId = $("#pleDetail-div").find("table").attr("title");
	top.flag = false;
	top.remove = false;
	top.editDetailFlag = false;
	top.openBigForm(inputUrl,title,refreshPleInfo);
}


//获取客户信息
function getCusInfo(){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId;
}

//获取申请信息
function getBusInfo(){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfBusApply/getSummary?appId="+appId;
}
//获取合同信息
function getPactInfo(){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfBusPact/getById?appId="+appId;
}

//合同资料
function pleDocInfo(){
	top.window.openBigForm(webPath+'/docManage/pubUpload?relNo='+appId+'&cusNo='+cusNo+'&scNo='+scNo,"反担保资料",viewCloseCallBack);
}
//押品评估
function addEvalInfo(){
	top.window.openBigForm(webPath+'/evalInfo/getInsertPage?collateralId='+collateralNo,"评估",viewCloseCallBack);
}

//押品检查
function addChkInfo(){
	top.window.openBigForm(webPath+'/chkInfo/input?collateralNo='+collateralNo+'&classId='+classId,"检查",addCollateralFormInfoCall);
}
//押品检查
function instockView(){
	top.window.openBigForm(webPath+'/keepInfo/getViewPage?collateralId='+collateralNo+'&keepType=1',"入库信息",viewCloseCallBack);
}

function inStockKeepInfo(){
	
	jQuery.ajax({
		url:webPath+"/keepInfo/isDataExistsAjax",
		data:{collateralId:collateralNo,collateralMethod:collateralMethod,keepType:'1'},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			//collateralName=encodeURIComponent(encodeURIComponent(collateralName));
			if(data.result=='1'){
				
				top.window.openBigForm(webPath+'/keepInfo/getInsertPage?collateralId='+collateralNo+'&collateralName='+collateralName+'&collateralMethod='+collateralMethod,"入库信息",refreshHeadInfo(collateralNo));
			}else if(data.result=='0'){
				top.window.openBigForm(webPath+'/keepInfo/getViewPage?collateralId='+collateralNo+'&keepType=1',"入库信息",viewCloseCallBack);
			}
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	
}

function outStockKeepInfo(){
	jQuery.ajax({
		url:webPath+"/keepInfo/isDataExistsAjax",
		data:{collateralId:collateralNo,collateralMethod:collateralMethod,keepType:'2'},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			//collateralName=encodeURIComponent(encodeURIComponent(collateralName));
			if(data.result=='1'){
				top.window.openBigForm(webPath+'/keepInfo/getOutStockPage?collateralId='+collateralNo+'&collateralName='+collateralName+'&collateralMethod='+collateralMethod,"出库信息",refreshHeadInfo(collateralNo));
			}else if(data.result=='0'){
				top.window.openBigForm(webPath+'/keepInfo/getViewPage?collateralId='+collateralNo+'&keepType=2',"出库信息",viewCloseCallBack);
			}
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	
}

function handleKeepInfo(){
	 
	jQuery.ajax({
		url:webPath+"/keepInfo/isDataExistsAjax",
		data:{collateralId:collateralNo,collateralMethod:collateralMethod,keepType:'3'},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			//collateralName=encodeURIComponent(encodeURIComponent(collateralName));
			if(data.result=='1'){
				top.window.openBigForm(webPath+'/keepInfo/getHandlePage?collateralId='+collateralNo+'&collateralName='+collateralName+'&collateralMethod='+collateralMethod,"处置信息",refreshHeadInfo(collateralNo));
			}else if(data.result=='0'){
				top.window.openBigForm(webPath+'/keepInfo/getViewPage?collateralId='+collateralId+'&keepType=3',"处置信息",viewCloseCallBack);
			}
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	
}

function inStock(collateralId,collateralMethod){
	jQuery.ajax({
		url:webPath+"/mfBusCollateral/inStockAjax",
		data:{collateralId:collateralId,collateralMethod:collateralMethod},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			window.location.href=webPath+"/mfBusCollateral/getByCollateralId?collateralId="+data.collateralId;
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}

function outStock(collateralId,collateralMethod){
	jQuery.ajax({
		url:webPath+"/mfBusCollateral/outStockAjax",
		data:{collateralId:collateralId,collateralMethod:collateralMethod},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			window.location.href=webPath+"/mfBusCollateral/getByCollateralId?collateralId="+data.collateralId;
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}

function handleCollateral(collateralId,collateralMethod){
	jQuery.ajax({
		url:webPath+"/mfBusCollateral/handleCollateralAjax",
		data:{collateralId:collateralId,collateralMethod:collateralMethod},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			window.location.href=webPath+"/mfBusCollateral/getByCollateralId?collateralId="+data.collateralId;
		},error:function(data){
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
function collateralWarning(){

	window.location.href=webPath+"/mfBusCollateral/collateralWarning";

}

function keepInfo(){ 
	top.openBigForm(webPath+'/keepInfo/getKeepByCollateralId?collateralId=' + collateralNo,
		'押品保管信息', function() {});
};

function viewCloseCallBack(){
	
};


// 重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key, data) {
    if (key == "certificateName") {
        data["certificateNo"] = $("input[name='certificateNo']").val();
    }
    if (key == "commonName") {
        data["commonNo"] = $("input[name='commonNo']").val();
    }
}

//单字段编辑的保存回调方法。
function oneCallback(data,disVal) {
	var name = data[0].name;
	var value = data[0].value;
    if(name=="maintenanceType" || name == "insType"){//养护类型
        BASE.oneRefreshTable(name,disVal);
    }else{
        BASE.oneRefreshTable(name,value);
    }
    var $_form = this;
    var formAction = $_form.attr("action");
    if(formAction == webPath+"/certiInfo/updateAjaxByOne") {//权证单字段更新刷新押品基础表
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
function showApproveHis(){
		//获得审批历史信息
		showWkfFlowVertical($("#wj-modeler2"),keepId,"17","inout_stock_approval");
		$("#inOutStockApproveHis").show();
};

function inStockKeepInfoAgain(){
    top.window.openBigForm(webPath+'/keepInfo/getInsertPageAgain?collateralId='+collateralNo+'&collateralName='+collateralName+'&collateralMethod='+collateralMethod,"补货入库",refreshHeadInfo(collateralNo));
};


		