package com.project1.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTest {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }
    @Test
    public void testSelect(){
        Long mno =100l;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("==================");

        if(result.isPresent()){
            Memo memo =result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2(){
        Long mno =100l;
        Memo memo= memoRepository.getOne(mno);

        System.out.println("==================");
        System.out.println(memo);
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().
                mno(100L).
                memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }
}
