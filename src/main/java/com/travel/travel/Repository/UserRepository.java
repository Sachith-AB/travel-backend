package com.travel.travel.Repository;

import com.travel.travel.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // ...additional query methods if needed...
}
