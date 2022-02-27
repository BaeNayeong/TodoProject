package com.example.todoproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {
    private String id;
    private String userId; // 오브젝트를 생성한 사용자의 아이디
    private String title; // to do 타이틀
    private boolean done; // 수행했는지 여부
}
