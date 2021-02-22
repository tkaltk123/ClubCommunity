package controller;

import domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Controller
@RequestMapping(value = "/clubs")
public class ClubController {

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "소모임 생성", notes = "소모임을 생성합니다.")
    public ResponseEntity<String> createClub(@RequestBody User user) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "소모임 조회", notes = "소모임 목록을 보여줍니다.")
    public String readClubs(){
        return "club/list";
    }

    @ResponseBody
    @RequestMapping(value = "/{club-id}", method = RequestMethod.PUT)
    @ApiOperation(value = "소모임 수정", notes = "소모임을 수정합니다.")
    public ResponseEntity<String> modifyClub(@PathVariable("club-id") Long clubid) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "/{club-id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "소모임 해체", notes = "소모임을 해체합니다.")
    public ResponseEntity<String> deleteClub(@PathVariable("club-id") Long clubid) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/{club-id}/join", method = RequestMethod.POST)
    @ApiOperation(value = "소모임 가입", notes = "사용자의 소모임 가입을 처리합니다.")
    public ResponseEntity<String> joinClub(@PathVariable("club-id") Long clubid) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "/{club-id}/withdrawal", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "소모임 탈퇴", notes = "선택된 소모임을 탈퇴합니다.")
    public ResponseEntity<String> withdrawalClub(@PathVariable("club-id") Long clubid) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
