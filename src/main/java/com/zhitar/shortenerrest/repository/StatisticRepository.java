package com.zhitar.shortenerrest.repository;

import com.zhitar.shortenerrest.domain.LinkStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<LinkStatistic, Long> {

    @Query("SELECT COUNT(s) FROM LinkStatistic s WHERE s.link.id=:linkId")
    Integer followCount(@Param("linkId") Long linkId);

    @Query("SELECT COUNT(s), s.followDate FROM LinkStatistic s WHERE s.link.id=?1 GROUP BY s.followDate")
    List<Object[]> getFollowByDate(Long linkId);

    @Query("SELECT COUNT(s), s.browser FROM LinkStatistic s WHERE s.link.id=?1 GROUP BY s.browser")
    List<Object[]> getFollowByBrowser(Long linkId);

    @Query("SELECT COUNT(s), s.refferer FROM LinkStatistic s WHERE s.link.id=?1 AND s.refferer IS NOT NULL GROUP BY s.refferer")
    List<Object[]> geFollowByReferrer(Long linkId);
}
