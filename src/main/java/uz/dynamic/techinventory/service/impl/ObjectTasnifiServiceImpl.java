package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.ObjectTasnifi;
import uz.dynamic.techinventory.repository.ObjectTasnifiRepository;
import uz.dynamic.techinventory.service.ObjectTasnifiService;
import uz.dynamic.techinventory.service.dto.ObjectTasnifiDTO;
import uz.dynamic.techinventory.service.mapper.ObjectTasnifiMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.ObjectTasnifi}.
 */
@Service
@Transactional
public class ObjectTasnifiServiceImpl implements ObjectTasnifiService {

    private final Logger log = LoggerFactory.getLogger(ObjectTasnifiServiceImpl.class);

    private final ObjectTasnifiRepository objectTasnifiRepository;

    private final ObjectTasnifiMapper objectTasnifiMapper;

    public ObjectTasnifiServiceImpl(ObjectTasnifiRepository objectTasnifiRepository, ObjectTasnifiMapper objectTasnifiMapper) {
        this.objectTasnifiRepository = objectTasnifiRepository;
        this.objectTasnifiMapper = objectTasnifiMapper;
    }

    @Override
    public ObjectTasnifiDTO save(ObjectTasnifiDTO objectTasnifiDTO) {
        log.debug("Request to save ObjectTasnifi : {}", objectTasnifiDTO);
        ObjectTasnifi objectTasnifi = objectTasnifiMapper.toEntity(objectTasnifiDTO);
        objectTasnifi = objectTasnifiRepository.save(objectTasnifi);
        return objectTasnifiMapper.toDto(objectTasnifi);
    }

    @Override
    public ObjectTasnifiDTO update(ObjectTasnifiDTO objectTasnifiDTO) {
        log.debug("Request to update ObjectTasnifi : {}", objectTasnifiDTO);
        ObjectTasnifi objectTasnifi = objectTasnifiMapper.toEntity(objectTasnifiDTO);
        objectTasnifi = objectTasnifiRepository.save(objectTasnifi);
        return objectTasnifiMapper.toDto(objectTasnifi);
    }

    @Override
    public Optional<ObjectTasnifiDTO> partialUpdate(ObjectTasnifiDTO objectTasnifiDTO) {
        log.debug("Request to partially update ObjectTasnifi : {}", objectTasnifiDTO);

        return objectTasnifiRepository
            .findById(objectTasnifiDTO.getId())
            .map(existingObjectTasnifi -> {
                objectTasnifiMapper.partialUpdate(existingObjectTasnifi, objectTasnifiDTO);

                return existingObjectTasnifi;
            })
            .map(objectTasnifiRepository::save)
            .map(objectTasnifiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectTasnifiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObjectTasnifis");
        return objectTasnifiRepository.findAll(pageable).map(objectTasnifiMapper::toDto);
    }

    @Override
    public Page<ObjectTasnifiDTO> findAllByObjectTasnifiTuri(Pageable pageable, Long objectTasnifiTuriId) {
        log.debug("Request to get all ObjectTasnifis");
        return objectTasnifiRepository.findAllByBjectTasnifiTuriId(pageable, objectTasnifiTuriId).map(objectTasnifiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjectTasnifiDTO> findOne(Long id) {
        log.debug("Request to get ObjectTasnifi : {}", id);
        return objectTasnifiRepository.findById(id).map(objectTasnifiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObjectTasnifi : {}", id);
        objectTasnifiRepository.deleteById(id);
    }
}
