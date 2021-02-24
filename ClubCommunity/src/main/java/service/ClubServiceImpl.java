package service;

import domain.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ClubMapper;
import repository.UserMapper;
import util.MyUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    ClubMapper clubMapper;

    @Override
    @Transactional
    public void create(Club club) {
        String clubName = club.getClub_name();
        if(clubMapper.getClubByName(clubName) != null)
            throw new RuntimeException("club name conflict");
        HttpSession session = MyUtil.getSession();
        Long ownerId = (Long)session.getAttribute("user-id");
        club.setOwner_id(ownerId);
        clubMapper.insertClub(club);
        Long clubId = clubMapper.getClubByName(clubName).getId();
        clubMapper.joinClub(ownerId,clubId);
        clubMapper.increaseMember(clubId);
    }

    @Override
    @Transactional
    public List<Club> getClubs() {
        return null;
    }

    @Override
    @Transactional
    public void update(Club club) {

    }

    @Override
    @Transactional
    public void delete(Long club_id) {

    }

    @Override
    @Transactional
    public void join(Long club_id) {

    }

    @Override
    @Transactional
    public void withdrawal(Long club_id) {

    }
}
