package uz.dynamic.techinventory.web.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uz.dynamic.techinventory.repository.CameraTypeRepository;
import uz.dynamic.techinventory.service.CameraTypeService;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.CameraType}.
 */
@RestController
@RequestMapping("/api/camera-types")
public class CameraTypeResource {

    private final Logger log = LoggerFactory.getLogger(CameraTypeResource.class);

    private static final String ENTITY_NAME = "cameraType";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CameraTypeService cameraTypeService;

    private final CameraTypeRepository cameraTypeRepository;

    public CameraTypeResource(CameraTypeService cameraTypeService, CameraTypeRepository cameraTypeRepository) {
        this.cameraTypeService = cameraTypeService;
        this.cameraTypeRepository = cameraTypeRepository;
    }

    /**
     * {@code POST  /camera-types} : Create a new cameraType.
     *
     * @param cameraTypeDTO the cameraTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cameraTypeDTO, or with status {@code 400 (Bad Request)} if the cameraType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CameraTypeDTO> createCameraType(@Valid @RequestBody CameraTypeDTO cameraTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CameraType : {}", cameraTypeDTO);
        if (cameraTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cameraType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CameraTypeDTO result = cameraTypeService.save(cameraTypeDTO);
        return ResponseEntity
            .created(new URI("/api/camera-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /camera-types/:id} : Updates an existing cameraType.
     *
     * @param id the id of the cameraTypeDTO to save.
     * @param cameraTypeDTO the cameraTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cameraTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cameraTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cameraTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CameraTypeDTO> updateCameraType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CameraTypeDTO cameraTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CameraType : {}, {}", id, cameraTypeDTO);
        if (cameraTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cameraTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CameraTypeDTO result = cameraTypeService.update(cameraTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cameraTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /camera-types/:id} : Partial updates given fields of an existing cameraType, field will ignore if it is null
     *
     * @param id the id of the cameraTypeDTO to save.
     * @param cameraTypeDTO the cameraTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cameraTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cameraTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cameraTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cameraTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CameraTypeDTO> partialUpdateCameraType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CameraTypeDTO cameraTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CameraType partially : {}, {}", id, cameraTypeDTO);
        if (cameraTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cameraTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CameraTypeDTO> result = cameraTypeService.partialUpdate(cameraTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cameraTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /camera-types} : get all the cameraTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cameraTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CameraTypeDTO>> getAllCameraTypes(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CameraTypes");
        Page<CameraTypeDTO> page = cameraTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /camera-types/:id} : get the "id" cameraType.
     *
     * @param id the id of the cameraTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cameraTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CameraTypeDTO> getCameraType(@PathVariable("id") Long id) {
        log.debug("REST request to get CameraType : {}", id);
        Optional<CameraTypeDTO> cameraTypeDTO = cameraTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cameraTypeDTO);
    }

    /**
     * {@code DELETE  /camera-types/:id} : delete the "id" cameraType.
     *
     * @param id the id of the cameraTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCameraType(@PathVariable("id") Long id) {
        log.debug("REST request to delete CameraType : {}", id);
        cameraTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
