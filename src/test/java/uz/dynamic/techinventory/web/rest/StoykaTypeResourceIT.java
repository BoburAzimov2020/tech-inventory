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
import uz.dynamic.techinventory.domain.StoykaType;
import uz.dynamic.techinventory.repository.StoykaTypeRepository;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;
import uz.dynamic.techinventory.service.mapper.StoykaTypeMapper;

/**
 * Integration tests for the {@link StoykaTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StoykaTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stoyka-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoykaTypeRepository stoykaTypeRepository;

    @Autowired
    private StoykaTypeMapper stoykaTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoykaTypeMockMvc;

    private StoykaType stoykaType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoykaType createEntity(EntityManager em) {
        StoykaType stoykaType = new StoykaType().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return stoykaType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoykaType createUpdatedEntity(EntityManager em) {
        StoykaType stoykaType = new StoykaType().name(UPDATED_NAME).info(UPDATED_INFO);
        return stoykaType;
    }

    @BeforeEach
    public void initTest() {
        stoykaType = createEntity(em);
    }

    @Test
    @Transactional
    void createStoykaType() throws Exception {
        int databaseSizeBeforeCreate = stoykaTypeRepository.findAll().size();
        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);
        restStoykaTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeCreate + 1);
        StoykaType testStoykaType = stoykaTypeList.get(stoykaTypeList.size() - 1);
        assertThat(testStoykaType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStoykaType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createStoykaTypeWithExistingId() throws Exception {
        // Create the StoykaType with an existing ID
        stoykaType.setId(1L);
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        int databaseSizeBeforeCreate = stoykaTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoykaTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStoykaTypes() throws Exception {
        // Initialize the database
        stoykaTypeRepository.saveAndFlush(stoykaType);

        // Get all the stoykaTypeList
        restStoykaTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stoykaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getStoykaType() throws Exception {
        // Initialize the database
        stoykaTypeRepository.saveAndFlush(stoykaType);

        // Get the stoykaType
        restStoykaTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, stoykaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stoykaType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingStoykaType() throws Exception {
        // Get the stoykaType
        restStoykaTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStoykaType() throws Exception {
        // Initialize the database
        stoykaTypeRepository.saveAndFlush(stoykaType);

        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();

        // Update the stoykaType
        StoykaType updatedStoykaType = stoykaTypeRepository.findById(stoykaType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStoykaType are not directly saved in db
        em.detach(updatedStoykaType);
        updatedStoykaType.name(UPDATED_NAME).info(UPDATED_INFO);
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(updatedStoykaType);

        restStoykaTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stoykaTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
        StoykaType testStoykaType = stoykaTypeList.get(stoykaTypeList.size() - 1);
        assertThat(testStoykaType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoykaType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingStoykaType() throws Exception {
        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();
        stoykaType.setId(longCount.incrementAndGet());

        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoykaTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stoykaTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStoykaType() throws Exception {
        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();
        stoykaType.setId(longCount.incrementAndGet());

        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStoykaType() throws Exception {
        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();
        stoykaType.setId(longCount.incrementAndGet());

        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoykaTypeWithPatch() throws Exception {
        // Initialize the database
        stoykaTypeRepository.saveAndFlush(stoykaType);

        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();

        // Update the stoykaType using partial update
        StoykaType partialUpdatedStoykaType = new StoykaType();
        partialUpdatedStoykaType.setId(stoykaType.getId());

        partialUpdatedStoykaType.info(UPDATED_INFO);

        restStoykaTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoykaType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoykaType))
            )
            .andExpect(status().isOk());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
        StoykaType testStoykaType = stoykaTypeList.get(stoykaTypeList.size() - 1);
        assertThat(testStoykaType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStoykaType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateStoykaTypeWithPatch() throws Exception {
        // Initialize the database
        stoykaTypeRepository.saveAndFlush(stoykaType);

        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();

        // Update the stoykaType using partial update
        StoykaType partialUpdatedStoykaType = new StoykaType();
        partialUpdatedStoykaType.setId(stoykaType.getId());

        partialUpdatedStoykaType.name(UPDATED_NAME).info(UPDATED_INFO);

        restStoykaTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStoykaType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStoykaType))
            )
            .andExpect(status().isOk());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
        StoykaType testStoykaType = stoykaTypeList.get(stoykaTypeList.size() - 1);
        assertThat(testStoykaType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStoykaType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingStoykaType() throws Exception {
        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();
        stoykaType.setId(longCount.incrementAndGet());

        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoykaTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stoykaTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStoykaType() throws Exception {
        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();
        stoykaType.setId(longCount.incrementAndGet());

        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStoykaType() throws Exception {
        int databaseSizeBeforeUpdate = stoykaTypeRepository.findAll().size();
        stoykaType.setId(longCount.incrementAndGet());

        // Create the StoykaType
        StoykaTypeDTO stoykaTypeDTO = stoykaTypeMapper.toDto(stoykaType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoykaTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stoykaTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StoykaType in the database
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStoykaType() throws Exception {
        // Initialize the database
        stoykaTypeRepository.saveAndFlush(stoykaType);

        int databaseSizeBeforeDelete = stoykaTypeRepository.findAll().size();

        // Delete the stoykaType
        restStoykaTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, stoykaType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StoykaType> stoykaTypeList = stoykaTypeRepository.findAll();
        assertThat(stoykaTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
