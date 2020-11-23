;
var getExamDocConfig=function(window,$){
	var _init=function(){
		if(docBizSceConList.length == scenceList.length){
			$("#scenceAdd").hide();
		}
		$(".content-div").width($(".right-doc").width()-150);
		$.each(docBizSceConList,function(i,docBizSceCon){
			if(docBizSceCon.scNo){
				var inputStr = "";
				var spanStr = "";
				if(docBizSceCon.ifMustInput == "1"){
					inputStr = '<input type="checkbox" onclick="changeIsMust(this);" checked=true >';
					spanStr = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>必</span>";
				}else{
					inputStr = '<input type="checkbox" onclick="changeIsMust(this);">';
				}
				var htmlStr = '<li class="docTypeItem" data-doctype="'+docBizSceCon.docType+'" data-docno="'+docBizSceCon.docSplitNo+'" data-formno="'+docBizSceCon.formId+'" data-ismust="'+docBizSceCon.ifMustInput+'">'
								+'<div  class="hover-div"><div class="hover-info"><label style="cursor: pointer;font-weight: normal;">'+inputStr+'是否必填</label></div>'
								+'<div class="hover-info"><span class="i i-lajitong1 span-lajitong" onclick="removeDocTypeItem(this);"></span>'
								+'</div></div>'
								+'<div class="docTypeShow">'+spanStr+docBizSceCon.docSplitName+'</div></li>';
				var $scenceItem	= $("#scenceList").find(".scenceItem[name="+docBizSceCon.scNo+"]");
				$scenceItem.show();
				$(".content-div").width($(".right-doc", $scenceItem).width()-150);
				$scenceItem.find("ul .docTypeItem1").before(htmlStr);
				var $spanCount = $scenceItem.find(".span-count");
				var count = parseInt($spanCount.data("count"))+1;
				$spanCount.data("count",count);
				$spanCount.html("(" + count + ")");
			}
		});
		
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$(".content-div").mCustomScrollbar({
			advanced:{ 
				theme: "minimal-dark",
				updateOnContentResize:true
			},
			horizontalScroll : true
		});
		/*$("#scenceList p:first").remove();
		$("span[class='btn btn-link-theme del-btn']").remove();
		$("#scenceList").find(".scenceItem").each(function(i,obj){
			if($(obj).attr("name")=="0000000010"){
				$(obj).show();
			}else{
				$(obj).remove();
			}
		});*/
		//_updateJT();
	};
	var _updateJT=function(){
		$scenceItem = $(".content-div .mCSB_scrollTools:hidden").parents(".scenceItem");
		$scenceItem.find(".content-div .docTypeItem1").css("display","inline-block");
		$scenceItem.find(".jiaDiv .docTypeItem1").hide();
		
		$scenceItem1 = $(".content-div .mCSB_scrollTools:visible").parents(".scenceItem");
		$scenceItem1.find(".content-div .docTypeItem1").hide();
		$scenceItem1.find(".jiaDiv .docTypeItem1").css("display","inline-block");
		$(".content-div").mCustomScrollbar("update");
	};
	var _initScenceList=function(){
		$("#scenceList p:first").remove();
		$("span[class='btn btn-link-theme del-btn']").remove();
		$("#scenceList").find(".scenceItem").each(function(i,obj){
			if($(obj).attr("name")=="loanAfterExamine"){
				$(obj).show();
			}else{
				$(obj).remove();
			}
		});
	};
	return{
		init:_init,
		initScenceList:_initScenceList
	};
}(window,jQuery);
var eventObj;
function selectCusTypeMutiDialog1() {
	selectCusTypeMutiDialog(cusTypeMutiDialogCall, $("input[name=cusType]").val());
};

function selectKindMutiDialog1() {
	selectKindMutiDialog(kindMutiDialogCall, $("input[name=kindNo]").val());
};
function selectScenceMutiDialog1() {
	var length = $("#scenceList").find(".scenceItem:visible").length;
	var scNo = "";
	$("#scenceList").find(".scenceItem:visible").each(function(i,o){
		if(i == length-1){
			scNo = scNo + $(this).attr("name");
		}else{
			scNo = scNo + $(this).attr("name")+"@";
		}
	});
	selectScenceMutiDialog(scenceMutiDialogCall, scNo);
};
function getDocTypeData(obj){
	var doctype="";
	var docSplitNo = "";
	var length = $(obj).parents(".scenceItem").find(".docTypeItem").length;
	$(obj).parents(".scenceItem").find(".docTypeItem").each(function(i,o){
		if(i == length-1){
			docSplitNo = docSplitNo + $(this).data("docno");
			doctype = doctype + $(this).data("doctype");
		}else{
			docSplitNo = docSplitNo + $(this).data("docno")+"@";
			doctype = doctype + $(this).data("doctype")+"@";
		}
	});
	mySelectDocTypeDialog(docTypeSelCall,docSplitNo,doctype);
};
function mySelectDocTypeDialog(callback,docSplitNo,docType){
	var theEvent = window.event || arguments.callee.caller.arguments[0];
	//var srcElement = theEvent.srcElement || theEvent.target;
	//var srcElement = document.getElementById("scenceList");
	var srcElement = $("input[name='templateName']").get(0);
	dialog({
		id:"docTypeDialog",
		title:'文档类型',
		url: webPath+'/docTypeConfig/getPageForSel?docType='+docType+'&docSplitNo='+docSplitNo,
		width:350,
		height:420,
		quickClose : true,
//		backdropOpacity:0,
		onshow:function(){
			this.returnValue = null;
		},onclose:function(){
			if(this.returnValue){
				//返回对象的属性docNo,docName；如果多个，使用@分隔
				if(typeof(callback)== "function"){
					callback(this.returnValue);
				}else{
				}
			}
		}
	}).show(srcElement);
};
function getEventObj(obj) {
	eventObj = obj;
};
function kindMutiDialogCall(sysKind) {
	$("input[name=kindNoDes]").val("");
	$("input[name=kindNo]").val("");
	$("input[name=kindNoDes]").val(sysKind.kindName);
	$("input[name=kindNo]").val(sysKind.kindNo);
};
function cusTypeMutiDialogCall(cusType) {
	$("input[name=cusType]").val("");
	$("input[name=cusTypeDes]").val("");
	$("input[name=cusType]").val(cusType.cusTypeNo);
	$("input[name=cusTypeDes]").val(cusType.cusTypeDes);
};
function scenceMutiDialogCall(scence){
	var scNo = scence.scNo.split("@");
	//var scName = scence.scName.split("@");
	$.each(scNo, function(i, scNoThis) {
		if(scNoThis){
			$("#scenceList").find(".scenceItem[name="+scNoThis+"]").show("slow");
		}
	});
	if($("#scenceList").find(".scenceItem:visible").length == scenceList.length){
		$("#scenceAdd").hide();
	}
};

//文档模型选择后 回调函数
function docTypeSelCall(doc) {
	var $scenceItem = $(eventObj).parents(".scenceItem");
	var $ul = $(eventObj).parents(".scenceItem").find("ul");
	//$obj.find(".docTypeItem").empty();
	//移除要件前,记录该场景下所有选中必填的要件编号
	var $inck = $ul.find("input[type='checkbox']");
	var thisArray = new Array();
    var i;
    var eleValue;
	for(i = 0;i<$inck.length;i++){
		if($inck[i].checked){
			eleValue = $($inck.get(i)).parent().parent().parent().parent().attr("data-docno");
			thisArray.push(eleValue);
		}
	}
	$ul.find(".docTypeItem").remove();
	var docNo = doc.docNo.split("@");
	var docName = doc.docName.split("@");
	var docType = doc.docType.split("@");
	var formNo = doc.formNo.split("@");
	//var docTypes =  $(eventObj).parent().find("input[name=]")
	var count = 0;
	$.each(docName, function(i, docThis) {
		if (docNo[i]) {
			count++;
			var inpuStr = "";
			var mustStr = "";
			/**注释原因：新添加文档时报docBizSceConList未定义**/
//			$.each(docBizSceConList,function(j,docBizSceCon){
//				if(docNo[i] == docBizSceCon.docSplitNo && docBizSceCon.ifMustInput == "1"){
//					inpuStr = '<input type="checkbox" onclick="changeIsMust(this);" checked=true>';
//					mustStr = '<span class="i i-sanjiao color_red btspan0"></span> <span class="btspan1">必</span>';
//					return false;
//				}else{
//					inpuStr = '<input type="checkbox" onclick="changeIsMust(this);">';
//				}
//			});
			inpuStr = '<input type="checkbox" onclick="changeIsMust(this);">'+'</input>';
			var htmlStr = '<li class="docTypeItem" id="li_'+docNo[i]+'" data-doctype="'+docType[i]+'" data-docno="'+docNo[i]+'" data-formno="'+formNo[i]+'" data-ismust="0">'
				+ '<div  class="hover-div">'
				+ '<div class="hover-info">'
				+ '<label style="cursor: pointer;font-weight: normal;">'+inpuStr+'是否必填</label>'
				+ '</div>'
				+ '<div class="hover-info">'
				+ '<span class="i i-lajitong1 span-lajitong" onclick="removeDocTypeItem(this);"></span>'
				+ '</div>'
				+ '</div>'
				+ '<div class="docTypeShow">'+ mustStr + docThis + '</div>' 
				+ '</li>';
			$ul.find(".docTypeItem1").before(htmlStr);
		}
	});
	var $spanCount = $(eventObj).parents(".scenceItem").find(".span-count");
	$spanCount.data("count",count);
	$spanCount.html("(" + count + ")");
	$scenceItem.find(".content-div").mCustomScrollbar("update");
	if($scenceItem.find(".content-div .mCSB_scrollTools").is(":visible")){
		$ul.find(".docTypeItem1").hide();
		$scenceItem.find(".jiaDiv .docTypeItem1").css("display","inline-block");
	}else{
		$ul.find(".docTypeItem1").css("display","inline-block");
		$scenceItem.find(".jiaDiv .docTypeItem1").hide();
	}
	$scenceItem.find(".content-div").mCustomScrollbar("update");
	//让之前的选中项,选中,并且调用选中相关方法
	$ul = $(eventObj).parents(".scenceItem").find("ul");
	var $inck2 = $ul.find("input[type='checkbox']");
	for(i = 0;i<$inck2.length;i++){
		eleValue = ($($inck2.get(i))).parent().parent().parent().parent().attr("data-docno");
		if(eleValue){
			//相关方法
			//thisArray
			for(var i2=0;i2<thisArray.length;i2++){
				var thisValue = thisArray[i2];
				if(eleValue == thisValue){
					//   input.setAttribute("title", "mgc");
					//$inck.get(i).setAttribute("checked","checked");
					//changeIsMust($inck.get(i));
					var thisObj = $inck2.get(i);
					$(thisObj).click();
				}
			}
		}
	}
	
	
	
};


function changeIsMust(obj) {
	if ($(obj).is(":checked")) {
		//$(obj).parents(".docTypeItem").find(".docTypeShow").prepend("<span class='span-xing'>*</span>");
		$(obj).parents(".docTypeItem").find(".docTypeShow").append(btspan);
		$(obj).parents(".docTypeItem").data("ismust", "1");
		
	} else {
		//$(obj).parents(".docTypeItem").find(".docTypeShow .span-xing").remove();
		$(obj).parents(".docTypeItem").find(".btspan0").remove();
		$(obj).parents(".docTypeItem").find(".btspan1").remove();
		$(obj).parents(".docTypeItem").data("ismust", "0");
	}
};

function removeDocTypeItem(obj) {
	var $spanCount = $(obj).parents(".scenceItem").find(".span-count");
	var count = parseInt($spanCount.data("count"))-1;
	$spanCount.data("count",count);
	$spanCount.html("(" + count + ")");
	$(obj).parents(".docTypeItem").hide("slow",function(){
		$(obj).parents(".docTypeItem").remove();
	});
	
	$(obj).parents(".content-div").mCustomScrollbar("update");
};
function ajaxInsertThis(obj) {
	var flag = submitJsMethod($(obj).get(0), '');
	if (flag) {
		var url = $(obj).attr("action");
		var dataParm = JSON.stringify($(obj).serializeArray());
		var dataParmList = [];
		$("#scenceList .scenceItem").each(function(i, scenceItem) {
			var scNo = $(this).find("input[name=scenceNo]").val();
			
			$(this).find(".docTypeItem").each(function(j, docTypeItem) {
				var entity = {};
				entity.scNo = scNo;
				entity.docType = $(docTypeItem).data("doctype");
				entity.docSplitNo = $(docTypeItem).data("docno");
				entity.ifMustInput = $(docTypeItem).data("ismust");
				entity.formId = $(docTypeItem).data("formno");
				dataParmList.push(entity);
			});
		});
		LoadingAnimate.start();
		jQuery.ajax({
			url : url,
			data : {
				ajaxData : dataParm,
				ajaxDataList : JSON.stringify(dataParmList),
				tempSerialId:tempSerialId
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					top.addFlag = true;
					if (data.htmlStrFlag == "1") {
						top.htmlStrFlag = true;
						top.htmlString = data.htmlStr;
					}
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
function removeScence(obj) {
	$(obj).parent().hide("slow");
	$(obj).parent().find(".docTypeItem").remove();
	var $spanCount = $(obj).parent().find(".span-count");
	$spanCount.data("count","0");
	$spanCount.html("(0)");
	$("#scenceAdd").show();
};


var scrollStep = 120 * 3;
function scrollToRight(obj){
	var $div = $(obj).parents(".scenceItem");
	var left = $div.find(".mCSB_dragger").position().left;
	
	var curPos = $(obj).data("curposition") || 0;
	curPos = curPos + scrollStep;
	$div.find(".content-div").mCustomScrollbar("scrollTo", curPos);
	$(obj).data("curposition", curPos);
	$div.find(".left-arrow").data("curposition", curPos);
};
function scrollToLeft(obj){
	var $div = $(obj).parents(".scenceItem");
	var left = $div.find(".mCSB_dragger").position().left;
	
	var curPos = $(obj).data("curposition") || 0;
	curPos = curPos == 0 ? 0 : curPos - scrollStep;
	$div.find(".content-div").mCustomScrollbar("scrollTo", curPos);
	$(obj).data("curposition", curPos);
	$div.find(".right-arrow").data("curposition", curPos);
};

//给主提示框添加滚动条
$("body").mCustomScrollbar({
	advanced:{
		updateOnContentResize:true
	}
});	
