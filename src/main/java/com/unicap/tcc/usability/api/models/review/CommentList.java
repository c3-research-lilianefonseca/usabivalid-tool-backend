package com.unicap.tcc.usability.api.models.review;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentList {
    private Set<Comment> commentList;
}
