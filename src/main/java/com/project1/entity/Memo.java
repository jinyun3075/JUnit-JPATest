package com.project1.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name ="t_memo")
@ToString
@NoArgsConstructor
@Getter
public class Memo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mno;

    @Column(length=200,nullable = false)
    private String memoText;

    @Builder
    Memo(Long mno,String memoText){
        this.mno=mno;
        this.memoText=memoText;
    }
}
