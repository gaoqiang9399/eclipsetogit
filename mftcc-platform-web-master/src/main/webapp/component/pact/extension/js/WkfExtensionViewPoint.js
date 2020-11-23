;
var WkfExtensionViewPoint=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//意见类型新版选择组件
		$('select[name=opinionType]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
	};
	//进入审批页面
	var _getApprovaPage=function(){
		$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block"); 
	 	$("#submitBtn").css("display","block"); 
	 	/*if(validateCnt==0){
	 		validateAccIsModify();
	 	}*/
	}
	//返回详情页面
	var _approvalBack=function(){
		$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	};
	var _termChange=function(){
		var extenTerm = $("input[name=extenTerm]").val();
		if(extenTerm==""){
			return;
		}
		var extenBeginDate = $("input[name=extenBeginDate]").val();
		var termType = $("[name=termType]").val();
		var str = "";
        var d;
		if(1==termType){ //融资期限类型为月 
			d = new Date(extenBeginDate);
			extenTerm=parseInt(extenTerm)
			d.setMonth(d.getMonth()+extenTerm);
			day=d.getDate()>9?d.getDate():"0"+d.getDate();
			//获取利息计算区间标志  1-算头不算尾 2-首尾都计算
			if(calcIntstFlag=="2"){
				day=parseInt(day)-1;
			}
			str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(day);
		}else{ //融资期限类型为日 
			d = new Date(extenBeginDate);
			extenTerm=parseInt(extenTerm)
		 	d.setDate(d.getDate()+extenTerm);
			day=d.getDate()>9?d.getDate():"0"+d.getDate();
			//获取利息计算区间标志  1-算头不算尾 2-首尾都计算
			if(calcIntstFlag=="2"){
				day=parseInt(day)-1;
			}
			str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
		}
		$("input[name=extenEndDate]").val(str);
	};

    //修改期限
    var _changeTermIntstEndDate = function () {

        var intstBeginDate = $("input[name=extenBeginDate]").val();
        intstBeginDate = intstBeginDate.replace(/-/g, "");  //开始日期

        var term = $("input[name=extenTerm]").val();//期限


        //计算出借据的结束日期
        $.ajax({
            url: webPath + "/mfBusFincApp/getextenEndDateInfoMapAjax",
            data: {"intstBeginDate": intstBeginDate, "appId": appId, "pactId": pactId, "term": term},
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.flag == "success") {
                    var intstEndDate = data.intstEndDate;
                    var intstEndDateShow = data.intstEndDateShow;
                    /*intstEndDate = intstEndDate.substring(0, 4) + "-" + intstEndDate.substring(4, 6) + "-" + intstEndDate.substring(6, 8);
                    intstEndDateShow = intstEndDateShow.substring(0, 4) + "-" + intstEndDateShow.substring(4, 6) + "-" + intstEndDateShow.substring(6, 8);*/
                    $("input[name=extenEndDateShow]").val(intstEndDateShow);
                    $("input[name=extenEndDate]").val(intstEndDate);
                } else {
                    $("input[name=termMonth]").val("");
                    window.top.alert(data.msg, 0);
                }
            }, error: function () {
                window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };






	//发起展期申请
	var _doSubmitAjax=function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/mfBusExtensionApply/submitUpdateAjax?appNo="+extensionApplyId,formObj,'extensionApplySP');
		}
	}
	return{
		init:_init,
		doSubmitAjax:_doSubmitAjax,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
        termChange: _termChange,
        changeTermIntstEndDate: _changeTermIntstEndDate
	};
}(window, jQuery);