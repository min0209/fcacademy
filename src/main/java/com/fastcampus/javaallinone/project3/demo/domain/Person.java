package com.fastcampus.javaallinone.project3.demo.domain;

import com.fastcampus.javaallinone.project3.demo.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.demo.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data   //@Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor    //required!!
@Where(clause = "deleted = false")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Min(1)
    @NonNull
    private  int age;

    private String hobby;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String bloodType;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)

    private Block block;

    public void set(PersonDto personDto){
        if(personDto.getAge() != 0){
            this.setAge(personDto.getAge());
        }
        if(personDto.getHobby() != null){
            this.setHobby(personDto.getHobby());
        }
        if(personDto.getBloodType() != null){
            this.setBloodType(personDto.getBloodType());
        }
        if(personDto.getAddress() != null){
            this.setAddress(personDto.getAddress());
        }
        if(personDto.getJob() != null){
            this.setJob(personDto.getJob());
        }
        if(personDto.getPhoneNumber() != null){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
    }

}
