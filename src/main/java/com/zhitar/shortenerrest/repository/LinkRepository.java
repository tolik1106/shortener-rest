package com.zhitar.shortenerrest.repository;

import com.zhitar.shortenerrest.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Link findByLink(String link);

    Link findByShortLink(String shortLink);

    @Modifying
    @Query("DELETE FROM Link l WHERE l.id=:id")
    void delete(@Param("id") Long id);
}
