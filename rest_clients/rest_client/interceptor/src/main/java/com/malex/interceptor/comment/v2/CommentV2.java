package com.malex.interceptor.comment.v2;

import com.malex.interceptor.comment.v1.CommentV1;
import java.util.List;

/**
 * @param comments - The list of comments
 * @param totalElements - Returns the total amount of elements.
 * @param pageSize - Returns the size of the Slice.
 */
public record CommentV2(List<CommentV1> comments, long totalElements, int pageSize) {}
