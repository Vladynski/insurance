package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.selection.CalculateOutDto;
import kp.bahmatov.insurance.domain.dto.selection.SelectionGroupOutDto;
import kp.bahmatov.insurance.domain.dto.selection.SelectionVariantOutDto;
import kp.bahmatov.insurance.domain.dto.selection.update.SelectionVariantUpdateDto;
import kp.bahmatov.insurance.service.SelectionService;
import kp.bahmatov.insurance.service.SettingsService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public long updateVariant(@Valid @RequestBody SelectionVariantUpdateDto updateDto) {
        return selectionService.updateVariant(updateDto);
    }

    @GetMapping("/variant")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SelectionVariantOutDto> getVariants(@RequestParam List<Long> ids) {
        return selectionService.getVariants(ids).stream().map(el -> new SelectionVariantOutDto(0, el)).toList();
    }

    @PutMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public long updateGroup(@RequestParam("id") int groupId, @RequestParam("name") String name) {
        return selectionService.updateGroupName(groupId, name);
    }

    @GetMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SelectionGroupOutDto getGroup(@RequestParam("id") int groupId) {
        return new SelectionGroupOutDto(selectionService.getGroup(groupId));
    }

    @GetMapping
    public CalculateOutDto getAllActive() {
        return new CalculateOutDto(selectionService.getAllActive(), SettingsService.getBaseSum().getFloatValue());
    }

    @DeleteMapping("/variant")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteVariant(@RequestParam("id") long variantId) {
        selectionService.deleteVariant(variantId);
    }

    @DeleteMapping("/group")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGroup(@RequestParam("id") int groupId) {
        selectionService.deleteGroup(groupId);
    }

    @Deprecated
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CalculateOutDto getAll() {
        return new CalculateOutDto(selectionService.getAll(), SettingsService.getBaseSum().getFloatValue());
    }
}
