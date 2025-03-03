package uz.devops.sovchilaruzv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.devops.sovchilaruzv2.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
