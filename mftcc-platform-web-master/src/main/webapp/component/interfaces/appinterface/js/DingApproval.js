;
/**
审批详情页面js
*/
var DingApproval = function(window,$){
	/**
	 * 审批历史详情动画效果
	 */
	var _openApproveDetail = function(obj){
		var $this = $(obj);
		if($this.hasClass("open")){//已经展开，点击隐藏
			$this.prev(".item-approve-detail").slideUp("slow");
			$this.removeClass("open");
			$this.addClass("close");
			$this.html("<i class=\"i i-open-down\"></i>");
		}else if($this.hasClass("close")){//已关闭，点击展开
// 			$this.prev(".item-approve-detail").show();
			$this.prev(".item-approve-detail").slideDown("slow");
			$this.removeClass("close");
			$this.addClass("open");
			$this.html("<i class=\"i i-close-up\"></i>");
		}
	};
	return {
		openApproveDetail:_openApproveDetail
	};
}(window,jQuery);