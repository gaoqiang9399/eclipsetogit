var iframeUrl;
$(document).ready(function () {
	window.sessionStorage.clear();
	$('body').layout({
		center__paneSelector:".inner-center",
		north__paneSelector:".inner-north",
		north__size:35,
		spacing_open:0,
		spacing_closed:0
	});
	
	var menuData=[],menuLvl3 = {};
    var i;
	for(i in wkfVpList){
		if(wkfVpList[i].wkfVpNo ==wkfVpNo){
			menuLvl3[wkfVpList[i].wkfVpMenuNo] = {};
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["upNo"] = wkfVpList[i].upWkfVpMenuNo;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["label"] = wkfVpList[i].wkfVpMenuName;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["url"] = wkfVpList[i].wkfVpMenuUrl;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["js"] = wkfVpList[i].jsMethod;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["type"] = wkfVpList[i].urlType;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["sts"] = wkfVpList[i].urlSts;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["expr"] = wkfVpList[i].expr;
			menuLvl3[wkfVpList[i].wkfVpMenuNo]["seq"] = wkfVpList[i].wkfSeq;
			var obj = {};
			obj["menuNo"] = wkfVpList[i].wkfVpMenuNo;
			obj["upNo"] = wkfVpList[i].upWkfVpMenuNo;
			obj["label"] = wkfVpList[i].wkfVpMenuName;
			obj["url"] = wkfVpList[i].wkfVpMenuUrl;
			obj["js"] = wkfVpList[i].jsMethod;
			obj["type"] = wkfVpList[i].urlType;
			obj["sts"] = wkfVpList[i].urlSts;
			obj["expr"] = wkfVpList[i].expr;
			obj["seq"] = wkfVpList[i].wkfSeq;
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
	for(var menuNo in menuData){
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
        var len,widthVal;
		if(!menuLvl3[upNo]){
			if($wrapper.find("li[name='"+menuData[menuNo].menuNo+"']").length>0){
				var li = $wrapper.find("li[name='"+menuData[menuNo].menuNo+"']").eq(0);
				var ul = $wrapperContent.find("ul[name='"+menuData[menuNo].menuNo+"']").eq(0);
				$wrapper.append(li);
				$wrapperContent.append(ul);
				continue;
			}
			len = menuData[menuNo].label.length;
			widthVal = len>8?((len-8)*10+110):110;
			$wrapper.append("<li style='width:"+widthVal+"px' data-name='"+menuData[menuNo].label+"'  name='"+menuData[menuNo].menuNo+"'><a href='"+menuData[menuNo].url+"' onclick='return false'>"+menuData[menuNo].label+"</a></li>");
			$wrapperContent.append("<ul class=\"hiden_li\" name='"+menuData[menuNo].menuNo+"'></ul>");
		}else{
			if($wrapperContent.find("ul[name='"+upNo+"']").length>0){
				$wrapperContent.find("ul[name='"+upNo+"']").append("<li><a href='"+menuData[menuNo].url+"'  onclick='return false'>"+menuData[menuNo].label+"</a></li>");
			}else{
				len = menuLvl3[upNo].label.length;
				widthVal = len>6?((len-6)*10+110):110;
				$wrapper.append("<li style='width:"+widthVal+"px' data-name='"+menuLvl3[upNo].label+"<i class=\"i i-jiantoub\"></i>'  name='"+upNo+"'><a href='"+menuLvl3[upNo].url+"' onclick='return false'>"+menuLvl3[upNo].label+"<i class='i i-jiantoub'></i></a></li>");
				$wrapperContent.append("<ul name='"+upNo+"'></ul>");
				$wrapperContent.find("ul[name='"+upNo+"']").append("<li ><a href='"+menuData[menuNo].url+"'  onclick='return false'>"+menuData[menuNo].label+"</a></li>");
			}
		}
	}
	if(jsCount<3){
		//$wrap.find(".app-screen").addClass("screen-right");
		$wrap.find(".app-screen").css("padding-right","14px");
	}
	
	$(".lavaLamp").lavaLamp({ fx: "backout", speed: 700 ,wrapper:true});
	$("#wrapper").parent().css("z-index",1);
	//$(".wrapper-content ul").css("height",$(".wrapper-content").height());
	
	$(".app-screen").find(".drop-down").bind("click",function(){
		if($(".app-screen-list").css("display")=="none"){ 
			$(".app-screen-list").css("display","block"); 
		}else{ 
			$(".app-screen-list").css("display","none"); 
		} 
	});
	//切换按钮start
	len = $("#wrapper .lavaLamp li").length-1;
	var liw = $("#wrapper .lavaLamp li").outerWidth(true);
	var lenw = $("#wrapper .app-screen").length>0?(parseInt($("#wrapper .app-screen").offset().left/liw)*liw):(parseInt(window.innerWidth/liw)*liw);
	var appw = $("#wrapper .app-screen").length>0?(window.innerWidth-$("#wrapper .app-screen").offset().left):0;
	$('<i class="i i-zuojiantouS"></i>').appendTo("#wrapper").bind("click",function(){
		var ww = window.innerWidth;
		var num= parseInt($("#wrapper .lavaLamp").css('marginLeft'));
		if(num<0&&(num+ww-appw)<0){
			$("#wrapper .lavaLamp").animate({"margin-left":(num+lenw)+"px"});
		}else{
			$("#wrapper .lavaLamp").animate({"margin-left":"0px"});
			$(this).hide();
		}
		if((len*liw)>(ww-appw)){
			$("#wrapper .i-youjiantous").show();
		}
	}).hide().css({"color":"#1e94cc","font-size":"28px"});
	$('<i class="i i-youjiantous"></i>').appendTo("#wrapper").bind("click",function(){
		var ww = window.innerWidth;
		var num= parseInt($("#wrapper .lavaLamp").css('marginLeft'));
		if((len*liw)>(ww-appw)&&(num-ww+appw+(len*liw))>=0){
			num= parseInt($("#wrapper .lavaLamp").css('marginLeft'));
			$("#wrapper .lavaLamp").animate({"margin-left":(num-lenw)+"px"});
			$("#wrapper .i-zuojiantouS").show();
		}else{
			$(this).hide();
		}
		if((num-lenw-ww+appw+(len*liw))<0){
			$(this).hide();
		}
	}).css({"right":(appw)+"px","color":"#1e94cc","font-size":"28px"}).hide();
	if((len*liw)>(window.innerWidth-appw)){
		$("#wrapper .i-youjiantous").show();
	}
	$( window ).resize(function() {
		lenw = $("#wrapper .app-screen").length>0?(parseInt($("#wrapper .app-screen").offset().left/liw)*liw):(parseInt(window.innerWidth/liw)*liw);
		$("#wrapper .lavaLamp").stop().animate({"margin-left":"0px"});
		$("#wrapper .i-zuojiantouS").hide();
		if((len*liw)>(window.innerWidth-appw)){
			$("#wrapper .i-youjiantous").show();
		}else{
			$("#wrapper .i-youjiantous").hide();
		}
	});
	//切换按钮end

	$("#wrapper ul li").click(function(){
		var iframe = document.getElementById('iframepage');
		var urlStr = $(this).find("a").attr("href");
		$(this).addClass('current-cat').siblings().removeClass('current-cat');
		iframeUrl = resUrl(jsonBean,urlStr);
		$("#iframepage").attr("src",iframeUrl);
		 $(".wrapper-content").hide();
		 $(this).find("a").html($(this).data("name"));
	});
	$("#wrapper-content ul li").click(function(){
		var iframe = document.getElementById('iframepage');
		var urlStr = $(this).find("a").attr("href");
		$(this).addClass('current-cat').siblings().removeClass('current-cat');
		iframeUrl = resUrl(jsonBean,urlStr);
		$("#iframepage").attr("src",iframeUrl);
		 $(".wrapper-content").hide();
		 $("#wrapper ul li[name="+$(this).parent().attr("name")+"] a").html($(this).find("a").text()+'<i class="i i-jiantoub"></i>');
	});
	$("#iframepage").attr("src",resUrl(jsonBean,$("#wrapper ul li a").eq(0).attr("href"))+"&moveBack=true");
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
		$(".inner-north").trigger('layoutpaneclose');
	}
}