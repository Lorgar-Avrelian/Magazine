package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CommentMapper {
    default CommentDTO commentToCommentDto(Comment comment) {
        CommentDTO commentDto = new CommentDTO();
        commentDto.setAuthor(comment.getAuthor().getId());
        commentDto.setAuthorImage(comment.getAuthorImage());
        commentDto.setAuthorFirstName(comment.getAuthorFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setPk(comment.getPk());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    default Comment commentDtoToComment(CommentDTO commentDto, User author) {
        Comment comment = new Comment(null, author, commentDto.getAuthorImage(), commentDto.getAuthorFirstName(), commentDto.getCreatedAt(), commentDto.getPk(), commentDto.getText(), null);
        return comment;
    }

    default CommentsDTO commentListToCommentsDto(List<Comment> commentList) {
        CommentsDTO comments = new CommentsDTO();
        comments.setCount(commentList.size());
        List<CommentDTO> commentDTODtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentDTO commentDto = commentToCommentDto(comment);
            commentDTODtoList.add(commentDto);
        }
        comments.setResult(commentDTODtoList);
        return comments;
    }
}
