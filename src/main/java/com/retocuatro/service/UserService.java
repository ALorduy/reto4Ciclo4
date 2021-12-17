package com.retocuatro.service;

import com.retocuatro.model.User;
import com.retocuatro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Alberto
 */
@Service
public class UserService {
    /**
     * Se instancia UserRepository 
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @return Lista los usuarios
     */
    public List<User> getAll() {
        return userRepository.getAll();
    }

    /**
     *
     * @param id
     * @return Obtiene el usuario por id
     */
    public Optional<User> getUser(int id) {
        
        return userRepository.getUser(id);
    }

    /**
     *
     * @param user
     * @return User
     */
    public User create(User user) {
        
        //Obtiene el maximo id existente en la coleccion
        Optional<User> userIdMaximo = userRepository.lastUserId();
        
        //Valida el si el if es true,luego valida el maximo id existente en base de datos
        if (user.getId() == null) {
            if (userIdMaximo.isEmpty())
                user.setId(1);
            else
                user.setId(userIdMaximo.get().getId() + 1);
        }
        /**
         * Guarda el Usuario
         */
        Optional<User> e = userRepository.getUser(user.getId());
        if (e.isEmpty()) {
            if (emailExists(user.getEmail())==false){
                return userRepository.create(user);
            }else{
                return user;
            }
        }else{
            return user;
        }
        
    }

    /**
     *
     * @param user
     * @return Actualiza el usuario 
     */
    public User update(User user) {

        if (user.getId() != null) {
            Optional<User> userDb = userRepository.getUser(user.getId());
            if (!userDb.isEmpty()) {
                if (user.getIdentification() != null) {
                    userDb.get().setIdentification(user.getIdentification());
                }
                if (user.getName() != null) {
                    userDb.get().setName(user.getName());
                }
                if (user.getAddress() != null) {
                    userDb.get().setAddress(user.getAddress());
                }
                if (user.getCellPhone() != null) {
                    userDb.get().setCellPhone(user.getCellPhone());
                }
                if (user.getEmail() != null) {
                    userDb.get().setEmail(user.getEmail());
                }
                if (user.getPassword() != null) {
                    userDb.get().setPassword(user.getPassword());
                }
                if (user.getZone() != null) {
                    userDb.get().setZone(user.getZone());
                }
                
                userRepository.update(userDb.get());
                return userDb.get();
            } else {
                return user;
            }
        } else {
            return user;
        }
    }

    /**
     *
     * @param userId
     * @return retorna true o false y si la respuesta es True elimina usuario por id
     */
    public boolean delete(int userId) {
        Boolean aBoolean = getUser(userId).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    /**
     *
     * @param email
     * @return Devuelve true o false al momento de validar existencia de usuario
     */
    public boolean emailExists(String email) {
        return userRepository.emailExists(email);
    }

    /**
     *
     * @param email
     * @param password
     * @return Autentica usuario 
     */
    public User authenticateUser(String email, String password) {
        Optional<User> usuario = userRepository.authenticateUser(email, password);

        if (usuario.isEmpty()) {
            return new User();
        } else {
            return usuario.get();
        }
    }
}
