;
var NmdWay = function(window, $) {
	/**
	 * 选择行业分类公用方法，可以根据级别参数灵活的配置表单中展示几级
	 * @param lev 展示级别
	 * @param callback 回调函数
	 * @returns
	 */
	var _getNmdWayDialog = function(lev,callback) {
		if(!lev){//默认展示两级
			lev = "2";
		}
		top.dialog({
			id:"nmdWayDialog",
			title:'行业分类',
			url:webPath+'/nmdWay/getNmdWayByLev?lev='+lev,
			width:350,
			height:420,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					//返回对象的属性有wayNo,wayName,industryClass,wayClassDetail:行业分类详述
					if(typeof(callback)== "function"){
						callback(this.returnValue);
                        $("input[name=wayClassDetail]").val(this.returnValue.wayClassDetail);
                        $("input[name=wayClass]").val(this.returnValue.wayNo);
					}else{
					}
				}
			}
		}).showModal();
	}

	return {
		getNmdWayDialog : _getNmdWayDialog
	};
}(window, jQuery);