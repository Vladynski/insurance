package kp.bahmatov.insurance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionGroup;
import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import kp.bahmatov.insurance.repo.SelectionGroupRepo;
import kp.bahmatov.insurance.repo.SelectionVariantRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class SelectionService {
    private final String defaultSelectionJsonFilePath;
    private final SelectionGroupRepo selectionGroupRepo;
    private final SelectionVariantRepo selectionVariantRepo;

    public SelectionService(SelectionGroupRepo selectionGroupRepo,
                            SelectionVariantRepo selectionVariantRepo,
                            @Value("${default.selection.json.file.path}") String filePath) {
        this.defaultSelectionJsonFilePath = filePath;
        this.selectionGroupRepo = selectionGroupRepo;
        this.selectionVariantRepo = selectionVariantRepo;
        fillingBdIfItsEmpty();
    }

    @Deprecated
    //FIXME read and parse to util class
    private void fillingBdIfItsEmpty() {
        if (selectionGroupRepo.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (defaultSelectionJsonFilePath != null) {
                    SelectionGroup[] selectionGroups = objectMapper.readValue(new File(defaultSelectionJsonFilePath), SelectionGroup[].class);
                    List<SelectionGroup> groups = Arrays.asList(selectionGroups);
                    for (SelectionGroup group : groups) {
                        group.getVariants().forEach(var -> var.setGroup(group));
                    }
                    selectionGroupRepo.saveAll(groups);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<SelectionGroup> getAll() {
        return selectionGroupRepo.findAll();
    }

    public List<SelectionVariant> findByIds(Iterable<Integer> ids) {
        return (List<SelectionVariant>) selectionVariantRepo.findAllById(ids);
    }

    public long getGroupCount() {
        return selectionGroupRepo.count();
    }
}
