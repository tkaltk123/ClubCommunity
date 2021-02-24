package service;

import domain.Club;
import domain.User;

import java.util.List;

public interface ClubService {
    void create(Club club);
    List<Club> getClubs();
    void update(Club club);
    void delete(Long club_id, User user);
    void join(Long club_id);
    void withdrawal(Long club_id);
}
