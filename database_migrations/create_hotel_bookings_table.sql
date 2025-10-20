-- Hotel Bookings Table Migration
-- This table stores all hotel room bookings made by users

CREATE TABLE IF NOT EXISTS hotel_bookings (
    id BIGSERIAL PRIMARY KEY,
    
    -- Booking Reference
    booking_reference VARCHAR(50) UNIQUE NOT NULL,
    
    -- User Information
    user_id BIGINT NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    user_phone VARCHAR(50),
    
    -- Hotel & Room Information
    hotel_id BIGINT NOT NULL,
    hotel_name VARCHAR(255) NOT NULL,
    room_id BIGINT NOT NULL,
    room_type VARCHAR(100) NOT NULL,
    
    -- Booking Details
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    number_of_nights INTEGER NOT NULL,
    number_of_guests INTEGER NOT NULL,
    
    -- Pricing Information
    price_per_night DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'LKR',
    
    -- Payment Information
    payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    stripe_payment_intent_id VARCHAR(255),
    stripe_charge_id VARCHAR(255),
    payment_date TIMESTAMP,
    
    -- Booking Status
    booking_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    
    -- Special Requests
    special_requests TEXT,
    
    -- Timestamps
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Cancellation Details
    cancelled_at TIMESTAMP,
    cancellation_reason TEXT,
    refund_amount DECIMAL(10, 2),
    refund_status VARCHAR(50),
    
    -- Foreign Key Constraints
    CONSTRAINT fk_hotel_booking_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_hotel_booking_hotel FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT fk_hotel_booking_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    
    -- Check Constraints
    CONSTRAINT check_dates CHECK (check_out_date > check_in_date),
    CONSTRAINT check_nights CHECK (number_of_nights > 0),
    CONSTRAINT check_guests CHECK (number_of_guests > 0),
    CONSTRAINT check_amounts CHECK (total_amount >= 0 AND price_per_night >= 0)
);

-- Create indexes for better query performance
CREATE INDEX idx_hotel_bookings_user_id ON hotel_bookings(user_id);
CREATE INDEX idx_hotel_bookings_hotel_id ON hotel_bookings(hotel_id);
CREATE INDEX idx_hotel_bookings_room_id ON hotel_bookings(room_id);
CREATE INDEX idx_hotel_bookings_reference ON hotel_bookings(booking_reference);
CREATE INDEX idx_hotel_bookings_status ON hotel_bookings(booking_status);
CREATE INDEX idx_hotel_bookings_payment_status ON hotel_bookings(payment_status);
CREATE INDEX idx_hotel_bookings_dates ON hotel_bookings(check_in_date, check_out_date);

-- Create updated_at trigger
CREATE OR REPLACE FUNCTION update_hotel_bookings_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_hotel_bookings_updated_at
    BEFORE UPDATE ON hotel_bookings
    FOR EACH ROW
    EXECUTE FUNCTION update_hotel_bookings_updated_at();

-- Add comments for documentation
COMMENT ON TABLE hotel_bookings IS 'Stores all hotel room bookings made by users';
COMMENT ON COLUMN hotel_bookings.booking_reference IS 'Unique booking reference number (e.g., HB-20250120-XXXX)';
COMMENT ON COLUMN hotel_bookings.payment_status IS 'Payment status: PENDING, COMPLETED, FAILED, REFUNDED';
COMMENT ON COLUMN hotel_bookings.booking_status IS 'Booking status: PENDING, CONFIRMED, CANCELLED, COMPLETED';
