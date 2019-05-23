package com.zhitar.shortenerrest.service;

import com.zhitar.shortenerrest.domain.Link;
import com.zhitar.shortenerrest.domain.LinkStatistic;
import com.zhitar.shortenerrest.repository.StatisticRepository;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatisticService {

    @Autowired
    private StatisticRepository repository;

    @Transactional
    public LinkStatistic create(Link link, HttpServletRequest request) {
        LinkStatistic statistic = new LinkStatistic();

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        statistic.setFollowDate(LocalDate.now());
        statistic.setRefferer(request.getHeader(HttpHeaders.REFERER));
        statistic.setBrowser(userAgent.getBrowser().getName());
        statistic.setIpAddress(request.getRemoteAddr());
        statistic.setLink(link);
        return repository.save(statistic);
    }

    public Integer countStatisticForLink(Long linkId) {
        return repository.followCount(linkId);
    }

    public List<Object[]> dateStatistic(Long linkId) {
        return repository.getFollowByDate(linkId);
    }

    public List<Object[]> browserStatistic(Long linkId) {
        return repository.getFollowByBrowser(linkId);
    }

    public List<Object[]> referrerStatistic(Long linkId) {
        return repository.geFollowByReferrer(linkId);
    }
}
