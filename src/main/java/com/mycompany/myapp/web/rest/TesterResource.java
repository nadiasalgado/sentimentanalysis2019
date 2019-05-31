package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.service.SearchService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import twitter4j.*;
import twitter4j.api.*;
import twitter4j.conf.*;
import java.util.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Search.
 */
@RestController
@RequestMapping("/api")
public class TesterResource {
    @PostMapping("/hello")
    public ResponseEntity<TestDTO> hello(@RequestBody TestDTO testdto) throws TwitterException{
        String txt = testdto.getstatText();
        int c = testdto.getnum();
        String tp = testdto.gettype();
        TestDTO test123 = new TestDTO();
        test123.getTweet(txt, c, tp);
        return ResponseEntity.ok().body(test123);
    }
}
