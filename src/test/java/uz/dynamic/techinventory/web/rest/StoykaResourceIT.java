package uz.dynamic.techinventory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.IntegrationTest;
import uz.dynamic.techinventory.domain.Stoyka;
import uz.dynamic.techinventory.repository.StoykaRepository;
import uz.dynamic.techinventory.service.dto.StoykaDTO;
import uz.dynamic.techinventory.service.mapper.StoykaMapper;

/**
 * Integration tests for the {@link StoykaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoykaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stoykas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoykaRepository stoykaRepository;

    @Autowired
    private StoykaMapper stoykaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoykaMockMvc;

    private Stoyka stoyka;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stoyka createEntity(EntityManager em) {
        Stoyka stoyka = new Stoyka().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return stoyka;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stoyka createUpdatedEntity(EntityManager em) {
        Stoyka stoyka = new Stoyka().name(UPDATED_NAME).info(UPDATED_INFO);
        return stoyka;
    }

    @BeforeEach
    public void initTest() {
        stoyka = createEntity(em);
    }

    @Test
    @Transactional
    void createStoyka() throws Exception {
        int databaseSizeBeforeCreate = stoykaRepository.findAll().size();
        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);
        restStoykaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoykaDTO)))
            .andExpect(status().isCreated());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeCreate + 1);
        Stoyka testStoyka = stoykaList.get(stoykaList.size() - 1);
        assertThat(testStoyka.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStoyka.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createStoykaWithExistingId() throws Exception {
        // Create the Stoyka with an existing ID
        stoyka.setId(1L);
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        int databaseSizeBeforeCreate = stoykaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoykaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoykaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStoykas() throws Exception {
        // Initialize the database
        stoykaRepository.saveAndFlush(stoyka);

        // Get all the stoykaList
        restStoykaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stoyka.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getStoyka() throws Exception {
        // Initialize the database
        stoykaRepository.saveAndFlush(stoyka);

        // Get the stoyka
        restStoykaMockMvc
            .perform(get(ENTITY_API_URL_ID, stoyka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stoyka.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingStoyka() throws Exception {
        // Get the stoyka
        restStoykaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStoyka() throws Exception {
        // Initialize the database
        stoykaRepository.saveAndFlush(stoyka);

        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();

        // Update the stoyka
        Stoyka updatedStoyka = stoykaRepository.findById(stoyka.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStoyka are not directly saved in db
        em.detach(updatedStoyka);
        updatedStoyka.name(UPDATED_NAME).info(UPDATED_INFO);
        StoykaDTO stoykaDTO = stoykaMapper.toDto(updatedStoyka);

        restStoykaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stoykaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoykaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
        Stoyka testStoyka = stoykaList.get(stoykaList.size() - 1);
        assertThat(testStoyka.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoyka.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingStoyka() throws Exception {
        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();
        stoyka.setId(longCount.incrementAndGet());

        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoykaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stoykaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoykaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStoyka() throws Exception {
        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();
        stoyka.setId(longCount.incrementAndGet());

        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoykaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStoyka() throws Exception {
        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();
        stoyka.setId(longCount.incrementAndGet());

        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoykaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoykaWithPatch() throws Exception {
        // Initialize the database
        stoykaRepository.saveAndFlush(stoyka);

        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();

        // Update the stoyka using partial update
        Stoyka partialUpdatedStoyka = new Stoyka();
        partialUpdatedStoyka.setId(stoyka.getId());

        partialUpdatedStoyka.info(UPDATED_INFO);

        restStoykaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoyka.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoyka))
            )
            .andExpect(status().isOk());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
        Stoyka testStoyka = stoykaList.get(stoykaList.size() - 1);
        assertThat(testStoyka.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStoyka.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateStoykaWithPatch() throws Exception {
        // Initialize the database
        stoykaRepository.saveAndFlush(stoyka);

        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();

        // Update the stoyka using partial update
        Stoyka partialUpdatedStoyka = new Stoyka();
        partialUpdatedStoyka.setId(stoyka.getId());

        partialUpdatedStoyka.name(UPDATED_NAME).info(UPDATED_INFO);

        restStoykaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoyka.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoyka))
            )
            .andExpect(status().isOk());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
        Stoyka testStoyka = stoykaList.get(stoykaList.size() - 1);
        assertThat(testStoyka.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoyka.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingStoyka() throws Exception {
        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();
        stoyka.setId(longCount.incrementAndGet());

        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoykaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stoykaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stoykaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStoyka() throws Exception {
        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();
        stoyka.setId(longCount.incrementAndGet());

        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stoykaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStoyka() throws Exception {
        int databaseSizeBeforeUpdate = stoykaRepository.findAll().size();
        stoyka.setId(longCount.incrementAndGet());

        // Create the Stoyka
        StoykaDTO stoykaDTO = stoykaMapper.toDto(stoyka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stoykaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stoyka in the database
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStoyka() throws Exception {
        // Initialize the database
        stoykaRepository.saveAndFlush(stoyka);

        int databaseSizeBeforeDelete = stoykaRepository.findAll().size();

        // Delete the stoyka
        restStoykaMockMvc
            .perform(delete(ENTITY_API_URL_ID, stoyka.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stoyka> stoykaList = stoykaRepository.findAll();
        assertThat(stoykaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
