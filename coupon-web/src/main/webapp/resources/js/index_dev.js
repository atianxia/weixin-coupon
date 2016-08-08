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
		var falg = userOperation($(this), obj);
		if(falg== false){
			return;
		}
		//阻止滑动
		if($("#shadeConBlur").hide()){
			$('html').css({
				//'position': 'fixed',
				//'height': '100%',
				'overflow': 'hidden'
				//'width': '100%'
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
	layer('#detailBtn','.layerConCoupon');
	oClose('.layerConRule .btn span','.layerConRule');
	oClose('.layerConCoupon .btn span','.layerConCoupon');
	
})

function userOperation($this, obj){
	
	if(obj == '.layerConCoupon'){//领取优惠券
		var couponType = $this.next().attr("value");
		if(couponType ==0){
			return receiveCoupon($this);
		}else{
			return false;
		}
		
	}else if(obj==".layerConRule"){//查看使用规则
		var couponType = $this.next().next().attr("value");
		if(couponType ==0){
			return false;
		}
		var usingRule = $this.next().attr("value");
		var buyLink = $this.next().next().next().attr("value");
		$('#usingRule').text(usingRule)
		var ruleToUse = $('#ruleToUse');
		if(ruleToUse){
			$("#ruleToUse").attr("href",buyLink);
		}
	}
	return true;
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
	var couponId = $(obj).next().next().next().attr("value");
	var buyLink = $(obj).next().next().next().next().attr("value");
	var userId = $('#userId').val();
	var result = false;
	$.ajax({
			url : receiveAjaxUrl,
			type : 'post', // 上线之前改成post
			timeout : 5000,
			data : {
				couponId :couponId,
				userId : userId
			},
			dataType : 'json',
			async: false,
			cache : false,
			success : function(json) {
				if(!json){
					return;
				}
				if (json.responseCode == 0 || json.responseCode ==2 ) {
					$("#receivedTimes_" + couponId).text(json.receivedTimes);
					$("#couponCode").text(json.couponCode);
					$("#toUse").attr("href",buyLink);
					buildHasReceivedCoponHtml($(obj))
					var result = true;
				}else if(json.responseCode == 3){
					buildReceivedCouponSellOutHtml($(obj));
				}
			},
			error : function(e) {
			}
	});
	
	return result;
}
/**
 * 构建已经领取html
 * 
 * @param $this
 * @returns
 */
function buildHasReceivedCoponHtml($this){
	$this.parent().attr("class", "conBot over");
	
	var myCouponUrl = $('#rootPath').val()+'/coupon/myCoupon.do?userId='+$('#userId').val();
	$this.parent().html(
	'<a href="javascript:;" class="gray">已领取</a>' + 
	'<div>' + 
		'<div>' + 
			'<span><a href="' + myCouponUrl +'" >查看我的优惠券</a></span>' + 
		'</div>' + 
	'</div>');
}

/**
 * 构建已经已经领完
 * 
 * @param $this
 * @returns
 */
function buildReceivedCouponSellOutHtml($this){
	$this.parent().attr("class", "conBot over");
	$this.parent().html(
	'<a href="javascript:;" class="gray">已领完</a>' + 
	'<div>' + 
		'<div>' + 
			'<span>已领取：<b id="receivedTimes_${couponInfo.couponId}">${couponInfo.receivedTimes}</b></span>' + 
		'</div>' + 
	'</div>');
}

