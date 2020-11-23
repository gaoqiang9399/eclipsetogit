;
! function(win, $) {

	//全局配置，如果采用默认均不需要改动
	var config = {};
	var Dates = {},
		doc = document,
		creat = 'createElement',
		byid = 'getElementById',
		tags = 'getElementsByTagName';;
	var prop = ['laydateYear_box', '/laydateYear.css'];
	//主接口
	win.laydateYear = function(options) {
		options = $.extend({}, this.config, $.isPlainObject(options) && options);
		var as = {};
		try {
			as.event = win.event ? win.event : laydateYear.caller.arguments[0];
		} catch(e) {};
		options.as = as;
		Dates.run(options);
		return laydateYear;
	};
	//获取组件存放路径
	Dates.getPath = (function() {
		var js = document.scripts,
			jsPath = js[js.length - 1].src;
		return config.path ? config.path : jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
	}());

	Dates.use = function(lib, id) {
		var link = doc[creat]('link');
		link.type = 'text/css';
		link.rel = 'stylesheet';
		link.href = Dates.getPath + lib + prop[1];
		id && (link.id = id);
		doc[tags]('head')[0].appendChild(link);
		link = null;
	};
	Dates.init = (function() {
		Dates.use('need');
	}());

	Dates.run = function(options) {
		var elem,
			as = options.as,
			even = as.event,
			target;
		try {
			target = even.target || even.srcElement || {};
		} catch(e) {
			target = {};
		}
		elem = options.elem ? $(options.elem) : target;

		options.blurFun = elem.getAttribute("onblur");
		elem.removeAttribute("onblur");

		as.elemv = /textarea|input/.test(elem.tagName.toLocaleLowerCase()) ? 'value' : 'innerHTML';
		Dates.elemValue = elem[as.elemv];
		Dates.view(elem, options);
	}
	Dates.view = function(elem, options) {
		var div, log = {};
		options = options;
		Dates.elem = elem;
		var skin = options.skin;
		var w = elem.offsetWidth;
		Dates.options = options;
		var as = Dates.options.as = options.as;
		if(!Dates.box) {
			div = doc[creat]('div');
			div.id = prop[0];
			div.className = prop[0];
			div.style.cssText = 'position: absolute;';
			div.style.width = w - 2 + "px";
			div.innerHTML = log.html = '<div class="laydate_ym laydate_y" id="laydate_YY">' +
				'<a class="laydate_choose laydate_chprev laydate_tab"><cite></cite></a>' +
				'<div y="2016" id="laydate_y">2016年</div>' +
				'<a class="laydate_choose laydate_chnext laydate_tab"><cite></cite></a>' +
				'<div class="laydate_yms">' +
				'<a class="laydate_tab laydate_chtop"><cite></cite></a>' +
				'<ul id="laydate_ys"></ul>' +
				'<a class="laydate_tab laydate_chdown"><cite></cite></a>' +
				'</div>' +
				'</div>';

			doc.body.appendChild(div);
			Dates.box = $('#' + prop[0])[0];
			Dates.events();
			div = null;
		} else {
			Dates.shde(Dates.box);
		}
		Dates.box.className = skin ? prop[0] + "  " + skin : prop[0];
		Dates.follow(Dates.box);
		Dates.stopMosup('click', Dates.box);
		Dates.initDate();
	};
	//初始化面板数据
	Dates.initDate = function() {
		var De = new Date();
		var ymd = [De.getFullYear()];
		Dates.viewDate(ymd[0]);
	};
	//吸附定位
	Dates.follow = function(obj) {
		if(Dates.options.fixed) {
			obj.style.position = 'fixed';
			Dates.orien(obj, 1);
		} else {
			obj.style.position = 'absolute';
			Dates.orien(obj);
		}
	};
	//方位辨别
	Dates.orien = function(obj, pos) {
		var tops, rect = Dates.elem.getBoundingClientRect();
		obj.style.left = rect.left + (pos ? 0 : Dates.scroll(1)) + 'px';
		if(rect.bottom + obj.offsetHeight / 1.5 <= Dates.winarea()) {
			tops = rect.bottom - 1;
		} else {
			tops = rect.top > obj.offsetHeight / 1.5 ? rect.top - obj.offsetHeight + 1 : Dates.winarea() - obj.offsetHeight;
		}
		obj.style.top = Math.max(tops + (pos ? 0 : Dates.scroll()), 1) + 'px';
	};
	Dates.scroll = function(type) {
		type = type ? 'scrollLeft' : 'scrollTop';
		return doc.body[type] | doc.documentElement[type];
	};
	Dates.winarea = function(type) {
		return document.documentElement[type ? 'clientWidth' : 'clientHeight']
	};
	//显示隐藏
	Dates.shde = function(elem, type) {
		elem.style.display = type ? 'none' : 'block';
	};
	//阻断mouseup
	Dates.stopMosup = function(evt, elem) {
		if(evt !== 'mouseup') {
			Dates.on(elem, 'mouseup', function(ev) {
				Dates.stopmp(ev);
			});
		}
	};
	Dates.stopmp = function(e) {
		e = e || win.event;
		e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
		return this;
	};

	//事件
	Dates.events = function() {
		var as = Dates.options.as;
		var De = new Date();
		var log = {
			box: '#' + prop[0]
		};
		var now = new Date();
		as.year = $('.laydateYear_box #laydate_y')[0];
		as.year.innerHTML = now.getFullYear() + '年';
		as.year.setAttribute("y", now.getFullYear());
		$(as.year).on('click', function() {
			Dates.creation(this.getAttribute('y'));
		});

		//显示更多年月
		log.YY = parseInt(as.year.getAttribute("y"));
		Dates.viewYears(log.YY);
		Dates.ymd = [De.getFullYear(), De.getMonth(), De.getDate()];
		//切换年
		log.tabYear = function(type) {
			if(type === 0) {
				Dates.ymd[0]--;
			} else if(type === 1) {
				Dates.ymd[0]++;
			} else if(type === 2) {
				log.YY -= 14;
			} else {
				log.YY += 14;
			}
			if(type < 2) {
				Dates.viewDate(Dates.ymd[0]);
				log.YY = Dates.ymd[0];
				Dates.viewYears(log.YY);
			} else {
				Dates.viewYears(log.YY);
			}
		};
		$.each($('#laydate_YY .laydate_tab'), function(i, elem) {
			$(elem).on('click', function(ev) {
				log.tabYear(i);
			});
		});

	}

	//生成年列表
	Dates.viewYears = function(YY) {
		var as = Dates.options.as;
		var elemValue = Dates.elemValue;
		var str = '';
        var li;
		$.each(new Array(14), function(i) {
			if(i === 7) {
				li =  '<li class="' ;
				if (parseInt(as.year.getAttribute("y")) === YY) {
					li+='now ';
				}
				if (parseInt(elemValue)===YY) {
					li+='on ';
				}
				li +='" y="' + YY + '">' + YY + '年</li>';
				str +=li;
			} else {
				li =  '<li class="' ;
				if (parseInt(as.year.getAttribute("y")) === (YY - 7 + i)) {
					li+='now ';
				}
				if (parseInt(elemValue)===(YY - 7 + i)) {
					li+='on ';
				}
				li +='" y="' + (YY - 7 + i) + '">' + (YY - 7 + i) + '年</li>';
				str +=li;
			}
		});
		$('#laydate_ys')[0].innerHTML = str;
		$.each($('#laydate_ys li'), function(i, elem) {
			$(elem).on('click', function(ev) {
				Dates.creation(this.getAttribute('y'));
			});
		});
	};
	Dates.viewDate = function(Y) {
		var as = Dates.options.as;
		var log = {},
			De = new Date();
		De.setFullYear(Y);
		log.ymd = [De.getFullYear()];
		Dates.ymd = log.ymd;
		//锁定年
		as.year ? as.year : as.year = $('.laydateYear_box #laydate_y')[0];
		as.year.innerHTML = Dates.ymd[0] + '年';
		as.year.setAttribute("y", Dates.ymd[0]);
		Dates.viewYears(Dates.ymd[0]);
	};
	//返回最终日期
	Dates.creation = function(ymd, hide) {
		var as = Dates.options.as;
		Dates.elem[as.elemv] = ymd;
		if(!hide) {
			Dates.close();
			typeof Dates.options.choose === 'function' && Dates.options.choose(ymd);
		}
	};
	//关闭控件
	Dates.close = function() {
		var fun = Dates.options.blurFun;
		var as = Dates.options.as;
		var elem = Dates.elem;
		if (elem.getAttribute('onchange')&&Dates.elem[as.elemv]!=Dates.elemValue) {
			if(elem.fireEvent) {
				elem.fireEvent('onchange');
			} else {
				elem.onchange();
			}
		}
		fun ? Dates.elem.setAttribute("onblur", Dates.options.blurFun) : "";
		if (fun) {
			if(elem.fireEvent) {
				elem.fireEvent('onblur');
			} else {
				elem.onblur();
			}
		}
		Dates.shde($('#' + prop[0])[0], 1);
		Dates.elem = null;
	};
	//事件监听器
	Dates.on = function(elem, even, fn) {
		elem.attachEvent ? elem.attachEvent('on' + even, function() {
			fn.call(elem, win.even);
		}) : elem.addEventListener(even, fn, false);
		return Dates;
	};
	Dates.on(doc, 'mouseup', function() {
		var box = $('#' + prop[0])[0];
		if(box && box.style.display !== 'none') {
			Dates.close();
		}
	})
}(window, jQuery);