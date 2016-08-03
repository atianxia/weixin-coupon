<#--
 分页（Pager对象、链接URL、参数Map、最大页码显示数） 
使用前需要先引用page.css样式表
使用示例：
<#assign pager = {"pageNumber": (orderList.pageNumber)!,"pageSize": (orderList.pageCount)!,"pageCount": (orderList.totalDataCount/orderList.pageCount)?ceiling} />
<@p.pager pager = pager baseUrl = "/order/queryOrdersForSQL.htm" parameterMap = {"orderName": (order.orderName)!} /> 
我们需要定义个pager对象，里面至少有pagerNumber,pageSize,pageCount这几个参数
pageNumber:当前的页数
pageSize:当前页面的记录条数
pageCount:总页数
property:其他属性
keyword:如果是搜索方式，输入搜索关键字
orderBy:输入按哪个字段进行排序
orderType:输入排序类型ASC升序,DESC降序
baseUrl：基础的页面分页url不带参数的
parameterMap:输入每次页面调整需要带入的参数
maxShowPageCount：最大显示的页面数量
-->
<#macro pager pager baseUrl parameterMap = {} maxShowPageCount = 6>

	<#local pageNumber = pager.pageNumber />
	<#local pageSize = pager.pageSize />
	<#local pageCount = pager.pageCount />
	<#local property = pager.property />
	<#local keyword = pager.keyword />
	<#local orderBy = pager.orderBy />
	<#local orderType = pager.orderType />
	
	<#local parameter = "" />
	<#if (pageSize != "")!>
		<#local parameter = parameter + "&pageSize=" + pageSize />
	</#if>
	<#if (property != "")!>
		<#local parameter = parameter + "&property=" + property />
	</#if>
	<#if (keyword != "")!>
		<#local parameter = parameter + "&keyword=" + keyword />
	</#if>
	<#if (orderBy != "")!>
		<#local parameter = parameter + "&orderBy=" + orderBy />
	</#if>
	<#if (orderType != "")!>
		<#local parameter = parameter + "&orderType=" + orderType />
	</#if>
	<#list parameterMap?keys as key>
		<#if parameterMap[key] != null && parameterMap[key] != "">
			<#local parameter = parameter + "&" + key + "=" + parameterMap[key] />
		</#if>
	</#list>
	
	<#if baseUrl?contains("?")>
		<#local baseUrl = baseUrl + "&" />
	<#else>
		<#local baseUrl = baseUrl + "?" />
	</#if>
	<#local firstPageUrl = baseUrl + "pageNumber=1" + parameter />
	<#local lastPageUrl = baseUrl + "pageNumber=" + pageCount + parameter />
	<#local prePageUrl = baseUrl + "pageNumber=" + (pageNumber - 1) + parameter />
	<#local nextPageUrl = baseUrl + "pageNumber=" + (pageNumber + 1) + parameter />

	<#if maxShowPageCount <= 0>
		<#local maxShowPageCount = 6>
	</#if>
	
	<#local segment = ((pageNumber - 1) / maxShowPageCount)?int + 1 />
	<#local startPageNumber = (segment - 1) * maxShowPageCount + 1 />
	<#local endPageNumber = segment * maxShowPageCount />
	<#if (startPageNumber < 1)>
		<#local startPageNumber = 1 />
	</#if>
	<#if (endPageNumber > pageCount)>
		<#local endPageNumber = pageCount />
	</#if>

	<#if (pageCount > 1)>
	<div class="pager_area">
		<ul class="pager">
			<li class="pageInfo">
				共 ${pageCount} 页
			</li>
			<#-- 首页 -->
			<#if (pageNumber > 1)>
				<li class="firstPage">
					<a href="${base}${firstPageUrl}">首页</a>
				</li>
			<#else>
				<li class="firstPage">
					<span>首页</span>
				</li>
			</#if>
			
			<#-- 上一页 -->
			<#if (pageNumber > 1)>
				<li class="prePage">
					<a href="${base}${prePageUrl}">上一页</a>
				</li>
			<#else>
				<li class="prePage">
					<span>上一页</span>
				</li>
			</#if>
			
			<#if (startPageNumber > 1)>
				<li>
					<a href="${base}${baseUrl + "pageNumber=" + (pageNumber - 2) + parameter}">...</a>
				</li>
			</#if>
			
			<#list startPageNumber .. endPageNumber as index>
				<#if pageNumber != index>
					<li>
						<a href="${base}${baseUrl + "pageNumber=" + index + parameter}">${index}</a>
					</li>
				<#else>
					<li class="currentPage">
						<span>${index}</span>
					</li>
				</#if>
			</#list>
			
			<#if (endPageNumber < pageCount)>
				<li>
					<a href="${base}${baseUrl + "pageNumber=" + (pageNumber + 2) + parameter}">...</a>
				</li>
			</#if>
		    
			<#-- 下一页 -->
			<#if (pageNumber < pageCount)>
				<li class="nextPage">
					<a href="${base}${nextPageUrl}">下一页</a>
				</li>
			<#else>
				<li class="nextPage">
					<span>下一页</span>
				</li>
			</#if>
			
			<#-- 末页 -->
			<#if (pageNumber < pageCount)>
				<li class="lastPage">
					<a href="${base}${lastPageUrl}">末页</a>
				</li>
			<#else>
				<li class="lastPage">
					<span>末页</span>
				</li>
			</#if>
		</ul>
		</div>
	</#if>
</#macro>