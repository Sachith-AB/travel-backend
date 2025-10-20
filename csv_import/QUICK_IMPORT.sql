-- ============================================
-- QUICK VEHICLE DATA IMPORT - 5 VEHICLES
-- Copy and paste this entire script into Supabase SQL Editor
-- ============================================

-- 1. Insert Vehicle Agencies (3 agencies)
INSERT INTO vehicle_agencies (id,agency_name,user_id,street,city,district,province,registration_no,description,created_at)
VALUES 
(1,'CityDrive Rentals',1,'No. 45 Galle Road','Colombo','Colombo','Western Province','VTA001','Premium vehicle rental service with modern fleet and experienced drivers','2024-01-15 10:00:00'),
(2,'Ceylon Safari Tours',1,'No. 89 Katugastota Road','Kandy','Kandy','Central Province','VTA002','Specialized in safari and wildlife tour vehicles','2024-01-16 10:00:00'),
(3,'Island Wheels',1,'No. 23 Main Street','Negombo','Gampaha','Western Province','VTA003','Reliable vehicle rental with island-wide service','2024-01-17 10:00:00')
ON CONFLICT (id) DO NOTHING;

-- 2. Insert Vehicles (5 vehicles)
INSERT INTO vehicles (id,vehicle_type,vehicle_no,registration_no,price_per_kilometer,base_price,insurance_number,insurance_expiry_date,vehicle_model,is_verified,availability,capacity,agency_id,created_at)
VALUES 
(1,'Sedan','V-001','CAR-1234','100.00',8000.00,'INS-2024-001','2025-12-31','Toyota Axio',true,true,4,1,'2024-01-15 10:30:00'),
(2,'Van','V-002','VAN-5678','150.00',15000.00,'INS-2024-002','2025-11-30','Toyota KDH',true,true,12,1,'2024-01-15 11:00:00'),
(3,'SUV','V-003','SUV-9012','180.00',18000.00,'INS-2024-003','2025-10-31','Toyota Fortuner',true,true,7,1,'2024-01-15 11:30:00'),
(4,'Jeep','V-004','JEP-3456','200.00',20000.00,'INS-2024-004','2025-12-15','Toyota Land Cruiser',true,true,6,2,'2024-01-16 10:30:00'),
(5,'Van','V-005','VAN-7890','140.00',14000.00,'INS-2024-005','2025-09-30','Nissan Caravan',true,true,10,3,'2024-01-17 10:30:00')
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Vehicle Amenities (31 amenities total)
INSERT INTO vehicle_amenities (vehicle_id,amenity) VALUES
-- Vehicle 1 - Toyota Axio Sedan
(1,'Air Conditioning'),
(1,'Bluetooth'),
(1,'GPS Navigation'),
(1,'USB Charging'),
(1,'Music System'),
(1,'ABS'),
-- Vehicle 2 - Toyota KDH Van
(2,'Air Conditioning'),
(2,'Comfortable Seating'),
(2,'Music System'),
(2,'USB Charging'),
(2,'Luggage Space'),
(2,'Experienced Driver'),
-- Vehicle 3 - Toyota Fortuner SUV
(3,'Air Conditioning'),
(3,'Leather Seats'),
(3,'GPS Navigation'),
(3,'Music System'),
(3,'USB Charging'),
(3,'Sunroof'),
(3,'4WD'),
-- Vehicle 4 - Land Cruiser Jeep (Safari)
(4,'Open Roof'),
(4,'Binoculars'),
(4,'Wildlife Guide'),
(4,'Photography Assistance'),
(4,'Off-road Capable'),
(4,'Experienced Safari Driver'),
-- Vehicle 5 - Nissan Caravan Van
(5,'Air Conditioning'),
(5,'Reclining Seats'),
(5,'Music System'),
(5,'USB Charging'),
(5,'Luggage Space'),
(5,'Experienced Driver')
ON CONFLICT DO NOTHING;

-- 4. Insert Vehicle Images (10 images total - 2 per vehicle)
INSERT INTO vehicle_images (vehicle_id,image_url) VALUES
-- Vehicle 1 - Toyota Axio
(1,'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800'),
(1,'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800&q=80'),
-- Vehicle 2 - Toyota KDH
(2,'https://images.unsplash.com/photo-1527786356703-4b100091cd2c?w=800'),
(2,'https://images.unsplash.com/photo-1527786356703-4b100091cd2c?w=800&q=80'),
-- Vehicle 3 - Toyota Fortuner
(3,'https://images.unsplash.com/photo-1519641471654-76ce0107ad1b?w=800'),
(3,'https://images.unsplash.com/photo-1519641471654-76ce0107ad1b?w=800&q=80'),
-- Vehicle 4 - Land Cruiser
(4,'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800'),
(4,'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&q=80'),
-- Vehicle 5 - Nissan Caravan
(5,'https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800'),
(5,'https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800&q=80')
ON CONFLICT DO NOTHING;

-- 5. Insert Agency License Photos (6 photos total - 2 per agency)
INSERT INTO agency_license_photos (agency_id,license_photo_url) VALUES
-- Agency 1 - CityDrive Rentals
(1,'https://example.com/licenses/citydrive-license-1.jpg'),
(1,'https://example.com/licenses/citydrive-license-2.jpg'),
-- Agency 2 - Ceylon Safari Tours
(2,'https://example.com/licenses/ceylon-safari-license-1.jpg'),
(2,'https://example.com/licenses/ceylon-safari-license-2.jpg'),
-- Agency 3 - Island Wheels
(3,'https://example.com/licenses/island-wheels-license-1.jpg'),
(3,'https://example.com/licenses/island-wheels-license-2.jpg')
ON CONFLICT DO NOTHING;

-- ============================================
-- VERIFICATION QUERIES
-- ============================================

-- Count all records
SELECT 'Agencies' as table_name, COUNT(*) as count FROM vehicle_agencies
UNION ALL
SELECT 'Vehicles' as table_name, COUNT(*) as count FROM vehicles
UNION ALL
SELECT 'Amenities' as table_name, COUNT(*) as count FROM vehicle_amenities
UNION ALL
SELECT 'Images' as table_name, COUNT(*) as count FROM vehicle_images
UNION ALL
SELECT 'License Photos' as table_name, COUNT(*) as count FROM agency_license_photos;

-- View all vehicles with details
SELECT 
    v.id,
    v.vehicle_type,
    v.vehicle_model,
    v.vehicle_no,
    v.capacity as seats,
    v.base_price as price_per_day,
    v.price_per_kilometer as price_per_km,
    v.is_verified,
    v.availability as available,
    va.agency_name as rental_agency,
    va.city as location
FROM vehicles v
LEFT JOIN vehicle_agencies va ON v.agency_id = va.id
ORDER BY v.id;

-- View amenities per vehicle
SELECT 
    v.vehicle_no,
    v.vehicle_model,
    STRING_AGG(vam.amenity, ', ' ORDER BY vam.amenity) as amenities
FROM vehicles v
LEFT JOIN vehicle_amenities vam ON v.id = vam.vehicle_id
GROUP BY v.id, v.vehicle_no, v.vehicle_model
ORDER BY v.id;

-- Success message
DO $$ 
BEGIN 
    RAISE NOTICE '✅ Vehicle data import completed successfully!';
    RAISE NOTICE '   - 3 Agencies added';
    RAISE NOTICE '   - 5 Vehicles added';
    RAISE NOTICE '   - 31 Amenities added';
    RAISE NOTICE '   - 10 Images added';
    RAISE NOTICE '   - 6 License photos added';
END $$;
