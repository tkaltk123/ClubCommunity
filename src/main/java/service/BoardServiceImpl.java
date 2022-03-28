package service;

import domain.Board;
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
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardMapper boardMapper;

    @Override
    public List<Board> getBoards() {
        return boardMapper.getBoards(MyUtil.getUserId());
    }
}
