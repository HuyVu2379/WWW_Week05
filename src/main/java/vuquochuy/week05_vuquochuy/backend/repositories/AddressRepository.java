package vuquochuy.week05_vuquochuy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuquochuy.week05_vuquochuy.backend.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}