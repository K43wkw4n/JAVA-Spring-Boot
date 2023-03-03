package monsterservice.controller;

import monsterservice.model.Monster;
import monsterservice.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monster")
public class MonsterController {
    @Autowired
    private MonsterService monsterService;

    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hi Kaewkwan, I'm from thailand!";
    }

    @PostMapping("/create")
    public Monster postCreate(@RequestBody Monster monster) {
        return monsterService.postCreateMonsterService(monster);
    }

    @GetMapping("/get-all")
    public List<Monster> getAll() {
        return monsterService.getAllMonsterService();
    }

    @GetMapping("/get-information")
    public Monster getInformation(@RequestHeader Integer id) {
        return monsterService.getInformation(id);
    }

    @PutMapping("/update")
    public Monster putUpdate(@RequestBody Monster monster) {
        return monsterService.updateMonsterByIdService(monster);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestHeader Integer id) {
        return monsterService.deleteMonsterService(id);
    }

    @PutMapping("/attack")
    public Monster putAttack(@RequestHeader Integer id, Integer health) {
        return monsterService.attackMonsterService(id, health);
    }
}
