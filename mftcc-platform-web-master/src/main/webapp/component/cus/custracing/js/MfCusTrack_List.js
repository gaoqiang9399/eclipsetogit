$(function() {
		$(".mf_content").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//$("#track_type").html(myHandelbars("tracktype-template",trackTypeData));
		initAddPlanDialog();
		addTrackPlanAjax();
		/*if($('#allotCusMngButton').css('display')=='inline-block'){//有分配客户经理按钮权限
			if(notFollowupFlag=='0'){//1-未跟进客户 0-已跟进客户
				$("#allotCusMngButton").hide();
			}
		}*/
	});
	// 客户跟进类型选择组件
	$("input[name=trackType]").popupSelection({
		searchOn:false,//启用搜索
		inline:true,//下拉模式
		multiple : false,//单选
		items:trackTypeArray,
		height:'200px',
		addBtn:{//添加扩展按钮
			"title":"新增",
			"fun":function(hiddenInput, elem){
				$(elem).popupSelection("hideSelect", elem);
				BASE.openDialogForSelect('新增跟进类型','TRACK_TYPE', elem);
			}
		},
		changeCallback : function (obj, elem) { 
			$(".pops-value").addClass("form-control left-text");
			$(".pops-item").addClass("left-text");
		}
	});
	$(".pops-value").addClass("form-control left-text");
	$(".pops-item").addClass("left-text");
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	function showTrackForm(obj){
		$("#trackForm").addClass("in");
		$(obj).parent().css("display","none");
		$("textarea[name='trackContent']").parent().find("span[class='wrap-placeholder']").remove();
		$("textarea[name='trackContent']").placeholder({isUseSpan:true,all:false}); 
	};

	function insertAjax(obj){
        LoadingAnimate.start();
        var dataParam = JSON.stringify($(obj).serializeArray());
        $.ajax({
            url:webPath+"/mfCusTrack/insertAjax",
            data:{
                ajaxData : dataParam,
            },
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    LoadingAnimate.stop();
                    top.saveFlag = true;
                    top.cusTrack = data.cusTrack;
                    myclose_click();
                }
            },error:function(){
                alert(top.getMessage("ERROR_INSERT"),0);
            }
        });
	};
	//选择随行人员
	function assignExamineTaskSingle(){
		selectUserCustomTitleDialog("选择随行人员",function(userInfo){
			var opNo=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
			var opName=userInfo.brName.replace(new RegExp(/(;)/g),'|');
			$('input[name=attendant]').val(opName);
			$('input[name=attendantNo]').val(opNo);
		});
	};
	function addTrackForm(){
		top.saveFlag = false;
        top.cusTrack = "";
        top.window.openBigForm(webPath+"/mfCusTrack/input?cusNo="+cusNo ,'客户跟进', function(){
        	if (top.saveFlag){
				var cusTrack = top.cusTrack;
                var htmlStr = '<div class="container track-line" id="track-line">'
                    + '<div id="track-info" class="track-info">'
                    + '<div class="row" style="text-align: left;">'
                    + '<span class="track-type-list">['
                    + cusTrack.trackTypeName
                    + ']</span>'
                    + '<span class="padding_0" >'
                    + cusTrack.opName
                    + '</span>'
                    + '<span class="track-reg-time">登记于'
                    + cusTrack.regTime
                    + '</span>'
                    + '</div>'
                    + '<div class="row padding_bottom_15">'
                    + '<div class="track-content">'
                    + cusTrack.trackContent
                    + '</div>'
                    +

                    '<div class="track-opt">'
                    + '<a href="javascript:void(0);" id="comment-num" data-toggle="collapse" data-target="#'
                    + cusTrack.trackId
                    + 'Comment" num="0">评论(0)</a>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '<div class="comment-div collapse" id="'+cusTrack.trackId+'Comment">'
                    + '<div class="comment-add margin_top_10">'
                    + '<form>'
                    + '<div class="form-group padding_0">'
                    + '<input value="'+cusTrack.cusNo+'" name="cusNo" type="hidden">'
                    + '<input value="'+cusTrack.trackId+'" name="parentId" type="hidden">'
                    + '<input value="2" id="handleType" type="hidden">'
                    + '<div>'
                    + '<textarea class="form-control" rows="4" maxlength="200" name="trackContent" style="border-radius: 0px;resize:none;" placeholder="请在这里登记您的评论（最多输入200个字）"></textarea>'
                    + '</div>'
                    + '<div style="text-align: left;">'
                    + '<button type="button" class="btn"  style="border-radius: 0px;background: #32b5cb;color: #fff;font-size: 12px;margin-top: 10px;" onclick="ajaxInsertComment(this)">提交评论</button>'
                    + '</div>'
                    + '</div>'
                    + '</form>'
                    + '<p id="comment-message" style="display: none;text-align: left;color: red;">您未输入任何内容！</p>'
                    + '</div>'
                    + '<div class="comment-info"></div>'
                    + '</div>' + '</div>';
                $(".track-div").find(".message").remove();
                $(".track-div").prepend(htmlStr);
			}
		});
	};

	//新增跟踪信息/评论/更新信息
	function ajaxInsertTrack(obj) {//obj是操作按钮本身
		var trackContent = $(obj).parent().parent().find("textarea").val();
		//判断客户内容是否为空
		if (trim(trackContent) == "") {
			window.top.alert(top.getMessage("NOT_FORM_EMPTY", "跟进内容"),0);
			return false;
		}
		if (handelType == '2') {
			updateTrack(obj);
		} else {
			var formObj = $(obj).parents(".addtracdiv").find("form");
			var dataParam = JSON.stringify($(formObj).serializeObject());
			LoadingAnimate.start();
			jQuery
					.ajax({
						url : webPath+'/mfCusTrack/insertAjax',
						data : {
							ajaxData : dataParam
						},
						type : "POST",
						dataType : "json",
						beforeSend : function() {
						},
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								// window.alert(top.getMessage("SUCCEED_OPERATION"),1);
								var cusTrack = data.cusTrack;
								if (handelType == '1') {
									var trackType = $(".addtracdiv").find(
											"select").find("option:selected")
											.text();
									var htmlStr = '<div class="container track-line" id="track-line">'
											+ '<div id="track-info" class="track-info">'
											+ '<div class="row" style="text-align: left;">'
											+ '<span class="track-type-list">['
											+ cusTrack.trackTypeName
											+ ']</span>'
											+ '<span class="padding_0" >'
											+ cusTrack.opName
											+ '</span>'
											+ '<span class="track-reg-time">登记于'
											+ cusTrack.regTime
											+ '</span>'
											+ '</div>'
											+ '<div class="row padding_bottom_15">'
											+ '<div class="track-content">'
											+ cusTrack.trackContent
											+ '</div>'
											+

											'<div class="track-opt">'
											+ '<a href="javascript:void(0);" id="comment-num" data-toggle="collapse" data-target="#'
											+ cusTrack.trackId
											+ 'Comment" num="0">评论(0)</a>'
											+ '</div>'
											+ '</div>'
											+ '</div>'
											+ '<div class="comment-div collapse" id="'+cusTrack.trackId+'Comment">'
											+ '<div class="comment-add margin_top_10">'
											+ '<form>'
											+ '<div class="form-group padding_0">'
											+ '<input value="'+cusTrack.cusNo+'" name="cusNo" type="hidden">'
											+ '<input value="'+cusTrack.trackId+'" name="parentId" type="hidden">'
											+ '<input value="2" id="handleType" type="hidden">'
											+ '<div>'
											+ '<textarea class="form-control" rows="4" maxlength="200" name="trackContent" style="border-radius: 0px;resize:none;" placeholder="请在这里登记您的评论（最多输入200个字）"></textarea>'
											+ '</div>'
											+ '<div style="text-align: left;">'
											+ '<button type="button" class="btn"  style="border-radius: 0px;background: #32b5cb;color: #fff;font-size: 12px;margin-top: 10px;" onclick="ajaxInsertComment(this)">提交评论</button>'
											+ '</div>'
											+ '</div>'
											+ '</form>'
											+ '<p id="comment-message" style="display: none;text-align: left;color: red;">您未输入任何内容！</p>'
											+ '</div>'
											+ '<div class="comment-info"></div>'
											+ '</div>' + '</div>';

									$(".track-div").find(".message").remove();
									$(".track-div").prepend(htmlStr);
									//$(".addtracdiv input[name=trackContent]").val('');
									$(".addtracdiv textarea[name=trackContent]")
											.val('');
									handelType = "1";
									top.updateFlag = true;
									$("#trackForm").removeClass("in");
									$("#show-form").css("display","block");
								}
							} else if (data.flag == "error") {
								alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							alert(top.getMessage("FAILED_OPERATION"," "), 0);
						}
					});
		}
	}
	//添加评论信息
	function ajaxInsertComment(obj) {//obj是操作按钮本身
		var trackComment = $(obj).parent().parent().find("textarea").val();
		//对评论内容进行判断
		if (trim(trackComment) == "") {
			window.top.alert(top.getMessage("NOT_FORM_EMPTY", "评论内容"),0);
			return false;
		}
		var formObj = $(obj).parents("div.comment-add").find("form");
		var commentnum = $(obj).parents(".track-line").find("#comment-num");
		var dataParam = JSON.stringify($(formObj).serializeArray());
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+'/mfCusTrack/insertAjax',
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					//window.alert(top.getMessage("SUCCEED_OPERATION"),1);
					var cusTrack = data.cusTrack;
					var num = parseInt($(commentnum).attr('num'));
					var htmlStr = '';
					htmlStr = '<div class="row comment-reg-time">'
							+ '<div class="col-sm-12">' + cusTrack.opName
							+ '&nbsp;&nbsp;登记于' + cusTrack.regTime +'</div>'
							+ '</div>' + '<div class="row">'
							+ '<div class="col-sm-12">' + cusTrack.trackContent
							+ '</div>' + '</div>';

					$(obj).parents(".track-line").find(".comment-info").append(
							htmlStr);
					num = num + 1;
					$(commentnum).attr('num', num);
					$(commentnum).text('评论(' + num + ')');
					//var commentContent=$(obj).parents(".comment-add").find("input[name=trackContent]");
					//$(commentContent).val('');
					$(obj).parents(".comment-add").find(
							"textarea[name=trackContent]").val('');
					$("#comment-message").css("display", "none");
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};

	function updateTrack(obj) {
		var dataParam = JSON.stringify($(obj).serializeObject());
		LoadingAnimate.start();
		$.ajax({
			url : webPath+"/mfCusTrack/updateAjax",
			data : {
				ajaxData : dataParam
			},
			dataType : "json",
			type : "POST",
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					alert(top.getMessage("SUCCEED_OPERATION"), 1);
					$(editbtn).parents(".track-line").find(
							".track-info .track-type-list").html(
							$(".addtracdiv").find("select").find(
									"option:selected").text());
					$(editbtn).parents(".track-line").find(
							".track-info .track-content").html(
							$(".addtracdiv").find("input[name=trackContent]")
									.val());
					$(".addtracdiv").find("input[name=trackContent]").val('');
					top.updateFlag = true;
				} else {
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	function initAddPlanDialog(){
        var i;
	  	for(i=0;i<24;i++){
	       if(i<10){
	            $("#starthour").append("<option value='0"+i+"'>0"+i+"</option>")
	            $("#endhour").append("<option value='0"+i+"'>0"+i+"</option>")
	       }else{
	            $("#starthour").append("<option value='"+i+"'>"+i+"</option>") 
	            $("#endhour").append("<option value='"+i+"'>"+i+"</option>")         
	       }       
	    }
	    for(i=0;i<6;i++){
           $("#startmin").append("<option value='"+i+"0'>"+i+"0</option>")
           $("#endmin").append("<option value='"+i+"0'>"+i+"0</option>")
	    }
	    $("#startdate").val(newDate);
	    $("#enddate").val(newDate);
	};
	/**
	 * 新建跟进计划弹窗
	 */
	function addTrackPlan() {
		
		artdialog = dialog({
			title : '新增跟进计划',
			id : "newEvent",
			content : $("#newEvent"),
			width : 400,
			height : 500,
			backdropOpacity : 0,
			onshow : function() {
			},
			onclose : function() {
			}
		});
		artdialog.showModal();
		$("textarea[name='title']").parent().find("span[class='wrap-placeholder']").remove();
		$("textarea[name='title']").placeholder({isUseSpan:true,all:false}); 
		
		var parentIndex=$("#warnTime").parent().offset().left; 
        var thisIndex=$("#warnTime").offset().left; 
		$("#warnTime").parent().find("span[class='wrap-placeholder']").remove();
		$("#warnTime").placeholder({isUseSpan:true,all:false}); 
		$("#location").parent().find("span[class='wrap-placeholder']").remove();
		$("#location").placeholder({isUseSpan:true,all:false}); 
		
	}
	/**
	*新建跟进计划提交
	*/
	function addTrackPlanAjax(){
		$(".insertAjax").click(function(){
			  //调整表单数据，并提交到底层
			  $("input[name='eventTime']").val($("#starthour").val()+":"+$("#startmin").val());
			  $("input[name='endTime']").val($("#endhour").val()+":"+$("#endmin").val());

			  var eventDate = $("input[name=eventDate]").val();
			  if(eventDate==""||eventDate==null){
			  	alert(top.getMessage("NOT_FORM_EMPTY","开始日期"), 3);
			  	return;
			  }
              var endDate = $("input[name=endDate]").val();
			  if(endDate==""||endDate==null){
                alert(top.getMessage("NOT_FORM_EMPTY","结束日期"), 3);
                return;
			  }
              var starttime = eventDate.replace(/-/g, "")+$("#starthour").val()+$("#startmin").val();
              var endtime = endDate.replace(/-/g, "")+$("#endhour").val()+$("#endmin").val();
              if(starttime>endtime){
              	alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"开始时间" , "timeTwo": "结束时间"}), 3);
              	return;
			  }
	          var url=webPath+"/workCalendar/fullCalendarInsertAjax";
	          var formObj = $("#eventForm");
	          var warnTimeMsg = num_validate(document.getElementById("warnTime"));
	          if(warnTimeMsg != ""){
	  			alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"输入的提醒时间（小时）" , "timeTwo": "零"}), 1);
	        	  return;
	          }
	          var title = $("#scheduleTitle").val();
	          if(title == ""){
	        	  window.top.alert(top.getMessage("NOT_FORM_EMPTY", "标题"),0);
	        	  return;
	          }
	          var dataParam = JSON.stringify(formObj.serializeArray());
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
	/**
	 * 根据Handlebars模板id和需要的数据源返回对应的html
	 * @param template <String> Handlebars模板id
	 * @param data <JS> 需要加载的数据源
	 * @returns 
	 */
	function myHandelbars(template,data){
		var myTemplate = Handlebars.compile($("#"+template).html());
		return myTemplate(data);
	};
	
	//客户经理新版选择组件
	function selectCusMngNameDialog(){
		selectUserDialog(function(data){
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/mfCusCustomer/updateCusManageAjax",
                type: "POST", //使用post方法访问后台
                data:{
                 	cusNo:cusNo,cusMngNo:data.opNo,cusMngName:data.opName
                },
                dataType : 'json',
                //要发送的数据
                success: function (data) {
                	LoadingAnimate.stop();
                    //对话框里面的数据提交完成，data为操作结果
                    if (data.flag == "success") {
						window.top.alert(top.getMessage("SUCCEED_OPERATION","分配客户经理"), 1);
					} else {
						window.top.alert(top.getMessage("FAILED_OPERATION","分配客户经理"),0);
					}
			     },
                 error : function() {
                	LoadingAnimate.stop();
                	window.top.alert(top.getMessage("FAILED_OPERATION","分配客户经理"),0);
				 }
           });
		});
	};
