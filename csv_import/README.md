# 🚗 Vehicle Data Import - CSV Files

## 📦 What's Included

This folder contains CSV files and scripts to import **5 complete vehicle records** into your database.

### Files Overview:

| File | Records | Description |
|------|---------|-------------|
| `vehicle_agencies.csv` | 3 | Rental agencies (CityDrive, Ceylon Safari, Island Wheels) |
| `vehicles.csv` | 5 | 5 vehicles (Sedan, 2 Vans, SUV, Jeep) |
| `vehicle_amenities.csv` | 31 | Amenities for all 5 vehicles |
| `vehicle_images.csv` | 10 | 2 images per vehicle |
| `agency_license_photos.csv` | 6 | 2 license photos per agency |
| `QUICK_IMPORT.sql` | - | Ready-to-run SQL script with all data |
| `IMPORT_GUIDE.md` | - | Detailed import instructions |

## 🚀 Quick Start (Easiest Method)

### Option 1: Copy-Paste SQL (Recommended - Takes 30 seconds)

1. Open **Supabase Dashboard** → **SQL Editor**
2. Open the file: `QUICK_IMPORT.sql`
3. Copy ALL content
4. Paste into Supabase SQL Editor
5. Click **RUN**
6. Done! ✅

### Option 2: Import CSV Files (Alternative)

See detailed instructions in `IMPORT_GUIDE.md`

## 📊 What Gets Imported

### 3 Vehicle Agencies:
- **CityDrive Rentals** (Colombo) - 3 vehicles
- **Ceylon Safari Tours** (Kandy) - 1 vehicle
- **Island Wheels** (Negombo) - 1 vehicle

### 5 Vehicles:

1. **Toyota Axio** - Sedan
   - 4 passengers | LKR 8,000/day
   - AC, Bluetooth, GPS, USB, Music, ABS

2. **Toyota KDH** - Van  
   - 12 passengers | LKR 15,000/day
   - AC, Comfortable Seating, Music, Luggage Space

3. **Toyota Fortuner** - SUV
   - 7 passengers | LKR 18,000/day
   - AC, Leather Seats, GPS, Sunroof, 4WD

4. **Land Cruiser** - Safari Jeep
   - 6 passengers | LKR 20,000/day
   - Open Roof, Binoculars, Wildlife Guide

5. **Nissan Caravan** - Van
   - 10 passengers | LKR 14,000/day
   - AC, Reclining Seats, Music, Luggage Space

## ✅ Verification

After import, you should see:
- ✅ 3 Agencies
- ✅ 5 Vehicles  
- ✅ 31 Amenities
- ✅ 10 Images
- ✅ 6 License Photos

Run this query to verify:
```sql
SELECT 'Agencies' as table_name, COUNT(*) as count FROM vehicle_agencies
UNION ALL
SELECT 'Vehicles' as table_name, COUNT(*) as count FROM vehicles;
```

## 🎯 Next Steps

After successful import:

1. **Start Backend**:
   ```bash
   cd /Users/hirunmihisarakariyawasam/Documents/projects/travel-full/travel-backend
   ./mvnw spring-boot:run
   ```

2. **Test API**:
   ```bash
   curl http://localhost:5454/api/vehicles
   ```
   Should return 5 vehicles with all details

3. **View in Frontend**:
   Navigate to: `http://localhost:5000/tour/select-vehicle`

4. **Expected Result**:
   - See 5 vehicle cards
   - Each with images, amenities, pricing
   - Agency information displayed
   - All properly formatted

## 📋 CSV Format Details

All CSV files use:
- UTF-8 encoding
- Comma delimiters
- Header row included
- Date format: `YYYY-MM-DD HH:MM:SS`

## 🔧 Troubleshooting

**Problem:** Foreign key errors
- **Solution:** Import `QUICK_IMPORT.sql` which handles dependencies automatically

**Problem:** Duplicate key errors
- **Solution:** SQL script uses `ON CONFLICT DO NOTHING` - safe to re-run

**Problem:** Data not showing in frontend
- **Solution:** 
  1. Verify data exists: Run verification query
  2. Restart backend
  3. Check browser console for errors
  4. Verify API endpoint works

## 📖 Documentation

- **Detailed Guide**: See `IMPORT_GUIDE.md` for all import methods
- **Vehicle Setup**: See `../VEHICLE_SETUP_GUIDE.md` for complete setup
- **SQL Script**: See `QUICK_IMPORT.sql` for copy-paste import

## 🎉 Success Criteria

After import, your vehicle page should display:
- ✅ 5 vehicle cards with images
- ✅ Vehicle types (Sedan, Van, SUV, Jeep)
- ✅ Passenger capacity for each
- ✅ Daily and per-km pricing
- ✅ Agency names and locations
- ✅ Full amenity lists
- ✅ Availability status

Perfect for testing the complete trip booking flow! 🚀
