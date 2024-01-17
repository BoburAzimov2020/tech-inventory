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
import uz.dynamic.techinventory.domain.Rozetka;
import uz.dynamic.techinventory.repository.RozetkaRepository;
import uz.dynamic.techinventory.service.dto.RozetkaDTO;
import uz.dynamic.techinventory.service.mapper.RozetkaMapper;

/**
 * Integration tests for the {@link RozetkaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RozetkaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rozetkas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RozetkaRepository rozetkaRepository;

    @Autowired
    private RozetkaMapper rozetkaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRozetkaMockMvc;

    private Rozetka rozetka;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rozetka createEntity(EntityManager em) {
        Rozetka rozetka = new Rozetka().name(DEFAULT_NAME).model(DEFAULT_MODEL).info(DEFAULT_INFO);
        return rozetka;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rozetka createUpdatedEntity(EntityManager em) {
        Rozetka rozetka = new Rozetka().name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);
        return rozetka;
    }

    @BeforeEach
    public void initTest() {
        rozetka = createEntity(em);
    }

    @Test
    @Transactional
    void createRozetka() throws Exception {
        int databaseSizeBeforeCreate = rozetkaRepository.findAll().size();
        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);
        restRozetkaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rozetkaDTO)))
            .andExpect(status().isCreated());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeCreate + 1);
        Rozetka testRozetka = rozetkaList.get(rozetkaList.size() - 1);
        assertThat(testRozetka.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRozetka.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testRozetka.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createRozetkaWithExistingId() throws Exception {
        // Create the Rozetka with an existing ID
        rozetka.setId(1L);
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        int databaseSizeBeforeCreate = rozetkaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRozetkaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rozetkaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRozetkas() throws Exception {
        // Initialize the database
        rozetkaRepository.saveAndFlush(rozetka);

        // Get all the rozetkaList
        restRozetkaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rozetka.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getRozetka() throws Exception {
        // Initialize the database
        rozetkaRepository.saveAndFlush(rozetka);

        // Get the rozetka
        restRozetkaMockMvc
            .perform(get(ENTITY_API_URL_ID, rozetka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rozetka.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingRozetka() throws Exception {
        // Get the rozetka
        restRozetkaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRozetka() throws Exception {
        // Initialize the database
        rozetkaRepository.saveAndFlush(rozetka);

        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();

        // Update the rozetka
        Rozetka updatedRozetka = rozetkaRepository.findById(rozetka.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRozetka are not directly saved in db
        em.detach(updatedRozetka);
        updatedRozetka.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(updatedRozetka);

        restRozetkaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rozetkaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rozetkaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
        Rozetka testRozetka = rozetkaList.get(rozetkaList.size() - 1);
        assertThat(testRozetka.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRozetka.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testRozetka.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingRozetka() throws Exception {
        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();
        rozetka.setId(longCount.incrementAndGet());

        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRozetkaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rozetkaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rozetkaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRozetka() throws Exception {
        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();
        rozetka.setId(longCount.incrementAndGet());

        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRozetkaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rozetkaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRozetka() throws Exception {
        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();
        rozetka.setId(longCount.incrementAndGet());

        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRozetkaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rozetkaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRozetkaWithPatch() throws Exception {
        // Initialize the database
        rozetkaRepository.saveAndFlush(rozetka);

        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();

        // Update the rozetka using partial update
        Rozetka partialUpdatedRozetka = new Rozetka();
        partialUpdatedRozetka.setId(rozetka.getId());

        partialUpdatedRozetka.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);

        restRozetkaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRozetka.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRozetka))
            )
            .andExpect(status().isOk());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
        Rozetka testRozetka = rozetkaList.get(rozetkaList.size() - 1);
        assertThat(testRozetka.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRozetka.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testRozetka.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateRozetkaWithPatch() throws Exception {
        // Initialize the database
        rozetkaRepository.saveAndFlush(rozetka);

        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();

        // Update the rozetka using partial update
        Rozetka partialUpdatedRozetka = new Rozetka();
        partialUpdatedRozetka.setId(rozetka.getId());

        partialUpdatedRozetka.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);

        restRozetkaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRozetka.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRozetka))
            )
            .andExpect(status().isOk());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
        Rozetka testRozetka = rozetkaList.get(rozetkaList.size() - 1);
        assertThat(testRozetka.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRozetka.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testRozetka.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingRozetka() throws Exception {
        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();
        rozetka.setId(longCount.incrementAndGet());

        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRozetkaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rozetkaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rozetkaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRozetka() throws Exception {
        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();
        rozetka.setId(longCount.incrementAndGet());

        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRozetkaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rozetkaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRozetka() throws Exception {
        int databaseSizeBeforeUpdate = rozetkaRepository.findAll().size();
        rozetka.setId(longCount.incrementAndGet());

        // Create the Rozetka
        RozetkaDTO rozetkaDTO = rozetkaMapper.toDto(rozetka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRozetkaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rozetkaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rozetka in the database
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRozetka() throws Exception {
        // Initialize the database
        rozetkaRepository.saveAndFlush(rozetka);

        int databaseSizeBeforeDelete = rozetkaRepository.findAll().size();

        // Delete the rozetka
        restRozetkaMockMvc
            .perform(delete(ENTITY_API_URL_ID, rozetka.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rozetka> rozetkaList = rozetkaRepository.findAll();
        assertThat(rozetkaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
