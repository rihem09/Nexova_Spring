package com.example.microservicecrud.service;

import com.example.microservicecrud.entity.User;
import com.example.microservicecrud.repository.UserRepository;
import com.example.microservicecrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        // Définir la date d'enregistrement
        user.setRegistrationDate(LocalDate.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec id: " + id));
        user.setNom(userDetails.getNom());
        user.setPrenom(userDetails.getPrenom());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        // On ne modifie pas la date d'enregistrement
        user.setPassword(userDetails.getPassword());
        user.setPhone(userDetails.getPhone());
        user.setNumeroDeContrat(userDetails.getNumeroDeContrat());
        user.setTypeDeContrat(userDetails.getTypeDeContrat());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> signIn(String email, String password) {
        // Vérification simple par email et mot de passe
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public User signUp(User user) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        user.setRegistrationDate(LocalDate.now());
        return userRepository.save(user);
    }
}
