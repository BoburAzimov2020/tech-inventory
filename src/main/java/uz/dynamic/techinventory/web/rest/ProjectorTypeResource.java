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
import uz.dynamic.techinventory.repository.ProjectorTypeRepository;
import uz.dynamic.techinventory.service.ProjectorTypeService;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.ProjectorType}.
 */
@RestController
@RequestMapping("/api/projector-types")
public class ProjectorTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProjectorTypeResource.class);

    private static final String ENTITY_NAME = "projectorType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectorTypeService projectorTypeService;

    private final ProjectorTypeRepository projectorTypeRepository;

    public ProjectorTypeResource(ProjectorTypeService projectorTypeService, ProjectorTypeRepository projectorTypeRepository) {
        this.projectorTypeService = projectorTypeService;
        this.projectorTypeRepository = projectorTypeRepository;
    }

    /**
     * {@code POST  /projector-types} : Create a new projectorType.
     *
     * @param projectorTypeDTO the projectorTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectorTypeDTO, or with status {@code 400 (Bad Request)} if the projectorType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProjectorTypeDTO> createProjectorType(@Valid @RequestBody ProjectorTypeDTO projectorTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectorType : {}", projectorTypeDTO);
        if (projectorTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectorType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectorTypeDTO result = projectorTypeService.save(projectorTypeDTO);
        return ResponseEntity
            .created(new URI("/api/projector-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projector-types/:id} : Updates an existing projectorType.
     *
     * @param id the id of the projectorTypeDTO to save.
     * @param projectorTypeDTO the projectorTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectorTypeDTO,
     * or with status {@code 400 (Bad Request)} if the projectorTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectorTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectorTypeDTO> updateProjectorType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectorTypeDTO projectorTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectorType : {}, {}", id, projectorTypeDTO);
        if (projectorTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectorTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectorTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectorTypeDTO result = projectorTypeService.update(projectorTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectorTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /projector-types/:id} : Partial updates given fields of an existing projectorType, field will ignore if it is null
     *
     * @param id the id of the projectorTypeDTO to save.
     * @param projectorTypeDTO the projectorTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectorTypeDTO,
     * or with status {@code 400 (Bad Request)} if the projectorTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectorTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectorTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectorTypeDTO> partialUpdateProjectorType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectorTypeDTO projectorTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectorType partially : {}, {}", id, projectorTypeDTO);
        if (projectorTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectorTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectorTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectorTypeDTO> result = projectorTypeService.partialUpdate(projectorTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectorTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /projector-types} : get all the projectorTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectorTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProjectorTypeDTO>> getAllProjectorTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProjectorTypes");
        Page<ProjectorTypeDTO> page = projectorTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projector-types/:id} : get the "id" projectorType.
     *
     * @param id the id of the projectorTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectorTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectorTypeDTO> getProjectorType(@PathVariable("id") Long id) {
        log.debug("REST request to get ProjectorType : {}", id);
        Optional<ProjectorTypeDTO> projectorTypeDTO = projectorTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectorTypeDTO);
    }

    /**
     * {@code DELETE  /projector-types/:id} : delete the "id" projectorType.
     *
     * @param id the id of the projectorTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectorType(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProjectorType : {}", id);
        projectorTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
