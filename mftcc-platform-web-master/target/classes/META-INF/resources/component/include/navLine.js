;
var navLine=function(window, $){
	//时间轴
	var _createNavLine=function(){
		var ddArr = [];
		var len = [];
		var allDD = [];
		$.each($("ol"),function(index,obj){
			allDD.push($(this));
		});
		$("ol").each(function(index,list){
			len.push($(this).find("span[name=title]").html()+"-"+$(this).attr("id"));
		});
		//$(".time-line-line").css("height",(len.length-1)*50);
		
		if(len.length<10){
			$(".time-line-line").css({"height":(len.length-1)*50});
		}else{
			$(".time-line-line").css({"height":450});
		}
		
		$(".time-line-line").css("top","100px");
		for(var i = 0;i<len.length;i++){
			var ddHtml;
			var parm = len[i].split("-");
			ddHtml = '<dd id="item_'+i+'"  index="'+i+'"  class="time-line-dd time-line-point" data-dit="' + parm[1] + '">'
						+ '<a title="'+parm[0]+'" data-dit="' + parm[1]+ '" class="time-line-a" href="#'+parm[1]+'" style="white-space:nowrap">'
						+ '<span class="time-line-dot"><em></em></span>'+ parm[0]
						+ '</a>' + '</dd>';
			ddArr.push(ddHtml);
		}
		
		$('.time-line-dl').css({'height':520,'overflow':'hidden'}).html(ddArr.join(''));
		
		$('.time-line-bg').delegate('.time-line-point', 'click', function(evt) {
			if($(this).hasClass("time-line-point-select")){
				return false;
			}else{
				$('.time-line-bg').find('.time-line-point-select').removeClass('time-line-point-select');
				$(this).addClass('time-line-point-select');
				$('.time-line-bg').find("*").removeClass('line-a-on').removeClass('line-dot-on i i-duihao1');
				$(this).find('a').addClass('line-a-on');
				$(this).find('span').addClass('line-dot-on i i-duihao1');
			}
		});
		//默认选中第一个节点
		$("dd").eq(0).addClass('time-line-point-select');
		$("dd").eq(0).find('a').addClass('line-a-on');
		$("dd").eq(0).find('span').addClass('line-dot-on i i-duihao1');
		// 当前定位到了哪个节点
		var keypoint = 10;
		var p=0,t=0;  
		$(window).scroll(function() {
			 p = $(this).scrollTop();  
			
			for (  var i = allDD.length - 1; i >= 0; i--) {
				if (($(this).scrollTop() + allDD[0].offset().top)> allDD[i].offset().top) {
					var index = i;
					var $dd = $('.time-line-dl dd').eq(index);
					
					$dd.find('a').addClass('line-a-on');
					$dd.find('span').addClass('line-dot-on i i-duihao1');
					$dd.siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
					$dd.siblings('dd').find('span').removeClass('line-dot-on i i-duihao1').removeClass('line-second-dot-on');
					
					// 最后一屏的第一个节点的索引
					var lastFirst =  Math.ceil((allDD.length/10))*10;
                    var j;
                    var length;
					if(t <= p){
						if($dd.attr('index')==keypoint){
							for(j=keypoint-10;j<keypoint;++j){
									$('#item_'+j).slideUp(500);
							}
							
							for(j=keypoint;j<keypoint+10;++j){
								$('#item_'+j).slideDown(500);
							}
							
							keypoint = keypoint+10;
							// 调整最后一屏的时间线的长度
							if(lastFirst ==keypoint){
								// 最后一屏的节点数
								length = allDD.length%10;
								$(".time-line-line").css({"height":(length-1)*50});
							}else{
								$(".time-line-line").css({"height":450});
							}
						}
			        }else{
			        	length = allDD.length%10;
			        	if(length<10 && lastFirst ==keypoint){//最后一屏
			        		$(".time-line-line").css({"height":(length-1)*50});
			        	}else{
			        		$(".time-line-line").css({"height":450});
			        	}
			        	
			        	if($dd.attr('index')==(keypoint-10-1) && $dd.attr('index')!=0 ){
							for(j=keypoint-10;j<keypoint;++j){
								$('#item_'+j).slideUp(500);
							}

							
							var minIndex = 0;
							if(keypoint-10<10){
								minIndex = 10;
							}else{
								minIndex= keypoint-10;
							}
							
							var begingindex = keypoint-10-10;
							if(keypoint-10-10<0){
								begingindex = 0;
							}
							
							for(j=begingindex;j<minIndex;++j){
									$('#item_'+j).slideDown(500);

							}
								
								keypoint = keypoint-10;
								if(keypoint<10){
									keypoint = 10;
								}
						}
			        }  
			              
					
					setTimeout(function(){t = p;},0);      
					
					
					return false;
				}
			}
		});
	};
	return{
		createNavLine:_createNavLine
	};
}(window, jQuery);