package com.example.todoproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor // 매개변수가 없는 생성자
@AllArgsConstructor // 모든 인자를 매개변수로 받는 생성자
@Table(uniqueConstraints = {@UniqueConstraint(columnNames="email")})
public class UserEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    private String id;

    @Column(nullable = false)
    private String username; // 회원 이름

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 패스워드

}
