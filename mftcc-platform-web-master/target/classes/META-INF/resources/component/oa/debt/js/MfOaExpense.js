		function selectType(){
			$(this).parents(".mysearch-div").find("li.mySelectedNode").removeClass("mySelectedNode");
			$(this).addClass("mySelectedNode");
			$("input[name='"+$(this).attr("name")+"']").val($(this).attr("value"));
			if($(this).parent().attr("id")=="exptype-more"){
				$("#exptype-show>li:last").appendTo("#exptype-more");
				$(this).appendTo("#exptype-show");
			}
		};
        function textdown(obj,e,num) {
            var textevent = e;
            if (textevent && textevent.keyCode == 8) {
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
        function textup() {
            var s = document.getElementById('textarea').value;
            //判断ID为text的文本区域字数是否超过100个 
            if (s.length > 200) {
                document.getElementById('textarea').value = s.substring(0, 200);
            }
        }
        function textupp(obj,num) {
            var s = document.getElementById($(obj).attr("id")).value;
            //判断ID为text的文本区域字数是否超过100个 
            if (s.length > num) {
                document.getElementById($(obj).attr("id")).value = s.substring(0, num);
            }
        }
        function initAmt(obj){
        	func_uior_valTypeImm(obj);
        	$("input[name='amt']").nextAll(".error").css("margin-left","0px");
        	$("input[name='amt']").nextAll(".error").css("margin-top","0px");
        }
        function bill(obj){
        	func_uior_valTypeImm(obj);
        	$("input[name='billNo']").next().css("margin-left","400px");
        }
        function bill1(obj){
        	func_uior_valTypeImm(obj);
        	$("input[name='bigMoney']").nextAll(".error").css("margin","0 0 0 0");
        }
		function money(obj) {
			toMoney(obj);
			debtExpType =$(obj).attr("name");
			bigMoney();
        }     
 		function bigMoney() {
			var amt = $("input[name='"+debtExpType+"']").val();
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
        