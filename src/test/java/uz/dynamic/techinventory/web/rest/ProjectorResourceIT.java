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
import uz.dynamic.techinventory.domain.Projector;
import uz.dynamic.techinventory.repository.ProjectorRepository;
import uz.dynamic.techinventory.service.dto.ProjectorDTO;
import uz.dynamic.techinventory.service.mapper.ProjectorMapper;

/**
 * Integration tests for the {@link ProjectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectorRepository projectorRepository;

    @Autowired
    private ProjectorMapper projectorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectorMockMvc;

    private Projector projector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projector createEntity(EntityManager em) {
        Projector projector = new Projector().name(DEFAULT_NAME).model(DEFAULT_MODEL).info(DEFAULT_INFO);
        return projector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projector createUpdatedEntity(EntityManager em) {
        Projector projector = new Projector().name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);
        return projector;
    }

    @BeforeEach
    public void initTest() {
        projector = createEntity(em);
    }

    @Test
    @Transactional
    void createProjector() throws Exception {
        int databaseSizeBeforeCreate = projectorRepository.findAll().size();
        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);
        restProjectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeCreate + 1);
        Projector testProjector = projectorList.get(projectorList.size() - 1);
        assertThat(testProjector.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjector.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testProjector.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createProjectorWithExistingId() throws Exception {
        // Create the Projector with an existing ID
        projector.setId(1L);
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        int databaseSizeBeforeCreate = projectorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectors() throws Exception {
        // Initialize the database
        projectorRepository.saveAndFlush(projector);

        // Get all the projectorList
        restProjectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projector.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getProjector() throws Exception {
        // Initialize the database
        projectorRepository.saveAndFlush(projector);

        // Get the projector
        restProjectorMockMvc
            .perform(get(ENTITY_API_URL_ID, projector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projector.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingProjector() throws Exception {
        // Get the projector
        restProjectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjector() throws Exception {
        // Initialize the database
        projectorRepository.saveAndFlush(projector);

        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();

        // Update the projector
        Projector updatedProjector = projectorRepository.findById(projector.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjector are not directly saved in db
        em.detach(updatedProjector);
        updatedProjector.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);
        ProjectorDTO projectorDTO = projectorMapper.toDto(updatedProjector);

        restProjectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
        Projector testProjector = projectorList.get(projectorList.size() - 1);
        assertThat(testProjector.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjector.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testProjector.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingProjector() throws Exception {
        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();
        projector.setId(longCount.incrementAndGet());

        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjector() throws Exception {
        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();
        projector.setId(longCount.incrementAndGet());

        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjector() throws Exception {
        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();
        projector.setId(longCount.incrementAndGet());

        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectorWithPatch() throws Exception {
        // Initialize the database
        projectorRepository.saveAndFlush(projector);

        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();

        // Update the projector using partial update
        Projector partialUpdatedProjector = new Projector();
        partialUpdatedProjector.setId(projector.getId());

        partialUpdatedProjector.model(UPDATED_MODEL);

        restProjectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjector))
            )
            .andExpect(status().isOk());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
        Projector testProjector = projectorList.get(projectorList.size() - 1);
        assertThat(testProjector.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjector.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testProjector.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateProjectorWithPatch() throws Exception {
        // Initialize the database
        projectorRepository.saveAndFlush(projector);

        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();

        // Update the projector using partial update
        Projector partialUpdatedProjector = new Projector();
        partialUpdatedProjector.setId(projector.getId());

        partialUpdatedProjector.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);

        restProjectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjector))
            )
            .andExpect(status().isOk());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
        Projector testProjector = projectorList.get(projectorList.size() - 1);
        assertThat(testProjector.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjector.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testProjector.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingProjector() throws Exception {
        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();
        projector.setId(longCount.incrementAndGet());

        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjector() throws Exception {
        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();
        projector.setId(longCount.incrementAndGet());

        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjector() throws Exception {
        int databaseSizeBeforeUpdate = projectorRepository.findAll().size();
        projector.setId(longCount.incrementAndGet());

        // Create the Projector
        ProjectorDTO projectorDTO = projectorMapper.toDto(projector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projector in the database
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjector() throws Exception {
        // Initialize the database
        projectorRepository.saveAndFlush(projector);

        int databaseSizeBeforeDelete = projectorRepository.findAll().size();

        // Delete the projector
        restProjectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, projector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projector> projectorList = projectorRepository.findAll();
        assertThat(projectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
