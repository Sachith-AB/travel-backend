# CSV Updates Summary - ID Conflict Resolution

## Problem
The database already contains user IDs: **1, 2, 52, 53, 103**

Original CSVs had potential conflicts:
- Hotels CSV used user IDs: 6, 7, 8, 9, 10 (potential conflicts)
- Guide IDs included: 103 (conflicts with existing user ID 103)

## Solution - Updated ID Ranges

### ✅ Users CSV - NO CHANGES NEEDED
**File:** `users_guides_hotels.csv`
- Already uses safe IDs: **200-209**
- No conflicts with existing IDs (1, 2, 52, 53, 103)
- ✅ Ready to use as-is

```csv
200-204: Guide users
205-209: Hotel owner users
```

### ✅ Hotels CSV - UPDATED
**File:** `hotels.csv`
**Changed:** User IDs from 6-10 to 205-209

| Hotel ID | OLD user_id | NEW user_id | Hotel Name |
|----------|-------------|-------------|------------|
| 1 | ~~6~~ | **205** | Cinnamon Grand Colombo |
| 2 | ~~7~~ | **206** | Jetwing Lighthouse |
| 3 | ~~8~~ | **207** | Heritance Tea Factory |
| 4 | ~~9~~ | **208** | The Kingsbury Colombo |
| 5 | ~~10~~ | **209** | Eco Lodge Habarana |

### ✅ Guides CSV - UPDATED
**File:** `guides.csv`
**Changed:** Guide IDs from 100-104 to 110-114 (avoiding conflict with user ID 103)

| OLD guide_id | NEW guide_id | user_id | Specialization |
|--------------|--------------|---------|----------------|
| ~~100~~ | **110** | 200 | Cultural Tours |
| ~~101~~ | **111** | 201 | Wildlife Safaris |
| ~~102~~ | **112** | 202 | Coastal Tours |
| ~~103~~ | **113** | 203 | Hill Country |
| ~~104~~ | **114** | 204 | Ancient Cities |

### ✅ Guide Languages CSV - UPDATED
**File:** `guid_languages.csv`
**Changed:** All guid_id references from 100-104 to 110-114

```
110: English, Sinhala, Tamil, German, French
111: English, Sinhala, Japanese, Korean
112: English, Sinhala, Italian, Spanish
113: English, Sinhala, Tamil, Hindi, Chinese
114: English, Sinhala, Tamil, Arabic, Russian
```

### ✅ Guide Specializations CSV - UPDATED
**File:** `guid_specializations.csv`
**Changed:** All guid_id references from 100-104 to 110-114

```
110: Cultural Tours, Historical Sites, Temple Tours, City Tours
111: Wildlife Safaris, Bird Watching, Nature Trails, Adventure Tours
112: Beach Tours, Water Sports, Coastal Heritage, Snorkeling & Diving
113: Tea Plantation Tours, Hill Country Trekking, Nature Photography, Train Journeys
114: Ancient Cities, UNESCO Sites, Archaeological Tours, Buddhist Heritage, Photography Tours
```

### ✅ Guide Documents CSVs - UPDATED
**Files:** 
- `guid_slta_license_photos.csv`
- `guid_nic_photos.csv`
- `guid_nic_photos_back.csv`

**Changed:** All guid_id references from 100-104 to 110-114

## Files NOT Changed (Already Safe)

### ✅ Profile Pictures
**File:** `profile_pictures.csv`
- Uses user IDs 200-209 ✅
- No changes needed

### ✅ Room Data
**Files:** 
- `rooms.csv`
- `room_images.csv`
- `room_amenities.csv`
- No conflicts ✅
- No changes needed

### ✅ Hotel Images & Amenities
**Files:**
- `hotel_license_photos.csv`
- `hotel_images.csv`
- `hotel_amenities.csv`
- No conflicts ✅
- No changes needed

## Complete ID Map

### Safe ID Ranges (No Conflicts)
```
Users:           200-209  ✅ Safe
Guides:          110-114  ✅ Safe (changed from 100-104)
Hotels:          1-5      ✅ Safe
Rooms:           1-15     ✅ Safe
```

### Existing Database IDs (Reserved)
```
User IDs:        1, 2, 52, 53, 103  ⚠️ Do not use
```

## Verification Checklist

Before importing, verify no conflicts:

```sql
-- 1. Check user IDs (should return 0 rows before import)
SELECT * FROM users WHERE id BETWEEN 200 AND 209;

-- 2. Check guide IDs (should return 0 rows before import)
SELECT * FROM guid WHERE id BETWEEN 110 AND 114;

-- 3. Check for conflicting user ID 103 (should exist)
SELECT * FROM users WHERE id = 103;
```

After importing, verify success:

```sql
-- 1. Should return 10 new users
SELECT COUNT(*) FROM users WHERE id BETWEEN 200 AND 209;

-- 2. Should return 5 new guides
SELECT COUNT(*) FROM guid WHERE id BETWEEN 110 AND 114;

-- 3. Should return 5 hotels owned by users 205-209
SELECT * FROM hotel WHERE user_id BETWEEN 205 AND 209;
```

## Import Commands

### Quick Import (All at once)
```bash
cd /path/to/travel-backend/csv_import
mysql -u root -p travel_db < GUIDES_HOTELS_IMPORT.sql
```

### Manual Import (Step by step)
See `GUIDES_HOTELS_IMPORT_GUIDE.md` for detailed instructions.

## Summary of Changes

| CSV File | Changes Made | Status |
|----------|-------------|--------|
| users_guides_hotels.csv | None (already safe) | ✅ Ready |
| profile_pictures.csv | None (already safe) | ✅ Ready |
| guides.csv | IDs 100-104 → 110-114 | ✅ Updated |
| guid_languages.csv | IDs 100-104 → 110-114 | ✅ Updated |
| guid_specializations.csv | IDs 100-104 → 110-114 | ✅ Updated |
| guid_slta_license_photos.csv | IDs 100-104 → 110-114 | ✅ Updated |
| guid_nic_photos.csv | IDs 100-104 → 110-114 | ✅ Updated |
| guid_nic_photos_back.csv | IDs 100-104 → 110-114 | ✅ Updated |
| hotels.csv | user_id 6-10 → 205-209 | ✅ Updated |
| hotel_license_photos.csv | None needed | ✅ Ready |
| hotel_images.csv | None needed | ✅ Ready |
| hotel_amenities.csv | None needed | ✅ Ready |
| rooms.csv | None needed | ✅ Ready |
| room_images.csv | None needed | ✅ Ready |
| room_amenities.csv | None needed | ✅ Ready |

## All Clear! 🎉

✅ **All CSV files updated**
✅ **No ID conflicts**
✅ **Ready for import**
✅ **Existing data will not be affected**

Import with confidence! 🚀
