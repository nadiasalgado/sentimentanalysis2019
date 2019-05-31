package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.SearchService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.SearchDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Search.
 */
@RestController
@RequestMapping("/api")
public class SearchResource {

    private final Logger log = LoggerFactory.getLogger(SearchResource.class);

    private static final String ENTITY_NAME = "search";

    private final SearchService searchService;

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * POST  /searches : Create a new search.
     *
     * @param searchDTO the searchDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new searchDTO, or with status 400 (Bad Request) if the search has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/searches")
    public ResponseEntity<SearchDTO> createSearch(@RequestBody SearchDTO searchDTO) throws URISyntaxException {
        log.debug("REST request to save Search : {}", searchDTO);
        if (searchDTO.getId() != null) {
            throw new BadRequestAlertException("A new search cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SearchDTO result = searchService.save(searchDTO);
        return ResponseEntity.created(new URI("/api/searches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /searches : Updates an existing search.
     *
     * @param searchDTO the searchDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated searchDTO,
     * or with status 400 (Bad Request) if the searchDTO is not valid,
     * or with status 500 (Internal Server Error) if the searchDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/searches")
    public ResponseEntity<SearchDTO> updateSearch(@RequestBody SearchDTO searchDTO) throws URISyntaxException {
        log.debug("REST request to update Search : {}", searchDTO);
        if (searchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SearchDTO result = searchService.save(searchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, searchDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /searches : get all the searches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of searches in body
     */
    @GetMapping("/searches")
    public List<SearchDTO> getAllSearches() {
        log.debug("REST request to get all Searches");
        return searchService.findAll();
    }

    /**
     * GET  /searches/:id : get the "id" search.
     *
     * @param id the id of the searchDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the searchDTO, or with status 404 (Not Found)
     */
    @GetMapping("/searches/{id}")
    public ResponseEntity<SearchDTO> getSearch(@PathVariable Long id) {
        log.debug("REST request to get Search : {}", id);
        Optional<SearchDTO> searchDTO = searchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(searchDTO);
    }

    /**
     * DELETE  /searches/:id : delete the "id" search.
     *
     * @param id the id of the searchDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/searches/{id}")
    public ResponseEntity<Void> deleteSearch(@PathVariable Long id) {
        log.debug("REST request to delete Search : {}", id);
        searchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
