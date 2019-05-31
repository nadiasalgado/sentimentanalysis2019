package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SearchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Search and its DTO SearchDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SearchMapper extends EntityMapper<SearchDTO, Search> {



    default Search fromId(Long id) {
        if (id == null) {
            return null;
        }
        Search search = new Search();
        search.setId(id);
        return search;
    }
}
