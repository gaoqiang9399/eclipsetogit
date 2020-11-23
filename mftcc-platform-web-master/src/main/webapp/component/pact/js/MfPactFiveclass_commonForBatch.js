;
var MfPactFiveclass_commonForBatch = function(window,$){
	var _changeSelectColor = function (trObj) {
		var $_tr = $(trObj);
		var lastFiveclass = $_tr.children(":eq(3)").text();
		
		// 有select取option的text；否则直接取td的值。兼容认定和查看页面。
		var $_select = $_tr.find("select[name=nowFiveclass]");
		var nowFiveclass, $_colorObj;
		if ($_select.length > 0) {
			nowFiveclass = $_select.find("option:selected").text() ;
			$_colorObj = $_select;
		} else {
			nowFiveclass = $_tr.children(":eq(5)").text();
			$_colorObj = $_tr.children(":eq(5)");
		}
		
		if(nowFiveclass != lastFiveclass){
			$_colorObj.addClass('redNowFiveclass');
		} else {
			$_colorObj.removeClass('redNowFiveclass');
		}
	};
	var _changeTrColor = function (){
        $("#tab").find("tr").each(function(){
        	_changeSelectColor(this);
        	
            var tdArr = $(this).children();
            var lastFiveclass = tdArr.eq(3).text();
            if(lastFiveclass != '正常'){
                $(this).addClass("notNormFiveclassTr");
            }
            
            var fiveclassSts = $(this).find("input[name=fiveclassSts]").val();
            if(fiveclassSts=='1'||fiveclassSts=='2'||fiveclassSts=='3'||fiveclassSts=='6'||fiveclassSts=='7'){
                $(this).find("select[name=nowFiveclass]").attr("disabled","disabled");
                $(this).find("input[name=nowChangeReason]").attr("disabled","disabled");
                $(this).find("input[name=changeReason]").attr("disabled","disabled");
            }
        });
	}
	return {
		changeTrColor : _changeTrColor,
		changeSelectColor : _changeSelectColor
	};
}(window,jQuery);