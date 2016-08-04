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
		userOperation($(this), obj);
		//阻止滑动
		if($("#shadeConBlur").hide()){
			$('html').css({
				'position': 'fixed',
				'height': '100%',
				'overflow': 'hidden',
				'width': '100%'
			});

			$('#shadeConBlur').show().addClass('show');
			$(obj).addClass('show');
			
			var oCon = btn.parents('.conTop').siblings('.conBot').find('a');

			if(oCon.hasClass('gray')){
				$('#shadeConBlur').find('.btn a').hide();
				$('#shadeConBlur').find('.btn span').css({
					'float': 'none',
					'margin': '0 auto',
					'display': 'block'
				});
			}
			else{
				$('#shadeConBlur').find('.btn a').show();
				$('#shadeConBlur').find('.btn span').css({
					'float': 'left',
					'margin': '0',
					'display': 'block'
				});
			}
		}
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
		
		$('html').css({
			'position': 'static',
			'height': 'auto',
			'overflow': 'auto',
			'width': 'auto'
		});
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

function userOperation($this, obj){
	if(obj == '.layerConCoupon'){//领取优惠券
		receiveCoupon($this);
	}else if(obj==".layerConRule"){//查看使用规则
		var usingRule = $this.next().attr("value");
		var buyLink = $this.next().next().attr("value");
		$('#usingRule').text(usingRule)
		var ruleToUse = $('#ruleToUse');
		if(ruleToUse){
			$("#ruleToUse").attr("href",buyLink);
		}
	}
}

/**
 * 获取优惠券信息
 * 
 * @param obj
 * @returns
 */
function receiveCoupon(obj){
	var rootPath = $("#rootPath").val();
	var receiveAjaxUrl= rootPath + "/coupon/receive.do"
	var couponId = $(obj).next().next().attr("value");
	var buyLink = $(obj).next().next().next().attr("value");
	var userId = $('#userId').val();
	$.ajax({
			url : receiveAjaxUrl,
			type : 'post', // 上线之前改成post
			timeout : 3000,
			data : {
				couponId :couponId,
				userId : userId
			},
			dataType : 'json',
			cache : false,
			success : function(json) {
				if (json) {
					$("#receivedTimes_" + couponId).text(json.receivedTimes);
					$("#couponCode").text(json.couponCode);
					$("#toUse").attr("href",buyLink);
				}
			},
			error : function(e) {
			}
	});
}

