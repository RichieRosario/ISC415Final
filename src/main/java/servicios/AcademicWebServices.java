package servicios;

import dao.*;
import modelo.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vacax on 18/06/17.
 */
@WebService
public class AcademicWebServices {

    @WebMethod
    public String holaMundo(String hola){
        return hola;
    }


    @WebMethod
    public List<PostService> getPublicaciones(String username){
        UserDaoImpl userDao = new UserDaoImpl(User.class);
        User u = userDao.searchByUsername(username);
        List<PostService> listado = new ArrayList<>();
        for(Post p : u.getPosts()){
            PostService tmp = new PostService();
            if(p.getPhoto() !=null)tmp.setFoto(p.getPhoto().getFoto());
            tmp.setCuerpo(p.getTexto());
            tmp.setUser(p.getUser().getUsername());
        if(p.getEtiqueta() !=null){
           if(p.getEtiqueta().getUsers() !=null){
               tmp.setTag(p.getEtiqueta().getUsers().getUsername());
           }
        }
            listado.add(tmp);
        }
        return listado;
    }

    @WebMethod
    public void CrearPublicacion(byte[] foto, String target, String etiqueta, String cuerpo){
        UserDaoImpl userDao = new UserDaoImpl(User.class);
        NotificationDaoImpl notificationDao = new NotificationDaoImpl(Notification.class);
        PhotoDaoImpl photoDao = new PhotoDaoImpl(Photo.class);
        PostDaoImpl postDao = new PostDaoImpl(Post.class);
        TagDaoImpl tagDao = new TagDaoImpl(Tag.class);
        User u = userDao.searchByUsername(target);
        Post n = new Post();
        Tag t = new Tag();
        Photo p = new Photo();
        t.setUsers(userDao.searchByUsername(etiqueta));
        if(foto!=null)p.setFoto(foto);
        n.setEtiqueta(t);
        n.setTexto(cuerpo);
        n.setLikes(0);
        n.setFecha(LocalDate.now());
        n.setWall(u.getWall());
        if(foto!=null)n.setPhoto(p);
        n.setUser(userDao.searchByUsername("clienteSOAP"));

        tagDao.add(t);
        if(foto!=null)photoDao.add(p);
        postDao.add(n);
        System.out.println("Publicacion creada");

    }



}