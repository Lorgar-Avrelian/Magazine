package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CommentMapper {
    default Comment commentToCommentDto(ru.skypro.homework.entity.Comment comment) {
        Comment commentDto = new Comment();
        commentDto.setAuthor(comment.getAuthor().getId());
        commentDto.setAuthorImage(comment.getAuthorImage());
        commentDto.setAuthorFirstName(comment.getAuthorFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setPk(comment.getPk());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    default ru.skypro.homework.entity.Comment commentDtoToComment(Comment commentDto, User author) {
        ru.skypro.homework.entity.Comment comment = new ru.skypro.homework.entity.Comment(null, author, commentDto.getAuthorImage(), commentDto.getAuthorFirstName(), commentDto.getCreatedAt(), commentDto.getPk(), commentDto.getText(), null);
        return comment;
    }

    default Comments commentListToCommentsDto(List<ru.skypro.homework.entity.Comment> commentList) {
        Comments comments = new Comments();
        comments.setCount(commentList.size());
        List<Comment> commentDtoList = new ArrayList<>();
        for (ru.skypro.homework.entity.Comment comment : commentList) {
            Comment commentDto = commentToCommentDto(comment);
            commentDtoList.add(commentDto);
        }
        comments.setResult(commentDtoList);
        return comments;
    }
}
