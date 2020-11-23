var ewmsize = "";
if (ewmsize == "") {
	ewmsize = 1.8;
}
// defaultZpSbj 发票打印预览默认上边距
// defaultZpZbj 发票打印预览默认左边距
// defaultPpSbj 发票打印预览默认上边距
// defaultPpZbj 发票打印预览默认左边距
// defaultQDSbj 发票清单打印预览默认上边距
// defaultQDZbj 发票清单打印预览默认左边距
var defaultZpSbj = 0, defaultZpZbj = -14,defaultPpSbj = 0, defaultPpZbj = -14, defaultQDSbj = 4, defaultQDZbj = 4.5;

/**
 * 专票打印、预览,如果cookie中没有设置上边距与左边距，则使用默认的上边距，左边距
 * @param jsonstr
 * @param yl true: 预览， false：打印
 * @returns
 * 
 * bz 字段设置了换行
 */
function printZzP(jsonstr, yl) {
	printZzP_sz(jsonstr, yl,$.cookie("sbj_zzszp") || defaultZpSbj,$.cookie("zbj_zzszp") || defaultZpZbj);
}

/**
 * 专票打印、预览，设置了打印、预览的上边距和左边距;
 * @param jsonstr JSON格式数据
 * @param yl  true: 预览， false：打印
 * @param sbj 上边距 
 * @param zbj 左边距 
 * @returns
 */
function printZzP_sz (jsonstr, yl,sbj,zbj) {
	if (!checkPrint()) {
		return;
	}
	fpmx = JSON.parse(jsonstr);
	// 设置左边距，上边距(根据实际打印效果更改)// 从cookie 中取的设置的上边距和左边距
	LODOP.PRINT_INITA(sbj + "mm", zbj + "mm", "230mm", "139.7mm", "增值税专用发票");
	LODOP.SET_PRINT_PAGESIZE(1, 2300, 1397, "CreateCustomPage");
	var fpmxPrint = $.extend({
		fplxdm: "004"
	}, fpmx, {
		// 计算价税合计大写
		jshjdx: je2Upper(fpmx.jshj),
		mxzb: filterMxzbPrint(fpmx)
	});
	printZzszp(fpmxPrint);
	if (yl) { // 预览
		LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 890, 700, "");
		LODOP.SET_SHOW_MODE("HIDE_PBUTTIN_PREVIEW", 1);
		LODOP.PREVIEW();
	} else {
		// 打印
		LODOP.PRINT();
	}
}


/** 普票打印、预览 ,如果cookie中没有设置上边距与左边距，则使用默认的上边距，左边距
 * @param jsonstr
 * @param yl true: 预览， false：打印
 **/
function printZpP(jsonstr, yl) {
	console.log("单张发票json"+jsonstr);
	printZpP_sz(jsonstr, yl, $.cookie("sbj_zzspp") || defaultPpSbj,$.cookie("zbj_zzspp") || defaultPpZbj);
}


/** 普票打印、预览设置了打印、预览的上边距和左边距;
 * @param jsonstr JSON格式数据
 * @param yl  true: 预览， false：打印
 * @param sbj 上边距 
 * @param zbj 左边距 
 **/
function printZpP_sz(jsonstr, yl, sbj, zbj) {
	if (!checkPrint()) {
		return;
	}
	fpmx = JSON.parse(jsonstr);	
	LODOP.PRINT_INITA(sbj + "mm", zbj + "mm", "230mm", "139.7mm", "增值税普通发票");
	LODOP.SET_PRINT_PAGESIZE(1, 2300, 1397, "CreateCustomPage");
	var fpmxPrint = $.extend({
		fplxdm: "007"
	}, fpmx, {
		jshjdx: je2Upper(fpmx.jshj),
		mxzb: filterMxzbPrint(fpmx)
	});
	printZzspp(fpmxPrint);	
	if (yl) { // 预览
		LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 890, 700, "");
		LODOP.SET_SHOW_MODE("HIDE_PBUTTIN_PREVIEW", 1);
		LODOP.PREVIEW();
	} else {
		// 打印
		LODOP.PRINT();
	}
}

/**
 * 专票清单打印、预览,如果cookie中没有设置上边距与左边距，则使用默认的上边距，左边距
 * @param jsonstr
 * @param yl true: 预览， false：打印
 * @returns
 */
function PrintZzpQD(jsonstr, yl) {
	PrintZzpQD_sz(jsonstr, yl, $.cookie("sbj_qd") || defaultQDSbj,$.cookie("zbj_qd") || defaultQDZbj);
}
/** 专票清单打印、预览 设置打印、预览的上边距和左边距;
 * @param jsonstr JSON格式数据
 * @param yl  true: 预览， false：打印
 * @param sbj 上边距 
 * @param zbj 左边距 
 **/
function PrintZzpQD_sz(jsonstr, yl, sbj, zbj) {
	if (!checkPrint()) {
		return;
	}
	fpmx = JSON.parse(jsonstr); 
	if (fpmx.mxzb.length - 1 > 500) {
		alertx("当前发票明细条数大于500建议用多明细清单打印！");
		return;
	} else {
		LODOP.PRINT_INITA(sbj + "mm", zbj + "mm", "230mm", "159mm", "增值税专用发票（清单）");
		var fpmxPrint = $.extend({
			fplxdm: "004"
		}, fpmx, {
			mxzb: filterMxzbQDPrint(fpmx)
		});
		printZzszpQD(fpmxPrint);
		if (yl) {
			LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 850, 750, "");
			LODOP.SET_SHOW_MODE("HIDE_PBUTTIN_PREVIEW", 1);
			LODOP.PREVIEW();
		} else {
			LODOP.PRINT();
		}
	}
}
/**
 * 普票清单打印、预览,如果cookie中没有设置上边距与左边距，则使用默认的上边距，左边距
 * @param jsonstr
 * @param yl true: 预览， false：打印
 * @returns
 */
function PrintZppQD(jsonstr, yl) {
	PrintZppQD_sz(jsonstr, yl, $.cookie("sbj_qd") || defaultQDSbj,$.cookie("zbj_qd") || defaultQDZbj);
}
/** 普票清单打印、预览 设置打印、预览的上边距和左边距;
 * @param jsonstr JSON格式数据
 * @param yl  true: 预览， false：打印
 * @param sbj 上边距 
 * @param zbj 左边距 
 **/
function PrintZppQD_sz(jsonstr, yl, sbj, zbj) {
	if (!checkPrint()) {
		return;
	}
	fpmx = JSON.parse(jsonstr);
	if (fpmx.mxzb.length - 1 > 500) {
		alertx("当前发票明细条数大于500建议用多明细清单打印！");
		return;
	} else {
		LODOP.PRINT_INITA(sbj + "mm", zbj + "mm", "230mm", "159mm", "增值税普通发票（清单）");
		var fpmxPrint = $.extend({
			fplxdm: "007"
		}, fpmx, {
			mxzb: filterMxzbQDPrint(fpmx)
		});
		printZzsppQD(fpmxPrint);
		if (yl) {
			LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 850, 750, "");
			LODOP.SET_SHOW_MODE("HIDE_PBUTTIN_PREVIEW", 1);
			LODOP.PREVIEW();
		} else {
			LODOP.PRINT();
		}
	}
}