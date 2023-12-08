package adapters;

public class Book {
    String title,content;

    public Book(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
