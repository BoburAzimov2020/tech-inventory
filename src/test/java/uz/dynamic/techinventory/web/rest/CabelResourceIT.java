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
import uz.dynamic.techinventory.domain.Cabel;
import uz.dynamic.techinventory.repository.CabelRepository;
import uz.dynamic.techinventory.service.dto.CabelDTO;
import uz.dynamic.techinventory.service.mapper.CabelMapper;

/**
 * Integration tests for the {@link CabelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CabelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cabels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CabelRepository cabelRepository;

    @Autowired
    private CabelMapper cabelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCabelMockMvc;

    private Cabel cabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cabel createEntity(EntityManager em) {
        Cabel cabel = new Cabel().name(DEFAULT_NAME).model(DEFAULT_MODEL).info(DEFAULT_INFO);
        return cabel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cabel createUpdatedEntity(EntityManager em) {
        Cabel cabel = new Cabel().name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);
        return cabel;
    }

    @BeforeEach
    public void initTest() {
        cabel = createEntity(em);
    }

    @Test
    @Transactional
    void createCabel() throws Exception {
        int databaseSizeBeforeCreate = cabelRepository.findAll().size();
        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);
        restCabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cabelDTO)))
            .andExpect(status().isCreated());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeCreate + 1);
        Cabel testCabel = cabelList.get(cabelList.size() - 1);
        assertThat(testCabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCabel.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCabel.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createCabelWithExistingId() throws Exception {
        // Create the Cabel with an existing ID
        cabel.setId(1L);
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        int databaseSizeBeforeCreate = cabelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCabelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCabels() throws Exception {
        // Initialize the database
        cabelRepository.saveAndFlush(cabel);

        // Get all the cabelList
        restCabelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getCabel() throws Exception {
        // Initialize the database
        cabelRepository.saveAndFlush(cabel);

        // Get the cabel
        restCabelMockMvc
            .perform(get(ENTITY_API_URL_ID, cabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cabel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingCabel() throws Exception {
        // Get the cabel
        restCabelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCabel() throws Exception {
        // Initialize the database
        cabelRepository.saveAndFlush(cabel);

        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();

        // Update the cabel
        Cabel updatedCabel = cabelRepository.findById(cabel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCabel are not directly saved in db
        em.detach(updatedCabel);
        updatedCabel.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);
        CabelDTO cabelDTO = cabelMapper.toDto(updatedCabel);

        restCabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cabelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cabelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
        Cabel testCabel = cabelList.get(cabelList.size() - 1);
        assertThat(testCabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCabel.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCabel.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingCabel() throws Exception {
        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();
        cabel.setId(longCount.incrementAndGet());

        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cabelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCabel() throws Exception {
        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();
        cabel.setId(longCount.incrementAndGet());

        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCabel() throws Exception {
        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();
        cabel.setId(longCount.incrementAndGet());

        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cabelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCabelWithPatch() throws Exception {
        // Initialize the database
        cabelRepository.saveAndFlush(cabel);

        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();

        // Update the cabel using partial update
        Cabel partialUpdatedCabel = new Cabel();
        partialUpdatedCabel.setId(cabel.getId());

        restCabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCabel))
            )
            .andExpect(status().isOk());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
        Cabel testCabel = cabelList.get(cabelList.size() - 1);
        assertThat(testCabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCabel.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCabel.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCabelWithPatch() throws Exception {
        // Initialize the database
        cabelRepository.saveAndFlush(cabel);

        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();

        // Update the cabel using partial update
        Cabel partialUpdatedCabel = new Cabel();
        partialUpdatedCabel.setId(cabel.getId());

        partialUpdatedCabel.name(UPDATED_NAME).model(UPDATED_MODEL).info(UPDATED_INFO);

        restCabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCabel))
            )
            .andExpect(status().isOk());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
        Cabel testCabel = cabelList.get(cabelList.size() - 1);
        assertThat(testCabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCabel.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCabel.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingCabel() throws Exception {
        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();
        cabel.setId(longCount.incrementAndGet());

        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cabelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCabel() throws Exception {
        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();
        cabel.setId(longCount.incrementAndGet());

        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCabel() throws Exception {
        int databaseSizeBeforeUpdate = cabelRepository.findAll().size();
        cabel.setId(longCount.incrementAndGet());

        // Create the Cabel
        CabelDTO cabelDTO = cabelMapper.toDto(cabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCabelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cabelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cabel in the database
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCabel() throws Exception {
        // Initialize the database
        cabelRepository.saveAndFlush(cabel);

        int databaseSizeBeforeDelete = cabelRepository.findAll().size();

        // Delete the cabel
        restCabelMockMvc
            .perform(delete(ENTITY_API_URL_ID, cabel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cabel> cabelList = cabelRepository.findAll();
        assertThat(cabelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
