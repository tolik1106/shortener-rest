package com.zhitar.shortenerrest.util;

import com.zhitar.shortenerrest.domain.Link;
import com.zhitar.shortenerrest.to.LinkTo;

import java.time.LocalDate;
import java.time.Period;

public class LinkConverter {
    private LinkConverter() {
    }

    public static Link convertToLink(LinkTo linkTo, LocalDate endDate, String shortLink) {
        return new Link(linkTo.getLink(), shortLink, endDate, linkTo.isActive());
    }

    public static LinkTo convertToLinkTo(Link link) {
        return new LinkTo(link.getId(), link.getLink(), link.getShortLink(), link.getEndDate(), Period.between(link.getCreatedDate(), link.getEndDate()).getDays(), link.isActive());
    }

    public static Link updateFromTo(LinkTo linkTo, Link link) {
        link.setId(linkTo.getId());
        link.setLink(linkTo.getLink());
        link.setShortLink(linkTo.getShortLink());
        link.setEndDate(linkTo.getDateExpired());
        return link;
    }
}
