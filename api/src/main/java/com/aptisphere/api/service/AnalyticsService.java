package com.aptisphere.api.service;

import com.aptisphere.api.dto.response.AdminDashboardResponse;

public interface AnalyticsService {
    AdminDashboardResponse getAdminDashboardStats();
}