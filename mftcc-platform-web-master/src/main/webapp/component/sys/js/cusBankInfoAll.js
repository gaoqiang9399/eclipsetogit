var dataMap;
var view_div_css;
$(window).resize(function() {
    var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
	$(".div_role_body").css("width",bW);
});
$(function(){
		var bW = (parseInt($(document.body).outerWidth(true)/234))*234+5;
		$(".div_role_body").css("width",bW);
		var role = dataMap.cusBankInfoList;
		$.each(role,function(i,obj){
			add_role_obj(obj);
		});
		new_role_obj();
		$('#searchvalue').bind('keypress',function(event){
            if(event.keyCode == "13"){searchRole();}
        });
	});
	
	(function($) {
		$.fn.role_btn_back = function() {
			 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			$(this).bind('click', function() {
				var obj = $(this).parent().parent();
				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
					obj.find(".div_role_front").removeClass("sub_move_front");
					obj.find(".div_role_back").removeClass("sub_move_back");
			    }else{
					obj.addClass("move_back").removeClass("move_front");
			    };
			});
		};
		$.fn.role_btn_front = function() {
			 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			$(this).bind('click', function() {
				var obj = $(this).parent().parent().parent();
				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
					$(".div_role_front").removeClass("sub_move_front");
					$(".div_role_back").removeClass("sub_move_back");
					obj.find(".div_role_front").addClass("sub_move_front");
					obj.find(".div_role_back").addClass("sub_move_back");
				}else{
					$(".div_role_obj").removeClass("move_front");
					obj.addClass("move_front").removeClass("move_back");
				}
			});
		};
		//删除
		$.fn.role_btn_del = function(bankNo) {
			$(this).bind('click', function() {
				var $this = $(this).parent().parent().parent().parent();
				$.ajax({
					 type: "POST",
					 url: webPath+"/cusBankInfo/del?bankNo="+bankNo,
		             dataType: "json",
		             success: function(data){
		            	 console.log(data);
						if(data.sts==1){
							$this.animate({width:0,padding:0,opacity:0},"300","linear",function(){$(this).remove();});
						}else{
							alert(top.getMessage("FAILED_DELETE"));
						}
		             },
		             error:function(xmlhq,ts,err){
		            	alert(top.getMessage("FAILED_DELETE"));
		             }
				});
			});
		};
		//修改保存
		$.fn.role_btn_update = function(bankNo) {
			$(this).bind('click', function() {
				var $this = $(this).parent();
				var postData = {};
				postData.bankNo=bankNo;
				postData.accType=$this.find("#accType_back").val();
				postData.accNo=$this.find("#accNo_back").val();
				postData.accName=$this.find("#accName_back").val();
				postData.accBank=$this.find("#accBank_back").val();
				postData.accBankName=$this.find("#accBankName_back").val();
				$.ajax({
					 type: "POST",
		             url: webPath+"/cusBankInfo/update",
		             data:{ajaxData:JSON.stringify(postData)},
		             dataType: "json",
		             success: function(data){
		            	 var accTypeVal = '';
		         		if(data.accType == '1'){
		         			accTypeVal = '扣款账户';
		         		}else if(data.accType == '2'){
		         			accTypeVal = '保证金账户';
		         		}
		            	 $this.parent().find(".div_acc_type").html(accTypeVal);
		            	 $this.parent().find(".div_acc_no").html(data.accNo);
		            	 $this.parent().find(".div_acc_name").html(data.accName);
		            	 $this.parent().find(".div_acc_bank").html(data.accBank);
		            	 $this.parent().find(".div_acc_bank_name").html(data.accBankName);
		            	 var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		     				if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
		     					$(".div_role_front").removeClass("sub_move_front");
		     					$(".div_role_back").removeClass("sub_move_back");
		     				}else{
		     					$(".div_role_obj").removeClass("move_front");
		     				}
		             },
		             error:function(xmlhq,ts,err){
		            	alert("修改失败！");
		             }
				});
			});
		};
		//新增角色事件
		$.fn.role_btn_add = function() {
			$(this).bind('click', function() {
				var $this = $(this).parent();
				var cusNo = $('input[name="cusNo"]').val();
				var cusName = $('input[name="cusName"]').val();
				var postData = {};
				postData.accType=$this.find("#accType_back").val();
				postData.accNo=$this.find("#accNo_back").val();
				postData.accName=$this.find("#accName_back").val();
				postData.accBank=$this.find("#accBank_back").val();
				postData.accBankName=$this.find("#accBankName_back").val();
				postData.cusNo=cusNo;
				postData.cusName=cusName;
				if(postData.accType==""||postData.accType=="undefined"||postData.accNo==""||postData.accNo=="undefined"||postData.accName==""||postData.accName=="undefined"||postData.accBank==""||postData.accBank=="undefined"||postData.accBankName==""||postData.accBankName=="undefined"){
					alert("不能有空值！");
					return false;
				}
				$.ajax({
					type: "POST",
					url: webPath+"/cusBankInfo/insert",
					data:{ajaxData:JSON.stringify(postData)},
					dataType: "json",
					success: function(data){
						if(data.sts==1){
							add_role_obj(data);
							$(".div_role_add").animate({width:0,opacity:0},function(){
								$(".div_role_add").appendTo("#div_role_body").animate({opacity:1,width:224},"100",function(){
								});
								var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
								if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1) || userAgent.indexOf("rv:11") > -1) {
									$(".div_role_front").removeClass("sub_move_front");
									$(".div_role_back").removeClass("sub_move_back");
								}else{
									$(".div_role_obj").removeClass("move_front");
								}
								$(".div_role_add").find("input").val("");
							});
						}else{
							alert("该角色号已存在！");
						}
					},
					error:function(xmlhq,ts,err){
						alert(top.getMessage("ERROR_INSERT"));
					}
				});
			});
		};
		$.fn.role_btn_view = function(bankNo) {
			$(this).bind("click",function(){
				var $view_div = $("#div_role_view");
				var $this = $(this).parent().parent().find(".imgWrap");
				var vT,vL,vW,vH;
				vW = $this.outerWidth(true);
				vH = $this.outerHeight(true);
				vT = $this.offset().top;
				vL = $this.offset().left;
				view_div_css = {"width":vW+10,"height":vH+10,"top":vT,"left":vL};
				var view_div_open = {"width":"100%","height":"100%","top":0,"left":0};
				$view_div.css(view_div_css);
				$view_div.animate(view_div_open,"slow");
				$('#div_role_edit').delay(500).fadeIn();
				/*$.ajax({
					 type: "POST",
		             url: webPath+"/cusBankInfo/getByRoleNo?roleNo="+roleNo,
		             dataType: "json",
		             success: function(data){
		            	 $("#roleNo").val(data.roleNo);
		            	 $("#roleName").val(data.roleName);
		            	 $("#roleType").val(data.roleType);
		            	 $("#roleLev").val(data.roleLev);
		             },
		             error:function(xmlhq,ts,err){
		            	alert("打开失败！");
		             }
				});*/
				//$view_div.append($('<input type="button" value="关闭" onclick="close_view();"/>'));
			});
		};
	})(jQuery);
	function new_role_obj(){
		var $div_rotate_add = $("<div class='div_role_rotate div_role_add'></div>");
		var $div_obj_add = $("<div class='div_role_obj'></div>");
		var $div_front_add = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//背面
		var $div_back_add = $("<div class='div_role_back'></div>");
		var $i_role_btn_back_add=$("<i class = 'i i-x1 i_role_btn'></i>");//取消
		var $i_role_btn_save_add=$("<i class = 'i i-duihaocheckmark17 i_role_btn'></i>");//保存
		var $div_role_back_ctrl_add = $("<div class='div_role_back_ctrl'></div>");
		//添加按钮
		var $input_role_ctrl1_add = $("<div style='height:26px;'><label class='label_select'>账户类型</label><div class='div_select'><select name='accType_back' id='accType_back'><option value='1'>扣款账户</option></select></div></div>");/*<option value='2'>保证金账户</option>这个东西波哥让拿出来*/
		var $input_role_ctrl2_add = $("<div><label>账户账号</label><input type='text' name='accNo_back' id='accNo_back' value='' maxlength='19'/></div>");
		var $input_role_ctrl3_add = $("<div><label>结算账号户名</label><input type='text' name='accName_back' id='accName_back' value=''/></div>");
		var $input_role_ctrl4_add = $("<div><label>开户行号</label><input type='text' name='accBank_back' id='accBank_back' value=''/></div>");
		var $input_role_ctrl5_add = $("<div><label>开户行名称</label><input type='text' name='accBankName_back' id='accBankName_back' value=''/></div>");
		$div_role_back_ctrl_add.append($input_role_ctrl1_add);
		$div_role_back_ctrl_add.append($input_role_ctrl2_add);
		$div_role_back_ctrl_add.append($input_role_ctrl3_add);
		$div_role_back_ctrl_add.append($input_role_ctrl4_add);
		$div_role_back_ctrl_add.append($input_role_ctrl5_add);
		$div_back_add.append($i_role_btn_back_add);
		$div_back_add.append($i_role_btn_save_add);
		$div_back_add.append($div_role_back_ctrl_add);
		//鼠标悬停
		var $div_hover_add = $('<div class="buttons"></div>');
		var $div_hover_view_add = $('<a title="新增" alt="新增" href="#" class="i i-jia2"></a>');
		var $i_icon_add = $("<i class = 'i i-jia2 i_role_plus'></i>");
		$div_hover_add.appendTo($div_front_add);
		$i_icon_add.appendTo($div_front_add);
		$div_hover_view_add.appendTo($div_hover_add);
		$div_obj_add.append($div_front_add);
		$div_obj_add.append($div_back_add);
		$div_obj_add.appendTo($div_rotate_add);
		$div_rotate_add.appendTo($("#div_role_body"));
		$div_hover_view_add.role_btn_front();
		$i_role_btn_back_add.role_btn_back();//翻转查看
		$i_role_btn_save_add.role_btn_add();
		$div_front_add.hover(function(){
			$(this).find(".ovrly").css("opacity","1");
			$div_hover_add.find("a").css({
				"opacity":"1",
				"transform":"scale(1)"
			});
		},function(){
			$(this).find(".ovrly").css("opacity","0");
			$div_hover_add.find("a").css({
				"opacity":"0",
				"transform":"scale(0)"
			});
		});
	}
	function add_role_obj(obj){
		var $div_rotate = $("<div class='div_role_rotate'></div>");
		var $div_obj = $("<div class='div_role_obj'></div>");
		var $div_front = $('<div class="div_role_front btn26"><div class="ovrly"></div></div>');
		//鼠标悬停
		var $div_hover = $('<div class="buttons"></div>');
		var $div_hover_view = $('<a title="修改" alt="修改" href="#" class="i i-bi"></a>');
		var $div_hover_del = $('<a title="删除" alt="删除" href="#" class="i i-lajitong"></a>');
		$div_hover.appendTo($div_front);
		$div_hover_view.appendTo($div_hover);
		$div_hover_del.appendTo($div_hover);
		
		var accTypeVal = '';
		if(obj.accType == '1'){
			accTypeVal = '扣款账户';
		}else if(obj.accType == '2'){
			accTypeVal = '保证金账户';
		}
		$div_front.hover(function(){
			$(this).find(".ovrly").css("opacity","1");
			$div_hover.find("a").css({
				"opacity":"1",
				"transform":"scale(1)"
			});
		},function(){
			$(this).find(".ovrly").css("opacity","0");
			$div_hover.find("a").css({
				"opacity":"0",
				"transform":"scale(0)"
			});
		});
		
		var $div_acc_type = $("<div class='div_acc_type'>"+accTypeVal+"</div>");
		var $div_acc_bank_name = $("<div class='div_acc_bank_name'>"+obj.accBankName+"</div>");
		var $div_acc_bank = $("<div class='div_acc_bank'>"+obj.accBank+"</div>");
		var $div_acc_name = $("<div class='div_acc_name'>"+obj.accName+"</div>");
        var $div_acc_no;
		if(obj.accNo.indexOf(' ') != -1){
			$div_acc_no = $("<div class='div_acc_no'>"+obj.accNo+"</div>");
		}else{
			$div_acc_no = $("<div class='div_acc_no'>"+obj.accNo.substring(0,4)+" "+obj.accNo.substring(4,8)+" "+obj.accNo.substring(8,12)+" "+obj.accNo.substring(12,16)+"</div>");
		}
		
		var $i_role_user=$("<i class = 'i_role_user'></i>");
		
		
		 //背面
		var $div_back = $("<div class='div_role_back'></div>");
		var $i_role_btn_back=$("<i class = 'i i-x1 i_role_btn'></i>");//取消
		var $i_role_btn_save=$("<i class = 'i i-duihaocheckmark17 i_role_btn'></i>");//保存
		var $div_role_back_ctrl = $("<div class='div_role_back_ctrl'></div>");
		//添加按钮
		var c = '';
		if(obj.accType == '1'){
			accType = "<div style='height:26px;'><label class='label_select'>账户类型</label><div class='div_select'><select name='accType_back' id='accType_back'><option value='1' selected='selected'>扣款账户</option></select></div></div>"  /*<option value='2'>保证金账户</option>*/
		}else if(obj.accType == '2'){
			accType = "<div style='height:26px;'><label class='label_select'>账户类型</label><div class='div_select'><select name='accType_back' id='accType_back'><option value='1'>扣款账户</option></select></div></div>"/*<option value='2' selected='selected'>保证金账户</option>*/
		} 
		var $input_role_ctrl1 = $(accType);
		var $input_role_ctrl2 = $("<div><label>结算账号</label><input  type='text' name='accNo_back' id='accNo_back' value='"+obj.accNo+"' maxlength='19'/></div>");
		var $input_role_ctrl3 = $("<div><label>结算账号户名</label><input type='text' name='accName_back' id='accName_back' value='"+obj.accName+"'/></div>");
		var $input_role_ctrl4 = $("<div><label>开户行号</label><input type='text' name='accBank_back' id='accBank_back' value='"+obj.accBank+"'/></div>");
		var $input_role_ctrl5 = $("<div><label>开户行名称</label><input type='text' name='accBankName_back' id='accBankName_back' value='"+obj.accBankName+"'/></div>");
		
		$input_role_ctrl3.find("#bankType_back").val(obj.bankType);
		$div_role_back_ctrl.append($input_role_ctrl1);
		$div_role_back_ctrl.append($input_role_ctrl2);
		$div_role_back_ctrl.append($input_role_ctrl3);
		$div_role_back_ctrl.append($input_role_ctrl4);
		$div_role_back_ctrl.append($input_role_ctrl5);
		$div_back.append($i_role_btn_back);
		$div_back.append($i_role_btn_save);
		$div_back.append($div_role_back_ctrl);
		
		$div_front.append($div_acc_type);
		$div_front.append($div_acc_name);
		$div_front.append($div_acc_bank_name);
		$div_front.append($div_acc_bank);
		$div_front.append($i_role_user);
		$div_front.append($div_acc_no);
		$div_obj.append($div_front);
		$div_obj.append($div_back);
		$div_obj.appendTo($div_rotate);
		$div_rotate.appendTo($("#div_role_body"));
		$i_role_btn_back.role_btn_back();//翻转查看
		$div_hover_view.role_btn_front();//翻转编辑
		$div_hover_del.role_btn_del(obj.bankNo);//绑定删除事件
		$i_role_btn_save.role_btn_update(obj.bankNo);//修改
	}
	function close_view(){
		$("#div_role_edit").delay(100).fadeOut();
		var $this = $("#div_role_view");
		$this.animate(view_div_css,"slow");
		vT = view_div_css.height/2;
		vL = view_div_css.width/2;
		$this.animate({"width":0,"height":0,top:'+='+vT+'px',left:'+='+vL+'px'},"slow");
		//$this.empty();
	}
	function searchRole(cusNo){
		var val = $("#searchvalue").val();
		$.ajax({
			 type: "POST",
             url: webPath+"/cusBankInfo/search?cusNo="+cusNo,
             data:{ajaxData:val},
             dataType: "json",
             success: function(data){
            	$("#div_role_body").animate({opacity:0},"fast",function(){
            		$(this).empty();
            		var role = data.cusBankInfoList;
					$.each(role,function(i,obj){
						add_role_obj(obj);
					});
					new_role_obj();
            		});
            		$("#div_role_body").animate({opacity:1},"fast");
            	
             },
             error:function(xmlhq,ts,err){
            	alert(top.getMessage("ERROR_SELECT"));
             }
		});
	}
