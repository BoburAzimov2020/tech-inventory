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

import uz.dynamic.techinventory.repository.ObjectTasnifiRepository;
import uz.dynamic.techinventory.service.ObjectTasnifiService;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.ObjectTasnifi}.
 */
@RestController
@RequestMapping("/api/object-tasnifis")
public class ObjectTasnifiResource {

    private final Logger log = LoggerFactory.getLogger(ObjectTasnifiResource.class);

    private static final String ENTITY_NAME = "objectTasnifi";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ObjectTasnifiService objectTasnifiService;

    private final ObjectTasnifiRepository objectTasnifiRepository;

    public ObjectTasnifiResource(ObjectTasnifiService objectTasnifiService, ObjectTasnifiRepository objectTasnifiRepository) {
        this.objectTasnifiService = objectTasnifiService;
        this.objectTasnifiRepository = objectTasnifiRepository;
    }

    /**
     * {@code POST  /object-tasnifis} : Create a new objectTasnifi.
     *
     * @param objectTasnifiDTO the objectTasnifiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new objectTasnifiDTO, or with status {@code 400 (Bad Request)} if the objectTasnifi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ObjectTasnifiDTO> createObjectTasnifi(@Valid @RequestBody ObjectTasnifiDTO objectTasnifiDTO)
        throws URISyntaxException {
        log.debug("REST request to save ObjectTasnifi : {}", objectTasnifiDTO);
        if (objectTasnifiDTO.getId() != null) {
            throw new BadRequestAlertException("A new objectTasnifi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObjectTasnifiDTO result = objectTasnifiService.save(objectTasnifiDTO);
        return ResponseEntity
            .created(new URI("/api/object-tasnifis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /object-tasnifis/:id} : Updates an existing objectTasnifi.
     *
     * @param id the id of the objectTasnifiDTO to save.
     * @param objectTasnifiDTO the objectTasnifiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectTasnifiDTO,
     * or with status {@code 400 (Bad Request)} if the objectTasnifiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the objectTasnifiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObjectTasnifiDTO> updateObjectTasnifi(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObjectTasnifiDTO objectTasnifiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ObjectTasnifi : {}, {}", id, objectTasnifiDTO);
        if (objectTasnifiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objectTasnifiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objectTasnifiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObjectTasnifiDTO result = objectTasnifiService.update(objectTasnifiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectTasnifiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /object-tasnifis/:id} : Partial updates given fields of an existing objectTasnifi, field will ignore if it is null
     *
     * @param id the id of the objectTasnifiDTO to save.
     * @param objectTasnifiDTO the objectTasnifiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objectTasnifiDTO,
     * or with status {@code 400 (Bad Request)} if the objectTasnifiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the objectTasnifiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the objectTasnifiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObjectTasnifiDTO> partialUpdateObjectTasnifi(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObjectTasnifiDTO objectTasnifiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObjectTasnifi partially : {}, {}", id, objectTasnifiDTO);
        if (objectTasnifiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objectTasnifiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objectTasnifiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObjectTasnifiDTO> result = objectTasnifiService.partialUpdate(objectTasnifiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectTasnifiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /object-tasnifis} : get all the objectTasnifis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of objectTasnifis in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ObjectTasnifiDTO>> getAllObjectTasnifis(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ObjectTasnifis");
        Page<ObjectTasnifiDTO> page = objectTasnifiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /object-tasnifis/:id} : get the "id" objectTasnifi.
     *
     * @param id the id of the objectTasnifiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the objectTasnifiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObjectTasnifiDTO> getObjectTasnifi(@PathVariable("id") Long id) {
        log.debug("REST request to get ObjectTasnifi : {}", id);
        Optional<ObjectTasnifiDTO> objectTasnifiDTO = objectTasnifiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objectTasnifiDTO);
    }

    /**
     * {@code DELETE  /object-tasnifis/:id} : delete the "id" objectTasnifi.
     *
     * @param id the id of the objectTasnifiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjectTasnifi(@PathVariable("id") Long id) {
        log.debug("REST request to delete ObjectTasnifi : {}", id);
        objectTasnifiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
