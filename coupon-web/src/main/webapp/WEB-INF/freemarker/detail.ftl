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
			<div class="content detail">
				<div class="conTit">
					<img src="${couponInfo.brandLogo}">
					<h3>${couponInfo.couponName}</h3>
					<div>
						<span>有效期至 ${couponInfo.expireDate? string('yyyy.MM.dd')}</span>
						<span>已领取：${couponInfo.receivedTimes}</span>
					</div>
				</div>
				<div class="place"></div>
				<div class="conTxt">
					<p>${couponInfo.usingRule}</p>
					<div class="btn">
						<#if couponInfo.received == 1>
							<a href="" class="gray">已领取</a>
						<#elseif couponInfo.amount == couponInfo.receivedTimes>
							<a href="" class="gray">已领完</a>
						<#else>
							<a id="detailBtn" href="">立即领取</a>
						</#if>
					</div>
				</div>
				
			</div>
		</section>
		
		<!-- layer -->
		<div id="shadeConBlur" style="display:none;">
			<div class="layerConRule">
				<div class="tit">使用规则</div>
				<p>因此券为活动券，并不消耗各位值友任何积分或金币，活动随时可能变更或取消，故具体活动情况还请询问商家或参照商家活动说明，如有变动或取消还请谅解。</p>
				<div class="btn">
					<span>取消</span>
					<a href="">立即领取</a>
				</div>
			</div>

			<div class="layerConCoupon">
				<div class="tit"></div>
				<strong>获得优惠券</strong>
				<p>B20160730SMZDM8138A001GH</p>
				<span>长按优惠码复制</span>
				<span class="gray">可在 特享优惠 公众号 — 我的优惠券 中查看</span>
				<div class="btn">
					<span>取消</span>
					<a href="">去使用</a>
				</div>
				<div class="bg"></div>
			</div>

		</div>
	</body>
	<#include "/common_bottom.ftl">
</html>