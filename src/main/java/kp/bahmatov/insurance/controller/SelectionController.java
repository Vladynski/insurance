package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.selection.CalculateOutDto;
import kp.bahmatov.insurance.service.SelectionService;
import kp.bahmatov.insurance.service.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("selection")
public class SelectionController {

    private final SelectionService selectionService;

    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }


    @GetMapping
    public CalculateOutDto getAll() {
        return new CalculateOutDto(selectionService.getAll(), SettingsService.getBaseSum().getFloatValue());
    }
}
