var monthNum="";
var dayNum = "";
var weekNum = "";


var FullCalendar_month_list = function(window, $) {
	var _init = function () {
		$('#calendar').fullCalendar({
			theme: false,
			header: {
				left: 'prev,title,next,today',
				center: 'month,agendaWeek',
				right: ''
			},
			editable: true, // 允许拖动
			allDayDefault :false,//不默认全天
			aspectRatio:2.1,
			/*monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],*/
            monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            today: ["今天"],
            firstDay: 1,
            buttonText: {
                today: '今天',
                month: '月',
                week: '周',
                day: '日',
                prev: '<',
                next: '>'
            },
            titleFormat:{ //日历头格式
            	month: "yyyy'年'MMMM",
            	week: "yyyy'年'MMMd'日' { '-' yyyy年MMMd'日'}", 
            },
            columnFormat:{ //列表头格式
            	week: 'ddd M-d',
            },
            timeFormat:'H:mm{-H:mm}',
            events:function(start, end, callback){
		             var holidays = [];
				     var arr;
				     LoadingAnimate.start();
				     $.ajax({
					 type: "POST",
					 async: false,
					 cache:false,
		             url: webPath+"/workCalendar/fullCalendarmonthlistAjax",
		             dataType: "json",
		             success: function(data){
		            	 LoadingAnimate.stop();
		            	 arr = eval(data.eventAll);
		            	 for(var iis = 0;iis<arr.length;iis++){
		            		 arr[iis].title = arr[iis].title.replace(new RegExp("<br>","gm"),"\r\n");
		            	 }
		            	 holidays = data.holidays;
		             },
		             error:function(xmlhq,ts,err){
		            	 LoadingAnimate.stop();
		            	 window.top.alert("获取日程失败！");
		              }                       
				    });//获取当前操作员创建的日程或他人共享的日程
		      		callback(arr);
            		},
			dayClick: function (date, allDay, jsEvent, view) {
				_clearForm();
				$(".insertAjax").attr("action","insert-event");
			     var selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");//选择当前日期的时间转换
			     $("#startdate").val(selectdate);
			     $("#enddate").val(selectdate);
			     _initMonthDayWeek(date);
			     _showDialog();
			 },
			 eventClick: function(calEvent, jsEvent, view) {
                      var calendarNo=calEvent.id;
                      LoadingAnimate.start();
                      $.ajax({
                         url:webPath+"/workCalendar/getByIdAjax",
                         type:"post",
                         data:{calendarNo:calendarNo},
                         success:function(data){
                        	LoadingAnimate.stop();
                            var calen=data.formData;
                            $("textarea[name=title]").val(calen.title.replace(new RegExp("<br>","gm"),"\r\n"));
                            $("#warnTime").val(calen.warnTime);
                            $("#location").val(calen.shareName);
                            $("#repetition").val(calen.repetition);
                            $("#desc").val(calen.eventDesc);
                            var eventDate = $.fullCalendar.formatDate(calEvent.start, "yyyy-MM-dd");
                            $("input[name='eventDate']").val(eventDate);
                            var endDate = $.fullCalendar.formatDate(calEvent.end, "yyyy-MM-dd");
                            $("input[name='endDate']").val(endDate); 
                            var starts= calen.eventTime.split(":");
                            $("#starthour").val(starts[0]);
                            $("#startmin").val(starts[1]);
                            var ends=calen.endTime.split(":");
                            $("#endhour").val(ends[0]);
                            $("#endmin").val(ends[1]);
                            $("input[name='calendarNo']").val(calen.calendarNo);
                            $(".insertAjax").attr("action","update-event");
                            _showDialog(calendarNo);
                         },
                         error:function(){
                        	 LoadingAnimate.stop();
                         }
                      })
             },
             eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) { 
				var end_date = event.end;										
				if(end_date!=null){
					end_date = $.fullCalendar.formatDate(end_date, "yyyy-MM-dd hh:mm:ss");
				}				
				var event_date = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd hh:mm:ss");
				LoadingAnimate.start();
				$.ajax({
					url:webPath+"/workCalendar/drag",
	  	 		 	type:"post",
	  	  			data:{"calendarNo":event.id,"event_date":event_date,"end_date":end_date},
		  	  			success:function(data){
		  	  			LoadingAnimate.stop();
	  	  				if(data.hasOwnProperty('workCalendar')){
	  	  					window.top.recWarnd.updateData(data.workCalendar);
	  	  				}	  	  				
		  	  			},
	  	  			error:function(){
	  	  				LoadingAnimate.stop();
	  	  				window.top.alert("修改出错",0);
	  	  			}	
	  	  		});
   			 },
   			 eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
   				var end_date = event.end;										
				if(end_date!=null){
					end_date = $.fullCalendar.formatDate(end_date, "yyyy-MM-dd hh:mm:ss");
				}				
				var event_date = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd hh:mm:ss");
				LoadingAnimate.start();
				$.ajax({
					url:webPath+"/workCalendar/drag",
	  	 		 	type:"post",
	  	  			data:{"calendarNo":event.id,"event_date":event_date,"end_date":end_date},
	  	  			success:function(data){
		  	  			LoadingAnimate.stop();
		  	  			if(data.hasOwnProperty('workCalendar')){
		  					window.top.recWarnd.updateData(data.workCalendar);
		  				}
	  	  			},
	  	  			error:function(){
	  	  				LoadingAnimate.stop();
	  	  				window.top.alert("修改出错",0);
	  	  			}	
	  	  		});
   		    },
            selectable: true,  
            selectHelper: true,   
            eventMouseover: function(event, jsEvent, view){
            	//为event添加bootstrap样式使用bootstrap弹出框
                $(jsEvent.target).attr({"title":event.title,"data-container":"body","data-toggle":"popover","data-placement":"bottom"});
           	}
		});
		//动态显示今天按钮
		$(".fc-button-today")[0].style.display="none";
		$(".fc-button").on("click",_todayShow);
		//新增日程按钮
		$(".fc-header-right").append("<span id='addEvent' class='fc-button fc-button-add ' unselectable='on'>新建日程</span>");
		_bindAddClick();//为新增日程按钮绑定点击事件
		_initForm();//初始化弹出表单
		_bindInsertClick();//为保存按钮添加点击事件
	};
	
	var _initForm = function() {
        var i;
	    for(i=0;i<24;i++){
	       if(i<10){
	         if(i==0){
	            $("#starthour").append("<option selected='selected' value='0"+i+"'>0"+i+"</option>")
	            $("#endhour").append("<option selected='selected' value='0"+i+"'>0"+i+"</option>")
	         }else{
	            $("#starthour").append("<option value='0"+i+"'>0"+i+"</option>")
	            $("#endhour").append("<option value='0"+i+"'>0"+i+"</option>")
	         }
	       }else{
	            $("#starthour").append("<option value='"+i+"'>"+i+"</option>") 
	            $("#endhour").append("<option value='"+i+"'>"+i+"</option>")         
	       }       
	    }
	    for(i=0;i<6;i++){
	        if(i==0){
	           $("#startmin").append("<option selected='selected' value='"+i+"0'>"+i+"0</option>")
	           $("#endmin").append("<option selected='selected' value='"+i+"0'>"+i+"0</option>")
	        }else{
	           $("#startmin").append("<option value='"+i+"0'>"+i+"0</option>")
	           $("#endmin").append("<option value='"+i+"0'>"+i+"0</option>")
	        }
	    }
	};
	var _bindInsertClick = function() {
		//点击保存按钮要执行的操作
		$(".insertAjax").click(function (){
			artdialog.close();
	          /*拼时间戳  */
	          var startstr = $("#startdate").val()+"T"+$("#starthour").val()+":"+$("#startmin").val(); //开始时间
	          var endstr = $("#enddate").val()+"T"+$("#endhour").val()+":"+$("#endmin").val(); //结束时间
	          startDate = $.fullCalendar.parseDate(startstr);  
	          endDate = $.fullCalendar.parseDate(endstr);  
	          if(startDate > endDate){
	             window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"开始时间" , "timeTwo": "结束时间"}),1); 
	             return;
	          }
			  //调整表单数据，并提交到底层
			  $("input[name='eventTime']").val($("#starthour").val()+":"+$("#startmin").val());
			  $("input[name='endTime']").val($("#endhour").val()+":"+$("#endmin").val());
	          //判断更新或是新增，确认URL
	          var flag = $(this).attr("action");
	          var url=webPath+"/workCalendar/fullCalendarInsertAjax";
	          if(flag == "update-event"){//更新操作
	        	  url = webPath+"/workCalendar/fullCalendarUpdateAjax";
	          }
	          var formObj = $("#eventForm");
	          var dataParam = formObj.serializeArray();
	          dataParam[0].value = dataParam[0].value.replace(/\n|\r\n/g,"<br>");
	          dataParam = JSON.stringify(dataParam);
	          var eventTitle = $("#scheduleTitle").val();
	          if(eventTitle == ""){
	        	  window.top.alert(top.getMessage("NOT_FORM_EMPTY","标题"),0);
	        	  return;
	          }
	         //ajax操作，将日程数据储存后台 
	          LoadingAnimate.start();
	          $.ajax({
	            	url: url, //要访问的后台地址
	                type: "POST", //使用post方法访问后台
	                data:{
	                 	ajaxData : dataParam
	                },
	                dataType : 'json',
	                //要发送的数据
	                success: function (data) {
	                	LoadingAnimate.stop();
	                    //对话框里面的数据提交完成，data为操作结果
	                    if (data.flag == "success") {
	                     	$(".cancel").click();                           	                            	
							window.top.alert(data.msg,1);
							$('#calendar').fullCalendar('refetchEvents');//重新加载日志
						} else {
							window.top.alert(data.msg,0);
						}
				     },
	                 error : function() {
	                	LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", ""));
					 }
	           });
	     });
	};
	/*var _bindClose = function() {
		$(".cancel").bind("click", function(event) {
			artdialog.close();
		});
	};*/
	//新增日程按钮点击
	var _bindAddClick = function() {
		$("#addEvent").click(function () {
			_clearForm();
			var date = $.fullCalendar.formatDate(new Date(),"yyyy-MM-dd");
			$("input[name='eventDate']").val(date);
			$("input[name='endDate']").val(date);
			_initMonthDayWeek(new Date());
			_showDialog();
       	});
	};
	var _deleteEvent = function(calendarNo){
		artdialog.close();
		window.top.alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			LoadingAnimate.start();
			$.ajax({
				url:webPath+"/workCalendar/fullCalendarDeleteAjax",
				data:{calendarNo:calendarNo},
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag=="success"){
						$('#calendar').fullCalendar('removeEvents', calendarNo);
						alert(data.msg,1);
					}else{
						alert(data.msg,0);
					}
				},
				error:function(){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_DELETE"),0);
				}
			});
		});
	};
	//新建日程和查看日程弹窗
	var _showDialog = function(obj){
		artdialog = dialog({
			id : "newEvent",
			content:$("#newEvent"),
			width:525,
			height:495,
			drag:true,
			backdropOpacity:0,
			onshow:function(){
			},
			onclose:function(){}
		});
		 if(obj){//查看和编辑日程
			 $(".delete").removeClass("hidden").attr("onclick","FullCalendar_month_list.deleteEvent("+obj+");");
			 $(".insertAjax").attr("value","更新");
			 artdialog.title("查看日程");
		 }else{
			 artdialog.title("新建日程");
			 $(".insertAjax").attr("value","保存");
			 $(".insertAjax").show();
			//重复周期动态赋值
		    $("#repetition option[value=3]").text("每周(每周的星期"+weekNum+")");
		    $("#repetition option[value=4]").text("每月("+dayNum+"日)");
		    $("#repetition option[value=5]").text("每年("+monthNum+"月"+dayNum+"日)");
		 }
		 artdialog.showModal();
	};
	//today按钮显示判断
	var _todayShow = function(){
		var dateNow = new Date();
		dateNowStr = $.fullCalendar.formatDate(dateNow,"yyyy-MM-dd");//当前系统年月
	    var dateView = $('#calendar').fullCalendar('getDate');
		dateViewStr = $.fullCalendar.formatDate(dateView,"yyyy-MM-dd");//当前日历年月
		if(dateNowStr == dateViewStr){
			$(".fc-button-today")[0].style.display="none";
		}else{
			$(".fc-button-today").removeAttr("style");
		}
	};
	
	//初始化月日周
	var _initMonthDayWeek =function(date){
		 var monthNumStr =  $.fullCalendar.formatDate(date, "MM");//月
	     monthNum= monthNumStr.charAt(0)!=0?monthNumStr:monthNumStr.charAt(1);
	     var dayNumStr =  $.fullCalendar.formatDate(date, "dd");//日
	     dayNum = dayNumStr.charAt(0)!=0?dayNumStr:dayNumStr.charAt(1);
	     weekNum =  $.fullCalendar.formatDate(date, "ddd");//周
	};
	
	
	//弹出日程dialog时调用该方法初始化表单
	var _clearForm = function() {
      $("#scheduleTitle").val("");   //标题
      $("#location").val(""); //共享人
      $("input[name='eventDate']").val("");
      $("input[name='endDate']").val("");
      $("#starthour").val("09");
      $("#startmin").val("00");
      $("#endhour").val("10");
      $("#endmin").val("00");
      $("#warnTime").val("");  
   };
   /**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		deleteEvent : _deleteEvent
	};
}(window, jQuery);