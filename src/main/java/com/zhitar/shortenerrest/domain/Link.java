package com.zhitar.shortenerrest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "linkStatisticSet")
@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 2048)
    private String link;

    @Column(nullable = false, unique = true)
    private String shortLink;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDate createdDate = LocalDate.now();

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<LinkStatistic> linkStatisticSet;

    public Link(String link, String shortLink, LocalDate endDate, boolean active) {
        this.link = link;
        this.shortLink = shortLink;
        this.endDate = endDate;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(id, link.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
