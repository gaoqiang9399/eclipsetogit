;
var ReCallDetailInfo = function(window, $) {
	var _init = function(){ 
		var len = $(".his-list tbody tr").length;
		$(".pageCount").text(len);
		if(len>=10){			
			var time = 10;//每页显示条数
			var times = parseInt(len/time);
			var j = 1;
			$(".loadCount").text("10");
			for(var i=10;i<len;i++){ //10以後的先隐藏 
				$("tbody tr:eq("+i+")").hide();
			}
			var nScrollHeight = 0;
			var nScrollTop = 0;
			var nDivHeight = $(".his-list").height();
			}else{
				$(".loadCount").text(len);
			}
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			},
			callbacks: {				
				whileScrolling :function(){
					nScrollHeight = $(this)[0].scrollHeight; //是notice-div的高度  
					nScrollTop = $(this)[0].scrollTop;//从顶端到现在滚动的高度 
					var paddingBottom = parseInt($(this).css('padding-bottom'));//.notice-div的内边距 
					var paddingTop = parseInt($(this).css('padding-top'));
					if(paddingBottom+paddingTop+nScrollTop+nDivHeight >= nScrollHeight){//判斷滾動條到達底部 
						$(".fa-3x").show();
						setTimeout(function(){
							$(".fa-3x").hide();
						},1000);
                        var i;
						if(j==times){
							for(i=time;i<len;i++){
								$("tbody tr:eq("+i+")").show();
								$(".loadCount").text(len);
							}
						}else if(j<times){
							for(i=time*j;i<time*(j+1);i++){
								$("tbody tr:eq("+i+")").show();
							}
							j++;
							$(".loadCount").text(time*j);
						}
						//滚动分页结束
					}
	        	}
			}
		});	
	};
	
	//催收登记
	var _recallRegist = function (pactId,cusNo) {
		window.location.href=webPath+'/recallBase/input?pactId='+pactId+'&cusNo='+cusNo+'&query=detailPage'+"&fincId="+fincId;
		
	};
	//催收改派
	var _recallReassign = function () {
		selectUserDialog(getCusMngNameDialog);
	};
	
	function getCusMngNameDialog(userInfo){
		var mgrNo=userInfo.opNo;
		var mgrName=userInfo.opName;
		var taskNo = $("input[id=taskNo]").val();
		if(mgrName!=null||mgrName!=''||mgrName!=undefined){
		    var dataParam = [{ customQuery: '' }];
		    var beanParam = {
		        taskNo: taskNo,
		        mgrNo: mgrNo,
		        mgrName: mgrName,
		        taskNoStr:""
		    };
		    jQuery.ajax({
		        url: webPath+"/recallBase/selectdUserToTaskAjax",
		        data: {
		            ajaxData: JSON.stringify(dataParam),
		            beanAjaxData: JSON.stringify(beanParam)
		        },
		        type: "POST",
		        dataType: "json",
		        beforeSend: function() {},
		        success: function(data) {
		            if (data.flag == "success") {
		            	//alert(data.msg,1);
		            	alert(top.getMessage("SUCCEED_UPDATE","更新成功。"),1);
		            	$("#recOpName").text(userInfo.opName);
		            }else{
		            	alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
		            }
		        },
		        error: function(data) {
		            alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
		        }
		    });
		}else{
			alert("请选择执行人！",0);
		}
		
		
	}; 
	return {
		init:_init,
		recallRegist:_recallRegist,
		recallReassign:_recallReassign
	};
}(window, jQuery);