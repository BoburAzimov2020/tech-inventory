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
import uz.dynamic.techinventory.domain.TerminalServer;
import uz.dynamic.techinventory.repository.TerminalServerRepository;
import uz.dynamic.techinventory.service.dto.TerminalServerDTO;
import uz.dynamic.techinventory.service.mapper.TerminalServerMapper;

/**
 * Integration tests for the {@link TerminalServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerminalServerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/terminal-servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerminalServerRepository terminalServerRepository;

    @Autowired
    private TerminalServerMapper terminalServerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerminalServerMockMvc;

    private TerminalServer terminalServer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerminalServer createEntity(EntityManager em) {
        TerminalServer terminalServer = new TerminalServer().name(DEFAULT_NAME).model(DEFAULT_MODEL).ip(DEFAULT_IP).info(DEFAULT_INFO);
        return terminalServer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerminalServer createUpdatedEntity(EntityManager em) {
        TerminalServer terminalServer = new TerminalServer().name(UPDATED_NAME).model(UPDATED_MODEL).ip(UPDATED_IP).info(UPDATED_INFO);
        return terminalServer;
    }

    @BeforeEach
    public void initTest() {
        terminalServer = createEntity(em);
    }

    @Test
    @Transactional
    void createTerminalServer() throws Exception {
        int databaseSizeBeforeCreate = terminalServerRepository.findAll().size();
        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);
        restTerminalServerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeCreate + 1);
        TerminalServer testTerminalServer = terminalServerList.get(terminalServerList.size() - 1);
        assertThat(testTerminalServer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTerminalServer.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testTerminalServer.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testTerminalServer.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createTerminalServerWithExistingId() throws Exception {
        // Create the TerminalServer with an existing ID
        terminalServer.setId(1L);
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        int databaseSizeBeforeCreate = terminalServerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerminalServerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTerminalServers() throws Exception {
        // Initialize the database
        terminalServerRepository.saveAndFlush(terminalServer);

        // Get all the terminalServerList
        restTerminalServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terminalServer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getTerminalServer() throws Exception {
        // Initialize the database
        terminalServerRepository.saveAndFlush(terminalServer);

        // Get the terminalServer
        restTerminalServerMockMvc
            .perform(get(ENTITY_API_URL_ID, terminalServer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terminalServer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingTerminalServer() throws Exception {
        // Get the terminalServer
        restTerminalServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTerminalServer() throws Exception {
        // Initialize the database
        terminalServerRepository.saveAndFlush(terminalServer);

        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();

        // Update the terminalServer
        TerminalServer updatedTerminalServer = terminalServerRepository.findById(terminalServer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTerminalServer are not directly saved in db
        em.detach(updatedTerminalServer);
        updatedTerminalServer.name(UPDATED_NAME).model(UPDATED_MODEL).ip(UPDATED_IP).info(UPDATED_INFO);
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(updatedTerminalServer);

        restTerminalServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terminalServerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isOk());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
        TerminalServer testTerminalServer = terminalServerList.get(terminalServerList.size() - 1);
        assertThat(testTerminalServer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerminalServer.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testTerminalServer.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testTerminalServer.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingTerminalServer() throws Exception {
        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();
        terminalServer.setId(longCount.incrementAndGet());

        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminalServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terminalServerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerminalServer() throws Exception {
        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();
        terminalServer.setId(longCount.incrementAndGet());

        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminalServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerminalServer() throws Exception {
        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();
        terminalServer.setId(longCount.incrementAndGet());

        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminalServerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerminalServerWithPatch() throws Exception {
        // Initialize the database
        terminalServerRepository.saveAndFlush(terminalServer);

        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();

        // Update the terminalServer using partial update
        TerminalServer partialUpdatedTerminalServer = new TerminalServer();
        partialUpdatedTerminalServer.setId(terminalServer.getId());

        partialUpdatedTerminalServer.name(UPDATED_NAME).model(UPDATED_MODEL);

        restTerminalServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerminalServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerminalServer))
            )
            .andExpect(status().isOk());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
        TerminalServer testTerminalServer = terminalServerList.get(terminalServerList.size() - 1);
        assertThat(testTerminalServer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerminalServer.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testTerminalServer.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testTerminalServer.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateTerminalServerWithPatch() throws Exception {
        // Initialize the database
        terminalServerRepository.saveAndFlush(terminalServer);

        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();

        // Update the terminalServer using partial update
        TerminalServer partialUpdatedTerminalServer = new TerminalServer();
        partialUpdatedTerminalServer.setId(terminalServer.getId());

        partialUpdatedTerminalServer.name(UPDATED_NAME).model(UPDATED_MODEL).ip(UPDATED_IP).info(UPDATED_INFO);

        restTerminalServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerminalServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerminalServer))
            )
            .andExpect(status().isOk());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
        TerminalServer testTerminalServer = terminalServerList.get(terminalServerList.size() - 1);
        assertThat(testTerminalServer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerminalServer.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testTerminalServer.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testTerminalServer.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingTerminalServer() throws Exception {
        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();
        terminalServer.setId(longCount.incrementAndGet());

        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminalServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, terminalServerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerminalServer() throws Exception {
        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();
        terminalServer.setId(longCount.incrementAndGet());

        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminalServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerminalServer() throws Exception {
        int databaseSizeBeforeUpdate = terminalServerRepository.findAll().size();
        terminalServer.setId(longCount.incrementAndGet());

        // Create the TerminalServer
        TerminalServerDTO terminalServerDTO = terminalServerMapper.toDto(terminalServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminalServerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terminalServerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TerminalServer in the database
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerminalServer() throws Exception {
        // Initialize the database
        terminalServerRepository.saveAndFlush(terminalServer);

        int databaseSizeBeforeDelete = terminalServerRepository.findAll().size();

        // Delete the terminalServer
        restTerminalServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, terminalServer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TerminalServer> terminalServerList = terminalServerRepository.findAll();
        assertThat(terminalServerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
