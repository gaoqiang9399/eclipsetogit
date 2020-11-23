;
var OaConsClassSelect = function(window, $) {
	var setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				showIcon: true,
				showLine : false,
				dblClickExpand : false,
				/* addDiyDom : addDiyDom */
			},
			/*check: {
				enable: false,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},*/
			callback : {
				onClick : zTreeOnClick
			}
		};

	function zTreeOnClick(event, treeId, treeNode) {	
		var flag = $(window.parent.document).find("#page_flag").val();
		//判断弹窗来源：新增资产、申领、新增类别
		if(flag == "new_class_page"){//新增类别选择父类
			var superClass = new Object();
			superClass.classId = treeNode.classId;
			superClass.className = treeNode.className;
			superClass.appType = treeNode.appType;
			parent.dialog.get("consClassDialog").close(superClass);
		}else{//新增资产和申领选择类别
			if(treeNode.isParent){
				var zTreeObj = $.fn.zTree.getZTreeObj("dataTree");
				zTreeObj.expandNode(treeNode, null, false);
			} else {
				var consClass = new Object();
				consClass.classId = treeNode.classId;
				consClass.className = treeNode.className;
				consClass.appType = treeNode.appType;
				/*if(treeNode.appType == "1"){
				consClass.appType = "领用";
				}
				if(treeNode.appType == "2"){
					consClass.appType = "借用";
				}*/
				parent.dialog.get("consClassDialog").close(consClass).remove();
			}
		}
	};
	
	var _init = function (zNodes){
		$.fn.zTree.init($("#dataTree"), setting, zNodes);
	}
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);