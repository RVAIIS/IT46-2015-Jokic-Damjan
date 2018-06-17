package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

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
import rva.jpa.Departman;
import rva.jpa.Fakultet;
import rva.jpa.Student;
import rva.reps.DepartmanRepository;
import rva.reps.FakultetRepository;

@RestController
@Api(tags= {"Departman CRUD operacije"})
public class DepartmanRestController {
	@Autowired
	private DepartmanRepository departmanRepository;
	@Autowired
	private FakultetRepository fakultetRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("departman")
	@ApiOperation(value="Vraca kolekciju svih departmana iz baze podataka")
	public Collection<Departman> getDepartmani(){
		return departmanRepository.findAll();
	}
	
	@GetMapping("departmanId/{id}")
	@ApiOperation(value="Vraca departman ciji je id jednak id-u proslednjenom kao path varijabla")
	public Departman getDepartman(@PathVariable ("id") Integer id) {
		return departmanRepository.getOne(id);
	}
	
	@GetMapping("departmanNaziv/{naziv}")
	@ApiOperation(value = "Vraca departman ciji je naziv jednak nazivu proslednjenom kao path varijabla")
	public Collection<Departman> getDepartmanByNaziv(@PathVariable ("naziv") String naziv){
		return departmanRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@GetMapping("departmanFakultet/{id}")
	public Collection<Departman> getDepartmanByFakultet(@PathVariable ("id") Integer id){
		Fakultet f = fakultetRepository.getOne(id);
		return departmanRepository.findByFakultet(f);
	}
	
	//DELETE
	@CrossOrigin
	@Transactional
	@DeleteMapping("departman/{id}")
	@ApiOperation(value = "Brise departman sa prosledjenim id-jem kao path varijablu")
	public ResponseEntity<Departman> deleteStudent(@PathVariable ("id") Integer id){
		if(!departmanRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		jdbcTemplate.execute("Delete from student where departman = "+id);
		departmanRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//INSERT
	@CrossOrigin
	@PostMapping("departman")
	@ApiOperation(value = "Upisuje departman u bazu")
	public ResponseEntity<Departman> insertDepartman(@RequestBody Departman departman){
		if(!departmanRepository.existsById(departman.getId())) {
			departmanRepository.save(departman);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	//UPDATE
	@CrossOrigin
	@PutMapping("departman")
	@ApiOperation(value = "Modifikuje postojeci departman u bazi")
	public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman){
		if(!departmanRepository.existsById(departman.getId())) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		departmanRepository.save(departman);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
