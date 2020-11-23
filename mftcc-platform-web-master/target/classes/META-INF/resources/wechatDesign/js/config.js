"use strict";
var tagPropertys;
var tagDatatypes;
var formData = {
	timestamp: "",
	formId: "",
	layout: "horizontal",
	desc:"",
	webFormId:""
};
$(function() { //页面加载完成后绑定页面按钮的点击事件
	initDatabase();
});

function getCurrentDb() {
	//打开数据库，或者直接连接数据库参数：数据库名称，版本，概述，大小
	//如果数据库不存在那么创建之
	var db = openDatabase("FormDb", "1.0", "it's to save form data!", 1024 * 1024);
	return db;
}

function initDatabase() {
	var db = getCurrentDb(); //初始化数据库
	if(!db) {
		console.log("您的浏览器不支持HTML5本地数据库");
		return;
	}
	db.transaction(function(trans) { //启动一个事务，并设置回调函数
			//执行创建表的Sql脚本
			trans.executeSql("drop table if exists FormData", [],
				function(trans, result) {},
				function(trans, message) {
					console.log(message);
				});
		},
		function(trans, result) {},
		function(trans, message) {});
	db.transaction(function(trans) { //启动一个事务，并设置回调函数
			//执行创建表的Sql脚本
			trans.executeSql("create table if not exists FormData(formId text null,timestamp text null,content text null)", [],
				function(trans, result) {},
				function(trans, message) {
					console.log(message);
				});
		},
		function(trans, result) {},
		function(trans, message) {});
}

function saveVersion(data) {
	var db = getCurrentDb();
	var formId = data.formId;
	var timestamp = new Date().getTime();
	var content = JSON.stringify(data);
	formData.formId = formId;
	formData.timestamp = timestamp;
	formData.layout = data.layout;
	formData.desc = data.desc;
	formData.webFormId = data.webFormId;
	//执行sql脚本，插入数据
	db.transaction(function(trans) {
		trans.executeSql("insert into FormData(formId,timestamp,content) values(?,?,?) ", [formId, timestamp, content],
			function(ts, data) {},
			function(ts, message) {
				console.log(message);
			});
	});
}

//显示所有数据库中的数据到页面上去
function showData() {
	var db = getCurrentDb();
	db.transaction(function(trans) {
		trans.executeSql("select * from FormData ", [],
			function(ts, data) {
				if(data) {
					console.log(data);
				}
			},
			function(ts, message) {
				console.log(message);
				var tst = message;
			});
	});
}
//撤销
function replyVersion() {
	var formId = formData.formId;
	var timestamp;
	if(formData.timestamp == "") {
		timestamp = new Date().getTime();
	} else {
		timestamp = formData.timestamp;
	}
	var db = getCurrentDb();
	db.transaction(function(trans) {
		var query = "select * from FormData where formId = '" + formId + "' and timestamp < " + timestamp + "  order by timestamp desc LIMIT 2";
		trans.executeSql(query, [],
			function(ts, data) {
				if(data && data.rows.length > 0) {
					var nodes = eval("(" + data.rows.item(0).content + ")");
					formData.timestamp = data.rows.item(0).timestamp;
					createHtmlFromXML(nodes);
					saveData(true);
				} else {
					$.toast("没有更多操作", "cancel");
				}
			},
			function(ts, message) {
				console.log(message);
			});
	});
}
//重做
function cancleVersion() {
	var formId = formData.formId;
	var timestamp = formData.timestamp;
	var db = getCurrentDb();
	db.transaction(function(trans) {
		trans.executeSql("select * from FormData where formId = '" + formId + "' and timestamp > " + timestamp + "  order by timestamp LIMIT 1", [],
			function(ts, data) {
				if(data && data.rows.length > 0) {
					var nodes = eval("(" + data.rows.item(0).content + ")");
					formData.timestamp = data.rows.item(0).timestamp;
					createHtmlFromXML(nodes);
					saveData(true);
				} else {
					$.toast("没有更多操作", "cancel");
				}
			},
			function(ts, message) {
				$.toast("没有更多操作", "cancel");
				console.log(message);
				var tst = message;
			});
	});
}

// 保存
var saveData = function(flag) {
	var formDataParms = $.extend({},formData);
	formDataParms.nodes = html2json("#dropArea");
	if(!flag) {
		saveVersion(formDataParms);
	}
	$.ajax({
		type: "post",
		url: "../SaveXml",
		async: true,
		cache: false,
		dataType: "json",
		data: {
			ajaxData: JSON.stringify(formDataParms)
		},
		success: function(data) {
			//console.log(data);
		},
		error: function(e) {
			console.log(e);
		}

	});
};

// 读取Form
var getData = function(formId) {
	formData.formId = formId;
	deraction.pkno = 0;
	$.ajax({
		type: "post",
		url: "../ReadXml",
		async: true,
		cache: false,
		dataType: "json",
		data: {
			formId: formId
		},
		success: function(data) {
			createHtmlFromXML(data);
			saveVersion(data);
			formData = $.extend({},data);
		},
		error: function(e) {
			console.log(e);
		}

	});
};
// 显示隐藏按钮
var showBtn = function(e) {
	e.preventDefault();
	var $this = $(this);
	$this.addClass("outside_border").siblings('.moveNode').removeClass("outside_border");
	var $i = $('<i class="weui_icon_cancel outside-btn"></i>');
	$this.prepend($i);
//	if($this.attr("data-property")=="panel"){
//		$i.css({
//			"position": "relative",
//			"float": "right",
//			"z-index": "999"
//		});
//	}else{
		$i.css({
			"position": "absolute",
			"right": "12px",
			"z-index": "999"
		});
//	}
	$i.unbind("click").bind("click",
		function() {
			$.confirm("确认删除[" + $this.attr("data-name") + "]元素吗？", function() {
				//点击确认后的回调函数
				$this.remove();
				saveData();
				$(this).hide();
				emptyConfigTree();
				if($this.attr("data-lv") == "1") {
					emptyConfigTree2();
				}
			});
			return false;
		});

};
// 隐藏删除按钮
var hideBtn = function(e) {
	e.preventDefault();
	var $this = $(this);
	$this.removeClass("outside_border");
	$this.children(".outside-btn").remove();
};
//垂直和水平布局
function changeLayout(layout) {
	if(layout == "horizontal") {
		$(".moveNode").each(function(i, node) {
			$(node).addClass("horizontal").removeClass("vertical");
		});
	} else {
		//vertical
		$(".moveNode").each(function(i, node) {
			$(node).removeClass("horizontal").addClass("vertical");
		});
	}
	formData.layout =layout;
	saveData();
}
function saveFormData(data){
	formData = $.extend({},data);
	saveData();
}
//获取页面隐藏域
function getHiddenNodes(){
	$("#consoleTree", document).empty();
	$("input.moveNode[type=hidden]").each(function(i,node){
		var $li = $('<li pkno="' + $(node).attr("data-pkno") + '"><span>隐藏域(' + $(node).attr("data-pkno") + ')</span><i class="fa fa-trash-o"></i></li>');
		if( $(node).attr("data-fieldname") != undefined) {
			$li.find("span").html("隐藏域(" + $(node).attr("data-fieldname") + ")");
		}
		$("#consoleTree", document).append($li);
		$li.bind("click", function(e) {
			nodeOnClick(e, $("#" + $(this).attr("pkno")).get(0));
		});
		$li.find(".fa-trash-o").bind("click", function() {
			$.confirm("确认删除该元素吗？", function() {
				//点击确认后的回调函数
				$("#" + $li.attr("pkno")).remove();
				saveData();
				$li.remove();
			});
		});
	});
}
//清空配置
function emptyConfigTree() {
	var configTree = $("#configTree", document);
	configTree.empty();
}

function emptyConfigTree2() {
	var configTree2 = $("#configTree2", document);
	configTree2.empty();
}

function emptyConsoleTree() {
	var consoleTree = $("#consoleTree", document);
	consoleTree.empty();
}

//---------------------分割线---------------------
function getAttrData(elem){
	var obj = elem.get(0).attributes;
	var json = {};
	$.each(obj, function(i,node) {
		if((node.nodeName).indexOf("data-")>-1){
			json[(node.nodeName).substring(5)]=node.nodeValue;
		}
	});
	return json;
}
//点击创建属性列表
function nodeOnClick(e, obj) {
	activeBtn("prop");
	var $this = $(obj);
	var items = getAttrData($this);
	
	if(!tagPropertys) {
		$.ajax({
			type: "post",
			async: false,
			cache: false,
			url: "file/menu.json",
			dataType: "json",
			success: function(jsonData) {
				tagPropertys = jsonData.property;
			},
			error: function() {
				alert("error");
			}
		});
	}
	var props = tagPropertys[$this.attr("data-property")];
	showProp(function(){
		emptyConfigTree();
		var configTree = $("#configTree", document);
		var configTree2 = $("#configTree2", document);
		configTree2.hide();
		$.each(props,
				function(i, obj) {
					var $li;
                    var $text;
					switch(obj.type) {
						case "value":
							$li = $('<div class="weui_cell"><div class="weui_cell_hd"><label class="weui_label"></label></div><div class="weui_cell_bd weui_cell_primary"></div></div>');
							$li.find(".weui_label").html(obj.disName);
							$text = $('<input name="' + obj.name + '" class="weui_input"  type="text" placeholder="请输入' + obj.disName + '">');
							$text.val(items[obj.name]);
							$li.find(".weui_cell_primary").append($text);
							break;
						case "selectValue":
							$li = $('<div class="weui_cell weui_cell_select weui_has_input"><div class="weui_cell_bd weui_cell_primary"></div></div>');
							$text = $('<select name="select_' + obj.name + '" onchange="saveData();" class="weui_select"></select>');
							$text.append('<option selected="selected" value="0">选择</option>');
							$.each(obj.opt,
								function(i, o) {
									$('<option value="' + o.val + '">' + o.key + '</option>').appendTo($text);
								});
							if(!!items[obj.name]) {
								$text.val(items[obj.name]);
							}
							var $text2 = $('<input name="text_' + obj.name + '"  class="weui_input" type="text" placeholder="请输入' + obj.disName + '">');
							$text2.val(items[obj.name]);
							$li.find(".weui_cell_primary").append($text).append($text2);
							break;
						case "select":
							$li = $('<div class="weui_cell weui_cell_select weui_cell_select_label"><div class="weui_cell_hd"><label class="weui_label"></label></div><div class="weui_cell_bd weui_cell_primary"></div></div>');
							$li.find(".weui_label").html(obj.disName);
							$text = $('<select name="' + obj.name + '"  class="weui_select"></select>');
							$.each(obj.opt,
								function(i, o) {
									$('<option value="' + o.val + '">' + o.key + '</option>').appendTo($text);
								});
							if(items[obj.name]) {
								$text.val(items[obj.name]);
							} else {
								$text.get(0).selectedIndex = 0;
							}
							$li.find(".weui_cell_primary").append($text);
							break;
						case "switch":
							$li = $('<div class="weui_cell weui_cell_switch"><div class="weui_cell_hd weui_cell_primary">接受通知</div><div class="weui_cell_ft"></div></div>');
							$li.find(".weui_cell_hd").html(obj.disName);
							$text = $('<input name="' + obj.name + '" value="1"   class="weui_switch"type="checkbox">');
							if(items[obj.name] && items[obj.name] == "1") {
								$text.prop("checked", true);
							}
							$li.find(".weui_cell_ft").append($text);
							break;
						case "hidden":
							$li = $('<input name="' + obj.name + '" type="hidden">');
							$li.val(items[obj.name]);
							break;
						default:
							break;
					}
					configTree.append($li);
				});
			switch($this.attr("data-property")) {
				case "btn":
					btnNode(configTree, $this);
					break;
				case "submit":
					submitNode(configTree, $this);
				case "label":
					labelNode(configTree, $this);
					break;
				case "text":
					textNode(configTree, $this);
					break;
				case "password":
					passwordNode(configTree, $this);
					break;
				case "textarea":
					textareaNode(configTree, $this);
					break;
				case "select":
					selectNode(configTree, $this);
					break;
				case "checkbox":
					checkboxNode(configTree, $this);
					break;
				case "radio":
					radioNode(configTree, $this);
					break;
				case "picker":
					pickerNode(configTree, $this);
					break;
				case "datepicker":
					datepickerNode(configTree, $this);
					break;
				case "timepicker":
					timepickerNode(configTree, $this);
				case "hidden":
					hiddenNode(configTree, $this);
					break;
				case "jgg":
					jggNode(configTree, $this);
					break;
				case "tabbar":
					tabbarNode(configTree, $this);
					break;
				case "dhl":
					dhlNode(configTree, $this);
					break;
				case "panel":
					panelNode(configTree, $this);
					break;
				default:
					break;
			}
	});
	e.preventDefault();
	e.stopPropagation();
};

var createHtmlFromXML = function(data) {
	emptyConfigTree();
	emptyConfigTree2();
	emptyConsoleTree();
	//var formData = {};
	formData.formId = data.formId;
	formData.layout = data.layout;
	if(data.layout == "horizontal") {
		$("input[name=layout]", document).prop("checked", false);
	} else {
		$("input[name=layout]", document).prop("checked", true);
	}
	var nodes = data.nodes;
	$("#dropArea").empty();
	json2html(nodes,"#dropArea");
	$("#dropArea .moveNode").hover(showBtn, hideBtn);
	$("#dropArea .moveNode").bind("click", function(e) {
		nodeOnClick(e, this);
	});
	initContainer();
};


var setPkNoFromXML = function(node, $div) {
	$.each(node,
		function(k, v) {
			$div.attr("data-" + k, v);
		});
	var pkno = node.pkno.substring(deraction.prefix.length+1,node.pkno.length);
	if(Number(pkno)>=Number(deraction.pkno)){
		deraction.pkno=Number(pkno);
	}
};

var createDatatypeHtml = function(configTree, node) {
		var $li = $('<div class="weui_cell weui_cell_select weui_cell_select_label"><div class="weui_cell_hd"><label class="weui_label"></label></div><div class="weui_cell_bd weui_cell_primary"></div></div>');
		$li.find(".weui_label").html("数据类型");
		var items = node.data();
		var $text = $('<select name="datatype"  class="weui_select"></select>');
		$.each(tagDatatypes,
			function(i, o) {
				$('<option value="' + o.optcode + '">' + o.optname + '</option>').appendTo($text);
			});
		if(items["datatype"]) {
			$text.val(items["datatype"]);
		} else {
			$text.get(0).selectedIndex = 0;
		}
		$li.find(".weui_cell_primary").append($text);
		configTree.append($li);
		$text.change(function() {
			node.attr("data-datatype", $(this).val());
			saveData();
		});

		var $div = $('<div class="weui_cell"><div class="weui_cell_hd"><label class="weui_label"></label></div><div class="weui_cell_bd weui_cell_primary"></div></div>');
		$div.find(".weui_label").html("参数");
		$text = $('<input name="para" class="weui_input"  type="text" placeholder="请输入参数">');
		$text.val(items["para"]);
		$div.find(".weui_cell_primary").append($text);
		configTree.append($div);
		$text.blur(function() {
			node.attr("data-para", $(this).val());
			saveData();
		});
	};
	//--------------------分割线------------------------
	//按钮属性start
var btnNode = function(configTree, node) {
	configTree.find("input[name=name]").blur(function() {
		node.html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("select[name=css]").change(function() {
		node.attr("data-css", $(this).val());
		node.attr("class", "weui_btn moveNode " + $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=click]").blur(function() {
		node.attr("data-click", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=disabled]").blur(function() {
		node.attr("data-disabled", $(this).val());
		saveData();
	});
};
var panelNode = function(configTree, node){
	configTree.find("select[name=type]").change(function() {
		node.attr("data-type", $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("select[name=grid]").change(function() {
		node.attr("data-grid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=objectname]").blur(function() {
		node.attr("data-objectname", $(this).val());
		saveData();
	});
	configTree.find("input[name=title]").blur(function() {
		node.attr("data-title", $(this).val());
		node.find(".weui_panel_hd").attr("data-title", $(this).val());
		node.find(".weui_panel_hd").html($(this).val());
		saveData();
	});
	configTree.find("select[name=titleshow]").change(function() {
		node.attr("data-titleshow", $(this).val());
		node.find(".weui_panel_hd").attr("data-titleshow", $(this).val());
		saveData();
	});
	configTree.find("select[name=grid]").change(function() {
		var count = $(this).val();
		node.attr("data-grid", count);
		node.find(".column").remove();
		for(var i = 0;i<count;i++){
			node.append('<div class="weui_panel_bd column panel-col-'+count+' " style="min-height:20px;padding:10px 0px"></div>');
		}
		node.find(".column").sortable({
			opacity : .35,
			connectWith : ".column",
			start : function(e, t) {
				var width = $("#dropArea").width();
				t.helper.width(width - 28);
				t.helper.css("z-index", "1000");
				t.helper.removeClass("moveNode");
				t.placeholder.removeClass("weui_panel");
				if (!startdrag) stopsave++;
				startdrag = 1;
			},
			stop : function(e, t) {
				t.item.width("auto");
				t.item.css("z-index", "0");
				t.item.css("height", "auto");
				t.item.css("position", "relative");
				t.item.addClass("moveNode");
				if (stopsave > 0) stopsave--;
				startdrag = 0;
				saveData();
			}
		});
		saveData();
	});
	
	
	
	
}
var submitNode = function(configTree, node) {
	configTree.find("input[name=name]").blur(function() {
		node.html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("select[name=css]").change(function() {
		node.attr("data-css", $(this).val());
		node.attr("class", "weui_btn moveNode " + $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=click]").blur(function() {
		node.attr("data-click", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=disabled]").blur(function() {
		node.attr("data-disabled", $(this).val());
		saveData();
	});
};
//按钮end
var labelNode = function(configTree, node) {
	configTree.find("input[name=fieldname]").blur(function() {
		node.html($(this).val());
		node.attr("data-text", $(this).val());
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
//	configTree.find("input[name=click]").blur(function() {
//		node.attr("data-click", $(this).val());
//		saveData();
//	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
};

// 输入框start
var textNode = function(configTree, node) {
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=maxlength]").blur(function() {
		node.attr("data-maxlength", $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-readonly", "1");
		} else {
			node.attr("data-readonly", "0");
		}
		saveData();
	});
	//获取焦点事件
	configTree.find("input[name=focus]").blur(function() {
		node.attr("data-focus", $(this).val());
		saveData();
	});
	//点击事件
	configTree.find("input[name=click]").blur(function() {
		node.attr("data-click", $(this).val());
		saveData();
	});
	//失去焦点事件
	configTree.find("input[name=blur]").blur(function() {
		node.attr("data-blur", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
	createDatatypeHtml(configTree, node);
};
//输入框属性end
// 密码域start
var passwordNode = function(configTree, node) {
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=maxlength]").blur(function() {
		node.attr("data-maxlength", $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-readonly", "1");
		} else {
			node.attr("data-readonly", "0");
		}
		saveData();
	});
	//获取焦点事件
	configTree.find("input[name=focus]").blur(function() {
		node.attr("data-focus", $(this).val());
		saveData();
	});
	//点击事件
	configTree.find("input[name=click]").blur(function() {
		node.attr("data-click", $(this).val());
		saveData();
	});
	//失去焦点事件
	configTree.find("input[name=blur]").blur(function() {
		node.attr("data-blur", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
	createDatatypeHtml(configTree, node);
};
//密码域属性end

//文本域start
var textareaNode = function(configTree, node) {
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_cells_title").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=maxlength]").blur(function() {
		node.attr("data-maxlength", $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("textarea.weui_textarea").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_cells_title").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_cells_title font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//获取焦点事件
	configTree.find("input[name=focus]").blur(function() {
		node.attr("data-focus", $(this).val());
		saveData();
	});
	//点击事件
	configTree.find("input[name=click]").blur(function() {
		node.attr("data-click", $(this).val());
		saveData();
	});
	//失去焦点事件
	configTree.find("input[name=blur]").blur(function() {
		node.attr("data-blur", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};

//文本域end

//下拉框start
var selectNode = function(configTree, node) {
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=parmkey]").blur(function() {
		node.attr("data-parmkey", $(this).val());
		saveData();
	});
	configTree.find("input[name=custom]").blur(function() {
		node.attr("data-custom", $(this).val());
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_cell_bd").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_cell_bd  font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//点击事件
	configTree.find("input[name=click]").blur(function() {
		node.attr("data-click", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};

//下拉框end

//复选框start
var checkboxNode = function(configTree, node) {
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});
	configTree.find("input[name=parmkey]").blur(function() {
		node.attr("data-parmkey", $(this).val());
		saveData();
	});
	configTree.find("input[name=custom]").blur(function() {
		node.attr("data-custom", $(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//picker打开事件
	configTree.find("input[name=open]").blur(function() {
		node.attr("data-open", $(this).val());
		saveData();
	});
	//picker关闭事件
	configTree.find("input[name=close]").blur(function() {
		node.attr("data-close", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};

//复选框end
//单选框start
var radioNode = function(configTree, node) {
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});
	configTree.find("input[name=parmkey]").blur(function() {
		node.attr("data-parmkey", $(this).val());
		saveData();
	});
	configTree.find("input[name=custom]").blur(function() {
		node.attr("data-custom", $(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//picker打开事件
	configTree.find("input[name=open]").blur(function() {
		node.attr("data-open", $(this).val());
		saveData();
	});
	//picker关闭事件
	configTree.find("input[name=close]").blur(function() {
		node.attr("data-close", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};


//单选框end

//picker选择器start
var pickerNode = function(configTree, node) {
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});
	configTree.find("input[name=parmkey]").blur(function() {
		node.attr("data-parmkey", $(this).val());
		saveData();
	});
	configTree.find("input[name=custom]").blur(function() {
		node.attr("data-custom", $(this).val());
		saveData();
	});
	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//picker关闭事件
	configTree.find("input[name=close]").blur(function() {
		node.attr("data-close", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};
//picker选择器end
//日历（日期选择）start
var datepickerNode = function(configTree, node) {
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});

	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//picker打开事件
	configTree.find("input[name=open]").blur(function() {
		node.attr("data-open", $(this).val());
		saveData();
	});
	//picker关闭事件
	configTree.find("input[name=close]").blur(function() {
		node.attr("data-close", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};
//日历
//日历（日期选择）end
//日期时间选择器（日期选择）start
var timepickerNode = function(configTree, node) {
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	configTree.find("input[name=domclass]").blur(function() {
		node.removeClass(node.attr("data-domclass"));
		node.attr("data-domclass", $(this).val());
		node.addClass($(this).val());
		saveData();
	});
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
		saveData();
	});
	configTree.find("input[name=name]").blur(function() {
		node.find(".weui_label").html($(this).val());
		node.attr("data-name", $(this).val());
		saveData();
	});
	configTree.find("input[name=placeholder]").change(function() {
		node.find("input.weui_input").attr("placeholder", $(this).val());
		node.attr("data-placeholder", $(this).val());
		saveData();
	});

	configTree.find("input[name=mustinput]").change(function() {
		if($(this).prop("checked")) {
			node.attr("data-mustinput", "1");
			node.find(".weui_label").prepend('<font class="mustinput-icon">*</font>');
		} else {
			node.attr("data-mustinput", "0");
			node.find(".weui_label font").remove();
		}
		saveData();
	});
	configTree.find("input[name=readonly]").change(function() {
		$(this).prop("checked") ? node.attr("data-readonly", "1") : node.attr("data-readonly", "0");
		saveData();
	});
	//picker打开事件
	/*configTree.find("input[name=open]").blur(function() {
		node.attr("data-open", $(this).val());
		saveData();
	});*/
	//picker关闭事件
	configTree.find("input[name=close]").blur(function() {
		node.attr("data-close", $(this).val());
		saveData();
	});
	//值改变事件
	/*configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});*/
};
//日期时间选择器end
//隐藏域start
var hiddenNode = function(configTree, node) {
	configTree.find("input[name=fieldname]").blur(function() {
		node.attr("data-fieldname", $(this).val());
	//	var consoleTree = $("#consoleTree", document);
	//	consoleTree.find("li[pkno=" + node.attr("data-pkno") + "] span").html("隐藏域(" + $(this).val() + ")");
		saveData();
	});
	configTree.find("input[name=domid]").blur(function() {
		node.attr("data-domid", $(this).val());
		saveData();
	});
	//值改变事件
	configTree.find("input[name=change]").blur(function() {
		node.attr("data-change", $(this).val());
		saveData();
	});
};

//隐藏域end

