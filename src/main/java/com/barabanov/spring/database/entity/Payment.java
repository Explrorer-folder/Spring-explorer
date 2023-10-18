package com.barabanov.spring.database.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = "receiver")
@ToString(exclude = "receiver")
@Builder
@Entity
public class Payment implements BaseEntity<Long>
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;
}
