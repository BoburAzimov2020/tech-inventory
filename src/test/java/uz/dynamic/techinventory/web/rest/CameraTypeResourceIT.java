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
import uz.dynamic.techinventory.domain.CameraType;
import uz.dynamic.techinventory.repository.CameraTypeRepository;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;
import uz.dynamic.techinventory.service.mapper.CameraTypeMapper;

/**
 * Integration tests for the {@link CameraTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CameraTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/camera-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CameraTypeRepository cameraTypeRepository;

    @Autowired
    private CameraTypeMapper cameraTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCameraTypeMockMvc;

    private CameraType cameraType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CameraType createEntity(EntityManager em) {
        CameraType cameraType = new CameraType().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return cameraType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CameraType createUpdatedEntity(EntityManager em) {
        CameraType cameraType = new CameraType().name(UPDATED_NAME).info(UPDATED_INFO);
        return cameraType;
    }

    @BeforeEach
    public void initTest() {
        cameraType = createEntity(em);
    }

    @Test
    @Transactional
    void createCameraType() throws Exception {
        int databaseSizeBeforeCreate = cameraTypeRepository.findAll().size();
        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);
        restCameraTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CameraType testCameraType = cameraTypeList.get(cameraTypeList.size() - 1);
        assertThat(testCameraType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCameraType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createCameraTypeWithExistingId() throws Exception {
        // Create the CameraType with an existing ID
        cameraType.setId(1L);
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        int databaseSizeBeforeCreate = cameraTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCameraTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraTypeRepository.findAll().size();
        // set the field null
        cameraType.setName(null);

        // Create the CameraType, which fails.
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        restCameraTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCameraTypes() throws Exception {
        // Initialize the database
        cameraTypeRepository.saveAndFlush(cameraType);

        // Get all the cameraTypeList
        restCameraTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cameraType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getCameraType() throws Exception {
        // Initialize the database
        cameraTypeRepository.saveAndFlush(cameraType);

        // Get the cameraType
        restCameraTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cameraType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cameraType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingCameraType() throws Exception {
        // Get the cameraType
        restCameraTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCameraType() throws Exception {
        // Initialize the database
        cameraTypeRepository.saveAndFlush(cameraType);

        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();

        // Update the cameraType
        CameraType updatedCameraType = cameraTypeRepository.findById(cameraType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCameraType are not directly saved in db
        em.detach(updatedCameraType);
        updatedCameraType.name(UPDATED_NAME).info(UPDATED_INFO);
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(updatedCameraType);

        restCameraTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
        CameraType testCameraType = cameraTypeList.get(cameraTypeList.size() - 1);
        assertThat(testCameraType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCameraType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingCameraType() throws Exception {
        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();
        cameraType.setId(longCount.incrementAndGet());

        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCameraType() throws Exception {
        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();
        cameraType.setId(longCount.incrementAndGet());

        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCameraType() throws Exception {
        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();
        cameraType.setId(longCount.incrementAndGet());

        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCameraTypeWithPatch() throws Exception {
        // Initialize the database
        cameraTypeRepository.saveAndFlush(cameraType);

        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();

        // Update the cameraType using partial update
        CameraType partialUpdatedCameraType = new CameraType();
        partialUpdatedCameraType.setId(cameraType.getId());

        partialUpdatedCameraType.name(UPDATED_NAME).info(UPDATED_INFO);

        restCameraTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCameraType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCameraType))
            )
            .andExpect(status().isOk());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
        CameraType testCameraType = cameraTypeList.get(cameraTypeList.size() - 1);
        assertThat(testCameraType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCameraType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCameraTypeWithPatch() throws Exception {
        // Initialize the database
        cameraTypeRepository.saveAndFlush(cameraType);

        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();

        // Update the cameraType using partial update
        CameraType partialUpdatedCameraType = new CameraType();
        partialUpdatedCameraType.setId(cameraType.getId());

        partialUpdatedCameraType.name(UPDATED_NAME).info(UPDATED_INFO);

        restCameraTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCameraType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCameraType))
            )
            .andExpect(status().isOk());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
        CameraType testCameraType = cameraTypeList.get(cameraTypeList.size() - 1);
        assertThat(testCameraType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCameraType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingCameraType() throws Exception {
        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();
        cameraType.setId(longCount.incrementAndGet());

        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cameraTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCameraType() throws Exception {
        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();
        cameraType.setId(longCount.incrementAndGet());

        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCameraType() throws Exception {
        int databaseSizeBeforeUpdate = cameraTypeRepository.findAll().size();
        cameraType.setId(longCount.incrementAndGet());

        // Create the CameraType
        CameraTypeDTO cameraTypeDTO = cameraTypeMapper.toDto(cameraType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cameraTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CameraType in the database
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCameraType() throws Exception {
        // Initialize the database
        cameraTypeRepository.saveAndFlush(cameraType);

        int databaseSizeBeforeDelete = cameraTypeRepository.findAll().size();

        // Delete the cameraType
        restCameraTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cameraType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CameraType> cameraTypeList = cameraTypeRepository.findAll();
        assertThat(cameraTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
