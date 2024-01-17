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
import uz.dynamic.techinventory.domain.ShelfType;
import uz.dynamic.techinventory.repository.ShelfTypeRepository;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;
import uz.dynamic.techinventory.service.mapper.ShelfTypeMapper;

/**
 * Integration tests for the {@link ShelfTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShelfTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shelf-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShelfTypeRepository shelfTypeRepository;

    @Autowired
    private ShelfTypeMapper shelfTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShelfTypeMockMvc;

    private ShelfType shelfType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShelfType createEntity(EntityManager em) {
        ShelfType shelfType = new ShelfType().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return shelfType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShelfType createUpdatedEntity(EntityManager em) {
        ShelfType shelfType = new ShelfType().name(UPDATED_NAME).info(UPDATED_INFO);
        return shelfType;
    }

    @BeforeEach
    public void initTest() {
        shelfType = createEntity(em);
    }

    @Test
    @Transactional
    void createShelfType() throws Exception {
        int databaseSizeBeforeCreate = shelfTypeRepository.findAll().size();
        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);
        restShelfTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ShelfType testShelfType = shelfTypeList.get(shelfTypeList.size() - 1);
        assertThat(testShelfType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShelfType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createShelfTypeWithExistingId() throws Exception {
        // Create the ShelfType with an existing ID
        shelfType.setId(1L);
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        int databaseSizeBeforeCreate = shelfTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelfTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllShelfTypes() throws Exception {
        // Initialize the database
        shelfTypeRepository.saveAndFlush(shelfType);

        // Get all the shelfTypeList
        restShelfTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelfType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getShelfType() throws Exception {
        // Initialize the database
        shelfTypeRepository.saveAndFlush(shelfType);

        // Get the shelfType
        restShelfTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, shelfType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shelfType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingShelfType() throws Exception {
        // Get the shelfType
        restShelfTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShelfType() throws Exception {
        // Initialize the database
        shelfTypeRepository.saveAndFlush(shelfType);

        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();

        // Update the shelfType
        ShelfType updatedShelfType = shelfTypeRepository.findById(shelfType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedShelfType are not directly saved in db
        em.detach(updatedShelfType);
        updatedShelfType.name(UPDATED_NAME).info(UPDATED_INFO);
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(updatedShelfType);

        restShelfTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shelfTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
        ShelfType testShelfType = shelfTypeList.get(shelfTypeList.size() - 1);
        assertThat(testShelfType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShelfType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingShelfType() throws Exception {
        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();
        shelfType.setId(longCount.incrementAndGet());

        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelfTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shelfTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShelfType() throws Exception {
        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();
        shelfType.setId(longCount.incrementAndGet());

        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShelfType() throws Exception {
        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();
        shelfType.setId(longCount.incrementAndGet());

        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShelfTypeWithPatch() throws Exception {
        // Initialize the database
        shelfTypeRepository.saveAndFlush(shelfType);

        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();

        // Update the shelfType using partial update
        ShelfType partialUpdatedShelfType = new ShelfType();
        partialUpdatedShelfType.setId(shelfType.getId());

        restShelfTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShelfType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShelfType))
            )
            .andExpect(status().isOk());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
        ShelfType testShelfType = shelfTypeList.get(shelfTypeList.size() - 1);
        assertThat(testShelfType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShelfType.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateShelfTypeWithPatch() throws Exception {
        // Initialize the database
        shelfTypeRepository.saveAndFlush(shelfType);

        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();

        // Update the shelfType using partial update
        ShelfType partialUpdatedShelfType = new ShelfType();
        partialUpdatedShelfType.setId(shelfType.getId());

        partialUpdatedShelfType.name(UPDATED_NAME).info(UPDATED_INFO);

        restShelfTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShelfType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShelfType))
            )
            .andExpect(status().isOk());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
        ShelfType testShelfType = shelfTypeList.get(shelfTypeList.size() - 1);
        assertThat(testShelfType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShelfType.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingShelfType() throws Exception {
        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();
        shelfType.setId(longCount.incrementAndGet());

        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelfTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shelfTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShelfType() throws Exception {
        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();
        shelfType.setId(longCount.incrementAndGet());

        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShelfType() throws Exception {
        int databaseSizeBeforeUpdate = shelfTypeRepository.findAll().size();
        shelfType.setId(longCount.incrementAndGet());

        // Create the ShelfType
        ShelfTypeDTO shelfTypeDTO = shelfTypeMapper.toDto(shelfType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shelfTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShelfType in the database
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShelfType() throws Exception {
        // Initialize the database
        shelfTypeRepository.saveAndFlush(shelfType);

        int databaseSizeBeforeDelete = shelfTypeRepository.findAll().size();

        // Delete the shelfType
        restShelfTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, shelfType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShelfType> shelfTypeList = shelfTypeRepository.findAll();
        assertThat(shelfTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
