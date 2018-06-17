package rva.reps;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Departman;
import rva.jpa.Fakultet;

public interface DepartmanRepository extends JpaRepository<Departman, Integer> {
	Collection<Departman> findByNazivContainingIgnoreCase(String naziv);
	Collection<Departman> findByFakultet(Fakultet f);
}
