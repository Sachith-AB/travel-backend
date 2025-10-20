-- Update guide_bookings table to support multiple guide IDs and tracking approved guide
-- Run this migration to add new columns for complete multi-guide booking functionality

-- Add column to store all selected guide IDs (comma-separated for simplicity, or JSON)
ALTER TABLE guide_bookings ADD COLUMN IF NOT EXISTS selected_guide_ids TEXT;

-- Add column to track which guide actually approved and will fulfill the booking
ALTER TABLE guide_bookings ADD COLUMN IF NOT EXISTS approved_guide_id BIGINT;

-- Add foreign key constraint for approved_guide_id
ALTER TABLE guide_bookings ADD CONSTRAINT fk_approved_guide 
    FOREIGN KEY (approved_guide_id) REFERENCES guides(id) ON DELETE SET NULL;

-- Add comment for clarity
COMMENT ON COLUMN guide_bookings.selected_guide_ids IS 'Comma-separated list of guide IDs that were requested for this booking';
COMMENT ON COLUMN guide_bookings.approved_guide_id IS 'The guide ID that approved and will fulfill this booking';

-- Create index for faster queries on approved_guide_id
CREATE INDEX IF NOT EXISTS idx_guide_bookings_approved_guide ON guide_bookings(approved_guide_id);

-- Update existing records to populate selected_guide_ids from guide_id
UPDATE guide_bookings 
SET selected_guide_ids = guide_id::TEXT 
WHERE selected_guide_ids IS NULL;

-- Note: guide_id column is kept for backward compatibility and represents the primary guide requested
-- When a booking is approved, both guide_id and approved_guide_id will be set to the approving guide
