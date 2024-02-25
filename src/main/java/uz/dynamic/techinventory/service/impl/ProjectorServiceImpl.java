package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Projector;
import uz.dynamic.techinventory.repository.ProjectorRepository;
import uz.dynamic.techinventory.service.ProjectorService;
import uz.dynamic.techinventory.service.dto.ProjectorDTO;
import uz.dynamic.techinventory.service.mapper.ProjectorMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Projector}.
 */
@Service
@Transactional
public class ProjectorServiceImpl implements ProjectorService {

    private final Logger log = LoggerFactory.getLogger(ProjectorServiceImpl.class);

    private final ProjectorRepository projectorRepository;

    private final ProjectorMapper projectorMapper;

    public ProjectorServiceImpl(ProjectorRepository projectorRepository, ProjectorMapper projectorMapper) {
        this.projectorRepository = projectorRepository;
        this.projectorMapper = projectorMapper;
    }

    @Override
    public ProjectorDTO save(ProjectorDTO projectorDTO) {
        log.debug("Request to save Projector : {}", projectorDTO);
        Projector projector = projectorMapper.toEntity(projectorDTO);
        projector = projectorRepository.save(projector);
        return projectorMapper.toDto(projector);
    }

    @Override
    public ProjectorDTO update(ProjectorDTO projectorDTO) {
        log.debug("Request to update Projector : {}", projectorDTO);
        Projector projector = projectorMapper.toEntity(projectorDTO);
        projector = projectorRepository.save(projector);
        return projectorMapper.toDto(projector);
    }

    @Override
    public Optional<ProjectorDTO> partialUpdate(ProjectorDTO projectorDTO) {
        log.debug("Request to partially update Projector : {}", projectorDTO);

        return projectorRepository
            .findById(projectorDTO.getId())
            .map(existingProjector -> {
                projectorMapper.partialUpdate(existingProjector, projectorDTO);

                return existingProjector;
            })
            .map(projectorRepository::save)
            .map(projectorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projectors");
        return projectorRepository.findAll(pageable).map(projectorMapper::toDto);
    }

    @Override
    public Page<ProjectorDTO> findAllByProjectorType(Pageable pageable, Long projectorTypeId) {
        log.debug("Request to get all Projectors");
        return projectorRepository.findAllByProjectorTypeId(pageable, projectorTypeId).map(projectorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectorDTO> findOne(Long id) {
        log.debug("Request to get Projector : {}", id);
        return projectorRepository.findById(id).map(projectorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projector : {}", id);
        projectorRepository.deleteById(id);
    }
}
