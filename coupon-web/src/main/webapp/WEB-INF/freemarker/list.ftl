<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>兑换中心</title>
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	</head>
	<body>
		<#include "/common_top.ftl">
		<section>
			<div class="content">
				<ul>
					<#list couponInfos as couponInfo>
					<li>
						<div class="conTop">
							<h3>${couponInfo.couponName}</h3>
							<div>
								<span>有效期至${couponInfo.expireDate? string('yyyy.MM.dd')}</span>
								<#if couponInfo.couponType = 0>
								<a href="${rootPath}/coupon/detail.do?couponId=${couponInfo.couponId}&userId=${userId}" class="ruleLy">查看使用规则</a>
								<#else>
								<a href="javascript:;" class="ruleLy">查看使用规则</a>
								</#if>
								<input type="hidden" id="usingRule_${couponInfo.couponId}" value="${couponInfo.usingRule}"/>
							</div>
							<img src="${couponInfo.brandLogo}">
							<s class="leftPie"></s>
							<s class="rightPie"></s>
						</div>
						
						<#if couponInfo.couponType = 1>
							<div class="conBot">
								<a href="${couponInfo.localLink}" class="cpLy">去领取</a>
								<input type="hidden" id="couponType_${couponInfo.couponId}" value="${couponInfo.couponType}"/>
							</div>
						<#elseif couponInfo.couponType = 2>
							<div class="conBot">
								<a href="${couponInfo.buyLink}" class="cpLy">去领取</a>
								<input type="hidden" id="couponType_${couponInfo.couponId}" value="${couponInfo.couponType}"/>
							</div>
						<#else>
							<#if couponInfo.received == 1>
								<div class="conBot over">
									<a href="javascript:;" class="gray">已领取</a>
									<div>
										<div>
											<span><a href="${rootPath}/coupon/myCoupon.do?userId=${userId}" >查看我的优惠券</a></span>
											<input type="hidden" id="couponId_${couponInfo.couponId}" value="${couponInfo.couponId}"/>
											<input type="hidden" id="buyLink_${couponInfo.couponId}" value="${couponInfo.buyLink}"/>
										</div>
									</div>
								</div>
							<#elseif couponInfo.amount == couponInfo.receivedTimes>
								<div class="conBot over">
									<a href="javascript:;" class="gray">已领完</a>
									<div>
										<div>
											<span>已领取：<b id="receivedTimes_${couponInfo.couponId}">${couponInfo.receivedTimes}</b></span>
										</div>
									</div>
								</div>
							<#else>
								<div class="conBot">
									<a href="javascript:;" class="cpLy">立即领取</a>
									<input type="hidden" id="couponType_${couponInfo.couponId}" value="${couponInfo.couponType}"/>
									<span>已领取：<b id="receivedTimes_${couponInfo.couponId}">${couponInfo.receivedTimes}</b></span>
									<input type="hidden" id="couponId_${couponInfo.couponId}" value="${couponInfo.couponId}"/>
									<input type="hidden" id="buyLink_${couponInfo.couponId}" value="${couponInfo.buyLink}"/>
								</div>
							</#if>
						</#if>
					</li>
					</#list>
				</ul>
			</div>
		</section>
		<div id="shadeConBlur" style="display:none;">
			<div class="layerConRule">
				<div class="tit">使用规则</div>
				<p id="usingRule">因此券为活动券，并不消耗各位值友任何积分或金币，活动随时可能变更或取消，故具体活动情况还请询问商家或参照商家活动说明，如有变动或取消还请谅解。</p>
				<div class="btn">
					<span>取消</span>
					<a href="">立即领取</a>
				</div>
			</div>

			<div class="layerConCoupon">
				<div class="tit"></div>
				<strong>获得优惠券</strong>
				<p id='couponCode'></p>
				<span>长按优惠码复制</span>
				<div class="btn">
					<span>取消</span>
					<a href="" id="toUse">去使用</a>
				</div>
				<div class="bg"></div>
			</div>

		</div>
		<#include "/common_bottom.ftl">
	</body>
</html>