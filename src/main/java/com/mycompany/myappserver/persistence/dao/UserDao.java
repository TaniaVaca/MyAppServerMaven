/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.persistence.dao;

import com.mycompany.myappserver.business.entities.UserEntity;
import com.mycompany.myappserver.exceptions.InfraestructureException;
import com.mycompany.myappserver.persistence.base.BaseDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexandra
 */
@Repository
public class UserDao extends BaseDao<UserEntity> implements Serializable{
    
    public UserDao () {
        super("MyAppBD-pu");
    }
    
    public UserEntity readUserByCedula (Integer id){
        return null;
    }
    
    public static void main (String[] argsv) {
        //CREATE
        UserDao daoImpl = new UserDao();
        //READ
        try {
            for (UserEntity userEntity:daoImpl.readEntitiesList(new UserEntity()))
                System.out.println(userEntity);
        } catch (InfraestructureException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //CREATE
//        UserEntity userEntity = new UserEntity(new Integer(123));
//        userEntity.setNombre("pepe");
//        userEntity.setApellido("molano");
//        
//        try {
//            daoImpl.createEntity(userEntity);
//        } catch (InfraestructureException ex) {
//            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        //CREATE
//        UserEntity userEntity = new UserEntity(new Integer(123));
//        userEntity.setNombre("pepe");
//        userEntity.setApellido("molano");
//        
//        try {
//            daoImpl.createEntity(userEntity);
//        } catch (InfraestructureException ex) {
//            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
    
}
