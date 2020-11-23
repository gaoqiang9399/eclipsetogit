	function list(eventDate,eventTime){
			var status="1";
			// window.open(webPath+"/workCalendar/fullCalendarInput?workCalendar.makeDate="+makeDate+"&workCalendar.makeMan="+brno+"&workCalendar.endSts="+status+"&flag=1"+"&workCalendar.eventDate="+eventDate+"&workCalendar.eventTime="+eventTime,"window","status:no;help:no;border:thin;statusbar:no,left=200,top=30,resizable=yes,width=1000,height=520");
			var url = webPath+"/workCalendar/fullCalendarInput?workCalendar.endSts="+status+"&flag=1"+"&workCalendar.eventDate="+eventDate+"&workCalendar.eventTime="+eventTime;
			dateCtrl(url);
		}
	function listNew(No){
		var url = webPath+"/workCalendar/fullCalendargetById?calendarNo="+No;
		dateCtrl(url);
      // window.open(webPath+"/workCalendar/fullCalendargetById?calendarNo="+No,"window","status:no;help:no;border:thin;statusbar:no,left=200,top=30,resizable=yes,width=1000,height=520");
	}
	
	var dateCtrl = function(url){
		if($('.date-zone-html').length>0){
			$('.date-zone-html').find('iframe').remove();
			$('.date-zone-html').remove();
			return false;
		}
		var $elem = $(DateHtml);
		$elem.find('.date-zone-title').html("日程管理");
		$elem.find('.i').bind("click",function(){
			$('.date-zone-html').slideUp(function(){
				$('.date-zone-html').find('iframe').remove();
				$('.date-zone-html').remove();
			});
		});
		$elem.hide();
		$elem.appendTo('body');
		$elem.slideDown(function(){
			$elem.find("iframe").attr("src",url);
		});
	};
	function relaod(data){
		$('.date-zone-html').slideUp(function(){
			$('.date-zone-html').find('iframe').remove();
			$('.date-zone-html').remove();
			location.reload();   
		});
	}
	var DateHtml = '<div class="date-zone-html">'
		+'<div class="date-zone-head">'
		+'<span class="date-zone-title"></span><i class="i i-x2"></i>'
		+'</div>'
		+'<div class="date-zone-body">'
		+'<iframe id="date-zone-iframe" src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" ></iframe>'
		+'</div>'
		+'</div>';
	var holidays = [];//假期管理
	function eventsInfo(){
		var arr;
		$.ajax({
			 type: "POST",
			 async: false,
			 cache:false,
             url: webPath+"/workCalendar/fullCalendarmonthlistAjax",
             dataType: "json",
             success: function(data){
            	 arr = eval(data.eventAll);
            	 holidays = data.holidays;
             },
             error:function(xmlhq,ts,err){
            	 window.top.alert("获取日程失败！");
             }
		});
		return arr;
	}
	function viewHolidays(currdate){
		$.ajax({
			 type: "POST",
			 async: false,
			 cache:false,
             url: webPath+"/workCalendar/fullCalendarGetHolidayslistAjax",
             dataType: "json",
             data:{
            	 event_date:currdate
             },
             success: function(data){
            	 showHolidays(data.holidays);
             },
             error:function(xmlhq,ts,err){
            	 window.top.alert("获取日程失败！");
             }
		});
	}
	//显示设置的节假期
	function showHolidays(array){
		$(".fc-day-number").each(function(i,elem){
			$.each(array,function(j,obj){
				var str = obj.begDt;
				str = str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
				if($(elem).data("date")==str){
					//$(elem).append("<span class='fc-day-number-holiday'>休</span>");
					//$($(".fc-day")[i]).append("<div class='fc-day-holiday'></div>");
					$(elem).css("color","#999");
				}
			})
		});
	}
	//显示当前年月
	function showCurrDate(){
		var d = $('#calendar').fullCalendar('getDate');
		var dayWrapper = moment(d);
		$(".fc-diy-center h2").text(dayWrapper.format("YYYY年 MMMM"));
		return dayWrapper;
	}
	$(document).ready(function() {
		$('#calendar').fullCalendar({
			events:eventsInfo(),
			firstDay:0,//设置第一天
 /*			events:<s:property value="event_all"/>,*/
			header: {// 表头
				left: false, center: false, right: false
				 /*
 				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay' 
				  */
			},
			editable: true, // 允许拖动
		    dragOpacity: {// 设置拖动时事件的透明度
		        agenda: .5, 
		        '':.6 
		    },
		   // eventBackgroundColor:'#fff',
		   // eventBorderColor:'#4dbaeb',
		   // eventBorderColor:'#fff',
		    eventTextColor:'#666666',
		    aspectRatio:1.6,
		    contentHeight:500,
			lang: 'zh-cn',// 语言
			defaultDate: moment().format('L'),
			eventLimit: true, // allow "more" link when too many events
// 鼠标点击天事件
			dayClick: function(date, allDay, jsEvent, view) {
				var eventDate;
				var eventTime;
				if(date.format().length==10){
					eventDate = date.format();
					eventTime = "";
				}else{
					eventDate = String(date.format()).substr(0,10);
					eventTime = String(date.format()).substr(11,5);
				}
				list(eventDate,eventTime);
			},	
// 当点击某一个事件(填好的)时触发此操作
			eventClick: function(calEvent, jsEvent, view) {
				listNew(calEvent.id);
    		},
// 鼠标拖拽事件
			eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) { 
				var end_date = event.end;
				if(end_date!=null){
					end_date = end_date.format();
				}
				var event_date = event.start.format();
				$.ajax({
					url:webPath+"/workCalendar/drag",
	  	 		 	type:"post",
	  	  			data:{"calendarNo":event.id,"event_date":event_date,"end_date":end_date},
	  	  			success:function(data){
	  	  				if(data.hasOwnProperty('workCalendar')){
	  	  					window.top.recWarnd.updateData(data.workCalendar);
	  	  				}
	  	  				window.top.alert("修改成功",1);
	  	  			},
	  	  			error:function(){
	  	  				window.top.alert("修改出错",0);
	  	  			}	
	  	  		});
   			 },
   			eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
   				var end_date = event.end;
				if(end_date!=null){
					end_date = end_date.format();
				}
				var event_date = event.start.format();
				$.ajax({
					url:webPath+"/workCalendar/drag",
	  	 		 	type:"post",
	  	  			data:{"calendarNo":event.id,"event_date":event_date,"end_date":end_date},
	  	  			success:function(data){
		  	  			if(data.hasOwnProperty('workCalendar')){
		  					window.top.recWarnd.updateData(data.workCalendar);
		  				}
	  	  				window.top.alert("修改成功",1);
	  	  			},
	  	  			error:function(){
	  	  				window.top.alert("修改出错",0);
	  	  			}	
	  	  		});
   		    }
		});
		$('body').mCustomScrollbar({
			theme:"minimal-dark",
			advanced:{
				updateOnContentResize:true
			}
		});
		showHolidays(holidays);
		$(".fc-diy-center button").bind("click",function(){
			$('#calendar').fullCalendar($(this).data('click'));
			var currDate = showCurrDate();
			var view = $('#calendar').fullCalendar('getView');
			if(view.name=="month"){
				viewHolidays(currDate.format('YYYYMM'));
			}
		})
		$(".fc-diy-right button").bind("click",function(){
			$('#calendar').fullCalendar('changeView',$(this).data('click'));
			var currDate = showCurrDate();
			var view = $('#calendar').fullCalendar('getView');
			if(view.name=="month"){
				viewHolidays(currDate.format('YYYYMM'));
			}
			$(this).addClass("on").siblings().removeClass("on");
		})
		showCurrDate();
	});