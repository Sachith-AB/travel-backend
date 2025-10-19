# Final ID Mapping - No Conflicts ✅

## Overview
All CSV files have been updated to avoid conflicts with existing database records. This document provides the final ID ranges used for all entities.

## Existing Database IDs (DO NOT USE)
Based on the database analysis, the following IDs are already in use:
- **User IDs:** 1, 2, 52, 53, 103
- These IDs are **reserved** and must not be used in new data imports

## New ID Ranges (SAFE TO USE)

### Users
| Role | ID Range | Email Pattern | Count |
|------|----------|---------------|-------|
| Guides | 200-204 | *@travelguide.lk, *@islandguide.lk, etc. | 5 users |
| Hotel Owners | 205-209 | *@luxuryhotels.lk, *@beachresorts.lk, etc. | 5 users |

**Total Users:** 10 (IDs 200-209)

### Guides
| Guide ID | User ID | Specialization | Languages | Rate/Hour |
|----------|---------|----------------|-----------|-----------|
| 110 | 200 | Cultural & Historical Tours | EN, SI, TA, DE, FR | Rs. 2,500 |
| 111 | 201 | Wildlife Safaris & Adventure | EN, SI, JA, KO | Rs. 2,800 |
| 112 | 202 | Coastal & Beach Tours | EN, SI, IT, ES | Rs. 3,000 |
| 113 | 203 | Hill Country & Tea Plantations | EN, SI, TA, HI, ZH | Rs. 2,600 |
| 114 | 204 | Ancient Cities & UNESCO Sites | EN, SI, TA, AR, RU | Rs. 3,500 |

**Total Guides:** 5 (IDs 110-114)

### Hotels
| Hotel ID | User ID (Owner) | Hotel Name | Type | Location |
|----------|-----------------|------------|------|----------|
| 1 | 205 | Cinnamon Grand Colombo | 5-Star Hotel | Colombo |
| 2 | 206 | Jetwing Lighthouse | Beach Resort | Galle |
| 3 | 207 | Heritance Tea Factory | Heritage Hotel | Nuwara Eliya |
| 4 | 208 | The Kingsbury Colombo | 5-Star Hotel | Colombo |
| 5 | 209 | Eco Lodge Habarana | Eco Lodge | Habarana |

**Total Hotels:** 5 (IDs 1-5)
- Each hotel has multiple rooms (15 total rooms across all hotels)
- Each hotel has 3-5 amenities
- Each hotel has 3-5 images

## Relationship Summary

### Guide Relationships
```
User (200-204)
  └── Profile Picture (1 per user)
  └── Guide (110-114)
      ├── Languages (3-5 per guide)
      ├── Specializations (4-5 per guide)
      ├── SLTA License Photo (1 per guide)
      ├── NIC Photo Front (1 per guide)
      └── NIC Photo Back (1 per guide)
```

### Hotel Relationships
```
User (205-209)
  └── Profile Picture (1 per user)
  └── Hotel (1-5)
      ├── License Photo (1 per hotel)
      ├── Hotel Images (3-5 per hotel)
      ├── Hotel Amenities (3-5 per hotel)
      └── Rooms (3 per hotel)
          ├── Room Images (2-3 per room)
          └── Room Amenities (3-4 per room)
```

## CSV Files Ready for Import

### User & Profile Data
1. ✅ `users_guides_hotels.csv` - 10 users (200-209)
2. ✅ `profile_pictures.csv` - 10 profile pictures

### Guide Data (5 guides)
3. ✅ `guides.csv` - 5 guides (110-114)
4. ✅ `guid_languages.csv` - 21 language entries
5. ✅ `guid_specializations.csv` - 21 specialization entries
6. ✅ `guid_slta_license_photos.csv` - 5 license photos
7. ✅ `guid_nic_photos.csv` - 5 NIC front photos
8. ✅ `guid_nic_photos_back.csv` - 5 NIC back photos

### Hotel Data (5 hotels with 15 rooms)
9. ✅ `hotels.csv` - 5 hotels
10. ✅ `hotel_license_photos.csv` - 5 license photos
11. ✅ `hotel_images.csv` - 23 hotel images
12. ✅ `hotel_amenities.csv` - 21 amenities
13. ✅ `rooms.csv` - 15 rooms
14. ✅ `room_images.csv` - 38 room images
15. ✅ `room_amenities.csv` - 53 room amenities

## Import Order
Follow this exact order to maintain referential integrity:

```sql
-- 1. Users first (referenced by guides and hotels)
LOAD DATA LOCAL INFILE 'users_guides_hotels.csv' ...

-- 2. Profile pictures (references users)
LOAD DATA LOCAL INFILE 'profile_pictures.csv' ...

-- 3. Guides (references users)
LOAD DATA LOCAL INFILE 'guides.csv' ...

-- 4. Guide details (reference guides)
LOAD DATA LOCAL INFILE 'guid_languages.csv' ...
LOAD DATA LOCAL INFILE 'guid_specializations.csv' ...
LOAD DATA LOCAL INFILE 'guid_slta_license_photos.csv' ...
LOAD DATA LOCAL INFILE 'guid_nic_photos.csv' ...
LOAD DATA LOCAL INFILE 'guid_nic_photos_back.csv' ...

-- 5. Hotels (references users)
LOAD DATA LOCAL INFILE 'hotels.csv' ...

-- 6. Hotel details (reference hotels)
LOAD DATA LOCAL INFILE 'hotel_license_photos.csv' ...
LOAD DATA LOCAL INFILE 'hotel_images.csv' ...
LOAD DATA LOCAL INFILE 'hotel_amenities.csv' ...

-- 7. Rooms (reference hotels)
LOAD DATA LOCAL INFILE 'rooms.csv' ...

-- 8. Room details (reference rooms)
LOAD DATA LOCAL INFILE 'room_images.csv' ...
LOAD DATA LOCAL INFILE 'room_amenities.csv' ...
```

## Verification Queries

### Check for ID Conflicts
```sql
-- Check if any of our new user IDs conflict with existing users
SELECT id, email, role FROM users WHERE id IN (200,201,202,203,204,205,206,207,208,209);
-- Should return 0 rows BEFORE import, 10 rows AFTER import

-- Check if any of our new guide IDs conflict
SELECT id, user_id FROM guid WHERE id IN (110,111,112,113,114);
-- Should return 0 rows BEFORE import, 5 rows AFTER import
```

### Post-Import Verification
```sql
-- Verify all users imported
SELECT COUNT(*) as total_users, 
       SUM(CASE WHEN role = 'GUIDE' THEN 1 ELSE 0 END) as guides,
       SUM(CASE WHEN role = 'HOTEL_OWNER' THEN 1 ELSE 0 END) as hotel_owners
FROM users WHERE id >= 200;
-- Expected: total_users=10, guides=5, hotel_owners=5

-- Verify all guides imported
SELECT COUNT(*) as total_guides FROM guid WHERE id >= 110;
-- Expected: 5

-- Verify all hotels imported
SELECT COUNT(*) as total_hotels FROM hotel;
-- Expected: 5 (or more if other hotels exist)

-- Verify all rooms imported
SELECT COUNT(*) as total_rooms FROM room;
-- Expected: 15 (or more if other rooms exist)

-- Verify guide languages
SELECT guid_id, COUNT(*) as language_count 
FROM guid_languages 
WHERE guid_id >= 110 
GROUP BY guid_id;
-- Expected: 5 rows, each with 3-5 languages

-- Verify guide specializations
SELECT guid_id, COUNT(*) as specialization_count 
FROM guid_specializations 
WHERE guid_id >= 110 
GROUP BY guid_id;
-- Expected: 5 rows, each with 4-5 specializations

-- Verify hotel amenities
SELECT hotel_id, COUNT(*) as amenity_count 
FROM hotel_amenities 
GROUP BY hotel_id;
-- Expected: 5 rows, each with 3-5 amenities

-- Verify room amenities
SELECT room_id, COUNT(*) as amenity_count 
FROM room_amenities 
GROUP BY room_id;
-- Expected: 15 rows, each with 3-4 amenities
```

## Data Statistics

### Guides
- **Total Guides:** 5
- **Total Languages:** 21 (average 4.2 per guide)
- **Total Specializations:** 21 (average 4.2 per guide)
- **Total Documents:** 15 (3 per guide: SLTA license, NIC front, NIC back)
- **Verification Status:** All guides are verified
- **Availability:** All guides are available

### Hotels
- **Total Hotels:** 5
- **Total Rooms:** 15 (average 3 per hotel)
- **Total Hotel Images:** 23 (average 4.6 per hotel)
- **Total Room Images:** 38 (average 2.5 per room)
- **Total Hotel Amenities:** 21 (average 4.2 per hotel)
- **Total Room Amenities:** 53 (average 3.5 per room)
- **Verification Status:** All hotels are verified

## Quick Import Script

Use the provided SQL script for quick import:
```bash
cd /path/to/travel-backend/csv_import
mysql -u root -p travel_db < GUIDES_HOTELS_IMPORT.sql
```

Or use the step-by-step guide in `GUIDES_HOTELS_IMPORT_GUIDE.md`

## Troubleshooting

### If You See "Duplicate entry" Errors
This means the IDs are already in use. Check existing data:
```sql
SELECT MAX(id) FROM users;
SELECT MAX(id) FROM guid;
```

If you need to adjust IDs, update all related CSVs accordingly.

### If Foreign Key Constraints Fail
Ensure import order is correct:
1. Users must be imported before guides and hotels
2. Guides must be imported before guide details
3. Hotels must be imported before rooms
4. Rooms must be imported before room details

## Support

For detailed instructions, see:
- `GUIDES_HOTELS_IMPORT_GUIDE.md` - Complete step-by-step import guide
- `README_COMPLETE.md` - Full documentation
- `DATABASE_STRUCTURE_VISUAL_GUIDE.md` - Visual database structure

---

✅ **All ID conflicts resolved!**
✅ **All CSVs updated and ready for import!**
✅ **No existing data will be affected!**
