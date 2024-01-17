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
import uz.dynamic.techinventory.domain.Stabilizator;
import uz.dynamic.techinventory.repository.StabilizatorRepository;
import uz.dynamic.techinventory.service.dto.StabilizatorDTO;
import uz.dynamic.techinventory.service.mapper.StabilizatorMapper;

/**
 * Integration tests for the {@link StabilizatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StabilizatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_POWER = "AAAAAAAAAA";
    private static final String UPDATED_POWER = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stabilizators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StabilizatorRepository stabilizatorRepository;

    @Autowired
    private StabilizatorMapper stabilizatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStabilizatorMockMvc;

    private Stabilizator stabilizator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stabilizator createEntity(EntityManager em) {
        Stabilizator stabilizator = new Stabilizator().name(DEFAULT_NAME).model(DEFAULT_MODEL).power(DEFAULT_POWER).info(DEFAULT_INFO);
        return stabilizator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stabilizator createUpdatedEntity(EntityManager em) {
        Stabilizator stabilizator = new Stabilizator().name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);
        return stabilizator;
    }

    @BeforeEach
    public void initTest() {
        stabilizator = createEntity(em);
    }

    @Test
    @Transactional
    void createStabilizator() throws Exception {
        int databaseSizeBeforeCreate = stabilizatorRepository.findAll().size();
        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);
        restStabilizatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeCreate + 1);
        Stabilizator testStabilizator = stabilizatorList.get(stabilizatorList.size() - 1);
        assertThat(testStabilizator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStabilizator.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testStabilizator.getPower()).isEqualTo(DEFAULT_POWER);
        assertThat(testStabilizator.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createStabilizatorWithExistingId() throws Exception {
        // Create the Stabilizator with an existing ID
        stabilizator.setId(1L);
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        int databaseSizeBeforeCreate = stabilizatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStabilizatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStabilizators() throws Exception {
        // Initialize the database
        stabilizatorRepository.saveAndFlush(stabilizator);

        // Get all the stabilizatorList
        restStabilizatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stabilizator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].power").value(hasItem(DEFAULT_POWER)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getStabilizator() throws Exception {
        // Initialize the database
        stabilizatorRepository.saveAndFlush(stabilizator);

        // Get the stabilizator
        restStabilizatorMockMvc
            .perform(get(ENTITY_API_URL_ID, stabilizator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stabilizator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingStabilizator() throws Exception {
        // Get the stabilizator
        restStabilizatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStabilizator() throws Exception {
        // Initialize the database
        stabilizatorRepository.saveAndFlush(stabilizator);

        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();

        // Update the stabilizator
        Stabilizator updatedStabilizator = stabilizatorRepository.findById(stabilizator.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStabilizator are not directly saved in db
        em.detach(updatedStabilizator);
        updatedStabilizator.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(updatedStabilizator);

        restStabilizatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stabilizatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
        Stabilizator testStabilizator = stabilizatorList.get(stabilizatorList.size() - 1);
        assertThat(testStabilizator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStabilizator.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testStabilizator.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testStabilizator.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingStabilizator() throws Exception {
        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();
        stabilizator.setId(longCount.incrementAndGet());

        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStabilizatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stabilizatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStabilizator() throws Exception {
        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();
        stabilizator.setId(longCount.incrementAndGet());

        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStabilizatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStabilizator() throws Exception {
        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();
        stabilizator.setId(longCount.incrementAndGet());

        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStabilizatorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStabilizatorWithPatch() throws Exception {
        // Initialize the database
        stabilizatorRepository.saveAndFlush(stabilizator);

        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();

        // Update the stabilizator using partial update
        Stabilizator partialUpdatedStabilizator = new Stabilizator();
        partialUpdatedStabilizator.setId(stabilizator.getId());

        partialUpdatedStabilizator.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);

        restStabilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStabilizator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStabilizator))
            )
            .andExpect(status().isOk());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
        Stabilizator testStabilizator = stabilizatorList.get(stabilizatorList.size() - 1);
        assertThat(testStabilizator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStabilizator.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testStabilizator.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testStabilizator.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateStabilizatorWithPatch() throws Exception {
        // Initialize the database
        stabilizatorRepository.saveAndFlush(stabilizator);

        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();

        // Update the stabilizator using partial update
        Stabilizator partialUpdatedStabilizator = new Stabilizator();
        partialUpdatedStabilizator.setId(stabilizator.getId());

        partialUpdatedStabilizator.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);

        restStabilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStabilizator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStabilizator))
            )
            .andExpect(status().isOk());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
        Stabilizator testStabilizator = stabilizatorList.get(stabilizatorList.size() - 1);
        assertThat(testStabilizator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStabilizator.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testStabilizator.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testStabilizator.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingStabilizator() throws Exception {
        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();
        stabilizator.setId(longCount.incrementAndGet());

        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStabilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stabilizatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStabilizator() throws Exception {
        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();
        stabilizator.setId(longCount.incrementAndGet());

        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStabilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStabilizator() throws Exception {
        int databaseSizeBeforeUpdate = stabilizatorRepository.findAll().size();
        stabilizator.setId(longCount.incrementAndGet());

        // Create the Stabilizator
        StabilizatorDTO stabilizatorDTO = stabilizatorMapper.toDto(stabilizator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStabilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stabilizatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stabilizator in the database
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStabilizator() throws Exception {
        // Initialize the database
        stabilizatorRepository.saveAndFlush(stabilizator);

        int databaseSizeBeforeDelete = stabilizatorRepository.findAll().size();

        // Delete the stabilizator
        restStabilizatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, stabilizator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stabilizator> stabilizatorList = stabilizatorRepository.findAll();
        assertThat(stabilizatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
