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
import uz.dynamic.techinventory.repository.AkumulatorRepository;
import uz.dynamic.techinventory.service.AkumulatorService;
import uz.dynamic.techinventory.service.dto.AkumulatorDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Akumulator}.
 */
@RestController
@RequestMapping("/api/akumulators")
public class AkumulatorResource {

    private final Logger log = LoggerFactory.getLogger(AkumulatorResource.class);

    private static final String ENTITY_NAME = "akumulator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkumulatorService akumulatorService;

    private final AkumulatorRepository akumulatorRepository;

    public AkumulatorResource(AkumulatorService akumulatorService, AkumulatorRepository akumulatorRepository) {
        this.akumulatorService = akumulatorService;
        this.akumulatorRepository = akumulatorRepository;
    }

    /**
     * {@code POST  /akumulators} : Create a new akumulator.
     *
     * @param akumulatorDTO the akumulatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akumulatorDTO, or with status {@code 400 (Bad Request)} if the akumulator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AkumulatorDTO> createAkumulator(@Valid @RequestBody AkumulatorDTO akumulatorDTO) throws URISyntaxException {
        log.debug("REST request to save Akumulator : {}", akumulatorDTO);
        if (akumulatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new akumulator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkumulatorDTO result = akumulatorService.save(akumulatorDTO);
        return ResponseEntity
            .created(new URI("/api/akumulators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akumulators/:id} : Updates an existing akumulator.
     *
     * @param id the id of the akumulatorDTO to save.
     * @param akumulatorDTO the akumulatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akumulatorDTO,
     * or with status {@code 400 (Bad Request)} if the akumulatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akumulatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AkumulatorDTO> updateAkumulator(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AkumulatorDTO akumulatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Akumulator : {}, {}", id, akumulatorDTO);
        if (akumulatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akumulatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akumulatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkumulatorDTO result = akumulatorService.update(akumulatorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, akumulatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /akumulators/:id} : Partial updates given fields of an existing akumulator, field will ignore if it is null
     *
     * @param id the id of the akumulatorDTO to save.
     * @param akumulatorDTO the akumulatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akumulatorDTO,
     * or with status {@code 400 (Bad Request)} if the akumulatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the akumulatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the akumulatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AkumulatorDTO> partialUpdateAkumulator(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AkumulatorDTO akumulatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Akumulator partially : {}, {}", id, akumulatorDTO);
        if (akumulatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akumulatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akumulatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AkumulatorDTO> result = akumulatorService.partialUpdate(akumulatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, akumulatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /akumulators} : get all the akumulators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akumulators in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AkumulatorDTO>> getAllAkumulators(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Akumulators");
        Page<AkumulatorDTO> page = akumulatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /akumulators/:id} : get the "id" akumulator.
     *
     * @param id the id of the akumulatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akumulatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AkumulatorDTO> getAkumulator(@PathVariable("id") Long id) {
        log.debug("REST request to get Akumulator : {}", id);
        Optional<AkumulatorDTO> akumulatorDTO = akumulatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akumulatorDTO);
    }

    /**
     * {@code DELETE  /akumulators/:id} : delete the "id" akumulator.
     *
     * @param id the id of the akumulatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAkumulator(@PathVariable("id") Long id) {
        log.debug("REST request to delete Akumulator : {}", id);
        akumulatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
