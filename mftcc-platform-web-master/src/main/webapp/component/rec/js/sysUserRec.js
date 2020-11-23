var SysUserRec = function(window, $) {
	var setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				showLine : false,
				dblClickExpand : false,
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


	var _bindTelConfirm = function () {
		$("#sysUserTelConfirm").bind("click", function(event){
			var selectNodes = $.fn.zTree.getZTreeObj("dataTree").getCheckedNodes(true);
			if(selectNodes.length == 0){
				parent.dialog.get("SysUserForRecDialog").close();
				return;
			}			
			var recNo = "";
			var recName = "";
			$.each(selectNodes,function(i,treeNode){
				if(treeNode.isParent){	
				}else{
					if( i == selectNodes.length-1){
						recNo = recNo + treeNode.id;
						recName = recName + treeNode.name;
					}else{
						recNo = recNo + treeNode.id+"@";
						recName = recName + treeNode.name+"@";
					}
				}
			});
			
			var userInfo = new Object();
			userInfo.opName = recName;
			userInfo.opNo = recNo;
			parent.dialog.get("SysUserForRecDialog").close(userInfo);
		});
	}; 
	
	var _init = function (zNodes){
		
		$.fn.zTree.init($("#dataTree"), setting, zNodes);
	};
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		bindTelConfirm : _bindTelConfirm
	};
}(window, jQuery);