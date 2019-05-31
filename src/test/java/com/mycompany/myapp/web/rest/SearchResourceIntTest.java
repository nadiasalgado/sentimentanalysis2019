package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.EntityTestApp;

import com.mycompany.myapp.domain.Search;
import com.mycompany.myapp.repository.SearchRepository;
import com.mycompany.myapp.service.SearchService;
import com.mycompany.myapp.service.dto.SearchDTO;
import com.mycompany.myapp.service.mapper.SearchMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SearchResource REST controller.
 *
 * @see SearchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntityTestApp.class)
public class SearchResourceIntTest {

    private static final String DEFAULT_SEARCH = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH = "BBBBBBBBBB";

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private SearchService searchService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSearchMockMvc;

    private Search search;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SearchResource searchResource = new SearchResource(searchService);
        this.restSearchMockMvc = MockMvcBuilders.standaloneSetup(searchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Search createEntity(EntityManager em) {
        Search search = new Search()
            .search(DEFAULT_SEARCH);
        return search;
    }

    @Before
    public void initTest() {
        search = createEntity(em);
    }

    @Test
    @Transactional
    public void createSearch() throws Exception {
        int databaseSizeBeforeCreate = searchRepository.findAll().size();

        // Create the Search
        SearchDTO searchDTO = searchMapper.toDto(search);
        restSearchMockMvc.perform(post("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchDTO)))
            .andExpect(status().isCreated());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeCreate + 1);
        Search testSearch = searchList.get(searchList.size() - 1);
        assertThat(testSearch.getSearch()).isEqualTo(DEFAULT_SEARCH);
    }

    @Test
    @Transactional
    public void createSearchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchRepository.findAll().size();

        // Create the Search with an existing ID
        search.setId(1L);
        SearchDTO searchDTO = searchMapper.toDto(search);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchMockMvc.perform(post("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSearches() throws Exception {
        // Initialize the database
        searchRepository.saveAndFlush(search);

        // Get all the searchList
        restSearchMockMvc.perform(get("/api/searches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(search.getId().intValue())))
            .andExpect(jsonPath("$.[*].search").value(hasItem(DEFAULT_SEARCH.toString())));
    }
    
    @Test
    @Transactional
    public void getSearch() throws Exception {
        // Initialize the database
        searchRepository.saveAndFlush(search);

        // Get the search
        restSearchMockMvc.perform(get("/api/searches/{id}", search.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(search.getId().intValue()))
            .andExpect(jsonPath("$.search").value(DEFAULT_SEARCH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSearch() throws Exception {
        // Get the search
        restSearchMockMvc.perform(get("/api/searches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSearch() throws Exception {
        // Initialize the database
        searchRepository.saveAndFlush(search);

        int databaseSizeBeforeUpdate = searchRepository.findAll().size();

        // Update the search
        Search updatedSearch = searchRepository.findById(search.getId()).get();
        // Disconnect from session so that the updates on updatedSearch are not directly saved in db
        em.detach(updatedSearch);
        updatedSearch
            .search(UPDATED_SEARCH);
        SearchDTO searchDTO = searchMapper.toDto(updatedSearch);

        restSearchMockMvc.perform(put("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchDTO)))
            .andExpect(status().isOk());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeUpdate);
        Search testSearch = searchList.get(searchList.size() - 1);
        assertThat(testSearch.getSearch()).isEqualTo(UPDATED_SEARCH);
    }

    @Test
    @Transactional
    public void updateNonExistingSearch() throws Exception {
        int databaseSizeBeforeUpdate = searchRepository.findAll().size();

        // Create the Search
        SearchDTO searchDTO = searchMapper.toDto(search);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchMockMvc.perform(put("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSearch() throws Exception {
        // Initialize the database
        searchRepository.saveAndFlush(search);

        int databaseSizeBeforeDelete = searchRepository.findAll().size();

        // Delete the search
        restSearchMockMvc.perform(delete("/api/searches/{id}", search.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Search.class);
        Search search1 = new Search();
        search1.setId(1L);
        Search search2 = new Search();
        search2.setId(search1.getId());
        assertThat(search1).isEqualTo(search2);
        search2.setId(2L);
        assertThat(search1).isNotEqualTo(search2);
        search1.setId(null);
        assertThat(search1).isNotEqualTo(search2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchDTO.class);
        SearchDTO searchDTO1 = new SearchDTO();
        searchDTO1.setId(1L);
        SearchDTO searchDTO2 = new SearchDTO();
        assertThat(searchDTO1).isNotEqualTo(searchDTO2);
        searchDTO2.setId(searchDTO1.getId());
        assertThat(searchDTO1).isEqualTo(searchDTO2);
        searchDTO2.setId(2L);
        assertThat(searchDTO1).isNotEqualTo(searchDTO2);
        searchDTO1.setId(null);
        assertThat(searchDTO1).isNotEqualTo(searchDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(searchMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(searchMapper.fromId(null)).isNull();
    }
}
