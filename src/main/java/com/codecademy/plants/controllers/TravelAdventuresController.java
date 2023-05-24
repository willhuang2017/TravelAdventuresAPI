package com.codecademy.plants.controllers;
import com.codecademy.plants.entities.Adventure;
import com.codecademy.plants.repositories.AdventureRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RestController
@RequestMapping("/traveladventures")
public class TravelAdventuresController {

  private final AdventureRepository adventureRepository;

  public TravelAdventuresController(AdventureRepository adventureRepo) {
    this.adventureRepository = adventureRepo;
  }

  // Add controller methods below:
  @GetMapping
  public Iterable<Adventure> getAdventures(){
    Iterable<Adventure> adventures = this.adventureRepository.findAll();
    return adventures;
  }

  @GetMapping(path="/bycountry/{country}")
  public Iterable<Adventure> getAdventuresByCountry(@PathVariable String country){
    Iterable<Adventure> adventures = this.adventureRepository.findByCountry(country);
    return adventures;
  }
  
  @GetMapping(path = "/bystate")
  public Iterable<Adventure> getAdventuresByState(@RequestParam String state){
    Iterable<Adventure> adventures = this.adventureRepository.findByState(state);
    return adventures;
  }

  @PostMapping
  public String createNewAdventure(@RequestBody Adventure adventure){
    this.adventureRepository.save(adventure);
    return "New Adventure Succesfully Added";
  }

  @PutMapping(path = "/{id}")
  public Adventure toggleIdBlog(@PathVariable Integer id, @RequestBody Adventure adventure){
    Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
    if(!adventureOptional.isPresent()){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This adventure does not exist");
    }
    Adventure adventureToUpdate = adventureOptional.get();
    if(adventure.getBlogCompleted()){
      adventureToUpdate.setBlogCompleted(true);
    } else {
      adventureToUpdate.setBlogCompleted(false);
    }
    Adventure updatedAdventure = this.adventureRepository.save(adventureToUpdate);
    return updatedAdventure;
  }

  @DeleteMapping(path = "/{id}")
  public void deleteAdventure(@PathVariable Integer id){
    Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
    if(!adventureOptional.isPresent()){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This adventure does not exist");
    }
    Adventure adventure = adventureOptional.get();
    this.adventureRepository.delete(adventure);
  }
}
