-- Vehicle Bookings Table
-- Stores all vehicle rental bookings with payment information

CREATE TABLE IF NOT EXISTS vehicle_bookings (
    id BIGSERIAL PRIMARY KEY,
    
    -- References
    vehicle_id BIGINT NOT NULL REFERENCES vehicles(id) ON DELETE RESTRICT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    
    -- Booking Details
    pickup_date TIMESTAMP NOT NULL,
    pickup_time TIME NOT NULL,
    return_date TIMESTAMP NOT NULL,
    return_time TIME NOT NULL,
    pickup_location VARCHAR(255) NOT NULL,
    dropoff_location VARCHAR(255) NOT NULL,
    
    -- Driver Options
    with_driver BOOLEAN DEFAULT FALSE,
    driver_license_number VARCHAR(100),
    license_expiry_date DATE,
    
    -- Pricing
    base_price DECIMAL(10, 2) NOT NULL,
    driver_fee DECIMAL(10, 2) DEFAULT 0,
    total_cost DECIMAL(10, 2) NOT NULL,
    number_of_days INTEGER NOT NULL,
    
    -- Payment Information
    payment_intent_id VARCHAR(255),
    payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, PAID, FAILED, REFUNDED
    payment_method VARCHAR(50), -- card, cash
    payment_date TIMESTAMP,
    
    -- Booking Status
    booking_status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, CONFIRMED, CANCELLED, COMPLETED
    
    -- Contact Information
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    
    -- Additional Information
    special_requests TEXT,
    
    -- Metadata
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancelled_at TIMESTAMP,
    cancellation_reason TEXT,
    
    CONSTRAINT valid_dates CHECK (return_date >= pickup_date),
    CONSTRAINT valid_license CHECK (
        (with_driver = TRUE) OR 
        (with_driver = FALSE AND driver_license_number IS NOT NULL AND license_expiry_date IS NOT NULL)
    )
);

-- Indexes for better query performance
CREATE INDEX idx_vehicle_bookings_vehicle_id ON vehicle_bookings(vehicle_id);
CREATE INDEX idx_vehicle_bookings_user_id ON vehicle_bookings(user_id);
CREATE INDEX idx_vehicle_bookings_pickup_date ON vehicle_bookings(pickup_date);
CREATE INDEX idx_vehicle_bookings_booking_status ON vehicle_bookings(booking_status);
CREATE INDEX idx_vehicle_bookings_payment_status ON vehicle_bookings(payment_status);
CREATE INDEX idx_vehicle_bookings_payment_intent ON vehicle_bookings(payment_intent_id);

-- Comments
COMMENT ON TABLE vehicle_bookings IS 'Stores all vehicle rental bookings with payment and contact information';
COMMENT ON COLUMN vehicle_bookings.with_driver IS 'TRUE if customer wants a driver, FALSE for self-drive';
COMMENT ON COLUMN vehicle_bookings.payment_status IS 'Payment status: PENDING, PAID, FAILED, REFUNDED';
COMMENT ON COLUMN vehicle_bookings.booking_status IS 'Booking status: PENDING, CONFIRMED, CANCELLED, COMPLETED';
COMMENT ON COLUMN vehicle_bookings.driver_fee IS 'Additional fee if with_driver is TRUE';

-- Trigger to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_vehicle_bookings_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_vehicle_bookings_updated_at
    BEFORE UPDATE ON vehicle_bookings
    FOR EACH ROW
    EXECUTE FUNCTION update_vehicle_bookings_updated_at();
