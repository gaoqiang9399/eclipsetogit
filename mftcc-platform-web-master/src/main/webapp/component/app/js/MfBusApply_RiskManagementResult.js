;
var MfBusApply_RiskManagementResult = function(window, $) {
	var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                updateOnContentResize:true
            }
        });
        // 根据三方类型不同解析数据
        switch(threeParty){
        case "MG"://海卡相关
        	_queryMGHtmlAjax();
        	break;
        case "BR_DURATION"://百融手机号在网时长
        	_queryBRDurationHtmlAjax();
        	break;
        case "JXL_MG"://聚信立-蜜罐
        	_queryJXLMGHtmlAjax();
        	break;
        case "JXL_MF"://聚信立-蜜蜂
            _queryMFHtmlAjax();
        	break;
        case "TTYY"://天天有余
        	_queryTTYYHtmlAjax();
        	break;
        default:
        	_queryFHHtmlAjax();
        	break;
        }
	};

    var _queryJXLMGHtmlAjax = function(){
        $.ajax({
            url:webPath+"/apiReturnRecord/getJXLMGResultAjax",
            data:{
                returnId:returnId,
                threeParty:threeParty,
                id:id
            },
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
					$("#inputCommonForm").html(data.httpStrs);
                    var authCode=data.authCode;
                    MfBusApply_RiskManagementResult.showpdf(authCode);
                }else{
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_INSERT"),0);
            }
        });
    }
    var _queryMGHtmlAjax = function(){
    	$.ajax({
    		url:webPath+"/apiReturnRecord/getMGResult",
    		data:{
    			returnId:returnId,
    			threeParty:threeParty,
    			id:id
    		},
    		type:'post',
    		dataType:'json',
    		success:function(data){
    			if(data.flag == "success"){
    				$("#inputCommonForm").html(data.httpStrs);
    			}else{
    				alert(top.getMessage("ERROR_INSERT"),0);
    			}
    		},error:function(){
    			alert(top.getMessage("ERROR_INSERT"),0);
    		}
    	});
    }
    var _queryTTYYHtmlAjax = function(){
    	$.ajax({
    		url:webPath+"/apiReturnRecord/getTTYYResult",
    		data:{
    			returnId:returnId,
    			threeParty:threeParty,
    			id:id
    		},
    		type:'post',
    		dataType:'json',
    		success:function(data){
    			if(data.flag == "success"){
    				$("#inputCommonForm").html(data.httpStrs);
    			}else{
    				alert(top.getMessage("ERROR_INSERT"),0);
    			}
    		},error:function(){
    			alert(top.getMessage("ERROR_INSERT"),0);
    		}
    	});
    }
    var _queryMFHtmlAjax = function(){
        $.ajax({
            url:webPath+"/apiReturnRecord/lookpreAjax",
            data:{
                token:token,
                cusNo:returnId,
            },
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    $("#inputCommonForm").html(data.httpStrs);
                    var authCode=data.authCode;
                    MfBusApply_RiskManagementResult.showpdf(authCode);
                }else{
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_INSERT"),0);
            }
        });
    }
    var _queryBRDurationHtmlAjax = function(){
    	$.ajax({
    		url:webPath+"/apiReturnRecord/getBRDurationAjax",
    		data:{
    			returnId:returnId,
    			threeParty:threeParty,
    			id:id,
    		},
    		type:'post',
    		dataType:'json',
    		success:function(data){
    			if(data.flag == "success"){
    				$("#inputCommonForm").append(data.httpStrs);
    			}else{
    				$("#inputCommonForm").append(data.msg);
    			}
    		},error:function(){
    			alert("解析三方数据失败",0);
    		}
    	});
    }

    var _queryFHHtmlAjax = function(){
        $.ajax({
            url:webPath+"/apiReturnRecord/getFHResult",
            data:{
                returnId:returnId,
                threeParty:threeParty,
                id:id,
            },
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    $("#inputCommonForm").append(data.httpStrs);
                }else{
                    alert(top.getMessage("ERROR_INSERT"),0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_INSERT"),0);
            }
        });
    }
    var _showpdf = function (authCode) {
        $("#exportToPdf").click(function() {
            top.openBigForm(webPath+'/apiReturnRecord/showpdf?authCode='+authCode,'pdf打印',function() {});
        });
    }

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
        init : _init,
        queryMGHtmlAjax : _queryMGHtmlAjax,
        queryJXLMGHtmlAjax : _queryJXLMGHtmlAjax,
        queryFHHtmlAjax : _queryFHHtmlAjax,
        queryTTYYHtmlAjax : _queryTTYYHtmlAjax,
        queryMFHtmlAjax : _queryMFHtmlAjax,
        showpdf:_showpdf
	};
}(window, jQuery);