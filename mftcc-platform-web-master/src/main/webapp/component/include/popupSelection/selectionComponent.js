(function($, win) {
	var methods = {
		init: function(optionsArgs) { //初始化
			var defaults = {
				ajaxUrl: false, //异步url
				valueClass: false, //String类型，自定义显示值class
				selectClass: false, //String类型，自定义选框class
				addBtn: false, //false-不启用新增按钮,{"title":"按钮名称","fun":function(){//点击执行}}
				items: [], //[{id:"1",name:"A"},{...}]
				changeCallback: false, //回调函数
				width: false, //自定义宽度
				height: 300, //自定义高度
				addSelected: true, //新增默认被选中
				pageFlag: false,
				ajaxData: false, //后台数据接收对象调整
				autoSelected: false //默认选中第一个搜索项
				//labelEdit: false, //选项编辑回调函数function(d){console.log(d)};
				//handle: false, //触发器
				//initFlag: true,
				//multiple: false, //false-单选,true-复选
				//splitStr: ",", //分隔符
			}
			var options = $.extend(defaults, optionsArgs);
			return this.each(function() {

				var $this = $(this);
				options.elem = $this;
				$this.data("options", options);
                var celem;
				if(options.pageFlag) {
					celem = $this.clone();
					$this.data("celem", celem);
					celem.removeAttr("id");
					celem.removeAttr("name");
					methods.bulidSelectDiv($this);
				} else {
					if(options.ajaxUrl && typeof(options.ajaxUrl) == "string") {
						$.ajax({
							type: "get",
							url: options.ajaxUrl,
							datatype: "json",
							async: true,
							success: function(data) {
								if(typeof(data) == "string") {
									options.items = JSON.parse(data.items);
								} else {
									options.items = data.items;
								}
								var celem = $('<input type="text">');
								$this.data("celem", celem);
								methods.bulidSelectDiv($this);
								methods.itemsToJson($this);
								methods.setInitValue($this);
								methods.initParm($this);
							},
							error: function() {
								console.log("数据源加载失败");
							}
						});
					} else {
						if($this.is('input')) {
							if(!$.isArray(options.items) || options.items.length == 0) {
								console.log("参数items不是数组或者为空！")
								return false;
							}
							celem = $this.clone();
							$this.data("celem", celem);
							celem.removeAttr("id");
							celem.removeAttr("name");
						} else if($this.is('select')) {
							if(!$.isArray(options.items) || options.items.length == 0) {
								options.items = methods.getItemsBySelect($this);
							}
							celem = $('<input type="text">');
							$this.data("celem", celem);
						}
						methods.bulidSelectDiv($this);
						methods.itemsToJson($this);
						methods.setInitValue($this);
					}
				}
			});
		},
		bulidSelectDiv: function(elem) {
			var html = $(methods.getSelectDomainHtml(elem));
			var celem = elem.data("celem");
			celem.addClass("sc-clone");
			celem.attr("placeholder", "请输入关键字...");
			var options = elem.data("options");
			elem.hide();
			elem.data("scdomain", html);
			celem.data("scdomain", html);
			celem.data("elem", elem);
			elem.data("celem", celem);
			html.insertAfter(elem);
			if(options.valueClass) {
				celem.addClass(options.valueClass);
			}
			html.data("elem", elem);
			celem.insertAfter(elem);
			if(options.width) {
				html.css("width", options.width + "px");
			} else {
				html.css("width", celem.outerWidth() + "px");
			}
			html.css("max-height", options.height + "px");
			var addbtn = html.find(".sc-add-btn");
			var h = 0;
			if(addbtn.length > 0) {
				h = addbtn.height();
			}
			var items = $(methods.getInlineItemsHtml(elem));
			html.find(".sc-body").append(items);
			html.find(".sc-body").css("height", (options.height - h) + "px");
			if(options.selectClass) {
				html.addClass(options.selectClass);
			}
			methods.initEvent(elem);
		},
		getSelectDomainHtml: function(elem) { //获取搜索html结构
			var options = elem.data("options");
			var html = [];
			html.push('<div class="sc-domain">');
			html.push('<div class="sc-body">');
			html.push('</div>');
			if(options.addBtn && !$.isEmptyObject(options.addBtn)) {
				if(options.addBtn.title) {
					html.push('	<button type="button" class="sc-add-btn">' + options.addBtn.title + '</button>');
				} else {
					html.push('	<button type="button" class="sc-add-btn"><i class="cross"></i>新增</button>');
				}
			}

			html.push('</div>');
			return html.join(" ");
		},
		getInlineItemsHtml: function(elem) { //获取内联选择项html结构
			var options = elem.data("options");
			var items = options.items
			var html = [];
			html.push('	<ul class="sc-items">');
			$.each(items, function(i, node) {
				html.push('	<li  class="sc-item" data-id="' + node.id + '">');
				if(typeof(node.id) != "undefined" && typeof(node.name) != "undefined") {
					html.push('<span title="' + node.name + '">' + node.name + '</span>');
				} else {
					html.push('<span title="' + node.id + '">' + node.id + '</span>');
				}
				html.push('	</li>');
			});
			html.push('	</ul>');
			return html.join(" ");
		},
		getItemsBySelect: function(elem) { //从select获取items
			var items = [];
			elem.find("option").each(function(i, node) {
				if($(node).val()) {
					var obj = {};
					obj.id = $(node).val();
					obj.name = $(node).text();
					items.push(obj);
				}
			});
			return items;
		},
		initEvent: function(elem) {
			var m = methods;
			var s = elem.data("scdomain");
			var options = elem.data("options");
			var celem = elem.data("celem");
			if(options.pageFlag) {
				m.lastTimeStamp = 0;
				celem.bind("keyup", function(e) {
					if(e.keyCode == 40 || e.keyCode == 38 || e.keyCode == 13) {
						m.triggerKeyboardEven(elem, e.keyCode);
					} else {
						//标记当前事件函数的时间戳
						lastTimeStamp = e.timeStamp;
						m.showLoadingDiv(elem);
						setTimeout(function() {
							if(lastTimeStamp == e.timeStamp) {
								m.searchItemsForAjax(elem, celem.val());
							}
						}, 800);
					}
				});
			} else {
				celem.bind("keyup", function(e) {
					if(e.keyCode == 40 || e.keyCode == 38 || e.keyCode == 13) {
						m.triggerKeyboardEven(elem, e.keyCode);
					} else {
						m.searchItems(elem, $(this).val());
					}
				});
				celem.bind("focus", function() {
					m.searchItems(elem, $(this).val());
				})
			}

			s.find(".sc-item").on("click", function() {
				m.setValue(elem, $(this));
			});
			if(options.addBtn && !$.isEmptyObject(options.addBtn)) {
				if(options.addBtn.fun && typeof(options.addBtn.fun) == "function") {
					s.find(".sc-add-btn").bind("click", function() {
						options.addBtn.fun.call(this, elem);
					})
				}
			}
		},
		initParm: function(elem) {
			var m = methods;
			var options = elem.data("options");
		},
		triggerKeyboardEven: function(elem, keyCode) {
			var m = methods;
			var s = elem.data("scdomain");
			var celem = elem.data("celem");
			if(s.css("display") == 'none') {
				//m.searchItems(elem,celem.val());
				s.show();
				return false;
			}
			var body = s.find(".sc-body");
			var hh = body.height();
			var curr = s.find(".sc-item.selected");
			var items = s.find(".sc-item.show");
			var index = $.inArray(curr[0], items);
            var top;
			switch(keyCode) {
				case 13: //回车
					methods.setValue(elem, curr);
					break;
				case 38: //上
					if(index > 0) {
						$(items[index - 1]).addClass("selected").siblings().removeClass("selected");
						top = $(items[index - 1]).position().top;
						if(top < 0) {
							body.animate({
								scrollTop: body.scrollTop() + top
							}, 100);
						}
					}
					break;
				case 40: //下
					if(index < (items.length - 1)) {
						$(items[index + 1]).addClass("selected").siblings().removeClass("selected");
						top = $(items[index + 1]).position().top + $(items[index + 1]).outerHeight(true);
						if(top > hh) {
							body.animate({
								scrollTop: body.scrollTop() + (top - hh)
							}, 100);
						}

					}
					break;
				default:
					break;
			}
		},
		searchItems: function(elem, kw) {
			var m = methods;
			var s = elem.data("scdomain");
			var o = elem.data("options");
			var count = 0;
			if(kw) {
				s.find(".sc-item").hide().removeClass("show");
				s.find(".sc-item span").each(function() {
					if($(this).attr("title").indexOf(kw) > -1) {
						if(o.autoSelected) {
							m.setTempValue(elem, $(this).parent());
						}
						var text = $(this).attr("title");
						text = text.replace(kw, "<b style='color:red;'>" + kw + "</b>");
						$(this).html(text);
						$(this).parent().show().addClass("show");
						if($(this).parent().data("id") == elem.val()) {
							$(this).parent().addClass("selected").siblings().removeClass("selected");
						}
						count++;
					}
				});
			} else {
				s.find(".sc-item span").each(function() {
					var text = $(this).attr("title");
					$(this).html(text);
				});
				s.find(".sc-item").show().addClass("show");
				count = 1027
			}
			if(count == 0 || count == 1027) {
				m.clearTempValue(elem);
			}
			m.showSelectDiv(elem, count);
		},
		searchItemsForAjax: function(elem, kw) {
			var m = methods;
			var s = elem.data("scdomain");
			var o = elem.data("options");
			var count = 1027;
			var ajaxData = o.ajaxData;
			var valobj;
			if(ajaxData && typeof(ajaxData) == "function") {
				valobj = ajaxData.call(this, kw);
			} else {
				valobj = {
					q: kw
				}
			}
			$.ajax({
				type: "get",
				url: o.ajaxUrl,
				data: valobj,
				datatype: "json",
				async: true,
				success: function(data) {
					m.createItemsForAjax(elem, data.items);
				},
				error: function() {
					console.log("数据源加载失败");
				},
				complete: function() {
					m.hideLoadingDiv(elem);
				}
			});
			m.showSelectDiv(elem, count);
		},
		createItemsForAjax: function(elem, items) {
			var domain = elem.data("scdomain");
			var html = [];
			var options = elem.data("options");
			var tempMap = {};
			$.each(items, function(i, node) {
				tempMap[node.id] = node;
				if (elem.val()&&elem.val()==node.id) {
					html.push('<li  class="sc-item show selected" data-id="' + node.id + '">');
				}else{
					html.push('<li  class="sc-item show" data-id="' + node.id + '">');
				}
				if(typeof(node.id) != "undefined" && typeof(node.name) != "undefined") {
					html.push('<span title="' + node.name + '">' + node.name + '(' + node.id + ')</span>');
				} else {
					html.push('<span title="' + node.id + '">' + node.id + '(' + node.id + ')</span>');
				}
				html.push('</li>');
			});
			options.itemMap = tempMap;
			var $html = $(html.join(" "));
			$html.prependTo(domain.find(".sc-items").empty());
			if (domain.find(".selected").length==0) {
				$(domain.find(".sc-item ")[0]).addClass("selected")
			}
			$html.bind("click", function() {
				methods.setValue(elem, $(this));
			})
		},
		showLoadingDiv: function(elem) {
			var domain = elem.data("scdomain");
			var body = domain.find(".sc-body");
			if(body.find(".sc-loading").length > 0) {
				body.find(".sc-loading").show();
			} else {
				body.append('<div class="sc-loading"></div>');
			}
		},
		hideLoadingDiv: function(elem) {
			var domain = elem.data("scdomain");
			var body = domain.find(".sc-body");
			if(body.find(".sc-loading").length > 0) {
				body.find(".sc-loading").hide();
			}
		},
		showSelectDiv: function(elem, count) {
			var s = elem.data("scdomain");
			var options = elem.data("options");
			var addbtn = s.find(".sc-add-btn");
			var maxh = options.height;
			if(addbtn.length > 0) {
				maxh = options.height - addbtn.height();
			}
			if(count == "auto") {
				count = 0;
				s.find(".sc-item").each(function() {
					if($(this).css("display") != 'none') {
						count++;
					}
				});
			}

			if(count == 1027) {
				s.find(".sc-body").css("height", (maxh) + "px");
			} else {
				if(count * 30 > maxh) {
					s.find(".sc-body").css("height", (maxh) + "px");
				} else {
					s.find(".sc-body").css("height", (count * 30) + "px");
				}
			}
			s.show();
		},
		hideSelectDiv: function(elem) {
			var s = elem.data("scdomain");
			s.hide();
		},
		addItem: function(node) {
			var elem = $(this);
			var domain = elem.data("scdomain");
			var options = elem.data("options");
			var itemMap = options.itemMap;
			if(itemMap[node.id]) {
				alert("已存在相同值的选项！");
				return false;
			}

			itemMap[node.id] = node;
			var html = [];
			html.push('	<li  class="sc-item" data-id="' + node.id + '">');
			if(typeof(node.id) != "undefined" && typeof(node.name) != "undefined") {
				html.push('<span title="' + node.name + '">' + node.name + '</span>');
			} else {
				html.push('<span title="' + node.id + '">' + node.id + '</span>');
			}
			html.push('	</li>');
			html.join(" ");
			var $html = $(html.join(" "));
			$html.prependTo(domain.find(".sc-items"));
			$html.bind("click", function() {
				methods.setValue(elem, $(this));
			})
			if(options.addSelected) {
				methods.setValue(elem, $html);
			}
		},
		setValue: function(elem, obj) {
			var id = obj.data("id");
			obj.addClass("selected").siblings().removeClass("selected");
			var options = elem.data("options");
			var itemMap = options.itemMap;
			var celem = elem.data("celem");
			elem.val(id);
			elem.data("currVal", id);
			celem.val(itemMap[id].name);
			methods.hideSelectDiv(elem);
			elem.data("tempVal", obj);
			if(typeof(options.changeCallback) == "function") {
				options.changeCallback.call(this, itemMap[id]);
			}
		},
		setSearchVal: function(elem) {
			var obj = elem.data("tempVal");
			var celem = elem.data("celem");
			if(obj) {
				var id = obj.data("id");
				obj.addClass("selected").siblings().removeClass("selected");
				var options = elem.data("options");
				var itemMap = options.itemMap;
				//var celem = elem.data("celem");
				elem.val(id);
				elem.data("currVal", id);
				celem.val(itemMap[id].name);
				elem.data("tempVal", obj);

			} else {
				elem.val("");
				celem.val("");
			}
		},
		setTempValue: function(elem, obj) {
			var id = obj.data("id");
			obj.addClass("selected").siblings().removeClass("selected");
			elem.data("tempVal", obj);
		},
		clearTempValue: function(elem) {
			elem.data("tempVal", "");
		},
		setInitValue: function(elem) {
			var options = elem.data("options");
			var celem = elem.data("celem");
			var s = elem.data("scdomain");
			var itemMap = options.itemMap;
			var selectedItem = s.find(".sc-item[data-id=" + elem.val() + "]");
			elem.data("tempVal", selectedItem);
			selectedItem.addClass("selected");
			if(elem.val()) {
				celem.val(itemMap[elem.val()].name);
			}

		},
		itemsToJson: function(elem) { //数组转json
			var options = elem.data("options");
			var tempMap = {};
			var items = options.items;
			$.each(items, function(i, node) {
				tempMap[node.id] = node;
			});
			options.itemMap = tempMap;
		}
	};
	$.fn.sc = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.selectionComponent');
		}
	};
	$(win).bind("click", function(e) {
		$(".sc-domain").each(function() {
			var _this = $(this);
			if(_this.css("display") != 'none') {
				methods.setSearchVal(_this.data("elem"));
			}
		})

		$(".sc-domain").hide();
		if($(e.target).data("scdomain")) {
			methods.showSelectDiv($(e.target).data("elem"), "auto");
		}
	});
})(jQuery, window);
var popupSelectionJsPath = document.scripts[document.scripts.length - 1].src;
var popupSelectionPath = popupSelectionJsPath.substring(0, popupSelectionJsPath.lastIndexOf("/") + 1);
var popupSelectionlink = document['createElement']('link');
popupSelectionlink.type = 'text/css';
popupSelectionlink.rel = 'stylesheet';
popupSelectionlink.href = popupSelectionPath + "selectionComponent.css";
document['getElementsByTagName']('head')[0].appendChild(popupSelectionlink);
popupSelectionlink = null;