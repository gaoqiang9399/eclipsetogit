function VFrameResizeH(id){
	var frame=document.getElementById(id);
	frame.height = "100px";
	$(frame).load(function(){
		alert(this.get(0).tagName);
		frame.height = (($(frame).contents().find("body").height() + 20) + "px");
	});
}
/**
 * nouse
 * @param {Object} id
 */
function VFrameResizeHIE(id){
	var frame=document.getElementById(id);
	if(frame.readyState=="complete")  {
        var v_height;
		if (frame.contentDocument && frame.contentDocument.body.offsetHeight){
			v_height = frame.contentDocument.body.offsetHeight + 20;
			frame.height = (v_height + "px");
		}else if(frame.Document && frame.Document.body.scrollHeight){
			v_height = frame.Document.body.scrollHeight + 20;
			frame.height = (v_height + "px");
		}
	} else {
		setTimeout("VFrameResizeHIE('"+id+"')",10);
	}
}
(function($){
	$.fn.VTabChange = function(){
        var VTab,frame;
		if(this.attr("class").indexOf("tab_mid_on")>=0){
			VTab = this.parent("td").parent("tr").parent("tbody").parent("table");
		    frame = $("iframe[targetId="+VTab.attr("id")+"]");
		    var f=frame[0];
		    if(f.attributes["sts"] == undefined || f.attributes["sts"]==null){
		    	f.setAttribute("sts","close");
		    	f.setAttribute("big-height",f.height);
		    	f.height="1px";
		    } else if(f.attributes["sts"].nodeValue=="open") {
		    	f.setAttribute("sts","close");
		    	f.setAttribute("big-height",f.height);
		    	f.height="1px";
		    } else {
		    	f.setAttribute("sts","open");
		    	f.height=f.attributes["big-height"].nodeValue;
		    }
			return false;
		}
		VTab = this.parent("td").parent("tr").parent("tbody").parent("table");
		frame = $("iframe[targetId="+VTab.attr("id")+"]");
		var tab_on = this.siblings(".tab_mid_on");
		tab_on.removeClass("tab_mid_on");//移除生效样式
		tab_on.addClass("tab_mid");//写入正常tab样式
		this.removeClass("tab_mid");//移除正常tab样式
		this.addClass("tab_mid_on");//写入生效样式
		frame.attr("src", this.attr("taburl"));
		//VFrameResizeH(frame.attr("id"));
	},
	$.fn.VTabPane = function(){
		var tabIndex = 0;
		var tableId = this.attr("id");
		var frame = $("iframe[targetId="+tableId+"]");
		frame.attr("id","frame"+Math.random());
		this.find("label.tab_mid").each(function(){
			$(this).bind("click", function(){
				$(this).VTabChange();
			});
			if(tabIndex==0){
				defaultLab = $(this);
				defaultLab.removeClass("tab_mid");
				defaultLab.addClass("tab_mid_on");
				frame.attr("src", defaultLab.attr("taburl"));
				//VFrameResizeH(frame.attr("id"));
			}
			tabIndex++;
		});
	}
})(jQuery);