package repository;

import domain.Board;
import domain.Club;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMapper {
    //insert
    void insertClubBoard(Club club);
    //select
    List<Board> getBoards(Long user_id);
    Board getBoardById(Long board_id);
    //update
    void softDeleteBoard(Long club_id);
}
