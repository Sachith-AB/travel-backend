-- Add Stripe Payment Intent ID to Trips Table
-- This migration adds a column to store Stripe payment intent IDs for trip payments
-- Run this migration before testing the Stripe payment integration

-- Add stripe_payment_intent_id column to trips table
ALTER TABLE trips
ADD COLUMN stripe_payment_intent_id VARCHAR(255);

-- Add index for faster lookups by payment intent ID
CREATE INDEX idx_trips_stripe_payment_intent_id ON trips(stripe_payment_intent_id);

-- Verify the column was added
SELECT column_name, data_type, character_maximum_length 
FROM information_schema.columns 
WHERE table_name = 'trips' AND column_name = 'stripe_payment_intent_id';
