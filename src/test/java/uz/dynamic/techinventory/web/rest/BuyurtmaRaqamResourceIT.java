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
import uz.dynamic.techinventory.domain.BuyurtmaRaqam;
import uz.dynamic.techinventory.repository.BuyurtmaRaqamRepository;
import uz.dynamic.techinventory.service.dto.BuyurtmaRaqamDTO;
import uz.dynamic.techinventory.service.mapper.BuyurtmaRaqamMapper;

/**
 * Integration tests for the {@link BuyurtmaRaqamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyurtmaRaqamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_OF_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_OF_ORDER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buyurtma-raqams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuyurtmaRaqamRepository buyurtmaRaqamRepository;

    @Autowired
    private BuyurtmaRaqamMapper buyurtmaRaqamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyurtmaRaqamMockMvc;

    private BuyurtmaRaqam buyurtmaRaqam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyurtmaRaqam createEntity(EntityManager em) {
        BuyurtmaRaqam buyurtmaRaqam = new BuyurtmaRaqam().name(DEFAULT_NAME).numberOfOrder(DEFAULT_NUMBER_OF_ORDER);
        return buyurtmaRaqam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyurtmaRaqam createUpdatedEntity(EntityManager em) {
        BuyurtmaRaqam buyurtmaRaqam = new BuyurtmaRaqam().name(UPDATED_NAME).numberOfOrder(UPDATED_NUMBER_OF_ORDER);
        return buyurtmaRaqam;
    }

    @BeforeEach
    public void initTest() {
        buyurtmaRaqam = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeCreate = buyurtmaRaqamRepository.findAll().size();
        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);
        restBuyurtmaRaqamMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeCreate + 1);
        BuyurtmaRaqam testBuyurtmaRaqam = buyurtmaRaqamList.get(buyurtmaRaqamList.size() - 1);
        assertThat(testBuyurtmaRaqam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBuyurtmaRaqam.getNumberOfOrder()).isEqualTo(DEFAULT_NUMBER_OF_ORDER);
    }

    @Test
    @Transactional
    void createBuyurtmaRaqamWithExistingId() throws Exception {
        // Create the BuyurtmaRaqam with an existing ID
        buyurtmaRaqam.setId(1L);
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        int databaseSizeBeforeCreate = buyurtmaRaqamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyurtmaRaqamMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuyurtmaRaqams() throws Exception {
        // Initialize the database
        buyurtmaRaqamRepository.saveAndFlush(buyurtmaRaqam);

        // Get all the buyurtmaRaqamList
        restBuyurtmaRaqamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyurtmaRaqam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numberOfOrder").value(hasItem(DEFAULT_NUMBER_OF_ORDER)));
    }

    @Test
    @Transactional
    void getBuyurtmaRaqam() throws Exception {
        // Initialize the database
        buyurtmaRaqamRepository.saveAndFlush(buyurtmaRaqam);

        // Get the buyurtmaRaqam
        restBuyurtmaRaqamMockMvc
            .perform(get(ENTITY_API_URL_ID, buyurtmaRaqam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyurtmaRaqam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numberOfOrder").value(DEFAULT_NUMBER_OF_ORDER));
    }

    @Test
    @Transactional
    void getNonExistingBuyurtmaRaqam() throws Exception {
        // Get the buyurtmaRaqam
        restBuyurtmaRaqamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyurtmaRaqam() throws Exception {
        // Initialize the database
        buyurtmaRaqamRepository.saveAndFlush(buyurtmaRaqam);

        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();

        // Update the buyurtmaRaqam
        BuyurtmaRaqam updatedBuyurtmaRaqam = buyurtmaRaqamRepository.findById(buyurtmaRaqam.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBuyurtmaRaqam are not directly saved in db
        em.detach(updatedBuyurtmaRaqam);
        updatedBuyurtmaRaqam.name(UPDATED_NAME).numberOfOrder(UPDATED_NUMBER_OF_ORDER);
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(updatedBuyurtmaRaqam);

        restBuyurtmaRaqamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyurtmaRaqamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
        BuyurtmaRaqam testBuyurtmaRaqam = buyurtmaRaqamList.get(buyurtmaRaqamList.size() - 1);
        assertThat(testBuyurtmaRaqam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyurtmaRaqam.getNumberOfOrder()).isEqualTo(UPDATED_NUMBER_OF_ORDER);
    }

    @Test
    @Transactional
    void putNonExistingBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();
        buyurtmaRaqam.setId(longCount.incrementAndGet());

        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyurtmaRaqamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyurtmaRaqamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();
        buyurtmaRaqam.setId(longCount.incrementAndGet());

        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyurtmaRaqamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();
        buyurtmaRaqam.setId(longCount.incrementAndGet());

        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyurtmaRaqamMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyurtmaRaqamWithPatch() throws Exception {
        // Initialize the database
        buyurtmaRaqamRepository.saveAndFlush(buyurtmaRaqam);

        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();

        // Update the buyurtmaRaqam using partial update
        BuyurtmaRaqam partialUpdatedBuyurtmaRaqam = new BuyurtmaRaqam();
        partialUpdatedBuyurtmaRaqam.setId(buyurtmaRaqam.getId());

        partialUpdatedBuyurtmaRaqam.name(UPDATED_NAME).numberOfOrder(UPDATED_NUMBER_OF_ORDER);

        restBuyurtmaRaqamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyurtmaRaqam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyurtmaRaqam))
            )
            .andExpect(status().isOk());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
        BuyurtmaRaqam testBuyurtmaRaqam = buyurtmaRaqamList.get(buyurtmaRaqamList.size() - 1);
        assertThat(testBuyurtmaRaqam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyurtmaRaqam.getNumberOfOrder()).isEqualTo(UPDATED_NUMBER_OF_ORDER);
    }

    @Test
    @Transactional
    void fullUpdateBuyurtmaRaqamWithPatch() throws Exception {
        // Initialize the database
        buyurtmaRaqamRepository.saveAndFlush(buyurtmaRaqam);

        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();

        // Update the buyurtmaRaqam using partial update
        BuyurtmaRaqam partialUpdatedBuyurtmaRaqam = new BuyurtmaRaqam();
        partialUpdatedBuyurtmaRaqam.setId(buyurtmaRaqam.getId());

        partialUpdatedBuyurtmaRaqam.name(UPDATED_NAME).numberOfOrder(UPDATED_NUMBER_OF_ORDER);

        restBuyurtmaRaqamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyurtmaRaqam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyurtmaRaqam))
            )
            .andExpect(status().isOk());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
        BuyurtmaRaqam testBuyurtmaRaqam = buyurtmaRaqamList.get(buyurtmaRaqamList.size() - 1);
        assertThat(testBuyurtmaRaqam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyurtmaRaqam.getNumberOfOrder()).isEqualTo(UPDATED_NUMBER_OF_ORDER);
    }

    @Test
    @Transactional
    void patchNonExistingBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();
        buyurtmaRaqam.setId(longCount.incrementAndGet());

        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyurtmaRaqamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyurtmaRaqamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();
        buyurtmaRaqam.setId(longCount.incrementAndGet());

        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyurtmaRaqamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyurtmaRaqam() throws Exception {
        int databaseSizeBeforeUpdate = buyurtmaRaqamRepository.findAll().size();
        buyurtmaRaqam.setId(longCount.incrementAndGet());

        // Create the BuyurtmaRaqam
        BuyurtmaRaqamDTO buyurtmaRaqamDTO = buyurtmaRaqamMapper.toDto(buyurtmaRaqam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyurtmaRaqamMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyurtmaRaqamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyurtmaRaqam in the database
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyurtmaRaqam() throws Exception {
        // Initialize the database
        buyurtmaRaqamRepository.saveAndFlush(buyurtmaRaqam);

        int databaseSizeBeforeDelete = buyurtmaRaqamRepository.findAll().size();

        // Delete the buyurtmaRaqam
        restBuyurtmaRaqamMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyurtmaRaqam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuyurtmaRaqam> buyurtmaRaqamList = buyurtmaRaqamRepository.findAll();
        assertThat(buyurtmaRaqamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
