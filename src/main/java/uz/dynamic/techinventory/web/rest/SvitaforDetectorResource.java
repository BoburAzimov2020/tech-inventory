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

import uz.dynamic.techinventory.repository.SvitaforDetectorRepository;
import uz.dynamic.techinventory.service.SvitaforDetectorService;
import uz.dynamic.techinventory.service.dto.SvitaforDetectorDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.SvitaforDetector}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/svitafor-detectors")
public class SvitaforDetectorResource {

    private final Logger log = LoggerFactory.getLogger(SvitaforDetectorResource.class);

    private static final String ENTITY_NAME = "svitaforDetector";

    @Value("${spring.application.name}")
    private String applicationName;

    private final SvitaforDetectorService svitaforDetectorService;

    private final SvitaforDetectorRepository svitaforDetectorRepository;

    public SvitaforDetectorResource(
        SvitaforDetectorService svitaforDetectorService,
        SvitaforDetectorRepository svitaforDetectorRepository
    ) {
        this.svitaforDetectorService = svitaforDetectorService;
        this.svitaforDetectorRepository = svitaforDetectorRepository;
    }

    /**
     * {@code POST  /svitafor-detectors} : Create a new svitaforDetector.
     *
     * @param svitaforDetectorDTO the svitaforDetectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new svitaforDetectorDTO, or with status {@code 400 (Bad Request)} if the svitaforDetector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SvitaforDetectorDTO> createSvitaforDetector(@Valid @RequestBody SvitaforDetectorDTO svitaforDetectorDTO)
        throws URISyntaxException {
        log.debug("REST request to save SvitaforDetector : {}", svitaforDetectorDTO);
        if (svitaforDetectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new svitaforDetector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SvitaforDetectorDTO result = svitaforDetectorService.save(svitaforDetectorDTO);
        return ResponseEntity
            .created(new URI("/api/svitafor-detectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /svitafor-detectors/:id} : Updates an existing svitaforDetector.
     *
     * @param id the id of the svitaforDetectorDTO to save.
     * @param svitaforDetectorDTO the svitaforDetectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated svitaforDetectorDTO,
     * or with status {@code 400 (Bad Request)} if the svitaforDetectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the svitaforDetectorDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SvitaforDetectorDTO> updateSvitaforDetector(
        @PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody SvitaforDetectorDTO svitaforDetectorDTO) {
        log.debug("REST request to update SvitaforDetector : {}, {}", id, svitaforDetectorDTO);
        if (svitaforDetectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, svitaforDetectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!svitaforDetectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SvitaforDetectorDTO result = svitaforDetectorService.update(svitaforDetectorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, svitaforDetectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /svitafor-detectors/:id} : Partial updates given fields of an existing svitaforDetector, field will ignore if it is null
     *
     * @param id the id of the svitaforDetectorDTO to save.
     * @param svitaforDetectorDTO the svitaforDetectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated svitaforDetectorDTO,
     * or with status {@code 400 (Bad Request)} if the svitaforDetectorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the svitaforDetectorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the svitaforDetectorDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SvitaforDetectorDTO> partialUpdateSvitaforDetector(
        @PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody SvitaforDetectorDTO svitaforDetectorDTO) {
        log.debug("REST request to partial update SvitaforDetector partially : {}, {}", id, svitaforDetectorDTO);
        if (svitaforDetectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, svitaforDetectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!svitaforDetectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SvitaforDetectorDTO> result = svitaforDetectorService.partialUpdate(svitaforDetectorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, svitaforDetectorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /svitafor-detectors} : get all the svitaforDetectors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of svitaforDetectors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SvitaforDetectorDTO>> getAllSvitaforDetectors(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SvitaforDetectors");
        Page<SvitaforDetectorDTO> page = svitaforDetectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /svitafor-detectors/obyekt/:obyektId} : get all the svitaforDetectors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of svitaforDetectors in body.
     */
    @GetMapping("/obyekt/{obyektId}")
    public ResponseEntity<List<SvitaforDetectorDTO>> getAllByObyekt(@ParameterObject Pageable pageable,
                                                                    @PathVariable("obyektId") Long obyektId) {
        log.debug("REST request to get a page of SvitaforDetectors");
        Page<SvitaforDetectorDTO> page = svitaforDetectorService.findAllByObyekt(pageable, obyektId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /svitafor-detectors/:id} : get the "id" svitaforDetector.
     *
     * @param id the id of the svitaforDetectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the svitaforDetectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SvitaforDetectorDTO> getSvitaforDetector(@PathVariable("id") Long id) {
        log.debug("REST request to get SvitaforDetector : {}", id);
        Optional<SvitaforDetectorDTO> svitaforDetectorDTO = svitaforDetectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(svitaforDetectorDTO);
    }

    /**
     * {@code DELETE  /svitafor-detectors/:id} : delete the "id" svitaforDetector.
     *
     * @param id the id of the svitaforDetectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSvitaforDetector(@PathVariable("id") Long id) {
        log.debug("REST request to delete SvitaforDetector : {}", id);
        svitaforDetectorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
