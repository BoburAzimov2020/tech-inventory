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

import uz.dynamic.techinventory.repository.StoykaTypeRepository;
import uz.dynamic.techinventory.service.StoykaTypeService;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.StoykaType}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/stoyka-types")
public class StoykaTypeResource {

    private final Logger log = LoggerFactory.getLogger(StoykaTypeResource.class);

    private static final String ENTITY_NAME = "stoykaType";

    @Value("${spring.application.name}")
    private String applicationName;

    private final StoykaTypeService stoykaTypeService;

    private final StoykaTypeRepository stoykaTypeRepository;

    public StoykaTypeResource(StoykaTypeService stoykaTypeService, StoykaTypeRepository stoykaTypeRepository) {
        this.stoykaTypeService = stoykaTypeService;
        this.stoykaTypeRepository = stoykaTypeRepository;
    }

    /**
     * {@code POST  /stoyka-types} : Create a new stoykaType.
     *
     * @param stoykaTypeDTO the stoykaTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stoykaTypeDTO, or with status {@code 400 (Bad Request)} if the stoykaType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StoykaTypeDTO> createStoykaType(@Valid @RequestBody StoykaTypeDTO stoykaTypeDTO) throws URISyntaxException {
        log.debug("REST request to save StoykaType : {}", stoykaTypeDTO);
        if (stoykaTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new stoykaType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoykaTypeDTO result = stoykaTypeService.save(stoykaTypeDTO);
        return ResponseEntity
            .created(new URI("/api/stoyka-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stoyka-types/:id} : Updates an existing stoykaType.
     *
     * @param id the id of the stoykaTypeDTO to save.
     * @param stoykaTypeDTO the stoykaTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stoykaTypeDTO,
     * or with status {@code 400 (Bad Request)} if the stoykaTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stoykaTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StoykaTypeDTO> updateStoykaType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StoykaTypeDTO stoykaTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StoykaType : {}, {}", id, stoykaTypeDTO);
        if (stoykaTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stoykaTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stoykaTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StoykaTypeDTO result = stoykaTypeService.update(stoykaTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stoykaTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stoyka-types/:id} : Partial updates given fields of an existing stoykaType, field will ignore if it is null
     *
     * @param id the id of the stoykaTypeDTO to save.
     * @param stoykaTypeDTO the stoykaTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stoykaTypeDTO,
     * or with status {@code 400 (Bad Request)} if the stoykaTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stoykaTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stoykaTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StoykaTypeDTO> partialUpdateStoykaType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StoykaTypeDTO stoykaTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StoykaType partially : {}, {}", id, stoykaTypeDTO);
        if (stoykaTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stoykaTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stoykaTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StoykaTypeDTO> result = stoykaTypeService.partialUpdate(stoykaTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stoykaTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stoyka-types} : get all the stoykaTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stoykaTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StoykaTypeDTO>> getAllStoykaTypes(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StoykaTypes");
        Page<StoykaTypeDTO> page = stoykaTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stoyka-types/:id} : get the "id" stoykaType.
     *
     * @param id the id of the stoykaTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stoykaTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StoykaTypeDTO> getStoykaType(@PathVariable("id") Long id) {
        log.debug("REST request to get StoykaType : {}", id);
        Optional<StoykaTypeDTO> stoykaTypeDTO = stoykaTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stoykaTypeDTO);
    }

    /**
     * {@code DELETE  /stoyka-types/:id} : delete the "id" stoykaType.
     *
     * @param id the id of the stoykaTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoykaType(@PathVariable("id") Long id) {
        log.debug("REST request to delete StoykaType : {}", id);
        stoykaTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
