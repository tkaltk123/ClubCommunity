package repository;

import domain.Board;
import domain.Club;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {
    public void insertClubBoard(Club club);
    public void softDeleteBoard(Long club_id);
    public List<Board> getBoards(Long user_id);
}
