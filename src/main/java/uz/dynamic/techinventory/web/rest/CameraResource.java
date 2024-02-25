package uz.dynamic.techinventory.web.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uz.dynamic.techinventory.repository.CameraRepository;
import uz.dynamic.techinventory.service.CameraService;
import uz.dynamic.techinventory.service.dto.CameraDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Camera}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/cameras")
public class CameraResource {

    private final Logger log = LoggerFactory.getLogger(CameraResource.class);

    private static final String ENTITY_NAME = "camera";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CameraService cameraService;

    private final CameraRepository cameraRepository;

    public CameraResource(CameraService cameraService, CameraRepository cameraRepository) {
        this.cameraService = cameraService;
        this.cameraRepository = cameraRepository;
    }

    /**
     * {@code POST  /cameras} : Create a new camera.
     *
     * @param cameraDTO the cameraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cameraDTO, or with status {@code 400 (Bad Request)} if the camera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CameraDTO> createCamera(@Valid @RequestBody CameraDTO cameraDTO) throws URISyntaxException {
        log.debug("REST request to save Camera : {}", cameraDTO);
        if (cameraDTO.getId() != null) {
            throw new BadRequestAlertException("A new camera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CameraDTO result = cameraService.save(cameraDTO);
        return ResponseEntity
            .created(new URI("/api/cameras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cameras/:id} : Updates an existing camera.
     *
     * @param id the id of the cameraDTO to save.
     * @param cameraDTO the cameraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cameraDTO,
     * or with status {@code 400 (Bad Request)} if the cameraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cameraDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CameraDTO> updateCamera(
        @PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody CameraDTO cameraDTO) {
        log.debug("REST request to update Camera : {}, {}", id, cameraDTO);
        if (cameraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cameraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CameraDTO result = cameraService.update(cameraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cameraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cameras/:id} : Partial updates given fields of an existing camera, field will ignore if it is null
     *
     * @param id the id of the cameraDTO to save.
     * @param cameraDTO the cameraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cameraDTO,
     * or with status {@code 400 (Bad Request)} if the cameraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cameraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cameraDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CameraDTO> partialUpdateCamera(
        @PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody CameraDTO cameraDTO) {
        log.debug("REST request to partial update Camera partially : {}, {}", id, cameraDTO);
        if (cameraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cameraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CameraDTO> result = cameraService.partialUpdate(cameraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cameraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cameras} : get all the cameras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cameras in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CameraDTO>> getAllCameras(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Cameras");
        Page<CameraDTO> page = cameraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cameras/type/:typeId} : get all the cameras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cameras in body.
     */
    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<CameraDTO>> getAllByType(@ParameterObject Pageable pageable,
                                                        @PathVariable("typeId") Long typeId) {
        log.debug("REST request to get a page of Cameras");
        Page<CameraDTO> page = cameraService.findAllByCameraType(pageable, typeId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cameras/brand/:brandId} : get all the cameras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cameras in body.
     */
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<CameraDTO>> getAllByBrand(@ParameterObject Pageable pageable,
                                                        @PathVariable("brandId") Long brandId) {
        log.debug("REST request to get a page of Cameras");
        Page<CameraDTO> page = cameraService.findAllByCameraBrand(pageable, brandId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cameras/:id} : get the "id" camera.
     *
     * @param id the id of the cameraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cameraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CameraDTO> getCamera(@PathVariable("id") Long id) {
        log.debug("REST request to get Camera : {}", id);
        Optional<CameraDTO> cameraDTO = cameraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cameraDTO);
    }

    /**
     * {@code DELETE  /cameras/:id} : delete the "id" camera.
     *
     * @param id the id of the cameraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamera(@PathVariable("id") Long id) {
        log.debug("REST request to delete Camera : {}", id);
        cameraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
