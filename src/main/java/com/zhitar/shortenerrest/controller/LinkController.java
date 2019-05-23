package com.zhitar.shortenerrest.controller;

import com.zhitar.shortenerrest.domain.Link;
import com.zhitar.shortenerrest.service.LinkService;
import com.zhitar.shortenerrest.service.StatisticService;
import com.zhitar.shortenerrest.to.LinkTo;
import com.zhitar.shortenerrest.to.StatisticTo;
import com.zhitar.shortenerrest.util.LinkConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
public class LinkController {

    private final LinkService linkService;

    private final StatisticService statisticService;

    @Autowired
    public LinkController(LinkService linkService, StatisticService statisticService) {
        Objects.requireNonNull(linkService);
        Objects.requireNonNull(statisticService);
        this.linkService = linkService;
        this.statisticService = statisticService;
    }

    @GetMapping("/links")
    public List<Link> getAll(Pageable pageable) {
        return linkService.getAll(pageable).getContent();
    }

    @GetMapping("/links/{id}")
    public LinkTo get(@PathVariable Long id) {
        return LinkConverter.convertToLinkTo(linkService.getById(id));
    }

    @PostMapping("/")
    public Link create(@RequestBody LinkTo linkTo) {
        return linkService.createLink(linkTo);
    }

    @PutMapping("/")
    public ResponseEntity<String> update(LinkTo linkTo) {
        linkService.update(linkTo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        linkService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity follow(@PathVariable String id, HttpServletRequest request) {
        Link link = linkService.getByShortLink(id);
        Objects.requireNonNull(link);
        statisticService.create(link, request);
        if (!link.isActive() || link.getEndDate().isBefore(LocalDate.now())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", link.getLink());
            return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
        }
    }

    @GetMapping("/statistic")
    public StatisticTo getStatistic(@RequestParam String shortUrl) {
        Link link = linkService.getByShortLink(shortUrl);
        if (link == null) {
            throw new IllegalArgumentException();
        }
        StatisticTo statisticTo = new StatisticTo();
        Integer countStatisticForLink = statisticService.countStatisticForLink(link.getId());
        List<Object[]> dateStatistic = statisticService.dateStatistic(link.getId());
        List<Object[]> browserStatistic = statisticService.browserStatistic(link.getId());
        List<Object[]> referrerStatistic = statisticService.referrerStatistic(link.getId());
        statisticTo.setLink(link);
        statisticTo.setStatisticCount(countStatisticForLink);
        statisticTo.setDateStatistic(dateStatistic);
        statisticTo.setBrowserStatistic(browserStatistic);
        statisticTo.setReferrerStatistic(referrerStatistic);
        return statisticTo;
    }

    @PostMapping("/links/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable Long id, @RequestParam boolean active) {
        linkService.enable(id, active);
    }

}
