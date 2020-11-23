//gu
var dicType =  {
	    "sts": {
	        "parm": [{"key": "是","value": "1"},{"key": "不是","value": "0"}],
	        "type": "0"
	    },
	    "number": {
	        "parm": [{"key": "等于","value": "0"},{"key": "大于","value": "1"},{"key": "小于","value": "2"},{"key": "在...之间","value": "3"},{"key": "不在...之间","value": "4"}],
	        "type": "1"
	    },
	    "value": {
	    	 "parm": [{"key": "是","value": "3"}],
	        "type": "3"
	    }
	};

/*var filter_dic = [
                  {
                      "keyChnName": "合同状态",
                      "keyName": "LNP_CONT_STS",
                      "parm": [
                          {"optName": "未生效", "optCode": "1"},
                          {"optName": "已签订","optCode": "2"},
                          {"optName": "已出账","optCode": "3"},
                          {"optName": "已注销","optCode": "4"}
                      ],
                      "condition":"sts",
                      "dicType":dicType.sts
                  }, {
                      "keyChnName": "余额",
                      "keyName": "MONEY",
                      "parm": [],
                      "condition":"money",
                      "dicType":dicType.number
                  }, {
                      "keyChnName": "天数",
                      "keyName": "demoDay",
                      "parm": [],
                      "condition":"demoDay",
                      "dicType":dicType.number
                  }, {
                      "keyChnName": "演示金额",
                      "keyName": "demoAmt",
                      "parm": [],
                      "condition":"demoAmt",
                      "dicType":dicType.number
                  }, {
                      "keyChnName": "演示名称",
                      "keyName": "demoName",
                      "parm": [],
                      "condition":"demoName",
                      "dicType":dicType.value
                  }
              ];*/

window.showFilterBox = function() {
	viewFilter();
	$(".my-filter-box").modal("show");
};


$(function() {
	$(".filter_in_input").on('keydown', function(e) {
		if(e.keyCode==13){
			var values = $(this).val().trim();
			//if(values!=""){
				updateTableData();
			//}
		}
	});
	$(".close").click(function() {
		$(this).parents(".modal").modal("hide");
	});
	$("#add_filter").bind('click', function() {
		add_filter();
	});
	
	/*筛选table*/
	$("#ckl").find("input[type='checkbox']").click(function() {
		if ($(this).is(':checked')) {
			addPill($(this).next().html().trim(), $(this).attr("name").trim(), "#5191d1", $("#pills"),$(this).val().trim());
		} else {
			delPill($("li[name='" + $(this).val().replace(/(\$|\.|\\|\"|\n|\r|\t)/g, "") + "']"));
		}
	});
	
	$("#save_filter").bind("click", function() {
		myFilter = [];
		$(".bs-example-modal-lg").modal("hide");
		$('#filter_table').find("input[type='checkbox']").each(function() {
			var $td = $(this).parent().parent().find('td');
			if ($td.length == 5) {
				var noValue=false,singleValue=false,betweenValue=false,listValue=false,$likeValue=false;
				var $keyChnName = $td.eq(1).children('input').val();
				var $keyName = $td.eq(2).find('button').attr("value");
				var $condition =  $td.eq(2).find('button').attr("condition");
				var $fun = $td.eq(3).find('button').attr("value");
				var $type = $td.eq(3).find('button').attr("typeValue");
				var $value, $secondValue;
				if($td.eq(4).find('button').length>0){
					$value = $td.eq(4).find('button').attr("value");
					$secondValue = -1;
				}else if($td.eq(4).find('input').length==1){
					$value = $td.eq(4).children('input').val();
					$secondValue = -1;
					singleValue=true;
					betweenValue=false;
				}else{
					$value = $td.eq(4).children('input').eq(0).val();
					$secondValue = $td.eq(4).children('input').eq(1).val();
					singleValue=false;
					betweenValue=true;
				}
				if($type==3){
					$likeValue=true;
					singleValue=false;
					betweenValue=false;
				}
				var json = {
					"noValue":noValue,
                	"singleValue":singleValue,
                	"betweenValue":betweenValue,
                	"listValue":listValue,
                	"tableName":"",
					"condition":$condition,
                	"type":$fun,
					"keyChnName": $keyChnName,
					"keyName": $keyName,
					"fun": $fun,
					"value": $value,
					"secondValue": $secondValue,
					"likeValue": $likeValue,
					"checked": $(this).is(":checked")
				};
				myFilter.push(json);
			}
		});
		var postData = {};
		postData.ajaxData = JSON.stringify(myFilter);
		$.ajax({
			type : 'post',
			url :webPath+"/pmsUserFilter/insert" ,
			dataType : 'json',
			data:postData,
			error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},success : function(data) {
				//myFilter = data;
				myFilter = eval("("+data.json+")");
			}
		});
		//$.myAlert.Alert(top.getMessage("SUCCEED_SAVE"));
		$('#filter_table').empty();
		myFilterList();
	});
});

function add_filter() {
	var $table = $('#filter_table');
	var $tr = $('<tr></tr>');
	add_checkbox($tr);
	add_input_name($tr);
	add_select_action($tr, filter_dic);
	$tr.appendTo($table);
}

var add_checkbox = function(obj,val) {
	var $this = $(obj);
	var $td = $('<td></td>');
	$td.addClass('filter_col_checkbox');
	var $checkbox = $('<input type="checkbox"/>');
	$checkbox.attr('checked', val);
	$checkbox.appendTo($td);
	$td.appendTo($this);
};
var add_input_name = function(obj,val) {
	var $this = $(obj);
	var $td = $('<td></td>');
	$td.addClass('filter_col_input');
	var $inputName = $('<input type="text"/>');
	$inputName.val(val);
	$inputName.addClass('form-control');
	$inputName.appendTo($td);
	$td.appendTo($this);
};
var add_select_action = function(obj, opt,val) {
	var $this = $(obj);
	var $td = $('<td></td>');
	var $div = $('<div></div>');
	$div.addClass('btn-group');
	var $btn = $('<button class="btn btn-default dropdown-toggle">选择类型</button>');
	$btn.attr('data-toggle', 'dropdown');
	var $span = $('<span></span>');
	$span.addClass('caret');
	var $ul = $('<ul></ul>');
	$ul.addClass('dropdown-menu');
	$.each(opt, function(i, list) {
		if(typeof(val)!="undefined"&&list.keyName==val){
			$btn.html(list.keyChnName);
			$btn.attr("value",list.keyName);
			$btn.attr("condition",list.condition);
		}
		var $li = $('<li></li>');
		var $a = $('<a></a>');
		$a.html(list.keyChnName);
		$a.attr("value",list.keyName);
		$a.attr('type', list.dicType);
		$a.attr("condition",list.condition);
		$a.appendTo($li);
		$a.bind('click', function() {
			$btn.html($(this).html());
			$btn.attr("value",$(this).attr("value"));
			$btn.attr("condition",$(this).attr("condition"));
			$span.appendTo($btn);
			if ($td.next()) {
				$td.nextAll().remove();
			}
			add_sub_select_action($this, list);
		});
		$li.appendTo($ul);
	});
	$span.appendTo($btn);
	$btn.appendTo($div);
	$ul.appendTo($div);
	$div.appendTo($td);
	$td.appendTo($this);
};

var add_sub_select_action = function(obj, dic) {
	var $tr = $(obj);
	var $td = $('<td></td>');
	var $div = $('<div></div>');
	$div.addClass('btn-group');
	var $btn = $('<button class="btn btn-default dropdown-toggle">选择条件</button>');
	$btn.attr('data-toggle', 'dropdown');
	var $span = $('<span></span>');
	$span.addClass('caret');
	var $ul = $('<ul></ul>');
	$ul.addClass('dropdown-menu');
	$.each(dic.dicType.parm, function(i, list) {
			var $li = $('<li></li>');
			var $a = $('<a></a>');
			$a.html(list.key);
			$a.attr("value",list.value);
			$a.attr("type",dic.dicType.type);
			$a.appendTo($li);
			$a.bind('click', function() {
				$btn.html($(this).html());
				$btn.attr("value",$(this).attr("value"));
				$btn.attr("typeValue",$(this).attr("type"));
				$span.appendTo($btn);
				if ($td.next()) {
					$td.nextAll().remove();
				}
				if ($(this).attr("type") == 0) {
					add_select_type($tr,dic);
				} else if ($(this).attr("type")==1&&$(this).attr("value") == 1||$(this).attr("value")==2||$(this).attr("value")==0) {
					 add_input_range($tr);
				} else if ($(this).attr("type")==1&&$(this).attr("value") == 3||$(this).attr("value")==4) {
					add_input_ranges($tr);
				}else if ($(this).attr("type")==3&&$(this).attr("value") == 3) {
					add_input_range($tr);
				}
			});
			$li.appendTo($ul);

	});
	$span.appendTo($btn);
	$btn.appendTo($div);
	$ul.appendTo($div);
	$div.appendTo($td);
	$td.appendTo($tr);
};

var add_input_range = function(obj,val) {
	var $tr = $(obj);
	var $td = $('<td></td>');
	var $input = $('<input type="text" style="width:99%"/>');
	if(typeof(val)!="undefined"){
		$input.val(val);
	}
	$input.addClass('form-control');
	$input.appendTo($td);
	$td.appendTo($tr);
	addRemoveBtn($tr);
};

var add_input_ranges = function(obj,val1,val2) {
	var $tr = $(obj);
	var $td = $('<td></td>');
	$td.addClass('filter_col_input');
	var $input1 = $('<input type="text" style="width:47%;" />');
	$input1.addClass('form-control filter_col_l');
	var $input2 = $('<input type="text" style="width:47%;"  />');
	$input2.addClass('form-control filter_col_l');
	var $lable = $('<label>至</label>');
	if(typeof(val1)!="undefined"){
		$input1.val(val1);
	}
	if(typeof(val2)!="undefined"){
		$input2.val(val2);
	}
	$input1.appendTo($td);
	$lable.appendTo($td);
	$input2.appendTo($td);
	$td.appendTo($tr);
	addRemoveBtn($tr);
};

var add_select_type = function(obj, opt,val) {
	var $tr = $(obj);
	var $td = $('<td></td>');
	var $div = $('<div></div>');
	$div.addClass('btn-group');
	var $btn = $('<button class="btn btn-default dropdown-toggle">选择条件</button>');
	$btn.attr('data-toggle', 'dropdown');
	var $span = $('<span></span>');
	$span.addClass('caret');
	var $ul = $('<ul></ul>');
	$ul.addClass('dropdown-menu');
	$.each(opt.parm, function(i, list) {
			if(typeof(val)!="undefined"&&list.optCode ==val){
				$btn.html(list.optName);
				$btn.attr("value",list.optCode);
			}
			var $li = $('<li></li>');
			var $a = $('<a></a>');
			$a.html(list.optName);
			$a.attr("value",list.optCode);
			$a.appendTo($li);
			$a.bind('click', function() {
				$btn.html($(this).html());
				$btn.val($(this).attr("value"));
				$span.appendTo($btn);
			});
			$li.appendTo($ul);
	});
	$span.appendTo($btn);
	$btn.appendTo($div);
	$ul.appendTo($div);
	$div.appendTo($td);
	$td.appendTo($tr);
	addRemoveBtn($tr);
};
//删除
var addRemoveBtn = function(obj){
	var $btn = $('<button type="button" class="btn btn-danger btn-sm ">删除</button>');
	$btn.css("margin-top","6px");
	$btn.bind("click",function(){
		$(this).parent().remove();
	});
	$btn.appendTo(obj);
};
//我的筛选  列表
var myFilterList = function() {
	var $ul = $('#my_filter');
	$ul.empty();
	$.each(myFilter, function(i, list) {
		if (list.checked) {
			var $li = $('<li></li>');
			var $checkbox = $('<input type="checkbox" />');
			var $span = $('<span></span>');
			$span.html(list.keyChnName);
		//	console.log(JSON.stringify(list).replace(/(\\|\"|\n|\r|\t)/g, "\\$1"));
			$checkbox.val(JSON.stringify(list));
			$checkbox.attr("name",list.keyChnName);
			$checkbox.appendTo($li);
			$checkbox.bind("click", function() {
				if ($(this).is(':checked')) {
					addPill($(this).next().html().trim(), $(this).attr("name").trim(), "#5191d1", $("#pills"),$(this).val().trim());
				} else {
					delPill($("li[name='" + $(this).attr("name").replace(/(\$|\.|\\|\"|\n|\r|\t)/g, "") + "']"));
				}
			});
			$span.appendTo($li);
			$li.appendTo($ul);
		}
	});
};
//添加筛选
var addPill = function($text, name, color, $div,val) {
	if ($text == "") return;
	if ($("li[name='" + name.replace(/(\$|\.|\\|\"|\n|\r|\t)/g, "") + "']").length > 0) return;
	$item = $('<li class="label fa filter-val" name="' + name.replace(/(\$|\.|\\|\"|\n|\r|\t)/g, "") + '" style="background-color: ' + color + ';">' + $text + '</li> ');
	var $hide = $("<input type='hidden'/>");
	$hide.val(val);
	$hide.appendTo($item);
	$div.prepend($item);
	$div.find('>li:last').click(function() {
		delPill($(this));
	});
	updateTableData();
};

//获取筛选值
function getFilterValArr(){
	var str=[];
	$(".filter-val").each(function(){
		str.push(eval('(' +$(this).find("input[type=hidden]").val()+ ')'));
	});
		var customQuery = {customQuery:$(".filter_in_input").val()};
		str.push(customQuery);
		return JSON.stringify(str);
}

//删除筛选
var delPill = function($pill) {
	$("#ckl ul li input[name=" + $pill.attr("name").replace(/(\$|\.|\\|\"|\n|\r|\t)/g, "") + "]").attr("checked", false);
	$pill.remove();
	updateTableData();
};



window.viewFilter = function() {
	var dicobj={};
	var $table = $('#filter_table');
	$table.empty();
	$.each(myFilter, function(i, list) {
		$.each(filter_dic,function(i,ss){
			if(ss.keyName==list.keyName){
				dicobj = jQuery.extend(true,{}, ss);
			}
		});
		//checkbox
		var $tr = $('<tr></tr>');
		add_checkbox($tr,list.checked);
		//name_input
		add_input_name($tr,list.keyChnName);
		//select_opt
		add_select_action($tr,filter_dic,list.keyName);
		//sub_select_opt
		var $td4 = $('<td></td>');
		var $div4 = $('<div></div>');
		$div4.addClass('btn-group');
		var $btn4 = $('<button class="btn btn-default dropdown-toggle">选择条件</button>');
		$btn4.attr('data-toggle', 'dropdown');
		var $span4 = $('<span></span>');
		$span4.addClass('caret');
		var $ul4 = $('<ul></ul>');
		$ul4.addClass('dropdown-menu');
		$.each(dicobj.dicType.parm, function(i, sub) {
			if (list.fun == sub.value) {
				$btn4.attr("value",sub.value);
				$btn4.html(sub.key);
			}
			var $li = $('<li></li>');
			var $a = $('<a></a>');
			$a.html(sub.key);
			$a.attr("value",sub.value);
			$a.attr('type', dicobj.dicType.type);
			$a.appendTo($li);
			$a.bind('click', function() {
				$btn4.attr("typeValue",$(this).attr("type"));
				$btn4.html($(this).html());
				$btn4.attr("value",$(this).attr("value"));
				$span4.appendTo($btn4);
				if ($td4.next()) {
					$td4.nextAll().remove();
				}
				if ($(this).attr('type') == 0) {
					add_select_type($tr,dicobj);
				} else if ((this).attr('type') == 1&&$(this).attr("value") == 1||$(this).attr("value")==2||$(this).attr("value")==0) {
					 add_input_range($tr);
				} else if ((this).attr('type') == 1&&$(this).attr("value") == 3||$(this).attr("value")==4) {
					add_input_ranges($tr);
				}else if ((this).attr('type') == 3&&$(this).attr("value") == 3) {
					add_input_range($tr);
				}
			});
			$li.appendTo($ul4);
		});
		$span4.appendTo($btn4);
		$btn4.appendTo($div4);
		$ul4.appendTo($div4);
		$div4.appendTo($td4);
		$td4.appendTo($tr);
		if (dicobj.dicType.type == 0) {
			add_select_type($tr,dicobj,list.value);
		} else if (dicobj.dicType.type == 1&&list.fun == 1||list.fun==2||list.fun==0) {
			 add_input_range($tr,list.value);
		} else if (dicobj.dicType.type == 1&&list.fun == 3||list.fun==4) {
			add_input_ranges($tr,list.value,list.secondValue);
		}else if (dicobj.dicType.type == 3&&list.fun == 3) {
			add_input_range($tr,list.value);
		}
		$tr.appendTo($table);
	});
};

var myFilter = [
//{"noValue":false,"singleValue":false,"betweenValue":false,"listValue":false,"tableName":"","type":"1","keyChnName":"测试1","keyName":"LNP_CONT_STS","fun":"1","value":"1","secondValue":-1,"checked":true},
//{"noValue":false,"singleValue":true,"betweenValue":false,"listValue":false,"tableName":"","type":"0","keyChnName":"测试2","keyName":"MONEY","fun":"0","value":"1000000","secondValue":-1,"checked":true},
//{"noValue":false,"singleValue":true,"betweenValue":true,"listValue":false,"tableName":"","type":"3","keyChnName":"测试3","keyName":"MONEY","fun":"3","value":"14000","secondValue":"27000","checked":true}
                ];
