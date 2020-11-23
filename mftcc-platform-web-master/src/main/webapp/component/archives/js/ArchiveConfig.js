;
var ArchiveConfig = function(window, $) {
	/**
	 * 归档设置时候选择归档类型和合同模板
	 * @param callback 回调函数
	 * @returns
	 */
	var _getArchiveInfoDialog = function(callback) {
		top.dialog({
			id:"archiveInfoDialog",
			title:'归档名称',
			url:webPath+'/archiveConfig/getDocInfo',
			width:400,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					if(typeof(callback)== "function"){
						callback(this.returnValue);
					}else{
					}
				}
			}
		}).showModal();
	}

	return {
		getArchiveInfoDialog : _getArchiveInfoDialog
	};
}(window, jQuery);