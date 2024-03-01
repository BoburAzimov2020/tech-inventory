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

import uz.dynamic.techinventory.repository.StoykaRepository;
import uz.dynamic.techinventory.service.StoykaService;
import uz.dynamic.techinventory.service.dto.StoykaDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Stoyka}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/stoykas")
public class StoykaResource {

    private final Logger log = LoggerFactory.getLogger(StoykaResource.class);

    private static final String ENTITY_NAME = "stoyka";

    @Value("${spring.application.name}")
    private String applicationName;

    private final StoykaService stoykaService;

    private final StoykaRepository stoykaRepository;

    public StoykaResource(StoykaService stoykaService, StoykaRepository stoykaRepository) {
        this.stoykaService = stoykaService;
        this.stoykaRepository = stoykaRepository;
    }

    /**
     * {@code POST  /stoykas} : Create a new stoyka.
     *
     * @param stoykaDTO the stoykaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stoykaDTO, or with status {@code 400 (Bad Request)} if the stoyka has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StoykaDTO> createStoyka(@Valid @RequestBody StoykaDTO stoykaDTO) throws URISyntaxException {
        log.debug("REST request to save Stoyka : {}", stoykaDTO);
        if (stoykaDTO.getId() != null) {
            throw new BadRequestAlertException("A new stoyka cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoykaDTO result = stoykaService.save(stoykaDTO);
        return ResponseEntity
                .created(new URI("/api/stoykas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                                                              result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /stoykas/:id} : Updates an existing stoyka.
     *
     * @param id        the id of the stoykaDTO to save.
     * @param stoykaDTO the stoykaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stoykaDTO,
     * or with status {@code 400 (Bad Request)} if the stoykaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stoykaDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StoykaDTO> updateStoyka(
            @PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody StoykaDTO stoykaDTO) {
        log.debug("REST request to update Stoyka : {}, {}", id, stoykaDTO);
        if (stoykaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stoykaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stoykaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StoykaDTO result = stoykaService.update(stoykaDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                                                            stoykaDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /stoykas/:id} : Partial updates given fields of an existing stoyka, field will ignore if it is null
     *
     * @param id        the id of the stoykaDTO to save.
     * @param stoykaDTO the stoykaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stoykaDTO,
     * or with status {@code 400 (Bad Request)} if the stoykaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stoykaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stoykaDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<StoykaDTO> partialUpdateStoyka(
            @PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody StoykaDTO stoykaDTO) {
        log.debug("REST request to partial update Stoyka partially : {}, {}", id, stoykaDTO);
        if (stoykaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stoykaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stoykaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StoykaDTO> result = stoykaService.partialUpdate(stoykaDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stoykaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stoykas} : get all the stoykas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stoykas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StoykaDTO>> getAllStoykas(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Stoykas");
        Page<StoykaDTO> page = stoykaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stoyka-types/obyekt/:obyektId} : get all the stoykaTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stoykaTypes in body.
     */
    @GetMapping("/obyekt/{obyektId}")
    public ResponseEntity<List<StoykaDTO>> getAllByObyekt(@ParameterObject Pageable pageable,
                                                          @PathVariable("obyektId") Long obyektId) {
        log.debug("REST request to get a page of Stoyka");
        Page<StoykaDTO> page = stoykaService.findAllByObyekt(pageable, obyektId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stoykas/type/:typeId} : get all the stoykas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stoykas in body.
     */
    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<StoykaDTO>> getAllByType(@ParameterObject Pageable pageable,
                                                        @PathVariable("typeId") Long typeId) {
        log.debug("REST request to get a page of Stoykas");
        Page<StoykaDTO> page = stoykaService.findAllByStoykaType(pageable, typeId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stoykas/:id} : get the "id" stoyka.
     *
     * @param id the id of the stoykaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stoykaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StoykaDTO> getStoyka(@PathVariable("id") Long id) {
        log.debug("REST request to get Stoyka : {}", id);
        Optional<StoykaDTO> stoykaDTO = stoykaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stoykaDTO);
    }

    /**
     * {@code DELETE  /stoykas/:id} : delete the "id" stoyka.
     *
     * @param id the id of the stoykaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoyka(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stoyka : {}", id);
        stoykaService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
