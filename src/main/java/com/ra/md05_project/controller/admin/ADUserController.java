package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.constant.RoleName;
import com.ra.md05_project.model.entity.ver1.Festival;
import com.ra.md05_project.model.entity.ver1.User;
import com.ra.md05_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin/users")
public class ADUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> findAllUser(
            @RequestParam(required = false) String search,
            @PageableDefault(page = 0, size = 5, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> users = userService.findAll(search, pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById (@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user != null) {
            if(user.getRoles().stream().anyMatch(r-> r.getRoleName().equals(RoleName.ROLE_ADMIN))){
                return new ResponseEntity<>(new ResponseDTOSuccess<>("Can not block ADMIN",403), HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(userService.changeStatus(userId), HttpStatus.OK);
        }else{
            throw new NoSuchElementException("User with ID " + userId + " not found");
        }
    }
}
