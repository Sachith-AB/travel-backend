-- Guide Bookings Table
-- This table stores standalone guide bookings (not part of tours)

CREATE TABLE IF NOT EXISTS guide_bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    guide_id BIGINT NOT NULL,
    locations TEXT,
    start_date DATE,
    end_date DATE,
    start_time TIME,
    number_of_days INTEGER,
    number_of_people INTEGER DEFAULT 1,
    contact_number VARCHAR(20),
    special_requests TEXT,
    preferred_language VARCHAR(50),
    accommodation_needed BOOLEAN DEFAULT FALSE,
    transportation_needed BOOLEAN DEFAULT FALSE,
    meal_preferences VARCHAR(255),
    total_price DECIMAL(10, 2),
    status VARCHAR(20) DEFAULT 'PENDING',
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    approved_at TIMESTAMP,
    paid_at TIMESTAMP,
    completed_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    cancellation_reason TEXT,
    rejection_reason TEXT,
    multi_request_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_guide_booking_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_guide_booking_guide FOREIGN KEY (guide_id) REFERENCES guids(id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_guide_bookings_user_id ON guide_bookings(user_id);
CREATE INDEX idx_guide_bookings_guide_id ON guide_bookings(guide_id);
CREATE INDEX idx_guide_bookings_status ON guide_bookings(status);
CREATE INDEX idx_guide_bookings_payment_status ON guide_bookings(payment_status);
CREATE INDEX idx_guide_bookings_dates ON guide_bookings(start_date, end_date);
CREATE INDEX idx_guide_bookings_created_at ON guide_bookings(created_at DESC);
CREATE INDEX idx_guide_bookings_multi_request_id ON guide_bookings(multi_request_id);

-- Comments for documentation
COMMENT ON TABLE guide_bookings IS 'Stores standalone guide booking requests from users';
COMMENT ON COLUMN guide_bookings.status IS 'Booking status: PENDING, APPROVED, REJECTED, PAID, COMPLETED, CANCELLED';
COMMENT ON COLUMN guide_bookings.payment_status IS 'Payment status: PENDING, PAID, REFUNDED';
COMMENT ON COLUMN guide_bookings.approved_at IS 'Timestamp when guide approved the booking';
COMMENT ON COLUMN guide_bookings.paid_at IS 'Timestamp when user paid for the booking';
COMMENT ON COLUMN guide_bookings.completed_at IS 'Timestamp when the service was completed';
COMMENT ON COLUMN guide_bookings.cancelled_at IS 'Timestamp when the booking was cancelled';
