package ru.itmo.kotikijava2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotikijava2.dao.model.CatsEntity;
import ru.itmo.kotikijava2.dao.model.Color;
import ru.itmo.kotikijava2.dao.model.OwnersEntity;
import ru.itmo.kotikijava2.service.CatsService;
import ru.itmo.kotikijava2.wrapper.CatsEntityWrapper;
import ru.itmo.kotikijava2.wrapper.WrapperBuilder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cat")
public class CatController {
    @Autowired
    private CatsService catsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCat(
            @RequestParam String name,
            @RequestParam String dateOfBirth,
            @RequestParam String breed,
            @RequestParam String color,
            @RequestParam int ownerId
    ) {
        try {
            catsService.add(
                    name,
                    new Date(new SimpleDateFormat("dd.MM.yyyy").parse(dateOfBirth).getTime()),
                    breed,
                    Color.fromString(color),
                    ownerId
            );
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
            catsService.addFriendToCat(first, second);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("friends successfuly made", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCatById(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String color
    ) {
        if (id != null) {
            CatsEntity cat = catsService.getById(id);
            if (cat == null) {
                return new ResponseEntity("cat not found", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(WrapperBuilder.getCatEntityWrapper(cat), HttpStatus.OK);
        }
        else if (color != null) {
            CatsEntity cat = catsService.getByColor(color);
            if (cat == null) {
                return new ResponseEntity("cat not found", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(WrapperBuilder.getCatEntityWrapper(cat), HttpStatus.OK);
        }

        List<CatsEntity> cats = catsService.getAll();
        List<CatsEntityWrapper> catsWrapper =
                cats.stream()
                .map(cat -> WrapperBuilder.getCatEntityWrapper(cat))
                .collect(Collectors.toList());

        return new ResponseEntity(catsWrapper, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCatFriends")
    public ResponseEntity getCatFriends(@RequestParam Integer id) {
        CatsEntity cat = catsService.getById(id);
        if (cat == null) {
            return new ResponseEntity("cat not found", HttpStatus.BAD_REQUEST);
        }
        Set<CatsEntity> cats = catsService.getCatFriend(cat);
        List<CatsEntityWrapper> catsWrapper =
                cats.stream()
                .map(catsEntity -> WrapperBuilder.getCatEntityWrapper(catsEntity))
                .collect(Collectors.toList());
        return new ResponseEntity(catsWrapper, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteCat(@RequestParam int id) {
        try {
            catsService.delete(id);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("cat successfuly deleted", HttpStatus.OK);
    }
}
