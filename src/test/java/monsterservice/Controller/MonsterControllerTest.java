package monsterservice.Controller;

import monsterservice.controller.MonsterController;
import monsterservice.handleExceptionError.HandleExceptionError;
import monsterservice.model.Monster;
import monsterservice.service.MonsterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class MonsterControllerTest {
    private Monster mockMonster() {
        Monster mockmonster = new Monster();
        mockmonster.setId(1);
        mockmonster.setName("drake");
        mockmonster.setHealth(400);
        return mockmonster;
    }

    @InjectMocks
    private MonsterController monsterController;

    @Mock
    private MonsterService monsterService;

    @Test
    void getGreetingTest() {
        String response = monsterController.getGreeting();
        assertEquals("Hi Kaewkwan, I'm from thailand!", response);
    }

    @Test
    void postCreateTest() {
        doReturn(mockMonster()).when(monsterService).postCreateMonsterService(any(Monster.class));

        Monster response = monsterController.postCreate(new Monster());

        assertEquals(mockMonster().getId(), response.getId());
        assertEquals(mockMonster().getName(), response.getName());
        assertEquals(mockMonster().getHealth(), response.getHealth());
    }

    @Test
    void getAllMonsterTest() {
        List<Monster> monsterList = new ArrayList<>();
        monsterList.add(mockMonster());

        doReturn(monsterList).when(monsterService).getAllMonsterService();

        List<Monster> response = monsterController.getAll();
        assertEquals(monsterList, response);
    }

    @Test
    void getInformationTest() {
        Optional<Monster> monsterOptional = Optional.of(mockMonster());

        doReturn(monsterOptional).when(monsterService).getInformation(any(Integer.class));

        Optional<Monster> response = monsterController.getInformation(1);

        assertTrue(response.isPresent());

        //For sample case error
        //assertFalse(response.isPresent());
    }

    @Test
    void putUpdateTestSuccess() throws HandleExceptionError {
        doReturn(mockMonster())
                .when(monsterService)
                .updateMonsterByIdService(any(Monster.class));

        ResponseEntity<Monster> response
                = monsterController.putUpdate(new Monster());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockMonster().getId(),
                Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void putUpdateTestFail() throws HandleExceptionError {
        doThrow(new HandleExceptionError("Error"))
                .when(monsterService)
                .updateMonsterByIdService(any(Monster.class));

        ResponseEntity<Monster> response =
                monsterController.putUpdate(new Monster());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void deleteTestSuccess() throws HandleExceptionError {
        doReturn(true)
                .when(monsterService)
                .deleteMonsterService(any(Integer.class));

        ResponseEntity<Boolean> response
                = monsterController.delete(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
        //assertEquals(true,response.getBody());
        //assertTrue(response.getBody());
    }

    @Test
    void deleteTestFail() throws HandleExceptionError {
        doThrow(new HandleExceptionError("Error"))
                .when(monsterService)
                .deleteMonsterService(any(Integer.class));

        ResponseEntity<Boolean> response
                = monsterController.delete(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
    }

    @Test
    void putAttackTestSuccess() throws HandleExceptionError {
        String Success = "Update success";
        doReturn(Success)
                .when(monsterService)
                .attackMonsterService(any(Integer.class), any(Integer.class));

        ResponseEntity<String> response
                = monsterController.putAttack(1, 30);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Success, response.getBody());
    }

    @Test
    void putAttackTestFailCanNotUpdate() throws HandleExceptionError {
        String notUpdate = "Can't update";
        doThrow(new HandleExceptionError(notUpdate))
                .when(monsterService)
                .attackMonsterService(any(Integer.class), any(Integer.class));

        ResponseEntity<String> response
                = monsterController.putAttack(1, 30);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(notUpdate, response.getBody());
    }

    @Test
    void putAttackTestFailDataNotFound() throws HandleExceptionError {
        String NotFound = "Data not found";
        doThrow(new HandleExceptionError(NotFound))
                .when(monsterService)
                .attackMonsterService(any(Integer.class), any(Integer.class));

        ResponseEntity<String> response
                = monsterController.putAttack(1, 30);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(NotFound, response.getBody());
    }
}
