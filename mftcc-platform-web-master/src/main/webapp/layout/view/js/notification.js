/**
 * Created by Let Aurn IV on 22/09/2015.
 */

/*global  $*/

Notification = window.Notification || {};

Notification = function () {
    'use strict';
    var animEndEventNames = {
    		'WebkitAnimation' : 'webkitAnimationEnd',
			'OAnimation' : 'oAnimationEnd',
			'msAnimation' : 'MSAnimationEnd',
			'animation' : 'animationend'
          };
    var st;
    var state ="create";
    var ishidden = true;
    var $notification=null;
    var template = function (msgCount, title, text, position) {
        var textHtml = '<div class="text">' + text + '</div>';
        var titleHtml = (!msgCount ? 1 : '<div class="title">您有<span class="msgCount">' + msgCount + '</span>条新'+title+'</div>');
        return {
            content: '<div class="notification" style="bottom:20px; left:70px;background:#a3e1f6;">' +
                '<div class="dismiss">&#10006;</div>' +
                '<div class="text">' + titleHtml + textHtml + '</div>' +
                '</div>'
        };
    };
    
    var hide = function () {
    	if($notification){
    		$notification.addClass("animated fadeOutDown");
    		addmsgNum();
    		state = "remove";
    	}
    };
    
    
    var create = function (msgCount,title, text, animation, position) {
    	state = "create";
    	if($notification){
    		var $msgCount = $notification.find(".msgCount").eq(0);
    		$notification.addClass("animated tada");
    		$msgCount.html(parseInt($msgCount.html())+1);
    	}else{
    		var notification = template(msgCount, title, text, position);
            $(notification.content).appendTo('body');
            $notification =  $(".notification").eq(0);
            var el = $notification[0];
            var transitionEvent="";
            for(var t in animEndEventNames){
                if( el.style[t] !== undefined ){
                	transitionEvent =  animEndEventNames[t];
                }
            }
            $notification.addClass('animated ' + animation);
            $notification.bind(transitionEvent,function() {
            	$(this).attr("class","notification");
            	if(state=="remove"){
            		$(this).remove();
            		$notification=null;
            		return false;
            	}
            	clearTimeout(st);
            	st = setTimeout(function () {
                    hide();
                }, 3000);
    		} );
            $notification.find(".dismiss").eq(0).bind("click",function(event){
            	hide();
            	clearTimeout(st);
            	event.stopPropagation();
            });
            $notification.bind("click",function(event){
            	rzzl.turnAorB(1);
            	hide();
            	clearTimeout(st);
            });
    	}
    };
    
   var addmsgNum = function(){
	  var $msgCount = $notification.find(".msgCount").eq(0);
	  var position = $notification.find(".msgCount").eq(0).position();
	  var msgcnt = $('<span id="msgcnt" class="msgCount">'+$msgCount.html()+'</span>');
	  msgcnt.css({
		  "top":position.top+$notification.position().top,
		  "left":position.left+$notification.position().left,
		  "width":$msgCount.width(),
		  "height":$msgCount.height(),
		  "font-weight": "700",
		  "position": "absolute",
	  	  "display": "block",
	  	  "text-align": "center",
	  	  "background-color": "#3eb0ed",
	  	  "border-radius": "40%"
	  });
	  msgcnt.appendTo("body");
	  
	  var $msgNum = $(".msgNum");
	  var endX = $msgNum.offset().left;
	  var endY = $msgNum.offset().top;
	  var endW = $msgNum.width();
	  var endH = $msgNum.height();
	  var fontSize = $msgNum.css("fontSize");
	  msgcnt.animate({
	    left:endX+'px',
	    top:endY+'px',
	    //opacity:'0.5',
	    width:endW+'px',
	    height:endH+'px',
	    fontSize:fontSize,
	    backgroundColor: "#105E7C"
	  },function(){
		  msgcnt.addClass("ripple");
		  $msgNum.html(parseInt($msgNum.html())+parseInt($msgCount.html()));
		  msgcnt.html($msgNum.html());
		  setTimeout(function () {
			  msgcnt.remove();
		  }, 750);
	  });
    };
    
    return {
        create: create
    };

}();
