package com.hooxi.config.util;

import com.hooxi.config.repository.entity.DestinationMappingEntity;
import com.hooxi.data.model.config.FindDestinationsRequest;

public class CacheUtils {
    public static String generateCacheKey(FindDestinationsRequest req) {
        return generateCacheKey(req.getTenantId(), req.getDomain(), req.getSubDomain(), req.getEventType());
    }

    public static String generateCacheKey(String tenantId, String domain, String subDomain, String eventType) {
        return "DEST:" + tenantId + ":" + domain + ":" + subDomain + ":" + eventType;
    }

    public static String generateCacheKey(DestinationMappingEntity de) {
        return generateCacheKey(de.getTenantId(), de.getDomainId(), de.getSubdomainId(), de.getEventType());
    }
}
