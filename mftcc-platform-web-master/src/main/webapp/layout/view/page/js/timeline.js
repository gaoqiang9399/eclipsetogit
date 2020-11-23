var myDate = new Date();
var allEle,headLen;
$(document).ready(function(){ 
	allEle = $('.task_style');
	headLen = allEle.length;
	var ddArr = [];
	myDate.setFullYear(2015, 8, 20);
	var weekflag = true;
	var monflag = true;
	allEle.each(function(i, list) {
		var ddHtml;
		if (checkWeek($(this).attr('name'))) {
			if (i == 0) {
				ddHtml = '<dd class="time-line-dd">' + '<a href="#' + $(this).attr('name') + '" class="time-line-a line-a-on">' + formatStringyyyyMMddToyyyy_MM_dd($(this).attr('name')) + '</a>' + '<span class="time-line-dot line-dot-on fa fa-check"></span>' + '</dd>';
			} else {
				ddHtml = '<dd class="time-line-dd">' + '<a href="#' + $(this).attr('name') + '" class="time-line-a">' + formatStringyyyyMMddToyyyy_MM_dd($(this).attr('name')) + '</a>' + '<span class="time-line-dot"></span>' + '</dd>';
			}
			ddArr.push(ddHtml);
		} else if (lastweek($(this).attr('name')) && weekflag) {
			weekflag = false;
			ddHtml = '<dd class="time-line-dd">' + '<a href="#' + $(this).attr('name') + '" class="time-line-a">上周</a>' + '<span class="time-line-dot"></span>' + '</dd>';
			ddArr.push(ddHtml);
		} else if (lastMon($(this).attr('name')) && monflag) {
			monflag = false;
			ddHtml = '<dd class="time-line-dd">' + '<a href="#' + $(this).attr('name') + '" class="time-line-a">上月</a>' + '<span class="time-line-dot"></span>' + '</dd>';
			ddArr.push(ddHtml);
		}
		
	});
	
	$('.time-line-dl').html(ddArr.join(''));
	
	$('.time-line-dl').delegate('dd', 'click', function() {
		var index = $('.time-line-dl dd').index($(this));
		var ddIndex = $(this).find('a').stop().attr('href').lastIndexOf('#');
		var ddId = $(this).find('a').stop().attr('href').substring(ddIndex + 1);
		var windowTop = $('div[name="' + ddId + '"]').offset().top;
		$('body,html').animate({
			scrollTop: windowTop
		}, 'slow');
	});
});

$(window).scroll(function() {
	for (var i = headLen - 1; i >= 0; i--) {
		if ($(this).scrollTop() + allEle.eq(0).offset().top > allEle.eq(i).offset().top && $(this).scrollTop() + allEle.eq(0).offset().top <= allEle.eq(i).offset().top + allEle.eq(i).outerHeight(true)) {
			var index = i;
			var $dd = $('.time-line-dl dd').eq(index);
			$dd.find('a').addClass('line-a-on');
			$dd.find('span').addClass('fa fa-check').addClass('line-dot-on');
			$dd.siblings('dd').find('a').removeClass('line-a-on');
			$dd.siblings('dd').find('span').removeClass('fa fa-check').removeClass('line-dot-on');
			return false;
		}
	}
});



function formatStringyyyyMMddToyyyy_MM_dd(value) {
	if (value.length == 8) {
		//return value.substring(0, 4) + "年" + value.substring(4, 6) + "月" + value.substring(6, 8)+"日";
		return Number(value.substring(4, 6)) + "月" + value.substring(6, 8) + "日";
	} else if (value.length == 6) {
		return value.substring(0, 4) + "月" + value.substring(4, 6) + "日";
	} else {
		return value + "日";
	}
}

function checkWeek(value) {
	var flag = false;
	var nDate = new Date();
	var sDate = new Date();
	sDate.setFullYear(value.substring(0, 4), Number(value.substring(4, 6)) - 1, value.substring(6, 8));
	if (myDate.getDay() == 0) {
		nDate.setFullYear(myDate.getFullYear(), myDate.getMonth(), myDate.getDate() - 6)
	} else {
		nDate.setFullYear(myDate.getFullYear(), myDate.getMonth(), myDate.getDate() - myDate.getDay() + 1)
	}
	flag = Date.parse(nDate) <= Date.parse(sDate);
	return flag
}

function lastweek(value) {
	var flag = false;
	var start = new Date();
	var end = new Date();
	var sDate = new Date();
	sDate.setFullYear(value.substring(0, 4), Number(value.substring(4, 6)) - 1, value.substring(6, 8));
	end.setFullYear(myDate.getFullYear(), myDate.getMonth(), myDate.getDate() - myDate.getDay() - 7);
	start.setFullYear(end.getFullYear(), end.getMonth(), end.getDate() - 6);
	flag = (Date.parse(start) <= Date.parse(sDate)) && (Date.parse(sDate) <= Date.parse(end));
	return flag
}

function lastMon(value) {
	var flag = false;
	var start = new Date();
	var end = new Date();
	var sDate = new Date();
	sDate.setFullYear(value.substring(0, 4), Number(value.substring(4, 6)) - 1, value.substring(6, 8));
	end.setFullYear(myDate.getFullYear(), myDate.getMonth() - 1, myDate.getDate() - myDate.getDay() - 7);
	start.setFullYear(end.getFullYear(), end.getMonth() - 1, end.getDate() - 6);
	flag = (Date.parse(start) <= Date.parse(sDate)) && (Date.parse(sDate) <= Date.parse(end));
	return flag;
}