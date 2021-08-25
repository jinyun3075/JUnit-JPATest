package com.project1.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.List;
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
                memoText("Sample...100").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test void testPageDefault(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("-----------------------------");
        System.out.println("Total Pages :"+result.getTotalPages()); // 총 몇 페이지
        System.out.println("Total Count :"+result.getTotalElements()); // 전체 개수
        System.out.println("PageNumber :"+result.getNumber()); // 현재 페이지 번호 0부터 시작
        System.out.println("Page Size :"+result.getSize()); // 페이지당 데이터 개수
        System.out.println("has next page? :"+result.hasNext());//다음페이지 존재 여부
        System.out.println("first page? :"+result.isFirst());//시작페이지(0) 여부
    }

    @Test void 페이징정렬테스트(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable= PageRequest.of(0,10,sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo->{
            System.out.println(memo);
        });
    }

    @Test void 검색쿼리메소드테스트(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L);
        for(Memo memo : list){
            System.out.println(memo);
        }
    }
    @Test void Test_검색쿼리메소드With페이징(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(70L,80L,pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }
    @Transactional // 검색으로 객체들을 가져오고 삭제하는 작업이 같이 이루어져 트랜잭션 어노테이션이 필요하다
    @Commit // delete문 최종결과를 커밋하기위해서 사용되는 어노테이션, delete는 기본적으로 롤백처리
    @Test void Test_삭제쿼리메소드(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
