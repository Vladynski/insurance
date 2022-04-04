package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectionGroupRepo extends CrudRepository<SelectionGroup, Integer> {
    List<SelectionGroup> findAll();
}
