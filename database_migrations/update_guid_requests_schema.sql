-- Update guid_requests table to support both tour and individual guide bookings
-- This allows tracking guide requests from both tour packages and standalone guide bookings

-- Add booking_type column to differentiate between tour and individual bookings
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS booking_type VARCHAR(20) DEFAULT 'TOUR';

-- Add reference to guide_booking for standalone guide bookings
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS guide_booking_id BIGINT;

-- Add foreign key constraint
ALTER TABLE guid_requests ADD CONSTRAINT fk_guide_booking 
    FOREIGN KEY (guide_booking_id) REFERENCES guide_bookings(id) ON DELETE CASCADE;

-- Add columns to store booking details (useful for both types)
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS start_date DATE;
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS end_date DATE;
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS number_of_days INTEGER;
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS number_of_people INTEGER;
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS locations TEXT;
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS total_price NUMERIC(10,2);

-- Add multi_request_id to group related requests
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS multi_request_id VARCHAR(255);

-- Add rejection reason
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS rejection_reason TEXT;

-- Add comments for clarity
COMMENT ON COLUMN guid_requests.booking_type IS 'Type of booking: TOUR (from trip) or INDIVIDUAL (standalone guide booking)';
COMMENT ON COLUMN guid_requests.guide_booking_id IS 'Reference to guide_bookings table for individual bookings (NULL for tour bookings)';
COMMENT ON COLUMN guid_requests.multi_request_id IS 'Groups multiple guide requests from the same booking (for multi-guide requests)';
COMMENT ON COLUMN guid_requests.start_date IS 'Start date of the booking';
COMMENT ON COLUMN guid_requests.end_date IS 'End date of the booking';
COMMENT ON COLUMN guid_requests.number_of_days IS 'Duration of booking in days';
COMMENT ON COLUMN guid_requests.number_of_people IS 'Number of people in the booking';
COMMENT ON COLUMN guid_requests.locations IS 'Locations to be visited';
COMMENT ON COLUMN guid_requests.total_price IS 'Total price for the guide service';

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_guid_requests_booking_type ON guid_requests(booking_type);
CREATE INDEX IF NOT EXISTS idx_guid_requests_guide_booking_id ON guid_requests(guide_booking_id);
CREATE INDEX IF NOT EXISTS idx_guid_requests_multi_request_id ON guid_requests(multi_request_id);

-- Update existing records to set booking_type to TOUR
UPDATE guid_requests 
SET booking_type = 'TOUR' 
WHERE booking_type IS NULL AND trip_id IS NOT NULL;

UPDATE guid_requests 
SET booking_type = 'INDIVIDUAL' 
WHERE booking_type IS NULL AND trip_id IS NULL;

-- Note: For tour bookings, trip_id will be populated and guide_booking_id will be NULL
-- For individual bookings, guide_booking_id will be populated and trip_id will be NULL
-- The status field continues to track: PENDING, APPROVED, REJECTED, CANCELLED
