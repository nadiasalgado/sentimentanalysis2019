package com.sentimentanalysis.project.web.rest;

import com.sentimentanalysis.project.Sentimentanalysis2019App;

import com.sentimentanalysis.project.domain.Search;
import com.sentimentanalysis.project.repository.SearchRepository;
import com.sentimentanalysis.project.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.sentimentanalysis.project.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sentimentanalysis.project.domain.enumeration.Searchlang;
/**
 * Test class for the SearchResource REST controller.
 *
 * @see SearchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Sentimentanalysis2019App.class)
public class SearchResourceIntTest {

    private static final String DEFAULT_SEARCH = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_SEARCH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_SEARCH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SLAST_SEARCH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SLAST_SEARCH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Searchlang DEFAULT_LANGUAGE = Searchlang.ENGLISH;
    private static final Searchlang UPDATED_LANGUAGE = Searchlang.FRENCH;

    @Autowired
    private SearchRepository searchRepository;

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
        final SearchResource searchResource = new SearchResource(searchRepository);
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
            .search(DEFAULT_SEARCH)
            .lastSearch(DEFAULT_LAST_SEARCH)
            .slastSearch(DEFAULT_SLAST_SEARCH)
            .language(DEFAULT_LANGUAGE);
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
        restSearchMockMvc.perform(post("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(search)))
            .andExpect(status().isCreated());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeCreate + 1);
        Search testSearch = searchList.get(searchList.size() - 1);
        assertThat(testSearch.getSearch()).isEqualTo(DEFAULT_SEARCH);
        assertThat(testSearch.getLastSearch()).isEqualTo(DEFAULT_LAST_SEARCH);
        assertThat(testSearch.getSlastSearch()).isEqualTo(DEFAULT_SLAST_SEARCH);
        assertThat(testSearch.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createSearchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchRepository.findAll().size();

        // Create the Search with an existing ID
        search.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchMockMvc.perform(post("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(search)))
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
            .andExpect(jsonPath("$.[*].search").value(hasItem(DEFAULT_SEARCH.toString())))
            .andExpect(jsonPath("$.[*].lastSearch").value(hasItem(DEFAULT_LAST_SEARCH.toString())))
            .andExpect(jsonPath("$.[*].slastSearch").value(hasItem(DEFAULT_SLAST_SEARCH.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
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
            .andExpect(jsonPath("$.search").value(DEFAULT_SEARCH.toString()))
            .andExpect(jsonPath("$.lastSearch").value(DEFAULT_LAST_SEARCH.toString()))
            .andExpect(jsonPath("$.slastSearch").value(DEFAULT_SLAST_SEARCH.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
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
            .search(UPDATED_SEARCH)
            .lastSearch(UPDATED_LAST_SEARCH)
            .slastSearch(UPDATED_SLAST_SEARCH)
            .language(UPDATED_LANGUAGE);

        restSearchMockMvc.perform(put("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSearch)))
            .andExpect(status().isOk());

        // Validate the Search in the database
        List<Search> searchList = searchRepository.findAll();
        assertThat(searchList).hasSize(databaseSizeBeforeUpdate);
        Search testSearch = searchList.get(searchList.size() - 1);
        assertThat(testSearch.getSearch()).isEqualTo(UPDATED_SEARCH);
        assertThat(testSearch.getLastSearch()).isEqualTo(UPDATED_LAST_SEARCH);
        assertThat(testSearch.getSlastSearch()).isEqualTo(UPDATED_SLAST_SEARCH);
        assertThat(testSearch.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingSearch() throws Exception {
        int databaseSizeBeforeUpdate = searchRepository.findAll().size();

        // Create the Search

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchMockMvc.perform(put("/api/searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(search)))
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
}
