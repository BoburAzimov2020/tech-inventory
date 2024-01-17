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
import uz.dynamic.techinventory.domain.Shelf;
import uz.dynamic.techinventory.repository.ShelfRepository;
import uz.dynamic.techinventory.service.dto.ShelfDTO;
import uz.dynamic.techinventory.service.mapper.ShelfMapper;

/**
 * Integration tests for the {@link ShelfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShelfResourceIT {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DIGIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DIGIT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shelves";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private ShelfMapper shelfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShelfMockMvc;

    private Shelf shelf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelf createEntity(EntityManager em) {
        Shelf shelf = new Shelf().serialNumber(DEFAULT_SERIAL_NUMBER).digitNumber(DEFAULT_DIGIT_NUMBER).info(DEFAULT_INFO);
        return shelf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelf createUpdatedEntity(EntityManager em) {
        Shelf shelf = new Shelf().serialNumber(UPDATED_SERIAL_NUMBER).digitNumber(UPDATED_DIGIT_NUMBER).info(UPDATED_INFO);
        return shelf;
    }

    @BeforeEach
    public void initTest() {
        shelf = createEntity(em);
    }

    @Test
    @Transactional
    void createShelf() throws Exception {
        int databaseSizeBeforeCreate = shelfRepository.findAll().size();
        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);
        restShelfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isCreated());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeCreate + 1);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testShelf.getDigitNumber()).isEqualTo(DEFAULT_DIGIT_NUMBER);
        assertThat(testShelf.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createShelfWithExistingId() throws Exception {
        // Create the Shelf with an existing ID
        shelf.setId(1L);
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        int databaseSizeBeforeCreate = shelfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllShelves() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        // Get all the shelfList
        restShelfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelf.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].digitNumber").value(hasItem(DEFAULT_DIGIT_NUMBER)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        // Get the shelf
        restShelfMockMvc
            .perform(get(ENTITY_API_URL_ID, shelf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shelf.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.digitNumber").value(DEFAULT_DIGIT_NUMBER))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingShelf() throws Exception {
        // Get the shelf
        restShelfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // Update the shelf
        Shelf updatedShelf = shelfRepository.findById(shelf.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedShelf are not directly saved in db
        em.detach(updatedShelf);
        updatedShelf.serialNumber(UPDATED_SERIAL_NUMBER).digitNumber(UPDATED_DIGIT_NUMBER).info(UPDATED_INFO);
        ShelfDTO shelfDTO = shelfMapper.toDto(updatedShelf);

        restShelfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shelfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shelfDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testShelf.getDigitNumber()).isEqualTo(UPDATED_DIGIT_NUMBER);
        assertThat(testShelf.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();
        shelf.setId(longCount.incrementAndGet());

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shelfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shelfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();
        shelf.setId(longCount.incrementAndGet());

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shelfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();
        shelf.setId(longCount.incrementAndGet());

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShelfWithPatch() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // Update the shelf using partial update
        Shelf partialUpdatedShelf = new Shelf();
        partialUpdatedShelf.setId(shelf.getId());

        partialUpdatedShelf.serialNumber(UPDATED_SERIAL_NUMBER);

        restShelfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShelf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShelf))
            )
            .andExpect(status().isOk());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testShelf.getDigitNumber()).isEqualTo(DEFAULT_DIGIT_NUMBER);
        assertThat(testShelf.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateShelfWithPatch() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // Update the shelf using partial update
        Shelf partialUpdatedShelf = new Shelf();
        partialUpdatedShelf.setId(shelf.getId());

        partialUpdatedShelf.serialNumber(UPDATED_SERIAL_NUMBER).digitNumber(UPDATED_DIGIT_NUMBER).info(UPDATED_INFO);

        restShelfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShelf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShelf))
            )
            .andExpect(status().isOk());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testShelf.getDigitNumber()).isEqualTo(UPDATED_DIGIT_NUMBER);
        assertThat(testShelf.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();
        shelf.setId(longCount.incrementAndGet());

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shelfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shelfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();
        shelf.setId(longCount.incrementAndGet());

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shelfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();
        shelf.setId(longCount.incrementAndGet());

        // Create the Shelf
        ShelfDTO shelfDTO = shelfMapper.toDto(shelf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShelfMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shelfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeDelete = shelfRepository.findAll().size();

        // Delete the shelf
        restShelfMockMvc
            .perform(delete(ENTITY_API_URL_ID, shelf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
