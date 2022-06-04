package ru.itmo.kotikicontrollers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotikicontrollers.util.KafkaUtil;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cat")
public class CatController {

    @Autowired
    KafkaUtil kafkaUtil;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCat(
            @RequestParam String name,
            @RequestParam String dateOfBirth,
            @RequestParam String breed,
            @RequestParam String color,
            @RequestParam Integer ownerId
    ) {
        try {
            Map<String, String> props = new HashMap<>();
            props.put("name", name);
            props.put("dateOfBirth", dateOfBirth);
            props.put("breed", breed);
            props.put("color", color);
            props.put("ownerId", Integer.toString(ownerId));
            kafkaUtil.sendMessage("cat.AddCat", props);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("success",HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/makeFriends")
    public ResponseEntity makeFriends(
            @RequestParam Integer first,
            @RequestParam Integer second
    ) {
        try {
            Map<String, String> props = new HashMap<>();
            props.put("first", Integer.toString(first));
            props.put("second", Integer.toString(second));
            kafkaUtil.sendMessage("cat.MakeFriends", props);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("friends successfuly made", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCatById(@RequestParam(required = false) Integer id,
            @RequestParam(required = false) String color) {
        Map<String, String> props = new HashMap<>();
        if (id != null) {
            props.put("id", Integer.toString(id));
        }
        else if (color != null) {
            props.put("color", color);
        }

        kafkaUtil.sendMessage("cat.GetBy", props);

        return new ResponseEntity("good", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCatFriends")
    public ResponseEntity getCatFriends(@RequestParam Integer id) {
        Map<String, String> props = new HashMap<>();
        props.put("id", Integer.toString(id));
        kafkaUtil.sendMessage("cat.getCatFriends", props);
        return new ResponseEntity("good", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteCat(@RequestParam int id) {
        try {
            Map<String, String> props = new HashMap<>();
            props.put("id", Integer.toString(id));
            kafkaUtil.sendMessage("cat.DeleteCat", props);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("cat successfuly deleted", HttpStatus.OK);
    }
}
