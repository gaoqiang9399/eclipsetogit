;

var pubInitEvalInfo = function(window, $) {
    //初始化评级
    var _initEval = function(cusNo,useType,relNo,gradeType,evalClass,spanId,buttonId){
        var param = {};
        top.evalCusNo = cusNo;
        top.evalRelNo = relNo;
        top.evalSpanId = spanId;
        top.evalButtonId = buttonId;
        param.cusNo = cusNo;
        param.useType = useType;
        param.relNo = relNo;
        param.gradeType = gradeType;
        param.evalClass = evalClass;
        param = JSON.stringify(param);
        $.ajax({
            url : webPath+"/appEval/getCurrAppEvalDataAjax",
            data : {
                param : param,
            },
            type : 'post',
            dataType : 'json',
            async: false,
            success : function(data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    var cusLevelName = data.cusLevelName;
                    if(cusLevelName != null&&cusLevelName != ""){
                        $("#"+spanId).text(cusLevelName);
                        $("#"+buttonId).attr("onclick","pubInitEvalInfo.getEvalDetailResult('1','"+relNo+"','"+gradeType+"','"+evalClass+"');");
                        if(cusLevelName.indexOf("A") != -1){
                            $("#"+buttonId).attr("class","btn btn-view btn-forestgreen");
                        }else if(cusLevelName.indexOf("B") != -1){
                            $("#"+buttonId).attr("class","btn btn-view cus-middle");
                        }else if(cusLevelName.indexOf("C") != -1 || cusLevelName.indexOf("D") != -1){
                            $("#"+buttonId).attr("class","btn btn-view btn-danger");
                        } else if(cusLevelName.indexOf("优秀") != -1 || cusLevelName.indexOf("较好") != -1 || cusLevelName.indexOf("良好") != -1){
                            $("#"+buttonId).attr("class","btn btn-view btn-forestgreen");
                        }else if(cusLevelName.indexOf("一般") != -1){
                            $("#"+buttonId).attr("class","btn btn-view cus-middle");
                        }else if(cusLevelName.indexOf("差") != -1){
                            $("#"+buttonId).attr("class","btn btn-view btn-danger");
                        }
                    }
                    if(data.debtEval == '0'){
                        $("#cusCreditDebtEvalRating-button").hide();
                        $("#creditDebtBtn").hide();
                        $("#cusCreditBusEvalRating-button").hide();
                        $("#debtEvalApply").hide();
                    }
                }
            },
            error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("ERROR_REQUEST_URL", "/appEval/getCurrAppEvalDataAjax"));
            }
        });
    };
    //评级详情
    var _getEvalDetailResult = function(parm,relNo,gradeType,evalClass){
        if (parm == '1') {
            top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=4&useType=1&relNo="+relNo+"&gradeType="+gradeType+"&evalClass="+evalClass, "评级信息", function() {});
        } else {
            return false;
        }
    };
	return {
        initEval:_initEval,
        getEvalDetailResult:_getEvalDetailResult
	};
}(window, jQuery);
