var slideout;
function skipPage(url){
	$("#a_iframe").attr("src",url);
	slideout.close();
}
function showLocale(objD) {
	var str, colorhead, colorfoot;
	var yy = objD.getYear();
	if (yy < 1900)
		yy = yy + 1900;
	var MM = objD.getMonth() + 1;
	if (MM < 10)
		MM = '0' + MM;
	var dd = objD.getDate();
	if (dd < 10)
		dd = '0' + dd;
	var hh = objD.getHours();
	if (hh < 10)
		hh = '0' + hh;
	var mm = objD.getMinutes();
	if (mm < 10)
		mm = '0' + mm;
	var ss = objD.getSeconds();
	if (ss < 10)
		ss = '0' + ss;
	var ww = objD.getDay();
	if (ww == 0)
		colorhead = "<font color=\"#FF0000\">";
	if (ww > 0 && ww < 6)
		colorhead = "<font color=\"#373737\">";
	if (ww == 6)
		colorhead = "<font color=\"#008000\">";
	if (ww == 0)
		ww = "星期日";
	if (ww == 1)
		ww = "星期一";
	if (ww == 2)
		ww = "星期二";
	if (ww == 3)
		ww = "星期三";
	if (ww == 4)
		ww = "星期四";
	if (ww == 5)
		ww = "星期五";
	if (ww == 6)
		ww = "星期六";
	colorfoot = "</font>"
	str = hh + ":" + mm + ":" + ss;
	return (str);
}
function tick() {
	var today;
	today = new Date();
	$("#currTime").html(showLocale(today));
	window.setTimeout("tick()", 1000);
}
function GetQueryString(name,url){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r;
    if(url==null){
   	 r = window.location.search.substr(1).match(reg);
    }else{
   	 if (url.indexOf("?") != -1) {
   		 url = url.substr(url.indexOf("?"));
   	 }
   	 r = url.substr(1).match(reg);
    }
    if(r!=null)return  unescape(r[2]); return null;
}

$(function() {
	slideout = new Slideout({
		'panel' : document.getElementById('panel'),
		'menu' : document.getElementById('menu'),
		'padding' : 175,
		'tolerance' : 70
	});
	// Toggle button
	$("#menuBtn").bind("click", function() {
		slideout.toggle();
	});
	tick();
	
	//初始化入口
	var isC = false;
	for(var i = 0,j =viewMenuData.length;i<j;i++){
		$(".menu-body").append('<li><a target="iframepage" href="'+path+"/"+viewMenuData[i].url+'"><i class="i i-'+viewMenuData[i].css+'"></i><span>'+viewMenuData[i].name+'</span></a></li>');
		if(GetQueryString("entranceNo",viewMenuData[i].url)=="99"){
			isC = true;
			skipPage(viewMenuData[i].url);
			$("#menu-title").text(viewMenuData[i].name);
		}
	}
	if(!isC){
		skipPage($(".menu-body").find("li a").eq(0).attr("href"));
		$("#menu-title").text($(".menu-body").find("li span").eq(0).text());
	}
	$(".menu-body li").bind("click",function(){
		var $li = $(this);
		var a = $li.find("a").eq(0);
		var span = $li.find("span").eq(0);
		skipPage(a.attr("href"));
		$li.addClass('curr');
		$li.siblings('li').removeClass('curr');
		$("#menu-title").text(span.text());
	});
	
});