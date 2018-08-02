package servicios;

import dao.UserDaoImpl;
import encapsulacion.Asignatura;
import encapsulacion.Estudiante;
import encapsulacion.Profesor;
import modelo.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
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
    public List<PostSOAP> getPublicaciones(String username){
        UserDaoImpl userDao = new UserDaoImpl(User.class);
        User u = userDao.searchByUsername(username);
        List<PostSOAP> listado = new ArrayList<>();
        for(Post p : u.getPosts()){
            PostSOAP tmp = new PostSOAP();
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
    public void CrearPublicacion(String foto, String target, String etiqueta, String cuerpo){
        UserDaoImpl userDao = new UserDaoImpl(User.class);
        User u = userDao.searchByUsername(target);
        Post n = new Post();
        Tag t = new Tag();
        Photo p = new Photo();
        t.setUsers(userDao.searchByUsername(target));
        p.setFoto(Base64.getDecoder().decode(foto));
        n.setEtiqueta(t);
        n.setLikes(0);
        n.setFecha(LocalDate.now());
        n.setWall(u.getWall());
        n.setPhoto(p);
        n.setUser(userDao.searchByUsername("clienteSOAP"));
        System.out.println("Publicacion creada");
    }



}