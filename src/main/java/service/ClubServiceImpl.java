package service;

import domain.Club;
import domain.Post;
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
    static final int PAGE_SIZE = 20;
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
        if(clubMapper.getJoinedClubNum(ownerId)>=20)
            throw new RuntimeException("Only up to 20 clubs are eligible to join.");
        club.setOwner_id(ownerId);
        clubMapper.insertClub(club);
        //소유자를 소모임에 추가
        Long clubId = clubMapper.getClubByName(clubName).getId();
        clubMapper.joinClub(ownerId, clubId);
        clubMapper.increaseMember(clubId);
        //게시판 추가
        club.setId(clubId);
        boardMapper.insertClubBoard(club);
    }
    //삭제되지 않은 소모임들을 반환
    @Override
    public List<Club> getClubs(Long page) {
        long offset = (page - 1) * PAGE_SIZE;
        if(offset < 0)
            offset = 0;
        return clubMapper.getClubs(offset, PAGE_SIZE);
    }
    //club_id를 가진 소모임이 존재하지 않거나 로그인된 사용자와 소모임의 소유자가 일치하지 않거나
    //소모임 이름이 중복될 경우 예외 발생
    @Override
    public void update(Club club) {
        checkClub(club.getId() );
        if(duplicated(club) )
            throw new RuntimeException("is duplicated club name.");
        //수정
        clubMapper.updateClub(club);
    }
    //club 이 존재하지 않거나 소유자가 아니거나 비밀번호가 일치하지 않는 경우 예외 발생
    //club 이 삭제되도 소모임 가입 상황, 게시판, 게시글, 댓글은 상태 유지
    @Override
    public void delete(Long club_id, User user) {
        checkClub(club_id);
        User dbUser = userMapper.getUserById(MyUtil.getUserId());
        if(MyUtil.incorrectPw(user, dbUser) )
            throw new RuntimeException("is wrong password.");
        //소모임 삭제(soft delete)
        clubMapper.softDeleteClub(club_id);
        boardMapper.softDeleteBoard(club_id);
    }
    @Override
    @Transactional
    public void join(Long clubId) {
        Club dbClub = clubMapper.getClubById(clubId);
        if(dbClub == null)
            throw new RuntimeException("club not exist.");
        Long userId = MyUtil.getUserId();
        if(clubMapper.getJoinedClubNum(userId)>=20)
            throw new RuntimeException("Only up to 20 clubs are eligible to join.");
        Boolean status = clubMapper.getJoinedStatus(userId, clubId);
        // 가입 이력이 존재할 경우
        if(status != null){
            // 이미 가입된 경우
            if(!status)
                throw  new RuntimeException("already join club.");
            clubMapper.rejoinClub(userId, clubId);
        }
        else
            clubMapper.joinClub(userId, clubId);
        clubMapper.increaseMember(clubId);
    }

    @Override
    @Transactional
    public void withdrawal(Long clubId) {
        Club dbClub = clubMapper.getClubById(clubId);
        if(dbClub == null)
            throw new RuntimeException("club not exist.");
        Long userId = MyUtil.getUserId();
        Boolean status = clubMapper.getJoinedStatus(userId, clubId);
        //가입되지 않은 경우
        if(status == null || status)
            throw  new RuntimeException("not joined club.");
        clubMapper.withdrawalClub(userId, clubId);
        clubMapper.decreaseMember(clubId);
        //소모임의 소유주가 탈퇴할 경우
        if(userId.equals(dbClub.getOwner_id() ) ){
            //회원이 있을 경우
            if(dbClub.getMember_num() > 1)
                throw  new RuntimeException("owner don't withdrawal club.");
            //회원이 없을 경우
            clubMapper.softDeleteClub(clubId);
            boardMapper.softDeleteBoard(clubId);
        }
    }

    private  Club getSameNameClub(Club club){
        return clubMapper.getClubByName(club.getClub_name() );
    }
    private boolean notOwn(Club club){
        return !club.getOwner_id().equals(MyUtil.getUserId() );
    }
    private  boolean duplicated(Club club){
        Club sameNameClub = getSameNameClub(club);
        return sameNameClub != null && MyUtil.isNotSameId(sameNameClub, club.getId() );
    }
    //club id를 받아 클럽이 존재하는지와 사용자가 클럽을 소유하고 있는지를 확인
    private void checkClub(Long clubId){
        Club dbClub = clubMapper.getClubById(clubId);
        if(dbClub == null)
            throw new RuntimeException("club not exist.");
        if(notOwn(dbClub) )
            throw new RuntimeException("not own this club.");
    }
}
