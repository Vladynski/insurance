package kp.bahmatov.insurance.domain.dto.selection;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionGroup;
import lombok.Data;

import java.util.List;

@Data
public class SelectionGroupOutDto {
    private int id;
    private String name;
    private List<? extends SelectionVariantOutDto> variants;

    public SelectionGroupOutDto(SelectionGroup selectionGroup) {
        this.id = selectionGroup.getId();
        this.name = selectionGroup.getName();
        this.variants = selectionGroup.getVariants().stream().map(el -> new SelectionVariantOutDto(selectionGroup.getId(), el)).toList();
    }
}
