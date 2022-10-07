package ru.itmo.kotikicontrollers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotikicontrollers.util.KafkaUtil;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    KafkaUtil kafkaUtil;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOwner(
            @RequestParam String name,
            @RequestParam String birthDay
    ) {
        try {
            Map<String, String> props = new HashMap<>();
            props.put("name", name);
            props.put("birthDay", birthDay);
            kafkaUtil.sendMessage("owner.AddOwner", props);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getOwnerById(@RequestParam(required = true) Integer id) {
        Map<String, String> props = new HashMap<>();
        props.put("id", Integer.toString(id));
        kafkaUtil.sendMessage("owner.GetOwnerById", props);
        return new ResponseEntity("good", HttpStatus.OK);
    }

}
