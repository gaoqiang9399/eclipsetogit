//标签可选样式
var labelList = [ {
	'value' : 'form-money',
	'label' : '表单金额'
}, {
	'value' : 'startRang',
	'label' : '[区间字段-起始'
}, {
	'value' : 'endRang',
	'label' : '区间字段-结束]'
} , {
	'value' : 'anchor-start',
	'label' : '[锚点-开始'
} , {
	'value' : 'anchor-end',
	'label' : '锚点-结束]'
}];
// 字段可选样式
var fieldList = [ {
	'value' : 'pointer',
	'label' : '手形指针'
},	{
	'value' : 'form-money',
	'label' : '表单金额'
}, {
	'value' : 'half',
	'label' : '半行字段'
}, {
	'value' : '3',
	'label' : 'three'
} , {
	'value' : 'anchor-ctrl',
	'label' : '锚点控制样式'
}];
var eventList = [ {
	'value' : "func_uior_valueFormat(this, 'idnum');",
	'label' : '表单格式校验—身份证号—类型参见uior_val1.js'
}, {
	'value' : "func_uior_valueFormat(this, 'nonnegative');",
	'label' : '表单格式校验—非负—其他类型参见uior_val1.js'
}, {
	'value' : 'toDouble(this);',
	'label' : 'double类型校验'
}, {
	'value' : 'inputAccount(this);',
	'label' : '银行卡格式化'
} ];
