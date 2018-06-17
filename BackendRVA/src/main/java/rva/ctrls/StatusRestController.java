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
import rva.jpa.Status;
import rva.jpa.Student;
import rva.reps.StatusRepository;

@Api(tags= {"Status CRUD operacije"})
@RestController
public class StatusRestController {
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("status")
	@ApiOperation(value="Vraca kolekciju svih statusa iz baze podataka")
	public Collection<Status> getStatusi(){
		return statusRepository.findAll();
	}
	
	@GetMapping("statusId/{id}")
	@ApiOperation(value="Vraca status na osnovu prosledjenog id-ja kroz path varijablu ")
	public Status getStatus(@PathVariable ("id") Integer id) {
		return statusRepository.getOne(id);
	}
	
	@GetMapping("statusNaziv/{naziv}")
	@ApiOperation(value="Vraca status na osnovu prosledjenog naziva statusa kroz path varijablu")
	public Collection<Status> getStatusByNaziv(@PathVariable("naziv") String naziv){
		return statusRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	//DELETE
	@CrossOrigin
	@DeleteMapping("status/{id}")
	@ApiOperation(value="Brise status iz baze podataka na osnovu prosledjenog id-ja kroz path varijablu")
	public ResponseEntity<Status> deleteStatus(@PathVariable ("id") Integer id){
		if(statusRepository.existsById(id)) {
			jdbcTemplate.execute("Delete from student where status =" + id);
			jdbcTemplate.execute("Delete from status where id =" + id);
			statusRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	//INSERT
	@CrossOrigin
	@PostMapping("status")
	@ApiOperation(value="Ubacuje status u bazu")
	public ResponseEntity<Status> insertStatus(@RequestBody Status status){
		if(!statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	//UPDATE
	@CrossOrigin
	@PutMapping("status")
	@ApiOperation(value="Modifikuje postojeci status u bazu podataka")
	public ResponseEntity<Status> updateStatus(@RequestBody Status status){
		if(statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
