function initData(){
	$("input[name=finScorePercent]").parents("tr").eq(0).hide();
	$("input[name=dlScorePercent]").parents("tr").eq(0).hide();
	$("input[name=dxScorePercent]").parents("tr").eq(0).hide();	
}
function initContent(){
//	$(".scroll-content").mCustomScrollbar({
//		advanced:{
//			updateOnContentResize:true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			updateOnContentResize : true
		}
	});
	$("input[name=evalWayClass]").popupSelection({		
		searchOn:true,//启用搜索
		inline:false,//下拉模式
		multiple:true,//多选
		labelShow:false,//选择区域显示已选择项
		title : "行业分类",//标题
		items:TRADE
	});
	$("input[name=evalProjSize]").popupSelection({		
		searchOn:true,//启用搜索
		inline:false,//下拉模式
		multiple:true,//多选
		labelShow:false,//选择区域显示已选择项
		title : "企业规模",//标题
		items:PROJSIZE
	});
	$("input[name=evalCusType]").popupSelection({		
		searchOn:true,//启用搜索
		inline:false,//下拉模式
		multiple:true,//多选
		labelShow:false,//选择区域显示已选择项
		title : "客户类型",//标题
		items:CUSTYPE
	});

    if(typeof gradeType != 'undefined' && gradeType != '4'){
        $("select[name='evalClass']").attr("disabled",true);
    }
}
function selectCheck(){
	$("#evalSceInsertAndUpdForm").find("input[name=evalIndexTypeRel]").each(function(index){
		if($(this).is(':checked')){
			if(index=="0"){
				$("input[name=finScorePercent]").parents("tr").eq(0).show();
//				$("input[name=finScorePercent]").val("0.00");
			}
			if(index=="1"){
				$("input[name=dlScorePercent]").parents("tr").eq(0).show();
//				$("input[name=dlScorePercent]").val("0.00");
			}
			if(index=="2"){
				$("input[name=dxScorePercent]").parents("tr").eq(0).show();
//				$("input[name=dxScorePercent]").val("0.00");
			}
		}else{
			if(index=="0"){
				$("input[name=finScorePercent]").parents("tr").eq(0).hide();
				$("input[name=finScorePercent]").val("");
			}
			if(index=="1"){
				$("input[name=dlScorePercent]").parents("tr").eq(0).hide();
				$("input[name=dlScorePercent]").val("");
			}
			if(index=="2"){
				$("input[name=dxScorePercent]").parents("tr").eq(0).hide();
				$("input[name=dxScorePercent]").val("");
			}
		};
	});
}


function checkScorePercent(){
	var finScorePercent = $("input[name=finScorePercent]").val();//财务所占权重
	var dlScorePercent = $("input[name=dlScorePercent]").val();//定量所占权重
	var dxScorePercent = $("input[name=dxScorePercent]").val();//定性所占权重
	var checkStr = "";
	$("#evalSceInsertAndUpdForm").find("input[name=evalIndexTypeRel]").each(function(index){
		if($(this).is(':checked')){
			checkStr = index+"|"+checkStr;
		};
	});
	var scorePercentSum =0.00;
	if(dxScorePercent!=""&dlScorePercent==""&finScorePercent==""){
		scorePercentSum = parseFloat(dxScorePercent);
	}
	if(dxScorePercent==""&dlScorePercent!=""&finScorePercent==""){
		scorePercentSum = parseFloat(dlScorePercent);
	}
	if(dxScorePercent==""&dlScorePercent==""&finScorePercent!=""){
		scorePercentSum = parseFloat(finScorePercent);
	}
	if(dxScorePercent!=""&dlScorePercent!=""&finScorePercent==""){
		scorePercentSum = parseFloat(dxScorePercent)+parseFloat(dlScorePercent);
	}
	if(dxScorePercent!=""&dlScorePercent==""&finScorePercent!=""){
		scorePercentSum = parseFloat(dxScorePercent)+parseFloat(finScorePercent);
	}
	if(dxScorePercent==""&dlScorePercent!=""&finScorePercent!=""){
		scorePercentSum = parseFloat(finScorePercent)+parseFloat(dlScorePercent);
	}
	if(dxScorePercent!=""&dlScorePercent!=""&finScorePercent!=""){
		scorePercentSum = parseFloat(dxScorePercent)+parseFloat(dlScorePercent)+parseFloat(finScorePercent);
	}
	if(scorePercentSum>0){
		if(scorePercentSum==100){
			flagTmp="1";
		}else{
			flagTmp = "0";
			alert("各项权重之和必须为100%",0);
		}
		/*if(scorePercentSum>100){
			flagTmp = "0";
			alert("各项权重之和必须为100%",0);
		}else if(scorePercentSum<100){
			flagTmp = "0";
			alert("各项权重之和必须为100%",0);
		}else{
			flagTmp="1";
		}*/
	}else{
		flagTmp = "0";
		alert("各项权重之和必须为100%",0);
	}
	return flagTmp;
}
function doCheckScorePercent(){
	var finScorePercent = $("input[name=finScorePercent]").val();//财务所占权重
	var dlScorePercent = $("input[name=dlScorePercent]").val();//定量所占权重
	var dxScorePercent = $("input[name=dxScorePercent]").val();//定性所占权重
	var checkStr = "";
	$("#evalSceInsertAndUpdForm").find("input[name=evalIndexTypeRel]").each(function(index){
		if($(this).is(':checked')){
			checkStr = index+"|"+checkStr;
		};
	});
	var scorePercentSum =0.00;
	if(dxScorePercent!=""&dlScorePercent==""&finScorePercent==""){
		scorePercentSum = parseFloat(dxScorePercent);
	}
	if(dxScorePercent==""&dlScorePercent!=""&finScorePercent==""){
		scorePercentSum = parseFloat(dlScorePercent);
	}
	if(dxScorePercent==""&dlScorePercent==""&finScorePercent!=""){
		scorePercentSum = parseFloat(finScorePercent);
	}
	if(dxScorePercent!=""&dlScorePercent!=""&finScorePercent==""){
		scorePercentSum = parseFloat(dxScorePercent)+parseFloat(dlScorePercent);
	}
	if(dxScorePercent!=""&dlScorePercent==""&finScorePercent!=""){
		scorePercentSum = parseFloat(dxScorePercent)+parseFloat(finScorePercent);
	}
	if(dxScorePercent==""&dlScorePercent!=""&finScorePercent!=""){
		scorePercentSum = parseFloat(finScorePercent)+parseFloat(dlScorePercent);
	}
	if(dxScorePercent!=""&dlScorePercent!=""&finScorePercent!=""){
		scorePercentSum = parseFloat(dxScorePercent)+parseFloat(dlScorePercent)+parseFloat(finScorePercent);
	}
	if(scorePercentSum>0){
		if(scorePercentSum==100){
			flagTmp="1";
		}else{
			flagTmp="0";
		}
		/*if(scorePercentSum>100){
			flagTmp = "0";
		}else if(scorePercentSum<100){
			flagTmp = "2";
		}else{
			flagTmp="1";
		}*/
	}else{
		flagTmp="0";
	}
	return flagTmp;
}

window.ajaxInsert = function(obj) {
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		//var flagTmp = doCheckScorePercent();
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url : url,
			data : {ajaxData : dataParam},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "success") {
					top.editFlag=true;
					LoadingAnimate.stop();
					window.top.alert(data.msg, 1);
					top.evalScenceConfig=data.evalScenceConfig;
					myclose_click(); 
				} else {
					LoadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
		}

}