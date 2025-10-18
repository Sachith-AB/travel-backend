# 📊 Database Structure & Relationships - Visual Guide

## 🎯 Complete Data Model Overview

This document visualizes the database structure and relationships for all entities.

---

## 🚗 VEHICLE DATA MODEL

```
vehicle_agencies (3 records)
    │
    ├── id (PK)
    ├── agency_name
    ├── contact_email
    ├── phone
    ├── address
    ├── city
    ├── license_number
    └── license_expiry_date
        │
        └──┬── agency_license_photos (3 records)
           │   ├── agency_id (FK)
           │   └── photo_url
           │
           └── vehicles (5 records)
               ├── id (PK)
               ├── agency_id (FK) ───┐
               ├── vehicle_model      │
               ├── vehicle_type       │
               ├── base_price         │
               ├── price_per_kilometer│
               └── capacity           │
                   │                  │
                   ├── vehicle_images (25 records)
                   │   ├── vehicle_id (FK)
                   │   └── image_url
                   │
                   └── vehicle_amenities (30 records)
                       ├── vehicle_id (FK)
                       └── amenity_name
```

---

## 🗣️ GUIDE DATA MODEL

```
users (5 guide users)
    │
    ├── id (PK)
    ├── first_name
    ├── last_name
    ├── email
    ├── role = 'GUIDE'
    └── created_at
        │
        ├──┬── profile_pictures (5 records)
        │  │   ├── user_id (FK)
        │  │   └── profile_picture
        │  │
        └──┴── guides (5 records)
               ├── id (PK)
               ├── user_id (FK) ───────┐
               ├── bio                 │
               ├── experience_years    │
               ├── hours_rate          │
               ├── is_verified         │
               ├── slta_license_id     │
               └── nic_number          │
                   │                   │
                   ├── guid_languages (23 records)
                   │   ├── guid_id (FK)
                   │   └── language
                   │
                   ├── guid_specializations (21 records)
                   │   ├── guid_id (FK)
                   │   └── specialization
                   │
                   ├── guid_slta_license_photos (5 records)
                   │   ├── guid_id (FK)
                   │   └── slta_license_photo
                   │
                   ├── guid_nic_photos (5 records)
                   │   ├── guid_id (FK)
                   │   └── nic_photo_front
                   │
                   └── guid_nic_photos_back (5 records)
                       ├── guid_id (FK)
                       └── nic_photo_back
```

---

## 🏨 HOTEL & ROOM DATA MODEL

```
users (5 hotel owner users)
    │
    ├── id (PK)
    ├── first_name
    ├── last_name
    ├── email
    ├── role = 'HOTEL_OWNER'
    └── created_at
        │
        ├──┬── profile_pictures (5 records)
        │  │   ├── user_id (FK)
        │  │   └── profile_picture
        │  │
        └──┴── hotels (5 records)
               ├── id (PK)
               ├── user_id (FK) ───────┐
               ├── hotel_name          │
               ├── street              │
               ├── city                │
               ├── district            │
               ├── province            │
               ├── type                │
               └── description         │
                   │                   │
                   ├── hotel_license_photos (5 records)
                   │   ├── hotel_id (FK)
                   │   └── license_photo_url
                   │
                   ├── hotel_images (25 records)
                   │   ├── hotel_id (FK)
                   │   └── image_url
                   │
                   ├── hotel_amenities (53 records)
                   │   ├── hotel_id (FK)
                   │   └── amenity
                   │
                   └── rooms (15 records - 3 per hotel)
                       ├── id (PK)
                       ├── hotel_id (FK) ──┐
                       ├── room_type       │
                       ├── max_guests      │
                       ├── bed_types       │
                       ├── price_per_night │
                       └── availability    │
                           │               │
                           ├── room_images (45 records)
                           │   ├── room_id (FK)
                           │   └── image_url
                           │
                           └── room_amenities (145 records)
                               ├── room_id (FK)
                               └── amenity
```

---

## 📊 Complete Database Summary

### Table Hierarchy:

```
Level 1 (Independent Tables):
    ├── vehicle_agencies (3)
    └── users (10)

Level 2 (Depends on Level 1):
    ├── agency_license_photos (3) ───── FK: agency_id
    ├── vehicles (5) ───────────────── FK: agency_id
    ├── profile_pictures (10) ──────── FK: user_id
    ├── guides (5) ─────────────────── FK: user_id
    └── hotels (5) ─────────────────── FK: user_id

Level 3 (Depends on Level 2):
    ├── vehicle_images (25) ────────── FK: vehicle_id
    ├── vehicle_amenities (30) ─────── FK: vehicle_id
    ├── guid_languages (23) ────────── FK: guid_id
    ├── guid_specializations (21) ──── FK: guid_id
    ├── guid_slta_license_photos (5) ── FK: guid_id
    ├── guid_nic_photos (5) ────────── FK: guid_id
    ├── guid_nic_photos_back (5) ───── FK: guid_id
    ├── hotel_license_photos (5) ───── FK: hotel_id
    ├── hotel_images (25) ──────────── FK: hotel_id
    ├── hotel_amenities (53) ───────── FK: hotel_id
    └── rooms (15) ─────────────────── FK: hotel_id

Level 4 (Depends on Level 3):
    ├── room_images (45) ───────────── FK: room_id
    └── room_amenities (145) ───────── FK: room_id
```

---

## 🔗 Foreign Key Relationships

### Vehicles:
```
vehicle_agencies.id → vehicles.agency_id (1:N)
vehicle_agencies.id → agency_license_photos.agency_id (1:N)
vehicles.id → vehicle_images.vehicle_id (1:N)
vehicles.id → vehicle_amenities.vehicle_id (1:N)
```

### Guides:
```
users.id → profile_pictures.user_id (1:N)
users.id → guides.user_id (1:1)
guides.id → guid_languages.guid_id (1:N)
guides.id → guid_specializations.guid_id (1:N)
guides.id → guid_slta_license_photos.guid_id (1:N)
guides.id → guid_nic_photos.guid_id (1:N)
guides.id → guid_nic_photos_back.guid_id (1:N)
```

### Hotels & Rooms:
```
users.id → profile_pictures.user_id (1:N)
users.id → hotels.user_id (1:1)
hotels.id → hotel_license_photos.hotel_id (1:N)
hotels.id → hotel_images.hotel_id (1:N)
hotels.id → hotel_amenities.hotel_id (1:N)
hotels.id → rooms.hotel_id (1:N)
rooms.id → room_images.room_id (1:N)
rooms.id → room_amenities.room_id (1:N)
```

---

## 📈 Data Distribution

### By Entity Type:

```
Vehicles
├── Main Tables: 2 (agencies, vehicles)
├── Support Tables: 3 (images, amenities, license photos)
└── Total Records: 66

Guides  
├── Main Tables: 2 (users, guides)
├── Support Tables: 6 (languages, specializations, documents, profile)
└── Total Records: 74

Hotels
├── Main Tables: 2 (users, hotels)
├── Support Tables: 4 (license, images, amenities, profile)
└── Total Records: 93

Rooms
├── Main Tables: 1 (rooms)
├── Support Tables: 2 (images, amenities)
└── Total Records: 205

TOTAL: 20 tables, 446 records
```

---

## 🎯 Import Order (Critical!)

```
┌─────────────────────────────────────────────────────┐
│ STEP 1: Independent Tables (No Dependencies)       │
├─────────────────────────────────────────────────────┤
│ 1. vehicle_agencies                                 │
│ 2. users                                            │
└─────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────┐
│ STEP 2: First Level Dependencies                   │
├─────────────────────────────────────────────────────┤
│ 3. profile_pictures (→ users)                      │
│ 4. agency_license_photos (→ agencies)              │
│ 5. vehicles (→ agencies)                           │
│ 6. guides (→ users)                                │
│ 7. hotels (→ users)                                │
└─────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────┐
│ STEP 3: Second Level Dependencies                  │
├─────────────────────────────────────────────────────┤
│ 8. vehicle_images (→ vehicles)                     │
│ 9. vehicle_amenities (→ vehicles)                  │
│ 10. guid_languages (→ guides)                      │
│ 11. guid_specializations (→ guides)                │
│ 12. guid_slta_license_photos (→ guides)            │
│ 13. guid_nic_photos (→ guides)                     │
│ 14. guid_nic_photos_back (→ guides)                │
│ 15. hotel_license_photos (→ hotels)                │
│ 16. hotel_images (→ hotels)                        │
│ 17. hotel_amenities (→ hotels)                     │
│ 18. rooms (→ hotels)                               │
└─────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────┐
│ STEP 4: Third Level Dependencies                   │
├─────────────────────────────────────────────────────┤
│ 19. room_images (→ rooms)                          │
│ 20. room_amenities (→ rooms)                       │
└─────────────────────────────────────────────────────┘
```

---

## 🔍 Data Integrity Checks

### Check Foreign Keys:
```sql
-- Verify vehicles have valid agency_id
SELECT v.id, v.vehicle_model, v.agency_id, va.agency_name
FROM vehicles v
LEFT JOIN vehicle_agencies va ON v.agency_id = va.id
WHERE va.id IS NULL;
-- Should return 0 rows

-- Verify guides have valid user_id
SELECT g.id, g.user_id, u.first_name, u.last_name
FROM guides g
LEFT JOIN users u ON g.user_id = u.id
WHERE u.id IS NULL;
-- Should return 0 rows

-- Verify rooms have valid hotel_id
SELECT r.id, r.room_type, r.hotel_id, h.hotel_name
FROM rooms r
LEFT JOIN hotels h ON r.hotel_id = h.id
WHERE h.id IS NULL;
-- Should return 0 rows
```

### Check Data Consistency:
```sql
-- Verify all vehicles have images
SELECT v.id, v.vehicle_model, COUNT(vi.image_url) as image_count
FROM vehicles v
LEFT JOIN vehicle_images vi ON v.id = vi.vehicle_id
GROUP BY v.id, v.vehicle_model
HAVING COUNT(vi.image_url) = 0;
-- Should return 0 rows

-- Verify all guides have languages
SELECT g.id, u.first_name, COUNT(gl.language) as language_count
FROM guides g
JOIN users u ON g.user_id = u.id
LEFT JOIN guid_languages gl ON g.id = gl.guid_id
GROUP BY g.id, u.first_name
HAVING COUNT(gl.language) = 0;
-- Should return 0 rows

-- Verify all rooms have images
SELECT r.id, r.room_type, COUNT(ri.image_url) as image_count
FROM rooms r
LEFT JOIN room_images ri ON r.id = ri.room_id
GROUP BY r.id, r.room_type
HAVING COUNT(ri.image_url) = 0;
-- Should return 0 rows
```

---

## 📐 Table Sizes

### Record Count by Table:

```
vehicle_agencies ................ 3 records
agency_license_photos ........... 3 records
vehicles ........................ 5 records
vehicle_images .................. 25 records
vehicle_amenities ............... 30 records
────────────────────────────────────────────
Vehicles Subtotal ............... 66 records

users (guides) .................. 5 records
profile_pictures (guides) ....... 5 records
guides .......................... 5 records
guid_languages .................. 23 records
guid_specializations ............ 21 records
guid_slta_license_photos ........ 5 records
guid_nic_photos ................. 5 records
guid_nic_photos_back ............ 5 records
────────────────────────────────────────────
Guides Subtotal ................. 74 records

users (hotel owners) ............ 5 records
profile_pictures (hotels) ....... 5 records
hotels .......................... 5 records
hotel_license_photos ............ 5 records
hotel_images .................... 25 records
hotel_amenities ................. 53 records
────────────────────────────────────────────
Hotels Subtotal ................. 98 records

rooms ........................... 15 records
room_images ..................... 45 records
room_amenities .................. 145 records
────────────────────────────────────────────
Rooms Subtotal .................. 205 records

═════════════════════════════════════════════
GRAND TOTAL ..................... 446 records
═════════════════════════════════════════════
```

---

*This visual guide helps understand the complete database structure and import dependencies.*
