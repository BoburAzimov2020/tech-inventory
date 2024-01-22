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

import uz.dynamic.techinventory.repository.ObyektRepository;
import uz.dynamic.techinventory.service.ObyektService;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Obyekt}.
 */
@RestController
@RequestMapping("/api/obyekts")
public class ObyektResource {

    private final Logger log = LoggerFactory.getLogger(ObyektResource.class);

    private static final String ENTITY_NAME = "obyekt";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ObyektService obyektService;

    private final ObyektRepository obyektRepository;

    public ObyektResource(ObyektService obyektService, ObyektRepository obyektRepository) {
        this.obyektService = obyektService;
        this.obyektRepository = obyektRepository;
    }

    /**
     * {@code POST  /obyekts} : Create a new obyekt.
     *
     * @param obyektDTO the obyektDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new obyektDTO, or with status {@code 400 (Bad Request)} if the obyekt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ObyektDTO> createObyekt(@Valid @RequestBody ObyektDTO obyektDTO) throws URISyntaxException {
        log.debug("REST request to save Obyekt : {}", obyektDTO);
        if (obyektDTO.getId() != null) {
            throw new BadRequestAlertException("A new obyekt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObyektDTO result = obyektService.save(obyektDTO);
        return ResponseEntity
            .created(new URI("/api/obyekts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obyekts/:id} : Updates an existing obyekt.
     *
     * @param id the id of the obyektDTO to save.
     * @param obyektDTO the obyektDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obyektDTO,
     * or with status {@code 400 (Bad Request)} if the obyektDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the obyektDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObyektDTO> updateObyekt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObyektDTO obyektDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Obyekt : {}, {}", id, obyektDTO);
        if (obyektDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, obyektDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!obyektRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObyektDTO result = obyektService.update(obyektDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obyektDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /obyekts/:id} : Partial updates given fields of an existing obyekt, field will ignore if it is null
     *
     * @param id the id of the obyektDTO to save.
     * @param obyektDTO the obyektDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obyektDTO,
     * or with status {@code 400 (Bad Request)} if the obyektDTO is not valid,
     * or with status {@code 404 (Not Found)} if the obyektDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the obyektDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObyektDTO> partialUpdateObyekt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObyektDTO obyektDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Obyekt partially : {}, {}", id, obyektDTO);
        if (obyektDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, obyektDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!obyektRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObyektDTO> result = obyektService.partialUpdate(obyektDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obyektDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /obyekts} : get all the obyekts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of obyekts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ObyektDTO>> getAllObyekts(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Obyekts");
        Page<ObyektDTO> page = obyektService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /obyekts/:id} : get the "id" obyekt.
     *
     * @param id the id of the obyektDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the obyektDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObyektDTO> getObyekt(@PathVariable("id") Long id) {
        log.debug("REST request to get Obyekt : {}", id);
        Optional<ObyektDTO> obyektDTO = obyektService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obyektDTO);
    }

    /**
     * {@code DELETE  /obyekts/:id} : delete the "id" obyekt.
     *
     * @param id the id of the obyektDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObyekt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Obyekt : {}", id);
        obyektService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
