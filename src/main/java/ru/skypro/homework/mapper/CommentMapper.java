package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.entity.Comment;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
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
