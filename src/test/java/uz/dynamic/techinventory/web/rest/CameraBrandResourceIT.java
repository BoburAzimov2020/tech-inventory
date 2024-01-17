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
import uz.dynamic.techinventory.domain.CameraBrand;
import uz.dynamic.techinventory.repository.CameraBrandRepository;
import uz.dynamic.techinventory.service.dto.CameraBrandDTO;
import uz.dynamic.techinventory.service.mapper.CameraBrandMapper;

/**
 * Integration tests for the {@link CameraBrandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CameraBrandResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/camera-brands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CameraBrandRepository cameraBrandRepository;

    @Autowired
    private CameraBrandMapper cameraBrandMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCameraBrandMockMvc;

    private CameraBrand cameraBrand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CameraBrand createEntity(EntityManager em) {
        CameraBrand cameraBrand = new CameraBrand().name(DEFAULT_NAME).info(DEFAULT_INFO);
        return cameraBrand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CameraBrand createUpdatedEntity(EntityManager em) {
        CameraBrand cameraBrand = new CameraBrand().name(UPDATED_NAME).info(UPDATED_INFO);
        return cameraBrand;
    }

    @BeforeEach
    public void initTest() {
        cameraBrand = createEntity(em);
    }

    @Test
    @Transactional
    void createCameraBrand() throws Exception {
        int databaseSizeBeforeCreate = cameraBrandRepository.findAll().size();
        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);
        restCameraBrandMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeCreate + 1);
        CameraBrand testCameraBrand = cameraBrandList.get(cameraBrandList.size() - 1);
        assertThat(testCameraBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCameraBrand.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createCameraBrandWithExistingId() throws Exception {
        // Create the CameraBrand with an existing ID
        cameraBrand.setId(1L);
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        int databaseSizeBeforeCreate = cameraBrandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCameraBrandMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraBrandRepository.findAll().size();
        // set the field null
        cameraBrand.setName(null);

        // Create the CameraBrand, which fails.
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        restCameraBrandMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isBadRequest());

        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCameraBrands() throws Exception {
        // Initialize the database
        cameraBrandRepository.saveAndFlush(cameraBrand);

        // Get all the cameraBrandList
        restCameraBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cameraBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getCameraBrand() throws Exception {
        // Initialize the database
        cameraBrandRepository.saveAndFlush(cameraBrand);

        // Get the cameraBrand
        restCameraBrandMockMvc
            .perform(get(ENTITY_API_URL_ID, cameraBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cameraBrand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingCameraBrand() throws Exception {
        // Get the cameraBrand
        restCameraBrandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCameraBrand() throws Exception {
        // Initialize the database
        cameraBrandRepository.saveAndFlush(cameraBrand);

        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();

        // Update the cameraBrand
        CameraBrand updatedCameraBrand = cameraBrandRepository.findById(cameraBrand.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCameraBrand are not directly saved in db
        em.detach(updatedCameraBrand);
        updatedCameraBrand.name(UPDATED_NAME).info(UPDATED_INFO);
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(updatedCameraBrand);

        restCameraBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraBrandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isOk());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
        CameraBrand testCameraBrand = cameraBrandList.get(cameraBrandList.size() - 1);
        assertThat(testCameraBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCameraBrand.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingCameraBrand() throws Exception {
        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();
        cameraBrand.setId(longCount.incrementAndGet());

        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraBrandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCameraBrand() throws Exception {
        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();
        cameraBrand.setId(longCount.incrementAndGet());

        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCameraBrand() throws Exception {
        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();
        cameraBrand.setId(longCount.incrementAndGet());

        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraBrandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCameraBrandWithPatch() throws Exception {
        // Initialize the database
        cameraBrandRepository.saveAndFlush(cameraBrand);

        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();

        // Update the cameraBrand using partial update
        CameraBrand partialUpdatedCameraBrand = new CameraBrand();
        partialUpdatedCameraBrand.setId(cameraBrand.getId());

        partialUpdatedCameraBrand.name(UPDATED_NAME).info(UPDATED_INFO);

        restCameraBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCameraBrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCameraBrand))
            )
            .andExpect(status().isOk());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
        CameraBrand testCameraBrand = cameraBrandList.get(cameraBrandList.size() - 1);
        assertThat(testCameraBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCameraBrand.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCameraBrandWithPatch() throws Exception {
        // Initialize the database
        cameraBrandRepository.saveAndFlush(cameraBrand);

        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();

        // Update the cameraBrand using partial update
        CameraBrand partialUpdatedCameraBrand = new CameraBrand();
        partialUpdatedCameraBrand.setId(cameraBrand.getId());

        partialUpdatedCameraBrand.name(UPDATED_NAME).info(UPDATED_INFO);

        restCameraBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCameraBrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCameraBrand))
            )
            .andExpect(status().isOk());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
        CameraBrand testCameraBrand = cameraBrandList.get(cameraBrandList.size() - 1);
        assertThat(testCameraBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCameraBrand.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingCameraBrand() throws Exception {
        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();
        cameraBrand.setId(longCount.incrementAndGet());

        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cameraBrandDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCameraBrand() throws Exception {
        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();
        cameraBrand.setId(longCount.incrementAndGet());

        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCameraBrand() throws Exception {
        int databaseSizeBeforeUpdate = cameraBrandRepository.findAll().size();
        cameraBrand.setId(longCount.incrementAndGet());

        // Create the CameraBrand
        CameraBrandDTO cameraBrandDTO = cameraBrandMapper.toDto(cameraBrand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraBrandMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cameraBrandDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CameraBrand in the database
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCameraBrand() throws Exception {
        // Initialize the database
        cameraBrandRepository.saveAndFlush(cameraBrand);

        int databaseSizeBeforeDelete = cameraBrandRepository.findAll().size();

        // Delete the cameraBrand
        restCameraBrandMockMvc
            .perform(delete(ENTITY_API_URL_ID, cameraBrand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CameraBrand> cameraBrandList = cameraBrandRepository.findAll();
        assertThat(cameraBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
