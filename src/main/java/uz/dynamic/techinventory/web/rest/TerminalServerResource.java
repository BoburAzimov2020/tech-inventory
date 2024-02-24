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

import uz.dynamic.techinventory.repository.TerminalServerRepository;
import uz.dynamic.techinventory.service.TerminalServerService;
import uz.dynamic.techinventory.service.dto.TerminalServerDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.TerminalServer}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/terminal-servers")
public class TerminalServerResource {

    private final Logger log = LoggerFactory.getLogger(TerminalServerResource.class);

    private static final String ENTITY_NAME = "terminalServer";

    @Value("${spring.application.name}")
    private String applicationName;

    private final TerminalServerService terminalServerService;

    private final TerminalServerRepository terminalServerRepository;

    public TerminalServerResource(TerminalServerService terminalServerService, TerminalServerRepository terminalServerRepository) {
        this.terminalServerService = terminalServerService;
        this.terminalServerRepository = terminalServerRepository;
    }

    /**
     * {@code POST  /terminal-servers} : Create a new terminalServer.
     *
     * @param terminalServerDTO the terminalServerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terminalServerDTO, or with status {@code 400 (Bad Request)} if the terminalServer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TerminalServerDTO> createTerminalServer(@Valid @RequestBody TerminalServerDTO terminalServerDTO)
        throws URISyntaxException {
        log.debug("REST request to save TerminalServer : {}", terminalServerDTO);
        if (terminalServerDTO.getId() != null) {
            throw new BadRequestAlertException("A new terminalServer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TerminalServerDTO result = terminalServerService.save(terminalServerDTO);
        return ResponseEntity
            .created(new URI("/api/terminal-servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terminal-servers/:id} : Updates an existing terminalServer.
     *
     * @param id the id of the terminalServerDTO to save.
     * @param terminalServerDTO the terminalServerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminalServerDTO,
     * or with status {@code 400 (Bad Request)} if the terminalServerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terminalServerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TerminalServerDTO> updateTerminalServer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TerminalServerDTO terminalServerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TerminalServer : {}, {}", id, terminalServerDTO);
        if (terminalServerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terminalServerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terminalServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TerminalServerDTO result = terminalServerService.update(terminalServerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, terminalServerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /terminal-servers/:id} : Partial updates given fields of an existing terminalServer, field will ignore if it is null
     *
     * @param id the id of the terminalServerDTO to save.
     * @param terminalServerDTO the terminalServerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminalServerDTO,
     * or with status {@code 400 (Bad Request)} if the terminalServerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the terminalServerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the terminalServerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TerminalServerDTO> partialUpdateTerminalServer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TerminalServerDTO terminalServerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TerminalServer partially : {}, {}", id, terminalServerDTO);
        if (terminalServerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terminalServerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terminalServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TerminalServerDTO> result = terminalServerService.partialUpdate(terminalServerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, terminalServerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /terminal-servers} : get all the terminalServers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terminalServers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TerminalServerDTO>> getAllTerminalServers(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TerminalServers");
        Page<TerminalServerDTO> page = terminalServerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terminal-servers/:id} : get the "id" terminalServer.
     *
     * @param id the id of the terminalServerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terminalServerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TerminalServerDTO> getTerminalServer(@PathVariable("id") Long id) {
        log.debug("REST request to get TerminalServer : {}", id);
        Optional<TerminalServerDTO> terminalServerDTO = terminalServerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(terminalServerDTO);
    }

    /**
     * {@code DELETE  /terminal-servers/:id} : delete the "id" terminalServer.
     *
     * @param id the id of the terminalServerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminalServer(@PathVariable("id") Long id) {
        log.debug("REST request to delete TerminalServer : {}", id);
        terminalServerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
