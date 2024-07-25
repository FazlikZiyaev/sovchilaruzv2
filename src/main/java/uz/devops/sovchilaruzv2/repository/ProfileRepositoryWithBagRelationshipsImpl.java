package uz.devops.sovchilaruzv2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uz.devops.sovchilaruzv2.domain.Profile;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProfileRepositoryWithBagRelationshipsImpl implements ProfileRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Profile> fetchBagRelationships(Optional<Profile> profile) {
        return profile.map(this::fetchGenderTags);
    }

    @Override
    public Page<Profile> fetchBagRelationships(Page<Profile> profiles) {
        return new PageImpl<>(fetchBagRelationships(profiles.getContent()), profiles.getPageable(), profiles.getTotalElements());
    }

    @Override
    public List<Profile> fetchBagRelationships(List<Profile> profiles) {
        return Optional.of(profiles).map(this::fetchGenderTags).orElse(Collections.emptyList());
    }

    Profile fetchGenderTags(Profile result) {
        return entityManager
            .createQuery("select profile from Profile profile left join fetch profile.genderTags where profile.id = :id", Profile.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Profile> fetchGenderTags(List<Profile> profiles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, profiles.size()).forEach(index -> order.put(profiles.get(index).getId(), index));
        List<Profile> result = entityManager
            .createQuery("select profile from Profile profile left join fetch profile.genderTags where profile in :profiles", Profile.class)
            .setParameter("profiles", profiles)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
