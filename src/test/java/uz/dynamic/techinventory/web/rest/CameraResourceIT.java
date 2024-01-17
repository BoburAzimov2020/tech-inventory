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
import uz.dynamic.techinventory.domain.Camera;
import uz.dynamic.techinventory.domain.enumeration.CameraStatus;
import uz.dynamic.techinventory.repository.CameraRepository;
import uz.dynamic.techinventory.service.dto.CameraDTO;
import uz.dynamic.techinventory.service.mapper.CameraMapper;

/**
 * Integration tests for the {@link CameraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CameraResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final CameraStatus DEFAULT_STATUS = CameraStatus.OFFLINE;
    private static final CameraStatus UPDATED_STATUS = CameraStatus.ONLINE;

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cameras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private CameraMapper cameraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCameraMockMvc;

    private Camera camera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camera createEntity(EntityManager em) {
        Camera camera = new Camera()
            .name(DEFAULT_NAME)
            .model(DEFAULT_MODEL)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .ip(DEFAULT_IP)
            .status(DEFAULT_STATUS)
            .info(DEFAULT_INFO);
        return camera;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camera createUpdatedEntity(EntityManager em) {
        Camera camera = new Camera()
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .ip(UPDATED_IP)
            .status(UPDATED_STATUS)
            .info(UPDATED_INFO);
        return camera;
    }

    @BeforeEach
    public void initTest() {
        camera = createEntity(em);
    }

    @Test
    @Transactional
    void createCamera() throws Exception {
        int databaseSizeBeforeCreate = cameraRepository.findAll().size();
        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);
        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isCreated());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeCreate + 1);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCamera.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCamera.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testCamera.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testCamera.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void createCameraWithExistingId() throws Exception {
        // Create the Camera with an existing ID
        camera.setId(1L);
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        int databaseSizeBeforeCreate = cameraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setName(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = cameraRepository.findAll().size();
        // set the field null
        camera.setIp(null);

        // Create the Camera, which fails.
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isBadRequest());

        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCameras() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get all the cameraList
        restCameraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(camera.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }

    @Test
    @Transactional
    void getCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get the camera
        restCameraMockMvc
            .perform(get(ENTITY_API_URL_ID, camera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(camera.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    void getNonExistingCamera() throws Exception {
        // Get the camera
        restCameraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera
        Camera updatedCamera = cameraRepository.findById(camera.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCamera are not directly saved in db
        em.detach(updatedCamera);
        updatedCamera
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .ip(UPDATED_IP)
            .status(UPDATED_STATUS)
            .info(UPDATED_INFO);
        CameraDTO cameraDTO = cameraMapper.toDto(updatedCamera);

        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCamera.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testCamera.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testCamera.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void putNonExistingCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(longCount.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cameraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(longCount.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(longCount.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cameraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCameraWithPatch() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera using partial update
        Camera partialUpdatedCamera = new Camera();
        partialUpdatedCamera.setId(camera.getId());

        partialUpdatedCamera.name(UPDATED_NAME).model(UPDATED_MODEL).ip(UPDATED_IP);

        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCamera.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testCamera.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testCamera.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCameraWithPatch() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera using partial update
        Camera partialUpdatedCamera = new Camera();
        partialUpdatedCamera.setId(camera.getId());

        partialUpdatedCamera
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .ip(UPDATED_IP)
            .status(UPDATED_STATUS)
            .info(UPDATED_INFO);

        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCamera.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testCamera.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testCamera.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCamera.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(longCount.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cameraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(longCount.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(longCount.incrementAndGet());

        // Create the Camera
        CameraDTO cameraDTO = cameraMapper.toDto(camera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cameraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeDelete = cameraRepository.findAll().size();

        // Delete the camera
        restCameraMockMvc
            .perform(delete(ENTITY_API_URL_ID, camera.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
