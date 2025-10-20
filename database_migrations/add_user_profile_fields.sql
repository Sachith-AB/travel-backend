-- Migration: Add profile fields to users table
-- Description: Adds phone_number, address, city, country, and profile_picture columns to support user profile management
-- Date: 2025-10-20

-- Add phone_number column
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS phone_number VARCHAR(20);

-- Add address column
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS address VARCHAR(255);

-- Add city column
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS city VARCHAR(100);

-- Add country column
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS country VARCHAR(100);

-- Add profile_picture column to store base64 or URL
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS profile_picture TEXT;

-- Add index on doc_id for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_doc_id ON users(doc_id);

-- Add index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Verify the changes
SELECT column_name, data_type, character_maximum_length 
FROM information_schema.columns 
WHERE table_name = 'users'
ORDER BY ordinal_position;
