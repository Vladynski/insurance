package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.selection.CalculateOutDto;
import kp.bahmatov.insurance.domain.dto.selection.SelectionGroupDto;
import kp.bahmatov.insurance.domain.dto.selection.SelectionVariantDto;
import kp.bahmatov.insurance.domain.dto.selection.update.SelectionVariantUpdateDto;
import kp.bahmatov.insurance.service.SelectionService;
import kp.bahmatov.insurance.service.SettingsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("selection")
public class SelectionController {

    private final SelectionService selectionService;

    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @PutMapping("/variant")
    public long updateVariant(@Valid @RequestBody SelectionVariantUpdateDto updateDto) {
        return selectionService.updateVariant(updateDto);
    }

    @GetMapping("/variant")
    public List<SelectionVariantDto> updateVariant(@RequestParam List<Long> ids) {
        return selectionService.getVariants(ids).stream().map(el -> new SelectionVariantDto(0, el)).toList();
    }

    @PutMapping("/group")
    public long updateGroup(@RequestParam("id") int groupId, @RequestParam("name") String name) {
        return selectionService.updateGroupName(groupId, name);
    }

    @GetMapping("/group")
    public SelectionGroupDto updateGroup(@RequestParam("id") int groupId) {
        return new SelectionGroupDto(selectionService.getGroup(groupId));
    }

    @GetMapping
    public CalculateOutDto getAllActive() {
        return new CalculateOutDto(selectionService.getAllActive(), SettingsService.getBaseSum().getFloatValue());
    }

    @DeleteMapping("/variant")
    public void deleteVariant(@RequestParam("id") long variantId) {
        selectionService.deleteVariant(variantId);
    }

    @DeleteMapping("/group")
    public void deleteGroup(@RequestParam("id") int groupId) {
        selectionService.deleteGroup(groupId);
    }

    @Deprecated
    @GetMapping("/all")
    public CalculateOutDto getAll() {
        return new CalculateOutDto(selectionService.getAll(), SettingsService.getBaseSum().getFloatValue());
    }
}
