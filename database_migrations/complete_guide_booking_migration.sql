-- ============================================================================
-- COMPLETE GUIDE BOOKING SCHEMA UPDATE
-- Run this script in Supabase SQL Editor to apply all changes
-- ============================================================================

-- ============================================================================
-- PART 1: Update guide_bookings table
-- ============================================================================

-- Add column to store all selected guide IDs (comma-separated)
ALTER TABLE guide_bookings ADD COLUMN IF NOT EXISTS selected_guide_ids TEXT;

-- Add column to track which guide actually approved and will fulfill the booking
ALTER TABLE guide_bookings ADD COLUMN IF NOT EXISTS approved_guide_id BIGINT;

-- Add foreign key constraint for approved_guide_id
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE constraint_name = 'fk_approved_guide' 
        AND table_name = 'guide_bookings'
    ) THEN
        ALTER TABLE guide_bookings ADD CONSTRAINT fk_approved_guide 
            FOREIGN KEY (approved_guide_id) REFERENCES guides(id) ON DELETE SET NULL;
    END IF;
END $$;

-- Add comments for clarity
COMMENT ON COLUMN guide_bookings.selected_guide_ids IS 'Comma-separated list of guide IDs that were requested for this booking';
COMMENT ON COLUMN guide_bookings.approved_guide_id IS 'The guide ID that approved and will fulfill this booking';

-- Create index for faster queries on approved_guide_id
CREATE INDEX IF NOT EXISTS idx_guide_bookings_approved_guide ON guide_bookings(approved_guide_id);

-- Update existing records to populate selected_guide_ids from guide_id
UPDATE guide_bookings 
SET selected_guide_ids = guide_id::TEXT 
WHERE selected_guide_ids IS NULL AND guide_id IS NOT NULL;

-- ============================================================================
-- PART 2: Update guid_requests table
-- ============================================================================

-- Add booking_type column to differentiate between tour and individual bookings
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS booking_type VARCHAR(20) DEFAULT 'TOUR';

-- Add reference to guide_booking for standalone guide bookings
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS guide_booking_id BIGINT;

-- Add foreign key constraint
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE constraint_name = 'fk_guide_booking' 
        AND table_name = 'guid_requests'
    ) THEN
        ALTER TABLE guid_requests ADD CONSTRAINT fk_guide_booking 
            FOREIGN KEY (guide_booking_id) REFERENCES guide_bookings(id) ON DELETE CASCADE;
    END IF;
END $$;

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

-- Update existing records to set booking_type to TOUR where trip_id exists
UPDATE guid_requests 
SET booking_type = 'TOUR' 
WHERE booking_type IS NULL AND trip_id IS NOT NULL;

-- Update existing records to set booking_type to INDIVIDUAL where trip_id is NULL
UPDATE guid_requests 
SET booking_type = 'INDIVIDUAL' 
WHERE booking_type IS NULL AND trip_id IS NULL;

-- ============================================================================
-- VERIFICATION QUERIES
-- ============================================================================

-- Verify guide_bookings updates
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'guide_bookings'
    AND column_name IN ('selected_guide_ids', 'approved_guide_id')
ORDER BY ordinal_position;

-- Verify guid_requests updates
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'guid_requests'
    AND column_name IN (
        'booking_type', 'guide_booking_id', 'start_date', 'end_date',
        'number_of_days', 'number_of_people', 'locations', 'total_price',
        'multi_request_id', 'rejection_reason'
    )
ORDER BY ordinal_position;

-- Verify indexes
SELECT 
    tablename, 
    indexname, 
    indexdef
FROM pg_indexes
WHERE tablename IN ('guide_bookings', 'guid_requests')
    AND indexname IN (
        'idx_guide_bookings_approved_guide',
        'idx_guid_requests_booking_type',
        'idx_guid_requests_guide_booking_id',
        'idx_guid_requests_multi_request_id'
    )
ORDER BY tablename, indexname;

-- Verify foreign keys
SELECT
    tc.table_name, 
    tc.constraint_name, 
    tc.constraint_type,
    kcu.column_name,
    ccu.table_name AS foreign_table_name,
    ccu.column_name AS foreign_column_name
FROM information_schema.table_constraints AS tc
JOIN information_schema.key_column_usage AS kcu
    ON tc.constraint_name = kcu.constraint_name
JOIN information_schema.constraint_column_usage AS ccu
    ON ccu.constraint_name = tc.constraint_name
WHERE tc.constraint_type = 'FOREIGN KEY'
    AND tc.table_name IN ('guide_bookings', 'guid_requests')
    AND tc.constraint_name IN ('fk_approved_guide', 'fk_guide_booking')
ORDER BY tc.table_name;

-- ============================================================================
-- NOTES
-- ============================================================================
-- 
-- For tour bookings:
--   - trip_id will be populated
--   - guide_booking_id will be NULL
--   - booking_type = 'TOUR'
-- 
-- For individual guide bookings:
--   - guide_booking_id will be populated
--   - trip_id will be NULL
--   - booking_type = 'INDIVIDUAL'
-- 
-- Multi-guide bookings:
--   - Multiple bookings with same multi_request_id
--   - Multiple guid_requests with same multi_request_id
--   - When one guide approves, others are auto-cancelled
--   - approved_guide_id identifies which guide fulfilled the booking
-- 
-- Status flow:
--   PENDING → APPROVED → PAID → COMPLETED
--   PENDING → REJECTED
--   PENDING/APPROVED → CANCELLED
-- 
-- ============================================================================

-- Migration completed successfully!
SELECT 'Migration completed! Please review verification queries above.' AS status;
