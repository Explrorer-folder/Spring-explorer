package com.barabanov.spring.database.entity;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user", "chat"})
@ToString(exclude = {"user", "chat"})
@Entity
@Table(name = "users_chat")
public class UserChat implements BaseEntity<Long>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    public void setUser(User user)
    {
        this.user = user;
        this.user.getUserChats().add(this);
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
        this.chat.getUserChats().add(this);
    }
}
