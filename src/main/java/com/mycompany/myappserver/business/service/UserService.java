/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.business.service;

import com.mycompany.myappserver.business.entities.UserEntity;
import com.mycompany.myappserver.exceptions.InfraestructureException;
import com.mycompany.myappserver.persistence.dao.UserDao;
import com.mycompany.myappserver.utils.logger.Log;
import com.sun.jersey.core.spi.factory.ResponseImpl;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


/**
 *
 * @author Alexandra
 */
@Path("/user")
public class UserService implements Serializable{
    
    @Context
    UriInfo uriInfo;
    //@Autowired
    private UserDao userDao = new UserDao();
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> readUsersList (){
        try {
            return (List<UserEntity>)userDao.readEntitiesList(new UserEntity());
        } catch (InfraestructureException ex) {
            Log.error(UserService.class.getName()+".readUsersList"+ex);
        }
        return null;
    }
    
    @GET
    @Path("{cedula}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity readUserByCedula (@PathParam("cedula")String cedula){
        try {
            return userDao.readEntity(new UserEntity(), new Integer(cedula));
        } catch (InfraestructureException ex) {
            Log.error(UserService.class.getName()+".readUsersList"+ex);
        }
        return null;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(UserEntity entity) {
        try {
            userDao.createEntity(entity);
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (Exception ex) {
            Log.error("AvailableProductWS.createAvailableProduct: ERROR:" + ex);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(UserEntity userEntity) {       
        try {
            userDao.updateEntity(userEntity);
        } catch (InfraestructureException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Short id) {
        try {
            userDao.deleteEntity(new UserEntity(), id);
        } catch (InfraestructureException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).build();
    }

}
