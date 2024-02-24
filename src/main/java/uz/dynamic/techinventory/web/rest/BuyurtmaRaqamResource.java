package uz.dynamic.techinventory.web.rest;

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

import uz.dynamic.techinventory.repository.BuyurtmaRaqamRepository;
import uz.dynamic.techinventory.service.BuyurtmaRaqamService;
import uz.dynamic.techinventory.service.dto.BuyurtmaRaqamDTO;
import uz.dynamic.techinventory.web.rest.errors.BadRequestAlertException;
import uz.dynamic.techinventory.web.rest.utils.HeaderUtil;
import uz.dynamic.techinventory.web.rest.utils.PaginationUtil;
import uz.dynamic.techinventory.web.rest.utils.ResponseUtil;
import org.springdoc.api.annotations.ParameterObject;

/**
 * REST controller for managing {@link uz.dynamic.techinventory.domain.BuyurtmaRaqam}.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/buyurtma-raqams")
public class BuyurtmaRaqamResource {

    private final Logger log = LoggerFactory.getLogger(BuyurtmaRaqamResource.class);

    private static final String ENTITY_NAME = "buyurtmaRaqam";

    @Value("${spring.application.name}")
    private String applicationName;

    private final BuyurtmaRaqamService buyurtmaRaqamService;

    private final BuyurtmaRaqamRepository buyurtmaRaqamRepository;

    public BuyurtmaRaqamResource(BuyurtmaRaqamService buyurtmaRaqamService, BuyurtmaRaqamRepository buyurtmaRaqamRepository) {
        this.buyurtmaRaqamService = buyurtmaRaqamService;
        this.buyurtmaRaqamRepository = buyurtmaRaqamRepository;
    }

    /**
     * {@code POST  /buyurtma-raqams} : Create a new buyurtmaRaqam.
     *
     * @param buyurtmaRaqamDTO the buyurtmaRaqamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyurtmaRaqamDTO, or with status {@code 400 (Bad Request)} if the buyurtmaRaqam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BuyurtmaRaqamDTO> createBuyurtmaRaqam(@RequestBody BuyurtmaRaqamDTO buyurtmaRaqamDTO) throws URISyntaxException {
        log.debug("REST request to save BuyurtmaRaqam : {}", buyurtmaRaqamDTO);
        if (buyurtmaRaqamDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyurtmaRaqam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyurtmaRaqamDTO result = buyurtmaRaqamService.save(buyurtmaRaqamDTO);
        return ResponseEntity
            .created(new URI("/api/buyurtma-raqams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buyurtma-raqams/:id} : Updates an existing buyurtmaRaqam.
     *
     * @param id the id of the buyurtmaRaqamDTO to save.
     * @param buyurtmaRaqamDTO the buyurtmaRaqamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyurtmaRaqamDTO,
     * or with status {@code 400 (Bad Request)} if the buyurtmaRaqamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyurtmaRaqamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BuyurtmaRaqamDTO> updateBuyurtmaRaqam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuyurtmaRaqamDTO buyurtmaRaqamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BuyurtmaRaqam : {}, {}", id, buyurtmaRaqamDTO);
        if (buyurtmaRaqamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyurtmaRaqamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyurtmaRaqamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuyurtmaRaqamDTO result = buyurtmaRaqamService.update(buyurtmaRaqamDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buyurtmaRaqamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buyurtma-raqams/:id} : Partial updates given fields of an existing buyurtmaRaqam, field will ignore if it is null
     *
     * @param id the id of the buyurtmaRaqamDTO to save.
     * @param buyurtmaRaqamDTO the buyurtmaRaqamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyurtmaRaqamDTO,
     * or with status {@code 400 (Bad Request)} if the buyurtmaRaqamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buyurtmaRaqamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyurtmaRaqamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyurtmaRaqamDTO> partialUpdateBuyurtmaRaqam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuyurtmaRaqamDTO buyurtmaRaqamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuyurtmaRaqam partially : {}, {}", id, buyurtmaRaqamDTO);
        if (buyurtmaRaqamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyurtmaRaqamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyurtmaRaqamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyurtmaRaqamDTO> result = buyurtmaRaqamService.partialUpdate(buyurtmaRaqamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buyurtmaRaqamDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /buyurtma-raqams} : get all the buyurtmaRaqams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyurtmaRaqams in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BuyurtmaRaqamDTO>> getAllBuyurtmaRaqams(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BuyurtmaRaqams");
        Page<BuyurtmaRaqamDTO> page = buyurtmaRaqamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buyurtma-raqams/:id} : get the "id" buyurtmaRaqam.
     *
     * @param id the id of the buyurtmaRaqamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyurtmaRaqamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuyurtmaRaqamDTO> getBuyurtmaRaqam(@PathVariable("id") Long id) {
        log.debug("REST request to get BuyurtmaRaqam : {}", id);
        Optional<BuyurtmaRaqamDTO> buyurtmaRaqamDTO = buyurtmaRaqamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyurtmaRaqamDTO);
    }

    /**
     * {@code DELETE  /buyurtma-raqams/:id} : delete the "id" buyurtmaRaqam.
     *
     * @param id the id of the buyurtmaRaqamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyurtmaRaqam(@PathVariable("id") Long id) {
        log.debug("REST request to delete BuyurtmaRaqam : {}", id);
        buyurtmaRaqamService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
