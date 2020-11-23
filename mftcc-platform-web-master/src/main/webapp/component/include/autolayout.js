function autolayout(){
	var fw = $(".layout #freewall");
	var fwW,fwH,dmW,dmH;
	dmW = window.innerWidth;
	dmH = window.innerHeight;
	fwW = fw.width();
	fwH = fw.height();
	if(fw.length==1){
		var cells = fw.children(".cell");
		var left = -1,top = -1,width = 0,height = 0;
		var len = cells.length;
        var i;
		for(i=0;i<cells.length;i++){
			if(cells.eq(i).css("top").split("p")[0]=="0"&&parseInt(cells.eq(i).css("left").split("p")[0])>left){
				left = parseInt(cells.eq(i).css("left").split("p")[0]);
				width = parseInt(cells.eq(i).css("width").split("p")[0]);
			}
			if(cells.eq(i).css("left").split("p")[0]=="0"&&parseInt(cells.eq(i).css("top").split("p")[0])>top){
				top = parseInt(cells.eq(i).css("top").split("p")[0]);
				height = parseInt(cells.eq(i).css("height").split("p")[0]);
			}
		}
		var maxWidth;
		var maxHeight;
		if(typeof(rp)=="undefined"){
			maxWidth = left + width;
			maxHeight = top + height;
		}else{
			maxWidth = left + width;
			maxHeight = fw.height();
		}
		
		for(i=0;i<cells.length;i++){
			var cleft,ctop,cwidth,cheight;
			cleft = parseInt(cells.eq(i).css("left").split("p")[0]);
			ctop = parseInt(cells.eq(i).css("top").split("p")[0]);
			cwidth = parseInt(cells.eq(i).css("width").split("p")[0]);
			cheight = parseInt(cells.eq(i).css("height").split("p")[0]);
			var divcss= {
					"left":cleft/maxWidth*100+"%",
					"top":ctop/maxHeight*100+"%",
					"width":cwidth/maxWidth*100+"%",
					"height":cheight/maxHeight*100+"%"
			}
//			cells.eq(i).css({
//				width:"0px",
//				height:"0px"
//			}).show();
			cells.eq(i).animate(divcss,function(){
				var info = $(this).find(".info");
				if($(this).find(".cover").length>0){
					info.css({
						"height":($(this).height()-$(this).find(".cover").outerHeight(true))+"px",
						"width":($(this).width())+"px"
					})
				}else{
					info.css({
						"height":"100%",
						"width":"100%",
						"top":"0px",
						"margin":"0px"
					});
				}
			});
			
		}
	}
}

function autolayoutIE(){
	var fw = $(".layout #freewall");
	var fwW,fwH,dmW,dmH;
	dmW = window.innerWidth;
	dmH = window.innerHeight-fw.parent()[0].offsetTop;
	var pMw = parseInt(fw.css("marginLeft"))+parseInt(fw.css("marginRight"));
	var pMh = parseInt(fw.css("marginTop"))+parseInt(fw.css("marginBottom"));
	fw.parent().css({
		"height":(dmH)+"px",
		"width":(dmW)+"px"
		});
	fw.css({
		"height":(dmH-pMh)+"px",
		"width":(dmW-pMw)+"px"
		});
	fwW = fw.width();
	fwH = fw.height();
	if(fw.length==1){
		var cells = fw.children(".cell");
		var left = -1,top = -1,width = 0,height = 0;
		var len = cells.length;
        var i;
		for(i=0;i<cells.length;i++){
			if(cells.eq(i).css("top").split("p")[0]=="0"&&parseInt(cells.eq(i).css("left").split("p")[0])>left){
				left = parseInt(cells.eq(i).css("left").split("p")[0]);
				width = parseInt(cells.eq(i).css("width").split("p")[0]);
			}
			if(cells.eq(i).css("left").split("p")[0]=="0"&&parseInt(cells.eq(i).css("top").split("p")[0])>top){
				top = parseInt(cells.eq(i).css("top").split("p")[0]);
				height = parseInt(cells.eq(i).css("height").split("p")[0]);
			}
		}
		var maxWidth = left + width;
		var maxHeight = top + height;
		for(i=0;i<cells.length;i++){
			var cleft,ctop,cwidth,cheight;
			cleft = parseInt(cells.eq(i).css("left").split("p")[0]);
			ctop = parseInt(cells.eq(i).css("top").split("p")[0]);
			cwidth = parseInt(cells.eq(i).css("width").split("p")[0]);
			cheight = parseInt(cells.eq(i).css("height").split("p")[0]);
			var divcss= {
					"left":cleft/maxWidth*100+"%",
					"top":ctop/maxHeight*100+"%",
					"width":cwidth/maxWidth*100+"%",
					"height":cheight/maxHeight*100+"%"
			}
//			cells.eq(i).css({
//				width:"0px",
//				height:"0px"
//			}).show();
			cells.eq(i).animate(divcss,function(){
				var info = $(this).find(".info");
				if($(this).find(".cover").length>0){
					info.css({
						"height":($(this).height()-$(this).find(".cover").outerHeight(true))+"px",
						"width":($(this).width())+"px"
					})
				}else{
					info.css({
						"height":"100%",
						"width":"100%",
						"top":"0px",
						"margin":"0px"
					});
				}
			});
		}
	}
	$("body").css("overflow","hidden");
}

function resDispaly(elem,parm){
	var $elem = $(elem);
	var $cells = $elem.find(".cell");
	if($cells.length==0){
		return false;
	}
	$cells.addClass("cell-dis");
	$.each($cells,function(index,cell){
		var arr = resParm($(cell).data("dis"));
		$.each(arr,function(k1,v1){
			$.each(parm,function(k2,v2){
				if(k1==k2&&$.inArray(v2,v1)>-1){
					$(cell).removeClass("cell-dis");
					console.log(cell);
				}
			});
		});
	});
}

function resParm(str){
	var o = {};
	var tem = [];
	if(str.indexOf("|")>-1){
		tem = str.split("|");
	}
	for(var i = 0 ;i<tem.length;i++){
		if(tem[i] == "" || typeof(tem[i]) == "undefined"||tem[i].indexOf("-")==-1){
        	 tem.splice(i,1);
             i= i-1;
        }else{
        	var t = tem[i].split("-");
        	if(typeof(o[t[0]])=="undefined"){
        		o[t[0]] = [];
        		o[t[0]].push(t[1]);
        	}else{
        		o[t[0]].push(t[1]);
        	}
        }
	 }
	return o;
}







