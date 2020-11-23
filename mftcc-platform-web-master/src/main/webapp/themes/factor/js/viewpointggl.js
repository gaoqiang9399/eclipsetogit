$(document).ready(function () {
	window.sessionStorage.clear();

	var $blockView = $(".container").find('.relate-corp');
	
	var menuData=[],menulvl = {};
	for(var i in viewPointData){
		if(viewPointData[i].urlSts=="1"&&viewPointData[i].viewpointNo==vpNo){
//			menulvl[viewPointData[i].viewpointMenuNo] = {};
//			menulvl[viewPointData[i].viewpointMenuNo]["upNo"] = viewPointData[i].upViewpointMenuNo;
//			menulvl[viewPointData[i].viewpointMenuNo]["label"] = viewPointData[i].viewpointMenuName;
//			menulvl[viewPointData[i].viewpointMenuNo]["url"] = viewPointData[i].viewpointMenuUrl;
//			menulvl[viewPointData[i].viewpointMenuNo]["js"] = viewPointData[i].jsMethod;
//			menulvl[viewPointData[i].viewpointMenuNo]["type"] = viewPointData[i].urlType;
//			menulvl[viewPointData[i].viewpointMenuNo]["sts"] = viewPointData[i].urlSts;
//			menulvl[viewPointData[i].viewpointMenuNo]["expr"] = viewPointData[i].expr;
//			menulvl[viewPointData[i].viewpointMenuNo]["isrefresh"] = viewPointData[i].isrefresh;
//			menulvl[viewPointData[i].viewpointMenuNo]["seq"] = viewPointData[i].seq;
			var obj = {};
			obj["menuNo"] = viewPointData[i].viewpointMenuNo;
			obj["upNo"] = viewPointData[i].upViewpointMenuNo;
			obj["label"] = viewPointData[i].viewpointMenuName;
			obj["url"] = viewPointData[i].viewpointMenuUrl;
			obj["js"] = viewPointData[i].jsMethod;
			obj["type"] = viewPointData[i].urlType;
			obj["sts"] = viewPointData[i].urlSts;
			obj["expr"] = viewPointData[i].expr;
			obj["isrefresh"] = viewPointData[i].isrefresh;
			obj["seq"] = viewPointData[i].seq;
			menuData.push(obj);
			if( viewPointData[i].urlType=="0" && viewPointData[i].urlSts=="1"){
				menuBtn.push(viewPointData[i].jsMethod);
			}
			if( viewPointData[i].urlType=="1" && viewPointData[i].urlSts=="1"){
				menuUrl.push(viewPointData[i].viewpointMenuUrl);
			}
			//var showFlag = false;
			$.each($blockView,function(index,blockView){
				if($(blockView).data('view') == viewPointData[i].viewpointMenuUrl){
					$(blockView).show();
					return false;
				}
			});
		}
	}

});