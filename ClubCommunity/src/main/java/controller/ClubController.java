package controller;

import domain.Club;
import domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ClubService;
import service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/clubs")
public class ClubController {
    @Autowired
    ClubService clubService;
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "소모임 생성", notes = "소모임을 생성합니다.")
    public ResponseEntity<String> createClub(
            @ApiParam(value = "(required: club_name, introduce)", required = true) @RequestBody @Valid Club club) {
        clubService.create(club);
        return new ResponseEntity<>("club create success", HttpStatus.OK);
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "소모임 조회", notes = "소모임 목록을 보여줍니다.")
    public ResponseEntity<List<Club>> readClubs()
    {
        return new ResponseEntity<>(clubService.getClubs(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{club-id}", method = RequestMethod.PUT)
    @ApiOperation(value = "소모임 수정", notes = "소모임의 소개를 수정합니다.")
    public ResponseEntity<String> modifyClub(@PathVariable("club-id") Long clubid, @RequestBody Club club) {
        club.setId(clubid);
        clubService.update(club);
        return new ResponseEntity<>("club modify success", HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{club-id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "소모임 해체", notes = "소모임을 해체합니다.")
    public ResponseEntity<String> deleteClub(@PathVariable("club-id") Long clubid) {
        clubService.delete(clubid);
        return new ResponseEntity<>("club delete success", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{club-id}/join", method = RequestMethod.POST)
    @ApiOperation(value = "소모임 가입", notes = "사용자의 소모임 가입을 처리합니다.")
    public ResponseEntity<String> joinClub(@PathVariable("club-id") Long clubid) {
        clubService.join(clubid);
        return new ResponseEntity<>("club join success", HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{club-id}/withdrawal", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "소모임 탈퇴", notes = "선택된 소모임을 탈퇴합니다.")
    public ResponseEntity<String> withdrawalClub(@PathVariable("club-id") Long clubid) {
        clubService.withdrawal(clubid);
        return new ResponseEntity<>("club withdrawal success", HttpStatus.OK);
    }
}
