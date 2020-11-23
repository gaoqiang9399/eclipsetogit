;
var BusExamine=function(window,$){
	//获得是否进行贷后检查
	var _getExamineResultAjax=function(){
		if(typeof(fincId)=="undefined"){
			fincId="";
		}
		if(typeof(cusNo)=="undefined"){
			cusNo="";
		}
        if(typeof(pactId)=="undefined"){
            pactId="";
        }
		
		$.ajax({
			url: webPath+"/mfExamineHis/getExamineResultAjax?fincId="+fincId+"&cusNo="+cusNo+"&pactId="+pactId,
			type:"post",
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				if(data.flag="success"){
					if(data.getFlag=="detail"){
						examResultFlag=true;
						$("#examineResult").removeClass("pact-fiveclass");
						$("#examineResult").removeClass("btn-lightgray");
						$("#examineResult").addClass("btn-dodgerblue");
					}
				}
			}
		});
	};
	var _init=function(){
		BusExamine.getExamineResultAjax();
	};
	var _loanAfterExamine=function(){

		var _url;
		if(typeof(pactIdForExamine)=='undefined' || pactIdForExamine==''){
			_url = webPath+'/mfExamineHis/examineApplyForCus?cusNo='+cusNo;
		}else{
			_url = webPath+'/mfExamineHis/ExamineApply?pactId='+pactIdForExamine+'&cusNo='+cusNo+"&fincId="+fincId;
		}
		top.window.openBigForm(_url,"贷后检查",function(){
			_refreshExamHis();
			_getExamineResultAjax();
		});
	};
	//新贷后检查入口
    var _loanAfterExamineNew=function(){

        var _url=webPath+'/mfExamineHis/examineApplyNew?';
        if('finc'==examEntrance){
        	//借据入口
            _url = _url+'pactId='+pactIdForExamine+'&cusNo='+cusNo+"&fincId="+fincId+"&examEntrance="+examEntrance;
        }else if('pact'==examEntrance){
			//合同入口
            _url = _url+'pactId='+pactId+'&cusNo='+cusNo+"&examEntrance="+examEntrance;
		}else if('cus'==examEntrance){
        	//客户入口
            _url = _url+'cusNo='+cusNo+"&examEntrance="+examEntrance;
        }
        top.window.openBigForm(_url,"贷后检查",function(){
            _refreshExamHis();
            _getExamineResultAjax();
        });
    };
	//获得贷后检查详情
	var _examineDetailResult=function(){
		if(typeof(pactIdForExamine)=='undefined'){
			pactIdForExamine='';
		}
		if (typeof(fincId)=='undefined'){
			fincId='';
		}
        if('pact'==examEntrance){
            //合同入口
            fincId='';
        }else if('cus'==examEntrance){
            //客户入口
            pactIdForExamine='';
            fincId='';
        }
		if(examResultFlag){
			top.window.openBigForm(webPath+'/mfExamineHis/ExamineDetailResult?pactId='+pactIdForExamine+'&cusNo='+cusNo+"&fincId="+fincId,"贷后检查",_refreshExamHis);
		}
	};
	//贷后检查历史超链获得贷后检查详情
	var _examineDetailResultByList=function(obj,url){
		var parm=url.split("?")[1];
		var examHisId=parm.split("&")[1];
		examHisId=examHisId.split("=")[1];
		top.window.openBigForm(webPath+'/mfExamineHis/ExamineDetailByList?fincId='+fincId+"&examHisId="+examHisId,"贷后检查详情",_refreshExamHis);
	};
	
	//刷新合同详情中的贷后检查列表
	var _refreshExamHis=function(){
		/*if(typeof(examTableId) == "undefined") {
			$.ajax({
				url:webPath + "/mfExamineHis/getAllMfExamineHisAjax?cusNo=" + cusNo ,
				type:"post",
				dataType:"json",
				async:false,
				error:function(){alert('error')},
				success:function(data){
					if(data.flag == "success" && data.size >0) {
						examResultFlag=true;
						$("#examineResult").removeClass("pact-fiveclass");
						$("#examineResult").addClass("btn-dodgerblue");
					}else {
					}
				}
			});
		}else {
			$.ajax({
				url: webPath+"/mfExamineHis/getMfExamineHisListAjax?pactId="+pactId+"&fincId="+fincId+"&tableId="+examTableId,
				type:"post",
				async: false,
				dataType:"json",
				error:function(){alert('error')},
				success:function(data){
					$("#examineHis").html(data.htmlStr);
					$("#mfExamineHis-div").show();
				}
			});
		}*/
		if(typeof(examTableId) != "undefined"){
            if(typeof(pactIdForExamine)=='undefined'){
                pactIdForExamine='';
            }
            if (typeof(fincId)=='undefined'){
                fincId='';
            }
            $.ajax({
                url: webPath+"/mfExamineHis/getMfExamineHisListAjax?pactId="+pactIdForExamine+"&fincId="+fincId+"&tableId="+examTableId+'&cusNo='+cusNo,
                type:"post",
                async: false,
                dataType:"json",
                error:function(){alert('error')},
                success:function(data){
                    $("#examineHis").html(data.htmlStr);
                    $("#mfExamineHis-div").show();
                }
            });
		}
	};
	return{
		loanAfterExamine:_loanAfterExamineNew,
		examineDetailResult:_examineDetailResult,
		init:_init,
		examineDetailResultByList:_examineDetailResultByList,
		getExamineResultAjax:_getExamineResultAjax
	};
}(window,jQuery);