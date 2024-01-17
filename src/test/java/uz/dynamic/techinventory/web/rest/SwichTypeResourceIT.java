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
import uz.dynamic.techinventory.domain.SwichType;
import uz.dynamic.techinventory.repository.SwichTypeRepository;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;
import uz.dynamic.techinventory.service.mapper.SwichTypeMapper;

/**
 * Integration tests for the {@link SwichTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SwichTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/swich-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SwichTypeRepository swichTypeRepository;

    @Autowired
    private SwichTypeMapper swichTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSwichTypeMockMvc;

    private SwichType swichType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SwichType createEntity(EntityManager em) {
        SwichType swichType = new SwichType().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return swichType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SwichType createUpdatedEntity(EntityManager em) {
        SwichType swichType = new SwichType().name(UPDATED_NAME).info(UPDATED_INFO);
        return swichType;
    }

    @BeforeEach
    public void initTest() {
        swichType = createEntity(em);
    }

    @Test
    @Transactional
    void createSwichType() throws Exception {
        int databaseSizeBeforeCreate = swichTypeRepository.findAll().size();
        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);
        restSwichTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SwichType testSwichType = swichTypeList.get(swichTypeList.size() - 1);
        assertThat(testSwichType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSwichType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createSwichTypeWithExistingId() throws Exception {
        // Create the SwichType with an existing ID
        swichType.setId(1L);
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        int databaseSizeBeforeCreate = swichTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSwichTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = swichTypeRepository.findAll().size();
        // set the field null
        swichType.setName(null);

        // Create the SwichType, which fails.
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        restSwichTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichTypeDTO)))
            .andExpect(status().isBadRequest());

        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSwichTypes() throws Exception {
        // Initialize the database
        swichTypeRepository.saveAndFlush(swichType);

        // Get all the swichTypeList
        restSwichTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(swichType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getSwichType() throws Exception {
        // Initialize the database
        swichTypeRepository.saveAndFlush(swichType);

        // Get the swichType
        restSwichTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, swichType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(swichType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingSwichType() throws Exception {
        // Get the swichType
        restSwichTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSwichType() throws Exception {
        // Initialize the database
        swichTypeRepository.saveAndFlush(swichType);

        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();

        // Update the swichType
        SwichType updatedSwichType = swichTypeRepository.findById(swichType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSwichType are not directly saved in db
        em.detach(updatedSwichType);
        updatedSwichType.name(UPDATED_NAME).info(UPDATED_INFO);
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(updatedSwichType);

        restSwichTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, swichTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(swichTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
        SwichType testSwichType = swichTypeList.get(swichTypeList.size() - 1);
        assertThat(testSwichType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwichType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingSwichType() throws Exception {
        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();
        swichType.setId(longCount.incrementAndGet());

        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSwichTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, swichTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(swichTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSwichType() throws Exception {
        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();
        swichType.setId(longCount.incrementAndGet());

        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(swichTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSwichType() throws Exception {
        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();
        swichType.setId(longCount.incrementAndGet());

        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSwichTypeWithPatch() throws Exception {
        // Initialize the database
        swichTypeRepository.saveAndFlush(swichType);

        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();

        // Update the swichType using partial update
        SwichType partialUpdatedSwichType = new SwichType();
        partialUpdatedSwichType.setId(swichType.getId());

        partialUpdatedSwichType.name(UPDATED_NAME);

        restSwichTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSwichType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSwichType))
            )
            .andExpect(status().isOk());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
        SwichType testSwichType = swichTypeList.get(swichTypeList.size() - 1);
        assertThat(testSwichType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwichType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateSwichTypeWithPatch() throws Exception {
        // Initialize the database
        swichTypeRepository.saveAndFlush(swichType);

        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();

        // Update the swichType using partial update
        SwichType partialUpdatedSwichType = new SwichType();
        partialUpdatedSwichType.setId(swichType.getId());

        partialUpdatedSwichType.name(UPDATED_NAME).info(UPDATED_INFO);

        restSwichTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSwichType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSwichType))
            )
            .andExpect(status().isOk());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
        SwichType testSwichType = swichTypeList.get(swichTypeList.size() - 1);
        assertThat(testSwichType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwichType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingSwichType() throws Exception {
        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();
        swichType.setId(longCount.incrementAndGet());

        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSwichTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, swichTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(swichTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSwichType() throws Exception {
        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();
        swichType.setId(longCount.incrementAndGet());

        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(swichTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSwichType() throws Exception {
        int databaseSizeBeforeUpdate = swichTypeRepository.findAll().size();
        swichType.setId(longCount.incrementAndGet());

        // Create the SwichType
        SwichTypeDTO swichTypeDTO = swichTypeMapper.toDto(swichType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(swichTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SwichType in the database
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSwichType() throws Exception {
        // Initialize the database
        swichTypeRepository.saveAndFlush(swichType);

        int databaseSizeBeforeDelete = swichTypeRepository.findAll().size();

        // Delete the swichType
        restSwichTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, swichType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SwichType> swichTypeList = swichTypeRepository.findAll();
        assertThat(swichTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
