var eventObj;
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
//	mySelectDocTypeDialog(docTypeSelCall,docSplitNo,doctype);
	selectDocTypeDialog(docTypeSelCall,docSplitNo,doctype);
};

function getEventObj(obj) {
	eventObj = obj;
};

//文档模型选择后 回调函数
function docTypeSelCall(doc) {
	var $scenceItem = $(eventObj).parents(".scenceItem");
	var docNo = doc.docNo.split("@");
	var docName = doc.docName.split("@");
	var docType = doc.docType.split("@");
	var formNo = doc.formNo.split("@");
	//var docTypes =  $(eventObj).parent().find("input[name=]")
	var scNo =  $scenceItem.find("input[name=scenceNo]").val();
	var count = 0;
	var htmlStr="";
	var dataParmList = [];
	$.each(docName, function(i, docThis) {
		if (docNo[i]) {
			var entity = {};
			entity.dime1 = cusType;
			entity.scNo =scNo;
			entity.docType = docType[i];
			entity.docSplitNo = docNo[i];
			entity.formId = formNo[i];
			entity.docName = docName[i];
			dataParmList.push(entity);
			count++;
			var inpuStr = "";
			var mustStr = "";
			inpuStr = '<input type="checkbox" onclick="changeIsMust(this);">'+'</input>';
			htmlStr = htmlStr + '<li class="docTypeItem" id="li_'+docNo[i]+'" data-doctype="'+docType[i]+'" data-docno="'+docNo[i]+'" data-formno="'+formNo[i]+'" data-ismust="0">'
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
			
		}
	});
	$.ajax({
		url : webPath+"/docBizSceConfig/insertDocsAjax",
		data:{ajaxData : JSON.stringify(dataParmList)},
		type:'post',
		dataType:'json',
		beforeSend:function(){  
			LoadingAnimate.start();
		},success:function(data){
			if(data.flag == "success"){
				//移除要件前,记录该场景下所有选中必填的要件编号
			    var $ul = $(eventObj).parents(".scenceItem").find("ul");
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
				$ul.find(".docTypeItem1").before(htmlStr);
				
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
								var thisObj = $inck2.get(i);
								$(thisObj).click();
							}
						}
					}
				}
			}
		},error:function(){
			alert(top.getMessage("ERROR"),0);
		},complete: function(){
			LoadingAnimate.stop();
		}
	});
	
	
};

function changeIsMust(obj) {
	var $scenceItem = $(obj).parents(".scenceItem");
	var $docTypeItem = $(obj).parents(".docTypeItem");
	var scNo =  $scenceItem.find("input[name=scenceNo]").val();
	var docBizSceConfig ={};
	if ($(obj).is(":checked")) {
		$(obj).parents(".docTypeItem").find(".docTypeShow").append(btspan);
		$(obj).parents(".docTypeItem").data("ismust", "1");
		docBizSceConfig.ifMustInput="1";
	} else {
		$(obj).parents(".docTypeItem").find(".btspan0").remove();
		$(obj).parents(".docTypeItem").find(".btspan1").remove();
		$(obj).parents(".docTypeItem").data("ismust", "0");
		docBizSceConfig.ifMustInput="0";
	}
	
	docBizSceConfig.dime1 = cusType;
	docBizSceConfig.scNo = scNo;
	docBizSceConfig.docType = $docTypeItem.data("doctype");
	docBizSceConfig.docSplitNo = $docTypeItem.data("docno");
	var ajaxData = JSON.stringify(docBizSceConfig);
	//更新必填状态
	$.ajax({
		url : webPath+"/docBizSceConfig/updateAjax",
		data:{ajaxData:ajaxData},
		type:'post',
		dataType:'json',
		beforeSend:function(){  
			LoadingAnimate.start();
		},success:function(data){
			if(data.flag == "success"){
				
			}else{
				window.top.alert(top.getMessage("FAILED_DELETE"),1);
			}
		},error:function(){
			alert(top.getMessage("FAILED_DELETE"),0);
		},complete: function(){
			LoadingAnimate.stop();
		}
	}); 
	

	
	
	
	
	
};

function removeDocTypeItem(obj) {
	var $scenceItem = $(obj).parents(".scenceItem");
	var $docTypeItem = $(obj).parents(".docTypeItem");
	var scNo =  $scenceItem.find("input[name=scenceNo]").val();
	//异步删除配置的文档信息
	var dime1 =cusType;
	//var scNo = scNo;
	var docType = $docTypeItem.data("doctype");
	var docSplitNo = $docTypeItem.data("docno");
	alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
		$.ajax({
			url : webPath+"/docBizSceConfig/deleteDocAjax",
			data:{dime1:dime1,scNo:scNo,docType:docType,docSplitNo:docSplitNo},
			type:'post',
			dataType:'json',
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					var $spanCount = $(obj).parents(".scenceItem").find(".span-count");
					var count = parseInt($spanCount.data("count"))-1;
					$spanCount.data("count",count);
					$spanCount.html("(" + count + ")");
					$(obj).parents(".docTypeItem").hide("slow",function(){
						$(obj).parents(".docTypeItem").remove();
					});
					$(obj).parents(".content-div").mCustomScrollbar("update");
				}else{
					window.top.alert(top.getMessage("FAILED_DELETE"),1);
				}
			},error:function(){
				alert(top.getMessage("FAILED_DELETE"),0);
			},complete: function(){
				LoadingAnimate.stop();
			}
		}); 
	});
	
	
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

