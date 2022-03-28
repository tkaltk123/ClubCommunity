package repository;

import domain.Club;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMapper {
    //insert
    void insertClub(Club club);
    void joinClub(Long user_id, Long club_id);
    //select
    Club getClubByName(String name);
    List<Club> getClubs(Long offset, int size);
    Club getClubById(Long club_id);
    Boolean getJoinedStatus(Long user_id, Long club_id);
    Long getJoinedClubNum(Long user_id);
    //update
    void increaseMember(Long club_id);
    void updateClub(Club club);
    void softDeleteClub(Long club_id);
    void rejoinClub(Long user_id, Long club_id);
    void withdrawalClub(Long user_id, Long club_id);
    void decreaseMember(Long club_id);
}
