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

import uz.dynamic.techinventory.repository.CabelTypeRepository;
import uz.dynamic.techinventory.service.CabelTypeService;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.CabelType}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/cabel-types")
public class CabelTypeResource {

    private final Logger log = LoggerFactory.getLogger(CabelTypeResource.class);

    private static final String ENTITY_NAME = "cabelType";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CabelTypeService cabelTypeService;

    private final CabelTypeRepository cabelTypeRepository;

    public CabelTypeResource(CabelTypeService cabelTypeService, CabelTypeRepository cabelTypeRepository) {
        this.cabelTypeService = cabelTypeService;
        this.cabelTypeRepository = cabelTypeRepository;
    }

    /**
     * {@code POST  /cabel-types} : Create a new cabelType.
     *
     * @param cabelTypeDTO the cabelTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cabelTypeDTO, or with status {@code 400 (Bad Request)} if the cabelType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CabelTypeDTO> createCabelType(
            @Valid @RequestBody CabelTypeDTO cabelTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CabelType : {}", cabelTypeDTO);
        if (cabelTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cabelType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CabelTypeDTO result = cabelTypeService.save(cabelTypeDTO);
        return ResponseEntity
                .created(new URI("/api/cabel-types/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                                                              result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /cabel-types/:id} : Updates an existing cabelType.
     *
     * @param id           the id of the cabelTypeDTO to save.
     * @param cabelTypeDTO the cabelTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cabelTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cabelTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cabelTypeDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CabelTypeDTO> updateCabelType(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody CabelTypeDTO cabelTypeDTO) {
        log.debug("REST request to update CabelType : {}, {}", id, cabelTypeDTO);
        if (cabelTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cabelTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cabelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CabelTypeDTO result = cabelTypeService.update(cabelTypeDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                                                            cabelTypeDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /cabel-types/:id} : Partial updates given fields of an existing cabelType, field will ignore if it is null
     *
     * @param id           the id of the cabelTypeDTO to save.
     * @param cabelTypeDTO the cabelTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cabelTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cabelTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cabelTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cabelTypeDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<CabelTypeDTO> partialUpdateCabelType(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody CabelTypeDTO cabelTypeDTO) {
        log.debug("REST request to partial update CabelType partially : {}, {}", id, cabelTypeDTO);
        if (cabelTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cabelTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cabelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CabelTypeDTO> result = cabelTypeService.partialUpdate(cabelTypeDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cabelTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cabel-types} : get all the cabelTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cabelTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CabelTypeDTO>> getAllCabelTypes(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CabelTypes");
        Page<CabelTypeDTO> page = cabelTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cabel-types/:id} : get the "id" cabelType.
     *
     * @param id the id of the cabelTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cabelTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CabelTypeDTO> getCabelType(@PathVariable("id") Long id) {
        log.debug("REST request to get CabelType : {}", id);
        Optional<CabelTypeDTO> cabelTypeDTO = cabelTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cabelTypeDTO);
    }

    /**
     * {@code DELETE  /cabel-types/:id} : delete the "id" cabelType.
     *
     * @param id the id of the cabelTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCabelType(@PathVariable("id") Long id) {
        log.debug("REST request to delete CabelType : {}", id);
        cabelTypeService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
