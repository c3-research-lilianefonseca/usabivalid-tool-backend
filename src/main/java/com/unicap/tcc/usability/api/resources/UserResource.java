package com.unicap.tcc.usability.api.resources;

import com.unicap.tcc.usability.api.models.ConfirmationToken;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.dto.UserDTO;
import com.unicap.tcc.usability.api.repository.ConfirmationTokenRepository;
import com.unicap.tcc.usability.api.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Api("User API")
@RequiredArgsConstructor
public class UserResource {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @GetMapping("/ping")
    @ApiOperation("pong")
    public String pong(){
        return "pong";
    }


    @GetMapping("/list-all")
    @ApiOperation("Lista todos os usuários")
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        try {
            List<UserDTO> userDTO = new ArrayList<>();
            userList.forEach(user -> {
                if (Objects.isNull(user.getAdmin())) {
                    user.setAdmin(false);
                }
                UserDTO dto = UserDTO.builder()
                        .login(user.getLogin())
                        .email(user.getEmail())
                        .admin(user.getAdmin())
                        .build();
                userDTO.add(dto);
            });
            return ResponseEntity.ok()
                    .body(userDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/delete/{id}")
    @ApiOperation("Deletar (logicamente) usuário")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") @ApiParam("ID do Usuário") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok()
                .body("Usuário deletado com seucesso");
    }

    @GetMapping(value = "/confirm-account")
    public ResponseEntity<UserDTO> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByUid(UUID.fromString(confirmationToken));

        if (Objects.nonNull(token)) {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            if (user.getIsEnabled().equals(true))
                return ResponseEntity.ok().build();
            user.setIsEnabled(true);
            userRepository.save(user);
            ResponseEntity.ok().body(UserDTO.builder()
                    .admin(user.getAdmin())
                    .email(user.getEmail())
                    .isEnable(user.getIsEnabled())
                    .login(user.getLogin())
                    .build());
        }
        return ResponseEntity.badRequest().build();
    }
}
