-- ============================================
-- GUIDES AND HOTELS CSV IMPORT SCRIPT
-- ============================================
-- This script imports 5 guides with all related data
-- and 5 hotels with 15 rooms (3 rooms per hotel)
-- ============================================

-- IMPORTANT: Run this script from the csv_import directory
-- Usage: psql -U your_username -d your_database -f GUIDES_HOTELS_IMPORT.sql

\echo '🚀 Starting import of guides and hotels data...'
\echo ''

-- ============================================
-- STEP 1: Import Users (Guide and Hotel Owners)
-- ============================================
\echo '📥 Step 1: Importing users (guides and hotel owners)...'

\copy users(id, first_name, last_name, email, role, is_deleted, created_at, doc_id) FROM 'users_guides_hotels.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Users imported successfully'
\echo ''

-- ============================================
-- STEP 2: Import Profile Pictures
-- ============================================
\echo '📥 Step 2: Importing profile pictures...'

\copy profile_pictures(user_id, profile_picture) FROM 'profile_pictures.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Profile pictures imported successfully'
\echo ''

-- ============================================
-- STEP 3: Import Guides
-- ============================================
\echo '📥 Step 3: Importing guides...'

\copy guides(id, user_id, bio, experience_years, hours_rate, is_verified, is_available, slta_license_id, slta_license_expiry, nic_number, created_at) FROM 'guides.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Guides imported successfully'
\echo ''

-- ============================================
-- STEP 4: Import Guide Languages
-- ============================================
\echo '📥 Step 4: Importing guide languages...'

\copy guid_languages(guid_id, language) FROM 'guid_languages.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Guide languages imported successfully'
\echo ''

-- ============================================
-- STEP 5: Import Guide Specializations
-- ============================================
\echo '📥 Step 5: Importing guide specializations...'

\copy guid_specializations(guid_id, specialization) FROM 'guid_specializations.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Guide specializations imported successfully'
\echo ''

-- ============================================
-- STEP 6: Import Guide SLTA License Photos
-- ============================================
\echo '📥 Step 6: Importing guide SLTA license photos...'

\copy guid_slta_license_photos(guid_id, slta_license_photo) FROM 'guid_slta_license_photos.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Guide SLTA license photos imported successfully'
\echo ''

-- ============================================
-- STEP 7: Import Guide NIC Photos (Front)
-- ============================================
\echo '📥 Step 7: Importing guide NIC photos (front)...'

\copy guid_nic_photos(guid_id, nic_photo_front) FROM 'guid_nic_photos.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Guide NIC photos (front) imported successfully'
\echo ''

-- ============================================
-- STEP 8: Import Guide NIC Photos (Back)
-- ============================================
\echo '📥 Step 8: Importing guide NIC photos (back)...'

\copy guid_nic_photos_back(guid_id, nic_photo_back) FROM 'guid_nic_photos_back.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Guide NIC photos (back) imported successfully'
\echo ''

-- ============================================
-- STEP 9: Import Hotels
-- ============================================
\echo '📥 Step 9: Importing hotels...'

\copy hotels(id, user_id, hotel_name, street, city, district, province, registration_no, is_verified, type, description, created_at) FROM 'hotels.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Hotels imported successfully'
\echo ''

-- ============================================
-- STEP 10: Import Hotel License Photos
-- ============================================
\echo '📥 Step 10: Importing hotel license photos...'

\copy hotel_license_photos(hotel_id, license_photo_url) FROM 'hotel_license_photos.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Hotel license photos imported successfully'
\echo ''

-- ============================================
-- STEP 11: Import Hotel Images
-- ============================================
\echo '📥 Step 11: Importing hotel images...'

\copy hotel_images(hotel_id, image_url) FROM 'hotel_images.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Hotel images imported successfully'
\echo ''

-- ============================================
-- STEP 12: Import Hotel Amenities
-- ============================================
\echo '📥 Step 12: Importing hotel amenities...'

\copy hotel_amenities(hotel_id, amenity) FROM 'hotel_amenities.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Hotel amenities imported successfully'
\echo ''

-- ============================================
-- STEP 13: Import Rooms
-- ============================================
\echo '📥 Step 13: Importing rooms...'

\copy rooms(id, hotel_id, room_type, max_guests, bed_types, description, price_per_night, availability, created_at) FROM 'rooms.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Rooms imported successfully'
\echo ''

-- ============================================
-- STEP 14: Import Room Images
-- ============================================
\echo '📥 Step 14: Importing room images...'

\copy room_images(room_id, image_url) FROM 'room_images.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Room images imported successfully'
\echo ''

-- ============================================
-- STEP 15: Import Room Amenities
-- ============================================
\echo '📥 Step 15: Importing room amenities...'

\copy room_amenities(room_id, amenity) FROM 'room_amenities.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');

\echo '✅ Room amenities imported successfully'
\echo ''

-- ============================================
-- VERIFICATION QUERIES
-- ============================================
\echo '📊 Data Import Summary:'
\echo ''

\echo '👥 Users (Guides + Hotel Owners):'
SELECT COUNT(*) AS total_users FROM users WHERE role IN ('GUIDE', 'HOTEL_OWNER');

\echo ''
\echo '🗣️ Guides:'
SELECT COUNT(*) AS total_guides FROM guides;

\echo ''
\echo '🏨 Hotels:'
SELECT COUNT(*) AS total_hotels FROM hotels;

\echo ''
\echo '🛏️ Rooms:'
SELECT COUNT(*) AS total_rooms FROM rooms;

\echo ''
\echo '🖼️ Guide Languages:'
SELECT guid_id, COUNT(*) as language_count FROM guid_languages GROUP BY guid_id ORDER BY guid_id;

\echo ''
\echo '🏷️ Guide Specializations:'
SELECT guid_id, COUNT(*) as specialization_count FROM guid_specializations GROUP BY guid_id ORDER BY guid_id;

\echo ''
\echo '🖼️ Hotel Images:'
SELECT hotel_id, COUNT(*) as image_count FROM hotel_images GROUP BY hotel_id ORDER BY hotel_id;

\echo ''
\echo '🛎️ Hotel Amenities:'
SELECT hotel_id, COUNT(*) as amenity_count FROM hotel_amenities GROUP BY hotel_id ORDER BY hotel_id;

\echo ''
\echo '🖼️ Room Images:'
SELECT room_id, COUNT(*) as image_count FROM room_images GROUP BY room_id ORDER BY room_id;

\echo ''
\echo '🛎️ Room Amenities:'
SELECT room_id, COUNT(*) as amenity_count FROM room_amenities GROUP BY room_id ORDER BY room_id;

\echo ''
\echo '✅ Import completed successfully!'
\echo ''
\echo '📋 Summary:'
\echo '  - 10 users imported (5 guides + 5 hotel owners)'
\echo '  - 5 guides with languages, specializations, and documents'
\echo '  - 5 hotels with images and amenities'
\echo '  - 15 rooms with images and amenities'
\echo ''
\echo '🎉 All data is now ready for use!'
