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
import uz.dynamic.techinventory.domain.Akumulator;
import uz.dynamic.techinventory.repository.AkumulatorRepository;
import uz.dynamic.techinventory.service.dto.AkumulatorDTO;
import uz.dynamic.techinventory.service.mapper.AkumulatorMapper;

/**
 * Integration tests for the {@link AkumulatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AkumulatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_POWER = "AAAAAAAAAA";
    private static final String UPDATED_POWER = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/akumulators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AkumulatorRepository akumulatorRepository;

    @Autowired
    private AkumulatorMapper akumulatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAkumulatorMockMvc;

    private Akumulator akumulator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Akumulator createEntity(EntityManager em) {
        Akumulator akumulator = new Akumulator().name(DEFAULT_NAME).model(DEFAULT_MODEL).power(DEFAULT_POWER).info(DEFAULT_INFO);
        return akumulator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Akumulator createUpdatedEntity(EntityManager em) {
        Akumulator akumulator = new Akumulator().name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);
        return akumulator;
    }

    @BeforeEach
    public void initTest() {
        akumulator = createEntity(em);
    }

    @Test
    @Transactional
    void createAkumulator() throws Exception {
        int databaseSizeBeforeCreate = akumulatorRepository.findAll().size();
        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);
        restAkumulatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akumulatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeCreate + 1);
        Akumulator testAkumulator = akumulatorList.get(akumulatorList.size() - 1);
        assertThat(testAkumulator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAkumulator.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testAkumulator.getPower()).isEqualTo(DEFAULT_POWER);
        assertThat(testAkumulator.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createAkumulatorWithExistingId() throws Exception {
        // Create the Akumulator with an existing ID
        akumulator.setId(1L);
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        int databaseSizeBeforeCreate = akumulatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAkumulatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akumulatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAkumulators() throws Exception {
        // Initialize the database
        akumulatorRepository.saveAndFlush(akumulator);

        // Get all the akumulatorList
        restAkumulatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(akumulator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].power").value(hasItem(DEFAULT_POWER)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getAkumulator() throws Exception {
        // Initialize the database
        akumulatorRepository.saveAndFlush(akumulator);

        // Get the akumulator
        restAkumulatorMockMvc
            .perform(get(ENTITY_API_URL_ID, akumulator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(akumulator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingAkumulator() throws Exception {
        // Get the akumulator
        restAkumulatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAkumulator() throws Exception {
        // Initialize the database
        akumulatorRepository.saveAndFlush(akumulator);

        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();

        // Update the akumulator
        Akumulator updatedAkumulator = akumulatorRepository.findById(akumulator.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAkumulator are not directly saved in db
        em.detach(updatedAkumulator);
        updatedAkumulator.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(updatedAkumulator);

        restAkumulatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akumulatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akumulatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
        Akumulator testAkumulator = akumulatorList.get(akumulatorList.size() - 1);
        assertThat(testAkumulator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkumulator.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAkumulator.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testAkumulator.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingAkumulator() throws Exception {
        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();
        akumulator.setId(longCount.incrementAndGet());

        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAkumulatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akumulatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akumulatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAkumulator() throws Exception {
        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();
        akumulator.setId(longCount.incrementAndGet());

        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkumulatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akumulatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAkumulator() throws Exception {
        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();
        akumulator.setId(longCount.incrementAndGet());

        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkumulatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akumulatorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAkumulatorWithPatch() throws Exception {
        // Initialize the database
        akumulatorRepository.saveAndFlush(akumulator);

        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();

        // Update the akumulator using partial update
        Akumulator partialUpdatedAkumulator = new Akumulator();
        partialUpdatedAkumulator.setId(akumulator.getId());

        partialUpdatedAkumulator.model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);

        restAkumulatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkumulator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkumulator))
            )
            .andExpect(status().isOk());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
        Akumulator testAkumulator = akumulatorList.get(akumulatorList.size() - 1);
        assertThat(testAkumulator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAkumulator.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAkumulator.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testAkumulator.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateAkumulatorWithPatch() throws Exception {
        // Initialize the database
        akumulatorRepository.saveAndFlush(akumulator);

        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();

        // Update the akumulator using partial update
        Akumulator partialUpdatedAkumulator = new Akumulator();
        partialUpdatedAkumulator.setId(akumulator.getId());

        partialUpdatedAkumulator.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);

        restAkumulatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkumulator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkumulator))
            )
            .andExpect(status().isOk());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
        Akumulator testAkumulator = akumulatorList.get(akumulatorList.size() - 1);
        assertThat(testAkumulator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkumulator.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAkumulator.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testAkumulator.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingAkumulator() throws Exception {
        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();
        akumulator.setId(longCount.incrementAndGet());

        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAkumulatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, akumulatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akumulatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAkumulator() throws Exception {
        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();
        akumulator.setId(longCount.incrementAndGet());

        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkumulatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akumulatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAkumulator() throws Exception {
        int databaseSizeBeforeUpdate = akumulatorRepository.findAll().size();
        akumulator.setId(longCount.incrementAndGet());

        // Create the Akumulator
        AkumulatorDTO akumulatorDTO = akumulatorMapper.toDto(akumulator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAkumulatorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(akumulatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Akumulator in the database
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAkumulator() throws Exception {
        // Initialize the database
        akumulatorRepository.saveAndFlush(akumulator);

        int databaseSizeBeforeDelete = akumulatorRepository.findAll().size();

        // Delete the akumulator
        restAkumulatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, akumulator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Akumulator> akumulatorList = akumulatorRepository.findAll();
        assertThat(akumulatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
