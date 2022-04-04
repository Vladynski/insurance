package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionVariantRepo extends CrudRepository<SelectionVariant, Integer> {
}
