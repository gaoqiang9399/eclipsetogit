/**
* 右侧展开表单
*
* @param {Object} 	options 参数
* @param {String} 	options.actionUrl 加载的URL
* @param {String} 	options.formUrl 提交from的URL
* @param {String} 	options.title 表单title
* @param {String} 	options.cssClass 展开样式扩展
* @param {Array} 	options.btns 表单按钮数组集合,input标签，属性和属性值的对象{type:button}
* @param {String} 	options.width 展开宽度，默认520px
* @param {Boolean} 	options.select3Flag 是否使用selet3初始化下来和复选，注意需要引用select3文件，默认false
* @param {Boolean} 	options.formFlag 是否直接传入Object，配合dataForm参数使用，为true时需参数dataForm，默认false
* @param {Object} 	options.dataForm 必须有{formHtml:"html结构的字符串"}
* @param {Function} options.beforeCallback 初始化完成回调
* @param {Function} options.callback 关闭页面后回调
* 
* @returns {object} this.options
*
* @date 2016-04-12
* @author wangcong
*/
function RightForm(options){
    this.init(options);
}

RightForm.prototype  = {
	DEFAULTS : {
		actionUrl:"",
		formUrl:"",
		title:"",
		cssClass:"",
		btns:[],
		width:"580px",
		formFlag:false,
		dataForm:"",
		select3Flag:true
	},
	rightForm_html : '<div class="rightForm-body">'+
							'<form id="rightFormInfo" name="rightFormInfo">'+
								'<div class="rightForm-edit">'+
									'<div class="rightForm-label">'+
										'<span>用户信息更改</span> <i class="i i-x5"></i>'+
									'</div>'+
									'<div class="rightForm-table">'+
									'</div>'+
									'<div class="rightForm-foot">'+
									'</div>'+
								'</div>'+
							'</form>'+
					 '</div>',
	init:function(options){
		this.options = $.extend({}, this.DEFAULTS, $.isPlainObject(options) && options);
		this.actionUrl = this.options.actionUrl;
		this.formUrl = this.options.formUrl;
		this.title = this.options.title;
		this.cssClass = options && options.hasOwnProperty('cssClass') ? options.cssClass : "";
		this.callback = options && options.hasOwnProperty('callback') ? options.callback : "";
		this.formCallback = options && options.hasOwnProperty('formCallback') ? options.formCallback : "";
		this.select3Flag = options && options.hasOwnProperty('select3Flag') ? options.select3Flag : false;
		this.beforeCallback = options && options.hasOwnProperty('beforeCallback') ? options.beforeCallback : "";
		this.btns = this.options.btns;
		this.formFlag = options && options.hasOwnProperty('formFlag') ? options.formFlag : false;
		this.dataForm = options && options.hasOwnProperty('dataForm') ? options.dataForm : "";
		this.clickElem = options && options.hasOwnProperty('clickElem') ? options.clickElem : "";
		this.initForm();
	},
	initForm : function(){
		var $rightFormHtml;
		var $this = this;
		var title = this.title;
		var cssClass = this.cssClass;
		var formUrl = this.formUrl;
		var callback = this.callback;
		var dataForm = this.dataForm;
		var formFlag = this.formFlag;
		var beforeCallback = this.beforeCallback;
		if($('.rightForm-body').length>0){
			$rightFormHtml = $('.rightForm-body');
		}else{
			$rightFormHtml = $(this.rightForm_html);
			$('body').append($rightFormHtml);
		}
		$this.$elem = $this.options.elem =  $rightFormHtml;
		$rightFormHtml.css('width',$this.options.width);
		$rightFormHtml.addClass(cssClass);
		$rightFormHtml.find("form").attr("action",formUrl);
		$rightFormHtml.find(".rightForm-label span").html(title);
		$rightFormHtml.find(".rightForm-label .i").unbind().bind("click",function(){
			$(".rightForm-body").animate({
				right : "-1000px"
			},function(){
				if(typeof(callback)=="function"){
					callback.call(this,$this.options);
				}
			});
		});
		if(!formFlag){
			$rightFormHtml.find(".rightForm-table").empty().append($this.getForm());
		}else{
			$this.options.jsonData = dataForm;
			$rightFormHtml.find(".rightForm-table").empty().append(dataForm.formHtml);
		}
		$rightFormHtml.animate({
				right : "0px"
		},function(){
			if(typeof(beforeCallback)=="function"){
				beforeCallback.call(this,$this.options);
			}
		});
		$this.createBtn();
		if ($this.select3Flag) {
			$this.initSelect($rightFormHtml);
			$this.initCheckBox($rightFormHtml);
		}
		
	},
	createBtn : function(){
		var $this = this;
		var $elem  = $this.$elem;
		$elem.find(".rightForm-foot").empty();
		var btns = this.btns;
		$.each(btns,function(i,btn){
			var inputHtml = $('<input />');
			$.each(btn, function(key,val) {
				if(key.indexOf("data")==-1){
					inputHtml.attr(key,val);
				}else{
					inputHtml.data(key.split("-")[1],val);
				}
			});
			$elem.find(".rightForm-foot").append(inputHtml);
		});
	},
	getForm:function(){
		var $this = this;
		var $formHtml = "";
		var actionUrl =this.actionUrl;
		$.ajax({
			type:"GET",
			async:false,
			cache:false,
			url:actionUrl,
			dataType:"json",
			success:function(jsonData){
				$this.options.jsonData = jsonData;
		    	$formHtml = jsonData.formHtml;
			},
			error:function(){
				alert("error");
			}
		});
		return $formHtml;
	},
	close:function(){
		var $this = this;
		var callback = this.callback;
		var $elem = this.$elem;
		$elem.animate({
			right : "-1000px"
		},function(){
			if(typeof(callback)=="function"){
				callback.call(this,$this.options);
			}
		});
	},
	remove:function(){
		var $elem = this.$elem;
		$elem.animate({
				right : "-1000px"
		},function(){
			$elem.remove();
		});
	},
	initSelect : function(elem){
		var selectArr = elem.find("select");
		$.each(selectArr, function(i,obj) {
			var desc = $(obj).attr("title");
			$(obj).select3({
				dropdownCssClass:"formSelect",
				placeholder:desc
				});
		});
	},
	initCheckBox : function(elem){
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
			var tempHidden = '<input type="hidden" name="'+key+'">';
			var tempPar = val[0].boxObj.parent();
			tempPar.append(tempHidden);
			tempPar.empty().select3({
				 items: val,
				 multiple: true,
				 placeholder:val[0].desc,
				 dropdownCssClass:"formCheckBox"
			});
			//没写完
		});
	}
};