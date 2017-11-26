package enterprise.velle.justnote.CustomItems;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Navigation item.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class NavigationItem {

    private String text;
    int icon;
    int ID;

    public NavigationItem(){
        text = null;
    }
    public NavigationItem(String text, int icon, int ID){
        this.text = text;
        this.icon = icon;
        this.ID = ID;
    }

    public void setText(String text){
        this.text = text;
    }
    public void setIcon(int icon){
        this.icon = icon;
    }
    public String getText(){
        return text;
    }
    public int getIcon(){
        return icon;
    }
    public int getID(){return ID;}
}
