-- Script to clean existing data and import fresh data from travel.sql dump
-- This will clear all existing data and constraints to allow fresh import

-- Disable foreign key checks temporarily
SET session_replication_role = replica;

-- Truncate all tables in the correct order to avoid foreign key constraints
TRUNCATE TABLE trip_selected_rooms CASCADE;
TRUNCATE TABLE trip_selected_night_rooms CASCADE;
TRUNCATE TABLE trip_selected_night_hotels CASCADE;
TRUNCATE TABLE trip_selected_hotels CASCADE;
TRUNCATE TABLE trip_selected_guides CASCADE;
TRUNCATE TABLE trip_places CASCADE;
TRUNCATE TABLE trip_interests CASCADE;
TRUNCATE TABLE guid_requests CASCADE;
TRUNCATE TABLE comments CASCADE;
TRUNCATE TABLE trips CASCADE;
TRUNCATE TABLE room_images CASCADE;
TRUNCATE TABLE room_amenities CASCADE;
TRUNCATE TABLE rooms CASCADE;
TRUNCATE TABLE hotel_images CASCADE;
TRUNCATE TABLE hotel_amenities CASCADE;
TRUNCATE TABLE hotel_license_photos CASCADE;
TRUNCATE TABLE hotels CASCADE;
TRUNCATE TABLE vehicle_images CASCADE;
TRUNCATE TABLE vehicle_amenities CASCADE;
TRUNCATE TABLE vehicles CASCADE;
TRUNCATE TABLE drivers CASCADE;
TRUNCATE TABLE agency_license_photos CASCADE;
TRUNCATE TABLE vehicle_agencies CASCADE;
TRUNCATE TABLE guid_slta_license_photos CASCADE;
TRUNCATE TABLE guid_specializations CASCADE;
TRUNCATE TABLE guid_nic_photos_back CASCADE;
TRUNCATE TABLE guid_nic_photos CASCADE;
TRUNCATE TABLE guid_languages CASCADE;
TRUNCATE TABLE guides CASCADE;
TRUNCATE TABLE profile_pictures CASCADE;
TRUNCATE TABLE users CASCADE;

-- Reset sequences
ALTER SEQUENCE comments_id_seq RESTART WITH 1;
ALTER SEQUENCE drivers_seq RESTART WITH 1;
ALTER SEQUENCE guid_requests_id_seq RESTART WITH 1;
ALTER SEQUENCE guides_seq RESTART WITH 1;
ALTER SEQUENCE hotels_seq RESTART WITH 1;
ALTER SEQUENCE rooms_seq RESTART WITH 1;
ALTER SEQUENCE trips_seq RESTART WITH 1;
ALTER SEQUENCE users_seq RESTART WITH 1;
ALTER SEQUENCE vehicle_agencies_seq RESTART WITH 1;
ALTER SEQUENCE vehicles_seq RESTART WITH 1;

-- Re-enable foreign key checks
SET session_replication_role = DEFAULT;

-- The data will be imported separately using pg_restore
