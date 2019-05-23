package com.zhitar.shortenerrest.service;

import com.zhitar.shortenerrest.domain.Link;
import com.zhitar.shortenerrest.repository.LinkRepository;
import com.zhitar.shortenerrest.to.LinkTo;
import com.zhitar.shortenerrest.util.Helper;
import com.zhitar.shortenerrest.util.LinkConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class LinkService {

    private static final long DEFAULT_EXPIRE_DAYS = 30;

    @Autowired
    private LinkRepository repository;

    @Transactional
    public Link createLink(LinkTo linkTo) {
        Link linkByURL = repository.findByLink(linkTo.getLink());
        if (linkByURL != null) {
            throw new IllegalArgumentException("Link " + linkTo.getLink() + " already exist!");
        }

        LocalDate expiredDate;
        if ((expiredDate = linkTo.getDateExpired()) == null) {
            expiredDate = LocalDate.now();
            Integer daysExpired = linkTo.getDaysExpired();
            if (daysExpired == null) {
                expiredDate = expiredDate.plusDays(DEFAULT_EXPIRE_DAYS);
            } else {
                expiredDate = expiredDate.plusDays(daysExpired);
            }
        }
        String shortLink = Helper.generateRandomString();
        Link newLink = LinkConverter.convertToLink(linkTo, expiredDate, shortLink);
        return repository.save(newLink);
    }

    public List<Link> getAll() {
        return repository.findAll();
    }

    public Page<Link> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Link getByShortLink(String shortLink) {
        return repository.findByShortLink(shortLink);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    @Transactional
    public void update(LinkTo linkTo) {
        Optional<Link> byId = repository.findById(linkTo.getId());
        Link link = LinkConverter.updateFromTo(linkTo, byId.orElseThrow(IllegalArgumentException::new));
        repository.save(link);
    }

    public Link getById(Long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void enable(Long id, boolean active) {
        Link link = getById(id);
        link.setActive(active);
        repository.save(link);
    }
}
