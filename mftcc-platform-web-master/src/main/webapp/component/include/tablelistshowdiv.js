/*
 * 列表获取焦点时，提示框
 */


$(document).ready(function() {
	var setText = function(event, api) {
		var target = $(event.originalEvent && event.originalEvent.target);
		if (target.length) {
			api.set('content', ("<div><table style='table-layout:fixed' width='200px'><tr><td style='word-wrap : break-word; overflow:hidden;'>" + target.text() + "</td></tr></table></div>")); // 更新内容
		}
	}
	$.fn.initMytitle = function() {
		var $this = $(this);
		// 在指定区域创建用于共享的tip
		if ($this) {
			if ($this.get(0)) {
				$this.each(function() {
					if ($(this).attr("mytitle")) {
						$(this).unbind();
						$(this).qtip({
							content:$(this).attr("mytitle"),
//								style: { 
//									tip: 'topMiddle',//箭头所在图形位置
//								},
								position: {  
								      // 提示信息的位置  
								      // 如提示的目标元素的右下角(at属性)  
								      // 对应 提示信息的左上角(my属性)  
								      my: 'top left',   
								      at: 'bottom right', 
								      adjust: {  
								          // 提示信息位置偏移  
								          x: -100, y: -10,  
								          mouse: true,  
								          resize: true,  
								          method: 'flip flip'  
								       }
								}
					        });
					}
				});
			}
		}
	}
	$("#tablist td[mytitle]").initMytitle();
});