/*
 
 * 新增按钮调用create_tools();方法
 * 获取当前页面帮助提示getHelp();方法
 * 
 * */
var helpData = [{
	"helpId": "0001",
	"lv": "2",
	"helpInfo": {
		"order": "",
		"remark": "融资租赁（Financial Leasing）又称设备租赁（Equipment Leasing）或现代租赁（Modern Leasing），是指实质上转移与资产所有权有关的全部或绝大部分风险和报酬的租赁。资产的所有权最终可以转移，也可以不转移。",
		"x": "200",
		"y": "100"
	}
}];
var help_lv = 2;
//*********************************************
//查看帮助信息
//*********************************************
var divArr = new Array();
var helpOptions = {skin:""};
var dmW ,dmH,help_H,help_W;
$(function(){
	help_W = document.body.clientWidth; 
	help_H = document.body.clientHeight;
	dmW = document.documentElement.clientWidth || help_W;
	dmH = document.documentElement.clientHeight || help_H;
//	alert(window.screen.height);   
//	alert(window.screen.availHeight);          //屏幕可用工作区的高
//	console.log(document.body.clientHeight);          //屏幕可用工作区的高
//	if(myBrowser()=="Chrome"){
//		h-=15-(window.screen.availHeight-document.body.clientHeight-103);
//	}
//	if(myBrowser()=="IE"){
//		h-=20-(window.screen.availHeight-document.body.clientHeight-97);
//	}
//	if(myBrowser()=="FF"){
//		h+=(window.screen.availHeight-document.body.clientHeight-113);
//		console.log(h);
//	}
//	dmH = document.documentElement.clientHeight || document.body.clientHeight;
});

function removeTips(){
	 if ($(".round")) {
		$(".round").remove();
	}
	divArr.length = 0;
}
function getHelp(skin) {
	// if(typeof(skin)!="undefined"&&skin!=""&&skin!=null){
	// 	helpOptions.skinClass = skin;
	// 	skin="_"+skin;
	// }else{
	// 	skin="";
	// }
	// helpOptions.skin = skin;
	// removeTips();
	// $.ajax({
	// 	  type: "POST",
     //         url:webPath+ "/helpUserMsg/getByListForReq",
     //         dataType: "json",
     //         success: function(data){
	// 			if (data.length > 0) {
	// 				help_W = document.body.clientWidth;
	// 				help_H = document.body.clientHeight;
	// 				dmW = document.documentElement.clientWidth || help_W;
	// 				dmH = document.documentElement.clientHeight || help_H;
	// 				$.each(data, function(i, list) {
	// 					create_help(list);
	// 				});
	// 			}
     //         },
     //         error:function(xmlhq,ts,err){
     //        	removeTips();
     //         }
	// });
    return;
}

function create_help(data) {
	var skin = helpOptions.skin;
	data.helpInfo = eval('(' + data.helpInfo + ')');
	if (checkId(data.helpId) && data.helpLv != "" && data.helpLv <= help_lv) {
		var hh = help_H / 2;
		var ww = help_W / 2;
		var scaleX = help_W/data.helpInfo.w;
		var scaleY = help_H/data.helpInfo.h;
		var roundDiv = $('<div></div>'); // 创建一个父div
		var icoDiv = $('<div></div>');
		if (typeof(data.helpInfo.order) != 'undefined' && data.helpInfo.order != "") {
			icoDiv.html(data.helpInfo.order);
			icoDiv.addClass('tip_order'+skin); // 添加css样式
		} else {
			icoDiv.addClass('i i-wenhao tip_ico'+skin); // 添加css样式
		}
		var edgDiv = $('<div></div>');
		var con = $('<span><span>');
		con.addClass('con'+skin);
		roundDiv.addClass('round'+skin); // 添加css样式
		edgDiv.addClass('edg'+skin); // 添加css样式
		if (data.helpInfo.y*scaleY > hh) {
			if (data.helpInfo.x*scaleX> ww) {
				edgDiv.addClass('edg_top_left');
			} else {
				edgDiv.addClass('edg_top_right');
			}
		} else {
			if (data.helpInfo.x*scaleX > ww) {
				edgDiv.addClass('edg_bottom_left');
			} else {
				edgDiv.addClass('edg_bottom_right');
			}
		}
		var roundCss = {
			left: data.helpInfo.x*scaleX + "px",
			top: data.helpInfo.y*scaleY + "px"
		};
		roundDiv.css(roundCss);
		roundDiv.attr('id', data.helpId);
		con.html(data.helpInfo.remark);
		con.appendTo(edgDiv);
		icoDiv.appendTo(roundDiv);
		edgDiv.appendTo(roundDiv); // 将子div添加到父div中
		getHover(roundDiv);
		roundDiv.appendTo('body'); // 将父div添加到body中
		roundDiv.data("roundCss",roundCss);
		hidebtn(roundDiv);
		divArr.push(data.helpId);
	}
}

function getHover(obj) {
	var skin  = helpOptions.skin;
	$(obj).hover(function() {
		$(this).css('z-index', '1002');
		$(this).find(".tip_ico"+skin).removeClass("i-wenhao").addClass('i-x2').bind("click",function() {
			var $this  = $(this);
			$.ajax({
					  type: "POST",
			             url:webPath+ "HelpUserMsgAction_updateUserSts.action",
			             data:{helpId:$(this).parent().attr('id')},
			             dataType: "text",
			             success: function(data){
							divArr.splice($.inArray($this.parent().attr('id'), divArr), 1);
							$this.parent().remove();
			             },error:function(xmlhq,ts,err){
			            	alert(xmlhq+"||"+ts+"||"+err);
			             }
				});
		});
	}, function() {
		$(this).css('z-index', '1001');
		$(this).unbind("click");
		$(this).find(".tip_ico"+skin).addClass("i-wenhao").removeClass("i-x2");
	});
}

function hidebtn(obj){
	var skin  = helpOptions.skin;
	if(skin!=""){
		var $this = $(obj);
		var $btn = $("<input type='button' value='知道了'/>");
		var $p = $("<p></p>");
		$btn.appendTo($p);
		$this.find(".edg"+skin).append($p);
		$btn.bind("click",function(){
			$.ajax({
						  type: "POST",
				             url:webPath+ "HelpUserMsgAction_updateUserSts.action",
				             data:{helpId:$this.attr('id')},
				             dataType: "text",
				             success: function(data){
								divArr.splice($.inArray($this.attr('id'), divArr), 1);
								$this.remove();
				             },error:function(xmlhq,ts,err){
				            	alert(xmlhq+"||"+ts+"||"+err);
				             }
					});
		});
	}
}

function checkId(id) {
	var flag = true;
	$.each(divArr, function(i, value) {
		if (value == id) {
			flag = false;
		}
	});
	return flag;
}
//*************************************
//级别选择挂载
//*************************************
var optionsVal = [{
	"key": "操作帮助提示",
	"value": "2"
}, {
	"key": "业务帮助提示",
	"value": "1"
}, {
	"key": "无需帮助提示",
	"value": "0"
}];

function openSelect(lv) {
	var skin  = helpOptions.skin;
			help_lv = lv;
			$.ajax({
				 type: "POST",
			//	 async: false,
	             url:webPath+ "HelpUserMsgAction_updateCainiao.action",
	             data:{helpLv:help_lv},
	             dataType: "text",
	             success: function(data){
	            	if ($('.tip_select_list'+skin)) {
						$('.tip_select_list'+skin).remove();
					}
					if ($(".round"+skin)) {
						$(".round"+skin).remove();
					}
					if ($(".new_edg"+skin)) {
						$(".new_edg"+skin).remove();
					}
					divArr.length = 0;
					getHelp(helpOptions.skinClass);
	             },error:function(xmlhq,ts,err){
	            	alert(xmlhq+"||"+ts+"||"+err);
	             }
			});
			
}



function closeSelect() {
	var skin  = helpOptions.skin;
	if ($('.tip_select_list'+skin)) {
		$('.tip_select_list'+skin).remove();
	}
}
//*************************************
//新增帮助信息,工具栏
//*************************************
var tipsTools = [{
		"btnName": "查看",
		"fire": "view_tips()",
		"setClass": "icon_help icon_help_view"
	}, {
		"btnName": "新增",
		"fire": "create_tips()",
		"setClass": "icon_help icon_help_disorder"
	}
	, {
		"btnName": "保存",
		"fire": "save_tips()",
		"setClass": "icon_help icon_help_save"
	}, {
		"btnName": "清除",
		"fire": "hide_tips()",
		"setClass": "icon_help icon_help_clear"
	},{
		"btnName": "关闭",
		"fire": "close_tools()",
		"setClass": "icon_help icon_help_close"
	}
];
var flag_tool = true;

function create_tools() {
	var skin  = helpOptions.skin;
	var par = $("body",parent.document);
	if(!par.find(".pt-page[name=A]").hasClass("pt-page-current")){
		alert("B面暂时无法提供帮助！");
		return false;
	}
	if ($(".round"+skin)) {
		$(".round"+skin).remove();
	}
	if (flag_tool) {
		var bgdiv = $('<div class="help_bg_div'+skin+'"></div>');
		var divTools = $('<div class="div_tools'+skin+'" style="right: 20px; bottom: 20px; "></div>');
		var divToolsList = $('<ul></ul>');
		$.each(tipsTools, function(i, obj) {
			var divSubLi = $('<li></li>');
			var divSubBtn = $('<a onselectstart="return false;" ><span>' + obj.btnName + '</span></a>');
			divSubBtn.addClass(obj.setClass);
			divSubBtn.addClass("showIcon"+skin);
			divSubBtn.find("span").html(obj.btnName).waveBox();
			divSubBtn.attr('onclick', obj.fire);
			divSubBtn.appendTo(divSubLi);
			divSubLi.appendTo(divToolsList);
		});
		divToolsList.appendTo(divTools);
		bgdiv.appendTo('body');
		bgdiv.css("height",document.body.scrollHeight);
		divTools.appendTo('body');
		
		//父页面遮罩
		var bannerDiv = $('<div class="help_banner_div'+skin+'"></div>');
		var menuDiv = $('<div class="help_menu_div'+skin+'"></div>');
		bannerDiv.appendTo(par);
		menuDiv.appendTo(par);
		flag_tool = false;
		view_tips();
	}
}

function close_tools() {
	var skin  = helpOptions.skin;
	if (confirm("请确认已保存，以免数据丢失！")) {
		if ($('.div_tools'+skin)) {
			$('.div_tools'+skin).remove();
		}
		if ($('.help_bg_div'+skin)) {
			$('.help_bg_div'+skin).remove();
		}
		if ($(".round"+skin)) {
			$(".round"+skin).remove();
		}
		if ($(".new_edg"+skin)) {
			$(".new_edg"+skin).remove();
		}
		if ($("body",parent.document).find(".help_banner_div"+skin)) {
			$("body",parent.document).find(".help_banner_div"+skin).remove();
		}
		if ($("body",parent.document).find(".help_menu_div"+skin)) {
			$("body",parent.document).find(".help_menu_div"+skin).remove();
		}
		divArr.length = 0;
		flag_tool = true;
	}
}
//切换 业务级  操作级 
function setSelect(obj) {
	var skin  = helpOptions.skin;
	if ($('.tip_select_list'+skin)) {
		$('.tip_select_list'+skin).remove();
	}
	var $this = obj;
	var tipsId = $this.val();
	var X = $this.offset().left;
	var Y = $this.offset().top;
	var selectDiv = $('<div></div>');
	selectDiv.addClass('tip_select_list'+skin);
	var sH = selectDiv.height();
	var sW = 150;
	$.each(optionsVal, function(i, list) {
		if (list.value != 0) {
			var opt = $('<a></a>');
			opt.html(list.key);
			opt.val(list.value);
			opt.waveBox();
			opt.bind('click', function() {
				$("#" + tipsId).find('#opt').val(list.value);
				$this.html(list.key);
				if ($('.tip_select_list'+skin)) {
					$('.tip_select_list'+skin).remove();
				}
			});
			opt.appendTo(selectDiv);
		}

	});
	var sX, sY;
	var selectCss = {
		left: 320 + 'px',
		top: 0
	};
	selectDiv.css(selectCss);
	selectDiv.appendTo($this.parent());
}

var tips_x = 200;
var tips_y = 80;
var tips_count = 0;

function view_tips() {
	var skin  = helpOptions.skin;
	if ($('.round'+skin)) {
		$('.round'+skin).remove();
	}
	divArr.length = 0;
	$.ajax({
		  type: "POST",
             url:webPath+ "HelpMsgAction_getByListForReq.action",
             dataType: "json",
             success: function(data){
				if (data.length > 0) {
					$.each(data, function(i, list) {
						create_view_tips(list);
					});
				}
             },
             error:function(xmlhq,ts,err){
            	removeTips();
             }
	});
}

function create_view_tips(data) {
	var skin  = helpOptions.skin;
	data.helpInfo = eval('(' + data.helpInfo + ')');
	if (checkId(data.helpId)) {
		var scaleX = help_W/data.helpInfo.w;
		var scaleY = help_H/data.helpInfo.h;
		var roundDiv = $('<div></div>');
		var icoDiv = $('<div></div>');
		var valDiv = $('<input id="msg" type="hidden">');
		var optDiv = $('<input id="opt" type="hidden">');
		valDiv.val(data.helpInfo.remark);
		optDiv.val(data.helpLv);
		if (typeof(data.helpInfo.order) != 'undefined' && data.helpInfo.order != "") {
			icoDiv.html(data.helpInfo.order);
			icoDiv.addClass('new_tip_order'+skin); // 添加css样式
			icoDiv.attr('ondblclick', 'tipsInfo(this,1)');
		} else {
			icoDiv.addClass('i i-wenhao new_tip_ico'+skin); // 添加css样式
			icoDiv.attr('ondblclick', 'tipsInfo(this)');
		}
		roundDiv.attr('id', data.helpId);
		roundDiv.addClass('round'+skin); // 添加css样式
		optDiv.appendTo(roundDiv);
		valDiv.appendTo(roundDiv);
		icoDiv.appendTo(roundDiv);
		var roundCss = {
			left: data.helpInfo.x*scaleX + "px",
			top: data.helpInfo.y*scaleY + "px"
		};
		roundDiv.attr('id', data.helpId);
		roundDiv.css(roundCss);
		icoDiv.dragBox({
			out: true
		});
		roundDiv.appendTo('body');
		roundDiv.data("roundCss",roundCss);
		divArr.push(data.helpId);
	}
}


function create_tips(order) {
	var skin  = helpOptions.skin;
	var roundDiv = $('<div></div>');
	var icoDiv = $('<div></div>');
	var valDiv = $('<input id="msg" type="hidden">');
	var optDiv = $('<input id="opt" type="hidden" value="2">');
	roundDiv.attr('id', 'tip' + tips_count);
	roundDiv.addClass('round'+skin); // 添加css样式
	if (typeof(order) != 'undefined' && order != "") {
		icoDiv.addClass('new_tip_order'+skin); // 添加css样式
		icoDiv.attr('ondblclick', 'tipsInfo(this,1)');
	} else {
		icoDiv.addClass('i i-wenhao new_tip_ico'+skin); // 添加css样式
		icoDiv.attr('ondblclick', 'tipsInfo(this)');
	}
	optDiv.appendTo(roundDiv);
	valDiv.appendTo(roundDiv);
	icoDiv.appendTo(roundDiv);
	var roundCss = {
		left: tips_x + "px",
		top: tips_y + "px"
	};
	var hh = $(document.body).height();
	var ww = $(document.body).width();
	if (tips_x < ww) {
		tips_x += 10;
	} else {
		tips_x -= 15;
	}
	if (tips_y < hh) {
		tips_y += 5;
	} else {
		tips_y -= 10;
	}
	roundDiv.css(roundCss);
	icoDiv.dragBox({
		out: true
	});
	roundDiv.appendTo('body');
	tips_count++;
}

function del_tips(btn, obj) {
	var skin  = helpOptions.skin;
	$.ajax({
		  type: "POST",
             url:webPath+ "HelpMsgAction_delHelpTips.action",
             data: {helpId:obj},
             dataType: "json",
             success: function(data){
            	 divArr.splice($.inArray($("#" + obj).attr('id'), divArr), 1);
				 $("#" + obj).remove();
				 $(btn).parents(".new_edg"+skin).remove();
             }
	});
}

function close_tips(btn, obj) {
	var skin  = helpOptions.skin;
	$(btn).parents(".new_edg"+skin).remove();
	$(".new_tip_ico"+skin).removeClass("new_tip_ico_db"+skin);
}
function tipsInfo(obj, order) {
	var skin  = helpOptions.skin;
	$('.new_edg'+skin).remove();
	$(".new_tip_ico"+skin).removeClass("new_tip_ico_db"+skin);
	$(obj).addClass("new_tip_ico_db"+skin);
	var tipsId = $(obj).parent().attr('id');
	var $tipsId = $("#" + tipsId);
	var divMove = $('<div class="div_tools_move'+skin+' div_tools_title'+skin+'"><i class="i i-tanhao2"></i><i class="title'+skin+'">单击选择级别</i></div>');
	var tipSelect = $('<div class="tip_select'+skin+'"><div class="rolling'+skin+'"></div></div>'); 
	var helpLevel = $('<div class="helpLevel'+skin+'"><i class="i i-jiantou9"></i><i class="val'+skin+'">操作级帮助</i></div>');
	var busLevel  = $('<div class="busLevel'+skin+'"><i class="val'+skin+'">业务级帮助</i><i class="i i-jiantoua"></i></div>');
	helpLevel.appendTo(tipSelect.find(".rolling"+skin));
	busLevel.appendTo(tipSelect.find(".rolling"+skin));
	if($tipsId.find('#opt').val()==2){
		divMove.find("i.title"+skin).html("操作级帮助");
		tipSelect.find(".rolling"+skin).animate({left:"0px"});
	}else{
		divMove.find("i.title"+skin).html("业务级帮助");
		tipSelect.find(".rolling"+skin).animate({left:"-100px"});
	}
	helpLevel.bind('click', function() {
		var obj = $(this);
		$(this).parent().animate({left:"-100px"},function(){
			obj.parents(".div_tools_title"+skin).find(".title"+skin).text("业务级帮助");
			$tipsId.find('#opt').val("1");
		//	console.log($tipsId.find('#opt').val());
		});
	});
	busLevel.bind('click', function() {
		var obj = $(this);
		$(this).parent().animate({left:"0px"},function(){
			obj.parents(".div_tools_title"+skin).find(".title"+skin).text("操作级帮助");
			$tipsId.find('#opt').val("2");
		//	console.log($tipsId.find('#opt').val());
		});
	});
	divMove.val(tipsId);
	/*divMove.bind('click', function() {
		setSelect($(this));
	});*/
	var roundDiv = $('<div></div>');
	roundDiv.addClass('new_edg'+skin);
	var textDiv = $('<div></div>');
	//*******************
	var orderText = $('<input type="text"/>');
	orderText.val($("#" + tipsId).find('.new_tip_order'+skin).html());
	orderText.attr('onblur', 'set_tips_order(this,"' + tipsId + '")');
	orderText.appendTo(textDiv);
	orderText.attr('placeholder', '填写序号');
	if (typeof(order) == 'undefined' || order == "" || order == "undefined") {
		orderText.css("display", "none");
	}
	//**************************
	var text = $('<textarea>' + $("#" + tipsId).find('#msg').val() + '</textarea>');
	var ibtnDiv = $('<div class="ibtn'+skin+'"></div>');
	var btnDel = $('<span onclick="del_tips(this,\'' + tipsId + '\');"><i class="i i-lajitong1"></i>删除</span>');
	var btnClo = $('<span onclick="close_tips(this);" ><i class="i i-x2"></i>关闭</span>');
	text.attr('placeholder', '输入提示内容...');
	text.attr('onblur', 'set_tips_val(this,"' + tipsId + '")');
	text.appendTo(textDiv);
	ibtnDiv.appendTo(textDiv);
	btnDel.appendTo(ibtnDiv);
	btnClo.appendTo(ibtnDiv);
	divMove.appendTo(roundDiv);
	tipSelect.appendTo(divMove);
	textDiv.appendTo(roundDiv);
	var y = $("#" + tipsId).offset().top;
	var x = $("#" + tipsId).offset().left;
	var hh = help_H / 2;
	var ww = help_W / 2;
	if (x > ww) {
		x -= 378;
	} else {
		x += 33;
	}
	if (y > hh) {
		y -= 238;
	} else {
		y += 33;
	}
	var roundCss = {
		left: x + "px",
		top: y + "px"
	};
	//使用有序提示时需要打开
	/*roundDiv.dblclick(function() {
		change_sts(this, tipsId);
	});*/
	divMove.dragBox({
		out: true
	});
	roundDiv.css(roundCss);
	roundDiv.appendTo('body');
};

function set_tips_val(txe, tipsId) {
	$("#" + tipsId).find('#msg').val(txe.value);
}

function set_tips_order(text, tipsId) {
	var skin  = helpOptions.skin;
	if ($("#" + tipsId).find('.new_tip_ico'+skin)) {
		var $ico = $("#" + tipsId).find('.new_tip_ico'+skin);
		$ico.removeClass();
		$ico.addClass("new_tip_order"+skin);
	}
	$("#" + tipsId).find('.new_tip_order'+skin).html(text.value);
}

function change_sts(obj, tipsId) {
	var $obj = $(obj);
	$obj.first("div").find('input[type=text]').toggle();
	if ($("#" + tipsId).find('.new_tip_order'+skin)) {
		var $ico = $("#" + tipsId).find('.new_tip_order'+skin);
		$ico.removeClass();
		$ico.addClass("i i-wenhao new_tip_ico"+skin);
		$ico.html("");
		$obj.first("div").find('input[type=text]').val("");
	}
}

function save_tips() {
	var skin  = helpOptions.skin;
	var tipsArr = $(".round"+skin);
	helpData.length = 0;
	$.each(tipsArr, function(i, tip) {
		var json = {
			"helpId": $(tip).attr('id'),
//			"remark": $(tip).find('#msg').val(),
//			"x": $(tip).offset().left,
//			"y": $(tip).offset().top,
//			"order": $(tip).find('.new_tip_order').html(),
			"lv": $(tip).find('#opt').val(),
			"helpInfo":{
				"remark": $(tip).find('#msg').val(),
				"x": $(tip).offset().left,
				"y": $(tip).offset().top,
				"order": $(tip).find('.new_tip_order'+skin).html(),
				"h":help_H,
				"w":help_W
			}
		};
		helpData.push(json);
	});
	$.ajax({
		  type: "POST",
             url:webPath+ "HelpMsgActionInsertForReq.action",
             data: {helpInfo:JSON.stringify(helpData)},
             dataType: "json",
             success: function(data){
				alert(top.getMessage("SUCCEED_SAVE"));
            	 view_tips();
             }
	});
}

function hide_tips() {
	var skin  = helpOptions.skin;
	if (confirm("确认清除吗？")) {
		$.ajax({
		  type: "post",
             url:webPath+ "HelpMsgAction_delAllHelpTips.action",
             dataType: "json",
             async:true,
             error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
             success: function(data){
            	if ($(".round"+skin)) {
					$(".round"+skin).remove();
				}
				if ($(".new_edg"+skin)) {
					$(".new_edg"+skin).remove();
				}
				divArr.length = 0;
             }
		});
	}
}

//拖拽
(function($) {
	$.fn.dragBox = function(options) {
		var opts = $.extend({}, $.fn.dragBox.defaults, options);
		var obj = this[0]
		var $this = $(this);
		/*var dmW = document.documentElement.clientWidth || document.body.clientWidth;
		var dmH = document.documentElement.clientHeight || document.body.clientHeight;*/
		var l = 0;
		var r = dmW - obj.offsetWidth;
		var t = 0;
		var b = dmH - obj.offsetHeight;
		var n = 10;
		obj.onmousedown = function(ev) {
			var oEvent = ev || event;
			var sentX = oEvent.clientX - obj.offsetLeft;
			var sentY = oEvent.clientY - obj.offsetTop;
			var objP = obj.parentNode;
			var sentPX = oEvent.clientX - objP.offsetLeft;
			var sentPY = oEvent.clientY - objP.offsetTop;
			document.onmousemove = function(ev) {
				/*if ($('.new_edg')) {
					$('.new_edg').remove();
				}*/
				var oEvent = ev || event;
				var slideLeft = oEvent.clientX - sentX;
				var slideTop = oEvent.clientY - sentY;
				if (opts.out) {
					slideLeft = oEvent.clientX - sentPX;
					slideTop = oEvent.clientY - sentPY;
					if (slideLeft <= l) {
						slideLeft = l;
					}
					if (slideLeft >= r) {
						slideLeft = r;
					}
					if (slideTop <= t) {
						slideTop = t;
					}
					/*if (slideTop >= b) {
						slideTop = b;
					}*/

//					obj.style.left = slideLeft + 'px';
//					obj.style.top = slideTop + 'px';
					objP.style.left = slideLeft + 'px';
					objP.style.top = slideTop + 'px';
					objP.setCapture && objP.setCapture();
				} else {
					if (slideLeft <= l) {
						slideLeft = l;
					}
					if (slideLeft >= r) {
						slideLeft = r;
					}
					if (slideTop <= t) {
						slideTop = t;
					}
					/*if (slideTop >= b) {
						slideTop = b;
					}*/

					obj.style.left = slideLeft + 'px';
					obj.style.top = slideTop + 'px';
					obj.setCapture && obj.setCapture();
				}
			};

			document.onmouseup = function(ev) {
				if( myBrowser()!="Chrome"){
					objP.releaseCapture();
					obj.releaseCapture();
					var oEvent = ev || event;
					oEvent.cancelBubble = true;
				}
				document.onmousemove = null;
				document.onmouseup = null;
			};
			return false;
		};
	};
	$.fn.dragBox.defaults = {
		out: false //默认父元素不跟随
	};
})(jQuery);
(function($) {
	$.fn.waveBox = function() {
		var $this = $(this);
		$this.bind('click', function(e) {
			event = e || window.event;
			if("IE"!=myBrowser()){
				addRippleEffect(event);
			}
		});
	};

})(jQuery);

var addRippleEffect = function(e) {
	var target = e.target;
	if (target.tagName.toLowerCase() !== 'button' && target.tagName.toLowerCase() !== 'a' && target.tagName.toLowerCase() !== 'input'&& target.tagName.toLowerCase() !== 'span') return false;
	var rect = target.getBoundingClientRect();
	var ripple = target.querySelector('.ripple');
	if (ripple) {
		ripple.parentNode.removeChild(ripple);
	}
	ripple = document.createElement('span');
	ripple.className = 'ripple';
	ripple.style.height = ripple.style.width = Math.max(rect.width, rect.height) + 'px';
	target.appendChild(ripple);
	ripple.classList.remove('show');
	var top = e.pageY - rect.top - ripple.offsetHeight / 2 - document.body.scrollTop;
	var left = e.pageX - rect.left - ripple.offsetWidth / 2 - document.body.scrollLeft;
	ripple.style.top = top + 'px';
	ripple.style.left = left + 'px';
	ripple.classList.add('show');
	return false;
};

var addSelectChose = function(level,iObj){
	if($(".container-fluid").length==0){
		var selectCont = $('<div class="container-fluid help-select-list"></div>');
		var btnGroup   = $('<div class="btn-group open"> </div>');
		var menuUl = $('<ul class="dropdown-menu"></ul>');
		var setLevelLi = $('<li><a href="#" style="padding-left: 4px;"><i class="i i-chevron-left" style="padding-right:6px"></i>设置帮助级别</a></li>');			
		var resetLi,setHelpLi,setHelpCont;
//		if(myBrowser()!="FF"){
//			resetLi	= $('<li><a href="#" onclick="frames[0].openSelect(2)">恢复帮助</a></li>');			
//			setHelpLi = $('<li><a href="#" onclick="frames[0].create_tools();">帮助设置</a></li>');				
//			setHelpCont = $('<ul class="dropdown-menu" style="left:-115px; top: 7px;">'+
//								'<li><a href="#" onclick="frames[0].openSelect(2)">业务级帮助</a></li>'+
//								'<li><a href="#" onclick="frames[0].openSelect(1)">操作级帮助</a></li>'+
//								'<li><a href="#" onclick="frames[0].openSelect(0)">不显示帮助</a></li>'+
//								'</ul></li>');
//		}else{
//			resetLi	= $('<li><a href="#" onclick="iframepage.window.openSelect(2)">恢复帮助</a></li>');			
//			setHelpLi = $('<li><a href="#" onclick="iframepage.window.create_tools();">帮助设置</a></li>');				
//			setHelpCont = $('<ul class="dropdown-menu" style="left:-115px; top: 7px;">'+
//								'<li><a href="#" onclick="iframepage.window.openSelect(2)">业务级帮助</a></li>'+
//								'<li><a href="#" onclick="iframepage.window.openSelect(1)">操作级帮助</a></li>'+
//								'<li><a href="#" onclick="iframepage.window.openSelect(0)">不显示帮助</a></li>'+
//								'</ul></li>');
//		}
		resetLi	= $('<li><a href="#" onclick="iframeOpenSelect(2)">恢复帮助</a></li>');			
		setHelpLi = $('<li><a href="#" onclick="iframeCreate_tools();">帮助设置</a></li>');				
		setHelpCont = $('<ul class="dropdown-menu" style="left:-115px; top: 7px;">'+
							'<li><a href="#" onclick="iframeOpenSelect(2)">业务级帮助</a></li>'+
							'<li><a href="#" onclick="iframeOpenSelect(1)">操作级帮助</a></li>'+
							'<li><a href="#" onclick="iframeOpenSelect(0)">不显示帮助</a></li>'+
							'</ul></li>');
		var $obj = $("body");
		var menuCss={
			left:$(iObj).offset().left-120,
			top:$(iObj).offset().top
		};
		selectCont.css(menuCss);
		selectCont.insertAfter($obj);
		btnGroup.appendTo(selectCont);
		menuUl.appendTo(btnGroup);
		setLevelLi.appendTo(menuUl);
		resetLi.appendTo(menuUl);
		if(level=="set"){
			setHelpLi.appendTo(menuUl);
			setHelpCont.appendTo(setLevelLi);
		}
		selectCont.hover(function(){
		},function(){
			$(this).remove();
		});
		setLevelLi.hover(function(){
			setHelpCont.show();
		},function(){
			setHelpCont.hide();
		});
		setHelpCont.hover(function(){
		},function(){
			$(this).hide();
		});
	}
}

function iframeCreate_tools(){
	document.getElementById("a_iframe").contentWindow.create_tools();
}
function iframeOpenSelect(lv){
	document.getElementById("a_iframe").contentWindow.openSelect(lv);
}
//function iframeCreate_tools(){
//	document.getElementById("a_iframe").contentWindow.create_tools();
//}

function myBrowser(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera";
    }; //判断是否Opera浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("Chrome") > -1){
	  return "Chrome";
	 }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //判断是否Safari浏览器
    if ((userAgent.indexOf("rv:11") > -1 || userAgent.indexOf("compatible") > -1 || userAgent.indexOf("MSIE") > -1) && !isOpera) {
        return "IE";
    }; //判断是否IE浏览器
}
