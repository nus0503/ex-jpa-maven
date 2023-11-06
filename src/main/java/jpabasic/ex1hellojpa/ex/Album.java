package jpabasic.ex1hellojpa.ex;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@DiscriminatorValue(value = "A")
@Entity
public class Album  extends Item{
    private String artist;
}
