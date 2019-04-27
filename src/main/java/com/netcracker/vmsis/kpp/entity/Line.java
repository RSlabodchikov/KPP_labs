package com.netcracker.vmsis.kpp.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(schema = "kpp_labs")
public class Line implements Comparable<Line> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "projection_x")
    private int projectionX;
    @Column(name = "projection_y")
    private int projectionY;
    @Column(name = "length")
    private double length;


    public Line(int projectionX, int projectionY, double length) {
        this.projectionX = projectionX;
        this.projectionY = projectionY;
        this.length = length;
    }

    @Override
    public int compareTo(Line o) {
        return Double.compare(this.length, o.length);
    }


}