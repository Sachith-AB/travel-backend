-- Using existing user with ID = 1 for all records

-- Insert Hotels with real Sri Lankan data (all using user_id = 1)
INSERT INTO hotels (id, hotel_name, street, city, district, province, registration_no, is_verified, type, description, created_at, user_id) VALUES 
(1, 'Galle Face Hotel', 'Galle Face Centre Road', 'Colombo', 'Colombo', 'Western Province', 'HTL001', true, 'Luxury', 'Historic luxury hotel overlooking the Indian Ocean in the heart of Colombo', NOW(), 1),
(2, 'Jetwing Vil Uyana', 'Sigiriya Road', 'Sigiriya', 'Matale', 'Central Province', 'HTL002', true, 'Eco Resort', 'Unique eco-luxury resort built on stilts over wetlands near Sigiriya Rock', NOW(), 1),
(3, 'Amangalla', 'Church Street', 'Galle', 'Galle', 'Southern Province', 'HTL003', true, 'Heritage', 'Colonial heritage hotel within Galle Fort walls dating back to 1684', NOW(), 1),
(4, 'Ceylon Tea Trails', 'Dunkeld Estate', 'Hatton', 'Nuwara Eliya', 'Central Province', 'HTL004', true, 'Plantation Bungalow', 'Luxury tea plantation bungalows in the hill country', NOW(), 1),
(5, 'Anantara Peace Haven Tangalle', 'Tangalle Beach', 'Tangalle', 'Hambantota', 'Southern Province', 'HTL005', true, 'Beach Resort', 'Luxury beach resort on the pristine southern coast of Sri Lanka', NOW(), 1)
ON CONFLICT (id) DO NOTHING;

-- Insert Hotel Amenities
INSERT INTO hotel_amenities (hotel_id, amenity) VALUES 
(1, 'Swimming Pool'), (1, 'Spa'), (1, 'Restaurant'), (1, 'Bar'), (1, 'Fitness Center'), (1, 'Business Center'), (1, 'WiFi'),
(2, 'Swimming Pool'), (2, 'Spa'), (2, 'Restaurant'), (2, 'Nature Walks'), (2, 'Bird Watching'), (2, 'WiFi'),
(3, 'Swimming Pool'), (3, 'Spa'), (3, 'Restaurant'), (3, 'Historical Tours'), (3, 'Library'), (3, 'WiFi'),
(4, 'Tea Factory Tours'), (4, 'Restaurant'), (4, 'Nature Walks'), (4, 'Butler Service'), (4, 'WiFi'),
(5, 'Swimming Pool'), (5, 'Spa'), (5, 'Restaurant'), (5, 'Water Sports'), (5, 'Beach Access'), (5, 'WiFi')
ON CONFLICT DO NOTHING;

-- Insert Hotel Images
INSERT INTO hotel_images (hotel_id, image_url) VALUES 
(1, 'https://example.com/galle-face-exterior.jpg'), (1, 'https://example.com/galle-face-lobby.jpg'),
(2, 'https://example.com/vil-uyana-stilts.jpg'), (2, 'https://example.com/vil-uyana-wetlands.jpg'),
(3, 'https://example.com/amangalla-fort.jpg'), (3, 'https://example.com/amangalla-courtyard.jpg'),
(4, 'https://example.com/tea-trails-bungalow.jpg'), (4, 'https://example.com/tea-trails-plantation.jpg'),
(5, 'https://example.com/tangalle-beach.jpg'), (5, 'https://example.com/tangalle-pool.jpg')
ON CONFLICT DO NOTHING;

-- Insert Guides with real Sri Lankan data (all using user_id = 1)
INSERT INTO guides (id, user_id, bio, experience_years, hours_rate, is_verified, is_available, slta_license_id, nic_number, created_at) VALUES 
(1, 1, 'Experienced guide specializing in cultural and historical tours of Sri Lanka. Born and raised in Kandy, I have deep knowledge of ancient temples, colonial architecture, and local traditions.', 8, 15.00, true, true, 'SLTA001', '198712345678', NOW()),
(2, 1, 'Wildlife and nature specialist guide with expertise in Sri Lankan national parks. I have guided visitors through Yala, Udawalawe, and Wilpattu for over 10 years.', 10, 18.00, true, true, 'SLTA002', '198523456789', NOW()),
(3, 1, 'Adventure and hiking guide specializing in mountain treks and tea country walks. Expert in Adams Peak, Knuckles Range, and Horton Plains expeditions.', 6, 12.00, true, true, 'SLTA003', '199034567890', NOW()),
(4, 1, 'Coastal and beach specialist covering the southern and western coasts. Expert in whale watching, surfing spots, and coastal culture from Negombo to Mirissa.', 7, 14.00, true, true, 'SLTA004', '198845678901', NOW()),
(5, 1, 'Cultural heritage guide specializing in UNESCO World Heritage sites including Anuradhapura, Polonnaruwa, Sigiriya, and Dambulla cave temples.', 12, 20.00, true, true, 'SLTA005', '198156789012', NOW())
ON CONFLICT (id) DO NOTHING;

-- Insert Guide Languages
INSERT INTO guid_languages (guid_id, language) VALUES 
(1, 'Sinhala'), (1, 'English'), (1, 'Tamil'),
(2, 'Sinhala'), (2, 'English'), (2, 'German'),
(3, 'Sinhala'), (3, 'English'), (3, 'French'),
(4, 'Sinhala'), (4, 'English'), (4, 'Japanese'),
(5, 'Sinhala'), (5, 'English'), (5, 'Tamil'), (5, 'Hindi')
ON CONFLICT DO NOTHING;

-- Insert Guide Specializations
INSERT INTO guid_specializations (guid_id, specialization) VALUES 
(1, 'Cultural Tours'), (1, 'Historical Sites'), (1, 'Temple Tours'),
(2, 'Wildlife Safari'), (2, 'Nature Photography'), (2, 'Bird Watching'),
(3, 'Adventure Tours'), (3, 'Hiking'), (3, 'Mountain Climbing'),
(4, 'Beach Tours'), (4, 'Whale Watching'), (4, 'Surfing'),
(5, 'Heritage Sites'), (5, 'Archaeological Tours'), (5, 'Ancient History')
ON CONFLICT DO NOTHING;

-- Insert Rooms with real Sri Lankan hotel room data
INSERT INTO rooms (id, hotel_id, room_type, max_guests, bed_types, description, price_per_night, availability, created_at) VALUES 
(1, 1, 'Ocean View Suite', '2', 'King Bed', 'Spacious suite with panoramic views of the Indian Ocean and Galle Face Green', 450.00, true, NOW()),
(2, 1, 'Heritage Room', '2', 'Twin Beds', 'Elegantly furnished room with colonial-era charm and modern amenities', 280.00, true, NOW()),
(3, 2, 'Wetland Villa', '4', 'King + Sofa Bed', 'Unique villa on stilts over natural wetlands with private terrace', 380.00, true, NOW()),
(4, 2, 'Forest Dwelling', '2', 'King Bed', 'Eco-friendly accommodation nestled within natural forest setting', 320.00, true, NOW()),
(5, 3, 'Fort Suite', '2', 'King Bed', 'Luxurious suite within the historic Galle Fort with antique furnishings', 520.00, true, NOW()),
(6, 4, 'Planter Bungalow', '6', 'Multiple Bedrooms', 'Entire colonial bungalow with butler service and tea plantation views', 800.00, true, NOW()),
(7, 5, 'Beach Villa', '4', 'King + Twin', 'Private villa with direct beach access and infinity pool', 680.00, true, NOW()),
(8, 1, 'City View Room', '2', 'Queen Bed', 'Comfortable room overlooking bustling Colombo city', 220.00, true, NOW()),
(9, 3, 'Courtyard Room', '2', 'King Bed', 'Peaceful room overlooking the hotel historic courtyard', 350.00, true, NOW()),
(10, 5, 'Ocean Pavilion', '2', 'King Bed', 'Beachfront pavilion with private deck and ocean views', 580.00, true, NOW())
ON CONFLICT (id) DO NOTHING;

-- Insert Room Amenities
INSERT INTO room_amenities (room_id, amenity) VALUES 
(1, 'Ocean View'), (1, 'Balcony'), (1, 'Mini Bar'), (1, 'Safe'), (1, 'WiFi'), (1, 'Air Conditioning'),
(2, 'Colonial Decor'), (2, 'Mini Bar'), (2, 'Safe'), (2, 'WiFi'), (2, 'Air Conditioning'),
(3, 'Private Terrace'), (3, 'Nature View'), (3, 'Mini Bar'), (3, 'WiFi'), (3, 'Fan'),
(4, 'Forest View'), (4, 'Eco Friendly'), (4, 'WiFi'), (4, 'Fan'),
(5, 'Fort View'), (5, 'Antique Furnishing'), (5, 'Mini Bar'), (5, 'Safe'), (5, 'WiFi'), (5, 'Air Conditioning'),
(6, 'Butler Service'), (6, 'Tea Plantation View'), (6, 'Fireplace'), (6, 'WiFi'),
(7, 'Beach Access'), (7, 'Private Pool'), (7, 'Mini Bar'), (7, 'Safe'), (7, 'WiFi'), (7, 'Air Conditioning'),
(8, 'City View'), (8, 'Mini Bar'), (8, 'Safe'), (8, 'WiFi'), (8, 'Air Conditioning'),
(9, 'Courtyard View'), (9, 'Historical Ambiance'), (9, 'Mini Bar'), (9, 'Safe'), (9, 'WiFi'), (9, 'Air Conditioning'),
(10, 'Beach Front'), (10, 'Private Deck'), (10, 'Mini Bar'), (10, 'Safe'), (10, 'WiFi'), (10, 'Air Conditioning')
ON CONFLICT DO NOTHING;

-- Insert Trips with real Sri Lankan destinations and data (all using user_id = 1)
INSERT INTO trips (id, trip_code, user_id, pickup_location, trip_start_date, trip_end_date, start_time, number_of_adults, number_of_kids, estimate_duration, distance_km, trip_status, full_name, email, phone, country, destination, duration, travel_style, group_type, budget_range, base_price, total_fare, guides) VALUES 
(1, 'TRP001', 1, 'Bandaranaike International Airport', '2025-08-15', '2025-08-22', '08:00:00', 2, 0, '7 days', 850, 'accepted', 'John Smith', 'john.smith@email.com', '+94771234567', 'Australia', 'Cultural Triangle Tour', '7 days', 'Cultural', 'Couple', '1500-2500', 1800.00, 2100.00, 1),
(2, 'TRP002', 1, 'Colombo City Center', '2025-08-18', '2025-08-25', '06:00:00', 4, 2, '7 days', 950, 'accepted', 'Sarah Johnson', 'sarah.johnson@email.com', '+94772345678', 'Canada', 'Wildlife & Beach Safari', '7 days', 'Adventure', 'Family', '2000-3500', 2800.00, 3200.00, 2),
(3, 'TRP003', 1, 'Negombo Beach', '2025-08-20', '2025-08-27', '07:30:00', 3, 0, '7 days', 1200, 'pending', 'Michael Brown', 'michael.brown@email.com', '+94773456789', 'UK', 'Hill Country & Tea Trails', '7 days', 'Scenic', 'Friends', '1200-2000', 1600.00, 1900.00, 3),
(4, 'TRP004', 1, 'Galle Fort', '2025-08-25', '2025-09-01', '09:00:00', 2, 1, '7 days', 680, 'ongoing', 'Emma Wilson', 'emma.wilson@email.com', '+94774567890', 'Germany', 'Southern Coast Explorer', '7 days', 'Relaxed', 'Family', '1800-2800', 2200.00, 2600.00, 4),
(5, 'TRP005', 1, 'Kandy City', '2025-09-01', '2025-09-08', '08:30:00', 6, 0, '7 days', 1100, 'completed', 'Robert Davis', 'robert.davis@email.com', '+94775678901', 'USA', 'Ancient Cities Heritage Tour', '7 days', 'Cultural', 'Group', '2500-4000', 3500.00, 4000.00, 5)
ON CONFLICT (id) DO NOTHING;

-- Insert Trip Places to Visit
INSERT INTO trip_places (trip_id, place_id) VALUES 
(1, 101), (1, 102), (1, 103), (1, 104), -- Sigiriya, Dambulla, Polonnaruwa, Anuradhapura
(2, 201), (2, 202), (2, 203), (2, 204), -- Yala, Udawalawe, Mirissa, Unawatuna
(3, 301), (3, 302), (3, 303), (3, 304), -- Kandy, Nuwara Eliya, Ella, Horton Plains
(4, 401), (4, 402), (4, 403), (4, 404), -- Galle, Weligama, Tangalle, Kataragama
(5, 501), (5, 502), (5, 503), (5, 504)  -- Anuradhapura, Polonnaruwa, Mihintale, Ritigala
ON CONFLICT DO NOTHING;

-- Insert Trip Selected Hotels (Many-to-Many relationship)
INSERT INTO trip_selected_hotels (trip_id, hotel_id) VALUES 
(1, 2), (1, 4), -- Sigiriya area hotels
(2, 1), (2, 5), -- Colombo and Southern coast
(3, 4), (3, 1), -- Hill country and Colombo
(4, 3), (4, 5), -- Galle and Southern coast
(5, 2), (5, 3)  -- Central and Southern heritage hotels
ON CONFLICT DO NOTHING;

-- Insert Trip Selected Rooms (Many-to-Many relationship)
INSERT INTO trip_selected_rooms (trip_id, room_id) VALUES 
(1, 3), (1, 6), -- Wetland villa and Planter's bungalow
(2, 1), (2, 7), -- Ocean view suite and Beach villa
(3, 6), (3, 8), -- Planter's bungalow and City view room
(4, 5), (4, 10), -- Fort suite and Ocean pavilion
(5, 4), (5, 9)  -- Forest dwelling and Courtyard room
ON CONFLICT DO NOTHING;

-- Insert Trip Interests
INSERT INTO trip_interests (trip_id, interest) VALUES 
(1, 'Ancient History'), (1, 'Archaeology'), (1, 'Culture'),
(2, 'Wildlife'), (2, 'Photography'), (2, 'Beach Activities'),
(3, 'Tea Plantation'), (3, 'Mountain Views'), (3, 'Train Rides'),
(4, 'Colonial History'), (4, 'Beach Relaxation'), (4, 'Local Cuisine'),
(5, 'Buddhist Heritage'), (5, 'Ancient Ruins'), (5, 'Meditation')
ON CONFLICT DO NOTHING;

-- Insert Vehicle Agencies with real Sri Lankan data
INSERT INTO vehicle_agencies (id, agency_name, user_id, street, city, district, province, registration_no, description, created_at) VALUES 
(1, 'Lanka Travel Services', 1, 'Galle Road', 'Colombo', 'Colombo', 'Western Province', 'VTA001', 'Premier vehicle rental service specializing in tourist transportation across Sri Lanka', NOW()),
(2, 'Island Tours Lanka', 1, 'Main Street', 'Kandy', 'Kandy', 'Central Province', 'VTA002', 'Family-owned vehicle rental business serving the hill country and cultural triangle', NOW()),
(3, 'Ceylon Car Rentals', 1, 'Beach Road', 'Negombo', 'Gampaha', 'Western Province', 'VTA003', 'Airport pickup specialists with modern fleet of cars and vans', NOW()),
(4, 'Southern Wheels', 1, 'Fort Road', 'Galle', 'Galle', 'Southern Province', 'VTA004', 'Southern coast vehicle rental experts with safari and beach tour vehicles', NOW()),
(5, 'Hill Country Motors', 1, 'Station Road', 'Nuwara Eliya', 'Nuwara Eliya', 'Central Province', 'VTA005', 'Mountain region specialists with 4WD vehicles for tea country tours', NOW())
ON CONFLICT (id) DO NOTHING;

-- Insert Vehicles with real Sri Lankan data
INSERT INTO vehicles (id, vehicle_type, vehicle_no, registration_no, price_per_kilometer, base_price, insurance_number, insurance_expiry_date, vehicle_model, is_verified, availability, capacity, agency_id, created_at) VALUES 
(1, 'Car', 'CAR-001', 'WP-CAB-1234', '50.00', 5000.00, 'INS001234', '2025-12-31', 'Toyota Axio', true, true, 4, 1, NOW()),
(2, 'Van', 'VAN-002', 'CP-CAR-5678', '75.00', 7500.00, 'INS005678', '2025-11-30', 'Toyota KDH Hiace', true, true, 8, 2, NOW()),
(3, 'SUV', 'SUV-003', 'WP-CAR-9012', '80.00', 8000.00, 'INS009012', '2026-01-15', 'Toyota Prado', true, true, 7, 3, NOW()),
(4, 'Bus', 'BUS-004', 'SP-BUS-3456', '60.00', 12000.00, 'INS003456', '2025-10-20', 'Tata LP 1109', true, true, 25, 4, NOW()),
(5, 'Car', 'CAR-005', 'CP-CAB-7890', '55.00', 5500.00, 'INS007890', '2025-09-25', 'Honda Vezel', true, true, 5, 5, NOW())
ON CONFLICT (id) DO NOTHING;

-- Insert Vehicle Amenities
INSERT INTO vehicle_amenities (vehicle_id, amenity) VALUES 
(1, 'Air Conditioning'), (1, 'Radio'), (1, 'GPS Navigation'), (1, 'First Aid Kit'),
(2, 'Air Conditioning'), (2, 'Sound System'), (2, 'Cooler Box'), (2, 'Comfortable Seating'), (2, 'Storage Space'),
(3, 'Air Conditioning'), (3, 'Sound System'), (3, '4WD'), (3, 'GPS Navigation'), (3, 'Roof Rack'), (3, 'Cooler Box'),
(4, 'Air Conditioning'), (4, 'Sound System'), (4, 'Comfortable Seating'), (4, 'Storage Space'), (4, 'Microphone'),
(5, 'Air Conditioning'), (5, 'Sound System'), (5, 'GPS Navigation'), (5, 'Reverse Camera'), (5, 'USB Charging')
ON CONFLICT DO NOTHING;

-- Insert Vehicle Images
INSERT INTO vehicle_images (vehicle_id, image_url) VALUES 
(1, 'https://example.com/toyota-axio-exterior.jpg'), (1, 'https://example.com/toyota-axio-interior.jpg'),
(2, 'https://example.com/hiace-van-exterior.jpg'), (2, 'https://example.com/hiace-van-interior.jpg'),
(3, 'https://example.com/prado-suv-exterior.jpg'), (3, 'https://example.com/prado-suv-interior.jpg'),
(4, 'https://example.com/tata-bus-exterior.jpg'), (4, 'https://example.com/tata-bus-interior.jpg'),
(5, 'https://example.com/honda-vezel-exterior.jpg'), (5, 'https://example.com/honda-vezel-interior.jpg')
ON CONFLICT DO NOTHING;
