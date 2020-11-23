/* Chinese initialisation for the jQuery UI date picker plugin. */
/* Written by Ressol (ressol@gmail.com). */
jQuery(function($){
	$.monthPicker.regional['zh-TW'] = {
		closeText: '�ر�',
		clearText: '���',
		prevText: '&#x3C;����',
		nextText: '����&#x3E;',
		currentText: '����',
		monthNames: ['һ��','����','����','����','����','����',
		'����','����','����','ʮ��','ʮһ��','ʮ����'],
		monthNamesShort: ['һ��','����','����','����','����','����',
		'����','����','����','ʮ��','ʮһ��','ʮ����'],
		dayNames: ['������','����һ','���ڶ�','������','������','������','������'],
		dayNamesShort: ['����','��һ','�ܶ�','����','����','����','����'],
		dayNamesMin: ['��','һ','��','��','��','��','��'],
		weekHeader: '��',
		dateFormat: 'yy/mm/dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '��'};
	$.monthPicker.setDefaults($.monthPicker.regional['zh-TW']);
});
