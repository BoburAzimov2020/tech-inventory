package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Akumulator;
import uz.dynamic.techinventory.repository.AkumulatorRepository;
import uz.dynamic.techinventory.service.AkumulatorService;
import uz.dynamic.techinventory.service.dto.AkumulatorDTO;
import uz.dynamic.techinventory.service.mapper.AkumulatorMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Akumulator}.
 */
@Service
@Transactional
public class AkumulatorServiceImpl implements AkumulatorService {

    private final Logger log = LoggerFactory.getLogger(AkumulatorServiceImpl.class);

    private final AkumulatorRepository akumulatorRepository;

    private final AkumulatorMapper akumulatorMapper;

    public AkumulatorServiceImpl(AkumulatorRepository akumulatorRepository, AkumulatorMapper akumulatorMapper) {
        this.akumulatorRepository = akumulatorRepository;
        this.akumulatorMapper = akumulatorMapper;
    }

    @Override
    public AkumulatorDTO save(AkumulatorDTO akumulatorDTO) {
        log.debug("Request to save Akumulator : {}", akumulatorDTO);
        Akumulator akumulator = akumulatorMapper.toEntity(akumulatorDTO);
        akumulator = akumulatorRepository.save(akumulator);
        return akumulatorMapper.toDto(akumulator);
    }

    @Override
    public AkumulatorDTO update(AkumulatorDTO akumulatorDTO) {
        log.debug("Request to update Akumulator : {}", akumulatorDTO);
        Akumulator akumulator = akumulatorMapper.toEntity(akumulatorDTO);
        akumulator = akumulatorRepository.save(akumulator);
        return akumulatorMapper.toDto(akumulator);
    }

    @Override
    public Optional<AkumulatorDTO> partialUpdate(AkumulatorDTO akumulatorDTO) {
        log.debug("Request to partially update Akumulator : {}", akumulatorDTO);

        return akumulatorRepository
            .findById(akumulatorDTO.getId())
            .map(existingAkumulator -> {
                akumulatorMapper.partialUpdate(existingAkumulator, akumulatorDTO);

                return existingAkumulator;
            })
            .map(akumulatorRepository::save)
            .map(akumulatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AkumulatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Akumulators");
        return akumulatorRepository.findAll(pageable).map(akumulatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AkumulatorDTO> findOne(Long id) {
        log.debug("Request to get Akumulator : {}", id);
        return akumulatorRepository.findById(id).map(akumulatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Akumulator : {}", id);
        akumulatorRepository.deleteById(id);
    }
}
