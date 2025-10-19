# 🎯 QUICK REFERENCE - Safe IDs for Import

## Existing Database (DO NOT USE ⚠️)
```
User IDs: 1, 2, 52, 53, 103
```

## New Safe IDs (✅ READY TO USE)

### Users (10 total)
```
200-204  →  Guide Users
205-209  →  Hotel Owner Users
```

### Guides (5 total)
```
110  →  User 200  →  Cultural & Historical Tours
111  →  User 201  →  Wildlife Safaris & Adventure
112  →  User 202  →  Coastal & Beach Tours
113  →  User 203  →  Hill Country & Tea Plantations
114  →  User 204  →  Ancient Cities & UNESCO Sites
```

### Hotels (5 total)
```
1  →  User 205  →  Cinnamon Grand Colombo
2  →  User 206  →  Jetwing Lighthouse
3  →  User 207  →  Heritance Tea Factory
4  →  User 208  →  The Kingsbury Colombo
5  →  User 209  →  Eco Lodge Habarana
```

### Rooms (15 total)
```
1-3    →  Hotel 1 (Cinnamon Grand)
4-6    →  Hotel 2 (Jetwing Lighthouse)
7-9    →  Hotel 3 (Heritance Tea Factory)
10-12  →  Hotel 4 (The Kingsbury)
13-15  →  Hotel 5 (Eco Lodge Habarana)
```

## Changes Made

### ✅ Updated
```
guides.csv                     →  IDs 100-104 → 110-114
guid_languages.csv             →  IDs 100-104 → 110-114
guid_specializations.csv       →  IDs 100-104 → 110-114
guid_slta_license_photos.csv   →  IDs 100-104 → 110-114
guid_nic_photos.csv            →  IDs 100-104 → 110-114
guid_nic_photos_back.csv       →  IDs 100-104 → 110-114
hotels.csv                     →  user_id 6-10 → 205-209
```

### ✅ Already Safe (No Changes)
```
users_guides_hotels.csv
profile_pictures.csv
hotel_license_photos.csv
hotel_images.csv
hotel_amenities.csv
rooms.csv
room_images.csv
room_amenities.csv
```

## Quick Import
```bash
cd /path/to/travel-backend/csv_import
mysql -u root -p travel_db < GUIDES_HOTELS_IMPORT.sql
```

## Verify Import
```sql
-- Check users (expect 10)
SELECT COUNT(*) FROM users WHERE id BETWEEN 200 AND 209;

-- Check guides (expect 5)
SELECT COUNT(*) FROM guid WHERE id BETWEEN 110 AND 114;

-- Check hotels (expect 5)
SELECT COUNT(*) FROM hotel WHERE user_id BETWEEN 205 AND 209;
```

## 🎉 Status: ALL CLEAR - NO CONFLICTS!
