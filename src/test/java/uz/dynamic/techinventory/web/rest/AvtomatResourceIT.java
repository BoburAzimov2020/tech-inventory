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
import uz.dynamic.techinventory.domain.Avtomat;
import uz.dynamic.techinventory.repository.AvtomatRepository;
import uz.dynamic.techinventory.service.dto.AvtomatDTO;
import uz.dynamic.techinventory.service.mapper.AvtomatMapper;

/**
 * Integration tests for the {@link AvtomatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvtomatResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/avtomats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvtomatRepository avtomatRepository;

    @Autowired
    private AvtomatMapper avtomatMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvtomatMockMvc;

    private Avtomat avtomat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avtomat createEntity(EntityManager em) {
        Avtomat avtomat = new Avtomat().name(DEFAULT_NAME).model(DEFAULT_MODEL).group(DEFAULT_GROUP).info(DEFAULT_INFO);
        return avtomat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avtomat createUpdatedEntity(EntityManager em) {
        Avtomat avtomat = new Avtomat().name(UPDATED_NAME).model(UPDATED_MODEL).group(UPDATED_GROUP).info(UPDATED_INFO);
        return avtomat;
    }

    @BeforeEach
    public void initTest() {
        avtomat = createEntity(em);
    }

    @Test
    @Transactional
    void createAvtomat() throws Exception {
        int databaseSizeBeforeCreate = avtomatRepository.findAll().size();
        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);
        restAvtomatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avtomatDTO)))
            .andExpect(status().isCreated());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeCreate + 1);
        Avtomat testAvtomat = avtomatList.get(avtomatList.size() - 1);
        assertThat(testAvtomat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAvtomat.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testAvtomat.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testAvtomat.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createAvtomatWithExistingId() throws Exception {
        // Create the Avtomat with an existing ID
        avtomat.setId(1L);
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        int databaseSizeBeforeCreate = avtomatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvtomatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avtomatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvtomats() throws Exception {
        // Initialize the database
        avtomatRepository.saveAndFlush(avtomat);

        // Get all the avtomatList
        restAvtomatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avtomat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getAvtomat() throws Exception {
        // Initialize the database
        avtomatRepository.saveAndFlush(avtomat);

        // Get the avtomat
        restAvtomatMockMvc
            .perform(get(ENTITY_API_URL_ID, avtomat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avtomat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingAvtomat() throws Exception {
        // Get the avtomat
        restAvtomatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvtomat() throws Exception {
        // Initialize the database
        avtomatRepository.saveAndFlush(avtomat);

        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();

        // Update the avtomat
        Avtomat updatedAvtomat = avtomatRepository.findById(avtomat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAvtomat are not directly saved in db
        em.detach(updatedAvtomat);
        updatedAvtomat.name(UPDATED_NAME).model(UPDATED_MODEL).group(UPDATED_GROUP).info(UPDATED_INFO);
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(updatedAvtomat);

        restAvtomatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avtomatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avtomatDTO))
            )
            .andExpect(status().isOk());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
        Avtomat testAvtomat = avtomatList.get(avtomatList.size() - 1);
        assertThat(testAvtomat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAvtomat.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAvtomat.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testAvtomat.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingAvtomat() throws Exception {
        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();
        avtomat.setId(longCount.incrementAndGet());

        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvtomatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avtomatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avtomatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvtomat() throws Exception {
        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();
        avtomat.setId(longCount.incrementAndGet());

        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvtomatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avtomatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvtomat() throws Exception {
        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();
        avtomat.setId(longCount.incrementAndGet());

        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvtomatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avtomatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvtomatWithPatch() throws Exception {
        // Initialize the database
        avtomatRepository.saveAndFlush(avtomat);

        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();

        // Update the avtomat using partial update
        Avtomat partialUpdatedAvtomat = new Avtomat();
        partialUpdatedAvtomat.setId(avtomat.getId());

        partialUpdatedAvtomat.name(UPDATED_NAME).model(UPDATED_MODEL).group(UPDATED_GROUP);

        restAvtomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvtomat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvtomat))
            )
            .andExpect(status().isOk());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
        Avtomat testAvtomat = avtomatList.get(avtomatList.size() - 1);
        assertThat(testAvtomat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAvtomat.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAvtomat.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testAvtomat.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateAvtomatWithPatch() throws Exception {
        // Initialize the database
        avtomatRepository.saveAndFlush(avtomat);

        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();

        // Update the avtomat using partial update
        Avtomat partialUpdatedAvtomat = new Avtomat();
        partialUpdatedAvtomat.setId(avtomat.getId());

        partialUpdatedAvtomat.name(UPDATED_NAME).model(UPDATED_MODEL).group(UPDATED_GROUP).info(UPDATED_INFO);

        restAvtomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvtomat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvtomat))
            )
            .andExpect(status().isOk());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
        Avtomat testAvtomat = avtomatList.get(avtomatList.size() - 1);
        assertThat(testAvtomat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAvtomat.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAvtomat.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testAvtomat.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingAvtomat() throws Exception {
        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();
        avtomat.setId(longCount.incrementAndGet());

        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvtomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avtomatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avtomatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvtomat() throws Exception {
        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();
        avtomat.setId(longCount.incrementAndGet());

        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvtomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avtomatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvtomat() throws Exception {
        int databaseSizeBeforeUpdate = avtomatRepository.findAll().size();
        avtomat.setId(longCount.incrementAndGet());

        // Create the Avtomat
        AvtomatDTO avtomatDTO = avtomatMapper.toDto(avtomat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvtomatMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avtomatDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avtomat in the database
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvtomat() throws Exception {
        // Initialize the database
        avtomatRepository.saveAndFlush(avtomat);

        int databaseSizeBeforeDelete = avtomatRepository.findAll().size();

        // Delete the avtomat
        restAvtomatMockMvc
            .perform(delete(ENTITY_API_URL_ID, avtomat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avtomat> avtomatList = avtomatRepository.findAll();
        assertThat(avtomatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
