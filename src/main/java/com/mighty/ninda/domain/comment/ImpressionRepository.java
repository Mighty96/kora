package com.mighty.ninda.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImpressionRepository extends JpaRepository<Impression, Long> {

    List<Impression> findByDirect_Id(@Param(value = "directId") Long directId);

}
