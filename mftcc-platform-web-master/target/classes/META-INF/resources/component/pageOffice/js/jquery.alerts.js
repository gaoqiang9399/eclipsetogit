/*
*  jquery  提示信息插件
*  刘沛整理 时间2010年11月3日  
*/
(function($) {
	$.alerts = {
		// 下面的这些属性可以在js中通过$.alerts.propertyName 进行修改  如：$.alerts.okButton='3333';  把确定按钮的文本修改为3333
		
		verticalOffset: -75,                // 设置提示信息的垂直位置

		horizontalOffset: 0,                // 设置提示信息的水平位置

		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .34,                // 设置遮罩层的透明度

		overlayColor: '#000000',               // 设置遮罩层的颜色
		draggable: true,                    // 设置对话框是否可以拖动(需要jquery ui 插件)
		okButton: '&nbsp;\u786E\u5B9A&nbsp;',        // 确定按钮的文本值

		cancelButton: '&nbsp;\u53d6\u6d88&nbsp;',    // 取消按钮的文本值

		dialogClass: null,                  //  自定义样式 
		fromCabinet:false,               //从文件柜中过来

		
		// 公共方法
		
		alert: function(str,message, title, callback) {
			
			//if( title == null ) title = "系统提示";
			//title="系统提示";
			title="";
			var alerttype = 'alert'
			if(str=='0'){
				alerttype = 'error';
				}
			if(str=='2'){
				alerttype = 'waring';
			}
			
			if(message.lastIndexOf("\u5931\u8D25")!=-1){
				alerttype = 'error';
			}
			$.alerts._show(title, message, null, alerttype, function(result) {
				if( callback ) callback(result);
			});
			
			var tmpAry=message.split("br");
			if(tmpAry.length>3)
			{
				$("#popup_message").css("margin-top","0px");
				$("#popup_message").css("height","80px");
				$("#popup_message").css("margin-bottom","5px");
				
			}
			
		},
		
	
		
		confirm: function(message, title, callback) {
			if( title == null ) title = 'Confirm';
			$.alerts._show("", message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			});
		},
			
		prompt: function(message, value, title, callback) {
			if( title == null ) title = 'Prompt';
			$.alerts._show(title, message, value, 'prompt', function(result) {
				if( callback ) callback(result);
			});
		},
		
		// 私有方法
		
		_show: function(title, msg, value, type, callback) {
			
			$.alerts._hide();
			$.alerts._overlay('show');
			
			$("body").append(
			  '<div id="popup_container">' +
			    '<div id="popup_title"></div>' +
			    '<div id="popup_content">' +
			      '<div id="popup_message"></div>' +
				'</div>' +
			  '</div>');
			
			if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);
			
			// IE6 Fix
			
			var pos = ($.browser.msie && parseInt($.browser.version) <= 6 ) ? 'absolute' : 'fixed'; 
			
			$("#popup_container").css({
				position: pos,
				zIndex: 999999,
				padding: 0,
				margin: 0
			});
			
			$("#popup_title").text(title);
			$("#popup_content").addClass(type);
			$("#popup_message").text(msg);
			$("#popup_message").html( $("#popup_message").text().replace(/\n/g, '<br />') );
			
			$("#popup_container").css({
				minWidth: $("#popup_container").outerWidth(),
				maxWidth: $("#popup_container").outerWidth()
			});
			
			$.alerts._reposition();
			$.alerts._maintainPosition(true);
			switch( type ) {
				
				case 'alert':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if(e.keyCode==32){alert("\u975E\u6B63\u5E38\u64CD\u4F5C");}
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				
				case 'error':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					if(!$.alerts.fromCabinet){
					 $("#popup_ok").focus().keypress( function(e) {
						 if(e.keyCode==32){alert("\u975E\u6B63\u5E38\u64CD\u4F5C");}
						 if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					 });
					}
				break;
				
				case 'waring':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						callback(true);
					});
					if(!$.alerts.fromCabinet){
					$("#popup_ok").focus().keypress( function(e) {
						if(e.keyCode==32){alert("\u975E\u6B63\u5E38\u64CD\u4F5C");}
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
					}
				break;
				
				case 'confirm':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						if( callback ) callback(true);
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback(false);
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress( function(e) {
						if(e.keyCode==32){alert("\u975E\u6B63\u5E38\u64CD\u4F5C");}
						if( e.keyCode == 13) $("#popup_ok").trigger('click');
						if( e.keyCode == 27) $("#popup_cancel").trigger('click');
						
					});
				break;
				case 'prompt':
					$("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
					$("#popup_prompt").width( $("#popup_message").width() );
					$("#popup_ok").click( function() {
						var val = $("#popup_prompt").val();
						$.alerts._hide();
						if( callback ) callback( val );
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						if( callback ) callback( null );
					});
					$("#popup_prompt, #popup_ok, #popup_cancel").keypress( function(e) {
						if(e.keyCode==32){alert("\u975E\u6B63\u5E38\u64CD\u4F5C");}
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
					if( value ) $("#popup_prompt").val(value);
					$("#popup_prompt").focus().select();
				break;
			}
			
			// Make draggable
			if( $.alerts.draggable ) {
				try {
					$("#popup_container").draggable({ focuEle: "#popup_title" });
					//$("#popup_title").css({ cursor: 'move' });
				} catch(e) { /* requires jQuery UI draggables */ }
			}
		},
		
		_hide: function() {
			$("#popup_container").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
		},
		
		_overlay: function(status) {
			switch( status ) {
				case 'show':
					$.alerts._overlay('hide');
					$("body").append('<div id="popup_overlay"></div>');
					if(($.browser.msie && parseInt($.browser.version) <= 6 )){
						$("#popup_overlay").css({
						position: 'absolute',
						zIndex: 999989,
						top: '0px',
						left: '0px',
						width: '100%',
						height: $(document).height(),//
						opacity: $.alerts.overlayOpacity
					});
					}else{
						$("#popup_overlay").css({
						position: 'absolute',
						zIndex: 999989,
						top: '0px',
						left: '0px',
						width: '100%',
						height: $(document).height(),
						background: $.alerts.overlayColor,// 在ie6 下会引起空白页面
						opacity: $.alerts.overlayOpacity
					});
					}
					
				break;
				case 'hide':
					$("#popup_overlay").remove();
				break;
			}
		},
		
		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
			
			// IE6 fix
			if( $.browser.msie && parseInt($.browser.version) <= 6 ) top = top + $(window).scrollTop();
			
			$("#popup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#popup_overlay").height($(document).height());
		},
		
		_maintainPosition: function(status) {
			if($.alerts.repositionOnResize) {
				switch(status) {
					case true:
						$(window).bind('resize', function() {
							//$.alerts._reposition();//草川禾 20111122 修改 禁止窗口重设置大小重新定位. 会影响到流程图alert移动
						});
					break;
					case false:
						$(window).unbind('resize');
					break;
				}
			}
		}
		
	}
	
	// 对方法

	jAlert = function(str,message, title, callback) {
		if(typeof message=='string'){
			message=convertStr(message);
			$.alerts.alert(str,message, title, callback);
		}
		if(typeof message=='object'){//{'id':'',"parm0":"0","parm1":"1"}
			//1 根据messageNo 找到对应的消息内容

			var msgStr=mes[message["id"]];
			//替换message中的参数
			for(i in message){
				if(i!='id'){
					msgStr=msgStr.replace(/parm[0-9]/g,message[i]);
				}
			}
			msgStr=convertStr(msgStr);
			msgStr=message.id+":"+msgStr;
			$.alerts.alert(str,msgStr, title, callback);
		}
	}

	
	jConfirm = function(message, title, callback) {
		if(typeof message=='string'){
			message=convertStr(message);
			$.alerts.confirm(message, title, callback);
		}
		if(typeof message=='object'){//{'id':'',"parm0":"0","parm1":"1"}
			//1 根据messageNo 找到对应的消息内容

			var msgStr=mes[message["id"]];
			//替换message中的参数
			for(i in message){
				if(i!='id'){
					msgStr=msgStr.replace(/parm[0-9]/,message[i]);
				}
			}
			msgStr=convertStr(msgStr);
			msgStr=message.id+":"+msgStr;
			$.alerts.confirm(msgStr, title, callback);
		}
	};
		
	jPrompt = function(message, value, title, callback) {
		if(typeof message=='string'){
			message=convertStr(message);
			$.alerts.prompt(message, value, title, callback);
		}
		if(typeof message=='object'){//{'id':'',"parm0":"0","parm1":"1"}
			//1 根据messageNo 找到对应的消息内容

			var msgStr=mes[message["id"]];
			//替换message中的参数
			for(i in message){
				if(i!='id'){
					msgStr=msgStr.replace(/parm[0-9]/g,message[i]);
				}
			};
			msgStr=convertStr(msgStr);
			msgStr=message.id+":"+msgStr;
			$.alerts.prompt(msgStr, value, title, callback);
		}
	};
	
	function convertStr(message){
		if(message.lastIndexOf("\uFF01")!=-1){
			message=message.replace("\uFF01","\u3002");
		}
		
		if(message.lastIndexOf("\21")!=-1){
			message=message.replace("\21","\u3002");
		}
		if(message.lastIndexOf(".")==message.length-1){
			message=message.replace(".","\u3002");
		}		
		if(message.indexOf("<strong>")!=-1){
			message=message.replace(/<strong>/g,"");
		}
		
		if(message.lastIndexOf("</strong>")!=-1){
			message=message.replace(/<\/strong>/g,"");
		}
		
		if(message.indexOf("<b>")!=-1){
			message=message.replace(/<b>/g,"");
		}
		
		if(message.lastIndexOf("</b>")!=-1){
			message=message.replace(/<\/b>/g,"");
		}
		
		if(message.indexOf("\u4F60")!=-1){
			message=message.replace(/\u4F60/g,"");
		}
		
		if(message.indexOf("\u60A8")!=-1){
			message=message.replace(/\u60A8/g,"");
		}
		return message;
	}
	$(window).scroll(function(){
     	if($("body").scrollTop != 0)
        {	var topd=$(document).height();
        	$("#popup_overlay").height($(document).height());
        }
    })
	
})(jQuery);

