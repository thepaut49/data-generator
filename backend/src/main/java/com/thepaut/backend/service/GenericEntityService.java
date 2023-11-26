package com.thepaut.backend.service;

import com.thepaut.backend.dto.GenericDto;
import com.thepaut.backend.model.data.GenericEntity;
import com.thepaut.backend.model.data.audit.GenericEntityAudit;
import com.thepaut.backend.repository.data.GenericEntityRepository;
import com.thepaut.backend.repository.data.audit.GenericAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
public abstract class GenericEntityService<CreationDto, UpdateDto extends GenericDto,Entity extends GenericEntity,EntityAudit extends GenericEntityAudit> implements IGenericEntityService<CreationDto, UpdateDto> {

    protected final GenericEntityRepository<Entity> entityRepository;

    protected final  GenericAuditRepository<EntityAudit> auditRepository;

    @Override
    public GenericDto getEntityById(Long categoryId) {
        Entity entity = entityRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie inconnu : " + categoryId));
        entity.setVersions(auditRepository.findByIdOrderByVersionDesc(categoryId));
        entity.addVersion(convertEntityToAudit(entity));
        return convertEntityToUpdateDto(entity);
    }

    @Transactional
    @Override
    public GenericDto rollbackToPreviousVersion(Long id) {
        Entity currentEntity = entityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Catégorie " + id + " inconnu !"));
        Entity previousVersion = (Entity) getPreviousVersion(id,currentEntity.getVersion() - 1);
        previousVersion = entityRepository.save(previousVersion);
        auditRepository.deleteFirstByIdOrderByVersionDesc(id);
        return convertEntityToUpdateDto(previousVersion);
    }

    @Override
    public GenericDto rollbackToVersion(Long id, Long version) {
        Entity specificVersion = getSpecificVersion(id,version);
        specificVersion = entityRepository.save(specificVersion);
        auditRepository.deleteByIdAndVersionGreaterThanEqual(id,version);
        return convertEntityToUpdateDto(specificVersion);
    }

    @Override
    public GenericDto createEntity(@Valid CreationDto creationDto) {
        Entity entity = convertCreationDtoToEntity(creationDto);
        entity.setModifiedBy(UserService.getUserId());
        entity = entityRepository.save(entity);
        entity.addVersion(convertEntityToAudit(entity));
        return convertEntityToUpdateDto(entity);
    }

    @Transactional
    @Override
    public GenericDto updateEntity(Long id, @Valid UpdateDto updateDto) {
        // Sauvegarde de l'ancienne version
        Entity oldVersion = entityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Catégorie " + id + " inconnu !"));
        auditRepository.save(convertEntityToAudit(oldVersion));
        // Mise à jour de l'entity
        List<EntityAudit> versions = auditRepository.findByIdOrderByVersionDesc(id);
        Entity updatedEntity = convertUpdateDtoToEntity(updateDto);
        updatedEntity.setVersion(updateDto.getVersion() + 1);
        updatedEntity.setModifiedBy(UserService.getUserId());
        updatedEntity = entityRepository.save(updatedEntity);
        // Construction de la liste des versions
        updatedEntity.setVersions(versions);
        updatedEntity.addVersion(convertEntityToAudit(updatedEntity));
        return convertEntityToUpdateDto(updatedEntity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        entityRepository.deleteById(id);
        auditRepository.deleteById(id);
        return true;
    }

    /**
     *
     * @param id
     * @param version
     * @return
     */
    private GenericEntity getPreviousVersion(Long id, Long version) {
        List<EntityAudit> versions = auditRepository.findByIdOrderByVersionDesc(id);
        if (versions.isEmpty()) {
            throw new ResourceNotFoundException("La version " + version +  " de la catégorie " + id + " n'existe pas !");
        } else {
            GenericEntityAudit previousVersion = versions.get(0);
            GenericEntity category = convertAuditToEntity((EntityAudit) previousVersion);
            category.setVersions(versions);
            auditRepository.deleteFirstByIdOrderByVersionDesc(id);
            return category;
        }
    }

    /**
     *
     * @param id
     * @param version
     * @return
     */
    private Entity getSpecificVersion(Long id, Long version) {
        List<EntityAudit> versions = auditRepository.findByIdAndVersionLessThanEqualOrderByVersionDesc(id,version);
        if (versions.isEmpty()) {
            throw new ResourceNotFoundException("La version " + version +  " de la catégorie " + id + " n'existe pas !");
        } else {
            EntityAudit specificVersion = (EntityAudit) versions.get(0);
            Entity category = convertAuditToEntity(specificVersion);
            category.setVersions(versions);
            auditRepository.deleteByIdAndVersionGreaterThanEqual(id, version);
            return category;
        }
    }

    public abstract Entity convertAuditToEntity(EntityAudit entityAudit);

    public abstract EntityAudit convertEntityToAudit(Entity entity);

    public abstract UpdateDto convertEntityToUpdateDto(Entity entity);

    public abstract Entity convertUpdateDtoToEntity(UpdateDto updateDto) ;

    public abstract Entity convertCreationDtoToEntity(CreationDto creationDto);

}
