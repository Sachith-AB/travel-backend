-- Add traveler information and request date to guid_requests table
-- This allows tracking who made the request and when

-- Add traveler name column
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS traveler_name VARCHAR(255);

-- Add traveler email column
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS traveler_email VARCHAR(255);

-- Add request date column
ALTER TABLE guid_requests ADD COLUMN IF NOT EXISTS request_date TIMESTAMP;

-- Add comments for clarity
COMMENT ON COLUMN guid_requests.traveler_name IS 'Full name of the traveler/tourist making the guide request';
COMMENT ON COLUMN guid_requests.traveler_email IS 'Email address of the traveler/tourist for communication';
COMMENT ON COLUMN guid_requests.request_date IS 'Date and time when the guide request was created';

-- Create index for email lookups
CREATE INDEX IF NOT EXISTS idx_guid_requests_traveler_email ON guid_requests(traveler_email);

-- Create index for request date (for sorting and filtering)
CREATE INDEX IF NOT EXISTS idx_guid_requests_request_date ON guid_requests(request_date);
