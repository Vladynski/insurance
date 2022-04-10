package kp.bahmatov.insurance.repo.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private Operation operation;
    private Object value;
    private Condition condition;

    public boolean isOrCondition() {
        return condition == Condition.OR;
    }
}