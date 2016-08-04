<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>我的优惠券</title>
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
					<#list couponItems as couponItem>
					<li>
						<div class="conTop">
							<h3>${couponItem.couponName}</h3>
							<div>
								<span>有效期至 ${couponItem.expireDate? string('yyyy.MM.dd')}</span>
								<a href="javascript:;" class="ruleLy">查看使用规则</a>
								<input type="hidden" id="usingRule_${couponItem.couponId}" value="${couponItem.usingRule}"/>
								<input type="hidden" id="buyLink_${couponItem.couponId}" value="${couponItem.buyLink}"/>
							</div>
							<img src="${imageRootPath}/${couponItem.brandLogo}">
							<s class="leftPie"></s>
							<s class="rightPie"></s>
						</div>
						<div class="conBot ed">
							<a href="${couponItem.buyLink}">去使用</a>
							
							<div>
								<strong>已领取</strong>
								<div>
									<p>${couponItem.couponCode}</p>
									<span>长按优惠码复制</span>
								</div>
							</div>
						</div>
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
					<a href="" id=ruleToUse>去使用</a>
				</div>
			</div>

			<div class="layerConCoupon">
				<div class="tit"></div>
				<strong>获得优惠券</strong>
				<p id='couponCode'></p>
				<span>复制</span>
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