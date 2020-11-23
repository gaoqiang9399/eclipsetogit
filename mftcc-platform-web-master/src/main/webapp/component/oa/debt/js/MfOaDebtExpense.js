;
function textdown(obj,e,num) {
    textevent = e;
    if (textevent.keyCode == 8) {
        return;
    }
    if (document.getElementById($(obj).attr("id")).value.length >= num) {
        if (!document.all) {
            textevent.preventDefault();
        } else {
            textevent.returnValue = false;
        }
    }
}
function textdown(e) {
    textevent = e;
    if (textevent.keyCode == 8) {
        return;
    }
    if (document.getElementById('textarea').value.length >= 200) {
        if (!document.all) {
            textevent.preventDefault();
        } else {
            textevent.returnValue = false;
        }
    }
}
function textup() {
    var s = document.getElementById('textarea').value;
    //判断ID为text的文本区域字数是否超过100个 
    if (s.length > 200) {
        document.getElementById('textarea').value = s.substring(0, 200);
    }
}
function textupp(obj,num) {
/* 	alert($(obj).attr("id"));
	alert(num); */
    var s = document.getElementById($(obj).attr("id")).value;
    //判断ID为text的文本区域字数是否超过100个 
    if (s.length > num) {
        document.getElementById($(obj).attr("id")).value = s.substring(0, num);
    }
}
		function select0(obj) {
            document.getElementsByName("applyType")[0].checked = true;
            $(".mysearchh-div").find("li").removeClass("mySelectedNode");
	        $(obj).addClass("mySelectedNode");
        }
        function select1(obj) {
            document.getElementsByName("applyType")[1].checked = true;
            $(".mysearchh-div").find("li").removeClass("mySelectedNode");
	        $(obj).addClass("mySelectedNode");
        }
		function select2(obj) {
            document.getElementsByName("applyType")[2].checked = true;
            $(".mysearchh-div").find("li").removeClass("mySelectedNode");
	        $(obj).addClass("mySelectedNode");
        }
        function select3(obj) {
            document.getElementsByName("applyType")[3].checked = true;
            $(".mysearchh-div").find("li").removeClass("mySelectedNode");
	        $(obj).addClass("mySelectedNode");
        }
		function select4(obj) {
            document.getElementsByName("debtSts")[0].checked = true;
            $(".mysearch-div1").find("li").removeClass("mySelectedNode");
	        $(obj).addClass("mySelectedNode"); 
        }
        function select5(obj) {
            document.getElementsByName("debtSts")[1].checked = true;
            $(".mysearch-div1").find("li").removeClass("mySelectedNode");
	        $(obj).addClass("mySelectedNode"); 
        }
        function bill(obj){
        	func_uior_valTypeImm(obj);
        	$("input[name='bigMoney']").next().css("margin","-3px 0 0 -99px");
        }
        function money(obj) {
			toMoney(obj);
			bigMoney();
        }     
 		function bigMoney() {
			var amt = $("input[name='applyAmt']").val();
			url=webPath+"/mfOaDebt/bigMoneyAjax";
			$.ajax({
					url : url,
					data :{
						money : amt 
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						$("input[name='bigMoney']").val(data.bigMoney);
					}
				});
        } 
  