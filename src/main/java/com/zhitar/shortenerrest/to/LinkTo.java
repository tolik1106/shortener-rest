package com.zhitar.shortenerrest.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkTo {

    private Long id;

    private String link;

    private String shortLink;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateExpired;

    private Integer daysExpired;

    private boolean active;
}
