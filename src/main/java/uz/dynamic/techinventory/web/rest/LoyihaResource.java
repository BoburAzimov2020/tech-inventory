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
import uz.dynamic.techinventory.repository.LoyihaRepository;
import uz.dynamic.techinventory.service.LoyihaService;
import uz.dynamic.techinventory.service.dto.LoyihaDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Loyiha}.
 */
@RestController
@RequestMapping("/api/loyihas")
public class LoyihaResource {

    private final Logger log = LoggerFactory.getLogger(LoyihaResource.class);

    private static final String ENTITY_NAME = "loyiha";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoyihaService loyihaService;

    private final LoyihaRepository loyihaRepository;

    public LoyihaResource(LoyihaService loyihaService, LoyihaRepository loyihaRepository) {
        this.loyihaService = loyihaService;
        this.loyihaRepository = loyihaRepository;
    }

    /**
     * {@code POST  /loyihas} : Create a new loyiha.
     *
     * @param loyihaDTO the loyihaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loyihaDTO, or with status {@code 400 (Bad Request)} if the loyiha has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LoyihaDTO> createLoyiha(@Valid @RequestBody LoyihaDTO loyihaDTO) throws URISyntaxException {
        log.debug("REST request to save Loyiha : {}", loyihaDTO);
        if (loyihaDTO.getId() != null) {
            throw new BadRequestAlertException("A new loyiha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoyihaDTO result = loyihaService.save(loyihaDTO);
        return ResponseEntity
            .created(new URI("/api/loyihas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loyihas/:id} : Updates an existing loyiha.
     *
     * @param id the id of the loyihaDTO to save.
     * @param loyihaDTO the loyihaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loyihaDTO,
     * or with status {@code 400 (Bad Request)} if the loyihaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loyihaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoyihaDTO> updateLoyiha(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LoyihaDTO loyihaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Loyiha : {}, {}", id, loyihaDTO);
        if (loyihaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loyihaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loyihaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoyihaDTO result = loyihaService.update(loyihaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loyihaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loyihas/:id} : Partial updates given fields of an existing loyiha, field will ignore if it is null
     *
     * @param id the id of the loyihaDTO to save.
     * @param loyihaDTO the loyihaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loyihaDTO,
     * or with status {@code 400 (Bad Request)} if the loyihaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loyihaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loyihaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoyihaDTO> partialUpdateLoyiha(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LoyihaDTO loyihaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Loyiha partially : {}, {}", id, loyihaDTO);
        if (loyihaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loyihaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loyihaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoyihaDTO> result = loyihaService.partialUpdate(loyihaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loyihaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loyihas} : get all the loyihas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loyihas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LoyihaDTO>> getAllLoyihas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Loyihas");
        Page<LoyihaDTO> page = loyihaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loyihas/:id} : get the "id" loyiha.
     *
     * @param id the id of the loyihaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loyihaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoyihaDTO> getLoyiha(@PathVariable("id") Long id) {
        log.debug("REST request to get Loyiha : {}", id);
        Optional<LoyihaDTO> loyihaDTO = loyihaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loyihaDTO);
    }

    /**
     * {@code DELETE  /loyihas/:id} : delete the "id" loyiha.
     *
     * @param id the id of the loyihaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoyiha(@PathVariable("id") Long id) {
        log.debug("REST request to delete Loyiha : {}", id);
        loyihaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
