package uz.dynamic.techinventory.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Cabel;
import uz.dynamic.techinventory.repository.CabelRepository;
import uz.dynamic.techinventory.service.CabelService;
import uz.dynamic.techinventory.service.dto.CabelDTO;
import uz.dynamic.techinventory.service.mapper.CabelMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Cabel}.
 */
@Service
@Transactional
public class CabelServiceImpl implements CabelService {

    private final Logger log = LoggerFactory.getLogger(CabelServiceImpl.class);

    private final CabelRepository cabelRepository;

    private final CabelMapper cabelMapper;

    public CabelServiceImpl(CabelRepository cabelRepository, CabelMapper cabelMapper) {
        this.cabelRepository = cabelRepository;
        this.cabelMapper = cabelMapper;
    }

    @Override
    public CabelDTO save(CabelDTO cabelDTO) {
        log.debug("Request to save Cabel : {}", cabelDTO);
        Cabel cabel = cabelMapper.toEntity(cabelDTO);
        cabel = cabelRepository.save(cabel);
        return cabelMapper.toDto(cabel);
    }

    @Override
    public CabelDTO update(CabelDTO cabelDTO) {
        log.debug("Request to update Cabel : {}", cabelDTO);
        Cabel cabel = cabelMapper.toEntity(cabelDTO);
        cabel = cabelRepository.save(cabel);
        return cabelMapper.toDto(cabel);
    }

    @Override
    public Optional<CabelDTO> partialUpdate(CabelDTO cabelDTO) {
        log.debug("Request to partially update Cabel : {}", cabelDTO);

        return cabelRepository
                .findById(cabelDTO.getId())
                .map(existingCabel -> {
                    cabelMapper.partialUpdate(existingCabel, cabelDTO);

                    return existingCabel;
                })
                .map(cabelRepository::save)
                .map(cabelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CabelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cabels");
        return cabelRepository.findAll(pageable).map(cabelMapper::toDto);
    }

    @Override
    public Page<CabelDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all CabelTypes");
        return cabelRepository.findAllByObyektId(pageable, obyektId).map(cabelMapper::toDto);
    }

    @Override
    public Page<CabelDTO> findAllByCabelType(Pageable pageable, Long cabelTypeId) {
        log.debug("Request to get all Cabels");
        return cabelRepository.findAllByCabelTypeId(pageable, cabelTypeId).map(cabelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CabelDTO> findOne(Long id) {
        log.debug("Request to get Cabel : {}", id);
        return cabelRepository.findById(id).map(cabelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cabel : {}", id);
        cabelRepository.deleteById(id);
    }
}
