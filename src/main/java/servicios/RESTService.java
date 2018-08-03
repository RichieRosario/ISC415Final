package servicios;

import dao.*;
import modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RESTService {

    private static RESTService instancia;
    private static List<Post> listaPosts = new ArrayList<>();


    private RESTService(){
        super();
    }

    public static RESTService getInstancia(){
        if(instancia==null){
           instancia = new RESTService();
        }
        return instancia;
    }

    /**
     * Lista todos las publicaciones dado un usuario.
     * @param username
     * @return
     */
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


    /**
     * Crea un nuevo post.
     * @param post
     * @return
     */
    public PostService crearPublicacion(PostService post){
        UserDaoImpl userDao = new UserDaoImpl(User.class);
        NotificationDaoImpl notificationDao = new NotificationDaoImpl(Notification.class);
        PhotoDaoImpl photoDao = new PhotoDaoImpl(Photo.class);
        PostDaoImpl postDao = new PostDaoImpl(Post.class);
        TagDaoImpl tagDao = new TagDaoImpl(Tag.class);
        User u = userDao.searchByUsername(post.getUser());
        Post n = new Post();
        Tag t = new Tag();
        Photo p = new Photo();
        t.setUsers(userDao.searchByUsername(post.getTag()));
        if(post.getFoto()!=null)p.setFoto(Base64.getDecoder().decode(post.getFoto()));
        n.setEtiqueta(t);
        n.setTexto(post.getCuerpo());
        n.setLikes(0);
        n.setFecha(LocalDate.now());
        n.setWall(u.getWall());
        if(post.getFoto()!=null)n.setPhoto(p);
        n.setUser(userDao.searchByUsername("clienteREST"));

        tagDao.add(t);
        if(post.getFoto()!=null)photoDao.add(p);
        postDao.add(n);
        System.out.println("Publicacion creada");
        return post;

    }

    
}
