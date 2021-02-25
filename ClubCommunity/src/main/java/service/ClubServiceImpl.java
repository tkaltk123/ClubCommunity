package service;

import domain.Club;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BoardMapper;
import repository.ClubMapper;
import repository.UserMapper;
import util.MyUtil;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClubMapper clubMapper;
    @Autowired
    BoardMapper boardMapper;
    //소모임 이름이 중복될 경우 예외 발생
    //소모임을 만든 유저는 자동 가입
    @Override
    @Transactional
    public void create(Club club) {
        String clubName = club.getClub_name();
        //소모임 이름이 중복될 경우
        if(clubMapper.getClubByName(clubName) != null)
            throw new RuntimeException("is duplicated club name.");
        //소모임 생성
        Long ownerId = MyUtil.getUserId();
        club.setOwner_id(ownerId);
        clubMapper.insertClub(club);
        //소유자를 소모임에 추가
        Long clubId = clubMapper.getClubByName(clubName).getId();
        clubMapper.joinClub(ownerId,clubId);
        clubMapper.increaseMember(clubId);
        //게시판 추가
        club.setId(clubId);
        boardMapper.insertClubBoard(club);

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
        checkClub(dbClub);
        checkOwner(dbClub);
        checkDuplicate(dbClub);
        clubMapper.updateClub(club);
    }
    //club 이 존재하지 않거나 소유자가 아니거나 비밀번호가 일치하지 않는 경우 예외 발생
    //club 이 삭제되도 소모임 가입 상황, 게시판, 게시글, 댓글은 상태 유지
    @Override
    public void delete(Long club_id, User user) {
        Club dbClub = clubMapper.getClubById(club_id);
        checkClub(dbClub);
        checkOwner(dbClub);
        User dbUser = userMapper.getUserById(MyUtil.getUserId());
        if(MyUtil.incorrectPw(user, dbUser) )
            throw new RuntimeException("is wrong password.");
        //소모임 삭제(soft delete)
        clubMapper.softDeleteClub(club_id);
        boardMapper.softDeleteBoard(club_id);
    }

    @Override
    @Transactional
    public void join(Long club_id) {
        Club dbClub = clubMapper.getClubById(club_id);
        checkClub(dbClub);
        Long userId = MyUtil.getUserId();
        Boolean joined = clubMapper.getJoined(userId, club_id);
        // 가입 이력이 존재할 경우
        if(joined != null){
            // 이미 가입된 경우
            if(!joined)
                throw  new RuntimeException("already join club.");
            clubMapper.rejoinClub(userId, club_id);
        }
        else
            clubMapper.joinClub(userId, club_id);
        clubMapper.increaseMember(club_id);
    }

    @Override
    @Transactional
    public void withdrawal(Long club_id) {
        Club dbClub = clubMapper.getClubById(club_id);
        checkClub(dbClub);
        Long userId = MyUtil.getUserId();
        Boolean joined = clubMapper.getJoined(userId, club_id);
        //가입되지 않은 경우
        if(joined == null || joined)
            throw  new RuntimeException("not joined club.");
        //소모임의 소유주가 탈퇴할 경우
        if(userId.equals(dbClub.getOwner_id() ) ){
            //회원이 있을 경우
            if(dbClub.getMember_num() > 1)
                throw  new RuntimeException("owner don't withdrawal club.");
            //회원이 없을 경우
            clubMapper.softDeleteClub(club_id);
            boardMapper.softDeleteBoard(club_id);
        }
        clubMapper.withdrawalClub(userId, club_id);
        clubMapper.decreaseMember(club_id);
    }

    private  Club getSameNameClub(Club club){
        return clubMapper.getClubByName(club.getClub_name() );
    }
    //소모임의 존재여부를 확인하고 없으면 예외 발생
    private void checkClub(Club club){
        if(club == null || club.isDeleted() )
            throw new RuntimeException("club not exist.");
    }
    //세션의 사용자가 소모임을 소유하고 있는지 확인하고 소유하지 않는다면 예외 발생
    private void checkOwner(Club club){
        if(!club.getOwner_id().equals(MyUtil.getUserId() ) )
            throw new RuntimeException("not own this club.");
    }
    //소모임의 이름이 중복되는지 확인하고 중복된다면 예외 발생
    private void checkDuplicate(Club club){
        Club sameNameClub = getSameNameClub(club);
        if(sameNameClub != null && MyUtil.isNotSameId(sameNameClub, club.getId() ) )
            throw new RuntimeException("is duplicated club name.");
    }
}
