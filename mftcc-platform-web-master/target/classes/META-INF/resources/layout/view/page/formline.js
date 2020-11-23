var allEle = $('.formDiv');
var allDD = [];
var headLen = allEle.length;
var subLen = $('.formtith4').length;
var formline = $(".time-line-line").css("height",(headLen-1)*26+(subLen-1)*24);
var ddArr = [];
allEle.each(function(i, list) {
	allDD.push($(this));
	var ddHtml;
	var name = $(this).attr('name');
		if (i == 0) {
			ddHtml = '<dd sub="false" class="time-line-dd">' + '<span class="time-line-dot line-dot-on"></span>' + '<a href="#' + name + '" class="time-line-a line-a-on">' + $(this).find("h3").html() + '</a>' + '</dd>';
			$(list).find(".formtith4").each(function(i){
				allDD.push($(this));
				var subname=name+"-"+i;
				$(this).attr("name",subname);
				if (i == 0) {
					ddHtml+='<dd sub="true" class="time-line-dd"><span class="time-line-second-dot line-second-dot-on"></span><a href="#' + subname + '" class="time-line-second-a line-a-second-on" >'+$(this).html()+'</a></li>';
				} else{
					ddHtml+='<dd sub="true"  class="time-line-dd"><span class="time-line-second-dot"></span><a href="#' + subname + '"  class="time-line-second-a">'+$(this).html()+'</a></li>';
				}
			});
		} else {
			ddHtml = '<dd sub="false" class="time-line-dd">' + '<span class="time-line-dot"></span>' + '<a href="#' + $(this).attr('name') + '" class="time-line-a">' +$(this).find("h3").html() + '</a>' + '</dd>';
			$(list).find(".formtith4").each(function(i){
				allDD.push($(this));
				var subname=name+"-"+i;
				$(this).attr("name",subname);
				ddHtml+='<dd sub="true" class="time-line-dd"><span class="time-line-second-dot"></span><a href="#' + subname + '"  class="time-line-second-a">'+$(this).html()+'</a></li>';
			});
		}
		ddArr.push(ddHtml);
});

$('.time-line-dl').html(ddArr.join(''));

$('.time-line-dl').delegate('dd', 'click', function() {
	$ (window).unbind ('scroll');
	var $dd = $(this);
	var index = $('.time-line-dl dd').index($(this));
	var ddIndex = $(this).find('a').stop().attr('href').lastIndexOf('#');
	var ddId = $(this).find('a').stop().attr('href').substring(ddIndex + 1);
    var windowTop;
	if($('div[name="' + ddId + '"]').length>0){
		windowTop = $('div[name="' + ddId + '"]').offset().top;
	}else{
		windowTop = $('span[name="' + ddId + '"]').offset().top;
	}
	if ($dd.attr("sub")) {
		$dd.find('a').addClass('line-a-second-on');
		$dd.find('span').addClass('line-second-dot-on');
		$dd.siblings('dd').find('a').removeClass('line-a-second-on').removeClass('line-a-on');
		$dd.siblings('dd').find('span').removeClass('line-second-dot-on').removeClass('line-dot-on');
	} else{
		console.log($dd.attr("sub"));
		$dd.find('a').addClass('line-a-on');
		$dd.find('span').addClass('line-dot-on');
		$dd.siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
		$dd.siblings('dd').find('span').removeClass('line-dot-on').removeClass('line-second-dot-on');
	}
	$('body,html').animate({
		scrollTop: windowTop
	}, 'slow',function(){
		$(window).scroll(function() {
	for (var i = allDD.length - 1; i >= 0; i--) {
		if (($(this).scrollTop() + allDD[0].offset().top)> allDD[i].offset().top) {
			var index = i;
			var $dd = $('.time-line-dl dd').eq(index);
			if ($dd.attr("sub")) {
				$dd.find('a').addClass('line-a-second-on');
				$dd.find('span').addClass('line-second-dot-on');
				$dd.siblings('dd').find('a').removeClass('line-a-second-on').removeClass('line-a-on');
				$dd.siblings('dd').find('span').removeClass('line-second-dot-on').removeClass('line-dot-on');
			} else{
				$dd.find('a').addClass('line-a-on');
				$dd.find('span').addClass('line-dot-on');
				$dd.siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
				$dd.siblings('dd').find('span').removeClass('line-dot-on').removeClass('line-second-dot-on');
			}
			return false;
		}
	}
});$ (window).bind ('scroll');
	});
});

$(window).scroll(function() {
	for (var i = allDD.length - 1; i >= 0; i--) {
		if (($(this).scrollTop() + allDD[0].offset().top)> allDD[i].offset().top) {
			var index = i;
			var $dd = $('.time-line-dl dd').eq(index);
			if ($dd.attr("sub")) {
				$dd.find('a').addClass('line-a-second-on');
				$dd.find('span').addClass('line-second-dot-on');
				$dd.siblings('dd').find('a').removeClass('line-a-second-on').removeClass('line-a-on');
				$dd.siblings('dd').find('span').removeClass('line-second-dot-on').removeClass('line-dot-on');
			} else{
				$dd.find('a').addClass('line-a-on');
				$dd.find('span').addClass('line-dot-on');
				$dd.siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
				$dd.siblings('dd').find('span').removeClass('line-dot-on').removeClass('line-second-dot-on');
			}
			return false;
		}
	}
});



