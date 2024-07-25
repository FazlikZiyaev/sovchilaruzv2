package uz.devops.sovchilaruzv2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.devops.sovchilaruzv2.domain.Nationality;

/**
 * Spring Data JPA repository for the Nationality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationalityRepository extends JpaRepository<Nationality, UUID> {}
