/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.business.service;

import com.mycompany.myappserver.business.entities.TarjetaCreditoEntity;
import com.mycompany.myappserver.business.entities.UserEntity;
import com.mycompany.myappserver.exceptions.InfraestructureException;
import com.mycompany.myappserver.persistence.dao.TarjetaCreditoDao;
import com.mycompany.myappserver.utils.logger.Log;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alexandra
 */
@Path("/tarjeta-credito")
public class TarjetaCreditoService implements Serializable{
    
    //@Autowired
    TarjetaCreditoDao tarjetaCreditoDao = new TarjetaCreditoDao();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TarjetaCreditoEntity> readTarjetaCreditoList (){
        try {
            return (List<TarjetaCreditoEntity>)tarjetaCreditoDao.readEntitiesList(new TarjetaCreditoEntity());
        } catch (InfraestructureException ex) {
            Log.error(TarjetaCreditoService.class.getName()+".readTCList"+ex);
        }
        return null;
    }
    @GET
    @Path("{bin}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TarjetaCreditoEntity readUserByBim (@PathParam("bin")String bin){
        try {
            return tarjetaCreditoDao.readEntity(new TarjetaCreditoEntity(), new Integer(bin));
        } catch (InfraestructureException ex) {
            Log.error(TarjetaCreditoService.class.getName()+".readUserByBim"+ex);
        }
        return null;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(TarjetaCreditoEntity tarjetaCreditoEntity) {
        try {
            tarjetaCreditoDao.createEntity(tarjetaCreditoEntity);
            return Response.status(Response.Status.OK).entity(tarjetaCreditoEntity).build();
        } catch (Exception ex) {
            Log.error("AvailableProductWS.createAvailableProduct: ERROR:" + ex);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(TarjetaCreditoEntity tarjetaCreditoEntity) {       
        try {
            tarjetaCreditoDao.updateEntity(tarjetaCreditoEntity);
        } catch (InfraestructureException ex) {
            Logger.getLogger(TarjetaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Short id) {
        try {
            tarjetaCreditoDao.deleteEntity(new TarjetaCreditoEntity(), id);
        } catch (InfraestructureException ex) {
            Logger.getLogger(TarjetaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).build();
    }
    
}
