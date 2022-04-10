package kp.bahmatov.insurance.repo.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings({"unchecked"})
public class SpecificationBuilder<T> {
    private final List<SearchCriteria> params;
    private final Function<SearchCriteria, StructureSpecification<T>> mapper;

    public SpecificationBuilder(Function<SearchCriteria, StructureSpecification<T>> mapper) {
        this.mapper = mapper;
        params = new ArrayList<>();
    }

    public SpecificationBuilder<T> with(String key, Object value) {
        return with(key, Operation.EQUALS, value, Condition.AND);
    }

    public SpecificationBuilder<T> with(String key, Operation operation, Object value) {
        return with(key, operation, value, Condition.AND);
    }

    public SpecificationBuilder<T> with(String key, Operation operation, Object value, Condition condition) {
        params.add(new SearchCriteria(key, operation, value, condition));
        return this;
    }

    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<StructureSpecification<T>> specs = params.stream().map(mapper).toList();

        Specification<T> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrCondition()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
