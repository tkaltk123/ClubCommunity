package repository;

import domain.Club;
import domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMapper {
    public void insertClub(Club club);
    public Club getClubByName(String name);
    public void joinClub(Long user_id, Long club_id);
    public void increaseMember(Long club_id);
    public List<Club> getClubs();
    public Club getClubById(Long club_id);
    public void updateClub(Club club);
    public void softDeleteClub(Long club_id);
    public Boolean getJoined(Long user_id, Long club_id);
    public void rejoinClub(Long user_id, Long club_id);
    public void withdrawalClub(Long user_id, Long club_id);
    public void decreaseMember(Long club_id);
    public List<Club> getClubsByOwner(Long owner_id);
    public List<Club> getJoinedClubs(Long user_id);
}
