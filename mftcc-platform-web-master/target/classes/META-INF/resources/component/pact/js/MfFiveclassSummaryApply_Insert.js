;
var MfFiveclassSummaryApply_Insert = function(window,$){
	var _init = function(){
		$("#plan_content").html(ajaxData.tableHtml);
		$("#tablist").show();
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_nowfiveclassChage();
		MfPactFiveclass_commonForBatch.changeTrColor();
	}
	var _nowfiveclassChage = function(){
		$("select[name='nowFiveclass']").each(function(){
			  $(this).bind("change",function () {  
	                if (this.value== 1){
	                	var normalCnt = $("input[name=normalCnt]").val();
	                	normalCnt=parseInt(normalCnt);
	                	$("input[name=normalCnt]").val(normalCnt+1);
	                } else if(this.value == 2){
	        			var focusCnt = $("input[name=focusCnt]").val();
	        			focusCnt=parseInt(focusCnt);
	                	$("input[name=focusCnt]").val(focusCnt+1);
	        		}else if(this.value == 3){
	        			var secondaryCnt = $("input[name=secondaryCnt]").val();
	        			secondaryCnt=parseInt(secondaryCnt);
	                	$("input[name=secondaryCnt]").val(secondaryCnt+1);
	        		}else if(this.value == 4){
	        			var suspiciousCnt = $("input[name=suspiciousCnt]").val();
	        			suspiciousCnt=parseInt(suspiciousCnt);
	                	$("input[name=suspiciousCnt]").val(suspiciousCnt+1);
	        		}else if(this.value == 5){
	        			var lossCnt = $("input[name=lossCnt]").val();
	        			lossCnt=parseInt(lossCnt);
	                	$("input[name=lossCnt]").val(lossCnt+1);
	        		} 
	                _changeCnt(this,this.value);
	            });
		});
	};
	var _changeCnt = function(obj,value){
		var nowFiveclassLat = $(obj).parent().parent().find("input[name=nowFiveclass]").val();
		if(nowFiveclassLat == 1){
			var normalCnt = $("input[name=normalCnt]").val();
			normalCnt=parseInt(normalCnt);
        	$("input[name=normalCnt]").val(normalCnt-1);
		}else if(nowFiveclassLat == 2){
			var focusCnt = $("input[name=focusCnt]").val();
			focusCnt=parseInt(focusCnt);
        	$("input[name=focusCnt]").val(focusCnt-1);
		}else if(nowFiveclassLat == 3){
			var secondaryCnt = $("input[name=secondaryCnt]").val();
			secondaryCnt=parseInt(secondaryCnt);
        	$("input[name=secondaryCnt]").val(secondaryCnt-1);
		}else if(nowFiveclassLat == 4){
			var suspiciousCnt = $("input[name=suspiciousCnt]").val();
			suspiciousCnt=parseInt(suspiciousCnt);
        	$("input[name=suspiciousCnt]").val(suspiciousCnt-1);
		}else if(nowFiveclassLat == 5){
			var lossCnt = $("input[name=lossCnt]").val();
			lossCnt=parseInt(lossCnt);
        	$("input[name=lossCnt]").val(lossCnt-1);
		}
		$(obj).parent().parent().find("input[name=nowFiveclass]").val(value);
	};
	//新增操作
	var  _ajaxInsert = function (formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if(flag){
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			var fiveclassList = [];
			$("#tab").find("tr").each(function(){
			    var tdArr = $(this).children();
			    var fiveclassId = tdArr.find("input[name='fiveclassId']").val();
			    var appName = tdArr.eq(0).text();
			    var nowChangeReason = tdArr.find("input[name='nowChangeReason']").val();
			    var changeReason = tdArr.find("input[name='changeReason']").val();
			    var fiveclassSts = tdArr.find("input[name='fiveclassSts']").val();
			    var nowFiveclass = tdArr.find("select[name='nowFiveclass']").val();
			    var fincShowId = tdArr.eq(1).text();
			    var putoutAmt = tdArr.eq(2).text();
			    var lastFiveclass = tdArr.eq(3).text();
			    var systemFiveclass = tdArr.eq(4).text();
			   // if(nowChangeReason!=""&&nowFiveclass!=""&&nowFiveclass!="99"){
			    	 var fiveclass = new Object();
					fiveclass.fiveclassId = fiveclassId;
					fiveclass.nowChangeReason = nowChangeReason;
					fiveclass.nowFiveclass = nowFiveclass;
					fiveclass.appName = appName;
					fiveclass.fincShowId = fincShowId;
					fiveclass.putoutAmt = putoutAmt;
					fiveclass.lastFiveclass = lastFiveclass;
					fiveclass.systemFiveclass = systemFiveclass;
					fiveclass.changeReason = changeReason;
					fiveclass.fiveclassSts = fiveclassSts;
					fiveclassList.push(fiveclass);
			  //  }
			   
			  });
			 var jsonString = JSON.stringify(fiveclassList);
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data:{ajaxData:dataForm,fiveclassContext:jsonString},
				type:"post",
				dataType:"json",
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg,3);
						window.location.href=webPath+"/mfPactFiveclass/getFiveClassAndPactListPageForBatch"; 
					}else{
						alert(data.msg,0);
					}
				},
				error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION","新增"),0);
				}
			});
		}
	};
	var _close = function(){
		window.location.href=webPath+"/mfPactFiveclass/getFiveClassAndPactListPageForBatch"; 
	}
	return{
		ajaxInsert:_ajaxInsert,
		nowfiveclassChage:_nowfiveclassChage,
		init:_init,
		close:_close
	};
}(window,jQuery);