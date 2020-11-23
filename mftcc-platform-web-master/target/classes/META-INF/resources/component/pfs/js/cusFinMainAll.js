var dataMap;
var view_div_css;
$(window).resize(function() {
    var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
	$(".div_role_body").css("width",bW);
});
$(function(){
		var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
		$(".div_role_body").css("width",bW);
		var cusFinMain = dataMap.cusFinMainList;
		if(cusFinMain!=null){
			$.each(cusFinMain,function(i,obj){
				add_cusFinMain_obj(obj);
			});
		}
		new_cusFinMain_obj();
	});
	(function($){
		$.fn.role_btn_del = function(url) {
			$(this).bind('click', function() {
				var $this = $(this).parents(".div_role_rotate");
				$.ajax({
					 type: "get",
					 url: url,
		             dataType: "json",
		             success: function(data){
						if(data.flag=="success"){
							$this.animate({width:0,padding:0,opacity:0},"300","linear",function(){$(this).remove();});
						}else if(data.flag=="error"){
							window.top.alert(top.getMessage("FAILED_DELETE"),0);
						}
		             },
		             error:function(xmlhq,ts,err){
		            	 window.top.alert(top.getMessage("FAILED_DELETE"),0);
		             }
				});
			});
		};
	})(jQuery);
	function new_cusFinMain_obj(){
		var $div_rotate_add = $("<div class='div_role_rotate div_role_add'></div>");
		var $div_obj_add = $("<div class='div_role_obj'></div>");
		var $div_front_add = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//鼠标悬停
		var $div_hover_add = $('<div class="buttons"></div>');
		var $div_hover_view_add = $('<a title="新增" alt="新增" href="#" class="i i-jia2"></a>');
		var $div_hover_view = $('<a title="批量导入" alt="批量导入" href="#" class="i i-bi"></a>');
		var $i_icon_add = $("<i class = 'i i-jia2 i_role_plus'></i>");
		$div_hover_add.appendTo($div_front_add);
		$i_icon_add.appendTo($div_front_add);
		$div_hover_view_add.appendTo($div_hover_add);
		$div_hover_view.appendTo($div_hover_add);
		$div_obj_add.append($div_front_add);
		$div_obj_add.appendTo($div_rotate_add);
		$div_rotate_add.appendTo($("#div_role_body"));
		$div_hover_view_add.click(function(){
			var cusNo = $("input[name=cusNo]").val();
			top.flag = false;
			window.top.window.showDialog(webPath+'/cusFinMain/input?cusNo='+cusNo,'新增报表',70,80,function(){
				if(top.flag){
					add_cusFinMain_obj(top.obj,true);
				}
			});
		});
		$div_hover_view.click(function(){
			var cusNo = $("input[name=cusNo]").val();
			//alert("批量导入cusNo:"+cusNo,1);
			window.top.window.showDialog(webPath+'/cusFinUploadFiles/toUpload?cusNo='+cusNo,'批量导入报表',70,80);
		});
	}
	function add_cusFinMain_obj(obj,flag){
		var $div_rotate = $("<div class='div_role_rotate'></div>");
		var $div_obj = $("<div class='div_role_obj'></div>");
		var $div_front = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//鼠标悬停
		var $div_hover = $('<div class="buttons"></div>');
		var $div_hover_view = $('<a title="修改" alt="修改" href="#" class="i i-bi"></a>');
		var $div_hover_menu = $('<a title="查看" alt="查看" href="#" class="i i-fangda"></a>');
		var $div_hover_confirm = $('<a title="数据确认" alt="数据确认" href="#" class="i i-duihao1"></a>');
		var $div_hover_del = $('<a title="删除" alt="删除" href="#" class="i i-lajitong"></a>');
		$div_hover.appendTo($div_front);
		$div_hover_view.appendTo($div_hover);
		$div_hover_menu.appendTo($div_hover);
		$div_hover_confirm.appendTo($div_hover);
		$div_hover_del.appendTo($div_hover);
		
		$div_hover_view.click(function(){
			if(obj.finRptSts==1){
				window.top.window.showDialog(webPath+'/cusFinMain/getById?cusNo='+obj.cusNo+'&finRptType='+obj.finRptType+'&finRptDate='+obj.finRptDate+"&query=query",'编辑报表',70,80);
			}else{
				window.top.window.showDialog(webPath+'/cusFinMain/getById?cusNo='+obj.cusNo+'&finRptType='+obj.finRptType+'&finRptDate='+obj.finRptDate,'编辑报表',70,80);
			}
		});
		$div_hover_menu.click(function(){
			//alert(webPath+'/cusFinMain/inputReportView?cusNo='+obj.cusNo+'&finRptType='+obj.finRptType+'&finRptDate='+obj.finRptDate+'&accRule='+obj.accRule);
			if(obj.finRptSts==1){
				window.top.window.showDialog(webPath+'/cusFinMain/inputReportView?cusNo='+obj.cusNo+'&finRptType='+obj.finRptType+'&finRptDate='+obj.finRptDate+'&accRule='+obj.accRule+"&finRptSts=1&query=query",'财务报表',false);
			}else{
				window.top.window.showDialog(webPath+'/cusFinMain/inputReportView?cusNo='+obj.cusNo+'&finRptType='+obj.finRptType+'&finRptDate='+obj.finRptDate+'&accRule='+obj.accRule,'财务报表',false);
			}
		});
		var delUrl = webPath+"/cusFinMain/deleteAjax?cusNo="+obj.cusNo+"&finRptType="+obj.finRptType+"&finRptDate="+obj.finRptDate;
		$div_hover_del.role_btn_del(delUrl);
		$div_hover_confirm.click(function(){
			var cusNo = obj.cusNo;
			var finRptType = obj.finRptType;
			var finRptDate = obj.finRptDate;
			var accRule = obj.accRule;
			var cusName = obj.cusName;
			window.top.alert("确认数据",2,function(){
				LoadingAnimate.start();
				$.ajax({
					type:"post",
					url:webPath+"/cusFinMain/updateReportConfirmAjax",
					async:false,
					data:{cusNo:cusNo,finRptDate:finRptDate,finRptType:finRptType,accRule:accRule,cusName:cusName},
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag=="success"){
							window.top.alert(data.msg,1);
							window.location.reload();
						}else if(data.flag=="error"){
							window.top.alert(data.msg,0);
						}
					},error:function(){
						LoadingAnimate.stop();
						window.top.alert("数据确认失败！",0);
					}
				});
			});
		});
		var finRptType = '';
		if(obj.finRptType==1){
			finRptType = "月报";
		}else if(obj.finRptType==2){
			finRptType = "季报"
		}else if(obj.finRptType==3){
			finRptType = "年报";
		}
		var accRule = "";
		if(obj.accRule==0){
			accRule = "旧会计准则";
		}else if(obj.accRule==1){
			accRule = "新会计准则";
		}
		var finRptSts = "",finRptStsImg = "";
		if(obj.finRptSts==0){
			finRptSts = "数据未确认";
			finRptStsImg = "finRptStsFalse"
		}else if(obj.finRptSts==1){
			finRptSts = "数据已确认";
			finRptStsImg = "finRptStsTrue"
			$div_hover_view.remove();
			$div_hover_confirm.remove();
			$div_hover_del.remove();
		}
		var $finRptType = $("<div class='div_acc_type finRptType'>"+finRptType+"</div>");
		var $accRule = $("<div class='div_acc_bank_name accRule'>"+accRule+"</div>");
		var $finRptDate = $("<div class='div_acc_bank finRptDate'>"+obj.finRptDate+"</div>");
		//var $remark = $("<div class='div_acc_no remark'>"+obj.remark+"</div>");
		var $finRptSts = $("<div class='finRptSts'><span class=\"finRptSts_text\">"+finRptSts+"</span><span class=\""+finRptStsImg+"\"></span></div>");
		var $i_role_user=$("<i class = 'i_role_user'></i>");
		//添加按钮
		var c = '';
		$div_front.append($finRptType);
		$div_front.append($finRptDate);
		$div_front.append($accRule);
		$div_front.append($i_role_user);
		$div_front.append($finRptSts);
		//$div_front.append($remark);
		$div_obj.append($div_front);
		$div_obj.appendTo($div_rotate);
		if(flag){
			$(".div_role_add").before($div_rotate);
		}else{
			$div_rotate.appendTo($("#div_role_body"));
		}
		
	}
