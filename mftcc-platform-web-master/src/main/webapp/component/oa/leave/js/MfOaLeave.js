;
var OaLeave = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindInsertAjax("#OaLeaveInsert");
	};
	var _totalTime = function (){
	       var startTime=$("input[name='startTime']").val();
	       var endTime=$("input[name='endTime']").val();
	       if(startTime!="" && endTime!=""){
	       	  var startDateStr = startTime.substring(0,10);
	       	  var startTimeStr = startTime.substring(11,19);
	       	  var endDateStr = endTime.substring(0,10);
	       	  var endTimeStr = endTime.substring(11,19);	       	  
	          var startDate = new Date((startDateStr).replace(/-/g,"/"));
	          var endDate = new Date((endDateStr).replace(/-/g,"/"));
	          var num = (endDate-startDate)/(1000*3600*24);
	          var starthh = parseInt(startTime.substring(11,13), 10);
	          var endhh = parseInt(endTime.substring(11,13), 10);
	          var startmm = startTime.substring(14,16);
	          var endmm = endTime.substring(14,16);
	          var startss = startTime.substring(17,19);
	          var endss = endTime.substring(17,19);
	          if(starthh >= 18){
	          	startStr = "2017-01-01"+" "+"16:00:00";
	          }else if(starthh < 8){
	          	startStr = "2017-01-01"+" "+"08:00:00";
	          }else if(starthh>=12&&starthh<14){
	          	startStr = "2017-01-01"+" "+"12:00:00";
	          }else if (starthh >= 14 && starthh < 18){
	          	hhstart = starthh-parseInt(2,10);
	          	startStr = "2017-01-01"+" "+hhstart+startTime.substring(13,19);
	          }else{
	          	startStr = "2017-01-01"+" "+startTimeStr;
	          }
	          if(endhh >= 18){
	          	endStr = "2017-01-01"+" "+"16:00:00";
	          }else if(endhh < 8){
	          	endStr = "2017-01-01"+" "+"08:00:00";
	          }else if(endhh >= 12 && endhh < 14){
	          	endStr = "2017-01-01"+" "+"12:00:00";
	          }else if(endhh >= 14 && endhh < 18){
	         	var hhend = endhh-parseInt(2,10);
	          	endStr = "2017-01-01"+" "+hhend+endTime.substring(13,19);
	          }else {
	          	endStr = "2017-01-01"+" "+endTimeStr;
	          }
	          var startTimenew = new Date((startStr).replace(/-/g,"/"));
	          var endTimenew = new Date((endStr).replace(/-/g,"/"));
	          var num1 = (endTimenew-startTimenew)/(1000*3600*8);
	          var n = (num+num1).toFixed(1);
	          var m= n;
	          if(m<0){
	        	  window.top.alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":"结束时间","timeTwo":"开始时间"}),0,function(){
	        		  $("input[name='startTime']").val("");
	        		  $("input[name='endTime']").val("");
	        	  });
	          }else{
	        	  $("input[name='timeSum']").val(m);
	          }
	       }
	  };
	
	var _bindInsertAjax = function(obj){		
		$(".insertAjax").bind("click", function(event){
			var leaveSts="1";
			var startTime=$("input[name='startTime']").val();
		    var endTime=$("input[name='endTime']").val();
			var timeSum=$("input[name='timeSum']").val();
			var leaveReason = $("textarea[name='leaveReason']").val();
			if(leaveReason.length>100){
				/*alert("请假理由在100个汉字以内");
				return false;*/
				$("textarea[name='leaveReason']").val(leaveReason.substring(0,100));
			}
			var time = parseFloat(timeSum);
		    if(startTime =="" && endTime == "" && leaveReason == ""){
		    	alert(top.getMessage("NOT_FORM_EMPTY","开始时间、结束时间和请假理由"), 3);
		    	return false;
			}
		    if(startTime ==""){
		    	alert(top.getMessage("NOT_FORM_EMPTY","开始时间"), 3);
		    	return false;
			}
		    if(endTime == ""){
		    	alert(top.getMessage("NOT_FORM_EMPTY","结束时间"), 3);
		    	return false;
			}
            if(startTime > endTime ){
                alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"开始时间" , "timeTwo": "结束时间"}), 3);
                return false;
            }
		    if(leaveReason == ""){
		    	alert(top.getMessage("NOT_FORM_EMPTY","请假理由"), 3);
		    	return false;
			}
		    leaveSave(obj,leaveSts);					 
		});
	};
	  
   function leaveSave(obj,leaveSts){	
		var flag = submitJsMethod($(obj).get(0), '');	
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());				
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					leaveSts:leaveSts,
					query:query
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {				
						LoadingAnimate.stop();
						if (data.flag == "success") {
							if(query=="perCenter"){
								window.top.alert(data.msg, 3);
								myclose_click();
							}else{
								var url=webPath+"/mfOaLeave/getListPage";
								window.top.alert(data.msg, 3);
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								myclose();
							}
						} else {
							window.top.alert(data.msg, 0);
						}			
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		totalTime :_totalTime,
	};
}(window, jQuery);

