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
import uz.dynamic.techinventory.domain.CabelType;
import uz.dynamic.techinventory.repository.CabelTypeRepository;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;
import uz.dynamic.techinventory.service.mapper.CabelTypeMapper;

/**
 * Integration tests for the {@link CabelTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CabelTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cabel-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CabelTypeRepository cabelTypeRepository;

    @Autowired
    private CabelTypeMapper cabelTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCabelTypeMockMvc;

    private CabelType cabelType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CabelType createEntity(EntityManager em) {
        CabelType cabelType = new CabelType().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return cabelType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CabelType createUpdatedEntity(EntityManager em) {
        CabelType cabelType = new CabelType().name(UPDATED_NAME).info(UPDATED_INFO);
        return cabelType;
    }

    @BeforeEach
    public void initTest() {
        cabelType = createEntity(em);
    }

    @Test
    @Transactional
    void createCabelType() throws Exception {
        int databaseSizeBeforeCreate = cabelTypeRepository.findAll().size();
        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);
        restCabelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CabelType testCabelType = cabelTypeList.get(cabelTypeList.size() - 1);
        assertThat(testCabelType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCabelType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createCabelTypeWithExistingId() throws Exception {
        // Create the CabelType with an existing ID
        cabelType.setId(1L);
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        int databaseSizeBeforeCreate = cabelTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCabelTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCabelTypes() throws Exception {
        // Initialize the database
        cabelTypeRepository.saveAndFlush(cabelType);

        // Get all the cabelTypeList
        restCabelTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cabelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getCabelType() throws Exception {
        // Initialize the database
        cabelTypeRepository.saveAndFlush(cabelType);

        // Get the cabelType
        restCabelTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cabelType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cabelType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingCabelType() throws Exception {
        // Get the cabelType
        restCabelTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCabelType() throws Exception {
        // Initialize the database
        cabelTypeRepository.saveAndFlush(cabelType);

        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();

        // Update the cabelType
        CabelType updatedCabelType = cabelTypeRepository.findById(cabelType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCabelType are not directly saved in db
        em.detach(updatedCabelType);
        updatedCabelType.name(UPDATED_NAME).info(UPDATED_INFO);
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(updatedCabelType);

        restCabelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cabelTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
        CabelType testCabelType = cabelTypeList.get(cabelTypeList.size() - 1);
        assertThat(testCabelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCabelType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingCabelType() throws Exception {
        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();
        cabelType.setId(longCount.incrementAndGet());

        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCabelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cabelTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCabelType() throws Exception {
        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();
        cabelType.setId(longCount.incrementAndGet());

        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCabelType() throws Exception {
        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();
        cabelType.setId(longCount.incrementAndGet());

        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCabelTypeWithPatch() throws Exception {
        // Initialize the database
        cabelTypeRepository.saveAndFlush(cabelType);

        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();

        // Update the cabelType using partial update
        CabelType partialUpdatedCabelType = new CabelType();
        partialUpdatedCabelType.setId(cabelType.getId());

        partialUpdatedCabelType.name(UPDATED_NAME).info(UPDATED_INFO);

        restCabelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCabelType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCabelType))
            )
            .andExpect(status().isOk());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
        CabelType testCabelType = cabelTypeList.get(cabelTypeList.size() - 1);
        assertThat(testCabelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCabelType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCabelTypeWithPatch() throws Exception {
        // Initialize the database
        cabelTypeRepository.saveAndFlush(cabelType);

        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();

        // Update the cabelType using partial update
        CabelType partialUpdatedCabelType = new CabelType();
        partialUpdatedCabelType.setId(cabelType.getId());

        partialUpdatedCabelType.name(UPDATED_NAME).info(UPDATED_INFO);

        restCabelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCabelType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCabelType))
            )
            .andExpect(status().isOk());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
        CabelType testCabelType = cabelTypeList.get(cabelTypeList.size() - 1);
        assertThat(testCabelType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCabelType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingCabelType() throws Exception {
        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();
        cabelType.setId(longCount.incrementAndGet());

        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCabelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cabelTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCabelType() throws Exception {
        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();
        cabelType.setId(longCount.incrementAndGet());

        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCabelType() throws Exception {
        int databaseSizeBeforeUpdate = cabelTypeRepository.findAll().size();
        cabelType.setId(longCount.incrementAndGet());

        // Create the CabelType
        CabelTypeDTO cabelTypeDTO = cabelTypeMapper.toDto(cabelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cabelTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CabelType in the database
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCabelType() throws Exception {
        // Initialize the database
        cabelTypeRepository.saveAndFlush(cabelType);

        int databaseSizeBeforeDelete = cabelTypeRepository.findAll().size();

        // Delete the cabelType
        restCabelTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cabelType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CabelType> cabelTypeList = cabelTypeRepository.findAll();
        assertThat(cabelTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
