var myCustomScrollbarForForm = function(options){
	//需要添加滚动条的jquery对象
	var $conentObj = $(options.obj);
	//需要添加的默认操作
	var newOptions = {
			advanced:{
				updateOnContentResize:true
			},
			callbacks: {
				onUpdate : function(){
			      if (!this.hasMoreBar) {
			    	  var $this = $(this);
						// 在当前区域追加一个moreBar，绝对定位，跟当前区域做底部对齐。
						var $moreBar = $('<div id="moreBar" style="padding:10px 5px 5px 5px; z-index:3;"><span class="i i-open-down">更多</span></div>');
						$moreBar.css({
							"left": "50%",
							"position": "fixed",
							"cursor": "pointer",
							"color": "#eee",
							"background-color": "rgba(132, 128, 132, 0.521569)",
							"border-radius": "50% 50% 0px 0px",
							"margin-left": $moreBar.width()
						});
						$(this).append($moreBar);
						// onUpdate回调不能正确取得高度，将DOM追加到页面后再次获取高度。
						$moreBar.css("top", $(this).height() - $moreBar.outerHeight() + $(this).offset().top) ;
						this.hasMoreBar = true;
						$moreBar.bind("click", function(){
							$this.mCustomScrollbar("scrollTo", "-=150");
						});
						if (!this.mcs || this.mcs.topPct > 95) {
							$("#moreBar").hide();
						}
					} else {
						$("#moreBar").css({
							"top": $(this).height() - $("#moreBar").outerHeight()
						});
						$("#moreBar").show();
						if (!this.mcs || this.mcs.topPct > 95) {
							$("#moreBar").hide();
						}
					}
				},
				whileScrolling :function(){
					if (this.hasMoreBar) {
						if (this.mcs.topPct > 95) {
							// 隐藏
							$("#moreBar").hide();
						} else {
							// 显示。
							$("#moreBar").show();
						}
					}
		        }
			}
		};
	//把传入的参数全部加入到调用mCustomScrollbar的参数中，保证滚动条可以进行设置
	for (var p in options){
            newOptions[p]=options[p];
    }

	//生成滚动条
    if(options.updateImmediate){
        $conentObj.mCustomScrollbar("destroy");
    }
	$conentObj.mCustomScrollbar(newOptions);

};
