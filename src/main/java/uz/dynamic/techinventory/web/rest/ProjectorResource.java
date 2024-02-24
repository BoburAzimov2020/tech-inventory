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

import uz.dynamic.techinventory.repository.ProjectorRepository;
import uz.dynamic.techinventory.service.ProjectorService;
import uz.dynamic.techinventory.service.dto.ProjectorDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Projector}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/projectors")
public class ProjectorResource {

    private final Logger log = LoggerFactory.getLogger(ProjectorResource.class);

    private static final String ENTITY_NAME = "projector";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ProjectorService projectorService;

    private final ProjectorRepository projectorRepository;

    public ProjectorResource(ProjectorService projectorService, ProjectorRepository projectorRepository) {
        this.projectorService = projectorService;
        this.projectorRepository = projectorRepository;
    }

    /**
     * {@code POST  /projectors} : Create a new projector.
     *
     * @param projectorDTO the projectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectorDTO, or with status {@code 400 (Bad Request)} if the projector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProjectorDTO> createProjector(@Valid @RequestBody ProjectorDTO projectorDTO) throws URISyntaxException {
        log.debug("REST request to save Projector : {}", projectorDTO);
        if (projectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new projector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectorDTO result = projectorService.save(projectorDTO);
        return ResponseEntity
            .created(new URI("/api/projectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projectors/:id} : Updates an existing projector.
     *
     * @param id the id of the projectorDTO to save.
     * @param projectorDTO the projectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectorDTO,
     * or with status {@code 400 (Bad Request)} if the projectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectorDTO> updateProjector(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectorDTO projectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Projector : {}, {}", id, projectorDTO);
        if (projectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectorDTO result = projectorService.update(projectorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /projectors/:id} : Partial updates given fields of an existing projector, field will ignore if it is null
     *
     * @param id the id of the projectorDTO to save.
     * @param projectorDTO the projectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectorDTO,
     * or with status {@code 400 (Bad Request)} if the projectorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectorDTO> partialUpdateProjector(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectorDTO projectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projector partially : {}, {}", id, projectorDTO);
        if (projectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectorDTO> result = projectorService.partialUpdate(projectorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /projectors} : get all the projectors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProjectorDTO>> getAllProjectors(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Projectors");
        Page<ProjectorDTO> page = projectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projectors/:id} : get the "id" projector.
     *
     * @param id the id of the projectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectorDTO> getProjector(@PathVariable("id") Long id) {
        log.debug("REST request to get Projector : {}", id);
        Optional<ProjectorDTO> projectorDTO = projectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectorDTO);
    }

    /**
     * {@code DELETE  /projectors/:id} : delete the "id" projector.
     *
     * @param id the id of the projectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjector(@PathVariable("id") Long id) {
        log.debug("REST request to delete Projector : {}", id);
        projectorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
