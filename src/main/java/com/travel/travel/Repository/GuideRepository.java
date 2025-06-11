package com.travel.travel.Repository;

import com.travel.travel.Models.Guide;
import com.travel.travel.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
}
