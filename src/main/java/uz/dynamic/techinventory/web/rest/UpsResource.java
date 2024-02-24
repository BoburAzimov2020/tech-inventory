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

import uz.dynamic.techinventory.repository.UpsRepository;
import uz.dynamic.techinventory.service.UpsService;
import uz.dynamic.techinventory.service.dto.UpsDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Ups}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/ups")
public class UpsResource {

    private final Logger log = LoggerFactory.getLogger(UpsResource.class);

    private static final String ENTITY_NAME = "ups";

    @Value("${spring.application.name}")
    private String applicationName;

    private final UpsService upsService;

    private final UpsRepository upsRepository;

    public UpsResource(UpsService upsService, UpsRepository upsRepository) {
        this.upsService = upsService;
        this.upsRepository = upsRepository;
    }

    /**
     * {@code POST  /ups} : Create a new ups.
     *
     * @param upsDTO the upsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new upsDTO, or with status {@code 400 (Bad Request)} if the ups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UpsDTO> createUps(@Valid @RequestBody UpsDTO upsDTO) throws URISyntaxException {
        log.debug("REST request to save Ups : {}", upsDTO);
        if (upsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UpsDTO result = upsService.save(upsDTO);
        return ResponseEntity
            .created(new URI("/api/ups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ups/:id} : Updates an existing ups.
     *
     * @param id the id of the upsDTO to save.
     * @param upsDTO the upsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated upsDTO,
     * or with status {@code 400 (Bad Request)} if the upsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the upsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UpsDTO> updateUps(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody UpsDTO upsDTO)
        throws URISyntaxException {
        log.debug("REST request to update Ups : {}, {}", id, upsDTO);
        if (upsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, upsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!upsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UpsDTO result = upsService.update(upsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, upsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ups/:id} : Partial updates given fields of an existing ups, field will ignore if it is null
     *
     * @param id the id of the upsDTO to save.
     * @param upsDTO the upsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated upsDTO,
     * or with status {@code 400 (Bad Request)} if the upsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the upsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the upsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UpsDTO> partialUpdateUps(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UpsDTO upsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ups partially : {}, {}", id, upsDTO);
        if (upsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, upsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!upsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UpsDTO> result = upsService.partialUpdate(upsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, upsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ups} : get all the ups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UpsDTO>> getAllUps(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ups");
        Page<UpsDTO> page = upsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ups/:id} : get the "id" ups.
     *
     * @param id the id of the upsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the upsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UpsDTO> getUps(@PathVariable("id") Long id) {
        log.debug("REST request to get Ups : {}", id);
        Optional<UpsDTO> upsDTO = upsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(upsDTO);
    }

    /**
     * {@code DELETE  /ups/:id} : delete the "id" ups.
     *
     * @param id the id of the upsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUps(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ups : {}", id);
        upsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
