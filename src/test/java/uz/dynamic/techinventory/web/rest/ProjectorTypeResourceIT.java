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
import uz.dynamic.techinventory.domain.ProjectorType;
import uz.dynamic.techinventory.repository.ProjectorTypeRepository;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;
import uz.dynamic.techinventory.service.mapper.ProjectorTypeMapper;

/**
 * Integration tests for the {@link ProjectorTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectorTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projector-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectorTypeRepository projectorTypeRepository;

    @Autowired
    private ProjectorTypeMapper projectorTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectorTypeMockMvc;

    private ProjectorType projectorType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectorType createEntity(EntityManager em) {
        ProjectorType projectorType = new ProjectorType().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return projectorType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectorType createUpdatedEntity(EntityManager em) {
        ProjectorType projectorType = new ProjectorType().name(UPDATED_NAME).info(UPDATED_INFO);
        return projectorType;
    }

    @BeforeEach
    public void initTest() {
        projectorType = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectorType() throws Exception {
        int databaseSizeBeforeCreate = projectorTypeRepository.findAll().size();
        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);
        restProjectorTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectorType testProjectorType = projectorTypeList.get(projectorTypeList.size() - 1);
        assertThat(testProjectorType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectorType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createProjectorTypeWithExistingId() throws Exception {
        // Create the ProjectorType with an existing ID
        projectorType.setId(1L);
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        int databaseSizeBeforeCreate = projectorTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectorTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectorTypes() throws Exception {
        // Initialize the database
        projectorTypeRepository.saveAndFlush(projectorType);

        // Get all the projectorTypeList
        restProjectorTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectorType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getProjectorType() throws Exception {
        // Initialize the database
        projectorTypeRepository.saveAndFlush(projectorType);

        // Get the projectorType
        restProjectorTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, projectorType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectorType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingProjectorType() throws Exception {
        // Get the projectorType
        restProjectorTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectorType() throws Exception {
        // Initialize the database
        projectorTypeRepository.saveAndFlush(projectorType);

        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();

        // Update the projectorType
        ProjectorType updatedProjectorType = projectorTypeRepository.findById(projectorType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProjectorType are not directly saved in db
        em.detach(updatedProjectorType);
        updatedProjectorType.name(UPDATED_NAME).info(UPDATED_INFO);
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(updatedProjectorType);

        restProjectorTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectorTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectorType testProjectorType = projectorTypeList.get(projectorTypeList.size() - 1);
        assertThat(testProjectorType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectorType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingProjectorType() throws Exception {
        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();
        projectorType.setId(longCount.incrementAndGet());

        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectorTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectorTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectorType() throws Exception {
        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();
        projectorType.setId(longCount.incrementAndGet());

        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectorType() throws Exception {
        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();
        projectorType.setId(longCount.incrementAndGet());

        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectorTypeWithPatch() throws Exception {
        // Initialize the database
        projectorTypeRepository.saveAndFlush(projectorType);

        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();

        // Update the projectorType using partial update
        ProjectorType partialUpdatedProjectorType = new ProjectorType();
        partialUpdatedProjectorType.setId(projectorType.getId());

        partialUpdatedProjectorType.info(UPDATED_INFO);

        restProjectorTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectorType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectorType))
            )
            .andExpect(status().isOk());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectorType testProjectorType = projectorTypeList.get(projectorTypeList.size() - 1);
        assertThat(testProjectorType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectorType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateProjectorTypeWithPatch() throws Exception {
        // Initialize the database
        projectorTypeRepository.saveAndFlush(projectorType);

        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();

        // Update the projectorType using partial update
        ProjectorType partialUpdatedProjectorType = new ProjectorType();
        partialUpdatedProjectorType.setId(projectorType.getId());

        partialUpdatedProjectorType.name(UPDATED_NAME).info(UPDATED_INFO);

        restProjectorTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectorType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectorType))
            )
            .andExpect(status().isOk());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectorType testProjectorType = projectorTypeList.get(projectorTypeList.size() - 1);
        assertThat(testProjectorType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectorType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingProjectorType() throws Exception {
        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();
        projectorType.setId(longCount.incrementAndGet());

        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectorTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectorTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectorType() throws Exception {
        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();
        projectorType.setId(longCount.incrementAndGet());

        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectorType() throws Exception {
        int databaseSizeBeforeUpdate = projectorTypeRepository.findAll().size();
        projectorType.setId(longCount.incrementAndGet());

        // Create the ProjectorType
        ProjectorTypeDTO projectorTypeDTO = projectorTypeMapper.toDto(projectorType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectorTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectorTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectorType in the database
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectorType() throws Exception {
        // Initialize the database
        projectorTypeRepository.saveAndFlush(projectorType);

        int databaseSizeBeforeDelete = projectorTypeRepository.findAll().size();

        // Delete the projectorType
        restProjectorTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectorType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectorType> projectorTypeList = projectorTypeRepository.findAll();
        assertThat(projectorTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
