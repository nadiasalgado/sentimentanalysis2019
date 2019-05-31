package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Search entity.
 */
public class SearchDTO implements Serializable {

    private Long id;

    private String search;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchDTO searchDTO = (SearchDTO) o;
        if (searchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), searchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
            "id=" + getId() +
            ", search='" + getSearch() + "'" +
            "}";
    }
}
