-- ============================================
-- Sample Data for Travel Application
-- Hotels, Rooms, and Vehicles
-- ============================================

-- ============================================
-- 1. HOTELS DATA
-- ============================================

-- Insert sample hotels
INSERT INTO hotels (name, location, description, address, city, province, postal_code, rating, price_per_night, phone, email, website, amenities, check_in_time, check_out_time, is_verified, is_available, created_at)
VALUES 
-- Colombo Hotels
('Cinnamon Grand Colombo', 'Colombo', 'Luxury 5-star hotel in the heart of Colombo with world-class amenities and service', '77 Galle Road', 'Colombo', 'Western', '00300', 5.0, 35000.00, '+94112437437', 'info@cinnamongrand.lk', 'www.cinnamonhotels.com', 'Free WiFi,Swimming Pool,Spa,Gym,Restaurant,Bar,Room Service,Airport Shuttle,Business Center,Parking', '14:00:00', '12:00:00', true, true, NOW()),

('Hilton Colombo', 'Colombo', 'Iconic 5-star waterfront hotel with stunning views of the Indian Ocean', '2 Sir Chittampalam A Gardiner Mawatha', 'Colombo', 'Western', '00200', 4.8, 32000.00, '+94112492492', 'reservations.colombo@hilton.com', 'www.hilton.com', 'Free WiFi,Infinity Pool,Spa,Fitness Center,Multiple Restaurants,Lounge Bar,24/7 Room Service,Concierge,Business Center,Valet Parking', '15:00:00', '12:00:00', true, true, NOW()),

('Galle Face Hotel', 'Colombo', 'Historic luxury hotel established in 1864, offering colonial charm with modern comforts', '2 Galle Road', 'Colombo', 'Western', '00400', 4.6, 28000.00, '+94112541010', 'reservations@gallefacehotel.com', 'www.gallefacehotel.com', 'Free WiFi,Ocean View Pool,Spa,Gym,Fine Dining,Bar,Room Service,Heritage Tours,Event Spaces,Parking', '14:00:00', '11:00:00', true, true, NOW()),

-- Kandy Hotels
('Earl''s Regency Hotel', 'Kandy', 'Scenic resort hotel surrounded by mountains and tea plantations', 'Tennekumbura', 'Kandy', 'Central', '20000', 4.7, 22000.00, '+94812233009', 'info@earlsregency.com', 'www.earlsregency.com', 'Free WiFi,Swimming Pool,Spa,Traditional Restaurant,Cultural Shows,Garden,Parking,Mountain Views', '14:00:00', '12:00:00', true, true, NOW()),

('Cinnamon Citadel Kandy', 'Kandy', 'Riverside luxury resort offering tranquil views of Mahaweli River', '124 Srimath Kuda Ratwatte Mawatha', 'Kandy', 'Central', '20000', 4.5, 24000.00, '+94812234365', 'citadel@cinnamonhotels.com', 'www.cinnamonhotels.com', 'Free WiFi,River View Pool,Spa,Fine Dining,Bar,Ayurveda Center,Boat Rides,Yoga,Parking', '14:00:00', '12:00:00', true, true, NOW()),

-- Galle Hotels
('Jetwing Lighthouse', 'Galle', 'Clifftop luxury hotel designed by Geoffrey Bawa with breathtaking ocean views', 'Dadella', 'Galle', 'Southern', '80000', 4.9, 30000.00, '+94912234744', 'lighthouse@jetwinghotels.com', 'www.jetwinghotels.com', 'Free WiFi,Clifftop Pool,Spa,Geoffrey Bawa Architecture,Fine Dining,Ocean Views,Yoga,Library,Art Gallery,Parking', '14:00:00', '12:00:00', true, true, NOW()),

('Fort Bazaar', 'Galle Fort', 'Boutique hotel within UNESCO World Heritage Galle Fort', '4 Church Street', 'Galle', 'Southern', '80000', 4.7, 26000.00, '+94912244644', 'info@fortbazaar.lk', 'www.fortbazaar.lk', 'Free WiFi,Rooftop Pool,Restaurant,Fort Views,Historical Setting,Spa Services,Library,Art Gallery', '14:00:00', '11:00:00', true, true, NOW()),

-- Negombo Hotels
('Jetwing Blue', 'Negombo', 'Beachfront resort perfect for first and last nights, close to airport', 'Ethukala', 'Negombo', 'Western', '11500', 4.4, 18000.00, '+94312279000', 'blue@jetwinghotels.com', 'www.jetwinghotels.com', 'Free WiFi,Beach Access,Swimming Pool,Spa,Restaurant,Bar,Water Sports,Airport Transfer,Parking', '14:00:00', '12:00:00', true, true, NOW()),

('Heritance Negombo', 'Negombo', 'Contemporary beachfront hotel with modern amenities near airport', 'Ethukala', 'Negombo', 'Western', '11500', 4.3, 16000.00, '+94312279000', 'info@heritancehotels.com', 'www.heritancehotels.com', 'Free WiFi,Private Beach,Pool,Spa,Multiple Restaurants,Kids Club,Water Sports,Gym,Parking', '15:00:00', '12:00:00', true, true, NOW()),

-- Ella Hotels
('98 Acres Resort', 'Ella', 'Eco-luxury resort nestled in tea plantations with panoramic views', 'Greenlands Estate', 'Ella', 'Uva', '90900', 4.8, 25000.00, '+94572050050', 'info@98acresresort.com', 'www.98acresresort.com', 'Free WiFi,Infinity Pool,Spa,Organic Restaurant,Tea Plantation Tours,Trekking,Nature Trails,Yoga', '14:00:00', '11:00:00', true, true, NOW()),

('Ella Jungle Resort', 'Ella', 'Boutique resort with treehouse-style rooms in natural jungle setting', 'Kithalella', 'Ella', 'Uva', '90900', 4.5, 15000.00, '+94552050400', 'info@ellajungleresort.com', 'www.ellajungleresort.com', 'Free WiFi,Natural Pool,Restaurant,Jungle Views,Trekking,Bird Watching,Waterfall Tours,Organic Garden', '14:00:00', '12:00:00', true, true, NOW()),

-- Nuwara Eliya Hotels
('Grand Hotel', 'Nuwara Eliya', 'Colonial-era luxury hotel in Little England of Sri Lanka', 'Grand Hotel Road', 'Nuwara Eliya', 'Central', '22200', 4.6, 20000.00, '+94522222881', 'info@grandhotel.lk', 'www.grandhotel.lk', 'Free WiFi,Fireplace Rooms,Fine Dining,English Garden,Golf Course,Billiards,Colonial Architecture,Spa', '14:00:00', '11:00:00', true, true, NOW()),

('Heritance Tea Factory', 'Nuwara Eliya', 'Unique hotel converted from a working tea factory', 'Kandapola', 'Nuwara Eliya', 'Central', '22220', 4.7, 23000.00, '+94522299600', 'teafactory@heritancehotels.com', 'www.heritancehotels.com', 'Free WiFi,Tea Factory Museum,Spa,Restaurant,Mountain Views,Tea Tasting,Trekking,Fireplaces,Parking', '14:00:00', '12:00:00', true, true, NOW()),

-- Sigiriya Hotels
('Aliya Resort & Spa', 'Sigiriya', 'Luxury resort with views of Sigiriya Rock', 'Kimbissa', 'Sigiriya', 'Central', '21120', 4.6, 27000.00, '+94662289999', 'info@aliyaresort.com', 'www.aliyaresort.com', 'Free WiFi,Sigiriya View Pool,Spa,Fine Dining,Cultural Shows,Rock Fortress Views,Ayurveda,Village Tours,Parking', '14:00:00', '12:00:00', true, true, NOW()),

('Jetwing Vil Uyana', 'Sigiriya', 'Eco-luxury resort with dwellings built on marshes and forests', 'Inamaluwa', 'Sigiriya', 'Central', '21120', 4.8, 35000.00, '+94662492814', 'viluyana@jetwinghotels.com', 'www.jetwinghotels.com', 'Free WiFi,Private Plunge Pools,Nature Reserve,Organic Restaurant,Spa,Bird Watching,Nature Walks,Wildlife,Eco-Luxury', '14:00:00', '11:00:00', true, true, NOW()),

-- Yala Hotels
('Cinnamon Wild Yala', 'Yala', 'Safari resort on the edge of Yala National Park', 'Palatupana', 'Yala', 'Southern', '82600', 4.5, 28000.00, '+94472239449', 'wild@cinnamonhotels.com', 'www.cinnamonhotels.com', 'Free WiFi,Wildlife Pool,Restaurant,Safari Jeep Tours,National Park Access,Beach Access,Nature Trails,Bar,Parking', '14:00:00', '12:00:00', true, true, NOW())

ON CONFLICT (name, location) DO NOTHING;

-- ============================================
-- 2. ROOMS DATA
-- ============================================

-- Get hotel IDs for room insertion
DO $$
DECLARE
    h_cinnamon_grand_id INTEGER;
    h_hilton_colombo_id INTEGER;
    h_galle_face_id INTEGER;
    h_earls_regency_id INTEGER;
    h_cinnamon_citadel_id INTEGER;
    h_jetwing_lighthouse_id INTEGER;
    h_fort_bazaar_id INTEGER;
    h_jetwing_blue_id INTEGER;
    h_heritance_negombo_id INTEGER;
    h_98_acres_id INTEGER;
    h_ella_jungle_id INTEGER;
    h_grand_hotel_id INTEGER;
    h_tea_factory_id INTEGER;
    h_aliya_resort_id INTEGER;
    h_jetwing_viluyana_id INTEGER;
    h_cinnamon_wild_id INTEGER;
BEGIN
    -- Get hotel IDs
    SELECT id INTO h_cinnamon_grand_id FROM hotels WHERE name = 'Cinnamon Grand Colombo' LIMIT 1;
    SELECT id INTO h_hilton_colombo_id FROM hotels WHERE name = 'Hilton Colombo' LIMIT 1;
    SELECT id INTO h_galle_face_id FROM hotels WHERE name = 'Galle Face Hotel' LIMIT 1;
    SELECT id INTO h_earls_regency_id FROM hotels WHERE name = 'Earl''s Regency Hotel' LIMIT 1;
    SELECT id INTO h_cinnamon_citadel_id FROM hotels WHERE name = 'Cinnamon Citadel Kandy' LIMIT 1;
    SELECT id INTO h_jetwing_lighthouse_id FROM hotels WHERE name = 'Jetwing Lighthouse' LIMIT 1;
    SELECT id INTO h_fort_bazaar_id FROM hotels WHERE name = 'Fort Bazaar' LIMIT 1;
    SELECT id INTO h_jetwing_blue_id FROM hotels WHERE name = 'Jetwing Blue' LIMIT 1;
    SELECT id INTO h_heritance_negombo_id FROM hotels WHERE name = 'Heritance Negombo' LIMIT 1;
    SELECT id INTO h_98_acres_id FROM hotels WHERE name = '98 Acres Resort' LIMIT 1;
    SELECT id INTO h_ella_jungle_id FROM hotels WHERE name = 'Ella Jungle Resort' LIMIT 1;
    SELECT id INTO h_grand_hotel_id FROM hotels WHERE name = 'Grand Hotel' LIMIT 1;
    SELECT id INTO h_tea_factory_id FROM hotels WHERE name = 'Heritance Tea Factory' LIMIT 1;
    SELECT id INTO h_aliya_resort_id FROM hotels WHERE name = 'Aliya Resort & Spa' LIMIT 1;
    SELECT id INTO h_jetwing_viluyana_id FROM hotels WHERE name = 'Jetwing Vil Uyana' LIMIT 1;
    SELECT id INTO h_cinnamon_wild_id FROM hotels WHERE name = 'Cinnamon Wild Yala' LIMIT 1;

    -- Cinnamon Grand Colombo Rooms
    IF h_cinnamon_grand_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_cinnamon_grand_id, 'Deluxe Room', 'King Bed', 'Spacious deluxe room with modern amenities and city views', 2, 35, 35000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Work Desk,Tea/Coffee Maker,Bathroom Amenities', true, 20, 15),
        (h_cinnamon_grand_id, 'Premium Room', 'Twin Beds', 'Premium room with enhanced comfort and partial city views', 2, 38, 38000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Work Desk,Tea/Coffee Maker,Bathroom Amenities,Balcony', true, 15, 10),
        (h_cinnamon_grand_id, 'Executive Suite', 'King Bed', 'Luxurious suite with separate living area and executive lounge access', 2, 55, 50000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,Smart TV,Living Area,Dining Area,Premium Bathroom,Executive Lounge Access,Butler Service', true, 10, 7),
        (h_cinnamon_grand_id, 'Royal Suite', 'King Bed', 'Ultimate luxury with panoramic city views and exclusive services', 3, 85, 75000.00, 'Air Conditioning,Full Bar,Safe,WiFi,Smart TV,Separate Living Room,Dining Room,Jacuzzi,Walk-in Closet,Butler Service,Airport Transfers', true, 5, 3);
    END IF;

    -- Hilton Colombo Rooms
    IF h_hilton_colombo_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_hilton_colombo_id, 'Standard Room', 'King Bed', 'Comfortable room with contemporary design and city or ocean views', 2, 32, 32000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Work Desk,Coffee Maker,Premium Toiletries', true, 25, 18),
        (h_hilton_colombo_id, 'Ocean View Room', 'King Bed', 'Stunning ocean-facing room with floor-to-ceiling windows', 2, 36, 40000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Work Desk,Coffee Maker,Premium Toiletries,Ocean View,Balcony', true, 20, 14),
        (h_hilton_colombo_id, 'Executive Room', 'King Bed', 'Enhanced room with executive lounge access and additional amenities', 2, 40, 45000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,Smart TV,Work Desk,Coffee Maker,Premium Toiletries,Executive Lounge,Complimentary Breakfast', true, 12, 8),
        (h_hilton_colombo_id, 'Presidential Suite', 'King Bed', 'Opulent two-bedroom suite with butler service', 4, 120, 95000.00, 'Air Conditioning,Full Bar,Safe,WiFi,Smart TV,Living Room,Dining Room,Kitchen,Jacuzzi,Butler Service,Ocean View,Airport Transfer', true, 3, 2);
    END IF;

    -- Jetwing Lighthouse Rooms (Galle)
    IF h_jetwing_lighthouse_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_jetwing_lighthouse_id, 'Deluxe Room', 'King Bed', 'Geoffrey Bawa designed room with ocean or garden views', 2, 38, 30000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Sitting Area,Tea/Coffee,Bathroom with Bathtub,Garden or Ocean View', true, 30, 22),
        (h_jetwing_lighthouse_id, 'Ocean View Suite', 'King Bed', 'Spacious suite with panoramic ocean views', 2, 52, 42000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Separate Living Area,Tea/Coffee,Premium Bathroom,Private Balcony,Ocean View', true, 12, 8),
        (h_jetwing_lighthouse_id, 'Spa Suite', 'King Bed', 'Luxury suite with in-room spa facilities', 2, 58, 48000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Living Area,In-room Spa,Jacuzzi,Premium Bathroom,Ocean View,Private Balcony', true, 8, 5);
    END IF;

    -- 98 Acres Resort Rooms (Ella)
    IF h_98_acres_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_98_acres_id, 'Deluxe Chalet', 'King Bed', 'Private chalet with tea plantation views', 2, 40, 25000.00, 'Air Conditioning,Mini Bar,WiFi,TV,Private Deck,Tea/Coffee,Panoramic Views,Premium Toiletries', true, 20, 15),
        (h_98_acres_id, 'Luxury Chalet', 'King Bed', 'Spacious chalet with enhanced amenities and views', 2, 48, 32000.00, 'Air Conditioning,Mini Bar,WiFi,TV,Private Deck,Tea/Coffee,Panoramic Views,Bathtub,Premium Toiletries,Fireplace', true, 10, 7),
        (h_98_acres_id, 'Family Chalet', 'King + Twin', 'Two-bedroom chalet perfect for families', 4, 65, 40000.00, 'Air Conditioning,Mini Bar,WiFi,TV,Private Deck,Tea/Coffee,Living Area,Two Bathrooms,Panoramic Views', true, 5, 3);
    END IF;

    -- Jetwing Blue Rooms (Negombo)
    IF h_jetwing_blue_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_jetwing_blue_id, 'Superior Room', 'King Bed', 'Comfortable room with modern amenities', 2, 30, 18000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Work Desk,Tea/Coffee,Bathroom Amenities', true, 35, 25),
        (h_jetwing_blue_id, 'Deluxe Room', 'King Bed', 'Enhanced room with beach or pool views', 2, 34, 22000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Work Desk,Tea/Coffee,Bathroom Amenities,Balcony,Beach/Pool View', true, 25, 18),
        (h_jetwing_blue_id, 'Family Room', 'King + Twin', 'Spacious room for families with connecting option', 4, 45, 28000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Tea/Coffee,Bathroom Amenities,Balcony,Extra Bed', true, 10, 6);
    END IF;

    -- More rooms for other hotels...
    -- (Adding rooms for remaining hotels)

    -- Grand Hotel Nuwara Eliya
    IF h_grand_hotel_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_grand_hotel_id, 'Classic Room', 'Twin Beds', 'Colonial-style room with fireplace', 2, 28, 20000.00, 'Fireplace,WiFi,TV,Tea/Coffee,Writing Desk,Colonial Decor,Bathroom Amenities', true, 25, 18),
        (h_grand_hotel_id, 'Deluxe Room', 'King Bed', 'Spacious room with garden views and fireplace', 2, 35, 25000.00, 'Fireplace,WiFi,TV,Tea/Coffee,Sitting Area,Garden View,Premium Bathroom,Colonial Decor', true, 15, 10),
        (h_grand_hotel_id, 'Heritage Suite', 'King Bed', 'Luxurious suite with period furniture and mountain views', 2, 50, 35000.00, 'Fireplace,WiFi,TV,Separate Living Room,Tea/Coffee,Mountain View,Bathtub,Colonial Furniture,Butler Service', true, 8, 5);
    END IF;

    -- Cinnamon Wild Yala
    IF h_cinnamon_wild_id IS NOT NULL THEN
        INSERT INTO rooms (hotel_id, room_type, bed_type, description, max_occupancy, size_sqm, price_per_night, amenities, is_available, total_rooms, available_rooms)
        VALUES 
        (h_cinnamon_wild_id, 'Superior Room', 'King Bed', 'Safari-themed room with nature views', 2, 32, 28000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Private Deck,Tea/Coffee,Bathroom Amenities,Wildlife Views', true, 30, 22),
        (h_cinnamon_wild_id, 'Deluxe Room', 'King Bed', 'Enhanced room with better wildlife viewing', 2, 38, 34000.00, 'Air Conditioning,Mini Bar,Safe,WiFi,TV,Private Deck,Tea/Coffee,Premium Bathroom,Wildlife Views,Binoculars', true, 20, 14),
        (h_cinnamon_wild_id, 'Luxury Tent', 'King Bed', 'Glamping experience with modern comforts', 2, 42, 40000.00, 'Air Conditioning,Mini Bar,WiFi,Private Deck,Outdoor Shower,Wildlife Views,Safari Decor,Premium Amenities', true, 10, 7);
    END IF;

END $$;

-- ============================================
-- 3. VEHICLE AGENCIES DATA
-- ============================================

INSERT INTO vehicle_agencies (name, description, address, city, phone, email, license_number, rating, is_verified, created_at)
VALUES 
('ABC Travels', 'Premium vehicle rental service with modern fleet and experienced drivers', 'No. 45, Galle Road, Colombo 03', 'Colombo', '+94112345678', 'info@abctravels.lk', 'VA-12345', 4.8, true, NOW()),
('Ceylon Safari Tours', 'Specialized in safari and wildlife tour vehicles', 'No. 89, Katugastota Road, Kandy', 'Kandy', '+94812223344', 'info@ceylonsafari.lk', 'VA-23456', 4.7, true, NOW()),
('Island Wheels', 'Reliable vehicle rental with island-wide service', 'No. 23, Main Street, Negombo', 'Negombo', '+94312225566', 'bookings@islandwheels.lk', 'VA-34567', 4.6, true, NOW()),
('Luxury Lanka Tours', 'Premium luxury vehicles for discerning travelers', 'No. 67, Duplication Road, Colombo 04', 'Colombo', '+94112998877', 'info@luxurylanka.lk', 'VA-45678', 4.9, true, NOW()),
('Budget Car Rentals', 'Affordable vehicle options for budget travelers', 'No. 12, Airport Road, Katunayake', 'Katunayake', '+94312334455', 'info@budgetcars.lk', 'VA-56789', 4.3, true, NOW())
ON CONFLICT (license_number) DO NOTHING;

-- ============================================
-- 4. VEHICLES DATA
-- ============================================

DO $$
DECLARE
    agency_abc_id INTEGER;
    agency_safari_id INTEGER;
    agency_island_id INTEGER;
    agency_luxury_id INTEGER;
    agency_budget_id INTEGER;
BEGIN
    -- Get agency IDs
    SELECT id INTO agency_abc_id FROM vehicle_agencies WHERE name = 'ABC Travels' LIMIT 1;
    SELECT id INTO agency_safari_id FROM vehicle_agencies WHERE name = 'Ceylon Safari Tours' LIMIT 1;
    SELECT id INTO agency_island_id FROM vehicle_agencies WHERE name = 'Island Wheels' LIMIT 1;
    SELECT id INTO agency_luxury_id FROM vehicle_agencies WHERE name = 'Luxury Lanka Tours' LIMIT 1;
    SELECT id INTO agency_budget_id FROM vehicle_agencies WHERE name = 'Budget Car Rentals' LIMIT 1;

    -- ABC Travels Vehicles
    IF agency_abc_id IS NOT NULL THEN
        INSERT INTO vehicles (vehicle_agency_id, vehicle_type, brand, model, year, color, license_plate, capacity, price_per_day, fuel_type, transmission, features, is_air_conditioned, is_verified, is_available, description)
        VALUES 
        (agency_abc_id, 'Van', 'Toyota', 'KDH High Roof', 2022, 'White', 'CAR-1234', 12, 15000.00, 'Diesel', 'Manual', 'Air Conditioning,Comfortable Seating,Music System,USB Charging,Luggage Space,Experienced Driver', true, true, true, 'Spacious 12-seater van perfect for group tours with ample luggage space'),
        (agency_abc_id, 'SUV', 'Toyota', 'Fortuner', 2023, 'Silver', 'CAR-1235', 7, 18000.00, 'Diesel', 'Automatic', 'Air Conditioning,Leather Seats,GPS,Music System,USB Charging,Sunroof,Experienced Driver', true, true, true, 'Luxury SUV for comfortable family or small group tours'),
        (agency_abc_id, 'Car', 'Toyota', 'Axio', 2021, 'Black', 'CAR-1236', 4, 8000.00, 'Petrol', 'Automatic', 'Air Conditioning,Music System,USB Charging,GPS,Economical,Experienced Driver', true, true, true, 'Economical sedan for couples or small families'),
        (agency_abc_id, 'Van', 'Nissan', 'Caravan', 2022, 'White', 'CAR-1237', 10, 13000.00, 'Diesel', 'Manual', 'Air Conditioning,Reclining Seats,Music System,USB Charging,Luggage Space,Experienced Driver', true, true, true, '10-seater van ideal for medium-sized groups');
    END IF;

    -- Ceylon Safari Tours Vehicles
    IF agency_safari_id IS NOT NULL THEN
        INSERT INTO vehicles (vehicle_agency_id, vehicle_type, brand, model, year, color, license_plate, capacity, price_per_day, fuel_type, transmission, features, is_air_conditioned, is_verified, is_available, description)
        VALUES 
        (agency_safari_id, 'Jeep', 'Toyota', 'Land Cruiser', 2023, 'Green', 'JEP-2001', 6, 20000.00, 'Diesel', 'Manual', 'Open Roof,Binoculars,Wildlife Guide,Photography Assistance,Off-road Capable,Experienced Safari Driver', false, true, true, 'Rugged safari jeep perfect for wildlife watching in national parks'),
        (agency_safari_id, 'SUV', 'Mitsubishi', 'Montero', 2022, 'Silver', 'SUV-2002', 7, 16000.00, 'Diesel', 'Automatic', 'Air Conditioning,High Clearance,Safari Package,Binoculars,Wildlife Guide,Experienced Driver', true, true, true, 'Comfortable SUV for safaris with AC option'),
        (agency_safari_id, 'Jeep', 'Mahindra', 'Thar', 2021, 'Olive', 'JEP-2003', 4, 14000.00, 'Diesel', 'Manual', 'Open Top,Off-road,Photography Platform,Wildlife Guide,Experienced Safari Driver', false, true, true, 'Compact safari jeep for adventurous small groups');
    END IF;

    -- Island Wheels Vehicles
    IF agency_island_id IS NOT NULL THEN
        INSERT INTO vehicles (vehicle_agency_id, vehicle_type, brand, model, year, color, license_plate, capacity, price_per_day, fuel_type, transmission, features, is_air_conditioned, is_verified, is_available, description)
        VALUES 
        (agency_island_id, 'Van', 'Toyota', 'Hiace', 2022, 'White', 'VAN-3001', 14, 16000.00, 'Diesel', 'Manual', 'Air Conditioning,Spacious,Music System,USB Charging,Large Luggage Space,Experienced Driver', true, true, true, 'Large capacity van for big groups'),
        (agency_island_id, 'Car', 'Honda', 'Civic', 2021, 'Blue', 'CAR-3002', 4, 9000.00, 'Petrol', 'Automatic', 'Air Conditioning,Music System,GPS,USB Charging,Comfortable,Experienced Driver', true, true, true, 'Comfortable sedan for city and long-distance travel'),
        (agency_island_id, 'Van', 'Nissan', 'NV350', 2023, 'Silver', 'VAN-3003', 12, 14500.00, 'Diesel', 'Manual', 'Air Conditioning,Reclining Seats,Music System,USB Charging,Experienced Driver', true, true, true, 'Modern 12-seater van with excellent comfort'),
        (agency_island_id, 'SUV', 'Mitsubishi', 'Pajero', 2022, 'Black', 'SUV-3004', 7, 17000.00, 'Diesel', 'Automatic', 'Air Conditioning,4WD,Leather Seats,Music System,GPS,Premium Comfort,Experienced Driver', true, true, true, 'Premium SUV for luxury travel');
    END IF;

    -- Luxury Lanka Tours Vehicles
    IF agency_luxury_id IS NOT NULL THEN
        INSERT INTO vehicles (vehicle_agency_id, vehicle_type, brand, model, year, color, license_plate, capacity, price_per_day, fuel_type, transmission, features, is_air_conditioned, is_verified, is_available, description)
        VALUES 
        (agency_luxury_id, 'Luxury Van', 'Mercedes-Benz', 'Sprinter', 2023, 'Black', 'LUX-4001', 8, 35000.00, 'Diesel', 'Automatic', 'Luxury Seating,Climate Control,Entertainment System,WiFi,Mini Bar,Premium Sound,Professional Chauffeur,Leather Interior', true, true, true, 'Ultimate luxury van with VIP amenities'),
        (agency_luxury_id, 'Luxury SUV', 'Land Rover', 'Range Rover', 2023, 'White', 'LUX-4002', 5, 40000.00, 'Diesel', 'Automatic', 'Luxury Interior,Climate Control,Premium Sound,Panoramic Roof,Massage Seats,Professional Chauffeur,WiFi', true, true, true, 'Premium luxury SUV for exclusive travel'),
        (agency_luxury_id, 'Luxury Car', 'BMW', '7 Series', 2023, 'Black', 'LUX-4003', 4, 38000.00, 'Petrol', 'Automatic', 'Executive Interior,Climate Control,Premium Audio,Massage Seats,WiFi,Professional Chauffeur,Champagne Service', true, true, true, 'Executive luxury sedan for business travelers'),
        (agency_luxury_id, 'Luxury Van', 'Toyota', 'Alphard', 2023, 'Pearl', 'LUX-4004', 7, 32000.00, 'Petrol', 'Automatic', 'Captain Seats,Entertainment System,Climate Control,Premium Interior,USB Charging,WiFi,Professional Chauffeur', true, true, true, 'Premium MPV with captain seats and luxury features');
    END IF;

    -- Budget Car Rentals Vehicles
    IF agency_budget_id IS NOT NULL THEN
        INSERT INTO vehicles (vehicle_agency_id, vehicle_type, brand, model, year, color, license_plate, capacity, price_per_day, fuel_type, transmission, features, is_air_conditioned, is_verified, is_available, description)
        VALUES 
        (agency_budget_id, 'Car', 'Suzuki', 'Alto', 2020, 'Red', 'ECO-5001', 4, 5000.00, 'Petrol', 'Manual', 'Air Conditioning,Music System,Economical,Basic Comfort,Experienced Driver', true, true, true, 'Budget-friendly compact car for economical travel'),
        (agency_budget_id, 'Van', 'Toyota', 'Dolphin', 2019, 'White', 'VAN-5002', 8, 10000.00, 'Diesel', 'Manual', 'Air Conditioning,Basic Comfort,Music System,Luggage Space,Experienced Driver', true, true, true, 'Affordable 8-seater van for budget groups'),
        (agency_budget_id, 'Car', 'Nissan', 'Sunny', 2020, 'Silver', 'CAR-5003', 4, 6000.00, 'Petrol', 'Manual', 'Air Conditioning,Music System,Economical,Basic Comfort,Experienced Driver', true, true, true, 'Economical sedan for budget-conscious travelers'),
        (agency_budget_id, 'Van', 'Mitsubishi', 'L300', 2019, 'White', 'VAN-5004', 10, 11000.00, 'Diesel', 'Manual', 'Air Conditioning,Basic Comfort,Luggage Space,Music System,Experienced Driver', true, true, true, 'Budget-friendly 10-seater van for larger groups');
    END IF;

END $$;

-- ============================================
-- 5. VERIFICATION QUERIES
-- ============================================

-- Count records
SELECT 'Hotels' as table_name, COUNT(*) as count FROM hotels
UNION ALL
SELECT 'Rooms' as table_name, COUNT(*) as count FROM rooms
UNION ALL
SELECT 'Vehicle Agencies' as table_name, COUNT(*) as count FROM vehicle_agencies
UNION ALL
SELECT 'Vehicles' as table_name, COUNT(*) as count FROM vehicles;

-- Show hotels with room count
SELECT 
    h.name as hotel_name,
    h.location,
    h.rating,
    h.price_per_night,
    COUNT(r.id) as room_types,
    SUM(r.total_rooms) as total_rooms
FROM hotels h
LEFT JOIN rooms r ON h.id = r.hotel_id
GROUP BY h.id, h.name, h.location, h.rating, h.price_per_night
ORDER BY h.location, h.name;

-- Show vehicle agencies with vehicle count
SELECT 
    va.name as agency_name,
    va.rating,
    COUNT(v.id) as vehicle_count,
    ARRAY_AGG(DISTINCT v.vehicle_type) as vehicle_types
FROM vehicle_agencies va
LEFT JOIN vehicles v ON va.id = v.vehicle_agency_id
GROUP BY va.id, va.name, va.rating
ORDER BY va.rating DESC;

-- Show price ranges
SELECT 
    'Hotels' as category,
    MIN(price_per_night) as min_price,
    MAX(price_per_night) as max_price,
    AVG(price_per_night) as avg_price
FROM hotels
UNION ALL
SELECT 
    'Rooms' as category,
    MIN(price_per_night) as min_price,
    MAX(price_per_night) as max_price,
    AVG(price_per_night) as avg_price
FROM rooms
UNION ALL
SELECT 
    'Vehicles' as category,
    MIN(price_per_day) as min_price,
    MAX(price_per_day) as max_price,
    AVG(price_per_day) as avg_price
FROM vehicles;

-- ============================================
-- DONE!
-- ============================================
