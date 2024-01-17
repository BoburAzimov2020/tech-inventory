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
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.repository.ObyektRepository;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.mapper.ObyektMapper;

/**
 * Integration tests for the {@link ObyektResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObyektResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOME = "AAAAAAAAAA";
    private static final String UPDATED_HOME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/obyekts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObyektRepository obyektRepository;

    @Autowired
    private ObyektMapper obyektMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObyektMockMvc;

    private Obyekt obyekt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obyekt createEntity(EntityManager em) {
        Obyekt obyekt = new Obyekt()
            .name(DEFAULT_NAME)
            .home(DEFAULT_HOME)
            .street(DEFAULT_STREET)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .info(DEFAULT_INFO);
        return obyekt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obyekt createUpdatedEntity(EntityManager em) {
        Obyekt obyekt = new Obyekt()
            .name(UPDATED_NAME)
            .home(UPDATED_HOME)
            .street(UPDATED_STREET)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .info(UPDATED_INFO);
        return obyekt;
    }

    @BeforeEach
    public void initTest() {
        obyekt = createEntity(em);
    }

    @Test
    @Transactional
    void createObyekt() throws Exception {
        int databaseSizeBeforeCreate = obyektRepository.findAll().size();
        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);
        restObyektMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obyektDTO)))
            .andExpect(status().isCreated());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeCreate + 1);
        Obyekt testObyekt = obyektList.get(obyektList.size() - 1);
        assertThat(testObyekt.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObyekt.getHome()).isEqualTo(DEFAULT_HOME);
        assertThat(testObyekt.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testObyekt.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testObyekt.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testObyekt.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createObyektWithExistingId() throws Exception {
        // Create the Obyekt with an existing ID
        obyekt.setId(1L);
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        int databaseSizeBeforeCreate = obyektRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObyektMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obyektDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = obyektRepository.findAll().size();
        // set the field null
        obyekt.setName(null);

        // Create the Obyekt, which fails.
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        restObyektMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obyektDTO)))
            .andExpect(status().isBadRequest());

        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObyekts() throws Exception {
        // Initialize the database
        obyektRepository.saveAndFlush(obyekt);

        // Get all the obyektList
        restObyektMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obyekt.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].home").value(hasItem(DEFAULT_HOME)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getObyekt() throws Exception {
        // Initialize the database
        obyektRepository.saveAndFlush(obyekt);

        // Get the obyekt
        restObyektMockMvc
            .perform(get(ENTITY_API_URL_ID, obyekt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obyekt.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.home").value(DEFAULT_HOME))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingObyekt() throws Exception {
        // Get the obyekt
        restObyektMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObyekt() throws Exception {
        // Initialize the database
        obyektRepository.saveAndFlush(obyekt);

        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();

        // Update the obyekt
        Obyekt updatedObyekt = obyektRepository.findById(obyekt.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedObyekt are not directly saved in db
        em.detach(updatedObyekt);
        updatedObyekt
            .name(UPDATED_NAME)
            .home(UPDATED_HOME)
            .street(UPDATED_STREET)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .info(UPDATED_INFO);
        ObyektDTO obyektDTO = obyektMapper.toDto(updatedObyekt);

        restObyektMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obyektDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obyektDTO))
            )
            .andExpect(status().isOk());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
        Obyekt testObyekt = obyektList.get(obyektList.size() - 1);
        assertThat(testObyekt.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObyekt.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testObyekt.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testObyekt.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testObyekt.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testObyekt.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingObyekt() throws Exception {
        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();
        obyekt.setId(longCount.incrementAndGet());

        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObyektMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obyektDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obyektDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObyekt() throws Exception {
        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();
        obyekt.setId(longCount.incrementAndGet());

        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObyektMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obyektDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObyekt() throws Exception {
        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();
        obyekt.setId(longCount.incrementAndGet());

        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObyektMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obyektDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObyektWithPatch() throws Exception {
        // Initialize the database
        obyektRepository.saveAndFlush(obyekt);

        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();

        // Update the obyekt using partial update
        Obyekt partialUpdatedObyekt = new Obyekt();
        partialUpdatedObyekt.setId(obyekt.getId());

        partialUpdatedObyekt.longitude(UPDATED_LONGITUDE).info(UPDATED_INFO);

        restObyektMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObyekt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObyekt))
            )
            .andExpect(status().isOk());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
        Obyekt testObyekt = obyektList.get(obyektList.size() - 1);
        assertThat(testObyekt.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObyekt.getHome()).isEqualTo(DEFAULT_HOME);
        assertThat(testObyekt.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testObyekt.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testObyekt.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testObyekt.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void fullUpdateObyektWithPatch() throws Exception {
        // Initialize the database
        obyektRepository.saveAndFlush(obyekt);

        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();

        // Update the obyekt using partial update
        Obyekt partialUpdatedObyekt = new Obyekt();
        partialUpdatedObyekt.setId(obyekt.getId());

        partialUpdatedObyekt
            .name(UPDATED_NAME)
            .home(UPDATED_HOME)
            .street(UPDATED_STREET)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .info(UPDATED_INFO);

        restObyektMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObyekt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObyekt))
            )
            .andExpect(status().isOk());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
        Obyekt testObyekt = obyektList.get(obyektList.size() - 1);
        assertThat(testObyekt.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObyekt.getHome()).isEqualTo(UPDATED_HOME);
        assertThat(testObyekt.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testObyekt.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testObyekt.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testObyekt.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingObyekt() throws Exception {
        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();
        obyekt.setId(longCount.incrementAndGet());

        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObyektMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, obyektDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obyektDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObyekt() throws Exception {
        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();
        obyekt.setId(longCount.incrementAndGet());

        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObyektMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obyektDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObyekt() throws Exception {
        int databaseSizeBeforeUpdate = obyektRepository.findAll().size();
        obyekt.setId(longCount.incrementAndGet());

        // Create the Obyekt
        ObyektDTO obyektDTO = obyektMapper.toDto(obyekt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObyektMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(obyektDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obyekt in the database
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObyekt() throws Exception {
        // Initialize the database
        obyektRepository.saveAndFlush(obyekt);

        int databaseSizeBeforeDelete = obyektRepository.findAll().size();

        // Delete the obyekt
        restObyektMockMvc
            .perform(delete(ENTITY_API_URL_ID, obyekt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Obyekt> obyektList = obyektRepository.findAll();
        assertThat(obyektList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
