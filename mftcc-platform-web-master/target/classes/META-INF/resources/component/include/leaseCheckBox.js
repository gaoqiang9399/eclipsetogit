function LeaseCheckBox(options){
    this.init(options);
}
function LeaseSwitch(options){
	this.init(options);
}
/**
 * callback:false,
 * tagType:"formTag"//formTag、bigForm
 */
function LeasePopCheckBox(options){
	this.init(options);
}

LeaseSwitch.prototype = {
		DEFAULTS : {
			theme:"lease",
			tag:"bigForm",
			checkedVal:null,
			uncheckedVal:null,
			checkedText:null,
			uncheckedText:null,
			callback:function ($element) {
	        	//console.log($element);
	        }
		},
		init:function(options){
			if(!options.hasOwnProperty('elem')){
				return false;
			}
			this.options = $.extend({}, this.DEFAULTS, $.isPlainObject(options) && options);
			var $elem = this.$elem = $(options.elem);
			this.initSwitch($elem);
		},
		initSwitch : function(elem){
			var $this = this;
			var callback = $this.options.callback;
			var $parent = elem.parent();
			var name = elem.attr("name");
			var mustinput = elem.attr("mustinput");
			var title = elem.attr("title");
			var $opts = elem.find("option");
			for(var i = $opts.length-1;i>=0;i--){
				if($opts[i].value==""){
					$opts.splice(i,1);
				}
			}
			if($opts.length>2){
				console.log("初始化对象！选项不能超过三个值！");
				return false;
			}
			var $input = $('<input type="hidden" class="multi-switch" />');
			$input.attr("name",name);
			$input.attr("mustinput",mustinput);
			$input.attr("title",title);
			if(elem.val()==""){
				$input.val($opts.eq(0).val());
				$input.attr("initial-value",$opts.eq(0).val());
			}else{
				$input.val(elem.val());
				$input.attr("initial-value",elem.val());
			}
			if($this.options.checkedVal!=null){
				$input.attr("checked-value",$this.options.checkedVal);
			}else{
				$input.attr("checked-value",$opts.eq(0).val());
			}
			if($this.options.uncheckedVal!=null){
				$input.attr("unchecked-value",$this.options.uncheckedVal);
			}else{
				$input.attr("unchecked-value",$opts.eq(1).val());
			}
			var textChecked,textNotChecked="";
			if($this.options.checkedText!=null){
				textChecked = $this.options.checkedText;
			}else{
				textChecked = $opts.eq(0).text();
			}
			if($this.options.uncheckedText!=null){
				textNotChecked = $this.options.uncheckedText;
			}else{
				textNotChecked = $opts.eq(1).text();
			}
			$parent.empty().append($input);
			$input.multiSwitch({
				functionOnChange:callback,
				textChecked:textChecked,
		        textNotChecked: textNotChecked
			});
		}
};

LeaseCheckBox.prototype  = {
		DEFAULTS : {
			cssClass:"",
			width:60,
			height:25,
			fontSize:12,
			col:4,
			callback:false,
			clickCallback:false,
			tagType:"bigForm"
		},
		init:function(options){
			this.options = $.extend({}, this.DEFAULTS, $.isPlainObject(options) && options);
			this.width = this.options.width;
			this.height = this.options.height;
			this.fontSize = this.options.fontSize;
			this.col = this.options.col;
			this.callback = options && options.hasOwnProperty('callback') ? options.callback : false;
			this.clickCallback = options && options.hasOwnProperty('clickCallback') ? options.clickCallback : false;
			this.elem = options && options.hasOwnProperty('elem') ? options.elem : "body";
			var $elem = this.$elem = $(options.elem);
			this.initCheckBox($elem);
		},
		initCheckBox : function(elem){
			var $this = this;
			var callback = this.callback;
			var checkBoxArr = elem.find("input[type=checkbox]");
			var temp = {};
			$.each(checkBoxArr, function(i,obj) {
				var $obj = $(obj);
				if(typeof(temp[$obj.attr("name")])=="undefined"){
					temp[$obj.attr("name")] = [];
					temp[$obj.attr("name")].push({"id":$obj.attr("value"),text:$obj.attr("datavalue"),desc:$obj.attr("title"),boxObj:$obj});
				}else{
					temp[$obj.attr("name")].push({"id":$obj.attr("value"),text:$obj.attr("datavalue"),desc:$obj.attr("title"),boxObj:$obj});
				}
			});
			$.each(temp, function(key,val) {
				var tempHidden = $('<input type="hidden" name="'+key+'" title="'+val[0].desc+'">');
				var tempPar = val[0].boxObj.parent();
				var $i = tempPar.find("i.mustinput");
				tempPar.empty().append(tempHidden);
				$this.initBox(val,tempHidden,tempPar);
				if($i.length>0){
					tempPar.append($i);
				}
			});
			if(callback){
				callback.call(this);
			}
		},
		initBox : function(arr,input,$elem){
			var clickCallback = this.clickCallback;
			var $this = this;
			var $ul = $('<ul class="lease_checkbox_ul"></ul>');
			$elem.append($ul);
			$ul.css("width",($this.width + 2)*$this.col+"px");
			var tempVal = "";
			$.each(arr,function(i,obj){
				var $li = $("<li class='lease_checkbox_li'></li>");
				$.each(obj,function(key,val){
					$li.data(key,val);
				});
				if(obj.boxObj.is(':checked')){
					$li.addClass("on");
					tempVal+=obj.id+"|";
				}else{
					$li.addClass("off");
				}
				$li.html(obj.text);
				$li.appendTo($ul);
				$li.attr("title",obj.text);
				$li.css("width",$this.width+"px");
				$li.css("height",$this.height+"px");
				$li.css("line-height",$this.height+"px");
				$li.css("font-size",$this.fontSize+"px");
				$li.bind("click",function(){
					if($(this).hasClass("off")){
						$(this).addClass("on").removeClass("off");
					}else{
						$(this).addClass("off").removeClass("on");
					}
					var str ="";
					$ul.find('.on').each(function(){
						str+=$(this).data("id")+"|";
					});
					input.val(str);
					if(clickCallback){
						clickCallback.call(this,$(this).data(),input);
					}
				});
			});
			input.attr("mustinput",arr[0].boxObj.attr('mustinput'));
			input.val(tempVal);
		}
};


LeasePopCheckBox.prototype  = {
		DEFAULTS : {
			title:"请选择",
			cssClass:"",
			callback:false,
			tagType:"formTag"//formTag、bigForm
		},
		init:function(options){
			var $this = this;
			$this.options = $.extend({}, this.DEFAULTS, $.isPlainObject(options) && options);
			var callback = this.callback = options && options.hasOwnProperty('callback') ? options.callback : false;
			$this.btn = options && options.hasOwnProperty('btn') ? options.btn : false;
			$this.elem = options && options.hasOwnProperty('elem') ? options.elem : "body";
			$this.handle = options && options.hasOwnProperty('handle') ? options.handle :false;
			$this.selectType = options && options.hasOwnProperty('selectType') ? options.selectType :false;
			$this.className = options && options.hasOwnProperty('className') ? options.className :"mulitSelector";
			
			var $elem = this.$elem = $(options.elem);
			if(options.hasOwnProperty('data')&&typeof(options.data) === 'object'){
				$elem.mulitselector({
					title: options.title,
					data: options.data,
					callback:callback,
					handle:$this.handle,
					selectType:$this.selectType,
					className:$this.className
				});
			}else{
				if(this.options.tagType=="formTag"){
					this.initFormCheckBox($elem);
				}else{
					this.initCheckBox($elem);
				}
			}
		},
		initFormCheckBox:function($elem){
			var $this = this;
			var temp = {};
			$.each($elem, function(i,obj) {
				var $obj = $(obj);
				if(typeof(temp[$obj.attr("name")])=="undefined"){
					temp[$obj.attr("name")] = [];
					temp[$obj.attr("name")].push({"id":$obj.attr("value"),name:$obj[0].nextSibling.data,desc:$obj.attr("title"),boxObj:$obj});
				}else{
					temp[$obj.attr("name")].push({"id":$obj.attr("value"),name:$obj[0].nextSibling.data,desc:$obj.attr("title"),boxObj:$obj});
				}
			});
			$.each(temp, function(key,val) {
				var tempHidden = $('<input type="hidden" name="'+key+'" title="'+val[0].desc+'">');
				var tempPar = val[0].boxObj.parent();
				var $i = tempPar.find("i.mustinput");
				tempPar.empty().append(tempHidden);
				$this.initBox(val,tempHidden,tempPar);
				if($i.length>0){
					tempPar.append($i);
				}
			});
		},
		initCheckBox : function(elem){
			var $this = this;
			//var checkBoxArr = elem.find("input[type=checkbox]");
			var temp = {};
			$.each(elem, function(i,obj) {
				var $obj = $(obj);
				if(typeof(temp[$obj.attr("name")])=="undefined"){
					temp[$obj.attr("name")] = [];
					temp[$obj.attr("name")].push({"id":$obj.attr("value"),name:$obj.attr("datavalue"),desc:$obj.attr("title"),boxObj:$obj});
				}else{
					temp[$obj.attr("name")].push({"id":$obj.attr("value"),name:$obj.attr("datavalue"),desc:$obj.attr("title"),boxObj:$obj});
				}
			});
			$.each(temp, function(key,val) {
				var tempHidden = $('<input type="hidden" name="'+key+'" title="'+val[0].desc+'">');
				var tempPar = val[0].boxObj.parent();
				var $i = tempPar.find("i.mustinput");
				tempPar.empty().append(tempHidden);
				$this.initBox(val,tempHidden,tempPar);
				if($i.length>0){
					tempPar.append($i);
				}
			});
		},
		initBox : function(arr,input,$elem){
			var callback = this.callback;
			var btn = this.btn;
			var handle = this.handle;
			var selectType = this.selectType;
			var $this = this;
			var $ul = $('<ul class="lease_checkbox_ul"></ul>');
			$elem.append($ul);
			$ul.css("width",($this.width + 2)*$this.col+"px");
			var tempVal = "";
			$.each(arr,function(i,obj){
				if(obj.boxObj.is(':checked')){
					tempVal+=obj.id+"|";
				}
			});
			input.attr("mustinput",arr[0].boxObj.attr('mustinput'));
			input.val(tempVal);
			input.mulitselector({
				title: $this.options.title,
				data: arr,
				callback:callback,
				btn:btn,
				handle:handle,
				selectType:selectType,
				className:$this.className
			});
		}
};

(function ($) {
$.fn.multiSwitch = function (options) {
    var settings = $.extend({
        textChecked: '启用',
        textNotChecked: '失效',
        functionOnChange: false
    }, options);

    // Create de base element
    var element = $('<div />').addClass('multi-switch');
    // Set width in the base element
    element.css('width', settings.width);
    // Create de content element
    var content = $('<div />').addClass('switch-content');
    // Insert a circle element
    content.append($('<div />').addClass('switch-circle'));
    // Insert a text element
    content.append($('<div />').addClass('switch-text'));
    // Append to base element
    element.append(content);

    // Store de main object
    var cheslides = this;

    cheslides.each(function () {

        var uelement = element.clone();

        var eventClick = true;

        var status;
        if ($(this).attr('checked-value') && !$(this).is(":disabled")) {

            var classe = 'initial';
            if ($(this).val() == $(this).attr('checked-value')) {
                classe = 'active';
                uelement.find('.switch-content .switch-text').text(settings.textChecked);
            } else
            if ($(this).val() == $(this).attr('unchecked-value')) {
                classe = 'disable';
                uelement.find('.switch-content .switch-text').text(settings.textNotChecked);
            }
        
            if (classe == 'initial') {
                var infoDeferido = $('<span class="info-slide disable" title="' + settings.textNotChecked + '"/>');
                var infoIndeferido = $('<span class="info-slide active" title="' + settings.textChecked + '"/>');
                uelement.find('.switch-content').append(infoDeferido);
                uelement.find('.switch-content').append(infoIndeferido);
                
                infoDeferido.click(function(){
                    var checkbox = $(uelement).find('input');                        
                    checkbox.val($(checkbox).attr('checked-value'));
                    
                    $(uelement).find('span').html(settings.textChecked);
                    $(uelement).find('.switch-content').addClass('active');
                    $(uelement).find('.switch-content').removeClass('disable');
                    
                    eventClick = true;
                    
                    $(uelement).find('.info-slide').remove();
                });
                
                infoDeferido.hover(function(){
                    $(uelement).find('.switch-content').addClass('disable');
                    $(uelement).find('.switch-content').removeClass('initial');
                }, function(){
                    $(uelement).find('.switch-content').addClass('initial');
                    $(uelement).find('.switch-content').removeClass('disable');
                });
                
                infoIndeferido.click(function(){
                    var checkbox = $(uelement).find('input');                        
                    checkbox.val($(checkbox).attr('unchecked-value'));
                    
                    $(uelement).find('span').html(settings.textChecked);
                    $(uelement).find('.switch-content').addClass('disable');
                    $(uelement).find('.switch-content').removeClass('active');
                    
                    eventClick = true;
                    
                    $(uelement).find('.info-slide').remove();
                });
                
                infoIndeferido.hover(function(){
                    $(uelement).find('.switch-content').addClass('active');
                    $(uelement).find('.switch-content').removeClass('initial');
                }, function(){
                    $(uelement).find('.switch-content').addClass('initial');
                    $(uelement).find('.switch-content').removeClass('active');
                });
                eventClick = false;
            }

            uelement.find('.switch-content')
                    .addClass(classe)
                    .addClass($(this).is(":disabled") ? 'disabled' : '');
            uelement.append($(this).clone());

        } else {
            var isChecked = $(this).is(":checked");

            uelement.find('span').html(isChecked ? settings.textChecked : settings.textNotChecked);
            uelement.find('.switch-content')
                    .addClass(isChecked ? 'active' : 'disable')
                    .addClass($(this).is(":disabled") ? 'disabled' : '');
            uelement.append($(this).clone());
        }

        uelement.click(function () {
            
            if (!eventClick)
                return;

            var checkbox = $(this).find('input');

            if (checkbox.is(":disabled"))
                return;

            if ($(checkbox).attr('checked-value')) {
                var checked = $(this).find('.switch-content').hasClass('active');
                status = !checked;
                if (checked) {
                    checkbox.val($(checkbox).attr('unchecked-value'));
                } else {
                    checkbox.val($(checkbox).attr('checked-value'));
                }
            } else {
                status = !checkbox.is(":checked");
                
                checkbox.prop('checked', status);
            }
           
            $(this).find('.switch-content').removeClass('initial');
            if (status) {
                $(this).find('span').html(settings.textChecked);
                $(this).find('.switch-content').addClass('active');
                $(this).find('.switch-content .switch-text').text(settings.textChecked);
                $(this).find('.switch-content').removeClass('disable');
            } else {
                $(this).find('span').html(settings.textNotChecked);
                $(this).find('.switch-content').addClass('disable');
                $(this).find('.switch-content .switch-text').text(settings.textNotChecked);
                $(this).find('.switch-content').removeClass('active');
            }
            if(settings.functionOnChange){
            	settings.functionOnChange.call(this,checkbox);
            }
        });
        uelement.change(function () {

            var checkbox = $(this).find('input');
            if (checkbox.is(":disabled"))
                return;

            if ($(checkbox).attr('checked-value')) {
                var checked = $(this).find('.switch-content').hasClass('active');
                status = !checked;

                if (checked) {
                    checkbox.val($(checkbox).attr('unchecked-value'));
                } else {
                    checkbox.val($(checkbox).attr('checked-value'));
                }
            } else {
                status = !checkbox.is(":checked");
                checkbox.prop('checked', status);
            }
            $(this).find('.switch-content').removeClass('initial');
            if (status) {
                $(this).find('span').html(settings.textChecked);
                $(this).find('.switch-content').addClass('active');
                $(this).find('.switch-content').removeClass('disable');
            } else {
                $(this).find('span').html(settings.textNotChecked);
                $(this).find('.switch-content').addClass('disable');
                $(this).find('.switch-content').removeClass('active');
            }
            if(settings.functionOnChange){
            	settings.functionOnChange.call(this,checkbox);
            }
        });

        $(this).after(uelement);
        $(this).remove();
    });
};
}(jQuery));
$(function() {
	$("body").bind("click", function(e) {
		if($(e.target).attr('handle')!="mulitHandle"&&e.target.id != "mulitSelector" && $(e.target).parents("#mulitSelector").length == 0 && e.target.className.indexOf("mulitSelectorDiv") ==-1) {
			$("#mulitSelector").remove();
		}
		if($(e.target).attr('handle')!="mulitHandle"&&e.target.id != "singleSelector" && $(e.target).parents("#singleSelector").length == 0 && e.target.className.indexOf("mulitSelectorDiv") ==-1) {
			$("#singleSelector").remove();
		}
	});
});
(function($) {
	$.fn.mulitselector = function(options) {
		var $input = $(this);
		var timestamp = Date.parse(new Date());
		var $div = $('<div class="mulitSelectorDiv '+timestamp+'"></div>');
		$input.parent().append($div);
		$input.attr("mark",timestamp);
		$input.attr("type","hidden");
		var ms_html,clickCallback,btn,handle,selectType,idName,initValue;

		var settings = {
			title: "请选择",
			data: null,
			pos:false
		};

		if(options) {
			jQuery.extend(settings, options);
			clickCallback = options.callback;
			btn = options.btn;
			handle = options.handle;
			selectType = options.selectType;
			initValue = options.initValue;
			idName =  options.className;
		}
		$div.bind("click", openMulit);
		$(handle).attr('handle',"mulitHandle").bind("click",openMulit);
		initVal();
		function initVal(){
			var dataArray = settings.data;
			var obj = $input.val().split("|");
			var html = [];
			for(var i = 0; i < obj.length; i++) {
				$.each(dataArray,function(k,v){
					if(v.id==obj[i]){
						html.push('<label class="'+idName+'  mulitValLabel">'+v.name+'<i relVal="'+v.id+'" class="mulit_del_val"></i></label>');
					}
				});
			}
			$(html.join("")).appendTo($div.empty());
			$div.find(".mulit_del_val").bind("click",function(){
				$(this).parent().remove();
				var tempRel = "";
				$div.find(".mulit_del_val").each(function(i,obj){
					tempRel += (i == 0 ? "" : "|") + $(obj).attr("relVal");
				});
				$input.val(tempRel);
			});
		}
		function openMulit(handle) {
			settings.pos=false;
			$("#mulitSelector").remove();
			$("#singleSelector").remove();
			$input.addClass("mulitSelectorElem");

			function initialise() {
				initContent();
				initEvent();
			}
			
			function MulitSetVal(){
					var result = "";
					var resultRel = "";
					var html = [];
					var obj = $("#allItems1 input:checked");
					for(var i = 0; i < obj.length; i++) {
						result += (i == 0 ? "" : "|") + obj[i].value.split("@")[1];
						html.push('<label class="'+idName+'  mulitValLabel">'+obj[i].value.split("@")[1]+'<i relVal="'+obj[i].value.split("@")[0]+'" class="mulit_del_val"></i></label>');
						
						resultRel += (i == 0 ? "" : "|") + obj[i].value.split("@")[0];
					}
					$(html.join("")).appendTo($div.empty());
					$div.find(".mulit_del_val").bind("click",function(){
						$(this).parent().remove();
						var tempRel = "";
						$div.find(".mulit_del_val").each(function(i,obj){
							tempRel += (i == 0 ? "" : "|") + $(obj).attr("relVal");
						});
						$input.val(tempRel);
					});
					$input.val(resultRel);
					
			}
			
			function closeMulit(){
				ms_html.remove();
			}
			
			function initEvent() {

				$("#ms_bt_ok").click(function() {
					MulitSetVal();
				});

				$("#ms_bt_clear").click(function() {
					ms_html.remove();
					$("#ms" + $input.attr("timestamp").substring(2)).removeAttr("checked");
					$input.val("");
					$div.html("");
				});
				$("#ms_bt_cancel").click(function() {
					ms_html.remove();
				});

				//全选
				$("#ms_bt_checkall").click(function() {

					$('label input:checkbox').prop('checked', true);
				});
				//全不选
				$("#ms_bt_checkno").click(function() {
					$('label input:checkbox').prop('checked', false);
				});
				$("#mulitSearch").bind({
					//focus:function(){showLi(this)},
					//blur:function(){showLi(this)},
					keydown:function(){showLi(this)},
					keyup:function(){showLi(this)},
					change:function(){showLi(this)}
				});
				
			}
			
			function showLi(obj){
				var kw = obj.value;
				ms_html.find(".nonelay span").each(function(){
					var str = this.innerHTML;
					//console.log(str,kw,str.indexOf(kw));
					if(str.indexOf(kw)==-1){
						$(this).parents(".nonelay").hide();
					}else{
						$(this).parents(".nonelay").show();
					}
				});
			}

			function initContent() {

				var offset = $div.offset();
				var divtop = '0px';
				var divleft =1 + $div[0].offsetWidth + 'px';
				if((1 + offset.left + $div[0].offsetWidth+200)>window.innerWidth){
					divtop = 1+ $div[0].offsetHeight + 'px';
					divleft ='0px';
					settings.pos=true;
				}
				var divwidth = '520px';
				if(selectType){
					divtop = $div[0].offsetHeight+'px';
					divleft = '0px';
					settings.pos=false;
					divwidth = $div[0].offsetWidth+'px';
				}
				var popmask = document.createElement('div');

				var html = [];

				html.push('<div class="'+timestamp+'" id="'+idName+'" style="width:'+divwidth+';display:block; top:' + divtop + ';left:' + divleft + '; position: absolute; z-index: 1999;">');
				html.push('    <div id="pslayer"  class="alert_div ">');
				html.push('        <div class="box">');
				if(!selectType){
					html.push('            <div class="psHeader"><span id="psHeader">' + settings.title + '</span><b id="ms_bt_cancel" class="mulit_del_val" ></b></div>');
				}
				html.push('            <div class="psSearch"><input type="text" id="mulitSearch"></div>');
				html.push('				<div class="blk">');
				html.push('				<div class="sech_layb"> ');
				html.push('					<ul id="allItems1">');

				var dataArray = settings.data;
				if(dataArray != null) {
					var len = dataArray.length;
					for(var i = 0; i < len; i++) {
						var d = dataArray[i];
						var status = findStatus(d.id);
						html.push('						<li id=$' + d.id + ' name=100 class="nonelay">');
						html.push('							<input class="regular-checkbox" id="' + d.id + '" type="checkbox" ' + status + ' value="' + (d.id + '@' + d.name) + '" />');
						html.push('							<label for="' + d.id + '"></label>');
						html.push('							<span class="checkbox-desc" for="' + d.id + '">'+ d.name+'</span>');
						html.push('						</li>');
					}
				}

				html.push('					</ul>');
				html.push('				</div>');
				html.push('			</div>');
				html.push('		</div>');
				html.push('   </div>');
				html.push('</div>');

				ms_html = $(html.join("")).appendTo($input.parent());
				ms_html.find(".regular-checkbox").bind("change",function(){
					MulitSetVal();
					if(clickCallback){
						clickCallback.call($input.val());
					}
					if(settings.pos){
						ms_html.css("top",$div[0].offsetHeight+'px');
					}
					
				});
				ms_html.find(".checkbox-desc").bind("click",function(){
					var $i = $(this).parent().find("input:checkbox");
					var flag = $i.prop("checked");
					if(selectType){
						$('#allItems1 input:checkbox').prop('checked', false);
						$i.prop('checked', flag);
					}
					$(this).prev().click();
				});
				if(typeof(btn)=="function"){
					ms_html.find(".box").append('<div class="btn"><button type="button">新增</button></div>');
					ms_html.find(".btn button").bind("click",function(){
						btn.call($input.val());
					});
				}
			}

			function findStatus(d) {

				var content = $input.val();
				if(jQuery.trim(content) == "") {
					return "";
				}

				var obj = content.split("|");
				for(var i = 0; i < obj.length; i++) {
					if(obj[i] == d) {
						return "checked";
					}
				}

			}

			initialise();
		}

	};

})(jQuery);