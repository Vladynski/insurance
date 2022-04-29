package kp.bahmatov.insurance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kp.bahmatov.insurance.domain.dto.selection.update.SelectionVariantUpdateDto;
import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionGroup;
import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.exceptions.NotFoundException;
import kp.bahmatov.insurance.repo.SelectionGroupRepo;
import kp.bahmatov.insurance.repo.SelectionVariantRepo;
import kp.bahmatov.insurance.util.DefaultStructureJsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class SelectionService {
    private static final Logger logger = LoggerFactory.getLogger(SelectionService.class);

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

    private void fillingBdIfItsEmpty() {
        if (selectionGroupRepo.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (defaultSelectionJsonFilePath != null) {
                    SelectionGroup[] selectionGroups = DefaultStructureJsonParser.parse(defaultSelectionJsonFilePath, SelectionGroup[].class);
                    List<SelectionGroup> groups = Arrays.asList(selectionGroups);
                    for (SelectionGroup group : groups) {
                        group.getVariants().forEach(var -> var.setGroup(group));
                    }
                    selectionGroupRepo.saveAll(groups);
                }
            } catch (IOException e) {
                logger.error("Не удалось загрузить стандартные (стартовые) варианты выбора");
                throw new RuntimeException(e);
            }
        }
    }

    public List<SelectionGroup> getAll() {
        return selectionGroupRepo.findAll();
    }

    public List<SelectionGroup> getAllActive() {
        List<SelectionGroup> all = selectionGroupRepo.findAll();
        all.removeIf(el -> !el.isActive());
        all.forEach((el) -> el.getVariants().removeIf(variant -> !variant.isActive()));
        return all;
    }

    public List<SelectionVariant> findByIds(Iterable<Long> ids) {
        return selectionVariantRepo.findAllById(ids);
    }

    public long getGroupCount() {
        return selectionGroupRepo.count();
    }


    /**
     * <p>Метод обновляет варианты условий страховки, но при редактировании варианты не изменяются и не удаляются,
     * а помечаются как неактивные, (такие варианты нельзя будет выбрать при оформлении страховки), такое поведение
     * связано с тем что уже оформленные страховки ссылаются на эти варианты, и в случае изменения записи получается что
     * заказчик оформил страховку по одним условиям, а после их редактирования условия поменялись. Поэтому если в БД
     * есть объект, который соответствует всем параметрам редактирования, уже существует в БД, то вместо создания нового
     * объекта, активируется старый, такой подход помогает избегать копий в таблице. А <u>редактируемый</u> объект
     * не изменяется или удаляется, а <u>помечается как неактивный</u>.</p>
     * <p>
     * Это так же работает при создании нового экземпляра ({@link SelectionService#createVariant}), если в БД уже есть идентичная запись то новая создана не будет,
     * вместо этого активируется уже существующая.
     * </p>
     *
     * @param updateDto если id=0, то будет создаваться новый экземпляр, если id > 0, будет обновляться старый
     * @throws BadRequestException в случае если id > 0 и запись с таким ID не найдена в БД, в случае, если
     *                             при создании объекта (id=0) не переданы основные данные (name, coefficient, groupId) ({@link SelectionService#createVariant})
     */
    public long updateVariant(SelectionVariantUpdateDto updateDto) {
        SelectionVariant editObject = updateDto.getId() == 0 ? null : findVariantById(updateDto.getId());

        //если editObject != null это значит что мы редактируем объект, а при редактировании мы передаём только те поля,
        //которые хотим изменить, например, если мы хотим изменить только coefficient, то name будет равен null,
        //но т.к. мы создаём новый объект по DTO, то DTO должен иметь всю информацию
        if (editObject != null) {
            //т.к. мы либо создадим новый, либо активируем другой вариант, редактируемый мы деактивируем
            editObject.setActive(false);
            selectionVariantRepo.save(editObject);

            if (updateDto.getName() == null)
                updateDto.setName(editObject.getName());
            if (updateDto.getCoefficient() == 0)
                updateDto.setCoefficient(String.format(Locale.US, "%.2f", editObject.getCoefficient()));
            if (updateDto.getGroupId() == 0)
                updateDto.setGroupId(editObject.getGroup().getId());
        }

        //если в бд есть элемент точно такой же, каким будет изменяемый элемент, то чтобы не повторялись записи
        Optional<SelectionVariant> equalsFromDbOption = selectionVariantRepo.findByNameAndGroupAndCoefficient(updateDto.getName(), updateDto.getGroupId(), updateDto.getCoefficient());
        if (equalsFromDbOption.isPresent()) {
            //мы берём уже существующий элемент и просто активируем его
            SelectionVariant equalFromDb = equalsFromDbOption.get();
            equalFromDb.setActive(true);
            return selectionVariantRepo.save(equalFromDb).getId();
        }

        return createVariant(updateDto);
    }


    private long createVariant(SelectionVariantUpdateDto updateDto) {
        if (updateDto.getName() == null || updateDto.getName().isBlank()) {
            throw new BadRequestException("Name не может быть пустой строкой при создании варианта");
        } else if (updateDto.getCoefficient() <= 0) {
            throw new BadRequestException("Coefficient не может быть меньше или равен нулю при создании варианта");
        } else if (updateDto.getGroupId() < 1) {
            throw new BadRequestException("GroupId должно быть больше 0 при создании варианта");
        }

        SelectionVariant selectionVariant = new SelectionVariant();
        selectionVariant.setGroup(findGroupById(updateDto.getGroupId()));
        selectionVariant.setCoefficient(updateDto.getCoefficient());
        selectionVariant.setName(updateDto.getName());

        return selectionVariantRepo.save(selectionVariant).getId();
    }

    public int updateGroupName(int id, String name) {
        if (id < 1 && id != 0)
            throw new BadRequestException("Id не может быть меньше единицы");
        if (name == null || name.isBlank())
            throw new BadRequestException("У группы не может быть пустое название");

        Optional<SelectionGroup> byName = selectionGroupRepo.findByName(name);
        if (byName.isPresent()) {
            SelectionGroup group = byName.get();
            if (group.isActive() && id != group.getId()) {
                throw new BadRequestException("Группа с таким названием уже существует");
            } else if (id == 0) {
                group.setActive(true);
                return selectionGroupRepo.save(group).getId();
            }
        }

        SelectionGroup group;
        if (id != 0)
            group = selectionGroupRepo.findById(id).orElseThrow(() -> new NotFoundException("Группа с таким id не найдена"));
        else
            group = new SelectionGroup();

        group.setName(name);
        return selectionGroupRepo.save(group).getId();
    }

    public SelectionGroup findGroupById(int id) {
        return selectionGroupRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Группы с таким id не существует"));
    }


    private SelectionVariant findVariantById(long id) {
        return selectionVariantRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Варианта с таком id не существует"));
    }

    public void deleteVariant(long id) {
        SelectionVariant variant = findVariantById(id);
        variant.setActive(false);
        selectionVariantRepo.save(variant);
    }

    public void deleteGroup(int groupId) {
        SelectionGroup group = findGroupById(groupId);
        group.getVariants().forEach(var -> var.setActive(false));
        group.setActive(false);
        selectionGroupRepo.save(group);
    }

    public SelectionGroup getGroup(int groupId) {
        SelectionGroup group = findGroupById(groupId);
        group.getVariants().removeIf(el -> !el.isActive());
        return group;
    }

    public List<SelectionVariant> getVariants(List<Long> ids) {
        return selectionVariantRepo.findAllByIdIn(ids);
    }
}
