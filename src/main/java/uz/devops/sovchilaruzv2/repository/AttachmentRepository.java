package uz.devops.sovchilaruzv2.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.devops.sovchilaruzv2.domain.Attachment;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findByFileKey(String fileKey);
}
