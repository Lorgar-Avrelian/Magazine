package ru.skypro.homework.dto;

import java.util.List;

/**
 * DTO <br>
 * <hr>
 * <br>
 * Comments { <br><br>
 * count	integer($int32) <br>
 * общее количество комментариев <br><br>
 * results	[ <br>
 * Comment { <br><br>
 * author	integer($int32) <br>
 * id автора комментария <br><br>
 * authorImage	string <br>
 * ссылка на аватар автора комментария <br><br>
 * authorFirstName	string <br>
 * имя создателя комментария <br><br>
 * createdAt	integer($int64) <br>
 * дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970 <br><br>
 * pk	integer($int32) <br>
 * id комментария <br><br>
 * text	string <br>
 * текст комментария <br>
 * }]
 * }
 */
public class CommentsDTO {
    private int count;
    private List<CommentDTO> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentDTO> getResults() {
        return results;
    }

    public void setResults(List<CommentDTO> results) {
        this.results = results;
    }
}
