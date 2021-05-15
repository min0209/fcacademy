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

    private String hobby;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;

    public void set(PersonDto personDto){
        if(personDto.getHobby() != null){
            this.setHobby(personDto.getHobby());
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
        if(personDto.getBirthday() != null){
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }
    }

    public Integer getAge(){
        if(birthday != null){
            return LocalDate.now().getYear()-birthday.getYearOfBirthday()+1;
        }else{
            return null;
        }
    }
    public Boolean isBirthdayToday(){
        if(birthday != null){
            return LocalDate.now().equals(LocalDate.of(birthday.getYearOfBirthday(),birthday.getMonthOfBirthday(),birthday.getDayOfBirthday()));
        }else{
            return null;
        }
    }
}
