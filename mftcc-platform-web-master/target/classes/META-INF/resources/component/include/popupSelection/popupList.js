(function($, win) {
	var methods = {
		init: function(optionsArgs) { //初始化
			var defaults = {
				ajaxUrl: false, //异步url
				multiple: false, //false-单选,true-复选
				inline: true, //true-内联,false-弹出
				valueClass: false, //String类型，自定义显示值class
				selectClass: false, //String类型，自定义选框class
                bgClass:false,//String 类型，自定义弹出层样式
				searchOn: false, //false-不启用搜索，true-启用搜索
				addBtn: false, //false-不启用新增按钮,{"title":"按钮名称","fun":function(){//点击执行}}
				items: [], //[{id:"1",name:"A"},{...}]
				queryData: {}, //[{id:"1",name:"A"},{...}]
				labelEdit: false, //选项编辑回调函数function(d){console.log(d)};
				labelShow: false, //是否在选择区域显示，已选择项
				elemEdit: false, //字段既能手动填写又能选择的情况，在打开弹层时，没有选择值直接关闭弹层，原输入框仍能手动填写
				title: false, //标题
				changeCallback: false, //回调函数
				handle: false, //触发器
				initFlag: true, //初始化状态
				tablehead: {}, //列表显示
				valueElem: false, //存储真实值的jQuery对象，必填
				ajaxData: false, //后台数据接收对象调整
				pageSize:15,//每页条数
				splitStr:"|",//分隔符
                searchplaceholder:'',//查询输入框的悬浮内容
                allCheckShow:false//复选时，选择前面是否展示复选按钮
			};
			var options = $.extend(defaults, optionsArgs);
			return this.each(function() {
				var $this = $(this);
				options.elem = $this;
				$this.data("options", options);
				if(options.ajaxUrl && typeof(options.ajaxUrl) == "string") {
					var hideItem;
					if(options.valueElem && $(options.valueElem).length > 0) {
						hideItem = $(options.valueElem);
					} else {
						hideItem = $('<input type="hidden" >');
						hideItem.insertAfter($this);
						if($this.attr("name")) {
							hideItem.attr('name', $this.attr("name"));
							$this.attr("name", "pops" + $this.attr("name"));
						}
						if($this.attr("mustinput") && $this.attr("mustinput") != "") {
							hideItem.attr('mustinput', $this.attr("mustinput"));
							hideItem.attr('title', $this.attr("title"));
							$this.removeAttr("mustinput");
						}
					}

					$this.data("values", hideItem);

					$this.data("items", options.items);

					methods.showValueDiv($this, options);
					methods.buildListValue($this, options);
					methods.initItems($this,options);
					methods.initValue($this,options);
				} else {
					console.log("popupList-ajaxUrl参数配置错误");
					return false;
				}

			});
		},
		initItems:function(elem, options){
			methods.getItems(options);
		},
		initValue: function(elem, options) { //赋初值
			var val = elem.val();
			var arr = [];
			if(val && val.indexOf(options.splitStr) > -1) {
				arr = val.split(options.splitStr);
				arr = $.grep(arr, function(n) {
					return $.trim(n).length > 0;
				});
			} else if(val) {
				arr.push(val);
			}
			var realVal = $(options.valueElem).val();
			var realArr = [];
			if(realVal && realVal.indexOf(options.splitStr) > -1) {
				realArr = realVal.split(options.splitStr);
				realArr = $.grep(realArr, function(n) {
					return $.trim(n).length > 0;
				})
			} else if(realVal) {
				realArr.push(realVal);
			}
			var popsparm = elem.data("popsparm");
			if(realArr.length==arr.length){
				for(var i = 0; i < arr.length; i++) {
					if(!options.multiple) { //单选的也是直接显示值就可以了
						methods.setSelectInitValue(options, elem, arr[i]);
					} else {
						methods.setChooseInitValue(options, elem, arr[i],realArr[i]);
					}
				}
			}
			
			options.initFlag = false;
		},
		setSelectInitValue: function(options, elem,val) { //赋值
			var popsvalue = elem.data("popsvalue");
			var popsparm = elem.data("popsparm");
			popsvalue.html(val);
			elem.data("text",val);
		},
		setChooseInitValue: function(options, elem, disName,realval) { //设置pop选中值
			var popsparm = elem.data("popsparm");
			var html = [];
			if(options.labelShow) {
				html.push('<div class="pops-label" data-id="' + realval+ '">');
			} else {
				html.push('<div class="pops-label off" data-id="' + realval + '">');
			}
			html.push('<div class="pops-label-alt">');
			html.push(disName);
			html.push('</div>');
			html.push('	<i class="pops-close"></i>');
			if(options.labelEdit && typeof(options.labelEdit) == "function") {
				html.push('	<i class="pops-edit"></i>');
			}
			html.push('</div>');
			var label = $(html.join(" "));
			label.appendTo(popsparm.find(".pops-list-values"));
			methods.setLabelEvent(label, options);
			methods.confirmValue(options,elem);
			popsparm.find(".list-checkbox[value="  + realval + "]").prop("checked", true);
		},
		showValueDiv: function(elem, options) { //页面显示值区域
			var div = $('<div class="pops-value"></div>');
			if(options.valueClass && typeof(options.valueClass) == "string") {
				div.addClass(options.valueClass);
			}
			div.css("min-height", elem.outerHeight());
			elem.css("display", "none");
			div.insertAfter(elem);
			div.bind("click", function() {
				methods.showList(this, elem);
				methods.showTableHeadDiv(options);
			});
			if(options.handle) {
				$(options.handle).bind("click", function() {
					methods.showList(this, elem);
					methods.showTableHeadDiv(options);
					return false;
				});
			}
			elem.data("popsvalue", div);
		},
		buildListValue: function(elem, options) { //构建选择区域
			var html = $(methods.getListHtml(options));
			$("body").append(html);
			elem.data("popsparm", html);
			if(options.multiple){
				$(".M-box").css('background-color','#F0F5FB');
			}
			if(options.searchOn) {
				var search = $(methods.getSearchHtml(options));
				html.find(".pops-list-search").append(search);
				html.find(".pops-search-btn").bind("click", function() {
					methods.searchItems(options, html.find(".pops-search-input").val());
				});
				html.find(".pops-search-input").bind("keydown", function(eArgs) {//添加回车事件
					var e = eArgs || window.event;
					if(e.keyCode == 13){ 
						methods.searchItems(options, html.find(".pops-search-input").val());
					} 
				});
				if(typeof($.fn.placeholder) == "function") {
					search.find("input").placeholder({
						isUseSpan: true,
						all: false
					});
				}
			}
			//新增按钮
			if(options.addBtn && !$.isEmptyObject(options.addBtn)) {
				var addbtn = $(methods.getAddBtnHtml());
				html.find(".pops-list-search").prepend(addbtn);
				addbtn.find(".pops-list-addbtn-btn").text(options.addBtn.title);
				if(options.addBtn.fun && typeof(options.addBtn.fun) == "function") {
					addbtn.bind("click", function() {
						options.addBtn.fun.call(this, elem.data("values"), elem);
					})
				}
				
			}

			var initTable = $(methods.getListTableHtml(options));
			html.find(".pops-list-body").append(initTable);
			html.find(".pops-list-head .pops-close").bind("click", function() {
				$(this).parents().find(".pops-bg").hide();
				if(options.elemEdit){
					if(!elem.val()){//字段既能手动填写又能选择的情况，在打开弹层时，没有选择值直接关闭弹层，原输入框仍能手动填写
						elem.css("display","block");
						elem.parent().find(".pops-value").hide();
					}
				}
				methods.hideList(elem);
			});
			if(options.multiple) {
				html.find(".pops-list-search .pops-btn-confirm").bind("click", function() {
					methods.confirmValue(options, elem);
				});
			}
		},
		getItems: function(options, kw, page) {
			methods.showLoading(options);
			var ajaxData = options.ajaxData;
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			if(kw && kw.trim()) {} else {
				if(popsparm.find(".pops-search-input").length > 0) {
					popsparm.find(".pops-search-input").val("");
				}
			}

			page = page || 1;
			var valobj;
			if(ajaxData && typeof(ajaxData) == "function") {
				valobj = ajaxData.call(this, kw, page);
			} else {
				valobj = {
					"pageSize":options.pageSize,
					"ajaxData": '[{"customQuery":"'+(kw?kw:"")+'"},{"customSorts":"[]"}]',
					"pageNo": page
				};
			}
			options.valobj = valobj;
			$.ajax({
				type: "get",
				url: options.ajaxUrl,
				data: valobj,
				datatype: "json",
				async: true,
				success: function(data) {
					var ipage = data.ipage;
					console.log(data);
					options.valobj.pageTotal=ipage.pageSum;
					popsparm.find("#pop-list-iterms").empty();
					popsparm.find("#pop-list-iterms").append(methods.getItemsHtml(ipage.result, options));
//					popsparm.find('.M-box').pagination({
//						totalData: ipage.pageCounts,
//						showData: ipage.pageSize,
//						coping: true,
//						callback: function(api) {
//							methods.showLoading(options);
//							methods.nextPage(options, api.getCurrent());
//						}
//					});
					methods.showPageDiv(options,ipage);
					
					//瀑布式加载
					popsparm.find(".pops-list-body").mCustomScrollbar({
						callbacks:{
							onTotalScrollOffset:20,
							scrollInertia:200,//滚动延时
							onTotalScroll:function(){
								if((options.valobj.pageNo+1)<=options.valobj.pageTotal){
									methods.showLoading(options);
									methods.nextPage(options,options.valobj.pageNo+1);
								}
							}
						}
					});
					
					methods.hideLoading(options);
				},
				error: function() {
					console.log("数据源加载失败");
				}
			});
		},
		nextPage: function(options, nextpage) {
			console.log(nextpage);
			var valobj = options.valobj;
			valobj.pageNo = nextpage;
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			$.ajax({
				type: "get",
				url: options.ajaxUrl,
				data: valobj,
				datatype: "json",
				async: true,
				success: function(data) {
					var ipage = data.ipage;
					console.log(data);
					options.valobj.pageTotal=ipage.pageSum;
				//	popsparm.find("#pop-list-iterms").empty();
					popsparm.find("#pop-list-iterms").append(methods.getItemsHtml(ipage.result, options));
					methods.showPageDiv(options,ipage);
					methods.showTableHeadDiv(options);
					methods.hideLoading(options);
				},
				error: function() {
					console.log("数据源加载失败");
				}
			});
		},
		showPageDiv:function(options,ipage){
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			var pagebox = popsparm.find(".M-box");
			pagebox.html("当前显示"+ipage.endNum+"条数据，一共"+ipage.pageCounts+"条数据");
		},
		showTableHeadDiv:function(options){
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			var listBody = popsparm.find(".pops-list-body");
			listBody.find(".pops-tablehead").remove();
			var $head = $('<table class="pops-tablehead"></table>');
			var $table = listBody.find(".table");
			var $thead = listBody.find(".table thead th");
			var W = $table.outerWidth(true);
			var $tr = $("<tr></tr>");
			var totalWidth = 0;
			$thead.each(function(i) {
					var tW = $(this).outerWidth(true);
					var tH = $(this).outerHeight(true);
					var $th = $("<th class='table-float-th'></th>");
						$th.html($(this).html());
					var $thcss = {};
					var align = $(this).attr("align");
		        	totalWidth += tW;
		        	if (totalWidth > W - 2) {
		        		tW = tW - (totalWidth - W + 2);
					}
		        	 $thcss = {
							"width" : tW,
							"height" : tH,
							"line-height" : tH + "px",
							"text-align":align,
							"white-space":"nowrap"
						};
					$th.css($thcss);
					$th.appendTo($tr);
					$tr.appendTo($head);
					$th=null;
			});
			$head.appendTo(listBody);
            if(options.allCheckShow){
                listBody.find(".pops-tablehead .all-check").bind("click", function() {
                    methods.checkAllBox(options, elem);
                });
                methods.checkAll(options);
            }
		},
		getItemsHtml: function(data, options) {
			var tablehead = options.tablehead;
			var returnData = options.returnData;
			var html = [];
			var itemsHtml;
			$.each(data, function(i, node) {
				options.queryData[node[returnData.value]]=node;
				html.push('<tr>');
				if(options.multiple) {
					html.push('<td align="center"><input class="list-checkbox" type="checkbox" value="' + node[returnData.value] + '" disName="' + node[returnData.disName] + '" ></td>');
				}
				$.each(tablehead, function(k, v) {
					if(typeof(v) == "string") {
						var tmpVal=node[k]==null?"":node[k];//处理页面显示null的问题  20180620 pengchong
						html.push('<td>' + tmpVal + '</td>');//html.push('<td>' + node[k] + '</td>');
					} else if(typeof(v) == "object") {
						html.push('<td');
						if(v.hasOwnProperty("align")) {
							html.push(' align="' + v.align + '" ');
						}
						if(v.hasOwnProperty("width")) {
							html.push(' width="' + v.width + '" ');
						}
						if(v.hasOwnProperty("dataType")){
							if(v.dataType=="money"){
                                html.push('>' + fmoney(node[k], 2) + '</td>');
                            }else{
                                var tmpVal=node[k]==null?"":node[k];//处理页面显示null的问题
                                html.push('>' + tmpVal + '</td>');
							}
						}else{
                            var tmpVal=node[k]==null?"":node[k];//处理页面显示null的问题
                            html.push('>' + tmpVal + '</td>');
						}

					}
				});
				if(!options.multiple) {
					html.push('<td align="center"><a class="list-setval" href="javascript:void(0);" value="' + node[returnData.value] + '" disName="' + node[returnData.disName] + '">选择</a></td>');
				}
				html.push('</tr>');
			});
			itemsHtml = $(html.join(" "));
			if(options.multiple) {
				itemsHtml.find(".list-checkbox").bind("click", function() {
					methods.setChooseValue(options, $(this));
				});
			} else {
				itemsHtml.find(".list-setval").bind("click", function() {
					methods.setSelectValue(options, $(this));
				});
			}

			return itemsHtml;
		},
		setSelectValue: function(options, node) { //赋值
			var elem = options.elem;
			var popsvalue = elem.data("popsvalue");
			var popsparm = elem.data("popsparm");
			var changeCallback = options.changeCallback;
			popsvalue.html($(node).attr("disName"));
			elem.data("selectData", options.queryData[$(node).attr("value")]);
			elem.val($(node).attr("disName"));
			elem.data("text", $(node).attr("disName"));
			elem.data("values").val($(node).attr("value"));
			$(node).parents().find(".pops-bg").hide();
			methods.hideList(elem);
			if(!options.initFlag) {
				if(changeCallback && typeof(changeCallback) == "function") {
					changeCallback.call(this, elem);
				}
			}
			if(popsvalue!=''){//选择内容后，删除不能为空的提示
				if(popsvalue.parent().find('.error.required').length>0){
					popsvalue.parent().find('.error.required').remove();
				}
			}
		},
		setChooseValue: function(options, node) { //设置pop选中值
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			if(options.multiple) {
				if(node.prop("checked")) {
					if(popsparm.find(".pops-list-values .pops-label[data-id=" + node.val() + "]").length > 0) return false;
					var label = $(methods.getMultipleHtml(node, options));
					label.appendTo(popsparm.find(".pops-list-values"));
					methods.setLabelEvent(label, options);
					if(options.allCheckShow){
                        methods.checkAll(options);
					}
				} else {
					methods.clearOneValue(options, node);
					if(options.allCheckShow){
                    	popsparm.find(".pops-tablehead .all-check").prop("checked", false);
					}
				}
			}
		},
		setLabelEvent: function(label, options) { //label绑定事件
			label.find(".pops-close").bind("click", function() {
				var popsparm = options.elem.data("popsparm");
				var dataId = $(this).parent().data("id");
				popsparm.find(".list-checkbox[value=" + dataId + "]").prop("checked", false);
				popsparm.find(".pops-label[data-id=" + dataId + "]").remove();
				$(this).parent().remove();
				return false;
			});
			label.find(".pops-edit").bind("click", function() {
				options.labelEdit.call(this, $(this).parent().data("id"));
				return false;
			});
		},
		setValueLabelEvent: function(label, options) { //label绑定事件
			label.find(".pops-close").bind("click", function() {
				var popsparm = options.elem.data("popsparm");
				var dataId = $(this).parent().data("id");
				popsparm.find(".list-checkbox[value=" + dataId + "]").prop("checked", false);
				popsparm.find(".pops-label[data-id=" + dataId + "]").remove();
				$(this).parent().remove();
				methods.confirmValue(options, options.elem);
				return false;
			});
			label.find(".pops-edit").bind("click", function() {
				options.labelEdit.call(this, $(this).parent().data("id"));
				return false;
			});
		},
		confirmValue: function(options, elem) { //确认pop选择值
			var popsvalue = elem.data("popsvalue");
			var popsparm = elem.data("popsparm");
			popsvalue.empty();
			var tmpRes={};
			var valueFlag = false;
			popsparm.find(".pops-label").each(function(i, node) {
				tmpRes[$(node).attr("data-id")]=options.queryData[$(node).attr("data-id")];
				var label = $(methods.getValueMultipleHtml(node, options));
				label.appendTo(popsvalue);
				methods.setValueLabelEvent(label, options);
				valueFlag = true;
			});
			if(valueFlag){//选择内容后，删除不能为空的提示
				if(popsvalue.parent().find('.error.required').length>0){
					popsvalue.parent().find('.error.required').remove();
				}
			}
			elem.data("selectData", tmpRes);
			methods.hideList(elem);
			methods.setElemValue(options, elem);
		},
		clearOneValue: function(options, node) { //清除一个选项
			var popsparm = options.elem.data("popsparm");
			popsparm.find(".pops-label[data-id=" + node.val() + "]").remove();
		},
		setElemValue: function(options, elem) { //对象隐藏域赋值
			var popsvalue = elem.data("popsvalue");
			var val = "";
			var str = "";
			var changeCallback = options.changeCallback;
			popsvalue.find(".pops-label").each(function(i, node) {
				if(options.multiple) {
					val += $(node).data("id") + options.splitStr ;
					str += $(node).find(".pops-label-alt").text().replace(/(^\s*)|(\s*$)/g,'') + options.splitStr;
				} else {
					val = $(node).data("id");
					str = $(node).find(".pops-label-alt").text();
				}
			});
			//去掉最后一个|-zhs 20170823
			if(val.charAt(val.length-1) == options.splitStr){
				val = val.substring(0, val.length-1);
				str = str.substring(0, str.length-1);
			}
			elem.val(str);
			elem.data("values").val(val);
			if(!options.initFlag) {
				if(changeCallback && typeof(changeCallback) == "function") {
					changeCallback.call(this, elem);
				}
			}
		},
		getMultipleHtml: function(node, options) { //构建选中选择项label
			var html = [];
			if(options.labelShow) {
				html.push('<div class="pops-label" data-id="' + node.val() + '">');
			} else {
				html.push('<div class="pops-label off" data-id="' + node.val() + '">');
			}
			html.push('<div class="pops-label-alt">');
			html.push(node.attr("disName"));
			html.push('</div>');
			html.push('	<i class="pops-close"></i>');
			if(options.labelEdit && typeof(options.labelEdit) == "function") {
				html.push('	<i class="pops-edit"></i>');
			}
			html.push('</div>');
			return html.join(" ");
		},
		getValueMultipleHtml: function(node, options) { //构建选中选择项label
			var html = [];
			html.push('<div class="pops-label" data-id="' + $(node).attr("data-id") + '">');
			html.push('<div class="pops-label-alt">');
			html.push($(node).find(".pops-label-alt").text());
			html.push('</div>');
			html.push('	<i class="pops-close"></i>');
			if(options.labelEdit && typeof(options.labelEdit) == "function") {
				html.push('	<i class="pops-edit"></i>');
			}
			html.push('</div>');
			return html.join(" ");
		},
		showLoading: function(options) {
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			popsparm.find(".pops-list-loading").show();
		},
		hideLoading: function(options) {
			var elem = options.elem;
			var popsparm = elem.data("popsparm");
			popsparm.find(".pops-list-loading").fadeOut();
		},
		searchItems: function(options, kw) { //搜索
			methods.getItems(options, kw);
		},
		hideList: function(elem) { //隐藏选择区域
			var popsparm = elem.data("popsparm");
			popsparm.hide();
			//	methods.searchItems(elem.data("options"));
		},
		getSearchHtml: function(options) { //获取搜索html结构
			var html = [];
			html.push('<div class="pops-search">');
			if(''!=options.searchplaceholder&&null!=options.searchplaceholder&&undefined!=options.searchplaceholder){
                html.push('	<input type="text" class="pops-search-input"  placeholder="'+options.searchplaceholder+'"/>');
			}else{
                html.push('	<input type="text" class="pops-search-input"  placeholder="输入关键字"/>');
			}
			html.push('	<i class="i i-fangdajing pops-search-btn"></i>');
			html.push('</div>');
			return html.join(" ");
		},
		getAddBtnHtml:function(){//获取按钮html结构
			var html = [];
			html.push('<div class="pops-list-addbtn">');
			html.push('	<button type="button" class="btn btn-primary pops-list-addbtn-btn"></button>');
			html.push('</div>');
			return html.join(" ");
		},
		getListHtml: function(options) { //获取html结构
			var html = [];
            html.push('<div class="pops-bg '+options.bgClass+'">');
			html.push('<div class="pops-list">');
			html.push('	<div class="pops-list-head">');
			if(options.title) {
				html.push('	<span class="pops-list-title">' + options.title + '</span>');
			}
			html.push('	<i class="pops-close"></i>');
			html.push('	</div>');
			html.push('	<div class="pops-list-search">');
			if(options.multiple) {
				html.push('	<div class="pops-list-values">');
				html.push('	<button type="button" class="pops-btn-confirm">确认</button>');
				html.push('	</div>');
			}
			html.push('	</div>');
			html.push('	<div class="pops-list-body">');
			html.push('	</div>');
			html.push('	<div class="pops-list-foot">');
			
			html.push('	<div class="M-box"></div>');
			
			html.push('	</div>');
			
			/*
			 * 改为factor中的加载动画。 by LiuYF 2070617
			 */
			// html.push('	<div class="pops-list-loading"><img src="'+webPath+'/themes/factor/images/loadingLogo.gif" style="position: absolute;left:45%;top:40%;"></div>');
			html.push('</div>');
			html.push('</div>');
			return html.join(" ");
		},
		getListTableHtml: function(options) { //获取html结构
			var thobj = options.tablehead;
			var html = [];
			html.push('<table class="table" width="95%">');
			html.push('<thead><tr>');
			if(options.multiple) {
				if(options.allCheckShow){
					html.push('<th align="center"><input class="all-check" type="checkbox">&nbsp;&nbsp;选择</th>');
				}else{
                    html.push('<th align="center">选择</th>');
				}

			}
			$.each(thobj, function(k, v) {
				if(typeof(v) == "string") {
					html.push('<th th-name="' + k + '">' + v + '</th>');
				} else if(typeof(v) == "object") {
					html.push('<th th-name="' + k + '"');
					if(v.hasOwnProperty("width")) {
						html.push(' width="' + v.width + '" ');
					}
					html.push('>' + v.disName + '</th>');
				}
			})
			if(!options.multiple) {
				html.push('<th>操作</th>');
			}
			html.push('</thead></tr>');
			html.push('<tbody id="pop-list-iterms" ></tbody>');
			html.push('</table>');
			return html.join(" ");
		},
		showList: function(obj, elem) { //显示选择区域
			var popsparm = elem.data("popsparm");
			$(".pops-list").hide();
			var pop = popsparm.find(".pops-list");
			$(".pops-list").css("z-index", "1");
			pop.css("z-index", "19911027");
			$(obj).parents().find(".pops-list").show();
			popsparm.show();
            $('#loadingAnimateModal').remove();
		},
        checkAllBox: function(options, elem){
            var checkAllFlag = true;//全选标志
            var popsparm = elem.data("popsparm");
            popsparm.find('.pops-tablehead').find('.all-check').each(function () {
                if(!$(this).prop("checked")){
                    checkAllFlag = false;
                    return false;
                }
            });
            if(checkAllFlag){
                popsparm.find('.list-checkbox').each(function () {
                    $(this).prop("checked",true);
                    methods.setChooseValue(options, $(this));
                });
            }else{
                popsparm.find('.list-checkbox').each(function () {
                    $(this).prop("checked",false);
                    methods.setChooseValue(options, $(this));
                });
            }
        },
        checkAll:function (options) {
			var elem = options.elem;
            var popsparm = elem.data("popsparm");
            var checkAllFlag = true;//全选标志
            popsparm.find('.list-checkbox').each(function () {
                if(!$(this).prop("checked")){
                    checkAllFlag = false;
                    return false;
                }
            });
            if(popsparm.find('.list-checkbox').length==0){
                checkAllFlag = false;
			}
            if(checkAllFlag){
                popsparm.find('.pops-tablehead').find('.all-check').prop("checked",true);
			}else{
                popsparm.find('.pops-tablehead').find('.all-check').prop("checked",false);
			}
        }
	}
	$.fn.popupList = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.popupList');
		}
	};
})(jQuery, window);
/**
 * pagination分页插件
 * @version 1.3.1
 * @调用方法
 * $(selector).pagination();
 */
;(function (factory) {
    if (typeof define === "function" && (define.amd || define.cmd) && !jQuery) {
        // AMD或CMD
        define([ "jquery" ],factory);
    } else if (typeof module === 'object' && module.exports) {
        // Node/CommonJS
        module.exports = function( root, jQuery ) {
            if ( jQuery === undefined ) {
                if ( typeof window !== 'undefined' ) {
                    jQuery = require('jquery');
                } else {
                    jQuery = require('jquery')(root);
                }
            }
            factory(jQuery);
            return jQuery;
        };
    } else {
        //Browser globals
        factory(jQuery);
    }
}(function ($) {

	//配置参数
	var defaults = {
		totalData: 0,			//数据总条数
		showData: 0,			//每页显示的条数
		pageCount: 9,			//总页数,默认为9
		current: 1,				//当前第几页
		prevCls: 'prev',		//上一页class
		nextCls: 'next',		//下一页class
		prevContent: '<',		//上一页内容
		nextContent: '>',		//下一页内容
		activeCls: 'active',	//当前页选中状态
		coping: false,			//首页和尾页
		isHide: false,			//当前页数为0页或者1页时不显示分页
		homePage: '',			//首页节点内容
		endPage: '',			//尾页节点内容
		keepShowPN: false,		//是否一直显示上一页下一页
		count: 3,				//当前页前后分页个数
		jump: false,			//跳转到指定页数
		jumpIptCls: 'jump-ipt',	//文本框内容
		jumpBtnCls: 'jump-btn',	//跳转按钮
		jumpBtn: '跳转',		//跳转按钮文本
		callback: function(){}	//回调
	};

	var Pagination = function(element,options){
		//全局变量
		var opts = options,//配置
			current,//当前页
			$document = $(document),
			$obj = $(element);//容器

		/**
		 * 设置总页数
		 * @param int page 页码
		 * @return opts.pageCount 总页数配置
		 */
		this.setPageCount = function(page){
			return opts.pageCount = page;
		};

		/**
		 * 获取总页数
		 * 如果配置了总条数和每页显示条数，将会自动计算总页数并略过总页数配置，反之
		 * @return int p 总页数
		 */
		this.getPageCount = function(){
			return opts.totalData || opts.showData ? Math.ceil(parseInt(opts.totalData) / opts.showData) : opts.pageCount;
		};

		/**
		 * 获取当前页
		 * @return int current 当前第几页
		 */
		this.getCurrent = function(){
			return current;
		};

		/**
		 * 填充数据
		 * @param int index 页码
		 */
		this.filling = function(index){
			var html = '';
			current = index || opts.current;//当前页码
			var pageCount = this.getPageCount();//获取的总页数
			if(opts.keepShowPN || current > 1){//上一页
				html += '<a href="javascript:;" class="'+opts.prevCls+'">'+opts.prevContent+'</a>';
			}else{
				if(opts.keepShowPN == false){
					$obj.find('.'+opts.prevCls) && $obj.find('.'+opts.prevCls).remove();
				}
			}
			if(current >= opts.count + 2 && current != 1 && pageCount != opts.count){
				var home = opts.coping && opts.homePage ? opts.homePage : '1';
				html += opts.coping ? '<a href="javascript:;" data-page="1">'+home+'</a><span>...</span>' : '';
			}
			var end = current + opts.count;
			var start = '';
			//修复到最后一页时比第一页少显示两页
			start = current === pageCount ? current - opts.count - 2 : current - opts.count;
			((start > 1 && current < opts.count) || current == 1) && end++;
			(current > pageCount - opts.count && current >= pageCount) && start++;
			for (;start <= end; start++) {
				if(start <= pageCount && start >= 1){
					if(start != current){
						html += '<a href="javascript:;" data-page="'+start+'">'+ start +'</a>';
					}else{
						html += '<span class="'+opts.activeCls+'">'+start+'</span>';
					}
				}
			}
			if(current + opts.count < pageCount && current >= 1 && pageCount > opts.count){
				end = opts.coping && opts.endPage ? opts.endPage : pageCount;
				html += opts.coping ? '<span>...</span><a href="javascript:;" data-page="'+pageCount+'">'+end+'</a>' : '';
			}
			if(opts.keepShowPN || current < pageCount){//下一页
				html += '<a href="javascript:;" class="'+opts.nextCls+'">'+opts.nextContent+'</a>'
			}else{
				if(opts.keepShowPN == false){
					$obj.find('.'+opts.nextCls) && $obj.find('.'+opts.nextCls).remove();
				}
			}
			html += opts.jump ? '<input type="text" class="'+opts.jumpIptCls+'"><a href="javascript:;" class="'+opts.jumpBtnCls+'">'+opts.jumpBtn+'</a>' : '';
			$obj.empty().html(html);
		};

		//绑定事件
		this.eventBind = function(){
			var that = this;
			var pageCount = that.getPageCount();//总页数
			var index = 1;
			$obj.off().on('click','a',function(){
				if($(this).hasClass(opts.nextCls)){
					if($obj.find('.'+opts.activeCls).text() >= pageCount){
						$(this).addClass('disabled');
						return false;
					}else{
						index = parseInt($obj.find('.'+opts.activeCls).text()) + 1;
					}
				}else if($(this).hasClass(opts.prevCls)){
					if($obj.find('.'+opts.activeCls).text() <= 1){
						$(this).addClass('disabled');
						return false;
					}else{
						index = parseInt($obj.find('.'+opts.activeCls).text()) - 1;
					}
				}else if($(this).hasClass(opts.jumpBtnCls)){
					if($obj.find('.'+opts.jumpIptCls).val() !== ''){
						index = parseInt($obj.find('.'+opts.jumpIptCls).val());
					}else{
						return;
					}
				}else{
					index = parseInt($(this).data('page'));
				}
				that.filling(index);
				typeof opts.callback === 'function' && opts.callback(that);
			});
			//输入跳转的页码
			$obj.on('input propertychange','.'+opts.jumpIptCls,function(){
				var $this = $(this);
				var val = $this.val();
				var reg = /[^\d]/g;
	            if (reg.test(val)) {
	                $this.val(val.replace(reg, ''));
	            }
	            (parseInt(val) > pageCount) && $this.val(pageCount);
	            if(parseInt(val) === 0){//最小值为1
	            	$this.val(1);
	            }
			});
			//回车跳转指定页码
			$document.keydown(function(e){
		        if(e.keyCode == 13 && $obj.find('.'+opts.jumpIptCls).val()){
		        	var index = parseInt($obj.find('.'+opts.jumpIptCls).val());
		            that.filling(index);
					typeof opts.callback === 'function' && opts.callback(that);
		        }
		    });
		};

		//初始化
		this.init = function(){
			this.filling(opts.current);
			this.eventBind();
			if(opts.isHide && this.getPageCount() == '1' || this.getPageCount() == '0') $obj.hide();
		};
		this.init();
	};

	$.fn.pagination = function(parameter,callback){
		if(typeof parameter == 'function'){//重载
			callback = parameter;
			parameter = {};
		}else{
			parameter = parameter || {};
			callback = callback || function(){};
		}
		var options = $.extend({},defaults,parameter);
		return this.each(function(){
			var pagination = new Pagination(this, options);
			callback(pagination);
		});
	};

}));