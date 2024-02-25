package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Avtomat;
import uz.dynamic.techinventory.repository.AvtomatRepository;
import uz.dynamic.techinventory.service.AvtomatService;
import uz.dynamic.techinventory.service.dto.AvtomatDTO;
import uz.dynamic.techinventory.service.mapper.AvtomatMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Avtomat}.
 */
@Service
@Transactional
public class AvtomatServiceImpl implements AvtomatService {

    private final Logger log = LoggerFactory.getLogger(AvtomatServiceImpl.class);

    private final AvtomatRepository avtomatRepository;

    private final AvtomatMapper avtomatMapper;

    public AvtomatServiceImpl(AvtomatRepository avtomatRepository, AvtomatMapper avtomatMapper) {
        this.avtomatRepository = avtomatRepository;
        this.avtomatMapper = avtomatMapper;
    }

    @Override
    public AvtomatDTO save(AvtomatDTO avtomatDTO) {
        log.debug("Request to save Avtomat : {}", avtomatDTO);
        Avtomat avtomat = avtomatMapper.toEntity(avtomatDTO);
        avtomat = avtomatRepository.save(avtomat);
        return avtomatMapper.toDto(avtomat);
    }

    @Override
    public AvtomatDTO update(AvtomatDTO avtomatDTO) {
        log.debug("Request to update Avtomat : {}", avtomatDTO);
        Avtomat avtomat = avtomatMapper.toEntity(avtomatDTO);
        avtomat = avtomatRepository.save(avtomat);
        return avtomatMapper.toDto(avtomat);
    }

    @Override
    public Optional<AvtomatDTO> partialUpdate(AvtomatDTO avtomatDTO) {
        log.debug("Request to partially update Avtomat : {}", avtomatDTO);

        return avtomatRepository
            .findById(avtomatDTO.getId())
            .map(existingAvtomat -> {
                avtomatMapper.partialUpdate(existingAvtomat, avtomatDTO);

                return existingAvtomat;
            })
            .map(avtomatRepository::save)
            .map(avtomatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AvtomatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Avtomats");
        return avtomatRepository.findAll(pageable).map(avtomatMapper::toDto);
    }

    @Override
    public Page<AvtomatDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all Avtomats");
        return avtomatRepository.findAllByObyektId(pageable, obyektId).map(avtomatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AvtomatDTO> findOne(Long id) {
        log.debug("Request to get Avtomat : {}", id);
        return avtomatRepository.findById(id).map(avtomatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avtomat : {}", id);
        avtomatRepository.deleteById(id);
    }
}
