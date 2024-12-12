package com.ra.md05_project.controller.admin;

import com.ra.md05_project.dto.comment.CommentAddDTO;
import com.ra.md05_project.dto.comment.CommentUpdateDTO;
import com.ra.md05_project.dto.user.ResponseDTOSuccess;
import com.ra.md05_project.model.entity.ver1.Category;
import com.ra.md05_project.model.entity.ver1.Cinema;
import com.ra.md05_project.model.entity.ver1.Comment;
import com.ra.md05_project.service.comment.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/admin/comment")
public class ADCommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping
    public ResponseEntity<Page<Comment>> findAllComment(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        Sort sortOrder = Sort.by(sort);
        sortOrder = direction.equalsIgnoreCase("desc") ? sortOrder.descending() : sortOrder.ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Comment> comments = commentService.findAll(search ,pageable );
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById (@PathVariable Long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentAddDTO commentAddDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(commentAddDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,@Valid @RequestBody CommentUpdateDTO commentUpdateDTO) throws IOException {
            return new ResponseEntity<>(commentService.update(id, commentUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
            commentService.delete(id);
            return new ResponseEntity<>(new ResponseDTOSuccess<>("Delete successfully",200),HttpStatus.OK);
    }
}
