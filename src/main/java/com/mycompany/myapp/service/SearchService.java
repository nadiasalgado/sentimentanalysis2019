package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Search;
import com.mycompany.myapp.repository.SearchRepository;
import com.mycompany.myapp.service.dto.SearchDTO;
import com.mycompany.myapp.service.mapper.SearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Search.
 */
@Service
@Transactional
public class SearchService {

    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final SearchRepository searchRepository;

    private final SearchMapper searchMapper;

    public SearchService(SearchRepository searchRepository, SearchMapper searchMapper) {
        this.searchRepository = searchRepository;
        this.searchMapper = searchMapper;
    }

    /**
     * Save a search.
     *
     * @param searchDTO the entity to save
     * @return the persisted entity
     */
    public SearchDTO save(SearchDTO searchDTO) {
        log.debug("Request to save Search : {}", searchDTO);
        Search search = searchMapper.toEntity(searchDTO);
        search = searchRepository.save(search);
        return searchMapper.toDto(search);
    }

    /**
     * Get all the searches.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SearchDTO> findAll() {
        log.debug("Request to get all Searches");
        return searchRepository.findAll().stream()
            .map(searchMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one search by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SearchDTO> findOne(Long id) {
        log.debug("Request to get Search : {}", id);
        return searchRepository.findById(id)
            .map(searchMapper::toDto);
    }

    /**
     * Delete the search by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Search : {}", id);
        searchRepository.deleteById(id);
    }
}
