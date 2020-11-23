	function init(saveType){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		getIdType();
	}

	function getIdType() {
		var options = $("select[name=idType]").find("option");
		var cusType = $("select[name=shareholderType]").val();
		if (cusType == "101") {
			makeOptionsJQ(options, 'A,B,C');
			$("select[name=idType]").val("A");
		} else if (cusType.substring(0,1) == "2") {
			makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9');
			$("select[name=idType]").val("0");
		}
	}

	function getIdTypeTmp() {
			var idType = $("select[name=idType]").val();
			if (idType == "A") {
				$("input[name=idNum]").attr("alt", "organ");
			} else if (idType == "B") {
				$("input[name=idNum]").attr("alt", "credit");
			} else if (idType == "C") {
				$("input[name=idNum]").attr("alt", "licence");
			} else if (idType == "0") {
				$("input[name=idNum]").attr("alt", "idnum");
			} else {
				$("input[name=idNum]").attr("alt", "tmp");
			}
			$("input[name=idNum]").val("");
		}
		
	function saveCertiInfo(obj,saveType){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			/*var checkFlag = "";
			//证件号码唯一性验证
			var idNum = $("input[name=idNum]").val();
			var idNumTitle = $("input[name=idNum]").attr("title");
			var idNumType = $("select[name=idType]").val();
			var relationId = $("input[name=shareholderId]").val();
			var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusShareholder",idNumType,saveType);
			checkFlag = idNumResult.split("&")[0];
			idNumResult = idNumResult.split("&")[1];
			if(checkFlag == "1"){
				window.top.alert(idNumResult,2,function(){
					ajaxInsertCusForm(obj);
				});
			}else{*/
			ajaxInsertCusForm(obj);
			//}
		}
	}
	
	function updateCallBack(){
		top.addFlag = true;
		
		myclose_click();
	};