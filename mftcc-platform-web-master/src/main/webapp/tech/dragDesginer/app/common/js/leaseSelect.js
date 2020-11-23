(function($) {
	$("body").bind("click",function(e) {
		if ($(e.target).parents(".leaseSelect-box").length == 0&& e.target.className.indexOf("leaseSelect-box") == -1&& e.target.className.indexOf("leaseSelect-elem") == -1) {
			$(".leaseSelect-box").remove();
		}
	});
	$.fn.leaseSelect = function(options) {
		var opts = $.extend({}, $.fn.leaseSelect.defaults, options);
		var $this = $(this);
		$this.addClass("leaseSelect-elem");
		
		$this.bind("click", function() {
			var h = $this[0].offsetHeight; // 高度
			var w = $this[0].offsetWidth; // 宽度
			opts.left = getElementLeft($this[0]) + 2;
			opts.top = getElementTop($this[0]) + h;
			var selectCss = {
					"min-width" : w,
					left : opts.left,
					top : opts.top
				};
			var data;
			$(".leaseSelect-box").remove();
			if (typeof opts.data === 'object') {
				data = opts.data;
				init($this,data,selectCss,opts.callback);
			}else{
				$.getJSON(opts.data, function(json){
					data = json.data;
					init($this,data,selectCss,opts.callback);
				});
				/*$.ajax({
	                url: opts.data,
	                type:"POST",
	                dataType:"json",
	                success:function(jsonData){
	                	data = jsonData;
						init($this,data,selectCss,opts.callback);
	    			}
	            });*/
			}
		});
	};
	
	function init(elem,data,selectCss,callback){
		var $ul = $('<ul class="leaseSelect-box"></ul>');
		$ul.css(selectCss);
		$ul.appendTo("body");
		$.each(data, function(i, node) {
			if (node.hasOwnProperty('value')) {
				var $li = $('<li class="leaseSelect-item"></li>');
				if (node.hasOwnProperty('label')) {
					$li.append('<span class="item-label">' + node.label
							+ '</span>|<span class="item-value">'
							+ node.value + '</span>');
				} else {
					$li.text('<span class="item-label">' + node.id
							+ '</span>|<span class="item-value">'
							+ node.value + '</span>');
				}
				$li.append('<button class="item-btn" type="button">追加</button>');
				$li.data("node", node);
				$li.appendTo($ul);
				$li.bind("click", function() {
					elem.val($li.data("node").value);
					//formAttributeUpdate();
					if (typeof (callback) == "function") {
						callback.call(this, $li.data("node"));
					}
					$li.unbind();
					$ul.remove();
					return false;
				});
				$li.find('button').bind("click",function(){
					elem.val(elem.val()+" "+$li.data("node").value);
					elem.blur();
					$li.unbind();
					$ul.remove();
					return false;
				});
			}
		});
	}
	
	
	$.fn.leaseSelect.defaults = {
		data : [],
		callback : function(p) {
			console.log(p);
		}
	};

	// 绝对位置
	function getElementLeft(element) {
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		while (current !== null) {
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
		return actualLeft;
	}
	function getElementTop(element) {
		var actualTop = element.offsetTop;
		var current = element.offsetParent;
		while (current !== null) {
			actualTop += current.offsetTop;
			current = current.offsetParent;
		}
		return actualTop;
	}
	// 相对位置
	function getElementViewLeft(element) {
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		while (current !== null) {
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
        var elementScrollLeft;
		if (document.compatMode == "BackCompat") {
			elementScrollLeft = document.body.scrollLeft;
		} else {
			elementScrollLeft = document.documentElement.scrollLeft;
		}
		return actualLeft - elementScrollLeft;
	}
	function getElementViewTop(element) {
		var actualTop = element.offsetTop;
		var current = element.offsetParent;
		while (current !== null) {
			actualTop += current.offsetTop;
			current = current.offsetParent;
		}
        var elementScrollTop;
		if (document.compatMode == "BackCompat") {
			elementScrollTop = document.body.scrollTop;
		} else {
			elementScrollTop = document.documentElement.scrollTop;
		}
		return actualTop - elementScrollTop;
	}
})(jQuery);
