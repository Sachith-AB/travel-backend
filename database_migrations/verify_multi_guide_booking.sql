-- ============================================================================
-- VERIFICATION SCRIPT FOR MULTI-GUIDE BOOKING SYSTEM
-- Run this in Supabase SQL Editor to verify schema is correct
-- ============================================================================

-- Check guide_bookings table structure
SELECT 
    'guide_bookings columns' as check_type,
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'guide_bookings'
    AND column_name IN ('selected_guide_ids', 'approved_guide_id', 'multi_request_id')
ORDER BY ordinal_position;

-- Check guid_requests table structure
SELECT 
    'guid_requests columns' as check_type,
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

-- Check indexes
SELECT 
    'indexes' as check_type,
    tablename, 
    indexname, 
    indexdef
FROM pg_indexes
WHERE tablename IN ('guide_bookings', 'guid_requests')
    AND (
        indexname LIKE '%approved_guide%' OR
        indexname LIKE '%booking_type%' OR
        indexname LIKE '%guide_booking_id%' OR
        indexname LIKE '%multi_request%'
    )
ORDER BY tablename, indexname;

-- Check foreign keys
SELECT
    'foreign_keys' as check_type,
    tc.table_name, 
    tc.constraint_name, 
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
    AND (
        tc.constraint_name LIKE '%approved_guide%' OR
        tc.constraint_name LIKE '%guide_booking%'
    )
ORDER BY tc.table_name;

-- Sample query: Get all multi-guide bookings grouped
SELECT 
    'sample_grouped_bookings' as check_type,
    gb.multi_request_id,
    gb.user_id,
    gb.guide_id,
    gb.selected_guide_ids,
    gb.approved_guide_id,
    gb.status,
    gb.locations,
    gb.start_date,
    gb.end_date,
    gb.created_at
FROM guide_bookings gb
WHERE gb.multi_request_id IS NOT NULL
ORDER BY gb.multi_request_id, gb.created_at DESC
LIMIT 10;

-- Sample query: Get guid_requests for individual bookings
SELECT 
    'sample_guid_requests' as check_type,
    gr.id,
    gr.user_id,
    gr.guid_id,
    gr.guide_booking_id,
    gr.booking_type,
    gr.multi_request_id,
    gr.status,
    gr.start_date,
    gr.end_date,
    gr.locations
FROM guid_requests gr
WHERE gr.booking_type = 'INDIVIDUAL'
ORDER BY gr.created_at DESC
LIMIT 10;

-- Check for any bookings without corresponding guid_requests
SELECT 
    'orphaned_bookings' as check_type,
    gb.id as booking_id,
    gb.guide_id,
    gb.status,
    gb.multi_request_id,
    gb.created_at,
    'No corresponding guid_request found' as issue
FROM guide_bookings gb
LEFT JOIN guid_requests gr ON gb.id = gr.guide_booking_id
WHERE gr.id IS NULL
    AND gb.created_at > NOW() - INTERVAL '7 days'  -- Only check recent bookings
LIMIT 10;

-- Summary statistics
SELECT 
    'booking_statistics' as check_type,
    COUNT(*) as total_bookings,
    COUNT(DISTINCT multi_request_id) as multi_request_groups,
    COUNT(DISTINCT CASE WHEN multi_request_id IS NOT NULL THEN multi_request_id END) as multi_guide_bookings,
    COUNT(CASE WHEN status = 'PENDING' THEN 1 END) as pending,
    COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) as approved,
    COUNT(CASE WHEN status = 'PAID' THEN 1 END) as paid,
    COUNT(CASE WHEN status = 'COMPLETED' THEN 1 END) as completed,
    COUNT(CASE WHEN status = 'CANCELLED' THEN 1 END) as cancelled,
    COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) as rejected
FROM guide_bookings
WHERE created_at > NOW() - INTERVAL '30 days';

-- ============================================================================
-- RESULTS INTERPRETATION
-- ============================================================================
-- 
-- If columns are missing:
--   → Run complete_guide_booking_migration.sql
-- 
-- If indexes are missing:
--   → They will be auto-created by migration script
-- 
-- If foreign keys are missing:
--   → Check if tables exist and have proper IDs
-- 
-- If orphaned_bookings shows results:
--   → Backend might not be creating guid_requests properly
--   → Check GuideBookingService.createGuidRequest() method
-- 
-- ============================================================================

SELECT 'Verification complete!' as status, NOW() as timestamp;
