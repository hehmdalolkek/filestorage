package ru.hehmdalolkek.filestorage.model;

import jakarta.persistence.*;

import java.io.Serializable;

@NamedEntityGraph(name="eventEntityGraph", attributeNodes={
        @NamedAttributeNode("user"),
        @NamedAttributeNode("file")
})
@Entity
@Table
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "file_id")
    File file;

    public Event() {
    }

    public Event(Integer id, User user, File file) {
        this.id = id;
        this.user = user;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return id.equals(event.id) && user.equals(event.user) && file.equals(event.file);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + file.hashCode();
        return result;
    }
}
