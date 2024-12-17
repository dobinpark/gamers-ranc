package jp.gamers_ranc.Entity.user;

public enum PointRule {

    POST_CREATION(50L, "게시글 작성"),
    COMMENT_CREATION(10L, "댓글 작성");

    private final Long points;
    private final String description;

    PointRule(Long points, String description) {
        this.points = points;
        this.description = description;
    }

    public Long getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }
}
