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
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uz.dynamic.techinventory.repository.AttachmentRepository;
import uz.dynamic.techinventory.service.AttachmentService;
import uz.dynamic.techinventory.service.dto.AttachmentDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.Attachment}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/attachments")
public class AttachmentResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

    private static final String ENTITY_NAME = "attachment";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AttachmentService attachmentService;

    private final AttachmentRepository attachmentRepository;

    public AttachmentResource(AttachmentService attachmentService, AttachmentRepository attachmentRepository) {
        this.attachmentService = attachmentService;
        this.attachmentRepository = attachmentRepository;
    }

    /**
     * {@code POST  /attachments} : Create a new attachment.
     *
     * @param attachmentDTO the attachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attachmentDTO, or with status {@code 400 (Bad Request)} if the attachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AttachmentDTO> createAttachment(@Valid @RequestBody AttachmentDTO attachmentDTO) throws URISyntaxException {
        log.debug("REST request to save Attachment : {}", attachmentDTO);
        if (attachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentDTO result = attachmentService.save(attachmentDTO);
        return ResponseEntity
            .created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attachments/:id} : Updates an existing attachment.
     *
     * @param id the id of the attachmentDTO to save.
     * @param attachmentDTO the attachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attachmentDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttachmentDTO> updateAttachment(
        @PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody AttachmentDTO attachmentDTO) {
        log.debug("REST request to update Attachment : {}, {}", id, attachmentDTO);
        if (attachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttachmentDTO result = attachmentService.update(attachmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attachments/:id} : Partial updates given fields of an existing attachment, field will ignore if it is null
     *
     * @param id the id of the attachmentDTO to save.
     * @param attachmentDTO the attachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attachmentDTO,
     * or with status {@code 400 (Bad Request)} if the attachmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attachmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attachmentDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttachmentDTO> partialUpdateAttachment(
        @PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody AttachmentDTO attachmentDTO) {
        log.debug("REST request to partial update Attachment partially : {}, {}", id, attachmentDTO);
        if (attachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttachmentDTO> result = attachmentService.partialUpdate(attachmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attachmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attachments} : get all the attachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Attachments");
        Page<AttachmentDTO> page = attachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachments/obyekt/:obyektId} : get all the attachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attachments in body.
     */
    @GetMapping("/obyekt/{obyektId}")
    public ResponseEntity<List<AttachmentDTO>> getAllByObyekt(@ParameterObject Pageable pageable,
                                                              @PathVariable("obyektId") Long obyektId) {
        log.debug("REST request to get a page of Attachments");
        Page<AttachmentDTO> page = attachmentService.findAllByObyekt(pageable, obyektId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attachments/:id} : get the "id" attachment.
     *
     * @param id the id of the attachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable("id") Long id) {
        log.debug("REST request to get Attachment : {}", id);
        Optional<AttachmentDTO> attachmentDTO = attachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentDTO);
    }

    /**
     * {@code DELETE  /attachments/:id} : delete the "id" attachment.
     *
     * @param id the id of the attachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Attachment : {}", id);
        attachmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
