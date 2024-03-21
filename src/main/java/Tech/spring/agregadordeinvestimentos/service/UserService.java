package Tech.spring.agregadordeinvestimentos.service;

import Tech.spring.agregadordeinvestimentos.controller.CreateUserDto;
import Tech.spring.agregadordeinvestimentos.controller.UpdateUserDto;
import Tech.spring.agregadordeinvestimentos.entity.User;
import Tech.spring.agregadordeinvestimentos.repository.UserRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepositoryç) {
        this.userRepository = userRepositoryç;
    }

    public Object createUser(CreateUserDto createUserDto) {

        //DTO >> IDENTITY
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }
        public Optional<User>getUserById(String userId){

            return userRepository.findById(UUID.fromString(userId));
    }

        public List<User> listUsers() {
            return  userRepository.findAll();
        }

        public void updateUserById(String userId,
                                   UpdateUserDto updateUserDto) {

            var id = UUID.fromString(userId);

            var userEntity = userRepository.findById(id);

            if (userEntity.isPresent()) {
                var user = userEntity.get();

                if (updateUserDto.username() != null) {
                    user.setUserbname(updateUserDto.username());
                }
                if (updateUserDto.password() != null) {
                    user.setPassword(updateUserDto.password());
                }

                userRepository.save(user);
            }
        }

        public void deleteById(String userId) {
            var id = UUID.fromString(userId);

            var userExists = userRepository.existsById(id);

            if (userExists) {
                    userRepository.deleteById(id);
            }
        }
}
