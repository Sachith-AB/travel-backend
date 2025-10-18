# CSV Import Guide for Vehicles

## 📂 CSV Files Created

The following CSV files have been created in `/travel-backend/csv_import/`:

1. **vehicle_agencies.csv** - 3 vehicle rental agencies
2. **vehicles.csv** - 5 vehicles with different types
3. **vehicle_amenities.csv** - Amenities for each vehicle
4. **vehicle_images.csv** - Image URLs for each vehicle
5. **agency_license_photos.csv** - License photos for agencies

## 📋 Vehicle Records Overview

### Vehicles Included:

1. **Toyota Axio** (Sedan)
   - Capacity: 4 passengers
   - Price: LKR 8,000/day, LKR 100/km
   - Agency: CityDrive Rentals
   - Amenities: AC, Bluetooth, GPS, USB, Music, ABS

2. **Toyota KDH** (Van)
   - Capacity: 12 passengers
   - Price: LKR 15,000/day, LKR 150/km
   - Agency: CityDrive Rentals
   - Amenities: AC, Comfortable Seating, Music, USB, Luggage Space

3. **Toyota Fortuner** (SUV)
   - Capacity: 7 passengers
   - Price: LKR 18,000/day, LKR 180/km
   - Agency: CityDrive Rentals
   - Amenities: AC, Leather Seats, GPS, Sunroof, 4WD

4. **Toyota Land Cruiser** (Jeep)
   - Capacity: 6 passengers
   - Price: LKR 20,000/day, LKR 200/km
   - Agency: Ceylon Safari Tours
   - Amenities: Open Roof, Binoculars, Wildlife Guide, Safari features

5. **Nissan Caravan** (Van)
   - Capacity: 10 passengers
   - Price: LKR 14,000/day, LKR 140/km
   - Agency: Island Wheels
   - Amenities: AC, Reclining Seats, Music, USB, Luggage Space

## 🔧 Import Methods

### Method 1: Using Supabase Dashboard (Recommended)

#### Step 1: Import Vehicle Agencies

1. Go to Supabase Dashboard → Table Editor
2. Select `vehicle_agencies` table
3. Click "Insert" → "Insert via CSV"
4. Upload `vehicle_agencies.csv`
5. Map columns (they should auto-map)
6. Click "Import"

#### Step 2: Import Vehicles

1. Select `vehicles` table
2. Click "Insert" → "Insert via CSV"
3. Upload `vehicles.csv`
4. **Important:** Make sure `agency_id` maps correctly
5. Click "Import"

#### Step 3: Import Vehicle Amenities

1. Select `vehicle_amenities` table
2. Click "Insert" → "Insert via CSV"
3. Upload `vehicle_amenities.csv`
4. **Important:** Ensure `vehicle_id` references are correct
5. Click "Import"

#### Step 4: Import Vehicle Images

1. Select `vehicle_images` table
2. Click "Insert" → "Insert via CSV"
3. Upload `vehicle_images.csv`
4. Click "Import"

#### Step 5: Import Agency License Photos

1. Select `agency_license_photos` table
2. Click "Insert" → "Insert via CSV"
3. Upload `agency_license_photos.csv`
4. Click "Import"

### Method 2: Using psql Command Line

If you have direct database access:

```bash
# Navigate to the csv_import directory
cd /Users/hirunmihisarakariyawasam/Documents/projects/travel-full/travel-backend/csv_import

# Import in this order (important for foreign key constraints)

# 1. Import agencies first
psql -h aws-1-ap-south-1.pooler.supabase.com -p 6543 -U postgres.dnouvojlwvsahnhzfhyf -d postgres -c "\COPY vehicle_agencies(id,agency_name,user_id,street,city,district,province,registration_no,description,created_at) FROM 'vehicle_agencies.csv' WITH CSV HEADER"

# 2. Import vehicles
psql -h aws-1-ap-south-1.pooler.supabase.com -p 6543 -U postgres.dnouvojlwvsahnhzfhyf -d postgres -c "\COPY vehicles(id,vehicle_type,vehicle_no,registration_no,price_per_kilometer,base_price,insurance_number,insurance_expiry_date,vehicle_model,is_verified,availability,capacity,agency_id,created_at) FROM 'vehicles.csv' WITH CSV HEADER"

# 3. Import amenities
psql -h aws-1-ap-south-1.pooler.supabase.com -p 6543 -U postgres.dnouvojlwvsahnhzfhyf -d postgres -c "\COPY vehicle_amenities(vehicle_id,amenity) FROM 'vehicle_amenities.csv' WITH CSV HEADER"

# 4. Import images
psql -h aws-1-ap-south-1.pooler.supabase.com -p 6543 -U postgres.dnouvojlwvsahnhzfhyf -d postgres -c "\COPY vehicle_images(vehicle_id,image_url) FROM 'vehicle_images.csv' WITH CSV HEADER"

# 5. Import license photos
psql -h aws-1-ap-south-1.pooler.supabase.com -p 6543 -U postgres.dnouvojlwvsahnhzfhyf -d postgres -c "\COPY agency_license_photos(agency_id,license_photo_url) FROM 'agency_license_photos.csv' WITH CSV HEADER"
```

### Method 3: Using SQL INSERT Statements

You can also use the SQL Editor in Supabase and run these commands:

```sql
-- 1. Insert Agencies
INSERT INTO vehicle_agencies (id,agency_name,user_id,street,city,district,province,registration_no,description,created_at)
VALUES 
(1,'CityDrive Rentals',1,'No. 45 Galle Road','Colombo','Colombo','Western Province','VTA001','Premium vehicle rental service with modern fleet and experienced drivers','2024-01-15 10:00:00'),
(2,'Ceylon Safari Tours',1,'No. 89 Katugastota Road','Kandy','Kandy','Central Province','VTA002','Specialized in safari and wildlife tour vehicles','2024-01-16 10:00:00'),
(3,'Island Wheels',1,'No. 23 Main Street','Negombo','Gampaha','Western Province','VTA003','Reliable vehicle rental with island-wide service','2024-01-17 10:00:00')
ON CONFLICT (id) DO NOTHING;

-- 2. Insert Vehicles
INSERT INTO vehicles (id,vehicle_type,vehicle_no,registration_no,price_per_kilometer,base_price,insurance_number,insurance_expiry_date,vehicle_model,is_verified,availability,capacity,agency_id,created_at)
VALUES 
(1,'Sedan','V-001','CAR-1234','100.00',8000.00,'INS-2024-001','2025-12-31','Toyota Axio',true,true,4,1,'2024-01-15 10:30:00'),
(2,'Van','V-002','VAN-5678','150.00',15000.00,'INS-2024-002','2025-11-30','Toyota KDH',true,true,12,1,'2024-01-15 11:00:00'),
(3,'SUV','V-003','SUV-9012','180.00',18000.00,'INS-2024-003','2025-10-31','Toyota Fortuner',true,true,7,1,'2024-01-15 11:30:00'),
(4,'Jeep','V-004','JEP-3456','200.00',20000.00,'INS-2024-004','2025-12-15','Toyota Land Cruiser',true,true,6,2,'2024-01-16 10:30:00'),
(5,'Van','V-005','VAN-7890','140.00',14000.00,'INS-2024-005','2025-09-30','Nissan Caravan',true,true,10,3,'2024-01-17 10:30:00')
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Amenities
INSERT INTO vehicle_amenities (vehicle_id,amenity) VALUES
(1,'Air Conditioning'),(1,'Bluetooth'),(1,'GPS Navigation'),(1,'USB Charging'),(1,'Music System'),(1,'ABS'),
(2,'Air Conditioning'),(2,'Comfortable Seating'),(2,'Music System'),(2,'USB Charging'),(2,'Luggage Space'),(2,'Experienced Driver'),
(3,'Air Conditioning'),(3,'Leather Seats'),(3,'GPS Navigation'),(3,'Music System'),(3,'USB Charging'),(3,'Sunroof'),(3,'4WD'),
(4,'Open Roof'),(4,'Binoculars'),(4,'Wildlife Guide'),(4,'Photography Assistance'),(4,'Off-road Capable'),(4,'Experienced Safari Driver'),
(5,'Air Conditioning'),(5,'Reclining Seats'),(5,'Music System'),(5,'USB Charging'),(5,'Luggage Space'),(5,'Experienced Driver')
ON CONFLICT DO NOTHING;

-- 4. Insert Images
INSERT INTO vehicle_images (vehicle_id,image_url) VALUES
(1,'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800'),
(1,'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800&q=80'),
(2,'https://images.unsplash.com/photo-1527786356703-4b100091cd2c?w=800'),
(2,'https://images.unsplash.com/photo-1527786356703-4b100091cd2c?w=800&q=80'),
(3,'https://images.unsplash.com/photo-1519641471654-76ce0107ad1b?w=800'),
(3,'https://images.unsplash.com/photo-1519641471654-76ce0107ad1b?w=800&q=80'),
(4,'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800'),
(4,'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&q=80'),
(5,'https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800'),
(5,'https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800&q=80')
ON CONFLICT DO NOTHING;

-- 5. Insert License Photos
INSERT INTO agency_license_photos (agency_id,license_photo_url) VALUES
(1,'https://example.com/licenses/citydrive-license-1.jpg'),
(1,'https://example.com/licenses/citydrive-license-2.jpg'),
(2,'https://example.com/licenses/ceylon-safari-license-1.jpg'),
(2,'https://example.com/licenses/ceylon-safari-license-2.jpg'),
(3,'https://example.com/licenses/island-wheels-license-1.jpg'),
(3,'https://example.com/licenses/island-wheels-license-2.jpg')
ON CONFLICT DO NOTHING;
```

## ✅ Verification Queries

After importing, run these queries to verify:

```sql
-- Count records
SELECT 'Agencies' as table_name, COUNT(*) as count FROM vehicle_agencies
UNION ALL
SELECT 'Vehicles' as table_name, COUNT(*) as count FROM vehicles
UNION ALL
SELECT 'Amenities' as table_name, COUNT(*) as count FROM vehicle_amenities
UNION ALL
SELECT 'Images' as table_name, COUNT(*) as count FROM vehicle_images
UNION ALL
SELECT 'License Photos' as table_name, COUNT(*) as count FROM agency_license_photos;

-- View complete vehicle data with agency
SELECT 
    v.id,
    v.vehicle_type,
    v.vehicle_model,
    v.vehicle_no,
    v.capacity,
    v.base_price,
    v.price_per_kilometer,
    v.is_verified,
    v.availability,
    va.agency_name,
    va.city,
    va.province
FROM vehicles v
LEFT JOIN vehicle_agencies va ON v.agency_id = va.id
ORDER BY v.id;

-- View vehicle amenities
SELECT 
    v.vehicle_no,
    v.vehicle_model,
    STRING_AGG(va.amenity, ', ' ORDER BY va.amenity) as amenities
FROM vehicles v
LEFT JOIN vehicle_amenities va ON v.id = va.vehicle_id
GROUP BY v.id, v.vehicle_no, v.vehicle_model
ORDER BY v.id;

-- View vehicle images
SELECT 
    v.vehicle_no,
    v.vehicle_model,
    COUNT(vi.image_url) as image_count
FROM vehicles v
LEFT JOIN vehicle_images vi ON v.id = vi.vehicle_id
GROUP BY v.id, v.vehicle_no, v.vehicle_model
ORDER BY v.id;
```

## Expected Results:

```
Agencies: 3
Vehicles: 5
Amenities: 31
Images: 10
License Photos: 6
```

## 🔍 Troubleshooting

### Issue: Foreign key constraint violation
**Solution:** Import in the correct order:
1. vehicle_agencies (first - parent table)
2. vehicles (depends on agencies)
3. vehicle_amenities (depends on vehicles)
4. vehicle_images (depends on vehicles)
5. agency_license_photos (depends on agencies)

### Issue: Duplicate key error
**Solution:** 
- Clear existing data first, or
- Remove the `id` column from CSV and let it auto-increment, or
- Use `ON CONFLICT DO NOTHING` in SQL method

### Issue: CSV format error
**Solution:** 
- Ensure CSV uses UTF-8 encoding
- Check for extra commas or quotes
- Verify date format is: YYYY-MM-DD HH:MM:SS

### Issue: NULL values in required fields
**Solution:** 
- Check that all required fields have values
- Verify `agency_id` exists in vehicle_agencies table
- Ensure `vehicle_id` exists in vehicles table

## 🚀 Next Steps

After successful import:

1. **Restart Backend** (if running):
   ```bash
   cd /Users/hirunmihisarakariyawasam/Documents/projects/travel-full/travel-backend
   ./mvnw spring-boot:run
   ```

2. **Test API Endpoint**:
   ```bash
   curl http://localhost:5454/api/vehicles
   ```

3. **Navigate to Frontend**:
   ```
   http://localhost:5000/tour/select-vehicle
   ```

4. **Verify Display**:
   - Should see 5 vehicles
   - Each with agency info
   - Amenities listed
   - Images displayed
   - Prices shown

## 📊 Data Summary

| Vehicle | Type | Capacity | Price/Day | Price/KM | Agency |
|---------|------|----------|-----------|----------|--------|
| Toyota Axio | Sedan | 4 | 8,000 | 100 | CityDrive Rentals |
| Toyota KDH | Van | 12 | 15,000 | 150 | CityDrive Rentals |
| Toyota Fortuner | SUV | 7 | 18,000 | 180 | CityDrive Rentals |
| Land Cruiser | Jeep | 6 | 20,000 | 200 | Ceylon Safari Tours |
| Nissan Caravan | Van | 10 | 14,000 | 140 | Island Wheels |

Perfect for testing different passenger capacities and vehicle types! 🎉
