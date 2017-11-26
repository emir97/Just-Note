package enterprise.velle.justnote.CustomItems;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Note item.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class NoteItem {
    private int ID;
    private String title, content, category;
    private String date;

    public NoteItem(int ID, String title, String content){
        this.ID = ID;
        this.title = title;
        this.content = content;
    }
    public NoteItem(int ID, String title, String content, String category){
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
