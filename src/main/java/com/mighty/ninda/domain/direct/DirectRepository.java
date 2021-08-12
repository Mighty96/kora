package com.mighty.ninda.domain.direct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectRepository extends JpaRepository<Direct, Long> {

    List<Direct> findAllByOrderByReleasedDateDesc();
}
