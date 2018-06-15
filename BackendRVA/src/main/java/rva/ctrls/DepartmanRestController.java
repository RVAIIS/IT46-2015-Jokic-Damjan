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
import rva.jpa.Student;
import rva.reps.DepartmanRepository;

@RestController
@Api(tags= {"Departman CRUD operacije"})
public class DepartmanRestController {
	@Autowired
	private DepartmanRepository departmanRepository;
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
	
	//DELETE
	@CrossOrigin
	@Transactional
	@DeleteMapping("departman/{id}")
	public ResponseEntity<Departman> deleteStudent(@PathVariable ("id") Integer id){
		if(!departmanRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		departmanRepository.deleteById(id);
		jdbcTemplate.execute("Delete from student where departman = "+id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//INSERT
	@CrossOrigin
	@PostMapping("departman")
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
	public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman){
		if(!departmanRepository.existsById(departman.getId())) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		departmanRepository.save(departman);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
