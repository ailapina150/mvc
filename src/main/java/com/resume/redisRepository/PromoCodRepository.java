package com.resume.redisRepository;

import com.resume.model.PromoCod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodRepository extends JpaRepository<PromoCod, Long> {
}
