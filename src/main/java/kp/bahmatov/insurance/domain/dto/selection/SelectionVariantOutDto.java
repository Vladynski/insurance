package kp.bahmatov.insurance.domain.dto.selection;

import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import lombok.Getter;

@Getter
public class SelectionVariantOutDto {
    private long id;
    private int groupId;
    private String name;
    private float coefficient;

    //Чтобы лишний раз не подгружать группу в selectionVariant передаём его параметром
    public SelectionVariantOutDto(int groupId, SelectionVariant selectionVariant) {
        this.id = selectionVariant.getId();
        this.name = selectionVariant.getName();
        this.coefficient = selectionVariant.getCoefficient();
        this.groupId = groupId;
    }
}
