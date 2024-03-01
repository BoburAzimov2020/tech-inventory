package uz.dynamic.techinventory.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.ProjectorType;
import uz.dynamic.techinventory.repository.ProjectorTypeRepository;
import uz.dynamic.techinventory.service.ProjectorTypeService;
import uz.dynamic.techinventory.service.dto.ProjectorTypeDTO;
import uz.dynamic.techinventory.service.mapper.ProjectorTypeMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.ProjectorType}.
 */
@Service
@Transactional
public class ProjectorTypeServiceImpl implements ProjectorTypeService {

    private final Logger log = LoggerFactory.getLogger(ProjectorTypeServiceImpl.class);

    private final ProjectorTypeRepository projectorTypeRepository;
    private final ProjectorTypeMapper projectorTypeMapper;

    public ProjectorTypeServiceImpl(ProjectorTypeRepository projectorTypeRepository,
                                    ProjectorTypeMapper projectorTypeMapper) {
        this.projectorTypeRepository = projectorTypeRepository;
        this.projectorTypeMapper = projectorTypeMapper;
    }

    @Override
    public ProjectorTypeDTO save(ProjectorTypeDTO projectorTypeDTO) {
        log.debug("Request to save ProjectorType : {}", projectorTypeDTO);
        ProjectorType projectorType = projectorTypeMapper.toEntity(projectorTypeDTO);
        projectorType = projectorTypeRepository.save(projectorType);
        return projectorTypeMapper.toDto(projectorType);
    }

    @Override
    public ProjectorTypeDTO update(ProjectorTypeDTO projectorTypeDTO) {
        log.debug("Request to update ProjectorType : {}", projectorTypeDTO);
        ProjectorType projectorType = projectorTypeMapper.toEntity(projectorTypeDTO);
        projectorType = projectorTypeRepository.save(projectorType);
        return projectorTypeMapper.toDto(projectorType);
    }

    @Override
    public Optional<ProjectorTypeDTO> partialUpdate(ProjectorTypeDTO projectorTypeDTO) {
        log.debug("Request to partially update ProjectorType : {}", projectorTypeDTO);

        return projectorTypeRepository
                .findById(projectorTypeDTO.getId())
                .map(existingProjectorType -> {
                    projectorTypeMapper.partialUpdate(existingProjectorType, projectorTypeDTO);

                    return existingProjectorType;
                })
                .map(projectorTypeRepository::save)
                .map(projectorTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectorTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectorTypes");
        return projectorTypeRepository.findAll(pageable).map(projectorTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectorTypeDTO> findOne(Long id) {
        log.debug("Request to get ProjectorType : {}", id);
        return projectorTypeRepository.findById(id).map(projectorTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectorType : {}", id);
        projectorTypeRepository.deleteById(id);
    }
}
