package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SearchService;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseEntity search(){
        searchService.binarySearch(7000000);
        searchService.linearSearch(7000000);
        return new ResponseEntity("Run searching compare!", HttpStatus.OK);
    }

}
