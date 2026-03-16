package com.invitation.backend.repository;

import com.invitation.backend.entity.Rsvp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

public interface RsvpRepository extends JpaRepository<Rsvp, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);

    Page<Rsvp> findAllByWillCome(Boolean willCome, Pageable pageable);

}
