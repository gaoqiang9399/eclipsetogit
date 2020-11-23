var myDate = new Date();
var allEle, headLen, mcSelector;
var mcTimeline = function(mc) {
  mcSelector = mc;
  mcSelector.mCustomScrollbar({
    scrollButtons: {
      autoHideScrollbar: true,
      scrollAmount: 200
    },
    advanced: {
      autoExpandHorizontalScroll: true,
      updateOnBrowserResize: true,
      updateOnContentResize: false,
      autoScrollOnFocus: true,
      scrollSpeed: 50
    },
    scrollInertia: 1000,
    autoDraggerLength: true,
    callbacks: {
      onScroll: function() {
        myCallback(this)
      },
      whileScrolling: function() {
        myCallback(this);
      },
      alwaysTriggerOffsets: false
    }
  });
}
$(document).ready(function() {

  allEle = $('.task_style');
  headLen = allEle.length;
  var ddArr = [];
  myDate.setFullYear(2015, 8, 20);
  var weekflag = true;
  var monflag = true;
  allEle.each(function(i, list) {
    var ddHtml;
    if (checkWeek($(this).attr('id'))) {
      if (i == 0) {
        ddHtml = '<dd class="time-line-dd">' + '<a data-dit="' + $(this).attr('id') + '" class="time-line-day line-a-on">' + showLocale($(this).attr('id')) + '</a>' + '<span class="time-line-dot line-dot-on fa fa-check"></span>' + '<a data-dit="' + $(this).attr('id') + '" class="time-line-a line-a-on">' + formatStringyyyyMMddToyyyy_MM_dd($(this).attr('id')) + '</a>' + '</dd>';
      } else {
        ddHtml = '<dd class="time-line-dd">' + '<a data-dit="' + $(this).attr('id') + '" class="time-line-day ">' + showLocale($(this).attr('id')) + '</a>' + '<span class="time-line-dot"></span>' + '<a data-dit="' + $(this).attr('id') + '" class="time-line-a">' + formatStringyyyyMMddToyyyy_MM_dd($(this).attr('id')) + '</a>' + '</dd>';
      }
      ddArr.push(ddHtml);
    } else if (lastweek($(this).attr('id')) && weekflag) {
      weekflag = false;
      ddHtml = '<dd class="time-line-dd">' + '<a data-dit="' + $(this).attr('id') + '" class="time-line-a">上周</a>' + '<span class="time-line-dot"></span>' + '</dd>';
      ddArr.push(ddHtml);
    } else if (lastMon($(this).attr('id')) && monflag) {
      monflag = false;
      ddHtml = '<dd class="time-line-dd">' + '<a data-dit="' + $(this).attr('id') + '" class="time-line-a">上月</a>' + '<span class="time-line-dot"></span>' + '</dd>';
      ddArr.push(ddHtml);
    }

  });

  $('.time-line-dl').html(ddArr.join(''));

  $('.time-line-dl').delegate('dd', 'click', function() {
    var index = $('.time-line-dl dd').index($(this));
    var ddId = $(this).find('a').stop().data('dit');
    mcSelector.mCustomScrollbar("scrollTo", "#" + ddId, {
      callback: true
    });
  });
});

function myCallback(el) {
  var mcsTop = el.mcs.top;
  var mscTopPct = el.mcs.topPct;
  var draggerTop = el.mcs.draggerTop;
  for (var i = headLen - 1; i >= 0; i--) {
    // console.log("mcsTop:"+mcsTop);
    // console.log("mscTopPct:"+mscTopPct);
    //  console.log("one:"+allEle.eq(0).offset().top);
    //  console.log("now:"+allEle.eq(i).offset().top);
    //  console.log("------------------------------------")
    if (allEle.eq(i).offset().top <= 0) {
      var index = i;
      var $dd = $('.time-line-dl dd').eq(index);
      $dd.find('a').addClass('line-a-on');
      $dd.find('span').addClass('fa fa-check').addClass('line-dot-on');
      $dd.siblings('dd').find('a').removeClass('line-a-on');
      $dd.siblings('dd').find('span').removeClass('fa fa-check').removeClass('line-dot-on');
      return false;
    }
  }
}



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

function showLocale(objD) {
  var strDate = new Date();
  var yy = Number(objD.substring(4, 6));
  var mm = Number(objD.substring(4, 6)) - 1;
  var dd = Number(objD.substring(6, 8));
  strDate.setFullYear(yy, mm, dd);
  var str;
  // var yy = objD.getYear();
  // if (yy < 1900) yy = yy + 1900;
  // var MM = objD.getMonth() + 1;
  // if (MM < 10) MM = '0' + MM;
  // var dd = objD.getDate();
  // if (dd < 10) dd = '0' + dd;
  // var hh = objD.getHours();
  // if (hh < 10) hh = '0' + hh;
  // var mm = objD.getMinutes();
  // if (mm < 10) mm = '0' + mm;
  // var ss = objD.getSeconds();
  // if (ss < 10) ss = '0' + ss;
  var ww = strDate.getDay();
  if (ww == 0) ww = "Sun";
  if (ww == 1) ww = "Mon";
  if (ww == 2) ww = "Tue";
  if (ww == 3) ww = "Web";
  if (ww == 4) ww = "Thur";
  if (ww == 5) ww = "Fri";
  if (ww == 6) ww = "Sat";
  //  str = colorhead + yy + "年" + MM + "月" + dd + "日" + hh + ":" + mm + ":" + ss + " " + ww + colorfoot;
  str = ww;
  return (str);
}
