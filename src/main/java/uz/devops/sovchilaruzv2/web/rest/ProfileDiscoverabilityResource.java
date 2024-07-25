package uz.devops.sovchilaruzv2.web.rest;

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
import uz.devops.sovchilaruzv2.repository.ProfileDiscoverabilityRepository;
import uz.devops.sovchilaruzv2.service.ProfileDiscoverabilityService;
import uz.devops.sovchilaruzv2.service.dto.ProfileDiscoverabilityDTO;
import uz.devops.sovchilaruzv2.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.devops.sovchilaruzv2.domain.ProfileDiscoverability}.
 */
@RestController
@RequestMapping("/api/profile-discoverabilities")
public class ProfileDiscoverabilityResource {

    private final Logger log = LoggerFactory.getLogger(ProfileDiscoverabilityResource.class);

    private static final String ENTITY_NAME = "profileDiscoverability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileDiscoverabilityService profileDiscoverabilityService;

    private final ProfileDiscoverabilityRepository profileDiscoverabilityRepository;

    public ProfileDiscoverabilityResource(
        ProfileDiscoverabilityService profileDiscoverabilityService,
        ProfileDiscoverabilityRepository profileDiscoverabilityRepository
    ) {
        this.profileDiscoverabilityService = profileDiscoverabilityService;
        this.profileDiscoverabilityRepository = profileDiscoverabilityRepository;
    }

    /**
     * {@code POST  /profile-discoverabilities} : Create a new profileDiscoverability.
     *
     * @param profileDiscoverabilityDTO the profileDiscoverabilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileDiscoverabilityDTO, or with status {@code 400 (Bad Request)} if the profileDiscoverability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProfileDiscoverabilityDTO> createProfileDiscoverability(
        @Valid @RequestBody ProfileDiscoverabilityDTO profileDiscoverabilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProfileDiscoverability : {}", profileDiscoverabilityDTO);
        if (profileDiscoverabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new profileDiscoverability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileDiscoverabilityDTO result = profileDiscoverabilityService.save(profileDiscoverabilityDTO);
        return ResponseEntity
            .created(new URI("/api/profile-discoverabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profile-discoverabilities/:id} : Updates an existing profileDiscoverability.
     *
     * @param id the id of the profileDiscoverabilityDTO to save.
     * @param profileDiscoverabilityDTO the profileDiscoverabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDiscoverabilityDTO,
     * or with status {@code 400 (Bad Request)} if the profileDiscoverabilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileDiscoverabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDiscoverabilityDTO> updateProfileDiscoverability(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProfileDiscoverabilityDTO profileDiscoverabilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProfileDiscoverability : {}, {}", id, profileDiscoverabilityDTO);
        if (profileDiscoverabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileDiscoverabilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileDiscoverabilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProfileDiscoverabilityDTO result = profileDiscoverabilityService.update(profileDiscoverabilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileDiscoverabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profile-discoverabilities/:id} : Partial updates given fields of an existing profileDiscoverability, field will ignore if it is null
     *
     * @param id the id of the profileDiscoverabilityDTO to save.
     * @param profileDiscoverabilityDTO the profileDiscoverabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDiscoverabilityDTO,
     * or with status {@code 400 (Bad Request)} if the profileDiscoverabilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profileDiscoverabilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profileDiscoverabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfileDiscoverabilityDTO> partialUpdateProfileDiscoverability(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProfileDiscoverabilityDTO profileDiscoverabilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProfileDiscoverability partially : {}, {}", id, profileDiscoverabilityDTO);
        if (profileDiscoverabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileDiscoverabilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileDiscoverabilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfileDiscoverabilityDTO> result = profileDiscoverabilityService.partialUpdate(profileDiscoverabilityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileDiscoverabilityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /profile-discoverabilities} : get all the profileDiscoverabilities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profileDiscoverabilities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProfileDiscoverabilityDTO>> getAllProfileDiscoverabilities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProfileDiscoverabilities");
        Page<ProfileDiscoverabilityDTO> page = profileDiscoverabilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profile-discoverabilities/:id} : get the "id" profileDiscoverability.
     *
     * @param id the id of the profileDiscoverabilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileDiscoverabilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDiscoverabilityDTO> getProfileDiscoverability(@PathVariable Long id) {
        log.debug("REST request to get ProfileDiscoverability : {}", id);
        Optional<ProfileDiscoverabilityDTO> profileDiscoverabilityDTO = profileDiscoverabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileDiscoverabilityDTO);
    }

    /**
     * {@code DELETE  /profile-discoverabilities/:id} : delete the "id" profileDiscoverability.
     *
     * @param id the id of the profileDiscoverabilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileDiscoverability(@PathVariable Long id) {
        log.debug("REST request to delete ProfileDiscoverability : {}", id);
        profileDiscoverabilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
