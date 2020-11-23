/*
 * 页面高亮
 * */
function fHl(o, flag, rndColor, url) {
	// / <summary>
	// / 使用 javascript HTML DOM 高亮显示页面特定字词.
	// / 实例:
	// / fHl(document.body, '纸伞|她');
	// / 这里的body是指高亮body里面的内容。
	// / fHl(document.body, '希望|愁怨', false, '/');
	// / fHl(document.getElementById('at_main'), '独自|飘过|悠长', true,'search.asp?keyword=');
	// / 这里的'at_main'是指高亮id='at_main'的div里面的内容。search.asp?keyword=指给关键字加的链接地址，
	// / 我这里加了一个参数，在后面要用到。可以是任意的地址。
	// / </summary>
	// / <param name="o"type="Object">
	// / 对象, 要进行高亮显示的对象.
	// / </param>
	// / <param name="flag"type="String">
	// / 字符串, 要进行高亮的词或多个词, 使用 竖杠(|) 分隔多个词 .
	// / </param>
	// / <param name="rndColor"type="Boolean">
	// / 布尔值, 是否随机显示文字背景色与文字颜色, true 表示随机显示.
	// / </param>
	// / <param name="url"type="String">
	// / URI, 是否对高亮的词添加链接.
	// / </param>
	// / <return></return>
	var bgCor = fgCor = '';
	if (rndColor) {
		bgCor = fRndCor(10, 20);
		fgCor = fRndCor(230, 255);
	} else {
//		bgCor = '#3399FF';
		fgCor = '#E14A47';
	}
	var re = new RegExp(flag, 'i');
	for ( var i = 0; i < o.childNodes.length; i++) {
		var o_ = o.childNodes[i];
		var o_p = o_.parentNode;
		if (o_.nodeType == 1) {
			fHl(o_, flag, rndColor, url);
		} else if (o_.nodeType == 3) {
			// if(!(o_p.nodeName=='A')){
			if (o_.data.search(re) == -1)
				continue;
			var temp = fEleA(o_.data, flag);
			o_p.replaceChild(temp, o_);
			// }
		}
	}
	// ------------------------------------------------
	function fEleA(text, flag) {
		var style = ' style="background-color:' + bgCor + ';color:' + fgCor
				+ ';"';
		//font-weight:700;
		var o = document.createElement('span');
		var str = '';
		var re = new RegExp('(' + flag + ')', 'gi');
		if (url) {
			str = text.replace(re, '<a href="' + url + '$1"' + style
					+ '>$1</a>'); // 这里是给关键字加链接，红色的$1是指上面链接地址后的具体参数。
		} else {
			str = text.replace(re, '<span ' + style + '>$1</span>'); // 不加链接时显示
		}
		o.innerHTML = str;
		return o;
	}
	// ------------------------------------------------
	function fRndCor(under, over) {
		if (arguments.length == 1) {
			over = under;
			under = 0;
		} else if (arguments.length == 0) {
			under = 0;
			over = 255;
		}
		var r = fRandomBy(under, over).toString(16);
		r = padNum(r, r, 2);
		var g = fRandomBy(under, over).toString(16);
		g = padNum(g, g, 2);
		var b = fRandomBy(under, over).toString(16);
		b = padNum(b, b, 2);
		// defaultStatus=r+' '+g+' '+b
		return '#' + r + g + b;
		function fRandomBy(under, over) {
			switch (arguments.length) {
				case 1 :
					return parseInt(Math.random() * under + 1);
				case 2 :
					return parseInt(Math.random() * (over - under + 1) + under);
				default :
					return 0;
			}
		}
		function padNum(str, num, len) {
			var temp = '';
			for ( var i = 0; i < len; temp += num, i++);
			return temp = (temp += str).substr(temp.length - len);
		}
	}
}