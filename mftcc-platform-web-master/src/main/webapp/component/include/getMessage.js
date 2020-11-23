/**
 * 调用方法：
 * 
 * <pre>
 * top.getMessage("SUCCEED_SAVE");
 * top.getMessage("NO_PHONENO", "17752506596");
 * top.getMessage("FAILED_SAVE", {"opration":"客户基本信息","reason":data.msg});
 * </pre>
 */
(function() {
	if (typeof top.getMessage === "undefined") {
	// 直接加载到top上。
		/* 跟后台的正则保持一致，注意特殊符号是否需要转义 */
		var rex = /\$\{(\w+)\}/;
	
		$.getJSON(webPath + "/sysMsgConfig/getMessageEnumJSONAjax", function (data) {
			/* 从后台取得的json字符串 */
			var messageObj = JSON.parse(data.messageObj);
			/**
			 * 用法类似于后台。
			 */
			top.getMessage = function (key, paramObj) {
				if (!key) {
					return "";
				}
				
				var msgObj = messageObj[key];
				if (!msgObj) {
					console.error("不存在对应的消息，key：" + key);
					return "";
				}
				
				var message = msgObj["message"];
				//console.log("使用消息模板，key：" + key);
				
				if (!paramObj) {
					// 没有传第二个参数
					while (rex.test(message)) {
						message = message.replace(rex, "");
					}
				} else if (typeof paramObj === "object") {
					// exec得到结果的match 是个数组。
					while (match = rex.exec(message)) {
						if (match.length > 1) {
							var paramKey = match[1];
							message = message.replace(rex, paramObj[paramKey]);
							// 消息中key值不对应的情况会出现undefined。为了容易看到错误，暂时不启用兼容处理。
							// message = message.replace(rex, paramObj[paramKey] || "");
						}
					}
				} if (typeof paramObj === "string") {
					while (rex.test(message)) {
						message = message.replace(rex, paramObj);
					}
				}
				return message;
			};
		});
	}
}) ();
