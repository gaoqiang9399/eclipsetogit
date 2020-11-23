;
var MfLawsuitFollow_ShowList = function(window, $) {
	var _init = function () {
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
  

	
	//新增案件跟进
	var _insertFollow = function(){
		 var url="/mfLawsuitFollow/input?caseId="+caseId;
		top.createShowDialog(url, "案件跟进登记", '80', '70', function() {
			$.ajax({
				url:webPath+"/mfLawsuitFollow/getMfLawsuitFollowList?caseId="+caseId,
				type:'post',
				dataType:'json',
				success:function(data){
					var html = data.htmlStr;
					var execRecovHtml = data.execRecovHtml;
					var htmlStrDecision = data.htmlStrDecision;
					$("#mfLawsuitFollowList").empty().html(html);
					if(execRecovHtml != null && execRecovHtml != ""&& execRecovHtml != "undefined"){
						$("#executionRecoveryList").empty().html(execRecovHtml);
					}
					if(htmlStrDecision != null && htmlStrDecision != "" && htmlStrDecision != "undefined"){
						$("#decisionMediateDetail").empty().html(htmlStrDecision);
					}
					$("#actAmt").empty().html(data.actAmt);
				}
			});
		});
		
	}
    //异步删除
    var _ajaxDelete =  function(obj,url){
        alert("是否确认删除!",2,function(){
                $.ajax({
                    url:url,
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        if(data.flag=="success"){
                            window.top.alert(data.msg, 1);
                            $.ajax({
                                url:webPath+"/mfLawsuitFollow/getMfLawsuitFollowList?caseId="+caseId,
                                type:'post',
                                dataType:'json',
                                success:function(data){
                                    var html = data.htmlStr;
                                    var execRecovHtml = data.execRecovHtml;
                                    var htmlStrDecision = data.htmlStrDecision;
                                    $("#mfLawsuitFollowList").empty().html(html);
                                    if(execRecovHtml != null && execRecovHtml != ""&& execRecovHtml != "undefined"){
                                        $("#executionRecoveryList").empty().html(execRecovHtml);
                                    }
                                    if(htmlStrDecision != null && htmlStrDecision != "" && htmlStrDecision != "undefined"){
                                        $("#decisionMediateDetail").empty().html(htmlStrDecision);
                                    }
                                    $("#actAmt").empty().html(data.actAmt);
                                }
                            });
                        }if(data.flat=="error"){
                            window.top.alert(data.msg, 1);
                        }
                    },
                    error:function(data){
                        window.top.alert(data.msg, 1);
                    }
                });
            },
            function(){
            }
        );

    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		insertFollow:_insertFollow,
         ajaxDelete:_ajaxDelete
        
	};
}(window, jQuery);