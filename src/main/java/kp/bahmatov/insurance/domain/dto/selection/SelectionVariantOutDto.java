package kp.bahmatov.insurance.domain.dto.selection;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import lombok.Getter;

@Getter
public class SelectionVariantOutDto {
    private final long id;
    private final int groupId;
    private final String name;
    private final float coefficient;

    //Чтобы лишний раз не подгружать группу в selectionVariant передаём его параметром
    public SelectionVariantOutDto(int groupId, SelectionVariant selectionVariant) {
        this.id = selectionVariant.getId();
        this.name = selectionVariant.getName();
        this.coefficient = selectionVariant.getCoefficient();
        this.groupId = groupId;
    }
}
