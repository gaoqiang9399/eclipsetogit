;
var OaNoticeList = function(window, $) {
	
	var _init = function () {
		$(".notice-div").height($(".notice-div").parent().parent().height()-100);
		var len = $(".notice-div tbody tr").length;
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
			var nDivHeight = $(".notice-div").height();
			}else{
				$(".loadCount").text(len);
			}
		$(".notice-div").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			},
			callbacks: {	
				onUpdate : function(){
					 $(window).resize(function() {
						 $(".notice-div").height($(".notice-div").parent().parent().height()-100);
					 });
					},
				whileScrolling :function(){
					nScrollHeight = $(this)[0].scrollHeight; //是notice-div的高度  
					nScrollTop = $(this)[0].scrollTop;//从顶端到现在滚动的高度 
					var paddingBottom = parseInt($(this).css('padding-bottom'));//.notice-div的内边距 
					var paddingTop = parseInt($(this).css('padding-top'));
                    var i;
					if(paddingBottom+paddingTop+nScrollTop+nDivHeight >= nScrollHeight){//判斷滾動條到達底部 
						console.log(paddingBottom);
						console.log(paddingTop);
						$(".fa-3x").show();
						setTimeout(function(){
							$(".fa-3x").hide();
						},1000);
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
		//$("#notice-div").css("height",height);
		_ajaxGetById();
		_ajaxPublishNotice();
		_ajaxFindNoticeLooking();
		_ajaxTrDeleteOaList();
	    _noticeInsert();	
	    _dblClick();
	  //滚动分页展示 
		
    
	};
	
   var _dblClick = function(obj){
	   $.each($("tr"),function(i,tr){
		   	$(this).bind("dblclick", function(event) {
		   		var buttonTitle =  $(this).find(".button-title");
		   		var noticeId = buttonTitle.attr("value");
		   		var noticeSts = buttonTitle.attr("name");
                var url;
				if(0==noticeSts){
					 url = webPath+"/mfOaNotice/getById1?noticeId="+noticeId;
					   top.addFlag = false;
					   top.openBigForm(url,"通知公告详情", function() {
							myclose();
				      //window.parent.openBigForm(url,"通知公告信息");
							if (top.addFlag) {
								window.location.reload();
							}
						});
				}else{
					 url = webPath+"/mfOaNotice/getById?noticeId="+noticeId;
					   top.addFlag = false;
					   top.openBigForm(url," ", function() {
							myclose();
				      //window.parent.openBigForm(url,"通知公告信息");
							if (top.addFlag) {
								window.location.reload();
							}
						});
				};	  
		   });
	  });
   }
	
   var _noticeInsert = function() {
		$("#noticeInsert").bind("click", function(event) {
			/* top.window.openBigForm(webPath+'/mfOaNotice/input','新增公告',_closeCallBack); */
			top.addFlag = false;
			top.openBigForm(webPath+"/mfOaNotice/input?menuNo="+menuNo, "新增公告", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		});
	}; 
	
	
   
	var _ajaxGetById = function (obj){
		   $(".button-title").bind("click", function(event){
				var noticeId = $(this).attr("value");
				var noticeSts = $(this).attr("name");
               var url;
				if(0==noticeSts){
					 url = webPath+"/mfOaNotice/getById1?noticeId="+noticeId;
					   top.addFlag = false;
					   top.openBigForm(url,"通知公告详情", function() {
							myclose();
				      //window.parent.openBigForm(url,"通知公告信息");
							if (top.addFlag) {
								window.location.reload();
							}
						});
				}else{
					 url = webPath+"/mfOaNotice/getById?noticeId="+noticeId;
					   top.addFlag = false;
					   top.openBigForm(url," ", function() {
							myclose();
				      //window.parent.openBigForm(url,"通知公告信息");
							if (top.addFlag) {
								window.location.reload();
							}
						});
				};
				
		   });
	   };
   
    var _ajaxFindNoticeLooking = function (obj ,url){
    	/* top.addFlag = false;
 		top.openBigForm(url,"查阅情况", function() {
 			myclose();
        // window.parent.openBigForm(url,"查阅情况");
 		});*/
    	$(".noticeLooking").bind("click", function(event){
    		var noticeId = $(this).attr("value");
			var url = webPath+"/mfOaNotice/getNoticeLooking?noticeId="+noticeId;
    	top.showDialog(url,"查阅情况", "60","60");
    	});
        };
        
        
    var _ajaxTrDeleteOaList = function (obj){
    	$(".delete").bind("click", function(event){
    		var html = $(this); 
        	var noticeId = $(this).attr("value");
        	var url = "/mfOaNotice/deleteAjax?noticeId="+noticeId;
        	ajaxTrDelete(html,url);
    	});
        };
        
        var _ajaxPublishNotice = function (){
        	$(".noticePublish").bind("click", function(event){
        		var noticeId = $(this).attr("value");
        		alert(top.getMessage("CONFIRM_OPERATION","发布"),2,function(){
        			jQuery.ajax({
        				url:webPath+"/mfOaNotice/publishNoticeAjax?noticeId="+noticeId,
        				type:"POST",
        				dataType:"json",
        				beforeSend:function(){  
        				},success:function(data){
        					if(data.flag == "success"){
        						alert(top.getMessage("SUCCEED_OPERATION"),1);
        						window.location.reload();
        					}else if(data.flag == "error"){
        						$.myAlert.Alert(data.msg);
        					}
        				},error:function(data){
        					alert(top.getMessage("FAILED_OPERATION"," "),0);
        				}
        			});
        		
        		});
        	});
        };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,		
	};
}(window, jQuery);