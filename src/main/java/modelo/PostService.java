package modelo;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class PostService {

    @Element(required = false)
    private String foto;
    @Element
    private String cuerpo;
    @Element
    private String user;
    @Element(required = false)
    private String etiqueta;

    public void setFoto(String foto){
    this.foto = foto;
}


    public String getFoto(){
        return foto;
    }

    public void setCuerpo(String foto){
        this.cuerpo = foto;
    }


    public String getCuerpo(){
        return cuerpo;
    }
    public void setUser(String foto){
        this.user = foto;
    }


    public String getUser(){
        return user;
    }

    public void setTag(String etiqueta){
        this.etiqueta = etiqueta;
    }


    public String getTag(){
        return etiqueta;
    }
}
