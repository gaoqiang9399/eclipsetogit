;
var OaNoticeScorp = function(window, $) {
	var setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				showLine : false,
				dblClickExpand : false,
                showIcon : false
                /* addDiyDom : addDiyDom */
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback : {
			}
		};


	var _bindScorpConfirm = function () {
		$("#noticeScorpConfirm").bind("click", function(event){
			var selectNodes = $.fn.zTree.getZTreeObj("dataTree").getCheckedNodes(true);
			if(selectNodes.length == 0){
				parent.dialog.get("NoticeScorpDialog").close();
				return;
			}			
			var brNo="";
			var brName = "";
			$.each(selectNodes,function(i,treeNode){
				if(treeNode.isParent){	
				}else{
					if( i == selectNodes.length-1){
						brNo = brNo + treeNode.id;
						brName = brName + treeNode.name;
					}else{
						brNo = brNo + treeNode.id+";";
						brName = brName + treeNode.name+";";
					}
				}
			});
			var noticeScorpInfo = new Object();
			noticeScorpInfo.brNo = brNo;
			noticeScorpInfo.brName = brName;
			parent.dialog.get("NoticeScorpDialog").close(noticeScorpInfo);
		});
	}; 
	
	var _init = function (zNodes){
		
		$.fn.zTree.init($("#dataTree"), setting, zNodes);
	}
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		bindScorpConfirm : _bindScorpConfirm
	};
}(window, jQuery);