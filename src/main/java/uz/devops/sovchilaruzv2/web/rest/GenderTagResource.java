package uz.devops.sovchilaruzv2.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
import uz.devops.sovchilaruzv2.repository.GenderTagRepository;
import uz.devops.sovchilaruzv2.service.GenderTagService;
import uz.devops.sovchilaruzv2.service.dto.GenderTagDTO;
import uz.devops.sovchilaruzv2.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.devops.sovchilaruzv2.domain.GenderTag}.
 */
@RestController
@RequestMapping("/api/gender-tags")
public class GenderTagResource {

    private final Logger log = LoggerFactory.getLogger(GenderTagResource.class);

    private static final String ENTITY_NAME = "genderTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenderTagService genderTagService;

    private final GenderTagRepository genderTagRepository;

    public GenderTagResource(GenderTagService genderTagService, GenderTagRepository genderTagRepository) {
        this.genderTagService = genderTagService;
        this.genderTagRepository = genderTagRepository;
    }

    /**
     * {@code POST  /gender-tags} : Create a new genderTag.
     *
     * @param genderTagDTO the genderTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genderTagDTO, or with status {@code 400 (Bad Request)} if the genderTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GenderTagDTO> createGenderTag(@Valid @RequestBody GenderTagDTO genderTagDTO) throws URISyntaxException {
        log.debug("REST request to save GenderTag : {}", genderTagDTO);
        if (genderTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new genderTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenderTagDTO result = genderTagService.save(genderTagDTO);
        return ResponseEntity
            .created(new URI("/api/gender-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gender-tags/:id} : Updates an existing genderTag.
     *
     * @param id the id of the genderTagDTO to save.
     * @param genderTagDTO the genderTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genderTagDTO,
     * or with status {@code 400 (Bad Request)} if the genderTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genderTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GenderTagDTO> updateGenderTag(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody GenderTagDTO genderTagDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GenderTag : {}, {}", id, genderTagDTO);
        if (genderTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genderTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genderTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GenderTagDTO result = genderTagService.update(genderTagDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, genderTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gender-tags/:id} : Partial updates given fields of an existing genderTag, field will ignore if it is null
     *
     * @param id the id of the genderTagDTO to save.
     * @param genderTagDTO the genderTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genderTagDTO,
     * or with status {@code 400 (Bad Request)} if the genderTagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the genderTagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the genderTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GenderTagDTO> partialUpdateGenderTag(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody GenderTagDTO genderTagDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GenderTag partially : {}, {}", id, genderTagDTO);
        if (genderTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genderTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genderTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GenderTagDTO> result = genderTagService.partialUpdate(genderTagDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, genderTagDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gender-tags} : get all the genderTags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genderTags in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GenderTagDTO>> getAllGenderTags(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GenderTags");
        Page<GenderTagDTO> page = genderTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gender-tags/:id} : get the "id" genderTag.
     *
     * @param id the id of the genderTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genderTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenderTagDTO> getGenderTag(@PathVariable UUID id) {
        log.debug("REST request to get GenderTag : {}", id);
        Optional<GenderTagDTO> genderTagDTO = genderTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genderTagDTO);
    }

    /**
     * {@code DELETE  /gender-tags/:id} : delete the "id" genderTag.
     *
     * @param id the id of the genderTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenderTag(@PathVariable UUID id) {
        log.debug("REST request to delete GenderTag : {}", id);
        genderTagService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
