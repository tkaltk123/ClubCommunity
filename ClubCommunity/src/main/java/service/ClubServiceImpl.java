package service;

import domain.Club;
import domain.User;
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
    //소모임 이름이 중복될 경우 예외 발생
    //소모임을 만든 유저는 자동 가입
    @Override
    @Transactional
    public void create(Club club) {
        String clubName = club.getClub_name();
        if(clubMapper.getClubByName(clubName) != null)
            throw new RuntimeException("club name conflict");
        Long ownerId = MyUtil.getUserId();
        club.setOwner_id(ownerId);
        clubMapper.insertClub(club);
        Long clubId = clubMapper.getClubByName(clubName).getId();
        clubMapper.joinClub(ownerId,clubId);
        clubMapper.increaseMember(clubId);
    }
    //삭제되지 않은 소모임들을 반환
    @Override
    public List<Club> getClubs() {
        return clubMapper.getClubs();
    }
    //club_id를 가진 소모임이 존재하지 않거나 로그인된 사용자와 소모임의 소유자가 일치하지 않거나
    //소모임 이름이 중복될 경우 예외 발생
    @Override
    public void update(Club club) {
        Club dbClub = clubMapper.getClubById(club.getId() );
        if(dbClub == null)
            throw new RuntimeException("club not exist.");
        //소모임의 소유자가 로그인된 사용자와 일치하지 않을 경우
        if(!dbClub.getOwner_id().equals(MyUtil.getUserId() ) )
            throw new RuntimeException("you not own this club.");
        Club sameNameClub = getSameNameClub(club);
        if(sameNameClub != null && MyUtil.isNotSameId(sameNameClub, club.getId() ) )
            throw new RuntimeException("is duplicated club name.");
        clubMapper.updateClub(club);
    }
    //클럽이 삭제되면 게시글도 모두 삭제되야함
    @Override
    @Transactional
    public void delete(Long club_id, User user) {

    }

    @Override
    @Transactional
    public void join(Long club_id) {

    }

    @Override
    @Transactional
    public void withdrawal(Long club_id) {

    }

    private  Club getSameNameClub(Club club){
        return clubMapper.getClubByName(club.getClub_name() );
    }
}
