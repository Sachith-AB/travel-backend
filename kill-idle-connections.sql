-- SQL Script to Kill Idle Connections in Supabase
-- Run this in the Supabase SQL Editor

-- Step 1: View all current connections
SELECT 
    pid,
    usename,
    application_name,
    client_addr,
    state,
    state_change,
    query_start,
    NOW() - query_start AS query_duration,
    query
FROM pg_stat_activity
WHERE datname = 'postgres'
ORDER BY state_change DESC;

-- Step 2: Count connections by state
SELECT 
    state,
    COUNT(*) as connection_count
FROM pg_stat_activity
WHERE datname = 'postgres'
GROUP BY state;

-- Step 3: Kill all IDLE connections (except your current session)
-- IMPORTANT: Review the output from Step 1 before running this!
SELECT 
    pg_terminate_backend(pid),
    'Terminated PID: ' || pid || ' - ' || usename || ' - ' || state AS action
FROM pg_stat_activity
WHERE datname = 'postgres'
    AND pid <> pg_backend_pid()  -- Don't kill your own connection
    AND state = 'idle'           -- Only kill idle connections
    AND state_change < NOW() - INTERVAL '5 minutes';  -- Idle for more than 5 minutes

-- Step 4: (Optional) Kill ALL connections except your own - USE WITH EXTREME CAUTION!
-- Uncomment only if you need to force-close everything
-- SELECT 
--     pg_terminate_backend(pid),
--     'Terminated PID: ' || pid AS action
-- FROM pg_stat_activity
-- WHERE datname = 'postgres'
--     AND pid <> pg_backend_pid();

-- Step 5: Verify connections are cleared
SELECT COUNT(*) as remaining_connections
FROM pg_stat_activity
WHERE datname = 'postgres';
