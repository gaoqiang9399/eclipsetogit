var iframeUrl;
$(document).ready(function () {
	window.sessionStorage.clear();
	$('body').layout({
		center__paneSelector:".inner-center",
		north__paneSelector:".inner-north",
		north__size:34,
		spacing_open:0,
		spacing_closed:0
	});
	
	var menuData=[],menulvl = {};
    var i;
	for(i in viewPointData){
		if(viewPointData[i].urlSts=="1"&&viewPointData[i].viewpointNo==vpNo){
			menulvl[viewPointData[i].viewpointMenuNo] = {};
			menulvl[viewPointData[i].viewpointMenuNo]["upNo"] = viewPointData[i].upViewpointMenuNo;
			menulvl[viewPointData[i].viewpointMenuNo]["label"] = viewPointData[i].viewpointMenuName;
			menulvl[viewPointData[i].viewpointMenuNo]["url"] = viewPointData[i].viewpointMenuUrl;
			menulvl[viewPointData[i].viewpointMenuNo]["js"] = viewPointData[i].jsMethod;
			menulvl[viewPointData[i].viewpointMenuNo]["type"] = viewPointData[i].urlType;
			menulvl[viewPointData[i].viewpointMenuNo]["sts"] = viewPointData[i].urlSts;
			menulvl[viewPointData[i].viewpointMenuNo]["expr"] = viewPointData[i].expr;
			menulvl[viewPointData[i].viewpointMenuNo]["isrefresh"] = viewPointData[i].isrefresh;
			menulvl[viewPointData[i].viewpointMenuNo]["seq"] = viewPointData[i].seq;
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
		}
	}
	var $wrapper = $("#wrapper .lavaLamp");
	var $wrap = $("#wrapper");
	var $wrapperContent = $("#wrapper-content");
	$wrapper.html("");
	$wrapperContent.html("");
	var jsCount = 0;
	menuData.sort(function(a,b){
		return a.seq-b.seq;
    });
//	for(var menuNo in menuData){
	for(var menuNo=0;menuNo<menuData.length;menuNo++){
		if(menuData[menuNo].expr!=null&&menuData[menuNo].expr!=""){
			var arr = menuData[menuNo].expr.split(";");
			var flag = false;
			for(i=0;i<arr.length;i++){
				var exprArr = arr[i].split("-");
				if(typeof(jsonBean[exprArr[0]])!="undefined"&&jsonBean[exprArr[0]] == exprArr[1]){
					flag = true;
					break;
				}
			}
			if(flag){
				continue;
			}
		}
		if(menuData[menuNo].type == 0){
			if(jsCount==0){
				$wrap.append('<div class="app-screen">');
			}
			if(jsCount<3){
				$wrap.find(".app-screen").eq(0).prepend('<input type="button" onclick="'+menuData[menuNo].js+'" value="'+menuData[menuNo].label+'">');
			}else if(jsCount == 3){
				$wrap.find(".app-screen").eq(0).prepend('<button type="button" class="drop-down fa fa-angle-down" ></button>');
				$wrap.parent().append('<div class="app-screen-list"><ul></ul><div>');
				$wrap.parent().find('.app-screen-list ul').append('<li><input type="button" onclick="'+menuData[menuNo].js+'" value="'+menuData[menuNo].label+'"></li>');
			}else{
				$wrap.parent().find('.app-screen-list ul').append('<li><input type="button" onclick="'+menuData[menuNo].js+'" value="'+menuData[menuNo].label+'"></li>');
			}
			jsCount++;
			continue;
		}
		var upNo = menuData[menuNo].upNo;
		if(!menulvl[upNo]){
			if($wrapper.find("li[name='"+menuData[menuNo].menuNo+"']").length>0){
				var li = $wrapper.find("li[name='"+menuData[menuNo].menuNo+"']").eq(0);
				var ul = $wrapperContent.find("ul[name='"+menuData[menuNo].menuNo+"']").eq(0);
				$wrapper.append(li);
				//$wrapper.find("li[name='"+menuData[menuNo].menuNo+"']").eq(0).remove();
				//var ul = $wrapperContent.find("ul").eq(index);
				$wrapperContent.append(ul);
				//$wrapperContent.find("ul").eq(index).remove();
				continue;
			}
			$wrapper.append("<li isrefresh='"+menuData[menuNo].isrefresh+"' name='"+menuData[menuNo].menuNo+"'><a href='"+menuData[menuNo].url+"' onclick='return false'>"+menuData[menuNo].label+"</a></li>");
			$wrapperContent.append("<ul class=\"hiden_li\" name='"+menuData[menuNo].menuNo+"'></ul>");
		}else{
			if($wrapperContent.find("ul[name='"+upNo+"']").length>0){
				$wrapperContent.find("ul[name='"+upNo+"']").append("<li isrefresh='"+menuData[menuNo].isrefresh+"'><a href='"+menuData[menuNo].url+"'  onclick='return false'>"+menuData[menuNo].label+"</a></li>");
			}else{
				$wrapper.append("<li isrefresh='"+menulvl[upNo].isrefresh+"' name='"+upNo+"'><a href='"+menulvl[upNo].url+"' onclick='return false'>"+menulvl[upNo].label+"<i class='i i-jiantou8'></i></a></li>");
				$wrapperContent.append("<ul name='"+upNo+"'></ul>");
				$wrapperContent.find("ul[name='"+upNo+"']").append("<li isrefresh='"+menuData[menuNo].isrefresh+"'><a href='"+menuData[menuNo].url+"'  onclick='return false'>"+menuData[menuNo].label+"</a></li>");
			}
		}
	}
	if(jsCount<3){
		$wrap.find(".app-screen").addClass("screen-right");
	}
	
	$(".lavaLamp").lavaLamp({ fx: "backout", speed: 700 ,wrapper:true});
	$("#wrapper").parent().css("z-index",1);
	$(".wrapper-content ul").css("height",$(".wrapper-content").height());
	
	$(".app-screen").find(".drop-down").bind("click",function(){
		if($(".app-screen-list").css("display")=="none"){ 
			$(".app-screen-list").css("display","block"); 
		}else{ 
			$(".app-screen-list").css("display","none"); 
		} 
	});
	

	$("#wrapper ul li,#wrapper-content ul li").click(function(){
		var flag = false;
		var iframe = document.getElementById('iframepage');
		var isrefresh = $(this).attr("isrefresh");
		var urlStr = $(this).find("a").attr("href");
		$(this).addClass('current-cat').siblings().removeClass('current-cat');
		iframeUrl = resUrl(jsonBean,urlStr);
		if(isrefresh!=""&&isrefresh=="0"&&window.sessionStorage){
			flag = true;
		}else{
			flag = false;
		}
		if(iframeUrl == "self"){
			$("#iframepage").attr("src","");
			$("#iframepage").css("height","0");
			$("#formInfo-div").show();
			$(".showOrHide").show();
		}else{
			$("#formInfo-div").hide();
			$(".showOrHide").hide();
			$("#iframepage").css("height","100%");
		  if(flag){
			if(!window.sessionStorage.getItem(iframeUrl)){
				$("#iframepage").attr("src",iframeUrl);
				if (iframe.attachEvent){
					iframe.attachEvent("onload", setStorage);
				} else {
					iframe.onload = function(){
						var htmlStr = document.getElementById('iframepage').contentWindow.document.documentElement.innerHTML;
						window.sessionStorage.setItem(iframeUrl,htmlStr);
						iframe.onload = null;
					};
				} 
			}else{
				var htmlStr = window.sessionStorage.getItem(iframeUrl);
				document.getElementById('iframepage').contentWindow.document.getElementsByTagName('html')[0].innerHTML = htmlStr;
			}
		}else{
			$("#iframepage").attr("src",iframeUrl);
		}
		//$wrapperContent.addClass("hided");
		 $(".wrapper-content").hide();
		}
	});
	if($("#wrapper ul li a").eq(0).attr("href") == "self"){
		$(".showOrHide").show();
	}else{
		$(".showOrHide").hide();
		$("#iframepage").attr("src",resUrl(jsonBean,$("#wrapper ul li a").eq(0).attr("href")));
	}
	
});
function setStorage(){
	var htmlStr = document.getElementById('iframepage').contentWindow.document.documentElement.innerHTML;
	window.sessionStorage.setItem(iframeUrl,htmlStr);
	document.getElementById('iframepage').detachEvent("onload",setStorage);
	
}
/**
 * 解析url
 * @param jsonBean
 */
function resUrl(jsonBean,urlStr){
	var url="";
	var arr = urlStr.split(";");
	for(var i=0;i<arr.length;i++){
		if(i==0){
			url += arr[i]+"?";
		}else if(arr[i]!=""){
			var name = arr[i].substring(0,arr[i].indexOf("-"));
			var value = arr[i].substring(arr[i].indexOf("-")+1,arr[i].length);
			var val = jsonBean[value];
			if(val==null||typeof(val)=="undefined"){
				url += name+"="+value +"&";
			}else{
				url += name+"="+val +"&";
			}
		}
	}
	url = url.substring(0,url.length-1);
	return url;
}
function iframepageload(){
	var wrapper = document.getElementById('iframepage').contentWindow.document.getElementById('wrapper');
	if(wrapper!=null){
		var wr = $(wrapper).parent().clone();
//		$(wrapper).parent().trigger('layoutpaneclose');
		$(".inner-north").trigger('layoutpaneclose');
	}
}