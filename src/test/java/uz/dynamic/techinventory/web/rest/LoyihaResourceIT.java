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
import uz.dynamic.techinventory.domain.Loyiha;
import uz.dynamic.techinventory.repository.LoyihaRepository;
import uz.dynamic.techinventory.service.dto.LoyihaDTO;
import uz.dynamic.techinventory.service.mapper.LoyihaMapper;

/**
 * Integration tests for the {@link LoyihaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoyihaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loyihas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoyihaRepository loyihaRepository;

    @Autowired
    private LoyihaMapper loyihaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoyihaMockMvc;

    private Loyiha loyiha;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loyiha createEntity(EntityManager em) {
        Loyiha loyiha = new Loyiha().name(DEFAULT_NAME);
        return loyiha;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loyiha createUpdatedEntity(EntityManager em) {
        Loyiha loyiha = new Loyiha().name(UPDATED_NAME);
        return loyiha;
    }

    @BeforeEach
    public void initTest() {
        loyiha = createEntity(em);
    }

    @Test
    @Transactional
    void createLoyiha() throws Exception {
        int databaseSizeBeforeCreate = loyihaRepository.findAll().size();
        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);
        restLoyihaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loyihaDTO)))
            .andExpect(status().isCreated());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeCreate + 1);
        Loyiha testLoyiha = loyihaList.get(loyihaList.size() - 1);
        assertThat(testLoyiha.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createLoyihaWithExistingId() throws Exception {
        // Create the Loyiha with an existing ID
        loyiha.setId(1L);
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        int databaseSizeBeforeCreate = loyihaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoyihaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loyihaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyihaRepository.findAll().size();
        // set the field null
        loyiha.setName(null);

        // Create the Loyiha, which fails.
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        restLoyihaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loyihaDTO)))
            .andExpect(status().isBadRequest());

        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLoyihas() throws Exception {
        // Initialize the database
        loyihaRepository.saveAndFlush(loyiha);

        // Get all the loyihaList
        restLoyihaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loyiha.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getLoyiha() throws Exception {
        // Initialize the database
        loyihaRepository.saveAndFlush(loyiha);

        // Get the loyiha
        restLoyihaMockMvc
            .perform(get(ENTITY_API_URL_ID, loyiha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loyiha.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingLoyiha() throws Exception {
        // Get the loyiha
        restLoyihaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoyiha() throws Exception {
        // Initialize the database
        loyihaRepository.saveAndFlush(loyiha);

        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();

        // Update the loyiha
        Loyiha updatedLoyiha = loyihaRepository.findById(loyiha.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLoyiha are not directly saved in db
        em.detach(updatedLoyiha);
        updatedLoyiha.name(UPDATED_NAME);
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(updatedLoyiha);

        restLoyihaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loyihaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loyihaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
        Loyiha testLoyiha = loyihaList.get(loyihaList.size() - 1);
        assertThat(testLoyiha.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingLoyiha() throws Exception {
        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();
        loyiha.setId(longCount.incrementAndGet());

        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoyihaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loyihaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loyihaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoyiha() throws Exception {
        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();
        loyiha.setId(longCount.incrementAndGet());

        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyihaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loyihaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoyiha() throws Exception {
        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();
        loyiha.setId(longCount.incrementAndGet());

        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyihaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loyihaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoyihaWithPatch() throws Exception {
        // Initialize the database
        loyihaRepository.saveAndFlush(loyiha);

        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();

        // Update the loyiha using partial update
        Loyiha partialUpdatedLoyiha = new Loyiha();
        partialUpdatedLoyiha.setId(loyiha.getId());

        partialUpdatedLoyiha.name(UPDATED_NAME);

        restLoyihaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoyiha.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoyiha))
            )
            .andExpect(status().isOk());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
        Loyiha testLoyiha = loyihaList.get(loyihaList.size() - 1);
        assertThat(testLoyiha.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateLoyihaWithPatch() throws Exception {
        // Initialize the database
        loyihaRepository.saveAndFlush(loyiha);

        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();

        // Update the loyiha using partial update
        Loyiha partialUpdatedLoyiha = new Loyiha();
        partialUpdatedLoyiha.setId(loyiha.getId());

        partialUpdatedLoyiha.name(UPDATED_NAME);

        restLoyihaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoyiha.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoyiha))
            )
            .andExpect(status().isOk());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
        Loyiha testLoyiha = loyihaList.get(loyihaList.size() - 1);
        assertThat(testLoyiha.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingLoyiha() throws Exception {
        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();
        loyiha.setId(longCount.incrementAndGet());

        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoyihaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loyihaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loyihaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoyiha() throws Exception {
        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();
        loyiha.setId(longCount.incrementAndGet());

        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyihaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loyihaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoyiha() throws Exception {
        int databaseSizeBeforeUpdate = loyihaRepository.findAll().size();
        loyiha.setId(longCount.incrementAndGet());

        // Create the Loyiha
        LoyihaDTO loyihaDTO = loyihaMapper.toDto(loyiha);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoyihaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loyihaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Loyiha in the database
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoyiha() throws Exception {
        // Initialize the database
        loyihaRepository.saveAndFlush(loyiha);

        int databaseSizeBeforeDelete = loyihaRepository.findAll().size();

        // Delete the loyiha
        restLoyihaMockMvc
            .perform(delete(ENTITY_API_URL_ID, loyiha.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Loyiha> loyihaList = loyihaRepository.findAll();
        assertThat(loyihaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
