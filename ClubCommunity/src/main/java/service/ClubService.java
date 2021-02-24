package service;

import domain.Club;

import java.util.List;

public interface ClubService {
    void create(Club club);
    List<Club> getClubs();
    void update(Club club);
    void delete(Long club_id);
    void join(Long club_id);
    void withdrawal(Long club_id);
}
