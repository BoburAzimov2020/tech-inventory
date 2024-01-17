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
import uz.dynamic.techinventory.domain.Swich;
import uz.dynamic.techinventory.repository.SwichRepository;
import uz.dynamic.techinventory.service.dto.SwichDTO;
import uz.dynamic.techinventory.service.mapper.SwichMapper;

/**
 * Integration tests for the {@link SwichResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SwichResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PORT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/swiches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SwichRepository swichRepository;

    @Autowired
    private SwichMapper swichMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSwichMockMvc;

    private Swich swich;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Swich createEntity(EntityManager em) {
        Swich swich = new Swich().name(DEFAULT_NAME).model(DEFAULT_MODEL).portNumber(DEFAULT_PORT_NUMBER).info(DEFAULT_INFO);
        return swich;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Swich createUpdatedEntity(EntityManager em) {
        Swich swich = new Swich().name(UPDATED_NAME).model(UPDATED_MODEL).portNumber(UPDATED_PORT_NUMBER).info(UPDATED_INFO);
        return swich;
    }

    @BeforeEach
    public void initTest() {
        swich = createEntity(em);
    }

    @Test
    @Transactional
    void createSwich() throws Exception {
        int databaseSizeBeforeCreate = swichRepository.findAll().size();
        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);
        restSwichMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichDTO)))
            .andExpect(status().isCreated());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeCreate + 1);
        Swich testSwich = swichList.get(swichList.size() - 1);
        assertThat(testSwich.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSwich.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testSwich.getPortNumber()).isEqualTo(DEFAULT_PORT_NUMBER);
        assertThat(testSwich.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createSwichWithExistingId() throws Exception {
        // Create the Swich with an existing ID
        swich.setId(1L);
        SwichDTO swichDTO = swichMapper.toDto(swich);

        int databaseSizeBeforeCreate = swichRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSwichMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSwiches() throws Exception {
        // Initialize the database
        swichRepository.saveAndFlush(swich);

        // Get all the swichList
        restSwichMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(swich.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].portNumber").value(hasItem(DEFAULT_PORT_NUMBER)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getSwich() throws Exception {
        // Initialize the database
        swichRepository.saveAndFlush(swich);

        // Get the swich
        restSwichMockMvc
            .perform(get(ENTITY_API_URL_ID, swich.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(swich.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.portNumber").value(DEFAULT_PORT_NUMBER))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingSwich() throws Exception {
        // Get the swich
        restSwichMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSwich() throws Exception {
        // Initialize the database
        swichRepository.saveAndFlush(swich);

        int databaseSizeBeforeUpdate = swichRepository.findAll().size();

        // Update the swich
        Swich updatedSwich = swichRepository.findById(swich.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSwich are not directly saved in db
        em.detach(updatedSwich);
        updatedSwich.name(UPDATED_NAME).model(UPDATED_MODEL).portNumber(UPDATED_PORT_NUMBER).info(UPDATED_INFO);
        SwichDTO swichDTO = swichMapper.toDto(updatedSwich);

        restSwichMockMvc
            .perform(
                put(ENTITY_API_URL_ID, swichDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(swichDTO))
            )
            .andExpect(status().isOk());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
        Swich testSwich = swichList.get(swichList.size() - 1);
        assertThat(testSwich.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwich.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testSwich.getPortNumber()).isEqualTo(UPDATED_PORT_NUMBER);
        assertThat(testSwich.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingSwich() throws Exception {
        int databaseSizeBeforeUpdate = swichRepository.findAll().size();
        swich.setId(longCount.incrementAndGet());

        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSwichMockMvc
            .perform(
                put(ENTITY_API_URL_ID, swichDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(swichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSwich() throws Exception {
        int databaseSizeBeforeUpdate = swichRepository.findAll().size();
        swich.setId(longCount.incrementAndGet());

        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(swichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSwich() throws Exception {
        int databaseSizeBeforeUpdate = swichRepository.findAll().size();
        swich.setId(longCount.incrementAndGet());

        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(swichDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSwichWithPatch() throws Exception {
        // Initialize the database
        swichRepository.saveAndFlush(swich);

        int databaseSizeBeforeUpdate = swichRepository.findAll().size();

        // Update the swich using partial update
        Swich partialUpdatedSwich = new Swich();
        partialUpdatedSwich.setId(swich.getId());

        partialUpdatedSwich.name(UPDATED_NAME).model(UPDATED_MODEL);

        restSwichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSwich.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSwich))
            )
            .andExpect(status().isOk());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
        Swich testSwich = swichList.get(swichList.size() - 1);
        assertThat(testSwich.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwich.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testSwich.getPortNumber()).isEqualTo(DEFAULT_PORT_NUMBER);
        assertThat(testSwich.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateSwichWithPatch() throws Exception {
        // Initialize the database
        swichRepository.saveAndFlush(swich);

        int databaseSizeBeforeUpdate = swichRepository.findAll().size();

        // Update the swich using partial update
        Swich partialUpdatedSwich = new Swich();
        partialUpdatedSwich.setId(swich.getId());

        partialUpdatedSwich.name(UPDATED_NAME).model(UPDATED_MODEL).portNumber(UPDATED_PORT_NUMBER).info(UPDATED_INFO);

        restSwichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSwich.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSwich))
            )
            .andExpect(status().isOk());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
        Swich testSwich = swichList.get(swichList.size() - 1);
        assertThat(testSwich.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSwich.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testSwich.getPortNumber()).isEqualTo(UPDATED_PORT_NUMBER);
        assertThat(testSwich.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingSwich() throws Exception {
        int databaseSizeBeforeUpdate = swichRepository.findAll().size();
        swich.setId(longCount.incrementAndGet());

        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSwichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, swichDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(swichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSwich() throws Exception {
        int databaseSizeBeforeUpdate = swichRepository.findAll().size();
        swich.setId(longCount.incrementAndGet());

        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(swichDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSwich() throws Exception {
        int databaseSizeBeforeUpdate = swichRepository.findAll().size();
        swich.setId(longCount.incrementAndGet());

        // Create the Swich
        SwichDTO swichDTO = swichMapper.toDto(swich);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSwichMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(swichDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Swich in the database
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSwich() throws Exception {
        // Initialize the database
        swichRepository.saveAndFlush(swich);

        int databaseSizeBeforeDelete = swichRepository.findAll().size();

        // Delete the swich
        restSwichMockMvc
            .perform(delete(ENTITY_API_URL_ID, swich.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Swich> swichList = swichRepository.findAll();
        assertThat(swichList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
