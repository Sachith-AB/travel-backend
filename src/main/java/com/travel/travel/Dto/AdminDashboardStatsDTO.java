package com.travel.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStatsDTO {
    
    // Main Statistics
    private StatsCard partnerRequests;
    private StatsCard activeTravelers;
    private StatsCard monthlyRevenue;
    private StatsCard activeBookings;
    
    // System Statistics
    private SystemStats systemStats;
    
    // Performance Metrics
    private PerformanceMetrics performanceMetrics;
    
    // Recent Activities
    private List<ActivityItem> recentActivities;
    
    // Top Performers
    private List<TopPerformer> topPerformers;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatsCard {
        private String title;
        private String value;
        private String change;
        private String changeType; // "positive" or "negative"
        private String subtitle;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SystemStats {
        private Long activeTours;
        private Long registeredPartners;
        private Long totalUsers;
        private Double systemUptime;
        private Long pendingApprovals;
        private Long activeBookings;
        private Double monthlyGrowth;
        private Long newRegistrations;
        private Long partnerSignups;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformanceMetrics {
        private Integer coveredDistricts;
        private Integer westernProvincePartners;
        private Integer centralProvincePartners;
        private Double averageRating;
        private Double fiveStarPercentage;
        private String averageResponseTime;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityItem {
        private Long id;
        private String type; // "booking", "payment", "partner", "complaint"
        private String customerName;
        private String customerAvatar;
        private String action;
        private String details;
        private String amount;
        private String time;
        private String status;
        private String location;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopPerformer {
        private Integer rank;
        private String name;
        private String type;
        private Double rating;
        private Integer count; // tours, bookings, or packages
        private String countLabel; // "tours", "bookings", "packages"
        private String revenue;
        private String location;
    }
}
