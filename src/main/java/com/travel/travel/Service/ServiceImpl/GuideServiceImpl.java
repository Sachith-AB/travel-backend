package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Guide;
import com.travel.travel.Repository.GuideRepository;
import com.travel.travel.Service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    GuideRepository guideRepository;

    @Override
    public Guide createGuide(Guide guide) {
        return guideRepository.save(guide);
    }
}
