package uz.devops.sovchilaruzv2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.devops.sovchilaruzv2.domain.ProfileDiscoverability;

/**
 * Spring Data JPA repository for the ProfileDiscoverability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileDiscoverabilityRepository extends JpaRepository<ProfileDiscoverability, UUID> {}
