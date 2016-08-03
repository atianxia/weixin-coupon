$(document).ready(function() {
	doSearch();
});
/**
 * 时间检查
 */
function checkDate(d1, d2) {
	var t1, t2, d1_new, d2_new;
	var d1_array = d1.substring(0, 10).split('-');
	var d2_array = d2.substring(0, 10).split('-');
	d1_new = d1_array[1] + '/' + d1_array[2] + '/' + d1_array[0] + ' ' + d1.substring(10, 19);
	d2_new = d2_array[1] + '/' + d2_array[2] + '/' + d2_array[0] + ' ' + d2.substring(10, 19);
	t1 = Date.parse(d1_new);
	t2 = Date.parse(d2_new);

	if (d1 != null && d1 != '' && (isNaN(t1) || d1.length != 19)) {
		msgOut("起始时间不合法，请重新选择");
		return false;
	}
	if (d2 != null && d2 != '' && (isNaN(t2) || d2.length != 19)) {
		msgOut("结束时间不合法，请重新选择");
		return false;
	}
	if (d1 != null && d1 != '' && d2 != null && d2 != '') {
		if (t1 > t2) {
			msgOut("起始时间不能大于结束时间，请重新选择");
			return false;
		}
	}
	return true;
}

function valueCheck() {
	var couponName = $.trim($('#couponName').val());
	var userId = $.trim($('#userId').val());
	var receiveStartTime = $.trim($('#receiveStartTime').datetimebox('getValue'));
	var receiveEndTime = $.trim($('#receiveEndTime').datetimebox('getValue'));
	var createStartTime = $.trim($('#createStartTime').datetimebox('getValue'));
	var createEndTime = $.trim($('#createEndTime').datetimebox('getValue'));
	if (!!couponName && !!userId && !!createStartTime && !!createEndTime) {
		return true;
	}
	if (couponName && !!userId && !!createStartTime && !!createEndTime) {
		if (!!round) {
			return false;
		} else {
			return true;
		}
	}
}

/**
 * 查询
 */
function doSearch(type) {
	var url = "items.do";
	if (type == "init") {
		url = "";
	}
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var couponName = $.trim($('#couponName').val());
	var userId = $.trim($('#userId').val());
	var createStartTime = $.trim($('#createStartTime').datetimebox('getValue'));
	var createEndTime = $.trim($('#createEndTime').datetimebox('getValue'));
	if (!checkDate($.trim(createStartTime), $.trim(createEndTime))) {
		return;
	}

	$('#tt').datagrid({
		method : 'POST',
		rownumbers : "true",
		pagination : "true",
		width : "100%",
		height : 350,
		queryParams : {
			couponId : $('#couponId').val(),
			couponName : $('#couponName').val(),
			userId : $('#userId').val(),
			createStartTime : $('#createStartTime').datetimebox('getValue'),
			createEndTime : $('#createEndTime').datetimebox('getValue'),
			receiveStartTime : $('#receiveStartTime').datetimebox('getValue'),
			receiveEndTime : $('#receiveEndTime').datetimebox('getValue'),
			rnd : Math.random()
		},
		url : url,
		columns : [ [ {
			field : 'couponId',
			title : '优惠券ID',
			align : 'center'

		}, {
			field : 'couponName',
			title : '优惠券名称',
			align : 'center'
		}, {
			field : 'couponCode',
			title : '优惠券编码',
			align : 'center'
		}, {
			field : 'userId',
			title : '用户Id',
			align : 'center'
		} ] ]
	});
}

function valueExport() {
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var itemId = $.trim($('#itemId').val());
	var itemName = $.trim($('#itemName').val());
	var round = $.trim($('#round').val());
	var actiTopic = $.trim($('#actiTopic').val());
	var userId = $.trim($('#userId').val());
	var createStartTime = $.trim($('#createStartTime').datetimebox('getValue'));
	var createEndTime = $.trim($('#createEndTime').datetimebox('getValue'));
	var timeDiffe = $.trim($('#timeDiffe').val());
	if (createStartTime.length > 0) {
		if (createStartTime.match(reg) == null) {
			msgOut("出价开始时间格式错误");
			return;
		}
	}
	if (createEndTime.length > 0) {
		if (createEndTime.match(reg) == null) {
			msgOut("出价结束时间格式错误");
			return;
		}
	}
	if (!!!itemId && !!!itemName && !!!round && !!!actiTopic && !!!userId && !!!createStartTime && !!!createEndTime && !!!timeDiffe) {
		msgOut("导出条件不能全部为空");
		return false;
	}
	if (!!!itemId && !!!itemName && !!!actiTopic && !!!userId && !!!createStartTime && !!!createEndTime) {
		if (!!!round && !!!timeDiffe) {
			return true;
		} else {
			msgOut("不能只填轮次一个条件");
			return false;
		}
	}
	if (!checkDate($.trim(createStartTime), $.trim(createEndTime))) {
		return;
	}
	var condition = {
		itemId : $('#itemId').val(),
		itemName : $('#itemName').val(),
		round : $('#round').val(),
		actiTopic : $('#actiTopic').val(),
		userId : $('#userId').val(),
		createStartTime : $('#createStartTime').datetimebox('getValue'),
		createEndTime : $('#createEndTime').datetimebox('getValue'),
		timeDiffe : $.trim($('#timeDiffe').val()),
		timeSpan : $.trim($('#timeSpan').val())
	};

	$.messager.progress({
		title : '准备',
		msg : '...'
	});
	$.post("bidHistoryExport.do", condition, function(data) {
		if (data.mess == 'no') {
			$.messager.show({
				title : '提示',
				msg : '没有数据可导出！'
			});
			$.messager.progress('close');
		} else if (data.mess == 'ok') {
			if (data.max != null) {
				$.messager.show({
					title : '提示',
					msg : '导出数据已经超出' + data.max + '条，请使用合理的查询条件进行限制！'
				});
				$.messager.progress('close');
			} else {
				var form1 = document.forms['form1'];
				form1.action = "export.do";
				form1.submit();
				$.messager.progress('close');
			}
		} else {
			$.messager.show({
				title : '提示',
				msg : '导出异常，稍后再试！'
			});
			$.messager.progress('close');
		}
	});
}
/**
 * 重置查詢條件
 */
function doReset() {
	$('#itemId').val('');
	$('#itemName').val('');
	$('#round').val('');
	$('#actiTopic').val('');
	$('#userId').val('');
	$('#createStartTime').datetimebox('setValue', '');
	$('#createEndTime').datetimebox('setValue', '');
	$('#timeDiffe').val('');
	$('#timeSpan').val('30');
}
function msgOut(content) {
	$.messager.show({
		title : '提示',
		msg : content,
		showType : 'fade',
		timeout : 3000
	});
}