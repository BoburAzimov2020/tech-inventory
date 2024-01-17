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
import uz.dynamic.techinventory.domain.ObjectTasnifi;
import uz.dynamic.techinventory.repository.ObjectTasnifiRepository;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiDTO;
import uz.dynamic.techinventory.service.mapper.ObjectTasnifiMapper;

/**
 * Integration tests for the {@link ObjectTasnifiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObjectTasnifiResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/object-tasnifis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectTasnifiRepository objectTasnifiRepository;

    @Autowired
    private ObjectTasnifiMapper objectTasnifiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjectTasnifiMockMvc;

    private ObjectTasnifi objectTasnifi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjectTasnifi createEntity(EntityManager em) {
        ObjectTasnifi objectTasnifi = new ObjectTasnifi().name(DEFAULT_NAME);
        return objectTasnifi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjectTasnifi createUpdatedEntity(EntityManager em) {
        ObjectTasnifi objectTasnifi = new ObjectTasnifi().name(UPDATED_NAME);
        return objectTasnifi;
    }

    @BeforeEach
    public void initTest() {
        objectTasnifi = createEntity(em);
    }

    @Test
    @Transactional
    void createObjectTasnifi() throws Exception {
        int databaseSizeBeforeCreate = objectTasnifiRepository.findAll().size();
        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);
        restObjectTasnifiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeCreate + 1);
        ObjectTasnifi testObjectTasnifi = objectTasnifiList.get(objectTasnifiList.size() - 1);
        assertThat(testObjectTasnifi.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createObjectTasnifiWithExistingId() throws Exception {
        // Create the ObjectTasnifi with an existing ID
        objectTasnifi.setId(1L);
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        int databaseSizeBeforeCreate = objectTasnifiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjectTasnifiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = objectTasnifiRepository.findAll().size();
        // set the field null
        objectTasnifi.setName(null);

        // Create the ObjectTasnifi, which fails.
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        restObjectTasnifiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isBadRequest());

        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObjectTasnifis() throws Exception {
        // Initialize the database
        objectTasnifiRepository.saveAndFlush(objectTasnifi);

        // Get all the objectTasnifiList
        restObjectTasnifiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objectTasnifi.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getObjectTasnifi() throws Exception {
        // Initialize the database
        objectTasnifiRepository.saveAndFlush(objectTasnifi);

        // Get the objectTasnifi
        restObjectTasnifiMockMvc
            .perform(get(ENTITY_API_URL_ID, objectTasnifi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objectTasnifi.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingObjectTasnifi() throws Exception {
        // Get the objectTasnifi
        restObjectTasnifiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObjectTasnifi() throws Exception {
        // Initialize the database
        objectTasnifiRepository.saveAndFlush(objectTasnifi);

        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();

        // Update the objectTasnifi
        ObjectTasnifi updatedObjectTasnifi = objectTasnifiRepository.findById(objectTasnifi.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedObjectTasnifi are not directly saved in db
        em.detach(updatedObjectTasnifi);
        updatedObjectTasnifi.name(UPDATED_NAME);
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(updatedObjectTasnifi);

        restObjectTasnifiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectTasnifiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isOk());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
        ObjectTasnifi testObjectTasnifi = objectTasnifiList.get(objectTasnifiList.size() - 1);
        assertThat(testObjectTasnifi.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingObjectTasnifi() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();
        objectTasnifi.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectTasnifiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectTasnifiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObjectTasnifi() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();
        objectTasnifi.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObjectTasnifi() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();
        objectTasnifi.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObjectTasnifiWithPatch() throws Exception {
        // Initialize the database
        objectTasnifiRepository.saveAndFlush(objectTasnifi);

        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();

        // Update the objectTasnifi using partial update
        ObjectTasnifi partialUpdatedObjectTasnifi = new ObjectTasnifi();
        partialUpdatedObjectTasnifi.setId(objectTasnifi.getId());

        restObjectTasnifiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjectTasnifi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjectTasnifi))
            )
            .andExpect(status().isOk());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
        ObjectTasnifi testObjectTasnifi = objectTasnifiList.get(objectTasnifiList.size() - 1);
        assertThat(testObjectTasnifi.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateObjectTasnifiWithPatch() throws Exception {
        // Initialize the database
        objectTasnifiRepository.saveAndFlush(objectTasnifi);

        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();

        // Update the objectTasnifi using partial update
        ObjectTasnifi partialUpdatedObjectTasnifi = new ObjectTasnifi();
        partialUpdatedObjectTasnifi.setId(objectTasnifi.getId());

        partialUpdatedObjectTasnifi.name(UPDATED_NAME);

        restObjectTasnifiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjectTasnifi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjectTasnifi))
            )
            .andExpect(status().isOk());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
        ObjectTasnifi testObjectTasnifi = objectTasnifiList.get(objectTasnifiList.size() - 1);
        assertThat(testObjectTasnifi.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingObjectTasnifi() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();
        objectTasnifi.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectTasnifiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, objectTasnifiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObjectTasnifi() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();
        objectTasnifi.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObjectTasnifi() throws Exception {
        int databaseSizeBeforeUpdate = objectTasnifiRepository.findAll().size();
        objectTasnifi.setId(longCount.incrementAndGet());

        // Create the ObjectTasnifi
        ObjectTasnifiDTO objectTasnifiDTO = objectTasnifiMapper.toDto(objectTasnifi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectTasnifiMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectTasnifiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjectTasnifi in the database
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObjectTasnifi() throws Exception {
        // Initialize the database
        objectTasnifiRepository.saveAndFlush(objectTasnifi);

        int databaseSizeBeforeDelete = objectTasnifiRepository.findAll().size();

        // Delete the objectTasnifi
        restObjectTasnifiMockMvc
            .perform(delete(ENTITY_API_URL_ID, objectTasnifi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObjectTasnifi> objectTasnifiList = objectTasnifiRepository.findAll();
        assertThat(objectTasnifiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
