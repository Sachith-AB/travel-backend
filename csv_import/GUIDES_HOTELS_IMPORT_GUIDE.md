# 📚 Complete Guide & Hotel Data Import Guide

## 🎯 Overview
This guide provides step-by-step instructions to import **5 guides** and **5 hotels** (with 15 rooms total) into your PostgreSQL database.

---

## 📦 What's Included

### Guides Data (5 Guides):
- **Users**: 5 guide user accounts
- **Profile Pictures**: Profile photos for each guide
- **Guides**: Complete guide profiles with bio, experience, rates
- **Languages**: 3-5 languages per guide (23 total language entries)
- **Specializations**: 4-5 specializations per guide (23 total)
- **SLTA License Photos**: License verification documents
- **NIC Photos**: Front and back NIC photos for verification

### Hotels Data (5 Hotels, 15 Rooms):
- **Users**: 5 hotel owner accounts
- **Hotels**: Complete hotel information with descriptions
- **Hotel License Photos**: Registration documents
- **Hotel Images**: 5 images per hotel (25 total)
- **Hotel Amenities**: 10-12 amenities per hotel
- **Rooms**: 3 rooms per hotel (15 total rooms)
- **Room Images**: 3 images per room (45 total)
- **Room Amenities**: 8-12 amenities per room

---

## 📁 CSV Files Created

### User & Profile Files:
1. `users_guides_hotels.csv` - 10 users (5 guides + 5 hotel owners)
2. `profile_pictures.csv` - Profile photos for all users

### Guide Files:
3. `guides.csv` - 5 guide profiles
4. `guid_languages.csv` - 23 language entries
5. `guid_specializations.csv` - 23 specialization entries
6. `guid_slta_license_photos.csv` - 5 license photos
7. `guid_nic_photos.csv` - 5 NIC front photos
8. `guid_nic_photos_back.csv` - 5 NIC back photos

### Hotel Files:
9. `hotels.csv` - 5 hotels
10. `hotel_license_photos.csv` - 5 license photos
11. `hotel_images.csv` - 25 hotel images
12. `hotel_amenities.csv` - Hotel amenities

### Room Files:
13. `rooms.csv` - 15 rooms
14. `room_images.csv` - 45 room images
15. `room_amenities.csv` - Room amenities

---

## 🚀 Quick Import (Recommended)

### Using the SQL Script:

```bash
# Navigate to the csv_import directory
cd /path/to/travel-backend/csv_import

# Run the import script
psql -U your_username -d travel_lk -f GUIDES_HOTELS_IMPORT.sql
```

**Replace:**
- `your_username` with your PostgreSQL username
- `travel_lk` with your database name

---

## 📝 Manual Import (Step by Step)

If you prefer to import manually or need to troubleshoot:

### 1. Navigate to CSV Directory
```bash
cd /path/to/travel-backend/csv_import
```

### 2. Connect to PostgreSQL
```bash
psql -U your_username -d travel_lk
```

### 3. Import Users
```sql
\copy users(id, first_name, last_name, email, role, is_deleted, created_at, doc_id) 
FROM 'users_guides_hotels.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 4. Import Profile Pictures
```sql
\copy profile_pictures(user_id, profile_picture) 
FROM 'profile_pictures.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 5. Import Guides
```sql
\copy guides(id, user_id, bio, experience_years, hours_rate, is_verified, is_available, slta_license_id, slta_license_expiry, nic_number, created_at) 
FROM 'guides.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 6. Import Guide Languages
```sql
\copy guid_languages(guid_id, language) 
FROM 'guid_languages.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 7. Import Guide Specializations
```sql
\copy guid_specializations(guid_id, specialization) 
FROM 'guid_specializations.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 8. Import Guide Documents
```sql
-- SLTA License Photos
\copy guid_slta_license_photos(guid_id, slta_license_photo) 
FROM 'guid_slta_license_photos.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');

-- NIC Front Photos
\copy guid_nic_photos(guid_id, nic_photo_front) 
FROM 'guid_nic_photos.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');

-- NIC Back Photos
\copy guid_nic_photos_back(guid_id, nic_photo_back) 
FROM 'guid_nic_photos_back.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 9. Import Hotels
```sql
\copy hotels(id, user_id, hotel_name, street, city, district, province, registration_no, is_verified, type, description, created_at) 
FROM 'hotels.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 10. Import Hotel Data
```sql
-- Hotel License Photos
\copy hotel_license_photos(hotel_id, license_photo_url) 
FROM 'hotel_license_photos.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');

-- Hotel Images
\copy hotel_images(hotel_id, image_url) 
FROM 'hotel_images.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');

-- Hotel Amenities
\copy hotel_amenities(hotel_id, amenity) 
FROM 'hotel_amenities.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 11. Import Rooms
```sql
\copy rooms(id, hotel_id, room_type, max_guests, bed_types, description, price_per_night, availability, created_at) 
FROM 'rooms.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

### 12. Import Room Data
```sql
-- Room Images
\copy room_images(room_id, image_url) 
FROM 'room_images.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');

-- Room Amenities
\copy room_amenities(room_id, amenity) 
FROM 'room_amenities.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',');
```

---

## ✅ Verification

### Check Import Success:

```sql
-- Count users
SELECT COUNT(*) FROM users WHERE role IN ('GUIDE', 'HOTEL_OWNER');
-- Expected: 10

-- Count guides
SELECT COUNT(*) FROM guides;
-- Expected: 5

-- Count hotels
SELECT COUNT(*) FROM hotels;
-- Expected: 5

-- Count rooms
SELECT COUNT(*) FROM rooms;
-- Expected: 15

-- Check guide languages
SELECT guid_id, COUNT(*) as language_count 
FROM guid_languages 
GROUP BY guid_id 
ORDER BY guid_id;
-- Expected: 5 rows, each with 3-5 languages

-- Check hotel images
SELECT hotel_id, COUNT(*) as image_count 
FROM hotel_images 
GROUP BY hotel_id 
ORDER BY hotel_id;
-- Expected: 5 rows, each with 5 images

-- Check room images
SELECT room_id, COUNT(*) as image_count 
FROM room_images 
GROUP BY room_id 
ORDER BY room_id;
-- Expected: 15 rows, each with 3 images
```

### View Sample Data:

```sql
-- View guides with details
SELECT g.id, u.first_name, u.last_name, g.bio, g.experience_years, g.hours_rate
FROM guides g
JOIN users u ON g.user_id = u.id
ORDER BY g.id;

-- View hotels with room count
SELECT h.id, h.hotel_name, h.city, h.type, COUNT(r.id) as room_count
FROM hotels h
LEFT JOIN rooms r ON h.id = r.hotel_id
GROUP BY h.id, h.hotel_name, h.city, h.type
ORDER BY h.id;

-- View rooms with prices
SELECT r.id, h.hotel_name, r.room_type, r.max_guests, r.price_per_night
FROM rooms r
JOIN hotels h ON r.hotel_id = h.id
ORDER BY h.id, r.id;
```

---

## 📊 Sample Data Summary

### 5 Guides:
1. **Samantha Perera** - Cultural & Historical Tours Expert
   - 8 years experience, LKR 2,500/hour
   - Languages: English, Sinhala, Tamil, German, French
   - Specializations: Cultural, Historical, Temple, City Tours

2. **Rohan Silva** - Adventure & Wildlife Specialist
   - 6 years experience, LKR 2,800/hour
   - Languages: English, Sinhala, Japanese, Korean
   - Specializations: Wildlife Safaris, Bird Watching, Nature Trails

3. **Nimal Fernando** - Coastal & Beach Expert
   - 10 years experience, LKR 3,000/hour
   - Languages: English, Sinhala, Italian, Spanish
   - Specializations: Beach Tours, Water Sports, Coastal Heritage

4. **Priya Jayawardena** - Hill Country Specialist
   - 7 years experience, LKR 2,600/hour
   - Languages: English, Sinhala, Tamil, Hindi, Chinese
   - Specializations: Tea Plantations, Hill Trekking, Nature Photography

5. **Kasun Wickramasinghe** - Ancient Cities Expert
   - 12 years experience, LKR 3,500/hour
   - Languages: English, Sinhala, Tamil, Arabic, Russian
   - Specializations: UNESCO Sites, Archaeology, Buddhist Heritage

### 5 Hotels (15 Rooms Total):

1. **Cinnamon Grand Colombo** (Colombo)
   - 5-Star Hotel
   - 3 rooms: Deluxe (LKR 15k), Executive Suite (LKR 28k), Premium Ocean View (LKR 22k)

2. **Jetwing Lighthouse** (Galle)
   - Beach Resort
   - 3 rooms: Ocean View Double (LKR 18k), Beach Villa (LKR 35k), Honeymoon Suite (LKR 42k)

3. **Heritance Tea Factory** (Nuwara Eliya)
   - Heritage Hotel
   - 3 rooms: Colonial Room (LKR 12k), Tea Planter Suite (LKR 20k), Mountain View Family (LKR 25k)

4. **The Kingsbury Colombo** (Colombo)
   - 5-Star Hotel
   - 3 rooms: Superior City View (LKR 16k), Premium Ocean View (LKR 24k), Presidential Suite (LKR 65k)

5. **Eco Lodge Habarana** (Habarana)
   - Eco Lodge
   - 3 rooms: Eco Cabin (LKR 8k), Family Eco Lodge (LKR 14k), Luxury Tree House (LKR 18k)

---

## 🔧 Troubleshooting

### Error: "No such file or directory"
**Solution**: Make sure you're in the `csv_import` directory when running the import.

### Error: "Permission denied"
**Solution**: Ensure CSV files have read permissions:
```bash
chmod 644 *.csv
```

### Error: "Foreign key constraint violation"
**Solution**: Import files in the correct order (users → guides/hotels → related tables)

### Error: "Duplicate key value"
**Solution**: Check if data already exists. Either delete existing data or use different IDs.

---

## 🧹 Clean Up (Optional)

If you need to remove the imported data:

```sql
-- Delete in reverse order to avoid foreign key violations
DELETE FROM room_amenities WHERE room_id BETWEEN 1 AND 15;
DELETE FROM room_images WHERE room_id BETWEEN 1 AND 15;
DELETE FROM rooms WHERE id BETWEEN 1 AND 15;

DELETE FROM hotel_amenities WHERE hotel_id BETWEEN 1 AND 5;
DELETE FROM hotel_images WHERE hotel_id BETWEEN 1 AND 5;
DELETE FROM hotel_license_photos WHERE hotel_id BETWEEN 1 AND 5;
DELETE FROM hotels WHERE id BETWEEN 1 AND 5;

DELETE FROM guid_nic_photos_back WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_nic_photos WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_slta_license_photos WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_specializations WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_languages WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guides WHERE id BETWEEN 1 AND 5;

DELETE FROM profile_pictures WHERE user_id BETWEEN 1 AND 10;
DELETE FROM users WHERE id BETWEEN 1 AND 10 AND role IN ('GUIDE', 'HOTEL_OWNER');
```

---

## 🎉 Success!

Once imported, you should have:
- ✅ 5 fully functional guides with all details
- ✅ 5 hotels with complete information
- ✅ 15 rooms with images and amenities
- ✅ All data ready for frontend display

---

## 📞 Next Steps

1. **Test Backend API**: Verify `/api/guides` and `/api/hotels` endpoints
2. **Update Frontend**: Ensure frontend fetches from API (similar to vehicles)
3. **Verify Display**: Check that all images and data display correctly

---

*Created: January 2024*  
*Last Updated: January 2024*  
*Status: ✅ Ready for Production*
