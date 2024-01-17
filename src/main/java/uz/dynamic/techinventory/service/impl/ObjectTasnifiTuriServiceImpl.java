package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.ObjectTasnifiTuri;
import uz.dynamic.techinventory.repository.ObjectTasnifiTuriRepository;
import uz.dynamic.techinventory.service.ObjectTasnifiTuriService;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiTuriDTO;
import uz.dynamic.techinventory.service.mapper.ObjectTasnifiTuriMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.ObjectTasnifiTuri}.
 */
@Service
@Transactional
public class ObjectTasnifiTuriServiceImpl implements ObjectTasnifiTuriService {

    private final Logger log = LoggerFactory.getLogger(ObjectTasnifiTuriServiceImpl.class);

    private final ObjectTasnifiTuriRepository objectTasnifiTuriRepository;

    private final ObjectTasnifiTuriMapper objectTasnifiTuriMapper;

    public ObjectTasnifiTuriServiceImpl(
        ObjectTasnifiTuriRepository objectTasnifiTuriRepository,
        ObjectTasnifiTuriMapper objectTasnifiTuriMapper
    ) {
        this.objectTasnifiTuriRepository = objectTasnifiTuriRepository;
        this.objectTasnifiTuriMapper = objectTasnifiTuriMapper;
    }

    @Override
    public ObjectTasnifiTuriDTO save(ObjectTasnifiTuriDTO objectTasnifiTuriDTO) {
        log.debug("Request to save ObjectTasnifiTuri : {}", objectTasnifiTuriDTO);
        ObjectTasnifiTuri objectTasnifiTuri = objectTasnifiTuriMapper.toEntity(objectTasnifiTuriDTO);
        objectTasnifiTuri = objectTasnifiTuriRepository.save(objectTasnifiTuri);
        return objectTasnifiTuriMapper.toDto(objectTasnifiTuri);
    }

    @Override
    public ObjectTasnifiTuriDTO update(ObjectTasnifiTuriDTO objectTasnifiTuriDTO) {
        log.debug("Request to update ObjectTasnifiTuri : {}", objectTasnifiTuriDTO);
        ObjectTasnifiTuri objectTasnifiTuri = objectTasnifiTuriMapper.toEntity(objectTasnifiTuriDTO);
        objectTasnifiTuri = objectTasnifiTuriRepository.save(objectTasnifiTuri);
        return objectTasnifiTuriMapper.toDto(objectTasnifiTuri);
    }

    @Override
    public Optional<ObjectTasnifiTuriDTO> partialUpdate(ObjectTasnifiTuriDTO objectTasnifiTuriDTO) {
        log.debug("Request to partially update ObjectTasnifiTuri : {}", objectTasnifiTuriDTO);

        return objectTasnifiTuriRepository
            .findById(objectTasnifiTuriDTO.getId())
            .map(existingObjectTasnifiTuri -> {
                objectTasnifiTuriMapper.partialUpdate(existingObjectTasnifiTuri, objectTasnifiTuriDTO);

                return existingObjectTasnifiTuri;
            })
            .map(objectTasnifiTuriRepository::save)
            .map(objectTasnifiTuriMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectTasnifiTuriDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObjectTasnifiTuris");
        return objectTasnifiTuriRepository.findAll(pageable).map(objectTasnifiTuriMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjectTasnifiTuriDTO> findOne(Long id) {
        log.debug("Request to get ObjectTasnifiTuri : {}", id);
        return objectTasnifiTuriRepository.findById(id).map(objectTasnifiTuriMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObjectTasnifiTuri : {}", id);
        objectTasnifiTuriRepository.deleteById(id);
    }
}
