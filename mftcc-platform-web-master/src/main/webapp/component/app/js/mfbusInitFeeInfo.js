;
var feeInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		var idIndex = $("#busfee-div").find("thead th[name=id]").index();
		$("#busfee-div").find("thead th[name=id]").hide();
		var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
		$("#busfee-div").find("tbody tr").each(function(index){
			$thisTr = $(this);
			$thisTr.find("td").eq(idIndex).hide();
			var $rateScaleTd = $thisTr.find("td").eq(rateScaleIndex); 
			var rateScale = $rateScaleTd.html().trim();
			$rateScaleTd.html('<input value="'+rateScale+'">');
		});
	}; 
	//申请初始化
	var _applyinit = function (){
		var idIndex = $("#busfee-div").find("thead th[name=id]").index();
		var itemIndex = $("#busfee-div").find("thead th[name=itemNo]").index();
		$("#busfee-div").find("thead th[name=id]").hide();
		$("#busfee-div").find("thead th[name=itemNo]").hide();
		var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
		$("#busfee-div").find("tbody tr").each(function(index){
			$thisTr = $(this);
			$thisTr.find("td").eq(idIndex).hide();
			$thisTr.find("td").eq(itemIndex).hide();
			var $rateScaleTd = $thisTr.find("td").eq(rateScaleIndex); 
			var rateScale = $rateScaleTd.html().trim();
			$rateScaleTd.html('<input value="'+rateScale+'">');
		});
	}; 
	//保存时获取数据源
	var _applydatas = function(){
		var dataArray = [];
		var idIndex = $("#busfee-div").find("thead th[name=id]").index();
		var itemIndex = $("#busfee-div").find("thead th[name=itemNo]").index();
		var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
		$("#busfee-div").find("tbody tr").each(function(index){
			var entity = {};
			$thisTr = $(this);
			entity.id = $thisTr.find("td").eq(idIndex).html().trim();
			entity.itemNo = $thisTr.find("td").eq(itemIndex).html().trim();
			entity.rateScale = $thisTr.find("td").eq(rateScaleIndex).find("input").val().replace(/,/g, "");
			dataArray.push(entity);
		});
		return dataArray;
	};
	
	var _datas = function(){
		var dataArray = [];
		var idIndex = $("#busfee-div").find("thead th[name=id]").index();
		var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
		$("#busfee-div").find("tbody tr").each(function(index){
			var entity = {};
			$thisTr = $(this);
			entity.id = $thisTr.find("td").eq(idIndex).html().trim();
			entity.rateScale = $thisTr.find("td").eq(rateScaleIndex).find("input").val().replace(/,/g, "");
			dataArray.push(entity);
		});
		return dataArray;
	};
	return {
		applyinit : _applyinit,
		applydatas : _applydatas,
		init : _init,
		datas : _datas,
	};
}(window, jQuery);
