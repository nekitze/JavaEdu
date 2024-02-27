package edu.school21.chat.models;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String messageText;
    private String dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Message(Long id, User author, Chatroom room, String messageText, String dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.messageText = messageText;
        this.dateTime = dateTime;
    }

    public Message() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!id.equals(message.id)) return false;
        if (!author.equals(message.author)) return false;
        if (!room.equals(message.room)) return false;
        if (!messageText.equals(message.messageText)) return false;
        return dateTime.equals(message.dateTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + room.hashCode();
        result = 31 * result + messageText.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message : {" +
                "\n\tid=" + id +
                ",\n\tauthor=" + author +
                ",\n\troom=" + room +
                ",\n\tmessageText=\"" + messageText + '\"' +
                ",\n\tdateTime=\"" + dateTime + '\"' +
                "\n}";
    }
}
