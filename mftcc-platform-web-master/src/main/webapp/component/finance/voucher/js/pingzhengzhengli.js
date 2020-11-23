;
var PzzhengliJs = function(window, $) {
	
	var _init = function () {
		loadedpz();
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);


var length = '';
	/**
	获取凭证字信息
	 */
	function loadedpz() {
		$.ajax({
			url : webPath+'/cwVoucherMst/getPzInfoAjax',
			type : 'post',
			data : null,
			async : false,
			dataType : 'json',
			error : function() {
				//alert("error");
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},
			success : function(data) {
				if (data.flag == 'success') {

					var pzzarr = data.pzlistmap;

					for ( var i = 0; i < pzzarr.length; i++) {

						$("#pz_type").append(
								"<option value="+pzzarr[i].pzProofNo+">"
										+ pzzarr[i].pzPrefix + "</option>");
					}
				} else {
					//alert("error");
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			}
		});
	}

	/*
	检查，凭证字号是否按照顺序
	1，查询某一期的
	2，查询某个凭证字的类型
	3，根据凭证号顺次还是按照时间来整理
	 */
	function checkPingzheng() {
		
		//获取某一期的值
		var weeks = $("#weeks").val();
		//获取凭证字类型
		var pzType = $("#pz_type").val();
		//获取是按照凭证字顺序还是按照时间整理的值
		var pzradio = $("input:radio:checked").val();

		var checkJson = {
			"weeks" : weeks,
			"pzProofNo" : pzType,
			"pzradioType" : pzradio
		};
		$.ajax({
			url : webPath+'/cwVoucherMst/checkPzByFieldsAjax',
			type : 'post',
			data : checkJson,
			async : false,
			dataType : 'json',
			error : function() {
				//alert("error");
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},
			success : function(data) {
				if (data.flag == 'success') {
					$(".appendClass").remove();
					var st = data.listmap;
					length = st.length;
					if (length == 0) {
						//alert("没有需要整理的凭证。", 1);
						alert(top.getMessage("NO_CW_VOUCHER_UP"," "), 0);
						return;
					}
					$("#zhenglitableDiv").show();//显示div
					$("#beginzhengliDiv").show();//显示按钮
					for ( var i = 0; i < st.length; i++) {
						$("#pzInfotable").append(
								"<tr class=\"appendClass\" ><td width=\"30%\" align=\"center\" >"
										+ st[i].pz_prefix + "-"
										+ st[i].voucher_note_no
										+ "</td><td  align=\"center\">"
										+ st[i].pz_prefix + "-"
										+ st[i].pz_new_no + "</td></tr>");
					}
					$(".span4").addClass("colorSty");//字体的样式改变
					$(".span3").removeClass("colorSty");//字体的样式改变
				} else {
					//alert("error");
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			}
		});

	}

	function beginZhengli() {
		
		if (length == 0) {
			//alert("没有需要整理的凭证。", 1);
			alert(top.getMessage("NO_CW_VOUCHER_UP"," "), 0);
			return;
		}
		//获取某一期的值
		var weeks = $("#weeks").val();
		//获取凭证字类型
		var pzType = $("#pz_type").val();
		//获取是按照凭证字顺序还是按照时间整理的值
		var pzradio = $("input:radio:checked").val();

		var checkJson = {
			"weeks" : weeks,
			"pzProofNo" : pzType,
			"pzradioType" : pzradio
		};
		$.ajax({
			url : webPath+'/cwVoucherMst/dealpingzhengliAjax',
			type : 'post',
			data : checkJson,
			async : false,
			dataType : 'json',
			error : function() {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
				//alert("error");
			},
			success : function(data) {
				//alert("恭喜你，凭证整理完成!", 1);
				alert(top.getMessage("COMPLETED_CW_VOUCHER_UP"),3);
				$("#maindivstyle").hide();
				$("#beginzhengliDiv").hide();
				$("#zhenglitableDiv").hide();
				$("#returnListdiv").show();
				$(".span5").addClass("colorSty");
				$(".span4").removeClass("colorSty");
			}
		});

	}