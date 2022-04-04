package kp.bahmatov.insurance.domain.dto.selection;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionGroup;
import lombok.Data;

import java.util.List;

@Data
//fixme check in
public class SelectionGroupDto {
    private int id;
    private String name;
    private List<? extends SelectionVariantDto> variants;

    public SelectionGroupDto(SelectionGroup selectionGroup) {
        this.id = selectionGroup.getId();
        this.name = selectionGroup.getName();
        this.variants = selectionGroup.getVariants().stream().map(el -> new SelectionVariantDto(selectionGroup.getId(), el)).toList();
    }
}
