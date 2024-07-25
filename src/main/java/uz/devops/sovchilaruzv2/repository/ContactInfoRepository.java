package uz.devops.sovchilaruzv2.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.devops.sovchilaruzv2.domain.ContactInfo;

/**
 * Spring Data JPA repository for the ContactInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, UUID> {}
