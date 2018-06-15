package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Fakultet;
import rva.reps.FakultetRepository;

@RestController
@Api(tags= {"Fakultet CRUD operacijae"})
public class FakultetRestController {
	@Autowired
	private FakultetRepository fakultetRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("fakultet")
	@ApiOperation(value = "Vraca kolekciju svih fakulteta iz baze podataka")
	public Collection<Fakultet> getFakulteti(){
		return fakultetRepository.findAll();
	}
	
	@GetMapping("fakultetId/{id}")
	@ApiOperation(value = "Vraca artikl iz baze podataka ciji je ID vrednost prosledjena kao path variabla")
	public Fakultet getFakultet(@PathVariable("id") Integer id) {
		return fakultetRepository.getOne(id);
	}
	
	@GetMapping("fakultetNaziv/{naziv}")
	@ApiOperation(value = "Vraca fakultet iz baze ciji je naziv vrednost prosledjena kao path variabla")
	public Collection<Fakultet> getFakultetByName(@PathVariable("naziv") String naziv) {
		return fakultetRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@CrossOrigin
	@PostMapping("fakultet")
	@ApiOperation(value="Upisuje fakultet u bazu podataka")
	public ResponseEntity<HttpStatus> insertFakultet(@RequestBody Fakultet fakultet){
		if(fakultetRepository.existsById(fakultet.getId()))
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		fakultetRepository.save(fakultet);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@PutMapping("fakultet")
	@ApiOperation(value="Modifikuje postojeci fakultet u bazi podataka")
	public ResponseEntity<HttpStatus> updateFakultet(@RequestBody Fakultet fakultet){
		if(fakultetRepository.existsById(fakultet.getId())) {
			fakultetRepository.save(fakultet);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@CrossOrigin
	@DeleteMapping("fakultet/{id}")
	@ApiOperation(value="Brise iz baze podataka fakultet ciji je id vrednost prosledjena kao path varijabla ")
	public ResponseEntity<HttpStatus> deleteFakultet(@PathVariable("id") Integer id){
		if(fakultetRepository.existsById(id)) 
			fakultetRepository.deleteById(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
