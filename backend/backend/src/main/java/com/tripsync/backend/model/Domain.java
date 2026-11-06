package com.tripsync.backend.model;

public final class Domain {
    private Domain() {
    }

    public enum Role {
        OWNER, ADMIN, MEMBER
    }

    public enum TripStatus {
        PLANNING, UPCOMING, ONGOING, COMPLETED, CANCELLED
    }

    public enum SettlementStatus {
        PENDING, CONFIRMED, CANCELLED
    }

    public enum NotificationType {
        TRIP_INVITATION, MEMBER_JOINED, ITINERARY_UPDATED, EXPENSE_ADDED, SETTLEMENT_UPDATED, NEW_MESSAGE
    }
}
