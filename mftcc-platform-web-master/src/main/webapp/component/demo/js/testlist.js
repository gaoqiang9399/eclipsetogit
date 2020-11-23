//创建列表
function createData(data){
				var showTable=$("#tablist");
				showTable.html("");
				var headjson = data[0];
				var $thead = $("<tr></tr>");
				addAllCheck("userid").appendTo($thead);
				var y = sx.split("|");
    			var j,x;
    			var $td;
				for(j=0;j<y.length;j++){
					for(x in headjson){
						if(y[j] == x){
							if(x=="brNo"||x=="displayname"){
								$td = $('<th width="10%" nowrap="nowrap" align="center" scope="col"></th>');
							}else{
								$td = $('<th nowrap="nowrap" align="center" scope="col"></th>');
							}
							$td.addClass("tddh");
							$td.html(x);
							$td.appendTo($thead);
						}
					}
				}
				$thead.appendTo(showTable);
				//var page=data.pop();
				var $tbody = $("<tbody id='tab'></tbody>");
				for(var i =0;i<data.length;i++){
					var $tr = $('<tr></tr>');
					if(i%2==0){
						$tr.addClass("t1");
					}else{
						$tr.addClass("t2");
					}
					var jsonobj = data[i];
					addCheck("userid").appendTo($tr);
					y = sx.split("|");
					for(j=0;j<y.length;j++){
						for(x in jsonobj){
							if(y[j] == x){
								if(x=="brNo"||x=="displayname"){
									$td = $('<td id="'+x+'" width="10%" align="center"></td>');
								}else{
									$td = $('<td id="'+x+'" align="center"></td>');
								}
								$td.addClass("tddh");
								$td.html(jsonobj[x]);
								$td.appendTo($tr);
							}
						}
					}
					$tr.appendTo($tbody);
					$tbody.appendTo(showTable);
				}
			}
			
			var sx =  "|brNo|displayname|userid|username|roleNo|sex|idNo";
			function allInfo(){
				sx = "|brNo|displayname|userid|username|roleNo|sex|idNo";
				createData(data);
			}
			function bufen1(){
				sx =  "|brNo|displayname|roleNo|sex|idNo";
				createData(data);
			}
			function bufen2(){
				sx =  "|brNo|displayname|userid|username|roleNo|";
				createData(data);
			}
			
			
			function deltd(){ 
				//var count = 100;
				if($("#delcol").val()<1){alert("1"); return;};
				var n = $("#delcol").val();
				$.each($("#tablist tr"),function(i,obj){
					
					$(obj).find("td").eq(n).animate({width:'0px',opacity:'0'},"fast",function(){
						var $this = $(this);
						$this.html("");
						setInterval(function(){
							$this.remove();
						}, 500);
					});
					$(obj).find("th").eq(n).animate({width:'0px',opacity:'0'},"fast",function(){
						//addsub(this);
						var $this = $(this);
						$this.html("");
						setInterval(function(){
							$this.remove();
						}, 500);
					});
				});
			}
			
			function addCheck(id){ 
				var $td = $("<td style='width: 22px;text-align: left;' ></td>");
				$td.addClass("tddh");
				var $hidden = $("<input type='hidden' name='checkVal' />");
				var $i = $("<i></i>");
				$i.addClass("checkDiv fa fa-check-circle wx");
				$i.bind("click",function(){
					var $this = $(this);
					if ($this.hasClass("wx")) {
						$this.removeClass("wx");
						$this.addClass("xz");
						$hidden.val($this.parent().parent().find("#"+id).html());
					} else{
						$this.removeClass("xz");
						$this.addClass("wx");
						$hidden.val("");
						if($(".checkAllDiv").hasClass("xz")){
							$(".checkAllDiv").removeClass("xz");
							$(".checkAllDiv").addClass("wx");
						}
					}
					getCheckVal();
				});
				$hidden.appendTo($td);
				$i.appendTo($td);
				return $td;
			}
			function addAllCheck(id){ 
				var $th = $("<th style='width: 22px;text-align: left;padding-left:5px;' ></th>");
				$th.addClass("tddh");
				var $i = $("<i></i>");
				$i.addClass("checkAllDiv fa fa-check-circle wx");
				$i.bind("click",function(){
					var $this = $(this);
					if ($this.hasClass("wx")) {
						$this.removeClass("wx");
						$this.addClass("xz");
						$.each($(".checkDiv"), function(i,obj) {
							if($(obj).hasClass("wx")){
								$(obj).removeClass("wx");
								$(obj).addClass("xz");
								$(obj).parent().find("input[name=checkVal]").val($(obj).parent().parent().find("#"+id).html());
							} 
						}); 
					} else{
						$.each($(".checkDiv"), function(i,obj) {
							if($(obj).hasClass("xz")){
								$(obj).removeClass("xz");
								$(obj).addClass("wx");
								$(obj).parent().find("input[name=checkVal]").val("");
							}
						});
						$this.removeClass("xz");
						$this.addClass("wx");
					}
					getCheckVal();
				});
				$i.appendTo($th);
				return $th;
			}
			function getCheckVal(){
				var str = "";
				$.each($("input[name=checkVal]"),function(i,obj){
					var $obj = $(obj).val();
					if($obj!=""&&$obj!=null&&typeof($obj)!="undefined"){
						str+=$obj+"|";
					}
				});
			}
			
			
			
			
			/*
//筛选JS
var dic1=[
          {
        	  "dicName":"合同状态",
        	  "dicKey":"htSts",
        	  "dicValue":[
        	              	{
        	              		"key":"生效","value":"1"},{
        	              		"key":"注销","value":"2"},{
        	              		"key":"提前终止","value":"3"},{
        	              		"key":"回购","value":"4"
        	              	}
        	              ]
          },{
        	  "dicName":"租赁类型",
        	  "dicKey":"zlType",
        	  "dicValue":[
        	              	{
        	              		"key":"直接租赁","value":"1"},{
        	              		"key":"售后回租","value":"2"},{
        	              		"key":"委托租赁","value":"3"
        	              	}
        	              ]
          }
];
function getSearchList(obj){
	var $this = $(obj);
	//var X = $this.offset().left + $this.width();
	var Y = $this.offset().top + $this.height();
	var searchCss = {
			right :0 ,
			top : Y
		};
	if($(".searchBody").length>0){
		$(".searchBody").show();
		return false;
	}
	var $searchBody = $("<div class='searchBody'></div>");
	var $sysBody = $("<div class='sysBody'></div>");
    var $myTitle = $("<div class='mySearchTitle'></div>");
    var $mySearch = $("<div class='mySearchBody'></div>");
    var $span = $("<span>我的筛选</span>");
    var $i = $("<i class='fa fa-plus'></i>");
    $i.Searchtable();
    $span.appendTo($myTitle);
    $i.appendTo($myTitle);
    getSysSeachList($sysBody);
    $sysBody.appendTo($searchBody);
    $myTitle.appendTo($searchBody);
    $mySearch.appendTo($searchBody);
    $searchBody.hover(function(){
    },function(){
    	$(this).hide();
    });
    $searchBody.css(searchCss);
    $searchBody.appendTo("body");
}


function getSysSeachList(obj){
	var dic = dic1;
	$.each(dic,function(i,dicObj){
		var $span = $("<span>"+dicObj.dicName+"</span>");
		var $ul = $("<ul></ul>");
		$.each(dicObj.dicValue,function(i,keyValue){
			var $li = $("<li></li>");
			var $box = $("<input type='checkbox' value='"+keyValue.value+"' name='"+dicObj.dicKey+"'/>");
			var $a = $("<a>"+keyValue.key+"</a>");
			$box.checked();
			$box.appendTo($li);
			$a.appendTo($li);
			$li.appendTo($ul);
		});
		$span.appendTo(obj);
		$ul.appendTo(obj);
	});
}

(function($) {
//checkbox添加点击事件
	$.fn.checked = function() {
		var $this = $(this);
		$this.bind('click', function() {
			if($(this).is(':checked')){
				addLabel(this);
			}else{
				removeLabel(this);
			}
		});
	};

	//添加自定义筛选
	$.fn.Searchtable = function(){
		 $(this).bind('click', function() {
				var $mySearchDiv = $('<div class="mySearchDiv"></div>');
				var $myHeadDiv = $('<div class="myHeadDiv"></div>');
				var $myCtrlDiv = $('<div class="myCtrlDiv"></div>');
				var $myTableDiv = $('<div class= "myTableDiv"></div>');
				var $i = $('<i class="fa fa-times-circle"></i>');
				var $span = $('<span>我的筛选</span>');
				$span.appendTo($myHeadDiv);
				$i.appendTo($myHeadDiv);
				var $saveBtn = $("<button type='button'>保存</button>");
				var $newBtn = $("<button type='button'>新增</button>");
				$newBtn.addSearch();
				$saveBtn.appendTo($myCtrlDiv);
				$newBtn.appendTo($myCtrlDiv);
				var $table = $("<table id='searchTable'></table>");
				$table.appendTo($myTableDiv);
				$myHeadDiv.appendTo($mySearchDiv);
				$myCtrlDiv.appendTo($mySearchDiv);
				$myTableDiv.appendTo($mySearchDiv);
				$mySearchDiv.appendTo("body");
				getSearchFilter(dic2);
				alert(1);
			});
	};
	$.fn.addSearch=function(){
		$(this).bind('click', function() {
			var $tr = $("<tr></tr>");
			add_checkbox($tr);
			add_input_name($tr);
			add_select_dic($tr, dic2);
			$tr.appendTo($("#searchTable"));
		});
	};
})(jQuery);

function addLabel(obj){
	var $this = $(obj);
	var $label = $("<label></label>");
	var $i = $("<i class='fa fa-close'></i>");
	$i.bind("click",function(){
		var str = $(this).parent().attr("id");
		var arrs = str.split("-");
		$(".searchBody").find("input[name = "+arrs[0]+"][value= "+arrs[1]+"]").attr("checked",false);
		$(this).parent().remove();
	});
	$label.html($this.next().html());
	$label.val($this.val());
	$label.attr("id",$this.attr("name")+"-"+$this.val());
	$i.appendTo($label);
	$label.appendTo(".search-lable");
}
function removeLabel(obj){
	var $this = $(obj);
	$("#"+$this.attr("name")+"-"+$this.val()).remove();
};

var add_checkbox = function(obj) {
	var $this = $(obj);
	var $td = $('<td></td>');
	var $checkbox = $('<input type="checkbox"/>');
	$checkbox.attr('name', 'checkPop');
	$checkbox.appendTo($td);
	$td.appendTo($this);
};
var add_input_name = function(obj) {
	var $this = $(obj);
	var $td = $('<td></td>');
	var $inputName = $('<input type="text"/>');
	$inputName.appendTo($td);
	$td.appendTo($this);
};

var add_select_dic = function(obj, opt) {
	var $this = $(obj);
	var $td = $('<td></td>');
	var $select = $('<select name="dicKey"><option></option></select>');
	$.each(opt,function(i,list){
		var $opt = $("<option></option>"); 
		$opt.val(list.dicKey);
		$opt.html(list.dicName);
		$opt.appendTo($select);
	});
	$select.bind("change",function(){add_sub_dic($(this),opt);});
	$select.appendTo($td);
	$td.appendTo($this);
};

var add_sub_dic = function(obj,opt){
	var $this = $(obj).parent().parent();
	var $td = $('<td></td>');
	$this.find("select[name='dicValue']").empty();
	if($this.find("select[name='dicOpt']").length>0){
		var $select = $this.find("select[name=dicOpt]");
		$select.empty();
		$select.append("<option></option>");
		$.each(opt,function(i,list){
			if(list.dicKey==obj.val()){
				var dicType=list.dicType;
				var dicValue=list.dicValue;
				$.each(list.dicOpt,function(i,sub){
					var $opt = $("<option></option>"); 
					$opt.val(sub.value);
					$opt.html(sub.key);
					$opt.appendTo($select);
				});
				$select.unbind("change");
				$select.bind("change",function(){
					addInputValue(dicType,dicValue,$this);
				});
				return false;
			};
		});
	}else{
		var $select = $('<select name="dicOpt"><option></option></select>');
		$.each(opt,function(i,list){
			if(list.dicKey==obj.val()){
				var dicType=list.dicType;
				var dicValue=list.dicValue;
				$.each(list.dicOpt,function(i,sub){
					var $opt = $("<option></option>"); 
					$opt.val(sub.value);
					$opt.html(sub.key);
					$opt.appendTo($select);
				});
				$select.unbind("change");
				$select.bind("change",function(){
					addInputValue(dicType,dicValue,$this);
				});
			};
		});
		$select.bind("change",function(){});
		$select.appendTo($td);
		$td.appendTo($this);
	}
};

var addInputValue=function(type,opt,obj){
	var $this = $(obj);
	if(type==0){
		if($this.find("select[name=dicValue]").length>0){
			var $select = $this.find("select[name='dicValue']");
			$select.empty();
			$.each(opt,function(i,list){
				var $opt = $("<option></option>"); 
				$opt.val(list.value);
				$opt.html(list.key);
				$opt.appendTo($select);
			});
			$select.unbind("change");
			$select.bind("change",function(){});
		}else{
			var $td = $('<td></td>');
			var $select = $('<select name="dicValue"></select>');
			$.each(opt,function(i,list){
				var $opt = $("<option></option>"); 
				$opt.val(list.value);
				$opt.html(list.key);
				$opt.appendTo($select);
			});
			$select.unbind("change");
			$select.bind("change",function(){});
			$select.appendTo($td);
			$td.appendTo($this);
		}
	}else{
		$this.find("select[name='dicOpt']").parent().next().remove();
		var num = $this.find("select[name='dicOpt']").val();
		if(num==1||num==2||num==3){
			add_input_range($this);
		}else{
			add_input_ranges($this);
		}
	}
};
var add_input_range = function(obj) {
	var $this = $(obj);
	var $td = $('<td></td>');
	var $input = $('<input type="text" />');
	$input.appendTo($td);
	$td.appendTo($this);
};

var add_input_ranges = function(obj) {
	var $this = $(obj);
	var $td = $('<td></td>');
	var $input1 = $('<input type="text" />');
	var $input2 = $('<input type="text" />');
	var $lable = $('<label>至</label>');
	$input1.appendTo($td);
	$lable.appendTo($td);
	$input2.appendTo($td);
	$td.appendTo($this);
};
//向table添加已有自定义标签
var getSearchFilter = function(opt){
	var $this = $("#searchTable");
	$.each(myFilter,function(i,list){
		var $tr = $("<tr></tr>");
		var $td1 = $("<td></td>");
		var $td2 = $("<td></td>");
		var $td3 = $("<td></td>");
		var $td4 = $("<td></td>");
		var $td5 = $("<td></td>");
		var $checkbox = $('<input type="checkbox"/>');
		$checkbox.attr("checked",list.checked);
		$checkbox.attr('name', 'checkPop');
		$checkbox.appendTo($td1);
		var $inputName = $('<input type="text"/>');
		$inputName.val(list.dicName);
		$inputName.appendTo($td2);
		var $select = $('<select name="dicKey"><option></option></select>');
		$.each(opt,function(i,list){
			var $opt = $("<option></option>"); 
			$opt.val(list.dicKey);
			$opt.html(list.dicName);
			$opt.appendTo($select);
		});
		$select.find("option[value='"+list.dicKey+"']").attr("selected",true);
		$select.unbind("change");
		$select.bind("change",function(){add_sub_dic($(this),opt);});
		$select.appendTo($td3);
		var $subselect = $('<select name="dicOpt"><option></option></select>');
			$.each(opt,function(i,sublist){
				if(list.dicKey==sublist.dicKey){
					$.each(sublist.dicOpt,function(i,ss){
						var $opt = $("<option></option>"); 
						$opt.val(ss.value);
						$opt.html(ss.key);
						$opt.appendTo($subselect);
					});
					$subselect.unbind("change");
					$subselect.bind("change",function(){
						addInputValue(sublist.dicType,sublist.dicValue,$tr);
					});
				}
			});
		$subselect.find("option[value='"+list.dicOpt+"']").attr("selected",true);
		
		$subselect.appendTo($td4);
		if(list.dicType==0){
			var $valueselect = $('<select name="dicValue"></select>');
			$.each(opt,function(i,sublist){
				if(list.dicKey==sublist.dicKey){
					$.each(sublist.dicValue,function(i,ss){
						var $opt = $("<option></option>"); 
						$opt.val(ss.value);
						$opt.html(ss.key);
						$opt.appendTo($valueselect);
					});
				}
			});
			$valueselect.unbind("change");
			$valueselect.bind("change",function(){});
			$valueselect.appendTo($td5);
		}else{
			var num = list.dicOpt;
			var $input1 = $('<input type="text" />');
			$input1.val(list.value1);
			$input1.appendTo($td5);
			if(num==4||num==5){
				var $input2 = $('<input type="text" />');
				var $lable = $('<label>至</label>');
				$lable.appendTo($td5);
				$input2.val(list.value2);
				$input2.appendTo($td5);
			}
		}
		
		$td1.appendTo($tr);
		$td2.appendTo($tr);
		$td3.appendTo($tr);
		$td4.appendTo($tr);
		$td5.appendTo($tr);
		$tr.appendTo($this);
	});
};

//自定义选择
var dic2=[
          {
        	  "dicName":"合同状态",
        	  "dicKey":"htSts",
        	  "dicValue":[
        	              	{
        	              		"key":"生效","value":"1"},{
        	              		"key":"注销","value":"2"},{
        	              		"key":"提前终止","value":"3"},{
        	              		"key":"回购","value":"4"
        	              	}
        	              ],
        	    "dicOpt":[
                	            	{"key":"是","value":"1"},
                	            	{"key":"不是","value":"0"}
                	           ],
                "dicType":"0"
          },{
        	  "dicName":"租赁类型",
        	  "dicKey":"zlType",
        	  "dicValue":[
        	              	{
        	              		"key":"直接租赁","value":"1"},{
        	              		"key":"售后回租","value":"2"},{
        	              		"key":"委托租赁","value":"3"
        	              	}
        	              ],
        	  "dicOpt":[
        	            	{"key":"是","value":"1"},
        	            	{"key":"不是","value":"0"}
        	            ],
        	   "dicType":"0"
          },{
        	  "dicName":"余额",
        	  "dicKey":"yue",
        	  "dicValue":[],
        	  "dicOpt":[
        	              	{"key":"等于","value":"1"},
        	              	{"key":"大于","value":"2"},
        	              	{"key":"小于","value":"3"},
        	              	{"key":"在...之间","value":"4"},
        	              	{"key":"不在...之间","value":"5"}
        	              ],
        	  "dicType":"1"
          }
];



var myFilter = [{
	"dicName": "S3",
	"dicKey": "htSts",
	"dicOpt": "1",
	"value1": "1",
	"value2": "-1",
	"dicType": "0",
	"checked": true
}, {
	"dicName": "Q1",
	"dicKey": "yue",
	"dicOpt": "1",
	"value1": "150000",
	"value2": "-1",
	"dicType": "1",
	"checked": true
}, {
	"dicName": "Q2",
	"dicKey": "yue",
	"dicOpt": "4",
	"value1": "150000",
	"value2": "350000",
	"dicType": "1",
	"checked": true
}];






*/