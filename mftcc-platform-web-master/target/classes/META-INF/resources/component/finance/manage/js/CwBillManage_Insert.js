
var CwBillManage_Insert=function(window,$){
    var _selectDialog=function(){
        selectInvoiceInfo(_selectBack);
    };

    //回调
    var _selectBack=function(mfCusInvoiceMation){
        var taxpayerNo=mfCusInvoiceMation.taxpayerNo;
        var address=mfCusInvoiceMation.address;
        var tel=mfCusInvoiceMation.tel;
        var bankName=mfCusInvoiceMation.bankName;
        var accountNumber=mfCusInvoiceMation.accountNumber;
        var id = mfCusInvoiceMation.id;
        $("input[name=taxpayerNum]").val(taxpayerNo);
        $("input[name=address]").val(address);
        $("input[name=telphone]").val(tel);
        $("input[name=bankAccount]").val(bankName);
        $("input[name=account]").val(accountNumber);
        $("input[name=taxpayerId]").val(id);
    };


	return{
		selectDialog:_selectDialog,
        selectBack:_selectBack
	}
}(window,jQuery);
function selectInvoiceInfo(callback){
    var url = "/mfCusInvoiceMation/getList?cusNo="+cusNo;
    dialog({
        id:'invoiceDialog',
        title:"选择开票信息",
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};
