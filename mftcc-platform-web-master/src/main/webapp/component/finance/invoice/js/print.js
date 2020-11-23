//输出控件
document.write("<object id=\"LODOP_OB\" "
    + "classid=\"clsid:2105C259-1E0C-4534-8141-A753534CB4CA\" "
    + "width=\"0\" height=\"0\">"
    + "<embed id=\"LODOP_EM\" type=\"application/x-print-lodop\" "
    + "width=0 height=0></embed></object>");
//打印控件
var LODOP;
//获取控件实例
if (navigator.appVersion.indexOf("MSIE") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
    LODOP = document.getElementById('LODOP_OB');
} else {
    LODOP = document.getElementById('LODOP_EM');
}
function checkPrint() {
//	console.log("lodop===="+LODOP.VERSION);
//   if (LODOP == null || typeof(LODOP.VERSION) == "undefined" || LODOP.VERSION != '6.1.9.8') {
//         alertMsg.confirm("尚未安装打印控件或者不是最新版本，点击确定下载？", {
//             okCall: function () {
//                 
// 				window.open(ctxres + "/download/install_ZCSBprintControl32.exe");
//             }
//         });
		//window.open("js/fpdy2.js");
//        return false;
//    } else {
        LODOP.SET_LICENSES("ZCSB技术处研发中心", "545C44BD459DBBAFB79F18A0F8160A12", "", "");
        return true;
//    }
}

function printZzszp(fpmx) {
    /**
     * {
	 * 	fplxdm
	 * 	fpzt
	 * 	fpdm
	 * 	fphm
	 * 	jqbh
	 * 	kprq
	 * 	ghdwmc
	 * 	ghdwdm
	 * 	ghdwdzdh
	 * 	ghdwyhzh
	 * 	zb
	 * 	hjje
	 * 	hjse
	 * 	jshj
	 * 	jshjdx
	 * 	xhdwmc
	 * 	xhdwdm
	 *  xhdwdzdh
	 *  xhdwyhzh
	 *  skr
	 *  fhr
	 *  kpr
	 *  zb:{
	 *  	[spmc,ggxh,dw,spsl,spdj,je,sl,se]
	 *  }
	 * }
     * */
    LODOP.ADD_PRINT_TEXT("6.9mm", "90mm", "58.2mm", "5mm", "增值税" + (fpmx.fplxdm == "004" ? "专用" : "普通") + "发票");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    if (fpmx.tspz == '08' && fpmx.fpzt != '01') {
        LODOP.ADD_PRINT_TEXT(71, 260, 100, 32, "成品油");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (fpmx.fpzt == '01' && fpmx.tspz != '08') {
        LODOP.ADD_PRINT_TEXT(71, 245, 100, 32, "销项负数");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (fpmx.fpzt == '01' && fpmx.tspz == '08') {
        LODOP.ADD_PRINT_TEXT(63, 269, 100, 32, "成品油");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.ADD_PRINT_TEXT(85, 262, 100, 32, "销项负数");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    }
    LODOP.ADD_PRINT_TEXT(42, 159, 132, 15, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_SHAPE(1, "16.9mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "18mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(42, 634, 113, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_IMAGE(39, 595, 26, 24, "data:image/jpeg;base64,/9j/" +
        "4AAQSkZJRgABAAEAYABgAAD//gAfTEVBRCBUZWNobm9sb2dpZXMgSW5jLiBWMS4wMQD/2wCEAAgFB" +
        "gcGBQgHBgcJCAgJDBQNDAsLDBgREg4UHRkeHhwZHBsgJC4nICIrIhscKDYoKy8xMzQzHyY4PDgyPC" +
        "4yMzEBCAkJDAoMFw0NFzEhHCExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTE" +
        "xMTExMTExMTExMf/EAaIAAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKCwEAAwEBAQEBAQEBAQAAA" +
        "AAAAAECAwQFBgcICQoLEAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrH" +
        "BFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h" +
        "5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OX" +
        "m5+jp6vHy8/T19vf4+foRAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQ" +
        "kjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3" +
        "h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm" +
        "5+jp6vLz9PX29/j5+v/AABEIABoAGwMBEQACEQEDEQH/2gAMAwEAAhEDEQA/APQvil421rwNBHqUOjWt" +
        "9pG5I5JTcFZUY5/hxjHHXNAEnjfx+2j+CbLXvD9qmpSalJFHaRsThi4yM457Yx60AbvgnV9R1rw1Z3+s6" +
        "c2mX0ynzbZgRtIOM4PIz1waANygDz748WsV94Iis5yVjuNRtoiw6gM+CfyJoA8dstRvtBurH4f6vvabS/" +
        "ENvNbNjhoixzj25DD/AHqAOhu/GHiWbw3q3jmDWbiB7HVvs8engj7P5A4Klcck+vWgD3uwn+02NvcYx5s" +
        "Svj0yAaAPJ/iPqHifXPHdt4QttEJ0pbq0ukvgrYAQh3JPTHUY9vegC78WfA0mp+JfDniXSoDJd2V7Cl0qd" +
        "Wi3ghv+A/yPtQBx2qaeTq15aJ4W8U/2JNqH2ybTkjj8uWUHqG6hT1xQB79bY+zRYjMQ2DCH+HjpQBLQAUAFABQB/9k=");
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT("23.3mm", "168mm", "19.6mm", "4.5mm", "开票日期：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "95mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "7.4mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "136mm", "5mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "51.6mm", "20.1mm", "201.1mm", "54mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 276, 1, 202, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 372, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 419, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 495, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 571, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 684, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 726, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "20.1mm", "7.4mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "136mm", "5mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(120, 84, 18, 76, "购货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 1);
    LODOP.ADD_PRINT_TEXT(118, 107, 114, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(136, 107, 114, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(155, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(155, 157, 62, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(175, 107, 111, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(123, 517, 18, 71, "密码区");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 6);
    LODOP.ADD_PRINT_TEXT(200, 108, 155, 17, "货物或应税劳务名称");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 295, 86, 17, "规格型号");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 382, 60, 17, "单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 438, 61, 17, "数量");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 512, 80, 17, "单价");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 602, 90, 17, "金额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_TEXT(200, 691, 60, 17, "税率");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 755, 90, 17, "税额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_SHAPE(0, 364, 77, 758, 1, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(348, 127, 103, 17, "合         计");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(375, 115, 146, 17, "价税合计（大写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);

    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "○");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "×");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);

    LODOP.ADD_PRINT_TEXT(375, 620, 65, 17, "（小写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(405, 84, 18, 71, "销货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -2);
    LODOP.ADD_PRINT_TEXT(404, 107, 109, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(420, 107, 108, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(439, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(439, 157, 61, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(454, 107, 109, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(411, 518, 18, 59, "备\n\n注");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 90, 68, 19, "收款人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 306, 60, 19, "复核：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 474, 68, 19, "开票人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 626, 130, 19, "销货单位：（章）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(74, 148, 162, 15, "机器编号：");
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 1);
    LODOP.ADD_PRINT_TEXT(92, 145, 147, 15, fpmx.jqbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(52, 778, 78, 15, fpmx.fpdm);
    LODOP.ADD_PRINT_TEXT(66, 762, 87, 15, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "年" + kprq.substring(4, 6) + "月" + kprq.substring(6, 8) + "日";
    LODOP.ADD_PRINT_TEXT("23.3mm", 714, 121, 15, kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    var dydx = ewmsize;
    var kdgd = dydx / 0.0264;
    LODOP.ADD_PRINT_IMAGE(31, 78, kdgd, kdgd, "data:image/png;base64," + fpmx.ewm);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    var ghdwmc = fpmx.ghdwmc;
    var ghdwmcLen = countStrLength(ghdwmc);
    
	if (ghdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
       	
    } else if (ghdwmcLen <= 54) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);

        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);

    } else if (ghdwmcLen <= 64) {
        if (ghdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
          
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(117, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(135, 225, 290, 15, fpmx.ghdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var ghdwdzdh = fpmx.ghdwdzdh;
    var ghdwdzdhLen = countStrLength(ghdwdzdh);
    if (ghdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(156, 208, 315, 16, ghdwdzdh);
    } else if (ghdwdzdhLen <= 54) {
        LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwdzdhLen <= 64) {
        if (ghdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(155, 208, 315, 16, ghdwdzdh);
             LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var ghdwyhzh = fpmx.ghdwyhzh;
    var ghdwyhzhLen = countStrLength(ghdwyhzh);
    if (ghdwyhzhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
    } else if (ghdwyhzhLen <= 53) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwyhzhLen <= 64) {
        if (ghdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    printSkmOneByOne(fpmx.skm, 120, 551);
//	LODOP.ADD_PRINT_TEXT(120,544,300,15,fpmx.skm.substring(0,28));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(137,544,300,15,fpmx.skm.substring(28,56));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(154,544,300,15,fpmx.skm.substring(56,84));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(171,544,300,15,fpmx.skm.substring(84,112));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
    $.each(fpmx.mxzb, function (i, n) {
        var cezsSl = (n.sl + "") == "" ? "" : (((n.sl + "").length > 4 && (n.sl + "").substring(4, 5) * 1 > 0 ? (n.sl * 100).toFixed(1) : (n.sl * 100).toFixed()) + "%");
        if (fpmx.zsfs == "1" || fpmx.zsfs == "2") {
            cezsSl = "***";
        }
        addPrintLineZzszp(i, n.spmc, n.ggxh, n.dw, n.spsl ? delRight(n.spsl.toFixed(6)) : "", n.spdj ? delRight(n.spdj.toFixed(6)) : "", n.je.toFixed(2), cezsSl, n.se.toFixed(2));
    });
    var hjje = (fpmx.hjje * 1).toFixed(2);
    var hjjeLen = countStrLength(hjje);
    LODOP.ADD_PRINT_TEXT(345, 553, 129, 20, hjje);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    addMoney(346, 551 + 129 - hjjeLen * 7.2 - 12);
    var hjse = (fpmx.se * 1).toFixed(2);
    var hjseLen = countStrLength(hjse);
    LODOP.ADD_PRINT_TEXT(345, 696, 135, 20, hjse);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    addMoney(346, 699 + 135 - hjseLen * 7.2 - 12);
    LODOP.ADD_PRINT_TEXT(376, 303, 335, 20, (fpmx.jshj < 0 ? '(负数)' : '') + fpmx.jshjdx);
    addMoney(374, 680);
    LODOP.ADD_PRINT_TEXT(373, 695, 135, 20, (fpmx.jshj * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var xhdwmc = fpmx.xhdwmc;
    var xhdwmcLen = countStrLength(xhdwmc);
    if (xhdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
    } else if (xhdwmcLen <= 55) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwmcLen <= 64) {
        if (xhdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(406, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(403, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(418, 225, 276, 15, fpmx.xhdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var xhdwdzdh = fpmx.xhdwdzdh;
    var xhdwdzdhLen = countStrLength(xhdwdzdh);
    if (xhdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
    } else if (xhdwdzdhLen <= 54) {
        LODOP.ADD_PRINT_TEXT(441, 208, 315, 16, xhdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwdzdhLen <= 64) {
        if (xhdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(441, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(441, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(442, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(437, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var xhdwyhzh = fpmx.xhdwyhzh;
    var xhdwyhzhLen = countStrLength(xhdwyhzh);
    if (xhdwyhzhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
    } else if (xhdwyhzhLen <= 53) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwyhzhLen <= 64) {
        if (xhdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(454, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }

    LODOP.ADD_PRINT_TEXT(407, 540, 293, 61, fpmx.bz);
    LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    LODOP.ADD_PRINT_TEXT(477, 152, 152, 15, fpmx.skr);
    LODOP.ADD_PRINT_TEXT(477, 359, 115, 15, fpmx.fhr);
    var kpr = fpmx.kpr;
    var kprLen = countStrLength(kpr);
    if (kprLen <= 14) {
        LODOP.ADD_PRINT_TEXT(476, 526, 121, 15, kpr);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else {
        LODOP.ADD_PRINT_TEXT(477, 526, 121, 15, kpr);
    }
}


/**
 * windows 10 缩放比例为125%的时候调用该方法。
 * printZzszp125  增值税专票打印
 */
function printZzszp125(fpmx) {
    /**
     * {
	 * 	fplxdm
	 * 	fpzt
	 * 	fpdm
	 * 	fphm
	 * 	jqbh
	 * 	kprq
	 * 	ghdwmc
	 * 	ghdwdm
	 * 	ghdwdzdh
	 * 	ghdwyhzh
	 * 	zb
	 * 	hjje
	 * 	hjse
	 * 	jshj
	 * 	jshjdx
	 * 	xhdwmc
	 * 	xhdwdm
	 *  xhdwdzdh
	 *  xhdwyhzh
	 *  skr
	 *  fhr
	 *  kpr
	 *  zb:{
	 *  	[spmc,ggxh,dw,spsl,spdj,je,sl,se]
	 *  }
	 * }
     * */
    LODOP.ADD_PRINT_TEXT("6.9mm", "90mm", "58.2mm", "5mm", "增值税" + (fpmx.fplxdm == "004" ? "专用" : "普通") + "发票");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    if (fpmx.tspz == '08' && fpmx.fpzt != '01') {
        LODOP.ADD_PRINT_TEXT(71, 260, 100, 32, "成品油");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (fpmx.fpzt == '01' && fpmx.tspz != '08') {
        LODOP.ADD_PRINT_TEXT(71, 245, 100, 32, "销项负数");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (fpmx.fpzt == '01' && fpmx.tspz == '08') {
        LODOP.ADD_PRINT_TEXT(63, 269, 100, 32, "成品油");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.ADD_PRINT_TEXT(85, 262, 100, 32, "销项负数");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    }
    LODOP.ADD_PRINT_TEXT(42, 159, 132, 15, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_SHAPE(1, "16.9mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "18mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(42, 634, 113, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_IMAGE(39, 595, 26, 24, "data:image/jpeg;base64,/9j/" +
        "4AAQSkZJRgABAAEAYABgAAD//gAfTEVBRCBUZWNobm9sb2dpZXMgSW5jLiBWMS4wMQD/2wCEAAgFB" +
        "gcGBQgHBgcJCAgJDBQNDAsLDBgREg4UHRkeHhwZHBsgJC4nICIrIhscKDYoKy8xMzQzHyY4PDgyPC" +
        "4yMzEBCAkJDAoMFw0NFzEhHCExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTE" +
        "xMTExMTExMTExMf/EAaIAAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKCwEAAwEBAQEBAQEBAQAAA" +
        "AAAAAECAwQFBgcICQoLEAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrH" +
        "BFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h" +
        "5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OX" +
        "m5+jp6vHy8/T19vf4+foRAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQ" +
        "kjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3" +
        "h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm" +
        "5+jp6vLz9PX29/j5+v/AABEIABoAGwMBEQACEQEDEQH/2gAMAwEAAhEDEQA/APQvil421rwNBHqUOjWt" +
        "9pG5I5JTcFZUY5/hxjHHXNAEnjfx+2j+CbLXvD9qmpSalJFHaRsThi4yM457Yx60AbvgnV9R1rw1Z3+s6" +
        "c2mX0ynzbZgRtIOM4PIz1waANygDz748WsV94Iis5yVjuNRtoiw6gM+CfyJoA8dstRvtBurH4f6vvabS/" +
        "ENvNbNjhoixzj25DD/AHqAOhu/GHiWbw3q3jmDWbiB7HVvs8engj7P5A4Klcck+vWgD3uwn+02NvcYx5s" +
        "Svj0yAaAPJ/iPqHifXPHdt4QttEJ0pbq0ukvgrYAQh3JPTHUY9vegC78WfA0mp+JfDniXSoDJd2V7Cl0qd" +
        "Wi3ghv+A/yPtQBx2qaeTq15aJ4W8U/2JNqH2ybTkjj8uWUHqG6hT1xQB79bY+zRYjMQ2DCH+HjpQBLQAUAFABQB/9k=");
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT("23.3mm", "168mm", "19.6mm", "4.5mm", "开票日期：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "95mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "7.4mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "136mm", "5mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "51.6mm", "20.1mm", "201.1mm", "54mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 276, 1, 202, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 372, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 419, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 495, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 571, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 684, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 726, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "20.1mm", "7.4mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "136mm", "5mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(120, 84, 18, 76, "购货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 1);
    LODOP.ADD_PRINT_TEXT(118, 107, 114, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(136, 107, 114, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(155, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(155, 157, 62, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(175, 107, 111, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(123, 517, 18, 71, "密码区");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 6);
    LODOP.ADD_PRINT_TEXT(200, 108, 155, 17, "货物或应税劳务名称");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 295, 86, 17, "规格型号");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 382, 60, 17, "单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 438, 61, 17, "数量");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 512, 80, 17, "单价");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 602, 90, 17, "金额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_TEXT(200, 691, 60, 17, "税率");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 755, 90, 17, "税额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_SHAPE(0, 364, 77, 758, 1, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(348, 127, 103, 17, "合         计");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(375, 115, 146, 17, "价税合计（大写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);

    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "○");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "×");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);

    LODOP.ADD_PRINT_TEXT(375, 620, 65, 17, "（小写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(405, 84, 18, 71, "销货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -2);
    LODOP.ADD_PRINT_TEXT(404, 107, 109, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(420, 107, 108, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(439, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(439, 157, 61, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(454, 107, 109, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(411, 518, 18, 59, "备\n\n注");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 90, 68, 19, "收款人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 306, 60, 19, "复核：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 474, 68, 19, "开票人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 626, 130, 19, "销货单位：（章）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(74, 148, 162, 15, "机器编号：");
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 1);
    LODOP.ADD_PRINT_TEXT(92, 145, 147, 15, fpmx.jqbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(52, 778, 78, 15, fpmx.fpdm);
    LODOP.ADD_PRINT_TEXT(66, 762, 87, 15, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "年" + kprq.substring(4, 6) + "月" + kprq.substring(6, 8) + "日";
    LODOP.ADD_PRINT_TEXT("23.3mm", 714, 121, 15, kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    var dydx = ewmsize;
    var kdgd = dydx / 0.0264;
    LODOP.ADD_PRINT_IMAGE(31, 78, kdgd, kdgd, "data:image/png;base64," + fpmx.ewm);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    var ghdwmc = fpmx.ghdwmc;
    var ghdwmcLen = countStrLength(ghdwmc);
    if (ghdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
       	
    } else if (ghdwmcLen <= 56) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);

        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);

    } else if (ghdwmcLen <= 64) {
        if (ghdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
          
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(117, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(135, 225, 290, 15, fpmx.ghdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var ghdwdzdh = fpmx.ghdwdzdh;
    var ghdwdzdhLen = countStrLength(ghdwdzdh);
    if (ghdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(160, 208, 315, 16, ghdwdzdh);
    } else if (ghdwdzdhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwdzdhLen <= 64) {
        if (ghdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(155, 208, 315, 16, ghdwdzdh);
             LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var ghdwyhzh = fpmx.ghdwyhzh;
    var ghdwyhzhLen = countStrLength(ghdwyhzh);
    if (ghdwyhzhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
    } else if (ghdwyhzhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwyhzhLen <= 64) {
        if (ghdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(174, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    printSkmOneByOne(fpmx.skm, 120, 551);
//	LODOP.ADD_PRINT_TEXT(120,544,300,15,fpmx.skm.substring(0,28));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(137,544,300,15,fpmx.skm.substring(28,56));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(154,544,300,15,fpmx.skm.substring(56,84));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(171,544,300,15,fpmx.skm.substring(84,112));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
    $.each(fpmx.mxzb, function (i, n) {
        var cezsSl = (n.sl + "") == "" ? "" : (((n.sl + "").length > 4 && (n.sl + "").substring(4, 5) * 1 > 0 ? (n.sl * 100).toFixed(1) : (n.sl * 100).toFixed()) + "%");
        if (fpmx.zsfs == "1" || fpmx.zsfs == "2") {
            cezsSl = "***";
        }
        addPrintLineZzszp(i, n.spmc, n.ggxh, n.dw, n.spsl ? delRight(n.spsl.toFixed(6)) : "", n.spdj ? delRight(n.spdj.toFixed(6)) : "", n.je.toFixed(2), cezsSl, n.se.toFixed(2));
    });
    var hjje = (fpmx.hjje * 1).toFixed(2);
    var hjjeLen = countStrLength(hjje);
    LODOP.ADD_PRINT_TEXT(345, 553, 129, 20, hjje);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    addMoney(346, 551 + 129 - hjjeLen * 7.2 - 12);
    var hjse = (fpmx.se * 1).toFixed(2);
    var hjseLen = countStrLength(hjse);
    LODOP.ADD_PRINT_TEXT(345, 696, 135, 20, hjse);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    addMoney(346, 699 + 135 - hjseLen * 7.2 - 12);
    LODOP.ADD_PRINT_TEXT(376, 303, 335, 20, (fpmx.jshj < 0 ? '(负数)' : '') + fpmx.jshjdx);
    addMoney(374, 680);
    LODOP.ADD_PRINT_TEXT(373, 695, 135, 20, (fpmx.jshj * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var xhdwmc = fpmx.xhdwmc;
    var xhdwmcLen = countStrLength(xhdwmc);
    if (xhdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
    } else if (xhdwmcLen <= 56) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwmcLen <= 64) {
        if (xhdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(403, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(419, 225, 276, 15, fpmx.xhdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var xhdwdzdh = fpmx.xhdwdzdh;
    var xhdwdzdhLen = countStrLength(xhdwdzdh);
    if (xhdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
    } else if (xhdwdzdhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwdzdhLen <= 64) {
        if (xhdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(436, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var xhdwyhzh = fpmx.xhdwyhzh;
    var xhdwyhzhLen = countStrLength(xhdwyhzh);
    if (xhdwyhzhLen <= 49) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
    } else if (xhdwyhzhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwyhzhLen <= 64) {
        if (xhdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(453, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }

    LODOP.ADD_PRINT_TEXT(407, 540, 293, 61, fpmx.bz);
    LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    LODOP.ADD_PRINT_TEXT(477, 152, 152, 15, fpmx.skr);
    LODOP.ADD_PRINT_TEXT(477, 359, 115, 15, fpmx.fhr);
    var kpr = fpmx.kpr;
    var kprLen = countStrLength(kpr);
    if (kprLen <= 14) {
        LODOP.ADD_PRINT_TEXT(476, 526, 121, 15, kpr);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else {
        LODOP.ADD_PRINT_TEXT(477, 526, 121, 15, kpr);
    }
}

function printZzspp(fpmx) {
    /**
     * {
	 * 	fplxdm
	 * 	fpzt
	 * 	fpdm
	 * 	fphm
	 * 	jqbh
	 * 	kprq
	 * 	ghdwmc
	 * 	ghdwdm
	 * 	ghdwdzdh
	 * 	ghdwyhzh
	 * 	zb
	 * 	hjje
	 * 	hjse
	 * 	jshj
	 * 	jshjdx
	 * 	xhdwmc
	 * 	xhdwdm
	 *  xhdwdzdh
	 *  xhdwyhzh
	 *  skr
	 *  fhr
	 *  kpr
	 *  zb:{
	 *  	spmc,ggxh,dw,spsl,spdj,je,sl,se
	 *  }
	 * }
     * */
    LODOP.ADD_PRINT_TEXT("6.9mm", "90mm", "58.2mm", "5mm", "增值税" + (fpmx.fplxdm == "004" ? "专用" : "普通") + "发票");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);

    if (fpmx.tspz == '02') {

        if (fpmx.fpzt == '01') {
            LODOP.ADD_PRINT_TEXT(63, 269, 100, 32, "收购");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
            LODOP.ADD_PRINT_TEXT(85, 262, 100, 32, "销项负数");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        } else {
            LODOP.ADD_PRINT_TEXT(71, 260, 100, 32, "收购");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    } else if (fpmx.tspz == '08') {
        if (fpmx.fpzt == '01') {
            LODOP.ADD_PRINT_TEXT(63, 269, 100, 32, "成品油");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
            LODOP.ADD_PRINT_TEXT(85, 262, 100, 32, "销项负数");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        } else {
            LODOP.ADD_PRINT_TEXT(71, 260, 100, 32, "成品油");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    } else {
        if (fpmx.fpzt == '01') {
            LODOP.ADD_PRINT_TEXT(71, 245, 100, 32, "销项负数");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    }
    LODOP.ADD_PRINT_TEXT(42, 159, 155, 15, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_SHAPE(1, "16.9mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "18mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(42, 634, 113, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_IMAGE(39, 595, 26, 24, "data:image/jpeg;base64,/9j/" +
        "4AAQSkZJRgABAAEAYABgAAD//gAfTEVBRCBUZWNobm9sb2dpZXMgSW5jLiBWMS4wMQD/2wCEAAgFB" +
        "gcGBQgHBgcJCAgJDBQNDAsLDBgREg4UHRkeHhwZHBsgJC4nICIrIhscKDYoKy8xMzQzHyY4PDgyPC" +
        "4yMzEBCAkJDAoMFw0NFzEhHCExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTE" +
        "xMTExMTExMTExMf/EAaIAAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKCwEAAwEBAQEBAQEBAQAAA" +
        "AAAAAECAwQFBgcICQoLEAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrH" +
        "BFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h" +
        "5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OX" +
        "m5+jp6vHy8/T19vf4+foRAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQ" +
        "kjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3" +
        "h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm" +
        "5+jp6vLz9PX29/j5+v/AABEIABoAGwMBEQACEQEDEQH/2gAMAwEAAhEDEQA/APQvil421rwNBHqUOjWt" +
        "9pG5I5JTcFZUY5/hxjHHXNAEnjfx+2j+CbLXvD9qmpSalJFHaRsThi4yM457Yx60AbvgnV9R1rw1Z3+s6" +
        "c2mX0ynzbZgRtIOM4PIz1waANygDz748WsV94Iis5yVjuNRtoiw6gM+CfyJoA8dstRvtBurH4f6vvabS/" +
        "ENvNbNjhoixzj25DD/AHqAOhu/GHiWbw3q3jmDWbiB7HVvs8engj7P5A4Klcck+vWgD3uwn+02NvcYx5s" +
        "Svj0yAaAPJ/iPqHifXPHdt4QttEJ0pbq0ukvgrYAQh3JPTHUY9vegC78WfA0mp+JfDniXSoDJd2V7Cl0qd" +
        "Wi3ghv+A/yPtQBx2qaeTq15aJ4W8U/2JNqH2ybTkjj8uWUHqG6hT1xQB79bY+zRYjMQ2DCH+HjpQBLQAUAFABQB/9k=");
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT("23.3mm", "168mm", "19.6mm", "4.5mm", "开票日期：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "95mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "7.4mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "136mm", "5mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "51.6mm", "20.1mm", "201.1mm", "54mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 276, 1, 202, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 372, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 419, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 495, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 571, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 684, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 726, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "20.1mm", "7.4mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "136mm", "5mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(120, 84, 18, 76, "购货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 1);
    LODOP.ADD_PRINT_TEXT(118, 107, 114, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(136, 107, 114, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(155, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(155, 157, 62, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(175, 107, 111, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(123, 517, 18, 71, "密码区");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 6);
    LODOP.ADD_PRINT_TEXT(200, 108, 155, 17, "货物或应税劳务名称");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 295, 86, 17, "规格型号");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 382, 60, 17, "单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 438, 61, 17, "数量");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 512, 80, 17, "单价");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 602, 90, 17, "金额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_TEXT(200, 691, 60, 17, "税率");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 755, 90, 17, "税额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_SHAPE(0, 364, 77, 758, 1, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(348, 127, 103, 17, "合         计");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(375, 115, 146, 17, "价税合计（大写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);

    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "○");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "×");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);

    LODOP.ADD_PRINT_TEXT(375, 620, 65, 17, "（小写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(405, 84, 18, 71, "销货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -2);
    LODOP.ADD_PRINT_TEXT(404, 107, 109, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(420, 107, 108, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(439, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(439, 157, 61, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(454, 107, 109, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(411, 518, 18, 59, "备\n\n注");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 90, 68, 19, "收款人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 306, 60, 19, "复核：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 474, 68, 19, "开票人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 626, 130, 19, "销货单位：（章）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(74, 148, 162, 15, "机器编号：");
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 1);
    LODOP.ADD_PRINT_TEXT(92, 145, 147, 15, fpmx.jqbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(52, 750, 85, 15, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(66, 762, 87, 15, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "年" + kprq.substring(4, 6) + "月" + kprq.substring(6, 8) + "日";
    LODOP.ADD_PRINT_TEXT("23.3mm", 714, 121, 15, kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    var dydx = ewmsize;
    var kdgd = dydx / 0.0264;
    LODOP.ADD_PRINT_IMAGE(31, 78, kdgd, kdgd, "data:image/png;base64," + fpmx.ewm);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    var ghdwmc = fpmx.ghdwmc;
    var ghdwmcLen = countStrLength(ghdwmc);
    if (ghdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
       	
    } else if (ghdwmcLen <= 54) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);

        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);

    } else if (ghdwmcLen <= 64) {
        if (ghdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
          
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(117, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(135, 225, 290, 15, fpmx.ghdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var ghdwdzdh = fpmx.ghdwdzdh;
    var ghdwdzdhLen = countStrLength(ghdwdzdh);
    if (ghdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(156, 208, 315, 16, ghdwdzdh);
    } else if (ghdwdzdhLen <= 54) {
        LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwdzdhLen <= 64) {
        if (ghdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(155, 208, 315, 16, ghdwdzdh);
             LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var ghdwyhzh = fpmx.ghdwyhzh;
    var ghdwyhzhLen = countStrLength(ghdwyhzh);
    if (ghdwyhzhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
    } else if (ghdwyhzhLen <= 53) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwyhzhLen <= 64) {
        if (ghdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    printSkmOneByOne(fpmx.skm, 120, 551);
//	LODOP.ADD_PRINT_TEXT(120,544,300,15,fpmx.skm.substring(0,28));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(137,544,300,15,fpmx.skm.substring(28,56));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(154,544,300,15,fpmx.skm.substring(56,84));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(171,544,300,15,fpmx.skm.substring(84,112));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
    var slFlag = true;
    $.each(fpmx.mxzb, function (i, n) {
        if ((fpmx.qdbz == "1" && (n.fphxz == 3 || n.fphxz == 4)) || (fpmx.qdbz == "0" && (n.fphxz <= 2))) {
            if (n.sl != 0 || (n.sl + "") == "" || (n.sl == 0 && n.se != 0)) {
                slFlag = false;
            }
            var spbmSl = n.sl;
            if (n.spbm && (spbmSl == 0 && spbmSl + "" != "")) {
                if (n.lslbs == "1") {
                    spbmSl = "免税";
                }
                if (n.lslbs == "2") {
                    spbmSl = "不征税";
                }
                if (n.lslbs == "3" || n.lslbs == "" || n.lslbs == null) {
                    spbmSl = "0%";
                }
            }
            if (fpmx.bmbbbh) {
                if (fpmx.qdbz == "1" && fpmx.zhsl == 0 && n.fphxz == 3) {
                    spbmSl = "0%";
                }
            }
            //差额征税
            if (fpmx.zsfs == "2" && n.sl == 0) {
                spbmSl = "0***";
            }
            if (fpmx.zsfs == "2" && n.sl != 0) {
                spbmSl = "1***";
            }
            if (fpmx.zsfs == "1") {
                spbmSl = "j***";
            }
            addPrintLineZzspp(i, n.spmc, n.ggxh, n.dw, n.spsl ? delRight(n.spsl.toFixed(6)) : "", n.spdj ? delRight(n.spdj.toFixed(6)) : "", n.je.toFixed(2), spbmSl, n.se.toFixed(2));
        }
    });
    var hjje = (fpmx.hjje * 1).toFixed(2);
    var hjjeLen = countStrLength(hjje);
    LODOP.ADD_PRINT_TEXT(345, 551, 129, 20, hjje);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    addMoney(346, 551 + 129 - hjjeLen * 7.2 - 12);
    var hjse = (fpmx.se * 1).toFixed(2);
    var hjseLen = countStrLength(hjse);
    if (hjse == 0) {
        hjse = "***";
    } else {
        addMoney(346, 698 + 135 - hjseLen * 7.2 - 12);
    }
    LODOP.ADD_PRINT_TEXT(345, 696, 135, 20, hjse);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    LODOP.ADD_PRINT_TEXT(376, 303, 335, 20, (fpmx.jshj < 0 ? '(负数)' : '') + fpmx.jshjdx);
    addMoney(374, 680);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    //LODOP.ADD_PRINT_TEXT(373, 695, 135, 20, fpmx.jshj);
    LODOP.ADD_PRINT_TEXT(373, 695, 135, 20, fpmx.jshj.toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var xhdwmc = fpmx.xhdwmc;
    var xhdwmcLen = countStrLength(xhdwmc);
    if (xhdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
    } else if (xhdwmcLen <= 55) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwmcLen <= 64) {
        if (xhdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(406, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(403, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(418, 225, 276, 15, fpmx.xhdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var xhdwdzdh = fpmx.xhdwdzdh;
    var xhdwdzdhLen = countStrLength(xhdwdzdh);
    if (xhdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
    } else if (xhdwdzdhLen <= 54) {
        LODOP.ADD_PRINT_TEXT(441, 208, 315, 16, xhdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwdzdhLen <= 64) {
        if (xhdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(441, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(441, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(442, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(437, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var xhdwyhzh = fpmx.xhdwyhzh;
    var xhdwyhzhLen = countStrLength(xhdwyhzh);
    if (xhdwyhzhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
    } else if (xhdwyhzhLen <= 53) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwyhzhLen <= 64) {
        if (xhdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(454, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }

    var jym = fpmx.jym;
    jym = jym.substring(0, 5) + " " + jym.substring(5, 10) + " " + jym.substring(10, 15) + " " + jym.substring(15, jym.length);
    var bzLen = countStrLength(fpmx.bz);
    if (bzLen <= 138) {
        LODOP.ADD_PRINT_TEXT(405, 539, 307, 61, "校验码 " + jym + "\n" + (fpmx.bz ? fpmx.bz : ""));
    } else {
        LODOP.ADD_PRINT_TEXT(403, 539, 307, 61, "校验码 " + jym + "\n" + (fpmx.bz ? fpmx.bz : ""));
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
    }
    LODOP.ADD_PRINT_TEXT(477, 152, 152, 15, fpmx.skr);
    LODOP.ADD_PRINT_TEXT(477, 359, 115, 15, fpmx.fhr);
    var kpr = fpmx.kpr;
    var kprLen = countStrLength(kpr);
    if (kprLen <= 14) {
        LODOP.ADD_PRINT_TEXT(476, 536, 91, 15, kpr);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else {
        LODOP.ADD_PRINT_TEXT(477, 536, 91, 15, kpr);
    }
}
/**
 * windows 10 缩放比例125% 调用（可先尝试使用printZzspp,如果不行，再使用printZzspp125）
 * printZzspp125 增值税普票打印
 */
function printZzspp125(fpmx) {
    /**
     * {
	 * 	fplxdm
	 * 	fpzt
	 * 	fpdm
	 * 	fphm
	 * 	jqbh
	 * 	kprq
	 * 	ghdwmc
	 * 	ghdwdm
	 * 	ghdwdzdh
	 * 	ghdwyhzh
	 * 	zb
	 * 	hjje
	 * 	hjse
	 * 	jshj
	 * 	jshjdx
	 * 	xhdwmc
	 * 	xhdwdm
	 *  xhdwdzdh
	 *  xhdwyhzh
	 *  skr
	 *  fhr
	 *  kpr
	 *  zb:{
	 *  	spmc,ggxh,dw,spsl,spdj,je,sl,se
	 *  }
	 * }
     * */
    LODOP.ADD_PRINT_TEXT("6.9mm", "90mm", "58.2mm", "5mm", "增值税" + (fpmx.fplxdm == "004" ? "专用" : "普通") + "发票");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);

    if (fpmx.tspz == '02') {

        if (fpmx.fpzt == '01') {
            LODOP.ADD_PRINT_TEXT(63, 269, 100, 32, "收购");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
            LODOP.ADD_PRINT_TEXT(85, 262, 100, 32, "销项负数");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        } else {
            LODOP.ADD_PRINT_TEXT(71, 260, 100, 32, "收购");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    } else if (fpmx.tspz == '08') {
        if (fpmx.fpzt == '01') {
            LODOP.ADD_PRINT_TEXT(63, 269, 100, 32, "成品油");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
            LODOP.ADD_PRINT_TEXT(85, 262, 100, 32, "销项负数");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        } else {
            LODOP.ADD_PRINT_TEXT(71, 260, 100, 32, "成品油");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    } else {
        if (fpmx.fpzt == '01') {
            LODOP.ADD_PRINT_TEXT(71, 245, 100, 32, "销项负数");
            LODOP.SET_PRINT_STYLEA(0, "FontName", "黑体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    }
    LODOP.ADD_PRINT_TEXT(42, 159, 155, 15, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_SHAPE(1, "16.9mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "18mm", "86.5mm", "65.1mm", "1", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(42, 634, 113, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#FF0000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_IMAGE(39, 595, 26, 24, "data:image/jpeg;base64,/9j/" +
        "4AAQSkZJRgABAAEAYABgAAD//gAfTEVBRCBUZWNobm9sb2dpZXMgSW5jLiBWMS4wMQD/2wCEAAgFB" +
        "gcGBQgHBgcJCAgJDBQNDAsLDBgREg4UHRkeHhwZHBsgJC4nICIrIhscKDYoKy8xMzQzHyY4PDgyPC" +
        "4yMzEBCAkJDAoMFw0NFzEhHCExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTE" +
        "xMTExMTExMTExMf/EAaIAAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKCwEAAwEBAQEBAQEBAQAAA" +
        "AAAAAECAwQFBgcICQoLEAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrH" +
        "BFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h" +
        "5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OX" +
        "m5+jp6vHy8/T19vf4+foRAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQ" +
        "kjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3" +
        "h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm" +
        "5+jp6vLz9PX29/j5+v/AABEIABoAGwMBEQACEQEDEQH/2gAMAwEAAhEDEQA/APQvil421rwNBHqUOjWt" +
        "9pG5I5JTcFZUY5/hxjHHXNAEnjfx+2j+CbLXvD9qmpSalJFHaRsThi4yM457Yx60AbvgnV9R1rw1Z3+s6" +
        "c2mX0ynzbZgRtIOM4PIz1waANygDz748WsV94Iis5yVjuNRtoiw6gM+CfyJoA8dstRvtBurH4f6vvabS/" +
        "ENvNbNjhoixzj25DD/AHqAOhu/GHiWbw3q3jmDWbiB7HVvs8engj7P5A4Klcck+vWgD3uwn+02NvcYx5s" +
        "Svj0yAaAPJ/iPqHifXPHdt4QttEJ0pbq0ukvgrYAQh3JPTHUY9vegC78WfA0mp+JfDniXSoDJd2V7Cl0qd" +
        "Wi3ghv+A/yPtQBx2qaeTq15aJ4W8U/2JNqH2ybTkjj8uWUHqG6hT1xQB79bY+zRYjMQ2DCH+HjpQBLQAUAFABQB/9k=");
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT("23.3mm", "168mm", "19.6mm", "4.5mm", "开票日期：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "95mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "201.1mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "20.1mm", "7.4mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "29.9mm", "136mm", "5mm", "22mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "51.6mm", "20.1mm", "201.1mm", "54mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 276, 1, 202, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 372, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 419, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 495, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 571, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 195, 684, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 196, 726, 1, 169, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "20.1mm", "7.4mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, "105.3mm", "136mm", "5mm", "19.6mm", 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(120, 84, 18, 76, "购货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 1);
    LODOP.ADD_PRINT_TEXT(118, 107, 114, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(136, 107, 114, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(155, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(155, 157, 62, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(175, 107, 111, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(123, 517, 18, 71, "密码区");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 6);
    LODOP.ADD_PRINT_TEXT(200, 108, 155, 17, "货物或应税劳务名称");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 295, 86, 17, "规格型号");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 382, 60, 17, "单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 438, 61, 17, "数量");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 512, 80, 17, "单价");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 12);
    LODOP.ADD_PRINT_TEXT(200, 602, 90, 17, "金额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_TEXT(200, 691, 60, 17, "税率");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(200, 755, 90, 17, "税额");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_SHAPE(0, 364, 77, 758, 1, 0, 1, "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(348, 127, 103, 17, "合         计");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(375, 115, 146, 17, "价税合计（大写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);

    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "○");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(374, 288, 15, 15, "×");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);

    LODOP.ADD_PRINT_TEXT(375, 620, 65, 17, "（小写）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(405, 84, 18, 71, "销货单位");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -2);
    LODOP.ADD_PRINT_TEXT(404, 107, 109, 20, "名        称：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(420, 107, 108, 20, "纳税人识别号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(439, 107, 58, 20, "地址、");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(439, 157, 61, 20, "电话：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(454, 107, 109, 20, "开户行及账号：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(411, 518, 18, 59, "备\n\n注");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 90, 68, 19, "收款人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 306, 60, 19, "复核：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 474, 68, 19, "开票人：");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(476, 626, 130, 19, "销货单位：（章）");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#008000");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(74, 148, 162, 15, "机器编号：");
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 1);
    LODOP.ADD_PRINT_TEXT(92, 145, 147, 15, fpmx.jqbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(52, 750, 85, 15, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(66, 762, 87, 15, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "年" + kprq.substring(4, 6) + "月" + kprq.substring(6, 8) + "日";
    LODOP.ADD_PRINT_TEXT("23.3mm", 714, 121, 15, kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    var dydx = ewmsize;
    var kdgd = dydx / 0.0264;
    LODOP.ADD_PRINT_IMAGE(31, 78, kdgd, kdgd, "data:image/png;base64," + fpmx.ewm);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    var ghdwmc = fpmx.ghdwmc;
    var ghdwmcLen = countStrLength(ghdwmc);
    if (ghdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
       	
    } else if (ghdwmcLen <= 56) {
        LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);

        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);

    } else if (ghdwmcLen <= 64) {
        if (ghdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
          
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(120, 208, 315, 16, ghdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(117, 208, 315, 16, ghdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(135, 225, 290, 15, fpmx.ghdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var ghdwdzdh = fpmx.ghdwdzdh;
    var ghdwdzdhLen = countStrLength(ghdwdzdh);
    if (ghdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(160, 208, 315, 16, ghdwdzdh);
    } else if (ghdwdzdhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwdzdhLen <= 64) {
        if (ghdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(158, 208, 315, 16, ghdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(155, 208, 315, 16, ghdwdzdh);
             LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var ghdwyhzh = fpmx.ghdwyhzh;
    var ghdwyhzhLen = countStrLength(ghdwyhzh);
    if (ghdwyhzhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
    } else if (ghdwyhzhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (ghdwyhzhLen <= 64) {
        if (ghdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(175, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (ghdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(177, 208, 315, 16, ghdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(174, 208, 315, 16, ghdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    printSkmOneByOne(fpmx.skm, 120, 551);
//	LODOP.ADD_PRINT_TEXT(120,544,300,15,fpmx.skm.substring(0,28));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(137,544,300,15,fpmx.skm.substring(28,56));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(154,544,300,15,fpmx.skm.substring(56,84));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
//	LODOP.ADD_PRINT_TEXT(171,544,300,15,fpmx.skm.substring(84,112));
//	LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
//	LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
    var slFlag = true;
    $.each(fpmx.mxzb, function (i, n) {
        if ((fpmx.qdbz == "1" && (n.fphxz == 3 || n.fphxz == 4)) || (fpmx.qdbz == "0" && (n.fphxz <= 2))) {
            if (n.sl != 0 || (n.sl + "") == "" || (n.sl == 0 && n.se != 0)) {
                slFlag = false;
            }
            var spbmSl = n.sl;
            if (n.spbm && (spbmSl == 0 && spbmSl + "" != "")) {
                if (n.lslbs == "1") {
                    spbmSl = "免税";
                }
                if (n.lslbs == "2") {
                    spbmSl = "不征税";
                }
                if (n.lslbs == "3" || n.lslbs == "" || n.lslbs == null) {
                    spbmSl = "0%";
                }
            }
            if (fpmx.bmbbbh) {
                if (fpmx.qdbz == "1" && fpmx.zhsl == 0 && n.fphxz == 3) {
                    spbmSl = "0%";
                }
            }
            //差额征税
            if (fpmx.zsfs == "2" && n.sl == 0) {
                spbmSl = "0***";
            }
            if (fpmx.zsfs == "2" && n.sl != 0) {
                spbmSl = "1***";
            }
            if (fpmx.zsfs == "1") {
                spbmSl = "j***";
            }
            addPrintLineZzspp(i, n.spmc, n.ggxh, n.dw, n.spsl ? delRight(n.spsl.toFixed(6)) : "", n.spdj ? delRight(n.spdj.toFixed(6)) : "", n.je.toFixed(2), spbmSl, n.se.toFixed(2));
        }
    });
    var hjje = (fpmx.hjje * 1).toFixed(2);
    var hjjeLen = countStrLength(hjje);
    LODOP.ADD_PRINT_TEXT(345, 551, 129, 20, hjje);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    addMoney(346, 551 + 129 - hjjeLen * 7.2 - 12);
    var hjse = (fpmx.se * 1).toFixed(2);
    var hjseLen = countStrLength(hjse);
    if (hjse == 0) {
        hjse = "***";
    } else {
        addMoney(346, 698 + 135 - hjseLen * 7.2 - 12);
    }
    LODOP.ADD_PRINT_TEXT(345, 696, 135, 20, hjse);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    LODOP.ADD_PRINT_TEXT(376, 303, 335, 20, (fpmx.jshj < 0 ? '(负数)' : '') + fpmx.jshjdx);
    addMoney(374, 680);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    LODOP.ADD_PRINT_TEXT(373, 695, 135, 20, fpmx.jshj.toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    var xhdwmc = fpmx.xhdwmc;
    var xhdwmcLen = countStrLength(xhdwmc);
    if (xhdwmcLen <= 50) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
    } else if (xhdwmcLen <= 56) {
        LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwmcLen <= 64) {
        if (xhdwmcLen <= 60) {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwmcLen <= 76) {
            LODOP.ADD_PRINT_TEXT(405, 208, 315, 16, xhdwmc);
        } else {
            LODOP.ADD_PRINT_TEXT(403, 208, 315, 16, xhdwmc);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    LODOP.ADD_PRINT_TEXT(419, 225, 276, 15, fpmx.xhdwdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var xhdwdzdh = fpmx.xhdwdzdh;
    var xhdwdzdhLen = countStrLength(xhdwdzdh);
    if (xhdwdzdhLen <= 50) {
        LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
    } else if (xhdwdzdhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwdzdhLen <= 64) {
        if (xhdwdzdhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwdzdhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(440, 208, 315, 16, xhdwdzdh);
        } else {
            LODOP.ADD_PRINT_TEXT(436, 208, 315, 16, xhdwdzdh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }
    var xhdwyhzh = fpmx.xhdwyhzh;
    var xhdwyhzhLen = countStrLength(xhdwyhzh);
    if (xhdwyhzhLen <= 49) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
    } else if (xhdwyhzhLen <= 56) {
        LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    } else if (xhdwyhzhLen <= 64) {
        if (xhdwyhzhLen <= 60) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    } else {
        if (xhdwyhzhLen <= 76) {
            LODOP.ADD_PRINT_TEXT(457, 208, 315, 16, xhdwyhzh);
        } else {
            LODOP.ADD_PRINT_TEXT(453, 208, 315, 16, xhdwyhzh);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
    }

    var jym = fpmx.jym;
    jym = jym.substring(0, 5) + " " + jym.substring(5, 10) + " " + jym.substring(10, 15) + " " + jym.substring(15, jym.length);
    var bzLen = countStrLength(fpmx.bz);
    if (bzLen <= 138) {
        LODOP.ADD_PRINT_TEXT(405, 539, 307, 61, "校验码 " + jym + "\n" + (fpmx.bz ? fpmx.bz : ""));
    } else {
        LODOP.ADD_PRINT_TEXT(403, 539, 307, 61, "校验码 " + jym + "\n" + (fpmx.bz ? fpmx.bz : ""));
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
    }
    LODOP.ADD_PRINT_TEXT(477, 152, 152, 15, fpmx.skr);
    LODOP.ADD_PRINT_TEXT(477, 359, 115, 15, fpmx.fhr);
    var kpr = fpmx.kpr;
    var kprLen = countStrLength(kpr);
    if (kprLen <= 14) {
        LODOP.ADD_PRINT_TEXT(476, 536, 91, 15, kpr);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else {
        LODOP.ADD_PRINT_TEXT(477, 536, 91, 15, kpr);
    }
}

function printZzszpQD(fpmx) {
    LODOP.SET_PRINT_STYLE("FontColor", "#0000FF");
    LODOP.ADD_PRINT_TEXT(41, 216, 330, 35, "销售货物或者提供应税劳务清单");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 14);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.ADD_PRINT_TEXT(90, 20, 534, 22, "购货单位名称:" + fpmx.ghdwmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(120, 20, 535, 23, "销货单位名称:" + fpmx.xhdwmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(154, 20, 358, 24, "所属增值税专用发票代码:" + fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(154, 395, 157, 23, "号码:" + fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(154, 585, 114, 25, " 第#页/共&页");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 2);
    
//    ADD_PRINT_LINE(Top1,Left1, Top2, Left2,intLineStyle, intLineWidth)
//    功能：增加直线，设定直线的两个端点，设定直线的线条类型和线宽。
    LODOP.ADD_PRINT_LINE(840, 70, 190, 71, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 300, 190, 301, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 358, 190, 359, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 400, 190, 401, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 454, 190, 455, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 528, 190, 529, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 613, 190, 614, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 642, 190, 643, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(841, 20, 840, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(191, 20, 190, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(840, 20, 190, 21, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(190, 710, 840, 711, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 20, 50, 20, "序号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 120, 145, 20, "货物（劳务）名称");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(215, 20, 216, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 307, 54, 20, "规格型号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 365, 34, 20, "单位");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 411, 40, 20, "数量");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 475, 40, 20, "单价");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 552, 40, 20, "金额");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 617, 35, 20, "税率");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 662, 40, 20, "税额");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(850, 36, 143, 25, "销货单位（章）:");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    var kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "年" + kprq.substring(4, 6) + "月" + kprq.substring(6, 8) + "日";
    LODOP.ADD_PRINT_TEXT(850, 473, 223, 25, "开票日期:" + kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(875, 36, 600, 25, "注：本清单一式两联：第一联，销售方留存；第二联，销售方送交购买方。");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(781, 20, 782, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(800, 24, 44, 25, "备注");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(788, 72, 630, 47, fpmx.bz);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(760, 20, 50, 20, "合计");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(740, 20, 50, 20, "小计");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    var upV = 220;
    var num = 0;
    var mxzbArr = fpmx.mxzb;
    var qdxms = [];
    for (var i = 0; i < mxzbArr.length; i++) {
        var mxzb = mxzbArr[i];
        if (mxzb.fphxz < 3) {
            qdxms.push(mxzb);
        }
    }
    var qdPages = Math.ceil(qdxms.length / 26);//总页数
    var qdPage = 0;//页号-1
    var pageXjje = 0;//单页小计金额
    var pageXjse = 0;//单页小计税额
    var pageHjje = 0;//此页为止合计金额
    var pageHjse = 0;//此页为止合计税额
    for (var n = 0; n < qdxms.length; n++) {
        var mxzb = qdxms[n];
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 20, 50, 20, n + 1);
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        var spmc = mxzb.spmc;
        var mcLen = countStrLength(spmc);
        if (mcLen <= 34) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
        } else if (mcLen <= 36) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        } else if (mcLen <= 80) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
            ;
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        var ggxh = mxzb.ggxh;
        var ggLen = countStrLength(ggxh);
        if (ggLen <= 8) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
        } else if (ggLen <= 16) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        } else if (ggLen <= 20) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        } else if (ggLen <= 24) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 5);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 5);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        var dw = mxzb.dw;
        var dwLen = countStrLength(dw);
        if (dwLen <= 5) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 360, 42, 20, dw);
        } else if (dwLen <= 10) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 360, 42, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 360, 42, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        var spslLen = countStrLength(mxzb.spsl ? mxzb.spsl : "");
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 390, 63, 20, mxzb.spsl ? delRight(mxzb.spsl.toFixed(6)) : "");// 商品数量
        if (spslLen > 8) {
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        var spdjLen = countStrLength( mxzb.spdj ? mxzb.spdj : "");
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 445, 82, 20, mxzb.spdj ? delRight(mxzb.spdj.toFixed(6)) : "");// 商品单价
        if(spdjLen > 11){
        	 LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);

        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 518, 95, 20, mxzb.je.toFixed(2));// 金额
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        pageXjje = pageXjje + parseFloat(mxzb.je);
        pageHjje = pageHjje + parseFloat(mxzb.je);
        // 税率
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 614, 34, 20, ((mxzb.sl + "").length > 4 && (mxzb.sl + "").substring(4, 5) * 1 > 0 ? (mxzb.sl * 100).toFixed(1) : (mxzb.sl * 100).toFixed()) + "%");
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 640, 69, 20, mxzb.se.toFixed(2));// 税额
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        pageXjse = pageXjse + parseFloat(mxzb.se);
        pageHjse = pageHjse + parseFloat(mxzb.se);
        if (num != 0 && num % 25 == 0) {
            LODOP.ADD_PRINT_TEXT(743, 501, 110, 20, pageXjje.toFixed(2)); // 小计金额
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);
            LODOP.ADD_PRINT_TEXT(743, 610, 100, 20, pageXjse.toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);
            LODOP.ADD_PRINT_TEXT(763, 502, 110, 20, pageHjje.toFixed(2));// 合计金额
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);
            LODOP.ADD_PRINT_TEXT(763, 610, 100, 20, pageHjse.toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);

            LODOP.NewPage();
            qdPage++;
            pageXjje = 0;
            pageXjse = 0;
            num = 0;
        } else {
            if (n == qdxms.length - 1) {
                LODOP.ADD_PRINT_TEXT(743, 502, 110, 20, pageXjje.toFixed(2));
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
                LODOP.ADD_PRINT_TEXT(743, 610, 100, 20, pageXjse.toFixed(2));
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
                LODOP.ADD_PRINT_TEXT(763, 502, 110, 20, pageHjje.toFixed(2));
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
                LODOP.ADD_PRINT_TEXT(763, 610, 100, 20, pageHjse.toFixed(2));
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
            } else {
                num++;
            }
        }
    }
}
function printZzsppQD(fpmx) {
    LODOP.SET_PRINT_STYLE("FontColor", "#0000FF");
    LODOP.ADD_PRINT_TEXT(41, 216, 330, 35, "销售货物或者提供应税劳务清单");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 14);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.ADD_PRINT_TEXT(90, 20, 534, 22, "购货单位名称:" + fpmx.ghdwmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(120, 20, 535, 23, "销货单位名称:" + fpmx.xhdwmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(154, 20, 358, 24, "所属增值税普通发票代码:" + fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(154, 395, 157, 23, "号码:" + fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(154, 585, 114, 25, " 第#页/共&页");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 2);
    LODOP.ADD_PRINT_LINE(840, 70, 190, 71, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 300, 190, 301, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 358, 190, 359, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 400, 190, 401, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 454, 190, 455, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 528, 190, 529, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 613, 190, 614, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(780, 642, 190, 643, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(841, 20, 840, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(191, 20, 190, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(840, 20, 190, 21, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(190, 710, 840, 711, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 20, 50, 20, "序号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 120, 145, 20, "货物（劳务）名称");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(215, 20, 216, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 307, 54, 20, "规格型号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 365, 34, 20, "单位");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 411, 40, 20, "数量");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 475, 40, 20, "单价");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 552, 40, 20, "金额");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 617, 35, 20, "税率");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(195, 662, 40, 20, "税额");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(850, 36, 143, 25, "销货单位（章）:");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    var kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "年" + kprq.substring(4, 6) + "月" + kprq.substring(6, 8) + "日";
    LODOP.ADD_PRINT_TEXT(850, 473, 223, 25, "开票日期:" + kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(875, 36, 600, 25, "注：本清单一式两联：第一联，销售方留存；第二联，销售方送交购买方。");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_LINE(781, 20, 782, 710, 0, 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(800, 24, 44, 25, "备注");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(788, 72, 630, 47, fpmx.bz);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(760, 20, 50, 20, "合计");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.ADD_PRINT_TEXT(740, 20, 50, 20, "小计");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    var upV = 220;
    var num = 0;
    var mxzbArr = fpmx.mxzb;
    var qdxms = [];
    for (var i = 0; i < mxzbArr.length; i++) {
        var mxzb = mxzbArr[i];
        if (mxzb.fphxz < 3) {
            qdxms.push(mxzb);
        }
    }
    var qdPages = Math.ceil(qdxms.length / 26);//总页数
    var qdPage = 0;//页号-1
    var pageXjje = 0;//单页小计金额
    var pageXjse = 0;//单页小计税额
    var pageHjje = 0;//此页为止合计金额
    var pageHjse = 0;//此页为止合计税额
    var qdslFlag = true;
    var qdslPageFlag = true;
    for (var n = 0; n < qdxms.length; n++) {
        var mxzb = qdxms[n];
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 20, 50, 20, n + 1);
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        var spmc = mxzb.spmc;
        var mcLen = countStrLength(spmc);
        if (mcLen <= 34) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
        } else if (mcLen <= 36) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        } else if (mcLen <= 80) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 75, 225, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        var ggxh = mxzb.ggxh;
        var ggLen = countStrLength(ggxh);
        if (ggLen <= 8) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
        } else if (ggLen <= 16) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        } else if (ggLen <= 20) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        } else if (ggLen <= 24) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 5);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 301, 65, 20, ggxh);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 5);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        var dw = mxzb.dw;
        var dwLen = countStrLength(dw);
        if (dwLen <= 5) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 360, 42, 20, dw);
        } else if (dwLen <= 10) {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 360, 42, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 360, 42, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 1);

        var spslLen = countStrLength(mxzb.spsl ? mxzb.spsl : "");
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 390, 63, 20, mxzb.spsl ? delRight(mxzb.spsl.toFixed(6)) : "");
        if (spslLen > 8) {
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        var spdjLen = countStrLength(mxzb.spdj ?mxzb.spdj :"");
        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 443, 85, 20, mxzb.spdj ? delRight(mxzb.spdj.toFixed(6)) : "");
        if(spdjLen > 11){
        	 LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        }
        
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);

        LODOP.ADD_PRINT_TEXT(upV + 20 * num, 518, 95, 20, mxzb.je.toFixed(2));
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        pageXjje = pageXjje + parseFloat(mxzb.je);
        pageHjje = pageHjje + parseFloat(mxzb.je);

        var qdsl = mxzb.sl;
        if (qdsl == 0) {
            if (mxzb.spbm) {
                if (mxzb.lslbs == "1") {
                    qdsl = "免税";
                }
                if (mxzb.lslbs == "2") {
                    qdsl = "不征税";
                }
                if (mxzb.lslbs == "3") {
                    qdsl = "0%";
                }
            } else {
                qdsl = "***";
            }
            if (qdsl == "不征税") {
                LODOP.ADD_PRINT_TEXT(upV + 20 * num, 608, 40, 20, qdsl);
                LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            } else if (qdsl == "免税") {
                LODOP.ADD_PRINT_TEXT(upV + 20 * num, 608, 40, 20, qdsl);
                LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            } else {
                LODOP.ADD_PRINT_TEXT(upV + 20 * num, 614, 34, 20, qdsl);
            }
            LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 614, 34, 20, ((qdsl + "").length > 4 && (qdsl + "").substring(4, 5) * 1 > 0 ? (qdsl * 100).toFixed(1) : (qdsl * 100).toFixed()) + "%");
            qdslFlag = false;
            qdslPageFlag = false;
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);

        var qdse = mxzb.se.toFixed(2);
        pageXjse = pageXjse + parseFloat(qdse);
        pageHjse = pageHjse + parseFloat(qdse);
        if (qdse == 0) {
            qdse = "***";
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 640, 69, 20, qdse);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        } else {
            LODOP.ADD_PRINT_TEXT(upV + 20 * num, 640, 69, 20, qdse);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        if (num != 0 && num % 25 == 0) {
            LODOP.ADD_PRINT_TEXT(743, 503, 110, 20, pageXjje.toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);
            var xjse = pageXjse.toFixed(2);
            if (xjse == 0) {
                xjse = "***";
            }
            LODOP.ADD_PRINT_TEXT(743, 610, 100, 20, xjse);
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);
            LODOP.ADD_PRINT_TEXT(763, 503, 110, 20, pageHjje.toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);
            var hjse = pageHjse.toFixed(2);
            if (hjse == 0) {
                hjse = "***";
            }
            LODOP.ADD_PRINT_TEXT(763, 610, 100, 20, hjse);
            LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPage + 1);

            LODOP.NewPage();
            qdPage++;
            pageXjje = 0;
            pageXjse = 0;
            num = 0;
        } else {
            if (n == qdxms.length - 1) {
                LODOP.ADD_PRINT_TEXT(743, 503, 110, 20, pageXjje.toFixed(2));
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
                var xjse = pageXjse.toFixed(2);
                if (xjse == 0) {
                    xjse = "***";
                }
                LODOP.ADD_PRINT_TEXT(743, 610, 100, 20, xjse);
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
                LODOP.ADD_PRINT_TEXT(763, 503, 110, 20, pageHjje.toFixed(2));
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
                var hjse = pageHjse.toFixed(2);
                if (hjse == 0) {
                    hjse = "***";
                }
                LODOP.ADD_PRINT_TEXT(763, 610, 100, 20, hjse);
                LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
                LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                LODOP.SET_PRINT_STYLEA(0, "PageIndex", qdPages);
            } else {
                num++;
            }
        }
    }
}
function addPrintLineZzszp(i, mc, gg, dw, spsl, dj, je, sl, se) {
    if (mc) {
        var mcLen = countStrLength(mc);
        if (mcLen <= 30) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 82, 197, 20, mc);
        } else if (mcLen <= 34) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 82, 197, 20, mc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else if (mcLen <= 40) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 82, 197, 20, mc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(214 + i * 17, 82, 197, 20, mc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    }
    if (gg) {
        var ggLen = countStrLength(gg);
        if (ggLen <= 14) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 281, 97, 20, gg);
        } else if (ggLen <= 16) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 281, 97, 20, gg);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else if (ggLen <= 18) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 281, 97, 20, gg);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(214 + i * 17, 281, 97, 20, gg);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
    }
    if (dw) {
        var dwLen = countStrLength(dw);
        if (dwLen <= 6) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 378, 45, 20, dw);
        } else if (dwLen <= 8) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 378, 45, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else {
            LODOP.ADD_PRINT_TEXT(214 + i * 17, 378, 45, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
    }
    if (spsl) {
        var spslLen = countStrLength(spsl);
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 411, 84, 20, spsl);
        if (spslLen > 11) {
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
            
        }
        //  LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
       
    }
    if (dj) {
        var djLen = countStrLength(dj);
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 485, 86, 20, dj);
        if (djLen > 11) {
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        }
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    }
    if (je) {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 569, 110, 20, je);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    }
    if (sl) {
    	
    	if(sl == "NaN%"){
    		LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, "");	
    	} else{
    		LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, sl);	
    	}
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    }
    if (se) {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 726, 104, 20, se);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    }
}
function addPrintLineZzspp(i, mc, gg, dw, spsl, dj, je, sl, se) {
    if (mc) {
        var mcLen = countStrLength(mc);
        if (mcLen <= 30) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 82, 197, 20, mc);
        } else if (mcLen <= 34) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 82, 197, 20, mc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else if (mcLen <= 40) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 82, 197, 20, mc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(214 + i * 17, 82, 197, 20, mc);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    }
    if (gg) {
        var ggLen = countStrLength(gg);
        if (ggLen <= 14) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 281, 97, 20, gg);
        } else if (ggLen <= 16) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 281, 97, 20, gg);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else if (ggLen <= 18) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 281, 97, 20, gg);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(214 + i * 17, 281, 97, 20, gg);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
    }
    if (dw) {
        var dwLen = countStrLength(dw);
        if (dwLen <= 6) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 378, 45, 20, dw);
        } else if (dwLen <= 8) {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 378, 45, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else {
            LODOP.ADD_PRINT_TEXT(214 + i * 17, 378, 45, 20, dw);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
        }
    }
    if (spsl) {
        var spslLen = countStrLength(spsl);
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 411, 80, 20, spsl);
        if (spslLen > 11) {
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
        }
        //  Alignment的值：数字型，1--左靠齐 2--居中 3--右靠齐，缺省值是1。
        //  LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -3);
    }    
    if (dj) {
        var djLen = countStrLength(dj);
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 486, 85, 20, dj);
        if (djLen > 11) {
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        }
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    }
    if (je) {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 569, 110, 20, je);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    }
    if ((sl + "") != "" && sl == "0" && se == 0) {
        sl = "***";
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, sl);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    } else if (sl == "0***" || sl == "1***" || sl == "j***") {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, "***");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    } else if (sl == "免税" || sl == "不征税" || sl == "0%") {
        if (sl == "不征税") {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 666, 60, 20, sl);
            //LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
        } else {
            LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, sl);
        }
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    } else if (sl == null || (sl + "") == "" || (sl == "0" && se != 0)) {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, "");
    } else {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 684, 37, 20, ((sl + "").length > 4 && (sl + "").substring(4, 5) * 1 > 0 ? (sl * 100).toFixed(1) : (sl * 100).toFixed()) + "%");
    }
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

    if (se == 0) {
        se = "***";
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 726, 104, 20, se);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    } else {
        LODOP.ADD_PRINT_TEXT(217 + i * 17, 726, 104, 20, se);
    }
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
}
function filterMxzbPrint(fpmx) {
    var qdbz = fpmx.qdbz == "1";
    return $.map(fpmx.mxzb, function (n) {
        return (!qdbz || (qdbz && (n.fphxz == "3" || n.fphxz == "4"))) && n.fphxz != "99" ? n : null;
    });
}
function filterMxzbQDPrint(fpmx) {
    var qdbz = fpmx.qdbz == "1";
    return $.map(fpmx.mxzb, function (n) {
        return (qdbz && (n.fphxz == "1" || n.fphxz == "2" || n.fphxz == "0")) && n.fphxz != "99" ? n : null;
    });
}
function printRzxh(json) {
    LODOP.PRINT_INITA("12.7mm", "12.7mm", "209mm", "147mm", "日终销号列表");
    LODOP.SET_PRINT_PAGESIZE(1, 2090, 1470, "CreateCustomPage");
    LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 890, 700, "");
    LODOP.ADD_PRINT_TEXT(0, 260, 238, 26, "增值税发票销号表");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 18);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.ADD_PRINT_TEXT(40, 0, 259, 20, "查询用户：" + json.czy);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(40, 260, 204, 20, "查询终端：" + json.kpd);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(40, 461, 306, 20, "打印时间：" + json.dysj);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(91, 0, 137, 20, "发票种类");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(91, 127, 144, 20, "发票代码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(91, 251, 146, 20, "发票起始号码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(91, 396, 152, 20, "发票终止号码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(91, 540, 159, 20, "销号数量");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(91, 620, 159, 20, "状态");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    var zf = 0;
    var zc = 0;
    $.each(json.xhlb, function (i, n) {
        LODOP.ADD_PRINT_TEXT(122 + i * 30, 0, 137, 20, n[0]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
        LODOP.ADD_PRINT_TEXT(122 + i * 30, 127, 144, 20, n[3]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
        LODOP.ADD_PRINT_TEXT(122 + i * 30, 251, 146, 20, n[2]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
        LODOP.ADD_PRINT_TEXT(122 + i * 30, 396, 152, 20, n[1]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
        LODOP.ADD_PRINT_TEXT(122 + i * 30, 540, 159, 20, n[4]);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
        LODOP.ADD_PRINT_TEXT(122 + i * 30, 620, 159, 20, n[5] == "1" ? "空白作废" : "正常");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
        n[5] == "1" ? zf += n[4] : zc += n[4];
    });
    LODOP.ADD_PRINT_TEXT(66, 0, 259, 20, "销号总数：" + (zf + zc));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(66, 260, 204, 20, "正常总数：" + zc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.ADD_PRINT_TEXT(66, 461, 306, 20, "空白作废总数：" + zf);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
    LODOP.PREVIEW();
}
/**
 * 打印税控码
 * @param skm 税控码
 * @param top 左上角第一个字的上边距
 * @param left 左上角第一个字的左边距
 * */
function printSkmOneByOne(skm, top, left) {
    //每隔2个字左移1像素
    var step = 2;
    for (var r = 0; r < 4; r++) {
        var row = skm.substring(r * 28, r * 28 + 28);
        var reduce = 0;
        for (var i = 0; i < 28; i++) {
            if (i != 0 && i % step == 0 && /*补偿3像素*/i % 8 != 0) {
                reduce++;
            }
            LODOP.ADD_PRINT_TEXT(top + r * 18, left + i * 10 - reduce, 15, 15, row.charAt(i));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    }

}
function printJsfp(fpmx, yl) {
    var jpbs = $.cookie("jsfpbs") || "06";
    fpmx.bs = (jpbs == "04") ? "07" : ((jpbs == "05") ? "06" : jpbs);
    if (fpmx.bs == "00") {
        LODOP.PRINT_INITA(yl ? 0 : ($.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "57mm", "1524mm", "增值税专用发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 570, 1524, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/57-152.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdbh = fpmx.fphm;
        LODOP.ADD_PRINT_TEXT(165, 80, 129, 10, jdbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(181, 80, 82, 14, fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.ADD_PRINT_TEXT(196, 84, 125, 46, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(242, 83, 130, 16, fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(123, 77, 147, 20, fpmx.fpdm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(144, 77, 147, 20, fpmx.fphm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);
        LODOP.ADD_PRINT_TEXT(257, 79, 123, 16, kprq);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(287, 20, 210, 46, "          " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(272, 80, 110, 16, fpmx.skr);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(424, 66, 100, 20, hjje);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var se = (fpmx.se * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(439, 66, 100, 20, se);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var jshj = (fpmx.jshj * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(455, 66, 100, 20, jshj);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var jshjdx = je2Upper(fpmx.jshj);
        LODOP.ADD_PRINT_TEXT(473, 66, 173, 10, jshjdx);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(333, 82, 130, 14, fpmx.ghdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var mxzbArr = fpmx.mxzb;
        for (var i = 0; i < mxzbArr.length; i++) {
            var mxzb = mxzbArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            dj = delRight(String(dj));
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            LODOP.ADD_PRINT_TEXT(360 + i * 22, 15, 60, 18, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
            LODOP.ADD_PRINT_TEXT(360 + i * 20, 65, 60, 12, (dj == 0 ? "" : dj));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.ADD_PRINT_TEXT(360 + i * 20, 120, 50, 12, (sl == 0 ? "" : sl));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.ADD_PRINT_TEXT(360 + i * 20, 160, 60, 12, (hsje * 1).toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        }
        LODOP.ADD_PRINT_TEXT(487, 40, 150, 20, fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        //LODOP.ADD_PRINT_IMAGE(503, 66, 66, 66, "data:image/png;base64,"+ fpmx.ewm);
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    } else if (fpmx.bs == "01") {
        LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "57mm", "177.8mm", "增值税专用发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 570, 1778, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/57-177.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdbh = fpmx.fphm;
        var se = (fpmx.se * 1).toFixed(2);
        var jshj = (fpmx.jshj * 1).toFixed(2);
        var jshjdx = je2Upper(fpmx.jshj);
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(517, 64, 100, 12, hjje);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(166, 77, 129, 10, jdbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(181, 77, 82, 14, fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.ADD_PRINT_TEXT(196, 85, 125, 46, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(242, 83, 130, 16, fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(123, 75, 147, 20, fpmx.fpdm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(144, 77, 147, 20, fpmx.fphm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);

        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);
        LODOP.ADD_PRINT_TEXT(257, 80, 123, 14, kprq);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(288, 20, 210, 46, "          " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(272, 80, 110, 16, fpmx.skr);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(549, 69, 100, 12, jshj);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(565, 67, 186, 12, jshjdx);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(335, 82, 130, 14, fpmx.ghdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(533, 65, 100, 12, fpmx.se);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        var spmxArr = fpmx.mxzb;

        for (var i = 0; i < spmxArr.length; i++) {
            var mxzb = spmxArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            dj = delRight(String(dj));
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            LODOP.ADD_PRINT_TEXT(360 + i * 25, 18, 64, 12, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -7);
            LODOP.ADD_PRINT_TEXT(360 + i * 25, 57, 58, 12, (dj == 0 ? "" : dj));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(360 + i * 25, 110, 45, 12, (sl == 0 ? "" : sl));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(360 + i * 25, 149, 58, 12, (hsje * 1).toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        }
        LODOP.ADD_PRINT_TEXT(578, 65, 168, 16, fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        //LODOP.ADD_PRINT_IMAGE(590, 70, 66, 66, "data:image/png;base64," + fpmx.ewm);
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    } else if (fpmx.bs == "02") {
        LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "76", "152.4mm", "增值税普通发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 760, 1524, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/76-152.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdbh = fpmx.fphm;
        LODOP.ADD_PRINT_TEXT(165, 80, 70, 10, jdbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(165, 188, 93, 12, fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(180, 80, 212, 26, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(211, 81, 150, 12, fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(123, 79, 150, 20, fpmx.fpdm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(144, 79, 150, 20, fpmx.fphm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);

        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);
        LODOP.ADD_PRINT_TEXT(227, 81, 70, 20, kprq);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(228, 180, 100, 20, fpmx.skr);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(242, 20, 270, 26, "          " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(273, 84, 212, 18, fpmx.ghdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var se = (fpmx.se * 1).toFixed(2);
        var jshj = (fpmx.jshj * 1).toFixed(2);
        var jshjdx = je2Upper(fpmx.jshj);
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(414, 80, 100, 12, hjje);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(429, 80, 100, 12, se);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(444, 80, 100, 12, jshj);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(460, 80, 239, 12, jshjdx);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(477, 80, 150, 12, fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var mxzbArr = fpmx.mxzb;
        for (var i = 0; i < mxzbArr.length; i++) {
            var mxzb = mxzbArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            dj = delRight(String(dj));
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            LODOP.ADD_PRINT_TEXT(302 + i * 35, 16, 90, 24, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
            LODOP.ADD_PRINT_TEXT(302 + i * 35, 75, 71, 20, (dj == 0 ? "" : dj));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(302 + i * 35, 137, 54, 20, (sl == 0 ? "" : sl));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(302 + i * 35, 185, 74, 20, (hsje * 1).toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        }
        //LODOP.ADD_PRINT_IMAGE(500, 80, 66, 66, "data:image/png;base64,"+ fpmx.ewm);
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    } else if (fpmx.bs == "03") {
        LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "76mm", "177.8mm", "增值税专用发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 760, 1778, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/76-177.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdhm = fpmx.fphm;
        LODOP.ADD_PRINT_TEXT(165, 80, 61, 10, jdhm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(165, 188, 83, 12, fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(180, 80, 218, 23, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(211, 81, 150, 12, fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(122, 79, 150, 20, fpmx.fpdm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        //LODOP.SET_PRINT_STYLEA(0,"LineSpacing",-6);
        LODOP.ADD_PRINT_TEXT(143, 79, 150, 20, fpmx.fphm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        //LODOP.SET_PRINT_STYLEA(0,"LineSpacing",-6);
        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);

        LODOP.ADD_PRINT_TEXT(226, 81, 80, 20, kprq);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(227, 180, 100, 20, fpmx.skr);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(243, 20, 270, 18, "          " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(273, 81, 201, 18, fpmx.ghdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var se = (fpmx.se * 1).toFixed(2);
        var jshj = (fpmx.jshj * 1).toFixed(2);
        var jshjdx = je2Upper(fpmx.jshj);
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(507, 80, 100, 12, hjje);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(522, 80, 100, 12, se);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(539, 80, 100, 12, jshj);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(554, 80, 239, 12, jshjdx);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(570, 80, 195, 12, fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var mxzbArr = fpmx.mxzb;

        for (var i = 0; i < mxzbArr.length; i++) {
            var mxzb = mxzbArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            dj = delRight(String(dj));
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            LODOP.ADD_PRINT_TEXT(302 + i * 32, 16, 90, 25, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
            LODOP.ADD_PRINT_TEXT(302 + i * 32, 74, 71, 15, (dj == 0 ? "" : dj));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -7);
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(302 + i * 32, 140, 54, 15, (sl == 0 ? "" : sl));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(302 + i * 32, 185, 74, 15, (hsje * 1).toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        }
        //LODOP.ADD_PRINT_IMAGE(590, 80, 66, 66, "data:image/png;base64,"+ fpmx.ewm);
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    } else if (fpmx.bs == "07") {
        LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "57mm", "177.8mm", "增值税专用发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 570, 1778, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/57-177.8.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdbh = fpmx.fphm;
        var se = (fpmx.se * 1).toFixed(2);
        var jshj = (fpmx.jshj * 1).toFixed(2);
        var jshjdx = je2Upper(fpmx.jshj);
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(165, 77, 129, 10, jdbh);
        LODOP.ADD_PRINT_TEXT(180, 77, 82, 14, fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.ADD_PRINT_TEXT(195, 79, 130, 46, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(242, 88, 130, 16, fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);
        LODOP.ADD_PRINT_TEXT(257, 80, 123, 14, kprq);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 11);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(289, 20, 200, 46, "          " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(272, 80, 110, 16, fpmx.skr);
        LODOP.ADD_PRINT_TEXT(552, 65, 100, 12, "￥" + jshj);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        //addMoney(550,64);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        LODOP.ADD_PRINT_TEXT(334, 86, 130, 14, fpmx.ghdwdm);
        if (fpmx.fpzt == "01" || fpmx.fpzt == "04") {
            LODOP.ADD_PRINT_TEXT(542, 20, 142, 20, "原发票号码：" + fpmx.yfphm);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.ADD_PRINT_TEXT(531, 20, 155, 20, "原发票代码：" + fpmx.yfpdm);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.ADD_PRINT_TEXT(568, 62, 186, 12, "负" + jshjdx);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7.5);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        } else {
            LODOP.ADD_PRINT_TEXT(568, 62, 186, 12, jshjdx);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7.5);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        }
        var spmxArr = fpmx.mxzb;
        var zhs8hz = 0;
        for (var i = 0; i < spmxArr.length; i++) {
            var mxzb = spmxArr[i];
            var spmc = mxzb.spmc;
            var zyhs7 = Math.ceil(countStrLength(spmc) / 10);
            var zyhs8 = Math.ceil(countStrLength(spmc) / 8);
            mxzb.zyhs7 = zyhs7;
            mxzb.zyhs8 = zyhs8;
            zhs8hz += zyhs8;
        }
        //已消耗行数
        var totalZyhs = 0;
        for (var i = 0; i < spmxArr.length; i++) {
            var mxzb = spmxArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            //dj = delRight(new String(dj));
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            if (countStrLength(fpmx.bz) > 0) {
                if (zhs8hz > 11) {//10
                    PrintLine07jsfp7hz(totalZyhs, spmc, dj, sl, hsje)//7号字
                    totalZyhs += mxzb.zyhs7;
                } else {
                    PrintLine07jsfp8hz(totalZyhs, spmc, dj, sl, hsje);//8号字
                    totalZyhs += mxzb.zyhs8;
                }
            } else {
                if (zhs8hz > 14) {//12
                    PrintLine07jsfp7hz(totalZyhs, spmc, dj, sl, hsje)//7号字
                    totalZyhs += mxzb.zyhs7;
                } else {
                    PrintLine07jsfp8hz(totalZyhs, spmc, dj, sl, hsje);//8号字
                    totalZyhs += mxzb.zyhs8;
                }
            }
            // addPrintLinejsfp(i, fpmx.bs, spmc);
            // LODOP.ADD_PRINT_TEXT(361 + i * 24, 58, 58, 12, (dj == 0 ? "" : dj));
            // LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            // LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            // LODOP.ADD_PRINT_TEXT(361 + i * 24, 105, 51, 12, (sl == 0 ? "" : sl));
            // LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            // LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            // LODOP.ADD_PRINT_TEXT(361 + i * 24, 147, 63, 12, (hsje * 1).toFixed(2));
            // LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            // LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);

        }
        addPrintLineJsfpBZ(fpmx.bs, fpmx.bz);
        LODOP.ADD_PRINT_TEXT(584, 65, 168, 16, fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -50);
        // LODOP.ADD_PRINT_IMAGE(600, 20, 66, 66, "data:image/png;base64," + fpmx.ewm);
        // LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    } else if (fpmx.bs == "06") {
        LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "76mm", "177.8mm", "增值税专用发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 760, 1778, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/76-177.8.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdhm = fpmx.fphm;
        LODOP.ADD_PRINT_TEXT(165, 80, 61, 10, jdhm);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(165, 190, 83, 12, fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(181, 20, 247, 23, "          " + fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(211, 84, 150, 12, fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);

        LODOP.ADD_PRINT_TEXT(227, 81, 80, 20, kprq);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(227, 180, 100, 20, fpmx.skr);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(244, 20, 247, 30, "          " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(275, 82, 201, 18, fpmx.ghdwdm);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var se = (fpmx.se * 1).toFixed(2);
        var jshj = (fpmx.jshj * 1).toFixed(2);
        var jshjdx = je2Upper(fpmx.jshj);
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(554, 78, 100, 12, "￥" + jshj);
        //addMoney(552,78)

        LODOP.ADD_PRINT_TEXT(585, 80, 195, 12, fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        if (fpmx.fpzt == "01" || fpmx.fpzt == "04") {
            LODOP.ADD_PRINT_TEXT(533, 18, 155, 20, "原发票代码：" + fpmx.yfpdm);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.ADD_PRINT_TEXT(543, 18, 145, 20, "原发票号码：" + fpmx.yfphm);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.ADD_PRINT_TEXT(570, 75, 239, 12, "负" + jshjdx);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7.5);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        } else {
            LODOP.ADD_PRINT_TEXT(570, 75, 239, 12, jshjdx);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7.5);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        }
        var mxzbArr = fpmx.mxzb;
        var zjj = 0;
        var zhs8hz = 0;
        for (var i = 0; i < mxzbArr.length; i++) {
            var mxzb = mxzbArr[i];
            var spmc = mxzb.spmc;
            var zyhs7 = Math.ceil(countStrLength(spmc) / 14);
            var zyhs8 = Math.ceil(countStrLength(spmc) / 12);
            mxzb.zyhs7 = zyhs7;
            mxzb.zyhs8 = zyhs8;
            zhs8hz += zyhs8;
        }
        //已消耗行数
        var totalZyhs = 0;
        for (var i = 0; i < mxzbArr.length; i++) {
            var mxzb = mxzbArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            if (countStrLength(fpmx.bz) > 0) {
                if (zhs8hz > 17) {
                    PrintLine06jsfp7hz(totalZyhs, spmc, dj, sl, hsje)//7号字
                    totalZyhs += mxzb.zyhs7;
                } else {
                    PrintLine06jsfp8hz(totalZyhs, spmc, dj, sl, hsje);//8号字
                    totalZyhs += mxzb.zyhs8;
                }
            } else {
                if (zhs8hz > 19) {
                    PrintLine06jsfp7hz(totalZyhs, spmc, dj, sl, hsje)//7号字
                    totalZyhs += mxzb.zyhs7;
                } else {
                    PrintLine06jsfp8hz(totalZyhs, spmc, dj, sl, hsje);//8号字
                    totalZyhs += mxzb.zyhs8;
                }
            }

        }
        addPrintLineJsfpBZ(fpmx.bs, fpmx.bz);
        // LODOP.ADD_PRINT_IMAGE(600, 20, 66, 66, "data:image/png;base64,"+ fpmx.ewm);
        // LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    } else if (fpmx.bs == "08") {
        LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jsfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jsfp") || 0) + "mm", "76", "152.4mm", "增值税普通发票(卷式)" + fpmx.bs);
        LODOP.SET_PRINT_PAGESIZE(1, 760, 1524, "CreateCustomPage");
        LODOP.ADD_PRINT_SETUP_BKIMG("<img src=\"" + ctxres + "/images/76-152.4.jpg" + "\"/>");
        LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
        var jdbh = fpmx.fphm;
        LODOP.ADD_PRINT_TEXT(211, 16, 120, 10, "机打号码：" + jdbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(211, 127, 144, 12, "机器编号：" + fpmx.jqbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(223, 16, 260, 26, "销售方名称：" + fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(257, 16, 264, 12, "销售方税号：" + fpmx.xhdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(109, 108, 150, 20, fpmx.fpdm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(131, 109, 150, 20, fpmx.fphm);
        LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", true);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);

        var kprq;
        kprq = fpmx.kprq;
        kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);
        LODOP.ADD_PRINT_TEXT(269, 16, 129, 20, "开票日期：" + kprq);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        LODOP.ADD_PRINT_TEXT(269, 125, 153, 20, "收款员：" + fpmx.skr);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        LODOP.ADD_PRINT_TEXT(156, 45, 230, 35, "               " + fpmx.ghdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        LODOP.ADD_PRINT_TEXT(199, 16, 262, 18, "购货方税号：" + fpmx.ghdwdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        var se = (fpmx.se * 1).toFixed(2);
        var jshj = (fpmx.jshj * 1).toFixed(2);
        var jshjdx = je2Upper(fpmx.jshj);
        var hjje = (fpmx.hjje * 1).toFixed(2);
        LODOP.ADD_PRINT_TEXT(414, 16, 268, 12, "合计金额（小写）：" + hjje);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(429, 16, 268, 12, "合计税额（小写）：" + se);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(444, 16, 268, 12, "价税合计（小写）：" + jshj);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        if (fpmx.fpzt == "01" || fpmx.fpzt == "04") {
            LODOP.ADD_PRINT_TEXT(390, 16, 155, 20, "原发票代码：" + fpmx.yfpdm);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.ADD_PRINT_TEXT(401, 16, 145, 20, "原发票号码：" + fpmx.yfphm);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.ADD_PRINT_TEXT(460, 16, 289, 12, "合计金额（大写）：负" + jshjdx);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7.5);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        } else {
            LODOP.ADD_PRINT_TEXT(460, 16, 289, 12, "合计金额（大写）：" + jshjdx);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7.5);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        }

        LODOP.ADD_PRINT_TEXT(475, 16, 268, 12, "检验码：" + fpmx.skm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(283, 15, 50, 20, "项目");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(283, 88, 61, 20, "含税单价");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(283, 160, 40, 20, "数量");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.ADD_PRINT_TEXT(283, 208, 73, 20, "含税金额");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        var mxzbArr = fpmx.mxzb;
        var zjj = 0;
        for (var i = 0; i < mxzbArr.length; i++) {
            var mxzb = mxzbArr[i];
            var spmc = mxzb.spmc;
            if (spmc == "") {
                break;
            }
            var hsdj = mxzb.hsdj;
            var dj = (hsdj * 1).toFixed(2);
            //dj = delRight(new String(dj));
            var spsl = mxzb.spsl;
            var sl = (spsl * 1).toFixed(2);
            sl = delRight(String(sl));
            var hsje = mxzb.hsje;
            var spmclength = countStrLength(spmc);
            var hs = Math.ceil(spmclength / 12);
            LODOP.ADD_PRINT_TEXT(295 + zjj, 16, 78, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
            LODOP.ADD_PRINT_TEXT(295 + zjj, 71, 71, 15, (dj == 0 ? "" : dj));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -7);
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(295 + zjj, 132, 58, 15, (sl == 0 ? "" : sl));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            LODOP.ADD_PRINT_TEXT(295 + zjj, 181, 75, 15, (hsje * 1).toFixed(2));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
            zjj += (hs * 11);
        }
        //LODOP.ADD_PRINT_IMAGE(500, 80, 66, 66, "data:image/png;base64,"+ fpmx.ewm);
        LODOP.SET_PRINT_STYLEA(0, "Stretch", 2);
    }
}
function printJdcfp(fpmx, yl) {
    LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_jdcfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_jdcfp") || 0) + "mm", "241mm", "177mm", "机动车销售统一发票");
    LODOP.SET_PRINT_PAGESIZE(1, 2410, 1770, "CreateCustomPage");
    //LODOP.ADD_PRINT_SETUP_BKIMG("C:\\Users\\llenovo\\Desktop\\jdcKJ.jpg");
    LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
    LODOP.ADD_PRINT_TEXT(50, 705, 131, 20, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(72, 705, 125, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(127, 209, "51.3mm", 20, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(155, 207, 196, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(184, 207, 196, 20, fpmx.jqbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    printJdcfpSkmOneByOne(fpmx.skm, 115, 447);
    // LODOP.ADD_PRINT_TEXT(108,437,400,107,fpmx.skm);
    // LODOP.SET_PRINT_STYLEA(0,"FontName","Courier New");
    // LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -2);
    var gmfmc = fpmx.gmfmc.length;
    if (gmfmc < 20) {
        LODOP.ADD_PRINT_TEXT(234, 185, 260, 45, fpmx.gmfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.ADD_PRINT_TEXT(245, 185, 247, 15, fpmx.sfzhm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (gmfmc >= 20 && gmfmc < 45) {
        LODOP.ADD_PRINT_TEXT(229, 185, 260, 45, fpmx.gmfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.ADD_PRINT_TEXT(247, 185, 247, 15, fpmx.sfzhm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (gmfmc >= 45) {
        LODOP.ADD_PRINT_TEXT(225, 185, 260, 45, fpmx.gmfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.ADD_PRINT_TEXT(253, 185, 247, 15, fpmx.sfzhm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    }
    LODOP.ADD_PRINT_TEXT(234, 604, 238, 30, fpmx.ghfsbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var cllx = fpmx.cllx.length;
    if (cllx < 13) {
        LODOP.ADD_PRINT_TEXT(280, 187, 165, 21, fpmx.cllx);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (cllx >= 13) {
        LODOP.ADD_PRINT_TEXT(277, 187, 165, 21, fpmx.cllx);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    }

    var cpxh = fpmx.cpxh.length;
    if (cpxh < 20) {
        LODOP.ADD_PRINT_TEXT(280, 411, 260, 20, fpmx.cpxh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (cpxh >= 20) {
        LODOP.ADD_PRINT_TEXT(278, 411, 260, 20, fpmx.cpxh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    }

    var cd = fpmx.cd.length;
    if (cd < 10) {
        LODOP.ADD_PRINT_TEXT(280, 703, 127, 20, fpmx.cd);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (cd >= 10) {
        LODOP.ADD_PRINT_TEXT(278, 703, 130, 20, fpmx.cd);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    }
    var hgzh = fpmx.hgzh.length;
    if (hgzh < 23) {
        LODOP.ADD_PRINT_TEXT(312, 185, 168, 25, fpmx.hgzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (hgzh >= 23) {
        LODOP.ADD_PRINT_TEXT(312, 185, 168, 25, fpmx.hgzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
    }

    LODOP.ADD_PRINT_TEXT(310, 447, 190, 19, fpmx.jkzmsh);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.ADD_PRINT_TEXT(311, 704, 134, 20, fpmx.sjdh);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.ADD_PRINT_TEXT(347, 187, 259, 20, fpmx.fdjhm);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.ADD_PRINT_TEXT(348, 591, 248, 25, fpmx.clsbdh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    // //图片打印显示
    // LODOP.ADD_PRINT_IMAGE(384,189,392,20,"data:image/bmp;base64,Qk0GAwAAAAAAADYAAAAoAAAADwAAAA8AAAABABgAAAAAAN" +
    // 	"ACAAAAAAAAAAAAAAAAAAAAAAAA////////////////////////////////////////////////////////////AAAA////////////////" +
    // 	"////////////////////////////////////////////AAAA////////////////AAAAAAAAAAAAAAAAAAAAAAAA///////////////////" +
    // 	"/AAAA////////////AAAAAAAA////////////////AAAAAAAA////////////////AAAA////////AAAAAAAA//////////////////////" +
    // 	"//AAAAAAAA////////////AAAA////AAAAAAAA////AAAAAAAA////////AAAAAAAA////AAAAAAAA////////AAAA////AAAAAAAA///////" +
    // 	"/AAAAAAAAAAAAAAAA////////AAAAAAAA////////AAAA////AAAAAAAA////////////AAAAAAAA////////////AAAAAAAA////////AAAA/" +
    // 	"///AAAAAAAA////////AAAAAAAAAAAAAAAA////////AAAAAAAA////////AAAA////AAAAAAAA////AAAAAAAA////////AAAAAAAA////AAAAA" +
    // 	"AAA////////AAAA////////AAAAAAAA////////////////////////AAAAAAAA////////////AAAA////////////AAAAAAAA//////////////" +
    // 	"//AAAAAAAA////////////////AAAA////////////////AAAAAAAAAAAAAAAAAAAAAAAA////////////////////AAAA////////////////////" +
    // 	"////////////////////////////////////////AAAA////////////////////////////////////////////////////////////AAAA");
    var jshjdx = je2Upper(fpmx.jshj) + "";
    var jshj = (fpmx.jshj) * 1;
    LODOP.ADD_PRINT_TEXT(384, 190, 392, 20, (fpmx.fpzt == "01" ? "负数：" + jshjdx : jshjdx));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.ADD_PRINT_TEXT(384, 662, 170, 20, "￥" + (jshj).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var xhdwmc = fpmx.xhdwmc.length;
    if (xhdwmc < 30) {
        LODOP.ADD_PRINT_TEXT(420, 187, 390, 26, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (xhdwmc >= 30) {
        LODOP.ADD_PRINT_TEXT(418, 187, 390, 26, fpmx.xhdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    }
    LODOP.ADD_PRINT_TEXT(417, 619, 184, 20, fpmx.dh);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.ADD_PRINT_TEXT(453, 188, 360, 25, fpmx.nsrsbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(452, 619, 184, 20, fpmx.zh);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    var dz = fpmx.dz.length;
    if (dz < 23) {
        LODOP.ADD_PRINT_TEXT(490, 185, 305, 25, fpmx.dz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (dz >= 23) {
        LODOP.ADD_PRINT_TEXT(488, 185, 305, 25, fpmx.dz);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    }
    var khyh = fpmx.khyh.length;
    if (khyh < 23) {
        LODOP.ADD_PRINT_TEXT(491, 539, 295, 20, fpmx.khyh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else if (khyh >= 23) {
        LODOP.ADD_PRINT_TEXT(489, 539, 295, 20, fpmx.khyh);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    }

    var sl, se;
    var lslbs = fpmx.lslbs;
    if (lslbs == "1") {
        sl = "免税";
        se = "***";
    }
    if (lslbs == "2") {
        sl = "不征税";
        se = "***";
    }
    if (lslbs == "3") {
        sl = "0%";
        se = "***";
    }
    if (lslbs == "" || lslbs == null) {
        var zzssl = fpmx.zzssl
        if (zzssl == 0) {
            sl = "0%";
            se = "***";
        } else {
            sl = zzssl * 100 + "%";
            se = "￥" + (fpmx.zzsse * 1).toFixed(2);
        }
    }
    LODOP.ADD_PRINT_TEXT(527, 194, 77, 26, sl);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(525, 338, 169, 25, se);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var zgswjg = fpmx.zgswjg.length;
    if (zgswjg < 20) {
        LODOP.ADD_PRINT_TEXT(525, 577, 256, 35, fpmx.zgswjg);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.ADD_PRINT_TEXT(537, 577, 195, 20, fpmx.swjgdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (zgswjg >= 20 && zgswjg < 45) {
        LODOP.ADD_PRINT_TEXT(521, 577, 256, 35, fpmx.zgswjg);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.ADD_PRINT_TEXT(539, 577, 195, 20, fpmx.swjgdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    } else if (zgswjg >= 45) {
        LODOP.ADD_PRINT_TEXT(517, 577, 256, 35, fpmx.zgswjg);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        LODOP.ADD_PRINT_TEXT(545, 577, 195, 20, fpmx.swjgdm);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    }
    LODOP.ADD_PRINT_TEXT(571, 213, 170, 19, "￥" + (fpmx.bhsj * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
    LODOP.ADD_PRINT_TEXT(567, 668, 44, 20, fpmx.dw);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.ADD_PRINT_TEXT(567, 774, 50, 25, fpmx.xcrs);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.ADD_PRINT_TEXT(602, 450, 100, 20, fpmx.kpr);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.ADD_PRINT_TEXT(565, 452, 176, 30, fpmx.wspzhm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var kprq;
    kprq = fpmx.kprq;
    kprq = kprq.substring(0, 4) + "-" + kprq.substring(4, 6) + "-" + kprq.substring(6, 8);
    LODOP.ADD_PRINT_TEXT(84, 157, 120, 20, kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_SHAPE(2, 103, 82, 745, 497, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 103, 434, 393, 119, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 103, 184, 1, 497, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(127, 92, 85, 20, "机打代码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_SHAPE(0, 221, 82, 352, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 270, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 305, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 339, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 376, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 410, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 481, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 445, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 515, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 560, 82, 745, 1, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, 103, 406, 1, 119, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 221, 434, 1, 49, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, 222, 601, 1, 47, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 269, 342, 65, 36, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 269, 661, 40, 36, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 304, 342, 95, 35, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 304, 637, 63, 35, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 339, 437, 150, 37, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 409, 569, 44, 72, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 480, 475, 62, 35, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 514, 278, 62, 46, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 514, 491, 82, 46, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 559, 357, 90, 41, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 559, 625, 40, 41, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(2, 559, 704, 62, 41, 0, 1, "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(155, 92, 85, 20, "机打号码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(184, 92, 85, 20, "机器编号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(128, 411, 21, 79, "税控码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "AlignJustify", 1);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 4);
    LODOP.ADD_PRINT_TEXT(223, 92, 89, 45, "购买方名称及\n身份证号码/\n组织结构代码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(281, 92, 90, 20, "车辆类型");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(281, 347, 64, 20, "厂牌型号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(281, 670, 39, 20, "产地");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(235, 469, 100, 20, "纳税人识别号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(313, 92, 90, 20, "合格证号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(350, 92, 80, 20, "发动机号码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(313, 345, 80, 20, "进口证明号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(313, 639, 63, 20, "商检单号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(350, 440, 155, 25, "车辆识别号代码/车架号码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(384, 92, 95, 20, "价税合计");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(384, 627, 41, 20, "小写");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(420, 92, 95, 20, "销货单位名称");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(420, 573, 39, 20, "电话");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(456, 573, 36, 20, "账号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(456, 92, 90, 20, "纳税人识别号");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(491, 92, 90, 20, "地址");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(522, 92, 95, 41, "增值税税率\n或 征 收 率");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(522, 285, 53, 35, "增值税\n税 额");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(491, 481, 63, 20, "开户银行");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(522, 495, 84, 36, "主管税务\n机关及代码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(571, 92, 85, 20, "不含税价");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(571, 185, 41, 20, "小写");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.ADD_PRINT_TEXT(571, 360, 85, 20, "完税凭证号码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(571, 630, 36, 20, "吨位");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(571, 709, 65, 20, "限乘人数");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(602, 81, 100, 20, "销货单位盖章");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(602, 384, 55, 20, "开票人");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(602, 631, 142, 20, "备注：一车一票");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(84, 86, 75, 20, "开票日期");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.ADD_PRINT_TEXT(50, 629, 75, 20, "发票代码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(73, 630, 74, 20, "发票号码");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(11, 285, 368, 30, "机 动  车  销  售  统  一  发  票");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(54, 379, 153, 30, "发   票   联");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#E22C3E");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_IMAGE(15, 90, 66, 66, "data:image/png;base64," + fpmx.ewm);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 1);
}
function printEscfp(fpmx, yl) {
    LODOP.PRINT_INITA((yl ? 0 : $.cookie("zbj_escfp") || 0) + "mm", (yl ? 0 : $.cookie("ybj_escfp") || 0) + "mm", "216.51mm", "178.01mm", "二手车销售统一发票");
    LODOP.SET_PRINT_PAGESIZE(1, "216.51mm", "178.01mm", "CreateCustomPage");
    LODOP.ADD_PRINT_SHAPE(2, "30.27mm", "9mm", "200mm", "131mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "51.01mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "58.21mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "66.2mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, "74mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "81.81mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "89.51mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "97.29mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "105.09mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "113mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "120.6mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "128.4mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "144.01mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "151.9mm", "9.21mm", "200mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, "30.27mm", "28.71mm", "0.21mm", "21.01mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "30.27mm", "100.99mm", "0.21mm", "21.01mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(1, "30.22mm", "109.01mm", "0.21mm", "21.01mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "51.01mm", "49mm", "0.21mm", "100.7mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "51.01mm", "114.91mm", "0.21mm", "7.2mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "66.2mm", "114.91mm", "0.21mm", "7.81mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "51.01mm", "152.51mm", "0.21mm", "30.8mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "58.21mm", "164.49mm", "0.21mm", "7.99mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "74mm", "164.49mm", "0.21mm", "31.09mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "81.81mm", "84.01mm", "0.21mm", "15.5mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "81.81mm", "102mm", "0.21mm", "15.5mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "81.81mm", "142.9mm", "0.21mm", "15.5mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "97.29mm", "152.51mm", "0.21mm", "7.81mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "113mm", "134.99mm", "0.21mm", "7.59mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "113mm", "152.51mm", "0.21mm", "7.59mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "120.6mm", "164.49mm", "0.21mm", "7.81mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "120.6mm", "177.01mm", "0.21mm", "7.81mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "128.4mm", "110.99mm", "0.21mm", "15.61mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "128.4mm", "128.91mm", "0.21mm", "15.61mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "144.01mm", "165.5mm", "0.21mm", "7.91mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "144.01mm", "177.01mm", "0.21mm", "7.81mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_SHAPE(0, "136.5mm", "110.99mm", "98mm", "0.21mm", 0, 1, "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT("34.32mm", "9.95mm", "20mm", "16.48mm", "机打代码\n机打号码\n机器编号");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 3);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT("8.23mm", "70.54mm", "78mm", "6.11mm", "二手车销售统一发票");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 8);
    LODOP.ADD_PRINT_TEXT(73, 322, 200, 20, "发票联");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 24);
    LODOP.ADD_PRINT_TEXT(89, 36, 100, 19, "开票日期：");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT("11.54mm", "160.52mm", "20mm", "5mm", "发票代码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT("19.31mm", "160.52mm", "19mm", "5mm", "发票号码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(125, 383, 32, 71, "税控码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", 5);
    LODOP.ADD_PRINT_TEXT(201, 44, 154, 19, "买方单位/个人 ");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 9);
    LODOP.ADD_PRINT_TEXT(231, 34, 158, 20, "买方单位/个人住址");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 4);
    LODOP.ADD_PRINT_TEXT(258, 38, 154, 19, "卖方单位/个人");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 9);
    LODOP.ADD_PRINT_TEXT(289, 34, 158, 20, "卖方单位/个人住址");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 4);
    LODOP.ADD_PRINT_TEXT(318, 42, 169, 20, "车牌照号");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 28);
    LODOP.ADD_PRINT_TEXT(348, 33, 160, 20, "车架号/车辆识别代码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(377, 40, 157, 20, "车价合计（大写）");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 6);
    LODOP.ADD_PRINT_TEXT(406, 40, 151, 20, "经营、拍卖单位");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 8);
    LODOP.ADD_PRINT_TEXT(435, 39, 147, 20, "经营、拍卖单位地址");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(466, 31, 170, 20, "开户银行、账户");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 8);
    LODOP.ADD_PRINT_TEXT(509, 21, 195, 20, "二手车市场");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 16);
    LODOP.ADD_PRINT_TEXT(553, 43, 144, 20, "开户银行、账户");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 7);
    LODOP.ADD_PRINT_TEXT(586, 40, 100, 20, "备 注：");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 5);
    LODOP.ADD_PRINT_TEXT(616, 40, 147, 20, "开票单位（盖章）");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(616, 472, 153, 20, "开票人");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 3);
    LODOP.ADD_PRINT_TEXT(489, 425, 71, 19, "纳 税 人  ");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(503, 418, 73, 20, "识 别 号");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(525, 425, 61, 20, "地    址");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(553, 617, 63, 20, "电话");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(465, 617, 58, 20, "电话");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(431, 514, 63, 20, "纳 税 人");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(444, 512, 66, 20, "识 别 号");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(201, 437, 143, 20, "单位代码/身份证号码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(230, 574, 52, 20, "电话");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(259, 437, 143, 20, "单位代码/身份证号码");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(288, 574, 52, 20, "电话");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(318, 314, 79, 20, "登记证号");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(348, 314, 79, 20, "厂牌型号");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(318, 533, 100, 20, "车 辆 类 型");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.ADD_PRINT_TEXT(341, 533, 100, 20, "转入地车辆");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(355, 533, 100, 20, "管理所名称");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT(377, 565, 70, 20, "小写");
    LODOP.SET_PRINT_STYLEA(0, "FontColor", "#7D7402");
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", 2);
    LODOP.ADD_PRINT_TEXT("10.48mm", 666, 135, 20, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT("18.0mm", 666, 100, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "PreviewOnly", 1);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    var kprq = fpmx.kprq;
    var year = kprq.substring(0, 4);
    var month = kprq.substring(4, 6) ;
    var day = kprq.substring(6, 8);
    kprq = year + "-" + month + "-" + day ;
    LODOP.ADD_PRINT_TEXT(89, 108, 122, 20, kprq);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.ADD_PRINT_TEXT(122, 127, 237, 20, fpmx.fpdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(145, 127, 235, 20, fpmx.fphm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(168, 127, 238, 20, fpmx.jqbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    if (countStrLength(fpmx.gfmc) <= 30) {
        LODOP.ADD_PRINT_TEXT(200, 190, 238, 40, fpmx.gfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(195, 190, 238, 40, fpmx.gfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    LODOP.ADD_PRINT_TEXT(198, 576, 215, 20, fpmx.gfdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    if (countStrLength(fpmx.gfdz) <= 46) {
        LODOP.ADD_PRINT_TEXT(229, 190, 384, 20, fpmx.gfdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(224, 190, 384, 20, fpmx.gfdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.gfdh) <= 15) {
        LODOP.ADD_PRINT_TEXT(233, 627, 164, 20, fpmx.gfdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(225, 627, 164, 20, fpmx.gfdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.xfmc) <= 30) {
        LODOP.ADD_PRINT_TEXT(258, 190, 240, 20, fpmx.xfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(253, 190, 240, 20, fpmx.xfmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }

    LODOP.ADD_PRINT_TEXT(258, 576, 207, 20, fpmx.xfdm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    if (countStrLength(fpmx.xfdz) <= 46) {
        LODOP.ADD_PRINT_TEXT(290, 190, 384, 20, fpmx.xfdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(285, 190, 384, 20, fpmx.xfdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.xfdh) <= 15) {
        LODOP.ADD_PRINT_TEXT(289, 627, 164, 20, fpmx.xfdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(281, 627, 164, 20, fpmx.xfdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.cpzh) <= 10) {
        LODOP.ADD_PRINT_TEXT(318, 186, 125, 20, fpmx.cpzh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(312, 192, 125, 20, fpmx.cpzh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.djzh) <= 14) {
        LODOP.ADD_PRINT_TEXT(318, 392, 145, 20, fpmx.djzh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(315, 390, 154, 20, fpmx.djzh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.cllx) <= 16) {
        LODOP.ADD_PRINT_TEXT(318, 627, 160, 20, fpmx.cllx);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(313, 630, 160, 20, fpmx.cllx);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.clsbh) <= 13) {
        LODOP.ADD_PRINT_TEXT(348, 183, 131, 20, fpmx.clsbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(341, 188, 131, 20, fpmx.clsbh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.cpxh) <= 16) {
        LODOP.ADD_PRINT_TEXT(348, 393, 137, 20, fpmx.cpxh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else if(fpmx.cpxh <=44){
        LODOP.ADD_PRINT_TEXT(344, 393, 137, 20, fpmx.cpxh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }else {
        LODOP.ADD_PRINT_TEXT(340, 393, 150, 20, fpmx.cpxh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.zrdclglsmc) <= 20) {
        LODOP.ADD_PRINT_TEXT(347, 626, 175, 20, fpmx.zrdclglsmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else if (countStrLength(fpmx.zrdclglsmc) <= 58) {
        LODOP.ADD_PRINT_TEXT(343, 626, 175, 20, fpmx.zrdclglsmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    } else {
        LODOP.ADD_PRINT_TEXT(340, 625, 175, 20, fpmx.zrdclglsmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    var cjhjdx = je2Upper(fpmx.cjhj);
    LODOP.ADD_PRINT_TEXT(378, 186, 378, 20, (fpmx.fpzt == "01" ? "负数：" + cjhjdx : cjhjdx));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.ADD_PRINT_TEXT(376, 612, 185, 20, "￥" + (fpmx.cjhj * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    if (countStrLength(fpmx.jypmdwmc) <= 64) {
        LODOP.ADD_PRINT_TEXT(407, 183, 600, 20, fpmx.jypmdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(405, 190, 600, 20, fpmx.jypmdwmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.jypmdwdz) <= 26) {
        LODOP.ADD_PRINT_TEXT(436, 188, 326, 20, fpmx.jypmdwdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(436, 190, 326, 20, fpmx.jypmdwdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    LODOP.ADD_PRINT_TEXT(436, 576, 210, 20, fpmx.jypmdwsbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    if (countStrLength(fpmx.jypmdwyhzh) <= 58) {
        LODOP.ADD_PRINT_TEXT(466, 188, 435, 20, fpmx.jypmdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(464, 188, 435, 20, fpmx.jypmdwyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.jypmdwdh) <= 12) {
        LODOP.ADD_PRINT_TEXT(466, 671, 130, 20, fpmx.jypmdwdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(460, 671, 130, 20, fpmx.jypmdwdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.escscmc) <= 28) {
        LODOP.ADD_PRINT_TEXT(493, 180, 243, 56, fpmx.escscmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(493, 188, 237, 56, fpmx.escscmc);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    LODOP.ADD_PRINT_TEXT(496, 497, 291, 20, fpmx.escscsbh);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    if (countStrLength(fpmx.escscyhzh) <= 60) {
        LODOP.ADD_PRINT_TEXT(553, 183, 441, 20, fpmx.escscyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(549, 187, 441, 20, fpmx.escscyhzh);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.escscdh) <= 12) {
        LODOP.ADD_PRINT_TEXT(550, 671, 128, 20, fpmx.escscdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(547, 671, 128, 20, fpmx.escscdh);
        LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    if (countStrLength(fpmx.bz) <= 120) {
        LODOP.ADD_PRINT_TEXT(583, 122, 677, 40, fpmx.bz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    } else {
        LODOP.ADD_PRINT_TEXT(578, 115, 680, 40, fpmx.bz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    LODOP.ADD_PRINT_TEXT(616, 585, 114, 20, fpmx.kpr);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
    LODOP.ADD_PRINT_TEXT(140, 429, 361, 43, fpmx.skm);
    LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    if (countStrLength(fpmx.escscdz) <= 38) {
        LODOP.ADD_PRINT_TEXT(521, 489, 310, 20, fpmx.escscdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    } else {
        LODOP.ADD_PRINT_TEXT(519, 492, 310, 20, fpmx.escscdz);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
        LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    }
    LODOP.ADD_PRINT_IMAGE(15, 40, 66, 66, "data:image/png;base64," + fpmx.ewm);
    LODOP.SET_PRINT_STYLEA(0, "Stretch", 1);
}

function PrintLine06jsfp8hz(i, spmc, dj, sl, hsje) {
    LODOP.ADD_PRINT_TEXT(302 + i * 11, 16, 80, 130, spmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    LODOP.ADD_PRINT_TEXT(302 + i * 11, 76, 71, 15, (dj == 0 ? "" : dj));
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(302 + i * 11, 137, 58, 15, (sl == 0 ? "" : sl));
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(302 + i * 11, 186, 75, 15, (hsje * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
}
function PrintLine06jsfp7hz(i, spmc, dj, sl, hsje) {
    LODOP.ADD_PRINT_TEXT(302 + i * 9, 16, 80, 100, spmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    LODOP.ADD_PRINT_TEXT(302 + i * 9, 76, 71, 15, (dj == 0 ? "" : dj));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(302 + i * 9, 137, 58, 15, (sl == 0 ? "" : sl));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(302 + i * 9, 186, 75, 15, (hsje * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
}
function PrintLine07jsfp8hz(i, spmc, dj, sl, hsje) {
    LODOP.ADD_PRINT_TEXT(361 + i * 11, 18, 59, 130, spmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    LODOP.ADD_PRINT_TEXT(361 + i * 11, 58, 58, 12, (dj == 0 ? "" : dj));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(361 + i * 11, 105, 51, 12, (sl == 0 ? "" : sl));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(361 + i * 11, 147, 63, 12, (hsje * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
}
function PrintLine07jsfp7hz(i, spmc, dj, sl, hsje) {
    LODOP.ADD_PRINT_TEXT(361 + i * 9, 18, 59, 100, spmc);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
    LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    LODOP.ADD_PRINT_TEXT(361 + i * 9, 58, 58, 12, (dj == 0 ? "" : dj));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(361 + i * 9, 105, 51, 12, (sl == 0 ? "" : sl));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
    LODOP.ADD_PRINT_TEXT(361 + i * 9, 147, 63, 12, (hsje * 1).toFixed(2));
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
}
function addPrintLinejsfp(i, bs, spmc) {
    if (bs == "07") {
        var spmclength = countStrLength(spmc);
        if (spmclength <= 16 && spmclength > 0) {
            LODOP.ADD_PRINT_TEXT(361 + i * 24, 18, 59, 30, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
        } else if (spmclength <= 30 && spmclength > 16) {
            LODOP.ADD_PRINT_TEXT(361 + i * 24, 18, 59, 30, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        } else if (spmclength <= 40 && spmclength > 30) {
            LODOP.ADD_PRINT_TEXT(361 + i * 24, 18, 59, 30, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    }
    if (bs == "06") {
        var spmclength = countStrLength(spmc);
        if (spmclength <= 12 && spmclength > 0) {
            LODOP.ADD_PRINT_TEXT(302 + i * 18, 16, 80, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        } else if (spmclength <= 24 && spmclength > 12) {
            LODOP.ADD_PRINT_TEXT(302 + i * 18, 16, 80, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -6);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        } else if (spmclength <= 32 && spmclength > 24) {
            LODOP.ADD_PRINT_TEXT(302 + i * 18, 16, 80, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        } else if (spmclength <= 40 && spmclength > 32) {
            LODOP.ADD_PRINT_TEXT(302 + i * 18, 16, 80, 20, spmc);
            LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
            LODOP.SET_PRINT_STYLEA(0, "LetterSpacing", -1);
        }
        LODOP.SET_PRINT_STYLEA(0, "TextNeatRow", true);
    }
}
function addPrintLineJsfpBZ(bs, bz) {
    if (bs == "07") {
        var bzlength = countStrLength(bz);
        if (bzlength > 0 && bzlength <= 68) {
            LODOP.ADD_PRINT_TEXT(503, 20, 201, 44, bz);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else {
            LODOP.ADD_PRINT_TEXT(503, 20, 201, 44, bz);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
    } else if (bs == "06") {
        var bzlength = countStrLength(bz);
        if (bzlength > 0 && bzlength <= 42) {
            LODOP.ADD_PRINT_TEXT(516, 16, 247, 20, bz);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        } else if (bzlength > 42 && bzlength <= 104) {
            LODOP.ADD_PRINT_TEXT(516, 16, 247, 20, bz);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -5);
        } else {
            LODOP.ADD_PRINT_TEXT(516, 16, 247, 20, bz);
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 6);
            LODOP.SET_PRINT_STYLEA(0, "LineSpacing", -4);
        }
    }
}
/**
 * 打印税控码
 * @param skm 税控码
 * @param top 左上角第一个字的上边距
 * @param left 左上角第一个字的左边距
 * */
function printJdcfpSkmOneByOne(skm, top, left) {
    //每隔2个字左移1像素
    var step = 2;
    for (var r = 0; r < 5; r++) {
        var row = skm.substring(r * 38, r * 38 + 38);
        var reduce = 0;
        for (var i = 0; i < 38; i++) {
            if (i != 0 && i % step == 0 && /*补偿3像素*/i % 8 != 0) {
                reduce++;
            }
            LODOP.ADD_PRINT_TEXT(top + r * 18, left + i * 10 - reduce, 15, 15, row.charAt(i));
            LODOP.SET_PRINT_STYLEA(0, "FontName", "Courier New");
            LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
        }
    }

}
var printTest = function () {
    var defaultZbj = 0, defaultYbj = 0;
    var fpmx = {
        fpdm: "1110000566",
        fphm: "90000017",
        kprq: "20160321145753",
        jqbh: "499099993684"
        ,
        skm: "03533*766-983-89<91*90>7*4-4/83-8-479876801*61342-139734+<5<30697071<7-+>73-3+02<0**2408503369016>07038-<3+30*7/"
        ,
        ghdwmc: "CCTV(北京)科技发展有限公司",
        ghdwdm: "110108777655831",
        ghdwdzdh: "北京市朝阳区建国路朝外大街甲6号万世恒通金融财富中心C座180层18702房间 (010)51263368"
        ,
        ghdwyhzh: "招行北京万通中心支行 110903871810601",
        xhdwmc: "打印测试",
        xhdwdm: "500123457845168",
        xhdwdzdh: "北京市韩家川55555555"
        ,
        xhdwyhzh: "北京农业银行88888888",
        swjgmc: "重庆市涪陵区国家税务局",
        swjgdm: "150010200",
        zyspmc: "基础联通服务费",
        hjje: -999999999.99
        ,
        se: -999999999.99,
        jshj: -999999999.99,
        bz: "票样票样票样票样",
        skr: "彦萍",
        fhr: "彦萍",
        kpr: "zpd",
        tzdh: "",
        kpjh: "0",
        fpzt: "00",
        qdbz: "0",
        jym: "1234567890123456789012"
        ,
        ewm: "iVBORw0KGgoAAAANSUhEUgAAAH4AAAB+CAIAAABtQTLfAAAByklEQVR42u3ZQQ6DMAwEwP7/0/QJFYq9pnh8bhMyILHYn0+qroO6tdTJvkmND3r06NGjX0d/1VXM6xZf7DJ+rowePXr06NfTn2SYwpVP9p26ZvTo0aNHj/4x9LEodbIUevTo0aNH/wr6qZuKHj169OjRB+mn0kJMZOOoBD169OjRj9H31V/kn6QGevTo0aNfQH8NVWH+iaWUYgH06NGjR/9++tgbv9DrJEo95Lzo0aNHj34H/dSpjsJAanhdeKLfjQT06NGjR/9C+tq3dmZaXfjfPtziUQl69OjRo38hfUykL+HUxpLGfj169OjRo19HH/soj9E/5OlBjx49evQr6fv4+ghiQaK3kYAePXr06NfRFx5jav5e2HWoHcejR48ePfr19H3WJykl9uPW5gd69OjRo19PPzXLjvX6p9oM6NGjR49+B/01VK3hIbPvacJBjx49evTvp39IAOg75DPb9+jRo0ePfiV9LMPECP4m4aBHjx49+nX0sbTQd1OnIhx69OjRo0f/n/SxsXjfvujRo0ePHv0cfSFQ7dd/YVsFPXr06NGvp+/r18ca5Q/JP+jRo0ePHv1cOzt2F2P0txsJ6NGjR4/+hfSXGqovJG80vUMxIdkAAAAASUVORK5CYII="
        ,
        mxzb: [{
            fphxz: 0,
            je: 99999999999.99,
            sl: 0.06,
            se: -999999999.99,
            spmc: "*谷物*78901234567890123456789012345678901234567890123456789012345678901234567890",
            ggxh: "H42H-2.5mpa 40mm",
            dw: "次",
            spsl: 123456.789012,
            spdj: 123456789.123456
        }
            , {
                fphxz: 0,
                je: 94.36,
                sl: 0.06,
                se: -999999999.99,
                spmc: "*谷物*78901234567890123456789012345678901234567890123456789012345678901234567890",
                ggxh: "H42H-2.5mpa 40mm",
                dw: "",
                spsl: 1.0,
                spdj: 123456.123456
            }]
    };
    var fpmxjs = {
        fpdm: "1110000566",
        fphm: "90000017",
        kprq: "20160321145753",
        jqbh: "499099993684"
        ,
        skm: "1234567890123456789012"
        ,
        ghdwmc: "CCTV(北京)科技发展有限公司",
        ghdwdm: "11010877765583155555",
        ghdwdzdh: "北京市朝阳区建国路朝外大街甲6号万世恒通金融财富中心C座180层18702房间 (010)51263368"
        ,
        ghdwyhzh: "招行北京万通中心支行 110903871810601",
        xhdwmc: "打印测试",
        xhdwdm: "50012345784516812345",
        xhdwdzdh: "北京市韩家川55555555"
        ,
        xhdwyhzh: "北京农业银行88888888",
        swjgmc: "重庆市涪陵区国家税务局",
        swjgdm: "150010200",
        zyspmc: "基础联通服务费",
        hjje: 141.53
        ,
        se: 8.47,
        jshj: 9999999.99,
        // bz: "票样票样票样票样",
        skr: "彦萍",
        fhr: "彦萍",
        kpr: "zpd",
        tzdh: "",
        kpjh: "0",
        fpzt: "00",
        qdbz: "0",
        jym: "1234567890123456789012"
        ,
        ewm: "iVBORw0KGgoAAAANSUhEUgAAAH4AAAB+CAIAAABtQTLfAAAByklEQVR42u3ZQQ6DMAwEwP7/0/QJFYq9pnh8bhMyILHYn0+qroO6tdTJvkmND3r06NGjX0d/1VXM6xZf7DJ+rowePXr06NfTn2SYwpVP9p26ZvTo0aNHj/4x9LEodbIUevTo0aNH/wr6qZuKHj169OjRB+mn0kJMZOOoBD169OjRj9H31V/kn6QGevTo0aNfQH8NVWH+iaWUYgH06NGjR/9++tgbv9DrJEo95Lzo0aNHj34H/dSpjsJAanhdeKLfjQT06NGjR/9C+tq3dmZaXfjfPtziUQl69OjRo38hfUykL+HUxpLGfj169OjRo19HH/soj9E/5OlBjx49evQr6fv4+ghiQaK3kYAePXr06NfRFx5jav5e2HWoHcejR48ePfr19H3WJykl9uPW5gd69OjRo19PPzXLjvX6p9oM6NGjR49+B/01VK3hIbPvacJBjx49evTvp39IAOg75DPb9+jRo0ePfiV9LMPECP4m4aBHjx49+nX0sbTQd1OnIhx69OjRo0f/n/SxsXjfvujRo0ePHv0cfSFQ7dd/YVsFPXr06NGvp+/r18ca5Q/JP+jRo0ePHv1cOzt2F2P0txsJ6NGjR4/+hfSXGqovJG80vUMxIdkAAAAASUVORK5CYII="
        ,
        mxzb: [{
            fphxz: 0,
            hsje: -9999999.99,
            sl: 0.06,
            se: 5.64,
            spmc: "基础服务费服务",
            ggxh: "",
            dw: "",
            spsl: -9999.99,
            hsdj: 9999999.99
        }
            , {
                fphxz: 0,
                hsje: -9999999.99,
                sl: 0.06,
                se: 5.64,
                spmc: "基础服务费服务",
                ggxh: "",
                dw: "",
                spsl: -9999.99,
                hsdj: 9999999.99
            },
            {
                fphxz: 0,
                hsje: -9999999.99,
                sl: 0.06,
                se: 5.64,
                spmc: "基础服务费服务",
                ggxh: "",
                dw: "",
                spsl: -9999.99,
                hsdj: 9999999.99
            }, {
                fphxz: 0,
                hsje: -9999999.99,
                sl: 0.06,
                se: 5.64,
                spmc: "基础服务费服务",
                ggxh: "",
                dw: "",
                spsl: -9999.99,
                hsdj: 9999999.99
            }
        ]
    };
    var jdcfpmx = {
        fpdm: "1110000566",
        fphm: "90000017",
        kprq: "20160321145753",
        jqbh: "499099993684"
        ,
        skm: "03533*766-983-89<91*90>7*4-4/83-8-479876801*61342-139734+<5<30697071<7-+>73-3+02<0**2408503369016>07038-<3+30*7/03533*766-983-89<91*90>7*4-4/83-8-47903533*766-983-89<91*90>7*4-4/83-8-47903533*766-983-89<91*90>7*4-4/83-8-479"
        ,
        gmfmc: "CCTV(北京)科技发展有限公司",
        ghfsbh: "110108777655831",
        cllx: "轿车",
        cpxh: "宝马A8",
        cd: "北京",
        hgzh: "123456",
        jkzmsh: "654321",
        sjdh: "7890",
        fdjhm: "0987",
        clsbdh: "23456"
        ,
        jshj: "1000000000.00",
        jshj_xx: "90000000",
        xhdwmc: "打印测试",
        dh: "123456789",
        nsrsbh: "50012345784516812345",
        zh: "88888888",
        dz: "北京市韩家川55555555",
        khyh: "北京农业银行",
        zzssl: "0.05"
        ,
        ewm: "iVBORw0KGgoAAAANSUhEUgAAAH4AAAB+CAIAAABtQTLfAAAByklEQVR42u3ZQQ6DMAwEwP7/0/QJFYq9pnh8bhMyILHYn0+qroO6tdTJvkmND3r06NGjX0d/1VXM6xZf7DJ+rowePXr06NfTn2SYwpVP9p26ZvTo0aNHj/4x9LEodbIUevTo0aNH/wr6qZuKHj169OjRB+mn0kJMZOOoBD169OjRj9H31V/kn6QGevTo0aNfQH8NVWH+iaWUYgH06NGjR/9++tgbv9DrJEo95Lzo0aNHj34H/dSpjsJAanhdeKLfjQT06NGjR/9C+tq3dmZaXfjfPtziUQl69OjRo38hfUykL+HUxpLGfj169OjRo19HH/soj9E/5OlBjx49evQr6fv4+ghiQaK3kYAePXr06NfRFx5jav5e2HWoHcejR48ePfr19H3WJykl9uPW5gd69OjRo19PPzXLjvX6p9oM6NGjR49+B/01VK3hIbPvacJBjx49evTvp39IAOg75DPb9+jRo0ePfiV9LMPECP4m4aBHjx49+nX0sbTQd1OnIhx69OjRo0f/n/SxsXjfvujRo0ePHv0cfSFQ7dd/YVsFPXr06NGvp+/r18ca5Q/JP+jRo0ePHv1cOzt2F2P0txsJ6NGjR4/+hfSXGqovJG80vUMxIdkAAAAASUVORK5CYII="
        ,
        zzsse: "111111",
        zgswjg: "重庆市涪陵区国家税务局",
        swjgdm: "150010200",
        bhsj: "1000000000.00",
        dw: "2",
        xcrs: "4",
        fhr: "彦萍",
        wspzhm: "333333333",
        kpr: "zpd",
        sfzhm: "1101111111112222222"
    };
    var escfpmx = {
        fpdm: "1110000566",
        fphm: "90000017",
        kprq: "20160321145753",
        jqbh: "499099993684"
        ,
        skm: "03533*766-983-89<91*"
        ,
        gfmc: "CCTV(北京)科技发展有限公司",
        gfdm: "110108777655831",
        gfdz: "北京市朝阳区建国路朝外大街甲6号万世恒通金融财富中心C座180层18702房间 (010)51263368",
        gfdh: "123456789012",
        xfmc: "打印测试",
        xfdm: "50012345784516812345",
        xfdz: "北京市韩家川55555555",
        xfdh: "098765432109",
        cpzh: "京A10086",
        djzh: "E1234567890",
        cllx: "轿车",
        clsbh: "1234567890SBH",
        cpxh: "奔驰CRV",
        zrdclglsmc: "北京车辆管理所",
        cjhj: 1000000000.00,
        jypmdwmc: "北京海淀经营单位",
        jypmdwsbh: "BJ1234567890123",
        jypmdwdz: "北京市海淀区98号",
        jypmdwdh: "123456789012",
        jypmdwyhzh: "北京农业银行88888888",
        escscmc: "瓜子二手车",
        escscsbh: "GZ1234567890",
        escscdz: "北京瓜子二手车",
        escscdh: "100010",
        escscyhzh: "北京工商银行9999999999",
        bz: "票样票样票样票样",
        kpr: "zpd",
        ewm: "iVBORw0KGgoAAAANSUhEUgAAAH4AAAB+CAIAAABtQTLfAAAByklEQVR42u3ZQQ6DMAwEwP7/0/QJFYq9pnh8bhMyILHYn0+qroO6tdTJvkmND3r06NGjX0d/1VXM6xZf7DJ+rowePXr06NfTn2SYwpVP9p26ZvTo0aNHj/4x9LEodbIUevTo0aNH/wr6qZuKHj169OjRB+mn0kJMZOOoBD169OjRj9H31V/kn6QGevTo0aNfQH8NVWH+iaWUYgH06NGjR/9++tgbv9DrJEo95Lzo0aNHj34H/dSpjsJAanhdeKLfjQT06NGjR/9C+tq3dmZaXfjfPtziUQl69OjRo38hfUykL+HUxpLGfj169OjRo19HH/soj9E/5OlBjx49evQr6fv4+ghiQaK3kYAePXr06NfRFx5jav5e2HWoHcejR48ePfr19H3WJykl9uPW5gd69OjRo19PPzXLjvX6p9oM6NGjR49+B/01VK3hIbPvacJBjx49evTvp39IAOg75DPb9+jRo0ePfiV9LMPECP4m4aBHjx49+nX0sbTQd1OnIhx69OjRo0f/n/SxsXjfvujRo0ePHv0cfSFQ7dd/YVsFPXr06NGvp+/r18ca5Q/JP+jRo0ePHv1cOzt2F2P0txsJ6NGjR4/+hfSXGqovJG80vUMxIdkAAAAASUVORK5CYII="
    };
    return {
        printZzsfp: function (fplxdm) {
            if (!checkPrint()) {
                return false;
            }
            if (!this.saveCookie(fplxdm, false)) {
                return false;
            }
            if ("004" == fplxdm) {
                LODOP.PRINT_INITA(_$("#zbj_zzsz").val() + "mm", $("#ybj_zzsz").val() + "mm", "230mm", "159mm", "增值税专用发票");
            } else if ("007" == fplxdm) {
                LODOP.PRINT_INITA(_$("#zbj_zzsp").val() + "mm", $("#ybj_zzsp").val() + "mm", "230mm", "159mm", "增值税普通发票");
            }
            LODOP.SET_PRINT_PAGESIZE(1, 2300, 1590, "CreateCustomPage");
            LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 890, 700, "");

            var fpmxPrint = $.extend({fplxdm: "004"}, fpmx, {
                jshjdx: je2Upper(fpmx.jshj),
                fplxdm: fplxdm
            });
            if ("004" == fplxdm) {
                printZzszp(fpmxPrint);
            } else if ("007" == fplxdm) {
                printZzspp(fpmxPrint);
            }
            LODOP.PREVIEW();
            // LODOP.PRINT_DESIGN();
        },
        printZzsfpQd: function (fplxdm) {
            if (!checkPrint()) {
                return false;
            }
            if (!this.saveCookieQd(fplxdm, false)) {
                return false;
            }
            if ("004" == fplxdm) {
                LODOP.PRINT_INITA(_$("#zbj_qd_zzsz").val() + "mm", $("#ybj_qd_zzsz").val() + "mm", 775, 959, "增值税专用发票（清单）");
            } else if ("007" == fplxdm) {
                LODOP.PRINT_INITA(_$("#zbj_qd_zzsp").val() + "mm", $("#ybj_qd_zzsp").val() + "mm", 775, 959, "增值税普通发票（清单）");
            }
            LODOP.SET_PREVIEW_WINDOW(1, 0, 0, 850, 750, "");

            var fpmxPrint = $.extend({fplxdm: "004"}, fpmx, {
                jshjdx: je2Upper(fpmx.jshj),
                fplxdm: fplxdm
            });
            if ("004" == fplxdm) {
                printZzszpQD(fpmxPrint);
            } else if ("007" == fplxdm) {
                printZzsppQD(fpmxPrint);
            }
            LODOP.PREVIEW();
            //LODOP.PRINT_DESIGN();
        },
        saveCookie: function (fplxdm, alert) {
            if ("004" == fplxdm) {
                if (!_$("form").eq(2).valid()) {
                    return false;
                }
                $.cookie("zbj_zzsz", $("#zbj_zzsz").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_zzsz", $("#ybj_zzsz").val() || 0, {path: "/", expires: 100000});
            } else if ("007" == fplxdm) {
                if (!_$("form").eq(1).valid()) {
                    return false;
                }
                $.cookie("zbj_zzsp", $("#zbj_zzsp").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_zzsp", $("#ybj_zzsp").val() || 0, {path: "/", expires: 100000});
            } else if ("025" == fplxdm) {
                if (!_$("form").eq(5).valid()) {
                    return false;
                }
                $.cookie("zbj_jsfp", $("#zbj_jsfp").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_jsfp", $("#ybj_jsfp").val() || 0, {path: "/", expires: 100000});
                $.cookie("jsfpbs", $("#jsfpbs").val() || "06", {path: "/", expires: 100000});
            } else if ("005" == fplxdm) {
                if (!_$("form").eq(6).valid()) {
                    return false;
                }
                $.cookie("zbj_jdcfp", $("#zbj_jdcfp").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_jdcfp", $("#ybj_jdcfp").val() || 0, {path: "/", expires: 100000});
            } else if ("006" == fplxdm) {
                if (!_$("form").eq(7).valid()) {
                    return false;
                }
                $.cookie("zbj_escfp", $("#zbj_escfp").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_escfp", $("#ybj_escfp").val() || 0, {path: "/", expires: 100000});
            }
            if (alert) {
                alertMsg.correct("保存成功");
            }

            var cookies = ($.cookie("zbj_zzsz") == null ? "0" : $.cookie("zbj_zzsz")) + "," + ($.cookie("ybj_zzsz") == null ? "0" : $.cookie("ybj_zzsz")) + "," + ($.cookie("zbj_zzsp") == null ? "0" : $.cookie("zbj_zzsp")) + "," +
                ($.cookie("ybj_zzsp") == null ? "0" : $.cookie("ybj_zzsp")) + "," + ($.cookie("zbj_qd_zzsz") == null ? "0" : $.cookie("zbj_qd_zzsz")) + "," + ($.cookie("ybj_qd_zzsz") == null ? "0" : $.cookie("ybj_qd_zzsz")) + "," +
                ($.cookie("zbj_qd_zzsp") == null ? "0" : $.cookie("zbj_qd_zzsp")) + "," + ($.cookie("ybj_qd_zzsp") == null ? "0" : $.cookie("ybj_qd_zzsp")) + "," + ($.cookie("zbj_jsfp") == null ? "0" : $.cookie("zbj_jsfp")) + "," + ($.cookie("ybj_jsfp") == null ? "0" : $.cookie("ybj_jsfp"))
                + "," + ($.cookie("jsfpbs") == null ? "06" : $.cookie("jsfpbs")) + "," + ($.cookie("zbj_jdcfp") == null ? "0" : $.cookie("zbj_jdcfp")) + "," + ($.cookie("ybj_jdcfp") == null ? "0" : $.cookie("ybj_jdcfp")) + "," +
                ($.cookie("zbj_escfp") == null ? "0" : $.cookie("zbj_escfp")) + "," + ($.cookie("ybj_escfp") == null ? "0" : $.cookie("ybj_escfp"));

            this.save2Sql(cookies);
            return true;
        },
        saveCookieQd: function (fplxdm, alert) {
            if ("004" == fplxdm) {
                if (!_$("form").eq(4).valid()) {
                    return false;
                }
                $.cookie("zbj_qd_zzsz", $("#zbj_qd_zzsz").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_qd_zzsz", $("#ybj_qd_zzsz").val() || 0, {path: "/", expires: 100000});
            } else if ("007" == fplxdm) {
                if (!_$("form").eq(3).valid()) {
                    return false;
                }
                $.cookie("zbj_qd_zzsp", $("#zbj_qd_zzsp").val() || 0, {path: "/", expires: 100000});
                $.cookie("ybj_qd_zzsp", $("#ybj_qd_zzsp").val() || 0, {path: "/", expires: 100000});
            }
            if (alert) {
                alertMsg.correct("保存成功");
            }
            var cookies = ($.cookie("zbj_zzsz") == null ? "0" : $.cookie("zbj_zzsz")) + "," + ($.cookie("ybj_zzsz") == null ? "0" : $.cookie("ybj_zzsz")) + "," + ($.cookie("zbj_zzsp") == null ? "0" : $.cookie("zbj_zzsp")) + "," +
                ($.cookie("ybj_zzsp") == null ? "0" : $.cookie("ybj_zzsp")) + "," + ($.cookie("zbj_qd_zzsz") == null ? "0" : $.cookie("zbj_qd_zzsz")) + "," + ($.cookie("ybj_qd_zzsz") == null ? "0" : $.cookie("ybj_qd_zzsz")) + "," +
                ($.cookie("zbj_qd_zzsp") == null ? "0" : $.cookie("zbj_qd_zzsp")) + "," + ($.cookie("ybj_qd_zzsp") == null ? "0" : $.cookie("ybj_qd_zzsp")) + "," + ($.cookie("zbj_jsfp") == null ? "0" : $.cookie("zbj_jsfp")) + "," + ($.cookie("ybj_jsfp") == null ? "0" : $.cookie("ybj_jsfp"))
                + "," + ($.cookie("jsfpbs") == null ? "06" : $.cookie("jsfpbs")) + "," + ($.cookie("zbj_jdcfp") == null ? "0" : $.cookie("zbj_jdcfp")) + "," + ($.cookie("ybj_jdcfp") == null ? "0" : $.cookie("ybj_jdcfp")) + "," +
                ($.cookie("zbj_escfp") == null ? "0" : $.cookie("zbj_escfp")) + "," + ($.cookie("ybj_escfp") == null ? "0" : $.cookie("ybj_escfp"));

            this.save2Sql(cookies);
            return true;
        },
        initPage: function () {
            var inputs = _$("p input:text");
            inputs.each(function () {
                $(this).val($.cookie($(this).attr("id")) || 0);
            });

            $("#jsfpbs").val($.cookie("jsfpbs"));

            if ($.cookie("dyfs") == "1") {
                _$("#dyfs").attr("checked", "checked");
            }
        },
        dyfs: function (c) {
            var _c = $(c);
            if (_c.attr("checked")) {
                $.cookie("dyfs", "1", {path: "/", expires: 100000});
            } else {
                $.cookie("dyfs", "0", {path: "/", expires: 100000});
            }
        },
        save2Sql: function (c) {

            var _c = c;
            //window.alert(_c);
            ajaxLoad(ctxPath + "/sys/cookie.do", {by1: _c}, function (json) {

            });
        },
        printJsfp: function (fplxdm) {
            if (!checkPrint()) {
                return false;
            }
            if (!this.saveCookie(fplxdm, false)) {
                return false;
            }
            printJsfp(fpmxjs, false);
            LODOP.PREVIEW();
            //LODOP.PRINT_DESIGN();//调试模式
        },
        printJdcfp: function (fplxdm) {
            if (!checkPrint()) {
                return false;
            }
            if (!this.saveCookie(fplxdm, false)) {
                return false;
            }
            printJdcfp(jdcfpmx, false);
            LODOP.PREVIEW();
            //LODOP.PRINT_DESIGN();//调试模式
        },
        printEscfp: function (fplxdm) {
            if (!checkPrint()) {
                return false;
            }
            if (!this.saveCookie(fplxdm, false)) {
                return false;
            }
            printEscfp(escfpmx, false);
            LODOP.PREVIEW();
            //LODOP.PRINT_DESIGN();//调试模式
        }
    }
}();
function printzdewm() {
    if (!checkPrint()) {
        return false;
    }
    LODOP.PRINT_INIT("终端二维码");
    ajaxLoad(ctxPath + "/qytt/getewmbase64.do", function (json) {
        LODOP.ADD_PRINT_IMAGE(20, 0, 230, 230, "data:image/png;base64," + json.ewmbase64);
        LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
        var trs = $_("#ewmtable tr");
        LODOP.ADD_PRINT_TEXT(255, 0, 383, 25, trs.eq(1).text().trim());
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
        LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
        LODOP.ADD_PRINT_TEXT(280, 0, 383, 25, trs.eq(2).text().trim());
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
        LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
        LODOP.ADD_PRINT_TEXT(305, 0, 383, 25, trs.eq(3).text().trim());
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
        LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
        LODOP.PREVIEW();
    });
}
function ocxPrint(fpmx) {
    var fyxm = "", qdxm = "", fyxmC = 0, qdxmC = 0;
    if (!fpmx.dyfs) {
        fpmx.dyfs = "1";
    }
    encodeForXml(fpmx);
    $.each(fpmx.mxzb, function (i, n) {
        encodeForXml(n);
        var mxxml = "<fphxz>" + n.fphxz + "</fphxz>" + "<spmc>" + n.spmc + "</spmc>" + "<spsm></spsm>" +
            "<ggxh>" + n.ggxh + "</ggxh>" + "<dw>" + n.dw + "</dw>" + "<spsl>" + ((n.spsl) == 0 ? "" : n.spsl ) + "</spsl>" + "<dj>" + ((n.spdj) == 0 ? "" : n.spdj) + "</dj>" +
            "<je>" + n.je + "</je>" + "<sl>" + n.sl + "</sl>" + "<se>" + n.se + "</se>" + "<hsbz>" + (n.hsbz ? "1" : "0") + "</hsbz>" + "<spbm>" + n.spbm + "</spbm>" +
            "<zxbm>" + n.zxbm + "</zxbm>" + "<yhzcbs>" + n.yhzcbs + "</yhzcbs>" + "<lslbs>" + n.lslbs + "</lslbs>" +
            "<zzstsgl>" + n.zzstsgl + "</zzstsgl>";
        if (n.fphxz == "3" || n.fphxz == "4") {
            fyxmC++;
            fyxm += ("<group xh=\"" + fyxmC + "\">" + mxxml + "</group>");
        } else if (n.fphxz == "0" || n.fphxz == "1" || n.fphxz == "2") {
            if (fpmx.qdbz == "0") {
                fyxmC++;
                fyxm += ("<group xh=\"" + fyxmC + "\">" + mxxml + "</group>");
            } else {
                qdxmC++;
                qdxm += ("<group xh=\"" + qdxmC + "\">" + mxxml + "</group>");
            }
        }
    });
    var xml = "<?xml version=\"1.0\" encoding=\"gbk\"?>" + "<business id=\"20007\" comment=\"\">" +
        "<body yylxdm=\"1\">" + "<returncode>0</returncode>" + "<returnmsg></returnmsg>" +
        "<returndata>" + "<kpxx count=\"1\">" + "<group xh=\"1\">" +
        "<fpdm>" + fpmx.fpdm + "</fpdm>" + "<fphm>" + fpmx.fphm + "</fphm>" + "<fpzt>" + fpmx.fpzt + "</fpzt>" +
        "<scbz></scbz>" + "<kprq>" + fpmx.kprq + "</kprq>" + "<jqbh>" + fpmx.jqbh + "</jqbh>" +
        "<skm>" + fpmx.skm + "</skm>" + "<jym>" + fpmx.jym + "</jym>" + "<tspz>" + fpmx.tspz + "</tspz>" + "<xhdwsbh>" + fpmx.xhdwdm + "</xhdwsbh>" +
        "<xhdwmc>" + fpmx.xhdwmc + "</xhdwmc>" + "<xhdwdzdh>" + fpmx.xhdwdzdh + "</xhdwdzdh>" + "<xhdwyhzh>" + fpmx.xhdwyhzh + "</xhdwyhzh>" +
        "<ghdwsbh>" + fpmx.ghdwdm + "</ghdwsbh>" + "<ghdwmc>" + fpmx.ghdwmc + "</ghdwmc>" + "<ghdwdzdh>" + fpmx.ghdwdzdh + "</ghdwdzdh>" +
        "<ghdwyhzh>" + fpmx.ghdwyhzh + "</ghdwyhzh>" + "<bmbbbh>" + fpmx.bmbbbh + "</bmbbbh>" + "<zsfs>" + fpmx.zsfs + "</zsfs>" +
        "<fyxm count=\"" + fyxmC + "\">" + fyxm + "</fyxm>" + "<qdxm count=\"" + qdxmC + "\">" + qdxm + "</qdxm><qtxm></qtxm>" +
        "<zhsl></zhsl>" + "<hjje>" + fpmx.hjje + "</hjje>" + "<hjse>" + fpmx.se + "</hjse>" + "<jshj>" + fpmx.jshj + "</jshj>" +
        "<bz>" + fpmx.bz + "</bz>" + "<skr>" + fpmx.skr + "</skr>" + "<fhr>" + fpmx.fhr + "</fhr>" + "<kpr>" + fpmx.kpr + "</kpr>" +
        "<jmbbh></jmbbh>" + "<zyspmc></zyspmc>" + "<spsm></spsm>" +
        "<qdbz>" + fpmx.qdbz + "</qdbz>" + "<ssyf>" + fpmx.ssyf + "</ssyf>" + "<kpjh>" + fpmx.kpjh + "</kpjh>" + "<tzdbh>" + fpmx.tzdh + "</tzdbh>" +
        "<yfpdm>" + fpmx.yfpdm + "</yfpdm>" + "<yfphm>" + fpmx.yfphm + "</yfphm>" + "<zfrq></zfrq>" + "<zfr></zfr>" +
        "<qmcs></qmcs>" + "<qmz></qmz>" + "<ykfsje></ykfsje>" + "<ewm></ewm>" +
        "</group>" + "</kpxx>" + "</returndata>" + "<fplxdm>" + fpmx.fplxdm + "</fplxdm>" + "<dylx>" + fpmx.dylx + "</dylx>" +
        "<dyfs>" + fpmx.dyfs + "</dyfs>" + "</body>" + "</business>";
    disableButtons(_$("#qddyx"));
    var ret = $.PRINT.Operate(xml);
    if (ret.indexOf("<returncode>0</returncode>") != -1) {
        alertMsg.correct("打印完成");
    } else {
        alertMsg.error(ret.substring(ret.indexOf("<returnmsg>") + 11, ret.indexOf("</returnmsg>")));
    }
    enableButtons(_$("#qddyx"))
}
function encodeForXml(fpmx) {
    for (var v in fpmx) {
        var n = fpmx[v];
        if (typeof n == "string") {
            fpmx[v] = n.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
    }
}