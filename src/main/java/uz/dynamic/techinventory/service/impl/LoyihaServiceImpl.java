package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Loyiha;
import uz.dynamic.techinventory.repository.LoyihaRepository;
import uz.dynamic.techinventory.service.LoyihaService;
import uz.dynamic.techinventory.service.dto.LoyihaDTO;
import uz.dynamic.techinventory.service.mapper.LoyihaMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Loyiha}.
 */
@Service
@Transactional
public class LoyihaServiceImpl implements LoyihaService {

    private final Logger log = LoggerFactory.getLogger(LoyihaServiceImpl.class);

    private final LoyihaRepository loyihaRepository;

    private final LoyihaMapper loyihaMapper;

    public LoyihaServiceImpl(LoyihaRepository loyihaRepository, LoyihaMapper loyihaMapper) {
        this.loyihaRepository = loyihaRepository;
        this.loyihaMapper = loyihaMapper;
    }

    @Override
    public LoyihaDTO save(LoyihaDTO loyihaDTO) {
        log.debug("Request to save Loyiha : {}", loyihaDTO);
        Loyiha loyiha = loyihaMapper.toEntity(loyihaDTO);
        loyiha = loyihaRepository.save(loyiha);
        return loyihaMapper.toDto(loyiha);
    }

    @Override
    public LoyihaDTO update(LoyihaDTO loyihaDTO) {
        log.debug("Request to update Loyiha : {}", loyihaDTO);
        Loyiha loyiha = loyihaMapper.toEntity(loyihaDTO);
        loyiha = loyihaRepository.save(loyiha);
        return loyihaMapper.toDto(loyiha);
    }

    @Override
    public Optional<LoyihaDTO> partialUpdate(LoyihaDTO loyihaDTO) {
        log.debug("Request to partially update Loyiha : {}", loyihaDTO);

        return loyihaRepository
            .findById(loyihaDTO.getId())
            .map(existingLoyiha -> {
                loyihaMapper.partialUpdate(existingLoyiha, loyihaDTO);

                return existingLoyiha;
            })
            .map(loyihaRepository::save)
            .map(loyihaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LoyihaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Loyihas");
        return loyihaRepository.findAll(pageable).map(loyihaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoyihaDTO> findOne(Long id) {
        log.debug("Request to get Loyiha : {}", id);
        return loyihaRepository.findById(id).map(loyihaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Loyiha : {}", id);
        loyihaRepository.deleteById(id);
    }
}
