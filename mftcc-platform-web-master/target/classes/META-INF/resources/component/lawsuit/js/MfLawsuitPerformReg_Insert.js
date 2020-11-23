;
var MfLawsuitPerformReg_Insert = function(window, $) {
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		// 关闭按钮
		$(".cancel").bind("click", function(event) {
			$(top.window.document).find("#showDialog").remove();
		});
	}

    var _bindClose = function () {
        $(".cancel").bind("click", function(event){
            myclose();
        });
    };
	var _ajaxSave = function(obj){
	    var prcp = $("input[name=performPrcp]").val();
        var intst = $("input[name=performIntst]").val();
        var cost = $("input[name=performCost]").val();
        //var amt = $("input[name=unallocatedAmt]").val();
        // const caseAmtList = [
        //  $("input[name=performPrcp]").val(),
        //  // $("input[name=performIntst]").val(),
        //  // $("input[name=performCost]").val()
        // ];
        // const caseAmt = CalcUtil.sum(caseAmtList);
        //compare方法只能接受两个字符串，不然replace方法会报错
         /*if(CalcUtil.compare(amt,prcp) == -1){
            alert("执行本金不能大于待分配金额",0);
            return;
        }*/
			//var dataParam = JSON.stringify($(obj).serializeArray());
			var url = $(obj).attr("action");
			$("input[name='caseId']").val(caseId);
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {ajaxData : dataParam},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
						alert(data.msg,1);
						myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});	
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave,
        bindClose:_bindClose
	
	};
}(window, jQuery);