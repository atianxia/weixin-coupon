package com.groundnine.coupon.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

@Component
public class RiskControlHelper {
	
	@Value("${coupon.admin.ip.whitelist}")
	private String validIpList;
	
	public boolean isIpValid(String ip) {
		if(ip == null){
			return true;
		}
		List<String> ipSet = Splitter.on(",").splitToList(validIpList);
        if (StringUtils.isNotBlank(ip)) {
            String[] ipArray = ip.split(",");
            for (String one : ipArray) {
                if (StringUtils.isNotBlank(one)) {
                    if (ipSet.contains(one.trim())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
