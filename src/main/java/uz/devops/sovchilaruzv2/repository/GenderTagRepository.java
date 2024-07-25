package uz.devops.sovchilaruzv2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.devops.sovchilaruzv2.domain.GenderTag;

/**
 * Spring Data JPA repository for the GenderTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenderTagRepository extends JpaRepository<GenderTag, UUID> {}
