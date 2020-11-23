;
var BussNodePmsBiz=function(window,$){
	var _checkPmsBiz=function(nodeName){
		var pmBizNoArr=pmBizNo.split(",");
		for ( var i = 0; i < pmBizNoArr.length; i++) {
			if(nodeName==pmBizNoArr[i]){
				return true;
			}
		}
		return false;
	};
	return{
		checkPmsBiz:_checkPmsBiz,
	};
}(window,jQuery);