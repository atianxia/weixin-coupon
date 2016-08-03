<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>兑换中心</title>
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" type="text/css" href="${resRoot}/css/style_dev.css">
	</head>
	<body>
		<#include "/common.ftl">
		<section>
			<div class="content">
				<ul>
					<#list couponInfos as couponInfo>
					<li>
						<div class="conTop">
							<h3>${couponInfo.couponName}</h3>
							<div>
								<span>有效期至${couponInfo.expireDate? string('yyyy.MM.dd')}</span>
								<a href="javascript:;" class="ruleLy">查看使用规则</a>
								<input type="hidden" id="usingRule_${couponInfo.couponId}" value="${couponInfo.usingRule}"/>
							</div>
							<img src="${imageRootPath}/${couponInfo.brandLogo}">
							<s class="leftPie"></s>
							<s class="rightPie"></s>
						</div>
						<div class="conBot">
							<a href="javascript:;" class="cpLy">立即领取</a>
							<input type="hidden" id="couponId_${couponInfo.couponId}" value="${couponInfo.couponId}"/>
							<input type="hidden" id="couponId_${couponInfo.couponId}" value="${couponInfo.buyLink}"/>
							<span>已领取：<b>${couponInfo.receivedTimes}</b></span>
						</div>
					</li>
					</#list>
				</ul>
			</div>
		</section>
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
				<p id='couponCode'>B20160730SMZDM8138A001GH</p>
				<span>长按优惠码复制</span>
				<div class="btn">
					<span>取消</span>
					<a href="" id="toUse">去使用</a>
				</div>
				<div class="bg"></div>
			</div>

		</div>
	</body>
	<script src="${resRoot}/js/zepto.min.js"></script>
	<script src="${resRoot}/js/index_dev.js"></script>
</html>