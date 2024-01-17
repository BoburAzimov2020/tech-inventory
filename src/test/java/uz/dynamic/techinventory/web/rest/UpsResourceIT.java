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
import uz.dynamic.techinventory.domain.Ups;
import uz.dynamic.techinventory.repository.UpsRepository;
import uz.dynamic.techinventory.service.dto.UpsDTO;
import uz.dynamic.techinventory.service.mapper.UpsMapper;

/**
 * Integration tests for the {@link UpsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UpsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_POWER = "AAAAAAAAAA";
    private static final String UPDATED_POWER = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UpsRepository upsRepository;

    @Autowired
    private UpsMapper upsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUpsMockMvc;

    private Ups ups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ups createEntity(EntityManager em) {
        Ups ups = new Ups().name(DEFAULT_NAME).model(DEFAULT_MODEL).power(DEFAULT_POWER).info(DEFAULT_INFO);
        return ups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ups createUpdatedEntity(EntityManager em) {
        Ups ups = new Ups().name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);
        return ups;
    }

    @BeforeEach
    public void initTest() {
        ups = createEntity(em);
    }

    @Test
    @Transactional
    void createUps() throws Exception {
        int databaseSizeBeforeCreate = upsRepository.findAll().size();
        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);
        restUpsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(upsDTO)))
            .andExpect(status().isCreated());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeCreate + 1);
        Ups testUps = upsList.get(upsList.size() - 1);
        assertThat(testUps.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUps.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testUps.getPower()).isEqualTo(DEFAULT_POWER);
        assertThat(testUps.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createUpsWithExistingId() throws Exception {
        // Create the Ups with an existing ID
        ups.setId(1L);
        UpsDTO upsDTO = upsMapper.toDto(ups);

        int databaseSizeBeforeCreate = upsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(upsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUps() throws Exception {
        // Initialize the database
        upsRepository.saveAndFlush(ups);

        // Get all the upsList
        restUpsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].power").value(hasItem(DEFAULT_POWER)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getUps() throws Exception {
        // Initialize the database
        upsRepository.saveAndFlush(ups);

        // Get the ups
        restUpsMockMvc
            .perform(get(ENTITY_API_URL_ID, ups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingUps() throws Exception {
        // Get the ups
        restUpsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUps() throws Exception {
        // Initialize the database
        upsRepository.saveAndFlush(ups);

        int databaseSizeBeforeUpdate = upsRepository.findAll().size();

        // Update the ups
        Ups updatedUps = upsRepository.findById(ups.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUps are not directly saved in db
        em.detach(updatedUps);
        updatedUps.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);
        UpsDTO upsDTO = upsMapper.toDto(updatedUps);

        restUpsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, upsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(upsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
        Ups testUps = upsList.get(upsList.size() - 1);
        assertThat(testUps.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUps.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testUps.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testUps.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingUps() throws Exception {
        int databaseSizeBeforeUpdate = upsRepository.findAll().size();
        ups.setId(longCount.incrementAndGet());

        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, upsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(upsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUps() throws Exception {
        int databaseSizeBeforeUpdate = upsRepository.findAll().size();
        ups.setId(longCount.incrementAndGet());

        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(upsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUps() throws Exception {
        int databaseSizeBeforeUpdate = upsRepository.findAll().size();
        ups.setId(longCount.incrementAndGet());

        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(upsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUpsWithPatch() throws Exception {
        // Initialize the database
        upsRepository.saveAndFlush(ups);

        int databaseSizeBeforeUpdate = upsRepository.findAll().size();

        // Update the ups using partial update
        Ups partialUpdatedUps = new Ups();
        partialUpdatedUps.setId(ups.getId());

        partialUpdatedUps.name(UPDATED_NAME).model(UPDATED_MODEL);

        restUpsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUps.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUps))
            )
            .andExpect(status().isOk());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
        Ups testUps = upsList.get(upsList.size() - 1);
        assertThat(testUps.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUps.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testUps.getPower()).isEqualTo(DEFAULT_POWER);
        assertThat(testUps.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateUpsWithPatch() throws Exception {
        // Initialize the database
        upsRepository.saveAndFlush(ups);

        int databaseSizeBeforeUpdate = upsRepository.findAll().size();

        // Update the ups using partial update
        Ups partialUpdatedUps = new Ups();
        partialUpdatedUps.setId(ups.getId());

        partialUpdatedUps.name(UPDATED_NAME).model(UPDATED_MODEL).power(UPDATED_POWER).info(UPDATED_INFO);

        restUpsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUps.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUps))
            )
            .andExpect(status().isOk());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
        Ups testUps = upsList.get(upsList.size() - 1);
        assertThat(testUps.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUps.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testUps.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testUps.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingUps() throws Exception {
        int databaseSizeBeforeUpdate = upsRepository.findAll().size();
        ups.setId(longCount.incrementAndGet());

        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, upsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(upsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUps() throws Exception {
        int databaseSizeBeforeUpdate = upsRepository.findAll().size();
        ups.setId(longCount.incrementAndGet());

        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(upsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUps() throws Exception {
        int databaseSizeBeforeUpdate = upsRepository.findAll().size();
        ups.setId(longCount.incrementAndGet());

        // Create the Ups
        UpsDTO upsDTO = upsMapper.toDto(ups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(upsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ups in the database
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUps() throws Exception {
        // Initialize the database
        upsRepository.saveAndFlush(ups);

        int databaseSizeBeforeDelete = upsRepository.findAll().size();

        // Delete the ups
        restUpsMockMvc.perform(delete(ENTITY_API_URL_ID, ups.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ups> upsList = upsRepository.findAll();
        assertThat(upsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
