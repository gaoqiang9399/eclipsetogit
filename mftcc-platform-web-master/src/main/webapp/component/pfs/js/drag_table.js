	//jquery与dwr.util冲突处理
	var jq = jQuery.noConflict();

	/*--------全局变量-----------*/
	var x0,y0,x1,y1;
	var movable = false;
	var preCell = null;
	var normalColor = null;
	var preColor = "lavender";
	var endColor = "#cccccc";
	/*--------全局变量-----------*/

	//得到控件的绝对位置
	function getPos(cell) {
		var pos = new Array();
	 	var t = cell.offsetTop;
	 	var l = cell.offsetLeft;
	 	while(cell = cell.offsetParent) {
	  		t += cell.offsetTop;
	  		l += cell.offsetLeft;
	 	}
	 	pos[0] = t - document.getElementById("man_zone").scrollTop;
	 	pos[1] = l;
	 	return pos;
	}

	//显示图层
	function showDiv() {
		var obj = event.srcElement; 
	 	var pos = new Array(); 
	 	//获取过度图层
	 	var oDiv = document.all.tempDiv;
	 	if(obj.tagName.toLowerCase() == "td" 
	 		&& obj.getAttribute("movable") != "no") {
	  		obj.style.cursor = "hand";
	  		pos = getPos(obj);
	  		//计算中间过度层位置，赋值
	  		oDiv.style.width = obj.offsetWidth;
	  		oDiv.style.height = obj.offsetHeight;
	  		oDiv.style.top = pos[0];
	  		oDiv.style.left = pos[1];
	  		oDiv.innerHTML = obj.innerHTML;
	  		oDiv.style.display = "";
			x0 = pos[1];
			y0 = pos[0];
			x1 = event.clientX;
			y1 = event.clientY;
			//记住原td
			normalColor = obj.style.backgroundColor;
			obj.style.backgroundColor = preColor;
			preCell = obj;
			movable = true;
	 	}
	}

	function dragDiv() {
		if(movable) {
	  		var oDiv = document.all.tempDiv;
	  		var pos = new Array();
	  		oDiv.style.top = event.clientY - y1 + y0;
	  		oDiv.style.left = event.clientX - x1 + x0;
	  		var oTable = document.all.dataTable; 
	  		for(var i = 0; i < oTable.cells.length; i++) {
	   			if(oTable.cells[i].tagName.toLowerCase() == "td" && oTable.cells[i].getAttribute("movable") != "no") {
	    			pos = getPos(oTable.cells[i]);
	    			if(event.x>pos[1]&&event.x<pos[1]+oTable.cells[i].offsetWidth 
	       				&& event.y>pos[0]&& event.y<pos[0]+oTable.cells[i].offsetHeight) {
		     			if(oTable.cells[i] != preCell) oTable.cells[i].style.backgroundColor = endColor;     
		    		} else {
		     			if(oTable.cells[i] != preCell) oTable.cells[i].style.backgroundColor = normalColor;
		    		}
	   			}
	  		}
	 	}
	}

	function hideDiv() {
		if(movable) {
	  		var oTable = document.all.dataTable;
	  		var pos = new Array(); 
	  		if(preCell != null) {
	   			for(var i=0; i<oTable.cells.length; i++) {   
					pos = getPos(oTable.cells[i]);
					//计算鼠标位置，是否在某个单元格的范围之内
					if(event.x>pos[1]&&event.x<pos[1]+oTable.cells[i].offsetWidth 
	     				&& event.y>pos[0]&& event.y<pos[0]+oTable.cells[i].offsetHeight) {
	     				if(oTable.cells[i].tagName.toLowerCase() == "td" && oTable.cells[i].getAttribute("movable") != "no") {
	      					//交换文本
	      					preCell.innerHTML = oTable.cells[i].innerHTML;
	      					oTable.cells[i].innerHTML = document.all.tempDiv.innerHTML;
	      					//清除原单元格和目标单元格的样式
	      					preCell.style.backgroundColor = normalColor;
	      					oTable.cells[i].style.backgroundColor = normalColor;
	      					oTable.cells[i].style.cursor = "";
	      					preCell.style.cursor = "";
	      					preCell.style.backgroundColor = normalColor;
	     				}
	    			}
	   			}
	  		}
			movable = false;
	  		//清除提示图层
	  		document.all.tempDiv.style.display = "none";  
	 	}
	}

	document.onmouseup = function() {
		hideDiv();
		var oTable = document.all.dataTable;
		for(var i=0; i<oTable.cells.length; i++)
		  	oTable.cells[i].style.backgroundColor = normalColor;
	}

	document.onmousemove = function() {
		dragDiv();
	}

	//财务报表模型拖拽编辑保存
	function func_save() {
		var text1 = "", text2 = "", text3 = "", text4 = "";
	    var str_code_name = "", str_cnts = "";
	    jq(("#data_tbody>tr")).each(function() {
	    	text1 = jq(this).find("td:eq(0)").html();
	    	if( text1 != "&nbsp;" ) {
	    		str_code_name += text1 + ",";
	    	}
	    	text2 = jq(this).find("td:eq(1)").html();
	    	if( text2 != "&nbsp;" ) {
	    		str_cnts += text2 + ",";
	    	}
	    	var text3 = jq(this).find("td:eq(2)").html();
	    	if( text3 != "&nbsp;" ) {
	    		str_code_name += text3 + ",";
	    	}
	    	text4 = jq(this).find("td:eq(3)").html();
	    	if( text4 != "&nbsp;" ) {
	    		str_cnts += text4 + ",";
	    	}
	    });
	    if( str_cnts.length <= 0 ) {
	    	alert("对不起,没有符合更新条件的数据!");
	    	return false;
	    }
	    jq("#cusFinModel_codeName").val(str_code_name);
	    jq("#cusFinModel_cnts").val(str_cnts);
	    document.operform.action = '/factor/cusFinModel/updateDragFinModel';
	    document.operform.submit();
	}