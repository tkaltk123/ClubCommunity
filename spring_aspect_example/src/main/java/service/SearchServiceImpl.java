package service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class SearchServiceImpl implements SearchService{
    ArrayList<Integer> arrayList;
    public SearchServiceImpl(){
        arrayList = new ArrayList<>();
        for(int i=1;i<=7000000;++i)
            arrayList.add(i);
    }
    @Override
    public void binarySearch(int key) {
        Collections.binarySearch(arrayList,key);
    }

    @Override
    public void linearSearch(int key) {
        arrayList.indexOf(key);
    }
}
