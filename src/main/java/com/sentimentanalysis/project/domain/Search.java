package com.sentimentanalysis.project.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.sentimentanalysis.project.domain.enumeration.Searchlang;

/**
 * A Search.
 */
@Entity
@Table(name = "search")
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "search")
    private String search;

    @Column(name = "last_search")
    private Instant lastSearch;

    @Column(name = "slast_search")
    private Instant slastSearch;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Searchlang language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public Search search(String search) {
        this.search = search;
        return this;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Instant getLastSearch() {
        return lastSearch;
    }

    public Search lastSearch(Instant lastSearch) {
        this.lastSearch = lastSearch;
        return this;
    }

    public void setLastSearch(Instant lastSearch) {
        this.lastSearch = lastSearch;
    }

    public Instant getSlastSearch() {
        return slastSearch;
    }

    public Search slastSearch(Instant slastSearch) {
        this.slastSearch = slastSearch;
        return this;
    }

    public void setSlastSearch(Instant slastSearch) {
        this.slastSearch = slastSearch;
    }

    public Searchlang getLanguage() {
        return language;
    }

    public Search language(Searchlang language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Searchlang language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Search search = (Search) o;
        if (search.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), search.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Search{" +
            "id=" + getId() +
            ", search='" + getSearch() + "'" +
            ", lastSearch='" + getLastSearch() + "'" +
            ", slastSearch='" + getSlastSearch() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
