/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.persistence.dao;

import com.mycompany.myappserver.business.entities.TarjetaCreditoEntity;
import com.mycompany.myappserver.business.entities.UserEntity;
import com.mycompany.myappserver.exceptions.InfraestructureException;
import com.mycompany.myappserver.persistence.base.BaseDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexandra
 */
@Repository
public class TarjetaCreditoDao extends BaseDao<TarjetaCreditoEntity> {
    
    public TarjetaCreditoDao () {
        super("MyAppBD-pu");
    }
    public static void main (String[] argsv) {
        //CREATE
        TarjetaCreditoDao daoImpl = new TarjetaCreditoDao();
        //READ
        try {
            for (TarjetaCreditoEntity tarjetaCreditoEntity:daoImpl.readEntitiesList(new TarjetaCreditoEntity()))
                System.out.println(tarjetaCreditoEntity);
        } catch (InfraestructureException ex) {
            Logger.getLogger(TarjetaCreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
