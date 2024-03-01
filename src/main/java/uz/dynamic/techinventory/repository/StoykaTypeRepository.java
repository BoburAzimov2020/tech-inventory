package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.StoykaType;

/**
 * Spring Data JPA repository for the StoykaType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoykaTypeRepository extends JpaRepository<StoykaType, Long> {}
