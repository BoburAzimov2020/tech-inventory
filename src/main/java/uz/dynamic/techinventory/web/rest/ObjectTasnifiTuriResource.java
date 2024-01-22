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

import uz.dynamic.techinventory.repository.ObjectTasnifiTuriRepository;
import uz.dynamic.techinventory.service.ObjectTasnifiTuriService;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiTuriDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.ObjectTasnifiTuri}.
 */
@RestController
@RequestMapping("/api/object-tasnifi-turis")
public class ObjectTasnifiTuriResource {

    private final Logger log = LoggerFactory.getLogger(ObjectTasnifiTuriResource.class);

    private static final String ENTITY_NAME = "objectTasnifiTuri";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ObjectTasnifiTuriService objectTasnifiTuriService;

    private final ObjectTasnifiTuriRepository objectTasnifiTuriRepository;

    public ObjectTasnifiTuriResource(
        ObjectTasnifiTuriService objectTasnifiTuriService,
        ObjectTasnifiTuriRepository objectTasnifiTuriRepository
    ) {
        this.objectTasnifiTuriService = objectTasnifiTuriService;
        this.objectTasnifiTuriRepository = objectTasnifiTuriRepository;
    }

    /**
     * {@code POST  /object-tasnifi-turis} : Create a new objectTasnifiTuri.
     *
     * @param objectTasnifiTuriDTO the objectTasnifiTuriDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new objectTasnifiTuriDTO, or with status {@code 400 (Bad Request)} if the objectTasnifiTuri has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ObjectTasnifiTuriDTO> createObjectTasnifiTuri(@Valid @RequestBody ObjectTasnifiTuriDTO objectTasnifiTuriDTO)
        throws URISyntaxException {
        log.debug("REST request to save ObjectTasnifiTuri : {}", objectTasnifiTuriDTO);
        if (objectTasnifiTuriDTO.getId() != null) {
            throw new BadRequestAlertException("A new objectTasnifiTuri cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObjectTasnifiTuriDTO result = objectTasnifiTuriService.save(objectTasnifiTuriDTO);
        return ResponseEntity
            .created(new URI("/api/object-tasnifi-turis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /object-tasnifi-turis/:id} : Updates an existing objectTasnifiTuri.
     *
     * @param id the id of the objectTasnifiTuriDTO to save.
     * @param objectTasnifiTuriDTO the objectTasnifiTuriDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectTasnifiTuriDTO,
     * or with status {@code 400 (Bad Request)} if the objectTasnifiTuriDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the objectTasnifiTuriDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObjectTasnifiTuriDTO> updateObjectTasnifiTuri(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObjectTasnifiTuriDTO objectTasnifiTuriDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ObjectTasnifiTuri : {}, {}", id, objectTasnifiTuriDTO);
        if (objectTasnifiTuriDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objectTasnifiTuriDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objectTasnifiTuriRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObjectTasnifiTuriDTO result = objectTasnifiTuriService.update(objectTasnifiTuriDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectTasnifiTuriDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /object-tasnifi-turis/:id} : Partial updates given fields of an existing objectTasnifiTuri, field will ignore if it is null
     *
     * @param id the id of the objectTasnifiTuriDTO to save.
     * @param objectTasnifiTuriDTO the objectTasnifiTuriDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectTasnifiTuriDTO,
     * or with status {@code 400 (Bad Request)} if the objectTasnifiTuriDTO is not valid,
     * or with status {@code 404 (Not Found)} if the objectTasnifiTuriDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the objectTasnifiTuriDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObjectTasnifiTuriDTO> partialUpdateObjectTasnifiTuri(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObjectTasnifiTuriDTO objectTasnifiTuriDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObjectTasnifiTuri partially : {}, {}", id, objectTasnifiTuriDTO);
        if (objectTasnifiTuriDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objectTasnifiTuriDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objectTasnifiTuriRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObjectTasnifiTuriDTO> result = objectTasnifiTuriService.partialUpdate(objectTasnifiTuriDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectTasnifiTuriDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /object-tasnifi-turis} : get all the objectTasnifiTuris.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of objectTasnifiTuris in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ObjectTasnifiTuriDTO>> getAllObjectTasnifiTuris(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ObjectTasnifiTuris");
        Page<ObjectTasnifiTuriDTO> page = objectTasnifiTuriService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /object-tasnifi-turis/:id} : get the "id" objectTasnifiTuri.
     *
     * @param id the id of the objectTasnifiTuriDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the objectTasnifiTuriDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObjectTasnifiTuriDTO> getObjectTasnifiTuri(@PathVariable("id") Long id) {
        log.debug("REST request to get ObjectTasnifiTuri : {}", id);
        Optional<ObjectTasnifiTuriDTO> objectTasnifiTuriDTO = objectTasnifiTuriService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objectTasnifiTuriDTO);
    }

    /**
     * {@code DELETE  /object-tasnifi-turis/:id} : delete the "id" objectTasnifiTuri.
     *
     * @param id the id of the objectTasnifiTuriDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjectTasnifiTuri(@PathVariable("id") Long id) {
        log.debug("REST request to delete ObjectTasnifiTuri : {}", id);
        objectTasnifiTuriService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
