package com.travel.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementDTO {
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserStats {
        private Long total;
        private Long active;
        private Long pending;
        private Long suspended;
        private String totalChange;
        private String activeChange;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDetails {
        private String id;
        private String name;
        private String email;
        private String phone;
        private String role;
        private String status;
        private String location;
        private String registered;
        private String lastActive;
        private Integer totalBookings;
        private Double totalSpent;
        private Double totalEarned;
        private Double totalRevenue;
        private Double rating;
        private Boolean verified;
        private String avatar;
        private String propertyName;
        private String vehicleType;
        private String agencyName;
        private List<String> specialties;
        private String suspensionReason;
        private String bio;
        private Integer experienceYears;
        private Double hoursRate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String role;
        private String location;
        private String password;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateUserRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserListResponse {
        private UserStats stats;
        private List<UserDetails> users;
        private Integer totalPages;
        private Integer currentPage;
        private Long totalElements;
    }
}
