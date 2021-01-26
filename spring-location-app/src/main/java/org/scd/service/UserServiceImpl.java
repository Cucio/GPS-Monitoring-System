package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.controller.UserController;
import org.scd.model.User;
import org.scd.model.dto.UserLoginDTO;
import org.scd.model.dto.UserRegisterDTO;
import org.scd.model.dto.UserUpdateDTO;
import org.scd.model.security.CustomUserDetails;
import org.scd.repository.RoleRepository;
import org.scd.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;


public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final long idAdmin = 1;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) throws BusinessException {

        if (Objects.isNull(userLoginDTO)) {
            throw new BusinessException(401, "Body null !");
        }

        if (Objects.isNull(userLoginDTO.getEmail())) {
            throw new BusinessException(400, "Email cannot be null ! ");
        }

        if (Objects.isNull(userLoginDTO.getPassword())) {
            throw new BusinessException(400, "Password cannot be null !");
        }


        final User user = userRepository.findByEmail(userLoginDTO.getEmail().trim());

        if (Objects.isNull(user)) {
            throw new BusinessException(401, "Bad credentials !");
        }

        if (!passwordEncoder.matches(userLoginDTO.getPassword().trim(), user.getPassword().trim())) {
            throw new BusinessException(401, "Bad credentials !");
        }

        return user;
    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws BusinessException {
        if (Objects.isNull(userRegisterDTO)) {
            throw new BusinessException(401, "BODY NULL");
        }
        if (Objects.isNull(userRegisterDTO.getEmail())) {
            throw new BusinessException(401, "Email null");
        }
        if (Objects.isNull(userRegisterDTO.getFirstName())) {
            throw new BusinessException(401, "Firstname NULL");
        }
        if (Objects.isNull(userRegisterDTO.getPassword())) {
            throw new BusinessException(401, "Password NULL");
        }
        if (Objects.isNull(userRegisterDTO.getLastName())) {
            throw new BusinessException(401, "Lastname NULL");
        }
        if (!Objects.isNull(userRepository.findByEmail(userRegisterDTO.getEmail()))) {
            throw new BusinessException(409, "The email adress is already used!");
        }
        if (!Objects.equals(userRegisterDTO.getPassword(), userRegisterDTO.getConfirmPassword())) {
            throw new BusinessException(400, "Password must match !");
        }


        if (userRepository.getId().size() == 0) {

            userRepository.insertRandomValue();
            final long idPassword = userRepository.getId().get(userRepository.getId().size() - 1) + 1;
            userRepository.insertValue(userRegisterDTO.getEmail(), passwordEncoder.encode(userRegisterDTO.getPassword()), idPassword);
            User user = new User(userRegisterDTO.getFirstName().trim(), userRegisterDTO.getLastName().trim(), userRegisterDTO.getEmail().trim(), passwordEncoder.encode(userRegisterDTO.getPassword().trim()));
            user.setRoles(roleRepository.findByRole("BASIC_USER"));
            return userRepository.save(user);

        } else {

            final long id_parola = userRepository.getId().get(userRepository.getId().size() - 1) + 1;
            userRepository.insertValue(userRegisterDTO.getEmail(), userRegisterDTO.getConfirmPassword(), id_parola);
            User user = new User(userRegisterDTO.getFirstName().trim(), userRegisterDTO.getLastName().trim(), userRegisterDTO.getEmail().trim(), passwordEncoder.encode(userRegisterDTO.getPassword().trim()));
            user.setRoles(roleRepository.findByRole("BASIC_USER"));
            return userRepository.save(user);

        }
    }

    @Override
    public void delete(long id) throws BusinessException {

        long idUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();

        if (idUser == id || idUser == idAdmin) {
            if (!Objects.isNull(userRepository.findById(id))) {
                userRepository.deleteUser(id);
                userRepository.deleteUserRole(id);
                userRepository.deleteUserLocation(id);

            } else {
                throw new BusinessException(404, "Id not found");
            }
        } else {
            throw (new BusinessException(403, "You dont have the permission to perform this action"));
        }
    }

    @Override
    public User update(UserUpdateDTO userUpdateDTO, long id) throws BusinessException {

        long idUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();

        if (idUser == id || idUser == idAdmin) {

            if (!Objects.isNull(userRepository.findById(id))) {

                if (!Objects.isNull(userUpdateDTO.getEmail())) {
                    if (idUser == idAdmin)
                        userRepository.findById(id).setEmail(userUpdateDTO.getEmail());
                }

                if (!Objects.isNull(userUpdateDTO.getFirstName())) {
                    userRepository.findById(id).setFirstName(userUpdateDTO.getFirstName());
                }

                if (!Objects.isNull(userUpdateDTO.getLastName())) {
                    userRepository.findById(id).setLastName(userUpdateDTO.getLastName());
                }

                if (!Objects.isNull(userUpdateDTO.getPassword())) {
                    userRepository.findById(id).setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
                }

                if (Objects.isNull(userUpdateDTO.getPassword()) && Objects.isNull(userUpdateDTO.getEmail())
                        && Objects.isNull(userUpdateDTO.getFirstName()) && Objects.isNull(userUpdateDTO.getLastName())) {
                    throw new BusinessException(400, "Nothing to update");
                }
            }

            userRepository.updateUser(id, userRepository.findById(id).getEmail(),
                    userRepository.findById(id).getFirstName(), userRepository.findById(id).getLastName(),
                    userRepository.findById(id).getPassword());

        } else {
            throw (new BusinessException(403, "You dont have the permission to perform this action"));
        }
        return userRepository.findById(id);
    }


}
