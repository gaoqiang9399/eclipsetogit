;
var DIALOG = (function(window, $, dialog, undefined) {

	var windowFeaturesMap = {
		dialogHeight : "height",// 对话框高度，不小于１００px，ＩＥ４中dialogHeight和dialogWidth默认的单位是em，而ＩＥ５中是px，为方便其见，在定义modal方式的对话框时，用px做单位。
		dialogWidth : "width",// 对话框宽度。
		dialogLeft : "left",// 距离桌面左的距离。
		dialogTop : "top",// 离桌面上的距离。
		resizable : "resize"
	};
	var valuesMap = {
		"yes" : true,
		"no" : false,
		"1" : true,
		"0" : false,
		"on" : true,
		"off" : false
	};
	var _handlerFeatures = function(options, features) {
		// 处理老式传递的features，转换为dialog的参数。
		if (typeof (features) === "string") {
			features = features.replace(/：|=/g, ":").replace(/；/g, ";")
			var tempArray = features.split(";");
			for ( var i = 0; i < tempArray.length; i++) {
				if ($.trim(tempArray[i]).length == 0) {
					continue;
				}
				var kv = tempArray[i].split(":");
				var key = windowFeaturesMap["" + kv[0]]; // .toLowerCase();
				var val = valuesMap[kv[1]] || kv[1];
				if (!!key) {
					options[key] = val;
				} else {
					options[kv[0]] = val;
				}
			}
		} else if (typeof (arguments) === "object") {
			// TODO 处理扩展参数
		}
	};
	
	/** 函数：获取尺寸，入参传入一个window对象，不传则获取top的宽高。*/
	function _findDimensions(aWindow)
	{
		if (!aWindow && typeof(aWindow) != "object") {
			aWindow = top || window;
		}
		// 获取窗口宽度
		if (aWindow.innerWidth)
			winWidth = aWindow.innerWidth;
		else if ((aWindow.document.body) && (aWindow.document.body.clientWidth))
			winWidth = aWindow.document.body.clientWidth;
		// 获取窗口高度
		if (aWindow.innerHeight)
			winHeight = aWindow.innerHeight;
		else if ((aWindow.document.body) && (aWindow.document.body.clientHeight))
			winHeight = aWindow.document.body.clientHeight;
		// 通过深入Document内部对body进行检测，获取窗口大小
		if (aWindow.document.documentElement && aWindow.document.documentElement.clientHeight && aWindow.document.documentElement.clientWidth) {
			winHeight = aWindow.document.documentElement.clientHeight;
			winWidth = aWindow.document.documentElement.clientWidth;
		}
		// 结果
		return {width:winWidth, height:winHeight};
	}

	var _showModelessDialog = function(sURL, argument, sFeatures, callback) {

		// 窗口传参挂载到top中。
		top.dialogArguments = argument;

		// 默认全屏，以及两个事件函数。
		var opts = {
//			id : "myModalDialog",
			url : sURL,
			title : " ",
			padding : 3,

			width : sFeatures.width||'',
			height : sFeatures.height||''
		};
		// 处理传入参数。
		if (sFeatures) {
			_handlerFeatures(opts, sFeatures);
		}
		// 增加回调方法。
		if (typeof (callback) === "function") {
			opts["onclose"] = function( ) {
				var val = top.returnValue;
				top.returnValue = undefined;
				callback(val);
			};
		}

		// 控制宽度不超出窗口大小。
		var winDimensions = _findDimensions();
		var oldWidth = opts["width"].replace("px","");
		if (!isNaN(oldWidth) && parseInt(oldWidth) > (winDimensions.width - 40)) {
			opts["width"] = winDimensions.width - 40;
		}
		// 打开窗口
		var d = top.dialog(opts);
		d.showModal();
	};
	
	/**
	 * iconType： 0-没有图标，1-提示图标，2-错误图标，
	 * 0-error;1-自动消失的提示;2-确定/取消，询问类;3-提示信息，不消失;4-警告类，warn。
	 */
	var __makeHTML = function(iconType, aMessage) {
		var html = 
			"<table>" +
				"<tr>" +
					"<td style='vertical-align:middle;'>" +
						"$icon$" +
					"</td>" +
					"<td style='vertical-align:middle;'>" +
						"<div style='font-size: 16px; min-width: 200px; max-width: 1024px;word-break: break-all;'>" +
							"$message$" +
						"</div>" +
					"</td>" +
				"</tr>" +
			"</table>";
		
		switch (iconType) {
		case 0: //"error":
			html = html.replace("$icon$", "<i class='i i-x5' style='color:#e6563e; margin-right: 10px; font-size:24px;'></i>");
			break;
		case 1: //"tip":
			html = html.replace("$icon$", "<i class='i i-tanhao2' style='color:#4889FF; margin-right: 10px; font-size:24px;'></i>");
			break;
		case 2: //"confirm":
			html = html.replace("$icon$", "<i class='i i-wenhao1' style='color:#4889FF; margin-right: 10px; font-size:24px;'></i>");
			break;
		case 3: //"msg":
			html = html.replace("$icon$", "<i class='i i-tanhao2' style='color:#4889FF; margin-right: 10px; font-size:24px;'></i>");
			break;
		case 4: //"warn":
			html = html.replace("$icon$", "<i class='i i-tanhao1' style='color:#F99F2B; margin-right: 10px; font-size:24px;'></i>");
			break;
		default:
			// 没有图标。
			html = html.replace("$icon$", "");
			break;
		}
		html = html.replace("$message$", aMessage || top.getMessage("NONE"));
		
		return html;
	};

	var defaultOptions = {
		//id : "dialogAlert"
		//,
		title : "系统提示"
		,padding : 25
		,okValue : "确定"
		,cancelValue : "取消"
		
	};
	/**
	 * 拼装dialog的默认参数、特有参数、和自定义的扩展参数。
	 */
	var _makeOptions = function (options, extendOptions) {
		var opts = {};
		
		$.extend(opts, defaultOptions);
		
		if (options && typeof options === "object") {
			$.extend(opts, options);
		}
		if (extendOptions && typeof extendOptions === "object") {
			$.extend(opts, extendOptions);
		}
		
		return opts;
	};
	
	/**
	 * _msg弹窗 普通消息，不自动关闭弹窗。
	 * @param aMessage 消息内容，任意artDialog能支持的内容。
	 * @param 
	 */
	var _msg = function (aMessage, callback, options) {
		var contentHtml = __makeHTML(3, aMessage);
		
		var executable = function () {
			if (typeof callback === "function") {
				callback();
			}
		};
		
		var opts = {
			content : contentHtml
			,ok : executable
		};
		opts = _makeOptions(opts, options);
		
		var d = top.dialog(opts);
		d.showModal();
	};
	
	var _error = function (aMessage, callback, options) {
		var contentHtml = __makeHTML(0, aMessage);
		
		var executable = function () {
			if (typeof callback === "function") {
				callback();
			}
		};
		var opts = {
			content : contentHtml
			,ok : executable
			,cancel: false
		};
		opts = _makeOptions(opts, options);
		
		var d = top.dialog(opts);
		d.showModal();
	};
	
	var _warn = function (aMessage, callback, options) {
		var contentHtml = __makeHTML(4, aMessage);
		
		var executable = function () {
			if (typeof callback === "function") {
				callback();
			}
		};
		var opts = {
			content : contentHtml
			,ok : executable
			,cancel: false
		};
		opts = _makeOptions(opts, options);
		
		var d = top.dialog(opts);
		d.showModal();
	};
	
	var _tip = function (aMessage, time, targetDOM, options) {
		var contentHtml = __makeHTML(1, aMessage);
		var opts = {
			content : contentHtml
//			,quickClose: true// 点击空白处快速关闭
		};
		opts = _makeOptions(opts, options);
		
		var d = top.dialog(opts);
		d.show(targetDOM);
		
		if (typeof time === "number" && time > 0) {
			setTimeout(function () {
				if (d) {
					d.close().remove();
				}
			}, time);
		}
	};
	
	var _confirm = function (aMessage, okCallback, cancelCallback, options) {
		var contentHtml = __makeHTML (2, aMessage);
		var opts = {
			content : contentHtml
			,ok: function(){}
			,cancel: function(){}
		};
		if (typeof okCallback === "function") {
			opts.ok = okCallback;
		}
		if (typeof cancelCallback === "function") {
			opts.cancel = cancelCallback;
		}

		opts = _makeOptions(opts, options);
		
		var d = top.dialog(opts);
		d.showModal();
	};
	/**
	 * type:0-error;1-自动消失的提示;2-确定/取消，询问类;3-提示信息，不消失;4-警告类，warn。
	 */
	var _alert = function (aMessage, type, okCallback, cancelCallback) {
		switch (type) {
		case 0:
			_error(aMessage, okCallback);
			break;
		case 1:
			_tip(aMessage, 1000);
			break;
		case 2:
			_confirm(aMessage, okCallback, cancelCallback);
			break;
		case 3:
			_msg(aMessage, okCallback);
			break;
		case 4:
			_warn(aMessage, okCallback);
			break;
		default:
			oldAlert(aMessage);
			break;
		}
	};

	/**
	 * 替换window对象的alert方法。
	 */
	window.alert = _alert;
	
	return {
		showModelessDialog : _showModelessDialog,
		showModalDialog : _showModelessDialog,
		open : _showModelessDialog,
		findDimensions : _findDimensions
		/* 弹窗提示信息类方法 */
		,alert : _alert
		,msg : _msg
		,warn : _warn
		,error : _error
		,tip : _tip
		,confirm : _confirm
	};
}(window, jQuery, dialog));
