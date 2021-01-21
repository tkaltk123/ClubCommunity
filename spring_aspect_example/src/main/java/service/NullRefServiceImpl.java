package service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NullRefServiceImpl implements NullRefService{
    @Override
    public void reference() throws NullPointerException{
        ArrayList<Integer> arr = null;
        arr.add(1);
    }
}
