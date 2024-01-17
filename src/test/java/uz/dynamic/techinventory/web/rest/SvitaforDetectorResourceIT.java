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
import uz.dynamic.techinventory.domain.SvitaforDetector;
import uz.dynamic.techinventory.repository.SvitaforDetectorRepository;
import uz.dynamic.techinventory.service.dto.SvitaforDetectorDTO;
import uz.dynamic.techinventory.service.mapper.SvitaforDetectorMapper;

/**
 * Integration tests for the {@link SvitaforDetectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SvitaforDetectorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PORT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/svitafor-detectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SvitaforDetectorRepository svitaforDetectorRepository;

    @Autowired
    private SvitaforDetectorMapper svitaforDetectorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSvitaforDetectorMockMvc;

    private SvitaforDetector svitaforDetector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SvitaforDetector createEntity(EntityManager em) {
        SvitaforDetector svitaforDetector = new SvitaforDetector()
            .name(DEFAULT_NAME)
            .model(DEFAULT_MODEL)
            .portNumber(DEFAULT_PORT_NUMBER)
            .info(DEFAULT_INFO);
        return svitaforDetector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SvitaforDetector createUpdatedEntity(EntityManager em) {
        SvitaforDetector svitaforDetector = new SvitaforDetector()
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .portNumber(UPDATED_PORT_NUMBER)
            .info(UPDATED_INFO);
        return svitaforDetector;
    }

    @BeforeEach
    public void initTest() {
        svitaforDetector = createEntity(em);
    }

    @Test
    @Transactional
    void createSvitaforDetector() throws Exception {
        int databaseSizeBeforeCreate = svitaforDetectorRepository.findAll().size();
        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);
        restSvitaforDetectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeCreate + 1);
        SvitaforDetector testSvitaforDetector = svitaforDetectorList.get(svitaforDetectorList.size() - 1);
        assertThat(testSvitaforDetector.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSvitaforDetector.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testSvitaforDetector.getPortNumber()).isEqualTo(DEFAULT_PORT_NUMBER);
        assertThat(testSvitaforDetector.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createSvitaforDetectorWithExistingId() throws Exception {
        // Create the SvitaforDetector with an existing ID
        svitaforDetector.setId(1L);
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        int databaseSizeBeforeCreate = svitaforDetectorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSvitaforDetectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSvitaforDetectors() throws Exception {
        // Initialize the database
        svitaforDetectorRepository.saveAndFlush(svitaforDetector);

        // Get all the svitaforDetectorList
        restSvitaforDetectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(svitaforDetector.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].portNumber").value(hasItem(DEFAULT_PORT_NUMBER)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getSvitaforDetector() throws Exception {
        // Initialize the database
        svitaforDetectorRepository.saveAndFlush(svitaforDetector);

        // Get the svitaforDetector
        restSvitaforDetectorMockMvc
            .perform(get(ENTITY_API_URL_ID, svitaforDetector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(svitaforDetector.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.portNumber").value(DEFAULT_PORT_NUMBER))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingSvitaforDetector() throws Exception {
        // Get the svitaforDetector
        restSvitaforDetectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSvitaforDetector() throws Exception {
        // Initialize the database
        svitaforDetectorRepository.saveAndFlush(svitaforDetector);

        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();

        // Update the svitaforDetector
        SvitaforDetector updatedSvitaforDetector = svitaforDetectorRepository.findById(svitaforDetector.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSvitaforDetector are not directly saved in db
        em.detach(updatedSvitaforDetector);
        updatedSvitaforDetector.name(UPDATED_NAME).model(UPDATED_MODEL).portNumber(UPDATED_PORT_NUMBER).info(UPDATED_INFO);
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(updatedSvitaforDetector);

        restSvitaforDetectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, svitaforDetectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isOk());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
        SvitaforDetector testSvitaforDetector = svitaforDetectorList.get(svitaforDetectorList.size() - 1);
        assertThat(testSvitaforDetector.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSvitaforDetector.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testSvitaforDetector.getPortNumber()).isEqualTo(UPDATED_PORT_NUMBER);
        assertThat(testSvitaforDetector.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingSvitaforDetector() throws Exception {
        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();
        svitaforDetector.setId(longCount.incrementAndGet());

        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSvitaforDetectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, svitaforDetectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSvitaforDetector() throws Exception {
        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();
        svitaforDetector.setId(longCount.incrementAndGet());

        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSvitaforDetectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSvitaforDetector() throws Exception {
        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();
        svitaforDetector.setId(longCount.incrementAndGet());

        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSvitaforDetectorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSvitaforDetectorWithPatch() throws Exception {
        // Initialize the database
        svitaforDetectorRepository.saveAndFlush(svitaforDetector);

        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();

        // Update the svitaforDetector using partial update
        SvitaforDetector partialUpdatedSvitaforDetector = new SvitaforDetector();
        partialUpdatedSvitaforDetector.setId(svitaforDetector.getId());

        partialUpdatedSvitaforDetector.name(UPDATED_NAME).portNumber(UPDATED_PORT_NUMBER);

        restSvitaforDetectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSvitaforDetector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSvitaforDetector))
            )
            .andExpect(status().isOk());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
        SvitaforDetector testSvitaforDetector = svitaforDetectorList.get(svitaforDetectorList.size() - 1);
        assertThat(testSvitaforDetector.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSvitaforDetector.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testSvitaforDetector.getPortNumber()).isEqualTo(UPDATED_PORT_NUMBER);
        assertThat(testSvitaforDetector.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateSvitaforDetectorWithPatch() throws Exception {
        // Initialize the database
        svitaforDetectorRepository.saveAndFlush(svitaforDetector);

        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();

        // Update the svitaforDetector using partial update
        SvitaforDetector partialUpdatedSvitaforDetector = new SvitaforDetector();
        partialUpdatedSvitaforDetector.setId(svitaforDetector.getId());

        partialUpdatedSvitaforDetector.name(UPDATED_NAME).model(UPDATED_MODEL).portNumber(UPDATED_PORT_NUMBER).info(UPDATED_INFO);

        restSvitaforDetectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSvitaforDetector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSvitaforDetector))
            )
            .andExpect(status().isOk());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
        SvitaforDetector testSvitaforDetector = svitaforDetectorList.get(svitaforDetectorList.size() - 1);
        assertThat(testSvitaforDetector.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSvitaforDetector.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testSvitaforDetector.getPortNumber()).isEqualTo(UPDATED_PORT_NUMBER);
        assertThat(testSvitaforDetector.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingSvitaforDetector() throws Exception {
        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();
        svitaforDetector.setId(longCount.incrementAndGet());

        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSvitaforDetectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, svitaforDetectorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSvitaforDetector() throws Exception {
        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();
        svitaforDetector.setId(longCount.incrementAndGet());

        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSvitaforDetectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSvitaforDetector() throws Exception {
        int databaseSizeBeforeUpdate = svitaforDetectorRepository.findAll().size();
        svitaforDetector.setId(longCount.incrementAndGet());

        // Create the SvitaforDetector
        SvitaforDetectorDTO svitaforDetectorDTO = svitaforDetectorMapper.toDto(svitaforDetector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSvitaforDetectorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(svitaforDetectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SvitaforDetector in the database
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSvitaforDetector() throws Exception {
        // Initialize the database
        svitaforDetectorRepository.saveAndFlush(svitaforDetector);

        int databaseSizeBeforeDelete = svitaforDetectorRepository.findAll().size();

        // Delete the svitaforDetector
        restSvitaforDetectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, svitaforDetector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SvitaforDetector> svitaforDetectorList = svitaforDetectorRepository.findAll();
        assertThat(svitaforDetectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
