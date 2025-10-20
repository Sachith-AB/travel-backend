# 📦 Complete CSV Import Package - Travel.lk Database

## 🌟 Overview
This package contains all CSV files and import scripts needed to populate your Travel.lk database with sample data for **vehicles, guides, and hotels**.

---

## 📊 What's Included

### 🚗 Vehicles (5 Vehicles, 3 Agencies)
- 5 fully detailed vehicles with images and amenities
- 3 rental agencies with complete information
- All vehicle relationships properly configured

### 🗣️ Guides (5 Guides)
- 5 professional tour guides with diverse specializations
- Multiple languages per guide
- Complete verification documents (SLTA licenses, NIC photos)
- Experience ranging from 6-12 years

### 🏨 Hotels (5 Hotels, 15 Rooms)
- 5 hotels across different locations in Sri Lanka
- 15 rooms total (3 rooms per hotel)
- Complete hotel and room images
- Detailed amenities for both hotels and rooms
- Various hotel types: 5-Star, Beach Resort, Heritage, Eco Lodge

---

## 📁 File Structure

```
csv_import/
├── QUICK_IMPORT.sql                    # Vehicle import script
├── GUIDES_HOTELS_IMPORT.sql           # Guides & hotels import script
├── IMPORT_GUIDE.md                     # Vehicle import guide
├── GUIDES_HOTELS_IMPORT_GUIDE.md      # Guides & hotels import guide
├── README.md                           # This file
│
├── Vehicle Files:
│   ├── vehicle_agencies.csv
│   ├── vehicles.csv
│   ├── vehicle_amenities.csv
│   ├── vehicle_images.csv
│   └── agency_license_photos.csv
│
├── User & Profile Files:
│   ├── users_guides_hotels.csv
│   └── profile_pictures.csv
│
├── Guide Files:
│   ├── guides.csv
│   ├── guid_languages.csv
│   ├── guid_specializations.csv
│   ├── guid_slta_license_photos.csv
│   ├── guid_nic_photos.csv
│   └── guid_nic_photos_back.csv
│
├── Hotel Files:
│   ├── hotels.csv
│   ├── hotel_license_photos.csv
│   ├── hotel_images.csv
│   └── hotel_amenities.csv
│
└── Room Files:
    ├── rooms.csv
    ├── room_images.csv
    └── room_amenities.csv
```

---

## 🚀 Quick Start

### Option 1: Import Everything at Once

```bash
# Navigate to csv_import directory
cd /path/to/travel-backend/csv_import

# Import vehicles
psql -U your_username -d travel_lk -f QUICK_IMPORT.sql

# Import guides and hotels
psql -U your_username -d travel_lk -f GUIDES_HOTELS_IMPORT.sql
```

### Option 2: Import Separately

**For Vehicles Only:**
```bash
psql -U your_username -d travel_lk -f QUICK_IMPORT.sql
```

**For Guides & Hotels Only:**
```bash
psql -U your_username -d travel_lk -f GUIDES_HOTELS_IMPORT.sql
```

---

## 📋 Data Summary

### Vehicles:
| Agency | Location | Vehicles | Total Images | Total Amenities |
|--------|----------|----------|--------------|-----------------|
| CityDrive Rentals | Colombo | 3 | 15 | 14 |
| EcoRide Solutions | Kandy | 1 | 5 | 8 |
| DriveEasy Kandy | Kandy | 1 | 5 | 8 |
| **TOTAL** | - | **5** | **25** | **30** |

**Vehicle Types**: Sedan (2), Van (1), SUV (1), Electric Car (1)

### Guides:
| Name | Experience | Rate/Hour | Languages | Specializations |
|------|------------|-----------|-----------|-----------------|
| Samantha Perera | 8 years | LKR 2,500 | 5 | 4 |
| Rohan Silva | 6 years | LKR 2,800 | 4 | 4 |
| Nimal Fernando | 10 years | LKR 3,000 | 4 | 4 |
| Priya Jayawardena | 7 years | LKR 2,600 | 5 | 4 |
| Kasun Wickramasinghe | 12 years | LKR 3,500 | 5 | 5 |
| **TOTAL** | **43 years** | - | **23** | **21** |

**Specializations**: Cultural Tours, Wildlife Safaris, Beach Tours, Hill Country, Ancient Cities, UNESCO Sites, Water Sports, Tea Plantations, and more.

### Hotels:
| Hotel | Location | Type | Rooms | Price Range |
|-------|----------|------|-------|-------------|
| Cinnamon Grand | Colombo | 5-Star | 3 | LKR 15k-28k |
| Jetwing Lighthouse | Galle | Beach Resort | 3 | LKR 18k-42k |
| Heritance Tea Factory | Nuwara Eliya | Heritage | 3 | LKR 12k-25k |
| The Kingsbury | Colombo | 5-Star | 3 | LKR 16k-65k |
| Eco Lodge Habarana | Habarana | Eco Lodge | 3 | LKR 8k-18k |
| **TOTAL** | - | - | **15** | **LKR 8k-65k** |

**Room Types**: Deluxe, Suite, Ocean View, Beach Villa, Colonial, Family, Tree House, and more.

---

## 📈 Database Impact

### Tables Populated:

**Vehicles (5 tables):**
- `vehicle_agencies` - 3 records
- `vehicles` - 5 records
- `vehicle_images` - 25 records
- `vehicle_amenities` - 30 records
- `agency_license_photos` - 3 records

**Users (2 tables):**
- `users` - 10 records (5 guides + 5 hotel owners)
- `profile_pictures` - 10 records

**Guides (6 tables):**
- `guides` - 5 records
- `guid_languages` - 23 records
- `guid_specializations` - 21 records
- `guid_slta_license_photos` - 5 records
- `guid_nic_photos` - 5 records
- `guid_nic_photos_back` - 5 records

**Hotels (4 tables):**
- `hotels` - 5 records
- `hotel_license_photos` - 5 records
- `hotel_images` - 25 records
- `hotel_amenities` - 53 records

**Rooms (3 tables):**
- `rooms` - 15 records
- `room_images` - 45 records
- `room_amenities` - 145 records

**TOTAL: 20 tables populated with 373 records**

---

## ✅ Verification

### Quick Check:
```sql
-- Count all imported data
SELECT 
    'Vehicles' as entity, COUNT(*) as count FROM vehicles
UNION ALL
SELECT 'Vehicle Agencies', COUNT(*) FROM vehicle_agencies
UNION ALL
SELECT 'Guides', COUNT(*) FROM guides
UNION ALL
SELECT 'Hotels', COUNT(*) FROM hotels
UNION ALL
SELECT 'Rooms', COUNT(*) FROM rooms;
```

**Expected Output:**
```
     entity       | count
------------------+-------
 Vehicles         |     5
 Vehicle Agencies |     3
 Guides           |     5
 Hotels           |     5
 Rooms            |    15
```

### Detailed Verification:
```sql
-- Check vehicles with agencies
SELECT v.id, v.vehicle_model, v.vehicle_type, va.agency_name, va.city
FROM vehicles v
JOIN vehicle_agencies va ON v.agency_id = va.id
ORDER BY v.id;

-- Check guides with languages
SELECT g.id, u.first_name, u.last_name, COUNT(gl.language) as language_count
FROM guides g
JOIN users u ON g.user_id = u.id
LEFT JOIN guid_languages gl ON g.id = gl.guid_id
GROUP BY g.id, u.first_name, u.last_name
ORDER BY g.id;

-- Check hotels with rooms
SELECT h.id, h.hotel_name, h.city, COUNT(r.id) as room_count
FROM hotels h
LEFT JOIN rooms r ON h.id = r.hotel_id
GROUP BY h.id, h.hotel_name, h.city
ORDER BY h.id;
```

---

## 🎯 Usage Scenarios

### For Developers:
- **Testing**: Use this data to test frontend components
- **Development**: Develop features without manual data entry
- **Demos**: Present a fully functional travel booking system

### For QA:
- **Testing**: Test search, filter, and booking flows
- **Edge Cases**: Various price ranges, room types, vehicle types
- **Validation**: Verify data integrity and relationships

### For Presentations:
- **Client Demos**: Show real-looking data
- **Stakeholder Reviews**: Present working features
- **User Testing**: Conduct usability testing with realistic data

---

## 🔧 Customization

### Adding More Data:
1. Follow the same CSV format
2. Ensure IDs don't conflict
3. Maintain foreign key relationships
4. Update verification queries

### Modifying Existing Data:
1. Edit CSV files directly
2. Delete existing data (see cleanup section)
3. Re-import modified CSV files

### Image URLs:
- Current images use Unsplash placeholders
- Replace URLs with your own image hosting
- Maintain the same CSV structure

---

## 🧹 Cleanup Scripts

### Remove Vehicles:
```sql
DELETE FROM agency_license_photos WHERE agency_id BETWEEN 1 AND 3;
DELETE FROM vehicle_amenities WHERE vehicle_id BETWEEN 1 AND 5;
DELETE FROM vehicle_images WHERE vehicle_id BETWEEN 1 AND 5;
DELETE FROM vehicles WHERE id BETWEEN 1 AND 5;
DELETE FROM vehicle_agencies WHERE id BETWEEN 1 AND 3;
```

### Remove Guides:
```sql
DELETE FROM guid_nic_photos_back WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_nic_photos WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_slta_license_photos WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_specializations WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guid_languages WHERE guid_id BETWEEN 1 AND 5;
DELETE FROM guides WHERE id BETWEEN 1 AND 5;
```

### Remove Hotels & Rooms:
```sql
DELETE FROM room_amenities WHERE room_id BETWEEN 1 AND 15;
DELETE FROM room_images WHERE room_id BETWEEN 1 AND 15;
DELETE FROM rooms WHERE id BETWEEN 1 AND 15;
DELETE FROM hotel_amenities WHERE hotel_id BETWEEN 1 AND 5;
DELETE FROM hotel_images WHERE hotel_id BETWEEN 1 AND 5;
DELETE FROM hotel_license_photos WHERE hotel_id BETWEEN 1 AND 5;
DELETE FROM hotels WHERE id BETWEEN 1 AND 5;
```

### Remove Users:
```sql
DELETE FROM profile_pictures WHERE user_id BETWEEN 1 AND 10;
DELETE FROM users WHERE id BETWEEN 1 AND 10 AND role IN ('GUIDE', 'HOTEL_OWNER');
```

---

## 📚 Related Documentation

- **IMPORT_GUIDE.md** - Detailed vehicle import instructions
- **GUIDES_HOTELS_IMPORT_GUIDE.md** - Detailed guide & hotel import instructions
- **VEHICLE_INTEGRATION_SUCCESS.md** - Frontend integration guide
- **COMPLETE_VEHICLE_INTEGRATION.md** - Complete integration documentation

---

## 🎉 Success Checklist

After importing, verify:
- [ ] Vehicles display in frontend vehicle search page
- [ ] Vehicle details show all images and amenities
- [ ] Guides appear in guide selection page
- [ ] Guide profiles show languages and specializations
- [ ] Hotels display in hotel search page
- [ ] Hotel details show all images and amenities
- [ ] Rooms display for each hotel
- [ ] Room details show images and amenities
- [ ] All search and filter functions work
- [ ] No database errors or warnings

---

## 🆘 Support

### Common Issues:

**Q: Import fails with "permission denied"**  
A: Ensure you have write access to the database and read access to CSV files.

**Q: Foreign key constraint violation**  
A: Import files in the correct order: agencies → vehicles, users → guides/hotels → rooms.

**Q: Duplicate key errors**  
A: Check if data already exists. Clean up existing data before re-importing.

**Q: Images not displaying**  
A: Verify image URLs are accessible. Check your internet connection.

**Q: Data looks incorrect in frontend**  
A: Verify backend API endpoints are returning data. Check browser console for errors.

---

## 📞 Contact & Contribution

### Found an Issue?
- Check the troubleshooting section first
- Review verification queries
- Check database logs

### Want to Contribute?
- Add more sample data
- Improve CSV quality
- Update documentation
- Report issues

---

## 📜 License & Usage

This sample data is provided for:
- ✅ Development and testing
- ✅ Demonstrations and presentations
- ✅ Learning and education

Please replace with real data for production use.

---

## 🎊 Summary

You now have access to:
- **5 Vehicles** across 3 rental agencies
- **5 Tour Guides** with diverse expertise
- **5 Hotels** with 15 rooms across Sri Lanka
- **373 Total database records**
- **Complete import scripts and documentation**

**Everything you need for a fully functional travel booking system!**

---

*Last Updated: January 2024*  
*Package Version: 1.0.0*  
*Status: ✅ Production Ready*
