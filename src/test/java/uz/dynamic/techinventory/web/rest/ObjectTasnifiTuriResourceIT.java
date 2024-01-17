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
import uz.dynamic.techinventory.domain.ObjectTasnifiTuri;
import uz.dynamic.techinventory.repository.ObjectTasnifiTuriRepository;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiTuriDTO;
import uz.dynamic.techinventory.service.mapper.ObjectTasnifiTuriMapper;

/**
 * Integration tests for the {@link ObjectTasnifiTuriResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObjectTasnifiTuriResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/object-tasnifi-turis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectTasnifiTuriRepository objectTasnifiTuriRepository;

    @Autowired
    private ObjectTasnifiTuriMapper objectTasnifiTuriMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjectTasnifiTuriMockMvc;

    private ObjectTasnifiTuri objectTasnifiTuri;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjectTasnifiTuri createEntity(EntityManager em) {
        ObjectTasnifiTuri objectTasnifiTuri = new ObjectTasnifiTuri().name(DEFAULT_NAME);
        return objectTasnifiTuri;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjectTasnifiTuri createUpdatedEntity(EntityManager em) {
        ObjectTasnifiTuri objectTasnifiTuri = new ObjectTasnifiTuri().name(UPDATED_NAME);
        return objectTasnifiTuri;
    }

    @BeforeEach
    public void initTest() {
        objectTasnifiTuri = createEntity(em);
    }

    @Test
    @Transactional
    void createObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeCreate = objectTasnifiTuriRepository.findAll().size();
        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);
        restObjectTasnifiTuriMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeCreate + 1);
        ObjectTasnifiTuri testObjectTasnifiTuri = objectTasnifiTuriList.get(objectTasnifiTuriList.size() - 1);
        assertThat(testObjectTasnifiTuri.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createObjectTasnifiTuriWithExistingId() throws Exception {
        // Create the ObjectTasnifiTuri with an existing ID
        objectTasnifiTuri.setId(1L);
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        int databaseSizeBeforeCreate = objectTasnifiTuriRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjectTasnifiTuriMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = objectTasnifiTuriRepository.findAll().size();
        // set the field null
        objectTasnifiTuri.setName(null);

        // Create the ObjectTasnifiTuri, which fails.
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        restObjectTasnifiTuriMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isBadRequest());

        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObjectTasnifiTuris() throws Exception {
        // Initialize the database
        objectTasnifiTuriRepository.saveAndFlush(objectTasnifiTuri);

        // Get all the objectTasnifiTuriList
        restObjectTasnifiTuriMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objectTasnifiTuri.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getObjectTasnifiTuri() throws Exception {
        // Initialize the database
        objectTasnifiTuriRepository.saveAndFlush(objectTasnifiTuri);

        // Get the objectTasnifiTuri
        restObjectTasnifiTuriMockMvc
            .perform(get(ENTITY_API_URL_ID, objectTasnifiTuri.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objectTasnifiTuri.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingObjectTasnifiTuri() throws Exception {
        // Get the objectTasnifiTuri
        restObjectTasnifiTuriMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObjectTasnifiTuri() throws Exception {
        // Initialize the database
        objectTasnifiTuriRepository.saveAndFlush(objectTasnifiTuri);

        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();

        // Update the objectTasnifiTuri
        ObjectTasnifiTuri updatedObjectTasnifiTuri = objectTasnifiTuriRepository.findById(objectTasnifiTuri.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedObjectTasnifiTuri are not directly saved in db
        em.detach(updatedObjectTasnifiTuri);
        updatedObjectTasnifiTuri.name(UPDATED_NAME);
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(updatedObjectTasnifiTuri);

        restObjectTasnifiTuriMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectTasnifiTuriDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isOk());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
        ObjectTasnifiTuri testObjectTasnifiTuri = objectTasnifiTuriList.get(objectTasnifiTuriList.size() - 1);
        assertThat(testObjectTasnifiTuri.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();
        objectTasnifiTuri.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectTasnifiTuriMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectTasnifiTuriDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();
        objectTasnifiTuri.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiTuriMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();
        objectTasnifiTuri.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiTuriMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObjectTasnifiTuriWithPatch() throws Exception {
        // Initialize the database
        objectTasnifiTuriRepository.saveAndFlush(objectTasnifiTuri);

        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();

        // Update the objectTasnifiTuri using partial update
        ObjectTasnifiTuri partialUpdatedObjectTasnifiTuri = new ObjectTasnifiTuri();
        partialUpdatedObjectTasnifiTuri.setId(objectTasnifiTuri.getId());

        restObjectTasnifiTuriMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjectTasnifiTuri.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjectTasnifiTuri))
            )
            .andExpect(status().isOk());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
        ObjectTasnifiTuri testObjectTasnifiTuri = objectTasnifiTuriList.get(objectTasnifiTuriList.size() - 1);
        assertThat(testObjectTasnifiTuri.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateObjectTasnifiTuriWithPatch() throws Exception {
        // Initialize the database
        objectTasnifiTuriRepository.saveAndFlush(objectTasnifiTuri);

        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();

        // Update the objectTasnifiTuri using partial update
        ObjectTasnifiTuri partialUpdatedObjectTasnifiTuri = new ObjectTasnifiTuri();
        partialUpdatedObjectTasnifiTuri.setId(objectTasnifiTuri.getId());

        partialUpdatedObjectTasnifiTuri.name(UPDATED_NAME);

        restObjectTasnifiTuriMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjectTasnifiTuri.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjectTasnifiTuri))
            )
            .andExpect(status().isOk());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
        ObjectTasnifiTuri testObjectTasnifiTuri = objectTasnifiTuriList.get(objectTasnifiTuriList.size() - 1);
        assertThat(testObjectTasnifiTuri.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();
        objectTasnifiTuri.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectTasnifiTuriMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, objectTasnifiTuriDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();
        objectTasnifiTuri.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiTuriMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObjectTasnifiTuri() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiTuriRepository.findAll().size();
        objectTasnifiTuri.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifiTuri
        ObjectTasnifiTuriDTO objectTasnifiTuriDTO = objectTasnifiTuriMapper.toDto(objectTasnifiTuri);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiTuriMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiTuriDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjectTasnifiTuri in the database
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObjectTasnifiTuri() throws Exception {
        // Initialize the database
        objectTasnifiTuriRepository.saveAndFlush(objectTasnifiTuri);

        int databaseSizeBeforeDelete = objectTasnifiTuriRepository.findAll().size();

        // Delete the objectTasnifiTuri
        restObjectTasnifiTuriMockMvc
            .perform(delete(ENTITY_API_URL_ID, objectTasnifiTuri.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObjectTasnifiTuri> objectTasnifiTuriList = objectTasnifiTuriRepository.findAll();
        assertThat(objectTasnifiTuriList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
