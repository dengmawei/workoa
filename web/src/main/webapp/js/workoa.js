$.extend($.gritter.options, { 
	position: 'top-right',
	fade_in_speed: 'fast',
	fade_out_speed: 'slow',
	time: 3000
});

function showTipInfo(text){
	$.gritter.add({
		title:'系统提示',
		text:text
	});
}