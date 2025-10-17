package com.travel.travel.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Guid;
import com.travel.travel.Repository.GuidRepository;
import com.travel.travel.Service.GuidService;

@Service
public class GuidServiceImpl implements GuidService {

    @Autowired
    private GuidRepository guidRepository;

    @Override
    public Guid createGuid(Guid guid) throws Exception {
        return guidRepository.save(guid);
    }

    @Override
    public Guid getGuidById(Long id) {
        return guidRepository.findById(id).orElse(null);
    }

    @Override
    public Guid getGuidByUserId(Long userId) {
        return guidRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public List<Guid> getAllGuids() {
        return guidRepository.findAll();
    }

    @Override
    public List<Guid> getAllGuidsWithFilters(java.util.Map<String, String> filters) {
        List<Guid> all = guidRepository.findAll();

        // Apply simple filters in-memory for now
        java.util.stream.Stream<Guid> stream = all.stream();

        String search = filters.getOrDefault("search", "").trim();
        if (!search.isEmpty()) {
            String s = search.toLowerCase();
            stream = stream.filter(g -> {
                String name = g.getUser() != null ? ((g.getUser().getFirstName() == null ? "" : g.getUser().getFirstName()) + " " + (g.getUser().getLastName() == null ? "" : g.getUser().getLastName())).toLowerCase() : "";
                String bio = g.getBio() != null ? g.getBio().toLowerCase() : "";
                return name.contains(s) || bio.contains(s);
            });
        }

        String language = filters.getOrDefault("language", "").trim();
        if (!language.isEmpty() && !language.equalsIgnoreCase("All Languages")) {
            String lang = language.toLowerCase();
            stream = stream.filter(g -> g.getLanguagesSpoken() != null && g.getLanguagesSpoken().stream().anyMatch(l -> l.toLowerCase().contains(lang)));
        }

        // rating stored? If not, skip. Assuming rating field exists on Guid as Double rating
        String minRating = filters.getOrDefault("minRating", "").trim();
        if (!minRating.isEmpty()) {
            try {
                double r = Double.parseDouble(minRating);
                stream = stream.filter(g -> {
                    try {
                        java.lang.reflect.Field f = g.getClass().getDeclaredField("rating");
                        f.setAccessible(true);
                        Object val = f.get(g);
                        if (val == null) {
                            // No rating available for this guide; skip filtering so guide remains in results
                            return true;
                        }
                        double rv = Double.parseDouble(val.toString());
                        return rv >= r;
                    } catch (NoSuchFieldException nsfe) {
                        // Guide model doesn't have a rating field; skip filtering
                        return true;
                    } catch (Exception e) {
                        // On any other reflection/parsing error, skip filtering for safety
                        return true;
                    }
                });
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        String minPrice = filters.getOrDefault("minPrice", "").trim();
        String maxPrice = filters.getOrDefault("maxPrice", "").trim();
        if (!minPrice.isEmpty() || !maxPrice.isEmpty()) {
            try {
                double lo = minPrice.isEmpty() ? Double.NEGATIVE_INFINITY : Double.parseDouble(minPrice);
                double hi = maxPrice.isEmpty() ? Double.POSITIVE_INFINITY : Double.parseDouble(maxPrice);
                stream = stream.filter(g -> {
                    Double price = g.getHoursRate();
                    if (price == null) return false;
                    return price >= lo && price <= hi;
                });
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        java.util.List<Guid> result = stream.collect(java.util.stream.Collectors.toList());

        String sortBy = filters.getOrDefault("sortBy", "").trim();
        if (!sortBy.isEmpty()) {
            switch (sortBy) {
                case "Lowest Price":
                    result.sort(java.util.Comparator.comparing(g -> g.getHoursRate() == null ? Double.MAX_VALUE : g.getHoursRate()));
                    break;
                case "Highest Price":
                    result.sort(java.util.Comparator.comparing((Guid g) -> g.getHoursRate() == null ? Double.MIN_VALUE : g.getHoursRate()).reversed());
                    break;
                case "Most Experience":
                    result.sort(java.util.Comparator.comparing((Guid g) -> g.getExperienceYears() == null ? 0 : g.getExperienceYears()).reversed());
                    break;
                default: // Highest Rated or fallback - skip if no rating
                    try {
                        result.sort((a, b) -> {
                            double ra = 0, rb = 0;
                            try {
                                java.lang.reflect.Field fa = a.getClass().getDeclaredField("rating"); fa.setAccessible(true); Object va = fa.get(a); ra = va == null ? 0 : Double.parseDouble(va.toString());
                                java.lang.reflect.Field fb = b.getClass().getDeclaredField("rating"); fb.setAccessible(true); Object vb = fb.get(b); rb = vb == null ? 0 : Double.parseDouble(vb.toString());
                            } catch (Exception e) { }
                            return Double.compare(rb, ra);
                        });
                    } catch (Exception e) { }
            }
        }

        return result;
    }

    @Override
    public Guid updateGuid(Long id, Guid guid) throws Exception {
        Guid existingGuid = guidRepository.findById(id).orElse(null);
        if (existingGuid == null) {
            throw new Exception("Guid not found");
        }
        // Update fields
        existingGuid.setBio(guid.getBio());
        existingGuid.setLanguagesSpoken(guid.getLanguagesSpoken());
        existingGuid.setSpecialization(guid.getSpecialization());
        existingGuid.setExperienceYears(guid.getExperienceYears());
        existingGuid.setHoursRate(guid.getHoursRate());
        existingGuid.setIsVerified(guid.getIsVerified());
        existingGuid.setIsAvailable(guid.getIsAvailable());
        existingGuid.setSltaLicenseId(guid.getSltaLicenseId());
        existingGuid.setSltaLicensePhoto(guid.getSltaLicensePhoto());
        existingGuid.setSltaLicenseExpiry(guid.getSltaLicenseExpiry());
        existingGuid.setNicNumber(guid.getNicNumber());
        existingGuid.setNicPhotoFront(guid.getNicPhotoFront());
        existingGuid.setNicPhotoBack(guid.getNicPhotoBack());
        // Do not update createdAt or user

        return guidRepository.save(existingGuid);
    }

    @Override
    public void deleteGuid(Long id) throws Exception {
        Guid existingGuid = guidRepository.findById(id).orElse(null);
        if (existingGuid == null) {
            throw new Exception("Guid not found");
        }
        guidRepository.deleteById(id);
    }
}
