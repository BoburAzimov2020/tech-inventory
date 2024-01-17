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
import uz.dynamic.techinventory.repository.RozetkaRepository;
import uz.dynamic.techinventory.service.RozetkaService;
import uz.dynamic.techinventory.service.dto.RozetkaDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Rozetka}.
 */
@RestController
@RequestMapping("/api/rozetkas")
public class RozetkaResource {

    private final Logger log = LoggerFactory.getLogger(RozetkaResource.class);

    private static final String ENTITY_NAME = "rozetka";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RozetkaService rozetkaService;

    private final RozetkaRepository rozetkaRepository;

    public RozetkaResource(RozetkaService rozetkaService, RozetkaRepository rozetkaRepository) {
        this.rozetkaService = rozetkaService;
        this.rozetkaRepository = rozetkaRepository;
    }

    /**
     * {@code POST  /rozetkas} : Create a new rozetka.
     *
     * @param rozetkaDTO the rozetkaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rozetkaDTO, or with status {@code 400 (Bad Request)} if the rozetka has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RozetkaDTO> createRozetka(@Valid @RequestBody RozetkaDTO rozetkaDTO) throws URISyntaxException {
        log.debug("REST request to save Rozetka : {}", rozetkaDTO);
        if (rozetkaDTO.getId() != null) {
            throw new BadRequestAlertException("A new rozetka cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RozetkaDTO result = rozetkaService.save(rozetkaDTO);
        return ResponseEntity
            .created(new URI("/api/rozetkas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rozetkas/:id} : Updates an existing rozetka.
     *
     * @param id the id of the rozetkaDTO to save.
     * @param rozetkaDTO the rozetkaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rozetkaDTO,
     * or with status {@code 400 (Bad Request)} if the rozetkaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rozetkaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RozetkaDTO> updateRozetka(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RozetkaDTO rozetkaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rozetka : {}, {}", id, rozetkaDTO);
        if (rozetkaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rozetkaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rozetkaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RozetkaDTO result = rozetkaService.update(rozetkaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rozetkaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rozetkas/:id} : Partial updates given fields of an existing rozetka, field will ignore if it is null
     *
     * @param id the id of the rozetkaDTO to save.
     * @param rozetkaDTO the rozetkaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rozetkaDTO,
     * or with status {@code 400 (Bad Request)} if the rozetkaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rozetkaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rozetkaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RozetkaDTO> partialUpdateRozetka(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RozetkaDTO rozetkaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rozetka partially : {}, {}", id, rozetkaDTO);
        if (rozetkaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rozetkaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rozetkaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RozetkaDTO> result = rozetkaService.partialUpdate(rozetkaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rozetkaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rozetkas} : get all the rozetkas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rozetkas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RozetkaDTO>> getAllRozetkas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Rozetkas");
        Page<RozetkaDTO> page = rozetkaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rozetkas/:id} : get the "id" rozetka.
     *
     * @param id the id of the rozetkaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rozetkaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RozetkaDTO> getRozetka(@PathVariable("id") Long id) {
        log.debug("REST request to get Rozetka : {}", id);
        Optional<RozetkaDTO> rozetkaDTO = rozetkaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rozetkaDTO);
    }

    /**
     * {@code DELETE  /rozetkas/:id} : delete the "id" rozetka.
     *
     * @param id the id of the rozetkaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRozetka(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rozetka : {}", id);
        rozetkaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
