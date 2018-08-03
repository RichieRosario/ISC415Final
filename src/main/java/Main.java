

import freemarker.template.Configuration;
import Rutas.*;
import freemarker.template.Version;
import hibernate.HibernateUtil;
import org.simpleframework.xml.Serializer;
import servicios.ConnectionService;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import encapsulacion.*;
import servicios.*;
import org.simpleframework.xml.core.Persister;
import spark.Request;
import spark.Response;

import java.io.ByteArrayOutputStream;

import static spark.Spark.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import dao.*;
import modelo.*;
import spark.template.freemarker.FreeMarkerEngine;

import javax.imageio.ImageIO;

import static spark.Spark.staticFileLocation;


public class Main {
    public final static String ACCEPT_TYPE_JSON = "application/json";
    public final static String ACCEPT_TYPE_XML = "application/xml";
    public final static int BAD_REQUEST = 400;
    public final static int ERROR_INTERNO = 500;

    public static void main(String[] args) throws Exception {



        UserDaoImpl usuarioadmin = new UserDaoImpl(User.class);
        ProfileDaoImpl profileadmin = new ProfileDaoImpl(Profile.class);
        WallDaoImpl wallDao = new WallDaoImpl(Wall.class);
        EventDaoImpl eventoDao = new EventDaoImpl(Event.class);
        PostDaoImpl postDao = new PostDaoImpl(Post.class);
        SoapUtils.stop();
        SoapUtils.init();
        try{
            ConnectionService.startDb();
        }
        catch (SQLException e){

            e.printStackTrace();
        }
        final Configuration configuration = new Configuration(new Version(2, 3, 26));

        configuration.setClassForTemplateLoading(Main.class, "/templates");

        staticFileLocation("/public");

        HibernateUtil.buildSessionFactory().openSession().close();
        User temp = usuarioadmin.searchByUsername("admin");

        if(temp == null){
            User usuarioPorDefecto = new User(1, "admin", "admin", "admin@gwebmaster.me",true,null,null,null);
                      usuarioadmin.add(usuarioPorDefecto);
                      Profile perfil = new Profile();
                      perfil.setUser(usuarioPorDefecto);
                      perfil.setSexo('M');
                      perfil.setLugartrabajo("PUCMM");
                      perfil.setLugarnacimiento("Santiago De Los Caballeros, Republica Dominicana");
                      perfil.setLugarestudio("PUCMM");
                      perfil.setNombre("John");
                      perfil.setApellido("Doe");
                      perfil.setFechanacimiento(LocalDate.parse("1980-10-10"));
                      perfil.setCiudadactual("Santo Domingo, Republica Dominicana");
            BufferedImage imagen = null;
            File here = new File(".");

                String path = here.getCanonicalPath()+"/src/main/resources/public/img/johndoe.jpg";

            try {
                imagen = ImageIO.read(new File((path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream imagenb = new ByteArrayOutputStream();
            try {
                ImageIO.write(imagen, "jpg",imagenb);
            } catch (IOException e) {
                e.printStackTrace();
            }


            perfil.setProfilepic(imagenb.toByteArray());
                      profileadmin.add(perfil);

                      Wall wall = new Wall();
                      wall.setUser(usuarioPorDefecto);
            wallDao.add(wall);

            Event evento = new Event();
            evento.setEvento(perfil.getNombre()+" "+perfil.getApellido()+" se ha unido a Una Red Social");
            evento.setUser(usuarioPorDefecto);
            evento.setFecha(LocalDate.now());
            evento.setWall(wall);
            eventoDao.add(evento);


            User usuarioPorDefecto2 = new User(50, "clienteSOAP", "admin", "admin@gwebmaster.me",true,null,null,null);
            usuarioadmin.add(usuarioPorDefecto2);
            Profile perfil2 = new Profile();
            perfil2.setUser(usuarioPorDefecto2);
            perfil2.setSexo('M');
            perfil2.setLugartrabajo("PUCMM");
            perfil2.setLugarnacimiento("Santiago De Los Caballeros, Republica Dominicana");
            perfil2.setLugarestudio("PUCMM");
            perfil2.setNombre("Cliente");
            perfil2.setApellido("SOAP");
            perfil2.setFechanacimiento(LocalDate.parse("1980-10-10"));
            perfil2.setCiudadactual("Santo Domingo, Republica Dominicana");



            perfil2.setProfilepic(imagenb.toByteArray());
            profileadmin.add(perfil2);

            Wall wall2 = new Wall();
            wall2.setUser(usuarioPorDefecto2);
            wallDao.add(wall2);

            Event evento2 = new Event();
            evento2.setEvento(perfil2.getNombre()+" "+perfil2.getApellido()+" se ha unido a Una Red Social");
            evento2.setUser(usuarioPorDefecto2);
            evento2.setFecha(LocalDate.now());
            evento2.setWall(wall2);
            eventoDao.add(evento2);

            User usuarioPorDefecto3 = new User(49, "clienteREST", "admin", "admin@gwebmaster.me",true,null,null,null);
            usuarioadmin.add(usuarioPorDefecto3);
            Profile perfil3 = new Profile();
            perfil3.setUser(usuarioPorDefecto3);
            perfil3.setSexo('F');
            perfil3.setLugartrabajo("PUCMM");
            perfil3.setLugarnacimiento("Santiago De Los Caballeros, Republica Dominicana");
            perfil3.setLugarestudio("PUCMM");
            perfil3.setNombre("Cliente");
            perfil3.setApellido("REST");
            perfil3.setFechanacimiento(LocalDate.parse("1980-10-10"));
            perfil3.setCiudadactual("Santo Domingo, Republica Dominicana");



            perfil3.setProfilepic(imagenb.toByteArray());
            profileadmin.add(perfil3);

            Wall wall3 = new Wall();
            wall3.setUser(usuarioPorDefecto3);
            wallDao.add(wall3);

            Event evento3 = new Event();
            evento3.setEvento(perfil3.getNombre()+" "+perfil3.getApellido()+" se ha unido a Una Red Social");
            evento3.setUser(usuarioPorDefecto3);
            evento3.setFecha(LocalDate.now());
            evento3.setWall(wall3);
            eventoDao.add(evento3);


        }

        RESTService rs = RESTService.getInstancia();

        //Serializando XML.
        Serializer serializer = new Persister();


        //Manejo de Excepciones.
        exception(IllegalArgumentException.class, (exception, request, response) -> {
            manejarError(Main.BAD_REQUEST, exception, request, response);
        });

        exception(JsonSyntaxException.class, (exception, request, response) -> {
            manejarError(Main.BAD_REQUEST, exception, request, response);
        });

        exception(Exception.class, (exception, request, response) -> {
            manejarError(Main.ERROR_INTERNO, exception, request, response);
        });


        //rutas servicios RESTFUL
        path("/rest", () -> {
            //rutas del api
            path("/publicaciones", () -> {


                //listar todas las publicaciones dado un usario.
                get("/:username", (request, response) -> {
                    return rs.getPublicaciones(request.params("username"));
                }, JsonUtils.json());


                //crea un estudiante
                post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {

                    PostService postnuevo = null;

                    //verificando el tipo de dato.
                    switch (request.headers("Content-Type")) {
                        case Main.ACCEPT_TYPE_JSON:
                            postnuevo = new Gson().fromJson(request.body(), PostService.class);
                            break;
                        case Main.ACCEPT_TYPE_XML:
                            //recibiendo el xml y convirtiendo a JSON.
                            postnuevo = serializer.read(PostService.class, request.body());
                            break;
                        default:
                            throw new IllegalArgumentException("Error el formato no disponible");
                    }

                    return rs.crearPublicacion(postnuevo);
                }, JsonUtils.json());



            });
        });



        HibernateUtil.openSession().close();

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);
        new RutasWeb(freeMarkerEngine);

    }


    /**
     *
     * @param codigo
     * @param exception
     * @param request
     * @param response
     */
    private static void manejarError(int codigo, Exception exception, Request request, Response response) {
        response.status(codigo);
        response.body(JsonUtils.toJson(new ErrorRespuesta(100, exception.getMessage())));
        exception.printStackTrace();
    }
}