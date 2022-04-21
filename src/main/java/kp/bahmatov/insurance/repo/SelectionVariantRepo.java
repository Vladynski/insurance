package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelectionVariantRepo extends JpaRepository<SelectionVariant, Long> {
    @Query("SELECT v FROM SelectionVariant v WHERE v.name=?1 AND v.group.id=?2 AND v.coefficient=?3")
    Optional<SelectionVariant> findByNameAndGroupAndCoefficient(String name, int groupId, float coefficient);

    @Deprecated
    @Query("SELECT v from SelectionVariant  v WHERE v.group.id=?1 AND v.name=?2")
    Optional<SelectionVariant> findByGroupIdAndName(int groupId, String name);

    List<SelectionVariant> findAllByIdIn(List<Long> ids);
}
