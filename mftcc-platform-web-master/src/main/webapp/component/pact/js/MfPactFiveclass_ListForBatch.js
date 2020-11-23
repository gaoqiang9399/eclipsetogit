;
var MfPactFiveclass_ListForBatch = function(window,$){
	var _storage = function(){
		
		 var fiveclassList = [];
		$("#tab").find("tr").each(function(){
		    var tdArr = $(this).children();
		    var fiveclassId = tdArr.find("input[name='fiveclassId']").val();
		    var nowChangeReason = tdArr.find("input[name='nowChangeReason']").val();
		    var changeReason = tdArr.find("input[name='changeReason']").val();
		    var nowFiveclass = tdArr.find("select[name='nowFiveclass']").val();
		    if((nowFiveclass!=""&&nowFiveclass!="99")||nowChangeReason!=""){
		    	 var fiveclass = new Object();
				    fiveclass.fiveclassId = fiveclassId;
				    fiveclass.nowChangeReason = nowChangeReason;
				    fiveclass.nowFiveclass = nowFiveclass;
				    fiveclass.changeReason = changeReason;
				    fiveclassList.push(fiveclass);
		    }
		   
		  });
		 var jsonString = JSON.stringify(fiveclassList);
		 $.ajax({
				url:webPath+"/mfPactFiveclass/fiveclassStorage",
				data:{ajaxData:jsonString},
				dataType:"json",
				type:"POST",
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						alert(data.msg,3);
                        window.location.href=webPath+"/mfPactFiveclass/getFiveClassAndPactListPageForBatch";
                    }else{
						alert(data.msg,0);
					}
				},error:function(){
					LoadingAnimate.stop();
					alert('操作失败',0);
				}
			});
	};
	var _batchApply = function(){
		window.location.href=webPath+"/mfPactFiveclass/batchApply"; 
	};
	var _applyResult = function(){
		window.location.href=webPath+"/mfFiveclassSummaryApply/getListPage"; 
	};
	var _stageApply = function(){
		window.location.href=webPath+"/mfPactFiveclass/stageApply";
	};
	return{
		storage:_storage,
		batchApply:_batchApply,
		applyResult:_applyResult,
        stageApply:_stageApply,
	};
}(window,jQuery);