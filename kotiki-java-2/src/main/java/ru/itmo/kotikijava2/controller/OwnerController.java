package ru.itmo.kotikijava2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotikijava2.dao.model.OwnersEntity;
import ru.itmo.kotikijava2.service.OwnerService;
import ru.itmo.kotikijava2.wrapper.OwnerEntityWrapper;
import ru.itmo.kotikijava2.wrapper.WrapperBuilder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOwner(
            @RequestParam String name,
            @RequestParam String birthDay
    ) {
        try {
            ownerService.add(name, new Date(new SimpleDateFormat("dd.MM.yyyy").parse(birthDay).getTime()));
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getOwnerById(@RequestParam(required = false) Integer id) {
        if (id != null) {
            OwnersEntity owner = ownerService.getById((int) id);
            if (owner == null) {
                return new ResponseEntity("owner not found", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(WrapperBuilder.getOwnerWrapper(owner), HttpStatus.OK);
        }
        List<OwnersEntity> owners = ownerService.getAll();
        if (owners == null) {
            return new ResponseEntity("owners not found", HttpStatus.BAD_REQUEST);
        }
        List<OwnerEntityWrapper> ownerWrappers =
                owners.stream()
                .map(owner -> WrapperBuilder.getOwnerWrapper(owner))
                .collect(Collectors.toList());
        return new ResponseEntity(ownerWrappers, HttpStatus.OK);
    }
}
