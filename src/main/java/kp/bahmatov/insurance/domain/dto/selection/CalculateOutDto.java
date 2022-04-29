package kp.bahmatov.insurance.domain.dto.selection;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionGroup;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CalculateOutDto {
    private final List<SelectionGroupOutDto> selections;
    private final float baseSum;


    public CalculateOutDto(List<SelectionGroup> all, float baseSum) {
        selections = all.stream().map(SelectionGroupOutDto::new).collect(Collectors.toList());
        this.baseSum = baseSum;
    }
}
