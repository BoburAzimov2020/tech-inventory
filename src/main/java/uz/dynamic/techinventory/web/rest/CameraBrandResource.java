package uz.dynamic.techinventory.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.dynamic.techinventory.repository.CameraBrandRepository;
import uz.dynamic.techinventory.service.CameraBrandService;
import uz.dynamic.techinventory.service.dto.CameraBrandDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.CameraBrand}.
 */
@RestController
@RequestMapping("/api/camera-brands")
public class CameraBrandResource {

    private final Logger log = LoggerFactory.getLogger(CameraBrandResource.class);

    private static final String ENTITY_NAME = "cameraBrand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CameraBrandService cameraBrandService;

    private final CameraBrandRepository cameraBrandRepository;

    public CameraBrandResource(CameraBrandService cameraBrandService, CameraBrandRepository cameraBrandRepository) {
        this.cameraBrandService = cameraBrandService;
        this.cameraBrandRepository = cameraBrandRepository;
    }

    /**
     * {@code POST  /camera-brands} : Create a new cameraBrand.
     *
     * @param cameraBrandDTO the cameraBrandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cameraBrandDTO, or with status {@code 400 (Bad Request)} if the cameraBrand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CameraBrandDTO> createCameraBrand(@Valid @RequestBody CameraBrandDTO cameraBrandDTO) throws URISyntaxException {
        log.debug("REST request to save CameraBrand : {}", cameraBrandDTO);
        if (cameraBrandDTO.getId() != null) {
            throw new BadRequestAlertException("A new cameraBrand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CameraBrandDTO result = cameraBrandService.save(cameraBrandDTO);
        return ResponseEntity
            .created(new URI("/api/camera-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /camera-brands/:id} : Updates an existing cameraBrand.
     *
     * @param id the id of the cameraBrandDTO to save.
     * @param cameraBrandDTO the cameraBrandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cameraBrandDTO,
     * or with status {@code 400 (Bad Request)} if the cameraBrandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cameraBrandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CameraBrandDTO> updateCameraBrand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CameraBrandDTO cameraBrandDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CameraBrand : {}, {}", id, cameraBrandDTO);
        if (cameraBrandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cameraBrandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraBrandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CameraBrandDTO result = cameraBrandService.update(cameraBrandDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cameraBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /camera-brands/:id} : Partial updates given fields of an existing cameraBrand, field will ignore if it is null
     *
     * @param id the id of the cameraBrandDTO to save.
     * @param cameraBrandDTO the cameraBrandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cameraBrandDTO,
     * or with status {@code 400 (Bad Request)} if the cameraBrandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cameraBrandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cameraBrandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CameraBrandDTO> partialUpdateCameraBrand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CameraBrandDTO cameraBrandDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CameraBrand partially : {}, {}", id, cameraBrandDTO);
        if (cameraBrandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cameraBrandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraBrandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CameraBrandDTO> result = cameraBrandService.partialUpdate(cameraBrandDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cameraBrandDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /camera-brands} : get all the cameraBrands.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cameraBrands in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CameraBrandDTO>> getAllCameraBrands(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CameraBrands");
        Page<CameraBrandDTO> page = cameraBrandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /camera-brands/:id} : get the "id" cameraBrand.
     *
     * @param id the id of the cameraBrandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cameraBrandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CameraBrandDTO> getCameraBrand(@PathVariable("id") Long id) {
        log.debug("REST request to get CameraBrand : {}", id);
        Optional<CameraBrandDTO> cameraBrandDTO = cameraBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cameraBrandDTO);
    }

    /**
     * {@code DELETE  /camera-brands/:id} : delete the "id" cameraBrand.
     *
     * @param id the id of the cameraBrandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCameraBrand(@PathVariable("id") Long id) {
        log.debug("REST request to delete CameraBrand : {}", id);
        cameraBrandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
