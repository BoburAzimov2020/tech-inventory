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
import uz.dynamic.techinventory.repository.CabelRepository;
import uz.dynamic.techinventory.service.CabelService;
import uz.dynamic.techinventory.service.dto.CabelDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Cabel}.
 */
@RestController
@RequestMapping("/api/cabels")
public class CabelResource {

    private final Logger log = LoggerFactory.getLogger(CabelResource.class);

    private static final String ENTITY_NAME = "cabel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CabelService cabelService;

    private final CabelRepository cabelRepository;

    public CabelResource(CabelService cabelService, CabelRepository cabelRepository) {
        this.cabelService = cabelService;
        this.cabelRepository = cabelRepository;
    }

    /**
     * {@code POST  /cabels} : Create a new cabel.
     *
     * @param cabelDTO the cabelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cabelDTO, or with status {@code 400 (Bad Request)} if the cabel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CabelDTO> createCabel(@Valid @RequestBody CabelDTO cabelDTO) throws URISyntaxException {
        log.debug("REST request to save Cabel : {}", cabelDTO);
        if (cabelDTO.getId() != null) {
            throw new BadRequestAlertException("A new cabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CabelDTO result = cabelService.save(cabelDTO);
        return ResponseEntity
            .created(new URI("/api/cabels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cabels/:id} : Updates an existing cabel.
     *
     * @param id the id of the cabelDTO to save.
     * @param cabelDTO the cabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cabelDTO,
     * or with status {@code 400 (Bad Request)} if the cabelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CabelDTO> updateCabel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CabelDTO cabelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cabel : {}, {}", id, cabelDTO);
        if (cabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cabelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CabelDTO result = cabelService.update(cabelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cabelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cabels/:id} : Partial updates given fields of an existing cabel, field will ignore if it is null
     *
     * @param id the id of the cabelDTO to save.
     * @param cabelDTO the cabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cabelDTO,
     * or with status {@code 400 (Bad Request)} if the cabelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cabelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CabelDTO> partialUpdateCabel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CabelDTO cabelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cabel partially : {}, {}", id, cabelDTO);
        if (cabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cabelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CabelDTO> result = cabelService.partialUpdate(cabelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cabelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cabels} : get all the cabels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cabels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CabelDTO>> getAllCabels(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Cabels");
        Page<CabelDTO> page = cabelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cabels/:id} : get the "id" cabel.
     *
     * @param id the id of the cabelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cabelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CabelDTO> getCabel(@PathVariable("id") Long id) {
        log.debug("REST request to get Cabel : {}", id);
        Optional<CabelDTO> cabelDTO = cabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cabelDTO);
    }

    /**
     * {@code DELETE  /cabels/:id} : delete the "id" cabel.
     *
     * @param id the id of the cabelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCabel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cabel : {}", id);
        cabelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
