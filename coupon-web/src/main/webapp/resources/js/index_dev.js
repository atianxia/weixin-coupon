//阻止滑动
document.addEventListener('touchmove', function(event) {
    //判断条件,条件成立才阻止背景页面滚动,其他情况不会再影响到页面滚动
    if(!$("#shadeConBlur").is(":hidden")){
        event.preventDefault();
    }
})

//弹层layer
function layer(btn,obj){
	$(btn).on('click',function(){
		//领取优惠券
		if(obj == '.layerConCoupon'){
			receiveCoupon($(this));
		}
		$('#shadeConBlur').show().addClass('show');
		$(obj).addClass('show');
	});
}

//关闭弹层
function oClose(btn,obj){
	$(btn).on('click',function(){
		$(obj).removeClass('show');
		$('#shadeConBlur').removeClass('show');
		var t = setTimeout(function(){
			$('#shadeConBlur').hide();
		},200)
	});
}

$(function(){
	$('.ruleLy').each(function(){
		var oThis = $(this);
		layer(oThis,'.layerConRule');
	});

	$('.cpLy').each(function(){
		var oThis = $(this);
		layer(oThis,'.layerConCoupon');
	});

	oClose('.layerConRule .btn span','.layerConRule');
	oClose('.layerConCoupon .btn span','.layerConCoupon');
	
})

/**
 * 获取优惠券信息
 * 
 * @param obj
 * @returns
 */
function receiveCoupon(obj){
	var rootPath = $("#rootPath").val();
	var receiveAjaxUrl= rootPath + "/coupon/receive.do"
	var couponId = $(obj).next().attr("value");
	var buyLink = $(obj).next().next().attr("value");
	$.ajax({
			url : receiveAjaxUrl,
			type : 'post', // 上线之前改成post
			timeout : 3000,
			data : {
				couponId :couponId,
				userId : '111'
			},
			dataType : 'json',
			cache : false,
			success : function(json) {
				if (json && json.couponCode) {
					$("#couponCode").text(json.couponCode);
					$("#toUse").attr("href",buyLink);
				}
			},
			error : function(e) {
			}
	});
}