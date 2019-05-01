package com.epam.vmsis.kpp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@NoArgsConstructor
public class Input {
    @Id
    long id;
    @Column(name = "coordinate_x1")
    int x1;
    @Column(name = "coordinate_x2")
    int x2;
    @Column(name = "coordinate_y1")
    int y1;
    @Column(name = "coordinate_y2")
    int y2;
    @Column(name = "line_id")
    long lineId;

    public Input(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input = (Input) o;
        return x1 == input.x1 &&
                x2 == input.x2 &&
                y1 == input.y1 &&
                y2 == input.y2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, y1, y2);
    }
}
